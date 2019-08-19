package com.snapdeal.opspanel.userpanel.p2preversal.constants;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.P2PReverseTxnTypes;

public class P2PReversalConstants {

	public static final String P2P_PARTIAL_REVERSAL = "P2P_PARTIAL_REVERSAL";

	public static final String P2P_PARTIAL_REVERSAL_DESTINATION_PREFIX = "p2preversal";

	public static final String SUCCESS = "SUCCESS";

	public static final String FAILURE = "FAILURE";

	public static final String CAUTION = "CAUTION";

	public static final String NA = "NA";

	public static final String OPS_LEGAL_PARTIAL_P2P_REVERSE_TRANSACTION = "OPS_LEGAL_PARTIAL_P2P_REVERSE_TRANSACTION";

	public static final String OPS_LEGAL_FULL_P2P_REVERSE_TRANSACTION = "OPS_LEGAL_PARTIAL_P2P_FULL_TRANSACTION";

	public static final String transactionNotFoundMessage = "Transaction not found.";

	public static final String amountNotValidMessage = "Amount given for this reversal is not valid";

	public static final String excessAmountMessage = "Given amount is greater than actual txn amount for this Idempotency ID";

	public static final String mobileRegex = "^[6-9]{1}[0-9]{9}$";

	public static final String emailRegex = "^[A-Za-z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]+$";

	public static String getPermissionForAction(String action, String activityId) {

	
		if(activityId.equalsIgnoreCase(P2PReverseTxnTypes.P2P_PARTIAL_REVERSE_TRANSACTION.toString())){
			if (action.equals(BulkProcessEnum.UPLOAD)) {

			}
			if (action.equals(BulkProcessEnum.LIST_PAGE)) {

			}
			if (action.equals(BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER)) {

			}
			if (action.equals(BulkProcessEnum.DOWNLOAD_FILE)) {

			}
		}

		if(activityId.equalsIgnoreCase(P2PReverseTxnTypes.P2P_FULL_REVERSE_TRANSACTION.toString())){
			if (action.equals(BulkProcessEnum.UPLOAD)) {

			}
			if (action.equals(BulkProcessEnum.LIST_PAGE)) {

			}
			if (action.equals(BulkProcessEnum.LIST_PAGE_FOR_SUPERUSER)) {

			}
			if (action.equals(BulkProcessEnum.DOWNLOAD_FILE)) {

			}
		
		}
		return null;
	}

}
