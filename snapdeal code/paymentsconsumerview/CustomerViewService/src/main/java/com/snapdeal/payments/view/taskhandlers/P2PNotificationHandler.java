package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.UUID;

import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.p2pengine.P2PEngineMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.entity.PartyDetailsEntity;
import com.snapdeal.payments.view.entity.RequestViewEntity;
import com.snapdeal.payments.view.request.commons.dto.PartyDetailsDto;
import com.snapdeal.payments.view.request.commons.enums.P2PTxnState;
import com.snapdeal.payments.view.request.commons.enums.PartyType;
import com.snapdeal.payments.view.request.commons.enums.RequestPartyType;
import com.snapdeal.payments.view.request.commons.enums.TxnStatus;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;
import com.snapdeal.payments.view.utils.metadata.P2PTxnStateMap;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

/**
 * 
 * @author abhishek.garg
 *
 */

@Service
public class P2PNotificationHandler extends BaseTaskHandler {

	@Autowired
	private IPersistanceManager persistManager;
	@Autowired
	private TSMClientUtil tsmClient;

	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;
	@Autowired
	private P2PTxnStateMap p2pTxnStateMap;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapdeal.payments.paymentview.taskhandlers.BaseTaskHandler#execute
	 * (com.snapdeal.payments.tsm.entity.NotificationMessage)
	 */
	@Retryable(maxAttempts = 2 , backoff = @Backoff(delay = 5000))
	public void execute(NotificationMessage notifactionMsg) throws Exception {
		TransactionType type = TransactionType.valueOf(notifactionMsg
				.getGlobalTxnType());
		switch (type) {
		case P2P_PAY_TO_MID:
		case P2P_SEND_MONEY:
		case P2P_REQUEST_MONEY:
			processP2PSendMoneyNotification(notifactionMsg);
			break;

		/*
		 * processP2PRequestMoneyNotification(notifactionMsg); break ;
		 */
		default:
			break;
		}
	}

	private void processP2PSendMoneyNotification(
			NotificationMessage notifactionMsg) {
		Map<String, String> txnDetails = getTxnDetailsMap(
				notifactionMsg.getGlobalTxnId(),
				notifactionMsg.getGlobalTxnType());
		List<RequestViewEntity> requestViewEntityList = persistManager
				.getRequestViewTransactionDetails(txnDetails);
		TransactionType type = TransactionType.valueOf(notifactionMsg
				.getGlobalTxnType());

		TsmState tsmTxnState = notifactionMsg.getTsmState();
		if (!(requestViewEntityList == null || requestViewEntityList.isEmpty())) {
			// uppdate txn
			List<Map<String, Object>> updateRVTxnDetailsList = new LinkedList<Map<String, Object>>();
			for (RequestViewEntity entity : requestViewEntityList) {
				Map<String, Object> updateRequestViewDetails = getDetailsToBeUpdated(
						notifactionMsg, entity);
				updateRVTxnDetailsList.add(updateRequestViewDetails);
			}
			persistManager
					.updateTxnDetailsOfP2PTxnDetails(updateRVTxnDetailsList);
			P2PTxnState p2pTxnState = P2PTxnState.valueOf(notifactionMsg
					.getGlobalTxnState());
			if (type == TransactionType.P2P_SEND_MONEY
					&& (tsmTxnState == TsmState.SUCCESS || p2pTxnState == P2PTxnState.MONEY_PARKED)) {
				// insert new row. for b to a.
				PartyDetailsEntity srcPartDetails = getPartyDetails(
						notifactionMsg, RequestPartyType.SENDER);
				PartyDetailsEntity destPartDetails = getPartyDetails(
						notifactionMsg, RequestPartyType.RECIVER);
				RequestViewEntity rvEntityReciever = getRVEntity(
						notifactionMsg, destPartDetails, srcPartDetails,
						RequestPartyType.RECIVER);
				List<RequestViewEntity> requestViewEntityListForReciver = new LinkedList<RequestViewEntity>();
				requestViewEntityListForReciver.add(rvEntityReciever);
				persistManager
						.saveRequestViewEntity(requestViewEntityListForReciver);

			}
		} else {
			requestViewEntityList = new LinkedList<RequestViewEntity>();
			PartyDetailsEntity srcPartDetails = getPartyDetails(notifactionMsg,
					RequestPartyType.SENDER);
			PartyDetailsEntity destPartDetails = getPartyDetails(
					notifactionMsg, RequestPartyType.RECIVER);
			RequestViewEntity rvEntitySender = getRVEntity(notifactionMsg,
					srcPartDetails, destPartDetails, RequestPartyType.SENDER);

			requestViewEntityList.add(rvEntitySender);
			P2PTxnState p2pTxnState = P2PTxnState.valueOf(notifactionMsg
					.getGlobalTxnState());

			if (type == TransactionType.P2P_REQUEST_MONEY
					|| tsmTxnState == TsmState.SUCCESS
					|| p2pTxnState == P2PTxnState.MONEY_PARKED) {
				RequestViewEntity rvEntityReciever = getRVEntity(
						notifactionMsg, destPartDetails, srcPartDetails,
						RequestPartyType.RECIVER);
				requestViewEntityList.add(rvEntityReciever);
			}
			persistManager.saveRequestViewEntity(requestViewEntityList);
		}
	}
	private Map<String, Object> getDetailsToBeUpdated(
			NotificationMessage notifactionMsg, RequestViewEntity entity) {
		P2PEngineMetadata p2pEngineMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		RequestPartyType requestPartyType = null;
		Map<String, Object> updatedDetails = new HashMap<String, Object>();
		TsmState state = notifactionMsg.getTsmState();
		if (p2pEngineMetaData.getSenderIMSId().equals(
				entity.getSrcPartyDetails().getPartyId())) {
			requestPartyType = RequestPartyType.SENDER;
			if(StringUtils.isNotBlank(p2pEngineMetaData.getReceiverIMSIdAfterWalletCreation()) && state ==TsmState.SUCCESS){
				updatedDetails.put(PaymentsViewConstants.RECEIVER_ID, p2pEngineMetaData.getReceiverIMSIdAfterWalletCreation()) ;
			}else{
				updatedDetails.put(PaymentsViewConstants.RECEIVER_ID,null) ;
			}
		} else {
			requestPartyType = RequestPartyType.RECIVER;
			if(StringUtils.isNotBlank(p2pEngineMetaData.getReceiverIMSIdAfterWalletCreation()) && state ==TsmState.SUCCESS){
				updatedDetails.put(PaymentsViewConstants.SENDER_ID, p2pEngineMetaData.getReceiverIMSIdAfterWalletCreation()) ;
			}else{
				updatedDetails.put(PaymentsViewConstants.SENDER_ID,null) ;
			}
		}
		String newRVTxnState = getNewRVTxnState(
				notifactionMsg.getGlobalTxnType(),
				notifactionMsg.getTsmState(),
				notifactionMsg.getGlobalTxnState(), requestPartyType);;
	
	
		updatedDetails.put(PaymentsViewConstants.RV_TXN_STATE, newRVTxnState);
		updatedDetails.put(PaymentsViewConstants.TSM_TXN_STATE,
				getRequestTSMSState(notifactionMsg));
		updatedDetails.put(PaymentsViewConstants.P2P_TXN_STATE,
				notifactionMsg.getGlobalTxnState());
		updatedDetails.put(PaymentsViewConstants.TSM_COMMIT_TIME,
					new Date(notifactionMsg.getTsmTimestamp()));
		updatedDetails.put(PaymentsViewConstants.META_DATA,
				notifactionMsg.getGlobalTxnMetaData());
		updatedDetails.put(PaymentsViewConstants.TXN_ID, entity.getTxnId());
		
		return updatedDetails;
	}

	private String getNewRVTxnState(String txnType, TsmState tsmState,
			String p2pTxnState, RequestPartyType requestPartType) {
		TransactionType txnTypeEnum = TransactionType.valueOf(txnType);
		P2PTxnState p2pTxnStateEnum = P2PTxnState.valueOf(p2pTxnState);
		return p2pTxnStateMap.getViewTxnType(txnTypeEnum, tsmState,
				p2pTxnStateEnum, requestPartType);
	}

	private RequestViewEntity getRVEntity(NotificationMessage notifactionMsg,
			PartyDetailsEntity srcPartyDetails,
			PartyDetailsEntity destPartyDetails, RequestPartyType requestParty) {
		P2PEngineMetadata p2pMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		String rvTxnState = getNewRVTxnState(notifactionMsg.getGlobalTxnType(),
				notifactionMsg.getTsmState(),
				notifactionMsg.getGlobalTxnState(), requestParty);
		RequestViewEntity rvEntity = new RequestViewEntity();
		rvEntity.setTxnId(UUID.randomUUID().toString());
		rvEntity.setFcTxnId(notifactionMsg.getGlobalTxnId());
		rvEntity.setTxnType(notifactionMsg.getGlobalTxnType());
		rvEntity.setTsmTxnState(getRequestTSMSState(notifactionMsg));
		rvEntity.setP2pTxnState(notifactionMsg.getGlobalTxnState());
		rvEntity.setRvTxnState(rvTxnState);
		rvEntity.setSrcPartyDetails(srcPartyDetails);
		rvEntity.setDestPartyDetails(destPartyDetails);
		rvEntity.setTxnDate(new Date(notifactionMsg.getTsmTimestamp()));
		rvEntity.setTsmTimeStamp(new Date(notifactionMsg.getTsmTimestamp()));
		rvEntity.setTxnAmount(notifactionMsg.getGlobalTxnAmount());
		rvEntity.setMetaData(notifactionMsg.getGlobalTxnMetaData());
		rvEntity.setMerchantTag(p2pMetaData.getMerchantTag());
		return rvEntity;
	}

	private String getRequestTSMSState(NotificationMessage notifactionMsg) {
		TsmState state = notifactionMsg.getTsmState();
		switch (state) {
		case CREATED:
		case IN_PROGRESS:
		case DEEMED_SUCCESS:
			return TxnStatus.PENDING.name();
		case SUCCESS:
			return TxnStatus.SUCCESS.name();
		case FAILED:
			return TxnStatus.FAILED.name();
		case ROLLED_BACK:
			return TxnStatus.ROLL_BACK.name();
		default:
			return null;
		}
	}

	private PartyDetailsEntity getPartyDetails(
			NotificationMessage notifactionMsg,
			RequestPartyType requestPartyType) {
		// TODO based on party type fill details
		P2PEngineMetadata p2pMetaData = viewMetaDataInterprator
				.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
		Map<String, PartyDetailsDto> displayInfo = viewMetaDataInterprator.getDisplayInfo(p2pMetaData.getDisplayInfo(),0) ;
		
		PartyDetailsEntity partyDetails = new PartyDetailsEntity();
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
	private void savePartyDetailsDto(PartyDetailsEntity partyDetails,
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
		}
	}
}
