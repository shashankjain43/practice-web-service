alter table user modify column status enum('UNVERIFIED','GUEST','REGISTERED','TEMP') not null default 'UNVERIFIED';

