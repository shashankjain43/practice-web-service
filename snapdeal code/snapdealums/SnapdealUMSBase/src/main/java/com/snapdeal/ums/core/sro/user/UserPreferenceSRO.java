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

public class UserPreferenceSRO implements Serializable {

    /**
	 * 
	 */
    private static final long serialVersionUID = 3049895982551418323L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            phoneNo;
    @Tag(3)
    private String            preference;
    
    @Tag(4)
    public static final String PREFRENCE_ENGLISH = "english";
    
    @Tag(5)
    public static final String PREFRENCE_HINDI   = "hindi";

    public UserPreferenceSRO() {
       
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

}
