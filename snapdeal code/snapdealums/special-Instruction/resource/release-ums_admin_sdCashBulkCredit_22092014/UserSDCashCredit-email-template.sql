INSERT INTO `ums`.`email_template` (`name`,`subject_template`,`body_template`,`from`,`to`,`cc`,`bcc`,`reply_to`,`email_channel_id`,`email_type`,`enabled`,`created`) VALUES ('sdCashCreditToUserViaBulkCredit','Credited SDCash to your account','','Snapdeal <noreply@snapdeals.co.in>','','','','Snapdeal <noreply@snapdeals.co.in>',4,null,1,'2014-09-04 10:13:32');

update `ums`.`email_template`  set `body_template` = "<table style=\"border: 1px dotted #ababab;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"640\">
<tbody>

<tr><td>
       

$name $amount $reason  $expiry
        </td>
      </tr>



</tbody>
</table>" where `name` = "sdCashCreditToUserViaBulkCredit";	

