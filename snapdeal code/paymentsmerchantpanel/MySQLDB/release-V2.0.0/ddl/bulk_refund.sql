CREATE TABLE `bulk_refund` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `merchant_id` varchar(255) NOT NULL ,
  `download_status` varchar(32) NOT NULL,
  `file_name` varchar(256) NOT NULL,
  `viewed` tinyint(1) NOT NULL,
  `created_on` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);
