alter table social_user modify column social_src enum('FACEBOOK','GOOGLE') NOT NULL;
alter table social_user drop PRIMARY KEY ,add PRIMARY KEY (`user_id`,`social_src`,`merchant`);
/*
alter table social_user modify column social_src enum('FACEBOOK','GOOGLE') NOT NULL;
alter table social_user drop PRIMARY KEY ,add PRIMARY KEY (`user_id`,`social_src`,`merchant`);
*/
ALTER TABLE social_user 
CHANGE COLUMN photo_url photo_url VARCHAR(1024)  DEFAULT NULL ,
CHANGE COLUMN about_me about_me VARCHAR(1024) DEFAULT NULL ;
/*
ALTER TABLE social_user CHANGE COLUMN photo_url photo_url VARCHAR(1024)  DEFAULT NULL, CHANGE COLUMN about_me about_me VARCHAR(1024) DEFAULT NULL;
*/
ALTER TABLE social_user CHANGE COLUMN social_token social_token VARCHAR(512)  DEFAULT NULL;
