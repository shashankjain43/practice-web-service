/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.payables.entity.Transaction;
import com.snapdeal.payments.payables.model.request.GetPayableTransactionsForTimeIntervalRequest;
import com.snapdeal.payments.payables.model.response.GetPayableTransactionsForTimeIntervalResponse;
import com.snapdeal.payments.payables.type.PayablesTransactionType;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.view.commons.enums.ExceptionType;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.commons.exception.codes.PaymentsViewServiceExceptionCodes;
import com.snapdeal.payments.view.commons.utils.TSMMetaDataParser;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.utils.ExceptionAuditHandler;
import com.snapdeal.payments.view.utils.clients.PayableClientUtil;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

/**
 * @author shubham.bansal
 *
 */
@Slf4j
@Service("PayableDisbursementTaskHandler")
public class PayableDisbursementTaskHandler implements
		TaskHandler<NotificationMessage, Void> {/*
												 * (non-Javadoc)
												 * 
												 * @see
												 * com.snapdeal.payments.view
												 * .taskhandlers
												 * .TaskHandler#execute
												 * (java.lang.Object)
												 */
	final static Integer PAGE_SIZE = 50;

	@Autowired
	private OnlinePaymentTaskHandler onlineTaskHandler;

	@Autowired
	private PayableClientUtil payableClient;

	@Autowired
	private TSMClientUtil tsmClient;

	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;

	@Autowired
	DisbursementTaskHandler disburseMentHandler;

	@Autowired
	ExceptionAuditHandler<MerchantViewAuditEntity> merchantAudit;

	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception {

		String metaDataString = notifactionMsg.getGlobalTxnMetaData();

		boolean unsucessfullDisbursement = false;

		DisbursementMetadata metaData = TSMMetaDataParser
				.getDisburseMetaData(metaDataString);
		String merchantId = notifactionMsg.getMerchantId();
		GetPayableTransactionsForTimeIntervalRequest request = new GetPayableTransactionsForTimeIntervalRequest();
		request.setEndTime(new Date(new Long(metaData.getSettlementEndTime())));
		if (metaData.getSettlementStartTime() != null)
			request.setStartTime(new Date(new Long(metaData
					.getSettlementStartTime())));
		request.setMerchantId(merchantId);
		request.setMerchantDomain(metaData.getDomain());
		request.setPageSize(PAGE_SIZE);
		GetPayableTransactionsForTimeIntervalResponse response = null;
		while (true) {

			if (response != null) {
				log.info("last Evaluated Key from payable " + 
			response.getLastEvaluatedKey() + " threadId: " + Thread.currentThread().getId());
				request.setLastEvaluatedKey(response.getLastEvaluatedKey());
			}

			response = payableClient.getTransactionList(request);

			if (response != null) {
				for (Transaction transaction : response.getTransactionList()) {
					
					if (PayablesTransactionType.PAYABLES_SETTLE ==
							transaction.getBusinessTransactionType()||
							PayablesTransactionType.PAYABLES_REVERT_SETTLE==
							transaction.getBusinessTransactionType())
						continue;
					log.info("settling transaction for id " + transaction.getIdempotencyId());
					String txnRef = transaction.getIdempotencyId();
					String freechargeTxnId = txnRef.split(":", 2)[0];
					String txnType = txnRef.split(":", 2)[1];

					TransactionDetailsDTO dto = disburseMentHandler
							.getTransactionIfExist(freechargeTxnId, txnType);

					try {
						if (dto == null) {

							NotificationMessage tsmNotificationMsg = tsmClient
									.getTransactionFromTSM(freechargeTxnId,
											txnType);
							onlineTaskHandler.execute(tsmNotificationMsg);

							dto = disburseMentHandler.getTransactionIfExist(
									freechargeTxnId, txnType);
						}

						disburseMentHandler.updateSettledTxnStatus(metaData,
								notifactionMsg, dto);
					} catch (PaymentsViewServiceException th) {
						unsucessfullDisbursement = true;
						handleException(freechargeTxnId, txnType, merchantId,
								th, notifactionMsg);
					}
				}
			}

			if (response == null || response.getTransactionList().size() < PAGE_SIZE ||
					response.getLastEvaluatedKey() == null) {
				break;
			}
		}
		if (unsucessfullDisbursement)
			throw new PaymentsViewServiceException(
					PaymentsViewServiceExceptionCodes.TSM_EXCEPTION.errCode(),
					PaymentsViewServiceExceptionCodes.TSM_EXCEPTION.errMsg(),
					ExceptionType.TSM_EXCEPTION);

	}

	void handleException(String txnId, String txnType, String merchantId,
			PaymentsViewServiceException ex, NotificationMessage notifactionMsg) {

		StringBuilder exceptionMessage = new StringBuilder(ex.getMessage());
		exceptionMessage.append("During Disburement.disbursementId :"
				+ notifactionMsg.getGlobalTxnId() + "Type :"
				+ notifactionMsg.getGlobalTxnType());
		PaymentsViewServiceException newEx = new PaymentsViewServiceException(
				ex.getErrCode(), exceptionMessage.toString(),
				ExceptionType.TSM_EXCEPTION);

		MerchantViewAuditEntity mAuditEntity = new MerchantViewAuditEntity();
		mAuditEntity.setFcTxnId(txnId);
		mAuditEntity.setTxnType(txnType);
		mAuditEntity.setTsmTimeStamp(new Date());
		mAuditEntity.setMerchantId(merchantId);
		merchantAudit.handleExceptionOfMerchantView(newEx, mAuditEntity);
	}

}
