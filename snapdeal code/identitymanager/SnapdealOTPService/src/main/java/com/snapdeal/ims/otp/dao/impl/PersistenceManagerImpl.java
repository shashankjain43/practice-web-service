package com.snapdeal.ims.otp.dao.impl;

import lombok.experimental.Delegate;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;

import com.snapdeal.ims.otp.dao.BlockedUserDao;
import com.snapdeal.ims.otp.dao.OTPInfoDao;
import com.snapdeal.ims.otp.dao.PersistenceManager;

@Repository("PersistenceManagerImpl")
public class PersistenceManagerImpl implements PersistenceManager {

	@Autowired
	@Delegate
	@Qualifier("OTPInfoDaoImpl")
	private OTPInfoDao otpInfoDao;

	@Autowired
	@Delegate
	@Qualifier("BlockedUserDaoImpl")
	private BlockedUserDao blockedUserDao;
}
