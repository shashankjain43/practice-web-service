package com.snapdeal.merchant.aero.config;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.merchant.config.constants.AeroConfigConstants;
import com.snapdeal.merchant.config.constants.AeroQueryConfig;
import com.snapdeal.merchant.config.constants.AeroQueryConstants;
import com.snapdeal.merchant.util.RequestContext;

/**
 * 
 * @author mridul
 *
 */
@Component
public class AerospikeConfiguration {

	private ClientPolicy clientPolicy;

	private Policy readPolicy;
	
	private WritePolicy tokenWritePolicy;

	private Map<RequestContext, AeroQueryConfig> contextToQueryConfig;

	@Autowired
	private AeroConfigConstants constants;

	@PostConstruct
	public void loadConfiguration() {
		// initialize client policy
		this.clientPolicy = new ClientPolicy();

		String maxSocketIdle = constants.getSocketIdle();
		if (maxSocketIdle != null && !maxSocketIdle.isEmpty()) {
			this.clientPolicy.maxSocketIdle = Integer.parseInt(maxSocketIdle);
		}

		Integer maxThreads = constants.getMaxThread();
		if (maxThreads != null && maxThreads > 0) {
			this.clientPolicy.maxThreads = maxThreads;
		}

		String isThreadPoolShared = constants.getIsSharedThreadPool();
		if (isThreadPoolShared != null && !isThreadPoolShared.isEmpty()) {
			this.clientPolicy.sharedThreadPool = Boolean
					.parseBoolean(isThreadPoolShared);
		}

		Integer connTimeout = constants.getConnTimeOut();
		if (connTimeout != null && connTimeout >= 0) {
			this.clientPolicy.timeout = connTimeout;
		}

		// initialize default read policy
		this.readPolicy = new Policy();

		Integer maxRetries = constants.getMaxReadRetry();
		if (maxRetries != null && maxRetries >= 0) {
			this.readPolicy.maxRetries = maxRetries;
		}

		Integer sleepInterval = constants.getSleepBetReadRetry();
		if (sleepInterval != null && sleepInterval >= 0) {
			this.readPolicy.sleepBetweenRetries = sleepInterval;
		}

		Integer readTimeout = constants.getDefReadTimeOut();
		if (readTimeout != null && readTimeout >= 0) {
			this.readPolicy.timeout = readTimeout;
		}
		
		this.tokenWritePolicy = new WritePolicy();
		Integer tokenExpiration = constants.getSessionExpiration();
		if(tokenExpiration != null) {
			this.tokenWritePolicy.expiration = tokenExpiration;
		}
		Integer writeRetryCount = constants.getMaxWriteRetry();
		if(writeRetryCount != null && writeRetryCount >=0){
			this.tokenWritePolicy.maxRetries = writeRetryCount;
		}
		
		populateContextToQueryConfig();
	}

	public ClientPolicy getClientPolicy() {
		return clientPolicy;
	}

	public Policy getReadPolicy() {
		return readPolicy;
	}
	
	public WritePolicy getTokenWritePolicy() {
		return tokenWritePolicy;
	}

	public Map<RequestContext, AeroQueryConfig> getContextToQueryConfig() {
		return contextToQueryConfig;
	}
	
	private void populateContextToQueryConfig() {
		this.contextToQueryConfig = new HashMap<RequestContext, AeroQueryConfig>();
		for (RequestContext context : RequestContext.values()) {
			switch (context) {
			case EMAIL_TOKEN_LIST_CACHE:
				AeroQueryConfig emailToTokenQueryConfig = new AeroQueryConfig(
						AeroQueryConstants.EMAIL_TOKEN_LIST_SET,
						AeroQueryConstants.EMAIL_TOKEN_LIST_BIN,
						this.tokenWritePolicy, this.readPolicy);
				contextToQueryConfig.put(RequestContext.EMAIL_TOKEN_LIST_CACHE,
						emailToTokenQueryConfig);
				break;
				
			case TOKEN_USER_CACHE:
				AeroQueryConfig tokentoUserQueryConfig = new AeroQueryConfig(
						AeroQueryConstants.TOKEN_USER_CACHE_SET,
						AeroQueryConstants.TOKEN_USER_CACHE_BIN,
						this.tokenWritePolicy, this.readPolicy);
				contextToQueryConfig.put(RequestContext.TOKEN_USER_CACHE,
						tokentoUserQueryConfig);
				break;
			default:
				break;
			}
		}
	}
}
