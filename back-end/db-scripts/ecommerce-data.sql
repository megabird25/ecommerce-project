USE `ecommerce`;

SET FOREIGN_KEY_CHECKS = 0;
TRUNCATE TABLE `categories`;
TRUNCATE TABLE `brand`;
TRUNCATE TABLE `products`;
TRUNCATE TABLE `product_detail_images`;
SET FOREIGN_KEY_CHECKS = 1;


-- Insert main categories
INSERT INTO `categories` (`name`, `image_url`, `parent_category_id`) VALUES
('3C', NULL, NULL),
('電器', NULL, NULL),
('服飾', NULL, NULL),
('護理', NULL, NULL),
('家居', NULL, NULL),
('休閒', NULL, NULL),
('食品', NULL, NULL),
('文具', NULL, NULL);

-- Insert subcategories
INSERT INTO `categories` (`name`, `image_url`, `parent_category_id`) VALUES
-- 電子產品 Subcategories (parent_category_id = 1)
('智慧型手機', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/evFDEipqrxV6tLWQNSHNz0ao.jpg', 1),
('筆記型電腦', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/fFnVJys6jFZamVJs8D1RD8ln.jpg', 1),
('平板電腦', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/66HUoDDzuIAWmTHfLBKsMfc4.jpg', 1),
('智能手錶', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/1bvR4Ka1hdAAPPb3PA6R9yyy.jpg', 1),
('液晶電視', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/XCq6egNK9hpO4tgIPKW5pTxP.jpg', 1),
('音響設備', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/4SOPbKaCfzUwX9arLz7EBga0.jpg', 1),

-- 家用電器 Subcategories (parent_category_id = 2)
('冰箱', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/0mazshEZZGxPCL2G8jJ6Ghu1.jpg', 2),
('洗衣機', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/woELPyr0yvS0TNQ3B88hlJK4.jpg', 2),
('微波爐', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/mygbU9SMylvtAgv8KfmQPpIO.jpg', 2),
('吸塵器', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/zzSCV14TlBT4GOHhfx33DLax.jpg', 2),
('電風扇', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/rFj6qLAm3dBTc72RAtZo5jjD.jpg', 2),

-- 時尚服飾 Subcategories (parent_category_id = 3)
('男裝', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/1010dcdc123cxxjims.jpg', 3),
('女裝', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/64dae68147d27245422205.jpg', 3),
('童裝', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/c883b9a86dfd70d360e33de402c520fc.jpg', 3),
('鞋類', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/16395610471_default.jpg', 3),

-- 美容與個人護理 Subcategories (parent_category_id = 4)
('化妝品', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/5c80e7eea51de.jpg', 4),
('衛生清潔', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/Pz8NM6ytbM_small.jpg', 4),

-- 家居與家具 Subcategories (parent_category_id = 5)
('家具', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/oJN0PqT01YbAuG4v8yoS8id8.jpg', 5),
('廚房用品', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/cb20eeaed9-Gd-9632629.jpg', 5),
('燈具', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/0596541a-c448-4aec-bb7e-fb2b2a6f118b.jpg', 5),
('家居裝飾', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/O1CN01ydlqhe2BeJF3Xngna_0-item_p.jpg', 5),

-- 運動與戶外 Subcategories (parent_category_id = 6)
('健身器材', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/21950029430870_661.jpg', 6),
('戶外裝備', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/10438380_O_m.jpg', 6),

-- 食品與飲料 Subcategories (parent_category_id = 7)
('零食', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/22A5A1C0B0D6A52E12C413BCFE189A7D.jpg', 7),
('生鮮食品', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/freshfoodimage.jpg', 7),
('飲料', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/11fbe79a022c600c73774e.jpg', 7),
('保健食品', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/phpSjVZnK.jpg', 7),

-- 書籍與文具 Subcategories (parent_category_id = 8)
('文學書籍', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/8534.jpg_wh300.jpg', 8),
('兒童讀物', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/D_CY026-1.jpg', 8),
('漫畫', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/7mDB9ItNqGMwnpXpirSEahZgU2N8Z1S7.jpg', 8),
('辦公用品', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Category/uhjm876dcjhmc9jxhjpg.jpg', 8);

-- Insert brand
INSERT INTO `brand` (`name`, `logo`) VALUES
('MB', 'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Brand/K8FBCgI.png');

-- Insert products
DELIMITER $$

DROP PROCEDURE IF EXISTS InsertProducts$$
CREATE PROCEDURE InsertProducts()
BEGIN
    DECLARE i INT DEFAULT 1001;
    WHILE i <= 2000 DO
            INSERT INTO `products` (`name`, `description`, `price`, `inventory`, `image_url`, `category_id`, `brand_id`)
            VALUES (
                       CONCAT('商品 ', i),
                       '品質保證，值得信賴，您的最佳選擇！',
                       ROUND(100 + (RAND() * 900)), -- Generates a random price between 100 and 1000
                       FLOOR(10 + (RAND() * 90)), -- Generates a random inventory between 10 and 100
                       'https://weihan-ecommerce.s3.ap-northeast-1.amazonaws.com/Product/OoSveVEAI68NnpP.thumb.700_0.jpeg',
                       FLOOR(9 + (RAND() * 30)),  -- Random category_id between 9 and 39
                       FLOOR(1 + (RAND() * 0))   -- Random brand_id between 1 and 1
                   );
            SET i = i + 1;
        END WHILE;
END $$

DELIMITER ;

CALL InsertProducts();
DROP PROCEDURE IF EXISTS InsertProducts;

-- Insert product_detail_images
DELIMITER $$

DROP PROCEDURE IF EXISTS InsertProductDetailImages$$
CREATE PROCEDURE InsertProductDetailImages()
BEGIN
    DECLARE i INT DEFAULT 1;
    WHILE i <= 1000 DO
            INSERT INTO `product_detail_images` (`image_url`, `product_id`)
            VALUES
                ('url', i);
            SET i = i + 1;
        END WHILE;
END $$

DELIMITER ;

CALL InsertProductDetailImages();
DROP PROCEDURE IF EXISTS InsertProductDetailImages;
