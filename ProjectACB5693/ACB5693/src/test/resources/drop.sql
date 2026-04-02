DELETE FROM role_permission
WHERE id IN (
             'cccccccc-0001-0001-0001-cccccccccccc',
             'cccccccc-0002-0002-0002-cccccccccccc',
             'dddddddd-0001-0001-0001-dddddddddddd',
             'dddddddd-0002-0002-0002-dddddddddddd',
             'dddddddd-0003-0003-0003-dddddddddddd',
             'dddddddd-0004-0004-0004-dddddddddddd'
    );

DELETE FROM permission
WHERE id IN (
             'aaaaaaaa-0001-0001-0001-aaaaaaaaaaaa',
             'aaaaaaaa-0002-0002-0002-aaaaaaaaaaaa',
             'aaaaaaaa-0003-0003-0003-aaaaaaaaaaaa',
             'aaaaaaaa-0004-0004-0004-aaaaaaaaaaaa'
    );

DELETE FROM employee
WHERE id IN (
             'aaaaaaaa-1111-1111-1111-aaaaaaaaaaaa',
             'bbbbbbbb-2222-2222-2222-bbbbbbbbbbbb',
             'cccccccc-3333-3333-3333-cccccccccccc',
             'dddddddd-4444-4444-4444-dddddddddddd'
    );

DELETE FROM role
WHERE id IN (
             '11111111-1111-1111-1111-111111111111',
             '22222222-2222-2222-2222-222222222222'
    );