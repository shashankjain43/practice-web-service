use ums;
INSERT INTO `email_template` (`name`,`subject_template`,`body_template`,`from`,`to`,`cc`,`bcc`,`reply_to`,`email_channel_id`,`email_type`,`enabled`,`created`) VALUES ('AutocreationTask','AutocreationTask','Task Result for task: $task.name<br/>Task status : $task.lastExecResult<br/><br/>UMS Admin','Snapdeal Admin<prod.monitor@snapdeals.co.in>','ghanshyam@jasperindia.com','rachit.khare@snapdeal.com','','ghanshyam@jasperindia.com',4,NULL,1,'2013-10-29 14:02:03');
ALTER TABLE `ums`.`user` ADD COLUMN `autocreated_notification_count` INT(11) NULL DEFAULT 0  AFTER `subscriber_profile_id` ;
INSERT INTO `ums`.`ums_task` (`name`, `impl_class`, `cron_expression`, `clustered`, `concurrent`, `created`, `updated`, `enabled`, `email_template`, `notification_email`, `last_exec_time`, `last_exec_result`) VALUES ('AutoAccountMailerTask', 'com.snapdeal.ums.admin.jobs.AutoAccountMailerTask', '0 0 2 1/1 * ? *', 1, 1, '2013-10-28 15:53:33', '2013-10-29 15:53:33', 1, 'AutocreationTask', 'ghanshyam@snapdeal.com,rachit.khare@snapdeal.com', '2013-10-28 00:03:00', 'Completed successfully = true');
INSERT INTO `task_parameter` (`task_id`,`name`,`value`,`created`,`updated`) VALUES ((select id from ums_task where name = 'AutoAccountMailerTask'),'autocreatedlastndays','10','2013-09-09 12:12:12','2013-10-28 22:56:02');
INSERT INTO `task_parameter` (`task_id`,`name`,`value`,`created`,`updated`) VALUES ((select id from ums_task where name = 'AutoAccountMailerTask'),'autocreatedNotificationCount','100','2013-09-09 12:12:12','2013-09-09 12:12:12');
INSERT INTO `task_parameter` (`task_id`,`name`,`value`,`created`,`updated`) VALUES ((select id from ums_task where name = 'AutoAccountMailerTask'),'daysinterval','1','2013-09-09 12:12:12','2013-10-28 22:56:02');
INSERT INTO `email_template` (`name`,`subject_template`,`body_template`,`from`,`to`,`cc`,`bcc`,`reply_to`,`email_channel_id`,`email_type`,`enabled`,`created`) VALUES ('reConfirmAutoAccountEmail','Your account created on Snapdeal.com','                                                                     
                                                                     
                                                                     
                                             
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html><head><META http-equiv="Content-Type" content="text/html; charset=utf-8"><style type="text/css">
body{background:#f8f8f8;}.P {
	font-family: Tahoma, Geneva, sans-serif;
}
.P {
	font-family: Tahoma, Geneva, sans-serif;
}
</style></head><body>
<div>
<table  border="0" cellspacing="0" cellpadding="0" style="width:648px; border:1px #ebebeb solid; font:normal 12px Segoe UI, arial; background:#fff; margin:0 auto; color:#272727;">
  <tr>
                <td><table width="100%" border="0" cellpadding="0" cellspacing="0">
                <tr>
                <td style="padding:9px 15px; border-bottom:2px solid #ccc;"><a href="http://www.snapdeal.com"><img
src="http://i1.sdlcdn.com/img/snapdeal/sprite/snapdeal_logo_tagline.png" border="0" /></a></td>
        </tr>
    </table></td>
  </tr>
<tr>
<td>
<table width="100%" border="0" cellpadding="0" cellspacing="0" style="border-bottom:1px dotted #ababab;">
          <tr>
             <td style="width:160px; border-right:1px dotted #ababab; padding:12px 3px;"><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2012/newsletter_icon/securePayment_dec12.jpg" align="left" hspace="3" vspace="6" alt="" width="32" height="22" border="0" />
             <span style="font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;"><strong> Secure Payments</strong></span> <br />
              <span style="font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;">100%</span>
            </td>
            <td style="width:104px; border-right:1px dotted #ababab; padding:12px 3px;"><img src="http://i.sdlcdn.com/img/mail/snapdeal/dailynewsletter/bag_icon.gif" align="left" hspace="3" vspace="6" alt="" width="32" height="22" border="0" /> <span style="font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;"><strong>Cash</strong></span> <br />
              <span style="font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;">on Delivery</span></td>
            <td style="width:149px; border-right:1px dotted #ababab; padding:0 6px;"><img src="http://i.sdlcdn.com/img/mail/snapdeal/dailynewsletter/free_shipping_icon.gif" align="left" vspace="6" hspace="3" alt="" width="32" height="22" border="0" /> <span style="font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;"><strong>Free Shipping</strong></span> <br />
              <span style="font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;">Across India</span></td>
            <td style="width:174px; padding:0 6px;"><img src="http://i.sdlcdn.com/img/mail/snapdeal/dailynewsletter/emi_icon.gif" align="left" hspace="3" vspace="6" alt="" width="24" height="22" border="0" /> <span style="font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;"><strong>EMI Options <a href="http://www.snapdeal.com/info/faq/EMI" target="_blank"><img src="http://i.sdlcdn.com/img/mail/snapdeal/dailynewsletter/info_icon.gif" style="vertical-align:-2px;" alt="" border="0" width="13" height="12" title="Read FAQ for EMI" /></a></strong></span> <br />
              <span style="font-size:11px; font-family:Tahoma, Geneva, sans-serif; color:#5d5d5d;">Available in 3 &amp; 6 months</span></td>
          </tr>
        </table>
</td>
</tr>
  <tr>
    <td style="padding-left:15px; padding-right:15px;"><table width="640" align="center" border="0" cellpadding="0" cellspacing="0">
      <tbody>
        <tr>
          <td></td>
          <td><table width="628" border="0" cellpadding="0" cellspacing="0">
            <tbody>
              <tr>
                <td> <p style="font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#000;"><br>
                  Dear ${name},<br>
                  <br>
                  Thank you for shopping with <strong><a href="http://www.snapdeal.com/">Snapdeal.com</a></strong>, India''s Favourite Online Mall.<br>
<br>                  We''ve created a new Snapdeal account for you. A Snapdeal account gives you a personalized shopping experience.<br>
                  <br>
             
Your account details are:<br><br>
<strong>Login ID is : ${email}
                  
                  <p style="font-size:13px; font-family:Tahoma, Geneva, sans-serif; color:#000;"><strong> <span style="font-size:14px; font-family:Tahoma, Geneva, sans-serif; color:#000;">To verify your account, <a href="$confirmationLink">Click Here</a></span></strong></p></td>
              </tr>
            </tbody>
          </table></td>
        </tr>
      </tbody>
    </table><table><tr><td></td></tr></table>
<table width="600" height="249" cellpadding="0" cellspacing="0" style="border:1px solid #d1d1d1" align="center">
  <tr>
    <td height="37" colspan="3" style="background-color:#ececec; border-bottom:1px solid #d1d1d1; font-family:Segoe UI; Arial, Helvetica, sans-serif; color:#0297c7; font-weight:bold; padding-left:15px; font-size:15px">What all can you do with your Snapdeal account :</td>
  </tr>
  <tr>
    <td width="61" height="69" align="center"><img src="${contentPath}img/mail/snapdeal/accountCreation/purchase.jpg" /></td>
    <td width="515" style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#14130e">Manage your purchases<br />
<span style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:12px; color:#58595b; font-weight:100">Now track your order status and print your vouchers anytime. All your purchases at Snapdeal are recorded here</span></td>
    <td width="22" style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#14130e">&nbsp;</td>
  </tr>
  <tr>
    <td height="71" align="center"><img src="${contentPath}img/mail/snapdeal/accountCreation/sdcash.jpg"/></td>
    <td style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#14130e">Maintain your SD Cash account<br />
<span style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:12px; color:#58595b; font-weight:100">View your <span style="font-weight:bold; color:#2020222">Snapdeal cash</span> earned, its usage and how much balance is left. SD cash enables you to make purchases without entering payment info every time.</span></td>
    <td style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#14130e">&nbsp;</td>
  </tr>
  <tr>
    <td height="72" align="center"><img src="${contentPath}img/mail/snapdeal/accountCreation/shopping.jpg" /></td>
    <td style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#14130e">Quick shopping<br />
<span style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:12px; color:#58595b; font-weight:100">We save your contact & shipping details, so you save on it
    <td style="font-family:Segoe UI, Arial, Helvetica, sans-serif; font-size:14px; font-weight:bold; color:#14130e">&nbsp;</td>
  </tr>
</table>
<br>
    </td>
  </tr>
  <tr>
                <td style="padding-left:15px; padding-right:15px; font-size:13px;"><img src="http://i.sdlcdn.com/img/mailer/thanks-icon.jpg" />
                      <br/>
Happy shopping!  <br/>
Snapdeal Team</p> <br></td>
  </tr>
  <tr>
  <td>
  </td>
  </tr>
  <tr bgcolor="#c4c4c4">
                <td style="padding:2px 0;">
                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                <tr bgcolor="#f5f5f5">
                <td style="padding:0 3px 0 13px;">
                                <table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                <td style="font-size:12px; font-family:Segoe UI, arial; padding:8px 0; color:#484848;"><a href=" http://www.snapdeal.com/info/faq" target="_blank" style="color:#484848; text-decoration:none;">&bull; FAQ</a> <img
src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/spacer.gif" width="20" height="1" alt=""> <a href="http://www.snapdeal.com/info/terms" target="_blank" style="color:#484848; text-decoration:none;">&bull; Privacy Policy</a> <img
src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/spacer.gif" height="1" width="20" alt=""> <a href="http://www.snapdeal.com/info/termsOfUse" target="_blank" style="color:#484848; text-decoration:none;">&bull; Terms &amp; Conditions</a>
<img src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/spacer.gif" width="20" height="1" alt=""> <a href="http://www.snapdeal.com/info/termsOfUse" target="_blank" style="color:#484848; text-decoration:none;">&bull; Snapdeal Blog</a></td>
                        </tr>
                        <tr>
                                <td style="border-top:1px dotted #aaaaaa; padding:2px 0;"><table border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr>
                                                <td style="font-size:12px; padding:4px 0; font-family:Segoe UI, arial; width:180px;  ">
 For any query or assistance, feel free to <a href="http://www.snapdeal.com/info/contactus?utm_source=cs_confirmEmail" style="text-decoration:none;color:#484848; text-decoration: underline" target="_blank">Contact Us</a>
</td>
                                </tr>
                            </table></td>
                        </tr>
                    </table>
                </td>
                <td style="width:2px; background:#ffffff;"></td>
                <td style="width:5px; background:#c4c4c4;"></td>
                <td bgcolor="#f5f5f5" style="width:156px; vertical-align:top; padding-top:10px; background:url(http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/findus_bg.png) top left no-repeat; padding-left:30px; border-left:1px solid #afaeae; font-family:Segoe UI, arial;"><table
border="0" cellpadding="0" cellspacing="0" width="100%">
                                <tr><td style="color:#484848; font-weight:bold; font-size:12px;">Find us on: </td></tr>
                    <tr><td style="padding-top:6px;"><table border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                <td style="width:33px;"><a href="http://www.facebook.com/SnapDeal" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/fb_icon.png" alt="Facebook" border="0"></a></td>
                            <td style="width:33px;"><a href="http://www.twitter.com/snapdeal" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/tw_icon.png" alt="Twitter" border="0"></a></td>
                            <td style="width:33px;"><a href="https://plus.google.com/100573109232340925417?prsrc=3" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/gp_icon.png" alt="Google Plus" border="0"></a></td>
                            <td style="width:33px;"><a href="http://pinterest.com/snapdeal/" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/2012/order_shipped/pi_icon.png" alt="Pintrest" border="0"></a></td>
                        </tr>
                    </table></td></tr>
                </table></td>
            </tr>
        </table>
    </td>
  </tr>
  <tr>
                <td align="center" bgcolor="#fdfdfd" style="font-size:11px; font-family:Segoe UI, arial; border-top:1px solid #dcdcdc; padding:7px 6px;">Reach us at : Snapdeal.com, 246, First Floor, Okhla Phase III, New Delhi - 110020 </td>
  </tr>
</table>
</div>','Snapdeal<newsletter@snapdeals.co.in>','','','','noreply@snapdeal.in',4,'nul',1,'2013-11-12 19:07:30');

