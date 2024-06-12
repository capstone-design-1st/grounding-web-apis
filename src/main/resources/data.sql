INSERT INTO
    admin_user (user_name, pw)
SELECT 'admin', 'admin' FROM DUAL
WHERE NOT EXISTS
(SELECT * FROM admin_user WHERE user_name = 'admin');

INSERT INTO
    admin_user (user_name, pw)
SELECT 'admin', 'admin' FROM DUAL
WHERE NOT EXISTS
          (SELECT * FROM admin_user WHERE user_name = 'admin');

INSERT INTO user (user_id, email, pw, phone_number, name, role, wallet_address, withdraw)
VALUES
    (1, 'test@user.com', '$2a$04$PqAI9RkcXM3QK6A/GkpbCetMX5Bh7Mt9eV5vO/3ULVPPJwG7Vishi', '01012341234', 'test_user', 'USER', 'sEdVcek3zjezSzMivnA9iu34adNYAW2', false)
    ON DUPLICATE KEY UPDATE user_id = user_id;
