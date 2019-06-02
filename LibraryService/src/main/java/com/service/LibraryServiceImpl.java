package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.ServiceException;
import com.model.Book;

@Service
public class LibraryServiceImpl implements ILibraryService {

	@Autowired
	private IBorrowService borrowService;

	@Autowired
	private IFineService fineService;

	@Autowired
	private IUserService userService;

	@Override
	public Book borrowBook(int bookId, int userId, int days) throws ServiceException {

		Book book = borrowService.borrowBook(bookId, userId, days);

		return book;
	}

	@Override
	public boolean returnBook(int bookId, int userId) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean payFine(int userId, int bookBorrowInfoId) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBook(Book book) throws ServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
