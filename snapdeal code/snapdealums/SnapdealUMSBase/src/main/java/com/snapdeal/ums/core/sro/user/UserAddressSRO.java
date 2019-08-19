/*
 *  Copyright 2013 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2013
 *  @author ghanshyam
 */
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.ext.userAddress.UserAddressStatus;

public class UserAddressSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 7475509272196842793L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Integer           userId;
    @Tag(3)
    private String            name;
    @Tag(4)
    private String            address1;
    @Tag(5)
    private String            address2;
    @Tag(6)
    private String            city;
    @Tag(7)
    private String            state;
    @Tag(8)
    private String            country;
    @Tag(9)
    private String            postalCode;
    @Tag(10)
    private String            mobile;
    @Tag(11)
    private String            landline;
    @Tag(12)
    private Date              created;
    @Tag(13)
    private Date              updated;
    @Tag(14)
    private String            addressTag;
    @Tag(15)
    private boolean           isDefault;
    
    @Tag(16)
    private Boolean           isActive;

    @Tag(17)
    private UserAddressStatus status;

    public UserAddressSRO() {
        super();
    }

    public UserAddressSRO(String state, Date created, Integer userId, String addressTag, String address1, String address2, Date updated, String postalCode, Integer id,
            String landline, String name, String mobile, String city, String country, boolean isDefault) {
        super();
        this.state = state;
        this.created = created;
        this.userId = userId;
        this.addressTag = addressTag;
        this.address1 = address1;
        this.address2 = address2;
        this.updated = updated;
        this.postalCode = postalCode;
        this.id = id;
        this.isDefault = isDefault;
        this.landline = landline;
        this.name = name;
        this.mobile = mobile;
        this.city = city;
        this.country = country;
        this.isDefault = isDefault;
    }

    /**
     * @return the id
     */
    public Integer getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Integer id) {
        this.id = id;
    }

    /**
     * @return the userId
     */
    public Integer getUserId() {
        return userId;
    }

    /**
     * @param userId the userId to set
     */
    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the address1
     */
    public String getAddress1() {
        return address1;
    }

    /**
     * @param address1 the address1 to set
     */
    public void setAddress1(String address1) {
        this.address1 = address1;
    }

    /**
     * @return the address2
     */
    public String getAddress2() {
        return address2;
    }

    /**
     * @param address2 the address2 to set
     */
    public void setAddress2(String address2) {
        this.address2 = address2;
    }

    /**
     * @return the city
     */
    public String getCity() {
        return city;
    }

    /**
     * @param city the city to set
     */
    public void setCity(String city) {
        this.city = city;
    }

    /**
     * @return the state
     */
    public String getState() {
        return state;
    }

    /**
     * @param state the state to set
     */
    public void setState(String state) {
        this.state = state;
    }

    /**
     * @return the country
     */
    public String getCountry() {
        return country;
    }

    /**
     * @param country the country to set
     */
    public void setCountry(String country) {
        this.country = country;
    }

    /**
     * @return the postalCode
     */
    public String getPostalCode() {
        return postalCode;
    }

    /**
     * @param postalCode the postalCode to set
     */
    public void setPostalCode(String postalCode) {
        this.postalCode = postalCode;
    }

    /**
     * @return the mobile
     */
    public String getMobile() {
        return mobile;
    }

    /**
     * @param mobile the mobile to set
     */
    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    /**
     * @return the landline
     */
    public String getLandline() {
        return landline;
    }

    /**
     * @param landline the landline to set
     */
    public void setLandline(String landline) {
        this.landline = landline;
    }

    /**
     * @return the created
     */
    public Date getCreated() {
        return created;
    }

    /**
     * @param created the created to set
     */
    public void setCreated(Date created) {
        this.created = created;
    }

    /**
     * @return the updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * @return the addressTag
     */
    public String getAddressTag() {
        return addressTag;
    }

    /**
     * @param addressTag the addressTag to set
     */
    public void setAddressTag(String addressTag) {
        this.addressTag = addressTag;
    }

    /**
     * @return the isDefault
     */
    public boolean isIsDefault() {
        return isDefault;
    }

    /**
     * @param isDefault the isDefault to set
     */
    public void setIsDefault(boolean isDefault) {
        this.isDefault = isDefault;
    }
    
    public Boolean getIsActive() {
		return isActive;
	}

	public void setIsActive(Boolean isActive) {
		this.isActive = isActive;
	}

	public UserAddressStatus getStatus() {
		return status;
	}

	public void setStatus(UserAddressStatus status) {
		this.status = status;
	}

	/* (non-Javadoc)
     * @see java.lang.Object#toString()
     */
    @Override
    public String toString() {
        return "UserAddressSRO [id=" + id + ", userId=" + userId + ", name=" + name + ", address1=" + address1 + ", address2=" + address2 + ", city=" + city + ", state=" + state
                + ", country=" + country + ", postalCode=" + postalCode + ", mobile=" + mobile + ", landline=" + landline + ", created=" + created + ", updated=" + updated
                + ", addressTag=" + addressTag + ", isDefault=" + isDefault + "]";
    }
    
    @Override
    public boolean equals(Object obj) {
        if (this == obj)
            return true;
        if (obj == null)
            return false;
        if (getClass() != obj.getClass())
            return false;
        UserAddressSRO other = (UserAddressSRO) obj;
        
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

}
