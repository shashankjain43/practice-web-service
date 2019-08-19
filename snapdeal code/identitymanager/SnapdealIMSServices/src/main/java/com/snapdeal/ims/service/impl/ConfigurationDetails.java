package com.snapdeal.ims.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;
import com.snapdeal.ims.enums.GetConfigurationDetails;
import com.snapdeal.ims.errorcodes.IMSDefaultExceptionCodes;
import com.snapdeal.ims.exception.InternalServerException;
import com.snapdeal.ims.otp.util.OTPUtility;
import com.snapdeal.ims.response.ConfigurationDetailsResponse;
import com.snapdeal.ims.service.IConfigurationDetails;

@Service
public class ConfigurationDetails implements IConfigurationDetails{

	@Autowired
	private OTPUtility otpUtility ;

	private ConfigurationDetailsResponse getForgotPasswordConfigurations() {
		ConfigurationDetailsResponse response = new ConfigurationDetailsResponse();
		Map<String, String> forgotPasswordConfigurations = new HashMap<String, String>();
		
		forgotPasswordConfigurations.put("reset.password.send.otp.on.mobile",Configuration
		.getGlobalProperty(ConfigurationConstants.RESET_PASSWORD_SEND_OTP_ON_MOBILE));
		forgotPasswordConfigurations.put("reset.password.send.otp.on.email",Configuration
				.getGlobalProperty(ConfigurationConstants.RESET_PASSWORD_SEND_OTP_ON_EMAIL));	
		response.setConfigurationMap(forgotPasswordConfigurations);
		return response;
	}


	private ConfigurationDetailsResponse getOTPConfigurationDetails() {
		// TODO Auto-generated method stub
		ConfigurationDetailsResponse response = new ConfigurationDetailsResponse();
		Map<String, String> otpConfiguration = new HashMap<String, String>();

		otpConfiguration.put("expiryDurationInMins",
				String.valueOf(otpUtility.getExpiryDurationInMins()));
		otpConfiguration.put("reSendAttemptsLimit",
				String.valueOf(otpUtility.getReSendAttemptsLimit()));
		otpConfiguration.put("invalidAttemptsLimit",
				String.valueOf(otpUtility.getInvalidAttemptsLimit()));
		otpConfiguration.put("blockDurationInMins",
				String.valueOf(otpUtility.getBlockDurationInMins()));

		response.setConfigurationMap(otpConfiguration);
		return response;
	}

	@Override
	public ConfigurationDetailsResponse getConfigurationDetails(
			GetConfigurationDetails getConfigurationFor) {

		switch (getConfigurationFor) {
		case OTP:
			return getOTPConfigurationDetails();
		case RESET_PASSWORD:
			return getForgotPasswordConfigurations();
		default:
			throw new InternalServerException(
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errCode(),
					IMSDefaultExceptionCodes.INTERNAL_SERVER.errMsg());
		}

	}
}
