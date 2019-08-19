/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Sep 30, 2010
 *  @author Karan
 */
package com.snapdeal.web.controller.forms;

import java.util.ArrayList;
import java.util.List;

import javax.validation.Valid;

import com.snapdeal.ums.core.entity.User;
import com.snapdeal.ums.core.entity.UserRole;

public class UserAdminForm {

    @Valid
    private User user;

    @Valid
    private List<UserRole>     userRoles     = new ArrayList<UserRole>();

   // @Valid
   // private List<UserReferral> userReferrals = new ArrayList<UserReferral>();

    public UserAdminForm() {

    }

    public UserAdminForm(User user) {
        this.user = user;
        this.userRoles.addAll(user.getUserRoles());
        
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
        
    }

    public List<UserRole> getuserRoles() {
        return userRoles;
    }

    public void setUserRoles(List<UserRole> userRoles) {
        this.userRoles = userRoles;
    }

}
