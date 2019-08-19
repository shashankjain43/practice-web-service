/* Create Database if this doesn't exists */
CREATE DATABASE IF NOT EXISTS ims_stg2;

USE ims_stg2;

SET GLOBAL event_scheduler = ON;
SOURCE ddl/event_schedluar.sql
SOURCE ddl/ExpirationOTP.sql