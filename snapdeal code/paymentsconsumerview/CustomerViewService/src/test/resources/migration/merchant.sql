CREATE DATABASE  IF NOT EXISTS `merchant_view`;
USE `merchant_view`;

CREATE TABLE `global_latest_child_map` (
  `freecharge_txn_id` varchar(255) DEFAULT NULL,
  `child_freecharge_txn_id` varchar(255) NOT NULL DEFAULT '',
  `txn_type` varchar(64) DEFAULT NULL,
  `child_txn_type` varchar(16) NOT NULL DEFAULT '',
  PRIMARY KEY (`child_freecharge_txn_id`,`child_txn_type`)
) ;


CREATE TABLE `txn_details` (
  `txn_id` varchar(255) NOT NULL DEFAULT '',
  `freecharge_txn_id` varchar(255) NOT NULL,
  `merchant_txn_type` varchar(128) NOT NULL,
  `txn_type` varchar(64) NOT NULL,
  `merchant_txn_id` varchar(255) DEFAULT NULL,
  `settlement_id` varchar(255) DEFAULT NULL,
  `settlement_date` datetime(3) DEFAULT NULL,
  `txn_ref` varchar(255) DEFAULT NULL,
  `txn_ref_type` varchar(255) DEFAULT NULL,
  `merchant_fee` decimal(8,2) DEFAULT NULL,
  `txn_amount` decimal(16,2) NOT NULL,
  `service_tax` decimal(8,2) DEFAULT NULL,
  `swach_bharat_cess` decimal(8,2) DEFAULT NULL,
  `amount_payable` decimal(16,2) DEFAULT NULL,
  `net_deduction` decimal(8,2) DEFAULT NULL,
  `merchant_Id` varchar(255) NOT NULL,
  `merchant_name` varchar(255) DEFAULT NULL,
  `customer_id﻿` varchar(255) DEFAULT NULL,
  `customer_name` varchar(128) DEFAULT NULL,
  `bts_txn_ref` varchar(128),
  `store_id` varchar(128) DEFAULT NULL,
  `store_name` varchar(128) DEFAULT NULL,
  `product_id` varchar(128) DEFAULT NULL,
  `terminal_id` varchar(32) DEFAULT NULL,
  `platform` varchar(128) DEFAULT NULL,
  `os_version` varchar(64) DEFAULT NULL,
  `custmer_ip` varchar(32) DEFAULT NULL,
  `location` varchar(128) DEFAULT NULL,
  `shipping_city` varchar(32) DEFAULT NULL,
  `source` varchar(16) NOT NULL,
  `tsm_time_stamp` datetime(3) NOT NULL,
  `txn_date` datetime(3) DEFAULT NULL,
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`txn_id`)
) ;


CREATE TABLE `txn_state_details` (
  `txn_id` varchar(255) NOT NULL,
  `txn_state` varchar(128) NOT NULL,
  `tsm_time_stamp` datetime(3) NOT NULL,
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  UNIQUE KEY (`txn_state`,`txn_id`,`tsm_time_stamp`)
) ;


insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be480','test_fc_txn_id','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id','test_settle_id',
10.00,'test_merchant_id','test_merchant_name','test_cust_id','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be480','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be480','SUCCESS','2015-12-16 16:38:34.480');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be480','TO_BE_SETTLED','2015-12-16 16:38:34.490');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be480','SETTLED','2015-12-16 16:38:34.495');


insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be490','test_fc_txn_id10','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id10','test_settle_id',
10.00,'test_merchant_id','test_merchant_name','test_cust_id10','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be490','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be490','SUCCESS','2015-12-16 16:38:34.480');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be490','TO_BE_SETTLED','2015-12-16 16:38:34.490');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be490','SETTLED','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be491','test_fc_txn_id11','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id11','test_settle_id',
101.00,'test_merchant_id','test_merchant_name','test_cust_id11','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be491','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be491','SUCCESS','2015-12-16 16:38:34.480');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be491','TO_BE_SETTLED','2015-12-16 16:38:34.490');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be491','SETTLED','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be481','test_fc_txn_id1','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id1','test_settle_id',
10.00,'test_merchant_id','test_merchant_name','test_cust_id1','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be481','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be481','SUCCESS','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be482','test_fc_txn_id2','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id2','test_settle_id',
10.00,'test_merchant_id','test_merchant_name','test_cust_id2','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be482','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be482','SUCCESS','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be483','test_fc_txn_id3','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id3','test_settle_id',
1.00,'test_merchant_id','test_merchant_name','test_cust_id3','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be483','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be483','SUCCESS','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be484','test_fc_txn_id4','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id4','test_settle_id',
20.00,'test_merchant_id','test_merchant_name','test_cust_id4','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be484','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be484','SUCCESS','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be485','test_fc_txn_id5','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id5','test_settle_id',
20.00,'test_merchant_id','test_merchant_name','test_cust_id5','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be485','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be485','SUCCESS','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be486','test_fc_txn_id6','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id6','test_settle_id',
20.00,'test_merchant_id','test_merchant_name','test_cust_id6','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be486','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be486','FAILED','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be487','test_fc_txn_id7','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id7','test_settle_id',
20.00,'test_merchant_id','test_merchant_name','test_cust_id7','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be487','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be487','FAILED','2015-12-16 16:38:34.495');

insert into txn_details (txn_id,freecharge_txn_id,merchant_txn_type,txn_type,merchant_txn_id,settlement_id,
txn_amount,merchant_Id,merchant_name,customer_id﻿,store_id,product_id,terminal_id,source,tsm_time_stamp,txn_date)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be488','test_fc_txn_id8','DEBIT','OPS_WALLET_DEBIT_VOID','test_mcnt_txn_id8','test_settle_id',
20.00,'test_merchant_id','test_merchant_name','test_cust_id8','test_store_id','test_product_id','test_terminal_id','OPS',
'2015-12-16 16:38:34.495','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be488','PENDING','2015-12-16 16:38:33.527');
insert into txn_state_details (txn_id,txn_state,tsm_time_stamp)
VALUES ('test_000709d5-07a6-41a1-934d-c535b39be488','FAILED','2015-12-16 16:38:34.495');




