package com.example.serviceinterfaces;

import java.util.List;


public interface UserServiceInterface<T, Long> {
    public List<T> getAllUsers();

    public T deleteUserById(Long userId);

    public T createUser(T userDto);

    public T updateUser(T userDto, Long id);
}
