package com.snapdeal.payments.view.service.impl;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.CannotGetJdbcConnectionException;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.commons.enums.Source;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.service.ITSMQueueHandler;
import com.snapdeal.payments.view.sqs.NotificationReaderService;
import com.snapdeal.payments.view.sqs.TSMNotification;
import com.snapdeal.payments.view.taskhandlers.TaskDecider;
import com.snapdeal.payments.view.utils.PaymentsViewShardContextHolder;
import com.snapdeal.payments.view.utils.ExceptionHandler;
import com.snapdeal.payments.view.utils.PaymentsViewShardUtil;

@Slf4j
@Component("PaymentsViewQueueHandler")
public class PaymentsViewQueueHandler implements ITSMQueueHandler {

	@Autowired
	private NotificationReaderService reader;
	@Autowired
	private ExceptionHandler exceptionHandler;
	@Autowired
	private TaskDecider taskDecider;
	@Autowired
	private PaymentsViewShardUtil paymentViewShardUtil;

	@Marked
	@Timed
	@Override
	@ExceptionMetered
	public void processTask(final TSMNotification notifaction,
			final String queueName,
			final Source source) {
		Throwable exception =null ;
		ViewTypeEnum viewType = null;
		log.info("processing from thread pool fc_txn_id is : "
				+ notifaction.getNotificationMessage().getGlobalTxnId()
				+ " txn_type : "
				+ notifaction.getNotificationMessage().getGlobalTxnType()
				+ " timstamp : " +  new Date(notifaction.getNotificationMessage().getTsmTimestamp())
				+ " recippent handle is : " + notifaction.getReceiptHandle());
		try {
			//attempt merchant_view
			viewType = ViewTypeEnum.MERCHANTVIEW;
			String merchantId = notifaction.getNotificationMessage()
					.getMerchantId();
			PaymentsViewShardContextHolder.setShardKey(paymentViewShardUtil.getTaskShardKey(
					merchantId, ViewTypeEnum.MERCHANTVIEW));
			taskDecider.processForMerchantView(notifaction, source);

			//attempt request_view
			PaymentsViewShardContextHolder.clearShardKey();
			viewType = ViewTypeEnum.REQUESTVIEW;
			PaymentsViewShardContextHolder.setShardKey(paymentViewShardUtil.getTaskShardKey(
					null, ViewTypeEnum.REQUESTVIEW));
			taskDecider
					.processForRequestView(notifaction, source);

		} catch (Throwable ex) {
			log.error("Exception in message :" + ex.getMessage());
			exceptionHandler.handelException(notifaction, viewType, ex);
			exception = ex ;
			
		} finally {
			if (!(source == Source.AUDIT || exception instanceof CannotGetJdbcConnectionException)) {
				log.info("deleting SQS message for Notification Type " + notifaction.getNotificationMessage().getGlobalTxnType());
				reader.deleteNotification(queueName,
						notifaction.getReceiptHandle());
			}
			PaymentsViewShardContextHolder.clearShardKey();
		}
	}
}
