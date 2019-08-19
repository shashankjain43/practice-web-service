CREATE DATABASE ims;

use ims;

DROP TABLE IF EXISTS `activity_log`;

CREATE TABLE `activity_log` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=57 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `client`;
CREATE TABLE `client` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(128) NOT NULL,
  `client_name` varchar(128) NOT NULL,
  `merchant` enum('SNAPDEAL','FREECHARGE','ONECHECK') NOT NULL,
  `client_type` enum('USER_FACING','NON_USER_FACING') NOT NULL,
  `client_platform` enum('WEB','WAP','APP') NOT NULL DEFAULT 'WEB',
  `client_key` varchar(128) NOT NULL,
  `client_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
  `created_time` datetime NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `client_id_uk` (`client_id`),
  UNIQUE KEY `client_name_merchant_uk` (`client_name`,`merchant`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `configuration`;
CREATE TABLE `configuration` (
  `config_type` varchar(128) NOT NULL DEFAULT '' COMMENT 'Type of configuration, this will clasify the configuration.',
  `config_key` varchar(128) NOT NULL DEFAULT '' COMMENT 'Configuration key.',
  `config_value` blob COMMENT 'Configuration value.',
  `description` varchar(128) DEFAULT NULL COMMENT 'Configuration description information.',
  PRIMARY KEY (`config_key`,`config_type`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `freeze_account`;
CREATE TABLE `freeze_account` (
  `user_id` varchar(127) NOT NULL,
  `otp_type` varchar(25) NOT NULL,
  `expiry_time` datetime NOT NULL,
  `freeze_reason` varchar(256) NOT NULL,
  `is_deleted` enum('true','false') NOT NULL DEFAULT 'false'
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `lock_user`;
CREATE TABLE `lock_user` (
  `user_id` varchar(127) NOT NULL,
  `expiry_time` datetime NOT NULL,
  `login_attempts` tinyint(4) NOT NULL,
  `status` enum('LOCKED','UNLOCKED') NOT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `social_user`;
CREATE TABLE `social_user` (
  `user_id` varchar(128) NOT NULL,
  `social_id` varchar(128) NOT NULL,
  `social_src` varchar(128) NOT NULL,
  `social_token` varchar(256) DEFAULT NULL,
  `secret` varchar(128) DEFAULT NULL,
  `expiry` datetime DEFAULT NULL,
  `photo_url` varchar(256) DEFAULT NULL,
  `about_me` varchar(128) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`social_id`,`social_src`),
  KEY `social_src` (`social_src`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `task`;
CREATE TABLE `task` (
  `id` varchar(250) NOT NULL,
  `task_status` enum('OPEN','CLOSED') NOT NULL DEFAULT 'OPEN',
  `task_meta_data` blob,
  `original_schedule_time` datetime NOT NULL,
  `current_schedule_time` datetime NOT NULL,
  `created_time` datetime NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `task_execution`;
CREATE TABLE `task_execution` (
  `task_id` varchar(250) NOT NULL,
  `run_no` int(10) unsigned NOT NULL DEFAULT '1',
  `start_time` datetime DEFAULT NULL,
  `complete_time` datetime DEFAULT NULL,
  `completion_status` enum('SUCCESS','FAILURE','RETRY') DEFAULT NULL,
  `completion_log` blob,
  `created_time` datetime NOT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  UNIQUE KEY `UK_id_run` (`task_id`,`run_no`),
  CONSTRAINT `FK_taskId` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `upgrade`;
CREATE TABLE `upgrade` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(256) NOT NULL,
  `initial_state` varchar(64) DEFAULT NULL,
  `current_state` varchar(64) DEFAULT NULL,
  `upgrade_status` varchar(64) DEFAULT NULL,
  `user_id` varchar(128) DEFAULT NULL,
  `fc_id` bigint(20) DEFAULT NULL,
  `sd_id` int(11) DEFAULT NULL,
  `dont_upgrade` tinyint(1) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;



DROP TABLE IF EXISTS `upgrade_history`;
CREATE TABLE `upgrade_history` (
  `upgrade_id` bigint(20) NOT NULL,
  `upgrade_state` varchar(64) DEFAULT NULL,
  `current_status` varchar(64) DEFAULT NULL,
  `current_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


DROP TABLE IF EXISTS `user`;
CREATE TABLE `user` (
  `user_id` varchar(128) NOT NULL,
  `sd_user_id` int(11) DEFAULT NULL,
  `fc_user_id` int(11) DEFAULT NULL,
  `sd_fc_user_id` int(11) NOT NULL AUTO_INCREMENT,
  `originating_src` enum('FREECHARGE','SNAPDEAL','ONECHECK') NOT NULL,
  `is_enabled` tinyint(1) NOT NULL DEFAULT '1',
  `email` varchar(255) NOT NULL,
  `password` varchar(255) DEFAULT NULL,
  `is_user_set_password` tinyint(1) DEFAULT '0',
  `mobile_number` varchar(20) DEFAULT NULL,
  `status` enum('UNVERIFIED','GUEST','REGISTERED','TEMP') NOT NULL DEFAULT 'UNVERIFIED',
  `is_google_user` tinyint(1) NOT NULL DEFAULT '0',
  `is_facebook_user` tinyint(1) NOT NULL DEFAULT '0',
  `is_email_verified` tinyint(1) NOT NULL DEFAULT '0',
  `is_mobile_verified` tinyint(1) NOT NULL DEFAULT '0',
  `first_name` varchar(128) DEFAULT NULL,
  `last_name` varchar(128) DEFAULT NULL,
  `middle_name` varchar(128) DEFAULT NULL,
  `display_name` varchar(128) DEFAULT NULL,
  `gender` enum('m','f','o') DEFAULT NULL,
  `dob` date DEFAULT NULL,
  `language_pref` enum('HINDI','ENGLISH') NOT NULL DEFAULT 'ENGLISH',
  `purpose` varchar(255) DEFAULT NULL,
  `created_time` datetime DEFAULT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `sd_fc_user_id_UNIQUE` (`sd_fc_user_id`),
  KEY `mobile` (`mobile_number`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

DROP TABLE IF EXISTS `user_otp`;
CREATE TABLE `user_otp` (
  `user_id` varchar(127) NOT NULL,
  `mobile_number` varchar(10) DEFAULT NULL,
  `email` varchar(256) DEFAULT NULL,
  `otp` varchar(6) NOT NULL,
  `otp_id` varchar(127) NOT NULL,
  `invalid_attempts` tinyint(4) DEFAULT NULL,
  `resend_attempts` tinyint(4) DEFAULT NULL,
  `expiry_time` datetime NOT NULL,
  `otp_type` varchar(25) NOT NULL,
  `otp_status` enum('ACTIVE','VERIFIED','DELETED') NOT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` datetime DEFAULT NULL,
  `client_id` varchar(250) DEFAULT NULL,
  `token` varchar(250) DEFAULT NULL,
  PRIMARY KEY (`otp_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

