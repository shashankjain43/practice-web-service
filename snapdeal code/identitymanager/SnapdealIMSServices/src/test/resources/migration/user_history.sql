CREATE TABLE user_history (
  user_id varchar(128),
  field varchar(128),
  old_value longblob,
  new_value longblob,
  updated_time timestamp
);
insert into user_history(user_id,field,old_value,new_value)values('12345','MOBILE_NO','9977777777','9999999999');
insert into user_history(user_id,field,old_value,new_value)values('56789','MOBILE_NO','9988888888','8888888888');