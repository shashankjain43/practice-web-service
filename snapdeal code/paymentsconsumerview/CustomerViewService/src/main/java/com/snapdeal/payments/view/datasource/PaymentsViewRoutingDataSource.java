package com.snapdeal.payments.view.datasource;

import org.springframework.jdbc.datasource.lookup.AbstractRoutingDataSource;

import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;

public class PaymentsViewRoutingDataSource extends AbstractRoutingDataSource {

	@Override
	protected Object determineCurrentLookupKey() {
		return PaymentsViewShardContextHolder.getShardKey() ;
	}
}
