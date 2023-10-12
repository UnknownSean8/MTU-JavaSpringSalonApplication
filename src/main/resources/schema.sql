CREATE TABLE salons
(
    salon_id           INT PRIMARY KEY,
    salon_name         VARCHAR(255) NOT NULL,
    salon_address      VARCHAR(255) NOT NULL,
    salon_phone_number INT          NOT NULL,
    salon_days_open    VARCHAR(255) NOT NULL
);