CREATE TABLE `whitelist_apis` (
  `client_id` varchar(128) NOT NULL,
  `ims_api_id` int(11) NOT NULL,
  `is_allowed` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`client_id`,`ims_api_id`)
);
