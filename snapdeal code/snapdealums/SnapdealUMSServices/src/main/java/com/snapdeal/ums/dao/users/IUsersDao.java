/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 16-Aug-2010
 *  @author bala
 */
package com.snapdeal.ums.dao.users;

import java.util.Collection;
import java.util.Date;
import java.util.List;

import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserInformation;
import com.snapdeal.ums.core.entity.UserPreference;
import com.snapdeal.ums.core.entity.UserReferral;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.entity.ZendeskUser;

public interface IUsersDao {

    public User getUserById(int userId);

    public User getUserByEmail(String email);

    public void persistUser(User user);

    public User updateUser(User user);

    public boolean isUserExists(String email);

    public UserRole getUserRoleById(Integer userRoleId);

    public UserRole persistUserRole(UserRole userRoleRegistered);

    public void deleteUserRole(UserRole userRole);
    
    public void createOrUpdateReferralSent(User user, String referralChannel, int sentCount);

    public void createOrUpdateReferralClick(User user, String referralChannel);

    public void persistUserReferral(UserReferral userReferral);

    public List<UserReferral> getReferral(User user);

    public List<UserInformation> getUserInformationsByUser(User user);

    public List<UserInformation> getUserInformationsByUserAndName(User user, String name);

    public void persistUserInformation(UserInformation information);

    boolean isVisaBenefitAvailed(int userId, String cardNumber);

    public ZendeskUser getZendeskUser(int userId);

    public UserPreference getUserPreferenceByMobile(String phoneNo);

    public UserPreference addUserPreference(UserPreference userPreference);

    SubscriberProfile getSubscriberProfileById(Integer profileId);

    Locality getLocalityById(Integer localityId);

    public User getUserByIdWithoutRoles(int userId);

    User getUserByEmailWithoutRoles(String emailId);

    public CustomerEmailScore mergeCustomerEmailScore(CustomerEmailScore customerEmailScore);

    public CustomerMobileScore mergeCustomerMobileScore(CustomerMobileScore customerMobileScore);

    public CustomerEmailScore getCustomerScoreByEmail(String email);

    public CustomerMobileScore getCustomerScoreByMobile(String mobile);

    void deleteUserRoleById(int userRoleID);

    public List<User> getAutoCreatedUnverifiedUsers(Integer resultSize, Date startsCreatedDate, Date endsCreatedDate, Integer autocreatedNotificationCount);

    public void incrementAutocreatedNotificationCount(String emailIds);

    boolean isMobileExist(String mobile);

    /**
     * Returns List<Object[]>, in which Object[0] = ID, Object[1]=email and Object[2]=firstName for the
     * users in request which are registered with Snapdeal.
     * 
     * @param emailIDs
     * @return
     */
    public List<Object[]> getActiveSDUserIDNameEmail(Collection<String> emailIDs);

	public String getUserEmailById(int id);

	public Integer getUserIdByEmail(String email);

	public User getUserByEmailWithoutJoin(String email);

	public boolean isUserExistsById(int id); 
	
	public List<Integer> getUsersCreatedWithoutMobileByDateRange(Date createdStartDate,
			Date createdEndDate);

}
