package org.example.commands;

import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.example.util.UtilReader;

/**
 * Команда удаления пользователя
 */
public class DeleteCommand implements Command {
    /**
     * Удаляет пользователя по ID
     */
    @Override
    public void execute() {
        UserRepository userRepository = new UserRepository();
        try {
            Long userId = UtilReader.readId();
            userRepository.deleteUserById(userId);
        } catch (MyCustomException e) {
            throw new MyCustomException(e.getMessage());
        }
    }
}
