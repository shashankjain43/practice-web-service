package com.snapdeal.ums.dao.loyalty.impl;

import org.hibernate.Query;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.AbstractApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.LoyaltyProgramDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyProgramDao;

@Repository
//@Transactional
public class LoyaltyProgramDao implements ILoyaltyProgramDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public LoyaltyProgramDO getLoyaltyProgram(int id)
    {

        Query query = sessionFactory.getCurrentSession().createQuery("from LoyaltyProgramDO where id=:id");
        query.setParameter("id", id);
        return (LoyaltyProgramDO) query.uniqueResult();
    }

    @Override
    public LoyaltyProgramDO getLoyaltyProgram(String name)
    {

        Query query = sessionFactory.getCurrentSession().createQuery("from LoyaltyProgramDO where name=:name");
        query.setParameter("name", name);
        return (LoyaltyProgramDO) query.uniqueResult();
    }

    public static void main(String[] args)
    {

        AbstractApplicationContext context = new ClassPathXmlApplicationContext(
            new String[] { "applicationContext.xml" });

        // User u = context.getBean(User.class);

        ILoyaltyProgramDao dao = context.getBean(ILoyaltyProgramDao.class);
        LoyaltyProgramDO lp1 = dao.getLoyaltyProgram(1);
        LoyaltyProgramDO lp2 = dao.getLoyaltyProgram("SNAPBOX");

        System.out.println(dao);
        System.out.println(lp1);
    }

}
