package com.snapdeal.merchant.config.constants;

import lombok.Data;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

@Data
@Component
public class AeroConfigConstants {
	
	@Value("${aero.namespace}")
	private String namespace;
	
	@Value("${aero.socket.idle}")
	private String socketIdle;
	
	@Value("${aero.max.threads}")
	private Integer maxThread;
	
	@Value("${aero.shared.threads}")
	private String isSharedThreadPool;
	
	@Value("${aero.connection.timeout}")
	private Integer connTimeOut;
	
	@Value("${aero.read.timeout}")
	private Integer defReadTimeOut;
	
	@Value("${aero.maxread.retry}")
	private Integer maxReadRetry;
	
	@Value("${aero.sleep.readretry}")
	private Integer sleepBetReadRetry;
	
	@Value("${aero.read.timeout}")
	private Integer defWriteTimeOut;
	
	@Value("${aero.maxwrite.retry}")
	private Integer maxWriteRetry;
	
	@Value("${aero.sleep.writeretry}")
	private Integer sleepBetWriteRetry;
	
	@Value("${aero.token.session.expiration}")
	private Integer sessionExpiration;
	
	@Value("${aero.cluster.info}")
	private String clusterInfo;
}
