


CREATE TABLE `ums`.`loyalty_uploads` (
  `id` int(10) NOT NULL AUTO_INCREMENT,
  `file_name` varchar(100) DEFAULT NULL,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `invalid_email_count` int(10) DEFAULT NULL,
  `valid_email_count` int(10) DEFAULT NULL,
  `existent_email_count` int(10) DEFAULT NULL,
  `raw_entries_count` int(10) DEFAULT NULL,
  `file_content` mediumblob,
  `status` varchar(20) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=178 DEFAULT CHARSET=utf8;


alter table `ums`.`loyalty_user_details` change id id int(10) UNSIGNED auto_increment;

INSERT INTO `ums`.`access_control`
(
`pattern`,
`roles`,
`feature`,
`link`,
`created`)
VALUES
(
"/admin/loyalty/",
"admin,SUPER,product",
"Snapbox Admin Panel",
"/admin/loyalty/uploadSnapBoxEligibilitySheet",
"2014-05-20 11:45:00");



ALTER TABLE `ums`.`loyalty_user_details` ADD UNIQUE KEY `email_loyaltyProg_unique` (email, loyalty_program);`

CREATE INDEX email_loyaltyProg_index ON `ums`.`loyalty_user_details` (email, loyalty_program);
