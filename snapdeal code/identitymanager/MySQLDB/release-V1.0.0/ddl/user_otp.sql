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
  PRIMARY KEY (`otp_id`));
