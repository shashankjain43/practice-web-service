/* New email templates for ums notifications */
UPDATE `ums`.`email_template` 
SET `subject_template` = '$subject' ,
`to` = 'debopam.basu@snapdeal.com,aditi.malhotra@snapdeal.com,ums-qa@snapdeal.com'
WHERE `name` = 'UMSCheckpointBreachNotification';

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`, `to`, `reply_to`, `email_channel_id`, `enabled`) VALUES ('techBreachNotification', '$subject', '$msg', 'Snapdeal <noreply@snapdeals.co.in>', 'tech.ums@snapdeal.com,ums-qa@snapdeal.com,prateek.kukreja@snapdeal.com', 'noreply@snapdeal.com', 2, 1);


/*New table to keep track of uniqueTxnId supplied by client(requestor) thus avoinding the idempotency*/
CREATE TABLE `ums`.`sd_wallet_txn_history` (
  `id` int(10) unsigned NOT NULL AUTO_INCREMENT,
  `source_unique_txn_id` varchar(150) NOT NULL,
  `source_app_id` varchar(45) NOT NULL,
  `ums_sdCash_txn_id` int(10) unsigned DEFAULT NULL,
  `created` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`id`),
  UNIQUE KEY `uq_sourceTxn_sourceAppId` (`source_unique_txn_id`,`source_app_id`)
);

/*This adds the column to existing history table to connect the records to new txn_history table*/
ALTER TABLE `ums`.`sd_wallet_history` ADD COLUMN `sdWallet_txn_history_id` INT(10) UNSIGNED NULL;


/* Adding/Modifying a column for File hash for its content in sd_cash_file_upload_history */
ALTER TABLE ums.sd_cash_file_upload_history ADD file_hash varchar(50);

/* set default sdcash value in activity record */
update sd_wallet_activity_type set sd_cash = 50 where name = 'Freecharge Credit';


/* SDCash back program configuration */

CREATE TABLE `ums`.`sdCashBack_program_config` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `subsidiary_name` varchar(45) DEFAULT NULL,
  `s3_access_id` varchar(45) DEFAULT NULL,
  `s3_secret_key` varchar(45) DEFAULT NULL,
  `s3_bucket_name` varchar(45) DEFAULT NULL,
  `s3_source_dir_name` varchar(45) DEFAULT NULL,
  `s3_success_dir_name` varchar(45) DEFAULT NULL,
  `s3_fail_dir_name` varchar(45) DEFAULT NULL,
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

 INSERT INTO `ums`.`sdCashBack_program_config` (`subsidiary_name`, `s3_access_id`, `s3_secret_key`, `s3_bucket_name`, `s3_source_dir_name`,`s3_success_dir_name`,`s3_fail_dir_name`, `is_enabled`, `activity_id`,`use_file_amount` ,`default_sdCash`, `sdcash_email_template`,`createUser_email_template`,`created`) VALUES ('Freecharge_SDCashBack_program', 'AKIAJZLXZLMVUONJ7XHQ', 'EHsMHIUr98Ocssw3lf9mAdgz4h2+ilr+Oy7GVR2w', 'sdprd', 'ums/new/', 'ums/processed/','ums/failed/',1, 33,1, 50, 'SubsidiaryCashBulkUploadEmail','accountCreationEmail',NOW()); 


/* insert template for accountCreationEmail and subsidiery sdcash credit*/

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`,`to`, `reply_to`, `email_channel_id`, `enabled`) VALUES ('SubsidiaryCashBulkUploadEmail', 'SD Cash Back - Congratulations! Your Freecharge shopping has got you Snapdeal Cash!', ' 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html><head><META http-equiv="Content-Type" content="text/html; charset=utf-8"></head><body>
 
 
 
 
 
<div>
<table border="0" cellspacing="0" cellpadding="0" style="width:600px;border:1px #ebebeb solid;font:normal 13px Arial,Helvetica,sans-serif;background:#fff;margin:0 auto;color:#5d5d5d">
  <tr>
  	<td height="60" bgcolor="#ffffff" align="center"><table width="98%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #ebebeb">
        <tr>
          <td align="left"><a href="http://www.snapdeal.com/?utm_source=DailyNewsletter&amp;utm_medium=%7B%5Eutm_medium%5E%7D&amp;utm_campaign=MWS_02042015&amp;utm_term=Logo&amp;utm_content=%7B%5Eutm_content%5E%7D&amp;email=%7B%5Eemailid%5E%7D" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/img1.png" alt=" Snapdeal.com" width="161" height="62" hspace="0" vspace="0" border="0" align="left" style="font:bold 20px Segoe UI,arial;color:#000000"></a></td>
          <td align="right"><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/img2.png" alt="Snapdeal Mobile App" width="152" height="62" hspace="0" vspace="0" border="0" align="right" style="font:bold 20px Segoe UI,arial;color:#000000"></td>
        </tr>
      </table></td>
  </tr>
  <tr>
  	<td align="center"><table style="font:normal 13px/18px Arial,Helvetica,sans-serif;text-align:left;color:#5d5d5d" width="94%" cellpadding="0" cellspacing="0" border="0">
<tr>
<td><br>Dear Customer,<br>
          <br>
Thank you for shopping on Freecharge .<br>
<br>
We have something special just for you... we are providing you <strong> ${sdCashBulkEmailRequest.sdCashAmount} </strong> SD Cash for your next purchase on the Snapdeal app. This SD cash is valid for <strong> ${sdCashBulkEmailRequest.expiryDays} </strong> days and can be used for any product on our app.<br>
<br> 
To check your SD cash, please sign in to the Snapdeal APP and go to My SD cash under my account. You can redeem your SD cash on the payments page.</td>
</tr>
<tr>
	<td height="16"></td>
</tr>
<tr>
	<td><a href="https://151172.api-05.com/serve?action=click&amp;publisher_id=151172&amp;site_id=57872&amp;site_id_ios=49546&amp;site_id_android=49846&amp;site_id_windows=57872" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/blu_btn.png" width="183" height="35" border="0"></a>
<br>
<br>
Happy Shopping,<br>
             Team Snapdeal</td>
</tr>
                          <tr>
                            <td height="16"></td>
                          </tr>
                          <tr>
                            <td><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/banner_withoutbtn.png" alt=" Snapdeal.com" width="100%" hspace="0" vspace="0" border="0" align="left" style="font:bold 20px Segoe UI,arial;color:#000000;height:auto"></td>
                          </tr>
<tr>
	<td height="16"></td>
</tr>
<tr>
	<td>For any queries or concerns,<a href="http://www.snapdeal.com/info/contactus?utm_source=cs_RefundandSDCashBackEmail" style="text-decoration:none;text-decoration:underline" target="_blank">Contact Us</a></td>
</tr>
<tr>
	<td height="16"></td>
</tr>
				</table></td>
  </tr>
</table>
 
 
</div>
 
</body></html>', 'Snapdeal <noreply@snapdeals.co.in>','', 'noreply@snapdeal.com', 2, 1);

INSERT INTO `ums`.`email_template` (`name`, `subject_template`, `body_template`, `from`, `to`,`reply_to`, `email_channel_id`, `enabled`) VALUES ('accountCreationEmail', 'Welcome to Snapdeal – India’s largest online seller site', ' 
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"><html><head><META http-equiv="Content-Type" content="text/html; charset=utf-8"></head><body>
 
 
 
 
 
 
 
<div>
<table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="max-width:730px">
  <tr>
    <td width="30" bgcolor="#f3f3f3" background="http://i4.sdlcdn.com/img/eventImage/10/pattern.png"><table width="95%" border="0" align="center" cellpadding="0" cellspacing="0" style="max-width:562px">
        <tr>
          <td> </td>
        </tr>
        <tr>
          <td height="88" bgcolor="#FFFFFF"><table width="100%" border="0" cellspacing="0" cellpadding="0">
              <tr>
                <td bgcolor="#313131" height="3"></td>
              </tr>
              <tr>
                <td height="60" bgcolor="#ffffff" align="center"><table width="98%" border="0" cellspacing="0" cellpadding="0" style="border-bottom:1px solid #ebebeb">
        <tr>
          <td align="left"><a href="http://www.snapdeal.com/?utm_source=DailyNewsletter&amp;utm_medium=%7B%5Eutm_medium%5E%7D&amp;utm_campaign=MWS_02042015&amp;utm_term=Logo&amp;utm_content=%7B%5Eutm_content%5E%7D&amp;email=%7B%5Eemailid%5E%7D" target="_blank"><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/img1.png" alt=" Snapdeal.com" width="161" height="62" hspace="0" vspace="0" border="0" align="left" style="font:bold 20px Segoe UI,arial;color:#000000"></a></td>
          <td align="right"><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/img2.png" alt="Snapdeal Mobile App" width="152" height="62" hspace="0" vspace="0" border="0" align="right" style="font:bold 20px Segoe UI,arial;color:#000000"></td>
        </tr>
      </table></td>
              </tr>
              <tr>
                <td height="1" bgcolor="#f2f2f2"></td>
              </tr>
              <tr>
                <td><table width="96%" border="0" align="center" cellpadding="0" cellspacing="0" style="max-width:510px">
                    <tr>
                      <td><table width="100%" border="0" align="left" cellpadding="0" cellspacing="0">
                          <tr>
                            <td height="16"></td>
                          </tr>
                          <tr>
                            <td style="font:normal 13px/18px Arial,Helvetica,sans-serif;color:#5d5d5d">Dear Customer,<br>
<br>
                              Thank you for your transacting on Freecharge. We have created an account for you on Snapdeal - India&#39;s Largest Online Shopping site.<br>
<br>
                              <table width="100%" border="0" cellspacing="0" cellpadding="0" style="font:normal 13px/30px Arial,Helvetica,sans-serif;color:#5d5d5d">
                              	<tr>
		<td width="100">Login id</td>
		<td width="10">:</td>
		<td>$loginID</td>
	</tr>
	<tr>
		<td>Password</td>
		<td>:</td>
		<td>$password</td>
	</tr>
	<tr>
		<td height="16"></td>
		<td></td>
		<td></td>
	</tr>
								</table>
You can use these credentials to login to your Snapdeal account. Soon, you will be getting a mail on your Snapdeal benefits for transacting on Freecharge. 
</td>
                          </tr>
                          <tr>
                            <td><table border="0" align="center" cellpadding="0" cellspacing="0" style="font:normal 13px/18px Arial,Helvetica,sans-serif;color:#5d5d5d">
                                <tr>
                                  <td height="16"></td>
                                </tr>
                                <tr>
                                  <td align="center" style="font-size:13px">With this account easily manage your orders and much more!</td>
                                </tr>
                                <tr>
                                  <td height="22"></td>
                                </tr>
                                <tr>
                                  <td align="center"><table width="100%" border="0" align="center" cellpadding="0" cellspacing="0" style="max-width:400px">
                                      <tr>
                                        <td align="center"><img src="http://i4.sdlcdn.com/img/storefront/03/to-icon.jpg" width="77" height="66" alt=""></td>
                                        <td align="center"><img src="http://i1.sdlcdn.com/img/storefront/03/co-icon.jpg" width="92" height="66" alt=""></td>
                                        <td align="center"><img src="http://i1.sdlcdn.com/img/storefront/03/return-icon.jpg" width="92" height="66" alt=""></td>
                                      </tr>
                                      <tr>
                                        <td height="16"></td>
                                        <td></td>
                                        <td></td>
                                      </tr>
                                      <tr>
                                        <td align="center"><img src="http://i1.sdlcdn.com/img/storefront/03/shortlist-icon.jpg" width="92" height="66" alt=""></td>
                                        <td align="center"><img src="http://i2.sdlcdn.com/img/storefront/03/sd-cash-icon.jpg" width="92" height="66" alt=""></td>
                                        <td align="center"><img src="http://i1.sdlcdn.com/img/storefront/03/recommendations.jpg" width="122" height="66" alt=""></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr>
                                  <td height="16"></td>
                                </tr>
                                <tr>
                                  <td align="center"><div style="width:220px;height:31px;margin-top:10px;margin-bottom:10px;border-radius:3px;background:#145cdc;border-bottom:4px solid #1049b0"><a href="https://151172.api-05.com/serve?action=click&amp;publisher_id=151172&amp;site_id=57872&amp;site_id_ios=49546&amp;site_id_android=49846&amp;site_id_windows=57872" style="color:#ffffff;font:normal 17px/33px Arial,Helvetica,sans-serif;text-decoration:none;width:220px;display:block;text-align:center" target="_blank">Download Snapdeal App &gt;</a> </div></td>
                                </tr>
                              </table></td>
                          </tr>
                          <tr>
                            <td height="16"></td>
                          </tr>
                          <tr>
                            <td style="font:normal 13px/18px Arial,Helvetica,sans-serif;color:#5d5d5d">Thank you for choosing Snapdeal.com, the one-stop shopping destination for millions of happy users.<br>
<br>
 
                              Happy Shopping<br>
                                 Team Snapdeal</td>
                          </tr>
                          <tr>
                            <td height="16"></td>
                          </tr>
                          <tr>
                            <td><img src="http://i.sdlcdn.com/img/marketing-mailers/mailer/2015/freecharge_cashback_24april/images/banner_withoutbtn.png" alt=" Snapdeal.com" width="100%" hspace="0" vspace="0" border="0" align="left" style="font:bold 20px Segoe UI,arial;color:#000000;height:auto"></td>
                          </tr>
                          <tr>
                            <td height="16"></td>
                          </tr>
                      </table></td>
                    </tr>
                    <tr>
                      <td height="1" bgcolor="#f4f4f4"></td>
                    </tr>
                    <tr>
                      <td height="80" style="font:normal 12px/18px Arial,Helvetica,sans-serif;color:#636363"><table border="0" cellspacing="0" cellpadding="2">
                          <tr>
                            <td width="90" valign="top"><table width="100%" border="0" cellpadding="0" cellspacing="0">
                                <tr>
                                  <td width="100%" align="left" style="padding:4px;font-size:11px">GET OUR APP</td>
                                </tr>
                                <tr>
                                  <td align="left"><table border="0" cellspacing="0" cellpadding="4">
                                      <tr>
                                        <td><a href="https://itunes.apple.com/in/app/snapdeal-mobile-shopping/id721124909" target="_blank"><img src="http://i4.sdlcdn.com/img/eventImage/10/apple.jpg" width="17" height="21" alt=""></a></td>
                                        <td><a href="https://play.google.com/store/apps/details?id=com.snapdeal.main" target="_blank"><img src="http://i1.sdlcdn.com/img/eventImage/10/android.jpg" width="18" height="21" alt=""></a></td>
                                        <td><a href="http://www.windowsphone.com/en-in/store/app/snapdeal/ee17fccf-40d0-4a59-80a3-04da47a5553f" target="_blank"><img src="http://i1.sdlcdn.com/img/eventImage/10/windows.jpg" width="19" height="21" alt=""></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                              </table></td>
                            <td width="10" align="left" valign="middle"><img src="http://i2.sdlcdn.com/img/eventImage/10/vr.jpg" width="1" height="35" alt=""></td>
                            <td valign="top"><table border="0" cellspacing="0" cellpadding="0">
                                <tr>
                                  <td align="left" style="padding:4px;font-size:11px">STAY CONNECTED</td>
                                </tr>
                                <tr>
                                  <td align="left"><table width="100%" border="0" cellspacing="0" cellpadding="4">
                                      <tr>
                                        <td><a href="http://www.facebook.com/SnapDeal" target="_blank"><img src="http://i2.sdlcdn.com/img/eventImage/10/facebook.jpg" width="13" height="21" alt=""></a></td>
                                        <td><a href="http://www.twitter.com/snapdeal" target="_blank"><img src="http://i1.sdlcdn.com/img/eventImage/10/twitter.jpg" width="26" height="21" alt=""></a></td>
                                        <td><a href="https://plus.google.com/100573109232340925417" target="_blank"><img src="http://i1.sdlcdn.com/img/eventImage/10/googleplus.jpg" width="19" height="21" alt=""></a></td>
                                        <td><a href="https://www.youtube.com/user/snapdeal" target="_blank"><img src="http://i1.sdlcdn.com/img/eventImage/10/youtube.jpg" width="22" height="21" alt=""></a></td>
                                      </tr>
                                    </table></td>
                                </tr>
                                <tr></tr>
                              </table></td>
                          </tr>
                        </table></td>
                    </tr>
                  </table></td>
              </tr>
            </table></td>
        </tr>
        <tr>
          <td> </td>
        </tr>
      </table></td>
  </tr>
</table>
</div>
</body></html>', 'Snapdeal <noreply@snapdeals.co.in>','', 'noreply@snapdeal.in', 2, 1);




