 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 22-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.email;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.core.entity.GetFeatured;

public class FeaturedSRO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3118901982981570479L;
	@Tag(1)
	private Integer id;
	@Tag(2)
    private String  companyName;
	@Tag(3)
    private String  businessType;
	@Tag(4)
    private String  contactName;
	@Tag(5)
    private String  mobile;
	@Tag(6)
    private String  email;
	@Tag(7)
    private String  city;
	@Tag(8)
    private String  extraInfo;
	@Tag(9)
    private Date created;
	
	public FeaturedSRO(GetFeatured featured) {
	    this.id=featured.getId();
	    this.companyName=featured.getCompanyName();
	    this.businessType=featured.getBusinessType();
	    this.contactName=featured.getContactName();
	    this.mobile=featured.getMobile();
	    this.email=featured.getEmail();
	    this.city=featured.getCity();
	    this.extraInfo=featured.getExtraInfo();
	    this.created=featured.getCreated();
	}
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getCompanyName() {
		return companyName;
	}
	public void setCompanyName(String companyName) {
		this.companyName = companyName;
	}
	public String getBusinessType() {
		return businessType;
	}
	public void setBusinessType(String businessType) {
		this.businessType = businessType;
	}
	public String getContactName() {
		return contactName;
	}
	public void setContactName(String contactName) {
		this.contactName = contactName;
	}
	public String getMobile() {
		return mobile;
	}
	public void setMobile(String mobile) {
		this.mobile = mobile;
	}
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getExtraInfo() {
		return extraInfo;
	}
	public void setExtraInfo(String extraInfo) {
		this.extraInfo = extraInfo;
	}
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
    
    
    
}

 