alter table user add column create_wallet_status enum('NOT_CREATED', 'IN_PROGRESS', 'CREATED', 'FAILED') default 'NOT_CREATED';
