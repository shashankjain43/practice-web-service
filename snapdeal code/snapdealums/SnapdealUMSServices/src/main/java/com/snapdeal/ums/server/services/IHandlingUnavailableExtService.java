package com.snapdeal.ums.server.services;

import org.springframework.stereotype.Component;

import com.snapdeal.ums.server.services.impl.HandlingUnavailableExtService.ExternalAppName;

@Component
public interface IHandlingUnavailableExtService {

	
	public void enableTemporarilyDisabledServices(ExternalAppName externalAppName);
	public void temporarilyDisableServices(ExternalAppName externalAppName);

}
