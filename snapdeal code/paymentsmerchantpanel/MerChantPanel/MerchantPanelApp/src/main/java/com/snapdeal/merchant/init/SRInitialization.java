package com.snapdeal.merchant.init;


import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.payments.settlement.report.client.SettlementReportClient;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class SRInitialization {

	
	@Autowired
	private MpanelConfig config;
	
	@PostConstruct
	public void initSR() throws Exception{
		try {
			
			new SettlementReportClient(config.getSrHostURL(), config.getSrClientId(), config.getSrClientKey());
		} catch (Exception e) {
			log.error("Error occurred in Merchant View  initialization {}", e);
			throw e;
		}
	}
	
}
