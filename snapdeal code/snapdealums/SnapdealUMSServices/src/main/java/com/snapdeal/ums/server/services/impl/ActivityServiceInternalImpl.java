 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 16-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services.impl;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.core.entity.Activity;
import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.dao.activity.IActivityDao;
import com.snapdeal.ums.dao.users.IUsersDao;
import com.snapdeal.ums.server.services.IActivityServiceInternal;

@Transactional
@Service("umsActivityServiceInternal")
public class ActivityServiceInternalImpl implements IActivityServiceInternal{

    @Autowired
    private IActivityDao activityDao;

    @Autowired
    private IUsersDao userDao;
    /*
     * SNAPDEALTECH-3864 made both as Transactional dao calls.
     */
    public void processActivity(Activity activity) {

      /*  int sdCash = 0;
        Map<String, Object> contextParams = new HashMap<String, Object>();
        contextParams.put("value", activity.getValue());
        sdCash = activity.getActivityType().getExpression().evaluate(contextParams, Integer.class);
        activity.setSdCash(sdCash);
        activityDao.persistActivity(activity);
        userDao.creditSDCash(activity.getUser().getId(), sdCash, true);*/
    }

    @Override
    public List<Activity> getActivityByUserId(int userId) {
        return activityDao.getActivityByUserId(userId);
    }

    @Override
    public List<Activity> getActivityByUserAndActivityType(User user, Integer activity_type_id) {
        return activityDao.getActivityByUserAndActivityType(user, activity_type_id);
    }

    @Override
    public List<Activity> getActivityByAttribute(Activity activity, String attribute) {
        return activityDao.getActivityByAttribute(activity, attribute);
    }

    @Override
    public List<Activity> getSDCashActivities(DateRange range, int firstResult, int maxResults) {
        return activityDao.getSDCashActivities(range, firstResult, maxResults);
    }

    @Override
    public Activity getLastOrderRefundActivityForUserId(Integer userId, Integer activityTypeId) {
        return activityDao.getLastOrderRefundActivityForUserId(userId, activityTypeId);
    }
}

 