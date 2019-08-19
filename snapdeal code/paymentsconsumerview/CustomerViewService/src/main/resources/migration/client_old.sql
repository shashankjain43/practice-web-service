DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(128) NOT NULL,
  `client_name` varchar(128) NOT NULL,
  `merchant` varchar(30) NOT NULL,
  `client_platform` enum('WEB','WAP','APP') NOT NULL DEFAULT 'WEB',
  `client_key` varchar(128) NOT NULL,
  `client_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `created_time` datetime NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `client_role` enum('ADMIN','USER') NOT NULL DEFAULT 'USER',
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id_uk` (`client_id`),
  UNIQUE KEY `client_name_merchant_uk` (`client_name`,`merchant`(1))
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;