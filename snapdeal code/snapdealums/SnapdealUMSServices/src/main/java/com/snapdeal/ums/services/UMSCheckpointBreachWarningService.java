/**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snapdeal.ums.services;

import java.util.Collection;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.email.intrnl.email.SendUMSNotificationRequest;
import com.snapdeal.ums.server.services.impl.UMSNotificationService;

/**
 * This class will provide the functionality to check the various checkpoint
 * breach observation and trigger the notification email to ums dev, qa and prod
 * teams.
 * 
 * @version 1.0, 13-Jan-2015
 * @author shashank
 * 
 */
@Service
public class UMSCheckpointBreachWarningService {

	private static final Logger LOG = LoggerFactory
			.getLogger(UMSCheckpointBreachWarningService.class);
	private static final String UMS_CHECKPOINT_BREACH_SUBJECT = "Checkpoint breach @ UMS production";

	public static enum CheckpointBreachContext {
		SD_CASH("SD cash"), SD_CASH_HISTORY("SD cash history");

		private String value;

		private CheckpointBreachContext(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.value;
		}
	}

	public static enum CheckpointBreachScenario {
		VALUE_EXCCEEDS_LIMIT("Value exceeded limit"), LIST_EXCEEDS_LIMIT(
				"List size exceeded limit");

		private String value;

		private CheckpointBreachScenario(String value) {
			this.value = value;
		}

		public String getValue() {
			return this.value;
		}

		@Override
		public String toString() {
			return this.value;
		}

	}

	@Autowired
	private UMSNotificationService umsNotificationService;

	private static final String UMS_CHECKPOINT_BREACH = "UMS checkpoint breach - ";

	public void checkIfValueExceedsLimit(
			UMSCheckpointBreachWarningService.CheckpointBreachContext breachContext,
			final int value, final int limit, final String id) {

		if (value > limit) {

			StringBuilder msg = new StringBuilder(UMS_CHECKPOINT_BREACH)
					.append("Context: ")
					.append(breachContext)
					.append(" ")
					.append(CheckpointBreachScenario.VALUE_EXCCEEDS_LIMIT
							.getValue())
					.append("<br />")
					.append("Limit: ")
					.append(limit)
					.append("<br />")
					.append("Actual value: ")
					.append(value)
					.append("<br />")
					.append(CheckpointBreachScenario.VALUE_EXCCEEDS_LIMIT
							.getValue())
					.append("<br />")
					.append("Time: ")
					.append(DateUtils.getCurrentTime().toString())
					.append("<br />")
					.append("Identification key: ")
					.append(id)
					.append("<br />")
					.append("@Server: ")
					.append(CacheManager.getInstance()
							.getCache(UMSPropertiesCache.class).getServerId());
			LOG.info(msg.toString());
			SendUMSNotificationRequest sendUMSNotificationRequest = new SendUMSNotificationRequest(
					msg.toString(), UMS_CHECKPOINT_BREACH_SUBJECT);
			umsNotificationService
					.sendUMSFunctionalNotification(sendUMSNotificationRequest);
		}
	}

	public void checkIfListSizeExceedsLimit(CheckpointBreachContext context,
			final Collection collection, final int limit, final String id) {

		if (collection == null || collection.isEmpty()) {
			return;
		}
		int collectionSize = collection.size();
		if (collectionSize > limit) {

			StringBuilder msg = new StringBuilder(UMS_CHECKPOINT_BREACH);
			msg.append("Context: ")
					.append(context)
					.append(" ")
					.append(CheckpointBreachScenario.LIST_EXCEEDS_LIMIT
							.getValue())
					.append("<br />")
					.append("Limit: ")
					.append(limit)
					.append("<br />")
					.append("Actual value: ")
					.append(collectionSize)
					.append("<br />")
					.append("Time: ")
					.append(DateUtils.getCurrentTime().toString())
					.append("<br />")
					.append("Identification key: ")
					.append(id)
					.append("<br />")
					.append("@Server: ")
					.append(CacheManager.getInstance()
							.getCache(UMSPropertiesCache.class).getServerId());
			;
			LOG.info(msg.toString());
			SendUMSNotificationRequest sendUMSNotificationRequest = new SendUMSNotificationRequest(
					msg.toString(), UMS_CHECKPOINT_BREACH_SUBJECT);
			umsNotificationService
					.sendUMSFunctionalNotification(sendUMSNotificationRequest);
		}
	}

}
