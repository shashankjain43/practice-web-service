package com.snapdeal.ims.otp.dao.impl;

import java.util.List;

import org.mybatis.spring.SqlSessionTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.google.common.base.Optional;
import com.snapdeal.ims.otp.dao.BlockedUserDao;
import com.snapdeal.ims.otp.dao.mapper.BlockedUserDaoMapper;
import com.snapdeal.ims.otp.entity.FreezeAccountEntity;
import com.snapdeal.ims.otp.internal.request.DropUserFromFreezeRequest;
import com.snapdeal.ims.otp.internal.request.GetFrozenAccount;

/**
 * 
 * @author shagun
 *
 */
@Repository("BlockedUserDaoImpl")
public class BlockedUserDaoImpl implements BlockedUserDao {

	@Autowired
   @Qualifier("myBatisIMSDB")
	private SqlSessionTemplate sqlSession;

	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW)
	public void freezeUser(FreezeAccountEntity freezeAccount) {

		sqlSession.insert(BlockedUserDaoMapper.FREEZE_ACCOUNT.toString(),
				freezeAccount);
	}

	@Override
	@Transactional( propagation = Propagation.REQUIRES_NEW)
	public void updateFreezeUser(FreezeAccountEntity freezeAccount) {

		sqlSession.update(
				BlockedUserDaoMapper.UPDATE_FREEZE_ACCOUNT.toString(),
				freezeAccount);
	}

	@Override
	public Optional<FreezeAccountEntity> getFreezedAccount(
			GetFrozenAccount getFrozenAccount) {
		// tested
		List<FreezeAccountEntity> frozenAccountInfo = sqlSession.selectList(
				BlockedUserDaoMapper.GET_FROZEN_ACCOUNT.toString(),
				getFrozenAccount);
		if (frozenAccountInfo == null || frozenAccountInfo.isEmpty()) {
			return Optional.<FreezeAccountEntity> absent();
		}
		return Optional.<FreezeAccountEntity> of(frozenAccountInfo.get(0));
	}

	@Override
	@Transactional
	public boolean dropFreezedUser(
			DropUserFromFreezeRequest dropUserFromFreezeRequest) {

		int row = sqlSession.update(BlockedUserDaoMapper.DROP_USER.toString(),
				dropUserFromFreezeRequest);
		if (row == 0) {
			return false;
		}
		return true;
	}

}
