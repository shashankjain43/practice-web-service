package com.snapdeal.merchant.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.util.AppConstants;
//import com.snapdeal.payments.customerView.utils.ClientDetails;
import com.snapdeal.payments.view.utils.ClientDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MVInitialization {

	@Autowired
	private MpanelConfig config;

	@PostConstruct
	private void initMV() throws Exception {
		try {
			ClientDetails.init(config.getMvIP(), config.getMvPort(), config.getMvClientKey(), config.getMvClientId(),
					config.getMvApiTimeOut());
		} catch (Exception e) {
			log.error("Error occurred in Merchant View  initialization {}", e);
			throw e;
		}
	}
}
