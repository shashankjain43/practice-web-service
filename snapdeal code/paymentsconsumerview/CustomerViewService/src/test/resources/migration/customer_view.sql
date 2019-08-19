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
) ;


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
) ;


