package com.example.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

public interface HateoasServiceInterface<T, Long> {

    EntityModel<T> getEntityModelWithCreateUser(T userDto);

    EntityModel<T> getEntityModelWithUpdateUser(T updateUserDto, Long id);

    EntityModel<T> getEntityModelWithDeleteUser(Long id);

    EntityModel<T> getEntityModelWithGetUser(Long id);

    CollectionModel<EntityModel<T>> getEntityModelWithGetAllUsers();
}
