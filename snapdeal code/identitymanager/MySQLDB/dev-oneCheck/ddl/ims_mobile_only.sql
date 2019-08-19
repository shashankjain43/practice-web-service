alter table user
add column is_mobile_only tinyint(1) DEFAULT 0,
add column platform varchar(128) DEFAULT NULL,
add column resource varchar(128) DEFAULT NULL;
ALTER TABLE user change column email email varchar(255) DEFAULT NULL;

