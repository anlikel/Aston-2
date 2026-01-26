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