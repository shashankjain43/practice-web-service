CREATE TABLE `user_neft_details` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `is_active` tinyint(1) DEFAULT NULL,
  `email` varchar(100) NOT NULL,
  `ifsc_code` varchar(30) NOT NULL,
  `branch` varchar(60) NOT NULL,
  `accHolderName` varchar(60) NOT NULL,
  `bank_name` varchar(60) NOT NULL,
  `account_no` varchar(100) NOT NULL,
  `last_updated` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
  `last_verified` datetime NOT NULL,
  `date_created` datetime NOT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `unique_user_neft_dtls` (`email`,`ifsc_code`,`branch`,`bank_name`,`account_no`),
  KEY `email_loyaltyProg_index` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=1 DEFAULT CHARSET=utf8;

CREATE INDEX email_loyaltyProg_index ON `ums`.`user_neft_details` (`email`);




INSERT INTO `ums`.`sms_template`
(
`name`,
`body_template`,
`sms_channel_id`,
`enabled`,
`dnd_scrubbing_on`)
VALUES
(
'NEFTUpdateSms',
'AccNum: $neftDetails.maskedAccountNo, Acc holder Name: $neftDetails.accHolderName, Bank: $neftDetails.bankName, Branch: $neftDetails.branchName, IFSC: $neftDetails.ifscCode ',
2,
1,
0);













INSERT INTO `ums`.`email_template` (`name`,`subject_template`,`body_template`,`from`,`to`,`cc`,`bcc`,`reply_to`,`email_channel_id`,`email_type`,`enabled`,`created`) VALUES ('userNEFTDetailsUpdated','Your NEFT details have been udpated!','','Snapdeal <noreply@snapdeals.co.in>','','','','Snapdeal <noreply@snapdeals.co.in>',4,null,1,'2014-06-24 19:07:30');




update `ums`.`email_template`  set `body_template` = "<table style=\"border: 1px dotted #ababab;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"640\">
<tbody>

<tr><td>
$userNEFTDetails.maskedAccountNo
$userNEFTDetails.bankName
$userNEFTDetails.branchName
$userNEFTDetails.ifscCode
$userNEFTDetails.name


       <table width=\"100%\">
      <tr>
<td style=\"padding-left:8px;\"><img src=\"images/add.jpg\" height=\"114\" width=\"643\" /></td>
</tr>

</tbody>
</table>" where `name` = "userNEFTDetailsUpdated";	
