package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;

/**
 * Request class to maintain Trigger email flag
 * 
 * @author lovey
 */
@AuditableClass
public class CreditSDWalletSendEmailRequest extends CreditSDWalletRequest {

	private static final long serialVersionUID = 5413946975698208963L;
	
	/**
	 * input parameter called 'triggerEmail'. By default this flag will be set
	 * to true.
	 */
	@AuditableField
	@Tag(20)
	private boolean triggerEmail = true;

	public CreditSDWalletSendEmailRequest() {
		super();
	}
	
	public CreditSDWalletSendEmailRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDays, String requestedBy) {
		super(userId, amount, activityTypeId, orderCode, transactionId, source,
				expiryDays, requestedBy);
	}
	
	public CreditSDWalletSendEmailRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDays, String requestedBy,
			boolean triggerEmail) {
		super(userId, amount, activityTypeId, orderCode, transactionId, source,
				expiryDays, requestedBy);
		this.triggerEmail = triggerEmail;
	}
	
	public CreditSDWalletSendEmailRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDays, String requestedBy,
			String idempotentId) {
		super(userId, amount, activityTypeId, orderCode, transactionId, source,
				expiryDays, requestedBy, idempotentId);
	}

	public CreditSDWalletSendEmailRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDays, String requestedBy,
			boolean triggerEmail, String idempotentId) {
		super(userId, amount, activityTypeId, orderCode, transactionId, source,
				expiryDays, requestedBy, idempotentId);
		this.triggerEmail = triggerEmail;
	}

	public boolean isTriggerEmail() {
		return triggerEmail;
	}

	public void setTriggerEmail(boolean triggerEmail) {
		this.triggerEmail = triggerEmail;
	}

}