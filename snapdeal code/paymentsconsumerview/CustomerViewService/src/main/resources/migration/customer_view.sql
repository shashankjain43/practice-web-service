CREATE DATABASE  IF NOT EXISTS `customer_view2` ;
USE `customer_view2`;

DROP TABLE IF EXISTS `global_transaction`;
CREATE TABLE `global_transaction` (
  `id` varchar(127) NOT NULL,
  `user_id` varchar(255) NOT NULL,
  `global_txn_id` varchar(255) NOT NULL,
  `merchant_name` varchar(45) NOT NULL,
  `merchant_order_id` varchar(45) DEFAULT NULL,
  `transaction_id` varchar(255) DEFAULT NULL,
  `tsm_created_time` datetime DEFAULT NULL,
  `total_transaction_amount` varchar(30) DEFAULT NULL,
  `order_details` varchar(512) DEFAULT NULL,
  `sub_order_details` varchar(512) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_on` datetime DEFAULT CURRENT_TIMESTAMP,
  `transaction_type` varchar(45) DEFAULT NULL,
  `transaction_status` varchar(45) DEFAULT NULL,
  `tsm_updated_time` datetime DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `global_transaction_IN1` (`global_txn_id`,`transaction_type`),
  KEY `user_id` (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;



DROP TABLE IF EXISTS `local_transaction`;
CREATE TABLE `local_transaction` (
  `global_transaction_id` varchar(255) NOT NULL,
  `local_transaction_id` varchar(255) DEFAULT NULL,
  `transaction_amount` varchar(30) DEFAULT NULL,
  `payment_mode` varchar(45) DEFAULT NULL,
  `tsm_created_date` datetime DEFAULT NULL,
  `transaction_status` varchar(45) DEFAULT NULL,
  `transaction_details` varchar(45) DEFAULT NULL,
  `created_on` datetime DEFAULT NULL,
  `updated_on` datetime DEFAULT CURRENT_TIMESTAMP,
  KEY `global_transaction_id` (`global_transaction_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1 ;


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

CREATE INDEX user_id_index ON global_transaction (user_id) ;
CREATE INDEX global_txn_id_index ON global_transaction (global_txn_id) ;
CREATE INDEX merchant_name_index ON global_transaction (merchant_name) ;
CREATE INDEX total_transaction_amount_index ON global_transaction (total_transaction_amount) ;
CREATE INDEX created_on_index ON global_transaction (created_on) ;

CREATE INDEX global_transaction_index ON local_transaction (global_transaction_id) ;
CREATE INDEX local_transaction_index ON local_transaction (local_transaction_id) ;

