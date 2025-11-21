package com.example.serviceinterfaces;

import java.util.List;

/**
 * Интерфейс сервиса для управления пользователями.
 * Определяет контракт для выполнения основных CRUD операций (Create, Read, Update, Delete) с пользователями.
 *
 * <p>Данный интерфейс предоставляет абстракцию для работы с пользовательскими данными,
 * позволяя использовать различные реализации (база данных, in-memory хранилище, внешний API и т.д.).</p>
 *
 * @param <T>    тип DTO (Data Transfer Object) сущности пользователя
 * @param <Long> тип идентификатора пользователя (должен быть ссылочным типом Long)
 * @implSpec Реализации должны гарантировать корректную обработку всех операций и соответствующих исключений
 * @see java.util.List
 */
public interface UserServiceInterface<T, Long> {

    /**
     * Получает список всех пользователей в системе.
     * Возвращает полную коллекцию пользовательских DTO объектов.
     *
     * @return список всех пользователей в виде List<T>. Если пользователи отсутствуют, возвращает пустой список.
     * @implSpec Реализация не должна возвращать null, даже если пользователи отсутствуют
     * @see List
     */
    public List<T> getAllUsers();

    /**
     * Удаляет пользователя по указанному идентификатору.
     * Выполняет операцию удаления и возвращает данные удаленного пользователя.
     *
     * @param userId идентификатор пользователя для удаления (must not be {@code null})
     * @return DTO объект удаленного пользователя
     * @throws IllegalArgumentException                  если userId является {@code null}
     * @throws javax.persistence.EntityNotFoundException если пользователь с указанным ID не найден
     * @implSpec После успешного выполнения метода пользователь должен быть полностью удален из системы
     */
    public T deleteUserById(Long userId);

    /**
     * Создает нового пользователя на основе предоставленных данных.
     * Сохраняет пользователя в системе и возвращает созданную сущность.
     *
     * @param userDto DTO объект с данными для создания пользователя (must not be {@code null})
     * @return DTO объект созданного пользователя с присвоенным идентификатором
     * @throws IllegalArgumentException                                если userDto является {@code null} или содержит невалидные данные
     * @throws org.springframework.dao.DataIntegrityViolationException при нарушении уникальности данных
     * @implSpec Метод должен присваивать уникальный идентификатор создаваемому пользователю
     */
    public T createUser(T userDto);

    /**
     * Обновляет данные существующего пользователя.
     * Находит пользователя по идентификатору и обновляет его данные.
     *
     * @param userDto DTO объект с обновленными данными пользователя (must not be {@code null})
     * @param id      идентификатор пользователя для обновления (must not be {@code null})
     * @return DTO объект обновленного пользователя
     * @throws IllegalArgumentException                  если userDto или id являются {@code null}
     * @throws javax.persistence.EntityNotFoundException если пользователь с указанным ID не найден
     * @implSpec Метод должен обновлять только те поля, которые предоставлены в userDto
     */
    public T updateUser(T userDto, Long id);
}