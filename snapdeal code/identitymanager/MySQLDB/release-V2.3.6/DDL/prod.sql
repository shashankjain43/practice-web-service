use ims;

alter table user_otp add column otp_encrypted varchar(32) DEFAULT NULL,lock=none;

ALTER TABLE user_otp CHANGE COLUMN otp otp VARCHAR(6) NULL DEFAULT NULL ,lock=none ;

CREATE TABLE `client_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `client_id` varchar(128) NOT NULL,
  `field` varchar(128) NOT NULL,
  `old_value` varchar(4000) DEFAULT NULL,
  `new_value` varchar(4000) DEFAULT NULL,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

CREATE TABLE `configuration_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
`config_key` varchar(128) NOT NULL,
 `config_type` varchar(128) NOT NULL,
`old_config_value` blob,  
  `old_description` varchar(128) DEFAULT NULL,
`new_config_value` blob,  
  `new_description` varchar(128) DEFAULT NULL,
`is_deleted` varchar(128),  
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);


CREATE TABLE `ims_apis_history` (
  `sno` int(11) NOT NULL AUTO_INCREMENT,
  `id` int(11) NOT NULL,
  `old_alias` varchar(45) DEFAULT NULL,
  `old_api_method` varchar(45) NOT NULL,
  `old_api_uri` varchar(255) NOT NULL,
  `new_alias` varchar(45) DEFAULT NULL,
  `new_api_method` varchar(45) NOT NULL,
  `new_api_uri` varchar(255) NOT NULL,  
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`sno`)
);

CREATE TABLE `whitelist_apis_history` (
`id` int(11) NOT NULL AUTO_INCREMENT,  
`client_id` varchar(128) NOT NULL,
  `ims_api_id` int(11) NOT NULL,
  `old_is_allowed_value` varchar(128) NOT NULL,
  `new_is_allowed_value` varchar(128) NOT NULL,
   `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);


 


