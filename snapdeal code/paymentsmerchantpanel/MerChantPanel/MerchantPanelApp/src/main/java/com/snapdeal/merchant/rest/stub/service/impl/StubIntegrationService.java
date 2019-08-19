package com.snapdeal.merchant.rest.stub.service.impl;


import org.springframework.stereotype.Service;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantKeyRequest;
import com.snapdeal.merchant.response.MerchantKeyResponse;
import com.snapdeal.merchant.rest.service.IIntegrationService;

@Service
public class StubIntegrationService implements IIntegrationService {

	@Override
	public MerchantKeyResponse getMerchantSandBoxKey(MerchantKeyRequest request) throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public MerchantKeyResponse getMerchantProductionKey(MerchantKeyRequest request) throws MerchantException {
		// TODO Auto-generated method stub
		return null;
	}

}
