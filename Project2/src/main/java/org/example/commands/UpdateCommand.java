package org.example.commands;

import org.example.entities.User;
import org.example.entitybuilders.ClassTag;
import org.example.entitybuilders.EntityBuilder;
import org.example.entitybuilders.EntityBuilderFactory;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.example.util.UtilReader;

public class UpdateCommand implements Command {
    @Override
    public void execute() {
        try {
            UserRepository userRepository = new UserRepository();
            EntityBuilder<User> entityBuilder = EntityBuilderFactory.getBuilder(ClassTag.USER);
            Long userId = UtilReader.readId();
            User currentUser = userRepository.getUserById(userId);
            if (currentUser == null) {
                throw new MyCustomException("User with id " + userId + " not found");
            }
            User user = entityBuilder.build();
            user.setId(currentUser.getId());
            userRepository.updateUser(user);
        } catch (MyCustomException e) {
            throw new MyCustomException(e.getMessage());
        }
    }
}
