/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 11-Jul-2012
 *  @author kuldeep
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.snapdeal.base.utils.DateUtils;

@Entity
@Table(name = "user_preference", catalog = "ums")
public class UserPreference implements Serializable {

    /**
     * 
     */
    private static final long  serialVersionUID  = -5977805236656978409L;
    private Integer            id;
    private String             phoneNo;
    private String             preference;
    private Date              updated;

    public static final String PREFRENCE_ENGLISH = "english";
    public static final String PREFRENCE_HINDI   = "hindi";
    
    public UserPreference(){
        this.updated = DateUtils.getCurrentTime();
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    @Column(name = "phone_no", nullable = false)
    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    @Column(name = "preference", nullable = false)
    public String getPreference() {
        return preference;
    }
    
    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false, length = 19)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }
}
