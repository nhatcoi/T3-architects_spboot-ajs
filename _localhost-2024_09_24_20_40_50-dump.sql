-- MySQL dump 10.13  Distrib 9.0.1, for macos14.4 (arm64)
--
-- Host: 127.0.0.1    Database: shopapp
-- ------------------------------------------------------
-- Server version	9.0.1

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `categories`
--

DROP TABLE IF EXISTS `categories`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) NOT NULL DEFAULT '' COMMENT 'Category name',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `categories`
--

LOCK TABLES `categories` WRITE;
/*!40000 ALTER TABLE `categories` DISABLE KEYS */;
INSERT INTO `categories` VALUES (3,'Programming Course'),(4,'Programming Course'),(5,'Programming Course'),(6,'Programming Course'),(7,'Programming Course'),(8,'DB Course'),(9,'CS Course'),(10,'Electronics'),(11,'Fashion'),(12,'Home & Kitchen'),(13,'Books'),(14,'Sports');
/*!40000 ALTER TABLE `categories` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `order_details`
--

DROP TABLE IF EXISTS `order_details`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `order_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `number_of_products` int DEFAULT NULL,
  `total_price` float DEFAULT NULL,
  `color` varchar(50) DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `order_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `order_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `order_details_chk_1` CHECK ((`price` >= 0)),
  CONSTRAINT `order_details_chk_2` CHECK ((`number_of_products` > 0)),
  CONSTRAINT `order_details_chk_3` CHECK ((`total_price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `order_details`
--

LOCK TABLES `order_details` WRITE;
/*!40000 ALTER TABLE `order_details` DISABLE KEYS */;
INSERT INTO `order_details` VALUES (1,1,1,299.99,1,299.99,'Black'),(2,1,2,19.99,1,19.99,'Blue'),(3,2,3,49.99,1,49.99,'Silver');
/*!40000 ALTER TABLE `order_details` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `orders`
--

DROP TABLE IF EXISTS `orders`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `full_name` varchar(100) DEFAULT '',
  `email` varchar(100) DEFAULT '',
  `phone_number` varchar(20) NOT NULL,
  `address` varchar(255) NOT NULL,
  `note` varchar(255) DEFAULT '',
  `order_date` datetime DEFAULT CURRENT_TIMESTAMP,
  `status` enum('pending','processing','completed','delivered','canceled') DEFAULT NULL COMMENT 'Order status',
  `total_price` float NOT NULL,
  `shipping_method` varchar(50) DEFAULT '',
  `shipping_address` varchar(50) DEFAULT '',
  `shipping_date` date DEFAULT NULL,
  `tracking_number` varchar(100) DEFAULT NULL,
  `payment_method` varchar(100) DEFAULT NULL,
  `payment_date` datetime DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_chk_1` CHECK ((`total_price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `orders`
--

LOCK TABLES `orders` WRITE;
/*!40000 ALTER TABLE `orders` DISABLE KEYS */;
INSERT INTO `orders` VALUES (1,1,'John Doe','john@example.com','1234567890','123 Main St','Please deliver between 9 AM - 5 PM.','2024-09-21 11:25:45','pending',349.99,'Express','123 Main St','2024-09-22','TRK123456','Credit Card','2024-09-24 20:21:12',1),(2,2,'Jane Smith','jane@example.com','9876543210','456 Maple St','','2024-09-21 11:25:45','processing',49.99,'Standard','456 Maple St','2024-09-23','TRK654321','Paypal','2024-09-24 20:21:18',1);
/*!40000 ALTER TABLE `orders` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `product_images`
--

DROP TABLE IF EXISTS `product_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `image_url` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_images_product_id` (`product_id`),
  CONSTRAINT `fk_product_images_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE,
  CONSTRAINT `product_images_ibfk_1` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_images`
--

LOCK TABLES `product_images` WRITE;
/*!40000 ALTER TABLE `product_images` DISABLE KEYS */;
INSERT INTO `product_images` VALUES (2,12,'4aaaa04b-5b34-4a79-ad25-00549ae993a9_pornhub.jpeg'),(8,11,'a0dac641-571b-4ffb-8bae-56384b5e7761_CDB9D3CF-6FA5-4BA9-A185-24DC3FFDB6D1_1_105_c.jpeg'),(9,25,'smartphone_1.jpg'),(10,25,'smartphone_2.jpg'),(11,26,'tshirt_1.jpg'),(12,27,'blender_1.jpg'),(13,28,'cookbook_1.jpg'),(14,29,'shoes_1.jpg');
/*!40000 ALTER TABLE `product_images` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `products`
--

DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL DEFAULT '' COMMENT 'Product name',
  `price` float NOT NULL,
  `thumbnail` varchar(255) DEFAULT '' COMMENT 'Product thumbnail',
  `description` longtext COMMENT 'Product description',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL ON UPDATE CURRENT_TIMESTAMP,
  `category_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=19767 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `products`
--

LOCK TABLES `products` WRITE;
/*!40000 ALTER TABLE `products` DISABLE KEYS */;
INSERT INTO `products` VALUES (1,'Chatgpt',50,NULL,NULL,NULL,NULL,3),(2,'Chatgpt',50,NULL,NULL,NULL,NULL,3),(3,'Youtube Premium',50,NULL,NULL,NULL,NULL,3),(4,'Youtube Premium',50,NULL,NULL,NULL,NULL,3),(5,'Youtube Premium',50,NULL,NULL,NULL,NULL,4),(6,'Youtube Premium Family',50,NULL,NULL,'2024-09-12 11:12:26','2024-09-12 11:12:26',4),(7,'Youtube Premium Family',50,NULL,NULL,'2024-09-12 11:17:40','2024-09-12 11:17:40',4),(8,'Spotify Premium',50,NULL,NULL,'2024-09-12 11:18:17','2024-09-12 11:18:17',4),(9,'Spotify Premium',50,NULL,NULL,'2024-09-12 11:18:52','2024-09-12 11:18:52',4),(10,'Spotify Premium',50,NULL,'NhatPre','2024-09-12 11:20:27','2024-09-12 11:20:27',4),(11,'Spotify Premium',50,NULL,'NhatPre','2024-09-12 11:20:29','2024-09-12 11:20:29',4),(12,'Spotify Premium',50,NULL,'NhatPre','2024-09-12 11:21:35','2024-09-12 11:21:35',4),(13,'Pornhub Pro',50,NULL,'NhatPre','2024-09-12 14:40:44','2024-09-12 14:40:44',4),(14,'Pornhub Pro',50,NULL,'NhatPre','2024-09-14 08:36:09','2024-09-14 08:36:09',4),(15,'123 Pro',50,NULL,'NhatPre','2024-09-14 08:38:10','2024-09-14 08:38:10',4),(16,'456 Pro',50,NULL,'NhatPre','2024-09-14 08:41:29','2024-09-14 08:41:29',4),(17,'456 Pro',50,NULL,'NhatPre','2024-09-14 08:53:09','2024-09-14 08:53:09',4),(18,'111 Pro',50,NULL,'NhatPre','2024-09-14 09:26:24','2024-09-14 09:26:24',4),(19,'999 Pro',70,NULL,'NhatPre','2024-09-14 09:27:44','2024-09-14 09:27:44',4),(25,'Smartphone',299.99,'smartphone.jpg','A modern smartphone with 128GB storage.','2024-09-21 11:22:59','2024-09-21 11:22:59',10),(26,'T-shirt',19.99,'tshirt.jpg','Comfortable cotton t-shirt.','2024-09-21 11:22:59','2024-09-21 11:22:59',11),(27,'Blender',49.99,'blender.jpg','High-power blender for smoothies.','2024-09-21 11:22:59','2024-09-21 11:22:59',12),(28,'Cookbook',24.99,'cookbook.jpg','A popular cookbook with 200+ recipes.','2024-09-21 11:22:59','2024-09-21 11:22:59',13),(29,'Running Shoes',89.99,'shoes.jpg','Lightweight running shoes.','2024-09-21 11:22:59','2024-09-21 11:22:59',14),(30,'Durable Marble Bottle',805.94,NULL,'Officiis officiis illum dicta odit labore.','2024-09-21 08:12:18','2024-09-21 08:12:18',3),(31,'Mediocre Bronze Bag',118.57,NULL,'Animi a exercitationem alias.','2024-09-21 08:12:18','2024-09-21 08:12:18',10),(32,'Rustic Leather Plate',354.7,NULL,'Amet blanditiis qui earum atque est iusto.','2024-09-21 08:12:18','2024-09-21 08:12:18',5),(33,'Enormous Silk Lamp',161.92,NULL,'Sint necessitatibus voluptatum consequuntur amet dolore culpa.','2024-09-21 08:12:18','2024-09-21 08:12:18',12),(34,'Ergonomic Aluminum Car',536.5,NULL,'Doloribus ab ipsa necessitatibus.','2024-09-21 08:12:18','2024-09-21 08:12:18',9),(35,'Synergistic Wooden Bench',818.8,NULL,'Inventore quia quam velit laudantium.','2024-09-21 08:12:18','2024-09-21 08:12:18',11),(36,'Mediocre Silk Computer',506.44,NULL,'Optio modi recusandae totam impedit.','2024-09-21 08:12:18','2024-09-21 08:12:18',12),(37,'Enormous Copper Wallet',942.74,NULL,'Veritatis deserunt dicta sint repellendus tempore quam.','2024-09-21 08:12:18','2024-09-21 08:12:18',9),(38,'Incredible Steel Knife',384.42,NULL,'Error culpa rem repudiandae error.','2024-09-21 08:12:18','2024-09-21 08:12:18',6);
/*!40000 ALTER TABLE `products` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
  `id` int NOT NULL,
  `name` varchar(20) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (1,'ADMIN'),(2,'USER'),(3,'MODERATOR');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `social_accounts`
--

DROP TABLE IF EXISTS `social_accounts`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `social_accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `provider` varchar(20) NOT NULL COMMENT 'facebook, google',
  `provider_id` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `social_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `social_accounts`
--

LOCK TABLES `social_accounts` WRITE;
/*!40000 ALTER TABLE `social_accounts` DISABLE KEYS */;
INSERT INTO `social_accounts` VALUES (1,'facebook','FB123','john@example.com','John Doe',1),(2,'google','GO456','jane@example.com','Jane Smith',2);
/*!40000 ALTER TABLE `social_accounts` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tokens`
--

DROP TABLE IF EXISTS `tokens`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `tokens` (
  `id` int NOT NULL AUTO_INCREMENT,
  `token` varchar(255) NOT NULL,
  `token_ype` varchar(50) NOT NULL,
  `expiration_date` datetime DEFAULT NULL,
  `revoked` tinyint(1) NOT NULL,
  `expired` tinyint(1) NOT NULL,
  `user_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `tokens_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tokens`
--

LOCK TABLES `tokens` WRITE;
/*!40000 ALTER TABLE `tokens` DISABLE KEYS */;
INSERT INTO `tokens` VALUES (1,'abcd1234','access','2024-09-22 11:26:36',0,0,1),(2,'efgh5678','refresh','2024-09-28 11:26:36',0,0,2);
/*!40000 ALTER TABLE `tokens` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `full_name` varchar(100) DEFAULT '',
  `phone_number` varchar(20) NOT NULL,
  `address` varchar(255) DEFAULT '',
  `password` varchar(100) NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_active` tinyint(1) DEFAULT '1',
  `date_of_birth` date DEFAULT NULL,
  `facebook_id` int DEFAULT '0',
  `google_id` int DEFAULT '0',
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  KEY `password` (`password`),
  KEY `password_2` (`password`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (1,'Dr. Gilbert Shanahan','879-361-6564 x020','Davenport','111111111111',NULL,NULL,0,'2037-01-26',0,0,2),(2,'Ino','0376696037','Davenport','111111111111','2024-09-12 13:16:07','2024-09-12 13:16:07',0,'2037-01-26',0,0,2),(3,'John Doe','1234567890','123 Main St','password123','2024-09-21 11:25:12','2024-09-21 11:25:12',1,'1990-01-01',0,0,2),(4,'Jane Smith','9876543210','456 Maple St','password456','2024-09-21 11:25:12','2024-09-21 11:25:12',1,'1985-05-15',123,0,3);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-09-24 20:40:51
