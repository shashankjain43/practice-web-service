package com.snapdeal.ums.dao.users.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.AdminUserRoleAudit;
import com.snapdeal.ums.dao.users.IAdminUserRoleAuditDao;

@Repository("adminUserRoleAuditDao")
@SuppressWarnings("unchecked")
public class AdminUserRoleAuditDao implements IAdminUserRoleAuditDao {

	private SessionFactory sessionFactory;

	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}

	@Override
	public void persistAdminUserRoleAudit(AdminUserRoleAudit adminUserRoleAudit) {
		sessionFactory.getCurrentSession().persist(adminUserRoleAudit);
	}

}
