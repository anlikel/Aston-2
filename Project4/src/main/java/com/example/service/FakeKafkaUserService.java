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

import java.util.Optional;

/**
 * Сервисный класс для управления операциями с пользователями.
 * Обеспечивает бизнес-логику для создания, чтения, обновления и удаления пользователей.
 * Включает валидацию данных, проверку уникальности email и логирование операций.
 */
@Service
@Transactional
public class FakeKafkaUserService {

    private final UserRepository userRepository;

    private final FakeKafkaService kafkaService;

    private static final Logger logger = LoggerFactory.getLogger(FakeKafkaUserService.class);

    /**
     * Конструктор для внедрения зависимости UserRepository.
     *
     * @param userRepository репозиторий для работы с данными пользователей
     */
    public FakeKafkaUserService(UserRepository userRepository, FakeKafkaService kafkaService) {
        this.userRepository = userRepository;
        this.kafkaService = kafkaService;
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


}
