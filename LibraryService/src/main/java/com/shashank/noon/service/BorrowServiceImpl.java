package com.shashank.noon.service;

import java.util.Date;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.shashank.noon.AppConstant.AppConstant;
import com.shashank.noon.dao.IBookDao;
import com.shashank.noon.exception.LibraryServiceException;
import com.shashank.noon.model.Book;
import com.shashank.noon.model.BookBorrowInfo;
import com.shashank.noon.model.User;

@Service
public class BorrowServiceImpl implements IBorrowService {

	@Autowired
	private IUserService userService;

	@Autowired
	private IBookDao bookDao;

	Map<Integer, Book> availableBooks;

	Map<Integer, Book> borrowedBooks;

	@Override
	public Book borrowBook(int bookId, int userId, int days) throws LibraryServiceException {

		User user = userService.getUserById(userId);
		if (user.getBooksBorrowed() >= AppConstant.MAX_BOOKS_ALLOWED_PER_USER) {
			throw new LibraryServiceException("ER-101", "MAx book borrowing limit reched!");
		}
		if (user.getCurrentBorrowedBook() != null) {
			throw new LibraryServiceException("ER-102", "Only one book per unit time is allowed!");
		}
		if (days > 7) {
			throw new LibraryServiceException("ER-103", "A book can be borrowed for max 7 days!");
		}

		if (user.getTotalPendingFine() >= AppConstant.MAX_PENDING_FINE_PER_USER) {
			throw new LibraryServiceException("ER-104", "Please pay your pending fine first!");
		}

		if (availableBooks.containsKey(bookId)) {
			BookBorrowInfo borrowInfo = new BookBorrowInfo();
			borrowInfo.setUserId(userId);
			borrowInfo.setCreateDate(new Date());
			borrowInfo.setDueDate(new Date(System.currentTimeMillis() + 7 * 24 * 60 * 60 * 1000));
			Book book = availableBooks.remove(bookId);
			borrowedBooks.put(bookId, book);

			user.setBooksBorrowed(user.getBooksBorrowed() + 1);
			user.setCurrentBorrowedBook(book);

			userService.updateUserInfo(user);

			return book;
		} else {
			throw new LibraryServiceException("ER-105", "Requested book is not availabe!");
		}
	}

	@Override
	public boolean returnBook(int bookId, int userId) throws LibraryServiceException {
		// TODO Auto-generated method stub
		return false;
	}

}
