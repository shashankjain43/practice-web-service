package com.snapdeal.payments.view.utils.metadata;

import java.math.BigDecimal;
import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.escrowengine.EscrowEngineMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.request.commons.enums.EscrowTxnState;
import com.snapdeal.payments.view.request.commons.enums.EscrowViewState;
import com.snapdeal.payments.view.request.commons.enums.RequestPartyType;



@Service
public class EscrowStateMap {

	public Map<String,String>  escrowSendTxnStateMap ;
	
	
	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;
	
	
	@PostConstruct
	public void mapEscrowTxnStateMap(){
		escrowSendTxnStateMap = new HashMap<String, String>();
		fillEscrowReciverTxnStateMap();
	}
	private void fillEscrowReciverTxnStateMap() {
		escrowSendTxnStateMap.put(
				TsmState.CREATED.name()+"."+EscrowTxnState.INITIATED.name(),
				EscrowViewState.PENDING.name());
		escrowSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+EscrowTxnState.ROLLBACKED.name(),
				EscrowViewState.FAILED.name());
		
		escrowSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+EscrowTxnState.INITIALIZED.name(),
				EscrowViewState.INITIALIZED.name());
		escrowSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+EscrowTxnState.FAILED.name(),
				EscrowViewState.PENDING.name());
		escrowSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+EscrowTxnState.PARTIALLY_COMPLETED.name(),
				EscrowViewState.PENDING.name());
		escrowSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+EscrowTxnState.BLOCKED.name(),
				EscrowViewState.PENDING.name());
		
		escrowSendTxnStateMap.put(
				TsmState.FAILED.name()+"."+EscrowTxnState.ROLLBACKED.name(),
				EscrowViewState.FAILED.name());
		escrowSendTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+EscrowTxnState.DECLINED.name(),
				EscrowViewState.DECLINED.name());
		escrowSendTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+EscrowTxnState.COMPLETED.name()+"1",
				EscrowViewState.MONEY_RECIEVED.name());
		escrowSendTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+EscrowTxnState.COMPLETED.name()+"2",
				EscrowViewState.MONEY_REFUNDED.name());
	}
	
	public String getViewTxnType(
			TransactionType txnType,
			TsmState tsmState,
			EscrowTxnState EscrowTxnState,
			RequestPartyType requestPartType,
			NotificationMessage notifactionMsg){
			
			return viewTxnTypeForEscrow(tsmState,EscrowTxnState,requestPartType,notifactionMsg) ;
				
	}
	private String viewTxnTypeForEscrow(
			TsmState tsmState,
			EscrowTxnState escrowTxnState,
			RequestPartyType requestPartType,
			NotificationMessage notifactionMsg){	
		EscrowViewState escrowViewState = null ;
		EscrowEngineMetadata escrowEngineMetaData = viewMetaDataInterprator
				.getGlobalMetData(notifactionMsg.getGlobalTxnMetaData(),EscrowEngineMetadata.class);
		if(tsmState == TsmState.SUCCESS && escrowTxnState == EscrowTxnState.COMPLETED){

 		   if(!(escrowEngineMetaData.getFinalizedAmount() == BigDecimal.ZERO) && 
 				   escrowEngineMetaData.getFinalizedAmount().equals(notifactionMsg.getGlobalTxnAmount())){
 			  escrowViewState  =  EscrowViewState.MONEY_RECIEVED ;
 		   }else{
 			  escrowViewState  =  EscrowViewState.MONEY_REFUNDED ;
 		   }
		}else {
			escrowViewState = EscrowViewState.valueOf(
					escrowSendTxnStateMap.get(tsmState.name() +
							"."  + escrowTxnState.name())) ;
		}
				return getEscrowViewState(requestPartType,escrowViewState) ;
	}
	
	private  String getEscrowViewState(RequestPartyType requestPartType,EscrowViewState EscrowViewState){
		if(EscrowViewState!=null){
			switch (requestPartType) {
			case SENDER:
				return EscrowViewState.getSourceStatus();
			case RECIVER:
				return EscrowViewState.getDestStatus();
			default:
				return null;
			}
		}
		else return null ;
	}
}
