/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.view.commons.constant.PaymentsViewConstants;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.entity.TransactionDetailsEntity;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionPaybleDto;
import com.snapdeal.payments.view.utils.metadata.ViewMetaDataInterprator;

/**
 * @author shubham.bansal
 *
 */
@Slf4j
@Service
public class PayableTaskHandler extends BaseTaskHandler {

	
	@Autowired
	private OnlinePaymentTaskHandler onlineTaskHandler;
	
	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception{
		Map<String, String> uplinkDetails1 = getUplinkMap(notifactionMsg);
		if (!validPayable(uplinkDetails1
				.get(PaymentsViewConstants.TXN_TYPE)))
			return;

		TransactionDetailsDTO txnDto = persistManager
				.getTransactionDetails(uplinkDetails1);
		if (txnDto == null) {
			NotificationMessage tsmNotificationMsg = tsmClient
					.getTransactionFromTSM(
							uplinkDetails1.get(PaymentsViewConstants.FC_TXN_ID),
							uplinkDetails1.get(PaymentsViewConstants.TXN_TYPE));
			onlineTaskHandler.execute(tsmNotificationMsg);
			txnDto = persistManager.getTransactionDetails(uplinkDetails1);
		}
		log.info("Transaction exists in our Db for uplinks : " + uplinkDetails1);
		TransactionDetailsEntity entity = new TransactionDetailsEntity();
		viewMetaDataInterprator.filledWithMetadataOfPayable(notifactionMsg, entity);
		TransactionPaybleDto dto = paymentsViewBuilder
				.convertTransactionDetailsEntityToDto(entity);
		dto.setFcTxnId(txnDto.getFcTxnId());
		dto.setFcTxnType(txnDto.getTxnType());
		dto.setBtsTxnRef(notifactionMsg.getGlobalTxnId());
		dto.setTsmTimeStamp(new Date(notifactionMsg.getTsmTimestamp()));
		dto.setPayableMetaData(notifactionMsg.getGlobalTxnMetaData());
		log.info("updating Taxes etc for transaction : " + dto);
		persistManager.updateTxnDeatilsPayableSystem(dto);
		log.info("successfully processsed the task of Payable");
	}
	
	private boolean validPayable(String txnType){
		 TransactionType type = TransactionType.valueOf(txnType);
		 switch (type) {
		 case CUSTOMER_PAYMENT:
         case CANCELLATION_REFUND:
            return true;
         case OPS_WALLET_DEBIT:
         case OPS_WALLET_DEBIT_REVERSE:
         case OPS_WALLET_DEBIT_VOID:
         case OPS_WALLET_DEBIT_VOID_REVERSE:
            return true;

		default:
			return false;
		}
	}
	
	

}
