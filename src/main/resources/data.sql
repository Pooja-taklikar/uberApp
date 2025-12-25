INSERT INTO public.app_user (email, name, password) VALUES
('john.doe@example.com', 'John Doe', 'pass123'),
('jane.smith@example.com', 'Jane Smith', 'test456'),
('michael.brown@example.com', 'Michael Brown', 'password789'),
('emily.johnson@example.com', 'Emily Johnson', 'welcome1'),
('daniel.wilson@example.com', 'Daniel Wilson', 'securepwd'),
('sophia.miller@example.com', 'Sophia Miller', 'abc12345'),
('liam.davis@example.com', 'Liam Davis', 'qwerty12'),
('olivia.garcia@example.com', 'Olivia Garcia', 'letmein99'),
('noah.rodriguez@example.com', 'Noah Rodriguez', 'mypassword'),
('ava.martinez@example.com', 'Ava Martinez', 'superpass'),
('william.hernandez@example.com', 'William Hernandez', 'admin123'),
('isabella.lopez@example.com', 'Isabella Lopez', 'pass2024'),
('james.gonzalez@example.com', 'James Gonzalez', 'test@123'),
('mia.wilson@example.com', 'Mia Wilson', 'pwd987'),
('benjamin.anderson@example.com', 'Benjamin Anderson', 'loginme'),
('charlotte.thomas@example.com', 'Charlotte Thomas', 'access12'),
('lucas.taylor@example.com', 'Lucas Taylor', 'hello123'),
('amelia.moore@example.com', 'Amelia Moore', 'passpass'),
('henry.jackson@example.com', 'Henry Jackson', 'zxcvbn12'),
('harper.white@example.com', 'Harper White', 'secretkey');


INSERT INTO public.user_roles (user_id, roles) VALUES
(1, 'DRIVER'),
(2, 'RIDER'),
(3, 'DRIVER'),
(4, 'RIDER'),
(5, 'DRIVER'),
(6, 'RIDER'),
(7, 'DRIVER'),
(8, 'RIDER'),
(9, 'DRIVER'),
(10, 'RIDER'),
(11, 'DRIVER'),
(12, 'RIDER'),
(13, 'DRIVER'),
(14, 'RIDER'),
(15, 'DRIVER'),
(16, 'RIDER'),
(17, 'DRIVER'),
(18, 'RIDER'),
(19, 'DRIVER'),
(20, 'RIDER');


INSERT INTO public.rider (rating, id, user_id)
VALUES (4.9, 1, 10);

INSERT INTO public.driver (available, rating, id, user_id, vehicle_id, current_location) VALUES
(true, 4.8, 1, 1, 101, ST_GeomFromText('POINT(72.8777 19.0760)', 4326)), -- Mumbai
(false, 4.5, 3, 3, 103, ST_GeomFromText('POINT(73.8567 18.5204)', 4326)), -- Pune
(true, 4.9, 5, 5, 105, ST_GeomFromText('POINT(77.1025 28.7041)', 4326)), -- Delhi
(true, 4.6, 7, 7, 107, ST_GeomFromText('POINT(79.0882 21.1458)', 4326)), -- Nagpur
(false, 4.7, 9, 9, 109, ST_GeomFromText('POINT(77.5946 12.9716)', 4326)), -- Bengaluru
(true, 49.8, 11, 11, 111, ST_GeomFromText('POINT(78.4867 17.3850)', 4326)), -- Hyderabad
(true, 4.4, 13, 13, 113, ST_GeomFromText('POINT(80.2707 13.0827)', 4326)), -- Chennai
(false, 4.6, 15, 15, 115, ST_GeomFromText('POINT(75.8577 22.7196)', 4326)), -- Indore
(true, 4.9, 17, 17, 117, ST_GeomFromText('POINT(72.5714 23.0225)', 4326)), -- Ahmedabad
(true, 4.7, 19, 19, 119, ST_GeomFromText('POINT(88.3639 22.5726)', 4326)); -- Kolkata

INSERT INTO public.wallet (id, user_id, balance)
VALUES (1, 1, 100),
       (2, 2, 500),
       (3,10,400),
       (4,13,200);