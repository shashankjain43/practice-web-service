package com.shashank.noon.service;

import com.shashank.noon.exception.LibraryServiceException;

public interface IFineService {
	
	public boolean payFine(int userId, int bookBorrowInfoId, int amount) throws LibraryServiceException;
	
	public int calcFine(int userId, int bookBorrowInfoId) throws LibraryServiceException;

}
