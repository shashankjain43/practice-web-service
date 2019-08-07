package com.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	
	private int userId;
	private String userName;
	private int totalBooksBorrowed;
	private Book currentBorrowedBook;
	private boolean isMemberActive;
	private Date createDate;
}
