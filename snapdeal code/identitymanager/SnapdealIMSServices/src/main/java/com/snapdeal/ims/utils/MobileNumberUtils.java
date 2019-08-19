package com.snapdeal.ims.utils;

import com.snapdeal.ims.common.constant.CommonConstants;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

public class MobileNumberUtils {

	public static void mobileValidator(String mobileNumber){
		if(StringUtils.isBlank(mobileNumber)){
			throw new IMSServiceException(IMSServiceExceptionCodes.MOBILE_NUMBER_IS_BLANK.errCode(),IMSServiceExceptionCodes.MOBILE_NUMBER_IS_BLANK.errMsg());
		}
		if(!mobileNumber.matches(CommonConstants.MOBILE_NUMBER_REGEX) || StringUtils.length(mobileNumber) != CommonConstants.MOBILE_NUMBER_DIGIT){
			throw new IMSServiceException(IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errCode(),IMSServiceExceptionCodes.WRONG_MOBILE_NUMBER.errMsg());		
		}
	}
}
