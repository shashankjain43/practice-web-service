package com.snapdeal.merchant.init;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.fcNotifier.utils.ClientDetails;
import com.snapdeal.merchant.config.MpanelConfig;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class FCNotifierIntialization {

	@Autowired
	private MpanelConfig config;

	@PostConstruct
	private void initFCNotifier() throws Exception {
		try {
			log.info("notif host is : {}{}",config.getFcNotifierHost(),"sd");
			log.info("notif port is : {}{}",config.getFcNotifierPort(),"sd");
			log.info("notif apitimeout is : {}",config.getFcNotifierApiTimeout());
			ClientDetails.init(config.getFcNotifierHost(),config.getFcNotifierPort(), config.getFcNotifierApiTimeout());
		} catch (Exception e) {
			log.error("Error occurred in mob initialization {}", e);
			throw e;
		}
	}
}
