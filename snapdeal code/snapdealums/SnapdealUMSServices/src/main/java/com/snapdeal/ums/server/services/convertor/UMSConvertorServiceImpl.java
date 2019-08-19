/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 28-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services.convertor;

import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.hibernate.SessionFactory;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.core.DisabledURLSRO;
import com.snapdeal.core.ServerBehaviourContextSRO;
import com.snapdeal.core.dto.BulkUploadResultDTO;
import com.snapdeal.core.dto.CommunicationAdminFilterDTO;
import com.snapdeal.core.dto.ProductMultiVendorMappingResultDTO;
import com.snapdeal.core.dto.UserRoleDTO;
import com.snapdeal.core.dto.feedback.CancelledOrderFeedbackDO;
import com.snapdeal.core.entity.Activity;
import com.snapdeal.core.entity.Audit;
import com.snapdeal.core.entity.Corporate;
import com.snapdeal.core.entity.CustomerQuery;
import com.snapdeal.core.entity.GetFeatured;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.core.entity.UserSDCashHistory;
import com.snapdeal.core.sro.email.TemplateParam;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.enums.Gender;
import com.snapdeal.locality.client.service.ILocalityClientService;
import com.snapdeal.oms.base.sro.order.AddressDetailSRO;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.CsZentrix;
import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.DisabledURL;
import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.core.entity.MobileSubscriberDetail;
import com.snapdeal.ums.core.entity.Newsletter;
import com.snapdeal.ums.core.entity.NewsletterEspMapping;
import com.snapdeal.ums.core.entity.Role;
import com.snapdeal.ums.core.entity.ServerBehaviourContext;
import com.snapdeal.ums.core.entity.SmsScheduler;
import com.snapdeal.ums.core.entity.SubscriberProfile;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserAddress;
import com.snapdeal.ums.core.entity.UserInformation;
import com.snapdeal.ums.core.entity.UserPreference;
import com.snapdeal.ums.core.entity.UserReferral;
import com.snapdeal.ums.core.entity.UserRole;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.core.entity.facebook.FacebookLike;
import com.snapdeal.ums.core.entity.facebook.FacebookProfile;
import com.snapdeal.ums.core.entity.facebook.FacebookUser;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;
import com.snapdeal.ums.core.sro.bulkemail.ESPFilterCityMappingSRO;
import com.snapdeal.ums.core.sro.bulkemail.ESPProfileFieldSRO;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;
import com.snapdeal.ums.core.sro.bulkemail.EmailServiceProviderSRO;
import com.snapdeal.ums.core.sro.customerfilter.CommunicationAdminFilterSRO;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;
import com.snapdeal.ums.core.sro.email.AuditSRO;
import com.snapdeal.ums.core.sro.email.BulkUploadResultSRO;
import com.snapdeal.ums.core.sro.email.CancelledOrderFeedbackDOSRO;
import com.snapdeal.ums.core.sro.email.CancelledOrderFeedbackDOSRO.MapEntryUtil;
import com.snapdeal.ums.core.sro.email.CorporateSRO;
import com.snapdeal.ums.core.sro.email.CustomerQuerySRO;
import com.snapdeal.ums.core.sro.email.EmailMessageSRO;
import com.snapdeal.ums.core.sro.email.FeaturedSRO;
import com.snapdeal.ums.core.sro.email.ProductMultiVendorMappingResultSRO;
import com.snapdeal.ums.core.sro.newsletter.NewsletterEspMappingSRO;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;
import com.snapdeal.ums.core.sro.smsscheduler.SmsSchedulerSRO;
import com.snapdeal.ums.core.sro.subscription.AffiliateSubscriptionOfferSRO;
import com.snapdeal.ums.core.sro.subscription.EmailMobileAssociationSRO;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberDetailSRO;
import com.snapdeal.ums.core.sro.subscription.EmailSubscriberSRO;
import com.snapdeal.ums.core.sro.subscription.LocalitySRO;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberDetailSRO;
import com.snapdeal.ums.core.sro.subscription.MobileSubscriberSRO;
import com.snapdeal.ums.core.sro.subscription.SubscriberProfileSRO;
import com.snapdeal.ums.core.sro.user.CsZentrixSRO;
import com.snapdeal.ums.core.sro.user.CustomerScoreSRO;
import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;
import com.snapdeal.ums.core.sro.user.RoleSRO;
import com.snapdeal.ums.core.sro.user.UserAddressSRO;
import com.snapdeal.ums.core.sro.user.UserInformationSRO;
import com.snapdeal.ums.core.sro.user.UserPreferenceSRO;
import com.snapdeal.ums.core.sro.user.UserReferralSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserSDCashHistorySRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.core.sro.user.ZendeskUserSRO;
import com.snapdeal.ums.dao.facebook.IFacebookUserDao;
import com.snapdeal.ums.dao.subscriptions.ISubscriptionsDao;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.server.services.IUserRoleService;
import com.snapdeal.ums.services.facebook.sro.FacebookLikeSRO;
import com.snapdeal.ums.services.facebook.sro.FacebookProfileSRO;
import com.snapdeal.ums.services.facebook.sro.FacebookUserSRO;
import com.snapdeal.ums.utils.UMSStringUtils;

@Service("umsConverterService")
@Transactional(readOnly = true)
public class UMSConvertorServiceImpl implements IUMSConvertorService {

    @Autowired
    private IUsersDao              usersDao;

    @Autowired
    private IFacebookUserDao       fbUserDao;

    @Autowired
    private ISubscriptionsDao      umsSubscriptionsDao;

    @Autowired
    private ILocalityClientService localityClientService;

    private SessionFactory         sessionFactory;

    private static final Logger    LOG = LoggerFactory.getLogger(UMSConvertorServiceImpl.class);

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public UserSRO getUserSROWithoutRolesfromEntity(User user) {
        if (user == null)
            return null;
        UserSRO sro = new UserSRO();

        sro.setId(user.getId());
        sro.setChannelCode(user.getChannelCode());
        sro.setCreated(user.getCreated());
        sro.setDisplayName(user.getDisplayName());
        sro.setEmail(user.getEmail());
        sro.setEmailVerified(user.isEmailVerified());
        sro.setEnabled(user.isEnabled());
        sro.setFirstName(user.getFirstName());
        sro.setGender(user.getGender());
        sro.setLastName(user.getLastName());
        sro.setLocation(user.getLocation());
        sro.setMiddleName(user.getMiddleName());
        sro.setMobile(user.getMobile());
        sro.setMobileVerified(user.isMobileVerified());
        sro.setModified(user.getModified());
        sro.setSdCash(user.getSdCash());
        sro.setSdCashEarned(user.getSdCashEarned());
        //sro.setAccountType(null);
        sro.setUserRoles(null);
        sro.setPassword(user.getPassword());
        sro.setBirthday(user.getBirthday());
        sro.setSource(user.getSource());
        sro.setUid(user.getUid());
        sro.setPhoto(user.getPhoto());
        sro.setReferredBy(user.getReferredBy());
        sro.setReferralCount(user.getReferralCount());
        sro.setMobileVerificationCode(user.getMobileVerificationCode());
        sro.setEmailVerificationCode(user.getEmailVerificationCode());
        sro.setAutoCreated(user.isAutocreated());
        sro.setFriendsUids(user.getFriendUids());

        SubscriberProfile profile = user.getSubscriberProfile();
        if (profile != null)
            sro.setSubscriberProfileId(profile.getId());

        return sro;
    }

    @Override
    public UserSRO getUserSROWithRolesfromEnity(User user) {
        if (user == null)
            return null;
        UserSRO sro = new UserSRO();

        sro.setId(user.getId());
        sro.setChannelCode(user.getChannelCode());
        sro.setCreated(user.getCreated());
        sro.setDisplayName(user.getDisplayName());
        sro.setEmail(user.getEmail());
        sro.setEmailVerified(user.isEmailVerified());
        sro.setEnabled(user.isEnabled());
        sro.setFirstName(user.getFirstName());
        sro.setGender(user.getGender());
        sro.setLastName(user.getLastName());
        sro.setLocation(user.getLocation());
        sro.setMiddleName(user.getMiddleName());
        sro.setMobile(user.getMobile());
        sro.setMobileVerified(user.isMobileVerified());
        sro.setModified(user.getModified());
        sro.setSdCash(user.getSdCash());
        sro.setSdCashEarned(user.getSdCashEarned());
        sro.setPassword(user.getPassword());
        sro.setBirthday(user.getBirthday());
        sro.setSource(user.getSource());
        sro.setUid(user.getUid());
        sro.setPhoto(user.getPhoto());
        sro.setReferredBy(user.getReferredBy());
        sro.setReferralCount(user.getReferralCount());
        sro.setMobileVerificationCode(user.getMobileVerificationCode());
        sro.setEmailVerificationCode(user.getEmailVerificationCode());
        sro.setAutoCreated(user.isAutocreated());
        sro.setFriendsUids(user.getFriendUids());
        
        if (user.getUserRoles() != null) {
            Set<UserRoleSRO> userRoleSROs = new HashSet<UserRoleSRO>();
            for (UserRole role : user.getUserRoles())
                userRoleSROs.add(getUserRoleSROfromEntity(role));
            sro.setUserRoles(userRoleSROs);
        }
        
        SubscriberProfile profile = user.getSubscriberProfile();
        if (profile != null)
            sro.setSubscriberProfileId(profile.getId());
        return sro;
    }

    @Override
    public UserRoleSRO getUserRoleSROfromEntity(UserRole role) {
        if (role == null)
            return null;
        UserRoleSRO sro = new UserRoleSRO();
        sro.setId(role.getId());
        sro.setUserId(role.getUser().getId());
        sro.setRole(role.getRole().getCode());

        //   Set<RoleZoneMappingSRO> roleZoneMappings = new HashSet<RoleZoneMappingSRO>();
        /*// LOG.info("yehan lazy initialize exception aata hai");
         for (RoleZoneMapping rzm : role.getRoleZoneMappings())
             roleZoneMappings.add(getRoleZoneMappingSROfromEntity(rzm));*/
        // sro.setRoleZoneMappings(roleZoneMappings);
        return sro;

    }

    
    /*    @Override
        public RoleZoneMappingSRO getRoleZoneMappingSROfromEntity(RoleZoneMapping rzm) {
            if (rzm == null)
                return null;
            RoleZoneMappingSRO sro = new RoleZoneMappingSRO();
            sro.setId(rzm.getId());
            sro.setUserRoleId(rzm.getUserRole().getId());
            sro.setZone(rzm.getZone().getId());
            return sro;
        }*/

    /*  @Override
      public ZoneSRO getZoneSROfromEntity(Zone z) {
          if (z == null)
              return null;
          ZoneSRO zone = new ZoneSRO();
          zone.setId(z.getId());
          zone.setName(z.getName());
          zone.setPageUrl(z.getPageUrl());
          zone.setEnabled(z.getEnabled() != null ? z.getEnabled() : true);
          zone.setFbPageName(z.getFbPageName());
          zone.setPriority(z.getPriority());
          zone.setMetaKeywords(z.getMetaKeywords());
          zone.setMetaDescription(z.getMetaDescription());
          zone.setPageTitle(z.getPageTitle());
          zone.setGetawaysMetaDescription(z.getGetawaysMetaDescription());
          zone.setGetawaysMetaKeywords(z.getGetawaysMetaKeywords());
          zone.setGetawaysPageTitle(z.getGetawaysPageTitle());
          zone.setAreas(z.getAreas());
          zone.setTwitterPageName(z.getTwitterPageName());
          zone.setGroupBuyEmail(z.getGroupBuyEmail());
          zone.setLatitude(z.getLatitude());
          zone.setLongitude(z.getLongitude());
          zone.setAuditTeamEmail(z.getAuditTeamEmail());
          zone.setUpdateMode(z.getUpdateMode());
          zone.setCanonicalTag(z.getCanonicalTag());
          zone.setStaticText(z.getStaticText());
          return zone;
      }*/

    @Override
    public UserReferralSRO getUserReferralSROfromEntity(UserReferral referral) {
        if (referral == null)
            return null;
        UserReferralSRO sro = new UserReferralSRO();
        sro.setId(referral.getId());
        sro.setUser(null);//TODO:Check if we need to fetch user here.
        sro.setEmail(referral.getEmail());
        sro.setConverted(referral.isConverted());
        sro.setUpdated(referral.getUpdated());
        sro.setCreated(referral.getCreated());
        return sro;
    }

    @Override
    public UserInformationSRO getUserInformationSROfromEntity(UserInformation information) {
        if (information == null)
            return null;
        UserInformationSRO sro = new UserInformationSRO();
        sro.setId(information.getId());
        sro.setUserId((information.getUser().getId()));
        sro.setName(information.getName());
        sro.setValue(information.getValue());
        return sro;
    }

    @Override
    public ZendeskUserSRO getZendeskUserSROfromEntity(ZendeskUser zendeskUser) {
        if (zendeskUser == null)
            return null;
        ZendeskUserSRO sro = new ZendeskUserSRO();
        sro.setId(zendeskUser.getId());
        sro.setUser(getUserSROWithoutRolesfromEntity(zendeskUser.getUser()));
        sro.setUpdated(zendeskUser.getUpdated());
        sro.setPassword(zendeskUser.getPassword());
        return sro;
    }

    @Override
    public UserSDCashHistorySRO getUserSDCashHistorySROfromEntity(UserSDCashHistory history) {
        /*  if (history == null)
              return null;
          UserSDCashHistorySRO sro = new UserSDCashHistorySRO();
          sro.setId(history.getId());
          sro.setCreated(history.getCreated());
          sro.setUserId((history.getUserId()));
          sro.setActivityType(history.getActivityType());
          sro.setActivityId(history.getActivityId());
          sro.setSdCashChange(history.getSdCashChange());
          return sro;*/
        return null;
    }

    @Override
    public UserSDCashHistory getUserSDCashHistoryEntityfromSRO(UserSDCashHistorySRO sro) {

        //        if (sro == null)
        //            return null;
        //        UserSDCashHistory entity = new UserSDCashHistory();
        //        entity.setId(sro.getId());
        //        entity.setCreated(sro.getCreated());
        //        entity.setUser(usersDao.getUserById(sro.getUserId()));
        //        entity.setActivityType(sro.getActivityType());
        //        entity.setActivityId(sro.getActivityId());
        //        entity.setSdCashChange(sro.getSdCashChange());
        //
        //        return entity;
        return null;
    }

    @Override
    public UserPreferenceSRO getUserPreferenceSROFromEntity(UserPreference pref) {
        if (pref == null)
            return null;
        UserPreferenceSRO sro = new UserPreferenceSRO();
        sro.setId(pref.getId());
        sro.setPhoneNo(pref.getPhoneNo());
        sro.setPreference(pref.getPreference());
        return sro;
    }

    @Override
    public UserPreference getUserPreferenceEntityFromSRO(UserPreferenceSRO sro) {
        if (sro == null)
            return null;
        UserPreference entity = new UserPreference();
        entity.setId(sro.getId());
        entity.setPhoneNo(sro.getPhoneNo());
        entity.setPreference(sro.getPreference());

        return entity;
    }

    @Override
    public EmailVerificationCodeSRO getEmailVerificationCodeSROfromEntity(EmailVerificationCode emailVerificationCode) {
        if (emailVerificationCode == null)
            return null;
        EmailVerificationCodeSRO sro = new EmailVerificationCodeSRO();
        sro.setCode(emailVerificationCode.getCode());
        sro.setSource(emailVerificationCode.getSource());
        sro.setTargetUrl(emailVerificationCode.getTargetUrl());
        return sro;
    }

    @Override
    public UserRoleSRO getUserRoleSROfromDTO(UserRoleDTO dto) {
        if (dto == null)
            return null;
        else {
            UserRoleSRO userRoleSRO = new UserRoleSRO();
            userRoleSRO.setEmail(dto.getEmail());
            userRoleSRO.setId(dto.getId());
            //            userRoleSRO.setRole(dto.getRole()); // TODO  Ghanshyam
            return null;
        }
    }

    @Override
    public void updateUserEntityFromSRO(UserSRO userSRO, User user) {
        if (userSRO == null) {
            return;
        }
		String email = userSRO.getEmail().trim().toLowerCase();
        user.setPassword(userSRO.getPassword());
        user.setEnabled(userSRO.isEnabled());
        user.setChannelCode(userSRO.getChannelCode());
        user.setCreated(userSRO.getCreated());
        user.setDisplayName(userSRO.getDisplayName());

        user.setEmailVerified(userSRO.isEmailVerified());

        user.setFirstName(userSRO.getFirstName());
        user.setGender(userSRO.getGender());
        user.setLastName(userSRO.getLastName());
        user.setLocation(userSRO.getLocation());
        user.setMiddleName(userSRO.getMiddleName());
        user.setMobile(userSRO.getMobile());
        user.setMobileVerified(userSRO.isMobileVerified());
        user.setModified(userSRO.getModified());
        user.setSdCash(userSRO.getSdCash());
        user.setSdCashEarned(userSRO.getSdCashEarned());
        if (userSRO.getUserRoles() != null) {
            Set<UserRoleSRO> userRoles = userSRO.getUserRoles();
            Set<UserRole> userRoleEntities = new HashSet<UserRole>();
			for (UserRoleSRO roleSRO : userRoles) {
				// Not allow non-snapdeal users to be provided any other role
				// apart from unverified or registered
				restrictRolesToNonSnapdealUser(email, userRoleEntities, roleSRO);

			}
            user.setUserRoles(userRoleEntities);
        }
        user.setBirthday(userSRO.getBirthday());
        user.setPhoto(userSRO.getPhoto());
        user.setReferredBy(userSRO.getReferredBy());
        user.setReferralCount(userSRO.getReferralCount());
        user.setMobileVerificationCode(userSRO.getMobileVerificationCode());
        user.setEmailVerificationCode(userSRO.getEmailVerificationCode());
        user.setSource(userSRO.getSource());
        user.setAutocreated(userSRO.getAutoCreated());
        user.setUid(userSRO.getUid());
        user.setFriendUids(userSRO.getFriendsUids());

        Integer subscriberProfileId = userSRO.getSubscriberProfileId();
        if (subscriberProfileId != null) {
            SubscriberProfile profile = usersDao.getSubscriberProfileById(subscriberProfileId);
            user.setSubscriberProfile(profile);
        }

    }
    
	/**
	 * Not allow non-snapdeal users to be provided any other role apart from
	 * unverified or registered
	 * 
	 * @param email
	 * @param userRoleEntities
	 * @param userRoleSRO
	 */
	private void restrictRolesToNonSnapdealUser(String email, Set<UserRole> userRoleEntities, UserRoleSRO userRoleSRO) {
		boolean isNonSnapdealUser = !(email.endsWith("@snapdeal.com") || email.endsWith("@jasperindia.com"));
		boolean isAuthRoleForNonSnapdealUser = userRoleSRO.getRole().equals(UserRoleSRO.Role.REGISTERED.role())
				|| userRoleSRO.getRole().equals(UserRoleSRO.Role.UNVERIFIED.role());
		if (isNonSnapdealUser) {
			if (isAuthRoleForNonSnapdealUser) {
				userRoleEntities.add(getUserRoleEntityfromSRO(userRoleSRO));
			} else {
				LOG.info(
						"Attempt of authority breach supressed, role: " + userRoleSRO.getRole() + " to user: " + email);
			}
		} else {
			userRoleEntities.add(getUserRoleEntityfromSRO(userRoleSRO));
		}
	}

    
    @Override
    public User getUserEntityFromSRO(UserSRO userSRO) {
        if (userSRO == null)
            return null;
        User user = new User();
        user.setId(userSRO.getId());
        user.setEmail(userSRO.getEmail());
        
        updateUserEntityFromSRO(userSRO, user);
        return user;
        
    }

    @Autowired
    IUserRoleService userRoleService;

    @Override
    public UserRole getUserRoleEntityfromSRO(UserRoleSRO userRoleSRO) {
        if (userRoleSRO == null)
            return null;

        UserRole entity = new UserRole();
        entity.setId(userRoleSRO.getId());
        entity.setRole(userRoleService.getRoleByCode(userRoleSRO.getRole()));

        entity.setUser(usersDao.getUserById(userRoleSRO.getUserId()));

        return entity;
    }

    @Override
    public Role getRoleEntityfromSRO(RoleSRO roleSRO) {
        if (roleSRO == null)
            return null;

        Role role = new Role();
        role.setId(roleSRO.getId());
        role.setCode(roleSRO.getCode());
        role.setDescription(roleSRO.getDescription());

        return role;
    }

    /*@Override
    public RoleZoneMapping getRoleZoneMappingEntityFromSRO(RoleZoneMappingSRO rzmSRO) {
        if (rzmSRO == null)
            return null;

        RoleZoneMapping entity = new RoleZoneMapping();
        entity.setId(rzmSRO.getId());
        entity.setUserRole(usersDao.getUserRoleById(rzmSRO.getUserRoleId()));//TODO:Naveen check if this works.
        entity.setZone(new Zone(rzmSRO.getZone().getId()));//TODO:Naveen always returning zone by id is that ok?
        return entity;
    }*/

    @Override
    public UserInformation getUserInfomationEntityFromSRO(UserInformationSRO infotoAdd) {
        if (infotoAdd == null)
            return null;
        UserInformation entity = new UserInformation();
        entity.setId(infotoAdd.getId());
        entity.setName(infotoAdd.getName());
        entity.setValue(infotoAdd.getValue());
        entity.setUser(usersDao.getUserById(infotoAdd.getUserId()));
        return entity;
    }

    @Override
    public CommunicationAdminFilterSRO getCommunicationAdminFilterSROfromDTO(CommunicationAdminFilterDTO commAdmFilter) {
        if (commAdmFilter == null)
            return null;
        return new CommunicationAdminFilterSRO(commAdmFilter);
    }

    @Override
    public CustomerFilter getCustomerFilterEntityFromSRO(CustomerFilterSRO sro) {
        if (sro == null)
            return null;
        CustomerFilter entity = new CustomerFilter();
        entity.setId(sro.getId());
        entity.setName(sro.getName());
        entity.setDisplayValue(sro.getDisplayValue());
        entity.setEnabled(sro.isEnabled());
        entity.setSelected(sro.isSelected());
        entity.setFilterDomain(CustomerFilter.FilterDomain.valueOf(sro.getFilterDomain().toString()));
        entity.setIntervalOffset(sro.getIntervalOffset());
        return entity;
    }

    @Override
    public CommunicationAdminFilterDTO getCommunicationAdminFilterDTOfromSRO(CommunicationAdminFilterSRO sro) {
        if (sro == null)
            return null;

        CommunicationAdminFilterDTO dto = new CommunicationAdminFilterDTO();
        dto.setId(sro.getId());
        dto.setDisplayValue(sro.getDisplayValue());
        dto.setOffsetHours(sro.getOffsetHours());
        dto.setOffsetMins(sro.getOffsetMins());
        dto.setDefaultSelected(sro.isDefaultSelected());
        dto.setSelected(sro.isSelected());
        return dto;
    }

    @Override
    public NewsletterSRO getNewsletterSROfromEntity(Newsletter newsletter) {
        if (newsletter == null)
            return null;
        NewsletterSRO sro = new NewsletterSRO();

        sro.setId(newsletter.getId());
        sro.setSubject(newsletter.getSubject());
        sro.setContent(newsletter.getContent());
        sro.setEnabled(newsletter.isEnabled());
        sro.setCityIds(newsletter.getCityIds());
        sro.setNumEmailSent(newsletter.getNumEmailSent());
        sro.setMessageId(newsletter.getMessageId());
        sro.setLyrisId(newsletter.getLyrisId());
        sro.setState(newsletter.getState());
        sro.setScheduleDate(newsletter.getScheduleDate());
        sro.setFilterTypes(newsletter.getFilterTypes());
        sro.setCreated(newsletter.getCreated());
        sro.setUpdated(newsletter.getUpdated());
        return sro;
    }

    @Override
    public NewsletterEspMappingSRO getNewsletterEspMappingSRO(NewsletterEspMapping nem) {
        if (nem == null)
            return null;
        NewsletterEspMappingSRO sro = new NewsletterEspMappingSRO();
        sro.setId(nem.getId());
        sro.setEmailServiceProvider(getEmailServiceProviderSROFromEntity(nem.getEmailServiceProvider()));
        sro.setNewsletter(getNewsletterSROfromEntity(nem.getNewsletter()));
        sro.setFilterType(nem.getFilterType());
        sro.setMessageId(nem.getMessageId());
        sro.setCityId(nem.getCityId());
        sro.setStatus(NewsletterEspMappingSRO.State.valueOf(nem.getStatus().toString()));

        return sro;
    }

    @Override
    public Newsletter getNewsletterEntityFromSRO(NewsletterSRO sro) {
        if (sro == null)
            return null;

        Newsletter entity = new Newsletter();
        entity.setId(sro.getId());
        entity.setCityIds(sro.getCityIds());
        entity.setContent(sro.getContent());
        entity.setCreated(sro.getCreated());
        entity.setEnabled(sro.isEnabled());
        entity.setFilterTypes(sro.getFilterTypes());
        entity.setLyrisId(sro.getFilterTypes());
        entity.setMessageId(sro.getMessageId());
        entity.setNumEmailSent(sro.getNumEmailSent());
        entity.setScheduleDate(sro.getScheduleDate());
        entity.setState(sro.getState());
        entity.setSubject(sro.getSubject());
        entity.setUpdated(DateUtils.getCurrentTime());

        return entity;
    }

    @Override
    public NewsletterEspMapping getNewsletterEspMappingEntityFromSRO(NewsletterEspMappingSRO sro) {
        if (sro == null)
            return null;

        NewsletterEspMapping entity = new NewsletterEspMapping();
        entity.setCityId(sro.getCityId());
        entity.setEmailServiceProvider(getEmailServiceProviderEntityFromSRO(sro.getEmailServiceProvider()));
        entity.setFilterType(sro.getFilterType());
        entity.setId(sro.getId());
        entity.setMessageId(sro.getMessageId());
        entity.setNewsletter(getNewsletterEntityFromSRO(sro.getNewsletter()));
        entity.setStatus(NewsletterEspMapping.State.valueOf(sro.getStatus().toString()));

        return entity;
    }

    @Override
    public EmailServiceProvider getEmailServiceProviderEntityFromSRO(EmailServiceProviderSRO emailServiceProvider) {
        if (emailServiceProvider == null)
            return null;
        EmailServiceProvider provider = new EmailServiceProvider();
        provider.setId(emailServiceProvider.getId());
        provider.setName(provider.getName());
        provider.setSiteId(emailServiceProvider.getSiteId());
        provider.setImplClass(emailServiceProvider.getImplClass());
        return provider;
    }

    @Override
    public SmsSchedulerSRO getSmsSchedulerSROfromEntity(SmsScheduler smsScheduler) {
        if (smsScheduler == null)
            return null;

        SmsSchedulerSRO sro = new SmsSchedulerSRO();
        sro.setId(smsScheduler.getId());
        sro.setCityIds(smsScheduler.getCityIds());
        sro.setFilterType(smsScheduler.getFilterType());
        sro.setScheduleTime(smsScheduler.getScheduleTime());
        sro.setSmsSenderResponse(smsScheduler.getSmsSenderResponse());
        sro.setSmsSenderService(smsScheduler.getSmsSenderService());
        sro.setSmsText(smsScheduler.getSmsText());
        sro.setStatus(smsScheduler.getStatus());
        sro.setTotalSmsSent(smsScheduler.getTotalSmsSent());

        return sro;
    }

    @Override
    public SmsScheduler getSmsSchedulerEntityFromSRO(SmsSchedulerSRO sro) {
        if (sro == null)
            return null;
        SmsScheduler entity = new SmsScheduler();
        entity.setId(sro.getId());
        entity.setCityIds(sro.getCityIds());
        entity.setFilterType(sro.getFilterType());
        entity.setScheduleTime(sro.getScheduleTime());
        entity.setSmsSenderResponse(sro.getSmsSenderResponse());
        entity.setSmsSenderService(sro.getSmsSenderService());
        entity.setSmsText(sro.getSmsText());
        entity.setStatus(sro.getStatus());
        entity.setTotalSmsSent(sro.getTotalSmsSent());

        return entity;

    }

    @Override
    public Activity getActivityfromSRO(ActivitySRO activitySRO) {
        /*    if (activitySRO == null)
                return null;
            Activity entity = new Activity();
            entity.setId(activitySRO.getId());
            entity.setActivityType(CacheManager.getInstance().getCache(ActivityTypeCache.class).getActivityTypeByCode(activitySRO.getActivityType().getCode()));
            entity.setAttributes(activitySRO.getAttributes());
            entity.setCreated(activitySRO.getCreated());
            entity.setIpAddress(activitySRO.getIpAddress());
            entity.setSdCash(activitySRO.getSdCash());
            entity.setUserId(activitySRO.getUserId());
            entity.setValue(activitySRO.getValue());
            return entity;*/
        return null;

    }

    @Override
    public ActivitySRO getActivitySROfromEntity(Activity activity) {
        /*        if (activity == null)
            return null;
        ActivitySRO sro = new ActivitySRO();
        sro.setId(activity.getId());
        sro.setUserId(activity.getUserId());
        sro.setActivityType(new ActivityTypeSRO(activity.getActivityType()));
        sro.setIpAddress(activity.getIpAddress());
        sro.setAttributes(activity.getAttributes());
        sro.setSdCash(activity.getSdCash());
        sro.setCreated(activity.getCreated());
        sro.setValue(activity.getValue());
        return sro;*/
        return null;
    }

    @Override
    public EmailBulkEspCityMapping getEmailBulkEspCityMappingEntityFromSRO(EmailBulkEspCityMappingSRO sro) {
        if (sro == null)
            return null;

        EmailBulkEspCityMapping entity = new EmailBulkEspCityMapping();
        entity.setCityId(sro.getCityId());
        entity.setEsp(getEmailServiceProviderEntityFromSRO(sro.getEsp()));
        entity.setId(sro.getId());

        return entity;
    }

    @Override
    public EmailBulkEspCityMappingSRO getEmailBulkEspCityMappingSROFromEntity(EmailBulkEspCityMapping mapping) {
        if (mapping == null)
            return null;
        EmailBulkEspCityMappingSRO sro = new EmailBulkEspCityMappingSRO();
        sro.setCityId(mapping.getCityId());
        sro.setEsp(getEmailServiceProviderSROFromEntity(mapping.getEsp()));
        sro.setId(mapping.getId());

        return sro;
    }

    @Override
    public ESPFilterCityMapping getESPFilterCityMappingEntityFromSRO(ESPFilterCityMappingSRO sro) {
        if (sro == null)
            return null;
        ESPFilterCityMapping entity = new ESPFilterCityMapping();
        entity.setCityId(sro.getCityId());
        entity.setEmailServiceProvider(getEmailServiceProviderEntityFromSRO(sro.getEmailServiceProvider()));
        entity.setFilter(sro.getFilter());
        entity.setFilterType(sro.getFilterType());
        entity.setId(sro.getId());
        entity.setMlid(sro.getMlid());
        entity.setSiteId(sro.getSiteId());
        return entity;
    }

    @Override
    public ESPFilterCityMappingSRO getESPFilterCityMappingSROFromEntity(ESPFilterCityMapping mapping) {
        if (mapping == null)
            return null;
        ESPFilterCityMappingSRO sro = new ESPFilterCityMappingSRO();
        sro.setId(mapping.getId());
        sro.setFilter(mapping.getFilter());
        sro.setCityId(mapping.getCityId());
        sro.setMlid(mapping.getMlid());
        sro.setFilterType(mapping.getFilterType());
        sro.setSiteId(mapping.getSiteId());
        sro.setEmailServiceProvider(getEmailServiceProviderSROFromEntity(mapping.getEmailServiceProvider()));
        return sro;
    }

    @Override
    public ESPProfileFieldSRO getESPProfileFieldSROFromEntity(ESPProfileField field) {
        if (field == null)
            return null;
        ESPProfileFieldSRO sro = new ESPProfileFieldSRO();
        sro.setId(field.getId());
        sro.setFieldName(field.getFieldName());
        sro.setEspId(field.getEspId());
        sro.setEspFieldName(field.getEspFieldName());

        return sro;
    }

    /*
        @Override
        public City getCityEntityFromSRO(CitySRO citySRO) {
            if (citySRO == null)
                return null;
            City entity = new City();
            entity.setDefaultLocation(citySRO.getDefaultLocation());
            entity.setEnabled(citySRO.isEnabled());
            entity.setId(citySRO.getId());
            entity.setLaunched(citySRO.getLaunched());
            entity.setLocations(citySRO.getLocations());
            entity.setName(citySRO.getName());
            entity.setPageUrl(citySRO.getPageUrl());
            entity.setPriority(citySRO.getPriority());
            entity.setSynonyms(citySRO.getSynonyms());
            entity.setVisible(citySRO.getVisible());

            Set<Zone> zones = new HashSet<Zone>();
            for (int id : citySRO.getZoneIds())
                zones.add(new Zone(id));
            entity.setZones(zones);

            return entity;
        }

        @Override
        public CitySRO getCitySROFromEntity(City c) {
            if (c == null)
                return null;

            CitySRO city = new CitySRO();
            city.setId(c.getId());
            city.setName(c.getName());
            city.setPriority(c.getPriority());
            city.setEnabled(c.getEnabled() != null ? c.getEnabled() : true);
            city.setPageUrl(c.getPageUrl());
            for (Zone z : c.getZones()) {
                city.getZoneIds().add(z.getId());
            }
            city.setLaunched(c.getLaunched());
            city.setLocations(c.getLocations());
            city.setDefaultLocation(c.getDefaultLocation());
            city.setSynonyms(c.getSynonyms());
            city.setVisible(c.getVisible());
            return city;
        }*/

    /*   @Override
       public AffiliateDealPrice getAffiliateDealPricesEntityFromSRO(AffiliateDealPriceSRO sro) {

           return new AffiliateDealPrice(sro.getDeal(), sro.getAffiliates(), sro.getPrice(), sro.getPayToMerchant(), sro.getVoucherPrice(), sro.isNoPromoCode());
       }*/

    @Override
    public Corporate getCorporateEmailEntityFromSRO(CorporateSRO sro) {
        if (sro == null)
            return null;

        Corporate entity = new Corporate();
        entity.setCity(sro.getCity());
        entity.setCompanyName(sro.getCompanyName());
        entity.setCompanyWebsite(sro.getCompanyWebsite());
        entity.setContactName(sro.getContactName());
        entity.setCreated(sro.getCreated());
        entity.setEmail(sro.getEmail());
        entity.setId(sro.getId());
        entity.setMobile(sro.getMobile());
        return entity;
    }

    @Override
    public CustomerQuery getCustomerQueryEntityFromSRO(CustomerQuerySRO sro) {
        if (sro == null)
            return null;

        CustomerQuery entity = new CustomerQuery();
        entity.setCity(sro.getCity());
        entity.setComments(sro.getComments());
        entity.setCreated(sro.getCreated());
        entity.setEmail(sro.getEmail());
        entity.setFullName(sro.getFullName());
        entity.setId(sro.getId());
        entity.setMobile(sro.getMobile());
        entity.setSubject(sro.getSubject());
        return entity;

    }

    /*   @Override
       public Zone getZoneEntityFromSRO(ZoneSRO sro) {
           if (sro == null)
               return null;

           Zone entity = new Zone();
           entity.setAreas(sro.getAreas());
           entity.setAuditTeamEmail(sro.getAuditTeamEmail());
           entity.setCanonicalTag(sro.getCanonicalTag());
           entity.setCity(getCityEntityFromSRO(sro.getCity()));
           entity.setEnabled(sro.getEnabled());
           entity.setFbPageName(sro.getFbPageName());
           entity.setGetawaysMetaDescription(sro.getGetawaysMetaDescription());
           entity.setGetawaysMetaKeywords(sro.getGetawaysMetaKeywords());
           entity.setGetawaysPageTitle(sro.getGetawaysPageTitle());
           entity.setGroupBuyEmail(sro.getGroupBuyEmail());
           entity.setId(sro.getId());
           entity.setLatitude(sro.getLatitude());
           entity.setLongitude(sro.getLongitude());
           entity.setMetaDescription(sro.getMetaDescription());
           entity.setMetaKeywords(sro.getMetaKeywords());
           entity.setName(sro.getName());
           entity.setPageTitle(sro.getPageTitle());
           entity.setPageUrl(sro.getPageUrl());
           entity.setPriority(sro.getPriority());
           //entity.setRoleZoneMappings(sro); //TODO Naveen RZM not present in the SRO.
           entity.setStaticText(sro.getStaticText());
           entity.setTwitterPageName(sro.getTwitterPageName());
           entity.setUpdateMode(sro.getUpdateMode());

           return entity;
       }
    */
    @Override
    public Audit getAuditfromSRO(AuditSRO sro) {
        if (sro == null)
            return null;

        Audit entity = new Audit();
        entity.setChangeLog(sro.getChangeLog());
        entity.setComments(sro.getComments());
        entity.setEntityId(sro.getEntityId());
        entity.setEntityType(sro.getEntityType());
        entity.setId(sro.getId());
        entity.setModifiedBy(sro.getModifiedBy());
        entity.setModifiedTime(sro.getModifiedTime());

        return entity;
    }

    /*
        @Override
        public ProductWorkflowDTO getProductWorkFlowDTOFromSRO(ProductWorkflowSRO sro) {
            if (sro == null)
                return null;

            ProductWorkflowDTO dto = new ProductWorkflowDTO();
            dto.setActionBy(sro.getActionBy());
            dto.setCategoryURL(sro.getCategoryURL());
            dto.setComments(sro.getComments());
            dto.setProductName(sro.getProductName());
            dto.setSku(sro.getSku());
            dto.setUrl(sro.getSku());

            return dto;
        }*/

    @Override
    public BulkUploadResultDTO getBulkUploadResultsDTOfromSRO(BulkUploadResultSRO sro) {
        if (sro == null)
            return null;

        BulkUploadResultDTO dto = new BulkUploadResultDTO();
        dto.setComments(sro.getComments());
        dto.setCreatedDate(sro.getCreatedDate());
        dto.setEndDate(sro.getEndDate());
        dto.setId(sro.getId());
        dto.setName(sro.getName());
        dto.setPageUrl(sro.getPageUrl());
        dto.setStartDate(sro.getStartDate());
        dto.setSupc(sro.getSupc());
        dto.setValid(sro.isValid());
        dto.setVendorCode(sro.getVendorCode());
        dto.setVendorSku(sro.getVendorSku());

        return dto;
    }

    @Override
    public CancelledOrderFeedbackDO getcancelledOrderFeedbackDOfromSRO(CancelledOrderFeedbackDOSRO cancelledOrderFeedbackSRO) {

        if (cancelledOrderFeedbackSRO == null)
            return null;
        CancelledOrderFeedbackDO dto = new CancelledOrderFeedbackDO();
        Map<Integer, String> map = new HashMap<Integer, String>();
        List<MapEntryUtil> list = cancelledOrderFeedbackSRO.getCancelledSuborders();
        for (MapEntryUtil entry : list)
            map.put(entry.getKey(), entry.getValue());

        dto.setCancelledSuborders(map);
        dto.setEmail(cancelledOrderFeedbackSRO.getEmail());
        dto.setOrderCode(cancelledOrderFeedbackSRO.getOrderCode());
        dto.setOrderId(cancelledOrderFeedbackSRO.getOrderId());
        dto.setUserName(cancelledOrderFeedbackSRO.getUserName());

        return dto;
    }

    @Override
    public ProductMultiVendorMappingResultDTO getProductMultiVendorMappingResultDTOfromSRO(ProductMultiVendorMappingResultSRO sro) {
        if (sro == null)
            return null;

        ProductMultiVendorMappingResultDTO dto = new ProductMultiVendorMappingResultDTO();
        dto.setMessage(sro.getMessage());
        dto.setSupc(sro.getSupc());
        dto.setVendorCode(sro.getVendorCode());

        return dto;
    }

    @Override
    public GetFeatured getFeaturedEntityFromSRO(FeaturedSRO featureSro) {
        if (featureSro == null)
            return null;

        GetFeatured entity = new GetFeatured();
        entity.setBusinessType(featureSro.getBusinessType());
        entity.setCity(featureSro.getCity());
        entity.setCompanyName(featureSro.getCompanyName());
        entity.setContactName(featureSro.getContactName());
        entity.setCreated(featureSro.getCreated());
        entity.setEmail(featureSro.getEmail());
        entity.setExtraInfo(featureSro.getExtraInfo());
        entity.setId(featureSro.getId());
        entity.setMobile(featureSro.getMobile());
        return entity;
    }

    @Override
    public CorporateSRO getCorporateSROFromEntity(Corporate corporate) {
        if (corporate == null)
            return null;

        return new CorporateSRO(corporate);
    }

    @Override
    public CancelledOrderFeedbackDOSRO getCancelledOrderFeedbackSROFromDO(CancelledOrderFeedbackDO cancelledOrderFeedbackDTO) {
        if (cancelledOrderFeedbackDTO == null)
            return null;

        return new CancelledOrderFeedbackDOSRO(cancelledOrderFeedbackDTO);

    }

    @Override
    public FeaturedSRO getFeaturedSROFromEntity(GetFeatured featured) {
        if (featured == null)
            return null;
        return new FeaturedSRO(featured);
    }

    @Override
    public CustomerQuerySRO getCustomerQuerySROFromEntity(CustomerQuery customerQuery) {
        if (customerQuery == null)
            return null;
        return new CustomerQuerySRO(customerQuery);
    }

    @Override
    public AuditSRO getAuditSROFromEntity(Audit audit) {
        if (audit == null)
            return null;
        return new AuditSRO(audit);
    }

    @Override
    public BulkUploadResultSRO getBulkUploadResultSROFromDTOs(BulkUploadResultDTO resultDTO) {
        if (resultDTO == null)
            return null;
        return new BulkUploadResultSRO(resultDTO);
    }

    @Override
    public ProductMultiVendorMappingResultSRO getProductMultiVendorMappingResultSROFromDTO(ProductMultiVendorMappingResultDTO dto) {
        if (dto == null)
            return null;
        return new ProductMultiVendorMappingResultSRO(dto);
    }

    /*
        @Override
        public ProductWorkflowSRO getProductWorkFlowSROFromDTO(ProductWorkflowDTO dto) {
            if (dto == null)
                return null;
            return new ProductWorkflowSRO(dto);
        }*/

    @Override
    public UserReferral getUserReferralEntityfromSRO(UserReferralSRO sro) {
        if (sro == null)
            return null;

        UserReferral entity = new UserReferral();
        entity.setId(sro.getId());
        entity.setUser(getUserEntityFromSRO(sro.getUser()));
        entity.setEmail(sro.getEmail());
        entity.setConverted(sro.isConverted());
        entity.setCreated(sro.getCreated());
        entity.setUpdated(DateUtils.getCurrentTime());
        return entity;
    }

    @Override
    public ZendeskUser getZendeskUserEntityfromSRO(ZendeskUserSRO sro) {
        if (sro == null)
            return null;
        ZendeskUser entity = new ZendeskUser();
        entity.setId(sro.getId());
        entity.setUser(getUserEntityFromSRO(sro.getUser()));
        entity.setPassword(sro.getPassword());
        entity.setUpdated(DateUtils.getCurrentTime());
        return entity;
    }

    @Override
    public EmailVerificationCode getEmailVerificationCodeEntityfromSRO(EmailVerificationCodeSRO emailVerificationCodeSRO) {
        if (emailVerificationCodeSRO == null)
            return null;

        EmailVerificationCode entity = new EmailVerificationCode();
        entity.setCode(emailVerificationCodeSRO.getCode());
        entity.setSource(emailVerificationCodeSRO.getSource());
        entity.setTargetUrl(emailVerificationCodeSRO.getTargetUrl());
        return entity;
    }

    @Override
    public UserRoleDTO getUserRoleDTOfromSRO(UserRoleSRO sro) {
        if (sro == null)
            return null;

        UserRoleDTO dto = new UserRoleDTO();
        dto.setId(sro.getId());
        dto.setEmail(sro.getEmail());
        dto.setRole(sro.getRole().toString());
        return dto;
    }

    @Override
    public ESPProfileField getESPProfileFieldEntityFromSRO(ESPProfileFieldSRO sro) {
        if (sro == null)
            return null;
        ESPProfileField entity = new ESPProfileField();
        entity.setId(sro.getId());
        entity.setEspFieldName(sro.getEspFieldName());
        entity.setespId(sro.getEspId());
        entity.setFieldName(sro.getFieldName());

        return entity;
    }

    @Override
    public EmailSubscriberDetailSRO getEmailSubscriberDetailSROfromEntity(EmailSubscriberDetail detail) {
        if (detail == null)
            return null;
        EmailSubscriberDetailSRO sro = new EmailSubscriberDetailSRO();
        sro.setId(detail.getId());
        sro.setEmail(detail.getEmail());
        sro.setCode(detail.getCode());
        sro.setVerified(detail.isVerified());
        sro.setUid(detail.getUid());
        sro.setSubscriberProfile(getSubscriberProfileSROFromEntity(detail.getSubscriberProfile()));
        sro.setUpdated(detail.getUpdated());
        sro.setCreated(detail.getCreated());
        sro.setJunk(detail.isJunk());
        sro.setGender(detail.getGender());
        return sro;
    }

    @Override
    public EmailSubscriberDetail getEmailSubscriberDetailEntityFromSRO(EmailSubscriberDetailSRO sro) {
        if (sro == null)
            return null;
        EmailSubscriberDetail entity = new EmailSubscriberDetail();
        entity.setId(sro.getId());
        entity.setEmail(sro.getEmail());
        entity.setCode(sro.getCode());
        entity.setVerified(sro.isVerified());
        entity.setUid(sro.getUid());
        Integer subscriberProfileId = sro.getSubscriberProfile() != null ? sro.getSubscriberProfile().getId() : null;
        if (subscriberProfileId != null)
            entity.setSubscriberProfile(umsSubscriptionsDao.getSubscriberProfileById(subscriberProfileId));
        entity.setCreated(sro.getCreated());
        entity.setUpdated(DateUtils.getCurrentTime());
        entity.setJunk(sro.isJunk());
        entity.setGender(sro.getGender());
        return entity;
    }

    @Override
    public EmailMobileAssociation getEmailMobileAssociationEntityFromSRO(EmailMobileAssociationSRO sro) {
        if (sro == null)
            return null;
        EmailMobileAssociation entity = new EmailMobileAssociation();
        entity.setId(sro.getId());
        entity.setEmail(sro.getEmail());
        entity.setMobile(sro.getMobile());
        entity.setVerified(sro.isVerified());
        entity.setCreated(sro.getCreated());
        return entity;
    }

    @Override
    public EmailSubscriberSRO getEmailSubscriberSROFromEntity(EmailSubscriber entity) {
        if (entity == null)
            return null;
        EmailSubscriberSRO sro = new EmailSubscriberSRO();
        sro.setId(entity.getId());
        sro.setEmail(entity.getEmail());
        sro.setNormalizedEmail(entity.getNormalizedEmail());
        sro.setZoneId(entity.getZoneId());
        sro.setSubscribed(entity.getSubscribed());
        sro.setUpdated(entity.getUpdated());
        sro.setCreated(entity.getCreated());
        sro.setSubscriptionPage(entity.getSubscriptionPage());
        sro.setCustomer(entity.isCustomer());
        sro.setReasonUnsubscription(entity.getReasonUnsubscription());
        sro.setChannelCode(entity.getChannelCode());
        sro.setAffiliateTrackingCode(entity.getAffiliateTrackingCode());
        sro.setActive(entity.isActive());
        sro.setPreference(entity.getPreference());
        sro.setEmailServiceProvider(getEmailServiceProviderSROFromEntity(entity.getEmailServiceProvider()));
        sro.setJunk(entity.isJunk());
        return sro;
    }

    @Override
    public EmailServiceProviderSRO getEmailServiceProviderSROFromEntity(EmailServiceProvider entity) {
        if (entity == null)
            return null;
        EmailServiceProviderSRO sro = new EmailServiceProviderSRO();
        sro.setId(entity.getId());
        sro.setName(entity.getName());
        sro.setSiteId(entity.getSiteId());
        sro.setImplClass(entity.getImplClass());
        return sro;
    }

    @Override
    public EmailMobileAssociationSRO getEmailMobileAssociationSROFromEntity(EmailMobileAssociation entity) {
        if (entity == null)
            return null;
        EmailMobileAssociationSRO sro = new EmailMobileAssociationSRO();
        sro.setId(entity.getId());
        sro.setEmail(entity.getEmail());
        sro.setMobile(entity.getMobile());
        sro.setVerified(entity.isVerified());
        sro.setCreated(entity.getCreated());
        return sro;
    }

    @Override
    public SubscriberProfileSRO getSubscriberProfileSROFromEntity(SubscriberProfile entity) {
        if (entity == null)
            return null;
        SubscriberProfileSRO sro = new SubscriberProfileSRO();
        sro.setId(entity.getId());
        sro.setName(entity.getName());
        sro.setDisplayName(entity.getDisplayName());
        sro.setEmail(entity.getEmail());
        sro.setGender(entity.getGender());
        sro.setZoneId(entity.getZoneId());
        sro.setLocalityId(entity.getLocalityId());
        return sro;
    }

    @Override
    public SubscriberProfile getSubscriberProfileEntityFromSRO(SubscriberProfileSRO sro) {
        if (sro == null)
            return null;
        SubscriberProfile entity = new SubscriberProfile();

        entity.setId(sro.getId());
        entity.setName(sro.getName());
        entity.setDisplayName(sro.getDisplayName());
        entity.setEmail(sro.getEmail());
        entity.setGender(sro.getGender());
        entity.setZoneId(sro.getZoneId());

        entity.setLocalityId(sro.getLocalityId());
        entity.setDob(sro.getDob());
        return entity;
    }

    @Override
    public Locality getLocalityEntityFromSRO(LocalitySRO sro) {
        if (sro == null)
            return null;
        Locality entity = new Locality();
        entity.setId(sro.getId());
        entity.setCityId(sro.getCityId());
        entity.setEnabled(sro.getEnabled());
        entity.setLatitude(sro.getLatitude());
        entity.setLongitude(sro.getLongitude());
        return entity;
    }

    @Override
    public MobileSubscriber getMobileSubscriberEntityFromSRO(MobileSubscriberSRO sro) {
        if (sro == null)
            return null;
        MobileSubscriber entity = new MobileSubscriber();

        entity.setId(sro.getId());
        entity.setMobile(sro.getMobile());
        entity.setZoneId(sro.getZoneId());
        entity.setSubscribed(sro.isSubscribed());
        entity.setCreated(sro.getCreated());
        entity.setSubscriptionPage(sro.getSubscriptionPage());
        entity.setPin(sro.getPin());
        entity.setReasonUnsubscription(sro.getReasonUnsubscription());
        entity.setSuggestionUnsubscription(sro.getSuggestionUnsubscription());
        entity.setChannelCode(sro.getChannelCode());
        entity.setAffiliateTrackingCode(sro.getAffiliateTrackingCode());
        entity.setDnd(sro.getDnd());
        entity.setInvalid(sro.isInvalid());
        entity.setVerified(sro.isVerified());
        entity.setSubscribed(sro.isSubscribed());
        entity.setCustomer(sro.isCustomer());
        entity.setUpdated(entity.getUpdated());

        return entity;
    }

    @Override
    public EmailSubscriber getEmailSubscriberEntityFromSRO(EmailSubscriberSRO sro) {
        if (sro == null)
            return null;
        EmailSubscriber entity = new EmailSubscriber();
        entity.setId(sro.getId());
        entity.setEmail(sro.getEmail());
        entity.setNormalizedEmail(sro.getNormalizedEmail());
        entity.setZoneId(sro.getZoneId());
        entity.setSubscribed(sro.isSubscribed());
        entity.setUpdated(DateUtils.getCurrentTime());
        entity.setCreated(sro.getCreated());
        entity.setSubscriptionPage(sro.getSubscriptionPage());
        entity.setCustomer(sro.isCustomer());
        entity.setReasonUnsubscription(sro.getReasonUnsubscription());
        entity.setSuggestionUnsubscription(sro.getSuggestionUnsubscription());
        entity.setChannelCode(sro.getChannelCode());
        entity.setAffiliateTrackingCode(sro.getAffiliateTrackingCode());
        entity.setActive(sro.isActive());
        entity.setPreference(sro.getPreference());
        entity.setEmailServiceProvider(getEmailServiceProviderEntityFromSRO(sro.getEmailServiceProvider()));
        entity.setJunk(sro.isJunk());

        return entity;
    }

    @Override
    public MobileSubscriberDetail getMobileSubscriberDetailEntityFromSRO(MobileSubscriberDetailSRO sro) {
        if (sro == null)
            return null;
        MobileSubscriberDetail entity = new MobileSubscriberDetail();

        entity.setId(sro.getId());
        entity.setMobile(sro.getMobile());
        entity.setVerified(sro.isVerified());
        entity.setUid(sro.getUid());
        entity.setUpdated(DateUtils.getCurrentTime());
        entity.setCreated(sro.getCreated());
        return entity;
    }

    @Override
    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferEntityFromSRO(AffiliateSubscriptionOfferSRO sro) {
        if (sro == null)
            return null;

        AffiliateSubscriptionOffer entity = new AffiliateSubscriptionOffer();
        entity.setId(sro.getId());
        entity.setOfferName(sro.getOfferName());
        entity.setImage(sro.getImage());
        entity.setPromoCode(sro.getPromoCode());
        entity.setEnabled(sro.isEnabled());
        entity.setCreated(sro.getCreated());
        entity.setUpdated(DateUtils.getCurrentTime());
        entity.setMobileEnabled(sro.isMobileEnabled());
        entity.setMobileImage(sro.getMobileImage());
        entity.setOfferText(sro.getOfferText());
        return entity;
    }

    @Override
    public EmailMessage getEmailMessageEntityFromSRO(EmailMessageSRO messageSRO) {
        if (messageSRO == null)
            return null;

        EmailMessage message = new EmailMessage(messageSRO.getTo(), messageSRO.getFrom(), messageSRO.getReplyTo(), messageSRO.getTemplateName());
        //cc bcc mailHTml TemplateParams.
        if (messageSRO.getCc() != null)
            message.addCCs(messageSRO.getCc());
        if (messageSRO.getBcc() != null)
            message.addBCCs(messageSRO.getBcc());
        if (messageSRO.getMailHTML() != null)
            message.setMailHTML(messageSRO.getMailHTML());
        if (messageSRO.getTemplateParams() != null)
            for (TemplateParam<? extends Serializable> param : messageSRO.getTemplateParams())
                message.addTemplateParam(param.getKey(), param.getValue());

        return message;

    }

    @Override
    public CustomerEmailScore getCustomerEmailScoreEntityFromSRO(CustomerScoreSRO customerScoreSRO) {
        if (customerScoreSRO == null)
            return null;
        CustomerEmailScore customerEmailScore = new CustomerEmailScore();
        customerEmailScore.setId(customerScoreSRO.getId());
        customerEmailScore.setEmail(customerScoreSRO.getEmail());
        customerEmailScore.setRiskLevel(customerScoreSRO.getRiskLevel());
        customerEmailScore.setCreated(customerScoreSRO.getCreated());
        return customerEmailScore;
    }

    @Override
    public CustomerScoreSRO getCustomerEmailScoreSROFromEntity(CustomerEmailScore customerEmailScore) {
        if (customerEmailScore == null)
            return null;
        CustomerScoreSRO customerScoreSRO = new CustomerScoreSRO();
        customerScoreSRO.setId(customerEmailScore.getId());
        customerScoreSRO.setEmail(customerEmailScore.getEmail());
        customerScoreSRO.setRiskLevel(customerEmailScore.getRiskLevel());
        customerScoreSRO.setCreated(customerEmailScore.getCreated());
        return customerScoreSRO;
    }

    @Override
    public CustomerMobileScore getCustomerMobileScoreEntityFromSRO(CustomerScoreSRO customerScoreSRO) {
        if (customerScoreSRO == null)
            return null;
        CustomerMobileScore customerMobileScore = new CustomerMobileScore();
        customerMobileScore.setId(customerScoreSRO.getId());
        customerMobileScore.setMobile(customerScoreSRO.getMobile());
        customerMobileScore.setRiskLevel(customerScoreSRO.getRiskLevel());
        customerMobileScore.setCreated(customerScoreSRO.getCreated());
        return customerMobileScore;
    }

    @Override
    public CustomerScoreSRO getCustomerMobileScoreSROFromEntity(CustomerMobileScore customerMobileScore) {
        if (customerMobileScore == null)
            return null;
        CustomerScoreSRO customerScoreSRO = new CustomerScoreSRO();
        customerScoreSRO.setId(customerMobileScore.getId());
        customerScoreSRO.setMobile(customerMobileScore.getMobile());
        customerScoreSRO.setRiskLevel(customerMobileScore.getRiskLevel());
        customerScoreSRO.setCreated(customerMobileScore.getCreated());
        return customerScoreSRO;
    }

    @Override
    public CsZentrixSRO getCsZentrixSROfromEntity(CsZentrix csZentrix) {
        if (csZentrix == null)
            return null;
        CsZentrixSRO csZentrixSRO = new CsZentrixSRO();
        csZentrixSRO.setId(csZentrix.getId());
        csZentrixSRO.setUpdated(csZentrix.getUpdated());
        csZentrixSRO.setZentrixId(csZentrix.getZentrixId());
        csZentrixSRO.setUserSRO(getUserSROWithoutRolesfromEntity(csZentrix.getUser()));
        return csZentrixSRO;
    }

    @Override
    public CsZentrix getCsZentrixEntityfromSRO(CsZentrixSRO csZentrixSRO) {
        if (csZentrixSRO == null)
            return null;
        CsZentrix csZentrix = new CsZentrix();
        csZentrix.setId(csZentrixSRO.getId());
        csZentrix.setUpdated(DateUtils.getCurrentTime());
        csZentrix.setUser(getUserEntityFromSRO(csZentrixSRO.getUserSRO()));
        csZentrix.setZentrixId(csZentrixSRO.getZentrixId());
        return csZentrix;
    }

    @Override
    public FacebookUserSRO getFacebookUserSROFromEntity(FacebookUser entity) {
        if (entity == null)
            return null;
        FacebookUserSRO sro = new FacebookUserSRO();
        sro.setCreated(entity.getCreated());
        sro.setEmailId(entity.getEmailId());
        sro.setFacebookId(entity.getFacebookId());
        for (FacebookLike fbLikes : entity.getFbLikes()) {
            sro.getFbLikes().add(getFacebookLikeSROFromEntity(fbLikes));
        }
        sro.setFbProfile(getFacebookProfileSROfromEntity(entity.getFbProfile()));
        sro.setId(entity.getId());
        sro.setUserSRO(getUserSROWithoutRolesfromEntity(entity.getUser()));

        return sro;
    }

    @Override
    public FacebookLikeSRO getFacebookLikeSROFromEntity(FacebookLike entity) {
        if (entity == null)
            return null;
        FacebookLikeSRO sro = new FacebookLikeSRO();
        sro.setCreatedAt(entity.getCreatedAt());
        sro.setEmail(sro.getEmail());
        sro.setFacebookUserId(entity.getFbUser().getId());
        sro.setId(entity.getId());
        sro.setLikeTime(entity.getLikeTime());
        sro.setPageCategory(entity.getPageCategory());
        sro.setPageId(entity.getPageId());
        sro.setPageName(entity.getPageName());
        sro.setUpdatedAt(entity.getUpdatedAt());
        sro.setUserSRO(getUserSROWithoutRolesfromEntity(entity.getUser()));

        return sro;
    }

    @Override
    public FacebookProfileSRO getFacebookProfileSROfromEntity(FacebookProfile entity) {
        if (entity == null)
            return null;
        FacebookProfileSRO sro = new FacebookProfileSRO();
        sro.setAboutMe(entity.getAboutMe());
        sro.setFirstName(entity.getFirstName());
        sro.setLastName(entity.getLastName());
        sro.setMiddleName(entity.getMiddleName());

        return sro;
    }

    @Override
    public FacebookUser getFacebookUserEntityFromSRO(FacebookUserSRO sro) {

        if (sro == null)
            return null;

        FacebookUser entity = new FacebookUser();

        entity.setCreated(sro.getCreated());
        entity.setEmailId(sro.getEmailId());
        entity.setFacebookId(sro.getFacebookId());
        for (FacebookLikeSRO fbLikeSRO : sro.getFbLikes()) {
            entity.getFbLikes().add(getFacebookLikeEntityFromSRO(fbLikeSRO));
        }
        entity.setSnapdealUser(getUserEntityFromSRO(sro.getUserSRO()));
        entity.setFbProfile(getFacebookProfileEntityFromSRO(sro.getFbProfile()));
        entity.setId(sro.getId());

        return entity;
    }

    @Override
    public FacebookLike getFacebookLikeEntityFromSRO(FacebookLikeSRO sro) {
        if (sro == null)
            return null;

        FacebookLike entity = new FacebookLike();
        entity.setCreatedAt(sro.getCreatedAt());
        entity.setEmail(sro.getEmail());
        entity.setFbUser(fbUserDao.getFacebookUserbyFBId(sro.getFacebookUserId()));
        entity.setId(sro.getId());
        entity.setLikeTime(sro.getLikeTime());
        entity.setPageCategory(sro.getPageCategory());
        entity.setPageId(sro.getPageId());
        entity.setPageName(sro.getPageName());
        entity.setUpdatedAt(DateUtils.getCurrentTime());
        entity.setUser(getUserEntityFromSRO(sro.getUserSRO()));

        return entity;
    }

    @Override
    public FacebookProfile getFacebookProfileEntityFromSRO(FacebookProfileSRO sro) {
        if (sro == null)
            return null;
        FacebookProfile entity = new FacebookProfile();
        entity.setAboutMe(sro.getAboutMe());
        entity.setFirstName(sro.getFirstName());
        entity.setLastName(sro.getLastName());
        entity.setMiddleName(sro.getMiddleName());
        return entity;

    }

    @Override
    public MobileSubscriberDetailSRO getMobileSubscriberDetailSROFromEntity(MobileSubscriberDetail entity) {
        if (entity == null)
            return null;

        MobileSubscriberDetailSRO sro = new MobileSubscriberDetailSRO();
        sro.setId(entity.getId());
        sro.setMobile(entity.getMobile());
        sro.setVerified(entity.isVerified());
        sro.setUid(entity.getUid());
        sro.setUpdated(entity.getUpdated());
        sro.setCreated(entity.getCreated());

        return sro;
    }

    @Override
    public AffiliateSubscriptionOfferSRO getAffiliateSubscriptionOfferSROFromEntity(AffiliateSubscriptionOffer offer) {
        if (offer == null)
            return null;

        AffiliateSubscriptionOfferSRO sro = new AffiliateSubscriptionOfferSRO();
        sro.setId(offer.getId());
        sro.setOfferName(offer.getOfferName());
        sro.setImage(offer.getImage());
        sro.setPromoCode(offer.getPromoCode());
        sro.setEnabled(offer.isEnabled());
        sro.setCreated(offer.getCreated());
        sro.setUpdated(offer.getUpdated());
        sro.setMobileEnabled(offer.isMobileEnabled());
        sro.setMobileImage(offer.getMobileImage());
        sro.setOfferText(offer.getOfferText());
        return sro;
    }

    @Override
    public CustomerFilterSRO getCustomerFilterSROfromEntity(CustomerFilter customerFilter) {
        if (customerFilter == null)
            return null;

        CustomerFilterSRO sro = new CustomerFilterSRO();
        sro.setId(customerFilter.getId());
        sro.setIntervalOffset(customerFilter.getIntervalOffset());
        sro.setName(customerFilter.getName());
        sro.setDisplayValue(customerFilter.getDisplayValue());
        sro.setEnabled(customerFilter.isEnabled());
        sro.setSelected(customerFilter.isSelected());
        sro.setFilterDomain(CustomerFilterSRO.FilterDomain.valueOf(customerFilter.getFilterDomain().toString()));
        return sro;
    }

    @Override
    public MobileSubscriberSRO getMobileSubscriberSROFromEntity(MobileSubscriber entity) {
        if (entity == null)
            return null;

        MobileSubscriberSRO sro = new MobileSubscriberSRO();
        sro.setId(entity.getId());
        sro.setMobile(entity.getMobile());
        sro.setZoneId(entity.getZoneId());
        sro.setSubscribed(entity.getSubscribed());
        sro.setCreated(entity.getCreated());
        sro.setSubscriptionPage(entity.getSubscriptionPage());
        sro.setPin(entity.getPin());
        sro.setReasonUnsubscription(entity.getReasonUnsubscription());
        sro.setSuggestionUnsubscription(entity.getSuggestionUnsubscription());
        sro.setChannelCode(entity.getChannelCode());
        sro.setAffiliateTrackingCode(entity.getAffiliateTrackingCode());
        sro.setDnd(entity.isDnd());
        sro.setInvalid(entity.isInvalid());
        sro.setCustomer(entity.isCustomer());
        sro.setUpdated(entity.getUpdated());
        return sro;
    }

    @Override
    public List<UserAddressSRO> getUserAddressSROsFromEntities(List<UserAddress> userAddresses){
        if(userAddresses == null )
            return null;
            
        List<UserAddressSRO> userAddressSROs = new ArrayList<UserAddressSRO>();
        for(UserAddress userAddress: userAddresses){
            userAddressSROs.add(getUserAddressSROFromEntity(userAddress));
        }
        return userAddressSROs;
    }
    
    @Override
    public UserAddressSRO getUserAddressSROFromEntity(UserAddress userAddress) {

        if (userAddress == null)
            return null;

        UserAddressSRO userAddressSRO = new UserAddressSRO();
        userAddressSRO.setId(userAddress.getId());
        userAddressSRO.setUserId(userAddress.getUserId());
        userAddressSRO.setName(userAddress.getName());
        userAddressSRO.setAddress1(userAddress.getAddress1());
        userAddressSRO.setAddress2(userAddress.getAddress2());
        userAddressSRO.setCity(userAddress.getCity());
        userAddressSRO.setState(userAddress.getState());
        userAddressSRO.setCountry(userAddress.getCountry());
        userAddressSRO.setPostalCode(userAddress.getPostalCode());
        userAddressSRO.setMobile(userAddress.getMobile());
        userAddressSRO.setLandline(userAddress.getLandline());
        userAddressSRO.setCreated(userAddress.getCreated());
        userAddressSRO.setUpdated(userAddress.getUpdated());
        userAddressSRO.setAddressTag(userAddress.getAddressTag());
        userAddressSRO.setIsDefault(userAddress.isDefault());
        
        //new fields
        userAddressSRO.setIsActive(userAddress.isActive());
        userAddressSRO.setStatus(userAddress.getStatus());

        return userAddressSRO;
    }
    
    @Override
    public UserAddressSRO getUserAddressSROFromAddressDetailSRO(AddressDetailSRO addressDetailSRO,User user) {

        if (addressDetailSRO == null)
            return null;

        UserAddressSRO userAddressSRO = new UserAddressSRO();
        
        userAddressSRO.setUserId(user.getId());
        userAddressSRO.setName(addressDetailSRO.getName());
        userAddressSRO.setAddress1(addressDetailSRO.getAddressLine1());
        userAddressSRO.setAddress2(addressDetailSRO.getAddressLine2());
        userAddressSRO.setCity(addressDetailSRO.getCity());
        userAddressSRO.setState(addressDetailSRO.getState());
        userAddressSRO.setPostalCode(addressDetailSRO.getPincode());
        userAddressSRO.setMobile(addressDetailSRO.getMobile());
        userAddressSRO.setLandline(addressDetailSRO.getLandline());

        return userAddressSRO;
    }

    @Override
    public List<UserAddress> getUserEntitiesFromSROs(List<UserAddressSRO> userAddressSROs){
        if(userAddressSROs == null)
            return null;
        
        List<UserAddress> userAddresses = new ArrayList<UserAddress>();
        for(UserAddressSRO userAddressSRO: userAddressSROs){
            userAddresses.add(getUserAddressEntityFromSRO(userAddressSRO));
        }
        
        return userAddresses;
    }
    
    @Override
    public UserAddress getUserAddressEntityFromSRO(UserAddressSRO userAddressSRO) {

        if (userAddressSRO == null)
            return null;

        UserAddress userAddress = new UserAddress();
        userAddress.setId(userAddressSRO.getId());

        //User user = usersDao.getUserById(userAddressSRO.getUserId());
        //userAddress.setUser(user);
        
        userAddress.setUserId(userAddressSRO.getUserId());
        userAddress.setName(UMSStringUtils.trim(userAddressSRO.getName()));
        userAddress.setAddress1(UMSStringUtils.trim(userAddressSRO.getAddress1()));
        userAddress.setAddress2(UMSStringUtils.trim(userAddressSRO.getAddress2()));
        userAddress.setCity(UMSStringUtils.trim(userAddressSRO.getCity()));
        userAddress.setState(UMSStringUtils.trim(userAddressSRO.getState()));
        userAddress.setCountry(UMSStringUtils.trim(userAddressSRO.getCountry()));
        userAddress.setPostalCode(UMSStringUtils.trim(userAddressSRO.getPostalCode()));
        userAddress.setMobile(UMSStringUtils.trim(userAddressSRO.getMobile()));
        userAddress.setLandline(UMSStringUtils.trim(userAddressSRO.getLandline()));
        userAddress.setCreated(userAddressSRO.getCreated());
        userAddress.setUpdated(userAddressSRO.getUpdated());
        userAddress.setAddressTag(userAddressSRO.getAddressTag());
        userAddress.setDefault(userAddressSRO.isIsDefault());
        //new fields
        if(userAddressSRO.getIsActive()!=null){
        	userAddress.setActive(userAddressSRO.getIsActive());
        }
        if(userAddressSRO.getStatus()!=null){
        	userAddress.setStatus(userAddressSRO.getStatus());
        }
        
        
        return userAddress;
    }
    
    public ServerBehaviourContextSRO getServerBehaviourContextSROFromEntity(ServerBehaviourContext serverBehaviourContext)
    {
    	if(serverBehaviourContext==null)
    		return null;
   
        ServerBehaviourContextSRO serverBehaviourContextSRO= new ServerBehaviourContextSRO();
        serverBehaviourContextSRO.setId(serverBehaviourContext.getId());
        serverBehaviourContextSRO.setName(serverBehaviourContext.getName());
        if (serverBehaviourContext.getDisbaledURLs() != null) {
            Set<DisabledURLSRO> disabledURLSRO = new HashSet<DisabledURLSRO>();
            for (DisabledURL url : serverBehaviourContext.getDisbaledURLs())
                disabledURLSRO.add(getDisabledURLSROfromEntity(url));
            serverBehaviourContextSRO.setDisbaledURLs(disabledURLSRO);
       
        }
		return serverBehaviourContextSRO;
    			
    }
    
    public ServerBehaviourContext getServerBehaviourContextEntityfromSRO(ServerBehaviourContextSRO serverBehaviourContextSRO){
    	
    	if(serverBehaviourContextSRO==null)
    		
		return null;
    	
    	ServerBehaviourContext serverBehaviourContext=new ServerBehaviourContext();
    	serverBehaviourContext.setId(serverBehaviourContextSRO.getId());
        serverBehaviourContext.setName(serverBehaviourContextSRO.getName());
        if (serverBehaviourContextSRO.getDisbaledURLs() != null) {
            Set<DisabledURL> disabledURL = new HashSet<DisabledURL>();
            for (DisabledURLSRO url : serverBehaviourContextSRO.getDisbaledURLs())
                disabledURL.add(getDisabledURLEntityfromSRO(url));
            serverBehaviourContext.setDisbaledURLs(disabledURL);
       
        }
    	
		return serverBehaviourContext;
    	
    	
    }
    
   
    public DisabledURLSRO getDisabledURLSROfromEntity(DisabledURL url) {
        if (url == null)
            return null;
        DisabledURLSRO sro=new DisabledURLSRO();
     
        sro.setId(url.getId());
        sro.setUrl(url.getUrl());
		return sro;
       
}
    
    public DisabledURL getDisabledURLEntityfromSRO(DisabledURLSRO url){
    	if(url==null)
    		return null;
    	DisabledURL sro=new DisabledURL();
        
        sro.setId(url.getId());
        sro.setUrl(url.getUrl());
		return sro;
    	
    }

	@Override
	public UserSRO getUserSROFromIMSUser(UserDetailsDTO userDetails) {
		
		if (userDetails == null){
			return null;
		}
		
		UserSRO sro = new UserSRO();
		sro.setId(userDetails.getSdUserId());
		sro.setEmail(userDetails.getEmailId());
		sro.setMobile(userDetails.getMobileNumber());
		sro.setFirstName(userDetails.getFirstName());
		sro.setMiddleName(userDetails.getMiddleName());
		sro.setLastName(userDetails.getLastName());
		sro.setDisplayName(userDetails.getDisplayName());
		
		if(userDetails.getGender()!=null){
			switch (userDetails.getGender()) {
			case MALE:
				sro.setGender("m");
				break;
			case FEMALE:
				sro.setGender("f");
				break;
			case OTHERS:
				break;
			default:
				break;
			}
		}
		
		if(userDetails.getDob()!=null){
			DateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
			try {
				sro.setBirthday(sdf.parse(userDetails.getDob()));
			} catch (ParseException e) {
				LOG.error("Parse error on birthday",e);
			}
		}
		
		sro.setEmailVerified(userDetails.isEmailVerified());
		sro.setMobileVerified(userDetails.isMobileVerified());

		return sro;

	}
}
