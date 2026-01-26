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