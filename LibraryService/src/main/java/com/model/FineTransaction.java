package com.model;

import java.util.Date;

import lombok.Data;

@Data
public class FineTransaction {
	
	int fineId;
	int bookBorrowId;
	int amount;
	Date createDate;

}
