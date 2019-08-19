
update `ums`.`email_template`  set `subject_template` = 'Congrats $name! Snapbox has been activated!' where `name`="snapBoxActivationConfirmation";



update `ums`.`email_template`  set `body_template` = "<table style=\"border: 1px dotted #ababab;\" align=\"center\" border=\"0\" cellpadding=\"0\" cellspacing=\"0\" width=\"640\">
<tbody>

<tr><td>
       <table width=\"100%\">
      <tr>
        <td><a target=\"_blank\" href=\"http://www.snapdeal.com\"><img style=\"color: #000; font: bold 18px Calibri; margin-left: 8px; background: #fff;padding-top:25px;\" title=\"Snapdeal.com\" alt=\"Snapdeal.com\" src=\"http://i1.sdlcdn.com/img/mail/snapdeal/dailynewsletter/SnapdealLogo.jpg\" border=\"0\" height=\"44\" width=\"172\" /></a>
        </td><td align=\"middle\" style=\"padding-top:20px;\"><a target=\"_blank\" href=\"${orderDTO.orderSummaryUrl}?utm_source=TrackOrder_OrderSummary\"><img style=\"color: #000; font: bold 18px Calibri;background: #ccc;\" title=\"Snapdeal.com\" alt=\"Snapdeal.com\" src=\"images/100%secure.jpg\" width=\"146\" height=\"46\" /></a></td>
<td align=\"middle\" style=\"padding-top:20px;\">
<a target=\"_blank\" href=\"http://www.snapdeal.com/info/TrustPay?utm_source=TrustPay_OrderSummary\"><img style=\"color: #000; font: bold 18px Calibri;background: #ccc;\" title=\"Snapdeal.com\" alt=\"Snapdeal.com\" src=\"images/trustpay.jpg\" width=\"125\" height=\"46\" border=\"0\" /></a>
</td>

        <td align=\"middle\" style=\"padding-top:20px;\"><a target=\"_blank\" href=\"http://www.snapdeal.com/info/easyReturnsPolicy?utm_source=Returns_OrderSummary\"><img style=\"color: #000; font: bold 18px Calibri;background: #ccc;\" title=\"Snapdeal.com\" alt=\"Snapdeal.com\" src=\"images/7daysreturn.jpg\" width=\"138\" height=\"46\" border=\"0\"/></a></td>
          </tr>
      </table>
        </td>
      </tr>

<tr>
<td height=\"40\" style=\"font-family:Calibri;font-size:28px;padding:5px;\">
<table style=\"border-top:2px solid #d53d41;border-bottom: 1px solid #cecece;\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\" width=\"640\">
<td width=\"28\"><img src=\"images/green_tick.gif\"  /></td>
<td style=\"text-transform:uppercase;font-family:Calibri;font-size:25px;color:#232323;\">Activated Your Snapbox</td>
</table>
</td>
</tr>




<tr>
<td style=\"padding-top:45px;\">
<table align=\"center\" cellpadding=\"0\" cellspacing=\"0\" width=\"640\" style=\"margin-top:-40px;margin-bottom:-46px;\">
<td width=\"400\" style=\"padding:0px;\">
<table width=\"100%\">
<tr>
<td  style=\"font-family: Calibri; font-size: 19px; font-weight:bold; color: #565656;\" colspan=\"2\">
Dear $name<br />
</td>
</tr>
<tr>
<td style=\"font-family:Calibri; font-size:23px; color:#dc3f3f;padding-top:10px;\">Congratulations! <i style=\"font-size:21px; color:#0697c4;font-style:normal;\">Your Snapbox is now Activated</i> 
</tr>
<tr>
<td style=\"font-family:Calibri; font-size:19px; color:#000;padding-top:10px;line-height:20px;font-weight:bold;\">So, what next ?</td>
</tr>
<tr>
<td style=\"font-family:Calibri; font-size:15px; color:#565656;padding-bottom:15px;line-height:20px;\">Nothing, just sit back and relax while we craft the most exclusive offers for you. We will keep notifying you about all the new coupons you get but do visit your Snapbox section regularly.
Funda is simple. The more you shop, the more coupons you get…
</td>
</tr>
</table>
</td>

<td width=\"200\" style=\"padding:0px;\">
<table width=\"100%\">
<tr height=\"35\"></tr>
<tr>
<td style=\"padding-left:45px;padding-top:20px;\"><img src=\"images/my_snapbox.jpg\" width=\"171\" height=\"163\"/></td>
</tr>
<tr height=\"40\"></tr>
</table>
</td>

</table>
</td>
</tr>


<tr>
<td style=\"font-family: Calibri; font-size: 13px; line-height:18px;padding-bottom:10px;padding-left:15px;\">

</td>
</tr>

<tr>
<td style=\"font-family: Calibri; font-size: 14px;line-height:18px; color:#680000;padding:10px;\">
<table width=\"640\" style=\"background:#f9f9f9;padding:10px 150px;border-top:1px solid #d7d7d7;border-bottom:1px solid #d7d7d7;text-align:center;\">
<tr>
<td style=\"font-size:19px; color:#fff; border:1px solid #0697c4;width:235px;display:inline-block;background:#0697c4;padding:10px;cursor:pointer;font-weight:bold;\"><a href='$link'>Get Started Now!</a></td>
</tr>
<tr>
<td style=\"font-size:19px; color:#0697c4;\">Don’t want to miss your other coupons!! </td>
</tr>
<tr>
<td style=\"font-size:17px; color:#000;\">Go check them and steal the deal</td>
</tr>
</table>
</td>
</tr>

<tr>
<td style=\"font-family:Calibri;font-size:16px;padding:30px 5px 5px;\">
<table style=\"border-bottom:4px solid #cecece;padding-bottom:40px;\" align=\"center\" cellpadding=\"4\" cellspacing=\"0\" width=\"640\">
<tr>
<td width=\"25\" style=\"padding-bottom:8px;\"><img src=\"images/question.jpg\"  /></td>
<td style=\"text-transform:uppercase;font-size:16px;font-weight:bold;color:#232323;padding-bottom:15px;vertical-align:top;\">Here are some answers to your queries</td>
</tr>
<tr>
<td style=\"font-size:13px;color:#000;padding-left:10px;\">Q.</td>
<td style=\"font-size:13px;color:#000;text-transform:uppercase;\">How do I redeem Snapbox Promo codes?</td>
</tr>
<tr>
<td style=\"vertical-align:top;font-size:13px;color:#000;padding-left:10px;padding-bottom:10px;\">A.</td>
<td style=\"font-size:13px;color:#565656;padding-bottom:10px;\">Visit your Snapbox section regularly to checkout the latest offers. While making the purchase, we would provide you the option to choose from all the available promo codes. (Info graphic to be shown)</td>
</tr>
<tr>
<td style=\"font-size:13px;color:#000;padding-left:10px;\">Q.</td>
<td style=\"font-size:13px;color:#000;text-transform:uppercase;\">How to find Snapbox promo codes while making purchase?</td>
</tr>
<tr>
<td style=\"vertical-align:top;font-size:13px;color:#000;padding-left:10px;padding-bottom:10px;\">A.</td>
<td style=\"font-size:13px;color:#565656;padding-bottom:10px;\">Please log in first to and visit the order summary page. Snapbox promo code can be seen on  bottom right as shown in image(Show Image) Ques: How many Snapbox promo codes can be used at once</td>
</tr>
<tr>
<td style=\"font-size:13px;color:#000;padding-left:10px;\">Q.</td>
<td style=\"font-size:13px;color:#000;text-transform:uppercase;\">What is the notification icon?</td>
</tr>
<tr>
<td style=\"vertical-align:top;font-size:13px;color:#000;padding-left:10px;padding-bottom:10px;\">A.</td>
<td style=\"font-size:13px;color:#565656;padding-bottom:10px;\">Notification icon would give alerts in case of arrival of new promo code, applicability of promo code in a particular category/PDP you are viewing.
</td>
</tr>
</table>
</td>
</tr>

<tr>
<td style=\"font-family: Calibri; font-size: 14px; line-height:18px;padding:5px 0px 0px 15px;\">
For any query or assistance, feel free to <a style=\"color:#265add;\" href=\"http://www.snapdeal.com/info/contactus\">Contact Us</a>
</td>
</tr>
<tr>
<td style=\"font-family: Calibri; font-size: 14px; line-height:18px;padding:5px 0px 25px 15px;\">
Visit Snapdeal from your mobile phone now - Web : <a style=\"color:#265add;\" href=\"http://m.snapdeal.com\">m.snapdeal.com</a>
</td>
</tr>

<tr>
<td style=\"font-family:Calibri; font-size:14px;color:#000000;\">
<table width=\"40%\">
	<td style=\"width:50%;padding-left:15px;\">Install Apps
 <table width=\"20%\" cellspacing=\"0\">
	<td width=\"26px\" style=\"padding-right:5px;padding-top:3px;\"><a href=\"#\"><img src=\"images/iso.gif\" width=\"26\" height=\"26\"  /></a></td>
    <td width=\"26px\" style=\"padding-right:5px;padding-top:3px;\"><a href=\"#\"><img src=\"images/windows.gif\" width=\"26\" height=\"26\"  /></a></td>
    <td style=\"padding-top:3px;\"><a href=\"#\"><img src=\"images/android.gif\" width=\"26\" height=\"26\" /></a></td>
</table>
    </td>
    <td style=\"width:50%;\">Follow us on
    	<table width=\"20%\" cellspacing=\"0\">
	<td width=\"26px\" style=\"padding-right:5px;padding-top:3px;\"><a href=\"#\"><img src=\"images/facebook.gif\" width=\"26\" height=\"26\" /></a></td>
    <td width=\"26px\" style=\"padding-right:5px;padding-top:3px;\"><a href=\"#\"><img src=\"images/googlePlus.gif\" width=\"26\" height=\"26\"  /></a></td>
    <td style=\"padding-right:5px;padding-top:3px;\"><a href=\"#\"><img src=\"images/p.gif\" width=\"26\" height=\"26\"  /></a></td>
    <td style=\"padding-top:3px;\"><a href=\"#\"><img src=\"images/tweet.gif\" width=\"26\" height=\"26\" /></a></td>
</table>
    </td>
</table>
</td>
</tr>

<tr>
<td style=\"font-family: Calibri; font-size: 17px; line-height:18px;padding:15px 0px 15px 15px;\">
In the mean time, check your listed products on <a style=\"color:#265add; text-decoration:none;\" href=\"#\">www.snapdeal.com</a>
</td>
</tr>

<tr>
<td style=\"padding-left:8px;\"><img src=\"images/add.jpg\" height=\"114\" width=\"643\" /></td>
</tr>

</tbody>
</table>" where `name` = "snapBoxActivationConfirmation";
