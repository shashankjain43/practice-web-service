package com.snapdeal.ums.core.dto;

public class RawSDCashBulkDebitExcelRow {

		String email;
		String sdCash;
		String orderId;
		boolean isDebited;
		int rowId;

		public String getEmail() {
			return email;
		}

		public void setEmail(String email) {
			this.email = email;
		}

		public RawSDCashBulkDebitExcelRow(String email, String sdCash, String orderId, boolean isDebited, int rowId) {
			super();
			this.email = email;
			this.sdCash = sdCash;
			this.orderId = orderId;
			this.isDebited = isDebited;
			this.rowId = rowId;
		}

		public String getSdCash() {
			return sdCash;
		}

		public void setSdCash(String sdCash) {
			this.sdCash = sdCash;
		}
		public String getOrderId() {
			return orderId;
		}

		public void setOrderId(String orderId) {
			this.orderId = orderId;
		}
		
		public void setIsDebited(boolean isDebited){
			this.isDebited = isDebited;
		}
		
		public boolean getIsDebited(){
			return this.isDebited;
		}
		
		public void setRowId(int rowID){
		 	this.rowId = rowID;
		}
		
		public int getRowId(){
			return this.rowId;
		}

		@Override
		public String toString() {
			return "RawSDCashBulkDebitExcelRow [email=" + email + ", sdCash="
					+ sdCash + ", orderId="
					+ orderId + ", isDebited=" + isDebited + ", rowId=" + rowId
					+ "]";
		}
		
	}

