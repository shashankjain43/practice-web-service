package com.snapdeal.ims.service.provider;

import com.snapdeal.ims.cache.BlacklistCache;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ICache;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.utility.EmailUtils;

import org.apache.commons.lang.math.NumberUtils;

import org.springframework.stereotype.Component;

import java.util.Random;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class RandomUpgradeChoiceUtil {

	private final Random random;

	private final int BASE = 100;

	private RandomUpgradeChoiceUtil() {
		this.random = new Random();
	}

	public boolean isEmailToBePickedForUpgrade(String clientId, String email) {
		if (!Boolean.valueOf(Configuration.getProperty(clientId,
				ConfigurationConstants.UPGRADE_ENABLED))) {
			return false;
		}
		if (isBlackListedUser(email)) {
			return false;
		}
		int randomNumber = 0;
		synchronized (random) {
			randomNumber = random.nextInt(BASE);
		}
		if (randomNumber <= getConfiguredUpgradePercentage(clientId)) {
			return true;
		}
		return false;

	}

	public boolean isBlackListedUser(String email) {

		if(email==null){
			return false;
		}
		
		ICache<EntityType, Set<String>> blacklistCache = CacheManager
				.getInstance().getCache(BlacklistCache.class);
		Set<String> entities = blacklistCache.get(EntityType.EMAIL);
		if (null == entities) {
			return false;
		}
		if (entities.contains(email)) {
			return true;
		}
		entities = blacklistCache.get(EntityType.DOMAIN);
		if (null == entities) {
			return false;
		}
		String domain = EmailUtils.getDomainFromEmail(email);
		if (entities.contains(domain)) {
			return true;
		}
		return false;
	}

   private int getConfiguredUpgradePercentage(String clientId) {
      int percentage = 0;

      String upgradePercentage = Configuration.getProperty(clientId,
               ConfigurationConstants.UPGRADE_PERCENTAGE);
      try {
         if (NumberUtils.isNumber(upgradePercentage)) {
            percentage = Integer.parseInt(upgradePercentage);
         }
         // If upgrade percentage is not configured for client, the pick up
         // global configuration.
         if (-1 == percentage) {
            String globalProperty = Configuration
                     .getGlobalProperty(ConfigurationConstants.UPGRADE_PERCENTAGE);
            if (NumberUtils.isNumber(globalProperty)) {
               percentage = Integer.parseInt(globalProperty);
            }
         }
      } catch (NumberFormatException ne) {
         log.error("Upgrade percentage is not configured for client id: " + clientId);
      }
      return percentage;
   }
}