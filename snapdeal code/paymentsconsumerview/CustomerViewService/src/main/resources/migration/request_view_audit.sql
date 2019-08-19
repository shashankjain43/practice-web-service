DROP TABLE IF EXISTS `request_view_audit` ;
CREATE TABLE `request_view_audit` (
  `freecharge_txn_id` varchar(255) NOT NULL,
  `txn_type` varchar(32) NOT NULL,
  `source_user_id` varchar(255) NOT NULL,
  `exception_type` varchar(45) NOT NULL,
  `exception_msg` text NOT NULL,
  `exception_code` varchar(32) NOT NULL,
  `retry_count` int(11) NOT NULL DEFAULT '0',
  `status` Enum('SUBMITTED','SUCCESS','PENDING','BLOCKED') NOT NULL DEFAULT 'PENDING',
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  `tsm_time_stamp` datetime(3) NOT NULL ,
  PRIMARY KEY (`txn_type`,`freecharge_txn_id`)
);

