package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.jfree.util.Log;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.email.ext.email.*;
import com.snapdeal.ums.server.services.IEmailService;

@Controller
@RequestMapping("/service/ums/email/")
public class EmailServiceController {

    @Autowired
    private IEmailService emailService;

    @RequestMapping(value = "send", produces = "application/sd-service")
    @ResponseBody
    public SendResponse send(@RequestBody SendRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendResponse response = emailService.send(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderSummaryEmail", produces = "application/sd-service")
    @ResponseBody
    public SendOrderSummaryEmailResponse sendOrderSummaryEmail(@RequestBody SendOrderSummaryEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderSummaryEmailResponse response = emailService.sendOrderSummaryEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderRetryEmail", produces = "application/sd-service")
    @ResponseBody
    public SendOrderRetryEmailResponse sendOrderRetryEmail(@RequestBody SendOrderRetryEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderRetryEmailResponse response = emailService.sendOrderRetryEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

  //  @RequestMapping(value = "sendSampleVoucherEmail", produces = "application/sd-service")
    @ResponseBody
    public SendSampleVoucherEmailResponse sendSampleVoucherEmail(@RequestBody SendSampleVoucherEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSampleVoucherEmailResponse response = emailService.sendSampleVoucherEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendConfirmationEmailResponse sendConfirmationEmail(@RequestBody SendConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendConfirmationEmailResponse response = emailService.sendConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendNewConfirmationEmailNotUsed", produces = "application/sd-service")
    @ResponseBody
    public SendNewConfirmationEmailResponse sendNewConfirmationEmail(@RequestBody SendNewConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendNewConfirmationEmailResponse response = emailService.sendNewConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAffiliateConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAffiliateConfirmationEmailResponse sendAffiliateConfirmationEmail(@RequestBody SendAffiliateConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAffiliateConfirmationEmailResponse response = emailService.sendAffiliateConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "send3rdPartyConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public Send3rdPartyConfirmationEmailResponse send3rdPartyConfirmationEmail(@RequestBody Send3rdPartyConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        Send3rdPartyConfirmationEmailResponse response = emailService.send3rdPartyConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendForgotPasswordEmail", produces = "application/sd-service")
    @ResponseBody
    public SendForgotPasswordEmailResponse sendForgotPasswordEmail(@RequestBody SendForgotPasswordEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendForgotPasswordEmailResponse response = emailService.sendForgotPasswordEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

 /*   @RequestMapping(value = "sendMovieVoucherEmail", produces = "application/sd-service")
    @ResponseBody
    public SendMovieVoucherEmailResponse sendMovieVoucherEmail(@RequestBody SendMovieVoucherEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendMovieVoucherEmailResponse response = emailService.sendMovieVoucherEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }*/

    @RequestMapping(value = "sendPromoCodeEmail", produces = "application/sd-service")
    @ResponseBody
    public SendPromoCodeEmailResponse sendPromoCodeEmail(@RequestBody SendPromoCodeEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendPromoCodeEmailResponse response = emailService.sendPromoCodeEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSurveyPromoCodeEmail", produces = "application/sd-service")
    @ResponseBody
    public SendSurveyPromoCodeEmailResponse sendSurveyPromoCodeEmail(@RequestBody SendSurveyPromoCodeEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSurveyPromoCodeEmailResponse response = emailService.sendSurveyPromoCodeEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCorporateEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCorporateEmailResponse sendCorporateEmail(@RequestBody SendCorporateEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCorporateEmailResponse response = emailService.sendCorporateEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCorporatebuyEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCorporatebuyEmailResponse sendCorporatebuyEmail(@RequestBody SendCorporatebuyEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCorporatebuyEmailResponse response = emailService.sendCorporatebuyEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFeaturedEmail", produces = "application/sd-service")
    @ResponseBody
    public SendFeaturedEmailResponse sendFeaturedEmail(@RequestBody SendFeaturedEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendFeaturedEmailResponse response = emailService.sendFeaturedEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCustomerQueryEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCustomerQueryEmailResponse sendCustomerQueryEmail(@RequestBody SendCustomerQueryEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCustomerQueryEmailResponse response = emailService.sendCustomerQueryEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCustomerFeedbackEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCustomerFeedbackEmailResponse sendCustomerFeedbackEmail(@RequestBody SendCustomerFeedbackEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCustomerFeedbackEmailResponse response = emailService.sendCustomerFeedbackEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendInviteEmail", produces = "application/sd-service")
    @ResponseBody
    public SendInviteEmailResponse sendInviteEmail(@RequestBody SendInviteEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendInviteEmailResponse response = emailService.sendInviteEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendShareDealEmail", produces = "application/sd-service")
    @ResponseBody
    public SendShareDealEmailResponse sendShareDealEmail(@RequestBody SendShareDealEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendShareDealEmailResponse response = emailService.sendShareDealEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendShareDealEmail_v1", produces = "application/sd-service")
    @ResponseBody
    public com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse sendShareDealEmail(@RequestBody com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse response = emailService.sendShareDealEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "sendGroupBuyEmail", produces = "application/sd-service")
    @ResponseBody
    public SendGroupBuyEmailResponse sendGroupBuyEmail(@RequestBody SendGroupBuyEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendGroupBuyEmailResponse response = emailService.sendGroupBuyEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendDailyMerchantEmail", produces = "application/sd-service")
    @ResponseBody
    public SendDailyMerchantEmailResponse sendDailyMerchantEmail(@RequestBody SendDailyMerchantEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendDailyMerchantEmailResponse response = emailService.sendDailyMerchantEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCodOrderSubmissionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCodOrderSubmissionEmailResponse sendCodOrderSubmissionEmail(@RequestBody SendCodOrderSubmissionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCodOrderSubmissionEmailResponse response = emailService.sendCodOrderSubmissionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCodOrderDispatchEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCodOrderDispatchEmailResponse sendCodOrderDispatchEmail(@RequestBody SendCodOrderDispatchEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCodOrderDispatchEmailResponse response = emailService.sendCodOrderDispatchEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendBdayCashBackEmail", produces = "application/sd-service")
    @ResponseBody
    public SendBdayCashBackEmailResponse sendBdayCashBackEmail(@RequestBody SendBdayCashBackEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendBdayCashBackEmailResponse response = emailService.sendBdayCashBackEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCashBackOfferEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCashBackOfferEmailResponse sendCashBackOfferEmail(@RequestBody SendCashBackOfferEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCashBackOfferEmailResponse response = emailService.sendCashBackOfferEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSdCashBackRewardOnPurchaseEmail", produces = "application/sd-service")
    @ResponseBody
    public SendSdCashBackRewardOnPurchaseEmailResponse sendSdCashBackRewardOnPurchaseEmail(@RequestBody SendSdCashBackRewardOnPurchaseEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        SendSdCashBackRewardOnPurchaseEmailResponse response = emailService.sendSdCashBackRewardOnPurchaseEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCustomerCareEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCustomerCareEmailResponse sendCustomerCareEmail(@RequestBody SendCustomerCareEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCustomerCareEmailResponse response = emailService.sendCustomerCareEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "sendCustomerCareEmail_v1", produces = "application/sd-service")
    @ResponseBody
    public com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse sendCustomerCareEmail(@RequestBody com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse response = emailService.sendCustomerCareEmailv1(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }


    @RequestMapping(value = "sendOrderSubmissionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendOrderSubmissionEmailResponse sendOrderSubmissionEmail(@RequestBody SendOrderSubmissionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderSubmissionEmailResponse response = emailService.sendOrderSubmissionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendReferralBenefitEmail", produces = "application/sd-service")
    @ResponseBody
    public SendReferralBenefitEmailResponse sendReferralBenefitEmail(@RequestBody SendReferralBenefitEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendReferralBenefitEmailResponse response = emailService.sendReferralBenefitEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendNewSubscriberReferralOneTimeEmail", produces = "application/sd-service")
    @ResponseBody
    public SendNewSubscriberReferralOneTimeEmailResponse sendNewSubscriberReferralOneTimeEmail(@RequestBody SendNewSubscriberReferralOneTimeEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        SendNewSubscriberReferralOneTimeEmailResponse response = emailService.sendNewSubscriberReferralOneTimeEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendMobileOrderSubmissionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(@RequestBody SendMobileOrderSubmissionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendMobileOrderSubmissionEmailResponse response = emailService.sendMobileOrderSubmissionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "sendMobileOrderSubmissionEmail_v1", produces = "application/sd-service")
    @ResponseBody
    public com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(@RequestBody com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse response = emailService.sendMobileOrderSubmissionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendMobileCustomerCareEmail", produces = "application/sd-service")
    @ResponseBody
    public SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(@RequestBody SendMobileCustomerCareEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendMobileCustomerCareEmailResponse response = emailService.sendMobileCustomerCareEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendMobileCustomerCareEmail_v1", produces = "application/sd-service")
    @ResponseBody
    public com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(@RequestBody com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse response = emailService.sendMobileCustomerCareEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "sendUSConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendUSConfirmationEmailResponse sendUSConfirmationEmail(@RequestBody SendUSConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendUSConfirmationEmailResponse response = emailService.sendUSConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendGeneralSDCashBackEmail", produces = "application/sd-service")
    @ResponseBody
    public SendGeneralSDCashBackEmailResponse sendGeneralSDCashBackEmail(@RequestBody SendGeneralSDCashBackEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendGeneralSDCashBackEmailResponse response = emailService.sendGeneralSDCashBackEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderRefundEmail", produces = "application/sd-service")
    @ResponseBody
    public SendOrderRefundEmailResponse sendOrderRefundEmail(@RequestBody SendOrderRefundEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderRefundEmailResponse response = emailService.sendOrderRefundEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendRefundAndSDCashBackEmail", produces = "application/sd-service")
    @ResponseBody
    public SendRefundAndSDCashBackEmailResponse sendRefundAndSDCashBackEmail(@RequestBody SendRefundAndSDCashBackEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendRefundAndSDCashBackEmailResponse response = emailService.sendRefundAndSDCashBackEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendVendorUserCreationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendVendorUserCreationEmailResponse sendVendorUserCreationEmail(@RequestBody SendVendorUserCreationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendVendorUserCreationEmailResponse response = emailService.sendVendorUserCreationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCodOrderSubmissionEmailProduct", produces = "application/sd-service")
    @ResponseBody
    public SendCodOrderSubmissionEmailProductResponse sendCodOrderSubmissionEmailProduct(@RequestBody SendCodOrderSubmissionEmailProductRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCodOrderSubmissionEmailProductResponse response = emailService.sendCodOrderSubmissionEmailProduct(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAuditMail", produces = "application/sd-service")
    @ResponseBody
    public SendAuditMailResponse sendAuditMail(@RequestBody SendAuditMailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAuditMailResponse response = emailService.sendAuditMail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFirstSubscriptionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendFirstSubscriptionEmailResponse sendFirstSubscriptionEmail(@RequestBody SendFirstSubscriptionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendFirstSubscriptionEmailResponse response = emailService.sendFirstSubscriptionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "notifySubscribedUser", produces = "application/sd-service")
    @ResponseBody
    public NotifySubscribedUserResponse notifySubscribedUser(@RequestBody NotifySubscribedUserRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        NotifySubscribedUserResponse response = emailService.notifySubscribedUser(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFeedbackMail", produces = "application/sd-service")
    @ResponseBody
    public SendFeedbackMailResponse sendFeedbackMail(@RequestBody SendFeedbackMailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendFeedbackMailResponse response = emailService.sendFeedbackMail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFeedbackMail_v1", produces = "application/sd-service")
    @ResponseBody
    public com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse sendFeedbackMail(@RequestBody com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse response = emailService.sendFeedbackMail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "sendAutoCaptureStatusEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAutoCaptureStatusEmailResponse sendAutoCaptureStatusEmail(@RequestBody SendAutoCaptureStatusEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAutoCaptureStatusEmailResponse response = emailService.sendAutoCaptureStatusEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFeaturedResponseEmail", produces = "application/sd-service")
    @ResponseBody
    public SendFeaturedResponseEmailResponse sendFeaturedResponseEmail(@RequestBody SendFeaturedResponseEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendFeaturedResponseEmailResponse response = emailService.sendFeaturedResponseEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendVoucherEmail", produces = "application/sd-service")
    @ResponseBody
    public SendVoucherEmailResponse sendVoucherEmail(@RequestBody SendVoucherEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendVoucherEmailResponse response = emailService.sendVoucherEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendEmployeeSdCashRewardEmail", produces = "application/sd-service")
    @ResponseBody
    public SendEmployeeSdCashRewardEmailResponse sendEmployeeSdCashRewardEmail(@RequestBody SendEmployeeSdCashRewardEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendEmployeeSdCashRewardEmailResponse response = emailService.sendEmployeeSdCashRewardEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendWayBillNumberExhaustionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendWayBillNumberExhaustionEmailResponse sendWayBillNumberExhaustionEmail(@RequestBody SendWayBillNumberExhaustionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendWayBillNumberExhaustionEmailResponse response = emailService.sendWayBillNumberExhaustionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendPendingResponseEmail", produces = "application/sd-service")
    @ResponseBody
    public SendPendingResponseEmailResponse sendPendingResponseEmail(@RequestBody SendPendingResponseEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendPendingResponseEmailResponse response = emailService.sendPendingResponseEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
/*
    @RequestMapping(value = "sendProductWorkflowRejectionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendProductWorkflowRejectionEmailResponse sendProductWorkflowRejectionEmail(@RequestBody SendProductWorkflowRejectionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendProductWorkflowRejectionEmailResponse response = emailService.sendProductWorkflowRejectionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
*/
    @RequestMapping(value = "sendDealShareEmailFromDealPage", produces = "application/sd-service")
    @ResponseBody
    public SendDealShareEmailFromDealPageResponse sendDealShareEmailFromDealPage(@RequestBody SendDealShareEmailFromDealPageRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendDealShareEmailFromDealPageResponse response = emailService.sendDealShareEmailFromDealPage(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendDealShareEmailFromPostBuy", produces = "application/sd-service")
    @ResponseBody
    public SendDealShareEmailFromPostBuyResponse sendDealShareEmailFromPostBuy(@RequestBody SendDealShareEmailFromPostBuyRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendDealShareEmailFromPostBuyResponse response = emailService.sendDealShareEmailFromPostBuy(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAppreciationAckSDCashEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAppreciationAckSDCashEmailResponse sendAppreciationAckSDCashEmail(@RequestBody SendAppreciationAckSDCashEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAppreciationAckSDCashEmailResponse response = emailService.sendAppreciationAckSDCashEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSDCashEmail", produces = "application/sd-service")
    @ResponseBody
    public SendSDCashEmailResponse sendSDCashEmail(@RequestBody SendSDCashEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSDCashEmailResponse response = emailService.sendSDCashEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendValentineEmail", produces = "application/sd-service")
    @ResponseBody
    public SendValentineEmailResponse sendValentineEmail(@RequestBody SendValentineEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendValentineEmailResponse response = emailService.sendValentineEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendBulkUploadResultEmail", produces = "application/sd-service")
    @ResponseBody
    public SendBulkUploadResultEmailResponse sendBulkUploadResultEmail(@RequestBody SendBulkUploadResultEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendBulkUploadResultEmailResponse response = emailService.sendBulkUploadResultEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendUserSDCashHistory", produces = "application/sd-service")
    @ResponseBody
    public SendUserSDCashHistoryResponse sendUserSDCashHistory(@RequestBody SendUserSDCashHistoryRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendUserSDCashHistoryResponse response = emailService.sendUserSDCashHistory(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendPromoCodeOnPurchaseEmail", produces = "application/sd-service")
    @ResponseBody
    public SendPromoCodeOnPurchaseEmailResponse sendPromoCodeOnPurchaseEmail(@RequestBody SendPromoCodeOnPurchaseEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendPromoCodeOnPurchaseEmailResponse response = emailService.sendPromoCodeOnPurchaseEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAutoAccountConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAutoAccountConfirmationEmailResponse sendAutoAccountConfirmationEmail(@RequestBody SendAutoAccountConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAutoAccountConfirmationEmailResponse response = emailService.sendAutoAccountConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderRefundEmailNew", produces = "application/sd-service")
    @ResponseBody
    public SendOrderRefundEmailNewResponse sendOrderRefundEmailNew(@RequestBody SendOrderRefundEmailNewRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderRefundEmailNewResponse response = emailService.sendOrderRefundEmailNew(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderReplacedSummaryEmail", produces = "application/sd-service")
    @ResponseBody
    public SendOrderReplacedSummaryEmailResponse sendOrderReplacedSummaryEmail(@RequestBody SendOrderReplacedSummaryEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderReplacedSummaryEmailResponse response = emailService.sendOrderReplacedSummaryEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFinanceVendorPaymentDetailsGenerationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendFinanceVendorPaymentDetailsGenerationEmailResponse sendFinanceVendorPaymentDetailsGenerationEmail(
            @RequestBody SendFinanceVendorPaymentDetailsGenerationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendFinanceVendorPaymentDetailsGenerationEmailResponse response = emailService.sendFinanceVendorPaymentDetailsGenerationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCODOrderEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCODOrderEmailResponse sendCODOrderEmail(@RequestBody SendCODOrderEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCODOrderEmailResponse response = emailService.sendCODOrderEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "cartDropoutNotificationAfter15days", produces = "application/sd-service")
    @ResponseBody
    public CartDropoutNotificationResponse cartDropoutNotificationAfter15days(@RequestBody CartDropoutNotificationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CartDropoutNotificationResponse response = emailService.cartDropoutNotificationAfter15days(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "cartDropoutNotificationWithin24hrTo15days", produces = "application/sd-service")
    @ResponseBody
    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrTo15days(@RequestBody CartDropoutNotificationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CartDropoutNotificationResponse response = emailService.cartDropoutNotificationWithin24hrTo15days(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "cartDropOutNotificationMail", produces = "application/sd-service")
    @ResponseBody
    public CartDropoutNotificationResponse cartDropOutNotificationMail(@RequestBody CartDropoutNotificationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CartDropoutNotificationResponse response = null;
        try {
            response = emailService.cartDropOutNotificationMail(request);
        } catch (Exception e) {
            response.setMessage("UMS Internal Server Error");
            Log.error("EmailServiceImpl Exception in cartDropOutNotificationMail",e);
        }
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "cartDropoutNotificationWithin24hrs", produces = "application/sd-service")
    @ResponseBody
    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrs(@RequestBody CartDropoutNotificationRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        CartDropoutNotificationResponse response = emailService.cartDropoutNotificationWithin24hrs(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendZendeskUploadedFileEmail", produces = "application/sd-service")
    @ResponseBody
    public SendZendeskUploadedFileEmailResponse sendZendeskUploadedFileEmail(@RequestBody SendZendeskUploadedFileEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendZendeskUploadedFileEmailResponse response = emailService.sendZendeskUploadedFileEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendHelpDeskEmail", produces = "application/sd-service")
    @ResponseBody
    public SendHelpDeskEmailResponse sendHelpDeskEmail(@RequestBody SendHelpDeskEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendHelpDeskEmailResponse response = emailService.sendHelpDeskEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCancelledOrderFeedbackRequestEmail", produces = "application/sd-service")
    @ResponseBody
    public SendCancelledOrderFeedbackRequestEmailResponse sendCancelledOrderFeedbackRequestEmail(@RequestBody SendCancelledOrderFeedbackRequestEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        SendCancelledOrderFeedbackRequestEmailResponse response = emailService.sendCancelledOrderFeedbackRequestEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendMultiVendorMappingResults", produces = "application/sd-service")
    @ResponseBody
    public SendMultiVendorMappingResultsResponse sendMultiVendorMappingResults(@RequestBody SendMultiVendorMappingResultsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendMultiVendorMappingResultsResponse response = emailService.sendMultiVendorMappingResults(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAlternateCollectMoneyEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAlternateCollectMoneyEmailResponse sendAlternateCollectMoneyEmail(@RequestBody SendAlternateCollectMoneyEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAlternateCollectMoneyEmailResponse response = emailService.sendAlternateCollectMoneyEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendReviewRequestMail", produces = "application/sd-service")
    @ResponseBody
    public SendReviewRequestMailResponse sendReviewRequestMail(@RequestBody SendReviewRequestMailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendReviewRequestMailResponse response = emailService.sendReviewRequestMail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAffiliateSubscriptionEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAffiliateSubscriptionEmailResponse sendAffiliateSubscriptionEmail(@RequestBody SendAffiliateSubscriptionEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAffiliateSubscriptionEmailResponse response = emailService.sendAffiliateSubscriptionEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAlternatePaymentConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAlternatePaymentConfirmationEmailResponse sendAlternatePaymentConfirmationEmail(@RequestBody SendAlternatePaymentConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        SendAlternatePaymentConfirmationEmailResponse response = emailService.sendAlternatePaymentConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAlternateRefundEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAlternateRefundEmailResponse sendAlternateRefundEmail(@RequestBody SendAlternateRefundEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAlternateRefundEmailResponse response = emailService.sendAlternateRefundEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAlternateAbsorbEmail", produces = "application/sd-service")
    @ResponseBody
    public SendAlternateAbsorbEmailResponse sendAlternateAbsorbEmail(@RequestBody SendAlternateAbsorbEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAlternateAbsorbEmailResponse response = emailService.sendAlternateAbsorbEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendReleaseDateShiftMail", produces = "application/sd-service")
    @ResponseBody
    public SendReleaseDateShiftMailResponse sendReleaseDateShiftMail(@RequestBody SendReleaseDateShiftMailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendReleaseDateShiftMailResponse response = emailService.sendReleaseDateShiftMail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSecondPaymentMail", produces = "application/sd-service")
    @ResponseBody
    public SendSecondPaymentMailResponse sendSecondPaymentMail(@RequestBody SendSecondPaymentMailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSecondPaymentMailResponse response = emailService.sendSecondPaymentMail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendPrebookPaymentConfirmationEmail", produces = "application/sd-service")
    @ResponseBody
    public SendPrebookPaymentConfirmationEmailResponse sendPrebookPaymentConfirmationEmail(@RequestBody SendPrebookPaymentConfirmationEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        SendPrebookPaymentConfirmationEmailResponse response = emailService.sendPrebookPaymentConfirmationEmail(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }
    
    @RequestMapping(value = "sendPrebookRetryEmail", produces = "application/sd-service")
    @ResponseBody
    public SendPrebookRetryEmailResponse sendPrebookRetryEmail(@RequestBody SendPrebookRetryEmailRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)  
            throws TransportException {
        SendPrebookRetryEmailResponse response = emailService.sendPrebookRetryEmail(request);
        response.setProtocol(request.getRequestProtocol());
        return response;
    }

}
