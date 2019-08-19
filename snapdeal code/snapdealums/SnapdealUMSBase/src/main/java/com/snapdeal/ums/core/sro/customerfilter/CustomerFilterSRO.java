 /*
*  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
*  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
*  
*  @version     1.0, 15-Oct-2012
*  @author naveen
*/
package com.snapdeal.ums.core.sro.customerfilter;

import java.io.Serializable;

import com.dyuproject.protostuff.Tag;

public class CustomerFilterSRO implements Serializable{
	
    public enum FilterType {
        SMS_CUSTOMER_ALL("customer_all", FilterDomain.SMS),
        SMS_SUBSCRIBER_60_DAY("subscriber_60_day", FilterDomain.SMS),
        SMS_SUBSCRIBER_120_DAY("subscriber_120_day", FilterDomain.SMS),
        SMS_SUBSCRIBER_240_DAY("subscriber_240_day", FilterDomain.SMS),
        SMS_SUBSCRIBER_OLD("subscriber_old", FilterDomain.SMS),
        NLM_CUSTOMER_ALL("customer_all", FilterDomain.NLM),
        NLM_OPENS_AND_CLICKS_ALL("opens_and_clicks_all", FilterDomain.NLM),
        NLM_SUBSCRIBER_NEW("subscriber_new", FilterDomain.NLM),
        NLM_SUBSCRIBER_OLD("subscriber_old", FilterDomain.NLM),
        NLM_DEAL_CUSTOMER_ALL("deal_customer_all", FilterDomain.NLM),
        NLM_DEAL_OPENS_AND_CLICKS_ALL("deal_opens_and_clicks_all", FilterDomain.NLM),
        NLM_DEAL_SUBSCRIBER_NEW("deal_subscriber_new", FilterDomain.NLM),
        NLM_DEAL_SUBSCRIBER_OLD("deal_subscriber_old", FilterDomain.NLM);        
        
        private String name;
        private FilterDomain filterDomain;
        private FilterType(String name, FilterDomain filterDomain) {
            this.name = name;
            this.filterDomain = filterDomain;
        }
        public String getName() {
            return name;
        }
        public FilterDomain getFilterDomain() {
            return filterDomain;
        }
        public boolean equals(CustomerFilterSRO customerFilter){
            if(customerFilter.getFilterDomain().equals(this.getFilterDomain()) && customerFilter.getName().equals(this.getName()))
                return true;
            return false;
        }
    }
    
	/**
	 * 
	 */
	private static final long serialVersionUID = 5197923796507856925L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private String name;
	@Tag(3)
	private String displayValue;
	@Tag(4)
	private boolean enabled;
	@Tag(5)
	private boolean selected;
	@Tag(6)
	private FilterDomain filterDomain;
	@Tag(7)
	private Integer intervalOffset;
	
    public FilterDomain getFilterDomain() {
		return filterDomain;
	}
	public void setFilterDomain(FilterDomain filterDomain) {
		this.filterDomain = filterDomain;
	}
	
	public Integer getId() {
		return id;
	}
	public void setId(Integer id) {
		this.id = id;
	}
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getDisplayValue() {
		return displayValue;
	}
	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	public boolean isEnabled() {
		return enabled;
	}
	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}
	public boolean isSelected() {
		return selected;
	}
	public void setSelected(boolean selected) {
		this.selected = selected;
	}
	
	public Integer getIntervalOffset() {
		return intervalOffset;
	}
	public void setIntervalOffset(Integer intervalOffset) {
		this.intervalOffset = intervalOffset;
	}
     
	public enum FilterDomain {
	        NLM, // NewsLetterManager
	        SMS, // SMSSenderManager
	}
     
}

 