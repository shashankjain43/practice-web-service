package com.snapdeal.core;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetServerBehaviourContextRequest extends ServiceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -4732493476860378835L;
	

	@Tag(1)
	private ServerBehaviourContextSRO serverBehaviourContextSRO;
	
	

	public GetServerBehaviourContextRequest() {

	}

	public GetServerBehaviourContextRequest(
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