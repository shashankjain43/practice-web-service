update configuration set config_value = '<!doctype html> <html> <head> <meta charset="utf-8"> <title>linking-email OTP and Secce</title> </head>  <body> <table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:arial;margin:0 auto; ">   <tr>     <td>      <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;">     <a href="https://www.freecharge.in/" target="_blank" style="text-decoration:none; border:0px"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>   </tr>   <tr bgcolor="#f4f5f6">     <td><table cellpadding="0" cellspacing="5" border="0">         <tr>           <td><table cellpadding="0" cellspacing="15" border="0">               <tr>                 <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>                    Here is a one-time verification code to use FreeCharge account on <strong>$merchant</strong>. This code ensures that only you can access your account.</p></td>               </tr>                                             <tr>               <td bgcolor="#ffffff">               <table cellpadding="15" cellspacing="0" border="0" width="600" >               <tr>               <td>               <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;"> Verification Code: <strong>$verificationCode</strong><br>  Valid for: <strong>30 minutes</strong>               </p>                            </td>               </tr>               </table>                             </td>               </tr>                              <tr>               <td><p align="left" style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878;"> After verification, you will be able to use your FreeCharge account on both FreeCharge and  Snapdeal.</p></td>               </tr>                              <tr>                 <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>                     Team FreeCharge </p></td>               </tr>             </table></td>         </tr>       </table></td>   </tr>   <tr bgcolor="#fbfcfd">       <td><p align="center"  style="padding:0px; margin:0px; padding-top:40px;">Download the FreeCharge app today</p>          <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px">          <a href="https://itunes.apple.com/in/app/id877495926?mt=8" target="_blank" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="https://play.google.com/store/apps/details?id=com.freecharge.android" style="border:0; text-decoration:none;" target="_blank"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a>           <a href="https://www.microsoft.com/en-us/store/apps/freecharge-recharge-mobile-dth-data-card/9wzdncrdsr94" style="border:0; text-decoration:none;" target="_blank"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>   </tr>   <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">
Get your account related queries clarified or raise a concern
 <a href="http://support.freecharge.in/" style=" color:#494949;" target="_blank">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>
  </tr> </table> </body> </html>' where config_key = 'link.account.verification.otp.email.template' AND config_type = "FREECHARGE";
