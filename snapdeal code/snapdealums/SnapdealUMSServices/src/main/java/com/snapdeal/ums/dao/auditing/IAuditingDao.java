package com.snapdeal.ums.dao.auditing;

import java.util.List;

import org.hibernate.HibernateException;

import com.snapdeal.ums.core.entity.AuditingDO;


public interface IAuditingDao
{
    public void save(List<AuditingDO> auditingDOs) throws HibernateException;

}
