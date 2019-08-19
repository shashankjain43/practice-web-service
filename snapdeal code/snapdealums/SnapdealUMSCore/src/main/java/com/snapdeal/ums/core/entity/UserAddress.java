/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

import com.snapdeal.ums.ext.userAddress.UserAddressStatus;


@Entity
@Table(name = "user_address", catalog = "ums", uniqueConstraints = @UniqueConstraint(columnNames = {
        "user_id",
        "name",
        "mobile",
        "landline",
        "address1",
        "address2",
        "city",
        "state",
        "country",
        "postal_code" }))
public class UserAddress implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5455079998561607271L;
    private Integer           id;
    private Integer           userId;
    private String            name;
    private String            address1;
    private String            address2;
    private String            city;
    private String            state;
    private String            country;
    private String            postalCode;
    private String            mobile;
    private String            landline;
    private Date              created;
    private Date              updated;
    private boolean           isDefault;
    private String            addressTag;
    //default true(say 1) in db
    private boolean           isActive = true;
    //default 'UNKNOWN' in db
    private UserAddressStatus status = UserAddressStatus.UNKNOWN;

    public UserAddress() {
        super();
    }

    public UserAddress(Integer userId, String name, String address1, String address2, String city, String state, String country, String postalCode, String mobile, String landline,
            Date created, Date updated, boolean isDefault, String addressTag) {
        super();
        this.userId = userId;
        this.name = name;
        this.address1 = address1;
        this.address2 = address2;
        this.city = city;
        this.state = state;
        this.country = country;
        this.postalCode = postalCode;
        this.landline = landline;
        this.mobile = mobile;
        this.created = created;
        this.updated = updated;
        this.isDefault = isDefault;
        this.addressTag = addressTag;
    }

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return this.id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id")
    @Column(name = "user_id")
    public Integer getUserId() {
        return this.userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    @Column(name = "name")
    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    @Column(name = "address1")
    public String getAddress1() {
        return this.address1;
    }

    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    @Column(name = "address2")
    public String getAddress2() {
        return this.address2;
    }

    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    @Column(name = "city")
    public String getCity() {
        return this.city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    @Column(name = "state")
    public String getState() {
        return this.state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "country")
    public String getCountry() {
        return this.country;
    }

    public void setCountry(String country) {
        this.country = country==null?"India":country;
    }

    @Column(name = "postal_code")
    public String getPostalCode() {
        return this.postalCode;
    }

    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return this.created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "mobile")
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "landline")
    public String getLandline() {
        return landline;
    }

    public void setLandline(String landline) {
        this.landline = landline;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * @return the isDefault
     */
    @Column(name = "isdefault")
    public boolean isDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }

    @Column(name = "address_tag")
    public String getAddressTag() {
        return addressTag;
    }

    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }
    
    @Column(name = "isActive")
    public boolean isActive() {
        return isActive;
    }

    public void setActive(boolean isActive) {
        this.isActive = isActive;
    }
    
    @Enumerated(EnumType.STRING)
    @Column(name = "status")
    public UserAddressStatus getStatus() {
        return status;
    }

    public void setStatus(UserAddressStatus status) {
        this.status = status;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#hashCode()
     */
    @Override
    public int hashCode() {
        final int prime = 31;
        int result = 1;
        result = prime * result + ((city == null) ? 0 : city.hashCode());
        result = prime * result + ((state == null) ? 0 : state.hashCode());
        result = prime * result + ((country == null) ? 0 : country.hashCode());
        result = prime * result + ((address1 == null) ? 0 : address1.hashCode());
        result = prime * result + ((address2 == null) ? 0 : address2.hashCode());
        result = prime * result + ((landline == null) ? 0 : landline.hashCode());
        result = prime * result + ((mobile == null) ? 0 : mobile.hashCode());
        result = prime * result + ((name == null) ? 0 : name.hashCode());
        result = prime * result + ((postalCode == null) ? 0 : postalCode.hashCode());
        result = prime * result + ((userId == null) ? 0 : userId.hashCode());
        return result;
    }

    /* (non-Javadoc)
     * @see java.lang.Object#equals(java.lang.Object)
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserAddress other = (UserAddress) obj;
        
        if (city == null) {
            if (other.city != null)
                return false;
        } else if (!city.equals(other.city))
            return false;
        if (state == null) {
            if (other.state != null)
                return false;
        } else if (!state.equals(other.state))
            return false;
        if (country == null) {
            if (other.country != null)
                return false;
        } else if (!country.equals(other.country))
            return false;
        if (postalCode == null) {
            if (other.postalCode != null)
                return false;
        } else if (!postalCode.equals(other.postalCode))
            return false;
        if (name == null) {
            if (other.name != null)
                return false;
        } else if (!name.equals(other.name))
            return false;
        if (mobile == null) {
            if (other.mobile != null)
                return false;
        } else if (!mobile.equals(other.mobile))
            return false;
        if (landline == null) {
            if (other.landline != null)
                return false;
        } else if (!landline.equals(other.landline))
            return false;
        if (address1 == null) {
            if (other.address1 != null)
                return false;
        } else if (!address1.equals(other.address1))
            return false;
        if (address2 == null) {
            if (other.address2 != null)
                return false;
        } else if (!address2.equals(other.address2))
            return false;
        if (userId == null) {
            if (other.userId != null)
                return false;
        } else if (!userId.equals(other.userId))
            return false;
        return true;
    }

}