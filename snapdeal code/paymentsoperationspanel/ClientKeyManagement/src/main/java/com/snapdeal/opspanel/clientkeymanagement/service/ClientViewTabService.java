package com.snapdeal.opspanel.clientkeymanagement.service;

import java.util.List;

import com.snapdeal.opspanel.clientkeymanagement.entity.ClientViewerEntity;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericOnBoardClientRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericUpdateClientStatusRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericUpdateSecretKeyRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GetClientDetailsRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GetTargetApplicationRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.ShowClientsRequest;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericOnBoardClientResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericUpdateClientStatusResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericUpdateSecretKeyResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GetClientDetailsResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.ShowClientsResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.payments.authorize.core.model.ClientDetails;
import com.snapdeal.payments.authorize.core.model.ClientDetailsSummary;
import com.snapdeal.payments.authorize.core.model.ClientSummary;
import com.snapdeal.payments.authorize.core.model.OnBoardClientRequest;
import com.snapdeal.payments.authorize.core.model.UpdateClientStatusRequest;
import com.snapdeal.payments.authorize.core.model.UpdateClientStatusResponse;
import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyRequest;
import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyResponse;

public interface ClientViewTabService {
	
	public ShowClientsResponse showClients(ShowClientsRequest request) throws ClientViewerException;
	
	public void addClientsToUser(String userId, String clientName, String application)  throws ClientViewerException;
	
	public void updateClientForUser(String userId, String clientName, String application) throws ClientViewerException;
	
	public GenericOnBoardClientResponse genericOnBoardClient(GenericOnBoardClientRequest request) throws ClientViewerException, OpsPanelException;
	
	public GenericUpdateClientStatusResponse genericUpdateClientStatus(GenericUpdateClientStatusRequest request) throws ClientViewerException;
	
	public GenericUpdateSecretKeyResponse genericUpdateSecretKey(GenericUpdateSecretKeyRequest request) throws ClientViewerException;
	
	public List<String> getSupportedTargetApplications() throws ClientViewerException;
	
	public GetClientDetailsResponse getClientDetails(GetClientDetailsRequest request) throws ClientViewerException;

	public List<String> getClientsForTargetApplication(
			GetTargetApplicationRequest request) throws ClientViewerException;
}
