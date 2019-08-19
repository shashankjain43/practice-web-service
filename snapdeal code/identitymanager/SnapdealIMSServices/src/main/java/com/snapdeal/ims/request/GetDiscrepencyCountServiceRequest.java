package com.snapdeal.ims.request;
import java.sql.Timestamp;
import lombok.Data;
/*
 * Author Radhika
 */

public @Data class GetDiscrepencyCountServiceRequest {
		
		
			private Timestamp fromDate;
			private Timestamp toDate;

	}



