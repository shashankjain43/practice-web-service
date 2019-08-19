create database request_view;
use request_view;

 CREATE TABLE `request_txn_details` (
  `txn_id` varchar(256) CHARACTER SET latin1 NOT NULL,
  `freecharge_txn_id` varchar(45) CHARACTER SET latin1 NOT NULL,
  `txn_type` varchar(45) CHARACTER SET latin1 NOT NULL,
  `p2p_txn_state` varchar(45)  NOT NULL,
  `tsm_txn_state` varchar(45)  NOT NULL,
  `request_view_txn_state` varchar(45)  NOT NULL,
  `txn_amount` varchar(45)  NOT NULL,
  `merchant_tag` varchar(45)  DEFAULT NULL,
  `source_party_type` varchar(45)  DEFAULT NULL,
  `dest_party_type` varchar(45)  DEFAULT NULL,
  `src_party_id` varchar(256) CHARACTER SET latin1 NOT NULL,
  `dest_party_id` varchar(256)  NOT NULL,
  `src_email_id` varchar(256)  DEFAULT NULL,
  `src_mobile_number` varchar(10)  DEFAULT NULL,
  `dest_email_id` varchar(256)  DEFAULT NULL,
  `dest_mobile_number` varchar(10)  DEFAULT NULL,
  `txn_date` datetime(3) NOT NULL,
  `tsm_time_stamp` datetime(3) NOT NULL,
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `src_party_tag` varchar(45)  DEFAULT NULL,
  `dest_party_tag` varchar(45)  DEFAULT NULL,
  `src_party_jabber_id` varchar(255)  DEFAULT NULL,
  `dest_party_jabber_id` varchar(255)  DEFAULT NULL,
  `meta_data` text ,
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`txn_id`),
  UNIQUE KEY `freecharge_txn_id` (`freecharge_txn_id`,`txn_type`,`src_party_id`),
  KEY `sourc_party_id_index` (`src_party_id`(255)),
  KEY `view_txn_type_index` (`request_view_txn_state`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC  ;
