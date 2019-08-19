/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.sdmoney.service.model.GetMerchantSettlementAccountTransactionsActivityRequest;
import com.snapdeal.payments.sdmoney.service.model.GetMerchantSettlementAccountTransactionsActivityResponse;
import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
import com.snapdeal.payments.sdmoney.service.model.type.BusinessTransactionType;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.commons.utils.TSMMetaDataParser;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.service.ITSMQueueHandler;
import com.snapdeal.payments.view.sqs.TSMNotification;
import com.snapdeal.payments.view.utils.clients.SDmoneyClientUtil;
import com.snapdeal.payments.view.utils.clients.TSMClientUtil;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

/**
 * @author shubham.bansal
 *
 */
@Slf4j
@Service("WalletDisbursementTaskHandler")
public class WalletDisbursementTaskHandler implements
		TaskHandler<NotificationMessage, Void> {
	@Autowired
	private OnlinePaymentTaskHandler onlineTaskHandler;
	
	@Qualifier("PaymentsViewQueueHandler")
	@Autowired
	private ITSMQueueHandler queueHandler	;
	
	@Autowired
	private RequestNotificationHandler actionNotificationHandler;

	@Autowired
	private TSMClientUtil tsmClient;

	@Autowired
	private ViewMetaDataInterprator viewMetaDataInterprator;

	@Autowired
	DisbursementTaskHandler disburseMentHandler;

	@Autowired
	private SDmoneyClientUtil sdMoneyClient;

	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception {

		String meteData = notifactionMsg.getGlobalTxnMetaData();

		DisbursementMetadata metaData = TSMMetaDataParser
				.getDisburseMetaData(meteData);

		GetMerchantSettlementAccountTransactionsActivityRequest request = new GetMerchantSettlementAccountTransactionsActivityRequest();
		request.setEndTime(new Date(new Long(metaData.getSettlementEndTime())));
		if (metaData.getSettlementStartTime() != null)
			request.setStartTime(new Date(new Long(metaData
					.getSettlementStartTime())));
		request.setMerchantId(notifactionMsg.getMerchantId());
		request.setPageSize(50);

		GetMerchantSettlementAccountTransactionsActivityResponse response = null;
		while (true) {

			if (response != null) {
				request.setLastEvaluatedKey(response.getLastEvaluatedKey());
			}

			response = sdMoneyClient.getTransactionFromWallet(request);

			if (response != null) {
				for (TransactionSummary transaction : response
						.getTransactions()) {

					if (!(BusinessTransactionType.P2M_TRANSFER == transaction.getBusinessTransactionType() || 
						  BusinessTransactionType.PAYMENT == transaction.getBusinessTransactionType()  ||
						  BusinessTransactionType.BLOCKEDIN_TRANSFER_FINALIZE == 
						  			transaction.getBusinessTransactionType()))
						continue;
					String freechargeTxnId = transaction.getIdempotencyId();
					String txnType = TransactionType.P2P_SEND_MONEY.name();
					log.info("settling transaction for id " + freechargeTxnId);
					if(transaction.getBusinessTransactionType() ==  BusinessTransactionType.BLOCKEDIN_TRANSFER_FINALIZE){
						freechargeTxnId = transaction.getParentIdempotencyId();
						txnType = TransactionType.ESCROW_PAYMENT.name();
					}
					TransactionDetailsDTO dto = disburseMentHandler
							.getTransactionIfExist(freechargeTxnId, txnType);
					NotificationMessage tsmNotificationMsg = null;
					if (dto == null) {
						try {
							tsmNotificationMsg = tsmClient
									.getTransactionFromTSM(freechargeTxnId,
											txnType);
						} catch (Throwable e) {
							if(!txnType.equals(TransactionType.ESCROW_PAYMENT.name())){
								txnType = TransactionType.P2P_REQUEST_MONEY.name();
								tsmNotificationMsg = tsmClient
										.getTransactionFromTSM(freechargeTxnId,
												txnType);
							}
						}
						TSMNotification notification = new TSMNotification();
						notification.setNotificationMessage(tsmNotificationMsg);
						queueHandler.processTask(notification, "TEST",
								Source.AUDIT);
						dto = disburseMentHandler.getTransactionIfExist(
								freechargeTxnId, txnType);
					}
					disburseMentHandler.updateSettledTxnStatus(metaData,
							notifactionMsg, dto);
				}
			}
			if (response == null
					|| response.getTransactions().size() < request
							.getPageSize() || response.getLastEvaluatedKey() == null) {
				break;
			}
			}

	}

}
