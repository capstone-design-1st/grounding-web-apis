INSERT INTO
    admin_user (user_name, pw)
SELECT 'admin', 'admin' FROM DUAL
WHERE NOT EXISTS
(SELECT * FROM admin_user WHERE user_name = 'admin');
