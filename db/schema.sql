
CREATE TABLE IF NOT EXISTS rol
(
    `id`            INT NOT NULL AUTO_INCREMENT,
    `rol`           VARCHAR(255) NOT NULL,

    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS users
(
    `id`                 INT NOT NULL AUTO_INCREMENT,
    `rol_id`             INT NOT NULL,
    `name`               VARCHAR(255) NOT NULL,
    `last_name`          VARCHAR(255) NOT NULL,
    `email`              VARCHAR(255) NOT NULL,
    `user_name`          VARCHAR(255) NOT NULL,
    `password`           VARCHAR(255) NOT NULL,
    `birth_date`         TIMESTAMP NOT NULL,

    FOREIGN KEY (`rol_id`) REFERENCES rol(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS product_state
(
    `id`                     INT NOT NULL AUTO_INCREMENT,
    `status`                 ENUM('BORRADOR', 'ACTIVO', 'ELIMINADO') NOT NULL,

    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS category
(
    `id`                  INT NOT NULL AUTO_INCREMENT,
    `category`            VARCHAR(255) NOT NULL,

    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS transactions
(
    `id`                  INT NOT NULL AUTO_INCREMENT,
    `user_id`             INT NOT NULL,
    `created_at`          TIMESTAMP DEFAULT CURRENT_TIMESTAMP,

    FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS product
(
    `id`                     INT NOT NULL AUTO_INCREMENT,
    `product_state_id`       INT NOT NULL,
    `category_id`            INT NOT NULL,
    `user_id`                INT NOT NULL,
    `stock`                  INT NOT NULL,
    `description`            VARCHAR(255) NOT NULL,
    `price`                  DOUBLE,
    `created_at`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    `updated_at`             TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,

    PRIMARY KEY (`id`),
    FOREIGN KEY (`product_state_id`) REFERENCES product_state(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`category_id`) REFERENCES category(`id`) ON DELETE CASCADE ON UPDATE CASCADE
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS product_bought
(
    `id`                     INT NOT NULL AUTO_INCREMENT,
    `category_id`            INT NOT NULL,
    `transaction_id`         INT NOT NULL,
    `price`                  DOUBLE,
    `description`            VARCHAR(255) NOT NULL,
    `is_outstanding`         BOOLEAN,

    FOREIGN KEY (`category_id`) REFERENCES category(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`transaction_id`) REFERENCES transactions(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS photo
(
    `id`             INT NOT NULL AUTO_INCREMENT,
    `product_id`     INT NOT NULL,
    `url`            VARCHAR(255) NOT NULL,

    FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS cart
(
    `id`                  INT NOT NULL AUTO_INCREMENT,
    `user_id`             INT NOT NULL,

    FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS product_cart
(
    `id`                  INT NOT NULL AUTO_INCREMENT,
    `cart_id`             INT NOT NULL,
    `product_id`          INT NOT NULL,

    FOREIGN KEY (`cart_id`) REFERENCES cart(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS historic
(
    `id`                  INT NOT NULL AUTO_INCREMENT,
    `user_id`             INT NOT NULL,
    `product_id`          INT NOT NULL,

    FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;


CREATE TABLE IF NOT EXISTS favourite
(
    `id`                  INT NOT NULL AUTO_INCREMENT,
    `user_id`             INT NOT NULL,
    `product_id`          INT NOT NULL,

    FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;


-- Inserciones para la tabla `rol`
INSERT INTO rol (`rol`) VALUES ('Admin'), ('Cliente'), ('Vendedor'), ('Invitado'), ('Soporte');

-- Inserciones para la tabla `users`
INSERT INTO users (`rol_id`, `name`, `last_name`, `email`, `user_name`, `password`, `birth_date`) VALUES
(1, 'John', 'Doe', 'john.doe@example.com', 'johndoe', 'password123', '1985-02-15'),
(2, 'Jane', 'Smith', 'jane.smith@example.com', 'janesmith', 'password123', '1990-05-21'),
(3, 'Michael', 'Brown', 'michael.brown@example.com', 'michaelbrown', 'password123', '1988-11-11'),
(4, 'Emily', 'Johnson', 'emily.johnson@example.com', 'emilyjohnson', 'password123', '1995-03-30'),
(5, 'Sarah', 'Davis', 'sarah.davis@example.com', 'sarahdavis', 'password123', '1992-07-17');


-- Inserciones para la tabla `product_state`
INSERT INTO product_state (`status`) VALUES ('BORRADOR'), ('ACTIVO'), ('ELIMINADO');


-- Inserciones para la tabla `category`
INSERT INTO category (`category`) VALUES ('Camisetas'), ('Pantalones'), ('Chaquetas'), ('Zapatos'), ('Accesorios');


-- Inserciones para la tabla `transactions`
INSERT INTO transactions (`user_id`, `created_at`) VALUES
(2, NOW()),
(2, NOW()),
(3, NOW()),
(4, NOW()),
(5, NOW());

-- Inserciones para la tabla `product`
INSERT INTO product (`product_state_id`, `category_id`, `user_id`, `stock`, `description`, `price`) VALUES
(2, 1, 3, 50, 'Camiseta de algodón', 15.99),
(2, 2, 3, 30, 'Pantalón vaquero', 39.99),
(2, 3, 4, 20, 'Chaqueta de cuero', 79.99),
(2, 4, 4, 100, 'Zapatillas deportivas', 59.99),
(2, 5, 5, 150, 'Gorra de béisbol', 9.99);

-- Inserciones para la tabla `product_bought`
INSERT INTO product_bought (`category_id`, `transaction_id`, `price`, `description`) VALUES
(1, 1, 15.99, 'Camiseta de algodón'),
(2, 2, 39.99, 'Pantalón vaquero'),
(3, 3, 79.99, 'Chaqueta de cuero'),
(4, 4, 59.99, 'Zapatillas deportivas'),
(5, 5, 9.99, 'Gorra de béisbol');


-- Inserciones para la tabla `photo`
INSERT INTO photo (`product_id`, `url`) VALUES
(1, 'https://example.com/photo1.jpg'),
(2, 'https://example.com/photo2.jpg'),
(3, 'https://example.com/photo3.jpg'),
(4, 'https://example.com/photo4.jpg'),
(5, 'https://example.com/photo5.jpg');

-- Inserciones para la tabla `cart`
INSERT INTO cart (`user_id`) VALUES (2), (3), (4), (5), (2);

-- Inserciones para la tabla `product_cart`
INSERT INTO product_cart (`cart_id`, `product_id`) VALUES
(1, 1),
(2, 2),
(3, 3),
(4, 4),
(5, 5);

-- Inserciones para la tabla `historic`
INSERT INTO historic (`user_id`, `product_id`) VALUES
(2, 1),
(3, 2),
(4, 3),
(5, 4),
(2, 5);