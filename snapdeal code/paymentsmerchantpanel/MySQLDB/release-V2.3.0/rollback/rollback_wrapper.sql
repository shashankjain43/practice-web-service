alter table bulk_refund drop column user_login_name;
alter table bulk_refund drop column file_idem_key;
alter table bulk_refund drop column updated_on;
ALTER TABLE bulk_refund MODIFY COLUMN viewed tinyint(1) NOT NULL;
ALTER TABLE bulk_refund MODIFY COLUMN upload_status varchar(32) NOT NULL;
