package com.snapdeal.opspanel.userpanel.bulkFraud;

import java.io.File;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.payments.pms.entity.EntityStatus;
import com.snapdeal.payments.pms.service.model.TagStatus;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUserByTokenRequest;
import com.snapdeal.payments.roleManagementModel.response.GetUserByTokenResponse;

@Component
@Slf4j
public class BulkFraudBlacklistValidator implements IBulkValidator{
	
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
		
		String actionType = fileMetaData.get(BulkFraudBlacklistConstatnts.ACTION_TYPE);
		if(actionType == null){
			log.info("ActionType Not Found!");
			return new ValidationResponse("BF-001", BulkFraudBlacklistConstatnts.ACTION_TYPE_NOT_FOUND, false);
		}
		
		String entityType = fileMetaData.get(BulkFraudBlacklistConstatnts.ENTITY_TYPE);
		if(entityType == null){
			log.info("EntityType Not Found!");
			return new ValidationResponse("BF-001", BulkFraudBlacklistConstatnts.ENTITY_TYPE_NOT_FOUND, false);
		}
		
		if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)) {
			String txnType = fileMetaData
					.get(BulkFraudBlacklistConstatnts.TXN_TYPE);
			if (txnType == null) {
				log.info("TxnType Not Found!");
				return new ValidationResponse("BF-002",
						BulkFraudBlacklistConstatnts.TXN_TYPE_NOT_FOUND, false);
			}
		}
		String status = fileMetaData.get(BulkFraudBlacklistConstatnts.STATUS);
		if(status == null){
			log.info("Status Not Found!");
			return new ValidationResponse("BF-003", BulkFraudBlacklistConstatnts.STATUS_NOT_FOUND, false);
		}
		if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)) {
			boolean tagStatusMatch = false;
			for (TagStatus tagStatusEnum : TagStatus.values()) {
				if (status.equalsIgnoreCase(tagStatusEnum.name())) {
					tagStatusMatch = true;
				}
			}
			if (tagStatusMatch == false) {
				log.info("TagStatus Enum Not Matched!");
				return new ValidationResponse("BF-004",
						BulkFraudBlacklistConstatnts.TAG_STATUS_INVALID, false);
			}
		} else if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)) {
			boolean entityStatusMatch = false;
			for (EntityStatus entityStatusEnum : EntityStatus.values()) {
				if (status.equalsIgnoreCase(entityStatusEnum.name())) {
					entityStatusMatch = true;
				}
			}
			if (entityStatusMatch == false) {
				log.info("EntityStatus Enum Not Matched!");
				return new ValidationResponse("BF-009",
						BulkFraudBlacklistConstatnts.ENTITY_STATUS_INVALID, false);
			}
		}
		String updateReason = fileMetaData.get(BulkFraudBlacklistConstatnts.UPDATE_REASON);
		if(updateReason == null){
			log.info("Reason For Update Not Found!");
			return new ValidationResponse("BF-005", BulkFraudBlacklistConstatnts.REASON_NOT_FOUND, false);
		}
		
		String jiraID = fileMetaData.get(BulkFraudBlacklistConstatnts.JIRA_ID);
		if(jiraID == null){
			log.info("Jira ID For Update Not Found!");
			return new ValidationResponse("BF-006", BulkFraudBlacklistConstatnts.JIRA_ID_NOT_FOUND, false);
		}
		
		String updateCode = fileMetaData.get(BulkFraudBlacklistConstatnts.UPDATE_CODE);
		if(updateCode == null){
			log.info("Update Code Not Found!");
			return new ValidationResponse("BF-007", BulkFraudBlacklistConstatnts.UPDATE_CODE_NOT_FOUND, false);
		}
		
		return new ValidationResponse(null, null, true);
	}

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		
		String tokenFromHeaders = headerValues.get(BulkFraudBlacklistConstatnts.TOKEN);
		String emailFromHeaders = headerValues.get(BulkFraudBlacklistConstatnts.EMAIL_ID);
		
		GetUserByTokenRequest getUserByTokenRequest = new GetUserByTokenRequest();
		getUserByTokenRequest.setRequestToken(tokenFromHeaders);
	
		GetUserByTokenResponse getUserByTokenResponse = new GetUserByTokenResponse();
		
		
		try {
			getUserByTokenResponse = rmsClient.getUserByToken(getUserByTokenRequest);
		} catch (Exception e) {
			log.info(e.getClass().getName() + "from RMS : " + e.getMessage() + "while getUserByToken \n");
			return false;
		}
		
		if(getUserByTokenResponse != null && getUserByTokenResponse.getUser() != null){
			String emailFromRMS = getUserByTokenResponse.getUser().getEmail();
			if(!emailFromRMS.equals(emailFromHeaders)){
				log.info("emailFromHeaders is NOT equal to email recieved from RMS ... \n");
				log.info("emailFromHeaders = " + emailFromHeaders + "and emailFromRMS = " + emailFromRMS + "\n");
				return false;
			} 
		} else {
			log.info("getUserByTokenResponse or getUserByTokenResponse.getUser() is NULL ... \n\n");
			return false;
		}
		
		String permission = BulkFraudBlacklistConstatnts.getPermissionForAction(action.toString());

		AuthrizeUserRequest request = new AuthrizeUserRequest();
		request.setRequestToken(tokenFromHeaders);
		request.setPreAuthrizeString(permission);
		try {
			rmsClient.authorizeUser(request);
		} catch (Exception e) {
			log.info(e.getClass().getName() + "from RMS : " + e.getMessage() + "while authorizeUser \n");
			return false;
		}
		
		return true;
	}

}
