package com.controller;

import com.model.Book;
import com.request.BorrowRequest;
import com.response.BorrowResponse;
import com.response.ServiceResponse;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/service/lb")
public class LBController {

	@RequestMapping(value = "/borrow", produces = "application/JSON", method = RequestMethod.POST)
	public ServiceResponse<BorrowResponse> borrowBook(@RequestBody BorrowRequest request) {

		Book book = null;//libraryService.borrowBook(request.getBookId(), request.getUserId(), request.getDays());
		BorrowResponse res = new BorrowResponse();
		res.setBook(book);
		ServiceResponse<BorrowResponse> response = new ServiceResponse<BorrowResponse>();
		response.setResponse(res);
		return response;
	}
}
