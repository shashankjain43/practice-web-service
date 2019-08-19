package com.snapdeal.opspanel.userpanel.stolenInstrument.model;

import com.snapdeal.opspanel.userpanel.stolenInstrument.enums.EntityType;

import lombok.Data;

@Data
public class BlockRequest {
	
	private String entityValue;
	
	private EntityType entityType;

}
