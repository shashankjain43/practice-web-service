package com.request;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class BorrowRequest extends BaseRequest {
	
	private static final long serialVersionUID = 1L;
	
	int bookId;
	int userId;
	int days;

}
