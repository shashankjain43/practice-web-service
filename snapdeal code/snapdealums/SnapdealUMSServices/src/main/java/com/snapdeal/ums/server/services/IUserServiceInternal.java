package com.snapdeal.ums.server.services;

import java.util.Date;
import java.util.List;

import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserInformation;
import com.snapdeal.ums.core.entity.UserPreference;
import com.snapdeal.ums.core.entity.UserReferral;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.user.CreateUserWithDetailsResponse;

public interface IUserServiceInternal {

	public boolean isUserExists(String email);

	public User addUser(User user);

	public User getUserByEmail(String email);

	public User updateUser(User user);

	public boolean verifyUser(String email, String code);

	public User getOpenIdUser(User rpxUser);

	public UserRole createUserRole(UserRole userRole);

	public User getOrCreateVendorUser(String email);

	public User getUserById(int id);

	// public RoleZoneMapping createRoleZoneMap(RoleZoneMapping roleZoneMap);

	public List<UserReferral> getReferral(User user);

	public List<UserInformation> getUserInformationsByUser(User user);

	public UserInformation getUserInformationByUserAndName(User user,
			String name);

	public void addUserInformation(UserInformation information);

	public User createUser(String email, String password, String initialRole,
			String source, String targetUrl, boolean autocreated);

	public EmailVerificationCode createEmailVerificationCode(String email,
			String source, String targetUrl);

	public EmailVerificationCode getEmailVerificationCode(String email);

	public void clearEmailVerificationCode(String email);

	// public List<Integer> getZonesForUserRoles(Set<UserRole> userRoles);

	public ZendeskUser getZendeskUser(int userId);

	void persistUser(User user);

	public UserPreference getUserPreferenceByMobile(String phoneNo);

	public UserPreference addUserPreference(UserPreference userPreference);

	public String getConfirmationLink(String email);

	public User getUserByIdWithoutRoles(int id);

	public User getUserByIdWithRoles(Integer id);

	public User getUserByEmailWithRoles(String email);

	public CustomerEmailScore mergeCustomerEmailScore(
			CustomerEmailScore customerEmailScore);

	public CustomerMobileScore mergeCustomerMobileScore(
			CustomerMobileScore customerMobileScore);

	public CustomerEmailScore getCustomerScoreByEmail(String email);

	public CustomerMobileScore getCustomerScoreByMobile(String mobile);

	void createOrUpdateReferralSent(User user, String referralChannel,
			int sentCount);

	void updateReferralClick(User user, String referralChannel);

	public boolean isVisaBenefitAvailed(int userId, String cardNumber);

	public UserRole getUserRoleById(Integer userRoleId);

	public void deleteUserRoleById(Integer userRoleId);

	public List<User> getAutoCreatedUnverifiedUsers(Integer resultSize,
			Date startsCreatedDate, Date endsCreatedDate,
			Integer autocreatedNotificationCount);

	public void incrementAutocreatedNotificationCount(String emailId);

	public void putEmailVerificationCode(
			EmailVerificationCode emailVerificationCode, String email);

	public String getEmailVerificationLink(String url, String email,
			EmailVerificationCode emailVerificationCode);

	boolean isMobileExist(String mobile);

	public String getUserEmailById(int id);

	public Integer getUserIdByEmail(String email);

	public User getUserByEmailWithoutJoin(String email);

	public User createSubsidieryUser(String email, String password,
			String name, String source, String targetUrl, boolean autocreated,
			boolean isMobileverified, String emailVerificationCode);

	public boolean isUserExistsById(int id);

	public User createUserWithDetails(UserSRO userWithPlainPassword,
			String initialRoleName, String source, String targetUrl,
			boolean autocreated);

	public void addUserRoleAudit(String userRole, String emailRoleGiver,
			String emailRoleReceiver, String action);
    
	public List<Integer> getUsersCreatedWithoutMobileByDateRange(Date createdStartDate,
			Date createdEndDate);

}
