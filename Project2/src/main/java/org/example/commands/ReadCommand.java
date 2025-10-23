package org.example.commands;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.repository.UserRepository;
import org.example.util.UtilReader;

import java.util.List;

public class ReadCommand implements Command{
    @Override
    public void execute() {
        UserRepository userRepository=new UserRepository();
        Long userId=UtilReader.readId();
        User user=userRepository.getUserById(userId);
        if(user==null){
            throw new MyCustomException("no user with current id");
        }
        UtilReader.writeMessage("find user+\n"+user);
    }
}
