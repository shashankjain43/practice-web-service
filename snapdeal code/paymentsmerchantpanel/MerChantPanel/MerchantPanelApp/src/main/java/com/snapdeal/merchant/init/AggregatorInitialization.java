package com.snapdeal.merchant.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.payments.aggregator.utils.ClientDetails;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class AggregatorInitialization {

	@Autowired
	private MpanelConfig config;

	@PostConstruct
	private void initAggregator() throws Exception {
		try {
			ClientDetails.init(config.getAggIP(), config.getAggPort(), config.getAggApiTimeout(),
					config.getAggClientId(), config.getAggClientKey());
		} catch (Exception e) {
			log.error("Error occurred in aggregator initialization {}", e);
			throw e;
		}
	}

}
