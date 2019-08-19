package com.snapdeal.payments.view.utils.metadata;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

import org.springframework.stereotype.Service;

import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.request.commons.enums.P2PTxnState;
import com.snapdeal.payments.view.request.commons.enums.RVTxnState;
import com.snapdeal.payments.view.request.commons.enums.RequestPartyType;



@Service
public class P2PTxnStateMap {

	public Map<String,String>  p2pSendTxnStateMap ;
	
	public Map<String,String>  p2pRequestTxnStateMap ;
	
	
	@PostConstruct
	public void mapP2PTxnStateMap(){
		p2pSendTxnStateMap = new HashMap<String, String>();
		p2pRequestTxnStateMap = new HashMap<String,String>() ;
		fillP2PSendTxnStateMap() ;
		fillP2PRequestTxnnStateMap();
	}
	private void fillP2PRequestTxnnStateMap() {
		p2pRequestTxnStateMap.put(
				TsmState.CREATED.name()+"."+P2PTxnState.REQUESTED.name(),
				RVTxnState.MONEY_REQUESTED.name()) ;

		p2pRequestTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+P2PTxnState.PROCESSING.name(),
				RVTxnState.MONEY_REQUESTED.name()) ;

		p2pRequestTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+P2PTxnState.BLOCKED.name(),
				RVTxnState.MONEY_REQUESTED.name()) ;
		
		p2pRequestTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+P2PTxnState.APPROVED.name(),
				RVTxnState.MONEY_REQUESTED_APPROVED.name()) ;;
		p2pRequestTxnStateMap.put(
				TsmState.FAILED.name()+"."+P2PTxnState.FAILED.name(),
				RVTxnState.MONEY_REQUESTED_FAILED.name()) ;
		
		p2pRequestTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+P2PTxnState.SUCCESS.name(),
				RVTxnState.MONEY_RECIEVED.name()) ;

		p2pRequestTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+P2PTxnState.REJECTED.name(),
				RVTxnState.MONEY_REQUESTED_REJECTED.name()) ;

		p2pRequestTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+P2PTxnState.CANCELLED.name(),
				RVTxnState.MONEY_REQUESTED_CANCELLED.name()) ;
		p2pRequestTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+P2PTxnState.EXPIRED.name(),
				RVTxnState.MONEY_REQUESTED_EXPIRED.name()) ;
	}
	private void fillP2PSendTxnStateMap() {
		p2pSendTxnStateMap.put(
				TsmState.CREATED.name()+"."+P2PTxnState.INITIATED.name(),
				RVTxnState.SEND_MONEY_REQUEST.name());
		p2pSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+P2PTxnState.PROCESSING.name(),
				RVTxnState.SEND_MONEY_REQUEST.name());
		p2pSendTxnStateMap.put(
				TsmState.IN_PROGRESS.name()+"."+P2PTxnState.MONEY_PARKED.name(),
				RVTxnState.SEND_MONEY_REQUEST_MONEY_PARKED.name());
		p2pSendTxnStateMap.put(
				TsmState.FAILED.name()+"."+P2PTxnState.FAILED.name(),
				RVTxnState.SEND_MONEY_REQUEST_FAILED.name());
		p2pSendTxnStateMap.put(
				TsmState.FAILED.name()+"."+P2PTxnState.MONEY_REVERTED.name(),
				RVTxnState.SEND_MONEY_REQUEST_FAILED.name());
		p2pSendTxnStateMap.put(
				TsmState.SUCCESS.name()+"."+P2PTxnState.SUCCESS.name(),
				RVTxnState.MONEY_SENT.name());
	}
	public String getViewTxnType(
			TransactionType txnType,
			TsmState tsmState,
			P2PTxnState p2pTxnState,
			RequestPartyType requestPartType){
			switch(txnType){
			case P2P_PAY_TO_MID :
			case P2P_SEND_MONEY :
				return viewTxnTypeForP2PSendMoney(tsmState,p2pTxnState,requestPartType) ;
			case P2P_REQUEST_MONEY :
				return viewTxnTypeForP2PRequestMoney(tsmState,p2pTxnState,requestPartType) ;
				default:
					return null;
			}
	}
	private String viewTxnTypeForP2PRequestMoney(
			TsmState tsmState,
			P2PTxnState p2pTxnState,
			RequestPartyType requestPartType){	
				RVTxnState rvTxnState =  RVTxnState.valueOf(p2pRequestTxnStateMap.get(
											  	tsmState.name()+"."
											  +p2pTxnState.name())) ;
				return getRvTxnState(requestPartType,rvTxnState) ;
	}
	private String viewTxnTypeForP2PSendMoney(
			TsmState tsmState,
			P2PTxnState p2pTxnState,
			RequestPartyType requestPartType){
			RVTxnState rvTxnState = null ;
				rvTxnState = RVTxnState.valueOf(p2pSendTxnStateMap.get(
											  tsmState.name()+"."
											  +p2pTxnState.name())) ;
				return getRvTxnState(requestPartType,rvTxnState) ;
			
	}
	private  String getRvTxnState(RequestPartyType requestPartType,RVTxnState rvTxnState){
		if(rvTxnState!=null){
			switch (requestPartType) {
			case SENDER:
				return rvTxnState.getSourceStatus();
			case RECIVER:
				return rvTxnState.getDestStatus();
			default:
				return null;
			}
		}
		else return null ;
	}
}
