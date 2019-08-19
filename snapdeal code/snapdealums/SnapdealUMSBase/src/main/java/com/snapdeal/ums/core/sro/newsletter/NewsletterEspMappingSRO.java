/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 15-Oct-2012
 *  @author naveen
 */
package com.snapdeal.ums.core.sro.newsletter;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.core.sro.bulkemail.EmailServiceProviderSRO;

public class NewsletterEspMappingSRO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = -5855650995971790947L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private EmailServiceProviderSRO emailServiceProvider;
	@Tag(3)
	private NewsletterSRO newsletter;
	@Tag(4)
	private String filterType;
	@Tag(5)
	private String messageId;
	@Tag(6)
	private Integer cityId;
	@Tag(7)
	private State   status;

	public static enum State {
	  success, failed;
	}
	

	public State getStatus() {
        return status;
    }

    public void setStatus(State status) {
        this.status = status;
    }

	
    public NewsletterEspMappingSRO() { 
	
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public EmailServiceProviderSRO getEmailServiceProvider() {
		return emailServiceProvider;
	}

	public void setEmailServiceProvider(
			EmailServiceProviderSRO emailServiceProvider) {
		this.emailServiceProvider = emailServiceProvider;
	}

	public NewsletterSRO getNewsletter() {
		return newsletter;
	}

	public void setNewsletter(NewsletterSRO newsletter) {
		this.newsletter = newsletter;
	}

	public String getFilterType() {
		return filterType;
	}

	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

	public String getMessageId() {
		return messageId;
	}

	public void setMessageId(String messageId) {
		this.messageId = messageId;
	}

	public Integer getCityId() {
		return cityId;
	}

	public void setCityId(Integer cityId) {
		this.cityId = cityId;
	}

}
