
package com.snapdeal.ums.services.facebook;

import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.facebook.FacebookUser;


public interface IFacebookUserServiceInternal {


    /**
     * Adds the {@link FacebookUser} to database. It checks first whether given
     * user already exists by using <code>email</code>.
     * @param fbUser
     * @return whether user has been added
     */
    public boolean addIfNotExistsFacebookUser(FacebookUser fbUser);
    /**
     * Updates the given facebook user's data.
     * @param fbUser
     */
    public void updateFacebookUser(FacebookUser fbUser);
    /**
     * Returns {@link FacebookUser} for the corresponding <code>Snapdeal</code>
     * user.
     * @param user
     * @return
     */
    public FacebookUser getFacebookUserByUser(User user);
    /**
     * Returns the {@link FacebookUser} object for the corresponding <code>Snapdeal</code>
     * user id.
     * @param userId
     * @return
     */
    public FacebookUser getFacebookUserbySDId(Long userId);
    /**
     * Returns {@link FacebookUser} object for the corresponding
     * <code>fb id</code>.
     * @param userId
     * @return
     */
    public FacebookUser getFacebookUserbyFBId(Long userId);
    /**
     * Returns {@link FacebookUser} for the given <code>email</code>.
     * @param email
     * @return
     */
    public FacebookUser getFacebookUserByEmail(String email);
    
    /**
     * Check whether facebook user already exists in our system
     * @param email
     * @return
     */
    public boolean facebookUserExists(String email);

}
