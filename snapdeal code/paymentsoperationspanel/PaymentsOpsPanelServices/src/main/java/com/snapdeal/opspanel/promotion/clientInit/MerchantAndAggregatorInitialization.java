package com.snapdeal.opspanel.promotion.clientInit;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.view.utils.ClientDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MerchantAndAggregatorInitialization {
	
	@Value("${payments.mv.ip}")
	private  String mvIP;
	
	@Value("${payments.mv.port}")
	private  String mvPort;
	
	@Value("${payments.mv.apitimeout}")
	private  int mvApiTimeOut;
	
	@Value("${payments.mv.clientid}")
	private  String mvClientId;
	
	@Value("${payments.mv.clientkey}")
	private  String mvClientKey;
	
	@Value("${payments.ag.ip}")
	private  String aggIP;
	
	@Value("${payments.ag.port}")
	private  String aggPort;
	
	@Value("${payments.ag.apitimeout}")
	private  int aggApiTimeout;
	
	@Value("${payments.ag.clientid}")
	private  String aggClientId;
	
	@Value("${payments.ag.clientkey}")
	private  String aggClientKey;
	
	@PostConstruct
	private void initMV() throws Exception {
		try {
			ClientDetails.init(mvIP.trim(), mvPort.trim(), mvClientKey.trim(), mvClientId.trim(), mvApiTimeOut);
		} catch (Exception e) {
			log.error("Error occurred in Merchant View  initialization {}", e);
			throw e;
		}
	}
	
	@PostConstruct
	private void initAggregator() throws Exception {
		try {
			com.snapdeal.payments.aggregator.utils.ClientDetails.init(aggIP.trim(), aggPort.trim(), aggApiTimeout,
					aggClientId.trim(), aggClientKey.trim());
		} catch (Exception e) {
			log.error("Error occurred in aggregator initialization {}", e);
			throw e;
		}
	}

}