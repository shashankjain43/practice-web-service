ALTER TABLE bulk_refund MODIFY COLUMN viewed tinyint(1) NULL;
ALTER TABLE bulk_refund MODIFY COLUMN upload_status varchar(32) NULL;
ALTER TABLE bulk_refund ADD COLUMN user_login_name varchar(255) NULL;
ALTER TABLE bulk_refund ADD COLUMN file_idem_key varchar(25) NULL;
ALTER TABLE bulk_refund CHANGE created_on created_on timestamp DEFAULT CURRENT_TIMESTAMP NOT NULL AFTER file_idem_key;
ALTER TABLE bulk_refund ADD COLUMN  updated_on timestamp NULL DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP;
create index mid_index on bulk_refund (merchant_id);
