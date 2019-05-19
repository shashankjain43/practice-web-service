package com.shashank.noon.model;

public class Book {
	
	enum BookStatus {
		  AVAILABLE,
		  BORROWED
		}
	
	private int bookId;
	private String title;
	private String subject;
	private String publisher;
	private String author;
	private BookStatus status;

}
