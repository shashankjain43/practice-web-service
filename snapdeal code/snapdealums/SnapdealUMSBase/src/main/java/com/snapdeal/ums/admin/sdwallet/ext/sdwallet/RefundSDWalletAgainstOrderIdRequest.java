package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;

@AuditableClass
public class RefundSDWalletAgainstOrderIdRequest extends SDWalletTxnRequest {

	private static final long serialVersionUID = 9189645100238614520L;

	@AuditableField
	@Tag(3)
	private Integer userId;

	@AuditableField
	@Tag(4)
	private Integer amount;

	@AuditableField
	@Tag(5)
	private Integer activityTypeId;

	@AuditableField
	@Tag(6)
	private String OrderCode = " ";

	@AuditableField
	@Tag(7)
	private String source;

	@AuditableField
	@Tag(8)
	private String requestedBy;

	public RefundSDWalletAgainstOrderIdRequest() {
		super();
	}

	public RefundSDWalletAgainstOrderIdRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, String source,
			String requestedBy) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.activityTypeId = activityTypeId;
		OrderCode = orderCode;
		this.source = source;
		this.requestedBy = requestedBy;
	}
	

	public RefundSDWalletAgainstOrderIdRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, String source,
			String requestedBy, String idempotentId) {
		super(idempotentId);
		this.userId = userId;
		this.amount = amount;
		this.activityTypeId = activityTypeId;
		OrderCode = orderCode;
		this.source = source;
		this.requestedBy = requestedBy;
	}


	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public Integer getAmount() {
		return amount;
	}

	public void setAmount(Integer amount) {
		this.amount = amount;
	}

	public Integer getActivityTypeId() {
		return activityTypeId;
	}

	public void setActivityTypeId(Integer activityTypeId) {
		this.activityTypeId = activityTypeId;
	}

	public String getOrderCode() {
		return OrderCode;
	}

	public void setOrderCode(String OrderCode) {
		this.OrderCode = OrderCode;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getRequestedBy() {
		return requestedBy;
	}

	public void setRequestedBy(String requestedBy) {
		this.requestedBy = requestedBy;
	}

}