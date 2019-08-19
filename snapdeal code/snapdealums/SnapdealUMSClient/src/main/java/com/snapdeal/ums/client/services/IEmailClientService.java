package com.snapdeal.ums.client.services;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.email.ext.email.*;

public interface IEmailClientService {

    public SendResponse send(SendRequest request);
    @Deprecated
    public SendOrderSummaryEmailResponse sendOrderSummaryEmail(SendOrderSummaryEmailRequest request);
    @Deprecated
    public SendOrderRetryEmailResponse sendOrderRetryEmail(SendOrderRetryEmailRequest request);

//    public SendSampleVoucherEmailResponse sendSampleVoucherEmail(SendSampleVoucherEmailRequest request);
    @Deprecated
    public SendConfirmationEmailResponse sendConfirmationEmail(SendConfirmationEmailRequest request);

//    public SendNewConfirmationEmailResponse sendNewConfirmationEmail(SendNewConfirmationEmailRequest request);
    @Deprecated
    public SendAffiliateConfirmationEmailResponse sendAffiliateConfirmationEmail(SendAffiliateConfirmationEmailRequest request);
    @Deprecated
    public Send3rdPartyConfirmationEmailResponse send3rdPartyConfirmationEmail(Send3rdPartyConfirmationEmailRequest request);
    @Deprecated
    public SendForgotPasswordEmailResponse sendForgotPasswordEmail(SendForgotPasswordEmailRequest request);

//   @Unused public SendMovieVoucherEmailResponse sendMovieVoucherEmail(SendMovieVoucherEmailRequest request);
    @Deprecated
    public SendPromoCodeEmailResponse sendPromoCodeEmail(SendPromoCodeEmailRequest request);

//    public SendAffiliatePromoCodeEmailResponse sendAffiliatePromoCodeEmail(SendAffiliatePromoCodeEmailRequest request);
    @Deprecated
    public SendSurveyPromoCodeEmailResponse sendSurveyPromoCodeEmail(SendSurveyPromoCodeEmailRequest request);
    @Deprecated
    public SendCorporateEmailResponse sendCorporateEmail(SendCorporateEmailRequest request);
    @Deprecated
    public SendCorporatebuyEmailResponse sendCorporatebuyEmail(SendCorporatebuyEmailRequest request);
    @Deprecated
    public SendFeaturedEmailResponse sendFeaturedEmail(SendFeaturedEmailRequest request);
    @Deprecated
    public SendCustomerQueryEmailResponse sendCustomerQueryEmail(SendCustomerQueryEmailRequest request);
    @Deprecated
    public SendCustomerFeedbackEmailResponse sendCustomerFeedbackEmail(SendCustomerFeedbackEmailRequest request);
    @Deprecated
    public SendInviteEmailResponse sendInviteEmail(SendInviteEmailRequest request);
    @Deprecated
    public SendShareDealEmailResponse sendShareDealEmail(SendShareDealEmailRequest request);
    @Deprecated
    public SendGroupBuyEmailResponse sendGroupBuyEmail(SendGroupBuyEmailRequest request);
    @Deprecated
    public SendDailyMerchantEmailResponse sendDailyMerchantEmail(SendDailyMerchantEmailRequest request);
    @Deprecated
    public SendCodOrderSubmissionEmailResponse sendCodOrderSubmissionEmail(SendCodOrderSubmissionEmailRequest request);
    @Deprecated
    public SendCodOrderDispatchEmailResponse sendCodOrderDispatchEmail(SendCodOrderDispatchEmailRequest request);
    @Deprecated
    public SendBdayCashBackEmailResponse sendBdayCashBackEmail(SendBdayCashBackEmailRequest request);
    @Deprecated
    public SendCashBackOfferEmailResponse sendCashBackOfferEmail(SendCashBackOfferEmailRequest request);
    @Deprecated
    public SendSdCashBackRewardOnPurchaseEmailResponse sendSdCashBackRewardOnPurchaseEmail(SendSdCashBackRewardOnPurchaseEmailRequest request);
    @Deprecated
    public SendCustomerCareEmailResponse sendCustomerCareEmail(SendCustomerCareEmailRequest request);
    @Deprecated
    public SendOrderSubmissionEmailResponse sendOrderSubmissionEmail(SendOrderSubmissionEmailRequest request);
    @Deprecated
    public SendReferralBenefitEmailResponse sendReferralBenefitEmail(SendReferralBenefitEmailRequest request);
    @Deprecated
    public SendNewSubscriberReferralOneTimeEmailResponse sendNewSubscriberReferralOneTimeEmail(SendNewSubscriberReferralOneTimeEmailRequest request);
    @Deprecated
    public SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(SendMobileOrderSubmissionEmailRequest request);
    @Deprecated
    public SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(SendMobileCustomerCareEmailRequest request);
    @Deprecated
    public SendUSConfirmationEmailResponse sendUSConfirmationEmail(SendUSConfirmationEmailRequest request);
    @Deprecated
    public SendGeneralSDCashBackEmailResponse sendGeneralSDCashBackEmail(SendGeneralSDCashBackEmailRequest request);
    @Deprecated
    public SendOrderRefundEmailResponse sendOrderRefundEmail(SendOrderRefundEmailRequest request);
    @Deprecated
    public SendRefundAndSDCashBackEmailResponse sendRefundAndSDCashBackEmail(SendRefundAndSDCashBackEmailRequest request);
    @Deprecated
    public SendVendorUserCreationEmailResponse sendVendorUserCreationEmail(SendVendorUserCreationEmailRequest request);
    @Deprecated
    public SendCodOrderSubmissionEmailProductResponse sendCodOrderSubmissionEmailProduct(SendCodOrderSubmissionEmailProductRequest request);
    @Deprecated
    public SendAuditMailResponse sendAuditMail(SendAuditMailRequest request);
    @Deprecated
    public SendFirstSubscriptionEmailResponse sendFirstSubscriptionEmail(SendFirstSubscriptionEmailRequest request);
    @Deprecated
    public NotifySubscribedUserResponse notifySubscribedUser(NotifySubscribedUserRequest request);
    @Deprecated
    public SendFeedbackMailResponse sendFeedbackMail(SendFeedbackMailRequest request);
    @Deprecated
    public SendAutoCaptureStatusEmailResponse sendAutoCaptureStatusEmail(SendAutoCaptureStatusEmailRequest request);
    @Deprecated
    public SendFeaturedResponseEmailResponse sendFeaturedResponseEmail(SendFeaturedResponseEmailRequest request);
    @Deprecated
    public SendVoucherEmailResponse sendVoucherEmail(SendVoucherEmailRequest request);
    @Deprecated
    public SendEmployeeSdCashRewardEmailResponse sendEmployeeSdCashRewardEmail(SendEmployeeSdCashRewardEmailRequest request);
    @Deprecated
    public SendWayBillNumberExhaustionEmailResponse sendWayBillNumberExhaustionEmail(SendWayBillNumberExhaustionEmailRequest request);
    @Deprecated
    public SendPendingResponseEmailResponse sendPendingResponseEmail(SendPendingResponseEmailRequest request);
//    public SendProductWorkflowRejectionEmailResponse sendProductWorkflowRejectionEmail(SendProductWorkflowRejectionEmailRequest request);
    @Deprecated
    public SendDealShareEmailFromDealPageResponse sendDealShareEmailFromDealPage(SendDealShareEmailFromDealPageRequest request);
    @Deprecated
    public SendDealShareEmailFromPostBuyResponse sendDealShareEmailFromPostBuy(SendDealShareEmailFromPostBuyRequest request);
    @Deprecated
    public SendAppreciationAckSDCashEmailResponse sendAppreciationAckSDCashEmail(SendAppreciationAckSDCashEmailRequest request);
    @Deprecated
    public SendSDCashEmailResponse sendSDCashEmail(SendSDCashEmailRequest request);
    @Deprecated
    public SendValentineEmailResponse sendValentineEmail(SendValentineEmailRequest request);
    @Deprecated
    public SendBulkUploadResultEmailResponse sendBulkUploadResultEmail(SendBulkUploadResultEmailRequest request);
    @Deprecated
    public SendUserSDCashHistoryResponse sendUserSDCashHistory(SendUserSDCashHistoryRequest request);
    @Deprecated
    public SendPromoCodeOnPurchaseEmailResponse sendPromoCodeOnPurchaseEmail(SendPromoCodeOnPurchaseEmailRequest request);
    @Deprecated
    public SendAutoAccountConfirmationEmailResponse sendAutoAccountConfirmationEmail(SendAutoAccountConfirmationEmailRequest request);
    @Deprecated
    public SendOrderRefundEmailNewResponse sendOrderRefundEmailNew(SendOrderRefundEmailNewRequest request);
    @Deprecated
    public SendOrderReplacedSummaryEmailResponse sendOrderReplacedSummaryEmail(SendOrderReplacedSummaryEmailRequest request);
    @Deprecated
    public SendFinanceVendorPaymentDetailsGenerationEmailResponse sendFinanceVendorPaymentDetailsGenerationEmail(SendFinanceVendorPaymentDetailsGenerationEmailRequest request);
    @Deprecated
    public SendCODOrderEmailResponse sendCODOrderEmail(SendCODOrderEmailRequest request);
    @Deprecated
    public CartDropoutNotificationResponse cartDropoutNotificationAfter15days(CartDropoutNotificationRequest request);
    @Deprecated
    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrTo15days(CartDropoutNotificationRequest request);
    @Deprecated
    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrs(CartDropoutNotificationRequest request);
    @Deprecated
    public SendZendeskUploadedFileEmailResponse sendZendeskUploadedFileEmail(SendZendeskUploadedFileEmailRequest request);
    @Deprecated
    public SendHelpDeskEmailResponse sendHelpDeskEmail(SendHelpDeskEmailRequest request);
    @Deprecated
    public SendCancelledOrderFeedbackRequestEmailResponse sendCancelledOrderFeedbackRequestEmail(SendCancelledOrderFeedbackRequestEmailRequest request);
    @Deprecated
    public SendMultiVendorMappingResultsResponse sendMultiVendorMappingResults(SendMultiVendorMappingResultsRequest request);
    @Deprecated
    public SendAlternateCollectMoneyEmailResponse sendAlternateCollectMoneyEmail(SendAlternateCollectMoneyEmailRequest request);
    @Deprecated
    public SendReviewRequestMailResponse sendReviewRequestMail(SendReviewRequestMailRequest request);
    @Deprecated
    public SendAffiliateSubscriptionEmailResponse sendAffiliateSubscriptionEmail(SendAffiliateSubscriptionEmailRequest request);
    @Deprecated
    public SendPrebookRetryEmailResponse sendPrebookRetryEmail(SendPrebookRetryEmailRequest request);
    @Deprecated
    public SendAlternateRetryEmailResponse sendAlternateRetryEmail(SendAlternateRetryEmailRequest request);
    @Deprecated
    public SendAlternatePaymentConfirmationEmailResponse sendAlternatePaymentConfirmationEmail(SendAlternatePaymentConfirmationEmailRequest request);
    @Deprecated
    public SendAlternateRefundEmailResponse sendAlternateRefundEmail(SendAlternateRefundEmailRequest request);
    @Deprecated
    public SendAlternateAbsorbEmailResponse sendAlternateAbsorbEmail(SendAlternateAbsorbEmailRequest request);
    @Deprecated
    public SendReleaseDateShiftMailResponse sendReleaseDateShiftMail(SendReleaseDateShiftMailRequest request);
    @Deprecated
    public SendSecondPaymentMailResponse sendSecondPaymentMail(SendSecondPaymentMailRequest request);
    @Deprecated
    public SendPrebookPaymentConfirmationEmailResponse sendPrebookPaymentConfirmationEmail(SendPrebookPaymentConfirmationEmailRequest request);
    @Deprecated
    public com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse sendCustomerCareEmail(com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest request);
    @Deprecated
    public com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse sendShareDealEmail(com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest request);
    @Deprecated
    public com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest request);
    @Deprecated
    public com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest request);
    @Deprecated
    public com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse sendFeedbackMail(com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request);
    @Deprecated
    public CartDropoutNotificationResponse cartDropOutNotificationMail(CartDropoutNotificationRequest request) throws TransportException;
}
