/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 14, 2010
 *  @author rahul
 */
package com.snapdeal.ums.cache;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.snapdeal.base.annotations.Cache;
import com.snapdeal.core.entity.ActivityType;

@Cache(name = "umsActivityTypeCache")
public class ActivityTypeCache {

    public enum ActivityTypeCode {
        signup("signup"), subscription("subs"), referral("referconv"), visa("visacard"), giftvoucher("gvoucher"), bdayCashBack("bdayCB"), referralReward("refReward"), generalSDCashService(
                "genSDCServ"), orderRefund("odrRefund"), cashBack("cashBack"), sdCashOnTransaction("sdCashOnTx"), sdCashOnFirstPurchase("sdCashFtPc"), sdCashOnSecondPurchase(
                "sdCashSdPc"), fraudDetection("fraudDebit"), orderTimeOut("orderTMT");

        private String code;

        private ActivityTypeCode(String code) {
            this.code = code;
        }

        public String code() {
            return this.code;
        }

    }

    private Map<Integer, ActivityType> activityTypeMap     = new HashMap<Integer, ActivityType>();
    private Map<String, ActivityType>  activityCodeTypeMap = new HashMap<String, ActivityType>();
    private List<ActivityType>         activityTypes       = new ArrayList<ActivityType>();

    public void addActivityType(ActivityType activityType) {
        activityTypeMap.put(activityType.getId(), activityType);
        activityCodeTypeMap.put(activityType.getCode(), activityType);
        activityTypes.add(activityType);
    }

    public ActivityType getActivityTypeById(Integer id) {
        return activityTypeMap.get(id);
    }

    public ActivityType getActivityTypeByCode(String code) {
        return activityCodeTypeMap.get(code);
    }

    public List<ActivityType> getActivityTypes() {
        return activityTypes;
    }

}
