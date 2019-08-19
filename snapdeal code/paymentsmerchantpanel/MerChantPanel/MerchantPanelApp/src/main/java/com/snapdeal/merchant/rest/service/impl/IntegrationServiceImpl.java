package com.snapdeal.merchant.rest.service.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.request.MerchantKeyRequest;
import com.snapdeal.merchant.response.MerchantKeyResponse;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.service.IIntegrationService;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.mob.dto.Key;
import com.snapdeal.mob.enums.IntegrationMode;
import com.snapdeal.mob.enums.MerchantStatus;
import com.snapdeal.mob.response.GetMerchantKeysResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.payments.metrics.annotations.Logged;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class IntegrationServiceImpl implements IIntegrationService {

	@Autowired
	MOBUtil mobUtil;

	@Autowired
	MpanelConfig config;

	@Logged
	@Override
	public MerchantKeyResponse getMerchantSandBoxKey(MerchantKeyRequest request) throws MerchantException {

		MerchantGetUserMerchantRequest getMerchantRequest = new MerchantGetUserMerchantRequest();

		getMerchantRequest.setToken(request.getToken());
		getMerchantRequest.setUserId(request.getLoggedUserId());

		GetUserMerchantResponse mobResponseDetails = mobUtil.getMerchantForUser(getMerchantRequest);

		String intMode = mobResponseDetails.getMerchantDetails().getIntegrationMode();

		if (!(IntegrationMode.ONLINE.getIntegrationMode().equalsIgnoreCase(intMode))) {
			throw new MerchantException(ErrorConstants.INVALID_REQUEST_FOR_MERCHANT_CODE,ErrorConstants.INVALID_REQUEST_FOR_MERCHANT_MSG);
		}

		GetMerchantKeysResponse mobResponse = mobUtil.getMerchantSandBoxKey(request);

		MerchantKeyResponse response = new MerchantKeyResponse();

		List<Key> mobKeysList = mobResponse.getKeys();

		for (Key key : mobKeysList) {

			if (key.isActive()) {
				response.setKey(key.getKey());
				break;
			}

		}

		response.setUrl(config.getSandboxUrl());

		return response;

	}

	@Logged
	@Override
	public MerchantKeyResponse getMerchantProductionKey(MerchantKeyRequest request) throws MerchantException {

		MerchantGetUserMerchantRequest getMerchantRequest = new MerchantGetUserMerchantRequest();

		getMerchantRequest.setToken(request.getToken());
		getMerchantRequest.setUserId(request.getLoggedUserId());

		GetUserMerchantResponse mobResponseDetails = mobUtil.getMerchantForUser(getMerchantRequest);

		String intMode = mobResponseDetails.getMerchantDetails().getIntegrationMode();

		String merchantStatus = mobResponseDetails.getMerchantDetails().getMerchantStatus();

		if (!(IntegrationMode.ONLINE.getIntegrationMode().equalsIgnoreCase(intMode))) {
			throw new MerchantException(ErrorConstants.INVALID_REQUEST_FOR_MERCHANT_CODE,ErrorConstants.INVALID_REQUEST_FOR_MERCHANT_MSG);
		}

		
		if (!(MerchantStatus.ACTIVE.getMerchantStatus().equalsIgnoreCase(merchantStatus))) {
			log.info("only active merchant can see prod keys");
			throw new MerchantException(ErrorConstants.ACTIVE_MERCHANT_PRODUCTION_KEY_MSG);
		}

		GetMerchantKeysResponse mobResponse = mobUtil.getMerchantProdKey(request);

		MerchantKeyResponse response = new MerchantKeyResponse();

		List<Key> mobKeysList = mobResponse.getKeys();

		for (Key key : mobKeysList) {

			if (key.isActive()) {
				response.setKey(key.getKey());
				break;
			}

		}

		response.setUrl(config.getProdUrl());

		return response;
	}
	
	public void setConfig(MpanelConfig config)
	{
		this.config=config;
	}
	

}
