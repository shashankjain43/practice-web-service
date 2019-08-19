use ims;

drop table if exists token_details;

drop table if exists global_token_details;

drop table if exists user_verification;

SOURCE ddl/ims_user.sql
SOURCE ddl/ims_social_user.sql
SOURCE ddl/client_alteration.sql
SOURCE ddl/user_table_changes.sql

/* Initial DML script to load default configuration */
SOURCE dml/configuration.sql

