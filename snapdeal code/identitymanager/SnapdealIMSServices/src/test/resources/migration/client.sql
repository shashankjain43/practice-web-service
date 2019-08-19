CREATE TABLE client (
  id int(11) NOT NULL AUTO_INCREMENT,
  client_id varchar(128) NOT NULL,
  client_name varchar(128) NOT NULL,
  merchant varchar(128) NOT NULL,
   client_type varchar(128) NOT NULL ,
  client_platform varchar(128) NOT NULL DEFAULT 'WEB',
  client_key varchar(128) NOT NULL,
  client_status varchar(128) NOT NULL DEFAULT 'ACTIVE',
  created_time datetime NOT NULL DEFAULT CURRENT_TIMESTAMP,
  updated_time timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (id),
  UNIQUE KEY client_id_uk(client_id),
  UNIQUE KEY client_name_merchant_uk (client_name,merchant)
);
