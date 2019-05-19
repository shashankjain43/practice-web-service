package com.shashank.noon.model;

public class BorrowRequest extends BaseRequest {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	int bookId;
	int userId;
	int days;

	public int getDays() {
		return days;
	}
	public void setDays(int days) {
		this.days = days;
	}
	public int getBookId() {
		return bookId;
	}
	public void setBookId(int bookId) {
		this.bookId = bookId;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	
}
