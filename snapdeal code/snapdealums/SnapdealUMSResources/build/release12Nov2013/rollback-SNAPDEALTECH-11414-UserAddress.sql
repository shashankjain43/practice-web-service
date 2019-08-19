use ums;
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `name` varchar(128) DEFAULT NULL,
  `address1` varchar(255) DEFAULT NULL,
  `address2` varchar(255) DEFAULT NULL,
  `city` varchar(48) DEFAULT NULL,
  `state` varchar(48) DEFAULT NULL,
  `country` varchar(48) DEFAULT NULL,
  `postal_code` varchar(10) DEFAULT NULL,
  `phone` varchar(20) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `FK_user_address` (`user_id`),
  CONSTRAINT `FK_user_address` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8
