/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 16-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.core.sro.activity;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.core.sro.activity.ActivityTypeSRO;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class ActivitySRO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 5800288143138304042L;

    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Integer           userId;
    @Tag(3)
    private ActivityTypeSRO   activityType;
    @Tag(4)
    private String            ipAddress;
    @Tag(5)
    private String            attributes;
    @Tag(6)
    private int               sdCash;
    @Tag(7)
    private Date              created;
    @Tag(8)
    private Integer           value;

    public ActivitySRO() {
    
    }

    public Integer getValue() {
        return value;
    }

    public void setValue(Integer value) {
        this.value = value;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public ActivityTypeSRO getActivityType() {
        return activityType;
    }

    public void setActivityType(ActivityTypeSRO activityType) {
        this.activityType = activityType;
    }

    public String getIpAddress() {
        return ipAddress;
    }

    public void setIpAddress(String ipAddress) {
        this.ipAddress = ipAddress;
    }

    public String getAttributes() {
        return attributes;
    }

    public void setAttributes(String attributes) {
        this.attributes = attributes;
    }

    public int getSdCash() {
        return sdCash;
    }

    public void setSdCash(int sdCash) {
        this.sdCash = sdCash;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Override
    public String toString() {
        return "ActivitySRO [id=" + id + ", user=" + userId + ", activityType=" + activityType + ", ipAddress=" + ipAddress + ", attributes=" + attributes + ", sdCash=" + sdCash
                + ", created=" + created + ", value=" + value + "]";
    }

}
