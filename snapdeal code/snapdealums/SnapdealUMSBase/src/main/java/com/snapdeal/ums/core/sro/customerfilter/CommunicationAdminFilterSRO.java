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
import com.snapdeal.core.dto.CommunicationAdminFilterDTO;

public class CommunicationAdminFilterSRO implements Serializable{

	/**
	 * 
	 */
	private static final long serialVersionUID = 5791373798680142454L;
	@Tag(1)
	private Integer id;
	@Tag(2)
	private String displayValue;
	@Tag(3)
	private Integer offsetHours;
	@Tag(4)
	private Integer offsetMins;
	@Tag(5)
	private boolean defaultSelected;
	@Tag(6)
	private boolean selected;

	public CommunicationAdminFilterSRO(CommunicationAdminFilterDTO commAdmFilter) {
	    this.setDisplayValue(commAdmFilter.getDisplayValue());
	    this.setId(commAdmFilter.getId());
	    this.setOffsetHours(commAdmFilter.getOffsetHours());
	    this.setOffsetMins(commAdmFilter.getOffsetMins());
	    this.setDefaultSelected(commAdmFilter.isDefaultSelected());
	    this.setSelected(commAdmFilter.isSelected());
	}

    public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getDisplayValue() {
		return displayValue;
	}

	public void setDisplayValue(String displayValue) {
		this.displayValue = displayValue;
	}
	
	public Integer getOffsetHours() {
		return offsetHours;
	}

	public void setOffsetHours(Integer offsetHours) {
		this.offsetHours = offsetHours;
	}

	public Integer getOffsetMins() {
		return offsetMins;
	}

	public void setOffsetMins(Integer offsetMins) {
		this.offsetMins = offsetMins;
	}

	public boolean isDefaultSelected() {
		return defaultSelected;
	}

	public void setDefaultSelected(boolean defaultSelected) {
		this.defaultSelected = defaultSelected;
	}

	public boolean isSelected() {
		return selected;
	}

	public void setSelected(boolean selected) {
		this.selected = selected;
	}
}

 