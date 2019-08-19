INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'upgrade.percentage', '0', 'Percentage wise upgrade details');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'upgrade.skip', 'true', 'Detail on whether skip for upgrade');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','default.globaltokengeneration.service.V02','com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV02Impl','');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','default.tokengeneration.service.V02','com.snapdeal.ims.token.service.impl.TokenGenerationServiceV02Impl','');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'forget.password.email.verification.url', 'https://www.freecharge.in/app/passwordrecoverysd.htm?esource={0}', 'forgot password email verification url for freecharge');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.from.emailid', 'FreeCharge <noreply@freecharge.in>', 'Freecharge from mail Id while sending email.');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.replyto.emailid', 'noreply@freecharge.in', 'Freecharge reply to mail ID while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.subject', 
'FreeCharge Password Recovery Details', 'Freecharge subject while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.from.emailid', 'Snapdeal<noreply@snapdeals.co.in>', 'Snapdeal from mail Id while sending email.');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.replyto.emailid', 'noreply@snapdeals.co.in', 'Snapdeal reply to mail ID while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.subject',
'Welcome to Snapdeal', 'Snapdeal subject while sending email');



INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.from.emailid', 'FreeCharge <noreply@freecharge.in>', 'Freecharge from mail Id while sending email.');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.replyto.emailid', 'noreply@freecharge.in', 'Freecharge reply to mail ID while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('FREECHARGE', 'send.email.subject', 
'FreeCharge Password Recovery Details', 'Freecharge subject while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.from.emailid', 'Snapdeal<noreply@snapdeals.co.in>', 'Snapdeal from mail Id while sending email.');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.replyto.emailid', 'noreply@snapdeals.co.in', 'Snapdeal reply to mail ID while sending email');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('SNAPDEAL', 'send.email.subject',
'Welcome to Snapdeal', 'Snapdeal subject while sending email');
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','validate.social.source','false','Enable/disable social validation.');
