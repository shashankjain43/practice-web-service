CREATE DATABASE  IF NOT EXISTS `merchant_view` /*!40100 DEFAULT CHARACTER SET latin1 */;
USE `merchant_view`;


DROP TABLE IF EXISTS `global_latest_child_map`;
CREATE TABLE `global_latest_child_map` (
  `freecharge_txn_id` varchar(255) NOT NULL,
  `child_freecharge_txn_id` varchar(255) NOT NULL,
  `txn_type` varchar(64) NOT NULL,
  `child_txn_type` varchar(16) NOT NULL ,
  PRIMARY KEY (`child_freecharge_txn_id`,`child_txn_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `txn_details`;
CREATE TABLE `txn_details` (
  `txn_id` varchar(255) NOT NULL,
  `freecharge_txn_id` varchar(255) NOT NULL,
  `merchant_txn_type` varchar(128) NOT NULL,
  `txn_type` varchar(64) NOT NULL,
  `merchant_txn_id` varchar(255) DEFAULT NULL,
  `settlement_id` varchar(255) DEFAULT NULL,
  `settlement_date` datetime(3) DEFAULT NULL,
  `txn_ref` varchar(255) NOT NULL,
  `txn_ref_type` varchar(255) NOT NULL,
  `merchant_fee` decimal(8,2),
  `txn_amount` decimal(16,2) NOT NULL,
  `service_tax` decimal(8,2) ,
  `swach_bharat_cess` decimal(8,2) ,
  `amount_payable` decimal(16,2),
  `net_deduction` decimal(8,2),
  `merchant_Id` varchar(255) NOT NULL,
  `merchant_name` varchar(255),
  `customer_name` varchar(128),
  `customer_id` varchar(255),
  `bts_txn_ref` varchar(128),
  `store_id` varchar(128),
  `store_name` varchar(128),
  `product_id` varchar(128),
  `terminal_id` varchar(32),
  `platform` varchar(128),
  `os_version` varchar(64) ,
  `custmer_ip` varchar(32),
  `location` varchar(128) ,
  `shipping_city` varchar(32) ,
  `source` varchar(16) NOT NULL,
  `tsm_time_stamp` datetime(3) NOT NULL,
  `txn_date` datetime(3) DEFAULT NULL,
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`txn_id`),
  UNIQUE KEY (`freecharge_txn_id`,`txn_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `txn_state_details`;
CREATE TABLE `txn_state_details` (
  `txn_id` varchar(255) NOT NULL,
  `txn_state` varchar(128) NOT NULL,
  `tsm_time_stamp` datetime(3) NOT NULL,
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  UNIQUE KEY (`txn_state`,`txn_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;




CREATE INDEX merchant_index ON txn_details (merchant_Id) ;
CREATE INDEX merchant_txn_id_index ON txn_details (merchant_txn_id) ;
CREATE INDEX custmer_id_index ON txn_details (customer_id﻿) ;
CREATE INDEX txnid_txnstate_index ON txn_state_details (txn_id,txn_state) ;
CREATE INDEX merchant_txn_typ_index ON txn_details (merchant_txn_type) ;
CREATE INDEX merchant_txn_ref_index ON txn_details (txn_ref) ;
