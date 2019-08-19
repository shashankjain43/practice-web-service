package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

/**
 * Base class for SDCash related transaction's requests 
 * @author shashank
 *
 */
@AuditableClass
public class SDWalletTxnRequest extends ServiceRequest{
	
	private static final long serialVersionUID = 505595500151296467L;
	
	@AuditableField
	@Tag(99)
	private String idempotentId;
	
	public SDWalletTxnRequest() {
		super();
	}

	public SDWalletTxnRequest(String idempotentId) {
		super();
		this.idempotentId = idempotentId;
	}

	public String getIdempotentId() {
		return idempotentId;
	}

	public void setIdempotentId(String idempotentId) {
		this.idempotentId = idempotentId;
	}
	

}