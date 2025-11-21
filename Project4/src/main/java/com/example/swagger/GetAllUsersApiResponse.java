package com.example.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Аннотация для документирования Swagger ответов операции получения списка всех пользователей.
 *
 * <p>Предоставляет стандартизированное описание API endpoint'а для получения полного списка
 * пользователей, включая примеры успешного выполнения и случая когда пользователи отсутствуют.</p>
 *
 * <p><b>Применение:</b></p>
 * <pre>
 * {@code
 * @GetAllUsersApiResponse
 * @GetMapping
 * public ResponseEntity<UserListDto> getAllUsers() {
 *     // реализация метода
 * }
 * }
 * </pre>
 *
 * <p><b>Содержит:</b></p>
 * <ul>
 *   <li>Описание операции "getAllUsers" - возврат списка пользователей</li>
 *   <li>Пример успешного ответа с непустым списком пользователей</li>
 *   <li>Пример ответа когда пользователи отсутствуют (пустой список)</li>
 *   <li>HATEOAS ссылки для навигации по API для каждого пользователя</li>
 * </ul>
 *
 * <p><b>Особенности ответа:</b></p>
 * <ul>
 *   <li>Всегда возвращает HTTP статус 200</li>
 *   <li>Список пользователей содержится в поле {@code _embedded.userDtoList}</li>
 *   <li>При отсутствии пользователей поле {@code _embedded} может отсутствовать или быть пустым</li>
 *   <li>Каждый пользователь в списке содержит полный набор HATEOAS ссылок</li>
 *   <li>Ответ включает корневые HATEOAS ссылки для навигации</li>
 * </ul>
 *
 * <p><b>Структура успешного ответа:</b></p>
 * <ul>
 *   <li>{@code _embedded.userDtoList} - массив объектов пользователя</li>
 *   <li>{@code _links} - навигационные ссылки</li>
 *   <li>Каждый пользователь содержит: id, name, email, age, createdAt, result, _links</li>
 * </ul>
 *
 * @see SwaggerDescriptionExamples
 * @see Operation
 * @see ApiResponse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "getAllUsers", description = "Возвращает список пользователей")
@ApiResponse(
        responseCode = "200",
        description = SwaggerDescriptionExamples.DESCRIPTION,
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "Success",
                                value = SwaggerDescriptionExamples.GET_ALL_USERS_SUCCESS
                        ),
                        @ExampleObject(
                                name = "Fail",
                                value = SwaggerDescriptionExamples.GET_ALL_USERS_FAIL
                        )
                }
        )
)
public @interface GetAllUsersApiResponse {
}