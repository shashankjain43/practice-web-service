ALTER TABLE activity_log 
ADD COLUMN server_ip_address VARCHAR(128) NULL DEFAULT NULL ;

ALTER TABLE `ims`.`activity_log` ADD INDEX `ENTITYTABLE` (`entity_id` ASC),ADD INDEX `CLIENTIDTABLE` (`client_id` ASC);
