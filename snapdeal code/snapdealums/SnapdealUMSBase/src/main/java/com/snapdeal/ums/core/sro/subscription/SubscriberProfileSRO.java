package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class SubscriberProfileSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -5481740967126097457L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            name;
    @Tag(3)
    private String            displayName;
    @Tag(4)
    private String            email;
    @Tag(5)
    private String            gender;
    @Tag(6)
    private Integer           zoneId;
    @Tag(7)
    private Integer       localityId;
    @Tag(8)
    private Date              dob;

    public SubscriberProfileSRO() {
       
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDisplayName() {
        return displayName;
    }

    public void setDisplayName(String displayName) {
        this.displayName = displayName;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getZoneId() {
        return zoneId;
    }

    public void setZoneId(Integer zoneId) {
        this.zoneId = zoneId;
    }

    public Integer getLocalityId() {
        return localityId;
    }

    public void setLocalityId(Integer localityId) {
        this.localityId = localityId;
    }

    public Date getDob() {
        return dob;
    }

    public void setDob(Date dob) {
        this.dob = dob;
    }

}
