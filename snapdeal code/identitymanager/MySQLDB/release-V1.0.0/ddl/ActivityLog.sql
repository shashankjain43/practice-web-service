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
);

