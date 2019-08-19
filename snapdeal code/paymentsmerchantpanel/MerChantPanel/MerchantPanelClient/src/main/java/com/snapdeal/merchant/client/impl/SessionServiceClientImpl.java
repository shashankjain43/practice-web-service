package com.snapdeal.merchant.client.impl;


import java.lang.reflect.Type;

import org.apache.mina.http.api.HttpMethod;

import com.google.gson.reflect.TypeToken;
import com.snapdeal.merchant.client.ISessionServiceClient;
import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.client.util.RestURIConstants;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;

public class SessionServiceClientImpl extends AbstractClientImpl implements ISessionServiceClient {

   @Override
   public MerchantLoginResponse login(MerchantLoginRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantLoginResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantLoginResponse.class,type, HttpMethod.POST,
               RestURIConstants.SESSION + RestURIConstants.LOGIN);

   }

   @Override
   public MerchantLogoutResponse logout(MerchantLogoutRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantLogoutResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantLogoutResponse.class,type, HttpMethod.POST,
               RestURIConstants.SESSION + RestURIConstants.LOGOUT);

   }

@Override
public MerchantSetPasswordResponse setPassword(MerchantSetPasswordRequest request)
		throws MerchantException, HttpTransportException {
	
	Type type = new TypeToken<GenericMerchantResponse<MerchantSetPasswordResponse>>() {
	}.getType();
  return prepareResponse(request, MerchantSetPasswordResponse.class,type, HttpMethod.POST,
           RestURIConstants.SESSION + RestURIConstants.SET_PASSWORD);
}

}
