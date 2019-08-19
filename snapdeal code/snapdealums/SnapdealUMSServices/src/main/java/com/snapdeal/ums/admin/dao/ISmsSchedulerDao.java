/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 18, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.admin.dao;

import java.util.Date;
import java.util.List;

import com.snapdeal.ums.core.entity.SmsScheduler;


public interface ISmsSchedulerDao {
    public void persist(SmsScheduler smsScheduler);
    
    public SmsScheduler getSmsSchedulerById(int id);
    
    public List<SmsScheduler> getSmsSchedulerList(Date date, String status);
    
    public List<SmsScheduler> getSmsScheduler(int cityId, Date date, String status);
    
    public List<SmsScheduler> getSmsSchedulerList(Date date);
    
    public List<SmsScheduler> getSmsScheduler(int cityId, Date date);
    
    public void update(SmsScheduler smsScheduler);
}
