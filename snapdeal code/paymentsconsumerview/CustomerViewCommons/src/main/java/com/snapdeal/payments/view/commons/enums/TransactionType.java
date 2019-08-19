package com.snapdeal.payments.view.commons.enums;

import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewValidationExceptionCodes;

public enum TransactionType {
	CUSTOMER_PAYMENT, CANCELLATION_REFUND, OPS_WALLET_DEBIT, OPS_WALLET_CREDIT, OPS_WALLET_CREDIT_REVERSE, OPS_WALLET_CREDIT_VOID, OPS_WALLET_DEBIT_REVERSE, OPS_WALLET_DEBIT_VOID, OPS_WALLET_DEBIT_VOID_REVERSE, PAYABLES_LOAD, PAYABLES_CHARGE, PAYABLES_REFUND, PAYABLES_DISBURSEMENT, P2P_SEND_MONEY, P2P_REQUEST_MONEY, P2P_PAY_TO_MID, WALLET_CORP_DISBURSEMENT,SPLIT_MONEY,ADD_CASH,FC_WALLET_CASHBACK,
	ESCROW_PAYMENT;
	
	public static TransactionType forvalue(String value) {
		for (TransactionType type : TransactionType.values()) {
			if (type.name().equals(value))
				return type;
		}
		throw new PaymentsViewServiceException(
				PaymentsViewValidationExceptionCodes.NOT_SUPPORTED_PARAM
						.errCode(),
				"Transaction Type Not Supported");

	}

}
