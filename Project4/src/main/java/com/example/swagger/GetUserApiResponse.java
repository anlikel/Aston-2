package com.example.swagger;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.ExampleObject;
import io.swagger.v3.oas.annotations.responses.ApiResponse;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

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
