/* DDL script for configuration table. */
DROP TABLE IF EXISTS `configuration`;

CREATE TABLE `configuration` (
    `config_type` varchar(128) COMMENT 'Type of configuration, this will clasify the configuration.',
    `config_key` varchar(128) COMMENT 'Configuration key.',
    `config_value` blob COMMENT 'Configuration value.',
    `description` varchar(128) COMMENT 'Configuration description information.',
    primary key (config_key, config_type)
);
