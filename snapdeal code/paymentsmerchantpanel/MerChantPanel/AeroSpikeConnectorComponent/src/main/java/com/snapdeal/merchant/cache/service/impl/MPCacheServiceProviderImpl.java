package com.snapdeal.merchant.cache.service.impl;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Service;

import com.aerospike.client.AerospikeException;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.cluster.Node;
import com.google.common.cache.CacheLoader.InvalidCacheLoadException;
import com.snapdeal.merchant.aero.client.impl.AeroSpikeClientWrapper;
import com.snapdeal.merchant.aero.config.AerospikeConfiguration;
import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.cache.client.ICacheClient;
import com.snapdeal.merchant.cache.service.IMerchantCacheServiceProvider;

@Service
@Slf4j
public class MPCacheServiceProviderImpl implements IMerchantCacheServiceProvider {

	@Autowired
	private AerospikeConfiguration config;

	private String hostname;
	private int port;

	private String clusterInfo;

	private ICacheClient aerospikeClient;

	public String getHostname() {
		return hostname;
	}

	public int getPort() {
		return port;
	}

	@Override
	public boolean isConnected() {
		if (aerospikeClient == null) {
			log.warn("AerospikeClient is NULL.");
			return false;
		}
		return aerospikeClient.isConnected();
	}

	@Override
	public void connectToCacheCluster(String clusterInfo)
			throws AerospikeException {
		try {
			if (this.aerospikeClient == null || this.clusterInfo == null
					|| !this.clusterInfo.equals(clusterInfo) || !isConnected()) {
				String[] nodes = clusterInfo.split(",");
				Host[] hosts = new Host[nodes.length];
				for (int i = 0; i < nodes.length; i++) {
					hosts[i] = new Host(nodes[i].split(":")[0],
							Integer.parseInt(nodes[i].split(":")[1]));
				}

				// Load Configurations
				
				/*this.config = new AerospikeConfiguration();
				this.config.loadConfiguration();*/
				 

				log.info("Going to initialize AerospikeClient. Hosts are: "
						+ hosts);
				try {
					Class.forName("com.aerospike.client.cluster.Node");
				} catch (ClassNotFoundException e) {
					throw new RuntimeException(e);
				}
				this.aerospikeClient = new AeroSpikeClientWrapper(
						this.config.getClientPolicy(), hosts);
				if (isConnected()) {
					log.info("Connected to Aerospike cluster");
					this.clusterInfo = clusterInfo;
				} else {
					log.error("Unable to connect to Aerospike cluster");
					throw new RuntimeException(
							"Unable to connect to Aerospike cluster");
				}
			} else {
				log.info("No change in Aerospike connection properties");
			}

			// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : this.aerospikeClient.getNodes()) {
				log.info(node.getHost().toString());
			}

		} catch (AerospikeException aex) {
			handleAerospikeException(clusterInfo,
					"Exception when connecting to Aerospike: ", aex);
		}
	}

	
	public ICacheClient getClient() throws AerospikeException {
		if (aerospikeClient == null) {
			throw new AerospikeException("Could not initialize aerospike");
		}
		return aerospikeClient;
	}

	@Override
	public AerospikeConfiguration getClientConfig()
			throws AerospikeException {
		if (config == null) {
			throw new AerospikeException("Could not init aeroclient config");
		}
		return config;
	}

	private void handleAerospikeException(String clusterInfo, String string,
			AerospikeException aex) throws AerospikeException {
		log.error(string + clusterInfo, aex);
		// since aerosipke is our primary source of truth, if not able to
		// connect due to any situation runtime exception should be thrown
		throw new AerospikeException("Aero connection failure");

	}
}
