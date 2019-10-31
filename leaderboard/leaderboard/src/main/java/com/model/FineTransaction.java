package com.model;

import lombok.Data;

import java.util.Date;

@Data
public class FineTransaction {
	
	int fineId;
	int bookBorrowId;
	int amount;
	Date createDate;

}
