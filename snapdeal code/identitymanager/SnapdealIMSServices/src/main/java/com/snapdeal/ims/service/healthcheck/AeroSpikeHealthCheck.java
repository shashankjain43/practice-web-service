package com.snapdeal.ims.service.healthcheck;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aerospike.client.Log;
import com.snapdeal.payments.healthcheck.Pingable;
import com.snapdeal.payments.healthcheck.StorePingable;

@Component
public class AeroSpikeHealthCheck implements StorePingable {

	@Autowired
	@Qualifier("IMSCacheServiceProviderImpl")
	Pingable pingable;

	@Override
	public List<String> getFailedDomains() {
		List<String> failedDomains = new ArrayList<String>();

		if (!pingable.isHealthy()) {
			Log.error("failure : " + "Problem in connecting with aerospike");
			failedDomains.add(pingable.getClass().getName());
		}

		return failedDomains;
	}

}
