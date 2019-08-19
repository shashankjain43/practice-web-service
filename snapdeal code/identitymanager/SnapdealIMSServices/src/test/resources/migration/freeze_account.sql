CREATE TABLE freeze_account (
  user_id varchar(256) NOT NULL,
  otp_type varchar(25) NOT NULL,
  expiry_time datetime NOT NULL,
  freeze_reason varchar(256) NOT NULL,
  is_deleted varchar(20) NOT NULL DEFAULT 'false'
);
