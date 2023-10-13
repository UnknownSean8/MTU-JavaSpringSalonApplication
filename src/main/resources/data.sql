INSERT INTO salons (salon_id, salon_name, salon_address, salon_phone_number, salon_days_open)
VALUES (1, 'Salon 1', 'Limerick, Ireland', 123456789, '1111111'),
       (2, 'Salon 2', 'Dublin, Ireland', 234567890, '0001110'),
       (3, 'Salon 3', 'Cork, Ireland', 345678901, '1111100'),
       (4, 'Salon 4', 'Cork, Ireland', 234567891, '1111111'),
       (5, 'Salon 5', 'Limerick, Ireland', 345678902, '1111110');

INSERT INTO stylists (stylist_id, stylist_name, stylist_phone_number, stylist_annual_salary, salon_id)
VALUES (1, 'Stylist 1', 785, 10000, 1),
       (2, 'Stylist 2', 345678902, 1000, 1),
       (3, 'Stylist 3', 345678902, 20000, 2);