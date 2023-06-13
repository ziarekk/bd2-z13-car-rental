-- Wstawianie danych do tabeli user_roles
INSERT INTO user_roles (name) VALUES
                                  ('normal'),
                                  ('employee'),
                                  ('vip'),
                                  ('superuser');

-- Wstawianie danych do tabeli payment_types
INSERT INTO payment_types (name) VALUES
                                     ('Przelew bankowy'),
                                     ('Karta kredytowa'),
                                     ('Gotówka');

-- Wstawianie danych do tabeli segments
INSERT INTO segments (hour_rate, km_rate, name, rental_fee)
VALUES
    (10.0, 0.5, 'Ekonomiczny', 30.0),
    (15.0, 0.7, 'Standardowy', 40.0),
    (20.0, 0.9, 'Luksusowy', 50.0),
    (12.0, 0.6, 'Kompaktowy', 35.0),
    (18.0, 0.8, 'Sportowy', 45.0);


-- Wstawianie danych do tabeli cars
INSERT INTO cars (brand, fuel_capacity, fuel_level, fuel_type, is_available_for_rent, latitude, longitude, mileage, model, production_year, registration_number, seats, transmission, segment_id)
VALUES
-- Ekonomiczne
('Volkswagen', 45.0, 30.0, 'benzyna', true, 52.2200, 21.0111, 50000, 'Golf', 2018, 'ABC123', 5, 'manualna', 1),
('Toyota', 50.0, 40.0, 'hybryda', true, 52.2250, 21.0150, 40000, 'Yaris', 2020, 'DEF456', 5, 'automatyczna', 1),
('Hyundai', 50.0, 35.0, 'benzyna', true, 52.2700, 21.0487, 30000, 'i30', 2019, 'EFG012', 5, 'manualna', 1),
('Kia', 45.0, 30.0, 'LPG', true, 52.2750, 21.0525, 28000, 'Ceed', 2020, 'HIJ345', 5, 'automatyczna', 1),
('Nissan', 50.0, 35.0, 'benzyna', true, 52.3150, 21.0825, 28000, 'Micra', 2020, 'EFG890', 5, 'manualna', 1),
('Skoda', 45.0, 30.0, 'benzyna', true, 52.3200, 21.0862, 30000, 'Fabia', 2019, 'HIJ123', 5, 'automatyczna', 1),
-- Standardowe
('Ford', 55.0, 45.0, 'LPG', false, 52.2300, 21.0188, 60000, 'Focus', 2017, 'GHI789', 5, 'manualna', 2),
('Renault', 60.0, 55.0, 'benzyna', true, 52.2350, 21.0225, 55000, 'Clio', 2019, 'JKL012', 5, 'automatyczna', 2),
('Peugeot', 55.0, 45.0, 'diesel', false, 52.2800, 21.0562, 50000, '308', 2018, 'KLM678', 5, 'manualna', 2),
('Citroen', 60.0, 55.0, 'benzyna', true, 52.2850, 21.0600, 45000, 'C4', 2019, 'NOP901', 5, 'automatyczna', 2),
('Vauxhall', 55.0, 45.0, 'diesel', true, 52.3250, 21.0900, 50000, 'Astra', 2018, 'KLM456', 5, 'manualna', 2),
-- Luksusowe
('Mercedes-Benz', 70.0, 60.0, 'diesel', true, 52.2400, 21.0262, 30000, 'E-Class', 2021, 'MNO345', 5, 'automatyczna', 3),
('BMW', 65.0, 55.0, 'benzyna', true, 52.2450, 21.0300, 35000, 'X5', 2020, 'PQR678', 5, 'automatyczna', 3),
('Lexus', 70.0, 60.0, 'hybryda', false, 52.2900, 21.0637, 40000, 'RX', 2020, 'QRS234', 5, 'automatyczna', 3),
('Jaguar', 75.0, 65.0, 'benzyna', true, 52.2950, 21.0675, 35000, 'F-Pace', 2021, 'TUV567', 5, 'automatyczna', 3),
-- Kompaktowe
('Fiat', 45.0, 30.0, 'benzyna', true, 52.2500, 21.0337, 45000, '500', 2018, 'STU901', 4, 'manualna', 4),
('Opel', 50.0, 40.0, 'diesel', true, 52.2550, 21.0375, 40000, 'Corsa', 2019, 'VWX234', 4, 'manualna', 4),
('Mazda', 45.0, 30.0, 'benzyna', true, 52.3000, 21.0712, 35000, '3', 2020, 'WXY901', 4, 'manualna', 4),
('Seat', 50.0, 40.0, 'diesel', true, 52.3050, 21.0750, 30000, 'Leon', 2019, 'ZAB234', 4, 'manualna', 4),
-- Sportowe
('Porsche', 80.0, 70.0, 'benzyna', true, 52.2600, 21.0412, 20000, '911', 2022, 'YZA567', 2, 'automatyczna', 5),
('Audi', 75.0, 24.0, 'elektryczny', true, 52.2650, 21.0450, 25000, 'E-Tron GT', 2021, 'BCD789', 2, 'automatyczna', 5),
('Chevrolet', 85.0, 75.0, 'benzyna', true, 52.3100, 21.0787, 15000, 'Camaro', 2022, 'CDE567', 2, 'automatyczna', 5);


-- Dodawanie użytkowników do tabeli users
INSERT INTO users (email, hashed_password, login, role_id) VALUES
                                                               ('janeck@uwu.pl', '$2a$10$Jj/6lPoEbbCFgp7aBT9rH.5fRAK0Q4zFta/OLDy.zNds2eLXWzZh2', 'johny', 1),
                                                               ('tik@tok.rak', '$2a$10$GehJwd3DyVrqqmyWHNSR5.b8C.qR20f5ALCPnfA5d63d3fREXCAu.', 'yanny', 2),
                                                               ('rockstar@gta.eu', '$2a$10$CiNMosNxAKKFCyA1AsVG4.saUejWKeixdL5IWYNVx8Dq1TJ8bvd8S', 'mikey', 3),
                                                               ('anime@lover.com', '$2a$10$ApAYwMmFuvy6AzSTDIcCD.G7VdbBNDyUmUuLY/uno.pt8Vk1sKusK', 'tate', 4);

-- Wstawianie danych do tabeli clients
INSERT INTO clients (birth_date, gender, is_verified, name, phone_number, surname, user_id) VALUES
                                                                                                ('1990-01-15', 'M', true, 'John', '123456789', 'Smith', 1),
                                                                                                ('1985-05-20', 'K', true, 'Jennifer', '987654321', 'Brown',  2),
                                                                                                ('1995-12-01', 'K', false, 'Michael', '555555555', 'Jones', 3);

-- Wstawianie danych do tabeli driver_licences
INSERT INTO driver_licences (category, date_obtained, expiration_date, number, client_id) VALUES
                                                                                              ('B', '2010-06-15', '2023-06-15', 'ABC123', 1),
                                                                                              ('A', '2008-02-10', '2024-02-10', 'XYZ789', 2),
                                                                                              ('B', '2015-08-01', '2023-08-01', 'DEF456', 3);

INSERT INTO location_history (latitude, longitude, registration_timestamp, car_id)
VALUES
    (52.2297, 21.0122, '2023-05-16 10:00:00', 2),
    (51.7592, 19.4559, '2023-05-16 11:30:00', 2),
    (53.4289, 14.5530, '2023-05-16 13:15:00', 2),
    (54.3520, 18.6466, '2023-05-16 15:45:00', 2),

    (50.0647, 19.9450, '2023-05-16 09:30:00', 4),
    (52.4064, 16.9252, '2023-05-16 11:00:00', 4),
    (53.1325, 23.1688, '2023-05-16 12:45:00', 4),
    (51.1079, 17.0385, '2023-05-16 14:30:00', 4),

    (52.2370, 21.0175, '2023-05-16 09:45:00', 6),
    (50.0497, 19.9445, '2023-05-16 11:15:00', 6),
    (51.7592, 19.4559, '2023-05-16 13:00:00', 6),
    (53.4289, 14.5530, '2023-05-16 15:30:00', 6),

    (50.0647, 19.9450, '2023-05-16 10:15:00', 16),
    (52.4064, 16.9252, '2023-05-16 11:45:00', 16),
    (53.1325, 23.1688, '2023-05-16 13:30:00', 16),
    (51.1079, 17.0385, '2023-05-16 15:15:00', 16);

-- Wprowadzanie danych dla wypożyczeń

INSERT INTO rental_start (start_mileage, start_time)
VALUES
    (49000, '2023-05-01 10:00:00'),
    (39900, '2023-05-02 11:30:00'),
    (29040, '2023-05-03 14:45:00'),
    (27899, '2023-05-04 09:15:00'),
    (27929, '2023-05-05 16:20:00');

INSERT INTO rentals (car_id, client_id, start_id)
VALUES
    (1, 1, 1),
    (2, 2, 2),
    (3, 3, 3),
    (4, 1, 4),
    (5, 2, 5);

INSERT INTO rental_end (end_mileage, end_time)
VALUES
    (50000, '2023-05-01 15:30:00'),
    (40000, '2023-05-02 18:45:00'),
    (30000, '2023-05-03 12:00:00'),
    (28000, '2023-05-04 14:30:00'),
    (28000, '2023-05-05 20:00:00');

UPDATE rentals
SET end_id = 1
WHERE rental_id = 1;

UPDATE rentals
SET end_id = 2
WHERE rental_id = 2;

UPDATE rentals
SET  end_id = 3
WHERE rental_id = 3;

UPDATE rentals
SET end_id = 4
WHERE rental_id = 4;

UPDATE rentals
SET  end_id = 5
WHERE rental_id = 5;


-- Dane do komentarzy, zniżek i kar
INSERT INTO penalties (amount, description, rental_id)
VALUES
    (50.00, 'Opóźnienie zwrotu', 1),
    (20.50, 'Uszkodzenie pojazdu', 2),
    (15.50, 'Brudne wnętrze', 5);


INSERT INTO comments (content, rental_id)
VALUES
    ('Bardzo dobry samochód!', 1),
    ('Pojazd miał pewne problemy techniczne.', 2),
    ('Obsługa klienta była świetna.', 3),
    ('Szybki i sprawnie przeprowadzony wynajem.', 4),
    ('Nie polecam. Samochód był brudny i zniszczony.', 5);


INSERT INTO discounts (description, percent, rental_id)
VALUES
    ('Karta stałego klienta', 5, 3),
    ('Rabat dla nowego klienta', 10, 4);


-- Dane dla rachunku i płatności
INSERT INTO bills (amount, date_due, rental_id)
VALUES
    (100.50, '2023-05-10', 1),
    (80.25, '2023-05-11', 2),
    (120.75, '2023-05-12', 3),
    (150.00, '2023-05-13', 4),
    (90.50, '2023-05-14', 5);

INSERT INTO payments (status, bill_id, type_id)
VALUES
    ('oczekujaca', 1, 1),
    ('oczekujaca', 2, 2),
    ('oczekujaca', 3, 3),
    ('oczekujaca', 4, 3),
    ('oczekujaca', 5, 2);

COMMIT;
