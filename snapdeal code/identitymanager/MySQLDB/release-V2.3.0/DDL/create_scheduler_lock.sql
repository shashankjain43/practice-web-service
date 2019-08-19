CREATE TABLE `schedular_lock` (
  `job_id` int(11) NOT NULL,
  `job_name` varchar(256) NOT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `created_date` timestamp NULL DEFAULT NULL,
  PRIMARY KEY (`job_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

