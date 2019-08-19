package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;

/**
 * 
 * @author kishan
 *
 */

public interface ITokenCacheService {

	GlobalTokenDetailsEntity getGtokenById(String gToken);

	boolean putGTokenById(GlobalTokenDetailsEntity gToken);

	boolean deleteGtokenById(String gTokenId);

	boolean deleteAllTokenByUserId(String userId);

	boolean updateGlobalTokenExpiryTime(
			GlobalTokenDetailsEntity globalTokenDetailsEntity);

	boolean deleteAllOtherToken(String userId, String globalTokenId);

	void cleanUpUserIdGtokenMap(GlobalTokenDetailsEntity gToken,boolean isGtokenLimitReached);
	
	int getGTokenIDSetSizeByUserId(String userId);

}
