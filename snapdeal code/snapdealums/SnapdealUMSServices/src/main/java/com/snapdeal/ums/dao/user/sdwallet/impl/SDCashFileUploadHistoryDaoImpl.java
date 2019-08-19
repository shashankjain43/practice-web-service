package com.snapdeal.ums.dao.user.sdwallet.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.SDCashFileUploadHistoryDO;
import com.snapdeal.ums.dao.user.sdwallet.ISDCashFileUploadHistoryDAO;
@Repository
public class SDCashFileUploadHistoryDaoImpl implements ISDCashFileUploadHistoryDAO {


	    @Autowired
	    private SessionFactory sessionFactory;

	@Override
	public SDCashFileUploadHistoryDO save(SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO) {
		sessionFactory.getCurrentSession().save(sdCashFileUploadHistoryDO);
		return sdCashFileUploadHistoryDO;

	}
	
	@Override
	public SDCashFileUploadHistoryDO findByFileNameAndHash(String fileName, String hash) {
		Query query = sessionFactory.getCurrentSession().createQuery("from SDCashFileUploadHistoryDO where fileName =:fileName and fileHash =:hash");
		 query.setParameter("fileName", fileName);
		 query.setParameter("hash", hash);
		return (SDCashFileUploadHistoryDO) query.uniqueResult();
	}
	
	@Override
	public List<SDCashFileUploadHistoryDO> getFileUploadHistoryByFileName(String fileName) {
		Query query = sessionFactory.getCurrentSession().createQuery("from SDCashFileUploadHistoryDO where fileName =:fileName");
		 query.setParameter("fileName", fileName);
		return query.list();
	}

}
