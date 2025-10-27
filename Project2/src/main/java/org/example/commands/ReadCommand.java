package org.example.commands;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.example.util.UtilReader;

/**
 * Команда чтения пользователя по ID
 */
public class ReadCommand implements Command {
    /**
     * Находит и отображает пользователя по ID
     */
    @Override
    public void execute() {
        UserRepository userRepository = new UserRepository();
        Long userId = UtilReader.readId();
        UserEntity user = userRepository.getUserById(userId);
        if (user == null) {
            throw new MyCustomException("no user with current id");
        }
        UtilReader.writeMessage("find user\n" + user);
    }
}
