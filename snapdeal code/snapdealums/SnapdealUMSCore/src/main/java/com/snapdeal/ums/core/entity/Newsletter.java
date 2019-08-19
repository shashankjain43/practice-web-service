/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Oct 27, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

import com.snapdeal.base.utils.DateUtils;

@Entity
@Table(name = "newsletter", catalog = "ums")
public class Newsletter implements java.io.Serializable {
    private static final long serialVersionUID = 2473187630956378085L;
    public static final String GLOBAL_CITY_NAME = "Global Cities";
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
    
    private Integer           id;
    private String            subject;
    private String            content;
    private Date              created;
    private Date              updated;
    private boolean           enabled = true;
    private String            cityIds;
    private Integer           numEmailSent;
    private String            messageId;
    private String            lyrisId;
    private String            state;
    private Date              scheduleDate;
    private String            filterTypes;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public Newsletter(){
        super();
        this.updated = DateUtils.getCurrentTime();
    }
    
    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "content", nullable = true)
    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Column(name = "created", nullable = true)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Column(name = "updated", nullable = true)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = "enabled", nullable = true)
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }

    @Column(name = "city_ids", nullable = true)
    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public void setSubject(String subject) {
        this.subject = subject;
    }

    @Column(name = "subject", nullable = true)
    public String getSubject() {
        return subject;
    }

    @Column(name = "num_email_sent", nullable = true)
    public Integer getNumEmailSent() {
        return numEmailSent;
    }

    public void setNumEmailSent(Integer numEmailSent) {
        this.numEmailSent = numEmailSent;
    }

    @Column(name = "msg_id", unique = true, nullable = true)
    public String getMessageId() {
        return messageId;
    }

    public void setMessageId(String messageId) {
        this.messageId = messageId;
    }

    @Column(name = "lyris_msg_id", nullable = true)
    public String getLyrisId() {
        return lyrisId;
    }

    public void setLyrisId(String lyrisId) {
        this.lyrisId = lyrisId;
    }

    @Column(name = "state", nullable = true)
    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    @Column(name = "schedule_date", nullable = true)
    public Date getScheduleDate() {
        return scheduleDate;
    }

    public void setScheduleDate(Date scheduleDate) {
        this.scheduleDate = scheduleDate;
    }

    public void setFilterTypes(String filterType) {
        this.filterTypes = filterType;
    }

    @Column(name = "filter_types")
    public String getFilterTypes() {
        return filterTypes;
    }

}
