CREATE TABLE user (
  user_id varchar(128),
  sd_user_id int(11),
  fc_user_id int(11),
  sd_fc_user_id int(11),
  originating_src varchar(64),
  is_enabled tinyint(1),
  email varchar(255),
  password varchar(255),
  is_user_set_password tinyint(1),
  mobile_number varchar(20),
  status varchar(64),
  is_google_user tinyint(1),
  is_facebook_user tinyint(1),
  is_email_verified tinyint(1),
  is_mobile_verified tinyint(1),
  first_name varchar(128),
  last_name varchar(128),
  middle_name varchar(128),
  display_name varchar(128),
  gender varchar(128),
  dob date,
  language_pref varchar(64),
  purpose varchar(255),
  created_time datetime,
  updated_time timestamp ,
  create_wallet_status varchar(64)
);

CREATE TABLE upgrade (
  id bigint(20),
  email varchar(256),
  initial_state varchar(64),
  current_state varchar(64),
  upgrade_status varchar(64),
  user_id varchar(128),
  fc_id bigint(20),
  sd_id int(11),
  dont_upgrade tinyint(1),
  created_date datetime,
  updated_date timestamp,
  upgrade_channel varchar(64),
  upgrade_source varchar(64)
  );  

CREATE TABLE user_archive (
  user_id varchar(128),
  sd_user_id int(11),
  fc_user_id int(11),
  sd_fc_user_id int(11),
  originating_src varchar(64),
  is_enabled tinyint(1),
  email varchar(255),
  password varchar(255),
  is_user_set_password tinyint(1),
  mobile_number varchar(20),
  status varchar(64),
  is_google_user tinyint(1),
  is_facebook_user tinyint(1),
  is_email_verified tinyint(1),
  is_mobile_verified tinyint(1),
  first_name varchar(128),
  last_name varchar(128),
  middle_name varchar(128),
  display_name varchar(128),
  gender varchar(128),
  dob date,
  language_pref varchar(64),
  purpose varchar(255),
  created_time datetime,
  updated_time timestamp ,
  create_wallet_status varchar(64)
);

insert into user(user_id,email)values('123','test123@gmail.com');
insert into user(user_id,email)values('456','test456@gmail.com');
insert into upgrade(email,user_id)values('test123@gmail.com','123');

 insert into upgrade(user_id,email,upgrade_status) values('100','john@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('101','john01@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('102','john02@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('103','john03@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('104','john04@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('105','john05@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('106','john06@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('107','john07@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('108','john08@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('109','john09@example.com','UPGRADE_COMPLETED');
 insert into upgrade(user_id,email,upgrade_status) values('110','john10@example.com','UPGRADE_COMPLETED');