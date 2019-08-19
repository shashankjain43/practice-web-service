package com.snapdeal.ims.utils;

import java.io.IOException;

import com.securitymanager.connection.KeyManagerConnectionException;
import com.snapdeal.core.utils.SDEncryptionUtils;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;

public class IMSEncryptionUtil {


	public static String getSDEncryptedPassword(String password){
		if(Boolean.valueOf(Configuration.getGlobalProperty(ConfigurationConstants.VAULT_ENABLED))){
			try {
				return Configuration.getMD5EncodedPassword(password);
			} catch (KeyManagerConnectionException | IOException e) {
				throw new IMSServiceException(IMSServiceExceptionCodes.ERROR_ON_GETTING_KEY_FROM_VAULT.errCode(),IMSServiceExceptionCodes.ERROR_ON_GETTING_KEY_FROM_VAULT.errMsg());
			}
		}else{
			return SDEncryptionUtils.getMD5EncodedPassword(password);
		}
	}

}
