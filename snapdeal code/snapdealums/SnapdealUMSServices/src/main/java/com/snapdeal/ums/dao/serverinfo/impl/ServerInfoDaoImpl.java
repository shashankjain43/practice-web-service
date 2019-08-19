/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Feb 10, 2011
 *  @author rahul
 */
package com.snapdeal.ums.dao.serverinfo.impl;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.ServerInfo;
import com.snapdeal.ums.dao.serverinfo.IServerInfoDao;

@Repository("umsServerInfoDao")
public class ServerInfoDaoImpl implements IServerInfoDao {

    @Autowired
    private SessionFactory sessionFactory;

    public void setSessionFactory(SessionFactory umsSessionFactory) {
        this.sessionFactory = umsSessionFactory;
    }
  
    @Override
    public void updateServer(ServerInfo server) {
        sessionFactory.getCurrentSession().saveOrUpdate(server);

    }
    
	@Override
    public ServerInfo getServersByNameAndAddress(String name, String address) {
		Query q = sessionFactory.getCurrentSession().createQuery("from ServerInfo where name=:name and address=:address");
		q.setParameter("name", name);
        q.setParameter("address", address);
        return (ServerInfo)q.uniqueResult();
    }
	
	@SuppressWarnings("unchecked")
	@Override
    public List<ServerInfo> getAllServers() {
        return sessionFactory.getCurrentSession().createQuery("from ServerInfo order by started desc").list();
    }


	@Override
	public void updateReloadRequired(List<String> nodes, String caches) {
		Query q = sessionFactory.getCurrentSession().createQuery("update from ServerInfo set reloadRequired=true,reloadRequiredFor=:caches where name in (:names)");
		q.setParameter("caches", caches);
		q.setParameterList("names", nodes);
		q.executeUpdate();		
	}

	@Override
	public void updateReloadRequired(String node, String caches) {
		Query q = sessionFactory.getCurrentSession().createQuery("update from ServerInfo set reloadRequired=true,reloadRequiredFor=:caches where name=:name");
		q.setParameter("caches", caches);
		q.setParameter("name", node);
		q.executeUpdate();		
	}

}
