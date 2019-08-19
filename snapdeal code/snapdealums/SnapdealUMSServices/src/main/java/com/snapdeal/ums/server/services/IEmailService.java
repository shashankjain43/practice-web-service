package com.snapdeal.ums.server.services;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.email.ext.email.*;

public interface IEmailService {

    public SendResponse send(SendRequest request);

    public SendOrderSummaryEmailResponse sendOrderSummaryEmail(SendOrderSummaryEmailRequest request);

    public SendOrderRetryEmailResponse sendOrderRetryEmail(SendOrderRetryEmailRequest request);

    public SendSampleVoucherEmailResponse sendSampleVoucherEmail(SendSampleVoucherEmailRequest request);

    public SendConfirmationEmailResponse sendConfirmationEmail(SendConfirmationEmailRequest request);

    public SendNewConfirmationEmailResponse sendNewConfirmationEmail(SendNewConfirmationEmailRequest request);

    public SendAffiliateConfirmationEmailResponse sendAffiliateConfirmationEmail(SendAffiliateConfirmationEmailRequest request);

    public Send3rdPartyConfirmationEmailResponse send3rdPartyConfirmationEmail(Send3rdPartyConfirmationEmailRequest request);

    public SendForgotPasswordEmailResponse sendForgotPasswordEmail(SendForgotPasswordEmailRequest request);

    // public SendMovieVoucherEmailResponse sendMovieVoucherEmail(SendMovieVoucherEmailRequest request);

    public SendPromoCodeEmailResponse sendPromoCodeEmail(SendPromoCodeEmailRequest request);

    //    public SendAffiliatePromoCodeEmailResponse sendAffiliatePromoCodeEmail(SendAffiliatePromoCodeEmailRequest request);

    public SendSurveyPromoCodeEmailResponse sendSurveyPromoCodeEmail(SendSurveyPromoCodeEmailRequest request);

    public SendCorporateEmailResponse sendCorporateEmail(SendCorporateEmailRequest request);

    public SendCorporatebuyEmailResponse sendCorporatebuyEmail(SendCorporatebuyEmailRequest request);

    public SendFeaturedEmailResponse sendFeaturedEmail(SendFeaturedEmailRequest request);

    public SendCustomerQueryEmailResponse sendCustomerQueryEmail(SendCustomerQueryEmailRequest request);

    public SendCustomerFeedbackEmailResponse sendCustomerFeedbackEmail(SendCustomerFeedbackEmailRequest request);

    public SendInviteEmailResponse sendInviteEmail(SendInviteEmailRequest request);

    public SendGroupBuyEmailResponse sendGroupBuyEmail(SendGroupBuyEmailRequest request);

    public SendDailyMerchantEmailResponse sendDailyMerchantEmail(SendDailyMerchantEmailRequest request);

    public SendCodOrderSubmissionEmailResponse sendCodOrderSubmissionEmail(SendCodOrderSubmissionEmailRequest request);

    public SendCodOrderDispatchEmailResponse sendCodOrderDispatchEmail(SendCodOrderDispatchEmailRequest request);

    public SendBdayCashBackEmailResponse sendBdayCashBackEmail(SendBdayCashBackEmailRequest request);

    public SendCashBackOfferEmailResponse sendCashBackOfferEmail(SendCashBackOfferEmailRequest request);

    public SendSdCashBackRewardOnPurchaseEmailResponse sendSdCashBackRewardOnPurchaseEmail(SendSdCashBackRewardOnPurchaseEmailRequest request);

    public SendOrderSubmissionEmailResponse sendOrderSubmissionEmail(SendOrderSubmissionEmailRequest request);

    public SendReferralBenefitEmailResponse sendReferralBenefitEmail(SendReferralBenefitEmailRequest request);

    public SendNewSubscriberReferralOneTimeEmailResponse sendNewSubscriberReferralOneTimeEmail(SendNewSubscriberReferralOneTimeEmailRequest request);

    public SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(SendMobileOrderSubmissionEmailRequest request) throws TransportException;

    public SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(SendMobileCustomerCareEmailRequest request) throws TransportException;

    public SendUSConfirmationEmailResponse sendUSConfirmationEmail(SendUSConfirmationEmailRequest request);

    public SendGeneralSDCashBackEmailResponse sendGeneralSDCashBackEmail(SendGeneralSDCashBackEmailRequest request);

    public SendOrderRefundEmailResponse sendOrderRefundEmail(SendOrderRefundEmailRequest request);

    public SendRefundAndSDCashBackEmailResponse sendRefundAndSDCashBackEmail(SendRefundAndSDCashBackEmailRequest request);

    public SendVendorUserCreationEmailResponse sendVendorUserCreationEmail(SendVendorUserCreationEmailRequest request);

    public SendCodOrderSubmissionEmailProductResponse sendCodOrderSubmissionEmailProduct(SendCodOrderSubmissionEmailProductRequest request);

    public SendAuditMailResponse sendAuditMail(SendAuditMailRequest request);

    public SendFirstSubscriptionEmailResponse sendFirstSubscriptionEmail(SendFirstSubscriptionEmailRequest request);

    public NotifySubscribedUserResponse notifySubscribedUser(NotifySubscribedUserRequest request) throws TransportException;

    public SendFeedbackMailResponse sendFeedbackMail(SendFeedbackMailRequest request) throws TransportException;

    public SendAutoCaptureStatusEmailResponse sendAutoCaptureStatusEmail(SendAutoCaptureStatusEmailRequest request);

    public SendFeaturedResponseEmailResponse sendFeaturedResponseEmail(SendFeaturedResponseEmailRequest request);

    public SendVoucherEmailResponse sendVoucherEmail(SendVoucherEmailRequest request);

    public SendEmployeeSdCashRewardEmailResponse sendEmployeeSdCashRewardEmail(SendEmployeeSdCashRewardEmailRequest request);

    public SendWayBillNumberExhaustionEmailResponse sendWayBillNumberExhaustionEmail(SendWayBillNumberExhaustionEmailRequest request);

    public SendPendingResponseEmailResponse sendPendingResponseEmail(SendPendingResponseEmailRequest request);

    //    public SendProductWorkflowRejectionEmailResponse sendProductWorkflowRejectionEmail(SendProductWorkflowRejectionEmailRequest request);

    public SendDealShareEmailFromDealPageResponse sendDealShareEmailFromDealPage(SendDealShareEmailFromDealPageRequest request);

    public SendDealShareEmailFromPostBuyResponse sendDealShareEmailFromPostBuy(SendDealShareEmailFromPostBuyRequest request);

    public SendAppreciationAckSDCashEmailResponse sendAppreciationAckSDCashEmail(SendAppreciationAckSDCashEmailRequest request);

    public SendSDCashEmailResponse sendSDCashEmail(SendSDCashEmailRequest request);

    public SendValentineEmailResponse sendValentineEmail(SendValentineEmailRequest request);

    public SendBulkUploadResultEmailResponse sendBulkUploadResultEmail(SendBulkUploadResultEmailRequest request);

    public SendUserSDCashHistoryResponse sendUserSDCashHistory(SendUserSDCashHistoryRequest request);

    public SendPromoCodeOnPurchaseEmailResponse sendPromoCodeOnPurchaseEmail(SendPromoCodeOnPurchaseEmailRequest request);

    public SendAutoAccountConfirmationEmailResponse sendAutoAccountConfirmationEmail(SendAutoAccountConfirmationEmailRequest request);

    public SendOrderRefundEmailNewResponse sendOrderRefundEmailNew(SendOrderRefundEmailNewRequest request);

    public SendOrderReplacedSummaryEmailResponse sendOrderReplacedSummaryEmail(SendOrderReplacedSummaryEmailRequest request);

    public SendFinanceVendorPaymentDetailsGenerationEmailResponse sendFinanceVendorPaymentDetailsGenerationEmail(SendFinanceVendorPaymentDetailsGenerationEmailRequest request);

    public SendCODOrderEmailResponse sendCODOrderEmail(SendCODOrderEmailRequest request);

    public CartDropoutNotificationResponse cartDropoutNotificationAfter15days(CartDropoutNotificationRequest request);

    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrTo15days(CartDropoutNotificationRequest request);

    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrs(CartDropoutNotificationRequest request);

    public SendZendeskUploadedFileEmailResponse sendZendeskUploadedFileEmail(SendZendeskUploadedFileEmailRequest request);

    public SendHelpDeskEmailResponse sendHelpDeskEmail(SendHelpDeskEmailRequest request);

    public SendCancelledOrderFeedbackRequestEmailResponse sendCancelledOrderFeedbackRequestEmail(SendCancelledOrderFeedbackRequestEmailRequest request);

    public SendMultiVendorMappingResultsResponse sendMultiVendorMappingResults(SendMultiVendorMappingResultsRequest request);

    public SendAlternateCollectMoneyEmailResponse sendAlternateCollectMoneyEmail(SendAlternateCollectMoneyEmailRequest request);

    public SendReviewRequestMailResponse sendReviewRequestMail(SendReviewRequestMailRequest request);

    public SendAffiliateSubscriptionEmailResponse sendAffiliateSubscriptionEmail(SendAffiliateSubscriptionEmailRequest request);

    public SendPrebookRetryEmailResponse sendPrebookRetryEmail(SendPrebookRetryEmailRequest request);

    public SendAlternateRetryEmailResponse sendAlternateRetryEmail(SendAlternateRetryEmailRequest request);

    public SendAlternatePaymentConfirmationEmailResponse sendAlternatePaymentConfirmationEmail(SendAlternatePaymentConfirmationEmailRequest request);

    public SendAlternateRefundEmailResponse sendAlternateRefundEmail(SendAlternateRefundEmailRequest request);

    public SendAlternateAbsorbEmailResponse sendAlternateAbsorbEmail(SendAlternateAbsorbEmailRequest request);

    public SendReleaseDateShiftMailResponse sendReleaseDateShiftMail(SendReleaseDateShiftMailRequest request);

    public SendSecondPaymentMailResponse sendSecondPaymentMail(SendSecondPaymentMailRequest request);

    public SendPrebookPaymentConfirmationEmailResponse sendPrebookPaymentConfirmationEmail(SendPrebookPaymentConfirmationEmailRequest request);

    public com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse sendCustomerCareEmailv1(com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest request)
            throws TransportException;

    public SendCustomerCareEmailResponse sendCustomerCareEmail(SendCustomerCareEmailRequest request) throws TransportException;

    public SendShareDealEmailResponse sendShareDealEmail(SendShareDealEmailRequest request) throws TransportException;
    
    public com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse sendShareDealEmail(com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest request) throws TransportException;

    public com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest request) throws TransportException;

    public com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest request) throws TransportException;

    public com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse sendFeedbackMail(com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request) throws TransportException;

    public CartDropoutNotificationResponse cartDropOutNotificationMail(CartDropoutNotificationRequest request);
}
