package com.snapdeal.opspanel.promotion.bulkframework;

import java.io.File;
import java.util.Map;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;

public class FileValidator implements IBulkValidator {

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidationResponse validate(Map<String, String> arg0, File arg1) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public ValidationResponse validateRow(String[] arg0, String[] arg1,
			Map<String, String> arg2) {
		// TODO Auto-generated method stub
		return null;
	}
}
