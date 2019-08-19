package com.snapdeal.opspanel.userpanel.bulkReverseTxn;

import java.io.File;
import java.util.Map;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;

public class BulkReverseTxnValidator implements IBulkValidator{

	@Override
	public ValidationResponse validateRow(String[] headers, String[] values,
			Map<String, String> fileMetaData) {
		// TODO Auto-generated method stub
		return new ValidationResponse(null, null, true);
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData, File file) {
		// TODO Auto-generated method stub
		return new ValidationResponse(null, null, true);
	}

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return false;
	}

}
