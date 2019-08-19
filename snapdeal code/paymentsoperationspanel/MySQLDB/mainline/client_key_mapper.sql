DROP TABLE IF EXISTS `client_key_mapper`;

CREATE TABLE `client_key_mapper` (
  `user_id` varchar(255) NOT NULL DEFAULT '',
  `client_name` varchar(255) NOT NULL DEFAULT '',
  `source_application` varchar(255) DEFAULT '',
  `target_application` varchar(255) NOT NULL,
  `client_context` varchar(255) DEFAULT NULL,
  `created_by` varchar(255) DEFAULT NULL,
  `created_on` varchar(255) DEFAULT NULL,
  `remarks` text,
  PRIMARY KEY (`user_id`,`client_name`,`target_application`)
) ;
