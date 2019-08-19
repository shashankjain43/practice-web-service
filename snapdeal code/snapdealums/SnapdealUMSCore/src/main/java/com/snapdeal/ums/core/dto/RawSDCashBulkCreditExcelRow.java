package com.snapdeal.ums.core.dto;

public class RawSDCashBulkCreditExcelRow {

	String email;
	String sdCash;
	String expiryDays;
	String orderId;
	boolean isCredited;
	int rowId;

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public RawSDCashBulkCreditExcelRow(String email, String sdCash2,
			String expiryDays, String orderId, boolean isCredited, int rowId) {
		super();
		this.email = email;
		this.sdCash = sdCash2;
		this.expiryDays = expiryDays;
		this.orderId = orderId;
		this.isCredited = isCredited;
		this.rowId = rowId;
	}

	public String getSdCash() {
		return sdCash;
	}

	public void setSdCash(String sdCash) {
		this.sdCash = sdCash;
	}

	public String getExpiryDays() {
		return expiryDays;
	}

	public void setExpiryDays(String expiryDays) {
		this.expiryDays = expiryDays;
	}

	public String getOrderId() {
		return orderId;
	}

	public void setOrderId(String orderId) {
		this.orderId = orderId;
	}
	
	public void setIsCredited(boolean isCredited){
		this.isCredited = isCredited;
	}
	
	public boolean getIsCredited(){
		return this.isCredited;
	}
	
	public void setRowId(int rowID){
	 	this.rowId = rowID;
	}
	
	public int getRowId(){
		return this.rowId;
	}

	@Override
	public String toString() {
		return "RawSDCashBulkCreditExcelRow [email=" + email + ", sdCash="
				+ sdCash + ", expiryDays=" + expiryDays + ", orderId="
				+ orderId + ", isCredited=" + isCredited + ", rowId=" + rowId
				+ "]";
	}
	
}
