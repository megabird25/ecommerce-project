DROP DATABASE IF EXISTS `ecommerce`;

CREATE DATABASE `ecommerce`;
USE `ecommerce`;

DROP TABLE IF EXISTS `users`;
DROP TABLE IF EXISTS `user_address`;
DROP TABLE IF EXISTS `categories`;
DROP TABLE IF EXISTS `products`;
DROP TABLE IF EXISTS `product_details`;
DROP TABLE IF EXISTS `brand`;
DROP TABLE IF EXISTS `reviews_and_ratings`;
DROP TABLE IF EXISTS `orders`;
DROP TABLE IF EXISTS `order_details`;
DROP TABLE IF EXISTS `payments`;


CREATE TABLE `users` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `account` VARCHAR(50) UNIQUE NOT NULL,
  `password` VARCHAR(255) NOT NULL,
  `email` VARCHAR(100) DEFAULT null,
  `mobile` VARCHAR(20) DEFAULT null,
  `avatar` VARCHAR(255) DEFAULT null,
  `nickname` VARCHAR(20) DEFAULT null,
  `gender` CHAR(1) DEFAULT null,
  `birthday` DATETIME DEFAULT null,
  `created_date` DATETIME DEFAULT (CURRENT_TIMESTAMP)
);

CREATE TABLE `user_address` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `receiver` VARCHAR(20) NOT NULL,
  `contact` VARCHAR(20) NOT NULL,
  `address` VARCHAR(255) NOT NULL,
  `isDefault` TINYINT NOT NULL,
  `user_id` INT
);

CREATE TABLE `categories` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `image_url` VARCHAR(255) DEFAULT null,
  `parent_category_id` INT
);

CREATE TABLE `products` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `description` TEXT DEFAULT null,
  `price` DECIMAL(10,2) NOT NULL,
  `inventory` INT NOT NULL,
  `image_url` VARCHAR(255) DEFAULT null,
  `created_date` DATETIME DEFAULT (CURRENT_TIMESTAMP),
  `category_id` INT,
  `brand_id` INT
);

CREATE TABLE `product_details` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(100) NOT NULL,
  `value` VARCHAR(100) NOT NULL,
  `product_id` INT
);

CREATE TABLE `brand` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `name` VARCHAR(50) NOT NULL,
  `logo` VARCHAR(255) DEFAULT null
);

CREATE TABLE `reviews_and_ratings` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `rating` INT NOT NULL,
  `review` TEXT DEFAULT null,
  `created_date` DATETIME DEFAULT (CURRENT_TIMESTAMP),
  `user_id` INT,
  `product_id` INT
);

CREATE TABLE `orders` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `receiver` VARCHAR(20) NOT NULL,
  `receiver_contact` VARCHAR(20) NOT NULL,
  `receiver_address` VARCHAR(255) NOT NULL,
  `total_amount` DECIMAL(10,2) NOT NULL,
  `total_quantity` INT NOT NULL,
  `status` VARCHAR(50) NOT NULL,
  `order_date` DATETIME DEFAULT (CURRENT_TIMESTAMP),
  `user_id` INT
);

CREATE TABLE `order_details` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `quantity` INT NOT NULL,
  `unit_price` DECIMAL(10,2) NOT NULL,
  `order_id` INT,
  `product_id` INT
);

CREATE TABLE `payments` (
  `id` INT PRIMARY KEY AUTO_INCREMENT,
  `amount` DECIMAL(10,2) NOT NULL,
  `method` VARCHAR(50) NOT NULL,
  `status` VARCHAR(50) NOT NULL,
  `payment_date` DATETIME DEFAULT (CURRENT_TIMESTAMP),
  `order_id` INT
);

ALTER TABLE `user_address` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `categories` ADD FOREIGN KEY (`parent_category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `products` ADD FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`);

ALTER TABLE `product_details` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `products` ADD FOREIGN KEY (`brand_id`) REFERENCES `brand` (`id`);

ALTER TABLE `orders` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `order_details` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`);

ALTER TABLE `reviews_and_ratings` ADD FOREIGN KEY (`product_id`) REFERENCES `products` (`id`);

ALTER TABLE `reviews_and_ratings` ADD FOREIGN KEY (`user_id`) REFERENCES `users` (`id`);

ALTER TABLE `payments` ADD FOREIGN KEY (`id`) REFERENCES `payments` (`order_id`);
