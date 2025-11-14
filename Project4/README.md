# User CRUD Application

## Prerequisites

- Docker and Docker Compose
- Java 17+
- Maven 3.6+

## Quick Start

### 1. Start PostgreSQL database:

```bash

1. Запустить spring-boot приложение:
Найдите класс Project4Application в src/main/java/org/example
Запустите метод main()

Происходит поднятие базы данных POSTGRESS 15 на базе докер-контейнера и автоматическая
ее иниицализация скрипом из папки /resources/data.sql

Происходит развернтывание встроенного томкат-сервера с уже готовыми эндпоинтами
по адресу http://localhost:8080/api/users для выполнения всех основных crud-операций.

2. Структура проекта:

project/
├── logs                                                   # logs
│  
├── src/
│   ├── main/
│   │    ├── java/
│   │    │   └── com/example/
│   │    │       ├── controller/                            # rest controller
│   │    │       │   └── UserController.java         
│   │    │       ├── entities           
│   │    │       │   └── UserEntity.java                  # User entity class
│   │    │       ├── dto                                     # data transfer objects
│   │    │       │   ├── CreateUserDto.java
│   │    │       │   ├── GetUserDto.java
│   │    │       │   └── UserMapper.java                  # mapper class to convertion dto/entity
│   │    │       ├── exceptions           
│   │    │       │   └── MyCustomException.java   
│   │    │       ├── notificationhandlers              #main package to organize notification events
│   │    │       │   ├── EventType.java                #Enum with event names
│   │    │       │   ├── ServiceEventDto.java          #DTO to transfer events to kafka
│   │    │       │   ├── NotificationHandler.java         
│   │    │       │   ├── NotificationHandlerFactory.java                
│   │    │       │   ├── CreateUserNotificationHandler.java 
│   │    │       │   └── DeleteUserNotificationHandler.java    
│   │    │       ├── repository                              # DB logic
│   │    │       │   └── UserRepository.java
│   │    │       ├── service           
│   │    │       │   ├── UserService.java
│   │    │       │   ├── FakeKafkaUserService.java     #userservice with fakekafkaservice call
│   │    │       │   ├── FakeKafkaService.java         #kafka server imitation working via events
│   │    │       │   ├── EmailService.java             #simple class mail send imitation       
│   │    │       │   ├── KafkaService.java
│   │    │       │   ├── KafkaListenerEmailNotificationService.java     # kafka consumer  
│   │    │       │   └── FakeKafkaListenerEmailNotificationService.java    #fake kafka consumer via event listener                        
│   │    │       ├── util           
│   │    │       │   └── UtilValidator.java
│   │    │       └── Project4Application.java                 # Main application class
│   │    └── resources/
│   │        ├── static 
│   │        ├── templates 
│   │        ├── application.yaml                             # configuration
│   │        └── data.sql                                     # init script to insert in main db before start
│   │
│   └── test/
│       ├──java/
│       │   └──/org/example/
│       │       ├── repositorytest                              # repository tests
│       │       │    ├── RepositoryTestAbstract.java
│       │       │    ├── RepositoryDeleteUserByIdTest.java
│       │       │    ├── RepositoryGetAllUsersTest.java
│       │       │    ├── RepositoryGetUserByIdTest.java
│       │       │    └──RepositorySaveUserTest.java
│       │       ├── usercontrollertest                          # controller tests
│       │       │    ├── UserControllerCreateUserTest.java
│       │       │    ├── UserControllerDeleteUserTest.java
│       │       │    ├── UserControllerGetAllUsersTest.java
│       │       │    ├── UserControllerGetUserTest.java
│       │       │    └──UserControllerUpdateUserTest.java
│       │       ├── service                                      # service tests
│       │       │   ├── UserServiceCreateUserTest.java
│       │       │   ├── UserServiceDeleteUserByIdTest.java
│       │       │   ├── UserServiceGetAllUsersTest.java
│       │       │   ├── UserServiceGetUserByIdTest.java
│       │       │   └── UserServiceUpdateUserTest.java
│       │       ├── utils                                         # util tests
│       │       │   ├── ValidAgeTest.java
│       │       │   ├── ValidEmailTest.java
│       │       │   ├── ValidIdTest.java
│       │       │   └── ValidNameTest.java
│       │       └── kafkatests                                         # kafka tests
│       │           ├── TestKafkaConfig.java
│       │           ├── KafkaServiceIntegrationCreateUserTest.java
│       │           └── KafkaServiceIntegrationCreateDeleteTest.java
│       │
│       └── resources/
│           ├── application-test.yaml                            # configuration
│           ├── cleanup.sql                                      # clean db script for tests
│           └── init.sql                                         # init db script for tests
│               
├── pom.xml                                                         # Maven dependencies
├──  README.md
├──  gitignore.md
└── compose.yaml

3. Конфигурация базы данных.
База данных поднимается через docker-compose с внешним портом 5433.
    Host: localhost:5433
    Database: postgres
    Username: user
    Password: 1234

4. Тестовые данные.

    При первом запуске автоматически создаются тестовые пользователи:

    User1 (mail1@example.com, 25 years)
    User2 (mail2@example.com, 30 years)

5. Доступные операции через сервер Tomcat.

    a) Создать нового пользователя.
    b) Получить пользователя по ид.
    c) Удалить пользователя по ид.
    d) Обновить пользователя по ид.
    e) Получить полный список текущих пользователей, сохраненных в бд.
    
6. Логирование.

    Все логи сохраняются в папку /logs в корне проекта.

7. Тестирование.

Реализованы тесты на валидацию вводимых данных для создание класса UserEntity,
тесты Dao-слоя для проверки работы с бд, тесты сервис слоя с помощью Mockito,
тесты слоя контрорллера.
тесты KAFKA сервиса.

Запуск: mvn test