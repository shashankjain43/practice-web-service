ALTER TABLE client
ADD COLUMN ims_internal_alias VARCHAR(128) NULL AFTER client_platform;
