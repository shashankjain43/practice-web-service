package com.shashank.noon.service;

import com.shashank.noon.exception.LibraryServiceException;
import com.shashank.noon.model.User;

public interface IUserService {
	
	public User getUserById(int userId) throws LibraryServiceException;

	public void updateTotalBorrowed(int i);

	public void updateUserInfo(User user);

}
