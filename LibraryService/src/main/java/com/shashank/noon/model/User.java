package com.shashank.noon.model;

import java.util.Date;

public class User {
	
	int userId;
	String userName;
	int totalBooksBorrowed;
	Book currentBorrowedBook;
	boolean isMemberActive;
	Date createDate;
	
	public int getTotalBooksBorrowed() {
		return totalBooksBorrowed;
	}
	public void setTotalBooksBorrowed(int totalBooksBorrowed) {
		this.totalBooksBorrowed = totalBooksBorrowed;
	}
	public boolean isMemberActive() {
		return isMemberActive;
	}
	public void setMemberActive(boolean isMemberActive) {
		this.isMemberActive = isMemberActive;
	}
	public Date getCreateDate() {
		return createDate;
	}
	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}
	int totalPendingFine;
	
	public int getTotalPendingFine() {
		return totalPendingFine;
	}
	public void setTotalPendingFine(int totalPendingFine) {
		this.totalPendingFine = totalPendingFine;
	}
	public Book getCurrentBorrowedBook() {
		return currentBorrowedBook;
	}
	public void setCurrentBorrowedBook(Book currentBorrowedBook) {
		this.currentBorrowedBook = currentBorrowedBook;
	}
	public int getUserId() {
		return userId;
	}
	public void setUserId(int userId) {
		this.userId = userId;
	}
	public String getUserName() {
		return userName;
	}
	public void setUserName(String userName) {
		this.userName = userName;
	}
	public int getBooksBorrowed() {
		return totalBooksBorrowed;
	}
	public void setBooksBorrowed(int booksBorrowed) {
		this.totalBooksBorrowed = booksBorrowed;
	}
	
	

}
