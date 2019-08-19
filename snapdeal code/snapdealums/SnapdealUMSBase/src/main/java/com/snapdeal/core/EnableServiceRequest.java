package com.snapdeal.core;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class EnableServiceRequest extends ServiceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = 5232611479034336968L;
	
	@Tag(3)
	private String url;

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

}