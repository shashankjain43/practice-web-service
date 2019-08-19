/**
 *  Copyright 2014 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
  
package com.snapdeal.ums.cache.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.cache.services.IUMSCacheService;
import com.snapdeal.ums.cache.services.IUserCacheService;
import com.snapdeal.ums.constants.AerospikeProperties;
import com.snapdeal.ums.core.sro.user.UserSRO;

/**
 *  @version   1.0, Dec 26, 2014
 *  @author 	Sushant Taneja <sushant.taneja@snapdeal.com>
 */

@Service
public class UserCacheServiceImpl implements IUserCacheService {

    private static final Logger LOG = LoggerFactory.getLogger(UserCacheServiceImpl.class);
    
    @Autowired
    private IUMSCacheService umsCacheService;
    
    @Override
    public UserSRO getUserSROByEmail(final String email) {
        UserSRO userSRO = null;
        if(umsCacheService.isCacheEnabled()){
            userSRO = umsCacheService.getCacheClient().getUserSROByEmail(email);
        }
        return userSRO;
    }
    
	public boolean putUserSROByEmail(final String email, final UserSRO userSRO) {
		boolean writeSuccessful = false;
		if (umsCacheService.isCacheEnabled()) {
			writeSuccessful = umsCacheService.getCacheClient()
					.putUserSROByEmail(email, userSRO);
		} else {
			// This approach is for the time being! It needs to be improvised!
			umsCacheService.getCacheClient().scheduleCachedKeyEviction(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_SET.getValue(), email);
		}
		return writeSuccessful;
	}
    
    public boolean deleteUserSROByEmail(final String email){
        boolean deleteSuccessful = false;
        if(umsCacheService.isCacheEnabled()){
            deleteSuccessful = umsCacheService.getCacheClient().deleteUserSROByEmail(email);
        }else{
        	umsCacheService.getCacheClient().scheduleCachedKeyEviction(
					AerospikeProperties.Namespace.USER_NAMESPACE.getValue(),
					AerospikeProperties.Set.USER_SET.getValue(), email);
        }
        return deleteSuccessful;
    }

    @Override
    public UserSRO getUserSROById(final int userId) {
        UserSRO userSRO = null;
        if(umsCacheService.isCacheEnabled()){
            userSRO = umsCacheService.getCacheClient().getUserSROById(userId);
        }
        return userSRO;
    }
    
    @Override
    public boolean putEmailByIdMapping(final int userId, final String email){
        boolean writeSuccessful = false;
        if(umsCacheService.isCacheEnabled()){
            writeSuccessful = umsCacheService.getCacheClient().putEmailByIdMapping(userId, email);
        }else{
        	//Do nothing because userID Vs emailID cannot change until we move to one userID Vs multiple emailIDs.
        }
        return writeSuccessful;
    }

    @Override
    public String getEmailByIdMapping(final int userId){
        String email = null;
        if(umsCacheService.isCacheEnabled()){
            email = umsCacheService.getCacheClient().getEmailByIdMapping(userId);
        }
        return email;
    }
    
    @Override
	public boolean putUserSROById(final int userId, final UserSRO userSRO) {
		boolean writeSuccessful = false;
		if (umsCacheService.isCacheEnabled()) {
			writeSuccessful = umsCacheService.getCacheClient().putUserSROById(
					userId, userSRO);
		} else {
			// Remove email Vs SRO mapping because that is what userID gets
			// translated to.
			String email = null;
			if ((email = userSRO.getEmail()) != null) {
				umsCacheService
						.getCacheClient()
						.scheduleCachedKeyEviction(
								AerospikeProperties.Namespace.USER_NAMESPACE
										.getValue(),
								AerospikeProperties.Set.USER_SET.getValue(),
								email);
			}
		}
		return writeSuccessful;
	}
    

    

}
