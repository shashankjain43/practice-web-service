CREATE TABLE user_otp (
  user_id varchar(127),
  mobile_number varchar(10),
  email varchar(256),
  otp varchar(6),
  otp_id varchar(127),
  invalid_attempts tinyint(4),
  resend_attempts tinyint(4),
  expiry_time datetime,
  otp_type varchar(25),
  otp_status varchar(127),
  created_date datetime ,
  updated_date datetime ,
  client_id varchar(250),
  token varchar(250),
  send_otp_by varchar(45),
  );

  insert into user_otp(user_id,otp_id,mobile_number,email) values('123','aaaaa','88888888','test123@gmail.com');
  insert into user_otp(user_id,otp_id,mobile_number,email) values('456','bbbbb','99999999','test456@gmail.com');
  insert into user_otp(user_id,otp_id,mobile_number,email) values('567','aaaaa','88888888','test123@gmail.com');
  insert into user_otp(user_id,otp_id,mobile_number,email) values('789','bbbbb','99999999','test456@gmail.com');