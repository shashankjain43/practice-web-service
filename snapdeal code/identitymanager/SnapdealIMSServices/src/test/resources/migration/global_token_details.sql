CREATE TABLE global_token_details (
  global_token_id varchar(64) NOT NULL DEFAULT '',
  global_token_string varchar(128) DEFAULT NULL,
  user_agent varchar(128) DEFAULT NULL,
  user_identifier varchar(32) DEFAULT NULL,
  expiry_time datetime DEFAULT NULL,
  user_id varchar(64) DEFAULT NULL,
  created_time timestamp NOT NULL,
  PRIMARY KEY (global_token_id)
);
