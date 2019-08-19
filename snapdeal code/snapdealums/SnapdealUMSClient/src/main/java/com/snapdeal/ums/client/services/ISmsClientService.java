
package com.snapdeal.ums.client.services;

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

public interface ISmsClientService {

	@Deprecated
    public SendAffiliateSubscriptionSmsResponse sendAffiliateSubscriptionSms(SendAffiliateSubscriptionSmsRequest request);

    @Deprecated
    public SendVoucherSmsResponse sendVoucherSms(SendVoucherSmsRequest request);

    @Deprecated
    public SendVoucherSmsResponse2 sendVoucherSms(SendVoucherSmsRequest2 request);

    @Deprecated
    public SendVerificationCodeResponse sendVerificationCode(SendVerificationCodeRequest request);

    @Deprecated
    public SendFreeDealSmsResponse sendFreeDealSms(SendFreeDealSmsRequest request);

    @Deprecated
    public SendRbtDealSmsResponse sendRbtDealSms(SendRbtDealSmsRequest request);

    @Deprecated
    public SendSurveyPromoCodeSmsResponse sendSurveyPromoCodeSms(SendSurveyPromoCodeSmsRequest request);

    @Deprecated
    public SendWelcomeSmsResponse sendWelcomeSms(SendWelcomeSmsRequest request);

    @Deprecated
    public SendSuborderSmsReminderResponse sendSuborderSmsReminder(SendSuborderSmsReminderRequest request);

    @Deprecated
    public SendShareDealSmsResponse sendShareDealSms(SendShareDealSmsRequest request);

    @Deprecated
    public SendPromoCodeForSmsCampaignResponse sendPromoCodeForSmsCampaign(SendPromoCodeForSmsCampaignRequest request);

    @Deprecated
    public SendOfferAvailedSmsResponse sendOfferAvailedSms(SendOfferAvailedSmsRequest request);

    @Deprecated
    public SendOrderConfirmationProductSmsResponse sendOrderConfirmationProductSms(SendOrderConfirmationProductSmsRequest request);

    @Deprecated
    public SendVoucherDetailMenuSmsResponse sendVoucherDetailMenuSms(SendVoucherDetailMenuSmsRequest request);

    @Deprecated
    public SendOrderLocalCODDeliveredSmsResponse sendOrderLocalCODDeliveredSms(SendOrderLocalCODDeliveredSmsRequest request);

    @Deprecated
    public SendOrderLocalCODShippedSmsResponse sendOrderLocalCODShippedSms(SendOrderLocalCODShippedSmsRequest request);

    @Deprecated
    public SendNewMobileSubscriberPromoCodeSmsResponse sendNewMobileSubscriberPromoCodeSms(SendNewMobileSubscriberPromoCodeSmsRequest request);

    @Deprecated
    public SendOrderSummarySmsResponse sendOrderSummarySms(SendOrderSummarySmsRequest request);

    @Deprecated
    public SendOrderVerificationCodeResponse sendOrderVerificationCode(SendOrderVerificationCodeRequest request);

    @Deprecated
    public SendUnsubscribeSmsResponse sendUnsubscribeSms(SendUnsubscribeSmsRequest request);

    @Deprecated
    public IsDNDActiveResponse isDNDActive(IsDNDActiveRequest request);

    @Deprecated
    public GetVoucherTextResponse getVoucherText(GetVoucherTextRequest request);

    @Deprecated
    public SendProductDeliveredSmsResponse sendProductDeliveredSms(SendProductDeliveredSmsRequest request);

    @Deprecated
    public SendCartDropoutSmsResponse sendCartDropoutSms(SendCartDropoutSmsRequest request);

    @Deprecated
    public GetProductDeliveredSmsResponse getProductDeliveredSms(GetProductDeliveredSmsRequest request);

    @Deprecated
    public GetVoucherSmsResponse getVoucherSms(GetVoucherSmsRequest request);

    @Deprecated
    public SendResponse send(SendRequest request);

    @Deprecated
    public SendOrderReplacedSummarySmsResponse sendOrderReplacedSummarySms(SendOrderReplacedSummarySmsRequest request);

    @Deprecated
    public SendPendingCODOrderSmsResponse sendPendingCODOrderSms(SendPendingCODOrderSmsRequest request);

    @Deprecated
    public SendCODOrderSmsResponse sendCODOrderSms(SendCODOrderSmsRequest request);

    @Deprecated
    public SendCsatSmsResponse sendCsatSms(SendCsatSmsRequest request);

    @Deprecated
    public SendSubscribtionSmsResponse sendSubscribtionSms(SendSubscribtionSmsRequest request);

    @Deprecated
    public SendAcknowladgeSmsResponse sendAcknowladgeSms(SendAcknowladgeSmsRequest request);

}
