delete from `configuration`;

insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","environment","PRODUCTION", "Environment");

/* Token Generation Service */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","default.tokengeneration.service.version","V01", "Token Generation Service version in use.");

insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","default.tokengeneration.service.V01","com.snapdeal.ims.token.service.impl.TokenGenerationServiceV01Impl", "Token Generation Service V01 implementaion class.");

/* Global Token Generation Service */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","default.globaltokengeneration.service.version","V01", "Global Token Generation Service in use.");
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","default.globaltokengeneration.service.V01","com.snapdeal.ims.token.service.impl.GlobalTokenGenerationServiceV01Impl", "Global Token Generation Service V01 implementation class.");

/* Storing cipher unique key "SnapdealUniqueKey" */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global", "cipher.unique.key", "U25hcGRlYWxVbmlxdWVLZXk=", "Cipher Key used to encrypt and decrypt.");

/* Token expiry gets renewed if there is any valid access via token. */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global", "days.to.renew.token", 5, "Days before which the token needs to be renewed.");

/* Client platform specific configuration. */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("WEB", "global.token.expiry.time", "30", "Global Token expiry time for WEB platform client.");
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("APP", "global.token.expiry.time", "365", "Global Token expiry time for WEB platform client.");
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("WAP", "global.token.expiry.time", "30", "Global Token expiry time for WEB platform client.");

/* Default verification code expiry time is 3 days in munutes (3 * 24 * 60) */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","default.verification.code.expiry.in.minutes", "4320", "Default verification code expiry time in minutes.");
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","verification.code.expiry.in.minutes.new.user","4320", "Validity of verification code in case of new user.");
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","verification.code.expiry.in.minutes.forgot.password","4320", "Validity of verification code in case of forget password.");

/*User locking configuration on login*/
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','maximum.number.of.login.attempts','5','Maximum number of login attempts');
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','user.locked.for.time','900000', 'Time duration for which user will be locked');
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','user.locking.window.frame','600000', 'Time duration within which if we attempt n number of invalid logins');

/*Request time out configuration*/
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ('global','request.expiry.time','600', 'Request expiry time');

/*Aerospike Seed data*/
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.cluster","52.74.73.77:3000,52.74.231.23:3000", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.connection.timeout","100000", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.default.read.maxRetries","2", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.default.read.sleepBetweenRetries","3", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.eviction.transaction.iteration.limit","999", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.eviction.transaction.size","1001", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.inconsistent.key.eviction.enabled","true", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.maxSocketIdle","", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.maxThreads","300", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","aerospike.sharedThreadPool","false", null);

/* Verification redirection for testing purpose. */
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","email.verification.testmode.id","Tech.ims@snapdeal.com", null);
insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) values ("global","email.verification.testmode","0", null);

INSERT INTO `ims`.`configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'login.socialuser.validate.enable', 'true', 'enable and disbale validations ins social requestDto');
