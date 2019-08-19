/**
 * 
 */
package com.snapdeal.ums.services.facebook;

import com.snapdeal.ums.core.entity.facebook.FacebookUser;
import com.snapdeal.ums.ext.facebook.FacebookUserRequest;
import com.snapdeal.ums.ext.facebook.FacebookUserResponse;

/**
 * @author fanendra
 */
public interface IFacebookUserService {
    /**
     * Adds the {@link FacebookUser} to database. It checks first whether given user already exists by using
     * <code>email</code>.
     * 
     * @param request
     * @return whether user has been added
     */
    public FacebookUserResponse addIfNotExistsFacebookUser(FacebookUserRequest request);

    /**
     * Updates the given facebook user's data.
     * 
     * @param request
     * @return
     */
    public FacebookUserResponse updateFacebookUser(FacebookUserRequest request);

    /**
     * Returns {@link FacebookUser} for the corresponding <code>Snapdeal</code> user.
     * 
     * @param request
     * @return
     */
    public FacebookUserResponse getFacebookUserByUser(FacebookUserRequest request);

    /**
     * Returns the {@link FacebookUser} object for the corresponding <code>Snapdeal</code> user id.
     * 
     * @param userId
     * @return
     */
    public FacebookUserResponse getFacebookUserbySDId(FacebookUserRequest request);

    /**
     * Returns {@link FacebookUser} object for the corresponding <code>fb id</code>.
     * 
     * @param userId
     * @return
     */
    public FacebookUserResponse getFacebookUserbyFBId(FacebookUserRequest request);

    /**
     * Returns {@link FacebookUser} for the given <code>email</code>.
     * 
     * @param email
     * @return
     */
    public FacebookUserResponse getFacebookUserByEmail(FacebookUserRequest request);

    /**
     * Check whether facebook user already exists in our system
     * 
     * @param email
     * @return
     */
    public FacebookUserResponse facebookUserExists(FacebookUserRequest request);

}
