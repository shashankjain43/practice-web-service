package com.snapdeal.ims.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.constants.UserEnabledStatus;
import com.snapdeal.ims.dashboard.dbmapper.IUserHistoryDetailsMapper;
import com.snapdeal.ims.dashboard.dbmapper.IUserSearchDao;
import com.snapdeal.ims.entity.UserEntity;
import com.snapdeal.ims.entity.UserHistory;
import com.snapdeal.ims.entity.UserSearchEnteredEntity;
import com.snapdeal.ims.enums.UpdatedFeild;
import com.snapdeal.ims.enums.Upgrade;
import com.snapdeal.ims.errorcodes.IMSServiceExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.request.GetUserHistoryDetailsRequest;
import com.snapdeal.ims.response.GetUserHistoryDetailsResponse;
import com.snapdeal.ims.service.IUserSearchService;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

import lombok.Getter;
import lombok.Setter;
import net.logstash.logback.encoder.org.apache.commons.lang.StringUtils;

@Service
@Getter
@Setter
public class UserSearchServiceImpl implements IUserSearchService {

	@Autowired
	IUserSearchDao userSearchDao;

	@Autowired
	private IUserHistoryDetailsMapper userUpdateIMSMapper;

	@Logged
	@Timed
	@Marked
	@Override
	public List<UserEntity> getUserByBasicSearch(UserSearchEnteredEntity userEnteredValue){
		List<UserEntity> users= userSearchDao.getUserSearch(userEnteredValue);
		for(UserEntity u : users){
			String upgrade_status = userSearchDao.getUpgradeStatus(u.getEmail());
			if(upgrade_status!=null){
				u.setMigration_status(Upgrade.valueOf(upgrade_status));
			}
		}
		if(users == null || users.isEmpty()){
			users = getArchivedUser(userEnteredValue);
			if(users ==   null || users.isEmpty()){
				throw new IMSServiceException(
						IMSServiceExceptionCodes.USER_NOT_EXIST.errCode(),
						IMSServiceExceptionCodes.USER_NOT_EXIST.errMsg());			
			}else{
				for(UserEntity u:users){
					u.setUserEnabledStatus(UserEnabledStatus.CLOSED);
					String upgrade_status = userSearchDao.getUpgradeStatus(u.getEmail());
					if(upgrade_status!=null){
						u.setMigration_status(Upgrade.valueOf(upgrade_status));
					}
				}
			}
		}else{
			for(UserEntity u:users){
				String migrationStatus = userSearchDao.getUpgradeStatus(u.getEmail());
				if(migrationStatus!=null){
					u.setMigration_status(Upgrade.valueOf(migrationStatus));
				}
				if(u.getIs_enabled()==1){
					u.setUserEnabledStatus(UserEnabledStatus.ENABLED);
				}else{
					u.setUserEnabledStatus(UserEnabledStatus.DISABLED);
				}
			}
			List<UserEntity> archivedUsers = new ArrayList<UserEntity>();
			archivedUsers= getArchivedUser(userEnteredValue);
			if(archivedUsers !=null && !archivedUsers.isEmpty()){
				for(UserEntity u:archivedUsers){
					String upgrade_status = userSearchDao.getUpgradeStatus(u.getEmail());
					if(upgrade_status!=null){
						u.setMigration_status(Upgrade.valueOf(upgrade_status));
					}
					u.setUserEnabledStatus(UserEnabledStatus.CLOSED);
				}
			}
			users.addAll(archivedUsers);	
		}
		
		return users;
	}

	@Logged
	@Timed
	@Marked
	@Override
	public List<UserEntity> getArchivedUser(
			UserSearchEnteredEntity userEnteredValue) {
		List<UserEntity> userList = userSearchDao
				.getUserArchivedSearch(userEnteredValue);
		return userList;
	}
	@Override
	@Logged
	@Timed
	@Marked
	public GetUserHistoryDetailsResponse getUserHistoryDetails(
			GetUserHistoryDetailsRequest request) {
		GetUserHistoryDetailsResponse response = new GetUserHistoryDetailsResponse();
		if (request.getUserId() == null
				|| StringUtils.isBlank(request.getUserId())) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.EMPTY_SEARCH_FIELD.errCode(),
					IMSServiceExceptionCodes.EMPTY_SEARCH_FIELD.errMsg());
		}
		
		List<UserHistory> historyList= userUpdateIMSMapper
				.getUserHistoryDetails(request);
		
		for (int i = 0; i < historyList.size(); i++) {
			UserHistory user = historyList.get(i);
			if(user.getField()==UpdatedFeild.PASSWORD){
				user.setOldValue("*******");
				user.setNewValue("*******");
			}
		}
		if (historyList == null || historyList.size()==0) {
			throw new IMSServiceException(
					IMSServiceExceptionCodes.USER_HISTORY_EMPTY.errCode(),
					IMSServiceExceptionCodes.USER_HISTORY_EMPTY.errMsg());
		}
		response.setUserHistoryDetails(historyList);
		return response;

	}

	}
