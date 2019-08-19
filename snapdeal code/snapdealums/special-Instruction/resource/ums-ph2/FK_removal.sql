use snapdeal;
alter table activity drop foreign key FK_user_id;
alter table sd_cash_history drop foreign key sd_cash_history_ibfk_1;
alter table affiliate drop foreign key FK_affiliates;
alter table vendor drop foreign key FK_vendor_user;
alter table role_zone_mapping drop foreign key FK_role_zone_mapping;
alter table deal_transaction drop foreign key FK_deal_trans_user;
alter table group_deal_comment drop foreign key FK_group_deal_comment1;

use ums;
alter table mobile_subscriber drop foreign key FK_mobile_subscriber_zone;
alter table email_subscriber drop foreign key FK_email_subscriber_zone;
alter table newsletter_esp_mapping drop foreign key FK_newsletter_esp_mapping_city;
alter table email_subscriber_detail add constraint fk_esd_subscriberprofile foreign key (subscriber_profile_id) references subscriber_profile(id);


