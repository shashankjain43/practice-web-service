package com.snapdeal.opspanel.userpanel.walletreversal.validator;

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
import com.snapdeal.opspanel.userpanel.walletreversal.Enum.InstrumentType;
import com.snapdeal.opspanel.userpanel.walletreversal.constants.WalletReversalConstants;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Component
public class WalletReversalValidator implements IBulkValidator {

	@Autowired
	RoleMgmtClient rmsClient;

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermissionForAction(String userId, BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub

		String permission = WalletReversalConstants.WALLET_REVERSAL_PERMISSION;
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

	@Override
	public ValidationResponse validateRow(String[] header, String[] values, Map<String, String> map) {
		String txnType = map.get("txnid_type");
		String instrumentType = map.get("instrumentType");
		if(txnType==null || instrumentType==null) {
			return new ValidationResponse("BULK-0512", "Txn Type or instrumentType can not be null", false);
		} else if (instrumentType.equals(InstrumentType.VOUCHER.name())&& (values.length != 2 || values[0] == null || values[1] == null)) {
			return new ValidationResponse("BULK-0512", "Both fields mandatory", false);
		} else if (instrumentType.equals(InstrumentType.WALLET.name()) && (values.length != 1 || values[0] == null)) {
			return new ValidationResponse("BULK-0513", "First field is mandatory", false);
		} else {
			return new ValidationResponse(null, null, true);
		}	
	}
}
