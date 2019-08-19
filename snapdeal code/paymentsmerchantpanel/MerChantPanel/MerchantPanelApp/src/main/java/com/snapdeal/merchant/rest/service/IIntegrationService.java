package com.snapdeal.merchant.rest.service;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantKeyRequest;
import com.snapdeal.merchant.response.MerchantKeyResponse;

public interface IIntegrationService {

	MerchantKeyResponse getMerchantSandBoxKey(MerchantKeyRequest request) throws MerchantException;
	
	MerchantKeyResponse getMerchantProductionKey(MerchantKeyRequest request) throws MerchantException;

}
