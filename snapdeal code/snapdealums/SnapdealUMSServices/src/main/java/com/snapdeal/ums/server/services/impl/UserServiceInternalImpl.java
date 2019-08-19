/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 22, 2012
 *  @author naveen
 */
package com.snapdeal.ums.server.services.impl;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;

import org.hibernate.Hibernate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.memcached.service.IMemcachedService;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.core.utils.SDEncryptionUtils;
import com.snapdeal.core.utils.SDUtils;
import com.snapdeal.ums.cache.LocalitiesCache;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.AdminUserRoleAudit;
import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.Role;
import com.snapdeal.ums.core.entity.Role.RoleDef;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserInformation;
import com.snapdeal.ums.core.entity.UserPreference;
import com.snapdeal.ums.core.entity.UserReferral;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.dao.users.IAdminUserRoleAuditDao;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserRoleService;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsServiceInternal;

@Transactional
@Service("umsUserServiceInternal")
public class UserServiceInternalImpl implements IUserServiceInternal {

    private static final Logger      LOG = LoggerFactory.getLogger(UserServiceInternalImpl.class);

    @Autowired
    private IUsersDao                usersDao;

    @Autowired
    private IEmailServiceInternal            emailService;

    @Autowired
    private ISubscriptionsServiceInternal    subscriptionService;

    @Autowired
    private IMemcachedService        cache;


    @Autowired
    private IUserRoleService    userRoleService;
    
    @Autowired
    private IAdminUserRoleAuditDao adminUserRoleAuditDao;
    
    @Override
    public boolean isUserExists(String email) {
        return usersDao.isUserExists(email);
    }
    
    @Override
    public boolean isUserExistsById(int id) {
        return usersDao.isUserExistsById(id);
    }

    @Override
    public User addUser(User user) {
        user.setModified(DateUtils.getCurrentTime());
        user.setCreated(DateUtils.getCurrentTime());
        user.setAutocreatedNotificationCount(0);
        return usersDao.updateUser(user);
    }

    @Override
    public void persistUser(User user) {
        user.setModified(DateUtils.getCurrentTime());
        user.setCreated(DateUtils.getCurrentTime());
        usersDao.persistUser(user);
    }

    @Override
    public User getUserById(int id) {
        return usersDao.getUserById(id);
    }

    @Override
    public User getUserByIdWithoutRoles(int id) {
        return usersDao.getUserByIdWithoutRoles(id);
    }

    @Override
    public User getUserByEmail(String email) {
        return usersDao.getUserByEmail(email);
    }

    @Override
    public User updateUser(User user) {
        user.setModified(DateUtils.getCurrentTime());
        return usersDao.updateUser(user);
    }

    
    @Override
    public boolean verifyUser(String email, String code) {
        User user = getUserByEmail(email);
        if (user != null) {
            if (!user.isEmailVerified()) {
                EmailVerificationCode emailVerificationCode = getEmailVerificationCode(email);
                if (emailVerificationCode != null && emailVerificationCode.getCode().equals(code)) {
                    user.setEmailVerified(true);
                    user.setEnabled(true);
                    UserRole userRoleRegistered = new UserRole(user,userRoleService.getRoleByCode(RoleDef.REGISTERED.code()));
                    usersDao.persistUserRole(userRoleRegistered);
                    UserRole role = user.getUserRole(RoleDef.UNVERIFIED.code());
                    if (role != null) {
                        usersDao.deleteUserRole(role);
                        user.getUserRoles().remove(role);
                    }
                    user.getUserRoles().add(userRoleRegistered);
                    updateUser(user);
                    return true;
                }
            } else {
                return true;
            }
        }
        return false;
    }

    @Override
    public User getOpenIdUser(User rpxUser) {
        User user = getUserByEmail(rpxUser.getEmail());
        if (user == null) {
            // create new user
            user = new User();
            EmailSubscriberDetail esd = subscriptionService.getEmailSubscriberDetailByEmail(rpxUser.getEmail());
            if (esd != null) {
                if (esd.getSubscriberProfile() != null) {
                    user.setSubscriberProfile(usersDao.getSubscriberProfileById(esd.getSubscriberProfile().getId()));
                } else {
                    if (rpxUser.getDisplayName() != null || rpxUser.getGender() != null || rpxUser.getBirthday() != null || rpxUser.getLocation() != null) {
                        LocalitiesCache cache = CacheManager.getInstance().getCache(LocalitiesCache.class);
                        Locality locality = cache.getLocalityByName(rpxUser.getLocation());
                        Integer localityId = null;
                        if (locality != null)
                            localityId = locality.getId();
                        SubscriberProfile subscriberProfile = subscriptionService.createSubscriberProfile(rpxUser.getEmail(), null, rpxUser.getDisplayName(), rpxUser.getGender(),
                                rpxUser.getBirthday(), localityId, null);
                        esd.setSubscriberProfile(usersDao.getSubscriberProfileById(subscriberProfile.getId()));
                        subscriptionService.updateEmailSubscriberDetail(esd);
                        user.setSubscriberProfile(subscriberProfile);
                    }
                }
            }
            user.setEmail(rpxUser.getEmail());
            user.setGender(rpxUser.getGender());
            user.setDisplayName(rpxUser.getDisplayName());
            user.setPassword(SDEncryptionUtils.getMD5EncodedPassword(rpxUser.getPassword()));
            user.setSource(rpxUser.getSource());
            user.setBirthday(rpxUser.getBirthday());
            user.setLocation(rpxUser.getLocation());
            user.setUid(rpxUser.getUid());
            if (StringUtils.isNotEmpty(rpxUser.getMobile())) {
                user.setMobile(rpxUser.getMobile());
            }
            user.setFriendUids(rpxUser.getFriendUids());
            user.setAutocreated(rpxUser.isAutocreated());
            user = addUser(user);
            emailService.send3rdPartyConfirmationEmail(user.getEmail(), null, null, null, rpxUser.getSource());
        } else {
            if (user.getBirthday() == null && (rpxUser.getBirthday() != null)) {
                user.setBirthday(rpxUser.getBirthday());
            }
            if (StringUtils.isEmpty(user.getLocation()) && StringUtils.isNotEmpty(rpxUser.getLocation())) {
                user.setLocation(rpxUser.getLocation());
            }
            if (StringUtils.isEmpty(user.getUid())) {
                user.setUid(rpxUser.getUid());
            }
            if (StringUtils.isEmpty(user.getFriendUids())) {
                user.setFriendUids(rpxUser.getFriendUids());
            }

            if (StringUtils.isNotEmpty(rpxUser.getMobile())) {
                user.setMobile(rpxUser.getMobile());
            }
            user.setSource(rpxUser.getSource());
        }

        UserRole userRoleRegistered = new UserRole(user, userRoleService.getRoleByCode(RoleDef.REGISTERED.code()));
        if (!user.getUserRoles().contains(userRoleRegistered)) {
            usersDao.persistUserRole(userRoleRegistered);
            user.getUserRoles().add(userRoleRegistered);
            user.setEmailVerified(true);
            user.setEnabled(true);
            UserRole unverifiedRole = user.getUserRole(RoleDef.UNVERIFIED.code());
            if (unverifiedRole != null) {
                usersDao.deleteUserRole(unverifiedRole);
                user.getUserRoles().remove(unverifiedRole);
            }
        }
        updateUser(user);
        return user;
    }

	@Override
	public UserRole createUserRole(UserRole userRole) {
		UserRole savedUserRole = null;

		// Not allow non-snapdeal users to be provided any other role apart from
		// unverified or registered
		String email = userRole.getUser().getEmail();
		boolean isNonSnapdealUser = !(email.endsWith("@snapdeal.com") || email.endsWith("@jasperindia.com"));
		boolean isAuthRoleForNonSnapdealUser = userRole.getRole().getCode().equals(UserRoleSRO.Role.REGISTERED.role())
				|| userRole.getRole().getCode().equals(UserRoleSRO.Role.UNVERIFIED.role());
		if (isNonSnapdealUser) {
			if (isAuthRoleForNonSnapdealUser) {
				savedUserRole = usersDao.persistUserRole(userRole);
			} else {
				LOG.info("Attempt of authority breach supressed at createUserRole, role: " + userRole.getRole().getCode() + " to user: "
						+ userRole.getUser().getEmail());
			}
		} else {
			savedUserRole = usersDao.persistUserRole(userRole);
		}

		return savedUserRole;
	}

//    @Override
//    public RoleZoneMapping createRoleZoneMap(RoleZoneMapping roleZoneMap) {
//        return usersDao.persistRoleZoneMap(roleZoneMap);
//    }

    @Override
    public User getOrCreateVendorUser(String email) {
        User user = getUserByEmail(email);

        if (user != null) {
            UserRole userRoleRegistered = new UserRole(user, userRoleService.getRoleByCode(RoleDef.REGISTERED.code()));
            UserRole userRoleVendor = new UserRole(user, userRoleService.getRoleByCode(RoleDef.VENDOR.code()));
            if (!user.getUserRoles().contains(userRoleRegistered)) {
                usersDao.persistUserRole(userRoleRegistered);
                user.getUserRoles().add(userRoleRegistered);
                user.setEmailVerified(true);
                user.setEnabled(true);
            }
            if (!user.getUserRoles().contains(userRoleVendor)) {
                usersDao.persistUserRole(userRoleVendor);
                user.getUserRoles().add(userRoleVendor);
            }
            updateUser(user);
            return user;
        } else {
            user = new User();
            user.setEmail(email);
            //TODO: user.setPassword(WebContextUtils.md5Encode(signupForm.getPassword(), user.getEmail()));
            String randomPassword = StringUtils.getRandom(8);
            user.setPassword(SDEncryptionUtils.getMD5EncodedPassword(randomPassword));
            user.setEnabled(true);
            user.setEmailVerified(true);
            user.setAutocreated(true);
            persistUser(user);
            UserRole userRoleRegistered = new UserRole(user, userRoleService.getRoleByCode(RoleDef.REGISTERED.code()));
            UserRole userRoleVendor = new UserRole(user, userRoleService.getRoleByCode(RoleDef.VENDOR.code()));
            usersDao.persistUserRole(userRoleRegistered);
            usersDao.persistUserRole(userRoleVendor);
            user.getUserRoles().add(userRoleRegistered);
            user.getUserRoles().add(userRoleVendor);
            updateUser(user);
            User userWithTextPassword = new User(user, randomPassword);
            //send vendor user email
            UMSPropertiesCache cache = CacheManager.getInstance().getCache(UMSPropertiesCache.class);
            emailService.sendVendorUserCreationEmail(userWithTextPassword, cache.getContextPath("http://www.snapdeal.com"), cache.getContentPath("http://i.sdlcdn.com/"));

            return user;
        }
    }

    public void updateReferralSent(User user, String referralChannel, int sentCount) {
        if (user != null) {
            usersDao.createOrUpdateReferralSent(user, referralChannel, sentCount);
        }
    }

    public void updateReferralClick(String referralCode, String referralChannel) {
        User user = usersDao.getUserById(Integer.parseInt(referralCode));
        if (user != null) {
            usersDao.createOrUpdateReferralClick(user, referralChannel);
        }
    }

    @Override
    public List<UserReferral> getReferral(User user) {
        return usersDao.getReferral(user);
    }

    @Override
    public List<UserInformation> getUserInformationsByUser(User user) {
        return usersDao.getUserInformationsByUser(user);
    }

    @Override
    public UserInformation getUserInformationByUserAndName(User user, String name) {
        for (UserInformation information : usersDao.getUserInformationsByUserAndName(user, name)) {
            if (name.equals(information.getName())) {
                return information;
            }
        }
        return null;
    }

    @Override
    public void addUserInformation(UserInformation information) {
        usersDao.persistUserInformation(information);
    }

    @Override
    public User createUser(String email, String password, String initialRole, String source, String targetUrl, boolean autocreated) {
        User user = new User();
        user.setEmail(email);
        user.setPassword(SDEncryptionUtils.getMD5EncodedPassword(password));
        user.setEnabled(true);
        user.setSource(source);
        Role role = userRoleService.getRoleByCode(initialRole.toLowerCase());
        if(role != null)
        {
    		UserRole userRole = new UserRole(user,role);
    		user.getUserRoles().add(userRole);
        }
        user.setAutocreated(autocreated);
        user = addUser(user);
        User userWithTextPassword = new User(user, password);
        createEmailVerificationCode(email, source, targetUrl);
        return userWithTextPassword;
    }

    @Override
    public EmailVerificationCode createEmailVerificationCode(String email, String source, String targetUrl) {
        EmailVerificationCode emailVerificationCode;
        emailVerificationCode = getEmailVerificationCode(email);
        if (emailVerificationCode == null) {
            emailVerificationCode = new EmailVerificationCode();
            emailVerificationCode.setCode(StringUtils.getRandom());
            if (StringUtils.isNotEmpty(source)) {
                emailVerificationCode.setSource(source);
            }
            if (StringUtils.isNotEmpty(targetUrl)) {
                emailVerificationCode.setTargetUrl(targetUrl);
            }
            cache.put(Constants.MEMCACHE_KEY_PREFIX_EMAIL_VERIFICATION_CODE + email, emailVerificationCode, 864000);
        }
        return emailVerificationCode;
    }

    @Override
    public EmailVerificationCode getEmailVerificationCode(String email) {
        return cache.get(Constants.MEMCACHE_KEY_PREFIX_EMAIL_VERIFICATION_CODE + email, EmailVerificationCode.class);
    }

    @Override
    public void clearEmailVerificationCode(String email) {
        try {
            cache.remove(Constants.MEMCACHE_KEY_PREFIX_EMAIL_VERIFICATION_CODE + email);
        } catch (Exception e) {
            // Not doing anything for now
        }
    }

    @Override
    public void putEmailVerificationCode(EmailVerificationCode emailVerificationCode, String email) {
        cache.put(Constants.MEMCACHE_KEY_PREFIX_EMAIL_VERIFICATION_CODE + email, emailVerificationCode, 864000);
    }
    //    @Override
    //    public List<User> getUsersByRoleAndZone(String role, List<Integer> zones) {
    //        return (usersDao.getUsersByRoleAndZone(role, zones));
    //    }

//    @Override
//    public List<Integer> getZonesForUserRoles(Set<UserRole> userRoles) {
//        List<RoleZoneMapping> rzmList = usersDao.getZonesForUserRoles(userRoles);
//        List<Integer> zoneList = new ArrayList<Integer>();
//        for (RoleZoneMapping rzm : rzmList) {
//            int zoneId = rzm.getZone().getId();
//            if (!zoneList.contains(zoneId)) {
//                zoneList.add(zoneId);
//            }
//        }
//        return zoneList;
//    }

    @Override
    public ZendeskUser getZendeskUser(int userId) {
        return usersDao.getZendeskUser(userId);
    }

    @Override
    public UserPreference getUserPreferenceByMobile(String phoneNo) {
        return usersDao.getUserPreferenceByMobile(phoneNo);
    }

    @Override
    public UserPreference addUserPreference(UserPreference userPreference) {
        return usersDao.addUserPreference(userPreference);
    }

    @Override
    public String getConfirmationLink(String email) {
        String confirmationLink = null;
        User user = getUserByEmail(email);
        if (user != null) {
            if (!user.isEmailVerified()) {
                confirmationLink = SDUtils.getEmailVerificationLink("resetPassword", user.getEmail(), createEmailVerificationCode(user.getEmail(), user.getSource(), null));
            }
        }
        return confirmationLink;
    }

    @Override
    public User getUserByIdWithRoles(Integer id) {
         User user = usersDao.getUserByIdWithoutRoles(id);
         Hibernate.initialize(user.getUserRoles());
        return user;
    }

    @Override
    public User getUserByEmailWithRoles(String email) {
        User user = usersDao.getUserByEmailWithoutRoles(email);
        Hibernate.initialize(user.getUserRoles());
        return user;
    }

    @Override
    public CustomerEmailScore mergeCustomerEmailScore(CustomerEmailScore customerEmailScore) {
        return usersDao.mergeCustomerEmailScore(customerEmailScore);
    }

    @Override
    public CustomerMobileScore mergeCustomerMobileScore(CustomerMobileScore customerMobileScore) {
        return usersDao.mergeCustomerMobileScore(customerMobileScore);
    }

    @Override
    public CustomerEmailScore getCustomerScoreByEmail(String email) {
        return usersDao.getCustomerScoreByEmail(email);
    }

    @Override
    public CustomerMobileScore getCustomerScoreByMobile(String mobile) {
        return usersDao.getCustomerScoreByMobile(mobile);
    }
    
    @Override
    public void createOrUpdateReferralSent(User user, String referralChannel, int sentCount){
        usersDao.createOrUpdateReferralSent(user, referralChannel, sentCount);
    }

    @Override
    public void updateReferralClick(User user, String referralChannel) {
        usersDao.createOrUpdateReferralClick(user, referralChannel);
    }

    @Override
    public boolean isVisaBenefitAvailed(int userId, String cardNumber) {
        return usersDao.isVisaBenefitAvailed(userId, cardNumber);
    }

    @Override
    public UserRole getUserRoleById(Integer userRoleId){
        return usersDao.getUserRoleById(userRoleId);
    }
    
    @Override
    public void deleteUserRoleById(Integer userRoleId) {
       usersDao.deleteUserRoleById(userRoleId);
        
    }
    
    
    
    @Override
    public List<User> getAutoCreatedUnverifiedUsers(Integer resultSize, Date startsCreatedDate, Date endsCreatedDate, Integer autocreatedNotificationCount){
        return usersDao.getAutoCreatedUnverifiedUsers(resultSize, startsCreatedDate,endsCreatedDate,autocreatedNotificationCount);
    }
    
    @Override
    public void incrementAutocreatedNotificationCount(String emailId){
        usersDao.incrementAutocreatedNotificationCount(emailId);
    }
    
    @Override
    public String getEmailVerificationLink(String url, String email, EmailVerificationCode emailVerificationCode) {
        if (emailVerificationCode != null) {
            return getEmailVerificationLink(url, email, emailVerificationCode.getCode(), emailVerificationCode.getSource(), emailVerificationCode.getTargetUrl());
        }
        return null;
    }
    
    @Override
    public boolean isMobileExist(String mobile){
        return usersDao.isMobileExist(mobile);
    }
    
    public static String getEmailVerificationLink(String url, String email, String confirmationCode, String source, String targetUrl) {
        StringBuilder builder = new StringBuilder().append(CacheManager.getInstance().getCache(UMSPropertiesCache.class).getContextPath("")).append(url);
        if (url.indexOf("?") == -1) {
            builder.append("?email=");
        } else {
            builder.append("&email=");
        }
        try {
            builder.append(URLEncoder.encode(email, "UTF-8"));
            builder.append("&code=").append(confirmationCode);
            if (StringUtils.isNotEmpty(source)) {
                builder.append("&source=").append(source);
            }
            if (StringUtils.isNotEmpty(targetUrl)) {
                builder.append("&targetUrl=").append(URLEncoder.encode(targetUrl, "UTF-8"));

            }
        } catch (UnsupportedEncodingException e) {
            builder.append(email);
            builder.append("&code=").append(confirmationCode);
            if (StringUtils.isNotEmpty(source)) {
                builder.append("&source=").append(source);
            }
            if (StringUtils.isNotEmpty(targetUrl)) {
                builder.append("&targetUrl=").append(targetUrl);
            }
        }
        return builder.toString();
    }
    
    @Override
    public String getUserEmailById(int id)
    {
    	
		return usersDao.getUserEmailById(id);
    	
    }
    
    @Override
    public Integer getUserIdByEmail(String email)
    {
    	
		return usersDao.getUserIdByEmail(email);
    	
    }

	@Override
	public User getUserByEmailWithoutJoin(String email) {
		
		return usersDao.getUserByEmailWithoutJoin(email);
	}

	@Override
	public User createSubsidieryUser(String email, String password,
			String initialRole, String source, String targetUrl, boolean autocreated,
			boolean isEmaileVerified, String emailVerificationCode) {
		User user = new User();
        user.setEmail(email);
        user.setPassword(SDEncryptionUtils.getMD5EncodedPassword(password));
        user.setEnabled(true);
        user.setSource(source);
        Role role = userRoleService.getRoleByCode(initialRole.toLowerCase());
        if(role != null)
        { 
        	UserRole userRole = new UserRole(user,role);
        	user.getUserRoles().add(userRole);
        }
        user.setAutocreated(autocreated);
        user.setEmailVerified(isEmaileVerified);
        user.setEmailVerificationCode(emailVerificationCode);
        user = addUser(user);
        User userWithTextPassword = new User(user, password);
        createEmailVerificationCode(email, source, targetUrl);
        return userWithTextPassword;
	}

	@Override
	public User createUserWithDetails(UserSRO newUser,
			String initialRoleName, String source, String targetUrl,
			boolean autocreated) {
		
		String email = newUser.getEmail().trim().toLowerCase();
		User user = new User();
        user.setEmail(email);
        user.setPassword(SDEncryptionUtils.getMD5EncodedPassword(newUser.getPassword()));
        user.setEnabled(true);
        user.setSource(source);
        Role role = userRoleService.getRoleByCode(initialRoleName.toLowerCase());
        if(role != null)
        { 
        	UserRole userRole = new UserRole(user,role);
        	user.getUserRoles().add(userRole);
        }
        user.setAutocreated(autocreated);
        user.setChannelCode(newUser.getChannelCode());
        user.setDisplayName(newUser.getDisplayName());
        user.setFirstName(newUser.getFirstName());
        user.setMiddleName(newUser.getMiddleName());
        user.setLastName(newUser.getLastName());
        user.setGender(newUser.getGender());
        user.setLocation(newUser.getLocation());
        user.setMobile(newUser.getMobile());
        user.setBirthday(newUser.getBirthday());

        user = addUser(user);
        createEmailVerificationCode(email, source, targetUrl);
        return user;
	}

	@Override
	public void addUserRoleAudit(String userRole, String emailRoleGiver, String emailRoleReceiver, String action) {

		AdminUserRoleAudit adminUserRoleAudit = new AdminUserRoleAudit();
		Integer userId = usersDao.getUserIdByEmail(emailRoleReceiver);
		adminUserRoleAudit.setUserId(userId);
		adminUserRoleAudit.setAction(action);
		adminUserRoleAudit.setUserRole(userRole);
		adminUserRoleAudit.setEmailRoleGiver(emailRoleGiver);
		adminUserRoleAuditDao.persistAdminUserRoleAudit(adminUserRoleAudit);
	}
	
	
	@Override
	public List<Integer> getUsersCreatedWithoutMobileByDateRange( Date createdStartDate,
			Date createdEndDate) {
		return usersDao.getUsersCreatedWithoutMobileByDateRange(createdStartDate, createdEndDate);
	}
    
}
