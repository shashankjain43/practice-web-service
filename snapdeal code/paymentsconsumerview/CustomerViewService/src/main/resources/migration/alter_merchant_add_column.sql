alter table txn_details add column user_id varchar(255);
CREATE INDEX user_id_index ON txn_details (user_id﻿) ;