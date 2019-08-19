
DROP TABLE IF EXISTS `file_templates`;

CREATE TABLE `file_templates` (
  `template_id` varchar(255) NOT NULL DEFAULT '',
  `template_name` varchar(255) DEFAULT NULL,
  `template_params` varchar(1000) DEFAULT NULL,
  `template_values` varchar(1000) DEFAULT NULL,
  `user_id` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`template_id`)
)
