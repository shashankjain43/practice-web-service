/* 
drop table upgrade_history_tmp;
drop table upgrade_tmp;
*/


/* Create upgrade_tmp */
create table upgrade_tmp like upgrade;
/*
CREATE TABLE `upgrade_tmp` (
  `id` bigint(20) NOT NULL AUTO_INCREMENT,
  `email` varchar(256) NOT NULL,
  `initial_state` varchar(64) DEFAULT NULL,
  `current_state` varchar(64) DEFAULT NULL,
  `upgrade_status` varchar(64) DEFAULT NULL,
  `user_id` varchar(128) DEFAULT NULL,
  `fc_id` bigint(20) DEFAULT NULL,
  `sd_id` int(11) DEFAULT NULL,
  `dont_upgrade` tinyint(1) DEFAULT NULL,
  `created_date` datetime NOT NULL,
  `updated_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `upgrade_channel` enum('MOBILE_WAP','WEB','CRM','OMS','OMS_BUYFLOW','BRAND_STORE','SELECT','IOS_APP','WIN_APP','ANDROID_APP','OTHERS') NOT NULL DEFAULT 'OTHERS',
  `upgrade_source` enum('SIGN_IN','SIGN_UP','ORDER_FLOW','THANK_YOU','MY_ACCOUNT','RETURN_ORDER','OTHERS','ONECHECK_SOCIAL_SIGNUP') NOT NULL DEFAULT 'OTHERS',
  PRIMARY KEY (`id`),
  UNIQUE KEY `email_UNIQUE` (`email`)
);
*/
/* Create upgrade_history_tmp */
create table upgrade_history_tmp like upgrade_history;
/*CREATE TABLE `upgrade_history_tmp` (
  `upgrade_id` bigint(20) NOT NULL,
  `upgrade_status` varchar(64) DEFAULT NULL,
  `current_state` varchar(64) DEFAULT NULL,
  `current_date` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);
*/

delete from user where email like '%snapdeal.com%' || email like '%freecharge.com%' || email like '%freecharge.in%';

insert into upgrade_tmp select * from upgrade where email in (select email from user);

insert into upgrade_history_tmp select * from upgrade_history where 
  upgrade_id in (select id from upgrade_tmp);

RENAME TABLE upgrade TO tmp_table,
             upgrade_tmp TO upgrade,
             tmp_table TO upgrade_tmp;


RENAME TABLE upgrade_history TO tmp_table,
             upgrade_history_tmp TO upgrade_history,
             tmp_table TO upgrade_history_tmp;
