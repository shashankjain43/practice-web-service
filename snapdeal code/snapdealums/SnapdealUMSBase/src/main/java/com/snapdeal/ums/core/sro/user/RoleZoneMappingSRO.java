/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Oct-2012
*  @author naveen

package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.core.entity.Zone;

public class RoleZoneMappingSRO implements Serializable {

    *//**
     * 
     *//*
    private static final long serialVersionUID = 2567133578353310649L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Integer           userRoleId;
    @Tag(3)
    private Integer           zoneId;

    public RoleZoneMappingSRO() {
       
    }

    public RoleZoneMappingSRO(UserRoleSRO userRole, Zone zone) {
        this.userRoleId = userRole.getId();
        this.zoneId = zone.getId();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserRoleId() {
        return userRoleId;
    }

    public void setUserRoleId(Integer userRoleId) {
        this.userRoleId = userRoleId;
    }

    public Integer getZone() {
        return zoneId;
    }

    public void setZone(Integer zone) {
        this.zoneId = zone;
    }

}
*/