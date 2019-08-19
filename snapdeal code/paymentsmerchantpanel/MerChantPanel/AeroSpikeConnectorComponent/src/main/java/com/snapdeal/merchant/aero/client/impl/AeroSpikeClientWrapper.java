package com.snapdeal.merchant.aero.client.impl;

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
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.snapdeal.merchant.aero.exception.MPAerospikeException;
import com.snapdeal.merchant.cache.client.ICacheClient;

@Slf4j
public class AeroSpikeClientWrapper  implements ICacheClient{
	
	private AerospikeClient aerospikeClient;

	public boolean isConnected() {
		return aerospikeClient.isConnected();
	}

	public AeroSpikeClientWrapper(ClientPolicy clientPolicy, Host[] hosts) {
		log.info("In constructor of AeroSpikeClientWrapper. Initializing with hosts: "
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

	@Override
	public Record get(Policy policy, Key key) throws MPAerospikeException {

		Record record = null;

		try {
			record = aerospikeClient.get(policy, key);
			if(record == null) {
				log.error("Record not found for key {} ",key);
				return null;
			}
			return record;
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ GET", aex);
			throw new MPAerospikeException("Could not get record");
		} catch (Exception ex) {
			log.error("Record not found for key {} , exception ",key,ex);
			throw new MPAerospikeException("Could not get record");
		}

	}

	@Override
	public void put(WritePolicy writePolicy, Key key, Bin bin)
			throws MPAerospikeException {
		try {
			Object objectToBePut = null;
			if (bin != null && bin.value != null) {
				objectToBePut = bin.value.getObject();
			}
			aerospikeClient.put(writePolicy, key, bin);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ PUT", aex);
			throw new MPAerospikeException("Could not put record");
		}
	}

	@Override
	public boolean exists(WritePolicy writePolicy, Key key)
			throws MPAerospikeException {
		boolean result = false;
		try {
			result = aerospikeClient.exists(writePolicy, key);
		} catch (AerospikeException aex) {
			// We got AerospikeException. To be on safe side, let us evict the
			// key
			try {
				delete(writePolicy, key);
			}catch(AerospikeException ex) {
				log.error("AerospikeException delete error @ EXISTS", aex);
				throw new MPAerospikeException("Could not delete record in exist");
			}
		}
		return result;

	}

	@Override
	public boolean delete(WritePolicy writePolicy, Key key)
			throws MPAerospikeException {

		boolean result = false;
		try {
			result = aerospikeClient.delete(writePolicy, key);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ DELETE", aex);
			throw new MPAerospikeException("Could not delete record");

		}
		return result;
	}

	@Override
	public void touch(WritePolicy writePolicy, Key key)
			throws MPAerospikeException {

		try {
			aerospikeClient.touch(writePolicy, key);
		} catch (AerospikeException aex) {
			log.error("AerospikeException @ Touch", aex);
			throw new MPAerospikeException("Could not touch record");
		}
	}


}
