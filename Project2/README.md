# User CRUD Application

## Prerequisites
- Docker and Docker Compose
- Java 17+
- Maven 3.6+

## Quick Start

### 1. Start PostgreSQL database:
```bash
1. В корне проекта запустите:
docker-compose up -d

2. Проверить работу дб:
bash
docker ps

3. Проверить существование тестовой таблицы:
bash
docker exec -it myapp-postgres psql -U user -d postgres -c "SELECT * FROM users;"

4. Запустить приложение:
Найдите класс Main в src/main/java/org/example
Запустите метод main()

5. Структура проекта:

project/
├── docker-compose.yml          # Docker configuration for PostgreSQL
├── init-scripts/
│   └── init.sql               # SQL script for test data initialization
├── src/
│   └── main/
│       ├── java/
│       │   └── com/org/example/
│       │       ├── commands/          # Command pattern classes
│       │       │   ├── Action.java
│       │       │   ├── Command.java
│       │       │   ├── CommandFactory.java
│       │       │   ├── CreateCommand.java
│       │       │   ├── DeleteCommand.java
│       │       │   ├── ExitCommand.java
│       │       │   ├── ReadCommand.java
│       │       │   ├── ReadAllCommand.java
│       │       │   └── UpdateCommand.java         
│       │       ├── entities           
│       │       │      └── User.java                  # User entity class
│       │       ├── entitybuilders           
│       │       │      ├── ClassTag.java
│       │       │      ├── EntityBuilder.java
│       │       │      ├── EntityBuilderFactory.java
│       │       │      └── UserBuilder.java
│       │       ├── exceptions           
│       │       │      └── MyCustomException.java                    
│       │       ├── menu           
│       │       │     ├── MenuHandler.java
│       │       │     └── MenuPrinter.java
│       │       ├── repository                        # DB logic
│       │       │     ├── TransactionManager.java
│       │       │     ├── UserDao.java
│       │       │     └── UserRepository.java
│       │       ├── util           
│       │       │    ├── HibernateUtil.java
│       │       │    ├── UtilReader.java
│       │       │    └── UtilValidator.java
│       │       └── Main.java                       # Main application class
│       └── resources/
│           └── hibernate.cfg.xml       # Hibernate configuration
│           └── logback.xml
├── pom.xml                     # Maven dependencies
└── README.md
└── gitignore.md
└── docker-compose.yml

6. Конфигурация базы данных.
База данных поднимается через docker-compose с внешним портом 5433.
    Host: localhost:5433
    Database: postgres
    Username: user
    Password: 1234

7. Тестовые данные.

    При первом запуске автоматически создаются тестовые пользователи:

    User1 (mail1@example.com, 25 years)
    User2 (mail2@example.com, 30 years)

8. Доступные операции.

    a) Создать нового пользователя.
    b) Получить пользователя по ид.
    c) Удалить пользователя по ид.
    d) Обновить пользователя по ид.
    e) ПОлучить полный список текущих пользователей, сохраненных в бд.

9. Остановить приложение/докер-контейнер.

   а)Обычная остановка с сохранением данных: 
    docker-compose down

   а)Остановить и очистить данные: 
    docker-compose down -v

10. Очистить и пересоздать тестовые данные.

docker-compose down -v
docker-compose up -d