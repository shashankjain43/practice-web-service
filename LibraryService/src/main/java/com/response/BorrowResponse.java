package com.response;

import com.model.Book;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowResponse extends BaseResponse {

	private static final long serialVersionUID = 1L;

	Book book;

}
