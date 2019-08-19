insert into configuration values("FREECHARGE","send.email.subject.freecharge","Welcome to FreeCharge","");
insert into configuration values("global","verification.otp.email.template","Your verification Pin: $verificationCode","");
update configuration set config_value = "SNAPDEAL<noreply@snapdeals.co.in>" where config_key = "snapdeal.email.client.fromTo.emailId";
insert into configuration values("global","verification.otp.email.subject","OTP Code","");
