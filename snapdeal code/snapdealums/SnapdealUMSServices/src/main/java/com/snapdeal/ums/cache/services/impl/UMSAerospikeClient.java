package com.snapdeal.ums.cache.services.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.aerospike.client.AerospikeClient;
import com.aerospike.client.AerospikeException;
import com.aerospike.client.Bin;
import com.aerospike.client.Host;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.cluster.Node;
import com.aerospike.client.policy.ClientPolicy;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;

/**
 * Wrapper around raw AerospikeClient to achieve:
 * 
 * 1. Logging of every event 2. CRUD operations of AerospikeClient are final.
 * This class provides intercept options.
 * 
 * @author ashish
 * 
 */
public class UMSAerospikeClient {

	private static final Logger LOG = LoggerFactory
			.getLogger("CacheOperationsLogger");

	private AerospikeClient aerospikeClient;

	public boolean isConnected() {
		return aerospikeClient.isConnected();
	}

//	UMSAerospikeClient(ClientPolicy clientPolicy, String hostname, int port) {
//		try {
//			LOG.info(
//					"Creating new client: Policy: {} || HostName: {} || Port: {}",
//					new Object[] { clientPolicy, hostname, port });
//			aerospikeClient = new AerospikeClient(clientPolicy, hostname, port);
//		} catch (AerospikeException aex) {
//			LOG.error("AerospikeException @ initialization", aex);
//			throw aex;
//		}
//	}

	UMSAerospikeClient(ClientPolicy clientPolicy, Host[] hosts) {
		LOG.info("In constructor of UMSAerospikeClient. Initializing with hosts: "+hosts);
		aerospikeClient = new AerospikeClient(clientPolicy, hosts);
	}

	public Node[] getNodes() {

		Node[] nodes = aerospikeClient.getNodes();
		if (nodes != null && nodes.length > 0) {
			LOG.info("Connected to:");
			// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : nodes) {
				LOG.info(node.getHost().toString());
			}
		} else {
			LOG.error("AerospikeClient could not fetch any NODES!!");
		}
		return nodes;
	}

	public Record get(Policy policy, Key key) {

		Record record = null;

		try {
			record = aerospikeClient.get(policy, key);
			logCacheOperation(OPERATION.GET, key, record);
			return record;
		} catch (AerospikeException aex) {
			LOG.error("AerospikeException @ GET", aex);
			throw aex;

		}

	}

	public void put(WritePolicy writePolicy, Key key, Bin bin) {
		try {
			Object objectToBePut = null;
			if (bin != null && bin.value != null) {
				objectToBePut = bin.value.getObject();
			}
			logCacheOperation(OPERATION.PUT, key, objectToBePut);
			aerospikeClient.put(writePolicy, key, bin);
		} catch (AerospikeException aex) {
			LOG.error("AerospikeException @ PUT", aex);
			throw aex;

		}
	}

	public boolean exists(WritePolicy writePolicy, Key key) {
		boolean result = false;
		try {
			result = aerospikeClient.exists(writePolicy, key);
			logCacheOperation(OPERATION.EXISTS, key, result);
		} catch (AerospikeException aex) {
			// We got AerospikeException. To be on safe side, let us evict the
			// key
			delete(writePolicy, key);
			LOG.error("AerospikeException @ EXISTS", aex);
			throw aex;

		}
		return result;

	}

	public boolean delete(WritePolicy writePolicy, Key key) {

		boolean result = false;
		try {
			result = aerospikeClient.delete(writePolicy, key);
			logCacheOperation(OPERATION.DELETE, key, result);
		} catch (AerospikeException aex) {
			LOG.error("AerospikeException @ DELETE", aex);
			throw aex;

		}
		return result;
	}

	private enum OPERATION {
		GET, PUT, DELETE, EXISTS
	}

	void logCacheOperation(OPERATION operation, Key key, Object record) {

		try {
			LOG.info("{} {} - {}", new Object[] { operation, key, record });
		} catch (Exception ex) {
			LOG.warn("Exception while trying to log cache event!", ex);
		}

	}

}
