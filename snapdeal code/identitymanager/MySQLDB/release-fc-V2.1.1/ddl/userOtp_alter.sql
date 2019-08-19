
--ALTER TABLE user_otp 
--ADD COLUMN send_otp_by VARCHAR(45) NOT NULL AFTER `token`;



ALTER TABLE user_otp 
CHANGE COLUMN send_otp_by send_otp_by VARCHAR(45) NOT NULL DEFAULT 'SNAPDEAL' ;

