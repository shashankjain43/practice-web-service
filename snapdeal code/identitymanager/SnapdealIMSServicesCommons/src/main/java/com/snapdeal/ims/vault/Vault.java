package com.snapdeal.ims.vault;

import java.io.IOException;
import java.util.HashMap;

import lombok.extern.slf4j.Slf4j;

import com.securitymanager.connection.KeyManager;
import com.securitymanager.connection.KeyManagerConnectionException;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.NewConfigurationConstant;

@Slf4j
public class Vault {

	private static KeyManager keyManager = new KeyManager(
			Configuration
					.getGlobalProperty(ConfigurationConstants.VAULT_CONF_PATH_ABSOLUTE));
	private static final String MD5_SALT_PASSWORD_ENCRYPTION = "snapdealsaltforpassword123876heysaltie";

	public static String getStoredKey(String value) {
		try {
			keyManager.authenticate();
			HashMap<String, String> hmap = keyManager.getCredentials();
			return hmap.get(value);
		} catch (KeyManagerConnectionException | IOException e) {
			log.error("Unable to connect to Vault", e);
			// This is temp code needs to be remove and exception needs to be
			// thrown once vault behaved properly
			if (NewConfigurationConstant.CIPHER_UNIQUE_KEY.getValue().equals(
					value)) {

				return Configuration
						.getGlobalProperty(ConfigurationConstants.CIPHER_UNIQUE_KEY);
			} else {
				return MD5_SALT_PASSWORD_ENCRYPTION;
			}
			/*
			 * throw new IMSServiceException(IMSServiceExceptionCodes.
			 * ERROR_ON_GETTING_KEY_FROM_VAULT
			 * .errCode(),IMSServiceExceptionCodes
			 * .ERROR_ON_GETTING_KEY_FROM_VAULT.errMsg());
			 */
		}
	}

}
