package com.example.service;

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
    public UserEntity deleteUserById(Long userId) {
        logger.info("DB event: try to deleteUserById {}", userId);

        if (!UtilValidator.isValidId(String.valueOf(userId))) {
            logger.warn("DB event: validation failed for deleteUserById {}, wrong id format", userId);
            throw new MyCustomException("wrong id, should be in range from 1 to LongMax");
        }
        Optional<UserEntity> userOptional = userRepository.deleteUserById(userId);
        if (userOptional.isEmpty()) {
            logger.error("DB event: user not found for deleteUserById {}", userId);
            throw new MyCustomException("User not found with id: " + userId);
        }
        logger.info("DB event: success deleteUserById {}", userId);
        UserEntity user = userOptional.get();
        kafkaService.sendEmailOnUserDelete(user);
        return user;
    }

    /**
     * Создает нового пользователя в системе.
     * Выполняет комплексную валидацию данных:
     * - Проверяет уникальность email
     * - Валидирует формат имени
     * - Валидирует формат email
     * - Проверяет корректность возраста
     *
     * @param user сущность пользователя для создания
     * @return созданная сущность пользователя с присвоенным ID
     * @throws MyCustomException если данные не проходят валидацию или email уже существует
     */

    public UserEntity createUser(UserEntity user) {
        logger.info("DB event: try to createUser {}", user.getName());
        if (userRepository.existsByEmail(user.getEmail())) {
            logger.error("DB event: user already exists with unique email {}", user.getEmail());
            throw new MyCustomException("User already exists with unique email: " + user.getEmail());
        }
        if (!UtilValidator.isValidName(user.getName())) {
            logger.warn("DB event: validation failed for createUser {}, wrong name format", user.getId());
            throw new MyCustomException("wrong name, should start from Uppercase letter");
        }
        if (!UtilValidator.isValidEmail(user.getEmail())) {
            logger.warn("DB event: validation failed for createUser {}, wrong email format", user.getEmail());
            throw new MyCustomException("wrong email");
        }
        if (!UtilValidator.isValidAge(user.getAge())) {
            logger.warn("DB event: validation failed for createUser {}, wrong age format", user.getAge());
            throw new MyCustomException("wrong age should be in range from 1 to 100");
        }
        UserEntity savedUser = userRepository.saveUser(user);
        kafkaService.sendEmailOnUserCreate(savedUser);
        logger.info("DB event: success createUser {} with id {}", user.getName(), savedUser.getId());
        return savedUser;
    }


}
