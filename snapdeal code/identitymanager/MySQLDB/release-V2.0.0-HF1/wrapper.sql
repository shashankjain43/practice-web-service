/* Create Database if this doesn't exists */
USE ims_stg2;

/* DDL scripts to create tables */
SOURCE ddl/social_user_table_changes.sql
SOURCE ddl/indexes.sql

/* Initial DML script to load default configuration */
SOURCE dml/configuration.sql
