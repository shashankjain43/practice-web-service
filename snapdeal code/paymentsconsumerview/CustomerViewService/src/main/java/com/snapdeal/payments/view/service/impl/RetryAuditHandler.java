package com.snapdeal.payments.view.service.impl;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.entity.NotificationMessageLinkTxnDetails;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.enums.RetryTaskStatus;
import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.dao.IPaymentsViewAuditDao;
import com.snapdeal.payments.view.dao.IRequestViewAuditDao;
import com.snapdeal.payments.view.datasource.DataBaseShardRelationMap;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.PaymentsViewAuditEntity;
import com.snapdeal.payments.view.merchant.commons.dto.RetryTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.request.RetryPaymentsViewAuditRequest;
import com.snapdeal.payments.view.merchant.commons.request.RetryTransactionListRequest;
import com.snapdeal.payments.view.service.ITSMQueueHandler;
import com.snapdeal.payments.view.service.request.ExceptionHandlerRequest;
import com.snapdeal.payments.view.sqs.TSMNotification;
import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;
import com.snapdeal.payments.view.utils.PaymentsViewThreadPoolExecutor;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;

@Slf4j
@Component
@EnableAsync
public class RetryAuditHandler {
	
	@Qualifier("PaymentsViewQueueHandler")
	@Autowired
	private ITSMQueueHandler paymentsViewTaskHandler;

	@Autowired
	private IPaymentsViewAuditDao<MerchantViewAuditEntity> persistanceMannager;

	@Autowired
	private TSMClientUtil tsmClient;

	@Autowired
	private DataBaseShardRelationMap databaseMap;
	
	@Autowired
	private IRequestViewAuditDao requestViewAuditDao ;
	
	@Autowired
	private PaymentsViewThreadPoolExecutor threadPoolExecutor;
	
	

	@Async
	public void retryPaymentViewAudit(RetryPaymentsViewAuditRequest request) {
		Map<String, List<String>> databaseShardMap = databaseMap
				.getDataBaseShardRelationMap();
		int totalCount = 0 ;
		List<String> databaseList = 
				databaseShardMap.get(request.getViewType().getMerchantName());
		for (String shardKey : databaseList) {
			PaymentsViewShardContextHolder.setShardKey(shardKey);
			int chunkSize = 1000;
			request.setLimit(chunkSize);
			List<PaymentsViewAuditEntity> entityAuditList = new LinkedList<PaymentsViewAuditEntity>();
			do {
				
				switch(request.getViewType()){
				case MERCHANTVIEW :
					entityAuditList =  persistanceMannager
								.getPaymentsViewAuditDetailsInChunks(request);
					break ;
				case REQUESTVIEW :
					 entityAuditList = requestViewAuditDao.
					 		getPaymentsViewAuditDetailsInChunks(request);
					 break ;
				default:
					break;
				}
				if (entityAuditList == null || entityAuditList.isEmpty())
					break;
				log.info("entity audit List is : " + entityAuditList.size());
				totalCount += entityAuditList.size() ;
				for (PaymentsViewAuditEntity entity : entityAuditList) {
					try{
					NotificationMessage notificationMsg = tsmClient
							.getTransactionFromTSM(entity.getFcTxnId(),
									entity.getTxnType());
					
					TSMNotification notification = new TSMNotification();
					notification.setNotificationMessage(notificationMsg);
					createAndSubmitTSMNotifcationTask(notification,request.getViewType());
					
					}catch(Throwable th){	
						PaymentsViewServiceException ex = new  PaymentsViewServiceException(
				        		 PaymentsViewServiceExceptionCodes.TSM_EXCEPTION.errCode(),
				        		 th.getMessage(),
				        		 ExceptionType.TSM_EXCEPTION);
						ExceptionHandlerRequest exceptionRequest = new ExceptionHandlerRequest() ;
						exceptionRequest.setTxnId(entity.getFcTxnId());
						exceptionRequest.setTxnType(entity.getTxnType());
						exceptionRequest.setTsmTimeStamp(entity.getTsmTimeStamp());
						//exceptionHandler.handleExceptionOfMerchantView(ex, exceptionRequest);
					}
				}
			/*	 try {
				//	Thread.sleep(1000);
				} catch (InterruptedException e) {
					// TODO Auto-generated catch block
					log.error("INTERUPPTED EXCEPTION :", e);
				}*/
			} while (entityAuditList.size() == chunkSize);
			PaymentsViewShardContextHolder.clearShardKey();
		}
		log.info("number of processeed :  "+ totalCount ) ;
	}
	@Async
	public void retryTransactionList(RetryTransactionListRequest request){
		List<RetryTransactionDTO>  retryTxnDTOLiost = request.getTransactionList() ;
		for (RetryTransactionDTO entity : retryTxnDTOLiost) {
			try{
			NotificationMessage notificationMsg = tsmClient
					.getTransactionFromTSM(entity.getTxnId(),
							entity.getTxnType());
			
			TSMNotification notification = new TSMNotification();
			notification.setNotificationMessage(notificationMsg);
			createAndSubmitTSMNotifcationTask(notification);
			wait(10);
			}catch(Throwable th){	
				//exceptionHandler.handleExceptionOfMerchantView(ex, exceptionRequest);
			}
		}
	}
	
	private boolean getMerchantViewTask(TSMNotification notification) {
		NotificationMessage notificationMsg = notification
				.getNotificationMessage();
		List<NotificationMessageLinkTxnDetails> uplinks = notificationMsg
				.getUplinkGlobalTxnIds();

		if (uplinks != null && uplinks.size() > 0) {
			String txnType = uplinks.get(0).getTxnType();
			if (txnType.equals("DEBUGGER_DASHBOARD_PG_LOAD_NODAL_ACCOUNT")
					|| txnType.equals("PAYABLES_DISBURSEMENT")
					|| txnType.equals("WALLET_CORP_DISBURSEMENT")) {

				log.info("Not Processing ppaybele notification  : "
						+ notification.getNotificationMessage());
			
				return false;
			}
		}
		if ((notificationMsg.getGlobalTxnType().equals("PAYABLES_DISBURSEMENT") || notificationMsg
				.getGlobalTxnType().equals("WALLET_CORP_DISBURSEMENT"))
				&& notificationMsg.getTsmState() != TsmState.SUCCESS) {
			// log.info("DE notifaction is not in success : " +
			// notificationMsg.getGlobalTxnId()
			// + " and its metaData is : " +
			// notificationMsg.getGlobalTxnMetaData());
			return false;
		}
		return true;
	}

	private void createAndSubmitTSMNotifcationTask(
			final TSMNotification notification) {
		boolean flag = getMerchantViewTask(notification);	
		//TODO comment below code
		//String txnType = notification.getNotificationMessage().getGlobalTxnType();
		///TransactionType  type  = TransactionType.valueOf(txnType) ;
		//if(type == TransactionType.P2P_SEND_MONEY || type == TransactionType.P2P_REQUEST_MONEY || type == TransactionType.P2P_PAY_TO_MID)
		if (flag) {
			
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					paymentsViewTaskHandler.processTask(notification, "TSET",
							Source.AUDIT);
				}
			});
		}
	}
	
	private boolean getMerchantViewTask(TSMNotification notification,final ViewTypeEnum viewType) {
		NotificationMessage notificationMsg = notification
				.getNotificationMessage();
		List<NotificationMessageLinkTxnDetails> uplinks = notificationMsg
				.getUplinkGlobalTxnIds();

		if (uplinks != null && uplinks.size() > 0) {
			String txnType = uplinks.get(0).getTxnType();
			if (txnType.equals("DEBUGGER_DASHBOARD_PG_LOAD_NODAL_ACCOUNT")
					|| txnType.equals("PAYABLES_DISBURSEMENT")
					|| txnType.equals("WALLET_CORP_DISBURSEMENT")) {
				
				changeStatus(notification.getNotificationMessage().getGlobalTxnId(),
						notification.getNotificationMessage().getGlobalTxnType(),
						RetryTaskStatus.SUCCESS,viewType) ;
				log.info("Not Processing ppaybele notification  : "
						+ notification.getNotificationMessage());
				return false;
			}
		}
		if ((notificationMsg.getGlobalTxnType().equals("PAYABLES_DISBURSEMENT") || notificationMsg
				.getGlobalTxnType().equals("WALLET_CORP_DISBURSEMENT"))
				&& notificationMsg.getTsmState() != TsmState.SUCCESS) {

			changeStatus(notification.getNotificationMessage().getGlobalTxnId(),
					notification.getNotificationMessage().getGlobalTxnType(),
					RetryTaskStatus.SUCCESS,viewType) ;
			return false;
		}
		return true;
	}

	private void createAndSubmitTSMNotifcationTask(
			final TSMNotification notification,final ViewTypeEnum viewType) {
		boolean flag = getMerchantViewTask(notification,viewType);
		if (flag) {
			changeStatus(notification.getNotificationMessage().getGlobalTxnId(),
						notification.getNotificationMessage().getGlobalTxnType(),
						RetryTaskStatus.SUBMITTED,
						viewType) ;
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					paymentsViewTaskHandler.processTask(notification, "TEST",
							Source.AUDIT);
				}
			});
		}
	}
	private void changeStatus(String txnId,String txnType,RetryTaskStatus status,final ViewTypeEnum viewType){
		Map<String,Object> txnDetails = new HashMap<String, Object>() ;
		txnDetails.put(PaymentsViewConstants.FC_TXN_ID,
					txnId);
		txnDetails.put(PaymentsViewConstants.FC_TXN_TYPE,
					txnType);
		txnDetails.put(PaymentsViewConstants.STATUS,
				status);
		
		switch(viewType){
	case MERCHANTVIEW :
		persistanceMannager.updatePaymentsViewAuditStatus(txnDetails) ;
		break ;
	case REQUESTVIEW :
		  requestViewAuditDao.
				 updatePaymentsViewAuditStatus(txnDetails);
		 break ;
		}
		
		
	}
	
}
