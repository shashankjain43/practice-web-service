package com.service;

import com.exception.LibraryServiceException;
import com.model.User;

public interface IUserService {
	
	public User getUserById(int userId) throws LibraryServiceException;

	public void updateTotalBorrowed(int i);

	public void updateUserInfo(User user);

}
