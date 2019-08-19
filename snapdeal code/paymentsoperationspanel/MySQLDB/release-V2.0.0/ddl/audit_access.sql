
drop table if exists `access_audit`;

create table `access_audit`(
`client_id` varchar(255),
`client_role` varchar(255),
`operation_mode` varchar(255),
`merchant_name` varchar(255),
`corporate_data` varchar(255),
`instrument_name` varchar(255),
`operation_time` DATETIME);
