
Drop table if exists `action_audit`;

create table `action_audit`(
 `client_name` varchar(100),
 `client_role` varchar(50),
  `action` varchar(100),
 `action_key_type` varchar(100),
 `action_key` varchar(255),
 `reason` varchar(100),
 `action_time` datetime);

