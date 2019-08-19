/**
 * 
 */
package com.snapdeal.ums.services.facebook.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.services.facebook.IFacebookUserService;
import com.snapdeal.ums.services.facebook.IFacebookUserServiceInternal;

/**
 * @author Ghanshyam
 */
@Service("fbUserService")
@Transactional
public class FacebookUserServiceImpl implements IFacebookUserService {

    @Autowired
    private IFacebookUserServiceInternal facebookUserServiceInternal;

    @Autowired
    private IUMSConvertorService         umsConvertorService;

    @Override
    public FacebookUserResponse addIfNotExistsFacebookUser(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (request.getFbUser() != null) {
            response.setAddIfNotExistsFacebookUser(facebookUserServiceInternal.addIfNotExistsFacebookUser(umsConvertorService.getFacebookUserEntityFromSRO(request.getFbUser())));
        } else {
            response.setSuccessful(false);
            response.setMessage("FacebookUserSRO can't be null for this client call");
        }
        return response;
    }

    @Override
    public FacebookUserResponse updateFacebookUser(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (request.getFbUser() != null) {
            facebookUserServiceInternal.updateFacebookUser(umsConvertorService.getFacebookUserEntityFromSRO(request.getFbUser()));
        } else {
            response.setSuccessful(false);
            response.setMessage("FacebookUserSRO can't be null for this client call");
        }
        return response;
    }

    @Override
    public FacebookUserResponse getFacebookUserByUser(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (request.getUser() != null) {
            response.setFbUserSRO(umsConvertorService.getFacebookUserSROFromEntity(facebookUserServiceInternal.getFacebookUserByUser(umsConvertorService.getUserEntityFromSRO(request.getUser()))));
        } else {
            response.setSuccessful(false);
            response.setMessage("UserSRO can't be null for this client call");
        }
        return response;
    }

    @Override
    public FacebookUserResponse getFacebookUserbySDId(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (request.getId() > 0) {
            response.setFbUserSRO(umsConvertorService.getFacebookUserSROFromEntity(facebookUserServiceInternal.getFacebookUserbySDId(request.getId())));
        } else {
            response.setSuccessful(false);
            response.setMessage("Id should be greater than zero");
        }
        return response;
    }

    @Override
    public FacebookUserResponse getFacebookUserbyFBId(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (request.getId() > 0) {
            response.setFbUserSRO(umsConvertorService.getFacebookUserSROFromEntity(facebookUserServiceInternal.getFacebookUserbyFBId(request.getId())));
        } else {
            response.setSuccessful(false);
            response.setMessage("Id should be greater than zero");
        }
        return response;
    }

    @Override
    public FacebookUserResponse getFacebookUserByEmail(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (StringUtils.isNotEmpty(request.getEmail())) {
            response.setFbUserSRO(umsConvertorService.getFacebookUserSROFromEntity(facebookUserServiceInternal.getFacebookUserByEmail(request.getEmail())));
        } else {
            response.setSuccessful(false);
            response.setMessage("Email field of request can't be empty");
        }
        return response;
    }

    @Override
    public FacebookUserResponse facebookUserExists(FacebookUserRequest request) {
        FacebookUserResponse response = new FacebookUserResponse();
        if (StringUtils.isNotEmpty(request.getEmail())) {
            response.setFacebookUserExists(facebookUserServiceInternal.facebookUserExists(request.getEmail()));
        } else {
            response.setSuccessful(false);
            response.setMessage("Email field of request can't be empty");
        }
        return response;
    }

}
