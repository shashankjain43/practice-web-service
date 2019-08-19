DROP TABLE IF EXISTS `upgrade_history`;


CREATE TABLE `upgrade_history` (
  `upgrade_id` bigint(20) NOT NULL,
  `upgrade_status` varchar(64) DEFAULT NULL,
  `current_state` varchar(64) DEFAULT NULL,
  `current_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

