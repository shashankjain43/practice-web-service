ALTER TABLE `freeze_account` 
ADD INDEX `freeze_account_user_id_IN1` (`user_id` ASC);

ALTER TABLE `social_user` 
ADD INDEX `social_user_user_id_IN1` (`user_id` ASC);

ALTER TABLE `user` 
ADD INDEX `user_sd_user_id_IN1` (`sd_user_id` ASC);

ALTER TABLE `user`
ADD INDEX `user_fc_user_id_IN1` (`fc_user_id` ASC);

ALTER TABLE user_otp 
ADD INDEX user_otp_IN1 (client_id, otp_type, user_id ASC);
