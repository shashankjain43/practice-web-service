package com.snapdeal.ims.client.impl;


import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.apache.mina.http.api.HttpMethod;

import com.snapdeal.ims.client.IMobileVerificationServiceClient;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.MobileVerificationStatusRequest;
import com.snapdeal.ims.request.UniqueMobileVerificationRequest;
import com.snapdeal.ims.response.MobileVerificationStatusResponse;
import com.snapdeal.ims.response.UniqueMobileVerificationResponse;

public class MobileVerificationServiceClientImpl 
   extends AbstractClientImpl
   implements IMobileVerificationServiceClient {

   @Override
   public UniqueMobileVerificationResponse
   						verifyUniqueMobile(UniqueMobileVerificationRequest request)
      throws ServiceException, HttpTransportException {

      return prepareResponse(request,
                             UniqueMobileVerificationResponse.class, 
                             HttpMethod.POST,
                             RestURIConstants.MOBILE_VERIFY);
   }

   @Override
   public MobileVerificationStatusResponse 
   			mobileVerificationStatus(MobileVerificationStatusRequest request) 
      throws ServiceException, HttpTransportException {

	  if (StringUtils.isBlank(request.getUserId())) {
		throw new ServiceException(
				IMSRequestExceptionCodes.USER_ID_IS_BLANK.errMsg(),
				IMSRequestExceptionCodes.USER_ID_IS_BLANK.errCode());
	  }

      return prepareResponse(request,
                             MobileVerificationStatusResponse.class, 
                             HttpMethod.GET,
                             RestURIConstants.USERS + "/" 
                                                    + request.getUserId() 
                                                    + RestURIConstants.MOBILE_VERIFICATION_STATUS);
   }
}
