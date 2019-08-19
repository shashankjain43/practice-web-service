package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.p2pengine.P2PEngineMetadata;
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
import com.snapdeal.payments.view.request.commons.dto.PartyDetailsDto;
import com.snapdeal.payments.view.request.commons.enums.P2PTxnState;
import com.snapdeal.payments.view.request.commons.enums.PartyType;
import com.snapdeal.payments.view.request.commons.enums.RequestPartyType;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;
import com.snapdeal.payments.view.utils.metadata.ActionContextMetaData;
import com.snapdeal.payments.view.utils.metadata.P2PTxnStateMap;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

/**
 * 
 * @author abhishek.garg
 *
 */

@Service
public class RequestNotificationHandler extends BaseTaskHandler {

	@Autowired
	private IPersistanceManager actionViewDao;
	
	@Autowired
	private TSMClientUtil tsmClient;

	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;
	
	@Autowired
	private P2PTxnStateMap p2pTxnStateMap;

	
	public void execute(NotificationMessage notifactionMsg) throws Exception {
		
		Map<String, String> txnDetails = getTxnDetailsMap(
											notifactionMsg.getGlobalTxnId(),
											notifactionMsg.getGlobalTxnType());
		
		List<ActionTxnDetailsDTO> actionTxnDtoList = actionViewDao
				.getActiontTxnDetails(txnDetails);
		TransactionType txnType = TransactionType.valueOf(notifactionMsg
				.getGlobalTxnType());
		TsmState tsmTxnState = notifactionMsg.getTsmState();
		
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
			P2PTxnState p2pTxnState = P2PTxnState.valueOf(notifactionMsg
					.getGlobalTxnState());
			if (txnType == TransactionType.P2P_SEND_MONEY
					&& (tsmTxnState == TsmState.SUCCESS || p2pTxnState == P2PTxnState.MONEY_PARKED)) {
				// insert new row. for b to a.
				ActionPartyDetailsEntity userPartDetails = getPartyDetails(
						notifactionMsg, RequestPartyType.SENDER);
				ActionPartyDetailsEntity otherPartDetails = getPartyDetails(
						notifactionMsg, RequestPartyType.RECIVER);
				ActionDetailsEntity actionViewEntityReciever = getRVEntity(
						notifactionMsg, otherPartDetails, userPartDetails,
						RequestPartyType.RECIVER);
				List<ActionDetailsEntity> ActionViewEntityListForReciver = new LinkedList<ActionDetailsEntity>();
				ActionViewEntityListForReciver.add(actionViewEntityReciever);
				actionViewDao
						.saveActionEntity(ActionViewEntityListForReciver);

			}
		} else {
			LinkedList<ActionDetailsEntity> actionViewEntityList = new LinkedList<ActionDetailsEntity>();
			ActionPartyDetailsEntity srcPartDetails = getPartyDetails(notifactionMsg,
					RequestPartyType.SENDER);
			ActionPartyDetailsEntity destPartDetails = getPartyDetails(
					notifactionMsg, RequestPartyType.RECIVER);
			ActionDetailsEntity actionViewEntitySender = getRVEntity(notifactionMsg,
					srcPartDetails, destPartDetails, RequestPartyType.SENDER);

			actionViewEntityList.add(actionViewEntitySender);
			P2PTxnState p2pTxnState = P2PTxnState.valueOf(notifactionMsg
					.getGlobalTxnState());

			if (txnType == TransactionType.P2P_REQUEST_MONEY
					|| tsmTxnState == TsmState.SUCCESS
					|| p2pTxnState == P2PTxnState.MONEY_PARKED) {
				ActionDetailsEntity actionViewEntityReciever = getRVEntity(
						notifactionMsg, destPartDetails, srcPartDetails,
						RequestPartyType.RECIVER);
				actionViewEntityList.add(actionViewEntityReciever);
			}
			actionViewDao.saveActionEntity(actionViewEntityList);
		}
	}

	@SuppressWarnings("unchecked")
	private Map<String, Object> getDetailsToBeUpdated(
			NotificationMessage notifactionMsg, ActionTxnDetailsDTO dto) {
		P2PEngineMetadata p2pEngineMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		RequestPartyType requestPartyType = null;
		Map<String, Object> updatedDetails = new HashMap<String, Object>();
		TsmState state = notifactionMsg.getTsmState();
		if (p2pEngineMetaData.getSenderIMSId().equals(
				dto.getUserId())) {
			requestPartyType = RequestPartyType.SENDER;
		} else {
			requestPartyType = RequestPartyType.RECIVER;
			if(StringUtils.isNotBlank(p2pEngineMetaData.getReceiverIMSIdAfterWalletCreation()) && state ==TsmState.SUCCESS){
				updatedDetails.put(PaymentsViewConstants.USER_ID, p2pEngineMetaData.getReceiverIMSIdAfterWalletCreation()) ;
			}else{
				updatedDetails.put(PaymentsViewConstants.USER_ID,null) ;
			}
		}
		String newActionTxnState = getNewActionTxnState(
				notifactionMsg.getGlobalTxnType(),
				notifactionMsg.getTsmState(),
				notifactionMsg.getGlobalTxnState(), requestPartyType);;
	
	
		updatedDetails.put(PaymentsViewConstants.ACTION_VIEW_STATE, newActionTxnState);
		updatedDetails.put(PaymentsViewConstants.ACTION_TXN_STATE,
				mapTsmStateToTxnStatus(notifactionMsg));
		updatedDetails.put(PaymentsViewConstants.ACTION_LAST_UPDATED_TIME_STAMP,
					new Date(notifactionMsg.getTsmTimestamp()));
		
		ActionContextMetaData<P2PEngineMetadata> actionContext  = 
				new ActionContextMetaData<P2PEngineMetadata>() ;
		actionContext = JsonUtils.deSerialize(dto.getActionContext(),actionContext.getClass());
		
		P2PEngineMetadata p2pMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		actionContext.setTxnMetaData(p2pMetaData);
		updatedDetails.put(PaymentsViewConstants.ACTION_CONEXT,
				JsonUtils.serialize(actionContext));
		updatedDetails.put(PaymentsViewConstants.ID, dto.getId());
		
		return updatedDetails;
	}

	private String getNewActionTxnState(String txnType, TsmState tsmState,
			String p2pTxnState, RequestPartyType requestPartType) {
		TransactionType txnTypeEnum = TransactionType.valueOf(txnType);
		P2PTxnState p2pTxnStateEnum = P2PTxnState.valueOf(p2pTxnState);
		return p2pTxnStateMap.getViewTxnType(txnTypeEnum, tsmState,
				p2pTxnStateEnum, requestPartType);
	}

	private ActionDetailsEntity getRVEntity(NotificationMessage notifactionMsg,
			ActionPartyDetailsEntity srcPartyDetails,
			ActionPartyDetailsEntity destPartyDetails, RequestPartyType requestParty) throws Exception {
		P2PEngineMetadata p2pMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		String rvTxnState = getNewActionTxnState(notifactionMsg.getGlobalTxnType(),
				notifactionMsg.getTsmState(),
				notifactionMsg.getGlobalTxnState(), requestParty);
		ActionDetailsEntity actionViewEntity = new ActionDetailsEntity();
		actionViewEntity.setId(UUID.randomUUID().toString());
		actionViewEntity.setActionId(notifactionMsg.getGlobalTxnId());
		actionViewEntity.setActionType(notifactionMsg.getGlobalTxnType());
		actionViewEntity.setActionState(mapTsmStateToTxnStatus(notifactionMsg));
		actionViewEntity.setActionViewState(rvTxnState);
		actionViewEntity.setUserId(srcPartyDetails.getPartyId());
		actionViewEntity.setUserType(srcPartyDetails.getPartyType());
		actionViewEntity.setOtherPartyId(destPartyDetails.getPartyId());
		setTxnRefAndType(actionViewEntity,notifactionMsg,p2pMetaData);
		actionViewEntity.setActionInitiationTimestamp(new Date(p2pMetaData.getTxnDate()));
		actionViewEntity.setActionLastIpdateTimestamp(new Date(notifactionMsg.getTsmTimestamp()));
		//actionViewEntity.setActionNextScheduleTimestamp(new Date(0));
		ActionContextMetaData<P2PEngineMetadata> actionContext =  new ActionContextMetaData<P2PEngineMetadata>();
		actionContext.setOtherPartyDTO(destPartyDetails);
		actionContext.setTxnMetaData(p2pMetaData);
		actionContext.setUserDisplayInfo(srcPartyDetails);
		actionContext.setTxnAmount(notifactionMsg.getGlobalTxnAmount());
		actionContext.setReferenceId(actionViewEntity.getReferenceId());
		actionContext.setReferenceType(actionViewEntity.getReferenceType());
		actionContext.setOtherPrtyId(actionViewEntity.getOtherPartyId());
		
		actionViewEntity.setActionContext(JsonUtils.serialize(actionContext));
	
		return actionViewEntity;
	}
	private void setTxnRefAndType(ActionDetailsEntity actionEntity,
								  NotificationMessage notifactionMsg,
								  P2PEngineMetadata p2pMetaData) throws Exception{
		  String refId = null,refType = null;
		    List<NotificationMessageLinkTxnDetails> uplink = notifactionMsg.getUplinkGlobalTxnIds();
		    List<ActionTxnDetailsDTO> actionDetailDTO =  null ;
		  if(StringUtils.isNotBlank(p2pMetaData.getOriginId())){
			  refId = p2pMetaData.getOriginId();
			  if( p2pMetaData.getOriginType()!=null){
				  refType = p2pMetaData.getOriginType().name() ;
			  }
		  }else if(uplink == null || uplink.size() <= 0) {
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
		P2PEngineMetadata p2pMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		Map<String, PartyDetailsDto> displayInfo = viewMetaDataInterprator.getDisplayInfo(p2pMetaData.getDisplayInfo(),0) ;
		
		ActionPartyDetailsEntity partyDetails = new ActionPartyDetailsEntity();
		switch (requestPartyType) {
		case RECIVER:
			partyDetails.setJabberId(p2pMetaData.getReceiverJabberId());
			partyDetails.setPartyId(p2pMetaData.getReceiverId());
			savePartyDetailsDto(partyDetails,
					displayInfo.get(PaymentsViewConstants.DEST_PATY_INFO),
					notifactionMsg,
					p2pMetaData,
					false) ;
			break;
		case SENDER:
			partyDetails.setJabberId(p2pMetaData.getSenderJabberId());
			partyDetails.setPartyId(p2pMetaData.getSenderIMSId());
			savePartyDetailsDto(partyDetails,
								displayInfo.get(PaymentsViewConstants.SRC_PATY_INFO),
								notifactionMsg,
								p2pMetaData,
								true) ;
			break;
		default:
			break;
		}
		return partyDetails;
	}
	private void savePartyDetailsDto(ActionPartyDetailsEntity partyDetails,
									PartyDetailsDto partyDto,
									NotificationMessage notifactionMsg,
									P2PEngineMetadata p2pMetaData,
									boolean isSrc)	{
		TransactionType type  = TransactionType.valueOf(notifactionMsg.getGlobalTxnType());
		boolean partyTypeBoolean = p2pMetaData.isReceiverMerchant();
		if (partyTypeBoolean && type == TransactionType.P2P_REQUEST_MONEY && isSrc) {
			partyDetails.setPartyType(PartyType.MERCHANT.name());
		}else if (partyTypeBoolean && type == TransactionType.P2P_SEND_MONEY && !isSrc) {
			partyDetails.setPartyType(PartyType.MERCHANT.name());
		}else {
			partyDetails.setPartyType(PartyType.USER.name());
		}	
		if(partyDto!=null){
			partyDetails.setMobileNumber(partyDto.getMobileNumber());
			partyDetails.setPartyTag(partyDto.getPartyTag());
			partyDetails.setPartyName(partyDto.getPartyName());
		}
	}
}
