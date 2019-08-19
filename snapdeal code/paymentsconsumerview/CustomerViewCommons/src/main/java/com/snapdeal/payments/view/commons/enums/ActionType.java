package com.snapdeal.payments.view.commons.enums;

import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewValidationExceptionCodes;

public enum ActionType {
	P2P_SEND_MONEY, P2P_REQUEST_MONEY,SPLIT_MONEY,ESCROW_PAYMENT;

	public static ActionType forvalue(String value) {
		for (ActionType type : ActionType.values()) {
			if (type.name().equals(value))
				return type;
		}
		throw new PaymentsViewServiceException(
				PaymentsViewValidationExceptionCodes.NOT_SUPPORTED_PARAM
						.errCode(),
				"Transaction Type Not Supported");

	}

}
