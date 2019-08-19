update configurations set config_value='30.0.0.110:3000,30.0.0.118:3000,30.0.0.109:3000' where config_key='aerospike.cluster';

insert into client(client_id, client_name, merchant, client_type, client_platform, client_key, client_status, created_time, updated_time) Values('BB6L0FF7BXA87C82', 'IMS Internal', 'SNAPDEAL', 'USER_FACING', 'WEB', '!#^1y7x73re$', 'ACTIVE', now(), now());

insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
    values ("BB6L0FF7BXA87C82","create.user.email.verification.url","www.snapdeal.com/verifyuser?vc={0}", null);

insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
    values ("BB6L0FF7BXA87C82","guest.email.verification.url","www.snapdeal.com/guestVerification?vc={0}", null);

insert into `configuration` (`config_type`, `config_key`, `config_value`, `description`) 
    values ("BB6L0FF7BXA87C82","forget.password.email.verification.url","www.snapdeal.com/reset?vc={0}", null);


DROP TABLE IF EXISTS `token_details`;

DROP TABLE IF EXISTS `global_token_details`;

DROP TABLE IF EXISTS `user_verification`;
