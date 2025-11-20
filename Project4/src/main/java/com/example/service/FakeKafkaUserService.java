package com.example.service;

import com.example.dto.UserDto;
import com.example.dto.UserMapper;
import com.example.entities.UserEntity;
import com.example.exceptions.MyCustomException;
import com.example.repository.UserRepository;
import com.example.serviceinterfaces.UserServiceInterface;
import com.example.util.UtilValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * Фейковый сервисный класс для управления операциями с пользователями с использованием FakeKafkaService.
 * Обеспечивает бизнес-логику для создания и удаления пользователей с отправкой событий через фейковый Kafka сервис.
 * Включает валидацию данных, проверку уникальности email и логирование операций.
 */
@Service
@Transactional
public class FakeKafkaUserService implements UserServiceInterface<UserDto, Long> {

    private final UserRepository userRepository;
    private final FakeKafkaService kafkaService;
    private static final Logger logger = LoggerFactory.getLogger(FakeKafkaUserService.class);

    /**
     * Конструктор для внедрения зависимостей UserRepository и FakeKafkaService.
     *
     * @param userRepository репозиторий для работы с данными пользователей
     * @param kafkaService   фейковый Kafka сервис для отправки событий
     */
    public FakeKafkaUserService(UserRepository userRepository, FakeKafkaService kafkaService) {
        this.userRepository = userRepository;
        this.kafkaService = kafkaService;
    }

    /**
     * Удаляет пользователя по указанному идентификатору.
     * Перед удалением проверяет валидность ID и существование пользователя.
     * После успешного удаления отправляет событие через фейковый Kafka сервис.
     *
     * @param userId идентификатор пользователя для удаления
     * @return DTO удаленного пользователя или DTO с сообщением об ошибке
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
     * После успешного создания отправляет событие через фейковый Kafka сервис.
     *
     * @param userDto DTO объект с данными для создания пользователя
     * @return DTO созданного пользователя или DTO с сообщением об ошибке
     */
    public UserDto createUser(UserDto userDto) {
        logger.info("DB event: try to createUser {}", userDto.getName());
        if (userRepository.existsByEmail(userDto.getEmail())) {
            logger.error("DB event: user already exists with unique email {}", userDto.getEmail());
            userDto.setResult("User already exists with unique email: " + userDto.getEmail());
            return userDto;
        }
        if (!UtilValidator.isValidName(userDto.getName())) {
            logger.warn("DB event: validation failed for createUser {}, wrong name format", userDto.getName());
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

    @Override
    public UserDto updateUser(UserDto userDto, Long id) {
        return null;
    }

    @Override
    public List<UserDto> getAllUsers() {
        return List.of();
    }
}