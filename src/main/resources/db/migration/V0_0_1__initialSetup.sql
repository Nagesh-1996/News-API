create SCHEMA IF NOT EXISTS `evaluation_db_20_21` DEFAULT CHARACTER SET utf8;
USE `evaluation_db_20_21` ;


create TABLE IF NOT EXISTS `evaluation_db_20_21`.`user_model`  (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `email_id` varchar(50)  NOT NULL,
 `user_name` varchar(20)  DEFAULT NULL,
 `mobile_no` varchar(15)  DEFAULT NULL,
 `preffered_language` varchar(50)  DEFAULT NULL,
 `password` varchar(1024)  DEFAULT NULL,
 `unique_user_id` TEXT  DEFAULT NULL,
 `is_registered` TINYINT(1)  DEFAULT 0,
 `created_at` DATETIME DEFAULT NULL,
 `updated_at` DATETIME DEFAULT NULL,
 PRIMARY KEY (`id`),
 INDEX `idx` (`id` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;




create TABLE IF NOT EXISTS `evaluation_db_20_21`.`language_model`  (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `language_name` varchar(50)  DEFAULT NULL,
 `language_code` varchar(20)  DEFAULT NULL,
 PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;


INSERT INTO `language_model` (`id`, `language_name`, `language_code`) VALUES
(1, 'English', 'en'),
(2, 'Hindi', 'hi'),
(3, 'Kannada', 'kn'),
(4, 'Bengali', 'bn');



create TABLE IF NOT EXISTS `evaluation_db_20_21`.`news_bookmark_model`  (
 `id` INT(11) NOT NULL AUTO_INCREMENT,
 `user_id` INT(11)  NOT NULL,
 `news_source` varchar(100)  DEFAULT NULL,
 `author` TEXT DEFAULT NULL,
 `title` varchar(20)  DEFAULT NULL,
 `description` TEXT  DEFAULT NULL,
 `url` TEXT  DEFAULT NULL,
 `url_to_image` TEXT  DEFAULT NULL,
 `published_at` TEXT  DEFAULT NULL,
 `created_at` DATETIME DEFAULT NULL,
 `updated_at` DATETIME DEFAULT NULL,
 PRIMARY KEY (`id`),
  INDEX `idx` (`id` ASC)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 ;

