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
);
