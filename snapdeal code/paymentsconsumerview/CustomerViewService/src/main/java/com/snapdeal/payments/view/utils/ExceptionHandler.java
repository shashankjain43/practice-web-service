package com.snapdeal.payments.view.utils;

import java.util.Date;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.payments.metrics.annotations.ExceptionMetered;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.RequestAware;
import com.snapdeal.payments.metrics.annotations.Timed;
import com.snapdeal.payments.view.commons.enums.ViewTypeEnum;
import com.snapdeal.payments.view.entity.MerchantViewAuditEntity;
import com.snapdeal.payments.view.entity.RequestViewAuditEntity;
import com.snapdeal.payments.view.sqs.TSMNotification;
import com.snapdeal.payments.view.utils.metadata.PaymentsViewBuilder;

@Slf4j
@Component
public class ExceptionHandler {

	@Autowired
	private PaymentsViewBuilder paymentsViewBuilder;

	@Autowired
	private ExceptionAuditHandler<MerchantViewAuditEntity> merchantAuditHandler;

	@Autowired
	private ExceptionAuditHandler<RequestViewAuditEntity> requestAuditHandler;

	@Marked
	@Logged
	@RequestAware
	@ExceptionMetered
	@Timed
	public void handelException(final TSMNotification notifaction,
			final ViewTypeEnum viewType, Throwable ex) {
		switch (viewType) {
		case MERCHANTVIEW:
			MerchantViewAuditEntity mAuditEntity = new MerchantViewAuditEntity();
			mAuditEntity.setFcTxnId(notifaction.getNotificationMessage()
					.getGlobalTxnId());
			mAuditEntity.setTxnType(notifaction.getNotificationMessage()
					.getGlobalTxnType());
			mAuditEntity.setTsmTimeStamp(new Date(notifaction
					.getNotificationMessage().getTsmTimestamp()));
			mAuditEntity.setMerchantId(notifaction.getNotificationMessage()
					.getMerchantId());
			merchantAuditHandler
					.handleExceptionOfMerchantView(ex, mAuditEntity);
			break;
		case REQUESTVIEW:

			RequestViewAuditEntity rAuditEntity = new RequestViewAuditEntity();
			rAuditEntity.setFcTxnId(notifaction.getNotificationMessage()
					.getGlobalTxnId());
			rAuditEntity.setTxnType(notifaction.getNotificationMessage()
					.getGlobalTxnType());
			rAuditEntity.setTsmTimeStamp(new Date(notifaction
					.getNotificationMessage().getTsmTimestamp()));
			// FIXME: add source user id instead of merchant id
			rAuditEntity.setSourceUserId((notifaction.getNotificationMessage()
					.getMerchantId()));
			requestAuditHandler.handleExceptionOfMerchantView(ex, rAuditEntity);
			return;

		}
	}

}
