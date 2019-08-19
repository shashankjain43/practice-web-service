package com.snapdeal.ums.dao.auditing;

import java.util.List;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.AuditingDO;

@Repository
public class AuditingDao implements IAuditingDao {

	@Autowired
	private SessionFactory sessionFactory;

	public void save(List<AuditingDO> auditingDOs)throws HibernateException{

		Session session = sessionFactory.getCurrentSession();
		for(AuditingDO auditingDO: auditingDOs){
			session.save(auditingDO);
		}
	}

}
