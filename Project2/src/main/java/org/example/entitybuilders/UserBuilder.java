package org.example.entitybuilders;

import org.example.entities.UserEntity;
import org.example.exceptions.MyCustomException;
import org.example.util.UtilReader;

/**
 * Строитель для создания объектов пользователей.
 * Реализует интерфейс EntityBuilder для типа User.
 * Обрабатывает ввод данных пользователя и создает экземпляры User.
 */
public class UserBuilder implements EntityBuilder<UserEntity> {

    /**
     * Создает новый экземпляр пользователя на основе введенных данных.
     * Выполняет чтение имени, email и возраста через UtilReader.
     * Обрабатывает возможные исключения при вводе данных.
     *
     * @return новый объект User с заполненными полями
     * @throws MyCustomException если произошла ошибка при вводе данных
     */
    @Override
    public UserEntity build() {
        try {
            String name = UtilReader.readName();
            String email = UtilReader.readEmail();
            int age = UtilReader.readAge();
            return new UserEntity(name, email, age);
        } catch (MyCustomException e) {
            throw new MyCustomException(e.getMessage());
        }
    }
}