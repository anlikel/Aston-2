package com.example.service;

import com.example.dto.UserDto;
import com.example.dto.UserMapper;
import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.repository.UserRepository;
import com.example.util.UtilValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Сервисный класс для управления операциями с пользователями.
 * Обеспечивает бизнес-логику для создания, чтения, обновления и удаления пользователей.
 * Включает валидацию данных, проверку уникальности email и логирование операций.
 */
@Service
@Transactional
public class UserService {

    private final UserRepository userRepository;

    private final KafkaService kafkaService;

    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    /**
     * Конструктор для внедрения зависимости UserRepository.
     *
     * @param userRepository репозиторий для работы с данными пользователей
     */
    public UserService(UserRepository userRepository, KafkaService kafkaService) {
        this.userRepository = userRepository;
        this.kafkaService = kafkaService;
    }

    /**
     * Получает пользователя по указанному идентификатору.
     * Выполняет поиск в базе данных и возвращает найденного пользователя.
     * Если пользователь не найден, выбрасывает исключение MyCustomException.
     *
     * @param userId идентификатор пользователя для поиска
     * @return найденная сущность пользователя
     * @throws MyCustomException если пользователь с указанным ID не найден
     */
    @Transactional(readOnly = true)
    public UserDto getUserById(Long userId) {
        logger.info("DB event: try to getUserById {}", userId);
        try {
            UserEntity user = userRepository.getUserById(userId)
                    .orElseThrow(() -> new MyCustomException("User not found with id: " + userId));
            logger.info("DB event: success getUserById {}", userId);
            UserDto userDto = UserMapper.toUserDto(user);
            return userDto;
        } catch (MyCustomException e) {
            UserDto userDto = new UserDto(null, null, 0);
            userDto.setResult(e.getMessage());
            logger.error("DB event: failed getUserById {}, error: {}", userId, e.getMessage());
            return userDto;
        }
    }

    /**
     * Получает список всех пользователей из базы данных.
     * Возвращает пустой список, если пользователи отсутствуют.
     *
     * @return список всех пользователей
     */
    @Transactional(readOnly = true)
    public List<UserDto> getAllUsers() {
        logger.info("DB event: try to getAllUsers");
        List<UserEntity> entities = userRepository.getAllUsers();
        List<UserDto> users = entities.stream()
                .map(UserMapper::toUserDto)
                .collect(Collectors.toList());
        logger.info("DB event: success getAllUsers, found {} users", users.size());
        return users;
    }

    /**
     * Удаляет пользователя по указанному идентификатору.
     * Перед удалением проверяет валидность ID и существование пользователя.
     *
     * @param userId идентификатор пользователя для удаления
     * @return true если пользователь успешно удален
     * @throws MyCustomException если ID невалиден или пользователь не найден
     */
    public UserDto deleteUserById(Long userId) {
        logger.info("DB event: try to deleteUserById {}", userId);

        if (!UtilValidator.isValidId(String.valueOf(userId))) {
            logger.warn("DB event: validation failed for deleteUserById {}, wrong id format", userId);
            UserDto userDto = new UserDto(null, null, 0);
            userDto.setResult("wrong id, should be in range from 1 to LongMax");
            return userDto;
        }
        Optional<UserEntity> userOptional = userRepository.deleteUserById(userId);
        if (userOptional.isEmpty()) {
            logger.error("DB event: user not found for deleteUserById {}", userId);
            UserDto userDto = new UserDto(null, null, 0);
            userDto.setResult("User not found with id: " + userId);
            return userDto;
        }
        logger.info("DB event: success deleteUserById {}", userId);
        UserEntity deletedUser = userOptional.get();
        kafkaService.sendEmailOnUserDelete(deletedUser);
        UserDto deletedUserDto = UserMapper.toUserDto(deletedUser);
        return deletedUserDto;
    }

    /**
     * Создает нового пользователя в системе.
     * Выполняет комплексную валидацию данных:
     * - Проверяет уникальность email
     * - Валидирует формат имени
     * - Валидирует формат email
     * - Проверяет корректность возраста
     *
     * @param userDto POJO объект для создания UserEntity
     * @return созданная сущность пользователя с присвоенным ID
     * @throws MyCustomException если данные не проходят валидацию или email уже существует
     */
    public UserDto createUser(UserDto userDto) {
        logger.info("DB event: try to createUser {}", userDto.getName());
        if (userRepository.existsByEmail(userDto.getEmail())) {
            logger.error("DB event: user already exists with unique email {}", userDto.getEmail());
            userDto.setResult("User already exists with unique email: " + userDto.getEmail());
            return userDto;
        }
        if (!UtilValidator.isValidName(userDto.getName())) {
            logger.warn("DB event: validation failed for createUser {}, wrong name format", userDto.getId());
            userDto.setResult("wrong name, should start from Uppercase letter");
            return userDto;
        }
        if (!UtilValidator.isValidEmail(userDto.getEmail())) {
            logger.warn("DB event: validation failed for createUser {}, wrong email format", userDto.getEmail());
            userDto.setResult("wrong email");
            return userDto;
        }
        if (!UtilValidator.isValidAge(userDto.getAge())) {
            logger.warn("DB event: validation failed for createUser {}, wrong age format", userDto.getAge());
            userDto.setResult("wrong age should be in range from 1 to 100");
            return userDto;
        }
        UserEntity user = UserMapper.toEntity(userDto);
        UserEntity savedUser = userRepository.saveUser(user);
        kafkaService.sendEmailOnUserCreate(savedUser);
        UserDto savedUserDto = UserMapper.toUserDto(savedUser);
        logger.info("DB event: success createUser {} with id {}", user.getName(), savedUser.getId());
        return savedUserDto;
    }

    /**
     * Обновляет данные существующего пользователя.
     * Выполняет проверки:
     * - Валидность ID пользователя
     * - Существование пользователя в базе данных
     * - Корректность обновляемых данных (имя, email, возраст)
     *
     * @param userDto POJO объект с переданными данными
     * @return обновленная сущность пользователя
     * @throws MyCustomException если пользователь не найден или данные невалидны
     */
    public UserDto updateUser(UserDto userDto, Long id) {
        logger.info("DB event: try to updateUser {}", id);
        if (!UtilValidator.isValidId(String.valueOf(id))) {
            logger.warn("DB event: validation failed for updateUser {}, wrong id ", id);
            userDto.setResult("wrong id, should be in range from 1 to LongMax");
            return userDto;
        }
        if (!userRepository.existsById(id)) {
            logger.error("DB event: user not found for updateUser {}", id);
            userDto.setResult("User not found with id: " + id);
            return userDto;
        }
        if (!UtilValidator.isValidName(userDto.getName())) {
            logger.warn("DB event: validation failed for updateUser {}, wrong name format", userDto.getName());
            userDto.setResult("wrong name, should start from Uppercase letter");
            return userDto;
        }
        if (!UtilValidator.isValidEmail(userDto.getEmail())) {
            logger.warn("DB event: validation failed for updateUser {}, wrong email format", userDto.getEmail());
            userDto.setResult("wrong email");
            return userDto;
        }
        if (!UtilValidator.isValidAge(userDto.getAge())) {
            logger.warn("DB event: validation failed for updateUser {}, wrong age format", userDto.getAge());
            userDto.setResult("wrong age should be in range from 1 to 100");
            return userDto;
        }
        UserEntity user = UserMapper.toEntity(userDto);
        user.setId(id);
        UserEntity updatedUser = userRepository.saveUser(user);
        UserDto updatedUserDto = UserMapper.toUserDto(updatedUser);
        logger.info("DB event: success updateUser {}", user.getId());
        return updatedUserDto;
    }
}