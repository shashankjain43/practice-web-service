package com.snapdeal.payments.view.aerospike.utils;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import lombok.Getter;
import lombok.Setter;

@Component
@Getter
@Setter
public class AerospikeClientModel {

	@Value("${aerospike.host}")
	private String host;

	@Value("${aerospike.port}")
	private Integer port;

	@Value("${aerospike.connection.timeout}")
	private String timeout;

	@Value("${aerospike.default.read.maxRetries}")
	private String maxRetries;
	
	@Value("${aerospike.default.read.sleepBetweenRetries}")
	private String sleepbetweenRetries;
	
	@Value("${aerospike.eviction.transaction.iteration.limit}")
	private String iterationLimit;
	
	@Value("${aerospike.eviction.transaction.size}")
	private String transactionSize;
	
	@Value("${aerospike.inconsistent.key.eviction.enabled}")
	private String evictionEnabled;

	@Value("${aerospike.maxThreads}")
	private String maxThreads;
	
	@Value("${aerospike.sharedThreadPool}")
	private String sharedThreadPool;
	
	@Value("${aerospike.default.read.timeout}")
	private String readTimeOut ;
	
	@Value("${aerospike.maxSocketIdle}")
	private String maxSocketIdleTime ;

}
