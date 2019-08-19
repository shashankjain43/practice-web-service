package com.snapdeal.ums.web.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.ums.server.services.ISmsService;
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

@Controller
@RequestMapping("/service/ums/smsservice/")
public class SmsServiceController {

    @Autowired
    private ISmsService smsService;

    @RequestMapping(value = "sendAffiliateSubscriptionSms", produces = "application/sd-service")
    @ResponseBody
    public SendAffiliateSubscriptionSmsResponse sendAffiliateSubscriptionSms(@RequestBody SendAffiliateSubscriptionSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAffiliateSubscriptionSmsResponse response = smsService.sendAffiliateSubscriptionSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendVoucherSms", produces = "application/sd-service")
    @ResponseBody
    public SendVoucherSmsResponse sendVoucherSms(@RequestBody SendVoucherSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendVoucherSmsResponse response = smsService.sendVoucherSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendVoucherSms2", produces = "application/sd-service")
    @ResponseBody
    public SendVoucherSmsResponse2 sendVoucherSms(@RequestBody SendVoucherSmsRequest2 request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendVoucherSmsResponse2 response = smsService.sendVoucherSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendVerificationCode", produces = "application/sd-service")
    @ResponseBody
    public SendVerificationCodeResponse sendVerificationCode(@RequestBody SendVerificationCodeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendVerificationCodeResponse response = smsService.sendVerificationCode(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendFreeDealSms", produces = "application/sd-service")
    @ResponseBody
    public SendFreeDealSmsResponse sendFreeDealSms(@RequestBody SendFreeDealSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendFreeDealSmsResponse response = smsService.sendFreeDealSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendRbtDealSms", produces = "application/sd-service")
    @ResponseBody
    public SendRbtDealSmsResponse sendRbtDealSms(@RequestBody SendRbtDealSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendRbtDealSmsResponse response = smsService.sendRbtDealSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSurveyPromoCodeSms", produces = "application/sd-service")
    @ResponseBody
    public SendSurveyPromoCodeSmsResponse sendSurveyPromoCodeSms(@RequestBody SendSurveyPromoCodeSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSurveyPromoCodeSmsResponse response = smsService.sendSurveyPromoCodeSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendWelcomeSms", produces = "application/sd-service")
    @ResponseBody
    public SendWelcomeSmsResponse sendWelcomeSms(@RequestBody SendWelcomeSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendWelcomeSmsResponse response = smsService.sendWelcomeSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSuborderSmsReminder", produces = "application/sd-service")
    @ResponseBody
    public SendSuborderSmsReminderResponse sendSuborderSmsReminder(@RequestBody SendSuborderSmsReminderRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSuborderSmsReminderResponse response = smsService.sendSuborderSmsReminder(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendShareDealSms", produces = "application/sd-service")
    @ResponseBody
    public SendShareDealSmsResponse sendShareDealSms(@RequestBody SendShareDealSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendShareDealSmsResponse response = smsService.sendShareDealSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendPromoCodeForSmsCampaign", produces = "application/sd-service")
    @ResponseBody
    public SendPromoCodeForSmsCampaignResponse sendPromoCodeForSmsCampaign(@RequestBody SendPromoCodeForSmsCampaignRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendPromoCodeForSmsCampaignResponse response = smsService.sendPromoCodeForSmsCampaign(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOfferAvailedSms", produces = "application/sd-service")
    @ResponseBody
    public SendOfferAvailedSmsResponse sendOfferAvailedSms(@RequestBody SendOfferAvailedSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOfferAvailedSmsResponse response = smsService.sendOfferAvailedSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderConfirmationProductSms", produces = "application/sd-service")
    @ResponseBody
    public SendOrderConfirmationProductSmsResponse sendOrderConfirmationProductSms(@RequestBody SendOrderConfirmationProductSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderConfirmationProductSmsResponse response = smsService.sendOrderConfirmationProductSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendVoucherDetailMenuSms", produces = "application/sd-service")
    @ResponseBody
    public SendVoucherDetailMenuSmsResponse sendVoucherDetailMenuSms(@RequestBody SendVoucherDetailMenuSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendVoucherDetailMenuSmsResponse response = smsService.sendVoucherDetailMenuSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderLocalCODDeliveredSms", produces = "application/sd-service")
    @ResponseBody
    public SendOrderLocalCODDeliveredSmsResponse sendOrderLocalCODDeliveredSms(@RequestBody SendOrderLocalCODDeliveredSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderLocalCODDeliveredSmsResponse response = smsService.sendOrderLocalCODDeliveredSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderLocalCODShippedSms", produces = "application/sd-service")
    @ResponseBody
    public SendOrderLocalCODShippedSmsResponse sendOrderLocalCODShippedSms(@RequestBody SendOrderLocalCODShippedSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderLocalCODShippedSmsResponse response = smsService.sendOrderLocalCODShippedSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendNewMobileSubscriberPromoCodeSms", produces = "application/sd-service")
    @ResponseBody
    public SendNewMobileSubscriberPromoCodeSmsResponse sendNewMobileSubscriberPromoCodeSms(@RequestBody SendNewMobileSubscriberPromoCodeSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse)
            throws TransportException {
        SendNewMobileSubscriberPromoCodeSmsResponse response = smsService.sendNewMobileSubscriberPromoCodeSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderSummarySms", produces = "application/sd-service")
    @ResponseBody
    public SendOrderSummarySmsResponse sendOrderSummarySms(@RequestBody SendOrderSummarySmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderSummarySmsResponse response = smsService.sendOrderSummarySms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderVerificationCode", produces = "application/sd-service")
    @ResponseBody
    public SendOrderVerificationCodeResponse sendOrderVerificationCode(@RequestBody SendOrderVerificationCodeRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderVerificationCodeResponse response = smsService.sendOrderVerificationCode(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendUnsubscribeSms", produces = "application/sd-service")
    @ResponseBody
    public SendUnsubscribeSmsResponse sendUnsubscribeSms(@RequestBody SendUnsubscribeSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendUnsubscribeSmsResponse response = smsService.sendUnsubscribeSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "isDNDActive", produces = "application/sd-service")
    @ResponseBody
    public IsDNDActiveResponse isDNDActive(@RequestBody IsDNDActiveRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        IsDNDActiveResponse response = smsService.isDNDActive(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getVoucherText", produces = "application/sd-service")
    @ResponseBody
    public GetVoucherTextResponse getVoucherText(@RequestBody GetVoucherTextRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetVoucherTextResponse response = smsService.getVoucherText(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendProductDeliveredSms", produces = "application/sd-service")
    @ResponseBody
    public SendProductDeliveredSmsResponse sendProductDeliveredSms(@RequestBody SendProductDeliveredSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendProductDeliveredSmsResponse response = smsService.sendProductDeliveredSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCartDropoutSms", produces = "application/sd-service")
    @ResponseBody
    public SendCartDropoutSmsResponse sendCartDropoutSms(@RequestBody SendCartDropoutSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCartDropoutSmsResponse response = smsService.sendCartDropoutSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getProductDeliveredSms", produces = "application/sd-service")
    @ResponseBody
    public GetProductDeliveredSmsResponse getProductDeliveredSms(@RequestBody GetProductDeliveredSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetProductDeliveredSmsResponse response = smsService.getProductDeliveredSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "getVoucherSms", produces = "application/sd-service")
    @ResponseBody
    public GetVoucherSmsResponse getVoucherSms(@RequestBody GetVoucherSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        GetVoucherSmsResponse response = smsService.getVoucherSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "send", produces = "application/sd-service")
    @ResponseBody
    public SendResponse send(@RequestBody SendRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendResponse response = smsService.send(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendOrderReplacedSummarySms", produces = "application/sd-service")
    @ResponseBody
    public SendOrderReplacedSummarySmsResponse sendOrderReplacedSummarySms(@RequestBody SendOrderReplacedSummarySmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendOrderReplacedSummarySmsResponse response = smsService.sendOrderReplacedSummarySms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendPendingCODOrderSms", produces = "application/sd-service")
    @ResponseBody
    public SendPendingCODOrderSmsResponse sendPendingCODOrderSms(@RequestBody SendPendingCODOrderSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendPendingCODOrderSmsResponse response = smsService.sendPendingCODOrderSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCODOrderSms", produces = "application/sd-service")
    @ResponseBody
    public SendCODOrderSmsResponse sendCODOrderSms(@RequestBody SendCODOrderSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCODOrderSmsResponse response = smsService.sendCODOrderSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendCsatSms", produces = "application/sd-service")
    @ResponseBody
    public SendCsatSmsResponse sendCsatSms(@RequestBody SendCsatSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendCsatSmsResponse response = smsService.sendCsatSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendSubscribtionSms", produces = "application/sd-service")
    @ResponseBody
    public SendSubscribtionSmsResponse sendSubscribtionSms(@RequestBody SendSubscribtionSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendSubscribtionSmsResponse response = smsService.sendSubscribtionSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

    @RequestMapping(value = "sendAcknowladgeSms", produces = "application/sd-service")
    @ResponseBody
    public SendAcknowladgeSmsResponse sendAcknowladgeSms(@RequestBody SendAcknowladgeSmsRequest request, HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse) throws TransportException {
        SendAcknowladgeSmsResponse response = smsService.sendAcknowladgeSms(request);
        response.setProtocol(request.getResponseProtocol());
        return response;
    }

}
