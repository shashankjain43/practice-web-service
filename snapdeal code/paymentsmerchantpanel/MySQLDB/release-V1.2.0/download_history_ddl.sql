CREATE TABLE `filter` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filter_metadata` varchar(1024) NOT NULL,
  `filter_hash` varchar(128) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=latin1;

CREATE TABLE `download_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `filter_id` int(11) NOT NULL,
  `download_status` varchar(32) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `user_id` varchar(45) NOT NULL,
  `viewed` tinyint(1) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  KEY `fk_download_history_1_idx` (`filter_id`),
  CONSTRAINT `fk_download_history_1` FOREIGN KEY (`filter_id`) REFERENCES `filter` (`id`) ON DELETE NO ACTION ON UPDATE NO ACTION
) ENGINE=InnoDB AUTO_INCREMENT=10 DEFAULT CHARSET=latin1;
