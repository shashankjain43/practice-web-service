package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.metadata.escrowengine.EscrowEngineMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.entity.NotificationMessageLinkTxnDetails;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.utils.JsonUtils;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.entity.ActionDetailsEntity;
import com.snapdeal.payments.view.entity.ActionPartyDetailsEntity;
import com.snapdeal.payments.view.request.commons.dto.ActionTxnDetailsDTO;
import com.snapdeal.payments.view.request.commons.enums.EscrowTxnState;
import com.snapdeal.payments.view.request.commons.enums.PartyType;
import com.snapdeal.payments.view.request.commons.enums.RequestPartyType;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;
import com.snapdeal.payments.view.utils.metadata.ActionContextMetaData;
import com.snapdeal.payments.view.utils.metadata.EscrowStateMap;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

@Component
public class EscrowActionHandler  extends BaseTaskHandler {

	@Autowired
	private IPersistanceManager actionViewDao;
	
	@Autowired
	private TSMClientUtil tsmClient;

	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;
	
	@Autowired
	private EscrowStateMap escrowTxnStateMap;

	
	public void execute(NotificationMessage notifactionMsg) throws Exception {
		
		Map<String, String> txnDetails = getTxnDetailsMap(
											notifactionMsg.getGlobalTxnId(),
											notifactionMsg.getGlobalTxnType());
		
		List<ActionTxnDetailsDTO> actionTxnDtoList = actionViewDao
				.getActiontTxnDetails(txnDetails);
		if (!(actionTxnDtoList == null || actionTxnDtoList.isEmpty())) {
			// uppdate txn
			List<Map<String, Object>> updateActionTxnDetailsList = new LinkedList<Map<String, Object>>();
			for (ActionTxnDetailsDTO actionDTO : actionTxnDtoList) {
				Map<String, Object> updateActionViewDetails = getDetailsToBeUpdated(
						notifactionMsg, actionDTO);
				updateActionTxnDetailsList.add(updateActionViewDetails);
			}
			actionViewDao
					.updateActionTxnDetails(updateActionTxnDetailsList);
			
		} else {
			LinkedList<ActionDetailsEntity> actionViewEntityList = new LinkedList<ActionDetailsEntity>();
			ActionPartyDetailsEntity srcPartDetails = getPartyDetails(notifactionMsg,
								RequestPartyType.SENDER);
			ActionPartyDetailsEntity destPartDetails = getPartyDetails(
					notifactionMsg, RequestPartyType.RECIVER);
			
			ActionDetailsEntity actionViewEntitySender = new ActionDetailsEntity();
			ActionDetailsEntity actionViewEntityReciver = new ActionDetailsEntity();
				actionViewEntityReciver = getAVEntity(notifactionMsg,
						destPartDetails,srcPartDetails,RequestPartyType.RECIVER);
				actionViewEntitySender = getAVEntity(notifactionMsg,
						srcPartDetails, destPartDetails,RequestPartyType.SENDER);
			actionViewEntityList.add(actionViewEntitySender);
			actionViewEntityList.add(actionViewEntityReciver);
			actionViewDao.saveActionEntity(actionViewEntityList);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getDetailsToBeUpdated(
			NotificationMessage notifactionMsg, ActionTxnDetailsDTO dto) {
		
		EscrowEngineMetadata escrowEngineMetaData = viewMetaDataInterprator
				.getGlobalMetData(notifactionMsg.getGlobalTxnMetaData(),EscrowEngineMetadata.class);
		
		RequestPartyType requestPartyType = null;
		Map<String, Object> updatedDetails = new HashMap<String, Object>();
		if (escrowEngineMetaData.getSourceImsId().equals(dto.getUserId())) {
			requestPartyType = RequestPartyType.SENDER;
		} else {
			requestPartyType = RequestPartyType.RECIVER;
		}
		String newActionTxnState = getEscrowActionTxnState(
										notifactionMsg.getGlobalTxnType(),
										notifactionMsg.getTsmState(),
										notifactionMsg.getGlobalTxnState(),
										requestPartyType,
										notifactionMsg);
		
		updatedDetails.put(PaymentsViewConstants.ACTION_VIEW_STATE,newActionTxnState);
		updatedDetails.put(PaymentsViewConstants.ACTION_TXN_STATE,
				mapTsmStateToTxnStatus(notifactionMsg));
		updatedDetails.put(PaymentsViewConstants.ACTION_LAST_UPDATED_TIME_STAMP,
					new Date(notifactionMsg.getTsmTimestamp()));	
		ActionContextMetaData<EscrowEngineMetadata> actionContext  = 
				new ActionContextMetaData<EscrowEngineMetadata>() ;
		actionContext = JsonUtils.deSerialize(dto.getActionContext(),actionContext.getClass());
		
		
		actionContext.setTxnMetaData(escrowEngineMetaData);
		updatedDetails.put(PaymentsViewConstants.ACTION_CONEXT,
				JsonUtils.serialize(actionContext));
		updatedDetails.put(PaymentsViewConstants.ID, dto.getId());		
		return updatedDetails;
	}
	private ActionDetailsEntity getAVEntity(NotificationMessage notifactionMsg,
			ActionPartyDetailsEntity srcPartyDetails,
			ActionPartyDetailsEntity destPartyDetails,
			RequestPartyType requestParty) throws Exception {
		EscrowEngineMetadata escrowEngineMetaData = viewMetaDataInterprator
				.getGlobalMetData(notifactionMsg.getGlobalTxnMetaData(),EscrowEngineMetadata.class);
		String rvTxnState = getEscrowActionTxnState(notifactionMsg.getGlobalTxnType(),
				notifactionMsg.getTsmState(),
				notifactionMsg.getGlobalTxnState(), 
				requestParty,notifactionMsg);
		ActionDetailsEntity actionViewEntity = new ActionDetailsEntity();
		actionViewEntity.setActionViewState(rvTxnState);
		actionViewEntity.setId(UUID.randomUUID().toString());
		actionViewEntity.setActionId(notifactionMsg.getGlobalTxnId());
		actionViewEntity.setActionType(notifactionMsg.getGlobalTxnType());
		actionViewEntity.setActionState(mapTsmStateToTxnStatus(notifactionMsg));
		actionViewEntity.setUserId(srcPartyDetails.getPartyId());
		actionViewEntity.setUserType(srcPartyDetails.getPartyType());
		actionViewEntity.setOtherPartyId(destPartyDetails.getPartyId());
		setTxnRefAndType(actionViewEntity,notifactionMsg);
		actionViewEntity.setActionInitiationTimestamp(escrowEngineMetaData.getTransactionTimestamp());
		actionViewEntity.setActionLastIpdateTimestamp(new Date(notifactionMsg.getTsmTimestamp()));
		//actionViewEntity.setActionNextScheduleTimestamp(new Date(0));
		ActionContextMetaData<EscrowEngineMetadata> actionContext =  new ActionContextMetaData<EscrowEngineMetadata>();
		actionContext.setOtherPartyDTO(destPartyDetails);
		actionContext.setTxnMetaData(escrowEngineMetaData);
		actionContext.setUserDisplayInfo(srcPartyDetails);
		actionContext.setTxnAmount(notifactionMsg.getGlobalTxnAmount());
		actionContext.setReferenceId(actionViewEntity.getReferenceId());
		actionContext.setReferenceType(actionViewEntity.getReferenceType());
		actionContext.setOtherPrtyId(actionViewEntity.getOtherPartyId());
		
		actionViewEntity.setActionContext(JsonUtils.serialize(actionContext));
	
		return actionViewEntity;
	}
	private void setTxnRefAndType(ActionDetailsEntity actionEntity,
								  NotificationMessage notifactionMsg) throws Exception{
		  String refId = null,refType = null;
		    List<NotificationMessageLinkTxnDetails> uplink = notifactionMsg.getUplinkGlobalTxnIds();
		    List<ActionTxnDetailsDTO> actionDetailDTO =  null ;
		  if(uplink == null || uplink.size() <= 0) {
			  refId = notifactionMsg.getGlobalTxnId();
			  refType = notifactionMsg.getGlobalTxnType();
	      } else {
	    	  refId = uplink.get(0).getTxnId();
	    	  refType = uplink.get(0).getTxnType();
	    	  actionDetailDTO = actionViewDao
	    					.getActiontTxnDetails(getTxnDetailsMap(refId,refType));
	    	  if (actionDetailDTO == null) {
	            NotificationMessage tsmNotificationMsg = tsmClient.getTransactionFromTSM(refId,
	            		refType);
	            execute(tsmNotificationMsg);
	    	  }
	    	  actionDetailDTO = actionViewDao
 					.getActiontTxnDetails(getTxnDetailsMap(refId,refType));
	      }
	      if (actionDetailDTO != null) {
	    	  refId = actionDetailDTO.get(0).getReferenceId();
	    	  refType = actionDetailDTO.get(0).getReferenceType();
	      }
	      actionEntity.setReferenceId(refId);
	      actionEntity.setReferenceType(refType);
	}

	private ActionPartyDetailsEntity getPartyDetails(
			NotificationMessage notifactionMsg,
			RequestPartyType requestPartyType) {
		// TODO based on party type fill details
		EscrowEngineMetadata escrowEngineMetaData = viewMetaDataInterprator
				.getGlobalMetData(notifactionMsg.getGlobalTxnMetaData(),EscrowEngineMetadata.class);
		ActionPartyDetailsEntity partyDetails = new ActionPartyDetailsEntity();
		switch (requestPartyType) {
		case SENDER:
			partyDetails.setPartyId(escrowEngineMetaData.getSourceImsId());
			partyDetails.setPartyType(PartyType.USER.name());
			partyDetails.setEmailId(escrowEngineMetaData.getEmail());
			partyDetails.setMobileNumber(escrowEngineMetaData.getMobileNumber());
			break;
		case RECIVER:
			partyDetails.setPartyId(escrowEngineMetaData.getDestinationImsId());
			if(escrowEngineMetaData.isDestinationMerchant() && !escrowEngineMetaData.isP2PTxn()){
				partyDetails.setPartyType(PartyType.MERCHANT.name());
			}else{
				partyDetails.setPartyType(PartyType.USER.name());
			}
			break;
		default:
			break;
		}
		return partyDetails;
	}
	
	private String getEscrowActionTxnState(String txnType, TsmState tsmState,
			String escrowTxnState, RequestPartyType requestPartType,NotificationMessage notifactionMsg) {
		TransactionType txnTypeEnum = TransactionType.valueOf(txnType);
		EscrowTxnState escrowTxnStateEnum = EscrowTxnState.valueOf(escrowTxnState);
		return escrowTxnStateMap.getViewTxnType(txnTypeEnum, tsmState,
				escrowTxnStateEnum, requestPartType,notifactionMsg);
	}
}

