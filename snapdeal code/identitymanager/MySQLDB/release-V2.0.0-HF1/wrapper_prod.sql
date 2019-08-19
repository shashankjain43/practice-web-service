/* Create Database if this doesn't exists */
USE ims;

/* DDL scripts to create tables */
SOURCE ddl/activity_log_table_changes.sql
SOURCE ddl/client_table_changes.sql
SOURCE ddl/social_user_table_changes.sql
SOURCE ddl/upgrade_table_changes.sql
SOURCE ddl/indexes.sql

/* Initial DML script to load default configuration */
SOURCE dml/configuration.sql
