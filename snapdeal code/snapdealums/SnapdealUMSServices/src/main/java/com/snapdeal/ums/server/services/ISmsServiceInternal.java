
package com.snapdeal.ums.server.services;

import java.util.Date;
import java.util.HashMap;
import java.util.List;

import com.snapdeal.base.notification.sms.SmsMessage;
import com.snapdeal.core.sro.order.PromoCodeSRO;
import com.snapdeal.ums.sms.sro.OrderComplaintSRO;
import com.snapdeal.ums.userNeftDetails.EnhancedUserNEFTDetailsSRO;

public interface ISmsServiceInternal {

    public void sendVoucherSms(Integer suborder);

    public void sendVoucherSms(List<Integer> suborders, int catalogId);

    public void sendVerificationCode(String mobile, String verificationCode);

    public void sendFreeDealSms(String mobile, PromoCodeSRO promoCode);

    public void sendRbtDealSms(String mobile, PromoCodeSRO promoCode);

    public void sendSurveyPromoCodeSms(String mobile, PromoCodeSRO promoCode);

    public void sendWelcomeSms(String mobile);

    void sendSuborderSmsReminder(String mobile, Integer suborder);

    public void sendShareDealSms(String mobile, String recipient, Integer deal);

    public void sendPromoCodeForSmsCampaign(String mobile, String code, Date expDate);

    public void sendOfferAvailedSms(String mobile);

    public void sendOrderConfirmationProductSms(Integer suborder);

    public void sendVoucherDetailMenuSms(Integer suborder);

    void sendOrderLocalCODDeliveredSms(Integer suborder);

    void sendOrderLocalCODShippedSms(Integer suborder);

    void sendNewMobileSubscriberPromoCodeSms(String mobile, PromoCodeSRO promoCode);

    // New Notifications
    void sendOrderSummarySms(Integer order);

    void sendOrderVerificationCode(String mobile, String verificationCode);

    public void sendUnsubscribeSms(String mobile);

    boolean isDNDActive(String mobile);

    public String getVoucherText(Integer order);

    void sendProductDeliveredSms(Integer suborder);

    void sendCartDropoutSms(String mobile, String userName, String catalogText);

    SmsMessage getProductDeliveredSms(Integer suborder);

    SmsMessage getVoucherSms(List<Integer> suborders, int catalogId);

    public void send(SmsMessage message);

    void sendOrderReplacedSummarySms(Integer order);

    void sendPendingCODOrderSms(String mobile, String orderCode);

    void sendCODOrderSms(String mobile, String customerName, String orderCode);

    void sendCsatSms(String mobile);

    public void sendAffiliateSubscriptionSms(String mobile, List<HashMap<String, String>> listOfMap);

    void sendSubscribtionSms(String mobile, String params,String email); 
    
    public void sendAcknowladgeSms(OrderComplaintSRO orderComplaint, String templateName);

    public void sendNEFTDetailsUpdatedSms(EnhancedUserNEFTDetailsSRO  enhancedUserNEFTDetails, String mobileNum);
}
