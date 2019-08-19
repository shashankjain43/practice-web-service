package com.snapdeal.ums.services.sdCashBulkUpdate;

import com.snapdeal.ums.server.services.impl.BasicEmailRequest;

/**
 * Request class for SDCash bulk credit email that extends basic email request
 * class containing email Id.
 * 
 * @author lovey
 * 
 */

public class SDCashBulkCreditEmailRequest extends BasicEmailRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4593101963760278409L;

	private int sdCashAmount;
	private int expiryDays;
	private String orderId;
	/**
	 * Flag to check if email is verified or not
	 */
	private boolean verified= false;



	/**
	 * Flag to check if email is registered or not
	 */
	private boolean registered=false;

	public SDCashBulkCreditEmailRequest()

	{
		super();
	}





	public SDCashBulkCreditEmailRequest(String email, int sdCashAmount,
			int expiryDays, String orderId) {
		super(email);
		this.sdCashAmount = sdCashAmount;
		this.expiryDays = expiryDays;
		this.orderId = orderId;
	}





	public SDCashBulkCreditEmailRequest(String email, int sdCashAmount,
			int expiryDays, String orderId, boolean verified, boolean registered) {
		super(email);
		this.sdCashAmount = sdCashAmount;
		this.expiryDays = expiryDays;
		this.orderId = orderId;
		this.verified = verified;
		this.registered = registered;
	}





	public int getSdCashAmount() {
		return sdCashAmount;
	}

	public void setSdCashAmount(int sdCashAmount) {
		this.sdCashAmount = sdCashAmount;
	}

	public int getExpiryDays() {
		return expiryDays;
	}

	public void setExpiryDays(int expiryDays) {
		this.expiryDays = expiryDays;
	}

	public boolean isVerified() {
		return verified;
	}

	public void setVerified(boolean verified) {
		this.verified = verified;
	}

	public boolean isRegistered() {
		return registered;
	}

	public void setRegistered(boolean registered) {
		this.registered = registered;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}



	

}
