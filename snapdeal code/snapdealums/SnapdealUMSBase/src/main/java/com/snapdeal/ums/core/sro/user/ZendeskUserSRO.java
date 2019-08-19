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

public class ZendeskUserSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -4343664188694087460L;
    /**
	 * 
	 */
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private UserSRO           user;
    @Tag(3)
    private Date              updated;
    @Tag(4)
    private String            password;
    

    public ZendeskUserSRO() {
       
    }

    public Integer getId() {
        return id;
    }

  
    public void setId(Integer id) {
        this.id = id;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
