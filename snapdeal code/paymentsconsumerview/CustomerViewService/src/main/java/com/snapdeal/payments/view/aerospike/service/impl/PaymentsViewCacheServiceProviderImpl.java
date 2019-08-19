package com.snapdeal.payments.view.aerospike.service.impl;

import java.util.Arrays;

import javax.annotation.PostConstruct;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Host;
import com.aerospike.client.cluster.Node;
import com.snapdeal.payments.healthcheck.Pingable;
import com.snapdeal.payments.view.aerospike.configuration.AerospikeConfiguration;
import com.snapdeal.payments.view.aerospike.configuration.PaymentsViewAerospikeClient;
import com.snapdeal.payments.view.aerospike.service.IPaymentsViewCacheServiceProvider;
import com.snapdeal.payments.view.aerospike.utils.AerospikeClientModel;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewAerospikeExceptionCodes;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewAerospikeException;

@Service
@Slf4j
public class PaymentsViewCacheServiceProviderImpl implements IPaymentsViewCacheServiceProvider,
		Pingable {

	@Autowired
	private AerospikeConfiguration config;

	private PaymentsViewAerospikeClient imsAerospikeClient;

	@Autowired
	private AerospikeClientModel clientModel;

	@Override
	public boolean isConnected() {
		if (imsAerospikeClient == null) {
			log.warn("AerospikeClient is NULL.");
			return false;
		}
		return imsAerospikeClient.isConnected();
	}

	@PostConstruct
	@Override
	public void init() {
		try {
			if (this.imsAerospikeClient == null || !isConnected()) {

				String[] hosts = clientModel.getHost().split(",");
				Host[] aeroSpikeHosts = new Host[hosts.length];

				for (int i = 0; i < aeroSpikeHosts.length; i++) {
					Host aerospikeHost = new Host(hosts[i],
							clientModel.getPort());
					aeroSpikeHosts[i] = aerospikeHost;
				}

				// Load Configurations
				/*this.config = new AerospikeConfiguration();
				this.config.loadConfiguration();*/

				log.info("Going to initialize IMSAerospikeClient. Hosts are: "
						+ Arrays.toString(hosts));
				try {
					Class.forName("com.aerospike.client.cluster.Node");
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
				this.imsAerospikeClient = new PaymentsViewAerospikeClient(
						this.config.getClientPolicy(), aeroSpikeHosts);
				if (isConnected()) {
					log.info("Connected to Aerospike cluster");
				} else {
					log.error("Unable to connect to Aerospike cluster");
					throw new RuntimeException(
							"Unable to connect to Aerospike cluster");
				}
			} else {
				log.info("No change in Aerospike connection properties");
			}

			// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : this.imsAerospikeClient.getNodes()) {
				log.info("aerospike node are : " + node.getHost().toString());
			}

		} catch (AerospikeException aex) {
			handleAerospikeException(clientModel.getHost(),
					"Exception when connecting to Aerospike: ", aex);

		}
	}

	@Override
	public PaymentsViewAerospikeClient getClient() {
		
		 if (imsAerospikeClient == null) { 
			 throw new PaymentsViewAerospikeException(
					 PaymentsViewAerospikeExceptionCodes .AEROSPIKE_CLIENT_NOT_INITIALIZED.errCode(),
		 			 PaymentsViewAerospikeExceptionCodes.AEROSPIKE_CLIENT_NOT_INITIALIZED.errMsg()); 
		 }
		 
		return imsAerospikeClient;
	}

	@Override
	public AerospikeConfiguration getClientConfig() {
		
		 if (config == null) { 
			 throw new PaymentsViewAerospikeException(
					 PaymentsViewAerospikeExceptionCodes.AEROSPIKE_CLIENT_CONFIG_NOT_PRESENT.errCode(),
					 PaymentsViewAerospikeExceptionCodes.AEROSPIKE_CLIENT_CONFIG_NOT_PRESENT.errMsg()); 
		 }
		 
		return config;
	}

	private void handleAerospikeException(String clusterInfo2, String string,
			AerospikeException aex) {
		log.error(string + clusterInfo2, aex);
		// since aerosipke is our primary source of truth, if not able to
		// connect due to any situation runtime exception should be thrown
		//throw new RuntimeException(aex);

	}


	@Override
	public boolean isHealthy() {
		return isConnected();
	}
}