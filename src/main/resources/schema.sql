CREATE TABLE salons
(
    salon_id           INT PRIMARY KEY,
    salon_name         VARCHAR(255) NOT NULL,
    salon_address      VARCHAR(255) NOT NULL,
    salon_phone_number VARCHAR(255)          NOT NULL,
    salon_days_open    VARCHAR(255) NOT NULL
);

CREATE TABLE stylists
(
    stylist_id            INT PRIMARY KEY,
    stylist_name          VARCHAR(255) NOT NULL,
    stylist_phone_number  VARCHAR(255),
    stylist_annual_salary INT          NOT NULL,
    salon_id INT,
    FOREIGN KEY (salon_id) references salons(salon_id) on delete cascade
);