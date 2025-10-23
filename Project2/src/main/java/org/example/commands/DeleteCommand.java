package org.example.commands;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.example.util.UtilReader;

public class DeleteCommand implements Command {
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
