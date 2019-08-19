alter table social_user add column merchant enum('SNAPDEAL','FREECHARGE','ONECHECK') NOT NULL;
alter table social_user modify column social_src enum('FACEBOOK','GOOGLE') NOT NULL;
alter table social_user drop PRIMARY KEY ,add PRIMARY KEY (`user_id`,`social_src`,`merchant`);
