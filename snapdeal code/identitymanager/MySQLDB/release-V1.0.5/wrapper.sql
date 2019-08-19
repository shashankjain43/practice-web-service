/* Create Database if this doesn't exists */
CREATE DATABASE IF NOT EXISTS ims_stg2;

USE ims_stg2;

/* DDL scripts to create tables */
drop table if exists token_details;

drop table if exists global_token_details;

drop table if exists user_verification;

SOURCE ddl/ims_user.sql
SOURCE ddl/ims_social_user.sql
SOURCE ddl/client_table_changes.sql
SOURCE ddl/user_table_changes.sql

/* Initial DML script to load default configuration */
SOURCE dml/configuration.sql



