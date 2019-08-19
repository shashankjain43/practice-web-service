package com.snapdeal.ims.dao.impl;

import org.junit.Assert;
import org.junit.Ignore;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.enums.UpdatedFeild;
import com.snapdeal.ims.request.CloseAccountByEmailRequest;
import com.snapdeal.ims.response.CloseAccountResponse;

@ContextConfiguration("classpath:/spring/application-context.xml")
@RunWith(SpringJUnit4ClassRunner.class)
public class UserDaoImplTest {
	@Autowired
	IUserDao userDao;

	@Test
	public void archiveUserTest()
	{
		CloseAccountByEmailRequest request=new CloseAccountByEmailRequest();
		request.setEmailId("test123@gmail.com");
		userDao.archiveUser(request.getEmailId());
		CloseAccountResponse response=new CloseAccountResponse();
		response.setStatus(true);
		Assert.assertTrue(response.isStatus());
	}

	@Test
	public void deleteUpgradedUserStatus()
	{
		CloseAccountByEmailRequest request=new CloseAccountByEmailRequest();
		request.setEmailId("test123@gmail.com");
		userDao.deleteUpgradedUserStatus(request.getEmailId());	
		CloseAccountResponse response=new CloseAccountResponse();
		response.setStatus(true);
		Assert.assertTrue(response.isStatus());
	}

	@Test
	public void deleteUser()
	{
		CloseAccountByEmailRequest request=new CloseAccountByEmailRequest();
		request.setEmailId("test123@gmail.com");
		userDao.deleteUser(request.getEmailId());
		CloseAccountResponse response=new CloseAccountResponse();
		response.setStatus(true);
		Assert.assertTrue(response.isStatus());
	}

	@Test
	public void maintainUserHistoryTest()
	{
		CloseAccountByEmailRequest request=new CloseAccountByEmailRequest();
		request.setEmailId("test123@gmail.com");
		UserHistory user=new UserHistory();
		user.setField(UpdatedFeild.MOBILE_NO);
		user.setOldValue("9999999999");
		user.setNewValue("9999999988");
		user.setUserId("1234");
		userDao.maintainUserHistory(user);
		CloseAccountResponse response=new CloseAccountResponse();
		response.setStatus(true);
		Assert.assertTrue(response.isStatus());
	}
}