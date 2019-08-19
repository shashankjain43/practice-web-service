/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 16, 2010
 *  @author singla
 */
package com.snapdeal.web.security;

import java.util.ArrayList;
import java.util.List;

import org.springframework.security.core.GrantedAuthority;

import com.snapdeal.ums.core.sro.user.UserRoleSRO;


public class SnapdealAuthority implements GrantedAuthority {

    /**
     * 
     */
    private static final long serialVersionUID = 68620155670337310L;
    private String            role;
    private List<Integer>     applicableZones  = new ArrayList<Integer>();

    public SnapdealAuthority(UserRoleSRO userRole) {
        this.role = userRole.getRole();
    }

    @Override
    public String getAuthority() {
        return role;
    }

    public void addZoneMapping(int zoneId) {
        this.applicableZones.add(zoneId);
    }

    public List<Integer> getApplicableZones() {
        return applicableZones;
    }
}
