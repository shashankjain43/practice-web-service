package com.shashank.noon.controller;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import com.shashank.noon.model.Book;
import com.shashank.noon.model.BorrowRequest;
import com.shashank.noon.model.BorrowResponse;
import com.shashank.noon.service.ILibraryService;

@RestController
@RequestMapping("/service/library")
public class ILibraryController {
	
	@Autowired private ILibraryService libraryService;

	@RequestMapping(value = "/borrow", produces = "application/JSON", method = RequestMethod.POST)
	public BorrowResponse borrowBook(
			@RequestBody BorrowRequest request,
			HttpServletRequest httpServletRequest) {
		
		Book book = libraryService.borrowBook(request.getBookId(), request.getUserId(), request.getDays());
		BorrowResponse response = new BorrowResponse();
		response.setBook(book);
		return response;
	}

}
