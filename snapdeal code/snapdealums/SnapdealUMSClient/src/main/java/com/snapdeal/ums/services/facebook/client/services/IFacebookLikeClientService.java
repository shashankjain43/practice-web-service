
package com.snapdeal.ums.services.facebook.client.services;

import com.snapdeal.ums.ext.facebook.FacebookLikesRequest;
import com.snapdeal.ums.ext.facebook.FacebookLikesResponse;

public interface IFacebookLikeClientService {

    public FacebookLikesResponse getUserFacebookLikesBySDId(FacebookLikesRequest request);

    public FacebookLikesResponse getUserFacebookLikesByFBId(FacebookLikesRequest request);

    public FacebookLikesResponse getUserFacebookLikesByEmail(FacebookLikesRequest request);

    public FacebookLikesResponse addFaceBookLike(FacebookLikesRequest request);

    public FacebookLikesResponse updateFacebookLike(FacebookLikesRequest request);

}
