package com.snapdeal.ims.service.impl;

import java.util.HashSet;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.ims.cache.BlacklistCache;
import com.snapdeal.ims.cache.CacheManager;
import com.snapdeal.ims.cache.ICache;
import com.snapdeal.ims.cache.service.IPasswordUpgradeCacheService;
import com.snapdeal.ims.dao.IBlackWhiteEntityDao;
import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.ims.enums.EntityType;
import com.snapdeal.ims.errorcodes.IMSMigrationExceptionCodes;
import com.snapdeal.ims.exception.IMSServiceException;
import com.snapdeal.ims.objectMapper.IMSServiceObjectsMapper;
import com.snapdeal.ims.request.BlacklistEntityRequest;
import com.snapdeal.ims.request.WhitelistEmailRequest;
import com.snapdeal.ims.response.BlacklistEntityResponse;
import com.snapdeal.ims.response.WhitelistEmailResponse;
import com.snapdeal.ims.service.IBlackWhiteListService;
import com.snapdeal.ims.service.provider.RandomUpgradeChoiceUtil;
import com.snapdeal.ims.service.provider.UmsServiceProvider;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Service
public class BlackWhiteListImpl implements IBlackWhiteListService {

	@Autowired
	private IPasswordUpgradeCacheService passwordCacheService;

	@Autowired
	private IBlackWhiteEntityDao blackListDao;

	@Autowired
	private RandomUpgradeChoiceUtil randomUpgradeChoiceUtil;
	

	@Autowired
	UmsServiceProvider serviceProvider;

	
	@Override
   @Timed
   @Marked
	public WhitelistEmailResponse WhitelistEmail(WhitelistEmailRequest request) {
		passwordCacheService
				.setIsUpgradeinitialized(request.getEmailId(), true);
		WhitelistEmailResponse response = new WhitelistEmailResponse();
      response.setSuccess(true);
		return response;
	}

	
	@Override
   @Timed
   @Marked
	public BlacklistEntityResponse addBlacklistEntity(
			BlacklistEntityRequest request) {

		BlackList blackList = IMSServiceObjectsMapper
				.mapBlacklistEntityRequestToBlackList(request);
		if (request.getBlackListType() == EntityType.EMAIL
				&& serviceProvider.isIntermediateState(request.getEntity())) {
			throw new IMSServiceException(
					IMSMigrationExceptionCodes.USER_INTERMEDIATE_STATE
							.errCode(),
					IMSMigrationExceptionCodes.USER_INTERMEDIATE_STATE.errMsg());
		}

		if (!randomUpgradeChoiceUtil.isBlackListedUser(blackList.getEntity())) {
			blackListDao.create(blackList);
			// add to the blacklist cache
			ICache<EntityType, Set<String>> blacklistCache = CacheManager
					.getInstance().getCache(BlacklistCache.class);
			Set<String> entities = blacklistCache.get(request
					.getBlackListType());
			if (entities == null) {
				entities = new HashSet<String>();
			}
			entities.add(request.getEntity());
			blacklistCache.put(request.getBlackListType(), entities);
			// Finally remove the isupgrade shown for blacklisted user
			if (request.getBlackListType() == EntityType.EMAIL) {
				passwordCacheService.setIsUpgradeinitialized(
						request.getEntity(), false);
			}
		}
		BlacklistEntityResponse response = new BlacklistEntityResponse();
		response.setSuccess(true);
		return response;
	}
	
	
	@Override
   @Timed
   @Marked
	public BlacklistEntityResponse removeBlacklistEntity(
			BlacklistEntityRequest request) {
		BlackList blackList = IMSServiceObjectsMapper
				.mapBlacklistEntityRequestToBlackList(request);
		//remove from cache
		BlacklistCache blacklistCache = CacheManager
				.getInstance().getCache(BlacklistCache.class);
		blacklistCache.remove(blackList);
		//remove from database
		blackListDao.remove(blackList);
		
		BlacklistEntityResponse response = new BlacklistEntityResponse();
		response.setSuccess(true);
		return response;
	}

}
