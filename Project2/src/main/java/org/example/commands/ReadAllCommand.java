package org.example.commands;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.example.util.UtilReader;

import java.util.List;

public class ReadAllCommand implements Command{@Override
public void execute() {
    UserRepository userRepository=new UserRepository();
    List<User> users=userRepository.getAllUsers();
    if(users==null || users.isEmpty()) {
        throw new MyCustomException("в базе нет пользователей");
    }
    users.stream().map(u->u.toString()).forEach(UtilReader::writeMessage);
}
}
