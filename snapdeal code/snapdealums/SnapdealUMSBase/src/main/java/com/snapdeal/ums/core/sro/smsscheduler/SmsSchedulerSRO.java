 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 15-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.smsscheduler;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class SmsSchedulerSRO implements Serializable {
	
	
    /**
	 * 
	 */
	private static final long serialVersionUID = -1300955000517572527L;
	@Tag(1)
	private Integer           id;
	@Tag(2)
    private String            smsText;
	@Tag(3)
    private Date              scheduleTime;
	@Tag(4)
    private Date              created;
	@Tag(5)
    private Integer           totalSmsSent;
	@Tag(6)
    private String            cityIds;
	@Tag(7)
    private String            status;
	@Tag(8)
    private String            smsSenderResponse;
	@Tag(9)
    private Integer           smsSenderService;
	@Tag(10)
    private String            filterType;
	
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
    
	public SmsSchedulerSRO() {
        super();
    }
    public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getSmsText() {
		return smsText;
	}
	public void setSmsText(String smsText) {
		this.smsText = smsText;
	}
	public Date getScheduleTime() {
		return scheduleTime;
	}
	public void setScheduleTime(Date scheduleTime) {
		this.scheduleTime = scheduleTime;
	}
	public Date getCreated() {
		return created;
	}
	public void setCreated(Date created) {
		this.created = created;
	}
	public Integer getTotalSmsSent() {
		return totalSmsSent;
	}
	public void setTotalSmsSent(Integer totalSmsSent) {
		this.totalSmsSent = totalSmsSent;
	}
	public String getCityIds() {
		return cityIds;
	}
	public void setCityIds(String cityIds) {
		this.cityIds = cityIds;
	}
	public String getStatus() {
		return status;
	}
	public void setStatus(String status) {
		this.status = status;
	}
	public String getSmsSenderResponse() {
		return smsSenderResponse;
	}
	public void setSmsSenderResponse(String smsSenderResponse) {
		this.smsSenderResponse = smsSenderResponse;
	}
	public Integer getSmsSenderService() {
		return smsSenderService;
	}
	public void setSmsSenderService(Integer smsSenderService) {
		this.smsSenderService = smsSenderService;
	}
	public String getFilterType() {
		return filterType;
	}
	public void setFilterType(String filterType) {
		this.filterType = filterType;
	}

}

 