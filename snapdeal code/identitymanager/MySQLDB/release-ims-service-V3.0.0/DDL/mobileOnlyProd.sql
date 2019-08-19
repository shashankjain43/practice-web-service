use ims;

ALTER TABLE user  
add platform varchar(128) DEFAULT NULL, 
add resource varchar(128) DEFAULT NULL,
lock=none;

ALTER TABLE user change column email email varchar(255) DEFAULT NULL,lock=none;

ALTER TABLE social_user
add column platform varchar(128) DEFAULT NULL,
add column resource varchar(128) DEFAULT NULL,
lock=none;

