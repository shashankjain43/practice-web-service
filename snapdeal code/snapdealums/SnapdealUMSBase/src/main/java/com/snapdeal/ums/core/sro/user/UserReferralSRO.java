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

public class UserReferralSRO implements Serializable {
    /**
	 * 
	 */
    private static final long serialVersionUID = 3344488347658216901L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private UserSRO           user;
    @Tag(3)
    private String            email;
    @Tag(4)
    private boolean           converted;
    @Tag(5)
    private Date              updated;
    @Tag(6)
    private Date              created;

    public UserReferralSRO() {
        
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public boolean isConverted() {
        return converted;
    }

    public void setConverted(boolean converted) {
        this.converted = converted;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
