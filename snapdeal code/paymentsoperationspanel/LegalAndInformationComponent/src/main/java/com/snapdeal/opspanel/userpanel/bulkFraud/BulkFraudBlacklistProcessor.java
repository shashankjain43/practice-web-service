package com.snapdeal.opspanel.userpanel.bulkFraud;

import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.payments.pms.client.ProfileManagementClient;
import com.snapdeal.payments.pms.entity.EntityStatus;
import com.snapdeal.payments.pms.exceptions.InternalClientException;
import com.snapdeal.payments.pms.exceptions.ProfileManagementException;
import com.snapdeal.payments.pms.service.model.AddTagInfoToEntityRequest;
import com.snapdeal.payments.pms.service.model.AddTagInfoToEntityResponse;
import com.snapdeal.payments.pms.service.model.EntityMetaInfo;
import com.snapdeal.payments.pms.service.model.EntityTagInfo;
import com.snapdeal.payments.pms.service.model.GetEntityRequest;
import com.snapdeal.payments.pms.service.model.GetEntityResponse;
import com.snapdeal.payments.pms.service.model.TagStatus;
import com.snapdeal.payments.pms.service.model.UpdateEntityRequest;

@Component
@Slf4j
public class BulkFraudBlacklistProcessor implements  IRowProcessor{
	
	
	@Autowired
	ProfileManagementClient pms;
	
	/* (non-Javadoc)
	 * @see com.snapdeal.bulkprocess.registration.IRowProcessor#execute(java.lang.String[], java.lang.String[], java.util.Map, long, java.lang.Object, java.util.Map)
	 */
	@Override
	public Object execute(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		
		String entityId = rowValues[0];
		
		String userId = headerValues.get(BulkFraudBlacklistConstatnts.EMAIL_ID);
		
		BulkFraudResponse response = new BulkFraudResponse();
		
		String actionType = map.get(BulkFraudBlacklistConstatnts.ACTION_TYPE);
		if(actionType == null){
			log.info("ActionType Not Found!");
			response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
			response.setError(BulkFraudBlacklistConstatnts.ACTION_TYPE_NOT_FOUND);
			return response;
		}
		
		String entityType = map.get(BulkFraudBlacklistConstatnts.ENTITY_TYPE);
		if(entityType == null){
			log.info("EntityType Not Found!");
			response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
			response.setError(BulkFraudBlacklistConstatnts.ENTITY_TYPE_NOT_FOUND);
			return response;
		}
		
		String txnType = null;
		if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)) {
			txnType = map.get(BulkFraudBlacklistConstatnts.TXN_TYPE);
			if (txnType == null) {
				log.info("TxnType Not Found!");
				response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
				response.setError(BulkFraudBlacklistConstatnts.TXN_TYPE_NOT_FOUND);
				return response;
			}
		}
		String status = map.get(BulkFraudBlacklistConstatnts.STATUS);
		if(status == null){
			log.info("Status Not Found!");
			response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
			response.setError(BulkFraudBlacklistConstatnts.STATUS_NOT_FOUND);
			return response;
		}
		TagStatus enumTagStatus = null;
		EntityStatus enumEntityStatus = null;
		if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)) {
			
			boolean tagStatusMatch = false;
			for (TagStatus tagStatusEnum : TagStatus.values()) {
				if (status.equalsIgnoreCase(tagStatusEnum.name())) {
					tagStatusMatch = true;
					enumTagStatus = tagStatusEnum;
				}
			}
			if (tagStatusMatch == false) {
				log.info("TagStatus Enum Not Matched!");
				response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
				response.setError(BulkFraudBlacklistConstatnts.TAG_STATUS_INVALID);
				return response;
			}
		} else if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)) {
			
			boolean entityStatusMatch = false;
			for (EntityStatus entityStatusEnum : EntityStatus.values()) {
				if (status.equalsIgnoreCase(entityStatusEnum.name())) {
					entityStatusMatch = true;
					enumEntityStatus = entityStatusEnum;
				}
			}
			if (entityStatusMatch == false) {
				log.info("EnumStatus Enum Not Matched!");
				response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
				response.setError(BulkFraudBlacklistConstatnts.ENTITY_STATUS_INVALID);
				return response;
			}
		}
		String updateReason = map.get(BulkFraudBlacklistConstatnts.UPDATE_REASON);
		if(updateReason == null){
			log.info("Reason For Update Not Found!");
			response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
			response.setError(BulkFraudBlacklistConstatnts.REASON_NOT_FOUND);
			return response;
		}
		
		String jiraID = map.get(BulkFraudBlacklistConstatnts.JIRA_ID);
		if(jiraID == null){
			log.info("Jira ID For Update Not Found!");
			response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
			response.setError(BulkFraudBlacklistConstatnts.JIRA_ID_NOT_FOUND);
			return response;
		}
		
		String updateCode = map.get(BulkFraudBlacklistConstatnts.UPDATE_CODE);
		if(updateCode == null){
			log.info("Update Code Not Found!");
			response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
			response.setError(BulkFraudBlacklistConstatnts.UPDATE_CODE_NOT_FOUND);
			return response;
		}
		
		
		response.setEntityStatus(status);
		response.setEntityType(entityType);
		if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)) {
			response.setTxnType(txnType);
		} else if (actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)){
			response.setTxnType(BulkFraudBlacklistConstatnts.NOT_APPLICABLE);
		}
		response.setUpdateCode(updateCode);
		response.setJiraID(jiraID);
		response.setUpdatedBy(userId);
		response.setUpdateReason(updateReason);
		
		GetEntityRequest getEntityRequest = new GetEntityRequest();
		getEntityRequest.setEntityId(entityId);
		getEntityRequest.setEntityType(entityType);
		
		GetEntityResponse getEntityResponse = new GetEntityResponse();
		getEntityResponse = null;
		boolean entityFound = false;
		
		try {
			getEntityResponse = pms.getEntity(getEntityRequest);
			log.info("Found getEntityResponse = " + getEntityResponse.toString());
		} catch(InternalClientException ice){
			log.info("InternalClientException while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + ice.getMessage() + " : WILL BE RETRIED \n");
			try {
				getEntityResponse = pms.getEntity(getEntityRequest);
				log.info("Found on retry , getEntityResponse = " + getEntityResponse.toString());
			} catch (InternalClientException ice2) {
				log.info("InternalClientException on retry while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + ice.getMessage() + " : FAILURE");
			} catch (ProfileManagementException pme) {
				log.info("ProfileManagementException on retry while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + pme.getMessage() + " : FAILURE");
			}
		} catch (ProfileManagementException pme) {
			log.info("ProfileManagementException while getting entity! Entity = [" + entityType + " , " + entityId + "] " + " : " + pme.getMessage() + " : FAILURE");
		}
		
		Integer entityVersion = null;
		
		
		
		if(getEntityResponse != null && getEntityResponse.getEntityId() != null){
			log.info("EntityType found and its value is not NULL! \n");
			entityFound = true;
		}
		
		if(getEntityResponse != null && !getEntityResponse.getEntityType().equalsIgnoreCase(entityType)){
			log.info("entityType is not same as entityType of already existing entity! \n");
			entityFound = false;
		}
		
		if(entityFound == true){
			entityVersion = getEntityResponse.getEntityVersion();
		} else {
			entityVersion = Integer.valueOf(0);
		}
		
		boolean entityCreated = true;
		
		if (!entityFound || actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)) {
			entityCreated = false;
			UpdateEntityRequest updateEntityRequest = new UpdateEntityRequest();
			updateEntityRequest.setEntityId(entityId);
			updateEntityRequest.setEntityType(entityType);
			updateEntityRequest.setEntityVersion(entityVersion);
			
			/*HARD CODED VALUE CHECK  FOR ENTITY STATUS AS DISCUSSED*/
			
			if(actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)){
				updateEntityRequest.setEntityStatus(EntityStatus.CHECK);
			} else if(actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)){
				updateEntityRequest.setEntityStatus(enumEntityStatus);
			}
			
			
			EntityMetaInfo entityMetaInfo = new EntityMetaInfo();
			entityMetaInfo.setJiraId(jiraID);
			entityMetaInfo.setUpdateCode(updateCode);
			entityMetaInfo.setUpdatedBy(userId);
			
			updateEntityRequest.setEntityMetaInfo(entityMetaInfo);
			updateEntityRequest.setUpdateReason(updateReason);
			try {
				pms.updateEntity(updateEntityRequest);
				log.info("Created Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] ");
				entityCreated = true;
				entityVersion = Integer.valueOf(1);
				if(actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)){
					log.info("FULL_BLOCKING the item Entity = ["
							+ entityType
							+ " , "
							+ entityId
							+ "] and Done!");
					response.setError(null);
					response.setStatus(BulkFraudBlacklistConstatnts.SUCCESS);
					return response;
				}
			} catch (InternalClientException ice) {
				log.info("InternalClientException while updating Entity! Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] "
						+ " : "
						+ ice.getMessage() + " : WILL BE RETRIED \n");
				try {
					pms.updateEntity(updateEntityRequest);
					log.info("Created on retry , Entity = ["
							+ entityType
							+ " , "
							+ entityId
							+ "] ");
					entityCreated = true;
					entityVersion = Integer.valueOf(1);
					if(actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.FULL_BLOCKING)){
						log.info("FULL_BLOCKING the item Entity = ["
								+ entityType
								+ " , "
								+ entityId
								+ "] and Done!");
						response.setError(null);
						response.setStatus(BulkFraudBlacklistConstatnts.SUCCESS);
						return response;
					}
				} catch (InternalClientException ice2) {
					log.info("InternalClientException on retry while updating Entity! Entity = ["
							+ entityType
							+ " , "
							+ entityId
							+ "] "
							+ " : "
							+ ice.getMessage() + " : FAILURE");
					response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
					response.setError(ice.getMessage());
					return response;
				} catch (ProfileManagementException pme) {
					log.info("ProfileManagementException on retry while updating Entity! Entity = ["
							+ entityType
							+ " , "
							+ entityId
							+ "] "
							+ " : "
							+ pme.getMessage() + " : FAILURE");
					response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
					response.setError(pme.getMessage());
					return response;
				}
			} catch (ProfileManagementException pme) {
				log.info("ProfileManagementException while updating Entity! Entity = ["
						+ entityType
						+ " , "
						+ entityId
						+ "] "
						+ " : "
						+ pme.getMessage() + " : FAILURE");
				response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
				response.setError(pme.getMessage());
				return response;
			}
		}
		if((entityFound == true || entityCreated == true) && actionType.equalsIgnoreCase(BulkFraudBlacklistConstatnts.PARTIAL_BLOCKING)){
			AddTagInfoToEntityRequest addTagInfoToEntityRequest = new AddTagInfoToEntityRequest();
			addTagInfoToEntityRequest.setEntityId(entityId);
			addTagInfoToEntityRequest.setEntityType(entityType);
			addTagInfoToEntityRequest.setEntityVersion(entityVersion);
			
			EntityTagInfo entityTagInfo = new EntityTagInfo();
			entityTagInfo.setTagName(txnType);
			entityTagInfo.setTagContext(jiraID);
			
			boolean tagEntityStatusMatch = false;
			TagStatus tagEnumEntityStatus = null;
			for(TagStatus tagStatusEnum : TagStatus.values()){
				if(status.equalsIgnoreCase(tagStatusEnum.name())){
					tagEntityStatusMatch  = true;
					tagEnumEntityStatus =  tagStatusEnum;
				}
			}
			if(tagEntityStatusMatch == false){
				log.info("TagStatus Enum Not Matched!");
				response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
				response.setError(BulkFraudBlacklistConstatnts.TAG_STATUS_INVALID);
				return response;
			}
			
			entityTagInfo.setTagStatus(tagEnumEntityStatus);
			
			EntityMetaInfo entityMetaInfo = new EntityMetaInfo();
			entityMetaInfo.setJiraId(jiraID);
			entityMetaInfo.setUpdateCode(updateCode);
			entityMetaInfo.setUpdatedBy(userId);
			
			entityTagInfo.setEntityMetaInfo(entityMetaInfo);
			
			addTagInfoToEntityRequest.setEntityTagInfo(entityTagInfo);
			
			AddTagInfoToEntityResponse addTagInfoToEntityResponse = new AddTagInfoToEntityResponse();
			addTagInfoToEntityResponse = null;
			
			try {
				addTagInfoToEntityResponse = pms.addTagInfoToEntity(addTagInfoToEntityRequest);
				log.info("Added Tag = [" + txnType + " , " + tagEnumEntityStatus.name() + "] to Entity = [" + entityType + " , " + entityId + "] ");
				response.setStatus(BulkFraudBlacklistConstatnts.SUCCESS);
				response.setError(null);
				return response;
			} catch(InternalClientException ice){
				log.info("InternalClientException while addTagInfoToEntity! Entity = [" + entityType + " , " + entityId + "] " + " and Tag = [" + txnType + " , " + tagEnumEntityStatus.name() + "]  "  + " : " + ice.getMessage() + " : WILL BE RETRIED \n");
				try {
					addTagInfoToEntityResponse = pms.addTagInfoToEntity(addTagInfoToEntityRequest);
					log.info("Added on retry , Tag = [" + txnType + " , " + tagEnumEntityStatus.name() + "] to Entity = [" + entityType + " , " + entityId + "] ");
					response.setStatus(BulkFraudBlacklistConstatnts.SUCCESS);
					response.setError(null);
					return response;
				} catch (InternalClientException ice2) {
					log.info("InternalClientException on retry while addTagInfoToEntity! Entity = [" + entityType + " , " + entityId + "] " + " and Tag = [" + txnType + " , " + tagEnumEntityStatus.name() + "]  "  + " : " + ice.getMessage() + " : FAILURE");
					response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
					response.setError(ice2.getMessage());
					return response;
				} catch (ProfileManagementException pme) {
					log.info("ProfileManagementException on retry while addTagInfoToEntity! Entity = [" + entityType + " , " + entityId + "] " + " and Tag = [" + txnType + " , " + tagEnumEntityStatus.name() + "]  "  + " : " + pme.getMessage() + " : FAILURE");
					response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
					response.setError(pme.getMessage());
					return response;
				}
			} catch (ProfileManagementException pme) {
				log.info("ProfileManagementException while addTagInfoToEntity! Entity = [" + entityType + " , " + entityId + "] " + " and Tag = [" + txnType + " , " + tagEnumEntityStatus.name() + "]  "  + " : " + pme.getMessage() + " : FAILURE");
				response.setStatus(BulkFraudBlacklistConstatnts.FAILURE);
				response.setError(pme.getMessage());
				return response;
			}
		}
		
		return null;
	}

	@Override
	public Set<String> columnsToIgnore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onFinish(Map<String, String> map, Object sharedObject,
			BulkFileStatus status, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Object onStart(Map<String, String> paramMap1, Object paramObject,
			Map<String, String> paramMap2) {
		// TODO Auto-generated method stub
		return null;
	}

}
