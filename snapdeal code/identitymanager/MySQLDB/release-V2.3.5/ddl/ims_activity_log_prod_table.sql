CREATE TABLE `activity_log_01_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


CREATE TABLE `activity_log_02_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `activity_log_03_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `activity_log_04_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


CREATE TABLE `activity_log_05_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `activity_log_06_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;


CREATE TABLE `activity_log_07_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `activity_log_08_2016` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;

CREATE TABLE `activity_log_12_2015` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `activity_type` varchar(128) DEFAULT NULL,
  `activity_subtype` varchar(128) DEFAULT NULL,
  `status` enum('SUCCESS','FAILURE') NOT NULL DEFAULT 'FAILURE',
  `ip_address` varchar(128) DEFAULT NULL,
  `mac_address` varchar(128) DEFAULT NULL,
  `client_id` varchar(128) DEFAULT NULL,
  `entity_id` varchar(128) DEFAULT NULL,
  `created_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `server_ip_address` varchar(128) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `entityId` (`entity_id`)
) ENGINE=InnoDB AUTO_INCREMENT=18 DEFAULT CHARSET=utf8;



