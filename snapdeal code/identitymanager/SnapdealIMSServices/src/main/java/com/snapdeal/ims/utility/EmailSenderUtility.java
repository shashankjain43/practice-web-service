package com.snapdeal.ims.utility;

import com.snapdeal.ims.authorize.AuthorizationContext;
import com.snapdeal.ims.common.ClientConfiguration;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.constants.ServiceCommonConstants;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dao.IUserVerificationDetailsDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dbmapper.entity.UserVerification;
import com.snapdeal.ims.dbmapper.entity.VerificationPurpose;
import com.snapdeal.ims.enums.IMSRequestHeaders;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.CipherException;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.service.impl.NotifierServiceDelegater;
import com.snapdeal.ims.utils.CipherServiceUtil;
import com.snapdeal.ims.utils.VerificationCodeGeneratorUtil;
import com.snapdeal.notifier.email.request.EmailMessage;

import org.apache.commons.lang.StringUtils;
import org.jsoup.helper.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import java.sql.Timestamp;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import lombok.extern.slf4j.Slf4j;

@Component
@Slf4j
public class EmailSenderUtility {
	@Autowired
	IMSUtility imsUtility;

	@Autowired
	private IUserVerificationDetailsDao userVerificationDetailsDao;

	@Autowired
	private AuthorizationContext context;

	@Autowired
	IUserDao userDao;
	
	@Autowired
	IMSUtility imsUtillity;
	
	
	@Autowired
	private NotifierServiceDelegater notifierService;

	public void sendVerificationEmail(String email, String url,
			ConfigurationConstants templateKeyConf,
			ConfigurationConstants verificationKeyConf,
			VerificationPurpose purpose,
			String subject) {

		String verificationCode = VerificationCodeGeneratorUtil
				.getVerificationCode();

		String merchant = getMerchant();

		String templateKey = imsUtility.getEmailTemplateKey(merchant,
				templateKeyConf);

		String verificationLink = null;
		if (StringUtils.isNotBlank(url)) {
			verificationLink = url;
		} else {
			verificationLink = getVerificationURL(merchant, verificationKeyConf);
		}
		User user = userDao.getUserByEmail(email);
		saveVerificationCode(user.getUserId(), verificationCode, purpose);

		// Step8: Send email
		sendVerificationMail(user.getEmailId(), user, user.getFirstName(),
				verificationCode, templateKey, verificationLink, false,subject);
		return;

	}

	public void sendEmail(String email,
			ConfigurationConstants emailTempelateKey, boolean blocking,ConfigurationConstants subjectKey, String verificationUrl) {

		User user = userDao.getUserByEmail(email);
		Map<String, String> tags = new HashMap<String, String>();
		String displayName = StringUtils.isNotBlank(user.getFirstName()) ? user.getFirstName() : user.getDisplayName() ; 
		if(StringUtils.isBlank(displayName)){
			displayName = " ";
		}
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.CUSTOMER_TAG,
				StringUtils.capitalize(StringUtils.lowerCase(displayName)));
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.EMAIL_TAG,
				user.getEmailId());
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.MOBILE_TAG,
				user.getMobileNumber());
		String emailSubject;
		String templateKey; 
      if (verificationUrl != null) {
         tags.put(com.snapdeal.ims.constants.ServiceCommonConstants.URL_TAG, verificationUrl);
         templateKey = getTemplateKey(emailTempelateKey, true);
         emailSubject=imsUtillity.getEmailSubject(ServiceCommonConstants.FREECHARGE, subjectKey!=null?subjectKey:ConfigurationConstants.SEND_EMAIL_UPGRADE_ACCOUNT_VERIFY);
      }else{
    	  templateKey =  getTemplateKey(emailTempelateKey);
    	  emailSubject=imsUtillity.getEmailSubject(ServiceCommonConstants.FREECHARGE, subjectKey!=null?subjectKey:ConfigurationConstants.SEND_EMAIL_UPGRADE_ACCOUNT);
      }
      sendEmailHelper(email, blocking, user, tags, templateKey,emailSubject);

	}

	private void saveVerificationCode(String userId, String verificationCode,
			VerificationPurpose purpose) {
		String expiryTime = null;
		switch (purpose) {
			case VERIFY_NEW_USER :
			case VERIFY_GUEST_USER :
				expiryTime = Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFICATION_CODE_EXPIRY_NEW_USER);
				break;
			case FORGOT_PASSWORD :
				expiryTime = Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFICATION_CODE_EXPIRY_FORGOT_PASSWORD);
				break;
		}
		int expiryTimeInMinutes = Integer
				.valueOf(Configuration
						.getGlobalProperty(ConfigurationConstants.DEFAULT_VERIFICATION_CODE_EXPIRY));
		if (expiryTime != null && StringUtil.isNumeric(expiryTime)) {
			expiryTimeInMinutes = Integer.valueOf(expiryTime);
		}
		UserVerification userVerificationEntity = new UserVerification();
		userVerificationEntity.setUserId(userId);
		userVerificationEntity.setCode(verificationCode);
		userVerificationEntity.setPurpose(purpose);
		// Convert time in minutes to milliseconds.
		long milliSecTime = expiryTimeInMinutes * 60 * 1000;
		userVerificationEntity.setCodeExpiryTime(new Timestamp(System
				.currentTimeMillis() + milliSecTime));
		userVerificationDetailsDao.create(userVerificationEntity);
	}

	private void sendEmailHelper(String email, boolean blocking, User user,
			Map<String, String> tags, String templateKey,String subject) {
		EmailMessage emailMessage = new EmailMessage();
		List<String> toList = new ArrayList<String>();

		toList.add(email);
		emailMessage.addRecepients(toList);
		String merchant = getMerchant();
        tags.put(ServiceCommonConstants.MERCHANT_TAG, StringUtils.capitalize(StringUtils.lowerCase(merchant)));
		String replyToEmailId = getEmailReplyTo(merchant,
				ConfigurationConstants.SEND_EMAIL_REPLY_TO_EMAIL_ID);

		String fromEmailId = getEmailFromMail(merchant,
				ConfigurationConstants.SEND_EMAIL_FROM_EMAIL_ID);

		emailMessage.setFrom(fromEmailId);

		emailMessage.setTemplateKey(templateKey);
		emailMessage.setSubject(subject);

		emailMessage.setReplyTo(replyToEmailId);
		emailMessage.setTags(tags);
		String taskId = "VERIFICATION_MAIL-" + user.getUserId() + "-"
				+ UUID.randomUUID().getLeastSignificantBits();
		emailMessage.setTaskId(taskId);
		emailMessage.setEmailSendBy(ServiceCommonConstants.FREECHARGE);

		log.debug("Email message: " + emailMessage);
		notifierService.sendEmail(emailMessage, blocking);
	}

	private String getVerificationURL(String merchant,
			ConfigurationConstants configurationConstant) {

		String url = Configuration.getClientProperty(merchant,
				configurationConstant);
		if (StringUtils.isBlank(url)) {
			url = Configuration.getGlobalProperty(configurationConstant);
		}

		return url;
	}

   protected String getTemplateKey(ConfigurationConstants configurationConstants,
            boolean showVerification) {
      String emailTemplateKey = imsUtility.getEmailTemplateKey(ServiceCommonConstants.FREECHARGE,
               configurationConstants);
      if (showVerification) {
         emailTemplateKey = emailTemplateKey + ".verification";
      }
      return emailTemplateKey;
   }

   protected String getTemplateKey(ConfigurationConstants configurationConstants) {
      return getTemplateKey(configurationConstants, false);
	}

	protected String getMerchant() {

		String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
		return ClientConfiguration.getMerchantById(clientId).getMerchantName();
	}

	protected Merchant getMerchantEnum() {
      String clientId = context.get(IMSRequestHeaders.CLIENT_ID.toString());
      return ClientConfiguration.getMerchantById(clientId);
   }

	protected String getEmailReplyTo(String merchant,
			ConfigurationConstants configConstants) {
		String replyToEmailId = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(replyToEmailId)) {
			replyToEmailId = Configuration.getGlobalProperty(configConstants);
		}
		return replyToEmailId;
	}

	protected String getEmailSubject(String merchant,
			ConfigurationConstants configConstants) {
		String subject = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(subject)) {
			subject = Configuration.getGlobalProperty(configConstants);
		}
		return subject;
	}

	protected String getEmailFromMail(String merchant,
			ConfigurationConstants configConstants) {
		String fromEmailId = Configuration.getClientProperty(merchant,
				configConstants);
		if (StringUtils.isBlank(fromEmailId)) {
			fromEmailId = Configuration.getGlobalProperty(configConstants);
		}
		return fromEmailId;
	}
 
	@Async
	protected void sendVerificationMail(String emailId, User user,
			String firstName, String verificationCode, String templateKey,
			String verificationLink, boolean blocking,String subject) {

		String encryptedVerificationCode;
		try {
			encryptedVerificationCode = CipherServiceUtil
					.encrypt(verificationCode);
		} catch (CipherException e) {
			log.error("Exception on encrypting verification code.");
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		if (verificationLink == null) {
			log.error("verificationLink is null ... please check configuration");
			throw new IMSServiceException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

		String url = MessageFormat.format(verificationLink,
				encryptedVerificationCode);
		// Step7: Send email with verification code
		String name = StringUtils.isNotBlank(user.getFirstName()) ? user.getFirstName() :user.getDisplayName();
		if(StringUtils.isEmpty(name)){
			name = " ";
		}
		Map<String, String> tags = new HashMap<String, String>();
		tags.put(com.snapdeal.ims.constants.ServiceCommonConstants.URL_TAG, url);
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.CUSTOMER_TAG,
				StringUtils.capitalize(StringUtils.lowerCase(name)));
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.EMAIL_TAG,
				user.getEmailId());
		tags.put(
				com.snapdeal.ims.constants.ServiceCommonConstants.MOBILE_TAG,
				user.getMobileNumber());
		/*
		 * We may need to verify email, putting test mode configuration
		 */
		if (isTestingModeOn()) {
			emailId = Configuration
					.getGlobalProperty(ConfigurationConstants.VERIFICATION_TEST_EMAIL_ID);
		}
		sendEmailHelper(emailId, false, user, tags, templateKey,subject);
	}

	protected boolean isTestingModeOn() {
		String emailTestingModeOn = Configuration
				.getGlobalProperty(ConfigurationConstants.VERIFICATION_EMAIL_TEST_MODE);
		if (emailTestingModeOn != null && emailTestingModeOn.equals("1")) {
			return true;
		}
		return false;
	}
	

}
