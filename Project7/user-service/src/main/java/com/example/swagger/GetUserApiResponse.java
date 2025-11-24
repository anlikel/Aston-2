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
 * Аннотация для документирования Swagger ответов операции получения пользователя по ID.
 *
 * <p>Предоставляет стандартизированное описание API endpoint'а для получения данных пользователя
 * по его идентификатору, включая примеры успешного и неуспешного выполнения операции.</p>
 *
 * <p><b>Применение:</b></p>
 * <pre>
 * {@code
 * @GetUserApiResponse
 * @GetMapping("/{id}")
 * public ResponseEntity<UserDto> getUserById(@PathVariable Long id) {
 *     // реализация метода
 * }
 * }
 * </pre>
 *
 * <p><b>Содержит:</b></p>
 * <ul>
 *   <li>Описание операции "getUserById" - возврат пользователя по идентификатору</li>
 *   <li>Пример успешного ответа с данными найденного пользователя</li>
 *   <li>Пример ошибки когда пользователь с указанным ID не найден</li>
 *   <li>HATEOAS ссылки для навигации по API</li>
 * </ul>
 *
 * <p><b>Особенности ответа:</b></p>
 * <ul>
 *   <li>Всегда возвращает HTTP статус 200</li>
 *   <li>Информация об ошибках передается в поле {@code result}</li>
 *   <li>При успехе поле {@code result} содержит "OK"</li>
 *   <li>При ошибке поле {@code result} содержит сообщение об ошибке</li>
 * </ul>
 *
 * @see SwaggerDescriptionExamples
 * @see Operation
 * @see ApiResponse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "getUserById", description = "Возвращает пользователя с указанным идентификатором")
@ApiResponse(
        responseCode = "200",
        description = SwaggerDescriptionExamples.DESCRIPTION,
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "Success",
                                value = SwaggerDescriptionExamples.GET_USER_SUCCESS
                        ),
                        @ExampleObject(
                                name = "Fail",
                                value = SwaggerDescriptionExamples.GET_USER_FAIL
                        )
                }
        )
)
public @interface GetUserApiResponse {
}