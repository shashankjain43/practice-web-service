package com.snapdeal.ims.request;

import com.snapdeal.ims.request.AbstractRequest;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

	@Setter
	@Getter
	@ToString

	public class GetDiscrepencyCountRequest extends AbstractRequest {
		
		private static final long serialVersionUID = 1L;
	String fromDate;
	String toDate;
	
}
