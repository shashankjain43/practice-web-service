package com.snapdeal.vanila.bulk.BulkTID;

import java.io.File;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;

@Component
@Slf4j
public class BulkTIDValidator implements IBulkValidator{

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
		return null;
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData,
			File file) {
		
		String actionType = fileMetaData.get(BulkTIDConstants.ACTION_TYPE);
		if(actionType == null){
			log.info("ActionType Not Found or Invalid!");
			return new ValidationResponse("BT-004", BulkTIDConstants.ACTION_TYPE_INVALID, false);
		}
		
		String merchantId = fileMetaData.get(BulkTIDConstants.MERCHANT_ID);
		if(merchantId == null){
			log.info("MerchantId Not Found or Invalid!");
			return new ValidationResponse("BT-001", BulkTIDConstants.MERCHANT_ID_INVALID, false);
		}

		String providerMerchantId = fileMetaData.get(BulkTIDConstants.PROVIDER_MERCHANT_ID);
		if(providerMerchantId == null){
			log.info("Provider MerchantId Not Found or Invalid!");
			return new ValidationResponse("BT-002", BulkTIDConstants.PROVIDER_MERCHANT_ID_INVALID, false);
		}

		String platformId = fileMetaData.get(BulkTIDConstants.PLATFORM_ID);
		if(platformId == null){
			log.info("PlatformId Not Found or Invalid!");
			return new ValidationResponse("BT-003", BulkTIDConstants.PLATFORM_ID_INVALID, false);
		}
		
		return new ValidationResponse(null, null, true);
	}

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		String tokenFromHeaders = headerValues.get(BulkTIDConstants.TOKEN);
		String permission = BulkTIDConstants.getPermissionForAction(action.toString());

		AuthrizeUserRequest request = new AuthrizeUserRequest();
		request.setRequestToken(tokenFromHeaders);
		request.setPreAuthrizeString(permission);
		try {
			rmsClient.authorizeUser(request);
		} catch (Exception e) {
			log.info(e.getClass().getName() + "from RMS : " + e.getMessage() + "while authorizeUser for user " + userId + " \n");
			return false;
		}
		
		return true;
	}

}
