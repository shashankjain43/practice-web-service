
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
  `upgrade_channel` enum('MOBILE_WAP','WEB','CRM','OMS','OMS_BUYFLOW','BRAND_STORE','SELECT','IOS_APP','WIN_APP','ANDROID_APP','OTHERS') NOT NULL DEFAULT 'OTHERS',
  `upgrade_source` enum('SIGN_IN','SIGN_UP','ORDER_FLOW','THANK_YOU','OMS_BUYFLOW','MY_ACCOUNT','RETURN_ORDER','OTHERS') NOT NULL DEFAULT 'OTHERS',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=latin1;

