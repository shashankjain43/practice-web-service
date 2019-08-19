CREATE TABLE `task` (
  `id` varchar(250) NOT NULL,
  `task_status` enum('OPEN','CLOSED','BLOCKED') NOT NULL,
  `task_meta_data` blob,
  `original_schedule_time` datetime(3) NOT NULL,
  `current_schedule_time` datetime(3) NOT NULL,
  `retry_limit` int(10) NOT NULL DEFAULT '0',
  `created_time` datetime(3) NOT NULL,
  `updated_time` datetime(3) NOT NULL DEFAULT CURRENT_TIMESTAMP(3) ON UPDATE CURRENT_TIMESTAMP(3),
  PRIMARY KEY (`id`)
)
