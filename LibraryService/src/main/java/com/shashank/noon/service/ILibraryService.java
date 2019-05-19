package com.shashank.noon.service;

import com.shashank.noon.exception.LibraryServiceException;
import com.shashank.noon.model.Book;

public interface ILibraryService {
	
	public Book borrowBook(int bookId, int userId, int days) throws LibraryServiceException;
	
	public boolean returnBook(int bookId, int userId) throws LibraryServiceException;
	
	public boolean payFine(int userId, int bookBorrowInfoId) throws LibraryServiceException;
	
	public boolean addBook(Book book) throws LibraryServiceException;

}
