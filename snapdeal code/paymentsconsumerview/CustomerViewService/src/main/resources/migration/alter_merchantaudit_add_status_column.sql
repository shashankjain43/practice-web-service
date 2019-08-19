use merchant_view1;
Alter table merchant_view_audit DROP Column active;

Alter table merchant_view_audit add column status Enum('SUBMITTED','SUCCESS','PENDING','BLOCKED') Default 'PENDING';

use merchant_view2;
Alter table merchant_view_audit DROP Column active;

Alter table merchant_view_audit add column status Enum('SUBMITTED','SUCCESS','PENDING','BLOCKED') Default 'PENDING';

use merchant_view3;
Alter table merchant_view_audit DROP Column active;

Alter table merchant_view_audit add column status Enum('SUBMITTED','SUCCESS','PENDING','BLOCKED') Default 'PENDING';
