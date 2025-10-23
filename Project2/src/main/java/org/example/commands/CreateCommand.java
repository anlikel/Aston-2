package org.example.commands;

import org.example.entities.User;
import org.example.entitybuilders.ClassTag;
import org.example.entitybuilders.EntityBuilder;
import org.example.entitybuilders.EntityBuilderFactory;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;

/**
 * Команда создания пользователя
 */
public class CreateCommand implements Command {
    /**
     * Создает нового пользователя
     */
    @Override
    public void execute() {
        try {
            UserRepository userRepository = new UserRepository();
            EntityBuilder<User> entityBuilder = EntityBuilderFactory.getBuilder(ClassTag.USER);
            User user = entityBuilder.build();
            userRepository.saveUser(user);
        } catch (MyCustomException e) {
            throw new MyCustomException(e.getMessage());
        }
    }
}