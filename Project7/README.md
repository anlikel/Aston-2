# User CRUD Application

## Prerequisites

- Docker and Docker Compose
- Java 17+
- Maven 3.6+

## Quick Start

### 1. Start PostgreSQL database:

```bash

1. Запустить развертывание через контейнеры:

docker-compose down
mvn clean package -Dmaven.test.skip=true
docker-compose build --no-cache && docker-compose up -d --force-recreate

Происходит поднятие базы данных POSTGRESS 15 на базе докер-контейнера и автоматическая
ее иниицализация скрипом из папки /resources/data.sql

Происходит развернтывание встроенного томкат-сервера с уже готовыми эндпоинтами
по адресу http://localhost:8080/api/users для выполнения всех основных crud-операций.

Происходит развернтывание контейнеров user-service, api-gateway, eureka-server.
Это может занять до нескольких минут.

2. Структура проекта:

project/
├── logs                                                   # logs
│  
├── src/
│   ├── main/
│   │    ├── java/
│   │    │   └── com/example/
│   │    │       ├── controller/                            # rest controller
│   │    │       │   ├── FakeKafkaUserController.java  
│   │    │       │   ├── HateoasUserController.java
│   │    │       │   └── UserController.java       
│   │    │       ├── entities           
│   │    │       │   └── UserEntity.java                  # User entity class
│   │    │       ├── dto                                     # data transfer objects
│   │    │       │   ├── UserDto.java
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
│   │    │       ├── serviceinterfaces              
│   │    │       │   ├── EmailServiceInterface.java                
│   │    │       │   ├── KafkaListenerEmailNotificationServiceInterface.java          
│   │    │       │   ├── KafkaServiceInterface.java         
│   │    │       │   └── UserServiceInterface.java                                      
│   │    │       ├── util           
│   │    │       │   └── UtilValidator.java
│   │    │       ├── swagger              
│   │    │       │   ├── SwaggerDescriptionExamples.java     #class with string constants               
│   │    │       │   ├── CreateUserApiResponse.java          
│   │    │       │   ├── DeleteUserApiResponse.java         
│   │    │       │   ├── UpdateUserApiResponse.java                
│   │    │       │   ├── GetUserApiResponse.java 
│   │    │       │   └── GetAllUsersUserApiResponse.java    
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

6. Доступные эндпоинты:

    а) http://localhost:8080/api/users - стандартный эндпоинт со всеми
    реализованными операциями и отправкой уведомлений через микросервис кафка.

    б) http://localhost:8080/api/fakeusers - эндпоинт c реализованными операциями 
    создания/удаления пользователя и отправкой уведомлений через аналог-микросервиса кафка,
    реализованный через стандартные средства спринг.
    
    в) http://localhost:8080/api/hateoasusers - эндпоинт со всеми
    реализованными операциями , отправкой уведомлений через микросервис кафка,
    также добавлена поддержка возврата доступных ссылок по операциям с пользователями
    через HATEOAS и документирование через open-api по адресу http://localhost:8080/swagger-ui.html
    
7. Логирование.

    Все логи сохраняются в папку /logs в корне проекта.

8. Тестирование.
Запуск тестов: mvn test

Реализованы тесты на валидацию вводимых данных для создание класса UserEntity,
тесты Dao-слоя для проверки работы с бд, тесты сервис слоя с помощью Mockito,
тесты слоя контрорллера.
тесты KAFKA сервиса.


9. Возможные проблемы
Остановка/удаление контейнеров для послеждующего перезапуска в случае конфликта имен контейнеров
скрипт DOCKER_IMAGES_DELETE.sh --удаление контейнеров
docker system prune -a -f ---удаление образов и очистка всего
docker-compose down - остановить контейнеры
mvn clean package -Dmaven.test.skip=true
docker-compose -f compose.yaml up -d 

# Смотреть логи user-service в реальном времени
docker logs gubenko-user-service -f

# Или через docker-compose
docker-compose logs -f user-service

# Только кафка-related логи
docker logs gubenko-user-service | grep -i "kafka\|email\|user"

# После создания пользователя посмотреть последние логи
curl -X POST http://localhost:8080/api/users -H "Content-Type: application/json" -d '{"name":"Test", "email":"test@test.com"}'
docker logs gubenko-user-service --tail=20