/*
 *  Copyright 2011 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Jun 21, 2011
 *  @author laptop
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

@Entity
@Table(name = "subscriber_profile", catalog = "ums")
public class SubscriberProfile implements Serializable {
    /**
     * 
     */
    private static final long serialVersionUID = 1L;
    private Integer           id;
    private String            name;
    private String            displayName;
    private String            email;
    private String            gender;
    private Integer           zoneId;
    private Integer           localityId;
    private Date              dob;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "email", nullable = false, length = 256)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    @Column(name = "zone_id")
    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "birth_date", nullable = true)
    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

    @Column(name = "name", nullable = true)
    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "display_name", nullable = true)
    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    @Column(name = "locality_id", nullable = true)
    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    @Column(name = "gender", columnDefinition = "enum('m','f')")
    @Enumerated(EnumType.STRING)
    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
