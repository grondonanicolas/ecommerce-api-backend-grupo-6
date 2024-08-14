
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

    FOREIGN KEY (`category_id`) REFERENCES category(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`transaction_id`) REFERENCES transactions(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    PRIMARY KEY (`id`)
) CHARACTER SET utf8mb4
  COLLATE utf8mb4_bin;

CREATE TABLE IF NOT EXISTS outstanding
(
    `id`                     INT NOT NULL AUTO_INCREMENT,
    `product_id`             INT NOT NULL,

    FOREIGN KEY (`product_id`) REFERENCES product(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
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

    FOREIGN KEY (`cart_id`) REFERENCES cart(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
    FOREIGN KEY (`user_id`) REFERENCES users(`id`) ON DELETE CASCADE ON UPDATE CASCADE,
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