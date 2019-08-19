package com.snapdeal.payments.view.taskhandlers;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.escrowengine.EscrowEngineMetadata;
import com.snapdeal.payments.metadata.p2pengine.P2PEngineMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.enums.RetryTaskStatus;
import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.RequestViewAuditEntity;
import com.snapdeal.payments.view.request.commons.enums.P2PTxnState;
import com.snapdeal.payments.view.sqs.TSMNotification;
import com.snapdeal.payments.view.utils.ExceptionAuditHandler;
import com.snapdeal.payments.view.utils.ExceptionHandler;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

@Slf4j
@Service
public class TaskDecider {
	
	@Autowired
	private IPersistanceManager persistManager;
	@Autowired
	private ExceptionHandler exceptionHandler;
	@Autowired
	private TaskHandlerFactory baseTaskFactory;
	@Autowired
	private ExceptionAuditHandler<MerchantViewAuditEntity> paymentsViewAuditHandler;
	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;

	public void processForRequestView(final TSMNotification notifaction,
			final Source source)
			throws Exception {
		NotificationMessage notifactionMsg = notifaction.getNotificationMessage() ;
		TransactionType type = TransactionType.valueOf(notifactionMsg
				.getGlobalTxnType());
		switch (type) {
		case P2P_PAY_TO_MID:
		case P2P_REQUEST_MONEY:
		case P2P_SEND_MONEY:
			log.info("Processing request View transaction");
			baseTaskFactory.getBaseTask(P2PNotificationHandler.class).execute(
					notifactionMsg);
			baseTaskFactory.getBaseTask(RequestNotificationHandler.class).execute(
					notifactionMsg);
			
			log.info("Successfully Processed request view notification");
			break;
		case ESCROW_PAYMENT:
			baseTaskFactory.getBaseTask(EscrowActionHandler.class).execute(
					notifactionMsg);
			break ;
		default:
			break;
		}
		updateAuditForSuccess(notifaction, source,ViewTypeEnum.REQUESTVIEW);

	}

	public void processForMerchantView(final TSMNotification notifaction,
			final Source source) throws Exception {
		NotificationMessage notifactionMsg = notifaction
				.getNotificationMessage();
		String txnType = notifaction.getNotificationMessage()
				.getGlobalTxnType();
		String txnId=notifactionMsg.getGlobalTxnId();
		TransactionType type = TransactionType.forvalue(txnType);
		switch (type) {
		case CUSTOMER_PAYMENT:
		case CANCELLATION_REFUND:
			baseTaskFactory.getBaseTask(OnlinePaymentTaskHandler.class)
					.execute(notifactionMsg);
			break;
		case OPS_WALLET_DEBIT:
		case OPS_WALLET_DEBIT_REVERSE:
		case OPS_WALLET_DEBIT_VOID:
		case OPS_WALLET_DEBIT_VOID_REVERSE:
			baseTaskFactory.getBaseTask(OfflinePaymentTaskHandler.class)
					.execute(notifactionMsg);
			break;
		case OPS_WALLET_CREDIT:
		case OPS_WALLET_CREDIT_REVERSE:
		case OPS_WALLET_CREDIT_VOID:
			if(notifactionMsg.getTsmState() == TsmState.SUCCESS){
				baseTaskFactory.getBaseTask(OfflinePaymentTaskHandler.class)
				.execute(notifactionMsg);
			}
			break;
		case PAYABLES_LOAD:
		case PAYABLES_CHARGE:
		case PAYABLES_REFUND:
			baseTaskFactory.getBaseTask(PayableTaskHandler.class).execute(
					notifactionMsg);
			break;
		case PAYABLES_DISBURSEMENT:
		case WALLET_CORP_DISBURSEMENT:
			baseTaskFactory.getBaseTask(DisbursementTaskHandler.class).execute(
					notifactionMsg);
			break;
		case P2P_PAY_TO_MID:
		case P2P_SEND_MONEY:
		case P2P_REQUEST_MONEY:
			P2PEngineMetadata p2pMetaData = viewMetaDataInterprator
					.getP2PEngineMetaData(notifactionMsg.getGlobalTxnMetaData());
			if (p2pMetaData.isReceiverMerchant() &&
				notifactionMsg.getTsmState() == TsmState.SUCCESS && 
				P2PTxnState.valueOf(notifactionMsg.getGlobalTxnState()) 
				== P2PTxnState.SUCCESS
				) {
					baseTaskFactory.getBaseTask(P2MPaymentHandler.class)
							.execute(notifactionMsg);
			}else {
				return;
			}
			break;
		case ESCROW_PAYMENT:
			EscrowEngineMetadata escrowMetaData = viewMetaDataInterprator
			.getGlobalMetData(notifactionMsg.getGlobalTxnMetaData(),EscrowEngineMetadata.class);
			
			if(escrowMetaData.isDestinationMerchant() && !escrowMetaData.isP2PTxn())
				baseTaskFactory.getBaseTask(EscrowPaymentTaskHandler.class)
					.execute(notifactionMsg);
			break ;
			
		default:
			log.info("no handling of this transaction type in merchant view: " + type+ " fcTxnId: "+ txnId) ;
			break;
		}

		updateAuditForSuccess(notifaction,  source,
				ViewTypeEnum.MERCHANTVIEW);
	}

	private void updateAuditForSuccess(
			final TSMNotification notifaction, 
			final Source source, ViewTypeEnum viewType) {
		if (source == Source.AUDIT) {
			switch(viewType){
			case MERCHANTVIEW:
				MerchantViewAuditEntity request = new MerchantViewAuditEntity();
				request.setFcTxnId((notifaction.getNotificationMessage()
						.getGlobalTxnId()));
				request.setTxnType(notifaction.getNotificationMessage()
						.getGlobalTxnType());
				paymentsViewAuditHandler.updateExceptionAuditStatus(request,
						RetryTaskStatus.SUCCESS.getName());
				break;
			case REQUESTVIEW:
				RequestViewAuditEntity rvRequest = new RequestViewAuditEntity();
				rvRequest.setFcTxnId((notifaction.getNotificationMessage()
						.getGlobalTxnId()));
				rvRequest.setTxnType(notifaction.getNotificationMessage()
						.getGlobalTxnType());
				paymentsViewAuditHandler.updateExceptionAuditStatus(rvRequest,
						RetryTaskStatus.SUCCESS.getName());
				break;
			default:
				break;
			}
		}
	}
	
	
}
