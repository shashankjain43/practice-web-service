DROP TABLE IF EXISTS `lock_user`;
CREATE TABLE `lock_user` (
  `user_id` varchar(127) NOT NULL,
  `expiry_time` datetime NOT NULL,
  `login_attempts` tinyint(4) NOT NULL,
  `status` enum('LOCKED','UNLOCKED') NOT NULL,
  `created_time` datetime DEFAULT NULL,
  PRIMARY KEY (`user_id`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

