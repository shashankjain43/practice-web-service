package com.snapdeal.merchant.client;

import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.response.MerchantDetailResponse;
import com.snapdeal.merchant.response.MerchantRoleResponse;

public interface IMerchantManagementServiceClient {

   public MerchantDetailResponse getMerchantDetails(MerchantProfileRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantRoleResponse getMerchantRoles(MerchantRoleRequest request)
            throws MerchantException, HttpTransportException;
}
