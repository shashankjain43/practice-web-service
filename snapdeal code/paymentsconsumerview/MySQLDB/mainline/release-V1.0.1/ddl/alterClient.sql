ALTER TABLE `client` 
DROP COLUMN `client_type`,
CHANGE COLUMN `merchant` `merchant` VARCHAR(30) NOT NULL ,
ADD COLUMN `client_role` ENUM('ADMIN','USER') NOT NULL DEFAULT 'USER' AFTER `updated_time`;

ALTER TABLE `global_transaction` 
CHANGE COLUMN `tsm_commit_time` `tsm_created_time` DATETIME NULL DEFAULT NULL ,
ADD COLUMN `tsm_updated_time` DATETIME NULL DEFAULT NULL AFTER `transaction_status`;


INSERT INTO `client` VALUES (1,'1','snapdeal','snapdeal','WEB','snapdeal','ACTIVE','2015-07-17 17:19:42','2015-07-17 11:49:42','ADMIN');
