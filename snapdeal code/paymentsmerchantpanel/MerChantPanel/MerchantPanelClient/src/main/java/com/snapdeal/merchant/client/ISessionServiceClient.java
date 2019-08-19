package com.snapdeal.merchant.client;

import com.snapdeal.merchant.client.exception.HttpTransportException;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;

public interface ISessionServiceClient {

   public MerchantLoginResponse login(MerchantLoginRequest request)
            throws MerchantException, HttpTransportException;

   public MerchantLogoutResponse logout(MerchantLogoutRequest request)
            throws MerchantException, HttpTransportException;
   
   public MerchantSetPasswordResponse setPassword(MerchantSetPasswordRequest request) throws MerchantException ,HttpTransportException;

}
