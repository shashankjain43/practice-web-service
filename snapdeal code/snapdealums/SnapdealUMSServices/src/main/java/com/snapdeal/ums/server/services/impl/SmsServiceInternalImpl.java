package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.memcached.service.IMemcachedService;
import com.snapdeal.base.notification.INotificationService;
import com.snapdeal.base.notification.sms.SmsMessage;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.base.vo.SmsTemplateVO;
import com.snapdeal.catalog.base.model.GetCatalogByIdRequest;
import com.snapdeal.catalog.base.model.GetServiceDealByIdRequest;
import com.snapdeal.catalog.base.sro.CatalogSRO;
import com.snapdeal.core.sro.order.PromoCodeSRO;
import com.snapdeal.core.sro.serviceDeal.ServiceDealSRO;
import com.snapdeal.ums.cache.SmsTemplateCache;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.oms.base.model.GetOrderByIdRequest;
import com.snapdeal.oms.base.model.GetSuborderByIdRequest;
import com.snapdeal.oms.base.sro.order.DealSuborderSRO;
import com.snapdeal.oms.base.sro.order.GetawaySuborderSRO;
import com.snapdeal.oms.base.sro.order.OrderSRO;
import com.snapdeal.oms.base.sro.order.SuborderSRO;
import com.snapdeal.oms.services.IOrderClientService;
import com.snapdeal.ums.core.entity.MobileSubscriber;
import com.snapdeal.ums.server.services.impl.SmsServiceImpl;
import com.snapdeal.shipping.core.model.GetShippingInfoForSuborderRequest;
import com.snapdeal.shipping.core.model.GetShippingInfoForSuborderResponse;
import com.snapdeal.shipping.service.IShippingClientService;
import com.snapdeal.shipping.sro.ShippingPackageSRO;
import com.snapdeal.ums.server.services.ISmsServiceInternal;
import com.snapdeal.ums.services.others.ICatalogService;
import com.snapdeal.ums.services.others.IDealsService;
import com.snapdeal.ums.sms.sro.OrderComplaintSRO;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsServiceInternal;
import com.snapdeal.ums.userNeftDetails.EnhancedUserNEFTDetailsSRO;

@Service("umsSmsServiceInternal")
@Transactional
public class SmsServiceInternalImpl implements ISmsServiceInternal {

    private static final Logger   LOG = LoggerFactory.getLogger(SmsServiceInternalImpl.class); 
    @Autowired
    private ICatalogService       catalogService;

    @Autowired
    private IMemcachedService     memcachedService;

    @Autowired
    private ISubscriptionsServiceInternal subscriptionsService;

    @Autowired
    private INotificationService  notificationService;

    @Autowired
    private IOrderClientService         orderService;
    
    @Autowired
    private IShippingClientService       shippingService;
    
    @Override
    public void sendVoucherSms(Integer suborderId) {
        SuborderSRO suborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        OrderSRO order = orderService.getOrderById(new GetOrderByIdRequest(suborder.getOrderId())).getOrderSRO();
        List<SuborderSRO> suborders = order.getSubordersForCatalog(suborder.getCatalogId());
        List<Integer> suborderIds = new ArrayList<Integer>();
        for(SuborderSRO sro : suborders)
            suborderIds.add(sro.getId());
        
        sendVoucherSms(suborderIds,suborder.getCatalogId());
    }

    @Override
    public void sendVoucherSms(List<Integer> suborderIds, int catalogId) {
        List<SuborderSRO> suborders = new ArrayList<SuborderSRO>();
        
        for(Integer suborderId : suborderIds){
            suborders.add(orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO());
        }

        if (suborders == null || suborders.isEmpty()) {
            return;
        }

        SmsMessage message = new SmsMessage(suborders.get(0).getOrderMobile());
        if (suborders.size() > 10) {
            message.setTemplateName("combinedSuborderSms");
        } else {
            message.setTemplateName("suborderSms");
        }
        CatalogSRO catalogDTO = catalogService.getCatalog(new GetCatalogByIdRequest((long)catalogId)).getCatalogSRO();
        if (catalogDTO == null) {
            return;
        }
        message.addTemplateParam("catalog", catalogDTO);

        StringBuilder voucherCodes = new StringBuilder();
        StringBuilder merchantPromoCodes = new StringBuilder();
        Date validUpto = null;
        for (SuborderSRO suborder : suborders) {
            voucherCodes.append(suborder.getCode());
            voucherCodes.append(",");

            if (SuborderSRO.Type.DEAL.code().equals(suborder.getCatalogType())) {
                DealSuborderSRO dealSuborder = (DealSuborderSRO) suborder;
                if (dealSuborder.getMerchantPromoCode() != null) {
                    merchantPromoCodes.append(dealSuborder.getMerchantPromoCode());
                    merchantPromoCodes.append(",");
                }
                if (validUpto == null) {
                    validUpto = dealSuborder.getValidUpto();
                }
            } else if (SuborderSRO.Type.GETAWAY.code().equals(suborder.getCatalogType())) {
                GetawaySuborderSRO getawaySuborder = (GetawaySuborderSRO) suborder;
                if (getawaySuborder.getMerchantPromoCode() != null) {
                    merchantPromoCodes.append(getawaySuborder.getMerchantPromoCode());
                    merchantPromoCodes.append(",");
                }
                if (validUpto == null) {
                    validUpto = getawaySuborder.getValidUpto();
                }
            }
        }
        message.addTemplateParam("suborders", voucherCodes.substring(0, voucherCodes.length() - 1));
        message.addTemplateParam("validUpto", validUpto);
        if (merchantPromoCodes.length() > 0) {
            message.addTemplateParam("merchantPromoCodes", merchantPromoCodes.substring(0, merchantPromoCodes.length() - 1));
        }

        send(message);
    
    }

    
    @Override
    public void sendVerificationCode(String mobile, String verificationCode) {
        SmsMessage message = new SmsMessage(mobile, "verificationSms");
        message.addTemplateParam("verificationCode", verificationCode);
        send(message);
    }

    @Override
    public void sendFreeDealSms(String mobile, PromoCodeSRO promoCode) {
        SmsMessage message = new SmsMessage(mobile, "freeDealSms");
        message.addTemplateParam("promoCode", promoCode);
        send(message);
    }

    @Override
    public void sendRbtDealSms(String mobile, PromoCodeSRO promoCode) {
        SmsMessage message = new SmsMessage(mobile, "rbtDealSms");
        message.addTemplateParam("promoCode", promoCode);
        send(message);
    }

    @Override
    public void sendSurveyPromoCodeSms(String mobile, PromoCodeSRO promoCode) {
        SmsMessage message = new SmsMessage(mobile, "surveyPromoCodeSms");
        message.addTemplateParam("promoCode", promoCode);
        send(message);
    }

    @Override
    public void sendWelcomeSms(String mobile) {
        if (!DateUtils.isDNDHour()) {
            SmsMessage message = new SmsMessage(mobile, "welcomeSms");
            send(message);
        } else {
            LOG.info("Blocking Message to-{} type-{} due to DND hour or DND active mobile ", mobile, "welcomeSms");
        }
    }

    @Override
    public void sendSuborderSmsReminder(String mobile, Integer suborderId) {
        SuborderSRO suborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        
        SmsMessage message = new SmsMessage(mobile, "suborderSmsReminder");
        message.addTemplateParam("suborder", suborder);
        message.addTemplateParam("catalog", catalogService.getCatalog(new GetCatalogByIdRequest((long)suborder.getCatalogId())).getCatalogSRO());
        send(message);
    }

   //TODO: @Autowired
    IDealsService dealService;
    @Override
    public void sendShareDealSms(String mobile, String recipient, Integer deal) {
        
        ServiceDealSRO  serviceDealSRO = dealService.getDealById(new GetServiceDealByIdRequest((long)deal)).getServiceDealSRO();
        SmsMessage message = new SmsMessage(mobile, "shareDealSms");
        message.addTemplateParam("recipientName", recipient);
        message.addTemplateParam("deal", serviceDealSRO);
        send(message);
    
    }

    @Override
    public void sendPromoCodeForSmsCampaign(String mobile, String code, Date expDate) {
        SmsMessage message = new SmsMessage(mobile, "smsCampaignMessage");
        message.addTemplateParam("promocode", code);
        message.addTemplateParam("expdate", expDate);
        send(message);
    }

    @Override
    public void sendOfferAvailedSms(String mobile) {
        SmsMessage message = new SmsMessage(mobile, "smsCampaignOfferAvailed");
        send(message);
    }

    @Override
    public void sendOrderConfirmationProductSms(Integer suborderId) {
        SuborderSRO suborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        OrderSRO order = orderService.getOrderById(new GetOrderByIdRequest(suborder.getOrderId())).getOrderSRO();
        SmsMessage message = new SmsMessage(order.getAddressDetail().getMobile(), "orderConfirmationProductSms");
        message.addTemplateParam("order", order);
        message.addTemplateParam("catalog", catalogService.getCatalog(new GetCatalogByIdRequest((long)suborder.getCatalogId())).getCatalogSRO());
        send(message);
    }

    @Override
    public void sendVoucherDetailMenuSms(Integer suborderId) {
        SuborderSRO suborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        
        SmsMessage message = new SmsMessage(suborder.getOrderAddressDetail().getMobile(), "voucherDetailMenuSms");
        send(message);
    }

    @Override
    public void sendOrderLocalCODDeliveredSms(Integer suborderId) {
        SuborderSRO suborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        
        OrderSRO order = orderService.getOrderById(new GetOrderByIdRequest(suborder.getOrderId())).getOrderSRO();
        SmsMessage message = new SmsMessage(order.getAddressDetail().getMobile(), "orderLocalCODDeliveredSms");
        message.addTemplateParam("order", order);
        message.addTemplateParam("deal", catalogService.getCatalog(new GetCatalogByIdRequest((long)suborder.getCatalogId())).getCatalogSRO());
        send(message);
    }

    @Override
    public void sendOrderLocalCODShippedSms(Integer suborderId) {
        SuborderSRO suborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        
        SmsMessage message = new SmsMessage(suborder.getOrderAddressDetail().getMobile(), "orderLocalCODShippedSms");
        message.addTemplateParam("suborder", suborder);
        message.addTemplateParam("catalog", catalogService.getCatalog(new GetCatalogByIdRequest((long)suborder.getCatalogId())).getCatalogSRO());
        send(message);
    }

    @Override
    public void sendNewMobileSubscriberPromoCodeSms(String mobile, PromoCodeSRO promoCode) {
        SmsMessage message = new SmsMessage(mobile, "newMobileSubscriberPromoSms");
        message.addTemplateParam("promoCode", promoCode);
        send(message);
    }

    @Override
    public void sendOrderSummarySms(Integer orderId) {
        
        OrderSRO order = orderService.getOrderById(new GetOrderByIdRequest(orderId)).getOrderSRO();
        SmsMessage message = new SmsMessage(order.getMobile(), "orderSummarySms");
        message.addTemplateParam("order", order);
        send(message);
    
    }

    @Override
    public void sendOrderVerificationCode(String mobile, String verificationCode) {
        SmsMessage message = new SmsMessage(mobile, "orderMobileVerificationSms");
        message.addTemplateParam("verificationCode", verificationCode);
        send(message);
    }

    @Override
    public void sendUnsubscribeSms(String mobile) {
        SmsMessage message = new SmsMessage(mobile, "unsubscribeSms");
        send(message);
    }

    @Override
    public boolean isDNDActive(String mobile) {

        Object obj = memcachedService.get(Constants.MEMCACHE_KEY_PREFIX_MOBILE + mobile);
        boolean dnd = false;
        if (obj != null) {
            dnd = (Boolean) obj;
        } else {
            List<MobileSubscriber> mobileSubscribers = subscriptionsService.getMobileSubscriptions(mobile);
            if (mobileSubscribers.size() > 0) {
                dnd = mobileSubscribers.get(0).isDnd();
                memcachedService.put(Constants.MEMCACHE_KEY_PREFIX_MOBILE + mobile, dnd, Constants.MOBILE_CACHE_TIME_TO_LIVE);
            }
        }
        return dnd;

    }

    @Override
    public String getVoucherText(Integer orderId) {
        OrderSRO order = orderService.getOrderById(new GetOrderByIdRequest(orderId)).getOrderSRO();
        String voucherText = null;
        List<SuborderSRO> suborderList = new ArrayList<SuborderSRO>();
        suborderList.addAll(order.getSuborders());
        SmsMessage message = new SmsMessage(order.getMobile(), "resendVouchersResponse");
        message.addTemplateParam("suborders", suborderList);
        message.addTemplateParam("catalog", catalogService.getCatalog(new GetCatalogByIdRequest((long)suborderList.get(0).getCatalogId())).getCatalogSRO());
        SmsTemplateVO template = CacheManager.getInstance().getCache(SmsTemplateCache.class).getTemplateByName(message.getTemplateName());
        voucherText = template.getBodyTemplate().evaluate(message.getTemplateParams());
        LOG.info("Pull message response for order {}", order.getCode());
        return voucherText;
    }

    @Override
    public void sendProductDeliveredSms(Integer suborderId) {
        SuborderSRO productSuborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        
        SmsMessage message = new SmsMessage(productSuborder.getOrderMobile(), "productSubOrderSMS");
        message.addTemplateParam("name", productSuborder.getCustomerName());
        message.addTemplateParam("orderCode", productSuborder.getOrderCode());
        send(message);
    }

    @Override
    public void sendCartDropoutSms(String mobile, String userName, String catalogText) {
        SmsMessage message = new SmsMessage(mobile, "cartDropoutSms");
        message.addTemplateParam("userName", userName);
        message.addTemplateParam("catalogText", catalogText);
        send(message);
    }

    @Override
    public SmsMessage getProductDeliveredSms(Integer suborderId) {
        SuborderSRO productSuborder = orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO();
        
        SmsMessage message = new SmsMessage(productSuborder.getOrderMobile(), "productSubOrderSMS");
        message.addTemplateParam("name", productSuborder.getCustomerName());
        message.addTemplateParam("orderCode", productSuborder.getOrderCode());
        return message;
    }

    @Override
    public SmsMessage getVoucherSms(List<Integer> suborderIds, int catalogId) {
        List<SuborderSRO> suborders = new ArrayList<SuborderSRO>();
        
        for(Integer suborderId : suborderIds){
            suborders.add(orderService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO());
        }

        if (suborders == null || suborders.isEmpty()) {
            return null;
        }

        SmsMessage message = new SmsMessage(suborders.get(0).getOrderMobile(), "suborderSms");
        CatalogSRO catalogDTO = catalogService.getCatalog(new GetCatalogByIdRequest((long)catalogId)).getCatalogSRO();
        if (catalogDTO == null) {
            return null;
        }
        message.addTemplateParam("catalog", catalogDTO);

        StringBuilder voucherCodes = new StringBuilder();
        StringBuilder merchantPromoCodes = new StringBuilder();
        Date validUpto = null;
        for (SuborderSRO suborder : suborders) {
            voucherCodes.append(suborder.getCode());
            voucherCodes.append(",");

            if (SuborderSRO.Type.DEAL.code().equals(suborder.getCatalogType())) {
                DealSuborderSRO dealSuborder = (DealSuborderSRO) suborder;
                if (dealSuborder.getMerchantPromoCode() != null) {
                    merchantPromoCodes.append(dealSuborder.getMerchantPromoCode());
                    merchantPromoCodes.append(",");
                }
                if (validUpto == null) {
                    validUpto = dealSuborder.getValidUpto();
                }
            } else if (SuborderSRO.Type.GETAWAY.code().equals(suborder.getCatalogType())) {
                GetawaySuborderSRO getawaySuborder = (GetawaySuborderSRO) suborder;
                if (getawaySuborder.getMerchantPromoCode() != null) {
                    merchantPromoCodes.append(getawaySuborder.getMerchantPromoCode());
                    merchantPromoCodes.append(",");
                }
                if (validUpto == null) {
                    validUpto = getawaySuborder.getValidUpto();
                }
            }
        }
        message.addTemplateParam("suborders", voucherCodes.substring(0, voucherCodes.length() - 1));
        message.addTemplateParam("validUpto", validUpto);
        if (merchantPromoCodes.length() > 0) {
            message.addTemplateParam("merchantPromoCodes", merchantPromoCodes.substring(0, merchantPromoCodes.length() - 1));
        }

        return message;
    
    }

    @Override
    public void send(SmsMessage message) {
        boolean isDNDActive = false;
        
        //TODO:ashish- remove this
        try{isDNDActive=
            isDNDActive(message.getMobile());}
        catch(Exception e){
            
        }
        SmsTemplateVO template = CacheManager.getInstance().getCache(SmsTemplateCache.class).getTemplateByName(message.getTemplateName());
        notificationService.sendSMS(message, template, isDNDActive);
    }

    @Override
    public void sendOrderReplacedSummarySms(Integer orderId) {
        OrderSRO order = orderService.getOrderById(new GetOrderByIdRequest(orderId)).getOrderSRO();
        
        SmsMessage message = new SmsMessage(order.getMobile(), "orderReplacedSummarySms");
        message.addTemplateParam("order", order);
        send(message);
    
    }

    @Override
    public void sendPendingCODOrderSms(String mobile, String orderCode) {

        SmsMessage message = new SmsMessage(mobile, "pendingCODOrderSms");
        message.addTemplateParam("orderCode", orderCode);
        send(message);
    

    }

    @Override
    public void sendCODOrderSms(String mobile, String customerName, String orderCode) {
        SmsMessage message = new SmsMessage(mobile, "codOrderSms");
        message.addTemplateParam("orderCode", orderCode);
        message.addTemplateParam("customerName", customerName);
        send(message);
    }

    @Override
    public void sendCsatSms(String mobile) {
        SmsMessage message = new SmsMessage(mobile, "csatSms");
        send(message);
    }

    @Override
    public void sendAffiliateSubscriptionSms(String mobile, List<HashMap<String, String>> listOfMap) {
        for (HashMap<String, String> offer : listOfMap) {
            SmsMessage message = new SmsMessage(mobile, "affiliateSms");
            message.addTemplateParam("promoCode", offer.get("promoCode"));
            message.addTemplateParam("offerName", offer.get("offerName"));
            send(message);
        }

    }

    @Override
    public void sendSubscribtionSms(String mobile, String params, String email) {
        SmsMessage message = new SmsMessage(mobile, "subscribeSms");
        message.addTemplateParam("params", params);
        message.addTemplateParam("email", email);
        send(message);
    }

    @Override
    public void sendAcknowladgeSms(OrderComplaintSRO orderComplaint, String templateName) {
        String name = "";
        String email = "";
        String orderId = "";
        String itemName = "";
        String estShippingDate = "";
        String ticketId = "";
        String mobile = "";
        if(orderComplaint.getCatalogId()!=null){
            CatalogSRO catalogDTO = catalogService.getCatalog(new GetCatalogByIdRequest((long)orderComplaint.getCatalogId())).getCatalogSRO();
            itemName = catalogDTO.getName();
        }
        
        if(orderComplaint.getSubOrderCode()!=null){
            GetShippingInfoForSuborderRequest shpReq = new GetShippingInfoForSuborderRequest();
            shpReq.setSubOrderCode(orderComplaint.getSubOrderCode());
            GetShippingInfoForSuborderResponse shpRes = shippingService.getShippingInfoForSuborder(shpReq);
            ShippingPackageSRO pack = shpRes.getDispatchedShipment();
            if (pack != null) {
                estShippingDate = DateUtils.dateToString(pack.getEstimatedShippingDate(), "dd MMM, yyyy");
            }
        }
        if(StringUtils.isNotEmpty(orderComplaint.getCustomerName())){
            name = orderComplaint.getCustomerName();
        }
        if(StringUtils.isNotEmpty(orderComplaint.getEmail())){
            email = orderComplaint.getEmail();
        }
        if(!(StringUtils.isEmpty(orderComplaint.getOrderCode()) || "undefined".equals(orderComplaint.getOrderCode()))){
            orderId = orderComplaint.getOrderCode();
        }
        if(StringUtils.isNotEmpty(orderComplaint.getTicketId())){
            ticketId = orderComplaint.getTicketId();
        }
        if(StringUtils.isNotEmpty(orderComplaint.getMobile())){
            mobile = orderComplaint.getMobile();
        }
        SmsMessage message = new SmsMessage(orderComplaint.getMobile(), templateName);
        message.addTemplateParam("name", name);
        message.addTemplateParam("email", email);
        message.addTemplateParam("orderId", orderId);
        message.addTemplateParam("itemName", itemName);
        message.addTemplateParam("estShippingDate", estShippingDate);
        message.addTemplateParam("ticketId", ticketId);
        message.addTemplateParam("mobile", mobile);
        send(message);
    }
    
    
    private static String NEFT_UPDATED_SMS_TEMPLATE = "NEFTUpdateSms";
    
    public void sendNEFTDetailsUpdatedSms(EnhancedUserNEFTDetailsSRO  enhancedUserNEFTDetails, String mobileNum){
            SmsMessage message = new SmsMessage(mobileNum, NEFT_UPDATED_SMS_TEMPLATE);
            message.addTemplateParam("neftDetails", enhancedUserNEFTDetails);
            send(message);
    }
}
