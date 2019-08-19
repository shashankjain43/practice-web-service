package com.snapdeal.ums.dao.loyalty.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.core.entity.LoyaltyProgramStatusDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyProgramStatusDao;

@Repository
//@Transactional
public class LoyaltyProgramStatusDao implements ILoyaltyProgramStatusDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public LoyaltyProgramStatusDO getLoyaltyStatus(int id)
    {

        Query query = sessionFactory.getCurrentSession().createQuery("from LoyaltyProgramStatusDO where id=:id");
        query.setParameter("id", id);
        return (LoyaltyProgramStatusDO) query.uniqueResult();

    }

    @Override
    public LoyaltyProgramStatusDO getLoyaltyStatus(String name)
    {

        Query query = sessionFactory.getCurrentSession().createQuery("from LoyaltyProgramStatusDO where name=:name");
        query.setParameter("name", name);
        return (LoyaltyProgramStatusDO) query.uniqueResult();
    }

}
