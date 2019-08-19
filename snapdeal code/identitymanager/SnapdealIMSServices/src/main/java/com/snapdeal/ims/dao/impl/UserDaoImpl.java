package com.snapdeal.ims.dao.impl;

import java.sql.Timestamp;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.aerospike.client.lua.LuaAerospikeLib.log;
import com.snapdeal.ims.dao.IUserDao;
import com.snapdeal.ims.dbmapper.IUserArchiveMapper;
import com.snapdeal.ims.dbmapper.IUserHistoryMapper;
import com.snapdeal.ims.dbmapper.IUserMapper;
import com.snapdeal.ims.dbmapper.entity.User;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.enums.CreateWalletStatus;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.token.service.IIDGenerator;
import com.snapdeal.ims.utility.EmailUtils;
import com.snapdeal.payments.healthcheck.Pingable;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository("UserDaoImpl")
public class UserDaoImpl implements IUserDao, Pingable {

	@Autowired
	private IUserMapper userIMSMapper;

	@Autowired
	private IUserArchiveMapper userArchiveIMSMapper;

	@Autowired
	private IUserHistoryMapper userUpdateIMSMapper;
	@Autowired
	private IIDGenerator idGenerator;

	@Override
	@Transactional("transactionManager")
	@Timed
	@Marked
	public void create(User user) {
		user.setUserId(idGenerator.generateUserId());
      user.setEnabled(true);
      user.setCreatedTime(new Timestamp(new Date().getTime()));
      user.setEmailId(EmailUtils.toLowerCaseEmail(user.getEmailId()));
		userIMSMapper.create(user);
	}

	@Override
	@Timed
	@Marked
	public User getUserById(String userId) {
		User user = userIMSMapper.getUserById(userId);
		return user;
	}

	@Override
	@Timed
	@Marked
	public User getUserBySdId(Integer sdUserId) {
		User user = userIMSMapper.getUserBySdId(sdUserId);
		return user;
	}

	@Override
	@Timed
	@Marked
	public User getUserByFcId(Integer fcUserId) {
		User user = userIMSMapper.getUserByFcId(fcUserId);
		return user;
	}

	@Override
	@Timed
	@Marked
	public User getUserBySdFcId(Integer sdFcUserId) {
		User user = userIMSMapper.getUserBySdFcId(sdFcUserId);
		return user;
	}

	@Override
	@Timed
	@Marked
	public User getUserByEmail(String emailId) {
		User user = userIMSMapper.getUserByEmail(emailId);
		return user;
	}

	@Override
	@Timed
	@Marked
	public User getUserByMobileNumber(String mobileNumber) {
		User user = userIMSMapper.getUserByMobileNumber(mobileNumber);
		return user;
	}

	@Override
	@Transactional("transactionManager")
	@Timed
	@Marked
	public void updateById(User user) {
		Integer rowsAffected = userIMSMapper.updateById(user);

		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	@Override
	@Transactional("transactionManager")
	@Timed
	@Marked
	public void updateBySdId(User user) {
		Integer rowsAffected = userIMSMapper.updateBySdId(user);
		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	@Override
	@Transactional("transactionManager")
	@Timed
	@Marked
	public void updateByFcId(User user) {
		Integer rowsAffected = userIMSMapper.updateByFcId(user);

		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	@Override
	@Transactional("transactionManager")
	@Timed
	@Marked
	public void updateBySdFcId(User user) {
		Integer rowsAffected = userIMSMapper.updateBySdFcId(user);

		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	@Override
	@Transactional("transactionManager")
	@Timed
	@Marked
	public void delete(String userId) {
		userIMSMapper.delete(userId);
	}

	@Override
	@Timed
	@Marked
	public void archiveUser(String emailId) {
		Integer rowsAffected = userArchiveIMSMapper.archiveUser(emailId);
		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}

	}

	
	@Override
	@Timed
	@Marked
	public void deleteUpgradedUserStatus(String emailId) {
		Integer rowsAffected = userArchiveIMSMapper.deleteUpgradedUser(emailId);
		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	@Override
	@Timed
	@Marked
	public void deleteUser(String emailId) {
		Integer rowsAffected = userArchiveIMSMapper.deleteUser(emailId);
		if (rowsAffected == 0) {
			log.error("Invalid Request Error occured while deleting user with emailId"+emailId);
		}
	}
	
	@Override
	@Timed
	@Marked
	public void maintainUserHistory(UserHistory user)
	{
		Integer rowsAffected = userUpdateIMSMapper.maintainUserHistory(user);
		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.ERROR_ON_UPDATING_USER_HISTORY.errCode(),
					IMSServiceExceptionCodes.ERROR_ON_UPDATING_USER_HISTORY.errMsg());
		}
	}
	@Override
	@Timed
	@Marked
	public void updateCreateWalletStatus(String userId, CreateWalletStatus newStatus) {
		userIMSMapper.updateCreateWalletStatus(userId, newStatus.name());
		;
	}

	@Override
	@Timed
	@Marked
	public void updateByEmailId(User user) {
		Integer rowsAffected = userIMSMapper.updateByEmailId(user);

		if (rowsAffected == 0) {
			throw new IMSServiceException(IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
					IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());
		}
	}

	
	@Override
	@Timed
	@Marked
	public boolean isHealthy() {
		try {
			userIMSMapper.getDummyResult();
		} catch (Exception e) {
			return false;
		}
		return true;
	}

}
