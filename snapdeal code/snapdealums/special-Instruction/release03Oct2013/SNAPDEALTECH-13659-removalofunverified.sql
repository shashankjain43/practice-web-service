DELIMITER //
CREATE PROCEDURE `ums`.unverified_users()
BEGIN
DECLARE i INT DEFAULT 0;
DECLARE done BOOLEAN default false;
DECLARE curs1 CURSOR FOR select distinct(ur1.id) from `ums`.user_role ur1, `ums`.user_role ur2 where ur1.user_id=ur2.user_id and ur1.id<>ur2.id and ur1.role_code='unverified' and ur2.role_code='registered';
DECLARE CONTINUE HANDLER FOR NOT FOUND SET done=TRUE;
OPEN curs1;
outer_loop: LOOP
FETCH curs1 INTO i;
        delete FROM `ums`.user_role where id = i;
if done then leave outer_loop;
end if;
END LOOP;

CLOSE curs1;

END
//

call `ums`.unverified_users();
