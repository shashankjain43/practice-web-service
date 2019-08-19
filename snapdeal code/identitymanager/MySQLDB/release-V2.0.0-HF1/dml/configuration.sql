INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.apiurl.valuefirst', 'http://api.myvaluefirst.com/psms/servlet/psms.Eservice2', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.template.otp.templateId', '1', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.template.otp.channelId', '2', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.template.otp.templateName', 'otp', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.template.otp.templateBody', 'Your verification Pin: $verificationCode', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.template.otp.dndScrubbingOn', '0', '');
/*
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'sms.apiurl.valuefirst', 'http://api.myvaluefirst.com/psms/servlet/psms.Eservice2', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'verify.success.message', 'SUCCESS', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'verify.failure.message', 'FAILURE', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'snapdeal.email.client.reply.emailId', 'snapdeal@snapdeal.com', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'snapdeal.email.client.fromTo.emailId', 'noreply@snapdeals.co.in', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'snapdeal.email.client.verificationText', 'Your verification pin is ', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'snapdeal.email.client.client.name', 'snapdeal', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'snapdeal.email.client.textContent', 'hi', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'otp.length', '4', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'otp.expiryInMins', '15', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'otp.reSendAttemptsLimit', '3', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'otp.invalidAttemptsLimit', '2', '');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`) VALUES ('global', 'user.blockTimeInMins', '5');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.from.emailid', 'FreeCharge <noreply@freecharge.in>', 'Freecharge from mail Id while sending email.');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.replyto.emailid', 'noreply@freecharge.in', 'Freecharge reply to mail ID while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.subject', 
'FreeCharge Password Recovery Details', 'Freecharge subject while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.from.emailid', 'Snapdeal<noreply@snapdeals.co.in>', 'Snapdeal from mail Id while sending email.');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.replyto.emailid', 'noreply@snapdeals.co.in', 'Snapdeal reply to mail ID while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.subject',
'Welcome to Snapdeal', 'Snapdeal subject while sending email');

Update `configuration` set `config_value`='https://www.freecharge.in/app/passwordrecoverysd.htm?esource={0}' where config_key = 'forget.password.email.verification.url';


insert into configuration values("global","test.otpNumber.fix","false","makike fix otp number fix for testing purposes");
insert into configuration VALUES("global","fix.otp.for.testing","false","");
insert into configuration VALUES("global","reset.password.send.otp.on.mobile","true","");

insert into configuration VALUES("global","reset.password.send.otp.on.email","true","");
*/
insert into configuration values("golbal","sd.money.wallet.client.name","SD_MONEY_WALLET","SD money client name.");
