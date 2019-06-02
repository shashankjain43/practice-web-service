package com.model;

import java.util.Date;

public class BookBorrowInfo {
	
	int boookBorrowId;
	int userId;
	Date dueDate;
	Date returnDate;
	Date createDate;
	public int getBoookBorrowId() {
		return boookBorrowId;
	}
	public void setBoookBorrowId(int boookBorrowId) {
		this.boookBorrowId = boookBorrowId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public Date getDueDate() {
		return dueDate;
	}
	public void setDueDate(Date dueDate) {
		this.dueDate = dueDate;
	}
	public Date getReturnDate() {
		return returnDate;
	}
	public void setReturnDate(Date returnDate) {
		this.returnDate = returnDate;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

}
