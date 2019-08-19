package com.snapdeal.ums.dao.loyalty.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.core.entity.LoyaltyUserHistoryDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUserHistoryDao;

@Repository
//@Transactional
public class LoyaltyUserHistoryDao implements ILoyaltyUserHistoryDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public LoyaltyUserHistoryDO saveLoyaltyUserHistory(LoyaltyUserHistoryDO loyaltyUserHistory)
    {

        if (loyaltyUserHistory == null) {
            return null;
        }

        sessionFactory.getCurrentSession().save(loyaltyUserHistory);
        // TODO Auto-generated method stub
        return loyaltyUserHistory;
    }

}
