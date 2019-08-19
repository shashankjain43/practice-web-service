/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 13-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class UserInformationSRO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = -7728373938078670976L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Integer           userId;
    @Tag(3)
    private String            name;
    @Tag(4)
    private String            value;

    public UserInformationSRO() {
        
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

    public void setUserId(Integer user) {
        this.userId = user;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

}
