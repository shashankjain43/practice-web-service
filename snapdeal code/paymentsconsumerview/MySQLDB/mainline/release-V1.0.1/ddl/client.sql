DROP TABLE IF EXISTS `client`;

CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(128) NOT NULL,
  `client_name` varchar(128) NOT NULL,
  `merchant` enum('SNAPDEAL','FREECHARGE','ONECHECK') NOT NULL,
  `client_type` enum('USER_FACING','NON_USER_FACING') NOT NULL,
  `client_platform` enum('WEB','WAP','APP') NOT NULL DEFAULT 'WEB',
  `client_key` varchar(128) NOT NULL,
  `client_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `created_time` datetime NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id_uk` (`client_id`),
  UNIQUE KEY `client_name_merchant_uk` (`client_name`,`merchant`)
) ENGINE=InnoDB AUTO_INCREMENT=20 DEFAULT CHARSET=utf8;
