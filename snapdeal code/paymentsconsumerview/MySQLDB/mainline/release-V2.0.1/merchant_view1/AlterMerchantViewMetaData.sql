ALTER TABLE `txn_details` 
ADD COLUMN `txn_meta_data` TEXT NULL AFTER `created_on`,
ADD COLUMN `payable_meta_data` TEXT NULL AFTER `txn_meta_data`;
