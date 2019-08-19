/*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 15-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.newsletter;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class NewsletterSRO implements Serializable {

    /**
	 * 
	 */
	private static final long serialVersionUID = 8398080199868992946L;
	@Tag(1)
	private Integer           id;
	@Tag(2)
    private String            subject;
	@Tag(3)
    private String            content;
	@Tag(4)
    private Date              created;
	@Tag(5)
    private Date              updated;
	@Tag(6)
    private boolean           enabled = true;
	@Tag(7)
    private String            cityIds;
	@Tag(8)
    private Integer           numEmailSent;
	@Tag(9)
    private String            messageId;
	@Tag(10)
    private String            lyrisId;
	@Tag(11)
    private String            state;
	@Tag(12)
    private Date              scheduleDate;
	@Tag(13)
    private String            filterTypes;
	
	public static enum State
    {
        SAVED("saved"), 
        TESTED("tested"), 
        SCHEDULED("scheduled"),
        SENT("sent"),
        CANCELLED("cancelled");
        
        private String name;
        private State(String name) 
        {
            this.name = name;
        }

        public String getName() 
         {
             return name;
        }
    }
    
	public NewsletterSRO() {
        super();
    }
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public Integer getNumEmailSent() {
        return numEmailSent;
    }

    public void setNumEmailSent(Integer numEmailSent) {
        this.numEmailSent = numEmailSent;
    }

    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    public String getLyrisId() {
        return lyrisId;
    }

    public void setLyrisId(String lyrisId) {
        this.lyrisId = lyrisId;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public String getFilterTypes() {
        return filterTypes;
    }

    public void setFilterTypes(String filterTypes) {
        this.filterTypes = filterTypes;
    }

}
