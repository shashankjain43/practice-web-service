/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 20-Jul-2012
 *  @author kuldeep
 */
package com.snapdeal.ums.dao.subscriptions;

import java.util.List;

import com.snapdeal.base.utils.DateUtils.DateRange;
import com.snapdeal.ums.core.entity.MobileSubscriber;

public interface IMobileSubscriberDao {
    public List<MobileSubscriber> getMobileSubscribersFromReplica(int cityId, DateRange dateRange, boolean subscribed, boolean dnd, boolean isCustomer, int firstResult, int maxResults);

}
