package com.snapdeal.ims.cache.configuration;

import org.springframework.stereotype.Service;

import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.ims.common.Configuration;
import com.snapdeal.ims.common.ConfigurationConstants;

/**
 * @version 1.0, june8, 2015
 * @author Kishan
 */

public class AerospikeConfiguration {

	private ClientPolicy clientPolicy;
	private Policy readPolicy;
	private WritePolicy writePolicy;
		
	public void loadConfiguration() {
		// initialize client policy
		this.clientPolicy = new ClientPolicy();

		String maxSocketIdle = Configuration
				.getGlobalProperty(ConfigurationConstants.SOCKET_IDLE);
		if (maxSocketIdle != null && !maxSocketIdle.isEmpty()) {
			this.clientPolicy.maxSocketIdle = Integer.parseInt(maxSocketIdle);
		}

		String maxThreads = Configuration
				.getGlobalProperty(ConfigurationConstants.MAX_THREADS);
		if (maxThreads != null && !maxThreads.isEmpty()) {
			this.clientPolicy.maxThreads = Integer.parseInt(maxThreads);
		}

		String isThreadPoolShared = Configuration
				.getGlobalProperty(ConfigurationConstants.SHARED_THREADS);
		if (isThreadPoolShared != null && !isThreadPoolShared.isEmpty()) {
			this.clientPolicy.sharedThreadPool = Boolean
					.parseBoolean(isThreadPoolShared);
		}

		String connTimeout = Configuration
				.getGlobalProperty(ConfigurationConstants.CONNECTION_TIMEOUT);
		if (connTimeout != null && !connTimeout.isEmpty()) {
			this.clientPolicy.timeout = Integer.parseInt(connTimeout);
		}

		// initialize default read policy
		this.readPolicy = new Policy();

		String maxRetries = Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_MAX_READ_RETRIES);
		if (maxRetries != null && !maxRetries.isEmpty()) {
			this.readPolicy.maxRetries = Integer.parseInt(maxRetries);
		}

		String sleepInterval = Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_SLEEP_BETWEEN_READ_RETRIES);
		if (sleepInterval != null && !sleepInterval.isEmpty()) {
			this.readPolicy.sleepBetweenRetries = Integer
					.parseInt(sleepInterval);
		}

		String readTimeout = Configuration
				.getGlobalProperty(ConfigurationConstants.DEFAULT_READ_TIMEOUT);
		if (readTimeout != null && !readTimeout.isEmpty()) {
			this.readPolicy.timeout = Integer.parseInt(readTimeout);
		}
		this.writePolicy = new WritePolicy();
	}

	public ClientPolicy getClientPolicy() {
		return clientPolicy;
	}

	public Policy getReadPolicy() {
		return readPolicy;
	}

	public WritePolicy getWritePolicy() {
		return writePolicy;
	}

}
