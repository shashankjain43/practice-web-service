package com.snapdeal.ims.cache.service.impl;

import lombok.extern.slf4j.Slf4j;

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
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

/**
 * Wrapper around raw AerospikeClient to achieve:
 * 
 * 1. Logging of every event 2. CRUD operations of AerospikeClient are final.
 * 
 * @author kishan
 * 
 */
@Slf4j
public class IMSAerospikeClient {

	private AerospikeClient aerospikeClient;

	public boolean isConnected() {
		return aerospikeClient.isConnected();
	}

	public IMSAerospikeClient(ClientPolicy clientPolicy, Host[] hosts) {
		log.info("In constructor of IMSAerospikeClient. Initializing with hosts: "
				+ hosts);
		aerospikeClient = new AerospikeClient(clientPolicy, hosts);
	}

	public Node[] getNodes() {

		Node[] nodes = aerospikeClient.getNodes();
		if (nodes != null && nodes.length > 0) {
			log.info("Connected to:");
			// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : nodes) {
				log.info(node.getHost().toString());
			}
		} else {
			log.error("AerospikeClient could not fetch any NODES!!");
		}
		return nodes;
	}

	@Timed
	@Marked
	public Record get(Policy policy, Key key) {

		Record record = null;

		try {
			record = aerospikeClient.get(policy, key);
			return record;
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ GET", aex);
			throw aex;

		}

	}

	@Timed
	@Marked
	public void put(WritePolicy writePolicy, Key key, Bin bin) {
		try {
			Object objectToBePut = null;
			if (bin != null && bin.value != null) {
				objectToBePut = bin.value.getObject();
			}
			aerospikeClient.put(writePolicy, key, bin);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ PUT", aex);
			throw aex;

		}
	}

	@Timed
	@Marked
	public boolean exists(WritePolicy writePolicy, Key key) {
		boolean result = false;
		try {
			result = aerospikeClient.exists(writePolicy, key);
		} catch (AerospikeException aex) {
			// We got AerospikeException. To be on safe side, let us evict the
			// key
			delete(writePolicy, key);
			log.error("AerospikeException @ EXISTS", aex);
			throw aex;

		}
		return result;

	}

	@Timed
	@Marked
	public boolean delete(WritePolicy writePolicy, Key key) {

		boolean result = false;
		try {
			result = aerospikeClient.delete(writePolicy, key);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ DELETE", aex);
			throw aex;

		}
		return result;
	}

	@Timed
	@Marked
	public void touch(WritePolicy writePolicy, Key key) {

		try {
			aerospikeClient.touch(writePolicy, key);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ Touch", aex);
			throw aex;
		}
	}
}
