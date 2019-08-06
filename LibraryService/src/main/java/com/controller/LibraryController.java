package com.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.model.Book;
import com.request.BorrowRequest;
import com.response.BorrowResponse;
import com.response.ServiceResponse;
import com.service.ILibraryService;

@RestController
@RequestMapping("/service/library")
public class LibraryController {

	@Autowired
	private ILibraryService libraryService;

	@RequestMapping(value = "/borrow", produces = "application/JSON", method = RequestMethod.POST)
	public ServiceResponse<BorrowResponse> borrowBook(@RequestBody BorrowRequest request) {

		Book book = libraryService.borrowBook(request.getBookId(), request.getUserId(), request.getDays());
		BorrowResponse res = new BorrowResponse();
		res.setBook(book);
		ServiceResponse<BorrowResponse> response = new ServiceResponse<BorrowResponse>();
		response.setResponse(res);
		return response;
	}
}
