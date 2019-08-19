

CREATE TABLE `sd_cash_file_upload_history` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `uploader` varchar(100) NOT NULL,
  `email_id_count` int(10) NOT NULL,
  `fail_count` int(10) NOT NULL,
  `file_name` varchar(100) NOT NULL,
  `file_content` mediumblob,
  `activity_type` varchar(45) DEFAULT NULL,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=60 DEFAULT CHARSET=utf8;


