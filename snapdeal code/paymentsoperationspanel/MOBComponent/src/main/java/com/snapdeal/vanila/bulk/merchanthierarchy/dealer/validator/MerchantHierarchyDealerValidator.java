package com.snapdeal.vanila.bulk.merchanthierarchy.dealer.validator;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;
import com.snapdeal.vanila.bulk.merchanthierarchy.dealer.enums.DealerOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MerchantHierarchyDealerValidator implements IBulkValidator{

	@Autowired
	RoleMgmtClient rmsClient;
	
	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ValidationResponse validateRow(String[] headers, String[] values, Map<String, String> fileMetaData) {
		DealerOperationType operation_type = DealerOperationType.valueOf(fileMetaData.get("operation_type"));
		String errorCode=null;
		String errorMessage=null;
		Boolean isValid=true;
		switch (operation_type) {
		case ONBOARD:
			if(headers.length != 17){
				errorCode="MHDV-1001";
				errorMessage="No. Of Column must  be equal to (17) . Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For  Dealer Onboarding.");
				
			}
			if(values.length!=17){
				errorCode="MHDV-1002";
				errorMessage="No. Of Column must  be equal to (17)/Remove extra comma(,) from any field. Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For Row in Dealer Onboarding.");
			}
			return new ValidationResponse(errorCode, errorMessage, isValid);
		case UPDATE:
			
			if(headers.length != 17){
				errorCode="MHDV-1001";
				errorMessage="No. Of Column must  be equal to (17) . Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For  Dealer Update");
				
			}
			if(values.length!=17){
				errorCode="MHDV-1002";
				errorMessage="No. Of Column must  be equal to (17)/Remove extra comma(,) from any field. Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For Row in Dealer Onboarding.");
			}
			
			return new ValidationResponse(errorCode, errorMessage, isValid);
	
		default:
			return new ValidationResponse(errorCode, errorMessage, isValid);
		}
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermissionForAction(String userId, BulkProcessEnum action, Map<String, String> headerValues) {
		String permission = BulkMerchantHierarchyMerchantConstants.getPermissionForAction(action.toString());
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

	
	

