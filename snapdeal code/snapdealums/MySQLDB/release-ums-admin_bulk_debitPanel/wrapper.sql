ALTER TABLE `ums`.`sd_cash_file_upload_history` ADD COLUMN `mode` VARCHAR(45) NULL DEFAULT NULL  ;




INSERT INTO `ums`.`access_control`
(
`pattern`,
`roles`,
`feature`,
`link`,`created`
)
VALUES
(
"/admin/sdCashDebit",
"sdcashbulkdebit",
"SD Cash Bulk Debit Panel",
"/admin/sdCashDebit/sdCashBulkDebitUpload", now()
);


INSERT INTO `ums`.`role`
(
`code`,
`description`)
VALUES
(
'sdcashbulkdebit','Debit SDCash in Bulk'
);


INSERT INTO `ums`.`email_template` (`name`,`subject_template`,`body_template`,`from`,`to`,`cc`,`bcc`,`reply_to`,`email_channel_id`,`email_type`,`enabled`,`created`) VALUES ('sdCashBulkDebitResponseToUploader','Debited SDCash to user','<table style=\"border: 1px dotted #ababab;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"640\">
<tbody>
<tr><td>
Successfully debited the sdCash except the following email Ids (Unsuccessful debit).</td></tr>
<tr><td>
${invalidEmailMap}
</td></tr>
</tbody>
</table>','Snapdeal <noreply@snapdeals.co.in>','','','','Snapdeal <noreply@snapdeals.co.in>',4,null,1,now());



