use ums;
alter table sd_wallet add column version int(11) default 0 after reference_id;
