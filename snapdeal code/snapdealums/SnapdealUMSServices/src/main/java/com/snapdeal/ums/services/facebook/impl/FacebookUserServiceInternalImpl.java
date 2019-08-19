/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 16, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.services.facebook.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.facebook.FacebookUser;
import com.snapdeal.ums.dao.facebook.IFacebookUserDao;
import com.snapdeal.ums.services.facebook.IFacebookUserServiceInternal;

@Service("facebookUserServiceInternal")
public class FacebookUserServiceInternalImpl implements IFacebookUserServiceInternal{

    @Autowired
    private IFacebookUserDao fbUserDao;
    
    @Override
    public boolean addIfNotExistsFacebookUser(FacebookUser fbUser) {
        return fbUserDao.addIfNotExistsFacebookUser(fbUser);
    }

    @Override
    public void updateFacebookUser(FacebookUser fbUser) {
        fbUserDao.updateFacebookUser(fbUser);
    }

    @Override
    public FacebookUser getFacebookUserByUser(User user) {
        return fbUserDao.getFacebookUser(user);
    }

    @Override
    public FacebookUser getFacebookUserbySDId(Long userId) {
        return fbUserDao.getFacebookUserbySDId(userId);
    }

    @Override
    public FacebookUser getFacebookUserbyFBId(Long userId) {
        return fbUserDao.getFacebookUserbyFBId(userId);
    }

    @Override
    public FacebookUser getFacebookUserByEmail(String email) {
        return fbUserDao.getFacebookUser(email);
    }
    
    @Override
    public boolean facebookUserExists(String email) {
        FacebookUser fbUser = getFacebookUserByEmail(email);
        if(fbUser != null){
            return true;
        }
        return false;
    }
}
