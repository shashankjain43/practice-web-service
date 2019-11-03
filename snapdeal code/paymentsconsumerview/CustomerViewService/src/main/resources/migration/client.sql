CREATE TABLE client_backup_JUN16 AS (SELECT * FROM client);

DROP TABLE IF EXISTS client;

CREATE TABLE `client` (
  `id` varchar(128) NOT NULL,
  `client_name` varchar(128) NOT NULL UNIQUE,
  `client_key` varchar(128) NOT NULL,
  `client_status` varchar(128) NOT NULL,
  `created_time` datetime(3) NOT NULL,
  `updated_time` datetime(3) NOT NULL,
  PRIMARY KEY (`id`)
);