package com.snapdeal.ims.service.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.cache.service.IUserCacheService;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.objectMapper.IMSServiceObjectsMapper;
import com.snapdeal.ims.request.UpgradeUserByEmailRequest;
import com.snapdeal.ims.response.UpgradeUserByEmailResponse;
import com.snapdeal.ims.service.IJobSchedularService;
import com.snapdeal.ims.service.IUMSService;
import com.snapdeal.ims.service.IUserMigrationService;
import com.snapdeal.ims.token.service.IActivityDataService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
public class JobSchedularServiceImpl implements IJobSchedularService {

	@Qualifier("IMSService")
	@Autowired
	private IUMSService imsService;

	@Autowired
	IUserDao userDao;

	@Autowired
	IActivityDataService activityDataService;

	@Autowired
	IUserCacheService userCacheService;

	
	@Qualifier("userMigrationService")
	@Autowired
	private IUserMigrationService userMigrationService;
	
	@Override
	@Timed
	@Marked
	@Logged
	@Transactional(value = "transactionManager", propagation = Propagation.REQUIRED)
	public UpgradeUserByEmailResponse upgradeUser(UpgradeUserByEmailRequest request){
		activityDataService.setActivityDataByEmailId(request.getEmailId());
		User user=imsService.updateUserStatus(request.getEmailId());
		UserDetailsDTO userDetailsDto=IMSServiceObjectsMapper.mapUserToUserDetailsDTO(user);
		UpgradeUserByEmailResponse response = new UpgradeUserByEmailResponse();
		response.setStatus(userMigrationService.upgradeUserStatusViaResetPassword(request.getEmailId(),userDetailsDto));
		return response;

	}
}