package com.snapdeal.ums.server.services.impl;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.exception.TransportException;
import com.snapdeal.base.ext.ShippingMethodType;
import com.snapdeal.base.model.common.CatalogType;
import com.snapdeal.base.notification.INotificationService;
import com.snapdeal.base.notification.email.EmailMessage;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.base.utils.EncryptionUtils;
import com.snapdeal.base.utils.StringUtils;
import com.snapdeal.catalog.base.model.GetCatalogByIdRequest;
import com.snapdeal.catalog.base.model.GetPageURLForCatalogRequest;
import com.snapdeal.catalog.base.sro.CatalogSRO;
import com.snapdeal.catalog.base.sro.PartnerPromoCodeSRO;
import com.snapdeal.catalog.base.sro.ProductPrebookSRO;
import com.snapdeal.catalog.base.sro.ProductSRO;
import com.snapdeal.catalog.base.sro.ZoneSRO;
import com.snapdeal.catalog.client.service.ICatalogClientService;
import com.snapdeal.core.dto.BulkUploadResultDTO;
import com.snapdeal.core.dto.ProductMultiVendorMappingResultDTO;
import com.snapdeal.core.dto.feedback.CancelledOrderFeedbackDO;
import com.snapdeal.core.entity.Audit;
import com.snapdeal.core.entity.Corporate;
import com.snapdeal.core.entity.CustomerQuery;
import com.snapdeal.core.entity.GetFeatured;
import com.snapdeal.core.sro.order.PromoCodeSRO;
import com.snapdeal.core.sro.productoffer.ProductOfferSRO;
import com.snapdeal.core.sro.serviceDeal.ServiceDealSRO;
import com.snapdeal.ipms.base.api.getSellerProductMappingBySUPC.v0.GetSellerProductMappingBySUPCRequest;
import com.snapdeal.ipms.base.api.getSellerProductMappingBySUPC.v0.GetSellerProductMappingBySUPCResponse;
import com.snapdeal.ipms.base.api.getSellerProductMappingBySUPC.v0.SellerProductMappingSRO;
import com.snapdeal.ipms.base.common.model.ProductVendorSRO;
import com.snapdeal.ipms.client.service.IIPMSClientService;
import com.snapdeal.mail.client.service.exceptions.SendEmailException;
import com.snapdeal.oms.base.model.FullfillAlternateSuborderRequest;
import com.snapdeal.oms.base.model.GetLastTransactionForOrderRequest;
import com.snapdeal.oms.base.model.GetOrderByCodeRequest;
import com.snapdeal.oms.base.model.GetOrderByIdRequest;
import com.snapdeal.oms.base.model.GetOrderMappingRequest;
import com.snapdeal.oms.base.model.GetOrderMappingResponse;
import com.snapdeal.oms.base.model.GetPaymentModeByCodeRequest;
import com.snapdeal.oms.base.model.GetPaymentModeByCodeResponse;
import com.snapdeal.oms.base.model.GetSuborderMappingRequest;
import com.snapdeal.oms.base.sro.order.AddressDetailSRO;
import com.snapdeal.oms.base.sro.order.DealSuborderSRO;
import com.snapdeal.oms.base.sro.order.GetawaySuborderSRO;
import com.snapdeal.oms.base.sro.order.OrderCancellationRequestReasonSRO;
import com.snapdeal.oms.base.sro.order.OrderSRO;
import com.snapdeal.oms.base.sro.order.OrderTransactionSRO;
import com.snapdeal.oms.base.sro.order.SuborderSRO;
import com.snapdeal.oms.base.sro.order.SuborderStatusSRO;
import com.snapdeal.oms.base.sro.order.SuborderTypeSRO;
import com.snapdeal.oms.base.sro.payment.PaymentModeSRO;
import com.snapdeal.oms.base.sro.payment.PaymentModeSubtypeSRO;
import com.snapdeal.oms.services.IOrderClientService;
import com.snapdeal.oms.services.IPaymentClientService;
import com.snapdeal.product.client.service.IProductClientService;
import com.snapdeal.shipping.core.model.GetShippingInfoForSuborderRequest;
import com.snapdeal.shipping.core.model.GetShippingInfoForSuborderResponse;
import com.snapdeal.shipping.service.IShippingClientService;
import com.snapdeal.shipping.sro.ShippingPackageSRO;
import com.snapdeal.ums.cache.EmailTemplateCache;
import com.snapdeal.ums.cache.ZonesCache;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.sro.email.CartItemSRO;
import com.snapdeal.ums.core.sro.email.OrderCancellationEmailSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.core.utils.Constants;
import com.snapdeal.ums.dto.OrderDetailDTO;
import com.snapdeal.ums.dto.OrderItemDetailDTO;
import com.snapdeal.ums.dto.OrderTransactionDetailDTO;
import com.snapdeal.ums.dto.PopularCatalogSRO;
import com.snapdeal.ums.dto.SuborderDetailDTO;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IEmailTriggerService;
import com.snapdeal.ums.server.services.IUserServiceInternal;
import com.snapdeal.ums.server.subsidiary.services.SubsidiaryEmailMessage;
import com.snapdeal.ums.services.others.ICatalogService;
import com.snapdeal.ums.services.others.IProductVendorService;
import com.snapdeal.ums.services.sdCashBulkUpdate.SDCashBulkCreditEmailRequest;
import com.snapdeal.ums.sro.email.vm.UMSPOGSRO;
import com.snapdeal.ums.subscription.server.services.ISubscriptionsServiceInternal;
import com.snapdeal.ums.userNeftDetails.EnhancedUserNEFTDetailsSRO;

@Service("umsEmailServiceInternal")
@Transactional
public class EmailServiceInternalImpl implements IEmailServiceInternal {

	private static final Logger LOG = LoggerFactory
			.getLogger(EmailServiceInternalImpl.class);

	@Autowired
	private ICatalogService catalogService;

	@Autowired
	private IOrderClientService orderService;

	@Autowired
	private IShippingClientService shippingService;

	@Autowired
	private IProductClientService productCatalogService;

	@Autowired
	private ISubscriptionsServiceInternal subscriptionsService;

	@Autowired
	private INotificationService notificationService;

	@Autowired
	private IUserServiceInternal userService;

	@Autowired
	private IProductVendorService productVendorService;

	@Autowired
	private IIPMSClientService ipmsClientService;

	@Autowired
	private IPaymentClientService paymentClient;

	@Autowired
	private ICatalogClientService catalogClientService;

	@Autowired
	private IEmailTriggerService emailTriggerService;

	private static final String DEFAULT_DEAL_GROUPCODES = "restaurants,health-beauty";
	private static final String DEFAULT_PRODUCT_CATEGORY_URLS = "mobiles,computers,perfumes-beauty";
	private static final String VIEW_ORDER_DETAIL_SOURCE_EMAIL = "&viewSource=email";

	private static final String SNAPBOX_ACTIVATION_CONFORMATION_EMAIL_TEMPLATE = "snapBoxActivationConfirmation";
	private static final String SNAPBOX_INVITATION_EMAIL = "snapBoxInvitation";
	private static final String USER_NEFT_DETAILS_UPDATED_EMAIL_TEMPLATE = "userNEFTDetailsUpdated";

	private static final String USER_SDCASH_CONFIRMATION_EMAIL_TEMPLATE = "sdCashCreditToUserViaBulkCredit";
	private static final String SD_CASH_BULK_CREDIT_RESPONSE_TO_UPLOADER = "sdCashBulkCreditResponseToUploader";
	private static final String SD_CASH_BULK_DEBIT_RESPONSE_TO_UPLOADER = "sdCashBulkDebitResponseToUploader";
	private static final String USER_SDWALLET_EMAIL_TEMPLATE = "sdWalletEmail";

	@AuditableMethod
	@Override
	public void send(EmailMessage message) {
		// EmailTemplateVO template =
		// CacheManager.getInstance().getCache(EmailTemplateCache.class).getTemplateByName(message.getTemplateName());
		// notificationService.sendEmail(message, template);

		// Getting the required template from cache
		EmailTemplate template = CacheManager.getInstance()
				.getCache(EmailTemplateCache.class)
				.getTemplateByName(message.getTemplateName());

		try {
			// Calling the trigger service to send the email request
			emailTriggerService.triggerEmail(message, template);

		} catch (SendEmailException e) {
			LOG.error(
					"error while sending email request to emailTriggerService:"
							+ template.getName(), e);
		}
	}
	
	/**
	 * @author Shashank jain
	 * @category call this method after creating the new User for sending the confirmation email to User
	 * @param email
	 * @param contextPath
	 * @param contentPath
	 * @param confirmationLink
	 * @param pwd
	 */
	@Override
	public void sendAccountCreationEmail(String email, String pwd, String emailTemplate) {
		
		LOG.info("inside sendAccountCreationEmail");
		EmailMessage emailMessage = new EmailMessage(email, emailTemplate);
		emailMessage.addTemplateParam("loginID", email);
		emailMessage.addTemplateParam("password", pwd);
		emailMessage.addTemplateParam("contextPath", CacheManager
				.getInstance().getCache(UMSPropertiesCache.class)
				.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath", CacheManager
				.getInstance().getCache(UMSPropertiesCache.class)
				.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMessage);
	}

	@Override
	public void sendOrderSummaryEmail(OrderSRO order) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"orderSummaryEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		// Added for modifying the uRL for JIRA-SNAPDEALTECH-2461
		modifyOrderSummaryUrlForEmail(orderDetailDTO);
		emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(order.getEmail()));
		send(emailMessage);
	}

	private static void modifyOrderSummaryUrlForEmail(
			OrderDetailDTO orderDetailDTO) {
		if (orderDetailDTO != null) {
			String orderSummaryUrl = orderDetailDTO.getOrderSummaryUrl();
			orderSummaryUrl = orderSummaryUrl + VIEW_ORDER_DETAIL_SOURCE_EMAIL;
			LOG.info("Order summary URL modified to: " + orderSummaryUrl);
			orderDetailDTO.setOrderSummaryUrl(orderSummaryUrl);
		}
	}

	@Override
	public void sendOrderReplacedSummaryEmail(OrderSRO order,
			Set<SuborderSRO> suborders) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"orderReplacedSummaryEmail");
		order.setSuborders(suborders);
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (order != null) {
			String contextPath = CacheManager.getInstance()
					.getCache(UMSPropertiesCache.class)
					.getContextPath("http://www.snapdeal.com");
			String primaryOrderSummaryUrl = contextPath
					+ "/orderSummary?code="
					+ EncryptionUtils.md5Encode(order.getCode(),
							order.getMobile()) + "&order=" + order.getCode();
			orderDetailDTO.setPrimaryOrderSummaryUrl(primaryOrderSummaryUrl);
			orderDetailDTO.setPrimaryOrderCode(order.getCode());
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			emailMessage.addTemplateParam("confirmationLink",
					userService.getConfirmationLink(order.getEmail()));
			send(emailMessage);
		}
	}

	@Override
	public void sendAlternateCollectMoneyEmail(double collectableAmount,
			OrderSRO order, SuborderSRO originalSuborder,
			SuborderSRO alternateSuborder, String buyPageUrl) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"alternateCollectMoneyEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
		emailMessage.addTemplateParam("originalSuborder",
				prepareSuborderDetailDTOFromSuborder(originalSuborder));
		emailMessage.addTemplateParam("alternateSuborder",
				prepareSuborderDetailDTOFromSuborder(alternateSuborder));
		emailMessage.addTemplateParam("collectableAmount", collectableAmount);
		emailMessage.addTemplateParam("buyPageUrl", buyPageUrl);

		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMessage);
	}

	@Override
	public void sendVoucherEmail(SuborderSRO suborder) {
		EmailMessage emailMessage = new EmailMessage(suborder.getOrderEmail(),
				"suborderEmail");
		SuborderDetailDTO suborderDetailDTO = prepareSuborderDetailDTOFromSuborder(suborder);
		CatalogSRO catalogDTO = catalogService.getCatalog(
				new GetCatalogByIdRequest(suborder.getCatalogIdL()))
				.getCatalogSRO();
		if (suborder instanceof DealSuborderSRO) {
			ServiceDealSRO dealSRO = (ServiceDealSRO) catalogDTO;
			emailMessage.addTemplateParam("deal", dealSRO);
		} /*
		 * else if (suborder instanceof GetawaySuborderSRO) { GetawayDealSRO
		 * dealDTO = (GetawayDealSRO) catalogDTO;
		 * emailMessage.addTemplateParam("deal", dealDTO); }
		 */
		emailMessage.addTemplateParam("suborder", suborderDetailDTO);
		emailMessage.addTemplateParam(
				"orderCode",
				orderService
						.getOrderById(
								new GetOrderByIdRequest(suborder.getOrderId()))
						.getOrderSRO().getCode());
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMessage);

	}

	@Override
	public void sendOrderRetryEmail(OrderSRO order, String retryUrl) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"dropoutTransactionEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		emailMessage.addTemplateParam("user", order.getCustomerName());
		emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
		emailMessage.addTemplateParam("buyPageUrl", retryUrl);
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMessage);
	}

	@Override
	public void sendPrebookRetryEmail(OrderSRO order, SuborderSRO suborder,
			String buyPageUrl) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"prebookDropoutTransactionEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			CatalogSRO catalog = catalogService.getCatalog(
					new GetCatalogByIdRequest(suborder.getCatalogIdL()))
					.getCatalogSRO();
			if (catalog == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						suborder.getCatalogId());
				return;
			}
			emailMessage.addTemplateParam("user", order.getCustomerName());
			emailMessage.addTemplateParam("suborderDTO",
					prepareSuborderDetailDTOFromSuborder(suborder));
			emailMessage.addTemplateParam("offerPrice",
					suborder.getOfferPrice());
			emailMessage.addTemplateParam("buyPageUrl", buyPageUrl);
			emailMessage.addTemplateParam("collectableAmount",
					suborder.getSellingPrice() - suborder.getOfferPrice());
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendAlternateRetryEmail(OrderSRO order,
			SuborderSRO alternateSuborder, String buyPageUrl) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"alternateDropoutTransactionEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			SuborderSRO originalSuborder = orderService.getSuborderMapping(
					new GetSuborderMappingRequest(alternateSuborder.getCode()))
					.getPrimarySuborerSRO();
			CatalogSRO oldCatalogDTO = catalogService
					.getCatalog(
							new GetCatalogByIdRequest(originalSuborder
									.getCatalogIdL())).getCatalogSRO();
			if (oldCatalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						originalSuborder.getCatalogId());
				return;
			}
			CatalogSRO catalogDTO = catalogService
					.getCatalog(
							new GetCatalogByIdRequest(alternateSuborder
									.getCatalogIdL())).getCatalogSRO();
			if (catalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						alternateSuborder.getCatalogId());
				return;
			}
			int collectableAmount = calculateAmountToBeCollected(catalogDTO,
					originalSuborder);
			emailMessage.addTemplateParam("user", order.getCustomerName());
			emailMessage.addTemplateParam("originalSuborder",
					prepareSuborderDetailDTOFromSuborder(originalSuborder));
			emailMessage.addTemplateParam("alternateSuborder",
					prepareSuborderDetailDTOFromSuborder(alternateSuborder));
			emailMessage.addTemplateParam("collectableAmount",
					(int) collectableAmount);
			emailMessage.addTemplateParam("buyPageUrl", buyPageUrl);
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	private int calculateAmountToBeCollected(CatalogSRO catalogSRO,
			SuborderSRO suborder) {
		int currentCatalogPrice = catalogSRO.getSellingPrice();
		int paidAmount = suborder.getPaidAmount() + suborder.getSdCash()
				+ suborder.getPromoValue();
		return (currentCatalogPrice + suborder.getShippingCharges()
				+ suborder.getShippingMethodCharges() - paidAmount);
	}

	@Override
	public void sendCancelledOrderFeedbackRequestEmail(
			CancelledOrderFeedbackDO cancelledOrderFeedbackDTO,
			String contentPath, String contextPath) {
		EmailMessage emailMessage = new EmailMessage(
				cancelledOrderFeedbackDTO.getEmail(),
				"CancelledOrderFeedbackEMail");
		emailMessage.addTemplateParam("orderId",
				cancelledOrderFeedbackDTO.getOrderId());
		emailMessage.addTemplateParam("orderCode",
				cancelledOrderFeedbackDTO.getOrderCode());
		emailMessage.addTemplateParam("userName",
				cancelledOrderFeedbackDTO.getUserName());
		emailMessage.addTemplateParam("products",
				cancelledOrderFeedbackDTO.getCancelledSuborders());
		emailMessage.addTemplateParam("userEmail",
				cancelledOrderFeedbackDTO.getEmail());
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMessage);
	}

	@Override
	public void sendCodOrderSubmissionEmail(SuborderSRO suborder,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(suborder.getOrderEmail(),
				"localCodConfirmation");
		emailMessage.addTemplateParam("suborder", suborder);
		emailMessage.addTemplateParam(
				"catalog",
				catalogService.getCatalog(
						new GetCatalogByIdRequest(suborder.getCatalogIdL()))
						.getCatalogSRO());
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(suborder.getOrderEmail()));
		send(emailMessage);
	}

	@Override
	public void sendOrderSubmissionEmail(SuborderSRO suborder,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(suborder.getOrderEmail(),
				"codOrderSubmission");
		emailMessage.addTemplateParam("suborder", suborder);
		emailMessage.addTemplateParam(
				"catalog",
				catalogService.getCatalog(
						new GetCatalogByIdRequest(suborder.getCatalogIdL()))
						.getCatalogSRO());
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(suborder.getOrderEmail()));
		send(emailMessage);
	}

	@Override
	public void sendCodOrderDispatchEmail(SuborderSRO suborder,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(suborder.getOrderEmail(),
				"codOrderDispatch");
		emailMessage.addTemplateParam("suborder", suborder);
		emailMessage.addTemplateParam(
				"catalog",
				catalogService.getCatalog(
						new GetCatalogByIdRequest(suborder.getCatalogIdL()))
						.getCatalogSRO());
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendSampleVoucherEmail(SuborderSRO suborder, ZoneSRO zone,
			String email, String contentPath) {/*
												 * EmailMessage emailMessage =
												 * new
												 * EmailMessage(StringUtils.split
												 * (email, ","),
												 * "sampleVoucherEmail");
												 * ServiceDealSRO deal =
												 * dealsService.getDealById(new
												 * GetServiceDealByIdRequest
												 * (suborder.getCatalogId())).
												 * getServiceDealSRO();
												 * emailMessage
												 * .addTemplateParam("suborder",
												 * suborder);
												 * emailMessage.addTemplateParam
												 * ("zone", zone);
												 * emailMessage.addTemplateParam
												 * ("deal", deal);
												 * emailMessage.addTemplateParam
												 * ("vendor", deal.getVendor());
												 * emailMessage
												 * .addTemplateParam(
												 * "contentPath", contentPath);
												 * send(emailMessage);
												 */
	}

	private void logProductOffers(PopularCatalogSRO catalogDTO, String type) {
		if (catalogDTO != null) {
			LOG.info("popular " + type + " dto: " + catalogDTO);
		} else {
			LOG.info("popular " + type + " pdt offer grps: IS NULL");
		}
	}

	@Override
	public void sendConfirmationEmail(String email, String contextPath,
			String contentPath, String confirmationLink) {
		// send confirmation email
		EmailMessage emailMessage = new EmailMessage(email, "confirmEmail");
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		send(emailMessage);
	}

	@Override
	public void sendAutoAccountConfirmationEmail(String email, String password,
			String name, String contextPath, String contentPath,
			String confirmationLink) {
		// send AutoAccountconfirmation email
		EmailMessage emailMessage = new EmailMessage(email,
				"confirmAutoAccountEmail");
		emailMessage.addTemplateParam("email", email);
		emailMessage.addTemplateParam("password", password);
		emailMessage.addTemplateParam("name", name);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		send(emailMessage);
	}

	@Override
	public void reSendAutoAccountConfirmationEmail(String email, String name,
			String contextPath, String contentPath, String confirmationLink) {
		EmailMessage emailMessage = new EmailMessage(email,
				"reConfirmAutoAccountEmail");
		emailMessage.addTemplateParam("email", email);
		emailMessage.addTemplateParam("name", name);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		send(emailMessage);
	}

	@Override
	public void sendNewConfirmationEmail(String email, Integer zoneId,
			String contextPath, String contentPath, String confirmationLink) {
	}

	@Override
	public void sendAffiliateConfirmationEmail(String email,
			String contextPath, String contentPath, String confirmationLink) {
		EmailMessage emailMessage = new EmailMessage(email,
				"affiliateConfirmEmail");
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		send(emailMessage);
	}

	@Override
	public void notifySubscribedUser(String email, ZoneSRO zone,
			String contextPath, String contentPath, String confirmationLink) {
		// send confirmation email
		EmailMessage emailMessage = new EmailMessage(email,
				"confirmationNotify");
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		emailMessage.addTemplateParam("zone", zone);
		send(emailMessage);
	}

	@Override
	public void send3rdPartyConfirmationEmail(String email, String contextPath,
			String contentPath, String confirmationLink, String source) {
		// send confirmation email
		EmailMessage emailMessage = new EmailMessage(email,
				"3rdPartyConfirmEmail");
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		emailMessage.addTemplateParam("source", source);
		send(emailMessage);
	}

	@Override
	public void sendUSConfirmationEmail(String email, String contextPath,
			String contentPath) {
		// send confirmation email
		EmailMessage emailMessage = new EmailMessage(email, "uswelcomeEmail");
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		send(emailMessage);
	}

	@Override
	public void sendForgotPasswordEmail(User user, String contextPath,
			String contentPath, String forgotPasswordLink) {
		EmailMessage emailMessage = new EmailMessage(user.getEmail(),
				"forgotPasswordEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("forgotPasswordLink", forgotPasswordLink);
		send(emailMessage);
	}

	@Override
	public void sendMovieVoucherEmail(String email,
			PartnerPromoCodeSRO promoCode, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = new EmailMessage(email, "movieVoucherEmail");
		emailMessage.addTemplateParam("emaemailMessageemailMessageil", email);
		emailMessage.addTemplateParam("issueDate", DateUtils.getCurrentTime());
		emailMessage.addTemplateParam("promoCode", promoCode);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		send(emailMessage);
	}

	@Override
	public void sendCustomerCareEmail(User user, ServiceDealSRO deal,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(user.getEmail(),
				"customerCareEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage
				.addTemplateParam("currentTime", DateUtils.getCurrentTime());
		emailMessage.addTemplateParam("deal", deal);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		send(emailMessage);
	}

	@Override
	public void sendPromoCodeEmail(String email, PromoCodeSRO prmCode,
			String contextPath, String contentPath) {
		EmailMessage freedealEmail = new EmailMessage(email, "freedealEmail");
		freedealEmail.addTemplateParam("promo", prmCode);
		freedealEmail.addTemplateParam("contentPath", contentPath);
		freedealEmail.addTemplateParam("contextPath", contextPath);
		send(freedealEmail);
	}

	// @Override
	// public void sendAffiliatePromoCodeEmail(String email, PromoCodeSRO
	// prmCode, Affiliate affiliate, String promoCodeTypeName, String
	// contextPath, String contentPath) {
	// EmailMessage affiliatePromoCodeEmail = new EmailMessage(email,
	// "affiliatePromoCodeEmail");
	// affiliatePromoCodeEmail.addTemplateParam("promo", prmCode);
	// affiliatePromoCodeEmail.addTemplateParam("affiliate", affiliate);
	// affiliatePromoCodeEmail.addTemplateParam("promoCodeTypeName",
	// promoCodeTypeName);
	// affiliatePromoCodeEmail.addTemplateParam("contentPath", contentPath);
	// affiliatePromoCodeEmail.addTemplateParam("contextPath", contextPath);
	// send(affiliatePromoCodeEmail);
	// }

	@Override
	public void sendFirstSubscriptionEmail(String email, PromoCodeSRO prmCode,
			String confirmationLink, String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(email,
				"firstTimeSubscriptionEmail");
		emailMessage.addTemplateParam("promo", prmCode);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		send(emailMessage);
	}

	@Override
	public void sendSurveyPromoCodeEmail(String email, PromoCodeSRO promoCode,
			String contextPath, String contentPath) {
		EmailMessage surveyPromoCodeEmail = new EmailMessage(email,
				"surveyPromoCodeEmail");
		surveyPromoCodeEmail.addTemplateParam("promo", promoCode);
		surveyPromoCodeEmail.addTemplateParam("contentPath", contentPath);
		surveyPromoCodeEmail.addTemplateParam("contextPath", contextPath);
		send(surveyPromoCodeEmail);
	}

	@Override
	public void sendCorporateEmail(Corporate corporate) {
		EmailMessage emailMessage = new EmailMessage("corporateEmail");
		emailMessage.addTemplateParam("corporate", corporate);
		send(emailMessage);
	}

	@Override
	public void sendCorporatebuyEmail(Object corporate) {
		EmailMessage emailMessage = new EmailMessage("corporateBuy");
		emailMessage.addTemplateParam("corporate", corporate);
		send(emailMessage);
	}

	@Override
	public void sendFeaturedEmail(GetFeatured featured) {
		EmailMessage emailMessage = new EmailMessage("getfeaturedEmail");
		emailMessage.addTemplateParam("featured", featured);
		send(emailMessage);
	}

	@Override
	public void sendFeaturedResponseEmail(GetFeatured featured, String email,
			String contentPath) {
		EmailMessage emailMessage = new EmailMessage(email,
				"getfeaturedresponseEmail");
		emailMessage.addTemplateParam("featured", featured);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendCustomerQueryEmail(CustomerQuery customerQuery) {
		EmailMessage emailMessage = new EmailMessage("customerQueryEmail");
		emailMessage.setFrom(customerQuery.getFullName() + "<"
				+ customerQuery.getEmail() + ">");
		emailMessage.addTemplateParam("customerQuery", customerQuery);
		send(emailMessage);
	}

	@Override
	public void sendCustomerFeedbackEmail(String name, String email,
			String city, String category, String message) {
		EmailMessage emailMessage = new EmailMessage("feedBack");
		emailMessage.setFrom(name + "<" + email + ">");
		emailMessage.addTemplateParam("name", name);
		emailMessage.addTemplateParam("email", email);
		emailMessage.addTemplateParam("city", city);
		emailMessage.addTemplateParam("category", category);
		emailMessage.addTemplateParam("message", message);
		send(emailMessage);
	}

	@Override
	public void sendInviteEmail(String refererEmail, String contextPath,
			String contentPath, String userName, String to, String from,
			String trackingUID) {
		EmailMessage emailMessage = new EmailMessage(to, from, refererEmail,
				"inviteEmail");
		emailMessage.addTemplateParam("refererEmail", refererEmail);
		userName = StringUtils.isNotEmpty(userName) ? userName : "Your friend";
		emailMessage.addTemplateParam("name", userName);
		emailMessage.addTemplateParam("trackingUID", trackingUID);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		send(emailMessage);
	}

	@Override
	public void sendShareDealEmail(String to, String from,
			String recipientName, ServiceDealSRO deal) {
		EmailMessage emailMessage = new EmailMessage(to, from, to, "shareDeal");
		emailMessage.addTemplateParam("recipientName", recipientName);
		emailMessage.addTemplateParam("deal", deal);
		send(emailMessage);
	}

	@Override
	public void sendDealShareEmailFromDealPage(String refererEmail,
			String userName, String from, String recipientName, String url,
			String dealDetail) {
		EmailMessage emailMessage = new EmailMessage(recipientName, from,
				refererEmail, "shareDealFromDealPage");
		emailMessage.addTemplateParam("recipientName", recipientName);
		emailMessage.addTemplateParam("name", userName);
		emailMessage.addTemplateParam("shareUrl", url);
		emailMessage.addTemplateParam("dealDetail", dealDetail);
		send(emailMessage);
	}

	@Override
	public void sendDealShareEmailFromPostBuy(String refererEmail,
			String userName, String from, String recipientName, String url,
			String dealDetail) {
		EmailMessage emailMessage = new EmailMessage(recipientName, from,
				refererEmail, "shareDealFromPostBuy");
		emailMessage.addTemplateParam("recipientName", recipientName);
		emailMessage.addTemplateParam("name", userName);
		emailMessage.addTemplateParam("shareUrl", url);
		emailMessage.addTemplateParam("dealDetail", dealDetail);
		send(emailMessage);
	}

	@Override
	public void sendGroupBuyEmail(Object groupDeal, String emailTemplate,
			String to) {
		EmailMessage emailMessage = new EmailMessage(emailTemplate);
		emailMessage.addTemplateParam("groupDeal", groupDeal);
		if (StringUtils.isNotEmpty(to)) {
			emailMessage.addRecepients(StringUtils.split(to, ","));
		}
		send(emailMessage);
	}

	@Override
	public void sendDailyMerchantEmail(String emailIds,
			List<SuborderSRO> suborders) {
		EmailMessage emailMessage = new EmailMessage("dailyMerchantEmail");
		emailMessage.addTemplateParam("suborders", suborders);

		if (StringUtils.isNotEmpty(emailIds)) {
			emailMessage.addRecepients(StringUtils.split(emailIds, ","));
		}
		send(emailMessage);
	}

	@Override
	public void sendBdayCashBackEmail(User user, int cashBackAmount,
			boolean newUser) {
		EmailMessage emailMessage = null;
		if (newUser) {
			emailMessage = new EmailMessage(user.getEmail(),
					"bdayNewUserCBEmail");
		} else {
			emailMessage = new EmailMessage(user.getEmail(),
					"bdayExistingUserCBEmail");
		}
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("cashBackAmount", cashBackAmount);
		send(emailMessage);
	}

	@Override
	public void sendSDCashEmail(User user, String emailTemplateName,
			int cashBackAmount, String confirmationLink, boolean newUser) {
		EmailMessage emailMessage = new EmailMessage(user.getEmail(),
				emailTemplateName);
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("cashBackAmount", cashBackAmount);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath("http://i.sdlcdn.com/"));
		if (newUser) {
			emailMessage.addTemplateParam("confirmationLink", confirmationLink);
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath("http://www.snapdeal.com"));
		}
		send(emailMessage);
	}

	@Override
	public void sendCashBackOfferEmail(User user, int cashBackAmount,
			boolean newUser, String confirmationLink, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = null;
		/*
		 * if (newUser) { emailMessage = new EmailMessage(user.getEmail(),
		 * "cashBackOfferNewUserEmail");
		 * emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		 * } else { emailMessage = new EmailMessage(user.getEmail(),
		 * "cashBackOfferExistingUserEmail"); }
		 */
		emailMessage = new EmailMessage(user.getEmail(), "cashBackOfferEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("cashBackAmount", cashBackAmount);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(user.getEmail()));
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendSdCashBackRewardOnPurchaseEmail(OrderSRO order,
			UserSRO user, int sdCash, Long purchase, boolean newUser,
			String confirmationLink, String contextPath, String contentPath) {
		EmailMessage emailMessage = null;
		/*
		 * if (newUser) { emailMessage = new EmailMessage(user.getEmail(),
		 * "sdCashRewardOnPurchaseNewUserEmail");
		 * emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		 * } else { emailMessage = new EmailMessage(user.getEmail(),
		 * "sdCashRewardOnPurchaseExistingUserEmail"); }
		 */
		emailMessage = new EmailMessage(user.getEmail(),
				"sdCashRewardOnPurchaseEmail");
		emailMessage.addTemplateParam("order", order);
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("sdCash", sdCash);
		emailMessage.addTemplateParam("purchase", purchase);
		emailMessage.addTemplateParam("newUser", newUser);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		send(emailMessage);
	}

	@Override
	public void sendEmployeeSdCashRewardEmail(User user, int sdCash,
			boolean newUser, String confirmationLink, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = null;
		/*
		 * if (newUser) { emailMessage = new EmailMessage(user.getEmail(),
		 * "employeeSdCashRewardNewUser");
		 * emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		 * } else { emailMessage = new EmailMessage(user.getEmail(),
		 * "employeeSdCashRewardExistingUser"); }
		 */
		emailMessage = new EmailMessage(user.getEmail(), "employeeSdCashReward");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(user.getEmail()));
		emailMessage.addTemplateParam("cashBackAmount", sdCash);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendReferralBenefitEmail(User user, int sdCashValue,
			int friendsReferred, int noOfConversions, boolean newUser,
			String confirmationLink, List<String> referredUserEmails,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = null;
		if (newUser) {
			emailMessage = new EmailMessage(user.getEmail(),
					"referralRewardNewUserEmail");
		} else {
			emailMessage = new EmailMessage(user.getEmail(),
					"referralRewardExistingUserEmail");
		}
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("sdCashValue", sdCashValue);
		emailMessage.addTemplateParam("friendsReferred", friendsReferred);
		emailMessage.addTemplateParam("noOfConversions", noOfConversions);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		emailMessage.addTemplateParam("referredUserEmails", referredUserEmails);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendNewSubscriberReferralOneTimeEmail(User user,
			String confirmationLink, List<String> referredUserEmails,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = null;
		emailMessage = new EmailMessage(user.getEmail(),
				"newSubscriberReferralOneTimeEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		emailMessage.addTemplateParam("referredUserEmails", referredUserEmails);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendMobileOrderSubmissionEmail(OrderSRO order,
			ServiceDealSRO deal, String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"mobileOrderSubmission");
		emailMessage.addTemplateParam("dealTransaction", order);
		emailMessage.addTemplateParam("deal", deal);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendMobileCustomerCareEmail(OrderSRO order,
			ServiceDealSRO deal, String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage("customerCareMobileEmail");
		emailMessage
				.addTemplateParam("currentTime", DateUtils.getCurrentTime());
		emailMessage.addTemplateParam("order", order);
		emailMessage.addTemplateParam("deal", deal);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("contextPath", contextPath);
		send(emailMessage);
	}

	@Override
	public void sendGeneralSDCashBackEmail(User user, Integer sdCashValue,
			boolean newUser, String confirmationLink, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = null;
		/*
		 * if (newUser) { emailMessage = new EmailMessage(user.getEmail(),
		 * "sdCashOnlyNewUserEmail"); } else { emailMessage = new
		 * EmailMessage(user.getEmail(), "sdCashOnlyExistingUserEmail"); }
		 */
		emailMessage = new EmailMessage(user.getEmail(), "sdCashOnlyEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("cashBackAmount", sdCashValue);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(user.getEmail()));
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendAppreciationAckSDCashEmail(User user, Integer sdCashValue,
			boolean newUser, String confirmationLink, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = null;
		/*
		 * if (newUser) { emailMessage = new EmailMessage(user.getEmail(),
		 * "appreciationSDCashEmailNewUser");
		 * emailMessage.addTemplateParam("confirmationLink", confirmationLink);
		 * } else { emailMessage = new EmailMessage(user.getEmail(),
		 * "appreciationSDCashEmailExistingUser"); }
		 */
		emailMessage = new EmailMessage(user.getEmail(),
				"appreciationSDCashEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(user.getEmail()));
		emailMessage.addTemplateParam("sdCashAmount", sdCashValue);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendOrderRefundEmail(OrderSRO order, String shippingMethodCode,
			List<String> cancelledProducts, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"orderRefundEmail");
		emailMessage.addTemplateParam("order", order);
		emailMessage.addTemplateParam("shippingMethodCode", shippingMethodCode);
		emailMessage.addTemplateParam("cancelledProducts", cancelledProducts);
		emailMessage.addTemplateParam("summaryLink",
				order.getOrderSummaryLink());
		emailMessage.addTemplateParam("date", DateUtils.getCurrentDate());
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(order.getEmail()));
		send(emailMessage);
	}

	@Override
	public void sendOrderRefundEmailNew(
			OrderCancellationEmailSRO orderCancellationEmailSRO) {
		String contextPath = CacheManager.getInstance()
				.getCache(UMSPropertiesCache.class)
				.getContextPath(Constants.DEFAULT_CONTEXT_PATH);
		String contentPath = CacheManager.getInstance()
				.getCache(UMSPropertiesCache.class)
				.getContextPath(Constants.DEFAULT_CONTENT_PATH);
		EmailMessage emailMessage = null;
		emailMessage = getRefundEmailMessage(orderCancellationEmailSRO);

		if (emailMessage != null) {
			emailMessage.addTemplateParam("orderCancellationEmailDTO",
					orderCancellationEmailSRO);
			emailMessage.addTemplateParam("contextPath", contextPath);
			emailMessage.addTemplateParam("contentPath", contentPath);
			emailMessage.addTemplateParam("confirmationLink",
					userService.getConfirmationLink(orderCancellationEmailSRO
							.getEmailId()));
			send(emailMessage);
		}
	}

	private EmailMessage getRefundEmailMessage(
			OrderCancellationEmailSRO orderCancellationEmailDTO) {
		if ((orderCancellationEmailDTO.getCancellationReasonCode()
				.equals(OrderCancellationRequestReasonSRO.Type.BULK_FRAUD
						.code()))
				|| (orderCancellationEmailDTO.getCancellationReasonCode()
						.equals(OrderCancellationRequestReasonSRO.Type.EXCESS_ORDERS
								.code()))
				|| (orderCancellationEmailDTO.getCancellationReasonCode()
						.equals(OrderCancellationRequestReasonSRO.Type.ADDRESS_FRAUD
								.code()))
				|| (orderCancellationEmailDTO.getCancellationReasonCode()
						.equals(OrderCancellationRequestReasonSRO.Type.STOCK_DELAYED
								.code()))) {
			return new EmailMessage(orderCancellationEmailDTO.getEmailId(),
					"orderCancellationRefundEmail");
		}

		else if ((orderCancellationEmailDTO.getCancellationReasonCode()
				.equals(OrderCancellationRequestReasonSRO.Type.OUT_OF_STOCK
						.code()))
				|| (orderCancellationEmailDTO.getCancellationReasonCode()
						.equals(OrderCancellationRequestReasonSRO.Type.NON_SERVICEABILITY
								.code()))
				|| (orderCancellationEmailDTO.getCancellationReasonCode()
						.equals(OrderCancellationRequestReasonSRO.Type.CUSTOMER_REQUEST
								.code()))
				|| (orderCancellationEmailDTO.getCancellationReasonCode()
						.equals(OrderCancellationRequestReasonSRO.Type.END_OF_LIFE
								.code()))) {
			return new EmailMessage(orderCancellationEmailDTO.getEmailId(),
					"newOrderCancellationRefundEmail");
		} else {
			return null;
		}
	}

	@Override
	public void sendRefundAndSDCashBackEmail(User user, OrderSRO order,
			String shippingMethodCode, List<String> cancelledProducts,
			Integer sdCashRefund, boolean newUser, String confirmationLink,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = null;
		emailMessage = new EmailMessage(user.getEmail(),
				"refundAndSDCashBackEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("cashBackAmount", sdCashRefund);
		emailMessage.addTemplateParam("order", order);
		emailMessage.addTemplateParam("shippingMethodCode", shippingMethodCode);
		emailMessage.addTemplateParam("cancelledProducts", cancelledProducts);
		emailMessage.addTemplateParam("date", DateUtils.getCurrentDate());
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(user.getEmail()));
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendVendorUserCreationEmail(User user, String contextPath,
			String contentPath) {
		EmailMessage emailMessage = new EmailMessage(user.getEmail(),
				"merchantAccountCreationEmail");
		emailMessage.addTemplateParam("user", user);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendCodOrderSubmissionEmailProduct(SuborderSRO suborder,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(suborder.getOrderEmail(),
				"codOrderSubmissionProduct");
		CatalogSRO catalogDTO = catalogService.getCatalog(
				new GetCatalogByIdRequest(suborder.getCatalogIdL()))
				.getCatalogSRO();
		emailMessage.addTemplateParam("dealTransaction", suborder);
		emailMessage.addTemplateParam("catalog", catalogDTO);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(suborder.getOrderEmail()));
		send(emailMessage);
	}

	@Override
	public void sendAuditMail(Audit audit, String useremailId) {
		// send mail
		EmailMessage email = new EmailMessage("auditlogcreation");
		email.addTemplateParam("type", audit.getEntityType());
		email.addTemplateParam("id", audit.getEntityId());
		email.addTemplateParam("changelog", audit.getChangeLog());
		email.addTemplateParam("comment", audit.getComments());
		email.addTemplateParam("user", useremailId);
		email.addTemplateParam("time", audit.getModifiedTime());
		send(email);
	}

	@Override
	public void sendFeedbackMail(SuborderSRO suborder, CatalogSRO catalog,
			String contentPath, String contextPath, boolean redeemed)
			throws TransportException {

		if (CatalogType.DEAL.type().equals(catalog.getCatalogType())
				|| CatalogType.GETAWAY.type().equals(catalog.getCatalogType())) {
			EmailMessage emailMessage = new EmailMessage(
					suborder.getOrderEmail(), "dealfeedbackEmail");
			emailMessage.addTemplateParam("contentPath", contentPath);
			emailMessage.addTemplateParam("contextPath", contextPath);
			emailMessage.addTemplateParam("catalog", catalog);
			emailMessage.addTemplateParam("suborder", suborder);
			emailMessage.addTemplateParam("redeemed", redeemed);
			send(emailMessage);
		} else {
			ProductOfferSRO poSRO = (ProductOfferSRO) catalog;
			String vendorCode = "";
			try {
				GetSellerProductMappingBySUPCResponse response = ipmsClientService
						.getSellerProductMappingsBySupc(new GetSellerProductMappingBySUPCRequest(
								poSRO.getFirstProduct().getSupc()));
				if (response.isSuccessful() == true
						&& !response.getSellerProductMappingSROs().isEmpty()) {
					vendorCode = response.getSellerProductMappingSROs().get(0)
							.getSellerCode();
				}
			} catch (TransportException e) {
				LOG.error(
						"Unable to execute web service call getProductVendorMappingBySUPC: ",
						e);
			}
			ProductVendorSRO vendor = null;
			try {
				vendor = productVendorService
						.getProductVendorByCode(vendorCode);
			} catch (Exception e) {
				LOG.error("Unable to fetch vendor for vendorCode: "
						+ vendorCode, e);
			}
			EmailMessage emailMessage = new EmailMessage(
					suborder.getOrderEmail(), "productfeedbackEmail");
			emailMessage.addTemplateParam("contentPath", contentPath);
			emailMessage.addTemplateParam("contextPath", contextPath);
			emailMessage.addTemplateParam("catalog", catalog);
			emailMessage.addTemplateParam("merchantName",
					vendor == null ? "N/A" : vendor.getName());
			emailMessage.addTemplateParam("suborder", suborder);
			emailMessage.addTemplateParam("redeemed", redeemed);
			send(emailMessage);
		}

	}

	@Override
	public void sendAutoCaptureStatusEmail(String pg, String date,
			Map<String, String> failedOrders) {
		EmailMessage emailMsg = new EmailMessage("autoCaptureTaskStatusEmail");
		emailMsg.addTemplateParam("pg", pg);
		emailMsg.addTemplateParam("date", date);
		emailMsg.addTemplateParam("failedOrders", failedOrders);
		send(emailMsg);
	}

	private OrderDetailDTO prepareOrderDetailDTOFromOrder(OrderSRO order) {
		List<OrderItemDetailDTO> orderItems = new ArrayList<OrderItemDetailDTO>();
		Map<Integer, Integer> catalogIdtoQuantityMap = order
				.getCatalogIdtoQuantityMap();
		int totalShippingCharges = 0;
		String contextPath = CacheManager.getInstance()
				.getCache(UMSPropertiesCache.class)
				.getContextPath("http://www.snapdeal.com");
		for (Integer catalogId : catalogIdtoQuantityMap.keySet()) {
			List<SuborderSRO> subordersForCatalog = order
					.getSubordersForCatalog(catalogId);
			Integer itemQuantity = catalogIdtoQuantityMap.get(catalogId);
			CatalogSRO catalogDTO = catalogService.getCatalog(
					new GetCatalogByIdRequest((long) catalogId))
					.getCatalogSRO();
			if (catalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						catalogId);
				return null;
			}
			ZoneSRO zone = CacheManager.getInstance()
					.getCache(ZonesCache.class).getZoneById(order.getZoneId());
			String pageUrl = contextPath
					+ "/"
					+ catalogService.getPageURLForCatalog(
							new GetPageURLForCatalogRequest(catalogDTO.getId(),
									zone.getCity().getId())).getPageURL();
			OrderItemDetailDTO orderItemDetailDTO = new OrderItemDetailDTO(
					itemQuantity, pageUrl, order.getSellingPrice(), catalogDTO);
			orderItemDetailDTO.setSellingPrice(catalogDTO.getSellingPrice());

			if (SuborderTypeSRO.Type.PREBOOK.code().equals(
					subordersForCatalog.get(0).getSuborderType().getCode())) {
				orderItemDetailDTO.setPrebook(true);
			}
			int promoValue = 0, sdCash = 0, shippingCharges = 0, shippingMethodCharges = 0;
			List<String> suborderCodeList = new ArrayList<String>();
			for (SuborderSRO suborder : subordersForCatalog) {
				suborderCodeList.add(suborder.getCode());
				promoValue += suborder.getPromoValue();
				sdCash += suborder.getSdCash();
				shippingCharges += suborder.getShippingCharges();
				shippingMethodCharges += suborder.getShippingMethodCharges();
			}
			orderItemDetailDTO.setSuborderCodeList(suborderCodeList);
			orderItemDetailDTO.setPromoValue(promoValue);
			orderItemDetailDTO.setSdCash(sdCash);
			orderItemDetailDTO.setShippingCharges(shippingCharges);
			totalShippingCharges += shippingCharges;
			orderItemDetailDTO.setShippingMethodCharges(shippingMethodCharges);
			if (subordersForCatalog.get(0).getSellingPrice() != null) {
				orderItemDetailDTO.setPendingPrebookAmount((subordersForCatalog
						.get(0).getSellingPrice() - subordersForCatalog.get(0)
						.getOfferPrice())
						* itemQuantity);
				orderItemDetailDTO.setTotalAmount(subordersForCatalog.get(0)
						.getSellingPrice() * itemQuantity);
				orderItemDetailDTO.setSellingPrice(subordersForCatalog.get(0)
						.getSellingPrice());
			} else {
				orderItemDetailDTO.setSellingPrice(null);
				orderItemDetailDTO.setPendingPrebookAmount(null);
			}
			if (subordersForCatalog.size() > 0) {
				String suborderCode = subordersForCatalog.get(0).getCode();
				GetShippingInfoForSuborderRequest shpReq = new GetShippingInfoForSuborderRequest();
				shpReq.setSubOrderCode(suborderCode);
				GetShippingInfoForSuborderResponse shpRes = shippingService
						.getShippingInfoForSuborder(shpReq);
				ShippingPackageSRO pack = shpRes.getDispatchedShipment();
				if (pack != null) {
					if (StringUtils.isNotEmpty(pack.getStatusForExternal())
							&& pack.isInProgress()) {
						orderItemDetailDTO.setExpectedDeliveryDate(DateUtils
								.dateToString(pack.getEstimatedShippingDate(),
										"MMM dd, yyyy"));// TODO change the
															// orderItemDetailDTO's
															// variable name:
															// expectedDeliveryDate
															// to
															// expectedShipDate
					} else {
						orderItemDetailDTO.setExpectedDeliveryDate("");
					}
				} else if (shpRes.getExpectedShippingDate() != null) {
					orderItemDetailDTO.setExpectedDeliveryDate(DateUtils
							.dateToString(shpRes.getExpectedShippingDate(),
									"MMM dd, yyyy"));
				} else {
					LOG.info("Order Id: "
							+ order.getCode()
							+ " not fulfilled by shipping, still sending the order summary email");
					if (SuborderTypeSRO.Type.PREBOOK.code().equals(
							subordersForCatalog.get(0).getSuborderType()
									.getCode())) {
						if (SuborderStatusSRO.Type.WAITING_FOR_FULFILLMENT
								.code().equals(
										subordersForCatalog.get(0)
												.getSuborderStatus().getCode())) {
							orderItemDetailDTO
									.setExpectedDeliveryDate("Will Ship Once Released");
						} else if (SuborderStatusSRO.Type.WAITING_FOR_FULL_PAYMENT
								.code().equals(
										subordersForCatalog.get(0)
												.getSuborderStatus().getCode())) {
							orderItemDetailDTO
									.setExpectedDeliveryDate("Awaiting Full Payment");
						}
					} else {
						Date promisedShipDate = DateUtils.addToDate(
								DateUtils.getCurrentTime(),
								Calendar.DAY_OF_MONTH,
								getPromisedShipDays(catalogDTO));
						orderItemDetailDTO
								.setExpectedDeliveryDate(DateUtils
										.dateToString(promisedShipDate,
												"MMM dd, yyyy"));
					}
				}
			} else {
				orderItemDetailDTO.setExpectedDeliveryDate("");
			}
			orderItems.add(orderItemDetailDTO);
		}

		OrderDetailDTO orderDetail = new OrderDetailDTO();
		orderDetail.setOrderId(order.getId());
		orderDetail.setOrderCode(order.getCode());
		String orderSummaryUrl = contextPath + "/orderSummary?code="
				+ EncryptionUtils.md5Encode(order.getCode(), order.getMobile())
				+ "&order=" + order.getCode();
		orderDetail.setOrderSummaryUrl(orderSummaryUrl);
		orderDetail.setPurchaseDate(DateUtils.dateToString(order.getCreated(),
				"yyyy-MM-dd HH:mm:ss"));
		orderDetail.setStatus(order.getOrderStatus().getValue());
		orderDetail.setMobile(order.getMobile());
		orderDetail.setEmailId(order.getEmail());
		orderDetail.setTotalAmount(order.getSellingPrice());
		orderDetail.setPaidAmount(order.getPaidAmount());
		orderDetail.setSdCash(order.getSdCash());
		orderDetail.setExternalCashbackValue(order.getExternalCashbackValue());
		orderDetail.setInternalCashbackValue(order.getInternalCashbackValue());
		orderDetail.setPromoValue(order.getPromoValue());
		orderDetail.setCustomerName(order.getCustomerName());
		orderDetail.setZoneId(order.getZoneId());
		orderDetail.setOrderItems(orderItems);
		orderDetail.setOrderType(order.getOrderType().getCode());
		orderDetail.setShippingCharges(totalShippingCharges);

		GetOrderMappingResponse response = orderService
				.getOrderMapping(new GetOrderMappingRequest(order.getCode()));
		if (response.getPrimaryOrderSRO() != null) {
			orderDetail.setChildOrder(true);
			String parentOrderCode = response.getPrimaryOrderSRO().getCode();
			orderDetail.setParentOrderCode(parentOrderCode);
		}

		AddressDetailSRO address = order.getAddressDetail();
		if (address != null) {
			orderDetail.setShippingDetailsRequired(true);
			orderDetail.setShippingName(address.getName());
			orderDetail.setAddressLine1(address.getAddressLine1());
			orderDetail.setAddressLine2(address.getAddressLine2());
			orderDetail.setCity(address.getCity());
			orderDetail.setState(address.getState());
			orderDetail.setPincode(address.getPincode());
			orderDetail.setShippingMobile(address.getMobile());
			orderDetail.setShippingCharges(order.getShippingCharges());
			orderDetail.setShippingMethodCharges(order
					.getShippingMethodCharges());
		}
		// set default shipping method
		orderDetail.setShippingMethod(ShippingMethodType.STANDARD.code());
		for (SuborderSRO suborder : order.getSuborders()) {
			if (ShippingMethodType.CASH_ON_DELIVERY.code().equals(
					suborder.getShippingMethodCode())) {
				orderDetail.setShippingMethod(suborder.getShippingMethodCode());
				break;
			}
		}
		orderDetail.setLastTransaction(prepareOrderTransactionDetailDTO(order));
		return orderDetail;
	}

	private Integer getPromisedShipDays(CatalogSRO catalogDTO) {
		if (CatalogType.PRODUCT.type().equals(catalogDTO.getCatalogType())) {
			ProductSRO productSRO = ((ProductOfferSRO) catalogDTO)
					.getFirstProduct(); // TODO: Handle case when multiple
										// products mapped to one product offer
			int maxEstimateDays = 0;
			List<SellerProductMappingSRO> pvpmSROs = new ArrayList<SellerProductMappingSRO>();
			try {
				GetSellerProductMappingBySUPCResponse response = ipmsClientService
						.getSellerProductMappingsBySupc(new GetSellerProductMappingBySUPCRequest(
								productSRO.getSupc()));
				if (response.isSuccessful() == true
						&& !response.getSellerProductMappingSROs().isEmpty()) {
					pvpmSROs = response.getSellerProductMappingSROs();
				}
			} catch (TransportException e) {
				LOG.error(
						"Unable to execute web service call getProductVendorMappingBySUPC: ",
						e);
			}
			for (SellerProductMappingSRO pvpm : pvpmSROs) {
				int pSla = 0, wSla = 0;
				if (pvpm.getProcurementSla() != null) {
					pSla = pvpm.getProcurementSla();
				}
				if (pvpm.getWarehouseProcessingSla() != null) {
					wSla = pvpm.getWarehouseProcessingSla();
				}
				int estimatedDays = pSla + wSla;
				if (estimatedDays > maxEstimateDays) {
					maxEstimateDays = estimatedDays;
				}
			}
			if (maxEstimateDays != 0) {
				return maxEstimateDays;
			}
		}
		return CacheManager.getInstance().getCache(UMSPropertiesCache.class)
				.getDefaultShippingTime();
	}

	private OrderTransactionDetailDTO prepareOrderTransactionDetailDTO(
			OrderSRO order) {
		OrderTransactionSRO transaction = orderService
				.getLastTransactionForOrder(
						new GetLastTransactionForOrderRequest(order))
				.getOrderTransactionSRO();

		if (transaction != null) {
			GetPaymentModeByCodeResponse response = paymentClient
					.getPaymentModeByCode(new GetPaymentModeByCodeRequest(
							transaction.getCode()));
			PaymentModeSRO pmSRO = response.getPaymentMode();
			String paymentMode = null;
			String paymentModeSubtype = null;
			if (pmSRO != null) {
				paymentMode = pmSRO.getDisplayName();

				if (transaction.getPaymentModeSubtype() != null) {
					Set<PaymentModeSubtypeSRO> pMsubtypes = pmSRO
							.getPaymentModeSubtypes();
					if (pMsubtypes != null) {
						for (PaymentModeSubtypeSRO pMSubtype : pMsubtypes)
							if (pMSubtype.getCode().equals(
									transaction.getPaymentModeSubtype()))
								paymentModeSubtype = pMSubtype.getDisplayName();
					}
				}
			}
			OrderTransactionDetailDTO orderTransactionDTO = new OrderTransactionDetailDTO(
					transaction.getPaymentStatus().getValue(),
					transaction.getCode(), paymentMode, paymentModeSubtype,
					transaction.getPaymentGateway().getName(),
					transaction.getPgOutgoingParams(),
					transaction.getPgIncomingParams(),
					transaction.getPgResponseMessage(),
					transaction.getPaymentAmount(), transaction.getCreated());
			return orderTransactionDTO;

		}
		return null;
	}

	private SuborderDetailDTO prepareSuborderDetailDTOFromSuborder(
			SuborderSRO suborder) {
		SuborderDetailDTO suborderDTO = new SuborderDetailDTO();

		CatalogSRO catalogDTO = catalogService.getCatalog(
				new GetCatalogByIdRequest(suborder.getCatalogIdL()))
				.getCatalogSRO();
		if (suborder instanceof GetawaySuborderSRO) {
			GetawaySuborderSRO getawaySuborder = (GetawaySuborderSRO) suborder;
			suborderDTO.setValidUpto(getawaySuborder.getValidUpto());
			suborderDTO.setMerchantPromoCode(getawaySuborder
					.getMerchantPromoCode());
		} else if (suborder instanceof DealSuborderSRO) {
			DealSuborderSRO dealSuborder = (DealSuborderSRO) suborder;
			suborderDTO.setValidUpto(dealSuborder.getValidUpto());
			suborderDTO.setMerchantPromoCode(dealSuborder
					.getMerchantPromoCode());
		}
		return setCommonSuborderDetails(suborder, suborderDTO, catalogDTO);
	}

	private SuborderDetailDTO setCommonSuborderDetails(SuborderSRO suborder,
			SuborderDetailDTO suborderDTO, CatalogSRO catalogDTO) {
		suborderDTO.setCode(suborder.getCode());
		suborderDTO.setItemName(catalogDTO.getName());
		suborderDTO.setItemTitle(catalogDTO.getTitle());
		suborderDTO.setSellingPrice(catalogDTO.getSellingPrice());
		suborderDTO.setFreebies(catalogDTO.getFreebies());
		ZoneSRO zone = CacheManager.getInstance().getCache(ZonesCache.class)
				.getZoneById(suborder.getZoneId());
		suborderDTO.setPageUrl(catalogService.getPageURLForCatalog(
				new GetPageURLForCatalogRequest(catalogDTO.getId(), zone
						.getCity().getId())).getPageURL());
		suborderDTO.setPaidAmount(suborder.getPaidAmount());
		suborderDTO.setCreated(suborder.getCreated());
		suborderDTO.setCustomerName(suborder.getCustomerName());
		suborderDTO.setCatalog(catalogDTO);
		suborderDTO.setStatusCode(suborder.getSuborderStatus().getCode());
		setExpectedShippingDate(suborder, suborderDTO, catalogDTO);
		return suborderDTO;
	}

	private void setExpectedShippingDate(SuborderSRO suborder,
			SuborderDetailDTO suborderDTO, CatalogSRO catalogDTO) {
		String suborderCode = suborder.getCode();
		GetShippingInfoForSuborderRequest shpReq = new GetShippingInfoForSuborderRequest();
		shpReq.setSubOrderCode(suborderCode);
		GetShippingInfoForSuborderResponse shpRes = shippingService
				.getShippingInfoForSuborder(shpReq);
		ShippingPackageSRO pack = shpRes.getDispatchedShipment();
		if (pack != null) {
			if (StringUtils.isNotEmpty(pack.getStatusForExternal())
					&& pack.isInProgress()) {
				suborderDTO.setExpectedDeliveryDate(DateUtils.dateToString(
						pack.getEstimatedShippingDate(), "MMM dd, yyyy"));// TODO
																			// change
																			// the
																			// orderItemDetailDTO's
																			// variable
																			// name:
																			// expectedDeliveryDate
																			// to
																			// expectedShipDate
			} else {
				suborderDTO.setExpectedDeliveryDate("");
			}
		} else if (shpRes.getExpectedShippingDate() != null) {
			suborderDTO.setExpectedDeliveryDate(DateUtils.dateToString(
					shpRes.getExpectedShippingDate(), "MMM dd, yyyy"));
		} else {
			LOG.info("Order Id: "
					+ suborder.getOrderCode()
					+ " not fulfilled by shipping, still sending the order summary email");
			Date promisedShipDate = DateUtils.addToDate(
					DateUtils.getCurrentTime(), Calendar.DAY_OF_MONTH,
					getPromisedShipDays(catalogDTO));
			suborderDTO.setExpectedDeliveryDate(DateUtils.dateToString(
					promisedShipDate, "MMM dd, yyyy"));
		}
	}

	@Override
	public void sendWayBillNumberExhaustionEmail(String shippingProviderName,
			String shippingMethodName, long countLeft) {
		EmailMessage emailMsg = new EmailMessage("wayBillNumberExhaustionEmail");
		emailMsg.addTemplateParam("shippingProvider", shippingProviderName);
		emailMsg.addTemplateParam("shippingMethod", shippingMethodName);
		emailMsg.addTemplateParam("countLeft", String.valueOf(countLeft));
		send(emailMsg);
	}

	@Override
	public void sendPendingResponseEmail(OrderSRO order) {
		EmailMessage emailMsg = new EmailMessage(order.getEmail(),
				"pendingCODOrderEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		emailMsg.addTemplateParam("orderDTO", orderDetailDTO);
		emailMsg.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMsg.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMsg);
	}

	@Override
	public void sendCODOrderEmail(String orderCode) {
		OrderSRO order = orderService.getOrderByCode(
				new GetOrderByCodeRequest(orderCode)).getOrderSRO();
		EmailMessage emailMsg = new EmailMessage(order.getEmail(),
				"codOrderEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		emailMsg.addTemplateParam("orderDTO", orderDetailDTO);
		emailMsg.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMsg.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMsg);
	}

	/*
	 * @Override public void
	 * sendProductWorkflowRejectionEmail(List<ProductWorkflowDTO> workflowDTOs,
	 * String role) { EmailMessage msg = new
	 * EmailMessage("productWorkflowRejectionEmail"); List<String> bds = new
	 * ArrayList<String>(); for (ProductWorkflowDTO workflowDTO : workflowDTOs)
	 * { if (StringUtils.isNotEmpty(workflowDTO.getActionBy())) {
	 * bds.add(workflowDTO.getActionBy()); } } msg.addRecepients(bds);
	 * msg.addTemplateParam("workflowDTOs", workflowDTOs);
	 * msg.addTemplateParam("role", role); send(msg); }
	 */

	@Override
	public void sendValentineEmail(String name, String recipient, String url) {
		EmailMessage emailMessage = new EmailMessage(recipient,
				"valentineEmail");
		emailMessage.addTemplateParam("name", name);
		emailMessage.addTemplateParam("recipient", recipient);
		emailMessage.addTemplateParam("url", url);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		send(emailMessage);
	}

	@Override
	public void cartDropoutNotificationWithin24hrs(String email, String name,
			String cartId, List<CartItemSRO> cartItemSROs) {
		EmailMessage emailMessage = new EmailMessage(email,
				"cartDropoutWithin24hr");
		emailMessage.addTemplateParam("recipientName", name);
		emailMessage.addTemplateParam("cartItems", cartItemSROs);
		emailMessage.addTemplateParam("cartID", cartId);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(emailMessage);
	}

	@Override
	public void cartDropoutNotificationWithin24hrTo15days(String email,
			String name, String cartId, List<CartItemSRO> cartItemSROs,
			String catalogText) {
		EmailMessage emailMessage = new EmailMessage(email,
				"cartDropoutWithin24hrTo15days");
		emailMessage.addTemplateParam("recipientName", name);
		emailMessage.addTemplateParam("cartItems", cartItemSROs);
		emailMessage.addTemplateParam("cartID", cartId);
		emailMessage.addTemplateParam("catalogText", catalogText);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(emailMessage);
	}

	@Override
	public void cartDropoutNotificationAfter15days(String email, String name,
			String cartId, List<CartItemSRO> cartItemSROs) {
		EmailMessage emailMessage = new EmailMessage(email,
				"cartDropoutAfter15days");
		emailMessage.addTemplateParam("recipientName", name);
		emailMessage.addTemplateParam("cartItems", cartItemSROs);
		emailMessage.addTemplateParam("cartID", cartId);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(emailMessage);
	}

	@Override
	public void cartDropOutNotificationMail(String email, String name,
			String cartId, List<CartItemSRO> cartItemSROs,
			Set<UMSPOGSRO> umsPOGSROs) {
		EmailMessage emailMessage = new EmailMessage(email,
				"cartDropOutNotificationMail");
		emailMessage.addTemplateParam("recipientName", name);
		emailMessage.addTemplateParam("cartItems", cartItemSROs);
		emailMessage.addTemplateParam("cartID", cartId);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("umsPOGSROs", umsPOGSROs);
		send(emailMessage);
	}

	@Override
	public void sendBulkUploadResultEmail(List<BulkUploadResultDTO> resultDTOs,
			String fileName, String email) {
		EmailMessage message = new EmailMessage(email, "bulkUploadResultEmail");
		message.addTemplateParam("resultDTOs", resultDTOs);
		message.addTemplateParam("file", fileName);
		message.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		message.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(message);
	}

	@Override
	public void sendUserSDCashHistory(String userEmail, String userName,
			int sdcashAtBegOfMonth, int sdcashEarningOfMonth,
			int sdcashUsedThisMonth, int sdcashAvailable, int sdCashExpired,
			DateRange range, int currSDCash, String linkToBeSent,
			String contextPath, String contentPath) {
		EmailMessage emailMessage = new EmailMessage(userEmail,
				"sdcashHistoryEmail");
		if (userName == null) {
			userName = "";
		}
		emailMessage.addTemplateParam("recipientName", userName);
		emailMessage.addTemplateParam("sdcashAtBegOfMonth", sdcashAtBegOfMonth);
		emailMessage.addTemplateParam("sdcashEarningOfMonth",
				sdcashEarningOfMonth);
		emailMessage.addTemplateParam("sdcashUsedThisMonth",
				sdcashUsedThisMonth);
		emailMessage.addTemplateParam("sdcashExpiredThisMonth", sdCashExpired);
		emailMessage.addTemplateParam("sdcashAvailable", currSDCash);
		emailMessage.addTemplateParam("startDate",
				DateUtils.dateToString(range.getStart(), "MMM dd, yyyy"));
		emailMessage.addTemplateParam("endDate",
				DateUtils.dateToString(range.getEnd(), "MMM dd, yyyy"));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		emailMessage.addTemplateParam("sdcashAtEndOfMonth", sdcashAvailable);
		emailMessage.addTemplateParam("finalPage", linkToBeSent);
		emailMessage.addTemplateParam("confirmationLink",
				userService.getConfirmationLink(userEmail));
		send(emailMessage);
	}

	@Override
	public void sendPromoCodeOnPurchaseEmail(OrderSRO order,
			PromoCodeSRO promoCode, String contextPath, String contentPath) {
		EmailMessage emailMessage = null;
		emailMessage = new EmailMessage(order.getEmail(),
				"promoCodeOnPurchaseEmail");
		emailMessage.addTemplateParam("order", order);
		emailMessage.addTemplateParam("promoCode", promoCode);
		emailMessage.addTemplateParam("contextPath", contextPath);
		emailMessage.addTemplateParam("contentPath", contentPath);
		send(emailMessage);
	}

	@Override
	public void sendFinanceVendorPaymentDetailsGenerationEmail(String email,
			String path) {
		EmailMessage emailMessage = null;
		emailMessage = new EmailMessage(email, "financeVendorPaymentFileEmail");
		emailMessage.addTemplateParam("path", path);
		send(emailMessage);
	}

	@Override
	public void sendZendeskUploadedFileEmail(String email, String path) {
		EmailMessage emailMessage = null;
		emailMessage = new EmailMessage(email, "zendeskUploadFileEmail");
		emailMessage.addTemplateParam("path", path);
		send(emailMessage);
	}

	@Override
	public void sendHelpDeskEmail(String subject, String name, String email,
			String mobile, String orderId, String itemName, String comments,
			String ticketId) {
		EmailMessage emailMessage = new EmailMessage(email,
				"helpDeskResponseEmail");
		emailMessage.addTemplateParam("subject", subject);
		emailMessage.addTemplateParam("name", name);
		emailMessage.addTemplateParam("mobile", mobile);
		emailMessage.addTemplateParam("email", email);
		emailMessage.addTemplateParam("orderId", orderId);
		emailMessage.addTemplateParam("itemName", itemName);
		emailMessage.addTemplateParam("query", comments);
		emailMessage.addTemplateParam("ticketId", ticketId);
		send(emailMessage);

	}

	@Override
	public void sendMultiVendorMappingResults(
			List<ProductMultiVendorMappingResultDTO> resultDTOs, String fileName) {
		EmailMessage email = new EmailMessage("multiVendorMappingResultEmail");
		email.addTemplateParam("resultDTOs", resultDTOs);
		email.addTemplateParam("file", fileName);
		email.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		email.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(email);
	}

	@Override
	public void sendReviewRequestMail(List<SuborderSRO> suborders,
			String contentPath, String contextPath) throws TransportException {
		Set<Long> addedProducts = new HashSet<Long>();
		boolean send = false;
		Collections.sort(suborders, new Comparator<SuborderSRO>() {

			@Override
			public int compare(SuborderSRO arg0, SuborderSRO arg1) {
				if (arg0.getSellingPrice() < arg1.getSellingPrice())
					return -1;
				if (arg0.getSellingPrice() < arg1.getSellingPrice())
					return 1;
				else
					return 0;
			}
		});
		Map<String, String> urlMap = new HashMap<String, String>();
		Map<String, String> imgMap = new HashMap<String, String>();
		for (SuborderSRO suborder : suborders) {

			ProductOfferSRO po = (ProductOfferSRO) catalogClientService
					.getCatalogContentById(
							new GetCatalogByIdRequest(suborder.getCatalogIdL()))
					.getCatalogSRO();

			po.getProductCategoryDTOs();
			if (!addedProducts.contains(po.getId())) {
				addedProducts.add(po.getId());
				ProductSRO product = po.getProductDTOs().iterator().next();
				urlMap.put(
						product.getName(),
						"/reviews/writeReviewEmail?pogId="
								+ po.getProductOfferGroupId() + "&email="
								+ suborder.getOrderEmail() + "&suborderCode="
								+ suborder.getCode());
				imgMap.put(product.getName(), po.getContentDTO()
						.getMainPictures().get(0).getContentPath());
				send = true;
			}
		}
		if (send) {
			ProductOfferSRO po = (ProductOfferSRO) catalogClientService
					.getCatalogContentById(
							new GetCatalogByIdRequest(suborders.get(0)
									.getCatalogIdL())).getCatalogSRO();
			EmailMessage emailMessage = new EmailMessage(suborders.get(0)
					.getOrderEmail(), "reviewRequestEmail");
			emailMessage.addTemplateParam("customerName", suborders.get(0)
					.getCustomerName());
			emailMessage.addTemplateParam("products", urlMap);
			emailMessage.addTemplateParam("images", imgMap);
			emailMessage.addTemplateParam("subcat", po.getProductCategoryDTOs()
					.get(0).getName());
			emailMessage.addTemplateParam("contentPath", contentPath);
			emailMessage.addTemplateParam("contextPath", contextPath);
			LOG.info("sending review email to email: "
					+ suborders.get(0).getOrderEmail());
			send(emailMessage);
		}
	}

	@Override
	public void sendAffiliateSubscriptionEmail(String email,
			List<HashMap<String, String>> listOfMap) {
		EmailMessage emailMessage = new EmailMessage(email, "affiliateEmail");
		emailMessage.addTemplateParam("offers", listOfMap);
		emailMessage.addTemplateParam("issueDate", DateUtils.dateToString(
				DateUtils.getCurrentTime(), "EEE, dd MMM yyyy"));
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		emailMessage.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(emailMessage);
	}

	@Override
	public void sendPrebookNotificationEmail(ProductPrebookSRO prebook,
			boolean isTriggerDateNotification) {
		EmailMessage message = new EmailMessage(Arrays.asList(prebook
				.getEmail().split(",")), "prebookAdminNotificationEmail");
		// message.addTemplateParam("productName", prebook.getProductName());
		message.addTemplateParam("notificationDate", DateUtils.dateToString(
				prebook.getNotificationDate(), "EEE,dd MMMMM yyyy HH:mm"));
		message.addTemplateParam("launchDate", DateUtils.dateToString(
				prebook.getLaunchDate(), "EEE,dd MMMMM yyyy HH:mm"));
		message.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		message.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		message.addTemplateParam("isReleaseDateNotification",
				isTriggerDateNotification);
		send(message);
	}

	/*
	 * @Override public void customerNotificationEmail(List<String> emailList,
	 * ProductOfferSRO productOfferSRO) { EmailMessage message = new
	 * EmailMessage(emailList, "customerNotificationEmail"); ProductSRO
	 * productSRO = productOfferSRO.getProductDTOs().get(0); Map<String, String>
	 * attributeMap = new HashMap<String, String>(); ArrayList<String>
	 * attributes = new ArrayList<String>(); for (ProductAttributeSRO
	 * productAttribueSRO : productSRO.getProductAttributes()) {
	 * attributeMap.put(productAttribueSRO.getAttributeName(),
	 * productAttribueSRO.getValue());
	 * attributes.add(productAttribueSRO.getAttributeName()); }
	 * message.addTemplateParam("attributes", attributes);
	 * message.addTemplateParam("attributeMap", attributeMap);
	 * message.addTemplateParam("pageUrl", productOfferSRO.getPageUrl()); if
	 * (productOfferSRO.getContentDTO().getMainPictures().size() != 0)
	 * message.addTemplateParam("imgUrl",
	 * productOfferSRO.getContentDTO().getMainPictures
	 * ().get(0).getContentPath()); message.addTemplateParam("productName",
	 * productOfferSRO.getName()); message.addTemplateParam("displayPrice",
	 * productOfferSRO.getDisplayPrice()); message.addTemplateParam("discount",
	 * productOfferSRO.getDiscount()); message.addTemplateParam("price",
	 * productOfferSRO.getPrice()); message.addTemplateParam("contentPath",
	 * CacheManager
	 * .getInstance().getCache(SystemPropertiesCache.class).getContentPath
	 * (Constants.DEFAULT_CONTENT_PATH));
	 * message.addTemplateParam("contextPath",
	 * CacheManager.getInstance().getCache
	 * (SystemPropertiesCache.class).getContextPath
	 * (Constants.DEFAULT_CONTEXT_PATH)); send(message); }
	 */

	@Override
	public void sendUploadRefundFinanceEmail(Map<String, String> suborders) {
		String addresses = CacheManager.getInstance()
				.getCache(UMSPropertiesCache.class)
				.getProperty("finance.upload.refund.email");
		EmailMessage msg = new EmailMessage(
				Arrays.asList(addresses.split(",")),
				"financeRefundUploadNotification");
		msg.addTemplateParam("suborders", suborders);
		send(msg);
	}

	public void adminUpdateNotificationMail(String updateInfo, String email) {
		EmailMessage message = new EmailMessage(email,
				"adminUpdateNotificationMail");
		message.addTemplateParam("updateInfo", updateInfo);
		message.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath(Constants.DEFAULT_CONTENT_PATH));
		message.addTemplateParam("contextPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
		send(message);
	}

	@Override
	public void sendAlternatePaymentConfirmationEmail(int collectableAmount,
			OrderSRO order, SuborderSRO originalSuborder,
			SuborderSRO alternateSuborder) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"alternatePaymentConfirmationEmail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			emailMessage.addTemplateParam(
					"sdCashReedemed",
					alternateSuborder.getSdCash()
							- originalSuborder.getSdCash());
			CatalogSRO oldCatalogDTO = catalogService
					.getCatalog(
							new GetCatalogByIdRequest(originalSuborder
									.getCatalogIdL())).getCatalogSRO();
			if (oldCatalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						originalSuborder.getCatalogId());
				return;
			}
			CatalogSRO catalogDTO = catalogService
					.getCatalog(
							new GetCatalogByIdRequest(alternateSuborder
									.getCatalogIdL())).getCatalogSRO();
			if (catalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						alternateSuborder.getCatalogId());
				return;
			}
			emailMessage.addTemplateParam(
					"originalSuborder",
					prepareSuborderDetailDTOFromSuborder(originalSuborder,
							oldCatalogDTO));
			emailMessage.addTemplateParam(
					"alternateSuborder",
					prepareSuborderDetailDTOFromSuborder(alternateSuborder,
							catalogDTO));
			emailMessage.addTemplateParam("collectableAmount",
					collectableAmount);

			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	private SuborderDetailDTO prepareSuborderDetailDTOFromSuborder(
			SuborderSRO suborder, CatalogSRO catalogDTO) {
		SuborderDetailDTO suborderDTO = new SuborderDetailDTO();
		if (suborder instanceof GetawaySuborderSRO) {
			GetawaySuborderSRO getawaySuborder = (GetawaySuborderSRO) suborder;
			suborderDTO.setValidUpto(getawaySuborder.getValidUpto());
			suborderDTO.setMerchantPromoCode(getawaySuborder
					.getMerchantPromoCode());
		} else if (suborder instanceof DealSuborderSRO) {
			DealSuborderSRO dealSuborder = (DealSuborderSRO) suborder;
			suborderDTO.setValidUpto(dealSuborder.getValidUpto());
			suborderDTO.setMerchantPromoCode(dealSuborder
					.getMerchantPromoCode());
		}
		return setCommonSuborderDetails(suborder, suborderDTO, catalogDTO);
	}

	@Override
	public void sendAlternateRefundEmail(OrderSRO order,
			FullfillAlternateSuborderRequest request) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"AlternateRefundEMail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			CatalogSRO oldCatalogDTO = catalogService.getCatalog(
					new GetCatalogByIdRequest(request.getOldSuborderSRO()
							.getCatalogIdL())).getCatalogSRO();
			if (oldCatalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						request.getOldSuborderSRO().getCatalogId());
				return;
			}
			CatalogSRO catalogDTO = catalogService.getCatalog(
					new GetCatalogByIdRequest()).getCatalogSRO();
			if (catalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						request.getSuborderSRO().getCatalogId());
				return;
			}
			emailMessage.addTemplateParam(
					"originalSuborder",
					prepareSuborderDetailDTOFromSuborder(
							request.getOldSuborderSRO(), oldCatalogDTO));
			emailMessage.addTemplateParam(
					"alternateSuborder",
					prepareSuborderDetailDTOFromSuborder(
							request.getSuborderSRO(), catalogDTO));
			emailMessage.addTemplateParam("refundAmount",
					request.getAmountRefunded());
			emailMessage.addTemplateParam("sdCashRefunded",
					request.getSdCashRefunded());
			emailMessage.addTemplateParam("totalRefundAmount",
					request.getAmountRefunded() + request.getSdCashRefunded());
			emailMessage.addTemplateParam("collectableAmount", request
					.getOldSuborderSRO().getSellingPrice()
					- request.getSuborderSRO().getSellingPrice());
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendAlternateAbsorbEmail(OrderSRO order,
			FullfillAlternateSuborderRequest request) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"AlternateAbsorbEMail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			CatalogSRO oldCatalogDTO = catalogService.getCatalog(
					new GetCatalogByIdRequest(request.getOldSuborderSRO()
							.getCatalogIdL())).getCatalogSRO();
			if (oldCatalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						request.getOldSuborderSRO().getCatalogId());
				return;
			}
			CatalogSRO catalogDTO = catalogService.getCatalog(
					new GetCatalogByIdRequest(request.getSuborderSRO()
							.getCatalogIdL())).getCatalogSRO();
			if (catalogDTO == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						request.getSuborderSRO().getCatalogId());
				return;
			}
			emailMessage.addTemplateParam(
					"originalSuborder",
					prepareSuborderDetailDTOFromSuborder(
							request.getOldSuborderSRO(), oldCatalogDTO));
			emailMessage.addTemplateParam(
					"alternateSuborder",
					prepareSuborderDetailDTOFromSuborder(
							request.getSuborderSRO(), catalogDTO));
			emailMessage.addTemplateParam("discount", request.getSuborderSRO()
					.getOfferDiscount());
			emailMessage.addTemplateParam("payableAmount", 0);
			emailMessage.addTemplateParam("differenceAmount", request
					.getSuborderSRO().getSellingPrice()
					- request.getOldSuborderSRO().getSellingPrice());
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendReleaseDateShiftMail(OrderSRO order, SuborderSRO suborder,
			String newReleaseDate, String oldReleaseDate) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"PrebookReleaseDateShiftMail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			CatalogSRO catalog = catalogService.getCatalog(
					new GetCatalogByIdRequest(suborder.getCatalogIdL()))
					.getCatalogSRO();
			if (catalog == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						suborder.getCatalogId());
				return;
			}
			emailMessage.addTemplateParam("suborderDTO",
					prepareSuborderDetailDTOFromSuborder(suborder, catalog));
			emailMessage.addTemplateParam("newReleaseDate", newReleaseDate);
			emailMessage.addTemplateParam("oldReleaseDate", oldReleaseDate);
			emailMessage.addTemplateParam("totalAmount",
					suborder.getOfferPrice());
			emailMessage.addTemplateParam("paidAmount",
					suborder.getPaidAmount());
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendSecondPaymentMail(OrderSRO order, SuborderSRO suborder,
			String buyPageUrl, String releaseDate) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"PrebookSecondPaymentMail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			CatalogSRO catalog = catalogService.getCatalog(
					new GetCatalogByIdRequest(suborder.getCatalogIdL()))
					.getCatalogSRO();
			if (catalog == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						suborder.getCatalogId());
				return;
			}
			emailMessage.addTemplateParam("suborderDTO",
					prepareSuborderDetailDTOFromSuborder(suborder, catalog));
			emailMessage.addTemplateParam("offerPrice",
					suborder.getOfferPrice());
			emailMessage.addTemplateParam("releaseDate", releaseDate);
			emailMessage.addTemplateParam("buyPageUrl", buyPageUrl);
			emailMessage.addTemplateParam("collectableAmount",
					suborder.getSellingPrice() - suborder.getOfferPrice());
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendPrebookPaymentConfirmationEmail(OrderSRO order,
			SuborderSRO suborder, Integer collectedAmount, Integer prebookAmount) {
		EmailMessage emailMessage = new EmailMessage(order.getEmail(),
				"PrebookPaymentConfirmationMail");
		OrderDetailDTO orderDetailDTO = prepareOrderDetailDTOFromOrder(order);
		if (orderDetailDTO != null) {
			modifyOrderSummaryUrlForEmail(orderDetailDTO);
			emailMessage.addTemplateParam("orderDTO", orderDetailDTO);
			CatalogSRO catalog = catalogService.getCatalog(
					new GetCatalogByIdRequest(suborder.getCatalogIdL()))
					.getCatalogSRO();
			if (catalog == null) { // should not happen
				LOG.error(
						"Error while fetching data from catalog server for catalog id {}",
						suborder.getCatalogId());
				return;
			}
			emailMessage.addTemplateParam("collectedAmount", collectedAmount);
			emailMessage.addTemplateParam("offerPrice", prebookAmount);
			emailMessage.addTemplateParam("suborderDTO",
					prepareSuborderDetailDTOFromSuborder(suborder, catalog));
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendSnapBoxActivationConfirmation(String email, String name,
			String link) {
		if (email != null && email.trim().length() > 0) {

			EmailMessage emailMessage = new EmailMessage(email,
					SNAPBOX_ACTIVATION_CONFORMATION_EMAIL_TEMPLATE);
			emailMessage.addTemplateParam("name", name);
			emailMessage.addTemplateParam("link", link);
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	@Override
	public void sendSnapBoxInvitationEmail(String email, String name,
			String link) {
		if (email != null && email.trim().length() > 0) {

			EmailMessage emailMessage = new EmailMessage(email,
					SNAPBOX_INVITATION_EMAIL);
			emailMessage.addTemplateParam("name", name);
			emailMessage.addTemplateParam("link", link);
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	public void sendUserNEFTUpdatedEmail(String email,
			EnhancedUserNEFTDetailsSRO enhancedUserNEFTDetails) {
		if (email != null && email.trim().length() > 0) {

			EmailMessage emailMessage = new EmailMessage(email,
					USER_NEFT_DETAILS_UPDATED_EMAIL_TEMPLATE);
			emailMessage.addTemplateParam("userNEFTDetails",
					enhancedUserNEFTDetails);
			emailMessage.addTemplateParam("contextPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContextPath(Constants.DEFAULT_CONTEXT_PATH));
			emailMessage.addTemplateParam("contentPath", CacheManager
					.getInstance().getCache(UMSPropertiesCache.class)
					.getContentPath(Constants.DEFAULT_CONTENT_PATH));
			send(emailMessage);
		}
	}

	/**
	 * Service to send out SDCash credit mails to user via different templates
	 * 
	 * @param sdCashBulkEmailRequest
	 *            containing email ID, expiry, amount, isRegistered, isVerified and template name
	 */
	@Override
	public void sendUserSDCashCreditEmail(
			SDCashBulkCreditEmailRequest sdCashBulkEmailRequest,
			String templateName) {

		EmailMessage emailMessage = new EmailMessage(
				sdCashBulkEmailRequest.getEmail(), templateName);
		emailMessage.addTemplateParam("sdCashBulkEmailRequest",
				sdCashBulkEmailRequest);
		emailMessage.addTemplateParam("contentPath",
				CacheManager.getInstance().getCache(UMSPropertiesCache.class)
						.getContentPath("http://i.sdlcdn.com/"));
		send(emailMessage);
	}

	/**
	 * Service to send out mail to the uploader of SDCash  Credit details excel file
	 * 
	 * @param email
	 *            , errorCodeEmailIds Map
	 */
	@Override
	public void sendSDCashBulkCreditResponseEmail(String email,
			Map<ErrorConstants, List<String>> errorCodeEmailIdsMap) {
		EmailMessage emailMessage = new EmailMessage(email,
				SD_CASH_BULK_CREDIT_RESPONSE_TO_UPLOADER);
		emailMessage.addTemplateParam("invalidEmailMap", errorCodeEmailIdsMap);
		send(emailMessage);

	}
	
	
	/**
	 * Service to send out mail to the uploader of SDCash Debit details excel file
	 * 
	 * @param email
	 *            , errorCodeEmailIds Map
	 */
	@Override
	public void sendSDCashBulkDebitResponseEmail(String email,
			Map<ErrorConstants, List<String>> errorCodeEmailIdsMap) {
		EmailMessage emailMessage = new EmailMessage(email,
				SD_CASH_BULK_DEBIT_RESPONSE_TO_UPLOADER);
		emailMessage.addTemplateParam("invalidEmailMap", errorCodeEmailIdsMap);
		send(emailMessage);

	}
	

	/**
	 * New email service that sends out emails when trigger email flag is set
	 * 
	 * @param email
	 *            , sdCash amount, expiry days
	 */

	@Override
	public void sendSDWalletEmail(String email, Integer amount,
			Integer expiryDays) {
		EmailMessage emailMessage = new EmailMessage(email,
				USER_SDWALLET_EMAIL_TEMPLATE);
		emailMessage.addTemplateParam("amount", amount);
		emailMessage.addTemplateParam("expiryDays", expiryDays);
		// emailMessage.addTemplateParam(templateName, templateName);
		send(emailMessage);
	}

}
