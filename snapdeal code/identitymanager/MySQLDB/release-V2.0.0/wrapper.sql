USE ims;

/* DDL scripts to create tables */
SOURCE ddl/upgrade.sql
SOURCE ddl/upgradehistory.sql
SOURCE ddl/user_table_changes.sql
SOURCE ddl/social_user_table_changes.sql
SOURCE ddl/activity_log_table_changes.sql

/* Initial DML script to load default configuration */
SOURCE dml/emailTemplateForFC.sql
SOURCE dml/configuration.sql
