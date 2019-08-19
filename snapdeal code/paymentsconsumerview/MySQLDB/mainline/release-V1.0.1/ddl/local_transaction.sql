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
) ENGINE=InnoDB DEFAULT CHARSET=latin1;
