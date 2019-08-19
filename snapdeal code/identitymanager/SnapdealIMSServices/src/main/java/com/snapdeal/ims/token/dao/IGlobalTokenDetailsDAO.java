package com.snapdeal.ims.token.dao;

import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;

public interface IGlobalTokenDetailsDAO {
	
	
	public void save(GlobalTokenDetailsEntity globalTokenDetailsEntity);

	public GlobalTokenDetailsEntity getGlobalTokenById(String globalTokenId);

	public void delete(String globalTokenId);

	public void deleteAllTokenForUser(String userId);
	
	public void updateExpiryDate(GlobalTokenDetailsEntity globalTokenDetailsEntity);

	boolean deleteAllOtherToken(String userId, String globalTokenId);
	
	int getGTokenIDSetSizeByUserId(String userId);
	
}