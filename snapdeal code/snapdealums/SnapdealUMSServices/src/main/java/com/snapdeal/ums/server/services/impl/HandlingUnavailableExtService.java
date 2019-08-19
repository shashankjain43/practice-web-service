/**
 /**
 *  Copyright 2015 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 */
package com.snapdeal.ums.server.services.impl;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.email.intrnl.email.SendUMSNotificationRequest;
import com.snapdeal.ums.server.services.IHandlingUnavailableExtService;
import com.snapdeal.ums.server.services.IServerBehaviourContextService;
import com.snapdeal.ums.server.services.impl.UMSNotificationService;

/**
 * 
 * @version 1.0, 19-Jan-2015
 * @author Jain.shashank@snapdeal.com
 * 
 */

@Service
@Transactional
public class HandlingUnavailableExtService implements
		IHandlingUnavailableExtService {

	private static final Logger LOG = LoggerFactory
			.getLogger(HandlingUnavailableExtService.class);

	private static final String EXTERNAL_SYSTEM_NOTIFICATION_SUBJECT = "External system availability @ UMS production";

	@Autowired
	private UMSNotificationService umsNotificationService;

	@Autowired
	private IServerBehaviourContextService serverBehaviourContextService;

	public enum ExternalAppName {
		CAMS("CaMS"), OMS("oms");

		private ExternalAppName(String code) {
			this.value = code;
		}

		private String value;

		public String getValue() {
			return this.value;
		}
	}

	private static final Set<String> CaMSDependentURL = new HashSet<String>();

	private static boolean isCaMSDependentSrvDisabled = false;

	static {
		CaMSDependentURL.add("/service/ums/email/sendOrderSummaryEmail");
		CaMSDependentURL
				.add("/service/ums/email/sendOrderReplacedSummaryEmail");
		CaMSDependentURL
				.add("/service/ums/email/sendAlternateCollectMoneyEmail");
		CaMSDependentURL.add("/service/ums/email/sendOrderRetryEmail");
		CaMSDependentURL.add("/service/ums/email/sendPrebookRetryEmail");
		CaMSDependentURL.add("/service/ums/email/sendPendingResponseEmail");
		CaMSDependentURL.add("/service/ums/email/sendCODOrderEmail");
		CaMSDependentURL
				.add("/service/ums/email/sendAlternatePaymentConfirmationEmail");
		CaMSDependentURL.add("/service/ums/email/sendAlternateRefundEmail");
		CaMSDependentURL.add("/service/ums/email/sendAlternateAbsorbEmail");
		CaMSDependentURL.add("/service/ums/email/sendReleaseDateShiftMail");
		CaMSDependentURL.add("/service/ums/email/sendSecondPaymentMail");
		CaMSDependentURL
				.add("/service/ums/email/sendPrebookPaymentConfirmationEmail");

		CaMSDependentURL.add("/service/ums/subscription/addSubscriber2");
		CaMSDependentURL.add("/service/ums/subscription/addEmailSubscriber4");
		CaMSDependentURL
				.add("/service/ums/subscription/createMobileSubscriber");
		CaMSDependentURL.add("/service/ums/subscription/addMobileSubscriber4");
		CaMSDependentURL.add("/service/ums/subscription/unsubscribeEmail");
		CaMSDependentURL.add("/service/ums/subscription/unsubscribeEmail2");
		CaMSDependentURL
				.add("/service/ums/subscription/addMobileSubscriberWithPin");
		CaMSDependentURL.add("/service/ums/subscription/searchZonesByMobile");
		CaMSDependentURL.add("/service/ums/subscription/unsubscribeMobile");
		CaMSDependentURL.add("/service/ums/subscription/unsubscribeMobile2");
		CaMSDependentURL
				.add("/service/ums/subscription/getEmailSubscribersIncremental");
	}

	public static Set<String> getCamsdependenturl() {
		return CaMSDependentURL;
	}

	public static boolean isCaMSDependentSrvDisabled() {
		return isCaMSDependentSrvDisabled;
	}

	public static void setCaMSDependentSrvDisabled(
			boolean isCaMSDependentSrvDisabled) {
		HandlingUnavailableExtService.isCaMSDependentSrvDisabled = isCaMSDependentSrvDisabled;
	}

	public void enableTemporarilyDisabledServices(
			ExternalAppName externalAppName) {
		if (externalAppName == null) {
			return;
		}

		if (externalAppName == ExternalAppName.CAMS) {
			if (isCaMSDependentSrvDisabled == true) {
				serverBehaviourContextService
						.enableTemporarilyDisabledServices(CaMSDependentURL);
				isCaMSDependentSrvDisabled = false;
				LOG.info("Enabled CaMS dependent services successfully.");
				Notify(ExternalAppName.CAMS.getValue(), "Enabled");
			}
		}
	}

	public void temporarilyDisableServices(ExternalAppName externalAppName) {
		if (externalAppName == null) {
			return;
		}
		if (externalAppName.getValue().equals(ExternalAppName.CAMS.getValue())) {
			if (isCaMSDependentSrvDisabled == false) {
				serverBehaviourContextService
						.temporarilyDisableServices(CaMSDependentURL);
				isCaMSDependentSrvDisabled = true;
				LOG.info("Disabled CaMS dependent services successfully.");
				Notify(ExternalAppName.CAMS.getValue(), "Disabled");
			} else {
				LOG.info("CaMS dependent services are already disabled.");

			}

		}

	}

	private void Notify(String extAppName, String action) {
		if (extAppName.equals(ExternalAppName.CAMS.getValue())) {
			StringBuilder msg = new StringBuilder(extAppName);
			if (action.equals("Disabled")) {
				msg.append(
						": External system not available, Hence disabled its dependent services as below")
						.append("<br />");
			}
			if (action.equals("Enabled")) {
				msg.append(
						": External system is up, Hence enabled its dependent services as below")
						.append("<br />");
			}
			msg.append(CaMSDependentURL.toString()).append("<br />")
					.append("@Time: ")
					.append(DateUtils.getCurrentTime().toString());
			SendUMSNotificationRequest sendUMSNotificationRequest = new SendUMSNotificationRequest(
					msg.toString(), EXTERNAL_SYSTEM_NOTIFICATION_SUBJECT);
			LOG.info(msg.toString());
			umsNotificationService
					.sendTechBreachNotification(sendUMSNotificationRequest);
		}

	}

}
