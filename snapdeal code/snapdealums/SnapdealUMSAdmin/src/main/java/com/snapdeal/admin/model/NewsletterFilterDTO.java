/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Dec 1, 2010
 *  @author Vikash
 */
package com.snapdeal.admin.model;

import java.util.Date;

import com.snapdeal.ums.core.sro.customerfilter.CustomerFilterSRO;


public class NewsletterFilterDTO {
    private String name;
    private String displayValue;
    private boolean selected;
    private String scheduleHour;
    private String scheduleMin;
    private Date   scheduleTime;
    private int intervalOffset;

    public NewsletterFilterDTO() {
    }

    public NewsletterFilterDTO(CustomerFilterSRO filter) {
        this.name = filter.getName();
        this.setDisplayValue(filter.getDisplayValue());
        this.selected = filter.isSelected();
        this.setIntervalOffset(filter.getIntervalOffset());
    }
 
    public boolean isSelected() {
        return selected;
    }

    public void setSelected(boolean selected) {
        this.selected = selected;
    }

    public String getScheduleHour() {
        return scheduleHour;
    }

    public void setScheduleHour(String scheduleHour) {
        this.scheduleHour = scheduleHour;
    }

    public String getScheduleMin() {
        return scheduleMin;
    }

    public void setScheduleMin(String scheduleMin) {
        this.scheduleMin = scheduleMin;
    }

    public Date getScheduleTime() {
        return scheduleTime;
    }

    public void setScheduleTime(Date scheduleTime) {
        this.scheduleTime = scheduleTime;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setDisplayValue(String displayValue) {
        this.displayValue = displayValue;
    }

    public String getDisplayValue() {
        return displayValue;
    }

    public void setIntervalOffset(int intervalOffset) {
        this.intervalOffset = intervalOffset;
    }

    public int getIntervalOffset() {
        return intervalOffset;
    }
        
}
