/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 22-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.core.sro.bulkemail;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class EmailBulkEspCityMappingSRO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3914737654770227399L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private Integer cityId;
	@Tag(3)
	private EmailServiceProviderSRO esp;

	public EmailBulkEspCityMappingSRO(){
	    super();
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer city) {
		this.cityId = city;
	}

	public EmailServiceProviderSRO getEsp() {
		return esp;
	}

	public void setEsp(EmailServiceProviderSRO esp) {
		this.esp = esp;
	}

}
