use ims;
SET GLOBAL event_scheduler = ON;

DELIMITER $$
create procedure deleteExpiredOTPs()
	BEGIN
	REPEAT
			DELETE FROM user_otp
			where expiry_time < (now() - INTERVAL 60 MINUTE)
			LIMIT 1000;
			UNTIL ROW_COUNT() = 0 
		END REPEAT;
	END$$
DELIMITER ;



CREATE EVENT delete_expired_otp  ON SCHEDULE EVERY '1' DAY STARTS '2015-08-05 02:00:00'  DO call deleteExpiredOTPs();
