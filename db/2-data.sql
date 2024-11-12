-- Inserciones para la tabla `users`
INSERT INTO users (`role`, `first_name`, `last_name`, `email`, `user_name`, `password`, `birth_date`, `image_url`)
VALUES ("ADMIN", 'John', 'Doe', 'john.doe@example.com', 'johndoe', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1985-02-15', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLui6cKL2tXUu4LkDYbN4uJKkgTHU6zVpgvQ&s'),
       ("USER", 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1990-05-21', 'https://static.wikia.nocookie.net/the-secret-world-of-the-animated-characters/images/a/a3/Brett_Hand.jpg/revision/latest?cb=20211207145251'),
       ("USER", 'Michael', 'Brown', 'michael.brown@example.com', 'michaelbrown', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1988-11-11','https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLui6cKL2tXUu4LkDYbN4uJKkgTHU6zVpgvQ&s'),
       ("USER", 'Emily', 'Johnson', 'emily.johnson@example.com', 'emilyjohnson', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1995-03-30','https://static.wikia.nocookie.net/the-secret-world-of-the-animated-characters/images/a/a3/Brett_Hand.jpg/revision/latest?cb=20211207145251'),
       ("ADMIN", 'Sarah', 'Davis', 'sarah.davis@example.com', 'sarahdavis', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1992-07-17', 'https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSLui6cKL2tXUu4LkDYbN4uJKkgTHU6zVpgvQ&s');

-- Inserciones para la tabla `product_state`
INSERT INTO product_state (`id`, `status`)
VALUES (0, 'BORRADOR'),
       (1, 'ACTIVO'),
       (2, 'ELIMINADO');

-- Inserciones para la tabla `category`
INSERT INTO category (`category`)
VALUES ('Camisetas'),
       ('Pantalones'),
       ('Camperas'),
       ('Zapatillas'),
       ('Accesorios');

-- Inserciones para la tabla `transactions`
INSERT INTO transactions (`user_id`, `created_at`)
VALUES (2, '2024-08-10 11:27:31'),
       (2, '2024-08-11 12:26:32'),
       (3, '2024-08-12 13:25:33'),
       (4, '2024-08-13 14:24:34'),
       (5, '2024-08-14 15:23:35');

-- Inserciones para la tabla `product`
INSERT INTO product (`product_state_id`, `category_id`, `user_id`, `stock`, `name`, `description`, `is_outstanding`,`price_per_unit`, `created_at`, `updated_at`)
VALUES (1, 1, 3, 50, 'Camiseta', 'Camiseta de la Selección Argentina', true, 15.99, '2024-07-10 11:27:31', '2024-08-10 11:27:31'),
       (1, 2, 3, 30, 'Pantalón', 'Pantalón deportivo Nike', true, 39.99, '2024-07-11 11:27:31', '2024-08-11 12:26:32'),
       (1, 3, 4, 20, 'Campera', 'Campera Adidas Originals', true, 79.99, '2024-07-12 11:27:31', '2024-08-12 13:25:33'),
       (1, 4, 4, 100, 'Zapatillas', 'Zapatillas deportivas', true, 59.99, '2024-07-13 11:27:31', '2024-08-13 14:24:34'),
       (1, 5, 5, 150, 'Gorra', 'Gorra de béisbol', true, 9.99, '2024-07-14 11:27:31', '2024-08-14 15:23:35');

-- Inserciones para la tabla `product_bought`
INSERT INTO product_bought (`product_id`, `transaction_id`, `price_per_unit`, `quantity`, `description`, `category`)
VALUES (1, 1, 15.99, 1, 'Camiseta de la Selección Argentina', 'Camisetas'),
       (2, 2, 39.99, 2, 'Pantalón deportivo Nike', 'Pantalones'),
       (3, 3, 79.99, 3, 'Campera Adidas Originals', 'Camperas'),
       (4, 4, 59.99, 4, 'Zapatillas deportivas', 'Zapatillas'),
       (5, 5, 9.99, 5, 'Gorra de béisbol', 'Accesorios');

-- Inserciones para la tabla `outstanding`
INSERT INTO outstanding (`product_id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5);


-- Inserciones para la tabla `photo`
INSERT INTO photo (`product_id`,`priority`, `url`)
VALUES (1, 1,'https://s3.sa-east-1.amazonaws.com/www.vaypol.com.ar/variants/61r8qfnq1c0t0rcepynd5zlc9f4x/77e513bcd3762f47919c96f85e400038a39acdbb0d268f51c1fd98fe5327bd96'),
       (2, 1,'https://soccerpost.com/cdn/shop/products/064fb2103c11bc9cf2b11ae531e537cf.png?v=1673296638'),
       (3, 1,'https://s3.sa-east-1.amazonaws.com/www.vaypol.com.ar/variants/92371qt9n7s9zz7rwkgb0e59ze2i/77e513bcd3762f47919c96f85e400038a39acdbb0d268f51c1fd98fe5327bd96'),
       (4, 1,'https://static.nike.com/a/images/t_default/km2tqfsvv77pll60gc2r/WMNS+AIR+MAX+COMMAND.png'),
       (5, 1,'https://ferreira.vtexassets.com/arquivos/ids/422047-800-auto?v=638340040747730000&width=800&height=auto&aspect=true');

-- Inserciones para la tabla `cart`
INSERT INTO cart (`user_id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5);

-- Inserciones para la tabla `product_cart`
INSERT INTO product_cart (`cart_id`, `product_id`, `quantity`)
VALUES (1, 1, 10),
       (2, 2, 20),
       (3, 3, 15),
       (4, 4, 40),
       (5, 5, 50);

-- Inserciones para la tabla `historic`
INSERT INTO historic (`user_id`, `product_id`, `updated_at`)
VALUES (2, 1, '2024-08-10 11:27:31'),
       (2, 2, '2024-08-11 11:27:31'),
       (3, 3, '2024-08-12 11:27:31'),
       (4, 4, '2024-08-13 11:27:31'),
       (5, 5, '2024-08-15 11:27:31');

-- Inserciones para la tabla `favourite`
INSERT INTO favourite (`user_id`, `product_id`)
VALUES (2, 1),
       (2, 2),
       (3, 3),
       (4, 4);