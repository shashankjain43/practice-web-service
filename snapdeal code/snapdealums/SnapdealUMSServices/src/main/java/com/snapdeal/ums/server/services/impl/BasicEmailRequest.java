package com.snapdeal.ums.server.services.impl;

import java.util.HashMap;
import java.util.Map;

import com.snapdeal.base.model.common.ServiceRequest;

/**
 * Basic email request class containing user email and other attributes.
 * @author lovey
 *
 */

public class BasicEmailRequest extends ServiceRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1308544068585372723L;
	
	String email;
	Map<String,Object> attribute=new HashMap<String,Object>();
	
	public BasicEmailRequest(String email) {
		this.email=email;
	}
	public BasicEmailRequest() {
			}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}
	public Map<String, Object> getAttribute() {
		return attribute;
	}
	public void setAttribute(Map<String, Object> attribute) {
		this.attribute = attribute;
	}
	
}
