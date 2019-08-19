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
import com.snapdeal.core.entity.Corporate;

public class CorporateSRO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3861625220608485844L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private String companyName;
	@Tag(3)
	private String contactName;
	@Tag(4)
	private String mobile;
	@Tag(5)
	private String email;
	@Tag(6)
	private String companyWebsite;
	@Tag(7)
	private String city;
	@Tag(8)
	private Date created;
	
	public CorporateSRO(Corporate corporate) {
	    this.id=corporate.getId();
	    this.companyName=corporate.getCompanyName();
	    this.contactName=corporate.getContactName();
	    this.mobile=corporate.getMobile();
	    this.email=corporate.getEmail();
	    this.companyWebsite=corporate.getCompanyWebsite();
	    this.city=corporate.getCity();
	    this.created=corporate.getCreated();
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
	public String getCompanyWebsite() {
		return companyWebsite;
	}
	public void setCompanyWebsite(String companyWebsite) {
		this.companyWebsite = companyWebsite;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
    public Date getCreated() {
        return created;
    }
    public void setCreated(Date created) {
        this.created = created;
    }
	
}
