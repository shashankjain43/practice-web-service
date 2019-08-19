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

import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.ums.core.entity.facebook.FacebookLike;
import com.snapdeal.ums.ext.facebook.FacebookLikesRequest;
import com.snapdeal.ums.ext.facebook.FacebookLikesResponse;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.services.facebook.IFacebookLikeService;
import com.snapdeal.ums.services.facebook.IFacebookLikeServiceInternal;

@Service("fbLikeService")
public class FacebookLikeServiceImpl implements IFacebookLikeService {

    @Autowired
    private IFacebookLikeServiceInternal fbLikeServiceInternal;

    @Autowired
    private IUMSConvertorService         umsConvertorService;

    @Override
    public FacebookLikesResponse getUserFacebookLikesBySDId(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        if (request.getId() > 0) {
            for (FacebookLike fbLike : fbLikeServiceInternal.getUserFacebookLikesBySDId(request.getId())) {
                response.getFbLikeSRO().add((umsConvertorService.getFacebookLikeSROFromEntity(fbLike)));
            }
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Id should be greater than zero");
        }
        return response;
    }

    @Override
    public FacebookLikesResponse getUserFacebookLikesByFBId(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        if (request.getId() > 0) {
            for (FacebookLike fbLike : fbLikeServiceInternal.getUserFacebookLikesByFBId(request.getId())) {
                response.getFbLikeSRO().add((umsConvertorService.getFacebookLikeSROFromEntity(fbLike)));
            }
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Id should be greater than zero");
        }
        return response;
    }

    @Override
    public FacebookLikesResponse getUserFacebookLikesByEmail(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        if (StringUtils.isNotEmpty(request.getEmail())) {
            for (FacebookLike fbLike : fbLikeServiceInternal.getUserFacebookLikesByEmail(request.getEmail())) {
                response.getFbLikeSRO().add((umsConvertorService.getFacebookLikeSROFromEntity(fbLike)));
            }
        } else {
            response.setSuccessful(false);
            response.setMessage("Email of request should not be Empty");
        }
        return response;
    }

    @Override
    public FacebookLikesResponse addFaceBookLike(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        if (request.getFbLike() != null) {
            fbLikeServiceInternal.addFaceBookLike(umsConvertorService.getFacebookLikeEntityFromSRO(request.getFbLike()));
        } else {
            response.setSuccessful(false);
            response.setMessage("FacebookLikeSRO can't be null for this client call");
        }
        return response;
    }

    @Override
    public FacebookLikesResponse updateFacebookLike(FacebookLikesRequest request) {
        FacebookLikesResponse response = new FacebookLikesResponse();
        if (request.getFbLike() != null) {
            fbLikeServiceInternal.updateFacebookLike(umsConvertorService.getFacebookLikeEntityFromSRO(request.getFbLike()));
        } else {
            response.setSuccessful(false);
            response.setMessage("FacebookLikeSRO can't be null for this client call");
        }
        return response;
    }

}
