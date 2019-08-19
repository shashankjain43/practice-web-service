/* Create Database if this doesn't exists */
CREATE DATABASE IF NOT EXISTS ims;

USE ims;

/* DDL scripts to create tables */

SOURCE ddl/configuration_table.sql
SOURCE ddl/client.sql
SOURCE ddl/freeze_account.sql
SOURCE ddl/user_otp.sql
SOURCE ddl/ActivityLog.sql
SOURCE ddl/lock_user.sql

/* Initial DML script to load default configuration */

SOURCE dml/configuration.sql
SOURCE dml/emailTemplate.sql
