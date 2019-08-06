package com.service;

import com.exception.ServiceException;

public interface IFineService {
	
	public boolean payFine(int userId, int bookBorrowInfoId, int amount) throws ServiceException;
	
	public int calcFine(int userId, int bookBorrowInfoId) throws ServiceException;

}
