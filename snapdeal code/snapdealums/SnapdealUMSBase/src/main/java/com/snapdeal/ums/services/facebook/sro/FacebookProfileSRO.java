/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Apr 16, 2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.services.facebook.sro;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class FacebookProfileSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7570460678028025548L;

    @Tag(1)
    private String            firstName;

    @Tag(2)
    private String            middleName;

    @Tag(3)
    private String            lastName;

    @Tag(4)
    private String            aboutMe;

    public FacebookProfileSRO() {
        super();
    }

    public FacebookProfileSRO(String firstName, String middleName, String lastName, String aboutMe) {
        super();
        this.aboutMe = aboutMe;
        this.firstName = firstName;
        this.middleName = middleName;
        this.lastName = lastName;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public void setAboutMe(String aboutMe) {
        this.aboutMe = aboutMe;
    }

}
