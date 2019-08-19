package com.snapdeal.merchant.client.impl;

import java.lang.reflect.Type;

import org.apache.mina.http.api.HttpMethod;

import com.google.gson.reflect.TypeToken;
import com.snapdeal.merchant.client.IMerchantManagementServiceClient;
import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.client.util.RestURIConstants;
import com.snapdeal.merchant.entity.response.GenericMerchantResponse;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.response.MerchantDetailResponse;
import com.snapdeal.merchant.response.MerchantRoleResponse;

public class MerchantManagemantServiceClientImpl extends AbstractClientImpl
         implements IMerchantManagementServiceClient {

   @Override
   public MerchantDetailResponse getMerchantDetails(MerchantProfileRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantDetailResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantDetailResponse.class,type, HttpMethod.GET,
               RestURIConstants.MERCHANT + RestURIConstants.GET_PROFILE);
   }

   @Override
   public MerchantRoleResponse getMerchantRoles(MerchantRoleRequest request)
            throws MerchantException, HttpTransportException {
	   Type type = new TypeToken<GenericMerchantResponse<MerchantRoleResponse>>() {
		}.getType();
      return prepareResponse(request, MerchantRoleResponse.class,type, HttpMethod.GET,
               RestURIConstants.MERCHANT + RestURIConstants.GET_ROLES);

   }

}
