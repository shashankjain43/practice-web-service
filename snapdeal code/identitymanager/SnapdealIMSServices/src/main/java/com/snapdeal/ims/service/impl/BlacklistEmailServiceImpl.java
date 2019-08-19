package com.snapdeal.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.dashboard.dbmapper.IBlacklistDao;
import com.snapdeal.ims.service.IBlacklistEmailService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
public class BlacklistEmailServiceImpl implements IBlacklistEmailService{

	@Autowired
	IBlacklistDao blacklistDao;
	
	@Logged
	@Timed
	@Marked
	@Override
	public List<String> getBlacklistEmail() {
		List<String> blacklistUsers = new ArrayList<String>();
		blacklistUsers = blacklistDao.getBlacklist();
		return blacklistUsers;
	}

}
