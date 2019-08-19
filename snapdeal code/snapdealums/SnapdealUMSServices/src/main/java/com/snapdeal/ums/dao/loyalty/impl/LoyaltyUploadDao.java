package com.snapdeal.ums.dao.loyalty.impl;

import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.snapdeal.ums.core.entity.LoyaltyUploadDO;
import com.snapdeal.ums.dao.loyalty.ILoyaltyUploadDao;
import com.snapdeal.ums.loyalty.LoyaltyConstants.LoyaltyUploadedFileStatus;

@Repository
public class LoyaltyUploadDao implements ILoyaltyUploadDao
{

    @Autowired
    private SessionFactory sessionFactory;

    @Override
    public LoyaltyUploadDO save(LoyaltyUploadDO loyaltyUploadDO)
    {

        sessionFactory.getCurrentSession().save(loyaltyUploadDO);
        return loyaltyUploadDO;
    }

    @Override
    public LoyaltyUploadDO updateStatus(int id, LoyaltyUploadedFileStatus status)
    {

        LoyaltyUploadDO loyaltyUploadDO = (LoyaltyUploadDO) sessionFactory.getCurrentSession().get(
            LoyaltyUploadDO.class, id);
        if (loyaltyUploadDO != null) {
            loyaltyUploadDO.setStatus(status);
        }
        ;
        return loyaltyUploadDO;
    }
}
