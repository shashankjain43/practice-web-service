package com.snapdeal.ims.service.healthcheck;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.aerospike.client.Log;
import com.snapdeal.payments.healthcheck.Pingable;
import com.snapdeal.payments.healthcheck.StorePingable;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Component
public class UserHealthCheck implements StorePingable {

	@Autowired
	@Qualifier("UserDaoImpl")
	Pingable pingable;

	@Timed
	@Marked
	@Logged
	@Override
	public List<String> getFailedDomains() {
		List<String> failedDomains = new ArrayList<String>();

		if (!pingable.isHealthy()) {
			Log.error("failure : " + pingable.getClass().getName());
			failedDomains.add(pingable.getClass().getName());
		}

		return failedDomains;
	}

}
