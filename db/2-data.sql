-- Inserciones para la tabla `users`
INSERT INTO users (`role`, `first_name`, `last_name`, `email`, `user_name`, `password`, `birth_date`)
VALUES ("ADMIN", 'John', 'Doe', 'john.doe@example.com', 'johndoe', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1985-02-15'),
       ("USER", 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1990-05-21'),
       ("USER", 'Michael', 'Brown', 'michael.brown@example.com', 'michaelbrown', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1988-11-11'),
       ("USER", 'Emily', 'Johnson', 'emily.johnson@example.com', 'emilyjohnson', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1995-03-30'),
       ("ADMIN", 'Sarah', 'Davis', 'sarah.davis@example.com', 'sarahdavis', '$2a$10$9GK5AKck9kZlnDM6ER/uL.P2Myy3Q3ifwsIuogoXK..oSjiNX.Pmm', '1992-07-17');

-- Inserciones para la tabla `product_state`
INSERT INTO product_state (`id`, `status`)
VALUES (0, 'BORRADOR'),
       (1, 'ACTIVO'),
       (2, 'ELIMINADO');

-- Inserciones para la tabla `category`
INSERT INTO category (`category`)
VALUES ('Camisetas'),
       ('Pantalones'),
       ('Chaquetas'),
       ('Zapatos'),
       ('Accesorios');

-- Inserciones para la tabla `transactions`
INSERT INTO transactions (`user_id`, `created_at`)
VALUES (2, '2024-08-10 11:27:31'),
       (2, '2024-08-11 12:26:32'),
       (3, '2024-08-12 13:25:33'),
       (4, '2024-08-13 14:24:34'),
       (5, '2024-08-14 15:23:35');

-- Inserciones para la tabla `product`
INSERT INTO product (`product_state_id`, `category_id`, `user_id`, `stock`, `description`, `price_per_unit`, `created_at`, `updated_at`)
VALUES (1, 1, 3, 50, 'Camiseta de algodón', 15.99, '2024-07-10 11:27:31', '2024-08-10 11:27:31'),
       (1, 2, 3, 30, 'Pantalón vaquero', 39.99, '2024-07-11 11:27:31', '2024-08-11 12:26:32'),
       (1, 3, 4, 20, 'Chaqueta de cuero', 79.99, '2024-07-12 11:27:31', '2024-08-12 13:25:33'),
       (1, 4, 4, 100, 'Zapatillas deportivas', 59.99, '2024-07-13 11:27:31', '2024-08-13 14:24:34'),
       (1, 5, 5, 150, 'Gorra de béisbol', 9.99, '2024-07-14 11:27:31', '2024-08-14 15:23:35');

-- Inserciones para la tabla `product_bought`
INSERT INTO product_bought (`product_id`, `transaction_id`, `price_per_unit`, `quantity`, `description`, `category`)
VALUES (1, 1, 15.99, 1, 'Camiseta de algodón', 'Camisetas'),
       (2, 2, 39.99, 2, 'Pantalón vaquero', 'Pantalones'),
       (3, 3, 79.99, 3, 'Chaqueta de cuero', 'Chaquetas'),
       (4, 4, 59.99, 4, 'Zapatillas deportivas', 'Zapatos'),
       (5, 5, 9.99, 5, 'Gorra de béisbol', 'Accesorios');

-- Inserciones para la tabla `outstanding`
INSERT INTO outstanding (`product_id`)
VALUES (1),
       (2),
       (3),
       (4),
       (5);


-- Inserciones para la tabla `photo`
INSERT INTO photo (`product_id`, `url`)
VALUES (1, 'https://example.com/photo1.jpg'),
       (2, 'https://example.com/photo2.jpg'),
       (3, 'https://example.com/photo3.jpg'),
       (4, 'https://example.com/photo4.jpg'),
       (5, 'https://example.com/photo5.jpg');

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
INSERT INTO historic (`user_id`, `product_id`)
VALUES (2, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5);
