
package com.snapdeal.ums.services.facebook.client.services;

import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;


public interface IFacebookUserClientService {


    public FacebookUserResponse addIfNotExistsFacebookUser(FacebookUserRequest request);

}
