package com.snapdeal.vanila.bulk.merchanthierarchy.association.validator;

import java.io.File;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.enums.AssociationOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MerchantHierarchyAssociationValidator implements IBulkValidator {

	@Autowired
	RoleMgmtClient rmsClient;
	
	@Override
	public ValidationResponse validateRow(String[] headers, String[] values, Map<String, String> fileMetaData) {
		// TODO Auto-generated method stub
		AssociationOperationType operationType = AssociationOperationType.valueOf(fileMetaData.get("operation_type"));
	//	ValidationResponse response = new ValidationResponse(errorCode, errorMessage, isValid)
		String errorCode=null;
		String errorMessage=null;
		Boolean isValid=false;
		switch(operationType){
		case MERCHANT_DEALER:
			if(headers.length!=2 || values.length!=2){
				errorCode="MHAV-1001";
				errorMessage="No. Of Column must  be equal to (TWO) . Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For Mechant Dealer Association.");
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			for(int i=0 ; i<values.length ; i++){
				if(values[i].length()==0){
					errorCode="MHAV-1002";
					errorMessage="Values can not be left blank in the File.Please Check";
					isValid=false;
					log.info("Validation Failed For Mechant Dealer Association.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}
			}
			isValid=true;
			return new ValidationResponse(errorCode, errorMessage, isValid);
			
		case MERCHANT_PARTNER:
			if(headers.length!=2 || values.length!=2){
				errorCode="MHAV-1001";
				errorMessage="No. Of Column must  be equal to (TWO) . Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For Mechant Dealer Association.");
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			for(int i=0 ; i<values.length ; i++){
				if(values[i].length()==0){
					errorCode="MHAV-1002";
					errorMessage="Values can not be left blank in the File.Please Check";
					isValid=false;
					log.info("Validation Failed For Mechant Dealer Association.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}
			}
			isValid=true;
			return new ValidationResponse(errorCode, errorMessage, isValid);
			
		case MERCHANT_PLATFORM:
			if(headers.length!=2 || values.length!=2){
				errorCode="MHAV-1001";
				errorMessage="No. Of Column must  be equal to (TWO) . Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For Mechant Dealer Association.");
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			for(int i=0 ; i<values.length ; i++){
				if(values[i].length()==0){
					errorCode="MHAV-1002";
					errorMessage="Values can not be left blank in the File.Please Check";
					isValid=false;
					log.info("Validation Failed For Mechant Dealer Association.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}	
			}
			isValid=true;
			return new ValidationResponse(errorCode, errorMessage, isValid);
			
		case PARTNER_PLATFORM:
			if(headers.length!=2 || values.length!=2){
				errorCode="MHAV-1001";
				errorMessage="No. Of Column must  be equal to (TWO) . Please Check and try again.";
				isValid=false;
				log.info("Validation Failed For Partner Platform Association.");
				return new ValidationResponse(errorCode, errorMessage, isValid);
			}
			for(int i=0 ; i<values.length ; i++){
				if(values[i].length()==0){
					errorCode="MHAV-1002";
					errorMessage="Values can not be left blank in the File.Please Check";
					isValid=false;
					log.info("Validation Failed For Partner Platform Association.");
					return new ValidationResponse(errorCode, errorMessage, isValid);
				}	
			}
			isValid=true;
			return new ValidationResponse(errorCode, errorMessage, isValid);
		}
		errorCode="MHAV-1003";
		errorMessage="Validation not successful because Association Operation Type is not valid";
		return new ValidationResponse(errorCode, errorMessage, isValid);
	
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData, File file) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean hasPermissionForAction(String userId, BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub
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

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return true;
	}

}
