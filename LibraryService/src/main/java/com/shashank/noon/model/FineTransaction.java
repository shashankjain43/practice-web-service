package com.shashank.noon.model;

import java.util.Date;

public class FineTransaction {
	
	int fineId;
	int bookBorrowId;
	int amount;
	Date createDate;
	public int getFineId() {
		return fineId;
	}
	public void setFineId(int fineId) {
		this.fineId = fineId;
	}
	public int getBookBorrowId() {
		return bookBorrowId;
	}
	public void setBookBorrowId(int bookBorrowId) {
		this.bookBorrowId = bookBorrowId;
	}
	public int getAmount() {
		return amount;
	}
	public void setAmount(int amount) {
		this.amount = amount;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	
	

}
