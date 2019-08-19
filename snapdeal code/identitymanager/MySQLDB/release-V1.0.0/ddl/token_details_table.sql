/* DDL for token_details */
DROP TABLE IF EXISTS `token_details`;
CREATE TABLE `token_details` (
    `token_string` VARCHAR(128),
    `client_id` VARCHAR(128),
    `global_token_id` VARCHAR(64),
    `expiry_time` DATETIME,
    `created_time` TIMESTAMP,
    primary key (token_string)
);
/* create index token_details_index1 on token_details (token_string); */

