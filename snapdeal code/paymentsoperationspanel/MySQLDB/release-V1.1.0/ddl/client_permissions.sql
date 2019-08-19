

DROP TABLE IF EXISTS `client_permissions`;


CREATE TABLE `client_permissions` (
  `email_id` varchar(100) NOT NULL DEFAULT '',
  `merchant_name` varchar(50) NOT NULL DEFAULT '',
  `corp_id` varchar(255) NOT NULL DEFAULT  '',
  `instrument_name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`email_id`,`merchant_name`,`corp_id`)
)
