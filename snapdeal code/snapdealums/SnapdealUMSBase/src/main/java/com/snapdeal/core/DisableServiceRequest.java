package com.snapdeal.core;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class DisableServiceRequest extends ServiceRequest {

	
	private static final long serialVersionUID = 3297808116091727378L;
	
	@Tag(4)
	private String url;
	
	

	public DisableServiceRequest() {
		super();
			}

	public DisableServiceRequest(String url) {
		this.url=url;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}