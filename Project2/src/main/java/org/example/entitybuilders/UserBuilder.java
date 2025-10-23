package org.example.entitybuilders;

import org.example.entities.User;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;

public class UserBuilder implements EntityBuilder<User>{
    @Override
    public User build() {
        try {
            String name = UtilReader.readName();
            String email = UtilReader.readEmail();
            int age = UtilReader.readAge();
            return new User(name, email, age);
        }
        catch(MyCustomException e){
            throw new MyCustomException(e.getMessage());
        }
    }
}
