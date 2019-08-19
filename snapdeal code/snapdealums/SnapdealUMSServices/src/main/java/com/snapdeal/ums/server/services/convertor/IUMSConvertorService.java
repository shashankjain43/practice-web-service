/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services.convertor;

import java.util.List;

import com.snapdeal.base.cache.EmailVerificationCode;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.catalog.base.sro.CitySRO;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.core.DisabledURLSRO;
import com.snapdeal.core.ServerBehaviourContextSRO;
import com.snapdeal.core.dto.BulkUploadResultDTO;
import com.snapdeal.core.dto.CommunicationAdminFilterDTO;
import com.snapdeal.core.dto.ProductMultiVendorMappingResultDTO;
import com.snapdeal.core.dto.UserRoleDTO;
import com.snapdeal.core.dto.feedback.CancelledOrderFeedbackDO;
import com.snapdeal.core.entity.Activity;
import com.snapdeal.core.entity.AffiliateDealPrice;
import com.snapdeal.core.entity.Audit;
import com.snapdeal.core.entity.Corporate;
import com.snapdeal.oms.base.sro.order.AddressDetailSRO;
import com.snapdeal.ums.core.entity.CsZentrix;
import com.snapdeal.ums.core.entity.CustomerFilter;
import com.snapdeal.core.entity.CustomerQuery;
import com.snapdeal.ums.core.entity.ESPFilterCityMapping;
import com.snapdeal.ums.core.entity.EmailMobileAssociation;
import com.snapdeal.ums.core.entity.EmailSubscriber;
import com.snapdeal.ums.core.entity.EmailSubscriberDetail;
import com.snapdeal.core.entity.GetFeatured;
import com.snapdeal.ums.core.entity.DisabledURL;
import com.snapdeal.ums.core.entity.Locality;
import com.snapdeal.ums.core.entity.AffiliateSubscriptionOffer;
import com.snapdeal.ums.core.entity.ESPProfileField;
import com.snapdeal.ums.core.entity.EmailBulkEspCityMapping;
import com.snapdeal.ums.core.entity.EmailServiceProvider;
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
import com.snapdeal.core.entity.UserSDCashHistory;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ums.core.entity.ZendeskUser;
import com.snapdeal.ums.core.entity.CustomerEmailScore;
import com.snapdeal.ums.core.entity.CustomerMobileScore;
import com.snapdeal.ums.core.entity.facebook.FacebookLike;
import com.snapdeal.ums.core.entity.facebook.FacebookProfile;
import com.snapdeal.ums.core.entity.facebook.FacebookUser;
import com.snapdeal.ums.core.sro.activity.ActivitySRO;
import com.snapdeal.ums.core.sro.affiliate.AffiliateDealPriceSRO;
import com.snapdeal.ums.core.sro.bulkemail.ESPFilterCityMappingSRO;
import com.snapdeal.ums.core.sro.bulkemail.ESPProfileFieldSRO;
import com.snapdeal.ums.core.sro.bulkemail.EmailBulkEspCityMappingSRO;
import com.snapdeal.ums.core.sro.bulkemail.EmailServiceProviderSRO;
import com.snapdeal.ums.core.sro.customerfilter.CommunicationAdminFilterSRO;
import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;
import com.snapdeal.ums.core.sro.email.AuditSRO;
import com.snapdeal.ums.core.sro.email.BulkUploadResultSRO;
import com.snapdeal.ums.core.sro.email.CancelledOrderFeedbackDOSRO;
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
import com.snapdeal.ums.services.facebook.sro.FacebookLikeSRO;
import com.snapdeal.ums.services.facebook.sro.FacebookProfileSRO;
import com.snapdeal.ums.services.facebook.sro.FacebookUserSRO;

public interface IUMSConvertorService{

    /*
     * User
     */
    public UserSRO getUserSROWithoutRolesfromEntity(User addedUser);

    public UserRoleSRO getUserRoleSROfromEntity(UserRole role);

    // public RoleZoneMappingSRO getRoleZoneMappingSROfromEntity(RoleZoneMapping rzm);

//    public ZoneSRO getZoneSROfromEntity(Zone zone);

//    public Zone getZoneEntityFromSRO(ZoneSRO zoneSro);

    public UserReferralSRO getUserReferralSROfromEntity(UserReferral referral);

    public UserReferral getUserReferralEntityfromSRO(UserReferralSRO sro);

    public UserInformationSRO getUserInformationSROfromEntity(UserInformation information);

    public ZendeskUserSRO getZendeskUserSROfromEntity(ZendeskUser zendeskUser);

    public ZendeskUser getZendeskUserEntityfromSRO(ZendeskUserSRO getZendeskUser);
    
    public CsZentrixSRO getCsZentrixSROfromEntity(CsZentrix csZentrix);
    
    public CsZentrix getCsZentrixEntityfromSRO(CsZentrixSRO csZentrixSRO);

    public UserSDCashHistorySRO getUserSDCashHistorySROfromEntity(UserSDCashHistory history);

    public UserPreferenceSRO getUserPreferenceSROFromEntity(UserPreference pref);

    public EmailVerificationCodeSRO getEmailVerificationCodeSROfromEntity(EmailVerificationCode emailVerificationCode);

    public EmailVerificationCode getEmailVerificationCodeEntityfromSRO(EmailVerificationCodeSRO createEmailVerificationCode);

    public UserRoleSRO getUserRoleSROfromDTO(UserRoleDTO dto);

    public UserRoleDTO getUserRoleDTOfromSRO(UserRoleSRO sro);

    public User getUserEntityFromSRO(UserSRO userSRO);

    public UserRole getUserRoleEntityfromSRO(UserRoleSRO userRoleSRO);

    //public RoleZoneMapping getRoleZoneMappingEntityFromSRO(RoleZoneMappingSRO rzmSRO);

    public UserInformation getUserInfomationEntityFromSRO(UserInformationSRO infotoAdd);

    public UserSDCashHistory getUserSDCashHistoryEntityfromSRO(UserSDCashHistorySRO sro);

    public UserPreference getUserPreferenceEntityFromSRO(UserPreferenceSRO sro);

    /*
     * CustomerFilter 
     */

    public CommunicationAdminFilterSRO getCommunicationAdminFilterSROfromDTO(CommunicationAdminFilterDTO commAdmFilter);

    public CustomerFilterSRO getCustomerFilterSROfromEntity(CustomerFilter customerFilter);

    public CustomerFilter getCustomerFilterEntityFromSRO(CustomerFilterSRO sro);

    public CommunicationAdminFilterDTO getCommunicationAdminFilterDTOfromSRO(CommunicationAdminFilterSRO sro);

    /*
     *  Newsletters
     */
    public NewsletterSRO getNewsletterSROfromEntity(Newsletter newsletter);
//
    public NewsletterEspMappingSRO getNewsletterEspMappingSRO(NewsletterEspMapping nem);
//
    public Newsletter getNewsletterEntityFromSRO(NewsletterSRO sro);
//
    public NewsletterEspMapping getNewsletterEspMappingEntityFromSRO(NewsletterEspMappingSRO sro);

    /*
     * SmsScheduler
     */
    public SmsSchedulerSRO getSmsSchedulerSROfromEntity(SmsScheduler smsScheduler);

    public SmsScheduler getSmsSchedulerEntityFromSRO(SmsSchedulerSRO sro);

    /*
     * Activity
     */
    public Activity getActivityfromSRO(ActivitySRO activitySRO);

    public ActivitySRO getActivitySROfromEntity(Activity activity);

    /*
     *  Audit
     */
    public Audit getAuditfromSRO(AuditSRO auditSRO);

    /*
     * Bulk email
     */

    public EmailBulkEspCityMapping getEmailBulkEspCityMappingEntityFromSRO(EmailBulkEspCityMappingSRO sro);

    public EmailBulkEspCityMappingSRO getEmailBulkEspCityMappingSROFromEntity(EmailBulkEspCityMapping mapping);

    public ESPFilterCityMapping getESPFilterCityMappingEntityFromSRO(ESPFilterCityMappingSRO sro);
//
    public ESPFilterCityMappingSRO getESPFilterCityMappingSROFromEntity(ESPFilterCityMapping mapping);

    public ESPProfileFieldSRO getESPProfileFieldSROFromEntity(ESPProfileField field);


//    public City getCityEntityFromSRO(CitySRO citySRO);

//    public CitySRO getCitySROFromEntity(City city);

    /*
     * email
     */

    public Corporate getCorporateEmailEntityFromSRO(CorporateSRO sro);

    public GetFeatured getFeaturedEntityFromSRO(FeaturedSRO sro);

    public CustomerQuery getCustomerQueryEntityFromSRO(CustomerQuerySRO customerQuerySRO);

//    public AffiliateDealPrice getAffiliateDealPricesEntityFromSRO(AffiliateDealPriceSRO affiliateDealPrices);

    public CorporateSRO getCorporateSROFromEntity(Corporate corporate);

    public BulkUploadResultDTO getBulkUploadResultsDTOfromSRO(BulkUploadResultSRO sro);

    public ProductMultiVendorMappingResultDTO getProductMultiVendorMappingResultDTOfromSRO(ProductMultiVendorMappingResultSRO sro);

    public CancelledOrderFeedbackDO getcancelledOrderFeedbackDOfromSRO(CancelledOrderFeedbackDOSRO cancelledOrderFeedbackSRO);

    public CancelledOrderFeedbackDOSRO getCancelledOrderFeedbackSROFromDO(CancelledOrderFeedbackDO cancelledOrderFeedbackDTO);

    public FeaturedSRO getFeaturedSROFromEntity(GetFeatured featured);

    public CustomerQuerySRO getCustomerQuerySROFromEntity(CustomerQuery customerQuery);

    public AuditSRO getAuditSROFromEntity(Audit audit);

    public ProductMultiVendorMappingResultSRO getProductMultiVendorMappingResultSROFromDTO(ProductMultiVendorMappingResultDTO dto);

    public BulkUploadResultSRO getBulkUploadResultSROFromDTOs(BulkUploadResultDTO resultDTO);

    public EmailSubscriberDetailSRO getEmailSubscriberDetailSROfromEntity(EmailSubscriberDetail detail);

    public EmailSubscriberDetail getEmailSubscriberDetailEntityFromSRO(EmailSubscriberDetailSRO detailSRO);

    public MobileSubscriberDetailSRO getMobileSubscriberDetailSROFromEntity(MobileSubscriberDetail detail);

    public EmailMobileAssociation getEmailMobileAssociationEntityFromSRO(EmailMobileAssociationSRO sro);

    public EmailSubscriberSRO getEmailSubscriberSROFromEntity(EmailSubscriber sub);

    public MobileSubscriberSRO getMobileSubscriberSROFromEntity(MobileSubscriber sub);

    public EmailMobileAssociationSRO getEmailMobileAssociationSROFromEntity(EmailMobileAssociation entity);

    public SubscriberProfileSRO getSubscriberProfileSROFromEntity(SubscriberProfile profile);

    public SubscriberProfile getSubscriberProfileEntityFromSRO(SubscriberProfileSRO profileSRO);

    public MobileSubscriber getMobileSubscriberEntityFromSRO(MobileSubscriberSRO sro);

    public EmailSubscriber getEmailSubscriberEntityFromSRO(EmailSubscriberSRO sro);

//    public AffiliateSubscriptionOfferSRO getAffiliateSubscriptionOfferSROFromEntity(AffiliateSubscriptionOffer offer);

    public MobileSubscriberDetail getMobileSubscriberDetailEntityFromSRO(MobileSubscriberDetailSRO sro);

//    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferEntityFromSRO(AffiliateSubscriptionOfferSRO responseSRO);

    Locality getLocalityEntityFromSRO(LocalitySRO sro);

    UserSRO getUserSROWithRolesfromEnity(User user);

    public EmailMessage getEmailMessageEntityFromSRO(EmailMessageSRO messageSRO);

    public CustomerEmailScore getCustomerEmailScoreEntityFromSRO(CustomerScoreSRO customerScoreSRO);

    public CustomerScoreSRO getCustomerEmailScoreSROFromEntity(CustomerEmailScore customerEmailScore);

    public CustomerMobileScore getCustomerMobileScoreEntityFromSRO(CustomerScoreSRO customerScoreSRO);

    public CustomerScoreSRO getCustomerMobileScoreSROFromEntity(CustomerMobileScore customerMobileScore);

    public Role getRoleEntityfromSRO(RoleSRO roleSRO);

    public FacebookUser getFacebookUserEntityFromSRO(FacebookUserSRO sro);

    public FacebookProfile getFacebookProfileEntityFromSRO(FacebookProfileSRO sro);

    public FacebookLike getFacebookLikeEntityFromSRO(FacebookLikeSRO sro);

    public FacebookUserSRO getFacebookUserSROFromEntity(FacebookUser entity);

    public FacebookLikeSRO getFacebookLikeSROFromEntity(FacebookLike entity);

    public FacebookProfileSRO getFacebookProfileSROfromEntity(FacebookProfile entity);

    public AffiliateSubscriptionOffer getAffiliateSubscriptionOfferEntityFromSRO(AffiliateSubscriptionOfferSRO sro);

    public AffiliateSubscriptionOfferSRO getAffiliateSubscriptionOfferSROFromEntity(AffiliateSubscriptionOffer offer);

    public EmailServiceProviderSRO getEmailServiceProviderSROFromEntity(EmailServiceProvider entity);

    public EmailServiceProvider getEmailServiceProviderEntityFromSRO(EmailServiceProviderSRO emailServiceProvider);

    public ESPProfileField getESPProfileFieldEntityFromSRO(ESPProfileFieldSRO sro);

    UserAddressSRO getUserAddressSROFromEntity(UserAddress userAddress);

    UserAddress getUserAddressEntityFromSRO(UserAddressSRO userAddressSRO);

    List<UserAddressSRO> getUserAddressSROsFromEntities(List<UserAddress> userAddresses);

    List<UserAddress> getUserEntitiesFromSROs(List<UserAddressSRO> userAddressSROs);

    public void updateUserEntityFromSRO(UserSRO userSRO, User user);

	UserAddressSRO getUserAddressSROFromAddressDetailSRO(
			AddressDetailSRO addressDetailSRO, User user);

   public ServerBehaviourContextSRO getServerBehaviourContextSROFromEntity(ServerBehaviourContext serverBehaviourContext);
   
   public DisabledURLSRO getDisabledURLSROfromEntity(DisabledURL url);
   
   public ServerBehaviourContext getServerBehaviourContextEntityfromSRO(ServerBehaviourContextSRO serverBehaviourContextSRO);
   
   public DisabledURL getDisabledURLEntityfromSRO(DisabledURLSRO url);

   public UserSRO getUserSROFromIMSUser(UserDetailsDTO userDetails);

}
