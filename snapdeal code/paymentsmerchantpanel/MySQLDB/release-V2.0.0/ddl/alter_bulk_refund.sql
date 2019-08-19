ALTER TABLE bulk_refund ADD refund_status varchar(25) NOT NULL;
ALTER TABLE bulk_refund CHANGE COLUMN download_status upload_status varchar(32) NOT NULL;
ALTER TABLE bulk_refund MODIFY COLUMN file_name varchar(256) NULL;
