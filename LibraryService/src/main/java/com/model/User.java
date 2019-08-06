package com.model;

import java.util.Date;

import lombok.Data;

@Data
public class User {
	
	int userId;
	String userName;
	int totalBooksBorrowed;
	Book currentBorrowedBook;
	boolean isMemberActive;
	Date createDate;
}
