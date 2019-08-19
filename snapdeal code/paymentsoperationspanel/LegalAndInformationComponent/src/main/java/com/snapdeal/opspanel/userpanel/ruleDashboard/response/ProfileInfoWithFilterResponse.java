package com.snapdeal.opspanel.userpanel.ruleDashboard.response;

import java.util.Date;
import java.util.Map;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.snapdeal.payments.dashboard.model.ProfileVariableInfo;
import com.snapdeal.payments.dashboard.model.RuleAttribute;

import lombok.Data;

@Data
public class ProfileInfoWithFilterResponse {
	
	private String transactionType;
	
	private String profileName;
	
	private Integer profileVersion;
	
	private Map<String, RuleAttribute> ruleAttributeMap;
	
	private Map<String, RuleAttribute> tagAttributeMap;
	
	private ProfileVariableInfo profileVariable;
	
	private Integer rulePercentage;
	
	private String createdBy;
	
	private Date createdOn;
	
	private String updatedBy;
	
	private Date updatedOn;
	
	@JsonProperty
	private boolean isActive;
	
	private Integer activeOnVersion;

}
