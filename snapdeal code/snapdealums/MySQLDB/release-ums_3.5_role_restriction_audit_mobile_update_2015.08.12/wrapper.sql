/* dropping the trigger for role restriction */
--drop trigger add_super;

/* audit table for capturing role grant/revoke activities through admin */
CREATE TABLE `admin_user_role_audit` (
  `id` bigint(18) unsigned NOT NULL AUTO_INCREMENT,
  `user_id` int(10) unsigned NOT NULL,
  `role` varchar(256) DEFAULT NULL,
  `action_taken_by` varchar(256) DEFAULT NULL,
  `action` varchar(30) DEFAULT NULL,
  `updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`)
);

/* batch job to updatemobile number of user from their addresses*/
INSERT INTO `ums`.`ums_task` (`name`, `impl_class`, `cron_expression`, `clustered`, `concurrent`, `created`, `enabled`, `email_template`, `notification_email`) VALUES ('AutoMobileUpdateTask', 'com.snapdeal.ums.admin.jobs.AutoMobileUpdateTask', '0 0 3 1/1 * ? *', '1', '0', now(), '1', 'AutoMobileUpdateTemplate', 'tech.ums@snapdeal.com');

INSERT INTO `ums`.`task_parameter` (`task_id`, `name`, `value`, `created`) VALUES ('19', 'createdlastndays', '1', now());
INSERT INTO `ums`.`task_parameter` (`task_id`, `name`, `value`, `created`) VALUES ('19', 'daysinterval', '1', now());

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`, `to`, `cc`, `bcc`, `reply_to`, `email_channel_id`, `enabled`, `created`, `trigger_id`) VALUES ('AutoMobileUpdateTemplate', 'AutoMobileUpdate Task', 'Task Result for task: $task.name<br/>Task status : $task.lastExecResult<br/><br/>UMS Release-Admin', 'Snapdeal Admin<prod.monitor@snapdeals.co.in>', 'jain.shashank@snapdeal.com,siddhartha.chhabra@snapdeal.com', 'newsletter@snapdeal.in', 'newsletter@snapdeal.in', 'newsletter@snapdeal.in', '4', '0', now(), '8141');
