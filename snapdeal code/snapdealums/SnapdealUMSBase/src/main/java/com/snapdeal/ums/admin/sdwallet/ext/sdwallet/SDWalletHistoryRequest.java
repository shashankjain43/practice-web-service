/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 17-Jan-2013
 *  @author himanshu
 */
package com.snapdeal.ums.admin.sdwallet.ext.sdwallet;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SDWalletHistoryRequest extends ServiceRequest {
	/**
     * 
     */
	private static final long serialVersionUID = 5178506238755295509L;
	@Tag(5)
	private Integer userId;

	@Tag(6)
	private String orderId;

	public SDWalletHistoryRequest() {
		super();
	}

	public SDWalletHistoryRequest(String orderId) {
		super();
		this.orderId = orderId;
	}

	public SDWalletHistoryRequest(Integer userId) {
		super();
		this.userId = userId;
	}

	public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}

}
