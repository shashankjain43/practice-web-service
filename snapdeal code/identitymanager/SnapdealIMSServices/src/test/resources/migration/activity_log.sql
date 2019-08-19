CREATE TABLE activity_log (
  id bigint(20) NOT NULL AUTO_INCREMENT,
  activity_type varchar(128) DEFAULT NULL,
  activity_subtype varchar(128) DEFAULT NULL,
  status varchar(20) NOT NULL DEFAULT 'FAILURE',
  ip_address varchar(128) DEFAULT NULL,
  mac_address varchar(128) DEFAULT NULL,
  client_id varchar(128) DEFAULT NULL,
  entity_id varchar(128) DEFAULT NULL,
  created_date timestamp NOT NULL,
  PRIMARY KEY (id)
);

ALTER TABLE activity_log 
ADD COLUMN server_ip_address VARCHAR(128) NULL DEFAULT NULL ;