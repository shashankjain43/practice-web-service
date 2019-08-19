CREATE TABLE `task_execution` (
  `task_id` varchar(250) NOT NULL,
  `run_no` int(10) unsigned NOT NULL DEFAULT '1',
  `start_time` datetime(3) DEFAULT NULL,
  `complete_time` datetime(3) DEFAULT NULL,
  `completion_status` enum('SUCCESS','FAILURE','RETRY') DEFAULT NULL,
  `completion_log` blob,
  `created_time` datetime(3) NOT NULL,
  `updated_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  UNIQUE KEY `UK_id_run` (`task_id`,`run_no`),
  CONSTRAINT `FK_taskId` FOREIGN KEY (`task_id`) REFERENCES `task` (`id`)
)
