/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 20, 2011
 *  @author laptop
 */
package com.snapdeal.ums.dao.accesscontrol;

import java.util.List;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.base.utils.DateUtils;
import com.snapdeal.ums.core.entity.AccessControl;
import com.snapdeal.ums.dao.accesscontrol.IAccessControlDao;

@Repository("access_control")
@SuppressWarnings("unchecked")
public class AccessControlDaoImpl implements  IAccessControlDao{    
    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public List<AccessControl> getAllAccessControls() {
        Query query = sessionFactory.getCurrentSession().createQuery("from AccessControl");
        return query.list();
    }

    @Override
    public AccessControl addControl(String pattern, String roles) {
        pattern = pattern.endsWith("/")?pattern.substring(0, pattern.length()-1):pattern;
        
        AccessControl accessControl = null;
        accessControl = getAccessControlByPattern(pattern);
        if(accessControl==null)
        {
            accessControl = new AccessControl();
            accessControl.setPattern(pattern);
        }
        roles.replaceAll(" ", "");
        accessControl.setRoles(roles);
        accessControl.setCreated(DateUtils.getCurrentDate());
        accessControl.setUpdated(null);
        sessionFactory.getCurrentSession().persist(accessControl);
        return accessControl;
    }

    @Override
    public AccessControl getAccessControlByPattern(String pattern) {
        Query query = sessionFactory.getCurrentSession().createQuery("from AccessControl where pattern =:pattern");
        query.setParameter("pattern", pattern);
        List accessControls = query.list();
        AccessControl  accCont = null;
        if(accessControls!=null && accessControls.size()>0)
        {
            accCont = (AccessControl)accessControls.get(0);
        }
        return accCont;
    }

}
