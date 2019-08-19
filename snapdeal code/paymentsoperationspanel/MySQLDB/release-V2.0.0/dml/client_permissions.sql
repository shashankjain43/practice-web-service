


Alter  TABLE `client_permissions` drop primary key;

Alter Table `client_permissions` add primary key(`email_id`,`merchant_name`,`corp_id`);

