/* New email templates for ums notifications */
UPDATE `ums`.`email_template` 
SET `subject_template` = '$subject' ,
`to` = 'debopam.basu@snapdeal.com,aditi.malhotra@snapdeal.com,amit.kurseja@snapdeal.com,ums-qa@snapdeal.com'
WHERE `name` = 'UMSCheckpointBreachNotification';

