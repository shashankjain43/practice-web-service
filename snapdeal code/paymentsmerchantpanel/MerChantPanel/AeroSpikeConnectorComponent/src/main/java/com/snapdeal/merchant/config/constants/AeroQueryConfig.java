package com.snapdeal.merchant.config.constants;

import com.aerospike.client.policy.Policy;
import com.aerospike.client.policy.WritePolicy;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
public class AeroQueryConfig {

	private final String querySet;

	private final String queryBin;

	private final WritePolicy queryWritePolicy;

	private final Policy queryPolicy;

	public AeroQueryConfig(String querySet, String queryBin,
			WritePolicy queryWritePolicy, Policy queryPolicy) {
		this.querySet = querySet;
		this.queryBin = queryBin;
		this.queryWritePolicy = queryWritePolicy;
		this.queryPolicy = queryPolicy;
	}

}
