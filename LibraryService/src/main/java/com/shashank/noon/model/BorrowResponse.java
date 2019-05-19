package com.shashank.noon.model;

public class BorrowResponse extends ServiceResponse{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	
	Book book;

	public BorrowResponse(String errCode, String errMsg) {
		super(errCode, errMsg);
		// TODO Auto-generated constructor stub
	}
	
	public BorrowResponse() {
		// TODO Auto-generated constructor stub
	}

	public Book getBook() {
		return book;
	}

	public void setBook(Book book) {
		this.book = book;
	}

	

}
