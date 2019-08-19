CREATE TABLE `activity_log_06_2015` (
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
  PRIMARY KEY (`id`)
);
      

CREATE TABLE `activity_log_07_2015` (
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
  PRIMARY KEY (`id`)
);
      
CREATE TABLE `activity_log_08_2015` (
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
  PRIMARY KEY (`id`)
);

CREATE TABLE `activity_log_09_2015` (
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
  PRIMARY KEY (`id`)
);

CREATE TABLE `activity_log_10_2015` (
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
  PRIMARY KEY (`id`)
);

CREATE TABLE `activity_log_11_2015` (
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
  PRIMARY KEY (`id`)
);

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
  PRIMARY KEY (`id`)
);