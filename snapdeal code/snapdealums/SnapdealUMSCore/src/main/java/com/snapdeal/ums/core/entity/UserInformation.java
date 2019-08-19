/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 29, 2010
 *  @author rahul
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import com.snapdeal.base.utils.DateUtils;

@Entity
@Table(name = "user_information", catalog = "ums")
public class UserInformation implements Serializable {

    private static final long serialVersionUID = 4941138805254539420L;

    private Integer           id;
    private User              user;
    private String            name;
    private String            value;
    private Date              updated;

    public UserInformation() {
        this.updated = DateUtils.getCurrentTime();
        
    }
    
    public UserInformation(User user, String name, String value) {
        this.user = user;
        this.name = name;
        this.value = value;
        this.updated = DateUtils.getCurrentTime();
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    @Column(name = "name", nullable = false, length = 20)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "value", nullable = false, length = 48)
    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
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
