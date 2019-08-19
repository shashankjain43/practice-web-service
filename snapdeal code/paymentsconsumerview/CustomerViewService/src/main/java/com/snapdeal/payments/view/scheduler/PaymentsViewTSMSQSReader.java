package com.snapdeal.payments.view.scheduler;

import java.util.List;
import java.util.concurrent.RejectedExecutionException;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.tsm.entity.NotificationMessage;
import com.snapdeal.payments.tsm.entity.NotificationMessageLinkTxnDetails;
import com.snapdeal.payments.tsm.enums.TsmState;
import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.service.ITSMQueueHandler;
import com.snapdeal.payments.view.sqs.NotificationReaderException;
import com.snapdeal.payments.view.sqs.NotificationReaderService;
import com.snapdeal.payments.view.sqs.TSMNotification;
import com.snapdeal.payments.view.utils.PaymentsViewThreadPoolExecutor;

@Slf4j
@Component
@Async
public class PaymentsViewTSMSQSReader {

	@Autowired
	private NotificationReaderService reader;

	@Autowired
	private PaymentsViewThreadPoolExecutor threadPoolExecutor;

	@Value("${snapdeal.payments.mercahnt.sqs.queuename}")
	String queueName;

	@Qualifier("PaymentsViewQueueHandler")
	@Autowired
	private ITSMQueueHandler paymentsTaskHandler;

	@Async
	public void readTSMQueue() {

		for (;;) {
			List<TSMNotification> notificationList = null;
			try {
				notificationList = reader.readNotifications(queueName);
				if(notificationList!=null){
					if( notificationList.size() > 0){
						log.debug("number of messages read from the queue : "
							+ notificationList.size());
					}
					log.info("number of currently active threads " + threadPoolExecutor.getActiveCount()) ;
					
					for (TSMNotification notification : notificationList) {
						try {
							log.info("processing from queue is fc txn id: " + notification.getNotificationMessage().getGlobalTxnId() +
									" txn_type : " + notification.getNotificationMessage().getGlobalTxnType());
							
							createAndSubmitTSMNotifcationTask(notification);
						} catch (RejectedExecutionException e) {
							paymentsTaskHandler.processTask(notification,
									queueName, Source.DIRECT);
						}catch(Throwable th){
							log.info("Exception occured while executing message : " + th.getMessage() +
									" FC_TXN_ID : " + notification.getNotificationMessage().getGlobalTxnId());
						}
					}
				}
				
				if (notificationList == null || notificationList.size() == 0) {
					Thread.sleep(100);
				}
			} catch (NotificationReaderException e) {
				log.error(e.getMessage());
			} catch (InterruptedException e) {
				log.error(e.getMessage());
			}
		}
	}

	private void createAndSubmitTSMNotifcationTask(
			final TSMNotification notification) {
		boolean okayToProcess = filterPaymentsViewTask(notification);
		if (okayToProcess) {
			
			threadPoolExecutor.submit(new Runnable() {
				@Override
				public void run() {
					paymentsTaskHandler.processTask(notification, queueName,
							Source.DIRECT);
				}
			});
		}
	}
	
	private boolean filterPaymentsViewTask(TSMNotification notification) {
		NotificationMessage notificationMsg = notification
				.getNotificationMessage();
		List<NotificationMessageLinkTxnDetails> uplinks = notificationMsg
				.getUplinkGlobalTxnIds();

		if (uplinks != null && uplinks.size() > 0) {
			String txnType = uplinks.get(0).getTxnType();
			if (txnType.equals("DEBUGGER_DASHBOARD_PG_LOAD_NODAL_ACCOUNT")
					|| txnType.equals("PAYABLES_DISBURSEMENT")
					|| txnType.equals("WALLET_CORP_DISBURSEMENT")) {

				log.info("Not Processing payables notification  : "
						+ notification.getNotificationMessage());
				reader.deleteNotification(queueName,
						notification.getReceiptHandle());
				return false;
			}
		}
		if ((notificationMsg.getGlobalTxnType().equals("PAYABLES_DISBURSEMENT") || notificationMsg
				.getGlobalTxnType().equals("WALLET_CORP_DISBURSEMENT"))
				&& notificationMsg.getTsmState() != TsmState.SUCCESS) {
			reader.deleteNotification(queueName,
					notification.getReceiptHandle());
			return false;
		}
		return true;
	}
}
