insert into configuration values('SNAPDEAL','money.out.verification.otp.email.template','snapdeal -- your verification code for creating a new password is $verificationCode.','');

insert into configuration values('FREECHARGE','money.out.verification.otp.email.template','Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.','');

insert into configuration values('FREECHARGE','link.account.verification.otp.email.template','<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>otp</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:arial;margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>
                   Your one-time verification code to use FreeCharge account on <strong>$merchant</strong> is 

<strong>$verificationCode</strong>. This code ensures that only you have access to your account.</p></td>
              </tr>
                 
              <tr>
              <td bgcolor="#ffffff">
              <table cellpadding="15" cellspacing="0" border="0" width="600" >
              <tr>
              <td>
              <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;">
Verification Code: <strong>$verificationCode</strong><br>

Valid for: <strong>30 minutes</strong>
              </p>
            
              </td>
              </tr>
              </table>
             
              </td>
              </tr>
              
              <tr>
              <td><p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;"> After verification, you will be able to use your FreeCharge account on both FreeCharge and

Snapdeal.</p></td>
              </tr>
              
              <tr>
                <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>
                    Team FreeCharge </p></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;"s>Download the FreeCharge app today</p>
       <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>.For any unresolved query,
 write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>
','');

insert into configuration values('FREECHARGE','forgot.password.verification.otp.email.template','<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>otp</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:Trebuchet MS;margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>
                   Your one-time verification code to create a new password is <strong>$verificationCode</strong>. 

   This code ensures that only you have access to your account. </p></td>
              </tr>
              
              
              <tr>
              <td bgcolor="#ffffff">
              <table cellpadding="15" cellspacing="0" border="0" width="600" >
              <tr>
              <td>
              <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;">
Verification Code: <strong>$verificationCode</strong><br>

Valid for:<strong> 30 minutes</strong>
              </p>
            
              </td>
              </tr>
              </table>
             
              </td>
              </tr>
              
              <tr>
                <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>
                    Team FreeCharge </p></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;"s>Download the FreeCharge app today</p>
       <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t requested for password change,<br>
 write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>','');

insert into configuration values('SNAPDEAL','forgot.password.verification.otp.email.template','snapdeal -- your verification code for creating a new password is $verificationCode.','');


insert into configuration values('SNAPDEAL','link.account.verification.otp.email.template','snapdeal -- your verification code for creating a new password is $verificationCode.','');

insert into configuration values('FREECHARGE','forgot.password.success.email.template','<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Forgot password</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:'Trebuchet MS'; margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878">Hi,<br><br>
                    Your FreeCharge account password has been changed successfully.  </p></td>
              </tr>
              
              
              <tr>
              <td bgcolor="#ffffff">
              <table cellpadding="20" cellspacing="0" border="0" width="600" >
              <tr>
              <td> <p align="center" style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878">Log in with your new password and experience<br>

 hassle-free transactions on</p>
              <p align="center"  style="padding:0px; margin:0px; padding-top:30px">
              <a href="#" style="border:0; text-decoration:none;"> <img src="http://i1.sdlcdn.com/static/klikpay/img/freecharge.png" alt="freecharge" style="padding-right:30px"/></a>
              <a href="#" style="border:0; text-decoration:none;"> <img src="http://i1.sdlcdn.com/static/klikpay/img/snapdeal.png" alt="snapdeal"/></a>
              
              </p>
              </td>
              </tr>
              </table>
             
              </td>
              </tr>
              
              <tr>
                <td><p style="font-size:15px;padding:0px; margin:0px; line-height:22px; color:#494949">Lots of Love,<br>
                    Team FreeCharge </p></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;">Download the FreeCharge app today</p>
        <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you did not request a password change,
 write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic;padding-top:10px">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>','');

insert into configuration values('SNAPDEAL','forgot.password.success.email.template','<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>Forgot password</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:'Trebuchet MS'; margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878">Hi,<br><br>
                    Your FreeCharge account password has been changed successfully.  </p></td>
              </tr>
              
              
              <tr>
              <td bgcolor="#ffffff">
              <table cellpadding="20" cellspacing="0" border="0" width="600" >
              <tr>
              <td> <p align="center" style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878">Log in with your new password and experience<br>

 hassle-free transactions on</p>
              <p align="center"  style="padding:0px; margin:0px; padding-top:30px">
              <a href="#" style="border:0; text-decoration:none;"> <img src="http://i1.sdlcdn.com/static/klikpay/img/freecharge.png" alt="freecharge" style="padding-right:30px"/></a>
              <a href="#" style="border:0; text-decoration:none;"> <img src="http://i1.sdlcdn.com/static/klikpay/img/snapdeal.png" alt="snapdeal"/></a>
              
              </p>
              </td>
              </tr>
              </table>
             
              </td>
              </tr>
              
              <tr>
                <td><p style="font-size:15px;padding:0px; margin:0px; line-height:22px; color:#494949">Lots of Love,<br>
                    Team FreeCharge </p></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;">Download the FreeCharge app today</p>
        <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you did not request a password change,
 write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic;padding-top:10px">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>','');


insert into configuration values ('SNAPDEAL','upgrade.user.email.template', '<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>otp</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:Trebuchet MS;margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>
                   Your one-time verification code to create a new password is <strong>$verificationCode</strong>. 

   This code ensures that only you have access to your account. </p></td>
              </tr>
              
              
              <tr>
              <td bgcolor="#ffffff">
              <table cellpadding="15" cellspacing="0" border="0" width="600" >
              <tr>
              <td>
              <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;">
Verification Code: <strong>$verificationCode</strong><br>

Valid for:<strong> 30 minutes</strong>
              </p>
            
              </td>
              </tr>
              </table>
             
              </td>
              </tr>
              
              <tr>
                <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>
                    Team FreeCharge </p></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;"s>Download the FreeCharge app today</p>
       <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t requested for password change,<br>
 write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>', '');


insert into configuration values ('FREECHARGE','upgrade.user.email.template', '<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>otp</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:Trebuchet MS;margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:24px; color:#787878">Hi,<br><br>
                   Your one-time verification code to create a new password is <strong>$verificationCode</strong>. 

   This code ensures that only you have access to your account. </p></td>
              </tr>
              
              
              <tr>
              <td bgcolor="#ffffff">
              <table cellpadding="15" cellspacing="0" border="0" width="600" >
              <tr>
              <td>
              <p align="left" style="font-size:14px;padding:0px; margin:0px; line-height:22px; color:#787878;">
Verification Code: <strong>$verificationCode</strong><br>

Valid for:<strong> 30 minutes</strong>
              </p>
            
              </td>
              </tr>
              </table>
             
              </td>
              </tr>
              
              <tr>
                <td><p style="font-size:15px;padding:0px; margin:0px; line-height:20px; color:#494949">Lots of Love,<br>
                    Team FreeCharge </p></td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;"s>Download the FreeCharge app today</p>
       <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t requested for password change,<br>
 write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic; padding-top:10px;">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>', '');



insert into configuration values ('SNAPDEAL','account.detail.update.template','<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>account detail</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:Trebuchet MS; margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878">Hi,<br><br>
                    The registered mobile number on your FreeCharge account has been successfully updated to <strong>$mobile</strong><br>
 </p>
<p style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878; padding-top:20px;">Log in to FreeCharge app or website for more information on this.</p>

<p style="font-size:15px;padding:0px; margin:0px; line-height:22px; color:#787878; padding-top:20px;">Lots of Love,<br>
                    Team FreeCharge</p>
</td>
              </tr>
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;">Download the FreeCharge app today</p>
         <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t changed your mobile number, write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic;padding-top:10px">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>','');

insert into configuration values ('FREECHARGE','account.detail.update.template','<!doctype html>
<html>
<head>
<meta charset="utf-8">
<title>account detail</title>
</head>

<body>
<table cellpadding="0" cellspacing="0" border="0" width="600" style="font-family:Trebuchet MS; margin:0 auto; ">
  <tr>
    <td>
    <p style="padding:0px; margin:0px; padding-bottom:10px; padding-top:10px;"><img src="http://i1.sdlcdn.com/static/klikpay/img/logo.png" alt="freecharge wallet" width="170"></p></td>
  </tr>
  <tr bgcolor="#f4f5f6">
    <td><table cellpadding="0" cellspacing="5" border="0">
        <tr>
          <td><table cellpadding="0" cellspacing="15" border="0">
              <tr>
                <td><p style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878">Hi,<br><br>
                    The registered mobile number on your FreeCharge account has been successfully updated to <strong>$mobile</strong><br>
 </p>
<p style="font-size:16px;padding:0px; margin:0px; line-height:22px; color:#787878; padding-top:20px;">Log in to FreeCharge app or website for more information on this.</p>

<p style="font-size:15px;padding:0px; margin:0px; line-height:22px; color:#787878; padding-top:20px;">Lots of Love,<br>
                    Team FreeCharge</p>

</td>
              </tr>             
            </table></td>
        </tr>
      </table></td>
  </tr>
  <tr bgcolor="#fbfcfd">
    <td><p align="center" style="padding:0px; margin:0px; padding-top:40px;">Download the FreeCharge app today</p>
         <p align="center" style="padding:0px; margin:0px; padding-bottom:40px;padding-top:20px"><a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/appstore.png" alt="appstore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/googlestore.png " alt="googlestore"/></a> <a href="#" style="border:0; text-decoration:none;"><img src="http://i1.sdlcdn.com/static/klikpay/img/windowstore.png " alt="windowstore"/></a> </p></td>
  </tr>
  <tr>
    <td><p style="font-size:14px;padding:0px; margin:0px; line-height:20px; color:#494949; padding-top:10px">Find answers to account related queries <a href="#" style=" color:#494949;">here</a>. If you haven’t changed your mobile number, write <a href="#" style=" color:#494949;">here</a>. </p>
      <p style="font-size:12px;padding:0px; margin:0px; line-height:20px; color:#494949;font-weight:bold; font-style:italic;padding-top:10px">* FreeCharge balance is issued by YES Bank </p></td>
  </tr>
</table>
</body>
</html>','');



insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.login","Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.","") ;
insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.forgot.password","Your one-time verification code to register your mobile number on Freecharge account is $verificationCode. It is valid for 30 minutes.","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.mobile.verification","Your one-time verification code to register your mobile number on MobileVerification account is $verificationCode. It is valid for 30 minutes.","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.user.signup","Your verification Pin: $verificationCode","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.update.mobile","Your verification Pin: $verificationCode","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.upgrade.user","Your verification Pin: $verificationCode","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.link.account","Your verification Pin: $verificationCode","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.onecheck","Your verification Pin: $verificationCode","") ;

insert  into configuration values("FREECHARGE","sms.template.otp.templateBody.money.out","Your verification Pin: $verificationCode","") ;


insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.login","login Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.forgot.password","forget password Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.mobile.verification","moblie verification Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.user.signup","signup Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.update.mobile","mobile Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.upgrade.user","upgrade user Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.link.account","link account Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.onecheck","onecheck Your verification Pin: $verificationCode","") ;

insert  into configuration values("SNAPDEAL","sms.template.otp.templateBody.money.out","money out Your verification Pin: $verificationCode","") ;





insert  into configuration values('SNAPDEAL","verification.otp.email.subject.forgot.password','OTP code Forgot','') ;
insert  into configuration values('SNAPDEAL','verification.otp.email.subject.link.account','link account otp','') ;
insert  into configuration values('SNAPDEAL','verification.otp.email.subject.money.out','OTP code moneyOut','') ;
insert  into configuration values('FREECHARGE','verification.otp.email.subject.forgot.password','OTP code Forgot','') ;
insert  into configuration values('FREECHARGE','verification.otp.email.subject.link.account','link account otp','') ;
insert  into configuration values('FREECHARGE','verification.otp.email.subject.money.out','OTP code moneyOut','') ;
