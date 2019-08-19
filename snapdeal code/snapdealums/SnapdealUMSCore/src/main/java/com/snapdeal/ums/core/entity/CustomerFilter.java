package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_filter", catalog = "ums")
public class CustomerFilter implements Serializable {

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
        public boolean equals(CustomerFilter customerFilter){
        	if(customerFilter.getFilterDomain().equals(this.getFilterDomain()) && customerFilter.getName().equals(this.getName()))
        		return true;
        	return false;
        }
	}
	private static final long serialVersionUID = 687991492854005033L;

	private Integer id;
	private String name;
	private String displayValue;
	private boolean enabled;
	private boolean selected;
	private FilterDomain filterDomain;
	private Integer intervalOffset;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "name")
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	@Column(name = "display_name")
	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}

	@Column(name = "enabled")
	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	@Column(name = "selected")
	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean checked) {
		this.selected = checked;
	}

	@Column(name = "filter_domain")
	@Enumerated(value = EnumType.STRING)
	public FilterDomain getFilterDomain() {
		return filterDomain;
	}

	public void setFilterDomain(FilterDomain filterDomain) {
		this.filterDomain = filterDomain;
	}

	@Column(name = "interval_offset")
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
