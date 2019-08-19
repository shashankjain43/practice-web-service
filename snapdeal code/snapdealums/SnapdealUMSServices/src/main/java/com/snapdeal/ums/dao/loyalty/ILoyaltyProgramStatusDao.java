package com.snapdeal.ums.dao.loyalty;

import com.snapdeal.ums.core.entity.LoyaltyProgramStatusDO;

public interface ILoyaltyProgramStatusDao {
	
	LoyaltyProgramStatusDO getLoyaltyStatus(int id);
	
	LoyaltyProgramStatusDO getLoyaltyStatus(String name);
	
	


}
