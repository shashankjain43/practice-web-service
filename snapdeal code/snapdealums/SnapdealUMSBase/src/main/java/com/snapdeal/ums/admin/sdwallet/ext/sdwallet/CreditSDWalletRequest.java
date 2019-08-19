package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

@AuditableClass
public class CreditSDWalletRequest extends SDWalletTxnRequest {

    private static final long serialVersionUID = 8302867336122506622L;
    
    @AuditableField
    @Tag(3)
    private Integer           userId;
    
    @AuditableField
    @Tag(4)
    private Integer           amount;
    
    @AuditableField
    @Tag(5)
    private Integer           activityTypeId;
    
    @AuditableField
    @Tag(6)
    private String            orderCode;
    
    @AuditableField
    @Tag(7)
    private Integer           transactionId;
    
    @AuditableField
    @Tag(8)
    private String            source;
    
    @AuditableField
    @Tag(9)
    private Integer           expiryDays       = 0;
    
    @AuditableField
    @Tag(10)
    private String            requestedBy;

    public CreditSDWalletRequest() {
		super();
    }
    
    public CreditSDWalletRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDays, String requestedBy) {
		super();
		this.userId = userId;
		this.amount = amount;
		this.activityTypeId = activityTypeId;
		this.orderCode = orderCode;
		this.transactionId = transactionId;
		this.source = source;
		this.expiryDays = expiryDays;
		this.requestedBy = requestedBy;
	}
    
    public CreditSDWalletRequest(Integer userId, Integer amount,
			Integer activityTypeId, String orderCode, Integer transactionId,
			String source, Integer expiryDays, String requestedBy, String idempotentId) {
		super(idempotentId);
		this.userId = userId;
		this.amount = amount;
		this.activityTypeId = activityTypeId;
		this.orderCode = orderCode;
		this.transactionId = transactionId;
		this.source = source;
		this.expiryDays = expiryDays;
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
        return orderCode;
    }

    public void setOrderCode(String orderCode) {
        this.orderCode = orderCode;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public void setExpiryDays(Integer expiryDays) {
        this.expiryDays = expiryDays;
    }

    public Integer getExpiryDays() {
        return expiryDays;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

}