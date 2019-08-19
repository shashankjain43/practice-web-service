use ums;
alter table email_city_esp_mapping add column created timestamp default current_timestamp,add column updated timestamp NULL default NULL ;
update email_city_esp_mapping set created=now(), updated=now();


alter table email_service_provider add column created timestamp default current_timestamp, add column updated timestamp NULL default NULL ;
update email_service_provider set created=now(),updated=now();

alter table esp_filter_city_mapping add column created timestamp default current_timestamp, add column updated timestamp NULL default NULL ;
update esp_filter_city_mapping set created=now(),updated=now();

alter table esp_profile_field add column created timestamp default current_timestamp,add column updated timestamp NULL default NULL ;
update esp_profile_field set created=now(), updated=now();

alter table newsletter_esp_mapping add column created timestamp default current_timestamp ,add column  updated timestamp NULL default NULL ;
update newsletter_esp_mapping set created=now(), updated=now();

alter table user_information add column created timestamp default current_timestamp,add column updated timestamp NULL default NULL ;
update user_information set created=now(),updated=now();

alter table user_openid_mapping add column created timestamp default current_timestamp, add column updated timestamp NULL default NULL ;
update user_openid_mapping set created=now(),updated=now();

alter table user_preference add column created timestamp default current_timestamp,  add column updated timestamp NULL default NULL ;
update user_preference set created=now(),updated=now();

alter table user_role add column created timestamp default current_timestamp, add column updated timestamp NULL default NULL;
update user_role set created=now(),updated=now();
