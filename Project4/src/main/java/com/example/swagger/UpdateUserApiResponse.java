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
 * Аннотация для документирования Swagger ответов операции обновления пользователя.
 *
 * <p>Предоставляет стандартизированное описание API endpoint'а для обновления данных пользователя,
 * включая примеры успешного и неуспешного выполнения операции.</p>
 *
 * <p><b>Применение:</b></p>
 * <pre>
 * {@code
 * @UpdateUserApiResponse
 * @PutMapping("/{id}")
 * public ResponseEntity<UserDto> updateUser(
 *     @PathVariable Long id,
 *     @RequestBody @Valid UserDto userDto) {
 *     // реализация метода
 * }
 * }
 * </pre>
 *
 * <p><b>Содержит:</b></p>
 * <ul>
 *   <li>Описание операции "updateUser"</li>
 *   <li>Пример успешного ответа с обновленными данными пользователя</li>
 *   <li>Пример ошибки при ненайденном пользователе</li>
 *   <li>HATEOAS ссылки для навигации</li>
 * </ul>
 *
 * @see SwaggerDescriptionExamples
 * @see Operation
 * @see ApiResponse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "updateUser", description = "Обновляет пользователя")
@ApiResponse(
        responseCode = "200",
        description = SwaggerDescriptionExamples.DESCRIPTION,
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(
                                name = "Success",
                                value = SwaggerDescriptionExamples.UPDATE_USER_SUCCESS
                        ),
                        @ExampleObject(
                                name = "Fail",
                                value = SwaggerDescriptionExamples.UPDATE_USER_FAIL
                        )
                }
        )
)
public @interface UpdateUserApiResponse {
}