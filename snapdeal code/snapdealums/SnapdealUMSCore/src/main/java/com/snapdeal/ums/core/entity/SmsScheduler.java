/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 18, 2010
 *  @author Vikash
 */
package com.snapdeal.ums.core.entity;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

import com.snapdeal.base.utils.DateUtils;

@Entity
@Table(name = "sms_scheduler", catalog = "ums")
@XmlRootElement
public class SmsScheduler implements java.io.Serializable {

    public static enum State {
        SCHEDULED("scheduled"), SENT("sent"), PARTIAL_SENT("partial sent"), CANCELLED("cancelled"), FAILED("failed");

        private String name;

        private State(String name) {
            this.name = name;
        }

        public String getName() {
            return name;
        }
    }

    private static final long serialVersionUID = 7966264513882495567L;
    private Integer           id;
    private String            smsText;
    private Date              scheduleTime;
    private Date              updated;
    private Integer           totalSmsSent;
    private String            cityIds;
    private String            status;
    private String            smsSenderResponse;
    private Integer           smsSenderService;
    private String            filterType;
    
    public SmsScheduler(){
        this.updated = DateUtils.getCurrentTime();
    }
    
    public SmsScheduler(String smsText, Date scheduleTime, Integer totalSmsSent, String cityIds, String status, String smsSenderResponse, Integer smsSenderService,
            String filterType) {
        super();
        this.smsText = smsText;
        this.scheduleTime = scheduleTime;
        this.totalSmsSent = totalSmsSent;
        this.cityIds = cityIds;
        this.status = status;
        this.smsSenderResponse = smsSenderResponse;
        this.smsSenderService = smsSenderService;
        this.filterType = filterType;
        this.updated = DateUtils.getCurrentTime();
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "sms_text", nullable = false)
    public String getSmsText() {
        return smsText;
    }

    public void setSmsText(String smsText) {
        this.smsText = smsText;
    }

    @Column(name = "schedule_time", nullable = false)
    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    @Column(name = "total_sms_sent", nullable = true)
    public Integer getTotalSmsSent() {
        return totalSmsSent;
    }

    public void setTotalSmsSent(Integer totalSmsSent) {
        this.totalSmsSent = totalSmsSent;
    }

    @Column(name = "city_ids", nullable = false)
    public String getCityIds() {
        return cityIds;
    }

    public void setCityIds(String cityIds) {
        this.cityIds = cityIds;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    @Column(name = "status", nullable = false, length = 19)
    public String getStatus() {
        return status;
    }

    public void setSmsSenderResponse(String smsSenderResponse) {
        this.smsSenderResponse = smsSenderResponse;
    }

    @Column(name = "sms_sender_response")
    public String getSmsSenderResponse() {
        return smsSenderResponse;
    }

    @Column(name = "sms_sender_service")
    public Integer getSmsSenderService() {
        return smsSenderService;
    }

    public void setSmsSenderService(Integer smsSenderService) {
        this.smsSenderService = smsSenderService;
    }

    @Column(name = "filter_type")
    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", nullable = false, length = 19, updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
