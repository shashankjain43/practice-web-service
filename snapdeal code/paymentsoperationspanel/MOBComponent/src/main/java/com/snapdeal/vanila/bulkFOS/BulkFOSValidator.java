package com.snapdeal.vanila.bulkFOS;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;
@Component
public class BulkFOSValidator implements IBulkValidator{
	
	@Autowired
	RoleMgmtClient rmsClient;

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public ValidationResponse validateRow(String[] headers, String[] values,
			Map<String, String> fileMetaData) {
		// TODO Auto-generated method stub
		return new ValidationResponse(null, null, true);
	}


	@Override
	public ValidationResponse validate(Map<String, String> map, File file) {
		if(!map.containsKey("idType") || !map.containsKey("acquisitionType")){
			return new ValidationResponse("BFOS-001", BulkFOSConstants.UI_DATA_MISSING, false);
		}
		return new ValidationResponse(null, null, true);
	}

	@Override
	public boolean hasPermissionForAction(String userId, BulkProcessEnum action, Map<String, String> headerValues) {
		String permission = BulkFOSConstants.getPermissionForAction(action.toString());
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

}
