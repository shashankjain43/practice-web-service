DROP TABLE IF EXISTS client_migrate;

CREATE TABLE `client_migrate` (
`uid` int(11) NOT NULL AUTO_INCREMENT,
`client_name` varchar(128) NOT NULL,
`client_key` varchar(255) NOT NULL,
`version_no`  int(11) NOT NULL,
`client_status` enum('ACTIVE','INACTIVE') NOT NULL DEFAULT 'ACTIVE',
`auth_mode` enum('ENABLED','DISABLED') NOT NULL DEFAULT 'DISABLED',
`authorized_api` LONGTEXT NOT NULL,
`bussiness_entity` LONGTEXT NOT NULL,
`created_time` datetime NOT NULL,
`created_by` varchar(128),
`updated_time` DATETIME(3) not null DEFAULT now(3) on update now(3),
`updated_by` varchar(128),
PRIMARY KEY (`uid`),
UNIQUE(client_name,version_no)
);
