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

public class ESPFilterCityMappingSRO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 6230496811806621157L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private String filter;
	@Tag(3)
	private Integer cityId;
	@Tag(4)
	private String mlid;
	@Tag(5)
	private String filterType;
	@Tag(6)
	private String siteId;
	@Tag(7)
	private EmailServiceProviderSRO emailServiceProvider;
	
	public ESPFilterCityMappingSRO() {
    
    }
	
	public ESPFilterCityMappingSRO(String mlId, Integer cityId, String filterType, String siteId, Integer emailServiceProviderId){
	    this.mlid = mlId;
	    this.cityId = cityId;
	    this.filterType = filterType;
	    this.siteId = siteId;
	    EmailServiceProviderSRO emailServiceProvider = new EmailServiceProviderSRO();
	    emailServiceProvider.setId(emailServiceProviderId);
	}
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getFilter() {
		return filter;
	}
	public void setFilter(String filter) {
		this.filter = filter;
	}
	public Integer getCityId() {
		return cityId;
	}
	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}
	public String getMlid() {
		return mlid;
	}
	public void setMlid(String mlid) {
		this.mlid = mlid;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}
	public String getSiteId() {
		return siteId;
	}
	public void setSiteId(String siteId) {
		this.siteId = siteId;
	}
	public EmailServiceProviderSRO getEmailServiceProvider() {
		return emailServiceProvider;
	}
	public void setEmailServiceProvider(EmailServiceProviderSRO emailServiceProvider) {
		this.emailServiceProvider = emailServiceProvider;
	}
	
	
	
}
