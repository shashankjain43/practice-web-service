package com.model;

import lombok.Data;

import java.util.Date;

@Data
public class User {
	
	int userId;
	String userName;
	int totalBooksBorrowed;
	Book currentBorrowedBook;
	boolean isMemberActive;
	Date createDate;
}
