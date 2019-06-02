package com.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.exception.LibraryServiceException;
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
	public Book borrowBook(int bookId, int userId, int days) throws LibraryServiceException {

		Book book = borrowService.borrowBook(bookId, userId, days);

		return book;
	}

	@Override
	public boolean returnBook(int bookId, int userId) throws LibraryServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean payFine(int userId, int bookBorrowInfoId) throws LibraryServiceException {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean addBook(Book book) throws LibraryServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
