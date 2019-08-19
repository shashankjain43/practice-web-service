use ums;
DROP TABLE IF EXISTS `user_address`;
CREATE TABLE `user_address` (
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `name` varchar(128) NOT NULL,
  `address1` varchar(400) NOT NULL,
  `address2` varchar(400) DEFAULT NULL,
  `city` varchar(48) NOT NULL,
  `state` varchar(48) NOT NULL,
  `country` varchar(48) NOT NULL DEFAULT 'India',
  `postal_code` varchar(10) NOT NULL,
  `mobile` varchar(10) DEFAULT NULL,
  `landline` varchar(15) DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `isdefault` tinyint(1) NOT NULL DEFAULT '0',
  `address_tag` varchar(48) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `FK_user_address_u` (`user_id`),
  CONSTRAINT unique_address UNIQUE (`user_id`,`address1`(200),`address2`(200),`city`,`state`,`country`,`postal_code`,`name`,`mobile`,`landline`),
  CONSTRAINT `FK_user_address_u` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

insert into `ums_property` (name,value) values ('user.address.count.limit','50');
