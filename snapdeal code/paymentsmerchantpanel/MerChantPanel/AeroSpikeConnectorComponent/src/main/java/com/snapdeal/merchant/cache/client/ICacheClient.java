package com.snapdeal.merchant.cache.client;

import com.aerospike.client.Bin;
import com.aerospike.client.Key;
import com.aerospike.client.Record;
import com.aerospike.client.cluster.Node;
import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.QueryPolicy;
import com.aerospike.client.policy.WritePolicy;
import com.aerospike.client.query.RecordSet;
import com.aerospike.client.query.Statement;
import com.snapdeal.merchant.aero.exception.MPAerospikeException;

public interface ICacheClient {
	
	public boolean isConnected() ;

	public Node[] getNodes() ;

	public Record get(Policy policy, Key key) throws MPAerospikeException;

	public void put(WritePolicy writePolicy, Key key, Bin bin) throws MPAerospikeException;

	public boolean exists(WritePolicy writePolicy, Key key) throws MPAerospikeException;

	public boolean delete(WritePolicy writePolicy, Key key) throws MPAerospikeException;
	
	public void touch(WritePolicy writePolicy, Key key) throws MPAerospikeException;
}
