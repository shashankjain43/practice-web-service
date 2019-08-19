USE ums;


CREATE TABLE `loyalty_programs` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `isDeleted` tinyint(1) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;


INSERT INTO `ums`.`loyalty_programs`
(`id`,
`isDeleted`,
`name`)
VALUES
(1,
0,
'SNAPBOX');





CREATE TABLE `loyalty_program_status` (
  `id` int(10) unsigned NOT NULL,
  `name` varchar(250) DEFAULT NULL,
  `isDeleted` tinyint(1) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  UNIQUE KEY `name` (`name`),
  UNIQUE KEY `name_2` (`name`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;



INSERT INTO `ums`.`loyalty_program_status`
(`id`,
`name`,
`isDeleted`)
VALUES
(0,'INELIGIBLE' ,0);

INSERT INTO `ums`.`loyalty_program_status`
(`id`,
`name`,
`isDeleted`)
VALUES
(1,'ACTIVE' ,0);

INSERT INTO `ums`.`loyalty_program_status`
(`id`,
`name`,
`isDeleted`)
VALUES
(2,'ELIGIBLE' ,0);

















CREATE TABLE `loyalty_user_details` (
  `id` int(10) unsigned NOT NULL DEFAULT '0',
  `email` varchar(255) DEFAULT NULL,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `verification_code` varchar(255) DEFAULT NULL,
  `loyalty_program` int(10) unsigned DEFAULT NULL,
  `loyalty_status` int(10) unsigned DEFAULT NULL,
  `user_id` int(10) unsigned DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_loyalty_user_details_1_idx` (`user_id`),
  KEY `fk_loyalty_user_details_2_idx` (`loyalty_program`),
  KEY `fk_loyalty_program_statusID_idx` (`loyalty_status`),
  CONSTRAINT `fk_loyalty_programID` FOREIGN KEY (`loyalty_program`) REFERENCES `loyalty_programs` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loyalty_statusID` FOREIGN KEY (`loyalty_status`) REFERENCES `loyalty_program_status` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION,
  CONSTRAINT `fk_loyalty_userID` FOREIGN KEY (`user_id`) REFERENCES `user` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB DEFAULT CHARSET=utf8;




CREATE TABLE `loyalty_user_history` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `email` varchar(255) DEFAULT NULL,
  `lastUpdated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `previous_status` varchar(45) DEFAULT NULL,
  `updated_status` varchar(45) DEFAULT NULL,
  `user_id` int(10) DEFAULT NULL,
  `loyalty_program` varchar(45) DEFAULT 'NULLL',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;




CREATE TABLE `job_sheet` (
  `id` int(20) unsigned NOT NULL AUTO_INCREMENT,
  `context_id` varchar(255) DEFAULT NULL,
  `scheduled_on` datetime NOT NULL,
  `job_type` varchar(45) DEFAULT NULL,
  `retry_attempts` int(10) DEFAULT NULL,
  `last_actioned_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=97 DEFAULT CHARSET=utf8;




