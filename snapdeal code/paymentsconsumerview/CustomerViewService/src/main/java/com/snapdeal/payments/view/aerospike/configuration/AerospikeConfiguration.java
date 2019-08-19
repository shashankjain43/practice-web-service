package com.snapdeal.payments.view.aerospike.configuration;

/**
 * @version 1.0, june8, 2015
 * @author Kishan
 */
import javax.annotation.PostConstruct;

import lombok.Getter;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;
import com.snapdeal.payments.view.aerospike.utils.AerospikeClientModel;

@Component
@Getter
public class AerospikeConfiguration {

	/**/
	private ClientPolicy clientPolicy;
	private Policy readPolicy;
	private WritePolicy writePolicy;

	@Autowired
	private AerospikeClientModel clientModel;

	@PostConstruct
	public void loadConfiguration() {
		// initialize client policy
		this.clientPolicy = new ClientPolicy();

		String maxSocketIdle = clientModel.getMaxSocketIdleTime();
		if (maxSocketIdle != null && !maxSocketIdle.isEmpty()) {
			this.clientPolicy.maxSocketIdle = Integer.parseInt(maxSocketIdle);
		}

		String maxThreads = clientModel.getMaxThreads();
		if (maxThreads != null && !maxThreads.isEmpty()) {
			this.clientPolicy.maxThreads = Integer.parseInt(maxThreads);
		}

		String isThreadPoolShared = clientModel.getSharedThreadPool();
		if (isThreadPoolShared != null && !isThreadPoolShared.isEmpty()) {
			this.clientPolicy.sharedThreadPool = Boolean
					.parseBoolean(isThreadPoolShared);
		}

		String connTimeout = clientModel.getTimeout();
		if (connTimeout != null && !connTimeout.isEmpty()) {
			this.clientPolicy.timeout = Integer.parseInt(connTimeout);
		}

		// initialize default read policy
		this.readPolicy = new Policy();

		String maxRetries = clientModel.getMaxRetries();
		if (maxRetries != null && !maxRetries.isEmpty()) {
			this.readPolicy.maxRetries = Integer.parseInt(maxRetries);
		}

		String sleepInterval = clientModel.getSleepbetweenRetries();
		if (sleepInterval != null && !sleepInterval.isEmpty()) {
			this.readPolicy.sleepBetweenRetries = Integer
					.parseInt(sleepInterval);
		}

		String readTimeout = clientModel.getReadTimeOut();
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
