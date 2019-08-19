package com.snapdeal.ims.dao.aerospike.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.ims.cache.service.IUserVerificationCacheService;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.errorcodes.IMSInternalServerExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;

@Component
public class UserVerificationDetailsCacheImpl
		implements
			IUserVerificationDetailsDao {

	@Autowired
	private IUserVerificationCacheService userVerificationCacheService;

	@Override
	public void create(UserVerification userVerificationEntity) {
		if (!userVerificationCacheService.create(userVerificationEntity)) {
			throw new IMSServiceException(
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errCode(),
					IMSInternalServerExceptionCodes.GENERIC_INTERNAL_SERVER
							.errMsg());
		}
	}

	@Override
	public UserVerification getUserVerificationDetailsByCode(String code) {
		return userVerificationCacheService
				.getUserVerificationDetailsByCode(code);
	}

	@Override
	public String getUserIdbyVerificationCode(String code) {
		UserVerification uverification = getUserVerificationDetailsByCode(code);
		if (uverification != null) {
			return uverification.getUserId();
		}
		return null;
	}
	
	@Override
	public boolean deleteUserVerificationCode(String code){
		return userVerificationCacheService
				.deleteUserVerificationDetailsByCode(code);
	}
}
