package com.snapdeal.ims.utils;

import java.io.IOException;

import com.securitymanager.connection.KeyManagerConnectionException;
import com.snapdeal.base.utils.MD5ChecksumUtils;
import com.snapdeal.core.utils.SDEncryptionUtils;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.common.NewConfigurationConstant;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.vault.Vault;

public class PasswordHashServiceUtil {

	public static String getSdHashedPassword(String password){
		return IMSEncryptionUtil.getSDEncryptedPassword(password);
	}

	public static String getFcHashedPassword(String password) {
		// TODO : need to correct the hashing algorithm
		// This will be sd hashed only from now on as we get plain password from
		// FC
		return IMSEncryptionUtil.getSDEncryptedPassword(password);
	}

	public static String getOcHashedPassword(String password){
		//TODO : need to correct the hashing algorithm
		// This will be sd hashed only from now on as we get plain password from
		// FC
		return IMSEncryptionUtil.getSDEncryptedPassword(password);
	}


}
