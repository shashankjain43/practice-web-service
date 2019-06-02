package com.service;

import com.exception.LibraryServiceException;
import com.model.Book;

public interface IBorrowService {
	
	public Book borrowBook(int bookId, int userId, int days) throws LibraryServiceException;
	
	public boolean returnBook(int bookId, int userId) throws LibraryServiceException;

}
