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
 * Аннотация для документирования Swagger ответов операции удаления пользователя.
 *
 * <p>Предоставляет стандартизированное описание API endpoint'а для удаления пользователя
 * по идентификатору, включая примеры успешного выполнения и случая когда пользователь не найден.</p>
 *
 * <p><b>Применение:</b></p>
 * <pre>
 * {@code
 * @DeleteUserApiResponse
 * @DeleteMapping("/{id}")
 * public ResponseEntity<UserDto> deleteUser(@PathVariable Long id) {
 *     // реализация метода
 * }
 * }
 * </pre>
 *
 * <p><b>Содержит:</b></p>
 * <ul>
 *   <li>Описание операции "deleteUser" - удаление пользователя</li>
 *   <li>Пример успешного ответа с данными удаленного пользователя</li>
 *   <li>Пример ошибки когда пользователь с указанным ID не найден</li>
 *   <li>HATEOAS ссылки для навигации по API</li>
 * </ul>
 *
 * <p><b>Особенности ответа:</b></p>
 * <ul>
 *   <li>Всегда возвращает HTTP статус 200</li>
 *   <li>При успешном удалении возвращает данные удаленного пользователя</li>
 *   <li>При ошибке поле {@code result} содержит сообщение "User not found with id: X"</li>
 *   <li>В случае ошибки основные поля пользователя (id, name, email, age, createdAt) имеют null или нулевые значения</li>
 *   <li>HATEOAS ссылки остаются доступными даже при неудачном выполнении</li>
 * </ul>
 *
 * <p><b>Поведение операции:</b></p>
 * <ul>
 *   <li>Успешное выполнение: возвращает данные удаленного пользователя с результатом "OK"</li>
 *   <li>Неудачное выполнение: возвращает сообщение об ошибке в поле {@code result}</li>
 *   <li>Операция идемпотентна - повторное удаление того же ID возвращает ошибку "не найден"</li>
 * </ul>
 *
 * @see SwaggerDescriptionExamples
 * @see Operation
 * @see ApiResponse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "deleteUser", description = "удаляет пользователя")
@ApiResponse(
        responseCode = "200",
        description = SwaggerDescriptionExamples.DESCRIPTION,
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "Success",
                                value = SwaggerDescriptionExamples.DELETE_USER_SUCCESS
                        ),
                        @ExampleObject(
                                name = "Fail",
                                value = SwaggerDescriptionExamples.DELETE_USER_FAIL
                        )
                }
        )
)
public @interface DeleteUserApiResponse {
}