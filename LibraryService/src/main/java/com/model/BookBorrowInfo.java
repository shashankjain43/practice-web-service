package com.model;

import java.util.Date;

import lombok.Data;

@Data
public class BookBorrowInfo {
	
	int boookBorrowId;
	int userId;
	Date dueDate;
	Date returnDate;
	Date createDate;

}
