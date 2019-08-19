insert into configuration values('SNAPDEAL','money.out.verification.otp.email.template','<!doctype html> <html> <head> <meta charset="utf-8"> <title>otp</title> </head>  <body> <table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:arial;margin:0 auto; ">   <tr>     <td>     <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="images/logo.png" alt="freecharge wallet" width="170"></p></td>   </tr>   <tr bgcolor="#f4f5f6">     <td><table cellpadding="0" cellspacing="5" border="0">         <tr>           <td><table cellpadding="0" cellspacing="15" border="0">               <tr>                 <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>                    Your one-time verification code for withdrawing money into your bank account is $verificationCode.   This code ensures that only you have access to your account. </p></td>               </tr>                                             <tr>               <td bgcolor="#ffffff">               <table cellpadding="15" cellspacing="0" border="0" width="600" >               <tr>               <td>               <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;"> Verification Code: $verificationCode<br> Valid for: 30 minutes               </p>                            </td>               </tr>               </table>                             </td>               </tr>                              <tr>                 <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>                     Team FreeCharge </p></td>               </tr>             </table></td>         </tr>       </table></td>   </tr>   <tr bgcolor="#fbfcfd">     <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;"s>Download the FreeCharge app today</p>        <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>   </tr>   <tr>     <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to transaction related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t requested for withdrawal of money,<br>  write <a href="#" style=" color:#494949;">here</a>. </p>       <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>   </tr> </table> </body> </html>','It should not be used as all link mail should go by freecharge template.');

insert into configuration values('FREECHARGE','money.out.verification.otp.email.template','<!doctype html> <html> <head> <meta charset="utf-8"> <title>otp</title> </head>  <body> <table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:arial;margin:0 auto; ">   <tr>     <td>     <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="images/logo.png" alt="freecharge wallet" width="170"></p></td>   </tr>   <tr bgcolor="#f4f5f6">     <td><table cellpadding="0" cellspacing="5" border="0">         <tr>           <td><table cellpadding="0" cellspacing="15" border="0">               <tr>                 <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>                    Your one-time verification code for withdrawing money into your bank account is $verificationCode.   This code ensures that only you have access to your account. </p></td>               </tr>                                             <tr>               <td bgcolor="#ffffff">               <table cellpadding="15" cellspacing="0" border="0" width="600" >               <tr>               <td>               <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;"> Verification Code: $verificationCode<br> Valid for: 30 minutes               </p>                            </td>               </tr>               </table>                             </td>               </tr>                              <tr>                 <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>                     Team FreeCharge </p></td>               </tr>             </table></td>         </tr>       </table></td>   </tr>   <tr bgcolor="#fbfcfd">     <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;"s>Download the FreeCharge app today</p>        <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>   </tr>   <tr>     <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to transaction related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t requested for withdrawal of money,<br>  write <a href="#" style=" color:#494949;">here</a>. </p>       <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>   </tr> </table> </body> </html>','');

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.login','Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.forgot.password','Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.mobile.verification','Your one-time verification code to register your mobile number on MobileVerification account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.user.signup','Your one-time verification code to register your mobile number on sign-up account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.update.mobile','Your one-time verification code to change your mobile number registered with Freecharge account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.upgrade.user','Your one-time verification code to register your mobile number on Freecharge account via $merchant is $verificationCode. It is valid for 30 minutes','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.link.account','Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.onecheck','Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('FREECHARGE','sms.template.otp.templateBody.money.out','Your verification Pin: $verificationCode','') ;


insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.login','Your verification Pin: $verificationCode','') ;
    
insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.forgot.password','Your verification Pin: $verificationCode','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.mobile.verification','Your one-time verification code to register your mobile number on Freecharge account via $merchant is $verificationCode. It is valid for 30 minutes','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.user.signup','Your one-time verification code to register your mobile number on Freecharge account via $merchant is $verificationCode. It is valid for 30 minutes','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.update.mobile','Your one-time verification code to change your mobile number registered with Freecharge account is $verificationCode. It is valid for 30 minutes.','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.upgrade.user','Your one-time verification code to register your mobile number on Freecharge account via $merchant is $verificationCode. It is valid for 30 minutes','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.link.account','Your verification Pin: $verificationCode','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.onecheck','Your verification Pin: $verificationCode','') ;

insert  into configuration values('SNAPDEAL','sms.template.otp.templateBody.money.out','Your verification Pin: $verificationCode','') ;












insert  into configuration values('SNAPDEAL','verification.otp.email.subject.money.out','OTP code moneyOut','') ;

insert  into configuration values('FREECHARGE','verification.otp.email.subject.money.out','OTP code moneyOut','') ;

