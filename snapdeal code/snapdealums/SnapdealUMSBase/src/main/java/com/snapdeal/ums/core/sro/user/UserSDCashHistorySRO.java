/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class UserSDCashHistorySRO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -4052050515998014720L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Date              created;
    @Tag(3)
    private Integer           userId;
    @Tag(4)
    private String            activityType;
    @Tag(5)
    private String            activityId;                               ;
    @Tag(6)
    private int               sdCashChange;

    public UserSDCashHistorySRO() {
       
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getActivityType() {
        return activityType;
    }

    public void setActivityType(String activityType) {
        this.activityType = activityType;
    }

    public String getActivityId() {
        return activityId;
    }

    public void setActivityId(String activityId) {
        this.activityId = activityId;
    }

    public int getSdCashChange() {
        return sdCashChange;
    }

    public void setSdCashChange(int sdCashChange) {
        this.sdCashChange = sdCashChange;
    }

}
