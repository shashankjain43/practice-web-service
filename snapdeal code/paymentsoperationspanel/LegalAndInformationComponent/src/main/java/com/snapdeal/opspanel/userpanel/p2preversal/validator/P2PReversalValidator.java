package com.snapdeal.opspanel.userpanel.p2preversal.validator;

import java.io.File;
import java.math.BigDecimal;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.opspanel.userpanel.p2preversal.constants.P2PReversalConstants;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.ImsIdTypes;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.P2PReverseTxnTypes;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Component
public class P2PReversalValidator implements IBulkValidator {

	@Autowired
	RoleMgmtClient rmsClient;

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ValidationResponse validateRow(String[] headers, String[] values, Map<String, String> fileMetaData) {
		// TODO Auto-generated method stub
		ImsIdTypes idType;
		String errorCode = null;
		String errorMessage = null;
		Boolean isValid = false;
		P2PReverseTxnTypes txnType;
		try{
		String txnTyp = fileMetaData.get("txn_type");
		txnType = P2PReverseTxnTypes.valueOf(txnTyp);
		}catch(Exception e){
			errorCode = "P2PR-1010";
			errorMessage = "Transaction Type is not correct. Please Check and try Again";
			isValid = false;
			log.info("Transaction Type is not correct. Error Occured "+ ExceptionUtils.getFullStackTrace(e));
			return new ValidationResponse(errorCode, errorMessage, isValid);
		}
		
		switch (txnType) {
		case P2P_PARTIAL_REVERSE_TRANSACTION:
			idType = ImsIdTypes.valueOf(fileMetaData.get("id_type"));
			if (headers.length != 4 || values.length != 4) {
				errorCode = "P2PR-1001";
				errorMessage = "No. Of Column must  be equal to (FOUR) . Please Check and try again.";
				isValid = false;
				log.info("Validation Failed For P2P Partial Reverse Transaction. " + errorMessage);
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			for (int i = 0; i < values.length; i++) {
				if (values[i].length() == 0) {
					errorCode = "P2PR-1002";
					errorMessage = "Values can not be left blank in the File.Please Check";
					isValid = false;
					log.info("Validation Failed For P2P Partial Reverse Transaction." + errorMessage);
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}
			}

			/*
			 * Validation For Mobile Of Source An Destination
			 */

			if (idType.equals(ImsIdTypes.MOBILE)) {
				// validation For Mobile
				Boolean isSourceMobileValidated = isValidMobile(values[1]);

				if (!isSourceMobileValidated) {
					errorCode = "P2PR-1004";
					errorMessage = "Source Mobile Number is Not Valid.Please Check";
					isValid = false;
					log.info("Validation Failed For Source Mobile Number in P2P Partial Reverse Transaction.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}
				Boolean isDestinationMobileValidated = isValidMobile(values[2]);
				if (!isDestinationMobileValidated) {
					errorCode = "P2PR-1004";
					errorMessage = "Destination Mobile Number is Not Valid.Please Check";
					isValid = false;
					log.info("Validation Failed For Destination Mobile Number in P2P Partial Reverse Transaction.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}

			}
			/*
			 * Validation For Email Of Source An Destination
			 */

			if (idType.equals(ImsIdTypes.EMAIL)) {
				// Validation For Email
				Boolean isSourceEmailValid = isEmailValid(values[1]);
				if (!isSourceEmailValid) {
					errorCode = "P2PR-1004";
					errorMessage = "Source Email is Not Valid.Please Check";
					isValid = false;
					log.info("Validation Failed For Source Email in P2P Partial Reverse Transaction.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}
				Boolean isDestinationEmailValid = isEmailValid(values[2]);
				if (!isDestinationEmailValid) {
					errorCode = "P2PR-1004";
					errorMessage = "Destination Email is Not Valid.Please Check";
					isValid = false;
					log.info("Validation Failed For Destination Email in P2P Partial Reverse Transaction.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}

			}

			/*
			 * Validation For Amount
			 */

			Boolean isAmountNumeric = isAmountValid(values[3]);
			if (!isAmountNumeric) {
				errorCode = "P2PR-1003";
				errorMessage = "Amount not valid. It should be numeric and greater than 0. Please Check";
				isValid = false;
				log.info("Validation Failed For P2P Partial Reverse Transaction.Amount Values is not numeric.");
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}

			isValid = true;
			return new ValidationResponse(errorCode, errorMessage, isValid);

		case P2P_FULL_REVERSE_TRANSACTION:
			if (values.length != 2 && headers.length != 2) {
				errorCode = "P2PR-1005";
				errorMessage = "Column length must be equal to 2. Please check and try again";
				isValid = false;
				log.info("Vaidation failed for P2P Full Reversal Transaction . Column length is not equal to 2.");
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			for (int i = 0; i < values.length; i++) {
				if (values[i].length() == 0) {
					errorCode = "P2PR-1006";
					errorMessage = "Values can not be left blank in the File.Please Check";
					isValid = false;
					log.info("Validation Failed For P2P Full Reverse Transaction." + errorMessage);
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}
			}
			String refundKey = fileMetaData.get("refund_key");
			refundKey = refundKey.trim();
			if (refundKey.length() > 15) {
				errorCode = "P2PR-1007";
				errorMessage = "Length Of refund key must not be greater than 15. ";
				isValid = false;
				log.info("Length od refund key is greater than 15 i.e. :" + refundKey);
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			if (refundKey.length() == 0) {
				errorCode = "P2PR-1008";
				errorMessage = "Refund Key can not be left blank";
				isValid = false;
				log.info(errorMessage);
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}

		}
		return null;
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermissionForAction(String userId, BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub

		String permission = P2PReversalConstants.OPS_LEGAL_PARTIAL_P2P_REVERSE_TRANSACTION;
		String token = headerValues.get("token");

		AuthrizeUserRequest request = new AuthrizeUserRequest();
		request.setRequestToken(token);
		request.setPreAuthrizeString(permission);
		try {
			rmsClient.authorizeUser(request);
		} catch (Exception e) {
			return false;
		}

		return true;
	}

	public static boolean isAmountValid(String str) {
		try {
			BigDecimal bigDecimal = new BigDecimal(str);
			return bigDecimal.compareTo(BigDecimal.ZERO) > 0;
		} catch (Exception e) {
			return false;
		}
	}

	public static boolean isValidMobile(String str) {
		return str.matches(P2PReversalConstants.mobileRegex);
	}

	public static boolean isEmailValid(String email) {
		return email.matches(P2PReversalConstants.emailRegex);
	}

}
