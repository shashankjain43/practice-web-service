package com.snapdeal.ums.server.services;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.catalog.base.sro.CatalogSRO;
import com.snapdeal.catalog.base.sro.PartnerPromoCodeSRO;
import com.snapdeal.catalog.base.sro.ProductPrebookSRO;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.core.dto.BulkUploadResultDTO;
import com.snapdeal.core.dto.ProductMultiVendorMappingResultDTO;
import com.snapdeal.core.dto.feedback.CancelledOrderFeedbackDO;
import com.snapdeal.core.entity.Audit;
import com.snapdeal.core.entity.Corporate;
import com.snapdeal.core.entity.CustomerQuery;
import com.snapdeal.core.entity.GetFeatured;
import com.snapdeal.core.sro.order.PromoCodeSRO;
import com.snapdeal.core.sro.serviceDeal.ServiceDealSRO;
import com.snapdeal.oms.base.model.FullfillAlternateSuborderRequest;
import com.snapdeal.oms.base.sro.order.OrderSRO;
import com.snapdeal.oms.base.sro.order.SuborderSRO;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.sro.email.CartItemSRO;
import com.snapdeal.ums.core.sro.email.OrderCancellationEmailSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.services.sdCashBulkUpdate.SDCashBulkCreditEmailRequest;
import com.snapdeal.ums.sro.email.vm.UMSPOGSRO;
import com.snapdeal.ums.userNeftDetails.EnhancedUserNEFTDetailsSRO;

public interface IEmailServiceInternal
{
    void send(EmailMessage message);
    
    public void sendAccountCreationEmail(String email, String pwd, String emailTemplate);
    
    void sendOrderSummaryEmail(OrderSRO order);

    void sendOrderRetryEmail(OrderSRO order, String retryUrl);

    void sendSampleVoucherEmail(SuborderSRO suborder, ZoneSRO zone, String email, String contentPath);

    void sendConfirmationEmail(String email, String contextPath, String contentPath, String confirmationLink);

    void sendNewConfirmationEmail(String email, Integer zoneId, String contextPath, String contentPath,
        String confirmationLink);

    public void sendAffiliateConfirmationEmail(String email, String contextPath, String contentPath,
        String confirmationLink);

    void send3rdPartyConfirmationEmail(String email, String contextPath, String contentPath, String confirmationLink,
        String source);

    void sendForgotPasswordEmail(User user, String contextPath, String contentPath, String forgotPasswordLink);

    void sendMovieVoucherEmail(String email, PartnerPromoCodeSRO promoCode, String contextPath, String contentPath);

    void sendPromoCodeEmail(String email, PromoCodeSRO prmCode, String contextPath, String contentPath);

    // void sendAffiliatePromoCodeEmail(String email, PromoCodeSRO prmCode,
    // Affiliate affiliate, String promoCodeTypeName, String contextPath, String
    // contentPath);

    void sendSurveyPromoCodeEmail(String email, PromoCodeSRO prmCode, String contextPath, String contentPath);

    void sendCorporateEmail(Corporate corporate);

    void sendCorporatebuyEmail(Object corporate);

    void sendFeaturedEmail(GetFeatured featured);

    void sendCustomerQueryEmail(CustomerQuery customerQuery);

    void sendCustomerFeedbackEmail(String name, String email, String city, String category, String message);

    void sendInviteEmail(String refererEmail, String contextPath, String contentPath, String userName, String to,
        String from, String trackingUID);

    void sendShareDealEmail(String to, String from, String recipientEmail, ServiceDealSRO deal);

    void sendGroupBuyEmail(Object groupDeal, String emailTemplate, String to);

    void sendDailyMerchantEmail(String emailIds, List<SuborderSRO> suborders);

    void sendCodOrderSubmissionEmail(SuborderSRO suborder, String http, String resources);

    void sendCodOrderDispatchEmail(SuborderSRO suborder, String http, String resources);

    void sendBdayCashBackEmail(User user, int cashBackAmount, boolean newUser);

    void sendCashBackOfferEmail(User user, int cashBackAmount, boolean newUser, String confirmationLink,
        String contextPath, String contentPath);

    public void sendSdCashBackRewardOnPurchaseEmail(OrderSRO order, UserSRO user, int sdCash, Long purchase,
        boolean newUser, String confirmationLink, String contextPath,
        String contentPath);

    void sendCustomerCareEmail(User user, ServiceDealSRO deal, String contextPath, String contentPath);

    void sendOrderSubmissionEmail(SuborderSRO suborder, String contextPath, String contentPath);

    void sendReferralBenefitEmail(User user, int sdCashValue, int friendsReferred, int noOfConversions,
        boolean newUser, String confirmationPath, List<String> referredUserEmails,
        String contextPath, String contentPath);

    void sendNewSubscriberReferralOneTimeEmail(User user, String confirmationLink, List<String> referredUserEmails,
        String contextPath, String contentPath);

    void sendMobileOrderSubmissionEmail(OrderSRO order, ServiceDealSRO deal, String contextPath, String contentPath);

    void sendMobileCustomerCareEmail(OrderSRO order, ServiceDealSRO deal, String contextPath, String contentPath);

    void sendUSConfirmationEmail(String email, String contextPath, String contentPath);

    void sendGeneralSDCashBackEmail(User user, Integer sdCashValue, boolean newUser, String conirmationLink,
        String contextPath, String contentPath);

    void sendOrderRefundEmail(OrderSRO order, String shippingMethodCode, List<String> cancelledProducts,
        String contextPath, String contentPath);

    void sendRefundAndSDCashBackEmail(User user, OrderSRO order, String shippingMethodCode,
        List<String> cancelledProducts, Integer sdCashRefund, boolean newUser,
        String confirmationLink, String contextPath, String contentPath);

    void sendVendorUserCreationEmail(User user, String contextPath, String contentPath);

    void sendCodOrderSubmissionEmailProduct(SuborderSRO suborder, String http, String resources);

    void sendAuditMail(Audit audit, String useremailId);

    public void sendFirstSubscriptionEmail(String email, PromoCodeSRO prmCode, String confirmationLink,
        String contextPath, String contentPath);

    public void notifySubscribedUser(String email, ZoneSRO zoneSro, String contextPath, String contentPath,
        String confirmationLink);

    public void sendFeedbackMail(SuborderSRO suborder, CatalogSRO catalog, String contentPath, String contextPath,
        boolean redeemed) throws TransportException;

    public void sendAutoCaptureStatusEmail(String pg, String date, Map<String, String> failedOrders);

    public void sendFeaturedResponseEmail(GetFeatured featured, String email, String contentPath);

    void sendVoucherEmail(SuborderSRO suborder);

    void sendEmployeeSdCashRewardEmail(User user, int cashBackAmount, boolean newUser, String confirmationLink,
        String contextPath, String contentPath);

    void sendWayBillNumberExhaustionEmail(String shippingProviderName, String shippingMethodName, long l);

    void sendPendingResponseEmail(OrderSRO order);

    // void sendProductWorkflowRejectionEmail(List<ProductWorkflowDTO>
    // workflowDTOs, String role);

    void sendDealShareEmailFromDealPage(String refererEmail, String name, String from, String recipientName,
        String url, String dealDetail);

    void sendDealShareEmailFromPostBuy(String refererEmail, String name, String from, String recipientName, String url,
        String dealDetail);

    public void sendAppreciationAckSDCashEmail(User user, Integer sdCashValue, boolean newUser, String conirmationLink,
        String contextPath, String contentPath);

    void sendSDCashEmail(User user, String emailTemplateName, int cashBackAmount, String confirmationLink,
        boolean newUser);

    void sendValentineEmail(String name, String recipient, String url);

    public void sendBulkUploadResultEmail(List<BulkUploadResultDTO> resultDTOs, String fileName, String email);

    void sendUserSDCashHistory(String userEmail, String userName, int sdcashAtBegOfMonth, int sdcashEarningOfMonth,
        int sdcashUsedThisMonth, int sdcashAvailable,
        int sdCashExpired, DateRange range, int currSDCash, String linkToBeSent, String contextPath, String contentPath);

    public void sendPromoCodeOnPurchaseEmail(OrderSRO order, PromoCodeSRO promoCode, String contextPath,
        String contentPath);

    public void sendAutoAccountConfirmationEmail(String email, String password, String name, String contextPath,
        String contentPath, String confirmationLink);

    void sendOrderReplacedSummaryEmail(OrderSRO order, Set<SuborderSRO> suborders);

    void sendFinanceVendorPaymentDetailsGenerationEmail(String email, String path);

    void sendCODOrderEmail(String orderCode);

    void cartDropoutNotificationAfter15days(String email, String name, String cartId, List<CartItemSRO> cartItemSROs);

    void cartDropoutNotificationWithin24hrTo15days(String email, String name, String cartId,
        List<CartItemSRO> cartItemSROs, String catalogText);

    void cartDropoutNotificationWithin24hrs(String email, String name, String cartId, List<CartItemSRO> cartItemSROs);

    void sendZendeskUploadedFileEmail(String email, String path);

    void sendHelpDeskEmail(String subject, String name, String email, String mobile, String orderId, String itemName,
        String comments, String ticketId);

    public void sendCancelledOrderFeedbackRequestEmail(CancelledOrderFeedbackDO cancelledOrderFeedbackDTO,
        String contentPath, String contextPath);

    public void sendMultiVendorMappingResults(List<ProductMultiVendorMappingResultDTO> resultDTOs, String fileName);

    void sendAlternateCollectMoneyEmail(double collectableAmount, OrderSRO order, SuborderSRO originalSuborder,
        SuborderSRO alternateSuborder, String buyPageurl);

    void sendAffiliateSubscriptionEmail(String email, List<HashMap<String, String>> listOfMap);

    void sendPrebookRetryEmail(OrderSRO order, SuborderSRO suborder, String buyPageUrl);

    void sendPrebookNotificationEmail(ProductPrebookSRO prebook, boolean isTriggerDateNotification);

    void sendAlternateRetryEmail(OrderSRO order, SuborderSRO alternateSuborder, String buyPageUrl);

    void sendReviewRequestMail(List<SuborderSRO> suborders, String contentPath, String contextPath)
        throws TransportException;

    void sendUploadRefundFinanceEmail(Map<String, String> suborders);

    // void customerNotificationEmail(List<String> emailList, ProductOfferSRO
    // productOfferSRO);

    public void sendAlternatePaymentConfirmationEmail(int collectableAmount, OrderSRO order,
        SuborderSRO originalSuborder, SuborderSRO alternateSuborder);

    public void sendAlternateRefundEmail(OrderSRO order, FullfillAlternateSuborderRequest request);

    public void sendAlternateAbsorbEmail(OrderSRO order, FullfillAlternateSuborderRequest request);

    public void sendReleaseDateShiftMail(OrderSRO order, SuborderSRO suborder, String newReleaseDate,
        String oldReleaseDate);

    public void sendSecondPaymentMail(OrderSRO order, SuborderSRO suborder, String buyPageUrl, String releaseDate);

    public void sendPrebookPaymentConfirmationEmail(OrderSRO order, SuborderSRO suborder, Integer collectedAmount,
        Integer prebookAmount);

    public void sendOrderRefundEmailNew(OrderCancellationEmailSRO orderCancellationEmailSRO);

    public void reSendAutoAccountConfirmationEmail(String email, String name, String contextPath, String contentPath,
        String confirmationLink);

    public void cartDropOutNotificationMail(String email, String name, String cartId, List<CartItemSRO> cartItemSROs,
        Set<UMSPOGSRO> umsPOGSROs);

    public void sendSnapBoxActivationConfirmation(String email, String name, String link);

    public void sendSnapBoxInvitationEmail(String email, String name, String link);

    public void sendUserNEFTUpdatedEmail(String email, EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails);

    public void sendSDCashBulkCreditResponseEmail(String email,Map<ErrorConstants, List<String>> errorCodeEmailIdsMap);
    
    public void sendUserSDCashCreditEmail(SDCashBulkCreditEmailRequest sdCashBulkEmailRequest, String templateName);

	public void sendSDWalletEmail(String email,Integer amount,Integer expiryDays);

	public void sendSDCashBulkDebitResponseEmail(String email,
			Map<ErrorConstants, List<String>> errorCodeEmailIdsMap);

}
