


DROP TABLE IF EXISTS `global_templates`;

CREATE TABLE `global_templates` (
  `merchant_name` varchar(255) DEFAULT NULL,
  `template_name` varchar(255) DEFAULT NULL,
  `template_url` varchar(2000) DEFAULT NULL,
  `template_type` varchar(100) DEFAULT NULL,
  `template_data` varchar(500) DEFAULT NULL
)
