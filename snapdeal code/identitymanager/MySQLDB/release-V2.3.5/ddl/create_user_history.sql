CREATE TABLE `user_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` varchar(128) NOT NULL,
  `field` varchar(128) NOT NULL,
  `old_value` varchar(4000) DEFAULT NULL,
  `new_value` varchar(4000) DEFAULT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);
