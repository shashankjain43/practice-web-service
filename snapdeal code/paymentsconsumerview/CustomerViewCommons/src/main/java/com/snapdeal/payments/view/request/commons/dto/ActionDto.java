package com.snapdeal.payments.view.request.commons.dto;

import java.util.Date;

import lombok.Data;

import com.snapdeal.payments.view.request.commons.enums.PartyType;

public @Data class ActionDto implements Comparable<ActionDto>{

	private String actionId;
	
    private String actionType;
    
    private String actionState;
    private String actionViewState ;
    private String actionTaken ;
    private String validActionCommands ;
 
    private String userId;
    private PartyType userType;
    
    private Date actionInitiationTimestamp ;
   
	private String metadata;

	@Override
	public int compareTo(ActionDto dto) {
		return dto.getActionInitiationTimestamp().compareTo(this.actionInitiationTimestamp);
	}
}