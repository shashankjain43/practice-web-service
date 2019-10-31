package com.model;

import lombok.Data;

import java.util.Date;

@Data
public class BookBorrowInfo {
	
	int boookBorrowId;
	int userId;
	Date dueDate;
	Date returnDate;
	Date createDate;

}
