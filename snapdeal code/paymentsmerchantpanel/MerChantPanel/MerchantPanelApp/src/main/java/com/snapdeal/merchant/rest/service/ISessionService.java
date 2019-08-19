package com.snapdeal.merchant.rest.service;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;

public interface ISessionService {

   public MerchantLoginResponse login(MerchantLoginRequest request) throws MerchantException;

   public MerchantLogoutResponse logout(MerchantLogoutRequest request) throws MerchantException;

   public MerchantSetPasswordResponse setPassword(MerchantSetPasswordRequest request) throws MerchantException;
}
