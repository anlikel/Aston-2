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
 * Аннотация для документирования Swagger ответов операции создания нового пользователя.
 *
 * <p>Предоставляет стандартизированное описание API endpoint'а для создания нового пользователя,
 * включая примеры успешного выполнения и случая нарушения уникальности email.</p>
 *
 * <p><b>Применение:</b></p>
 * <pre>
 * {@code
 * @CreateUserApiResponse
 * @PostMapping
 * public ResponseEntity<UserDto> createUser(@RequestBody @Valid UserDto userDto) {
 *     // реализация метода
 * }
 * }
 * </pre>
 *
 * <p><b>Содержит:</b></p>
 * <ul>
 *   <li>Описание операции "createUser" - создание нового пользователя</li>
 *   <li>Пример успешного ответа с данными созданного пользователя</li>
 *   <li>Пример ошибки при нарушении уникальности email адреса</li>
 *   <li>HATEOAS ссылки для навигации по API</li>
 * </ul>
 *
 * <p><b>Особенности ответа:</b></p>
 * <ul>
 *   <li>Всегда возвращает HTTP статус 200</li>
 *   <li>При успешном создании возвращает данные пользователя с присвоенным ID</li>
 *   <li>При ошибке поле {@code result} содержит сообщение "User already exists with unique email: X"</li>
 *   <li>В случае ошибки поле {@code id} имеет значение null</li>
 *   <li>При ошибке HATEOAS ссылки используют шаблонные параметры ({templated: true})</li>
 * </ul>
 *
 * <p><b>Требования к данным:</b></p>
 * <ul>
 *   <li>Email адрес должен быть уникальным в системе</li>
 *   <li>При нарушении уникальности создание пользователя отклоняется</li>
 *   <li>Все обязательные поля должны быть валидными</li>
 * </ul>
 *
 * <p><b>Поведение операции:</b></p>
 * <ul>
 *   <li>Успешное выполнение: возвращает данные созданного пользователя с результатом "OK"</li>
 *   <li>Неудачное выполнение: возвращает сообщение об ошибке дублирования email</li>
 *   <li>При успехе присваивает уникальный ID и временную метку создания</li>
 * </ul>
 *
 * @see SwaggerDescriptionExamples
 * @see Operation
 * @see ApiResponse
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Operation(summary = "createUser", description = "Создает нового пользователя")
@ApiResponse(
        responseCode = "200",
        description = SwaggerDescriptionExamples.DESCRIPTION,
        content = @Content(
                mediaType = "application/json",
                examples = {
                        @ExampleObject(name = "Success", value = SwaggerDescriptionExamples.CREATE_USER_SUCCESS),
                        @ExampleObject(name = "Fail", value = SwaggerDescriptionExamples.CREATE_USER_FAIL)
                }
        )
)
public @interface CreateUserApiResponse {
}