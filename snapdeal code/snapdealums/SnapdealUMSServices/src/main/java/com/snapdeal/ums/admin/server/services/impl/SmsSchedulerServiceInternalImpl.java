/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 18, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.server.services.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.ums.admin.dao.ISmsSchedulerDao;
import com.snapdeal.ums.admin.server.services.ISmsSchedulerServiceInternal;
import com.snapdeal.ums.core.entity.SmsScheduler;

@Transactional
@Service("umsSmsSchedulerServiceInternal")
public class SmsSchedulerServiceInternalImpl implements ISmsSchedulerServiceInternal {
    private ISmsSchedulerDao smsSchedulerDao;

    @Autowired
    public void setSmsSchedulerDao(ISmsSchedulerDao smsSchedulerDao) {
        this.smsSchedulerDao = smsSchedulerDao;
    }

    @Override
    public void persist(SmsScheduler smsScheduler) {
        smsSchedulerDao.persist(smsScheduler);
    }

    @Override
    public List<SmsScheduler> getSmsSchedulerList(Date date, String status) {
        return smsSchedulerDao.getSmsSchedulerList(date, status);
    }

    @Override
    public List<SmsScheduler> getSmsScheduler(int cityId, Date date, String status) {
        return smsSchedulerDao.getSmsScheduler(cityId, date, status);
    }

    @Override
    public List<SmsScheduler> getSmsScheduler(List<Integer> cityIds, Date date, String status) {
    	HashSet<SmsScheduler> smsSchedulerHash = new HashSet<SmsScheduler>();
        for (int cityId : cityIds) {
        	smsSchedulerHash.addAll(getSmsScheduler(cityId, date, status));
        }
        return new ArrayList<SmsScheduler>(smsSchedulerHash);
    }
    
    @Override
    public List<SmsScheduler> getSmsSchedulerList(Date date) {
        return smsSchedulerDao.getSmsSchedulerList(date);
    }

    @Override
    public List<SmsScheduler> getSmsScheduler(int cityId, Date date) {
        return smsSchedulerDao.getSmsScheduler(cityId, date);
    }

    @Override
    public List<SmsScheduler> getSmsScheduler(List<Integer> cityIds, Date date) {
    	HashSet<SmsScheduler> smsSchedulerHash = new HashSet<SmsScheduler>();
        for (int cityId : cityIds) {
        	smsSchedulerHash.addAll(getSmsScheduler(cityId, date));
        }
        return new ArrayList<SmsScheduler>(smsSchedulerHash);
    }
    
    @Override
    public SmsScheduler getSmsSchedulerById(int id) {
        return smsSchedulerDao.getSmsSchedulerById(id);
    }

    @Override
    public void update(SmsScheduler smsScheduler) {
        smsSchedulerDao.update(smsScheduler);
    }

}
