package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.catalog.base.model.GetCatalogByIdRequest;
import com.snapdeal.catalog.base.model.GetCatalogResponse;
import com.snapdeal.catalog.base.model.GetServiceDealByIdRequest;
import com.snapdeal.catalog.base.model.GetServiceDealResponse;
import com.snapdeal.catalog.base.model.GetZoneByIdRequest;
import com.snapdeal.catalog.base.sro.CatalogSRO;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.catalog.client.service.ICatalogClientService;
import com.snapdeal.core.dto.BulkUploadResultDTO;
import com.snapdeal.core.dto.ProductMultiVendorMappingResultDTO;
import com.snapdeal.core.dto.feedback.CancelledOrderFeedbackDO;
import com.snapdeal.core.entity.Audit;
import com.snapdeal.core.entity.GetFeatured;
import com.snapdeal.core.sro.order.PromoCodeSRO;
import com.snapdeal.core.sro.serviceDeal.ServiceDealSRO;
import com.snapdeal.locality.client.service.ILocalityClientService;
import com.snapdeal.oms.base.model.FullfillAlternateSuborderRequest;
import com.snapdeal.oms.base.model.GetOrderByIdRequest;
import com.snapdeal.oms.base.model.GetOrderResponse;
import com.snapdeal.oms.base.model.GetSuborderByIdRequest;
import com.snapdeal.oms.base.model.GetSuborderResponse;
import com.snapdeal.oms.base.sro.order.OrderSRO;
import com.snapdeal.oms.base.sro.order.SuborderSRO;
import com.snapdeal.oms.services.IOrderClientService;
import com.snapdeal.serviceDeal.client.service.IServiceDealClientService;
import com.snapdeal.ums.aspect.annotation.EnableMonitoring;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.sro.email.AuditSRO;
import com.snapdeal.ums.core.sro.email.BulkUploadResultSRO;
import com.snapdeal.ums.core.sro.email.CancelledOrderFeedbackDOSRO;
import com.snapdeal.ums.core.sro.email.CorporateSRO;
import com.snapdeal.ums.core.sro.email.CustomerQuerySRO;
import com.snapdeal.ums.core.sro.email.EmailMessageSRO;
import com.snapdeal.ums.core.sro.email.FeaturedSRO;
import com.snapdeal.ums.core.sro.email.OrderCancellationEmailSRO;
import com.snapdeal.ums.core.sro.email.ProductMultiVendorMappingResultSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.email.ext.email.*;
import com.snapdeal.ums.email.ext.email.SendAutoCaptureStatusEmailRequest.MapEntryUtil;
import com.snapdeal.ums.server.services.IEmailService;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;

@Service("umsEmailService")
public class EmailServiceImpl implements IEmailService {

    private static final Logger       LOG = LoggerFactory.getLogger(EmailServiceImpl.class);

    @Autowired
    IOrderClientService               orderClientService;
    @Autowired
    private IEmailServiceInternal     emailServiceInternal;
    @Autowired
    private IUMSConvertorService      umsConvertorService;
    @Autowired
    private ILocalityClientService    localityClientService;

    @Autowired
    private ICatalogClientService     catalogClientService;

    @Autowired
    private IServiceDealClientService serviceDealClientService;

    @Override
    public SendResponse send(SendRequest request) {
        SendResponse response = new SendResponse();

        EmailMessageSRO messageSRO = request.getMessage();
        EmailMessage message = umsConvertorService.getEmailMessageEntityFromSRO(messageSRO);
        if (message != null) {
            emailServiceInternal.send(message);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Email message null");
            LOG.info("Email message null");
        }

        return response;
    }

    @Deprecated
    @Override
    public SendOrderSummaryEmailResponse sendOrderSummaryEmail(SendOrderSummaryEmailRequest request) {
        SendOrderSummaryEmailResponse response = new SendOrderSummaryEmailResponse();
        OrderSRO orderSRO = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        if (orderSRO != null) {
            emailServiceInternal.sendOrderSummaryEmail(orderSRO);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("order SRO  null");
            LOG.info("order SRO  null");
        }

        return response;
    }

    @Deprecated
    @Override
    public SendOrderRetryEmailResponse sendOrderRetryEmail(SendOrderRetryEmailRequest request) {
        SendOrderRetryEmailResponse response = new SendOrderRetryEmailResponse();
        OrderSRO orderSRO = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        String retryUrl = request.getRetryUrl();
        if (orderSRO != null) {
            emailServiceInternal.sendOrderRetryEmail(orderSRO, retryUrl);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("order SRO  null");
            LOG.info("order SRO  null");
        }

        return response;
    }

    /**
     * Unused
     */
    @Deprecated
    @Override
    public SendSampleVoucherEmailResponse sendSampleVoucherEmail(SendSampleVoucherEmailRequest request) {
        SendSampleVoucherEmailResponse response = new SendSampleVoucherEmailResponse();
        String email = request.getEmail();
        SuborderSRO suborderSRO = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        Integer zoneId = request.getZone();
        ZoneSRO zoneSRO = null;
        //Naveen localityClientService.getZoneById();
        String contentPath = request.getContentPath();
        if (StringUtils.isNotEmpty(email) && (suborderSRO != null) && (zoneSRO != null)) {
            emailServiceInternal.sendSampleVoucherEmail(suborderSRO, zoneSRO, email, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Email :" + email + " SuborderSO :" + suborderSRO + " zoneSRO " + zoneSRO);
        }
        return response;
    }

    @Override
    public SendConfirmationEmailResponse sendConfirmationEmail(SendConfirmationEmailRequest request) {
        SendConfirmationEmailResponse response = new SendConfirmationEmailResponse();
        String confirmationLink = request.getConfirmationLink();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String email = request.getEmail();
        if ((StringUtils.isNotEmpty(email))) {
            emailServiceInternal.sendConfirmationEmail(email, contextPath, contentPath, confirmationLink);
            LOG.info("Successfully send confirmation mail for request: "+request.toString());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath);
            LOG.info("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath);
        }
        return response;
    }

    @Deprecated
    @Override
    public SendNewConfirmationEmailResponse sendNewConfirmationEmail(SendNewConfirmationEmailRequest request) {
        SendNewConfirmationEmailResponse response = new SendNewConfirmationEmailResponse();
        String confirmationLink = request.getConfirmationLink();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String email = request.getEmail();
        Integer zoneId = request.getZoneId();
        if ((StringUtils.isNotEmpty(email))) {

            emailServiceInternal.sendNewConfirmationEmail(email, zoneId, contextPath, contentPath, confirmationLink);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath);
            LOG.info("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath);
        }
        return response;
    }

    @Deprecated
    @Override
    public SendAffiliateConfirmationEmailResponse sendAffiliateConfirmationEmail(SendAffiliateConfirmationEmailRequest request) {
        SendAffiliateConfirmationEmailResponse response = new SendAffiliateConfirmationEmailResponse();
        String confirmationLink = request.getConfirmationLink();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String email = request.getEmail();
        if ((StringUtils.isNotEmpty(email))) {

            emailServiceInternal.sendAffiliateConfirmationEmail(email, contextPath, contentPath, confirmationLink);
        } else {
            response.setSuccessful(false);
            response.setMessage("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath);
            LOG.info("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath);
        }
        return response;
    }

    @Deprecated
    @Override
    public Send3rdPartyConfirmationEmailResponse send3rdPartyConfirmationEmail(Send3rdPartyConfirmationEmailRequest request) {
        Send3rdPartyConfirmationEmailResponse response = new Send3rdPartyConfirmationEmailResponse();
        String confirmationLink = request.getConfirmationLink();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String email = request.getEmail();
        String source = request.getSource();
        if ((StringUtils.isNotEmpty(email)) && (StringUtils.isNotEmpty(source))) {

            emailServiceInternal.send3rdPartyConfirmationEmail(email, contextPath, contentPath, confirmationLink, source);
        } else {
            response.setSuccessful(false);
            response.setMessage("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath + " source :"
                    + source);
            LOG.info("Email :" + email + " ConfirmationLink :" + confirmationLink + " contentPath  " + contentPath + " contextPath " + contextPath + " source :" + source);
        }
        return response;
    }

    @EnableMonitoring
    @Override
    public SendForgotPasswordEmailResponse sendForgotPasswordEmail(SendForgotPasswordEmailRequest request) {
        SendForgotPasswordEmailResponse response = new SendForgotPasswordEmailResponse();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String forgotPasswordLink = request.getForgotPasswordLink();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        if ((StringUtils.isNotEmpty(forgotPasswordLink)) && (user != null)) {
            emailServiceInternal.sendForgotPasswordEmail(user, contextPath, contentPath, forgotPasswordLink);
            LOG.info("Successfully send forgot password mail for request: "+ request.toString());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("user :" + user + " contextPath :" + contextPath + " contentPath  " + contentPath + " forgotPasswordLink " + forgotPasswordLink);
            LOG.info("user :" + user.getEmail() + " contextPath :" + contextPath + " contentPath  " + contentPath + " forgotPasswordLink " + forgotPasswordLink);
        }
        return response;
    }

    /* @Override
     public SendMovieVoucherEmailResponse sendMovieVoucherEmail(SendMovieVoucherEmailRequest request) {
         SendMovieVoucherEmailResponse response = new SendMovieVoucherEmailResponse();
         String contentPath = request.getContentPath();
         String contextPath = request.getContextPath();
         String email = request.getEmail();
         PartnerPromoCodeSRO promoCodeSRO = request.getPromoCode();

         if ((StringUtils.isNotEmpty(email)) && (promoCodeSRO != null)) {
             emailServiceInternal.sendMovieVoucherEmail(email, promoCodeSRO, contextPath, contentPath);
             response.setSuccessful(true);
         } else {
             response.setSuccessful(false);
             response.setMessage(" contextPath :" + contextPath + " contentPath  " + contentPath + " email " + email + " promoCodeSRO " + promoCodeSRO);

         }
         return response;
     }
    */
  
    @Override
    public SendPromoCodeEmailResponse sendPromoCodeEmail(SendPromoCodeEmailRequest request) {
        SendPromoCodeEmailResponse response = new SendPromoCodeEmailResponse();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String email = request.getEmail();
        PromoCodeSRO promoCodeSRO = request.getPrmCode();

        if ((StringUtils.isNotEmpty(email)) && (promoCodeSRO != null)) {
            emailServiceInternal.sendPromoCodeEmail(email, promoCodeSRO, contextPath, contentPath);
        } else {
            response.setSuccessful(false);
            response.setMessage(" contextPath :" + contextPath + " contentPath  " + contentPath + " email " + email + " promoCodeSRO " + promoCodeSRO);
            LOG.info(" contextPath :" + contextPath + " contentPath  " + contentPath + " email " + email + " promoCodeSRO " + promoCodeSRO);
        }
        return response;
    }

    //    @Override
    //    public SendAffiliatePromoCodeEmailResponse sendAffiliatePromoCodeEmail(SendAffiliatePromoCodeEmailRequest request) {
    //        SendAffiliatePromoCodeEmailResponse response = new SendAffiliatePromoCodeEmailResponse();
    //        String contentPath = request.getContentPath();
    //        String contextPath = request.getContextPath();
    //        String email = request.getEmail();
    //        PromoCodeSRO promoCodeSRO = request.getPrmCode();
    //        String promoCodeTypeName = request.getPromoCodeTypeName();
    //        AffiliateSRO affiliateSRO = request.getAffiliate();
    //        Affiliate affiliate = umsConvertorService.getAffiliateEntityFromSRO(affiliateSRO);
    //
    //        if ((StringUtils.isNotEmpty(email)) && (promoCodeSRO != null) && (StringUtils.isNotEmpty(promoCodeTypeName)) && (affiliateSRO != null)) {
    //            emailServiceInternal.sendAffiliatePromoCodeEmail(email, promoCodeSRO, affiliate, promoCodeTypeName, contextPath, contentPath);
    //
    //            response.setSuccessful(true);
    //        } else {
    //            response.setSuccessful(false);
    //            response.setMessage("Invalid Request: contextPath :" + contextPath + " contentPath  " + contentPath + " email " + email + " promoCodeSRO " + promoCodeSRO
    //                    + " affiliate " + affiliate + "promoCodeTypeName " + promoCodeTypeName);
    //
    //        }
    //        return response;
    //    }

    @Override
    public SendSurveyPromoCodeEmailResponse sendSurveyPromoCodeEmail(SendSurveyPromoCodeEmailRequest request) {
        SendSurveyPromoCodeEmailResponse response = new SendSurveyPromoCodeEmailResponse();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String email = request.getEmail();
        PromoCodeSRO promoCodeSRO = request.getPrmCode();
        if ((StringUtils.isNotEmpty(email)) && (promoCodeSRO != null)) {
            emailServiceInternal.sendSurveyPromoCodeEmail(email, promoCodeSRO, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid Request: contextPath :" + contextPath + " contentPath  " + contentPath + " email " + email + " promoCodeSRO " + promoCodeSRO);
            LOG.info("Invalid Request: contextPath :" + contextPath + " contentPath  " + contentPath + " email " + email + " promoCodeSRO " + promoCodeSRO);
        }
        return response;
    }

    @Deprecated
    @Override
    public SendCorporateEmailResponse sendCorporateEmail(SendCorporateEmailRequest request) {
        SendCorporateEmailResponse response = new SendCorporateEmailResponse();
        CorporateSRO sro = request.getCorporate();
        if (sro != null) {
            emailServiceInternal.sendCorporateEmail(umsConvertorService.getCorporateEmailEntityFromSRO(sro));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Corporate sro null " + sro);
            LOG.info("Corporate sro null " + sro);
        }

        return response;
    }

  
    @Override
    public SendCorporatebuyEmailResponse sendCorporatebuyEmail(SendCorporatebuyEmailRequest request) {
        SendCorporatebuyEmailResponse response = new SendCorporatebuyEmailResponse();
        Object corporate = request.getCorporate();

        if (corporate != null) {
            emailServiceInternal.sendCorporatebuyEmail(corporate);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Corporate object null " + corporate);
            LOG.info("Corporate object null " + corporate);
        }

        return response;
    }

    @Deprecated
    @Override
    public SendFeaturedEmailResponse sendFeaturedEmail(SendFeaturedEmailRequest request) {
        SendFeaturedEmailResponse response = new SendFeaturedEmailResponse();
        FeaturedSRO sro = request.getFeatured();

        if (sro != null) {
            emailServiceInternal.sendFeaturedEmail(umsConvertorService.getFeaturedEntityFromSRO(sro));
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Featured SRO null ");
            LOG.info("Featured SRO null ");
        }

        return response;

    }

    @Deprecated
    @Override
    public SendCustomerQueryEmailResponse sendCustomerQueryEmail(SendCustomerQueryEmailRequest request) {
        SendCustomerQueryEmailResponse response = new SendCustomerQueryEmailResponse();
        CustomerQuerySRO customerQuerySRO = request.getCustomerQuery();
        if (customerQuerySRO != null) {
            emailServiceInternal.sendCustomerQueryEmail(umsConvertorService.getCustomerQueryEntityFromSRO(customerQuerySRO));
            response.setSuccessful(true);
        } else {
            response.setMessage("Customer Query SRO null" + customerQuerySRO);
            response.setSuccessful(false);
            LOG.info("Customer Query SRO null" + customerQuerySRO);
        }
        return response;
    }

    @Override
    public SendCustomerFeedbackEmailResponse sendCustomerFeedbackEmail(SendCustomerFeedbackEmailRequest request) {
        SendCustomerFeedbackEmailResponse response = new SendCustomerFeedbackEmailResponse();
        String category = request.getCategory();
        String city = request.getCity();
        String email = request.getEmail();
        String name = request.getName();
        String message = request.getMessage();
        if (StringUtils.isNotEmpty(category) && StringUtils.isNotEmpty(city) && StringUtils.isNotEmpty(email) && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(message)) {
            emailServiceInternal.sendCustomerFeedbackEmail(name, email, city, category, message);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request : category =" + category + " city =" + city + " email =" + email + " name =" + name + " message =" + message);
            response.setSuccessful(false);
            LOG.info("Invalid request : category =" + category + " city =" + city + " email =" + email + " name =" + name + " message =" + message);
        }

        return response;
    }

   
    @Override
    public SendInviteEmailResponse sendInviteEmail(SendInviteEmailRequest request) {
        SendInviteEmailResponse response = new SendInviteEmailResponse();
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        String refererEmail = request.getRefererEmail();
        String userName = request.getUserName();
        String to = request.getTo();
        String from = request.getFrom();
        String trackingUID = request.getTrackingUID();

        if (StringUtils.isNotEmpty(refererEmail) && StringUtils.isNotEmpty(userName) && StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(from)
                && StringUtils.isNotEmpty(trackingUID)) {
            emailServiceInternal.sendInviteEmail(refererEmail, contextPath, contentPath, userName, to, from, trackingUID);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : contentPath =" + contentPath + " contextPath =" + contextPath + " referrerEmail =" + refererEmail + " userName =" + userName
                    + " to =" + to + " from =" + from + " trackingUID =" + trackingUID);
            LOG.info("Invalid request : contentPath =" + contentPath + " contextPath =" + contextPath + " referrerEmail =" + refererEmail + " userName =" + userName + " to =" + to
                    + " from =" + from + " trackingUID =" + trackingUID);
        }

        return response;
    }

    @Deprecated
    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse sendShareDealEmail(com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest request)
            throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse();
        String from = request.getFrom();
        String to = request.getTo();
        String recipientEmail = request.getRecipientEmail();
        GetServiceDealResponse dealResponse = serviceDealClientService.getDealById(new GetServiceDealByIdRequest(request.getDealId()));
        if (dealResponse != null && StringUtils.isNotEmpty(from) && StringUtils.isNotEmpty(to) && StringUtils.isNotEmpty(recipientEmail)) {
            ServiceDealSRO deal = dealResponse.getServiceDealSRO();
            emailServiceInternal.sendShareDealEmail(to, from, recipientEmail, deal);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : deal =" + request.getDealId() + " from =" + from + " to =" + to + " recepient email =" + recipientEmail);
            LOG.info("Invalid request : deal =" + request.getDealId() + " from =" + from + " to =" + to + " recepient email =" + recipientEmail);
        }
        return response;
    }

    @Override
    public SendShareDealEmailResponse sendShareDealEmail(SendShareDealEmailRequest request) throws TransportException {
        SendShareDealEmailResponse response = new SendShareDealEmailResponse();
        com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest sendShareDealEmailRequest = new com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailRequest(
                request.getTo(), request.getFrom(), request.getRecipientEmail(), (long) request.getDeal());
        com.snapdeal.ums.email.ext.v1.email.SendShareDealEmailResponse sendShareDealEmailResponse = sendShareDealEmail(sendShareDealEmailRequest);
        response.setSuccessful(sendShareDealEmailResponse.isSuccessful());
        if (sendShareDealEmailResponse.getMessage() != null)
            response.setMessage(sendShareDealEmailResponse.getMessage());

        return response;
    }

    //TODO:Naveen
    @Override
    public SendGroupBuyEmailResponse sendGroupBuyEmail(SendGroupBuyEmailRequest request) {
        SendGroupBuyEmailResponse response = new SendGroupBuyEmailResponse();
        String emailTemplate = request.getEmailTemplate();
        Object groupDeal = request.getGroupDeal();
        String to = request.getTo();

        emailServiceInternal.sendGroupBuyEmail(groupDeal, emailTemplate, to);
        response.setSuccessful(true);

        return response;
    }

    @Deprecated
    @Override
    public SendDailyMerchantEmailResponse sendDailyMerchantEmail(SendDailyMerchantEmailRequest request) {
        SendDailyMerchantEmailResponse response = new SendDailyMerchantEmailResponse();
        String emailIds = request.getEmailIds();
        List<SuborderSRO> suborders = new ArrayList<SuborderSRO>();
        for (Integer suborderId : request.getSuborderIds()) {
            suborders.add(orderClientService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO());
        }
        emailServiceInternal.sendDailyMerchantEmail(emailIds, suborders);
        response.setSuccessful(true);

        return response;
    }

    @Override
    public SendCodOrderSubmissionEmailResponse sendCodOrderSubmissionEmail(SendCodOrderSubmissionEmailRequest request) {
        SendCodOrderSubmissionEmailResponse response = new SendCodOrderSubmissionEmailResponse();
        String http = request.getContextPath();
        String resources = request.getContentPath();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        emailServiceInternal.sendCodOrderSubmissionEmail(suborder, http, resources);
        response.setSuccessful(true);
        return response;
    }

    
    @Override
    public SendCodOrderDispatchEmailResponse sendCodOrderDispatchEmail(SendCodOrderDispatchEmailRequest request) {
        SendCodOrderDispatchEmailResponse response = new SendCodOrderDispatchEmailResponse();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        String http = request.getContextPath();
        String resources = request.getContentPath();
        if ((suborder != null) && StringUtils.isNotEmpty(http) && StringUtils.isNotEmpty(resources)) {
            emailServiceInternal.sendCodOrderDispatchEmail(suborder, http, resources);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : suborderSRO =" + suborder + " http =" + http + " resources =" + resources);
            LOG.info("Invalid request : suborderSRO =" + suborder + " http =" + http + " resources =" + resources);
        }
        return response;
    }

    @Override
    public SendBdayCashBackEmailResponse sendBdayCashBackEmail(SendBdayCashBackEmailRequest request) {
        SendBdayCashBackEmailResponse response = new SendBdayCashBackEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        int cashBackAmount = request.getCashBackAmount();
        boolean newUser = request.getNewUser();
        // if ((userSRO != null) && cashBackAmount!=0 && newUser!=false){
        if ((userSRO != null)) {
            emailServiceInternal.sendBdayCashBackEmail(user, cashBackAmount, newUser);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : User=" + user + " cashBackAmount=" + cashBackAmount + " newUser=" + newUser);
            LOG.info("Invalid request : User=" + user + " cashBackAmount=" + cashBackAmount + " newUser=" + newUser);
        }
        return response;
    }

    @Override
    public SendCashBackOfferEmailResponse sendCashBackOfferEmail(SendCashBackOfferEmailRequest request) {
        SendCashBackOfferEmailResponse response = new SendCashBackOfferEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        int cashBackAmount = request.getCashBackAmount();
        boolean newUser = request.getNewUser();
        String confirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null) {
            emailServiceInternal.sendCashBackOfferEmail(user, cashBackAmount, newUser, confirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : User=" + user + " cashBankAccount=" + cashBackAmount + " newUser=" + newUser + " ConfirmationLink=" + confirmationLink
                    + " contextpath=" + contentPath + " contentpath=" + contentPath);
            LOG.info("Invalid request : User=" + user + " cashBankAccount=" + cashBackAmount + " newUser=" + newUser + " ConfirmationLink=" + confirmationLink + " contextpath="
                    + contentPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendSdCashBackRewardOnPurchaseEmailResponse sendSdCashBackRewardOnPurchaseEmail(SendSdCashBackRewardOnPurchaseEmailRequest request) {
        SendSdCashBackRewardOnPurchaseEmailResponse response = new SendSdCashBackRewardOnPurchaseEmailResponse();
        UserSRO user = request.getUser();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        int sdCash = request.getSdCash();
        Long purchase = request.getPurchase();
        boolean newUser = request.getNewUser();
        String confirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null) {
            emailServiceInternal.sendSdCashBackRewardOnPurchaseEmail(order, user, sdCash, purchase, newUser, confirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order =" + order + "user= " + user + "sdcas= " + sdCash + "purchase= " + purchase + " newUser= " + newUser
                    + " ConfirmationLink= " + confirmationLink + " contextPath= " + contextPath + " contentpath= " + contentPath);
            LOG.info("Invalid request: order =" + order + "user= " + user + "sdcas= " + sdCash + "purchase= " + purchase + " newUser= " + newUser + " ConfirmationLink= "
                    + confirmationLink + " contextPath= " + contextPath + " contentpath= " + contentPath);
        }
        return response;
    }

    @Override
    public SendCustomerCareEmailResponse sendCustomerCareEmail(SendCustomerCareEmailRequest request) throws TransportException {
        SendCustomerCareEmailResponse response = new SendCustomerCareEmailResponse();
        com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest sendCustomerCareEmailRequest = new com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest(
                request.getUser(), (long) request.getDeal(), request.getContextPath(), request.getContentPath());
        com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse sendCustomerCareEmailResponse = sendCustomerCareEmailv1(sendCustomerCareEmailRequest);
        response.setSuccessful(sendCustomerCareEmailResponse.isSuccessful());
        if (sendCustomerCareEmailResponse.getMessage() != null)
            response.setMessage(sendCustomerCareEmailResponse.getMessage());
        return response;
    }

    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse sendCustomerCareEmailv1(com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailRequest request)
            throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendCustomerCareEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        GetServiceDealResponse getDealByIdResponse = serviceDealClientService.getDealById(new GetServiceDealByIdRequest(request.getDealId()));
        if (getDealByIdResponse != null && getDealByIdResponse.getServiceDealSRO() != null && user != null) {
            ServiceDealSRO deal = getDealByIdResponse.getServiceDealSRO();
            emailServiceInternal.sendCustomerCareEmail(user, deal, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : user=" + user + " Deal " + request.getDealId() + " contextpath=" + contentPath + " contentpath=" + contentPath);
            LOG.info("Invalid request : user=" + user + " Deal " + request.getDealId() + " contextpath=" + contentPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendOrderSubmissionEmailResponse sendOrderSubmissionEmail(SendOrderSubmissionEmailRequest request) {
        SendOrderSubmissionEmailResponse response = new SendOrderSubmissionEmailResponse();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        if (suborder != null) {
            emailServiceInternal.sendOrderSubmissionEmail(suborder, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : suborder= " + suborder + " contextpath=" + contextPath + " contentpath=" + contentPath);
            LOG.info("Invalid request : suborder= " + suborder + " contextpath=" + contextPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendReferralBenefitEmailResponse sendReferralBenefitEmail(SendReferralBenefitEmailRequest request) {
        SendReferralBenefitEmailResponse response = new SendReferralBenefitEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        int sdCashValue = request.getSdCashValue();
        int friendsReferred = request.getFriendsReferred();
        int noOfConversions = request.getNoOfConversions();
        boolean newUser = request.getNewUser();
        List<String> referredUserEmails = request.getReferredUserEmails();
        String confirmationPath = request.getConfirmationPath();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null && referredUserEmails != null) {
            emailServiceInternal.sendReferralBenefitEmail(user, sdCashValue, friendsReferred, noOfConversions, newUser, confirmationPath, referredUserEmails, contextPath,
                    contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: user= " + user + " sdcashvalue=" + sdCashValue + " friendsReferred=" + friendsReferred + " noOfConversions=" + noOfConversions
                    + " newuser" + newUser + " confirmationPath=" + confirmationPath + " contextPath=" + contextPath + " contentPath=" + contentPath);
            LOG.info("Invalid request: user= " + user + " sdcashvalue=" + sdCashValue + " friendsReferred=" + friendsReferred + " noOfConversions=" + noOfConversions + " newuser"
                    + newUser + " confirmationPath=" + confirmationPath + " contextPath=" + contextPath + " contentPath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendNewSubscriberReferralOneTimeEmailResponse sendNewSubscriberReferralOneTimeEmail(SendNewSubscriberReferralOneTimeEmailRequest request) {
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        List<String> referredUserEmails = request.getReferredUserEmails();
        String confirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        SendNewSubscriberReferralOneTimeEmailResponse response = new SendNewSubscriberReferralOneTimeEmailResponse();
        if (user != null && referredUserEmails != null) {
            emailServiceInternal.sendNewSubscriberReferralOneTimeEmail(user, confirmationLink, referredUserEmails, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: user=" + user + " confirmationLink=" + confirmationLink + " contextPath=" + contextPath + " contentPath" + contentPath);
            LOG.info("Invalid request: user=" + user + " confirmationLink=" + confirmationLink + " contextPath=" + contextPath + " contentPath" + contentPath);
        }
        return response;
    }

    @Override
    public SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(SendMobileOrderSubmissionEmailRequest request) throws TransportException {
        SendMobileOrderSubmissionEmailResponse response = new SendMobileOrderSubmissionEmailResponse();

        com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest sendMobileOrderSubmissionEmailRequest = new com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest(
                request.getOrderId(), (long) request.getDeal(), request.getContextPath(), request.getContentPath());
        com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmailResponse = sendMobileOrderSubmissionEmail(sendMobileOrderSubmissionEmailRequest);
        response.setSuccessful(sendMobileOrderSubmissionEmailResponse.isSuccessful());
        if (sendMobileOrderSubmissionEmailResponse.getMessage() != null)
            response.setMessage(sendMobileOrderSubmissionEmailResponse.getMessage());

        return response;
    }

    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse sendMobileOrderSubmissionEmail(
            com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailRequest request) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendMobileOrderSubmissionEmailResponse();
        GetOrderResponse getOrderByIdResponse = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId()));
        GetServiceDealResponse dealResponse = serviceDealClientService.getDealById(new GetServiceDealByIdRequest(request.getDealId()));
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (dealResponse !=null && getOrderByIdResponse !=null && dealResponse.getServiceDealSRO() != null  && getOrderByIdResponse.getOrderSRO() !=null) {
            emailServiceInternal.sendMobileOrderSubmissionEmail(getOrderByIdResponse.getOrderSRO(), dealResponse.getServiceDealSRO(), contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request order=" + request.getOrderId() + " deal=" + request.getDealId() + "contextPath=" + contextPath + " contentpath=" + contentPath);
            LOG.info("Invalid request order=" + request.getOrderId() + " deal=" + request.getDealId() + "contextPath=" + contextPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(
            com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest request) throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse();
        GetOrderResponse getorderByIdResponse = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId()));
        GetServiceDealResponse getDealByIdResponse = serviceDealClientService.getDealById(new GetServiceDealByIdRequest(request.getDealId()));

        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (getDealByIdResponse != null && getorderByIdResponse != null && getDealByIdResponse.getServiceDealSRO() != null && getorderByIdResponse.getOrderSRO()!=null) {
            emailServiceInternal.sendMobileCustomerCareEmail(getorderByIdResponse.getOrderSRO(), getDealByIdResponse.getServiceDealSRO(), contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request order=" + request.getOrderId() + " deal=" + request.getDealId() + "contextPath=" + contextPath + " contentpath=" + contentPath);
            LOG.info("Invalid request order=" + request.getOrderId() + " deal=" + request.getDealId() + "contextPath=" + contextPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmail(SendMobileCustomerCareEmailRequest request) throws TransportException {
        SendMobileCustomerCareEmailResponse response = new SendMobileCustomerCareEmailResponse();

        com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest sendMobileCustomerCareEmailRequest = new com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailRequest(
                request.getOrderId(), (long) request.getDeal(), request.getContextPath(), request.getContextPath());
        com.snapdeal.ums.email.ext.v1.email.SendMobileCustomerCareEmailResponse sendMobileCustomerCareEmailResponse = sendMobileCustomerCareEmail(sendMobileCustomerCareEmailRequest);
        response.setSuccessful(sendMobileCustomerCareEmailResponse.isSuccessful());
        if (sendMobileCustomerCareEmailResponse.getMessage() != null)
            response.setMessage(sendMobileCustomerCareEmailResponse.getMessage());

        return response;
    }

    @Override
    public SendUSConfirmationEmailResponse sendUSConfirmationEmail(SendUSConfirmationEmailRequest request) {
        SendUSConfirmationEmailResponse response = new SendUSConfirmationEmailResponse();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        String email = request.getEmail();
        if (StringUtils.isNotEmpty(email)) {
            emailServiceInternal.sendUSConfirmationEmail(email, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request : email= " + email + " contextpath= " + contextPath + " contentpath= " + contentPath);
            LOG.info("Invalid request : email= " + email + " contextpath= " + contextPath + " contentpath= " + contentPath);
        }
        return response;
    }

    @Override
    public SendGeneralSDCashBackEmailResponse sendGeneralSDCashBackEmail(SendGeneralSDCashBackEmailRequest request) {
        SendGeneralSDCashBackEmailResponse response = new SendGeneralSDCashBackEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        int sdCashValue = request.getSdCashValue();
        boolean newUser = request.getNewUser();
        String conirmationLink = request.getConirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null) {
            emailServiceInternal.sendGeneralSDCashBackEmail(user, sdCashValue, newUser, conirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request user=" + user + " sdcashvalue=" + sdCashValue + " confirmation link=" + conirmationLink + " contextpath= " + contextPath
                    + " contentpath=" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request user=" + user + " sdcashvalue=" + sdCashValue + " confirmation link=" + conirmationLink + " contextpath= " + contextPath + " contentpath="
                    + contentPath);
        }
        return response;
    }

    @Override
    public SendOrderRefundEmailResponse sendOrderRefundEmail(SendOrderRefundEmailRequest request) {
        SendOrderRefundEmailResponse response = new SendOrderRefundEmailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        List<String> cancelledProducts = request.getCancelledProducts();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        String shippingCode = request.getShippingMethodCode();
        if (order != null && cancelledProducts != null) {
            emailServiceInternal.sendOrderRefundEmail(order, shippingCode, cancelledProducts, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: Order=" + order + " contextpath=" + contextPath + " contentpath=" + contentPath);
            LOG.info("Invalid request: Order=" + order + " contextpath=" + contextPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendRefundAndSDCashBackEmailResponse sendRefundAndSDCashBackEmail(SendRefundAndSDCashBackEmailRequest request) {
        SendRefundAndSDCashBackEmailResponse response = new SendRefundAndSDCashBackEmailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        List<String> cancelledProducts = request.getCancelledProducts();
        int sdCashRefund = request.getSdCashRefund();
        boolean newuser = request.getNewUser();
        String conirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        String shippingCode = request.getShippingMethodCode();
        if (user != null && !cancelledProducts.isEmpty() && order != null) {
            emailServiceInternal.sendRefundAndSDCashBackEmail(user, order, shippingCode, cancelledProducts, sdCashRefund, newuser, conirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: user" + user + " order=" + order + " cancelledproducts=" + cancelledProducts + " sdcashrefund=" + sdCashRefund + "newuser="
                    + newuser + " contextpath=" + contextPath + " contnentpath=" + contentPath + "confirmationmlink=" + conirmationLink);
            response.setSuccessful(false);
            LOG.info("Invalid request: user" + user + " order=" + order + " cancelledproducts=" + cancelledProducts + " sdcashrefund=" + sdCashRefund + "newuser=" + newuser
                    + " contextpath=" + contextPath + " contnentpath=" + contentPath + "confirmationmlink=" + conirmationLink);
        }
        return response;
    }

    @Override
    public SendVendorUserCreationEmailResponse sendVendorUserCreationEmail(SendVendorUserCreationEmailRequest request) {
        SendVendorUserCreationEmailResponse response = new SendVendorUserCreationEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null) {
            emailServiceInternal.sendVendorUserCreationEmail(user, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: user=" + user + "contextpath= " + contextPath + "contentpath=" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request: user=" + user + "contextpath= " + contextPath + "contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendCodOrderSubmissionEmailProductResponse sendCodOrderSubmissionEmailProduct(SendCodOrderSubmissionEmailProductRequest request) {
        SendCodOrderSubmissionEmailProductResponse response = new SendCodOrderSubmissionEmailProductResponse();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        String http = request.getcontextPath();
        String resources = request.getcontentPath();
        if (suborder != null && StringUtils.isNotEmpty(resources) && StringUtils.isNotEmpty(http)) {
            emailServiceInternal.sendCodOrderSubmissionEmailProduct(suborder, http, resources);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: suborder=" + suborder + " http=" + http + " resources" + resources);
            response.setSuccessful(false);
            LOG.info("Invalid request: suborder=" + suborder + " http=" + http + " resources" + resources);
        }
        return response;
    }

    @Override
    public SendAuditMailResponse sendAuditMail(SendAuditMailRequest request) {
        SendAuditMailResponse response = new SendAuditMailResponse();
        AuditSRO auditSRO = request.getAudit();
        Audit audit = umsConvertorService.getAuditfromSRO(auditSRO);
        String useremailId = request.getUseremailId();
        if (audit != null && StringUtils.isNotEmpty(useremailId)) {
            emailServiceInternal.sendAuditMail(audit, useremailId);
            response.setSuccessful(true);
        } else {
            response.setMessage("invalid request: audit=" + audit + " useremailid=" + useremailId);
            response.setSuccessful(false);
            LOG.info("invalid request: audit=" + audit + " useremailid=" + useremailId);
        }
        return response;
    }

    @Override
    public SendFirstSubscriptionEmailResponse sendFirstSubscriptionEmail(SendFirstSubscriptionEmailRequest request) {
        SendFirstSubscriptionEmailResponse response = new SendFirstSubscriptionEmailResponse();
        String email = request.getEmail();
        PromoCodeSRO prmCode = request.getPrmCode();
        String confirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (StringUtils.isNotEmpty(email) && prmCode != null) {
            emailServiceInternal.sendFirstSubscriptionEmail(email, prmCode, confirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: email=" + email + " Prmocode=" + prmCode + " confirmationlink=" + confirmationLink + " contextpath=" + contextPath
                    + " contentpath=" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request: email=" + email + " Prmocode=" + prmCode + " confirmationlink=" + confirmationLink + " contextpath=" + contextPath + " contentpath="
                    + contentPath);
        }
        return response;
    }

    @Override
    public NotifySubscribedUserResponse notifySubscribedUser(NotifySubscribedUserRequest request) throws TransportException {
        NotifySubscribedUserResponse response = new NotifySubscribedUserResponse();
        String confirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        String email = request.getEmail();
        ZoneSRO zoneSro = localityClientService.getZoneByIdRequest(new GetZoneByIdRequest(request.getZone())).getZone();

        if (StringUtils.isNotEmpty(contentPath)) {
            emailServiceInternal.notifySubscribedUser(email, zoneSro, contextPath, contentPath, confirmationLink);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: Zone=" + zoneSro + " Email=" + email + " confirmationlink=" + confirmationLink + " contextpath=" + contextPath + " contentpath="
                    + contentPath);
            response.setSuccessful(true);
            LOG.info("Invalid request: Zone=" + zoneSro + " Email=" + email + " confirmationlink=" + confirmationLink + " contextpath=" + contextPath + " contentpath="
                    + contentPath);
        }
        return response;
    }

   
    @Override
    public com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse sendFeedbackMail(com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest request)
            throws TransportException {
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse response = new com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        boolean redeemed = request.getRedeemed();
        GetSuborderResponse getSuborderByIdResponse = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId()));
        GetCatalogResponse getCatalogContentById = catalogClientService.getCatalogContentById(new GetCatalogByIdRequest(request.getCatalogId()));

        if (getSuborderByIdResponse != null && getCatalogContentById != null && getCatalogContentById.getCatalogSRO() != null && getSuborderByIdResponse.getSuborderSRO() != null) {

            SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
            CatalogSRO catalog = catalogClientService.getCatalogContentById(new GetCatalogByIdRequest(request.getCatalogId())).getCatalogSRO();
            emailServiceInternal.sendFeedbackMail(suborder, catalog, contentPath, contextPath, redeemed);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid sendFeedbackMail Request: Catalog=" + request.getCatalogId() + "Suborder" + request.getSuborderId() + " contextpath=" + contextPath
                    + " contentpath=" + contentPath);
            LOG.info("Invalid sendFeedbackMail Request: Catalog=" + request.getCatalogId() + "Suborder" + request.getSuborderId() + " contextpath=" + contextPath + " contentpath="
                    + contentPath);
        }
        return response;
    }

    @Override
    public SendFeedbackMailResponse sendFeedbackMail(SendFeedbackMailRequest request) throws TransportException {
        SendFeedbackMailResponse response = new SendFeedbackMailResponse();

        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest sendFeedbackMailRequest = new com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailRequest(
                request.getSuborderId(), (long) request.getCatalog(), request.getContentPath(), request.getContextPath(), request.getRedeemed());
        com.snapdeal.ums.email.ext.v1.email.SendFeedbackMailResponse sendFeedbackMailResponse = sendFeedbackMail(sendFeedbackMailRequest);
        response.setSuccessful(sendFeedbackMailResponse.isSuccessful());
        if (sendFeedbackMailResponse.getMessage() != null)
            response.setMessage(sendFeedbackMailResponse.getMessage());

        return response;
    }

    @Deprecated
    @Override
    public SendAutoCaptureStatusEmailResponse sendAutoCaptureStatusEmail(SendAutoCaptureStatusEmailRequest request) {
        SendAutoCaptureStatusEmailResponse response = new SendAutoCaptureStatusEmailResponse();
        String pg = request.getPg();
        String date = request.getDate();
        Map<String, String> map = new HashMap<String, String>();
        List<MapEntryUtil> list = request.getFailedOrders();
        for (MapEntryUtil entry : list)
            map.put(entry.getKey(), entry.getValue());

        Map<String, String> failedOrders = map;
        if (StringUtils.isNotEmpty(date) && StringUtils.isNotEmpty(pg) && !failedOrders.isEmpty()) {
            emailServiceInternal.sendAutoCaptureStatusEmail(pg, date, failedOrders);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: Pg=" + pg + " date" + date + " failedorders.size=" + failedOrders.size());
            response.setSuccessful(false);
            LOG.info("Invalid request: Pg=" + pg + " date" + date + " failedorders.size=" + failedOrders.size());
        }
        return response;
    }

    @Deprecated
    @Override
    public SendFeaturedResponseEmailResponse sendFeaturedResponseEmail(SendFeaturedResponseEmailRequest request) {
        SendFeaturedResponseEmailResponse response = new SendFeaturedResponseEmailResponse();
        FeaturedSRO featureSro = request.getFeatured();
        GetFeatured featured = umsConvertorService.getFeaturedEntityFromSRO(featureSro);
        String email = request.getEmail();
        String contentPath = request.getContentPath();
        if (featured != null) {
            emailServiceInternal.sendFeaturedResponseEmail(featured, email, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: fatured=" + featured + " email=" + email + " contentPath" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request: fatured=" + featured + " email=" + email + " contentPath" + contentPath);
        }
        return response;
    }

    @Override
    public SendVoucherEmailResponse sendVoucherEmail(SendVoucherEmailRequest request) {
        SendVoucherEmailResponse response = new SendVoucherEmailResponse();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        if (suborder != null) {
            emailServiceInternal.sendVoucherEmail(suborder);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: suborder=" + suborder);
            LOG.info("Invalid request: suborder=" + suborder);
        }
        return response;
    }

    @Override
    public SendEmployeeSdCashRewardEmailResponse sendEmployeeSdCashRewardEmail(SendEmployeeSdCashRewardEmailRequest request) {
        SendEmployeeSdCashRewardEmailResponse response = new SendEmployeeSdCashRewardEmailResponse();
        UserSRO userSro = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSro);
        int cashBackAmount = request.getCashBackAmount();
        boolean newUser = request.getNewUser();
        String confirmationLink = request.getConfirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null) {
            emailServiceInternal.sendEmployeeSdCashRewardEmail(user, cashBackAmount, newUser, confirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: User=" + user + " ConfirmationLink" + confirmationLink + " contextpath=" + contextPath + " contentpath=" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request: User=" + user + " ConfirmationLink" + confirmationLink + " contextpath=" + contextPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendWayBillNumberExhaustionEmailResponse sendWayBillNumberExhaustionEmail(SendWayBillNumberExhaustionEmailRequest request) {
        SendWayBillNumberExhaustionEmailResponse response = new SendWayBillNumberExhaustionEmailResponse();
        String shippingProviderName = request.getShippingProviderName();
        String shippingMethodName = request.getShippingMethodName();
        long l = request.getL();
        if (StringUtils.isNotEmpty(shippingMethodName) && StringUtils.isNotEmpty(shippingProviderName)) {
            emailServiceInternal.sendWayBillNumberExhaustionEmail(shippingProviderName, shippingMethodName, l);
            response.setSuccessful(true);
        } else {
            response.setMessage("invalid request: shippingMethodName=" + shippingMethodName + " shippingProviderName=" + shippingProviderName);
            response.setSuccessful(false);
            LOG.info("invalid request: shippingMethodName=" + shippingMethodName + " shippingProviderName=" + shippingProviderName);
        }
        return response;
    }

    @Override
    public SendPendingResponseEmailResponse sendPendingResponseEmail(SendPendingResponseEmailRequest request) {
        SendPendingResponseEmailResponse response = new SendPendingResponseEmailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        if (order != null) {
            emailServiceInternal.sendPendingResponseEmail(order);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: order=" + order);
            response.setSuccessful(false);
            LOG.info("Invalid request: order=" + order);
        }
        return response;
    }

    /*@Override
    public SendProductWorkflowRejectionEmailResponse sendProductWorkflowRejectionEmail(SendProductWorkflowRejectionEmailRequest request) {
        SendProductWorkflowRejectionEmailResponse response = new SendProductWorkflowRejectionEmailResponse();
        List<ProductWorkflowSRO> workflowSROs = request.getWorkflowSROs();
        List<ProductWorkflowDTO> workFlowDTOs = new ArrayList<ProductWorkflowDTO>();
        for (ProductWorkflowSRO sro : workflowSROs)
            workFlowDTOs.add(umsConvertorService.getProductWorkFlowDTOFromSRO(sro));
        String role = request.getRole();
        if (workFlowDTOs.isEmpty() && StringUtils.isNotEmpty(role)) {
            emailServiceInternal.sendProductWorkflowRejectionEmail(workFlowDTOs, role);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: workflowDTOs=" + workFlowDTOs + " role=" + role);
            response.setSuccessful(false);
            LOG.info("Invalid request: workflowDTOs=" + workFlowDTOs + " role=" + role);
        }
        return response;
    }*/

   
    @Override
    public SendDealShareEmailFromDealPageResponse sendDealShareEmailFromDealPage(SendDealShareEmailFromDealPageRequest request) {
        SendDealShareEmailFromDealPageResponse response = new SendDealShareEmailFromDealPageResponse();
        String refererEmail = request.getRefererEmail();
        String name = request.getName();
        String from = request.getFrom();
        String recipientName = request.getRecipientName();
        String url = request.getUrl();
        String dealDetail = request.getDealDetail();
        if (StringUtils.isNotEmpty(dealDetail) && StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(recipientName) && StringUtils.isNotEmpty(from)
                && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(refererEmail)) {
            emailServiceInternal.sendDealShareEmailFromDealPage(refererEmail, name, from, recipientName, url, dealDetail);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invaled request: refererEmail" + refererEmail + "name=" + name + " From" + from + " recipientName" + recipientName + " url" + url + " dealDetail"
                    + dealDetail);
            response.setSuccessful(false);
            LOG.info("Invaled request: refererEmail" + refererEmail + "name=" + name + " From" + from + " recipientName" + recipientName + " url" + url + " dealDetail"
                    + dealDetail);
        }
        return response;
    }

    @Deprecated
    @Override
    public SendDealShareEmailFromPostBuyResponse sendDealShareEmailFromPostBuy(SendDealShareEmailFromPostBuyRequest request) {
        SendDealShareEmailFromPostBuyResponse response = new SendDealShareEmailFromPostBuyResponse();
        String refererEmail = request.getRefererEmail();
        String name = request.getName();
        String from = request.getFrom();
        String recipientName = request.getRecipientName();
        String url = request.getUrl();
        String dealDetail = request.getDealDetail();
        if (StringUtils.isNotEmpty(dealDetail) && StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(recipientName) && StringUtils.isNotEmpty(from)
                && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(refererEmail)) {
            emailServiceInternal.sendDealShareEmailFromPostBuy(refererEmail, name, from, recipientName, url, dealDetail);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: refererEmail" + refererEmail + "name=" + name + " From" + from + " recipientName" + recipientName + " url" + url + " dealDetail"
                    + dealDetail);
            response.setSuccessful(false);
            LOG.info("Invalid request: refererEmail" + refererEmail + "name=" + name + " From" + from + " recipientName" + recipientName + " url" + url + " dealDetail"
                    + dealDetail);
        }
        return response;
    }

    @Override
    public SendAppreciationAckSDCashEmailResponse sendAppreciationAckSDCashEmail(SendAppreciationAckSDCashEmailRequest request) {
        SendAppreciationAckSDCashEmailResponse response = new SendAppreciationAckSDCashEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        int sdCashValue = request.getSdCashValue();
        boolean newUser = request.getNewUser();
        String conirmationLink = request.getConirmationLink();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (user != null) {
            emailServiceInternal.sendAppreciationAckSDCashEmail(user, sdCashValue, newUser, conirmationLink, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request user=" + user + " sdcashvalue=" + sdCashValue + " confirmation link=" + conirmationLink + " contextpath= " + contextPath
                    + " contentpath=" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request user=" + user + " sdcashvalue=" + sdCashValue + " confirmation link=" + conirmationLink + " contextpath= " + contextPath + " contentpath="
                    + contentPath);
        }
        return response;
    }

    @Override
    public SendSDCashEmailResponse sendSDCashEmail(SendSDCashEmailRequest request) {
        SendSDCashEmailResponse response = new SendSDCashEmailResponse();
        UserSRO userSRO = request.getUser();
        User user = umsConvertorService.getUserEntityFromSRO(userSRO);
        String emailTemplateName = request.getEmailTemplateName();
        int cashBackAmount = request.getCashBackAmount();
        boolean newUser = request.getNewUser();
        String confirmationLink = request.getConfirmationLink();

        if (user != null && StringUtils.isNotEmpty(emailTemplateName)) {
            emailServiceInternal.sendSDCashEmail(user, emailTemplateName, cashBackAmount, confirmationLink, newUser);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: user" + user.getFirstName() + " emailTemplateName" + emailTemplateName + " confirmationLink" + confirmationLink);
            LOG.info("Invalid request: user" + user.getFirstName() + " emailTemplateName" + emailTemplateName + " confirmationLink" + confirmationLink);
        }
        return response;
    }

    @Override
    public SendValentineEmailResponse sendValentineEmail(SendValentineEmailRequest request) {
        SendValentineEmailResponse response = new SendValentineEmailResponse();
        String name = request.getName();
        String recipient = request.getRecipient();
        String url = request.getUrl();
        if (StringUtils.isNotEmpty(url) && StringUtils.isNotEmpty(recipient) && StringUtils.isNotEmpty(name)) {
            emailServiceInternal.sendValentineEmail(name, recipient, url);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid Request: Name=" + name + " Recipient=" + recipient + " url=" + url);
            LOG.info("Invalid Request: Name=" + name + " Recipient=" + recipient + " url=" + url);
        }
        return response;
    }


    @Override
    public SendBulkUploadResultEmailResponse sendBulkUploadResultEmail(SendBulkUploadResultEmailRequest request) {
        SendBulkUploadResultEmailResponse response = new SendBulkUploadResultEmailResponse();
        List<BulkUploadResultSRO> resultsSro = request.getResultDTOs();
        List<BulkUploadResultDTO> resultDTOs = new ArrayList<BulkUploadResultDTO>();
        for (BulkUploadResultSRO sro : resultsSro) {
            resultDTOs.add(umsConvertorService.getBulkUploadResultsDTOfromSRO(sro));
        }
        String fileName = request.getFileName();
        String email = request.getEmail();
        if ((!resultDTOs.isEmpty()) && StringUtils.isNotEmpty(fileName)) {
            emailServiceInternal.sendBulkUploadResultEmail(resultDTOs, fileName, email);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: Filename=" + fileName + "resultDTO" + resultDTOs);
            LOG.info("Invalid request: Filename=" + fileName + "resultDTO" + resultDTOs);
        }
        return response;
    }

    @Override
    public SendUserSDCashHistoryResponse sendUserSDCashHistory(SendUserSDCashHistoryRequest request) {
        SendUserSDCashHistoryResponse response = new SendUserSDCashHistoryResponse();
        String userEmail = request.getUserEmail();
        String userName = request.getUserName();
        int sdcashAtBegOfMonth = request.getSdcashAtBegOfMonth();
        int sdcashUsedThisMonth = request.getSdcashUsedThisMonth();
        int sdcashEarningOfMonth = request.getSdcashEarningOfMonth();
        int sdcashAvailable = request.getSdcashAvailable();
        int sdCashExpired = request.getSdCashExpiredThisMonth();
        int currSDCash = request.getCurrSDCash();
        DateRange range = request.getRange();
        String linkToBeSent = request.getLinkToBeSent();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        //if (StringUtils.isNotEmpty(contentPath) && StringUtils.isNotEmpty(contextPath) && StringUtils.isNotEmpty(linkToBeSent) && StringUtils.isNotEmpty(userName)
        //      && StringUtils.isNotEmpty(userEmail)) {
        emailServiceInternal.sendUserSDCashHistory(userEmail, userName, sdcashAtBegOfMonth, sdcashEarningOfMonth, sdcashUsedThisMonth, sdcashAvailable, sdCashExpired, range,
                currSDCash, linkToBeSent, contextPath, contentPath);
        response.setSuccessful(true);
        /* } else {
             response.setSuccessful(false);
             response.setMessage("Invalid Request: ContentPath" + contentPath + " contextpath=" + contextPath + " linktobesent" + linkToBeSent + " username=" + userName
                     + " userEmail=" + userEmail + " sdcashAtBegOfMonth=" + sdcashAtBegOfMonth + " sdcashAvailable=" + sdcashAvailable + " sdcashEarningOfMonth="
                     + sdcashEarningOfMonth + " sdcashUsedThisMonth=" + sdcashUsedThisMonth + " range=" + range);
         }*/
        return response;
    }

    @Override
    public SendPromoCodeOnPurchaseEmailResponse sendPromoCodeOnPurchaseEmail(SendPromoCodeOnPurchaseEmailRequest request) {
        SendPromoCodeOnPurchaseEmailResponse response = new SendPromoCodeOnPurchaseEmailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        PromoCodeSRO promoCode = request.getPromoCode();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        if (order != null && promoCode != null) {
            emailServiceInternal.sendPromoCodeOnPurchaseEmail(order, promoCode, contextPath, contentPath);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: Order=" + order + " promocode= " + promoCode + " contextpath=" + contextPath + " contentpath=" + contentPath);
            response.setSuccessful(false);
            LOG.info("Invalid request: Order=" + order + " promocode= " + promoCode + " contextpath=" + contextPath + " contentpath=" + contentPath);
        }
        return response;
    }

    @Override
    public SendAutoAccountConfirmationEmailResponse sendAutoAccountConfirmationEmail(SendAutoAccountConfirmationEmailRequest request) {
        SendAutoAccountConfirmationEmailResponse response = new SendAutoAccountConfirmationEmailResponse();
        String email = request.getEmail();
        String password = request.getPassword();
        String name = request.getName();
        String contextPath = request.getContextPath();
        String contentPath = request.getContentPath();
        String confirmationLink = request.getConfirmationLink();
        if (StringUtils.isNotEmpty(confirmationLink) && StringUtils.isNotEmpty(name) && StringUtils.isNotEmpty(password) && StringUtils.isNotEmpty(email)) {
            emailServiceInternal.sendAutoAccountConfirmationEmail(email, password, name, contextPath, contentPath, confirmationLink);
            LOG.info("Successfully send autoAccount Confirmation Email for request: "+request.toString());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: email=" + email + " password" + password + " name=" + name + " contextPath=" + contextPath + " contentPath" + contentPath
                    + " confirmationLink" + confirmationLink);
            LOG.info("Invalid request: email=" + email + " password" + password + " name=" + name + " contextPath=" + contextPath + " contentPath" + contentPath
                    + " confirmationLink" + confirmationLink);
        }
        return response;
    }

    @Override
    public SendOrderRefundEmailNewResponse sendOrderRefundEmailNew(SendOrderRefundEmailNewRequest request) {
        SendOrderRefundEmailNewResponse response = new SendOrderRefundEmailNewResponse();
        OrderCancellationEmailSRO orderCancellationEmailSRO = request.getOrderCancellationEmailSRO();
        //OrderCancellationEmailSRO orderCancellationEmailDTO = umsConvertorService.getOrderCancellationEmailDTOFromSRO(orderCancellationEmailSRO);
        if (request.getOrderCancellationEmailSRO() != null) {
            emailServiceInternal.sendOrderRefundEmailNew(request.getOrderCancellationEmailSRO());
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: orderCancellationEmailDTO=" + request.getOrderCancellationEmailSRO());
            response.setSuccessful(false);
            LOG.info("Invalid request: orderCancellationEmailDTO=" + request.getOrderCancellationEmailSRO());
        }
        return response;
    }

    @Override
    public SendOrderReplacedSummaryEmailResponse sendOrderReplacedSummaryEmail(SendOrderReplacedSummaryEmailRequest request) {
        SendOrderReplacedSummaryEmailResponse response = new SendOrderReplacedSummaryEmailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        Set<SuborderSRO> suborders = new HashSet<SuborderSRO>();
        for (Integer suborderId : request.getSuborderIds()) {
            suborders.add(orderClientService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO());
        }
        if ((!suborders.isEmpty()) && order != null) {
            emailServiceInternal.sendOrderReplacedSummaryEmail(order, suborders);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: order=" + order + " suborders" + suborders);
            response.setSuccessful(false);
            LOG.info("Invalid request: order=" + order + " suborders" + suborders);
        }
        return response;
    }

    @Override
    public SendFinanceVendorPaymentDetailsGenerationEmailResponse sendFinanceVendorPaymentDetailsGenerationEmail(SendFinanceVendorPaymentDetailsGenerationEmailRequest request) {
        SendFinanceVendorPaymentDetailsGenerationEmailResponse response = new SendFinanceVendorPaymentDetailsGenerationEmailResponse();
        String email = request.getEmail();
        String path = request.getPath();
        if (StringUtils.isNotEmpty(path) && StringUtils.isNotEmpty(email)) {
            emailServiceInternal.sendFinanceVendorPaymentDetailsGenerationEmail(email, path);
            response.setSuccessful(true);
        } else {
            response.setMessage("Invalid request: email=" + email + "path=" + path);
            response.setSuccessful(false);
            LOG.info("Invalid request: email=" + email + "path=" + path);
        }
        return response;
    }

    @Override
    public SendCODOrderEmailResponse sendCODOrderEmail(SendCODOrderEmailRequest request) {
        SendCODOrderEmailResponse response = new SendCODOrderEmailResponse();
        String orderCode = request.getOrderCode();
        if (StringUtils.isNotEmpty(orderCode)) {
            emailServiceInternal.sendCODOrderEmail(orderCode);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + orderCode);
            LOG.info("Invalid request: order=" + orderCode);
        }
        return response;
    }

    @Override
    public CartDropoutNotificationResponse cartDropoutNotificationAfter15days(CartDropoutNotificationRequest request) {
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        if (StringUtils.isNotEmpty(request.getEmail()) && request.getCartItemSRO() != null) {
            emailServiceInternal.cartDropoutNotificationAfter15days(request.getEmail(), request.getName(), request.getCartId(), request.getCartItemSRO());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: custInfo=" + request.getEmail() + " cart" + request.getCartId());
            LOG.info("Invalid request: custInfo=" + request.getEmail() + " cart" + request.getCartId());
        }
        return response;
    }

    @Override
    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrTo15days(CartDropoutNotificationRequest request) {
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        if (StringUtils.isNotEmpty(request.getEmail()) && request.getCartItemSRO() != null && StringUtils.isNotEmpty(request.getCatalogText())) {
            emailServiceInternal.cartDropoutNotificationWithin24hrTo15days(request.getEmail(), request.getName(), request.getCartId(), request.getCartItemSRO(),
                    request.getCatalogText());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: catalogText=" + request.getCatalogText() + " cart=" + request.getCartId() + " custInfo" + request.getEmail());
            LOG.info("Invalid request: catalogText=" + request.getCatalogText() + " cart=" + request.getCartId() + " custInfo" + request.getEmail());
        }
        return response;
    }

    @Override
    public CartDropoutNotificationResponse cartDropoutNotificationWithin24hrs(CartDropoutNotificationRequest request) {
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        if (StringUtils.isNotEmpty(request.getEmail()) && request.getCartItemSRO() != null) {
            emailServiceInternal.cartDropoutNotificationWithin24hrs(request.getEmail(), request.getName(), request.getCartId(), request.getCartItemSRO());
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: custInfo=" + request.getEmail() + " cart" + request.getCartId());
            LOG.info("Invalid request: custInfo=" + request.getEmail() + " cart" + request.getCartId());
        }
        return response;
    }

    @Override
    public CartDropoutNotificationResponse cartDropOutNotificationMail(CartDropoutNotificationRequest request){
        CartDropoutNotificationResponse response = new CartDropoutNotificationResponse();
        if(StringUtils.isNotEmpty(request.getEmail()) && request.getCartItemSRO()!=null){
            emailServiceInternal.cartDropOutNotificationMail(request.getEmail(), request.getName(), request.getCartId(), request.getCartItemSRO(), request.getUmsPOGSROs());
            LOG.info("successfully send cartDropOutNotificationMail for request: "+request.toString());
            response.setSuccessful(true);
        }else{
            response.setSuccessful(false);
            response.setMessage("Invalid Request:"+ request.toString());
            LOG.info("Invalid CartDropoutNotificationRequest " + request.toString());
        }
        return response;
    }
    
    
    @Override
    public SendZendeskUploadedFileEmailResponse sendZendeskUploadedFileEmail(SendZendeskUploadedFileEmailRequest request) {
        SendZendeskUploadedFileEmailResponse response = new SendZendeskUploadedFileEmailResponse();
        String email = request.getEmail();
        String path = request.getPath();
        if (StringUtils.isNotEmpty(path) && StringUtils.isNotEmpty(email)) {
            emailServiceInternal.sendZendeskUploadedFileEmail(email, path);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: email=" + email + " path=" + path);
            LOG.info("Invalid request: email=" + email + " path=" + path);
        }
        return response;
    }

    @Override
    public SendHelpDeskEmailResponse sendHelpDeskEmail(SendHelpDeskEmailRequest request) {
        SendHelpDeskEmailResponse response = new SendHelpDeskEmailResponse();
        String subject = request.getSubject();
        String name = request.getName();
        String email = request.getEmail();
        String mobile = request.getMobile();
        String orderId = request.getOrderId();
        String itemName = request.getItemName();
        String comments = request.getComments();
        String ticketId = request.getTicketId();
        emailServiceInternal.sendHelpDeskEmail(subject, name, email, mobile, orderId, itemName, comments, ticketId);
        response.setSuccessful(true);
        /*} else {
            response.setMessage("Invalid request: subject=" + subject + " name=" + name + " email=" + email + " mobile=" + mobile + " orderId=" + orderId + " itemName=" + itemName
                    + " comments=" + comments + " ticketId" + ticketId);
            response.setSuccessful(false);
        }*/
        return response;
    }

    @Override
    public SendCancelledOrderFeedbackRequestEmailResponse sendCancelledOrderFeedbackRequestEmail(SendCancelledOrderFeedbackRequestEmailRequest request) {
        SendCancelledOrderFeedbackRequestEmailResponse response = new SendCancelledOrderFeedbackRequestEmailResponse();
        CancelledOrderFeedbackDOSRO cancelledOrderFeedbackSRO = request.getCancelledOrderFeedbackDOSRO();
        CancelledOrderFeedbackDO cancelledOrderFeedbackDTO = umsConvertorService.getcancelledOrderFeedbackDOfromSRO(cancelledOrderFeedbackSRO);
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        if (cancelledOrderFeedbackDTO != null) {
            emailServiceInternal.sendCancelledOrderFeedbackRequestEmail(cancelledOrderFeedbackDTO, contentPath, contextPath);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: contentPath=" + contentPath + " contextPath=" + contentPath + " cancelledOrderFeedbackDTO" + cancelledOrderFeedbackDTO);
            LOG.info("Invalid request: contentPath=" + contentPath + " contextPath=" + contentPath + " cancelledOrderFeedbackDTO" + cancelledOrderFeedbackDTO);
        }
        return response;
    }

    @Override
    public SendMultiVendorMappingResultsResponse sendMultiVendorMappingResults(SendMultiVendorMappingResultsRequest request) {
        SendMultiVendorMappingResultsResponse response = new SendMultiVendorMappingResultsResponse();
        String fileName = request.getFileName();
        List<ProductMultiVendorMappingResultSRO> productMultiVendorMappingResultSRO = request.getResultSROs();
        List<ProductMultiVendorMappingResultDTO> resultDTOs = new ArrayList<ProductMultiVendorMappingResultDTO>();
        for (ProductMultiVendorMappingResultSRO sro : productMultiVendorMappingResultSRO) {
            resultDTOs.add(umsConvertorService.getProductMultiVendorMappingResultDTOfromSRO(sro));
        }
        if (StringUtils.isNotEmpty(fileName) && (!resultDTOs.isEmpty())) {
            emailServiceInternal.sendMultiVendorMappingResults(resultDTOs, fileName);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: resultDTOs=" + resultDTOs + " fileName" + fileName);
            LOG.info("Invalid request: resultDTOs=" + resultDTOs + " fileName" + fileName);
        }
        return response;
    }

    @Override
    public SendAlternateCollectMoneyEmailResponse sendAlternateCollectMoneyEmail(SendAlternateCollectMoneyEmailRequest request) {
        SendAlternateCollectMoneyEmailResponse response = new SendAlternateCollectMoneyEmailResponse();
        double collectableAmount = request.getCollectableAmount();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        SuborderSRO originalSuborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getOriginalSuborderId())).getSuborderSRO();
        SuborderSRO alternateSuborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getAlternateSuborderId())).getSuborderSRO();
        String buyPageurl = request.getBuyPageurl();
        if (order != null && originalSuborder != null && alternateSuborder != null && StringUtils.isNotEmpty(buyPageurl)) {
            emailServiceInternal.sendAlternateCollectMoneyEmail(collectableAmount, order, originalSuborder, alternateSuborder, buyPageurl);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: collectableAmount=" + collectableAmount + " order=" + order + " originalSuborder=" + originalSuborder + " alternateSuborder="
                    + alternateSuborder + "buyPageurl" + buyPageurl);
            LOG.info("Invalid request: collectableAmount=" + collectableAmount + " order=" + order + " originalSuborder=" + originalSuborder + " alternateSuborder="
                    + alternateSuborder + "buyPageurl" + buyPageurl);
        }
        return response;
    }

    @Override
    public SendReviewRequestMailResponse sendReviewRequestMail(SendReviewRequestMailRequest request) {
        SendReviewRequestMailResponse response = new SendReviewRequestMailResponse();
        List<SuborderSRO> suborders = new ArrayList<SuborderSRO>();
        for (Integer suborderId : request.getSuborderIds()) {
            suborders.add(orderClientService.getSuborderById(new GetSuborderByIdRequest(suborderId)).getSuborderSRO());
        }
        String contentPath = request.getContentPath();
        String contextPath = request.getContextPath();
        if (suborders != null) {
            try {
                emailServiceInternal.sendReviewRequestMail(suborders, contentPath, contextPath);
                response.setSuccessful(true);
            } catch (TransportException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: contentPath=" + contentPath + "contextPath=" + contextPath + "order=" + suborders);
            LOG.info("Invalid request: contentPath=" + contentPath + "contextPath=" + contextPath + "order=" + suborders);
        }
        return response;
    }

    @Override
    public SendAffiliateSubscriptionEmailResponse sendAffiliateSubscriptionEmail(SendAffiliateSubscriptionEmailRequest request) {
        SendAffiliateSubscriptionEmailResponse response = new SendAffiliateSubscriptionEmailResponse();
        String email = request.getEmail();
        List<HashMap<String, String>> listOfMap = request.getListOfMap();
        if (StringUtils.isNotEmpty(email)) {
            emailServiceInternal.sendAffiliateSubscriptionEmail(email, listOfMap);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: email=" + email + "listOfMap.isempty=" + listOfMap);
            LOG.info("Invalid request: email=" + email + "listOfMap.isempty=" + listOfMap);

        }
        return response;
    }

    @Override
    public SendPrebookRetryEmailResponse sendPrebookRetryEmail(SendPrebookRetryEmailRequest request) {
        SendPrebookRetryEmailResponse response = new SendPrebookRetryEmailResponse();
        String buyPageUrl = request.getBuyPageUrl();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        ;
        if ((order != null) && (suborder != null)) {
            emailServiceInternal.sendPrebookRetryEmail(order, suborder, buyPageUrl);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + "suborder=" + suborder);
            LOG.info("Invalid request: order=" + order + "suborder=" + suborder);

        }
        return response;
    }

    @Override
    public SendAlternateRetryEmailResponse sendAlternateRetryEmail(SendAlternateRetryEmailRequest request) {
        SendAlternateRetryEmailResponse response = new SendAlternateRetryEmailResponse();
        SuborderSRO alternateSuborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getAlternateSuborderId())).getSuborderSRO();
        String buyPageUrl = request.getBuyPageUrl();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        if ((order != null) && (alternateSuborder != null)) {
            emailServiceInternal.sendAlternateRetryEmail(order, alternateSuborder, buyPageUrl);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + "alternateSuborder=" + alternateSuborder);
            LOG.info("Invalid request: order=" + order + "alternateSuborder=" + alternateSuborder);
        }
        return response;
    }

    @Override
    public SendAlternatePaymentConfirmationEmailResponse sendAlternatePaymentConfirmationEmail(SendAlternatePaymentConfirmationEmailRequest request) {
        SendAlternatePaymentConfirmationEmailResponse response = new SendAlternatePaymentConfirmationEmailResponse();
        int collectableAmount = request.getCollectableAmount();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        SuborderSRO originalSuborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getOriginalSuborderId())).getSuborderSRO();
        ;
        SuborderSRO alternateSuborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getAlternateSuborderId())).getSuborderSRO();
        ;

        if ((order != null) && (alternateSuborder != null)) {
            emailServiceInternal.sendAlternatePaymentConfirmationEmail(collectableAmount, order, originalSuborder, alternateSuborder);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: collectableAmount =" + collectableAmount + " order=" + order + " originalSuborder =" + originalSuborder + "alternateSuborder="
                    + alternateSuborder);
            LOG.info("Invalid request: collectableAmount =" + collectableAmount + " order=" + order + " originalSuborder =" + originalSuborder + "alternateSuborder="
                    + alternateSuborder);
        }
        return response;
    }

    @Override
    public SendAlternateRefundEmailResponse sendAlternateRefundEmail(SendAlternateRefundEmailRequest request) {
        SendAlternateRefundEmailResponse response = new SendAlternateRefundEmailResponse();

        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        FullfillAlternateSuborderRequest altRequest = new FullfillAlternateSuborderRequest();
        altRequest.setSuborderSRO(orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO());
        altRequest.setAbsorb(request.isAbsorb());
        altRequest.setPaidAmount(request.getPaidAmount());
        altRequest.setAmountRefunded(request.getAmountRefunded());
        altRequest.setSdCashRefunded(request.getSdCashRefunded());
        altRequest.setOldSuborderSRO(orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getOldSuborderId())).getSuborderSRO());
        if ((order != null) && (altRequest != null)) {
            emailServiceInternal.sendAlternateRefundEmail(order, altRequest);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + " FullfillAlternateSuborderRequest =" + altRequest);
            LOG.info("Invalid request: order=" + order + " FullfillAlternateSuborderRequest =" + altRequest);
        }
        return response;
    }

  
    @Override
    public SendAlternateAbsorbEmailResponse sendAlternateAbsorbEmail(SendAlternateAbsorbEmailRequest request) {
        SendAlternateAbsorbEmailResponse response = new SendAlternateAbsorbEmailResponse();

        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        FullfillAlternateSuborderRequest altRequest = new FullfillAlternateSuborderRequest();
        altRequest.setSuborderSRO(orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO());
        altRequest.setAbsorb(request.isAbsorb());
        altRequest.setPaidAmount(request.getPaidAmount());
        altRequest.setAmountRefunded(request.getAmountRefunded());
        altRequest.setSdCashRefunded(request.getSdCashRefunded());
        altRequest.setOldSuborderSRO(orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getOldSuborderId())).getSuborderSRO());

        if ((order != null) && (altRequest != null)) {
            emailServiceInternal.sendAlternateAbsorbEmail(order, altRequest);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + " FullfillAlternateSuborderRequest =" + altRequest);
            LOG.info("Invalid request: order=" + order + " FullfillAlternateSuborderRequest =" + altRequest);
        }
        return response;
    }

    @Deprecated
    @Override
    public SendReleaseDateShiftMailResponse sendReleaseDateShiftMail(SendReleaseDateShiftMailRequest request) {
        SendReleaseDateShiftMailResponse response = new SendReleaseDateShiftMailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        String newReleaseDate = request.getNewReleaseDate();
        String oldReleaseDate = request.getOldReleaseDate();
        if ((order != null) && (suborder != null) && (StringUtils.isNotEmpty(newReleaseDate))) {
            emailServiceInternal.sendReleaseDateShiftMail(order, suborder, newReleaseDate, oldReleaseDate);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + " Suborder =" + suborder + " newReleaseDate = " + newReleaseDate);
            LOG.info("Invalid request: order=" + order + " Suborder =" + suborder + " newReleaseDate = " + newReleaseDate);
        }
        return response;
    }

    @Override
    public SendSecondPaymentMailResponse sendSecondPaymentMail(SendSecondPaymentMailRequest request) {
        SendSecondPaymentMailResponse response = new SendSecondPaymentMailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        String buyPageUrl = request.getBuyPageUrl();
        String releaseDate = request.getReleaseDate();
        if ((order != null) && (suborder != null)) {
            emailServiceInternal.sendSecondPaymentMail(order, suborder, buyPageUrl, releaseDate);
            response.setSuccessful(true);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + " Suborder =" + suborder + " releaseDate = " + releaseDate);
            LOG.info("Invalid request: order=" + order + " Suborder =" + suborder + " releaseDate = " + releaseDate);
        }
        return response;
    }

  
    @Override
    public SendPrebookPaymentConfirmationEmailResponse sendPrebookPaymentConfirmationEmail(SendPrebookPaymentConfirmationEmailRequest request) {
        SendPrebookPaymentConfirmationEmailResponse response = new SendPrebookPaymentConfirmationEmailResponse();
        OrderSRO order = orderClientService.getOrderById(new GetOrderByIdRequest(request.getOrderId())).getOrderSRO();
        SuborderSRO suborder = orderClientService.getSuborderById(new GetSuborderByIdRequest(request.getSuborderId())).getSuborderSRO();
        int collectedAmount = request.getCollectedAmount();
        int prebookAmount = request.getPrebookAmount();
        if ((order != null) && (suborder != null)) {
            emailServiceInternal.sendPrebookPaymentConfirmationEmail(order, suborder, collectedAmount, prebookAmount);
        } else {
            response.setSuccessful(false);
            response.setMessage("Invalid request: order=" + order + " Suborder =" + suborder + " collectedAmount = " + collectedAmount + " prebookAmount = " + prebookAmount);
            LOG.info("Invalid request: order=" + order + " Suborder =" + suborder + " collectedAmount = " + collectedAmount + " prebookAmount = " + prebookAmount);
        }
        return response;
    }

}
