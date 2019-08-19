drop table if exists user_verification;
CREATE TABLE `user_verification` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `code` varchar(128) NOT NULL,
  `user_id` varchar(128) NOT NULL,
  `code_expiry_time` datetime NOT NULL,
  `created_time` datetime NOT NULL,
  `purpose` enum('VERIFY_NEW_USER','FORGOT_PASSWORD') NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY code_uk(`code`),
  INDEX user_id_index (`user_id`) 
);
