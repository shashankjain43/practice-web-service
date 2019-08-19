/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.entity.NotificationMessageLinkTxnDetails;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.exception.service.PaymentsViewGenericException;
import com.snapdeal.payments.view.dao.IPersistanceManager;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.request.commons.enums.TxnStatus;
import com.snapdeal.payments.view.service.request.ExceptionHandlerRequest;
import com.snapdeal.payments.view.sqs.NotificationReaderService;
import com.snapdeal.payments.view.utils.PaymentsViewShardUtil;
import com.snapdeal.payments.view.utils.clients.PayableClientUtil;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;
import com.snapdeal.payments.view.utils.metadata.PaymentsViewBuilder;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

/**
 * @author shubham.bansal
 *
 */
public abstract class BaseTaskHandler implements
		TaskHandler<NotificationMessage, Void> {

	@Autowired
	protected ViewMetaDataInterprator viewMetaDataInterprator;
	@Autowired
	protected IPersistanceManager persistManager;
	@Autowired
	protected NotificationReaderService reader;
	@Autowired
	protected PaymentsViewBuilder paymentsViewBuilder;
	@Autowired
	protected PaymentsViewShardUtil paymentsViewShardUtil;
	@Autowired
	protected PayableClientUtil payableClient;
	@Autowired
	protected TSMClientUtil tsmClient;

	/**
	 * @param request
	 * @param ex
	 * @return
	 * 
	 *         Convert To AuditEntity for Audit Table
	 */
	public MerchantViewAuditEntity getMerchantViewEntity(
			ExceptionHandlerRequest request, PaymentsViewGenericException ex) {
		MerchantViewAuditEntity entity = new MerchantViewAuditEntity();
		entity.setMerchantId(request.getMerchantId());
		entity.setFcTxnId(request.getTxnId());
		entity.setTxnType(request.getTxnType());
		entity.setExceptionType(ex.getExceptionType().name());
		entity.setExceptionCode(ex.getErrCode());
		entity.setExceptionMsg(ex.getMessage());
		entity.setTsmTimeStamp(request.getTsmTimeStamp());
		return entity;
	}

	protected TransactionDetailsDTO getTransactionIfExist(String txnId,
			String txnType) {
		TransactionDetailsDTO dto = persistManager
				.getTransactionDetails(getTxnDetailsMap(txnId,txnType));
		return dto;
	}

	protected void handleStateChange(TransactionStateDetailsEntity stateEntity) {

		boolean validStateTxn = persistManager
				.verifyForTxnStatusValid(stateEntity);
		if (!validStateTxn) {
			persistManager.saveTransactionStateDetails(stateEntity);
		} else {
			persistManager.updateForAlreadyExistState(stateEntity);
		}
	}

	protected Map<String, String> getUplinkMap(
			NotificationMessage notificationMsg) {
		List<NotificationMessageLinkTxnDetails> uplink = notificationMsg
				.getUplinkGlobalTxnIds();
		if (uplink == null || uplink.size() == 0) {
			return null;
		} else {
			return getTxnDetailsMap(uplink.get(0).getTxnId(),uplink.get(0).getTxnType()) ;
		}
	}
	
	protected Map<String, String> getTxnDetailsMap(String txnId, String txnType) {
		Map<String, String> txnDetails = new HashMap<String, String>();
		txnDetails.put(PaymentsViewConstants.FC_TXN_ID, txnId);
		txnDetails.put(PaymentsViewConstants.FC_TXN_TYPE, txnType);
		return txnDetails;
	}

	
	protected String mapTsmStateToTxnStatus(NotificationMessage notifactionMsg) {
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

}
