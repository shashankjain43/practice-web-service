package com.service;

import com.exception.ServiceException;
import com.model.Book;

public interface IBorrowService {
	
	public Book borrowBook(int bookId, int userId, int days) throws ServiceException;
	
	public boolean returnBook(int bookId, int userId) throws ServiceException;

}
