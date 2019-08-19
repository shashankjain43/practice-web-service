/* New email templates for ums notifications */
UPDATE `ums`.`email_template` 
SET `subject_template` = '$subject' ,
`to` = 'debopam.basu@snapdeal.com,aditi.malhotra@snapdeal.com,ums-qa@snapdeal.com'
WHERE `name` = 'UMSCheckpointBreachNotification';

DELETE FROM `ums`.`email_template`
WHERE `name` = 'externalSystemAvailabilityNotification';

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`, `to`, `reply_to`, `email_channel_id`, `enabled`) VALUES ('techBreachNotification', '$subject', '$msg', 'Snapdeal <noreply@snapdeals.co.in>', 'tech.ums@snapdeal.com,ums-qa@snapdeal.com,prateek.kukreja@snapdeal.com,pankaj.bajeli@snapdeal.com', 'noreply@snapdeal.com', 2, 1);

/* SDCash back program configuration */
CREATE TABLE `ums`.`sdCashBack_program_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subsidiary_name` varchar(45) DEFAULT NULL,
  `s3_access_id` varchar(45) DEFAULT NULL,
  `s3_secret_key` varchar(45) DEFAULT NULL,
  `s3_bucket_name` varchar(45) DEFAULT NULL,
  `s3_bucket_dir_name` varchar(45) DEFAULT NULL,
  `is_enabled` tinyint(4) NOT NULL,
  `activity_id` int(11) NOT NULL,
  `use_file_amount` tinyint(4) NOT NULL,
  `default_sdCash` int(11) DEFAULT NULL,
  `last_processed` datetime NULL,
  `sdcash_email_template` varchar(45) NOT NULL,
  `createUser_email_template` varchar(45) NOT NULL,
  `created` datetime NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP  ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

INSERT INTO `ums`.`sdCashBack_program_config` (`subsidiary_name`, `s3_access_id`, `s3_secret_key`, `s3_bucket_name`, `s3_bucket_dir_name`, `is_enabled`, `activity_id`,`use_file_amount` ,`default_sdCash`, `sdcash_email_template`,`createUser_email_template`,`created`) VALUES ('Freecharge_SDCashBack_program', 'AKIAI6OFSUIFQSIKFRIQ', 'nxx3ASEVKzI1ycUcsXQ2cuLPASTcROGLgkrzRzjk', 'sdl-ums', 'new/', 1, 18,1, 1, 'SubsidiaryCashBulkUploadEmail','accountCreationEmail',NOW());


/* insert template for accountCreationEmail */

INSERT INTO `ums`.`email_template`
(`name`,
`subject_template`,
`body_template`,
`from`,
`reply_to`,
`email_channel_id`,
`enabled`)
VALUES
('accountCreationEmail',
'Email notification for User account creation',
'$contentPath, $contextPath, $loginID, $password',
'Snapdeal<noreply@snapdeals.co.in>',
'noreply@snapdeal.com',
'2',
'1');


/* insert template for SubsidiaryCashBulkUploadEmail */

INSERT INTO `ums`.`email_template`
(`name`,
`subject_template`,
`body_template`,
`from`,
`reply_to`,
`email_channel_id`,
`enabled`)
VALUES
('SubsidiaryCashBulkUploadEmail',
'Email notification for SD cash credited in User Account',
'${sdCashBulkEmailRequest.sdCashAmount} , ${sdCashBulkEmailRequest.expiryDays} days.... , Registered - ${sdCashBulkEmailRequest.registered}',
'Snapdeal<noreply@snapdeals.co.in>',
'noreply@snapdeal.com',
'2',
'1');

/* if template sdWalletEmail has <noreply@Snapdeal.com> for "from" column, then make it to <noreply@snapdeals.co.in> */

UPDATE `ums`.`email_template` SET `from` = 'Snapdeal<noreply@snapdeals.co.in>' WHERE `name` = 'sdWalletEmail';

