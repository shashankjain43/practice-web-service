CREATE EVENT delete_expired_otp  ON SCHEDULE EVERY '1' DAY STARTS '2015-08-12 02:00:00'  DO call deleteExpiredOTPs();
