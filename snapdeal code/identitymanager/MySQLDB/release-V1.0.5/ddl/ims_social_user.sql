DROP TABLE IF EXISTS `social_user`;
CREATE TABLE `social_user` (
  `user_id` varchar(128) NOT NULL,
  `social_id` varchar(128) NOT NULL,
  `social_src` varchar(128) NOT NULL,
  `social_token` varchar(256) DEFAULT NULL,
  `secret` varchar(128) DEFAULT NULL,
  `expiry` datetime,
  `photo_url` varchar(256) DEFAULT NULL,
  `about_me` varchar(128) DEFAULT NULL,
  `created_time` datetime,
  `updated_time` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`user_id`,`social_id`,`social_src`),
  KEY `social_src` (`social_src`)
);
