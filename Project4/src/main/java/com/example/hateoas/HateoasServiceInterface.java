package com.example.hateoas;

import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;

/**
 * Интерфейс сервиса для работы с HATEOAS (Hypermedia as the Engine of Application State).
 * Предоставляет методы для оборачивания сущностей в HATEOAS-модели с соответствующими ссылками.
 *
 * <p>HATEOAS позволяет клиентам динамически обнаруживать доступные действия через гипермедиа-ссылки,
 * что делает API более самодокументированным и удобным для навигации.</p>
 *
 * @param <T>    тип DTO сущности, с которой работает сервис
 * @param <Long> тип идентификатора сущности (должен быть ссылочным типом, например {@link java.lang.Long})
 * @see org.springframework.hateoas.EntityModel
 * @see org.springframework.hateoas.CollectionModel
 */
public interface HateoasServiceInterface<T, Long> {

    /**
     * Создает HATEOAS-модель для вновь созданной сущности пользователя.
     * Добавляет соответствующие ссылки для работы с созданным ресурсом.
     *
     * @param userDto DTO объект с данными созданного пользователя
     * @return EntityModel с данными пользователя и HATEOAS-ссылками
     */
    EntityModel<T> getEntityModelWithCreateUser(T userDto);

    /**
     * Создает HATEOAS-модель для обновленной сущности пользователя.
     * Добавляет ссылки для дальнейших операций с обновленным ресурсом.
     *
     * @param updateUserDto DTO объект с обновленными данными пользователя
     * @param id            идентификатор обновленного пользователя
     * @return EntityModel с обновленными данными пользователя и HATEOAS-ссылками
     */
    EntityModel<T> getEntityModelWithUpdateUser(T updateUserDto, Long id);

    /**
     * Создает HATEOAS-модель для удаленной сущности пользователя.
     * Может содержать ссылки для навигации или подтверждение операции удаления.
     *
     * @param id идентификатор удаленного пользователя
     * @return EntityModel с данными об операции удаления и HATEOAS-ссылками
     */
    EntityModel<T> getEntityModelWithDeleteUser(Long id);

    /**
     * Создает HATEOAS-модель для сущности пользователя, полученной по идентификатору.
     * Добавляет ссылки для возможных операций с найденным ресурсом.
     *
     * @param id идентификатор пользователя
     * @return EntityModel с данными пользователя и HATEOAS-ссылками
     */
    EntityModel<T> getEntityModelWithGetUser(Long id);

    /**
     * Создает HATEOAS-модель для коллекции всех сущностей пользователей.
     * Добавляет ссылки для навигации по коллекции и операций над ней.
     *
     * @return CollectionModel с коллекцией пользователей и HATEOAS-ссылками
     */
    CollectionModel<EntityModel<T>> getEntityModelWithGetAllUsers();
}