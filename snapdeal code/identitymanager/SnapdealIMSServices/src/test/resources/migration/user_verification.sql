CREATE TABLE user_verification (
  id int(11) NOT NULL AUTO_INCREMENT,
  code varchar(128) NOT NULL,
  user_id varchar(128) NOT NULL,
  purpose varchar(20) NOT NULL,
  code_expiry_time datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  created_time datetime NOT NULL DEFAULT '0000-00-00 00:00:00',
  updated_time timestamp NOT NULL,
  PRIMARY KEY (id),
  UNIQUE KEY code_uk (code),
);
