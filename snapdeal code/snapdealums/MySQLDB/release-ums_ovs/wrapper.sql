-- Add two new column in user_address for soft delete and ovs status
ALTER TABLE `ums`.`user_address` ADD COLUMN `isActive` TINYINT(1) NOT NULL DEFAULT '1', ADD COLUMN `status` ENUM ('UNKNOWN', 'SAFE', 'UNSAFE') DEFAULT 'UNKNOWN';
