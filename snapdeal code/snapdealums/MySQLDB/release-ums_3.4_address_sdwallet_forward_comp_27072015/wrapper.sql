/* drop constraints for decoupling user from ums */
ALTER TABLE `ums`.`user_address` DROP FOREIGN KEY `FK_user_address_u` ;
ALTER TABLE `ums`.`sd_wallet` DROP FOREIGN KEY `FK_sd_wallet_user_id` ;
ALTER TABLE `ums`.`sd_wallet_history` DROP FOREIGN KEY `FK_sd_wallet_history_user_id` ;

/* IMS credentials */
INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('ims.userId.benchmark', '130000000');
INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('ims.web.service.ip', 'identity.snapdeal.com');
INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('ims.web.service.port', '443');
INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('ims.web.service.client.key', 'pz#4lqcbonue');
INSERT INTO `ums`.`ums_property` (`name`, `value`) VALUES ('ims.web.service.client.id', '8798D15008067904');




