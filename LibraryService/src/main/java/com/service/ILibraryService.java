package com.service;

import com.exception.ServiceException;
import com.model.Book;

public interface ILibraryService {
	
	public Book borrowBook(int bookId, int userId, int days) throws ServiceException;
	
	public boolean returnBook(int bookId, int userId) throws ServiceException;
	
	public boolean payFine(int userId, int bookBorrowInfoId) throws ServiceException;
	
	public boolean addBook(Book book) throws ServiceException;

}
