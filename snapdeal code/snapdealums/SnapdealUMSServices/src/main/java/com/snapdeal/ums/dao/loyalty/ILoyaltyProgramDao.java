package com.snapdeal.ums.dao.loyalty;

import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyUserDetailDO;


public interface ILoyaltyProgramDao
{

    LoyaltyProgramDO getLoyaltyProgram(int id);

    LoyaltyProgramDO getLoyaltyProgram(String name);

}
