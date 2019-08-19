package com.snapdeal.ims.dao.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ims.dao.IBlackWhiteEntityDao;
import com.snapdeal.ims.dbmapper.IBlackListMapper;
import com.snapdeal.ims.dbmapper.entity.BlackList;
import com.snapdeal.payments.metrics.annotations.Marked;
import com.snapdeal.payments.metrics.annotations.Timed;

@Repository("IBlackWhiteEntityDaoImpl")
public class IBlackWhiteEntityDaoImpl implements IBlackWhiteEntityDao {

	@Autowired
	private IBlackListMapper blackListMapper;

	@Override
	@Transactional("transactionManager")
   @Timed
   @Marked
	public void create(BlackList blackList) {

		blackListMapper.create(blackList);
	}

	@Override
   @Timed
   @Marked
	public List<BlackList> getAllEntities() {

		return blackListMapper.getAllEntities();
	}

	@Override
	@Transactional("transactionManager")
   @Timed
   @Marked
	public void remove(BlackList blackList) {
		blackListMapper.remove(blackList);
	}

}
