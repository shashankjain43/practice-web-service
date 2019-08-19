package com.snapdeal.ims.request;

import java.sql.Timestamp;

import com.snapdeal.ims.enums.DiscrepencyCase;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@ToString
public  class GetEmailForDiscrepencyCasesServiceRequest {
	
	
	Timestamp fromDate;
	Timestamp toDate;
	DiscrepencyCase dCase;
	
}
