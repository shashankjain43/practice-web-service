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
);
