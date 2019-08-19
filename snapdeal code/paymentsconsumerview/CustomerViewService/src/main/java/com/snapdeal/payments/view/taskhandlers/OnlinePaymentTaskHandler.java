/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import java.util.List;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Service;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.entity.NotificationMessageLinkTxnDetails;
import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;

/**
 * @author shubham.bansal
 *
 */
@Service
@Slf4j
public class OnlinePaymentTaskHandler extends BaseTaskHandler {
	
	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception {
		TransactionDetailsDTO dto = null, uplinkDto = null;
		dto = getTransactionIfExist(notifactionMsg.getGlobalTxnId(),
				notifactionMsg.getGlobalTxnType());

		String txnRef = null, txnRefType = null;
		List<NotificationMessageLinkTxnDetails> uplink = notifactionMsg
				.getUplinkGlobalTxnIds();
		if (uplink == null || uplink.size() <= 0) {
			txnRef = notifactionMsg.getGlobalTxnId();
			txnRefType = notifactionMsg.getGlobalTxnType();
		} else if (uplink.get(0) != null && uplink.get(0).getTxnType() != null
				&& uplink.get(0).getTxnType().equals("ADD_CASH")) {
			txnRef = notifactionMsg.getGlobalTxnId();
			txnRefType = notifactionMsg.getGlobalTxnType();
		} else {
			txnRef = uplink.get(0).getTxnId();
			txnRefType = uplink.get(0).getTxnType();

			if (dto == null) {
				NotificationMessage tsmNotificationMsg = tsmClient
						.getTransactionFromTSM(txnRef, txnRefType);
				execute(tsmNotificationMsg);
			}
			uplinkDto = getTransactionIfExist(txnRef, txnRefType);
		}
		TransactionDetailsEntity entity = paymentsViewBuilder
				.getTransactionDetailEntity(notifactionMsg);

		if (uplinkDto != null) {
			txnRef = uplinkDto.getParentTxnId();
			txnRefType = uplinkDto.getParentTxnType();
		}

		entity.setTxnRef(txnRef);
		entity.setTxnRefType(txnRefType);
		entity.setTxnMetaData(notifactionMsg.getGlobalTxnMetaData());

		if (dto == null) {
			persistManager.saveTransactionDetails(entity);
			TransactionStateDetailsEntity stateEntity = paymentsViewBuilder
					.getTransactionDetailsStateEntity(notifactionMsg);
			dto = getTransactionIfExist(notifactionMsg.getGlobalTxnId(),
					notifactionMsg.getGlobalTxnType());

			stateEntity.setTxnId(dto.getTxnId());
			persistManager.saveTransactionStateDetails(stateEntity);
		} else {
			TransactionStateDetailsEntity stateEntity = paymentsViewBuilder
					.getTransactionDetailsStateEntity(notifactionMsg);
			stateEntity.setTxnId(dto.getTxnId());
			handleStateChange(stateEntity);
			persistManager.updateTxnDetailsOFDirectSystem(entity);
		}
		log.info("successfully processsed the task of DirectSystem");
	}

}
