package com.snapdeal.payments.view.aerospike.configuration;

import java.util.Arrays;

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

@Slf4j
public class PaymentsViewAerospikeClient {

	private AerospikeClient aerospikeClient;

	public boolean isConnected() {
		return aerospikeClient.isConnected();
	}

	private AerospikeClient client;

	public PaymentsViewAerospikeClient(ClientPolicy clientPolicy, Host[] hosts) {
		log.info("In constructor of AerospikeClient. Initializing with hosts: "
				+ Arrays.toString(hosts));
		aerospikeClient = new AerospikeClient(clientPolicy, hosts);
	}

	public AerospikeClient getAerospikeClientInstance() {
		return client;
	}

	public Node[] getNodes() {

		Node[] nodes = aerospikeClient.getNodes();
		if (nodes != null && nodes.length > 0) {
			log.info("Connected to:");
			/*// Prints hostname and port of all the cluster nodes to log stream
			for (Node node : nodes) {
				log.info(node.getHost().toString());
			}*/
		} else {
			log.error("AerospikeClient could not fetch any NODES!!");
		}
		return nodes;
	}

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

	public void touch(WritePolicy writePolicy, Key key) {

		try {
			aerospikeClient.touch(writePolicy, key);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ Touch", aex);
			throw aex;
		}
	}
}
