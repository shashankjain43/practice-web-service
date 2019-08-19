/* Create Database if this doesn't exists */
DROP DATABASE IF EXISTS payments_ops_panel;

CREATE DATABASE IF NOT EXISTS payments_ops_panel;

USE payments_ops_panel;

/* DDL scripts to create tables */

SOURCE  ddl/users.sql
SOURCE  ddl/global_templates.sql
SOURCE  ddl/file_templates.sql
SOURCE  ddl/client_permissions.sql
SOURCE  ddl/file_meta_data.sql
SOURCE  ddl/task.sql
SOURCE  ddl/task_execution.sql




/* Initial DML script to load default configuration */
SOURCE  dml/users.sql
SOURCE  dml/global_templates.sql
