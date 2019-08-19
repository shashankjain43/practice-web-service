 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 16-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.server.services;

import java.util.List;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.core.entity.Activity;
import com.snapdeal.ums.core.entity.User;

public interface IActivityServiceInternal {


    public void processActivity(Activity activity);

    public List<Activity> getActivityByUserId(int userId);

    public List<Activity> getActivityByUserAndActivityType(User user, Integer activity_type_id);

    public List<Activity> getActivityByAttribute(Activity activity, String attribute);

    List<Activity> getSDCashActivities(DateRange range, int firstResult, int maxResults);

    Activity getLastOrderRefundActivityForUserId(Integer userId, Integer activityTypeId);

}

 