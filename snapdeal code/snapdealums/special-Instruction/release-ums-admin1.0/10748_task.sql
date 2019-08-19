use ums;
 CREATE TABLE `ums_task` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `name` varchar(255) NOT NULL,
  `impl_class` varchar(255) NOT NULL,
  `cron_expression` varchar(128) NOT NULL,
  `clustered` tinyint(1) DEFAULT NULL,
  `concurrent` tinyint(1) DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `enabled` tinyint(1) DEFAULT NULL,
  `email_template` varchar(50) DEFAULT NULL,
  `notification_email` varchar(200) DEFAULT NULL,
  `last_exec_time` datetime DEFAULT NULL,
  `last_exec_result` text,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=17 DEFAULT CHARSET=utf8;

CREATE TABLE `task_parameter` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `task_id` int(10) unsigned NOT NULL,
  `name` varchar(100) NOT NULL,
  `value` varchar(500) DEFAULT NULL,
  `created` datetime NOT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=110 DEFAULT CHARSET=utf8 ;

insert into ums_task values(null,'SDStatementMailerTask','com.snapdeal.ums.admin.jobs.SDStatementMailerTask','0 0 0 1 * ?',1,0,now(),now(),0,'smsTaskResult','naveen.upadhyay@snapdeal.com',null,null);

 CREATE TABLE `ums_property` (
  `name` varchar(48) NOT NULL,
  `value` varchar(256) NOT NULL,
  PRIMARY KEY (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1

