package com.snapdeal.merchant.rest.http.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.fcNotifier.client.INotifierServiceClient;
import com.snapdeal.fcNotifier.exception.ServiceException;
import com.snapdeal.fcNotifier.reponse.EmailResponse;
import com.snapdeal.fcNotifier.request.EmailMessage;
import com.snapdeal.fcNotifier.request.EmailNotifierRequest;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GeneralUserContactUsRequest;
import com.snapdeal.merchant.request.MerchantContactUsRequest;
import com.snapdeal.merchant.request.MerchantUpdateDetailsRequest;
import com.snapdeal.merchant.response.GeneralUserContactUsResponse;
import com.snapdeal.merchant.response.MerchantContactUsResponse;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.payments.roleManagementClient.exceptions.HttpTransportException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IMSUtil {

	@Autowired
	private INotifierServiceClient fcNotifierClient;

	@Autowired
	private MpanelConfig config;

	public MerchantContactUsResponse sendMerchantUserContactUsEmail(MerchantContactUsRequest request)
			throws MerchantException {

		EmailNotifierRequest fcNotifierRequest = new EmailNotifierRequest();
		fcNotifierRequest.setBlocking(true);

		EmailMessage emailMessage = new EmailMessage();

		List<String> to = new ArrayList<String>();
		to.add(config.getMerchantSupportEmailId());

		Map<String, String> tags = new HashMap<String, String>();

		tags.put("Merchant_name", request.getMerchantName());
		tags.put("Mid", request.getMerchantId());
		tags.put("Issue_Subject", request.getIssueType());
		tags.put("Description", request.getEmailContent());
		tags.put("email_id", request.getEmailId());

		emailMessage.setTo(to);
		emailMessage.setSubject(request.getIssueType());
		emailMessage.setTemplateKey(config.getMerchantUserContactusTmplKey());
		emailMessage.setRequestId(UUID.randomUUID().toString());
		emailMessage.setTags(tags);
		emailMessage.setFrom(config.getFromEmail());
		emailMessage.setReplyTo(config.getReplyToEmail());
		emailMessage.setTaskId(UUID.randomUUID().toString());

		fcNotifierRequest.setEmailMessage(emailMessage);

		EmailResponse fcNotifierResponse = null;
		try {
			log.info("Email message and Email Request for FcNotifier : {} , {}", emailMessage, fcNotifierRequest);
			fcNotifierResponse = fcNotifierClient.sendEmail(fcNotifierRequest);
			log.info("FCNotifier Response  {} for email Request :{}", fcNotifierResponse, fcNotifierRequest);
		} catch (HttpTransportException hte) {
			log.error("Getting HTTP Transport Exception from FCNotifier:{}", hte.getErrMsg());
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Getting Exception while sending Email : {} {}", e.getErrMsg(), e);
			throw new MerchantException(ErrorConstants.EMAIL_FAIL_CODE, ErrorConstants.EMAIL_FAIL_MSG);
		}

		MerchantContactUsResponse response = new MerchantContactUsResponse();
		response.setMessage(ErrorConstants.EMAIL_SUCCESS_MSG);

		return response;

	}

	public void sendCreateMerchantEmail(MerchantUpdateDetailsRequest request, String merchantEmail,
			String IntegrationMode) throws MerchantException {
		EmailNotifierRequest fcNotifierRequest = new EmailNotifierRequest();
		fcNotifierRequest.setBlocking(true);

		EmailMessage emailMessage = new EmailMessage();

		MerchantBusinessInfo businessInformationDTO = request.getBusinessInformationDTO();
		List<String> to = new ArrayList<String>();

		List<String> onlineToEmailIdList = config.getCreateOfflineMerchantToEmails();

		List<String> offlineToEmailIdList = config.getCreateOnlineMerchantToEmails();

		if (IntegrationMode.equalsIgnoreCase(AppConstants.Online)) {
			for (String toEmailId : onlineToEmailIdList) {
				to.add(toEmailId);
			}
		}
		if (IntegrationMode.equalsIgnoreCase(AppConstants.Offline)) {
			for (String toEmailId : offlineToEmailIdList) {
				to.add(toEmailId);
			}
		}

		Map<String, String> tags = new HashMap<String, String>();

		tags.put("MerchantName", businessInformationDTO.getMerchantName());
		tags.put("Name", businessInformationDTO.getMerchantName());
		tags.put("BusinessName", businessInformationDTO.getBusinessName());
		tags.put("Mobile", businessInformationDTO.getPrimaryMobile());
		tags.put("Email", merchantEmail);
		if (businessInformationDTO.getLandLineNumber() != null)
			tags.put("Landline", businessInformationDTO.getLandLineNumber());
		else
			tags.put("Landline", "");
		
		tags.put("AddressLine1", businessInformationDTO.getAddress1());
		if (businessInformationDTO.getAddress2() == null) {
			tags.put("AddressLine2", "");
		} else {
			tags.put("AddressLine2", businessInformationDTO.getAddress2());
		}

		tags.put("City", businessInformationDTO.getCity());
		tags.put("Category", businessInformationDTO.getBusinessCategory());
		tags.put("IntegrationMode", IntegrationMode);
		tags.put("BusinessType", businessInformationDTO.getBusinessType());

		StringBuilder subject = new StringBuilder();
		subject.append("New ").append(IntegrationMode).append(" ").append("Merchant OnBoarded ")
				.append(businessInformationDTO.getMerchantName()).append(" ")
				.append(businessInformationDTO.getPrimaryMobile());

		emailMessage.setTo(to);
		emailMessage.setSubject(subject.toString());
		emailMessage.setTemplateKey(config.getCreateMerchantTemplateKey());
		emailMessage.setRequestId(UUID.randomUUID().toString());
		emailMessage.setTags(tags);
		emailMessage.setFrom(config.getFromEmail());
		emailMessage.setReplyTo(config.getReplyToEmail());
		emailMessage.setTaskId(UUID.randomUUID().toString());

		fcNotifierRequest.setEmailMessage(emailMessage);

		EmailResponse fcNotifierResponse = null;

		int maxRetry = config.getApiRetryCount(), i = -1;

		while (i < maxRetry) {
			try {
				log.info("Email message and Email Request of create Merchant: {} , {}", emailMessage,
						fcNotifierRequest);
				fcNotifierResponse = fcNotifierClient.sendEmail(fcNotifierRequest);
				log.info("FCNotifier Response for email Request :{}", fcNotifierResponse);
				return;
			} catch (HttpTransportException hte) {
				log.error("Getting HTTP Transport Exception from FCNotifier {} pass {}", hte, i + 2);
				if (i + 1 == maxRetry) {
					log.error("Email Not Sent for Merchant : {} {} {} ", request.getMerchantId(), merchantEmail, hte);
					return;
				}
			} catch (ServiceException e) {
				log.error("Email Not Sent for Merchant : {} {}", request.getMerchantId(), merchantEmail);
				log.error("Getting Exception while sending Email : {} {}", e.getErrMsg(), e);
				return;
			}

			i++;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
				log.info("suppressed thread interruption. will continue to retry");
			}
		}

	}

	/*
	 * public CreateOfflineMerchantResponse
	 * sendCreateOfflineMerchantEmail(CreateOfflineMerchantRequest request)
	 * throws MerchantException {
	 * 
	 * EmailNotifierRequest fcNotifierRequest = new EmailNotifierRequest();
	 * fcNotifierRequest.setBlocking(true);
	 * 
	 * EmailMessage emailMessage = new EmailMessage();
	 * 
	 * List<String> to = new ArrayList<String>();
	 * to.add(config.getAllianceTeamEmailId());
	 * to.add(config.getMerchantSupportEmailId());
	 * to.add(config.getProductCreateOfflineMerchantEmail());
	 * 
	 * Map<String, String> tags = new HashMap<String, String>();
	 * 
	 * tags.put("CompanyName", request.getCompanyName()); tags.put("Name",
	 * request.getContactPersonName()); tags.put("Mobile",
	 * request.getContactPersonMobile()); tags.put("Email",
	 * request.getContactPersonEmail()); tags.put("Landline",
	 * request.getLandline()); tags.put("Address",
	 * request.getBusinessAddress()); tags.put("Category",
	 * request.getBusinessCategory()); tags.put("NoOfStores",
	 * request.getNoOfStores()); tags.put("NoOfTxn", request.getDailyTxnVol());
	 * 
	 * StringBuilder subject = new StringBuilder(); subject.append("NEW LEAD: "
	 * ).append(request.getContactPersonName()).append(" ")
	 * .append(request.getContactPersonMobile()); emailMessage.setTo(to);
	 * emailMessage.setSubject(subject.toString());
	 * emailMessage.setTemplateKey(config.getOfflineMerchantTmplKey());
	 * emailMessage.setRequestId(UUID.randomUUID().toString());
	 * emailMessage.setTags(tags); emailMessage.setFrom(config.getFromEmail());
	 * emailMessage.setReplyTo(config.getReplyToEmail());
	 * emailMessage.setTaskId(UUID.randomUUID().toString());
	 * 
	 * fcNotifierRequest.setEmailMessage(emailMessage);
	 * 
	 * EmailResponse fcNotifierResponse = null; try { log.info(
	 * "Email message and Email Request of create Merchant: {} , {}",
	 * emailMessage, fcNotifierRequest); fcNotifierResponse =
	 * fcNotifierClient.sendEmail(fcNotifierRequest); log.info(
	 * "FCNotifier Response for email Request :{} {}", fcNotifierResponse,
	 * fcNotifierRequest); } catch (HttpTransportException hte) { log.error(
	 * "Getting HTTP Transport Exception from FCNotifier:{}", hte.getErrMsg());
	 * throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
	 * ErrorConstants.GENERIC_INTERNAL_SERVER_MSG); } catch (ServiceException e)
	 * { log.error("Getting Exception while sending Email : {} {}",
	 * e.getErrMsg(), e); throw new
	 * MerchantException(ErrorConstants.EMAIL_FAIL_CODE,
	 * ErrorConstants.EMAIL_FAIL_MSG); }
	 * 
	 * CreateOfflineMerchantResponse response = new
	 * CreateOfflineMerchantResponse();
	 * response.setMessage(ErrorConstants.EMAIL_SUCCESS_MSG);
	 * 
	 * return response;
	 * 
	 * }
	 */

	public GeneralUserContactUsResponse sendGeneralUserContactUsEmail(GeneralUserContactUsRequest request)
			throws MerchantException {
		EmailNotifierRequest fcNotifierRequest = new EmailNotifierRequest();
		fcNotifierRequest.setBlocking(true);

		EmailMessage emailMessage = new EmailMessage();

		List<String> to = new ArrayList<String>();
		to.add(config.getMerchantSupportEmailId());

		Map<String, String> tags = new HashMap<String, String>();

		tags.put("Issue_Subject", request.getIssueType());
		tags.put("Description", request.getEmailContent());
		tags.put("email_id", request.getEmailId());

		emailMessage.setTo(to);
		emailMessage.setSubject(request.getIssueType());
		emailMessage.setTemplateKey(config.getGeneralUserContactusTmplKey());
		emailMessage.setRequestId(UUID.randomUUID().toString());
		emailMessage.setTags(tags);
		emailMessage.setFrom(config.getFromEmail());
		emailMessage.setReplyTo(config.getReplyToEmail());
		emailMessage.setTaskId(UUID.randomUUID().toString());

		fcNotifierRequest.setEmailMessage(emailMessage);

		EmailResponse fcNotifierResponse = null;
		try {
			log.info("Email message and Email Request for FcNotifier : {} , {}", emailMessage, fcNotifierRequest);
			fcNotifierResponse = fcNotifierClient.sendEmail(fcNotifierRequest);
			log.info("FCNotifier Response  {} for email Request :{}", fcNotifierResponse, fcNotifierRequest);
		} catch (HttpTransportException hte) {
			log.error("Getting HTTP Transport Exception from FCNotifier:{}", hte.getErrMsg());
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Getting Exception while sending Email : {} {}", e.getErrMsg(), e);
			throw new MerchantException(ErrorConstants.EMAIL_FAIL_CODE, ErrorConstants.EMAIL_FAIL_MSG);
		}

		GeneralUserContactUsResponse response = new GeneralUserContactUsResponse();
		response.setMessage(ErrorConstants.EMAIL_SUCCESS_MSG);

		return response;
	}

	public void setFCNotifierService(INotifierServiceClient fcNotifierClient) {
		this.fcNotifierClient = fcNotifierClient;
	}

	public void setMpanelConfig(MpanelConfig config) {
		this.config = config;
	}

}
