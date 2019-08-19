package com.snapdeal.opspanel.userpanel.stolenInstrument.request;

import java.util.List;

import com.snapdeal.opspanel.userpanel.stolenInstrument.model.BlockRequest;

import lombok.Data;

@Data
public class SubmitRequest {

	private List<BlockRequest> blockThese;
	
	private String reportingEntity;
	
	private String fraudDescription;
	
	private String actionPerformer;
	
}
