CREATE TABLE `task_range` (
  `task_id` int(11) NOT NULL AUTO_INCREMENT,
  `task_range` varchar(100) NOT NULL,
  `job_id` varchar(100) NOT NULL,
  `task_fetched` tinyint(4) DEFAULT '0',
  `created_date` timestamp NULL DEFAULT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `success_count` int(11) DEFAULT '0',
  `failure_count` int(11) DEFAULT '0',
  `total_count` int(11) DEFAULT '0',
  `error_messsage` varchar(200) DEFAULT NULL,
  PRIMARY KEY (`task_id`)
) ENGINE=InnoDB AUTO_INCREMENT=31 DEFAULT CHARSET=latin1;

