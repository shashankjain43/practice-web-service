package com.snapdeal.payments.view.commons.exception.service;

public class PaymentsViewAerospikeException extends PaymentsViewGenericException {

	private static final long serialVersionUID = 3480539223261710510L;

	public PaymentsViewAerospikeException(String errCode, String errMsg) {
		super(errCode, errMsg);
	}
}
