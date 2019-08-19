package com.snapdeal.ums.dao;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.Role;


@Repository("umsRoleDao")
public class UserRoleDaoImpl implements IUserRoleDao {

    private SessionFactory sessionFactory;

    @Autowired
    public void setSessionFactory(SessionFactory sessionFactory) {
        this.sessionFactory = sessionFactory;
    }

    @Override
    public Role getRoleByCode(String code) {
        Query q = sessionFactory.getCurrentSession().createQuery("from Role where code = :code");
        q.setParameter("code", code);
        return (Role) q.uniqueResult();
    }

    @Override
    public Role addRole(String code, String description) {
        Role r = new Role(code,description);
        sessionFactory.getCurrentSession().persist(r);
        return null;
    }

}

