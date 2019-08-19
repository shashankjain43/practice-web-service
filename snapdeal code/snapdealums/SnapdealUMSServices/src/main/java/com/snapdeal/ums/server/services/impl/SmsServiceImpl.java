package com.snapdeal.ums.server.services.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.notification.sms.SmsMessage;
import com.snapdeal.ums.server.services.ISmsService;
import com.snapdeal.ums.server.services.ISmsServiceInternal;
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
@Service("umsSmsService")
public class SmsServiceImpl implements ISmsService {

    @Autowired
    ISmsServiceInternal smsServiceInternal;

    @Deprecated
    @Override
    public SendAffiliateSubscriptionSmsResponse sendAffiliateSubscriptionSms(SendAffiliateSubscriptionSmsRequest request) {
        SendAffiliateSubscriptionSmsResponse response = new SendAffiliateSubscriptionSmsResponse();
        smsServiceInternal.sendAffiliateSubscriptionSms(request.getMobile(), request.getListOfMap());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendVoucherSmsResponse sendVoucherSms(SendVoucherSmsRequest request) {

        SendVoucherSmsResponse response = new SendVoucherSmsResponse();
        smsServiceInternal.sendVoucherSms(request.getSuborderId());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendVoucherSmsResponse2 sendVoucherSms(SendVoucherSmsRequest2 request) {
        SendVoucherSmsResponse2 response = new SendVoucherSmsResponse2();
        smsServiceInternal.sendVoucherSms(request.getSuborders(), request.getCatalogId());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendVerificationCodeResponse sendVerificationCode(SendVerificationCodeRequest request) {
        SendVerificationCodeResponse response = new SendVerificationCodeResponse();
        smsServiceInternal.sendVerificationCode(request.getMobile(), request.getVerificationCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendFreeDealSmsResponse sendFreeDealSms(SendFreeDealSmsRequest request) {
        SendFreeDealSmsResponse response = new SendFreeDealSmsResponse();
        smsServiceInternal.sendFreeDealSms(request.getMobile(), request.getPromoCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendRbtDealSmsResponse sendRbtDealSms(SendRbtDealSmsRequest request) {
        SendRbtDealSmsResponse response = new SendRbtDealSmsResponse();
        smsServiceInternal.sendRbtDealSms(request.getMobile(), request.getPromoCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendSurveyPromoCodeSmsResponse sendSurveyPromoCodeSms(SendSurveyPromoCodeSmsRequest request) {
        SendSurveyPromoCodeSmsResponse response = new SendSurveyPromoCodeSmsResponse();
        smsServiceInternal.sendSurveyPromoCodeSms(request.getMobile(), request.getPromoCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendWelcomeSmsResponse sendWelcomeSms(SendWelcomeSmsRequest request) {
        SendWelcomeSmsResponse response = new SendWelcomeSmsResponse();
        smsServiceInternal.sendWelcomeSms(request.getMobile());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendSuborderSmsReminderResponse sendSuborderSmsReminder(SendSuborderSmsReminderRequest request) {
        SendSuborderSmsReminderResponse response = new SendSuborderSmsReminderResponse();
        smsServiceInternal.sendSuborderSmsReminder(request.getMobile(), request.getSuborder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendShareDealSmsResponse sendShareDealSms(SendShareDealSmsRequest request) {
        SendShareDealSmsResponse response = new SendShareDealSmsResponse();
        smsServiceInternal.sendShareDealSms(request.getMobile(), request.getRecipient(), request.getDeal());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendPromoCodeForSmsCampaignResponse sendPromoCodeForSmsCampaign(SendPromoCodeForSmsCampaignRequest request) {
        SendPromoCodeForSmsCampaignResponse response = new SendPromoCodeForSmsCampaignResponse();
        smsServiceInternal.sendPromoCodeForSmsCampaign(request.getMobile(), request.getCode(), request.getExpDate());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOfferAvailedSmsResponse sendOfferAvailedSms(SendOfferAvailedSmsRequest request) {
        SendOfferAvailedSmsResponse response = new SendOfferAvailedSmsResponse();
        smsServiceInternal.sendOfferAvailedSms(request.getMobile());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOrderConfirmationProductSmsResponse sendOrderConfirmationProductSms(SendOrderConfirmationProductSmsRequest request) {
        SendOrderConfirmationProductSmsResponse response = new SendOrderConfirmationProductSmsResponse();
        smsServiceInternal.sendOrderConfirmationProductSms(request.getSuborder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendVoucherDetailMenuSmsResponse sendVoucherDetailMenuSms(SendVoucherDetailMenuSmsRequest request) {
        SendVoucherDetailMenuSmsResponse response = new SendVoucherDetailMenuSmsResponse();
        smsServiceInternal.sendVoucherDetailMenuSms(request.getSuborder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOrderLocalCODDeliveredSmsResponse sendOrderLocalCODDeliveredSms(SendOrderLocalCODDeliveredSmsRequest request) {
        SendOrderLocalCODDeliveredSmsResponse response = new SendOrderLocalCODDeliveredSmsResponse();
        smsServiceInternal.sendOrderLocalCODDeliveredSms(request.getSuborder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOrderLocalCODShippedSmsResponse sendOrderLocalCODShippedSms(SendOrderLocalCODShippedSmsRequest request) {
        SendOrderLocalCODShippedSmsResponse response = new SendOrderLocalCODShippedSmsResponse();
        smsServiceInternal.sendOrderLocalCODShippedSms(request.getSuborder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendNewMobileSubscriberPromoCodeSmsResponse sendNewMobileSubscriberPromoCodeSms(SendNewMobileSubscriberPromoCodeSmsRequest request) {
        SendNewMobileSubscriberPromoCodeSmsResponse response = new SendNewMobileSubscriberPromoCodeSmsResponse();
        smsServiceInternal.sendNewMobileSubscriberPromoCodeSms(request.getMobile(), request.getPromoCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOrderSummarySmsResponse sendOrderSummarySms(SendOrderSummarySmsRequest request) {
        SendOrderSummarySmsResponse response = new SendOrderSummarySmsResponse();
        smsServiceInternal.sendOrderSummarySms(request.getOrder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOrderVerificationCodeResponse sendOrderVerificationCode(SendOrderVerificationCodeRequest request) {
        SendOrderVerificationCodeResponse response = new SendOrderVerificationCodeResponse();
        smsServiceInternal.sendOrderVerificationCode(request.getMobile(), request.getVerificationCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendUnsubscribeSmsResponse sendUnsubscribeSms(SendUnsubscribeSmsRequest request) {
        SendUnsubscribeSmsResponse response = new SendUnsubscribeSmsResponse();
        smsServiceInternal.sendUnsubscribeSms(request.getMobile());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public IsDNDActiveResponse isDNDActive(IsDNDActiveRequest request) {
        IsDNDActiveResponse response = new IsDNDActiveResponse();
        boolean isDNDActive = smsServiceInternal.isDNDActive(request.getMobile());
        response.setIsDNDActive(isDNDActive);

        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public GetVoucherTextResponse getVoucherText(GetVoucherTextRequest request) {
        GetVoucherTextResponse response = new GetVoucherTextResponse();
        String voucherText = smsServiceInternal.getVoucherText(request.getOrder());
        response.setGetVoucherText(voucherText);
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendProductDeliveredSmsResponse sendProductDeliveredSms(SendProductDeliveredSmsRequest request) {
        SendProductDeliveredSmsResponse response = new SendProductDeliveredSmsResponse();
        smsServiceInternal.sendProductDeliveredSms(request.getSuborder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendCartDropoutSmsResponse sendCartDropoutSms(SendCartDropoutSmsRequest request) {
        SendCartDropoutSmsResponse response = new SendCartDropoutSmsResponse();
        smsServiceInternal.sendCartDropoutSms(request.getMobile(), request.getUserName(), request.getCatalogText());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public GetProductDeliveredSmsResponse getProductDeliveredSms(GetProductDeliveredSmsRequest request) {
        GetProductDeliveredSmsResponse response = new GetProductDeliveredSmsResponse();
        SmsMessage message = smsServiceInternal.getProductDeliveredSms(request.getSuborder());
        //TODO        response.setGetProductDeliveredSms(message);
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public GetVoucherSmsResponse getVoucherSms(GetVoucherSmsRequest request) {
        GetVoucherSmsResponse response = new GetVoucherSmsResponse();
        SmsMessage message = smsServiceInternal.getVoucherSms(request.getSuborders(), request.getCatalogId());
        //TODO:        response.setGetVoucherSms(message);
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendResponse send(SendRequest request) {
        SendResponse response = new SendResponse();
        //TODO:        smsServiceInternal.send(request.getSuborderId());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendOrderReplacedSummarySmsResponse sendOrderReplacedSummarySms(SendOrderReplacedSummarySmsRequest request) {
        SendOrderReplacedSummarySmsResponse response = new SendOrderReplacedSummarySmsResponse();
        smsServiceInternal.sendOrderReplacedSummarySms(request.getOrder());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendPendingCODOrderSmsResponse sendPendingCODOrderSms(SendPendingCODOrderSmsRequest request) {
        SendPendingCODOrderSmsResponse response = new SendPendingCODOrderSmsResponse();
        smsServiceInternal.sendPendingCODOrderSms(request.getMobile(), request.getOrderCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendCODOrderSmsResponse sendCODOrderSms(SendCODOrderSmsRequest request) {
        SendCODOrderSmsResponse response = new SendCODOrderSmsResponse();
        smsServiceInternal.sendCODOrderSms(request.getMobile(), request.getCustomerName(), request.getOrderCode());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendCsatSmsResponse sendCsatSms(SendCsatSmsRequest request) {
        SendCsatSmsResponse response = new SendCsatSmsResponse();
        smsServiceInternal.sendCsatSms(request.getMobile());

        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendSubscribtionSmsResponse sendSubscribtionSms(SendSubscribtionSmsRequest request) {
        SendSubscribtionSmsResponse response = new SendSubscribtionSmsResponse();
        smsServiceInternal.sendSubscribtionSms(request.getMobile(), request.getParams(), request.getEmail());
        response.setSuccessful(true);
        return response;
    }

    @Deprecated
    @Override
    public SendAcknowladgeSmsResponse sendAcknowladgeSms(SendAcknowladgeSmsRequest request) {
        SendAcknowladgeSmsResponse response = new SendAcknowladgeSmsResponse();
        //TODO:        smsServiceInternal.sendAcknowladgeSms(request.getOrderComplaint(), request.getOrderComplaint());
        response.setSuccessful(true);
        return response;
    }

}
