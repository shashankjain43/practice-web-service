

CREATE TABLE `ums`.`email_type` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `type_name` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
UNIQUE KEY `type_name_UNIQUE` (`type_name`)
);


CREATE TABLE `ums`.`email_type_template_mapping`(
  `id` int(11) unsigned NOT NULL AUTO_INCREMENT,
  `email_type_id` int(11) NOT NULL,
 `email_template_id` int(11) unsigned NOT NULL,
  PRIMARY KEY (`id`),
 FOREIGN KEY (`email_type_id`)  REFERENCES email_type(`id`), 
FOREIGN KEY (`email_template_id`) REFERENCES email_template(`id`)
);


INSERT INTO `ums`.`email_template`
(
`name`,
`subject_template`,
`body_template`,
`from`,
`to`,
`cc`,
`bcc`,
`reply_to`,
`email_channel_id`,
`email_type`,
`enabled`,
`created`,
`trigger_id`)
VALUES
(
'sdCashBulkCreditTemplate1',
'via $env: Credited SDCash to your account',
'',
'Snapdeal<noreply@snapdeals.co.in>',
'',
'',
'',
'noreply@snapdeal.in', 2,
'',
1,
CURRENT_TIMESTAMP,8141);



INSERT INTO `ums`.`email_template`
(
`name`,
`subject_template`,
`body_template`,
`from`,
`to`,
`cc`,
`bcc`,
`reply_to`,
`email_channel_id`,
`email_type`,
`enabled`,
`created`,
`trigger_id`)
VALUES
(
'sdCashBulkCreditTemplate2',
'via $env: Credited SDCash to your account',
'',
'Snapdeal<noreply@snapdeals.co.in>',
'',
'',
'',
'noreply@snapdeal.in', 2,
'',
1,
CURRENT_TIMESTAMP,8141
);

INSERT INTO `ums`.`email_template`
(
`name`,
`subject_template`,
`body_template`,
`from`,
`to`,
`cc`,
`bcc`,
`reply_to`,
`email_channel_id`,
`email_type`,
`enabled`,
`created`,
`trigger_id`
)
VALUES
(
'sdWalletEmail',
'via $env: Credited SDCash to your account',
'',
'Snapdeal<noreply@snapdeals.co.in>',
'<table style="border: 1px dotted #ababab;" align="center" border="0" cellpadding="0" cellspacing="0" width="640">
<tbody>

<tr><td>
       

$amount $expiryDays
        </td>
      </tr>



</tbody>
</table>',
'',
'',
'noreply@snapdeal.in', 2,
'',
1,
CURRENT_TIMESTAMP,8141
);


INSERT INTO `ums`.`email_template`
(
`name`,
`subject_template`,
`body_template`,
`from`,
`to`,
`cc`,
`bcc`,
`reply_to`,
`email_channel_id`,
`email_type`,
`enabled`,
`created`,
`trigger_id`)
VALUES
(
'sdCashBulkCreditTemplate3',
'via $env: Credited SDCash to your account',
'',
'Snapdeal<noreply@snapdeals.co.in>',
'',
'',
'',
'noreply@snapdeal.in', 2,
'',
1,
CURRENT_TIMESTAMP,8141
);

INSERT INTO `ums`.`email_template`
(
`name`,
`subject_template`,
`body_template`,
`from`,
`to`,
`cc`,
`bcc`,
`reply_to`,
`email_channel_id`,
`email_type`,
`enabled`,
`created`,
`trigger_id`)
VALUES
(
'sdCashBulkCreditTemplate4',
'via $env: Credited SDCash to your account',
'',
'Snapdeal<noreply@snapdeals.co.in>',
'',
'',
'',
'noreply@snapdeal.in', 2,
'',
1,
CURRENT_TIMESTAMP,8141
);

INSERT INTO `ums`.`email_template`
(
`name`,
`subject_template`,
`body_template`,
`from`,
`to`,
`cc`,
`bcc`,
`reply_to`,
`email_channel_id`,
`email_type`,
`enabled`,
`created`,
`trigger_id`)
VALUES
(
'sdCashBulkCreditTemplate5',
'via $env: Credited SDCash to your account',
'',
'Snapdeal<noreply@snapdeals.co.in>',
'',
'',
'',
'noreply@snapdeal.in', 2,
'',
1,
CURRENT_TIMESTAMP,8141);




INSERT INTO `ums`.`email_type`
(
`type_name`)
VALUES
(
'sdCashBulkCreditCustomerTemplate'
);

INSERT INTO `ums`.`email_type_template_mapping`
(
`email_type_id`,
`email_template_id`)
VALUES
(
(SELECT `email_type`.`id`
 from `ums`.`email_type` where `email_type`.`type_name`="sdCashBulkCreditCustomerTemplate"),
(SELECT `email_template`.`id`
 from `ums`.`email_template` where `email_template`.`name`="sdCashBulkCreditTemplate1")
);


INSERT INTO `ums`.`email_type_template_mapping`
(
`email_type_id`,
`email_template_id`)
VALUES
(
(SELECT `email_type`.`id`
 from `ums`.`email_type` where `email_type`.`type_name`="sdCashBulkCreditCustomerTemplate"),
(SELECT `email_template`.`id`
 from `ums`.`email_template` where `email_template`.`name`="sdCashBulkCreditTemplate2")
);

INSERT INTO `ums`.`email_type_template_mapping`
(
`email_type_id`,
`email_template_id`)
VALUES
(
(SELECT `email_type`.`id`
 from `ums`.`email_type` where `email_type`.`type_name`="sdCashBulkCreditCustomerTemplate"),
(SELECT `email_template`.`id`
 from `ums`.`email_template` where `email_template`.`name`="sdCashBulkCreditTemplate3")
);

INSERT INTO `ums`.`email_type_template_mapping`
(
`email_type_id`,
`email_template_id`)
VALUES
(
(SELECT `email_type`.`id`
 from `ums`.`email_type` where `email_type`.`type_name`="sdCashBulkCreditCustomerTemplate"),
(SELECT `email_template`.`id`
 from `ums`.`email_template` where `email_template`.`name`="sdCashBulkCreditTemplate4")
);

INSERT INTO `ums`.`email_type_template_mapping`
(
`email_type_id`,
`email_template_id`)
VALUES
(
(SELECT `email_type`.`id`
 from `ums`.`email_type` where `email_type`.`type_name`="sdCashBulkCreditCustomerTemplate"),
(SELECT `email_template`.`id`
 from `ums`.`email_template` where `email_template`.`name`="sdCashBulkCreditTemplate5")
);

INSERT INTO `ums`.`email_type_template_mapping`
(
`email_type_id`,
`email_template_id`)
VALUES
(
(SELECT `email_type`.`id`
 from `ums`.`email_type` where `email_type`.`type_name`="sdCashBulkCreditCustomerTemplate"),
(SELECT `email_template`.`id`
 from `ums`.`email_template` where `email_template`.`name`="sdCashCreditToUserViaBulkCredit")
);

UPDATE `ums`.`access_control` SET `pattern`='/admin/sdCash' WHERE `feature`='SD Cash Bulk Credit Panel';


UPDATE `ums`.`access_control` SET `link`='/admin/sdCash/sdCashBulkCreditUpload' WHERE `feature`='SD Cash Bulk Credit Panel';




UPDATE `ums`.`email_template` SET `body_template`='Thank you very much for shopping on Snapdeal App with order code ${sdCashBulkEmailRequest.orderId}. As a token of appreciation we have credited Rs.${sdCashBulkEmailRequest.sdCashAmount} Snapdeal cash with ${sdCashBulkEmailRequest.expiryDays} days.... Registered -${sdCashBulkEmailRequest.registered}, Verified- ${sdCashBulkEmailRequest.verified}' WHERE `name`='sdCashBulkCreditTemplate1';

UPDATE `ums`.`email_template` SET `body_template`='Thank you very much for shopping on Snapdeal App with order code ${sdCashBulkEmailRequest.orderId}. As a token of appreciation we have credited Rs.${sdCashBulkEmailRequest.sdCashAmount} Snapdeal cash with ${sdCashBulkEmailRequest.expiryDays} days.... Registered -${sdCashBulkEmailRequest.registered}, Verified- ${sdCashBulkEmailRequest.verified}' WHERE `name`='sdCashBulkCreditTemplate2';



UPDATE `ums`.`email_template` SET `body_template`='Thank you very much for shopping on Snapdeal App with order code ${sdCashBulkEmailRequest.orderId}. As a token of appreciation we have credited Rs.${sdCashBulkEmailRequest.sdCashAmount} Snapdeal cash with ${sdCashBulkEmailRequest.expiryDays} days.... Registered -${sdCashBulkEmailRequest.registered}, Verified- ${sdCashBulkEmailRequest.verified}' WHERE `name`='sdCashBulkCreditTemplate3';


UPDATE `ums`.`email_template` SET `body_template`='Thank you very much for shopping on Snapdeal App with order code ${sdCashBulkEmailRequest.orderId}. As a token of appreciation we have credited Rs.${sdCashBulkEmailRequest.sdCashAmount} Snapdeal cash with ${sdCashBulkEmailRequest.expiryDays} days.... Registered -${sdCashBulkEmailRequest.registered}, Verified- ${sdCashBulkEmailRequest.verified}' WHERE `name`='sdCashBulkCreditTemplate4';

UPDATE `ums`.`email_template` SET `body_template`='Thank you very much for shopping on Snapdeal App with order code ${sdCashBulkEmailRequest.orderId}. As a token of appreciation we have credited Rs.${sdCashBulkEmailRequest.sdCashAmount} Snapdeal cash with ${sdCashBulkEmailRequest.expiryDays} days.... Registered -${sdCashBulkEmailRequest.registered}, Verified- ${sdCashBulkEmailRequest.verified}' WHERE `name`='sdCashBulkCreditTemplate5';
