package com.snapdeal.ums.server.services;

import java.util.Collection;

import org.springframework.stereotype.Component;

import com.snapdeal.core.DisableServiceRequest;
import com.snapdeal.core.EnableServiceRequest;
import com.snapdeal.core.GetServerBehaviourContextRequest;
import com.snapdeal.core.GetServerBehaviourContextResponse;

@Component
public interface IServerBehaviourContextService {

	public GetServerBehaviourContextResponse getCurrentServerBehaviourContext();

	public void loadServerBehaviourContext(String disabledServerProfile);

	public GetServerBehaviourContextResponse enableService(
			EnableServiceRequest request);

	public GetServerBehaviourContextResponse disableService(
			DisableServiceRequest request);

	public boolean isServiceURLDisabled(String url);

	public GetServerBehaviourContextResponse createServerBehaviourContext(
			GetServerBehaviourContextRequest behaviourContextRequest);

	public void temporarilyDisableServices(Collection<String> camsdependenturl);

	public void enableTemporarilyDisabledServices(
			Collection<String> camsdependenturl);
}
