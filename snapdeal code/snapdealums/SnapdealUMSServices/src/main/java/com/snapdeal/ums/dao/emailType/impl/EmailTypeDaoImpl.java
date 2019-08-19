package com.snapdeal.ums.dao.emailType.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.EmailTemplate;
import com.snapdeal.ums.core.entity.EmailTypeDO;
import com.snapdeal.ums.core.entity.SDCashFileUploadHistoryDO;
import com.snapdeal.ums.dao.emailType.IEmailTypeDao;

@Repository
public class EmailTypeDaoImpl implements IEmailTypeDao {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}


	/**
	 * Dao service to get EmailType Object by passing emailType name
	 * @param emailType name
	 */
	@Override
	public EmailTypeDO getEmailType(String emailType) {
		 Query query = sessionFactory.getCurrentSession().createQuery("from EmailTypeDO where typeName =:emailType");
		 query.setParameter("emailType", emailType);
		return (EmailTypeDO) query.uniqueResult();
	}
/**
 * Dao service to get EmailType Object by passing emailType ID
 * @param emailType Id
 */
	@Override
	public EmailTypeDO getEmailType(Integer emailTypeId) {
        Query query=sessionFactory.getCurrentSession().createQuery("from EmailTypeDO where id=:id");
        query.setParameter("id",emailTypeId);
        return (EmailTypeDO) query.uniqueResult();

	}
	
	@Override
	public EmailTypeDO save(
			EmailTypeDO emailTypeDO) {
		   sessionFactory.getCurrentSession().save(emailTypeDO);
	        return emailTypeDO;
		
}
}
