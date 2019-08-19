
Alter table merchant_view_audit DROP Column active;

Alter table merchant_view_audit add column status Enum('SUBMITTED','SUCCESS','PENDING','BLOCKED') Default 'PENDING';


