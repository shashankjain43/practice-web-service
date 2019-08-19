package com.snapdeal.core;

import org.springframework.beans.factory.annotation.Autowired;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;

public class GetServerBehaviourContextResponse extends ServiceResponse {

	/**
	 * 
	 */
	private static final long serialVersionUID = -3503421351915132563L;

	@Tag(1)
	private ServerBehaviourContextSRO serverBehaviourContextSRO;

	

	public GetServerBehaviourContextResponse() {

	}

	public GetServerBehaviourContextResponse(
			ServerBehaviourContextSRO serverBehaviourContextSRO) {
		super();
		this.serverBehaviourContextSRO = serverBehaviourContextSRO;
	}

	
	public ServerBehaviourContextSRO getServerBehaviourContextSRO() {
	
		return serverBehaviourContextSRO;
		
	
	}

	public void setServerBehaviourContextSRO(
			ServerBehaviourContextSRO serverBehaviourContextSRO) {
		this.serverBehaviourContextSRO = serverBehaviourContextSRO;
	}
	


}
