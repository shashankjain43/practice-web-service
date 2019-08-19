package com.snapdeal.ums.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.IEmailClientService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.email.ext.email.*;

@Service("EmailClientService")
public class EmailClientServiceImpl implements IEmailClientService {

    private final static String CLIENT_SERVICE_URL = "/email";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;
    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(EmailClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/email/", "emailserver.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    public SendResponse send(SendRequest request) {
        SendResponse response = new SendResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/send";
            response = (SendResponse) transportService.executeRequest(url, request, null, SendResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderSummaryEmailResponse sendOrderSummaryEmail(SendOrderSummaryEmailRequest request) {
        SendOrderSummaryEmailResponse response = new SendOrderSummaryEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderSummaryEmail";
            response = (SendOrderSummaryEmailResponse) transportService.executeRequest(url, request, null, SendOrderSummaryEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderRetryEmailResponse sendOrderRetryEmail(SendOrderRetryEmailRequest request) {
        SendOrderRetryEmailResponse response = new SendOrderRetryEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderRetryEmail";
            response = (SendOrderRetryEmailResponse) transportService.executeRequest(url, request, null, SendOrderRetryEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    /*
        public SendSampleVoucherEmailResponse sendSampleVoucherEmail(SendSampleVoucherEmailRequest request) {
            SendSampleVoucherEmailResponse response = new SendSampleVoucherEmailResponse();
            response.setSuccessful(false);
            try {
                String url = getWebServiceURL() + "/sendSampleVoucherEmail";
                response = (SendSampleVoucherEmailResponse) transportService.executeRequest(url, request, null, SendSampleVoucherEmailResponse.class);
                return response;
            } catch (com.snapdeal.base.exception.TransportException e) {
                LOG.error("Error Message: {}, error {}", e.getMessage(), e);
            }
            return response;
        }
    */
    public SendConfirmationEmailResponse sendConfirmationEmail(SendConfirmationEmailRequest request) {
        SendConfirmationEmailResponse response = new SendConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendConfirmationEmail";
            response = (SendConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    /* public SendNewConfirmationEmailResponse sendNewConfirmationEmail(SendNewConfirmationEmailRequest request) {
         SendNewConfirmationEmailResponse response = new SendNewConfirmationEmailResponse();
         response.setSuccessful(false);
         try {
             String url = getWebServiceURL() + "/sendNewConfirmationEmail";
             response = (SendNewConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendNewConfirmationEmailResponse.class);
             return response;
         } catch (com.snapdeal.base.exception.TransportException e) {
             LOG.error("Error Message: {}, error {}", e.getMessage(), e);
         }
         return response;
     }*/

    @Deprecated
    public SendAffiliateConfirmationEmailResponse sendAffiliateConfirmationEmail(SendAffiliateConfirmationEmailRequest request) {
        SendAffiliateConfirmationEmailResponse response = new SendAffiliateConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAffiliateConfirmationEmail";
            response = (SendAffiliateConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendAffiliateConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public Send3rdPartyConfirmationEmailResponse send3rdPartyConfirmationEmail(Send3rdPartyConfirmationEmailRequest request) {
        Send3rdPartyConfirmationEmailResponse response = new Send3rdPartyConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/send3rdPartyConfirmationEmail";
            response = (Send3rdPartyConfirmationEmailResponse) transportService.executeRequest(url, request, null, Send3rdPartyConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendForgotPasswordEmailResponse sendForgotPasswordEmail(SendForgotPasswordEmailRequest request) {
        SendForgotPasswordEmailResponse response = new SendForgotPasswordEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendForgotPasswordEmail";
            response = (SendForgotPasswordEmailResponse) transportService.executeRequest(url, request, null, SendForgotPasswordEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    /*   public SendMovieVoucherEmailResponse sendMovieVoucherEmail(SendMovieVoucherEmailRequest request) {
           SendMovieVoucherEmailResponse response = new SendMovieVoucherEmailResponse();
           response.setSuccessful(false);
           try {
               String url = getWebServiceURL() + "/sendMovieVoucherEmail";
               response = (SendMovieVoucherEmailResponse) transportService.executeRequest(url, request, null, SendMovieVoucherEmailResponse.class);
               return response;
           } catch (com.snapdeal.base.exception.TransportException e) {
               LOG.error("Error Message: {}, error {}", e.getMessage(), e);
           }
           return response;
       }
    */
    public SendPromoCodeEmailResponse sendPromoCodeEmail(SendPromoCodeEmailRequest request) {
        SendPromoCodeEmailResponse response = new SendPromoCodeEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPromoCodeEmail";
            response = (SendPromoCodeEmailResponse) transportService.executeRequest(url, request, null, SendPromoCodeEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    //    public SendAffiliatePromoCodeEmailResponse sendAffiliatePromoCodeEmail(SendAffiliatePromoCodeEmailRequest request) {
    //        SendAffiliatePromoCodeEmailResponse response = new SendAffiliatePromoCodeEmailResponse();
    //        response.setSuccessful(false);
    //        try {
    //            String url = getWebServiceURL() + "/sendAffiliatePromoCodeEmail";
    //            response = (SendAffiliatePromoCodeEmailResponse) transportService.executeRequest(url, request, null, SendAffiliatePromoCodeEmailResponse.class);
    //            return response;
    //        } catch (com.snapdeal.base.exception.TransportException e) {
    //            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
    //        }
    //        return response;
    //    }

    public SendSurveyPromoCodeEmailResponse sendSurveyPromoCodeEmail(SendSurveyPromoCodeEmailRequest request) {
        SendSurveyPromoCodeEmailResponse response = new SendSurveyPromoCodeEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSurveyPromoCodeEmail";
            response = (SendSurveyPromoCodeEmailResponse) transportService.executeRequest(url, request, null, SendSurveyPromoCodeEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCorporateEmailResponse sendCorporateEmail(SendCorporateEmailRequest request) {
        SendCorporateEmailResponse response = new SendCorporateEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCorporateEmail";
            response = (SendCorporateEmailResponse) transportService.executeRequest(url, request, null, SendCorporateEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCorporatebuyEmailResponse sendCorporatebuyEmail(SendCorporatebuyEmailRequest request) {
        SendCorporatebuyEmailResponse response = new SendCorporatebuyEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCorporatebuyEmail";
            response = (SendCorporatebuyEmailResponse) transportService.executeRequest(url, request, null, SendCorporatebuyEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendFeaturedEmailResponse sendFeaturedEmail(SendFeaturedEmailRequest request) {
        SendFeaturedEmailResponse response = new SendFeaturedEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFeaturedEmail";
            response = (SendFeaturedEmailResponse) transportService.executeRequest(url, request, null, SendFeaturedEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCustomerQueryEmailResponse sendCustomerQueryEmail(SendCustomerQueryEmailRequest request) {
        SendCustomerQueryEmailResponse response = new SendCustomerQueryEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCustomerQueryEmail";
            response = (SendCustomerQueryEmailResponse) transportService.executeRequest(url, request, null, SendCustomerQueryEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCustomerFeedbackEmailResponse sendCustomerFeedbackEmail(SendCustomerFeedbackEmailRequest request) {
        SendCustomerFeedbackEmailResponse response = new SendCustomerFeedbackEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCustomerFeedbackEmail";
            response = (SendCustomerFeedbackEmailResponse) transportService.executeRequest(url, request, null, SendCustomerFeedbackEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendInviteEmailResponse sendInviteEmail(SendInviteEmailRequest request) {
        SendInviteEmailResponse response = new SendInviteEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendInviteEmail";
            response = (SendInviteEmailResponse) transportService.executeRequest(url, request, null, SendInviteEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendShareDealEmailResponse sendShareDealEmail(SendShareDealEmailRequest request) {
        SendShareDealEmailResponse response = new SendShareDealEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendShareDealEmail";
            response = (SendShareDealEmailResponse) transportService.executeRequest(url, request, null, SendShareDealEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse sendShareDealEmail(com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest request) {
        com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendShareDealEmail_v1";
            response = (com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse) transportService.executeRequest(url, request, null, com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    public SendGroupBuyEmailResponse sendGroupBuyEmail(SendGroupBuyEmailRequest request) {
        SendGroupBuyEmailResponse response = new SendGroupBuyEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendGroupBuyEmail";
            response = (SendGroupBuyEmailResponse) transportService.executeRequest(url, request, null, SendGroupBuyEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendDailyMerchantEmailResponse sendDailyMerchantEmail(SendDailyMerchantEmailRequest request) {
        SendDailyMerchantEmailResponse response = new SendDailyMerchantEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendDailyMerchantEmail";
            response = (SendDailyMerchantEmailResponse) transportService.executeRequest(url, request, null, SendDailyMerchantEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCodOrderSubmissionEmailResponse sendCodOrderSubmissionEmail(SendCodOrderSubmissionEmailRequest request) {
        SendCodOrderSubmissionEmailResponse response = new SendCodOrderSubmissionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCodOrderSubmissionEmail";
            response = (SendCodOrderSubmissionEmailResponse) transportService.executeRequest(url, request, null, SendCodOrderSubmissionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCodOrderDispatchEmailResponse sendCodOrderDispatchEmail(SendCodOrderDispatchEmailRequest request) {
        SendCodOrderDispatchEmailResponse response = new SendCodOrderDispatchEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCodOrderDispatchEmail";
            response = (SendCodOrderDispatchEmailResponse) transportService.executeRequest(url, request, null, SendCodOrderDispatchEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendBdayCashBackEmailResponse sendBdayCashBackEmail(SendBdayCashBackEmailRequest request) {
        SendBdayCashBackEmailResponse response = new SendBdayCashBackEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendBdayCashBackEmail";
            response = (SendBdayCashBackEmailResponse) transportService.executeRequest(url, request, null, SendBdayCashBackEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCashBackOfferEmailResponse sendCashBackOfferEmail(SendCashBackOfferEmailRequest request) {
        SendCashBackOfferEmailResponse response = new SendCashBackOfferEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCashBackOfferEmail";
            response = (SendCashBackOfferEmailResponse) transportService.executeRequest(url, request, null, SendCashBackOfferEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendSdCashBackRewardOnPurchaseEmailResponse sendSdCashBackRewardOnPurchaseEmail(SendSdCashBackRewardOnPurchaseEmailRequest request) {
        SendSdCashBackRewardOnPurchaseEmailResponse response = new SendSdCashBackRewardOnPurchaseEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSdCashBackRewardOnPurchaseEmail";
            response = (SendSdCashBackRewardOnPurchaseEmailResponse) transportService.executeRequest(url, request, null, SendSdCashBackRewardOnPurchaseEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCustomerCareEmailResponse sendCustomerCareEmail(SendCustomerCareEmailRequest request) {
        SendCustomerCareEmailResponse response = new SendCustomerCareEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCustomerCareEmail";
            response = (SendCustomerCareEmailResponse) transportService.executeRequest(url, request, null, SendCustomerCareEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse sendCustomerCareEmail(com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest request) {
        com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCustomerCareEmail_v1";
            response = (com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse) transportService.executeRequest(url, request, null, com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendOrderSubmissionEmailResponse sendOrderSubmissionEmail(SendOrderSubmissionEmailRequest request) {
        SendOrderSubmissionEmailResponse response = new SendOrderSubmissionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderSubmissionEmail";
            response = (SendOrderSubmissionEmailResponse) transportService.executeRequest(url, request, null, SendOrderSubmissionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendReferralBenefitEmailResponse sendReferralBenefitEmail(SendReferralBenefitEmailRequest request) {
        SendReferralBenefitEmailResponse response = new SendReferralBenefitEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendReferralBenefitEmail";
            response = (SendReferralBenefitEmailResponse) transportService.executeRequest(url, request, null, SendReferralBenefitEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendNewSubscriberReferralOneTimeEmailResponse sendNewSubscriberReferralOneTimeEmail(SendNewSubscriberReferralOneTimeEmailRequest request) {
        SendNewSubscriberReferralOneTimeEmailResponse response = new SendNewSubscriberReferralOneTimeEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendNewSubscriberReferralOneTimeEmail";
            response = (SendNewSubscriberReferralOneTimeEmailResponse) transportService.executeRequest(url, request, null, SendNewSubscriberReferralOneTimeEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(SendMobileOrderSubmissionEmailRequest request) {
        SendMobileOrderSubmissionEmailResponse response = new SendMobileOrderSubmissionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendMobileOrderSubmissionEmail";
            response = (SendMobileOrderSubmissionEmailResponse) transportService.executeRequest(url, request, null, SendMobileOrderSubmissionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest request) {
        com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendMobileOrderSubmissionEmail_v1";
            response = (com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse) transportService.executeRequest(url, request, null, com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest request) {
        com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendMobileCustomerCareEmail_v1";
            response = (com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse) transportService.executeRequest(url, request, null, com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(SendMobileCustomerCareEmailRequest request) {
        SendMobileCustomerCareEmailResponse response = new SendMobileCustomerCareEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendMobileCustomerCareEmail";
            response = (SendMobileCustomerCareEmailResponse) transportService.executeRequest(url, request, null, SendMobileCustomerCareEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    public SendUSConfirmationEmailResponse sendUSConfirmationEmail(SendUSConfirmationEmailRequest request) {
        SendUSConfirmationEmailResponse response = new SendUSConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendUSConfirmationEmail";
            response = (SendUSConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendUSConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendGeneralSDCashBackEmailResponse sendGeneralSDCashBackEmail(SendGeneralSDCashBackEmailRequest request) {
        SendGeneralSDCashBackEmailResponse response = new SendGeneralSDCashBackEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendGeneralSDCashBackEmail";
            response = (SendGeneralSDCashBackEmailResponse) transportService.executeRequest(url, request, null, SendGeneralSDCashBackEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendOrderRefundEmailResponse sendOrderRefundEmail(SendOrderRefundEmailRequest request) {
        SendOrderRefundEmailResponse response = new SendOrderRefundEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderRefundEmail";
            response = (SendOrderRefundEmailResponse) transportService.executeRequest(url, request, null, SendOrderRefundEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendRefundAndSDCashBackEmailResponse sendRefundAndSDCashBackEmail(SendRefundAndSDCashBackEmailRequest request) {
        SendRefundAndSDCashBackEmailResponse response = new SendRefundAndSDCashBackEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendRefundAndSDCashBackEmail";
            response = (SendRefundAndSDCashBackEmailResponse) transportService.executeRequest(url, request, null, SendRefundAndSDCashBackEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendVendorUserCreationEmailResponse sendVendorUserCreationEmail(SendVendorUserCreationEmailRequest request) {
        SendVendorUserCreationEmailResponse response = new SendVendorUserCreationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendVendorUserCreationEmail";
            response = (SendVendorUserCreationEmailResponse) transportService.executeRequest(url, request, null, SendVendorUserCreationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCodOrderSubmissionEmailProductResponse sendCodOrderSubmissionEmailProduct(SendCodOrderSubmissionEmailProductRequest request) {
        SendCodOrderSubmissionEmailProductResponse response = new SendCodOrderSubmissionEmailProductResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCodOrderSubmissionEmailProduct";
            response = (SendCodOrderSubmissionEmailProductResponse) transportService.executeRequest(url, request, null, SendCodOrderSubmissionEmailProductResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAuditMailResponse sendAuditMail(SendAuditMailRequest request) {
        SendAuditMailResponse response = new SendAuditMailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAuditMail";
            response = (SendAuditMailResponse) transportService.executeRequest(url, request, null, SendAuditMailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendFirstSubscriptionEmailResponse sendFirstSubscriptionEmail(SendFirstSubscriptionEmailRequest request) {
        SendFirstSubscriptionEmailResponse response = new SendFirstSubscriptionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFirstSubscriptionEmail";
            response = (SendFirstSubscriptionEmailResponse) transportService.executeRequest(url, request, null, SendFirstSubscriptionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public NotifySubscribedUserResponse notifySubscribedUser(NotifySubscribedUserRequest request) {
        NotifySubscribedUserResponse response = new NotifySubscribedUserResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/notifySubscribedUser";
            response = (NotifySubscribedUserResponse) transportService.executeRequest(url, request, null, NotifySubscribedUserResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendFeedbackMailResponse sendFeedbackMail(SendFeedbackMailRequest request) {
        SendFeedbackMailResponse response = new SendFeedbackMailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFeedbackMail";
            response = (SendFeedbackMailResponse) transportService.executeRequest(url, request, null, SendFeedbackMailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse sendFeedbackMail(com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request) {
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFeedbackMail_v1";
            response = (com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse) transportService.executeRequest(url, request, null, com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAutoCaptureStatusEmailResponse sendAutoCaptureStatusEmail(SendAutoCaptureStatusEmailRequest request) {
        SendAutoCaptureStatusEmailResponse response = new SendAutoCaptureStatusEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAutoCaptureStatusEmail";
            response = (SendAutoCaptureStatusEmailResponse) transportService.executeRequest(url, request, null, SendAutoCaptureStatusEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendFeaturedResponseEmailResponse sendFeaturedResponseEmail(SendFeaturedResponseEmailRequest request) {
        SendFeaturedResponseEmailResponse response = new SendFeaturedResponseEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFeaturedResponseEmail";
            response = (SendFeaturedResponseEmailResponse) transportService.executeRequest(url, request, null, SendFeaturedResponseEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendVoucherEmailResponse sendVoucherEmail(SendVoucherEmailRequest request) {
        SendVoucherEmailResponse response = new SendVoucherEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendVoucherEmail";
            response = (SendVoucherEmailResponse) transportService.executeRequest(url, request, null, SendVoucherEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendEmployeeSdCashRewardEmailResponse sendEmployeeSdCashRewardEmail(SendEmployeeSdCashRewardEmailRequest request) {
        SendEmployeeSdCashRewardEmailResponse response = new SendEmployeeSdCashRewardEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendEmployeeSdCashRewardEmail";
            response = (SendEmployeeSdCashRewardEmailResponse) transportService.executeRequest(url, request, null, SendEmployeeSdCashRewardEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendWayBillNumberExhaustionEmailResponse sendWayBillNumberExhaustionEmail(SendWayBillNumberExhaustionEmailRequest request) {
        SendWayBillNumberExhaustionEmailResponse response = new SendWayBillNumberExhaustionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendWayBillNumberExhaustionEmail";
            response = (SendWayBillNumberExhaustionEmailResponse) transportService.executeRequest(url, request, null, SendWayBillNumberExhaustionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendPendingResponseEmailResponse sendPendingResponseEmail(SendPendingResponseEmailRequest request) {
        SendPendingResponseEmailResponse response = new SendPendingResponseEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPendingResponseEmail";
            response = (SendPendingResponseEmailResponse) transportService.executeRequest(url, request, null, SendPendingResponseEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    /*   public SendProductWorkflowRejectionEmailResponse sendProductWorkflowRejectionEmail(SendProductWorkflowRejectionEmailRequest request) {
           SendProductWorkflowRejectionEmailResponse response = new SendProductWorkflowRejectionEmailResponse();
           response.setSuccessful(false);
           try {
               String url = getWebServiceURL() + "/sendProductWorkflowRejectionEmail";
               response = (SendProductWorkflowRejectionEmailResponse) transportService.executeRequest(url, request, null, SendProductWorkflowRejectionEmailResponse.class);
               return response;
           } catch (com.snapdeal.base.exception.TransportException e) {
               LOG.error("Error Message: {}, error {}", e.getMessage(), e);
           }
           return response;
       }*/

    public SendDealShareEmailFromDealPageResponse sendDealShareEmailFromDealPage(SendDealShareEmailFromDealPageRequest request) {
        SendDealShareEmailFromDealPageResponse response = new SendDealShareEmailFromDealPageResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendDealShareEmailFromDealPage";
            response = (SendDealShareEmailFromDealPageResponse) transportService.executeRequest(url, request, null, SendDealShareEmailFromDealPageResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendDealShareEmailFromPostBuyResponse sendDealShareEmailFromPostBuy(SendDealShareEmailFromPostBuyRequest request) {
        SendDealShareEmailFromPostBuyResponse response = new SendDealShareEmailFromPostBuyResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendDealShareEmailFromPostBuy";
            response = (SendDealShareEmailFromPostBuyResponse) transportService.executeRequest(url, request, null, SendDealShareEmailFromPostBuyResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAppreciationAckSDCashEmailResponse sendAppreciationAckSDCashEmail(SendAppreciationAckSDCashEmailRequest request) {
        SendAppreciationAckSDCashEmailResponse response = new SendAppreciationAckSDCashEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAppreciationAckSDCashEmail";
            response = (SendAppreciationAckSDCashEmailResponse) transportService.executeRequest(url, request, null, SendAppreciationAckSDCashEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendSDCashEmailResponse sendSDCashEmail(SendSDCashEmailRequest request) {
        SendSDCashEmailResponse response = new SendSDCashEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSDCashEmail";
            response = (SendSDCashEmailResponse) transportService.executeRequest(url, request, null, SendSDCashEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendValentineEmailResponse sendValentineEmail(SendValentineEmailRequest request) {
        SendValentineEmailResponse response = new SendValentineEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendValentineEmail";
            response = (SendValentineEmailResponse) transportService.executeRequest(url, request, null, SendValentineEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendBulkUploadResultEmailResponse sendBulkUploadResultEmail(SendBulkUploadResultEmailRequest request) {
        SendBulkUploadResultEmailResponse response = new SendBulkUploadResultEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendBulkUploadResultEmail";
            response = (SendBulkUploadResultEmailResponse) transportService.executeRequest(url, request, null, SendBulkUploadResultEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendUserSDCashHistoryResponse sendUserSDCashHistory(SendUserSDCashHistoryRequest request) {
        SendUserSDCashHistoryResponse response = new SendUserSDCashHistoryResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendUserSDCashHistory";
            response = (SendUserSDCashHistoryResponse) transportService.executeRequest(url, request, null, SendUserSDCashHistoryResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendPromoCodeOnPurchaseEmailResponse sendPromoCodeOnPurchaseEmail(SendPromoCodeOnPurchaseEmailRequest request) {
        SendPromoCodeOnPurchaseEmailResponse response = new SendPromoCodeOnPurchaseEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPromoCodeOnPurchaseEmail";
            response = (SendPromoCodeOnPurchaseEmailResponse) transportService.executeRequest(url, request, null, SendPromoCodeOnPurchaseEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAutoAccountConfirmationEmailResponse sendAutoAccountConfirmationEmail(SendAutoAccountConfirmationEmailRequest request) {
        SendAutoAccountConfirmationEmailResponse response = new SendAutoAccountConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAutoAccountConfirmationEmail";
            response = (SendAutoAccountConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendAutoAccountConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendOrderRefundEmailNewResponse sendOrderRefundEmailNew(SendOrderRefundEmailNewRequest request) {
        SendOrderRefundEmailNewResponse response = new SendOrderRefundEmailNewResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderRefundEmailNew";
            response = (SendOrderRefundEmailNewResponse) transportService.executeRequest(url, request, null, SendOrderRefundEmailNewResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendOrderReplacedSummaryEmailResponse sendOrderReplacedSummaryEmail(SendOrderReplacedSummaryEmailRequest request) {
        SendOrderReplacedSummaryEmailResponse response = new SendOrderReplacedSummaryEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderReplacedSummaryEmail";
            response = (SendOrderReplacedSummaryEmailResponse) transportService.executeRequest(url, request, null, SendOrderReplacedSummaryEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendFinanceVendorPaymentDetailsGenerationEmailResponse sendFinanceVendorPaymentDetailsGenerationEmail(SendFinanceVendorPaymentDetailsGenerationEmailRequest request) {
        SendFinanceVendorPaymentDetailsGenerationEmailResponse response = new SendFinanceVendorPaymentDetailsGenerationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFinanceVendorPaymentDetailsGenerationEmail";
            response = (SendFinanceVendorPaymentDetailsGenerationEmailResponse) transportService.executeRequest(url, request, null,
                    SendFinanceVendorPaymentDetailsGenerationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCODOrderEmailResponse sendCODOrderEmail(SendCODOrderEmailRequest request) {
        SendCODOrderEmailResponse response = new SendCODOrderEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCODOrderEmail";
            response = (SendCODOrderEmailResponse) transportService.executeRequest(url, request, null, SendCODOrderEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public CartDropoutNotificationResponse cartDropoutNotificationAfter15days(CartDropoutNotificationRequest request) {
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/cartDropoutNotificationAfter15days";
            response = (CartDropoutNotificationResponse) transportService.executeRequest(url, request, null, CartDropoutNotificationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrTo15days(CartDropoutNotificationRequest request) {
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/cartDropoutNotificationWithin24hrTo15days";
            response = (CartDropoutNotificationResponse) transportService.executeRequest(url, request, null, CartDropoutNotificationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrs(CartDropoutNotificationRequest request) {
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/cartDropoutNotificationWithin24hrs";
            response = (CartDropoutNotificationResponse) transportService.executeRequest(url, request, null, CartDropoutNotificationResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendZendeskUploadedFileEmailResponse sendZendeskUploadedFileEmail(SendZendeskUploadedFileEmailRequest request) {
        SendZendeskUploadedFileEmailResponse response = new SendZendeskUploadedFileEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendZendeskUploadedFileEmail";
            response = (SendZendeskUploadedFileEmailResponse) transportService.executeRequest(url, request, null, SendZendeskUploadedFileEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendHelpDeskEmailResponse sendHelpDeskEmail(SendHelpDeskEmailRequest request) {
        SendHelpDeskEmailResponse response = new SendHelpDeskEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendHelpDeskEmail";
            response = (SendHelpDeskEmailResponse) transportService.executeRequest(url, request, null, SendHelpDeskEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendCancelledOrderFeedbackRequestEmailResponse sendCancelledOrderFeedbackRequestEmail(SendCancelledOrderFeedbackRequestEmailRequest request) {
        SendCancelledOrderFeedbackRequestEmailResponse response = new SendCancelledOrderFeedbackRequestEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCancelledOrderFeedbackRequestEmail";
            response = (SendCancelledOrderFeedbackRequestEmailResponse) transportService.executeRequest(url, request, null, SendCancelledOrderFeedbackRequestEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendMultiVendorMappingResultsResponse sendMultiVendorMappingResults(SendMultiVendorMappingResultsRequest request) {
        SendMultiVendorMappingResultsResponse response = new SendMultiVendorMappingResultsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendMultiVendorMappingResults";
            response = (SendMultiVendorMappingResultsResponse) transportService.executeRequest(url, request, null, SendMultiVendorMappingResultsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAlternateCollectMoneyEmailResponse sendAlternateCollectMoneyEmail(SendAlternateCollectMoneyEmailRequest request) {
        SendAlternateCollectMoneyEmailResponse response = new SendAlternateCollectMoneyEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAlternateCollectMoneyEmail";
            response = (SendAlternateCollectMoneyEmailResponse) transportService.executeRequest(url, request, null, SendAlternateCollectMoneyEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendReviewRequestMailResponse sendReviewRequestMail(SendReviewRequestMailRequest request) {
        SendReviewRequestMailResponse response = new SendReviewRequestMailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendReviewRequestMail";
            response = (SendReviewRequestMailResponse) transportService.executeRequest(url, request, null, SendReviewRequestMailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAffiliateSubscriptionEmailResponse sendAffiliateSubscriptionEmail(SendAffiliateSubscriptionEmailRequest request) {
        SendAffiliateSubscriptionEmailResponse response = new SendAffiliateSubscriptionEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAffiliateSubscriptionEmail";
            response = (SendAffiliateSubscriptionEmailResponse) transportService.executeRequest(url, request, null, SendAffiliateSubscriptionEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public SendPrebookRetryEmailResponse sendPrebookRetryEmail(SendPrebookRetryEmailRequest request) {
        SendPrebookRetryEmailResponse response = new SendPrebookRetryEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPrebookRetryEmail";
            response = (SendPrebookRetryEmailResponse) transportService.executeRequest(url, request, null, SendPrebookRetryEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Override
    public SendAlternateRetryEmailResponse sendAlternateRetryEmail(SendAlternateRetryEmailRequest request) {
        SendAlternateRetryEmailResponse response = new SendAlternateRetryEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAlternateRetryEmail";
            response = (SendAlternateRetryEmailResponse) transportService.executeRequest(url, request, null, SendAlternateRetryEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAlternatePaymentConfirmationEmailResponse sendAlternatePaymentConfirmationEmail(SendAlternatePaymentConfirmationEmailRequest request) {
        SendAlternatePaymentConfirmationEmailResponse response = new SendAlternatePaymentConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAlternatePaymentConfirmationEmail";
            response = (SendAlternatePaymentConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendAlternatePaymentConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAlternateRefundEmailResponse sendAlternateRefundEmail(SendAlternateRefundEmailRequest request) {
        SendAlternateRefundEmailResponse response = new SendAlternateRefundEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAlternateRefundEmail";
            response = (SendAlternateRefundEmailResponse) transportService.executeRequest(url, request, null, SendAlternateRefundEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendAlternateAbsorbEmailResponse sendAlternateAbsorbEmail(SendAlternateAbsorbEmailRequest request) {
        SendAlternateAbsorbEmailResponse response = new SendAlternateAbsorbEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAlternateAbsorbEmail";
            response = (SendAlternateAbsorbEmailResponse) transportService.executeRequest(url, request, null, SendAlternateAbsorbEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendReleaseDateShiftMailResponse sendReleaseDateShiftMail(SendReleaseDateShiftMailRequest request) {
        SendReleaseDateShiftMailResponse response = new SendReleaseDateShiftMailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendReleaseDateShiftMail";
            response = (SendReleaseDateShiftMailResponse) transportService.executeRequest(url, request, null, SendReleaseDateShiftMailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendSecondPaymentMailResponse sendSecondPaymentMail(SendSecondPaymentMailRequest request) {
        SendSecondPaymentMailResponse response = new SendSecondPaymentMailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSecondPaymentMail";
            response = (SendSecondPaymentMailResponse) transportService.executeRequest(url, request, null, SendSecondPaymentMailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    public SendPrebookPaymentConfirmationEmailResponse sendPrebookPaymentConfirmationEmail(SendPrebookPaymentConfirmationEmailRequest request) {
        SendPrebookPaymentConfirmationEmailResponse response = new SendPrebookPaymentConfirmationEmailResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPrebookPaymentConfirmationEmail";
            response = (SendPrebookPaymentConfirmationEmailResponse) transportService.executeRequest(url, request, null, SendPrebookPaymentConfirmationEmailResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }
    
    @Override
    public CartDropoutNotificationResponse cartDropOutNotificationMail(CartDropoutNotificationRequest request) throws TransportException{
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        String url = getWebServiceURL() + "/cartDropOutNotificationMail";
        response = (CartDropoutNotificationResponse) transportService.executeRequest(url, request, null, CartDropoutNotificationResponse.class);
        return response;
    }
}
