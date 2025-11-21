package com.example.swagger;

/**
 * Класс-контейнер с примерами ответов API для документации Swagger.
 * <p>
 * Содержит строковые константы с JSON примерами успешных и неуспешных ответов
 * для всех операций REST API пользователей.
 * </p>
 *
 * <p><b>Использование в аннотациях Swagger:</b></p>
 * <pre>
 * {@code
 * @ApiResponse(
 *     responseCode = "200",
 *     description = SwaggerDescriptionExamples.DESCRIPTION,
 *     content = @Content(
 *         schema = @Schema(implementation = UserDto.class),
 *         examples = @ExampleObject(value = SwaggerDescriptionExamples.GET_USER_SUCCESS)
 *     )
 * )
 * }
 * </pre>
 */
public class SwaggerDescriptionExamples {

    /**
     * Общее описание поведения всех endpoint'ов API.
     * Все методы всегда возвращают HTTP статус 200, а информация об ошибках
     * передается в поле {@code result} response body.
     */
    public static final String DESCRIPTION = "всегда возвращает статус 200 с полем статуса где указана ошибка в случае ее возникновения";

    /**
     * Пример успешного ответа для получения пользователя по ID.
     * Содержит полные данные пользователя с HATEOAS ссылками.
     */
    public static final String GET_USER_SUCCESS =
            """
                    {
                        "id": 1,
                        "name": "User1",
                        "email": "mail1@example.com",
                        "age": 25,
                        "createdAt": "2025-11-18T15:32:08.939762",
                        "result": "OK",
                        "_links": {
                            "self": {
                                "href": "http://localhost:8080/api/users/1"
                            },
                            "getAllUsers": {
                                "href": "http://localhost:8080/api/users"
                            },
                            "deleteUser": {
                                "href": "http://localhost:8080/api/users/1"
                            },
                            "createUser": {
                                "href": "http://localhost:8080/api/users"
                            },
                            "updateUser": {
                                "href": "http://localhost:8080/api/users/1"
                            }
                        }
                    }
                    """;

    /**
     * Пример неуспешного ответа для получения пользователя по ID.
     * Пользователь не найден, поле {@code result} содержит сообщение об ошибке.
     */
    public static final String GET_USER_FAIL =
            """
                    {
                        "id": 0,
                        "name": null,
                        "email": null,
                        "age": 0,
                        "createdAt": null,
                        "result": "User not found with id: 5",
                        "_links": {
                            "self": {
                                "href": "http://localhost:8080/api/users/5"
                            },
                            "getAllUsers": {
                                "href": "http://localhost:8080/api/users"
                            },
                            "deleteUser": {
                                "href": "http://localhost:8080/api/users/5"
                            },
                            "createUser": {
                                "href": "http://localhost:8080/api/users"
                            },
                            "updateUser": {
                                "href": "http://localhost:8080/api/users/5"
                            }
                        }
                    }
                    """;

    /**
     * Пример успешного ответа для получения всех пользователей.
     * Содержит список пользователей в поле {@code _embedded.userDtoList} с HATEOAS ссылками.
     */
    public static final String GET_ALL_USERS_SUCCESS = """
            {
                "_embedded": {
                    "userDtoList": [
                        {
                            "id": 1,
                            "name": "User1",
                            "email": "mail1@example.com",
                            "age": 25,
                            "createdAt": "2025-11-19T10:54:15.028273",
                            "result": "OK",
                            "_links": {
                                "self": {
                                    "href": "http://localhost:8080/api/users/1"
                                },
                                "deleteUser": {
                                    "href": "http://localhost:8080/api/users/1"
                                },
                                "createUser": {
                                    "href": "http://localhost:8080/api/users"
                                },
                                "updateUser": {
                                    "href": "http://localhost:8080/api/users/1"
                                }
                            }
                        },
                        {
                            "id": 2,
                            "name": "User2",
                            "email": "mail2@example.com",
                            "age": 30,
                            "createdAt": "2025-11-19T10:54:15.028273",
                            "result": "OK",
                            "_links": {
                                "self": {
                                    "href": "http://localhost:8080/api/users/2"
                                },
                                "deleteUser": {
                                    "href": "http://localhost:8080/api/users/2"
                                },
                                "createUser": {
                                    "href": "http://localhost:8080/api/users"
                                },
                                "updateUser": {
                                    "href": "http://localhost:8080/api/users/2"
                                }
                            }
                        }
                    ]
                },
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users"
                    }
                }
            }
            """;

    /**
     * Пример ответа когда список пользователей пуст.
     * Содержит только HATEOAS ссылки без данных пользователей.
     */
    public static final String GET_ALL_USERS_FAIL = """
            {
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users"
                    }
                }
            }
            """;

    /**
     * Пример успешного ответа для создания пользователя.
     * Содержит данные созданного пользователя с присвоенным ID.
     */
    public static final String CREATE_USER_SUCCESS = """
            {
                "id": 3,
                "name": "Jofefhn66441",
                "email": "erg@erggg77er.ru",
                "age": 12,
                "createdAt": "2025-11-19T11:01:49.531525159",
                "result": "OK",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getAllUsers": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getUser": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "deleteUser": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "updateUser": {
                        "href": "http://localhost:8080/api/users/3"
                    }
                }
            }
            """;

    /**
     * Пример неуспешного ответа для создания пользователя.
     * Пользователь с таким email уже существует.
     */
    public static final String CREATE_USER_FAIL = """
            {
                "id": null,
                "name": "Jofefhn66441",
                "email": "erg@erggg77er.ru",
                "age": 12,
                "createdAt": null,
                "result": "User already exists with unique email: erg@erggg77er.ru",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getAllUsers": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getUser": {
                        "href": "http://localhost:8080/api/users/{id}",
                        "templated": true
                    },
                    "deleteUser": {
                        "href": "http://localhost:8080/api/users/{id}",
                        "templated": true
                    },
                    "updateUser": {
                        "href": "http://localhost:8080/api/users/{id}",
                        "templated": true
                    }
                }
            }
            """;

    /**
     * Пример успешного ответа для обновления пользователя.
     * Содержит обновленные данные пользователя.
     */
    public static final String UPDATE_USER_SUCCESS = """
            {
                "id": 2,
                "name": "Jofefhn66441",
                "email": "erg@erggg77er.ru",
                "age": 20,
                "createdAt": "2025-11-19T11:04:45.371244265",
                "result": "OK",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/2"
                    },
                    "getAllUsers": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getUser": {
                        "href": "http://localhost:8080/api/users/2"
                    },
                    "deleteUser": {
                        "href": "http://localhost:8080/api/users/2"
                    },
                    "createUser": {
                        "href": "http://localhost:8080/api/users"
                    }
                }
            }
            """;

    /**
     * Пример неуспешного ответа для обновления пользователя.
     * Пользователь с указанным ID не найден.
     */
    public static final String UPDATE_USER_FAIL = """
            {
                "id": null,
                "name": "Jofefhn66441",
                "email": "erg@erggg77er.ru",
                "age": 20,
                "createdAt": null,
                "result": "User not found with id: 4",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/4"
                    },
                    "getAllUsers": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getUser": {
                        "href": "http://localhost:8080/api/users/4"
                    },
                    "deleteUser": {
                        "href": "http://localhost:8080/api/users/4"
                    },
                    "createUser": {
                        "href": "http://localhost:8080/api/users"
                    }
                }
            }
            """;

    /**
     * Пример успешного ответа для удаления пользователя.
     * Содержит данные удаленного пользователя.
     */
    public static final String DELETE_USER_SUCCESS = """
            {
                "id": 3,
                "name": "Jofefhn66441",
                "email": "erg@erggg77er.ru",
                "age": 20,
                "createdAt": "2025-11-19T11:01:49.531525",
                "result": "OK",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "getAllUsers": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getUser": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "createUser": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "updateUser": {
                        "href": "http://localhost:8080/api/users/3"
                    }
                }
            }
            """;

    /**
     * Пример неуспешного ответа для удаления пользователя.
     * Пользователь с указанным ID не найден.
     */
    public static final String DELETE_USER_FAIL = """
            {
                "id": 0,
                "name": null,
                "email": null,
                "age": 0,
                "createdAt": null,
                "result": "User not found with id: 3",
                "_links": {
                    "self": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "getAllUsers": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "getUser": {
                        "href": "http://localhost:8080/api/users/3"
                    },
                    "createUser": {
                        "href": "http://localhost:8080/api/users"
                    },
                    "updateUser": {
                        "href": "http://localhost:8080/api/users/3"
                    }
                }
            }
            """;
}