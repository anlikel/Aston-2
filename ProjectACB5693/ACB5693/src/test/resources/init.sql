CREATE TYPE CODE AS ENUM ('CREATE', 'READ', 'UPDATE', 'DELETE');

CREATE TABLE role (
                      id uuid NOT NULL,
                      role_name varchar(50) NOT NULL,
                      description text,
                      created_at timestamptz NOT NULL DEFAULT now(),
                      updated_at timestamptz NOT NULL DEFAULT now()
);

ALTER TABLE role ADD CONSTRAINT pk_role PRIMARY KEY (id);
ALTER TABLE role ADD CONSTRAINT uq_role_name UNIQUE (role_name);

COMMENT ON TABLE role IS 'Таблица ролей пользователей';
COMMENT ON COLUMN role.id IS 'Идентификатор роли';
COMMENT ON COLUMN role.role_name IS 'Название роли';
COMMENT ON COLUMN role.description IS 'Описание роли';
COMMENT ON COLUMN role.created_at IS 'Дата создания записи';
COMMENT ON COLUMN role.updated_at IS 'Дата обновления записи';

CREATE TABLE employee (
                          id uuid NOT NULL,
                          role_id uuid NOT NULL,
                          first_name varchar(40) NOT NULL,
                          last_name varchar(40) NOT NULL,
                          middle_name varchar(40),
                          phone_number char(12) NOT NULL,
                          birth_date date NOT NULL,
                          is_active boolean NOT NULL DEFAULT true,
                          created_at timestamptz NOT NULL DEFAULT now(),
                          updated_at timestamptz NOT NULL DEFAULT now()
);

ALTER TABLE employee ADD CONSTRAINT pk_employee PRIMARY KEY (id);
ALTER TABLE employee ADD CONSTRAINT uq_employee_phone_number UNIQUE (phone_number);
ALTER TABLE employee ADD CONSTRAINT fk_employee_role_id FOREIGN KEY (role_id) REFERENCES role(id);

COMMENT ON TABLE employee IS 'Таблица сотрудников';
COMMENT ON COLUMN employee.id IS 'Уникальный идентификатор сотрудника';
COMMENT ON COLUMN employee.role_id IS 'Идентификатор роли сотрудника';
COMMENT ON COLUMN employee.first_name IS 'Имя сотрудника';
COMMENT ON COLUMN employee.last_name IS 'Фамилия сотрудника';
COMMENT ON COLUMN employee.middle_name IS 'Отчество сотрудника';
COMMENT ON COLUMN employee.phone_number IS 'Номер телефона сотрудника';
COMMENT ON COLUMN employee.birth_date IS 'Дата рождения сотрудника';
COMMENT ON COLUMN employee.is_active IS 'Статус активности сотрудника';
COMMENT ON COLUMN employee.created_at IS 'Дата создания записи';
COMMENT ON COLUMN employee.updated_at IS 'Дата обновления записи';

CREATE TABLE permission (
                            id uuid NOT NULL,
                            code CODE NOT NULL,
                            description text,
                            created_at timestamptz NOT NULL DEFAULT now(),
                            updated_at timestamptz NOT NULL DEFAULT now()
);

ALTER TABLE permission ADD CONSTRAINT pk_permission PRIMARY KEY (id);

COMMENT ON TABLE permission IS 'Таблица разрешений';
COMMENT ON COLUMN permission.id IS 'Идентификатор разрешения';
COMMENT ON COLUMN permission.code IS 'Код разрешения';
COMMENT ON COLUMN permission.description IS 'Описание разрешения';
COMMENT ON COLUMN permission.created_at IS 'Дата создания записи';
COMMENT ON COLUMN permission.updated_at IS 'Дата обновления записи';

CREATE TABLE role_permission (
                                 id uuid NOT NULL,
                                 role_id uuid NOT NULL,
                                 permission_id uuid NOT NULL,
                                 created_at timestamptz NOT NULL DEFAULT now(),
                                 updated_at timestamptz NOT NULL DEFAULT now()
);

ALTER TABLE role_permission ADD CONSTRAINT pk_role_permission PRIMARY KEY (id);
ALTER TABLE role_permission ADD CONSTRAINT uk_role_permission_unique UNIQUE (role_id, permission_id);
ALTER TABLE role_permission ADD CONSTRAINT fk_role_id FOREIGN KEY (role_id) REFERENCES role(id) ON DELETE CASCADE;
ALTER TABLE role_permission ADD CONSTRAINT fk_permission_id FOREIGN KEY (permission_id) REFERENCES permission(id) ON DELETE CASCADE;

COMMENT ON TABLE role_permission IS 'Таблица связи ролей и разрешений';
COMMENT ON COLUMN role_permission.id IS 'Уникальный идентификатор записи';
COMMENT ON COLUMN role_permission.role_id IS 'Идентификатор роли';
COMMENT ON COLUMN role_permission.permission_id IS 'Идентификатор разрешения';
COMMENT ON COLUMN role_permission.created_at IS 'Дата создания записи';
COMMENT ON COLUMN role_permission.updated_at IS 'Дата обновления записи';

INSERT INTO role (id, role_name, description)
VALUES
    (
        '11111111-1111-1111-1111-111111111111',  -- Фиксированный UUID для операциониста
        'Операционист',
        'Сотрудник, выполняющий операции с клиентами: открытие счетов, переводы, платежи, работа с документами'
    ),
    (
        '22222222-2222-2222-2222-222222222222',  -- Фиксированный UUID для менеджера
        'Менеджер',
        'Руководитель отдела, отвечает за управление командой, планирование, контроль выполнения задач'
    )
    ON CONFLICT (id) DO NOTHING;

INSERT INTO employee (id, role_id, first_name, last_name, middle_name, phone_number, birth_date, is_active)
VALUES
    (
        'aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa',  -- Фиксированный UUID для тестов
        '11111111-1111-1111-1111-111111111111',  -- Операционист
        'Иван',
        'Иванов',
        'Иванович',
        '79101234567',
        '1990-05-15',
        true
    ),
    (
        'bbbbbbbb-2222-2222-2222-bbbbbbbbbbbb',
        '11111111-1111-1111-1111-111111111111',  -- Операционист
        'Мария',
        'Петрова',
        'Сергеевна',
        '79107654321',
        '1992-08-22',
        true
    ),
    (
        'cccccccc-3333-3333-3333-cccccccccccc',
        '22222222-2222-2222-2222-222222222222',  -- Менеджер
        'Алексей',
        'Сидоров',
        'Александрович',
        '79109998877',
        '1985-12-03',
        true
    ),
    (
        'dddddddd-4444-4444-4444-dddddddddddd',
        '22222222-2222-2222-2222-222222222222',  -- Менеджер
        'Елена',
        'Кузнецова',
        'Владимировна',
        '79105554433',
        '1988-03-18',
        false  -- Не активна
    )
    ON CONFLICT (id) DO NOTHING;

INSERT INTO permission (id, code, description)
VALUES
    (
        'aaaaaaaa-0001-0001-0001-aaaaaaaaaaaa',
        'CREATE'::CODE,
        'Разрешение на создание записей в системе'
    ),
    (
        'aaaaaaaa-0002-0002-0002-aaaaaaaaaaaa',
        'READ'::CODE,
        'Разрешение на чтение и просмотр данных'
    ),
    (
        'aaaaaaaa-0003-0003-0003-aaaaaaaaaaaa',
        'UPDATE'::CODE,
        'Разрешение на изменение и обновление данных'
    ),
    (
        'aaaaaaaa-0004-0004-0004-aaaaaaaaaaaa',
        'DELETE'::CODE,
        'Разрешение на удаление данных из системы'
    )
    ON CONFLICT (id) DO NOTHING;

INSERT INTO role_permission (id, role_id, permission_id)
VALUES
    -- Операционист READ
    (
        'cccccccc-0001-0001-0001-cccccccccccc',
        '11111111-1111-1111-1111-111111111111',
        'aaaaaaaa-0002-0002-0002-aaaaaaaaaaaa'
    ),
    -- Операционист UPDATE
    (
        'cccccccc-0002-0002-0002-cccccccccccc',
        '11111111-1111-1111-1111-111111111111',
        'aaaaaaaa-0003-0003-0003-aaaaaaaaaaaa'
    ),
    -- Менеджер CREATE
    (
        'dddddddd-0001-0001-0001-dddddddddddd',
        '22222222-2222-2222-2222-222222222222',
        'aaaaaaaa-0001-0001-0001-aaaaaaaaaaaa'
    ),
    -- Менеджер READ
    (
        'dddddddd-0002-0002-0002-dddddddddddd',
        '22222222-2222-2222-2222-222222222222',
        'aaaaaaaa-0002-0002-0002-aaaaaaaaaaaa'
    ),
    -- Менеджер UPDATE
    (
        'dddddddd-0003-0003-0003-dddddddddddd',
        '22222222-2222-2222-2222-222222222222',
        'aaaaaaaa-0003-0003-0003-aaaaaaaaaaaa'
    ),
    -- Менеджер DELETE
    (
        'dddddddd-0004-0004-0004-dddddddddddd',
        '22222222-2222-2222-2222-222222222222',
        'aaaaaaaa-0004-0004-0004-aaaaaaaaaaaa'
    )
    ON CONFLICT (id) DO NOTHING;