CREATE PROCEDURE `deleteExpiredOTPs`()
BEGIN
    REPEAT
            DELETE FROM user_otp
            where expiry_time < (now() - INTERVAL 60 MINUTE)
            LIMIT 1000;
            UNTIL ROW_COUNT() = 0 
        END REPEAT;
    END
