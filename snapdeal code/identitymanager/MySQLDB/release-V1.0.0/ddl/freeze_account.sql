DROP TABLE IF EXISTS `freeze_account`;
CREATE TABLE `freeze_account` (
  `user_id`  varchar(127) NOT NULL ,
  `otp_type` varchar(25) NOT NULL,
  `expiry_time` datetime NOT NULL,
  `freeze_reason` varchar(256) NOT NULL,
  `is_deleted` enum('true','false') NOT NULL DEFAULT 'false');
