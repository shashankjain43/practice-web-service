package com.snapdeal.ims.cache.service;

import com.snapdeal.ims.token.entity.GlobalTokenDetailsEntity;

public interface IGtokenCleanup {
	
	
	public void asyncTokenCleanUp(final GlobalTokenDetailsEntity gToken);
}
