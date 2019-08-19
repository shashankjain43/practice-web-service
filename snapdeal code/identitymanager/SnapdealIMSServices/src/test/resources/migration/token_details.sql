CREATE TABLE token_details (
  token_string varchar(128) NOT NULL DEFAULT '',
  client_id varchar(128) DEFAULT NULL,
  global_token_id varchar(64) DEFAULT NULL,
  expiry_time datetime DEFAULT NULL,
  created_time timestamp NOT NULL,
  PRIMARY KEY (token_string)
);
