package com.service;

import com.exception.ServiceException;
import com.model.User;

public interface IUserService {
	
	public User getUserById(int userId) throws ServiceException;

	public void updateTotalBorrowed(int i);

	public void updateUserInfo(User user);

}
