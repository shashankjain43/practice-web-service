CREATE TABLE configuration (
  config_type varchar(128) NOT NULL DEFAULT '' COMMENT 'Type of configuration, this will clasify the configuration.',
  config_key varchar(128) NOT NULL DEFAULT '' COMMENT 'Configuration key.',
  config_value varchar(128) DEFAULT NULL COMMENT 'Configuration value.',
  description varchar(128) DEFAULT NULL COMMENT 'Configuration description information.',
  PRIMARY KEY (config_key,config_type)
);
