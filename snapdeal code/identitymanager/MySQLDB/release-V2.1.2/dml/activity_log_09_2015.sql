INSERT INTO activity_log_09_2015
SELECT *
FROM activity_log
WHERE month(created_date)=9 and
	  year(created_date)=2015;