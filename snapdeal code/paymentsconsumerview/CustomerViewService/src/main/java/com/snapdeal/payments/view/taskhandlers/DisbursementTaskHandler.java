/**
 * 
 */
package com.snapdeal.payments.view.taskhandlers;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;

import com.snapdeal.mob.enums.IntegrationMode;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.payments.metadata.DisbursementMetadata;
import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.enums.TransactionType;
import com.snapdeal.payments.view.customer.commons.enums.MerchantViewState;
import com.snapdeal.payments.view.entity.TransactionDetailsForDisbursment;
import com.snapdeal.payments.view.entity.TransactionStateDetailsEntity;
import com.snapdeal.payments.view.merchant.commons.dto.TransactionDetailsDTO;
import com.snapdeal.payments.view.utils.clients.MOBClientUtil;

/**
 * @author shubham.bansal
 *
 */
@Slf4j
@Service
public class DisbursementTaskHandler extends BaseTaskHandler {

	@Autowired
	private OnlinePaymentTaskHandler onlineTaskHandler;
	@Autowired
	@Qualifier("PayableDisbursementTaskHandler")
	TaskHandler<NotificationMessage, Void> payableDisbursmentHandler;
	@Autowired
	@Qualifier("WalletDisbursementTaskHandler")
	TaskHandler<NotificationMessage, Void> walletDisbursmentHandler;
	
	@Autowired
	private MOBClientUtil mobClientUtil;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * (com.snapdeal.payments.tsm.entity.NotificationMessage)
	 */
	@Override
	public void execute(NotificationMessage notifactionMsg) throws Exception {

		TransactionType type = TransactionType.valueOf(notifactionMsg
				.getGlobalTxnType());
		String merchantId = notifactionMsg.getMerchantId();
		GetMerchantDetails merchant= mobClientUtil.getMerchantDisplayInfoById(merchantId);
		String meteData = notifactionMsg.getGlobalTxnMetaData();
		// Listen Only Sucess and Rollback(For Revert Settlement)
		if (!TsmState.SUCCESS.equals(notifactionMsg.getTsmState()))
			return;
		if (StringUtils.isBlank(meteData)) {
			log.info("metaData  is coming null " + notifactionMsg);
			return;
		}
		IntegrationMode integrationMode = IntegrationMode.forValue(merchant.getIntegrationMode());
		if((!(IntegrationMode.FCPLUS == integrationMode) && TransactionType.WALLET_CORP_DISBURSEMENT==type))
		return;
		if (IntegrationMode.FCPLUS == integrationMode)
			walletDisbursmentHandler.execute(notifactionMsg);
		
		else
			payableDisbursmentHandler.execute(notifactionMsg);

		log.info("successfully processsed the task of Disbursement");

	}

	public void updateSettledTxnStatus(DisbursementMetadata metaData,
			NotificationMessage notifactionMsg, TransactionDetailsDTO dto) {

		TransactionDetailsForDisbursment deEntity = new TransactionDetailsForDisbursment();
		deEntity.setSettlementId(metaData.getTransactionReference());
		deEntity.setTxnId(dto.getTxnId());
		deEntity.setSettlementDate(new Date(notifactionMsg.getTsmTimestamp()));
		/*new Date(new Long(metaData
				.getSettlementEndTime()))*/
		deEntity.setTsmTimeStamp(new Date(notifactionMsg.getTsmTimestamp()));
		deEntity.setDisbursementEngineMetadata(notifactionMsg.getGlobalTxnMetaData());
		persistManager.updateTxnDeatilsDisbursmentSystem(deEntity);

		TransactionStateDetailsEntity stateEntity = new TransactionStateDetailsEntity();
		stateEntity.setTxnId(dto.getTxnId());
		stateEntity.setTsmTimeStamp(new Date(notifactionMsg.getTsmTimestamp()));
		stateEntity.setTxnState(MerchantViewState.SETTLED.getName());
		handleStateChange(stateEntity);

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.snapdeal.payments.view.taskhandlers.BaseTaskHandler#getTransactionIfExist
	 * (java.lang.String, java.lang.String)
	 */
	@Override
	public TransactionDetailsDTO getTransactionIfExist(String txnId,
			String txnType) {
		return super.getTransactionIfExist(txnId, txnType);
	}

}
