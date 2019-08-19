DROP TABLE IF EXISTS `global_token_details`;
CREATE TABLE `global_token_details` (
    `global_token_id` VARCHAR(64),
    `global_token_string` VARCHAR(128),
    `user_agent` VARCHAR(128),
    `user_identifier` VARCHAR(32),
    `expiry_time` DATETIME,
    `user_id` VARCHAR(64),
    `created_time` TIMESTAMP,
    primary key (global_token_id)
);
create index global_token_details_index1 on global_token_details (global_token_string);

