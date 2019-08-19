use request_view;
CREATE TABLE `action_details` (
  `id` varchar(255) NOT NULL,
  `action_id` varchar(255) CHARACTER SET latin1 NOT NULL,
  `action_type` varchar(45) CHARACTER SET latin1 NOT NULL,
  `action_state` varchar(45) NOT NULL,
  `action_view_state` varchar(45) NOT NULL,
  `action_taken` varchar(45) DEFAULT NULL,
  `valid_action_commands` text,
  `user_id` varchar(255) CHARACTER SET latin1 NOT NULL,
  `user_type` varchar(45) DEFAULT NULL,
  `other_party_id` varchar(255),
  `reference_id` varchar(255) CHARACTER SET latin1 ,
  `reference_type` varchar(45) CHARACTER SET latin1 ,
  `action_expiry_time` datetime(3) DEFAULT NULL,
  `metadata` text,
  `action_initiation_timestamp` datetime(3) NOT NULL,
  `action_last_update_timestamp` datetime(3) NOT NULL,
  `action_next_schedule_timestamp` datetime(3) DEFAULT NULL,
  `created_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3),
  `updated_on` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`),
  UNIQUE KEY `txn_id` (`action_id`,`action_type`,`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8 ROW_FORMAT=DYNAMIC;


CREATE INDEX start_date_with_user_and_type_index ON action_details (action_initiation_timestamp, user_id,action_type,reference_type) USING BTREE;

