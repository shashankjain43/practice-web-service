package com.snapdeal.ims.service.impl;

import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.Set;

import javax.annotation.PostConstruct;

import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.BlacklistCache;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ClientCache;
import com.snapdeal.ims.cache.ConfigCache;
import com.snapdeal.ims.cache.VaultCache;
import com.snapdeal.ims.cache.WhiteListAPICache;
import com.snapdeal.ims.cache.service.IIMSCacheServiceProvider;
import com.snapdeal.ims.client.dao.IClientDetailsDao;
import com.snapdeal.ims.client.dbmapper.entity.Client;
import com.snapdeal.ims.client.service.IClientService;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.NewConfigurationConstant;
import com.snapdeal.ims.dao.IBlackWhiteEntityDao;
import com.snapdeal.ims.dao.IConfigDetailsDao;
import com.snapdeal.ims.dao.IIMSApiDao;
import com.snapdeal.ims.dao.IWhiteListAPIsDao;
import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.dbmapper.entity.ConfigDetails;
import com.snapdeal.ims.dbmapper.entity.IMSApi;
import com.snapdeal.ims.dbmapper.entity.WhiteListAPI;
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.enums.Merchant;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.vault.Vault;
import com.snapdeal.notifier.service.Notifier;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.extern.slf4j.Slf4j;

/**
 * This class is used to load all caches. It is a cron/scheduler job which runs
 * after fix interval
 *
 * @author subhash
 *
 */
@Service("CacheUpdater")
@Slf4j
public class CacheUpdater {

	@Autowired
	private Notifier notifier;

	@Autowired
	private IConfigDetailsDao configDetailsDao;

	@Autowired
	private IClientDetailsDao clientDetailsDao;
	
	@Autowired
	private IClientService clientService;

	@Autowired
	private IIMSCacheServiceProvider imsCacheService;

	@Autowired
	private OTPUtility otpUtility;

	@Autowired
	private IBlackWhiteEntityDao blackEntityDao;

	@Autowired
	private IWhiteListAPIsDao whiteListAPIsDao;

	@Autowired
	private IIMSApiDao imsAPIsDao;

	@PostConstruct
	@Timed
	@Marked
	@Logged
	public String loadAll() {
		log.debug("----------Cache update started[ConfigCache, AccessCache]------------");
		loadConfigCache();
		loadVaultCache();
		loadClientCache();
		loadEmailTemplates();
		loadAndUpdateAerospikeCache();
		loadOTPProperties();
		loadBlacklistCache();
		loadWhiteListApiCache();
		log.debug("-------------------Cache update completed-----------------");
		return "Cache Reloaded Successfully";
	}

	/**
	 * Configuration load method.
	 */
	private void loadConfigCache() {

		final ConfigCache configCache = new ConfigCache();
		// final ClientPermissionsCache clientPermissionsCache = new
		// ClientPermissionsCache();
		final List<ConfigDetails> configList = configDetailsDao.getAllConfigs();
		log.debug("---------Updating config cache now. Fetched "
				+ configList.size() + " configurations from database--------");
		for (ListIterator<ConfigDetails> it = configList.listIterator(); it
				.hasNext();) {
			ConfigDetails configDetails = (ConfigDetails) it.next();
			if (configDetails != null) {
				String type = configDetails.getConfigType();
				String key = configDetails.getConfigKey();
				Map<String, String> map = configCache.get(type);
				if (null == map) {
					map = new HashMap<String, String>();
				}
				map.put(key, configDetails.getConfigValue());
				configCache.put(type, map);
			}
		}
		CacheManager.getInstance().setCache(configCache);
		log.debug("------------Config cache updated successfully---------");
	}

	private void loadOTPProperties() {
		otpUtility.setApiUrl(Configuration
				.getGlobalProperty(ConfigurationConstants.API_URL));
		otpUtility
				.setBlockDurationInMins(Integer.parseInt(Configuration
						.getGlobalProperty(ConfigurationConstants.USER_BLOCK_TIME_IN_MINS)));
		otpUtility
				.setChannelId(Integer.parseInt(Configuration
						.getGlobalProperty(ConfigurationConstants.SMS_TEMPLATE_OTP_CHANNELID)));
		otpUtility.setExpiryDurationInMins(Integer.parseInt(Configuration
				.getGlobalProperty(ConfigurationConstants.OTP_EXPIRYINMINS)));
		otpUtility
				.setFailureMessage(Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFY_FAILUE_MESSAGE));
		otpUtility
				.setInvalidAttemptsLimit(Integer.parseInt(Configuration
						.getGlobalProperty(ConfigurationConstants.OTP_INVALID_ATTEMPTS_LIMIT)));
		otpUtility
				.setFailureMessage(Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFY_FAILUE_MESSAGE));
		otpUtility
				.setReSendAttemptsLimit(Integer.parseInt(Configuration
						.getGlobalProperty(ConfigurationConstants.OTP_RESED_ATTEMPTS_LIMIT)));
		otpUtility
				.setFromEmailId(Configuration
						.getGlobalProperty(ConfigurationConstants.SNAPDEAL_EMAIL_CLIENT_FROMTO_EMAILID));
		otpUtility
				.setSnapdealReplyEmailId(Configuration
						.getGlobalProperty(ConfigurationConstants.SNAPDEAL_EMAIL_CLIENT_REPLY_EMAILID));
		otpUtility
				.setFreechargeReplyEmailId(Configuration
						.getGlobalProperty(ConfigurationConstants.FREECHARGE_EMAIL_CLIENT_REPLY_EMAILID));
		otpUtility
				.setClientName(Configuration
						.getGlobalProperty(ConfigurationConstants.SNAPDEAL_EMAI_CLIENT_CLIENT_NAME));
		otpUtility
				.setTemplateName(Configuration
						.getGlobalProperty(ConfigurationConstants.SMS_TEMEPLATE_OTP_TEMPLATENAME));
		otpUtility
				.setTextContent(Configuration
						.getGlobalProperty(ConfigurationConstants.SNAPDEAL_EMAIL_CLIENT_TEXTCONTENT));
		otpUtility
				.setVerificationPin(Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFICATION_OTP_EMAIL_TEMPLATE));
		otpUtility
				.setSuccessMessage(Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFY_SUCCESS_MESSAGE));
		otpUtility
				.setOtpNumberFix(Boolean.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.FIX_OTP_FOR_TESTING)));
		otpUtility
				.setEmailSubject(Configuration
						.getGlobalProperty(ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT));
		otpUtility
				.setFcNotifierEnabled(Boolean.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.FC_NOTIFIER_ENABLED)));

		otpUtility
				.setOtpEncryptionEnabled(Boolean.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.OTP_ENCRYPTION_ENABLED)));
		otpUtility
				.setFromEmailIdFreeCharge(Configuration
						.getGlobalProperty(ConfigurationConstants.SNAPDEAL_EMAIL_CLIENT_FROMTO_EMAILID_FREECHARGE));

		otpUtility
				.setEmailSubjectFORGOT_PASSWORD(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT_FORGOT_PASSWORD));
		otpUtility
				.setEmailSubjectLINK_ACCOUNT(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT_LINK_ACCOUNT));
		otpUtility
				.setEmailSubjectMONEY_OUT(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT_MONEY_OUT));

		otpUtility
				.setEmailSubjectFORGOT_PASSWORDSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT_FORGOT_PASSWORD));
		otpUtility
				.setEmailSubjectLINK_ACCOUNTSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT_LINK_ACCOUNT));
		otpUtility
				.setEmailSubjectMONEY_OUTSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.VERIFICATION_OTP_EMAIL_SUBJECT_MONEY_OUT));

		otpUtility
				.setTemplateBody(Configuration
						.getGlobalProperty(ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY));

		otpUtility.setSmsTemplateLoginFreecharge(Configuration.getProperty(
				Merchant.FREECHARGE.toString(),
				ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_LOGIN));
		otpUtility
				.setSmsTemplateForgotPasswordFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_FORGOT_PASSWORD));
		otpUtility
				.setSmsTemplateMobileVerificationFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_MOBILE_VERIFICATION));
		otpUtility
				.setSmsTemplateUserSignupFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPGRADE_USER));
		otpUtility
				.setSmsTemplateUpdateMobileFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPDATE_MOBILE));
		otpUtility
				.setSmsTemplateUpgradeUserFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPGRADE_USER));
		otpUtility
				.setSmsTemplateLinkAccountFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_LINK_ACCOUNT));
		otpUtility
				.setSmsTemplateOneCheckFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_ONECHECK_SOCIAL_SIGNUP));
		otpUtility
				.setSmsTemplateMoneyOutFreecharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_MONEY_OUT));

		otpUtility
				.setSmsTemplateWalletPayFreeCharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_WALLET_PAY));
		otpUtility
				.setSmsTemplateWalletLoadFreeCharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_ONECHECK_WALLET_LOAD));
		otpUtility
				.setSmsTemplateWalletEnquiryFreeCharge(Configuration.getProperty(
						Merchant.FREECHARGE.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_WALLET_ENQUIRY));

		otpUtility.setSmsTemplateLoginSnapdeal(Configuration.getProperty(
				Merchant.SNAPDEAL.toString(),
				ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_LOGIN));
		otpUtility
				.setSmsTemplateForgotPasswordSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_FORGOT_PASSWORD));
		otpUtility
				.setSmsTemplateMobileVerificationSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_MOBILE_VERIFICATION));
		otpUtility
				.setSmsTemplateUserSignupSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPGRADE_USER));
		otpUtility
				.setSmsTemplateUpdateMobileSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPDATE_MOBILE));
		otpUtility
				.setSmsTemplateUpgradeUserSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_UPGRADE_USER));
		otpUtility
				.setSmsTemplateLinkAccountSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_LINK_ACCOUNT));
		otpUtility
				.setSmsTemplateOneCheckSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_ONECHECK_SOCIAL_SIGNUP));
		otpUtility
				.setSmsTemplateMoneyOutSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_MONEY_OUT));

		otpUtility
				.setSmsTemplateWalletPaySnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_WALLET_PAY));
		otpUtility
				.setSmsTemplateWalletLoadSnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_ONECHECK_WALLET_LOAD));
		otpUtility
				.setSmsTemplateWalletEnquirySnapdeal(Configuration.getProperty(
						Merchant.SNAPDEAL.toString(),
						ConfigurationConstants.SMS_TEMPLATE_OTP_TEMPLATE_BODY_WALLET_ENQUIRY));

		otpUtility
				.setSendEmailOnTestEmailId(Boolean.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.NOTIFIER_SEND_TEST_EMAIL)));
		otpUtility
				.setSendSMSOnTestMobile(Boolean.parseBoolean(Configuration
						.getGlobalProperty(ConfigurationConstants.NOTIFIER_SEND_TEST_SMS)));
		otpUtility
				.setTestEmailId(Configuration
						.getGlobalProperty(ConfigurationConstants.NOTIFIER_TEST_EMAIL_ID));
		otpUtility
				.setTestMobileNumber(Configuration
						.getGlobalProperty(ConfigurationConstants.NOTIFIER_TEST_SMS_NUMBER));
		otpUtility
		.setSendCallOnTestMobile(Boolean.parseBoolean(Configuration
				.getGlobalProperty(ConfigurationConstants.NOTIFIER_SEND_TEST_CALL)));

	}

	/**
	 * This method will load the client related details into cache
	 */
	private void loadClientCache() {

		final ClientCache clientCache = new ClientCache();
      Set<String> alliasNames = new HashSet<String>();
		final List<Client> clients = clientService.getAllClient();
		log.debug("---------Updating client cache now. Fetched "
				+ clients.size() + " from database--------");
		for (ListIterator<Client> it = clients.listIterator(); it.hasNext();) {
			Client client = (Client) it.next();
			if (client != null) {
				clientCache.put(client.getClientId(), client);
				if (StringUtils.isNotBlank(client.getImsInternalAlias())) {
					alliasNames.add(client.getImsInternalAlias());
				}
			}
		}
		clientCache.setAlliasNames(Collections.unmodifiableSet(alliasNames));
		CacheManager.getInstance().setCache(clientCache);
		log.debug("------------Client cache updated successfully---------");
	}

	public void loadAndUpdateAerospikeCache() {
		log.info("Aerospike cluster info: "
				+ Configuration
						.getGlobalProperty(ConfigurationConstants.AEROSPIKE_CLUSTER));
		imsCacheService.connectToCacheCluster(Configuration
				.getGlobalProperty(ConfigurationConstants.AEROSPIKE_CLUSTER));
	}

	private void loadEmailTemplates() {
		final List<ConfigDetails> configList = configDetailsDao.getAllConfigs();
		log.debug("---------Updating emailTemplate now--------");
		Map<String, String> map = new HashMap<String, String>();

		for (ListIterator<ConfigDetails> it = configList.listIterator(); it
				.hasNext();) {

			ConfigDetails configDetails = (ConfigDetails) it.next();
			if (configDetails != null) {
				String type = configDetails.getConfigType();
				String key = configDetails.getConfigKey();

				if (isEmailTemplateKey(key)) {
					map.put(type + "." + key, configDetails.getConfigValue());
				}
			}
		}
		notifier.registerOrRefreshEmailTemplate(map);
		log.debug("------------EmailTemplate registered successfully---------");
	}

	private boolean isEmailTemplateKey(String key) {
		return key.contains("template");
	}

	/**
	 * BlackList Entities load method.
	 */
	private void loadBlacklistCache() {

		final BlacklistCache blacklistCache = new BlacklistCache();
		final List<BlackList> entityList = blackEntityDao.getAllEntities();

		log.debug("---------Updating black list cache now. Fetched "
				+ entityList.size() + " entities from database--------");
		for (ListIterator<BlackList> it = entityList.listIterator(); it
				.hasNext();) {
			BlackList entityDetails = (BlackList) it.next();
			if (entityDetails != null) {
				EntityType key = entityDetails.getEntityType();
				String value = entityDetails.getEntity();
				Set<String> entities = blacklistCache.get(key);
				if (null == entities) {
					entities = new HashSet<String>();
				}
				entities.add(value);
				blacklistCache.put(key, entities);
			}
		}
		CacheManager.getInstance().setCache(blacklistCache);
		log.debug("------------Blacklist cache updated successfully---------");
	}

	/**
	 * WhiteList Apis load method.
	 */
	private void loadWhiteListApiCache() {

		final WhiteListAPICache whiteListAPICache = new WhiteListAPICache();
		final List<WhiteListAPI> apisList = whiteListAPIsDao.getAllEntities();

		log.debug("---------Updating white list apis cache now. Fetched "
				+ apisList.size() + " entities from database--------");
		Map<Long, IMSApi> imsApis = getIMSApis();
		for (ListIterator<WhiteListAPI> it = apisList.listIterator(); it
				.hasNext();) {
			WhiteListAPI whiteListAPIDetails = (WhiteListAPI) it.next();
			if (whiteListAPIDetails != null) {

				String clientId = whiteListAPIDetails.getClientId();
				boolean value = whiteListAPIDetails.isAllowed();

				IMSApi imsApi = imsApis.get(whiteListAPIDetails.getImsApiId());
				String apiUri = imsApi.getApiUri();
				String apiMethod = imsApi.getApiMethod();

				whiteListAPICache.put(clientId, apiUri, apiMethod, value);
			}
		}
		CacheManager.getInstance().setCache(whiteListAPICache);
		log.debug("------------WhiteListAPIs cache updated successfully---------");
	}

	private Map<Long, IMSApi> getIMSApis() {
		Map<Long, IMSApi> result = new HashMap<Long, IMSApi>();
		List<IMSApi> imsApis = imsAPIsDao.getAllIMSApis();
		if (imsApis != null) {
			for (IMSApi imsApi : imsApis) {
				result.put(imsApi.getId(), imsApi);
			}
		}
		return result;
	}

	private void loadVaultCache() {
		final VaultCache vaultCache = new VaultCache();
		log.debug("---------Updating Vault cache now.");
		if (Boolean.valueOf(Configuration
				.getGlobalProperty(ConfigurationConstants.VAULT_ENABLED))) {
			vaultCache.put(NewConfigurationConstant.CIPHER_UNIQUE_KEY
					.getValue(), Vault
					.getStoredKey(NewConfigurationConstant.CIPHER_UNIQUE_KEY
							.getValue()));
			/*log.debug("CIPHER_UNIQUE_KEY is" + vaultCache.get(NewConfigurationConstant.CIPHER_UNIQUE_KEY
					.getValue()));*/
			vaultCache
					.put(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION
							.getValue(),
							Vault.getStoredKey(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION
									.getValue()));
			/*log.debug("MD5_SALT_PASSWORD_ENCRYPTION is " + vaultCache.get(NewConfigurationConstant.MD5_SALT_PASSWORD_ENCRYPTION
							.getValue()));*/
			CacheManager.getInstance().setCache(vaultCache);
		}
		log.debug("------------Vault cache updated successfully---------");
	}

}
