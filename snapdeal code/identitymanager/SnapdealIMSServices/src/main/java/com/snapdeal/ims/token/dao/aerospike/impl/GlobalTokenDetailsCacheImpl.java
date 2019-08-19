package com.snapdeal.ims.token.dao.aerospike.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.service.ITokenCacheService;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.ims.response.GtokenSizeResponse;
import com.snapdeal.ims.token.dao.IGlobalTokenDetailsDAO;
import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
@Slf4j
public class GlobalTokenDetailsCacheImpl implements IGlobalTokenDetailsDAO {

	@Autowired
	ITokenCacheService imsCacheService;
	

	@Override
	@Timed
	@Marked
	@Logged
	public void save(GlobalTokenDetailsEntity globalTokenDetailsEntity) {
		boolean isWriteSuccessful = imsCacheService
				.putGTokenById(globalTokenDetailsEntity);
		if (!isWriteSuccessful) {
			throw new IMSServiceException(
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errCode(),
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errMsg());
		}
	}
	


	@Override
	@Timed
	@Marked
	@Logged
	public GlobalTokenDetailsEntity getGlobalTokenById(String globalTokenId) {
		return imsCacheService.getGtokenById(globalTokenId);
	}

	@Override
	@Timed
	@Marked
	@Logged
	public void delete(String globalTokenId) {
		boolean isDeleteSucessful = imsCacheService
				.deleteGtokenById(globalTokenId);
		if (!isDeleteSucessful) {
			throw new IMSServiceException(
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errCode(),
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errMsg());
		}
	}

	@Override
	@Timed
	@Marked
	@Logged
	// throw exception in case unable to delete
	public boolean deleteAllOtherToken(String userId, String globalTokenId) {
		boolean isDeleteSucessful = imsCacheService
				.deleteAllOtherToken(userId,globalTokenId);
		if (!isDeleteSucessful) {
			throw new IMSServiceException(
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errCode(),
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errMsg());
		}
		return isDeleteSucessful;
	}
	
	@Override
	@Timed
	@Marked
	@Logged
	// throw exception in case unable to delete
	public void deleteAllTokenForUser(String userId) {
		boolean isDeleteSucessful = imsCacheService
				.deleteAllTokenByUserId(userId);
		if (!isDeleteSucessful) {
			throw new IMSServiceException(
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errCode(),
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errMsg());
		}
	}

	@Override
	@Timed
	@Marked
	@Logged
	public void updateExpiryDate(
			GlobalTokenDetailsEntity globalTokenDetailsEntity) {
		boolean isUpdateSuccessful = imsCacheService
				.updateGlobalTokenExpiryTime(globalTokenDetailsEntity);
		if (!isUpdateSuccessful) {
         log.warn("Renew of global token expiry failed");
         /*
          * throw new IMSServiceException(
          * IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
          * .errCode(),
          * IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
          * .errMsg());
          */
		}
	}



	@Override
	public int getGTokenIDSetSizeByUserId(String userId) {
		
		return imsCacheService.getGTokenIDSetSizeByUserId(userId);
	}
}