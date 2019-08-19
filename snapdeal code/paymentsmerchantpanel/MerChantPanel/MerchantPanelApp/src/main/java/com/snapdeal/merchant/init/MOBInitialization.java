package com.snapdeal.merchant.init;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.mob.utils.ClientDetails;

@Slf4j
@Component
public class MOBInitialization {

	@Autowired
	private MpanelConfig config;

	@PostConstruct
	private void initMOB() throws Exception {
		try {
			ClientDetails.init(config.getMobClientName(), config.getMobIP(), config.getMobPort(),
					config.getMobApiTimeout());
		} catch (Exception e) {
			log.error("Error occurred in mob initialization {}", e);
			throw e;
		}
	}
}
