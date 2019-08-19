/*
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`)
values ('global','fc.notifier.enabled','false','');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`)
values ('global','merge.card.enabled','false','');



INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
VALUES ('global', 'notifier.send.test.email', 'true', '');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
VALUES ('global', 'notifier.send.test.sms', 'true', '');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
VALUES ('global', 'notifier.test.email.id', 'tech.ims@snapdeal.com', '');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
VALUES ('global', 'notifier.test.sms.number', '9034169466', '');

INSERT INTO `ims`.`configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'default.transfertokengeneration.service.V01', 'com.snapdeal.ims.token.service.impl.TransferTokenGenerationServiceV01Impl', 'Transfer Token Generation Service V01 implementation class.');
INSERT INTO `ims`.`configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'default.transfertokengeneration.service.version', 'V01', 'Transfer Token Generation Service in use.');
INSERT INTO `ims`.`configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'transfer.token.expiry.time.in.seconds', '15', 'Transfer Token expiry time in Seconds');


INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
VALUES ('global', 'aerospike.userIdByGTokenIds.update.maxRetries', '5', '');

INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
VALUES ('global', 'aerospike.userIdByGTokenIds.retry.sleep.in.milliseconds', '10', '');

INSERT INTO `ims`.`configuration` (`config_type`, `config_key`, `config_value`, `description`) VALUES ('global', 'transfer.token.expiry.time.in.seconds', '15', 'Transfer Token expiry time in Seconds');
*/
INSERT INTO `configuration` (`config_type`, `config_key`, `config_value`, `description`)
values ('global','upgrade.status.send.blacklist.enabled','false','Flag to decide, if we should send black list upgrade status.');
