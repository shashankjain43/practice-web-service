package com.snapdeal.ums.client.services.impl;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.exception.TransportException.TransportErrorCode;
import com.snapdeal.base.transport.service.ITransportService;
import com.snapdeal.ums.client.services.ISmsClientService;
import com.snapdeal.ums.client.services.IUMSClientService;
import com.snapdeal.ums.services.ext.smsservice.GetProductDeliveredSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.GetProductDeliveredSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.GetVoucherSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.GetVoucherSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.GetVoucherTextRequest;
import com.snapdeal.ums.services.ext.smsservice.GetVoucherTextResponse;
import com.snapdeal.ums.services.ext.smsservice.IsDNDActiveRequest;
import com.snapdeal.ums.services.ext.smsservice.IsDNDActiveResponse;
import com.snapdeal.ums.services.ext.smsservice.SendAcknowladgeSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendAcknowladgeSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendAffiliateSubscriptionSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendAffiliateSubscriptionSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendCODOrderSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendCODOrderSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendCartDropoutSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendCartDropoutSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendCsatSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendCsatSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendFreeDealSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendFreeDealSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendNewMobileSubscriberPromoCodeSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendNewMobileSubscriberPromoCodeSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOfferAvailedSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOfferAvailedSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOrderConfirmationProductSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOrderConfirmationProductSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOrderLocalCODDeliveredSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOrderLocalCODDeliveredSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOrderLocalCODShippedSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOrderLocalCODShippedSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOrderReplacedSummarySmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOrderReplacedSummarySmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOrderSummarySmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOrderSummarySmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendOrderVerificationCodeRequest;
import com.snapdeal.ums.services.ext.smsservice.SendOrderVerificationCodeResponse;
import com.snapdeal.ums.services.ext.smsservice.SendPendingCODOrderSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendPendingCODOrderSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendProductDeliveredSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendProductDeliveredSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendPromoCodeForSmsCampaignRequest;
import com.snapdeal.ums.services.ext.smsservice.SendPromoCodeForSmsCampaignResponse;
import com.snapdeal.ums.services.ext.smsservice.SendRbtDealSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendRbtDealSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendRequest;
import com.snapdeal.ums.services.ext.smsservice.SendResponse;
import com.snapdeal.ums.services.ext.smsservice.SendShareDealSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendShareDealSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendSuborderSmsReminderRequest;
import com.snapdeal.ums.services.ext.smsservice.SendSuborderSmsReminderResponse;
import com.snapdeal.ums.services.ext.smsservice.SendSubscribtionSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendSubscribtionSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendSurveyPromoCodeSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendSurveyPromoCodeSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendUnsubscribeSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendUnsubscribeSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendVerificationCodeRequest;
import com.snapdeal.ums.services.ext.smsservice.SendVerificationCodeResponse;
import com.snapdeal.ums.services.ext.smsservice.SendVoucherDetailMenuSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendVoucherDetailMenuSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendVoucherSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendVoucherSmsRequest2;
import com.snapdeal.ums.services.ext.smsservice.SendVoucherSmsResponse;
import com.snapdeal.ums.services.ext.smsservice.SendVoucherSmsResponse2;
import com.snapdeal.ums.services.ext.smsservice.SendWelcomeSmsRequest;
import com.snapdeal.ums.services.ext.smsservice.SendWelcomeSmsResponse;

@Service("SmsClientService")
public class SmsClientServiceImpl implements ISmsClientService {

    private final static String CLIENT_SERVICE_URL = "/sms";
    private String              webServiceURL;
    @Autowired
    private IUMSClientService   umsClientService;

    @Autowired
    private ITransportService   transportService;
    private final static Logger LOG                = (org.slf4j.LoggerFactory.getLogger(SmsClientServiceImpl.class));

    @PostConstruct
    public void init() {
        transportService.registerService("/service/ums/sms/", "smsservice.");
    }

    private String getWebServiceURL() throws TransportException {
        if (umsClientService.getWebServiceBaseURL() == null) {
            throw new TransportException(TransportErrorCode.BASE_URL_MISSING_EXCEPTION);
        }
        webServiceURL = umsClientService.getWebServiceBaseURL() + CLIENT_SERVICE_URL;
        return webServiceURL;
    }

    @Deprecated
    public SendAffiliateSubscriptionSmsResponse sendAffiliateSubscriptionSms(SendAffiliateSubscriptionSmsRequest request) {
        SendAffiliateSubscriptionSmsResponse response = new SendAffiliateSubscriptionSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAffiliateSubscriptionSms";
            response = (SendAffiliateSubscriptionSmsResponse) transportService.executeRequest(url, request, null, SendAffiliateSubscriptionSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendVoucherSmsResponse sendVoucherSms(SendVoucherSmsRequest request) {
        SendVoucherSmsResponse response = new SendVoucherSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendVoucherSms";
            response = (SendVoucherSmsResponse) transportService.executeRequest(url, request, null, SendVoucherSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendVoucherSmsResponse2 sendVoucherSms(SendVoucherSmsRequest2 request) {
        SendVoucherSmsResponse2 response = new SendVoucherSmsResponse2();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendVoucherSms";
            response = (SendVoucherSmsResponse2) transportService.executeRequest(url, request, null, SendVoucherSmsResponse2.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendVerificationCodeResponse sendVerificationCode(SendVerificationCodeRequest request) {
        SendVerificationCodeResponse response = new SendVerificationCodeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendVerificationCode";
            response = (SendVerificationCodeResponse) transportService.executeRequest(url, request, null, SendVerificationCodeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendFreeDealSmsResponse sendFreeDealSms(SendFreeDealSmsRequest request) {
        SendFreeDealSmsResponse response = new SendFreeDealSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendFreeDealSms";
            response = (SendFreeDealSmsResponse) transportService.executeRequest(url, request, null, SendFreeDealSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendRbtDealSmsResponse sendRbtDealSms(SendRbtDealSmsRequest request) {
        SendRbtDealSmsResponse response = new SendRbtDealSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendRbtDealSms";
            response = (SendRbtDealSmsResponse) transportService.executeRequest(url, request, null, SendRbtDealSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendSurveyPromoCodeSmsResponse sendSurveyPromoCodeSms(SendSurveyPromoCodeSmsRequest request) {
        SendSurveyPromoCodeSmsResponse response = new SendSurveyPromoCodeSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSurveyPromoCodeSms";
            response = (SendSurveyPromoCodeSmsResponse) transportService.executeRequest(url, request, null, SendSurveyPromoCodeSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendWelcomeSmsResponse sendWelcomeSms(SendWelcomeSmsRequest request) {
        SendWelcomeSmsResponse response = new SendWelcomeSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendWelcomeSms";
            response = (SendWelcomeSmsResponse) transportService.executeRequest(url, request, null, SendWelcomeSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendSuborderSmsReminderResponse sendSuborderSmsReminder(SendSuborderSmsReminderRequest request) {
        SendSuborderSmsReminderResponse response = new SendSuborderSmsReminderResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSuborderSmsReminder";
            response = (SendSuborderSmsReminderResponse) transportService.executeRequest(url, request, null, SendSuborderSmsReminderResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendShareDealSmsResponse sendShareDealSms(SendShareDealSmsRequest request) {
        SendShareDealSmsResponse response = new SendShareDealSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendShareDealSms";
            response = (SendShareDealSmsResponse) transportService.executeRequest(url, request, null, SendShareDealSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendPromoCodeForSmsCampaignResponse sendPromoCodeForSmsCampaign(SendPromoCodeForSmsCampaignRequest request) {
        SendPromoCodeForSmsCampaignResponse response = new SendPromoCodeForSmsCampaignResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPromoCodeForSmsCampaign";
            response = (SendPromoCodeForSmsCampaignResponse) transportService.executeRequest(url, request, null, SendPromoCodeForSmsCampaignResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOfferAvailedSmsResponse sendOfferAvailedSms(SendOfferAvailedSmsRequest request) {
        SendOfferAvailedSmsResponse response = new SendOfferAvailedSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOfferAvailedSms";
            response = (SendOfferAvailedSmsResponse) transportService.executeRequest(url, request, null, SendOfferAvailedSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderConfirmationProductSmsResponse sendOrderConfirmationProductSms(SendOrderConfirmationProductSmsRequest request) {
        SendOrderConfirmationProductSmsResponse response = new SendOrderConfirmationProductSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderConfirmationProductSms";
            response = (SendOrderConfirmationProductSmsResponse) transportService.executeRequest(url, request, null, SendOrderConfirmationProductSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendVoucherDetailMenuSmsResponse sendVoucherDetailMenuSms(SendVoucherDetailMenuSmsRequest request) {
        SendVoucherDetailMenuSmsResponse response = new SendVoucherDetailMenuSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendVoucherDetailMenuSms";
            response = (SendVoucherDetailMenuSmsResponse) transportService.executeRequest(url, request, null, SendVoucherDetailMenuSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderLocalCODDeliveredSmsResponse sendOrderLocalCODDeliveredSms(SendOrderLocalCODDeliveredSmsRequest request) {
        SendOrderLocalCODDeliveredSmsResponse response = new SendOrderLocalCODDeliveredSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderLocalCODDeliveredSms";
            response = (SendOrderLocalCODDeliveredSmsResponse) transportService.executeRequest(url, request, null, SendOrderLocalCODDeliveredSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderLocalCODShippedSmsResponse sendOrderLocalCODShippedSms(SendOrderLocalCODShippedSmsRequest request) {
        SendOrderLocalCODShippedSmsResponse response = new SendOrderLocalCODShippedSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderLocalCODShippedSms";
            response = (SendOrderLocalCODShippedSmsResponse) transportService.executeRequest(url, request, null, SendOrderLocalCODShippedSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendNewMobileSubscriberPromoCodeSmsResponse sendNewMobileSubscriberPromoCodeSms(SendNewMobileSubscriberPromoCodeSmsRequest request) {
        SendNewMobileSubscriberPromoCodeSmsResponse response = new SendNewMobileSubscriberPromoCodeSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendNewMobileSubscriberPromoCodeSms";
            response = (SendNewMobileSubscriberPromoCodeSmsResponse) transportService.executeRequest(url, request, null, SendNewMobileSubscriberPromoCodeSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderSummarySmsResponse sendOrderSummarySms(SendOrderSummarySmsRequest request) {
        SendOrderSummarySmsResponse response = new SendOrderSummarySmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderSummarySms";
            response = (SendOrderSummarySmsResponse) transportService.executeRequest(url, request, null, SendOrderSummarySmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendOrderVerificationCodeResponse sendOrderVerificationCode(SendOrderVerificationCodeRequest request) {
        SendOrderVerificationCodeResponse response = new SendOrderVerificationCodeResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderVerificationCode";
            response = (SendOrderVerificationCodeResponse) transportService.executeRequest(url, request, null, SendOrderVerificationCodeResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendUnsubscribeSmsResponse sendUnsubscribeSms(SendUnsubscribeSmsRequest request) {
        SendUnsubscribeSmsResponse response = new SendUnsubscribeSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendUnsubscribeSms";
            response = (SendUnsubscribeSmsResponse) transportService.executeRequest(url, request, null, SendUnsubscribeSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public IsDNDActiveResponse isDNDActive(IsDNDActiveRequest request) {
        IsDNDActiveResponse response = new IsDNDActiveResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/isDNDActive";
            response = (IsDNDActiveResponse) transportService.executeRequest(url, request, null, IsDNDActiveResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetVoucherTextResponse getVoucherText(GetVoucherTextRequest request) {
        GetVoucherTextResponse response = new GetVoucherTextResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getVoucherText";
            response = (GetVoucherTextResponse) transportService.executeRequest(url, request, null, GetVoucherTextResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendProductDeliveredSmsResponse sendProductDeliveredSms(SendProductDeliveredSmsRequest request) {
        SendProductDeliveredSmsResponse response = new SendProductDeliveredSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendProductDeliveredSms";
            response = (SendProductDeliveredSmsResponse) transportService.executeRequest(url, request, null, SendProductDeliveredSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendCartDropoutSmsResponse sendCartDropoutSms(SendCartDropoutSmsRequest request) {
        SendCartDropoutSmsResponse response = new SendCartDropoutSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCartDropoutSms";
            response = (SendCartDropoutSmsResponse) transportService.executeRequest(url, request, null, SendCartDropoutSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetProductDeliveredSmsResponse getProductDeliveredSms(GetProductDeliveredSmsRequest request) {
        GetProductDeliveredSmsResponse response = new GetProductDeliveredSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getProductDeliveredSms";
            response = (GetProductDeliveredSmsResponse) transportService.executeRequest(url, request, null, GetProductDeliveredSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public GetVoucherSmsResponse getVoucherSms(GetVoucherSmsRequest request) {
        GetVoucherSmsResponse response = new GetVoucherSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/getVoucherSms";
            response = (GetVoucherSmsResponse) transportService.executeRequest(url, request, null, GetVoucherSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
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
    public SendOrderReplacedSummarySmsResponse sendOrderReplacedSummarySms(SendOrderReplacedSummarySmsRequest request) {
        SendOrderReplacedSummarySmsResponse response = new SendOrderReplacedSummarySmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendOrderReplacedSummarySms";
            response = (SendOrderReplacedSummarySmsResponse) transportService.executeRequest(url, request, null, SendOrderReplacedSummarySmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendPendingCODOrderSmsResponse sendPendingCODOrderSms(SendPendingCODOrderSmsRequest request) {
        SendPendingCODOrderSmsResponse response = new SendPendingCODOrderSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendPendingCODOrderSms";
            response = (SendPendingCODOrderSmsResponse) transportService.executeRequest(url, request, null, SendPendingCODOrderSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendCODOrderSmsResponse sendCODOrderSms(SendCODOrderSmsRequest request) {
        SendCODOrderSmsResponse response = new SendCODOrderSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCODOrderSms";
            response = (SendCODOrderSmsResponse) transportService.executeRequest(url, request, null, SendCODOrderSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendCsatSmsResponse sendCsatSms(SendCsatSmsRequest request) {
        SendCsatSmsResponse response = new SendCsatSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendCsatSms";
            response = (SendCsatSmsResponse) transportService.executeRequest(url, request, null, SendCsatSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendSubscribtionSmsResponse sendSubscribtionSms(SendSubscribtionSmsRequest request) {
        SendSubscribtionSmsResponse response = new SendSubscribtionSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendSubscribtionSms";
            response = (SendSubscribtionSmsResponse) transportService.executeRequest(url, request, null, SendSubscribtionSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

    @Deprecated
    public SendAcknowladgeSmsResponse sendAcknowladgeSms(SendAcknowladgeSmsRequest request) {
        SendAcknowladgeSmsResponse response = new SendAcknowladgeSmsResponse();
        response.setSuccessful(false);
        try {
            String url = getWebServiceURL() + "/sendAcknowladgeSms";
            response = (SendAcknowladgeSmsResponse) transportService.executeRequest(url, request, null, SendAcknowladgeSmsResponse.class);
            return response;
        } catch (com.snapdeal.base.exception.TransportException e) {
            LOG.error("Error Message: {}, error {}", e.getMessage(), e);
        }
        return response;
    }

}
