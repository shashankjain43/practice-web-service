import org.springframework.beans.factory.annotation.Autowired;

import com.snapdeal.opspanel.rms.service.TokenService;

/*package com.snapdeal.opspanel.clientkeymanagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.rms.service.TokenService;

import com.snapdeal.opspanel.clientkeymanagement.entity.ClientViewerEntity;
import com.snapdeal.opspanel.clientkeymanagement.enums.TargetApplications;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericOnBoardClientRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericUpdateClientStatusRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GenericUpdateSecretKeyRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.GetClientDetailsRequest;
import com.snapdeal.opspanel.clientkeymanagement.request.ShowClientsRequest;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericOnBoardClientResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericUpdateClientStatusResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GenericUpdateSecretKeyResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.GetClientDetailsResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.ShowClientsResponse;
import com.snapdeal.opspanel.clientkeymanagement.response.WalletClients;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewTabService;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewerService;
import com.snapdeal.payments.authorize.core.exception.SDMoneyAuthException;
import com.snapdeal.payments.authorize.core.model.AuthEnable;
import com.snapdeal.payments.authorize.core.model.ClientDetailsSummary;
import com.snapdeal.payments.authorize.core.model.ClientStatus;
import com.snapdeal.payments.authorize.core.model.ClientSummary;
import com.snapdeal.payments.authorize.core.model.GetClientHistoryRequest;
import com.snapdeal.payments.authorize.core.model.GetClientHistoryResponse;
import com.snapdeal.payments.authorize.core.model.GetLatestClientDetailsRequest;
import com.snapdeal.payments.authorize.core.model.GetLatestClientDetailsResponse;
import com.snapdeal.payments.authorize.core.model.UpdateClientStatusRequest;
import com.snapdeal.payments.authorize.core.model.UpdateClientStatusResponse;
import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyRequest;
import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyResponse;
import com.snapdeal.payments.sdmoney.admin.client.SDMoneyAdminClient;

@Slf4j
@Service("clientViewMockService")
public class ClientViewMockServiceImpl implements ClientViewTabService{

	@Autowired
	ClientViewerService clientViewerService;
	
	@Autowired
	SDMoneyAdminClient sdMoneyAdminClient;
	
	@Autowired
	HttpServletRequest servletRequest;
	
	@Autowired
	TokenService tokenService;

	public ShowClientsResponse showClients(ShowClientsRequest showClientsRequest) throws ClientViewerException {
		
		List<String> clients = new ArrayList<String>();
		clients.add("Freecharge");
		clients.add("SomeCompany");
		clients.add("DashBoard");
		List<ClientDetailsSummary> allClients = new ArrayList<ClientDetailsSummary>();
		for(String client : clients){
			ClientDetailsSummary cds = new ClientDetailsSummary();
			List<String> apis = new ArrayList<String>();
			apis.add("enablePromotionalAccount");
			apis.add("getParkedVoucherBalanceDetails");
			cds.setApiList(apis);
			cds.setAuthEnable(AuthEnable.ENABLED);
			cds.setClientContext("Creating"+ client);
			cds.setClientKey("3d323dcc%24");
			cds.setClientName(client);
			cds.setClientStatus(ClientStatus.ACTIVE);
			cds.setCreatedBy("aaquib.javed@snapdeal.com");
			cds.setCreatedTime(new Date());
			cds.setUpdatedBy("aaquib.javed@snapdeal.com");
			cds.setUpdatedTime(new Date());
			cds.getVersionNo();
			allClients.add(cds);
				
		}
		
		ShowClientsResponse showClientsResponse = new ShowClientsResponse(); 
		
		WalletClients walletClients = new  WalletClients();
		walletClients.setClients(allClients);
		walletClients.setTargetApplication(TargetApplications.WALLET.toString());
		
		showClientsResponse.setWalletClients(walletClients);
		return showClientsResponse;
	}
	
	public void addClientsToUser(String userId, String clientName, String application) throws ClientViewerException{
		ClientViewerEntity entity = new ClientViewerEntity();
		entity.setUserId(userId);
		entity.setClientName(clientName);
		entity.setTargetApplication(application);
		clientViewerService.insertClientViewer(entity);
		
	}
	
	
	public void updateClientForUser(String userId, String clientName, String application) throws ClientViewerException{
		ClientViewerEntity entity = new ClientViewerEntity();
		entity.setUserId(userId);
		entity.setClientName(clientName);
		entity.setTargetApplication(application);
		clientViewerService.updateClientForUser(entity);
		
	}

	
	public GenericOnBoardClientResponse genericOnBoardClient(GenericOnBoardClientRequest request) throws ClientViewerException {
		
		String targetApplication = request.getTargetApplication();
		
		Boolean matchedTargetApplication = Boolean.FALSE;
		
		GenericOnBoardClientResponse response = new GenericOnBoardClientResponse();
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
			matchedTargetApplication  = Boolean.TRUE;
			try {
				sdMoneyAdminClient.onBoardClient(request.getOnBoardClientRequest());
			} catch (SDMoneyAuthException sdme) {
	         log.info( "SDMoneyAuthException in onBoardClient, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
	         throw new ClientViewerException(sdme.getErrorCode()
						.getErrorCode().toString(), sdme.getMessage());
			}
			response.setMessage("SDMoneyAdminClient successfully onBoarded!");
		}
		
		String token = servletRequest.getHeader("token");
		String userId = tokenService.getEmailFromToken(token);
		
		if(matchedTargetApplication.equals(Boolean.TRUE && response.getMessage() != null)) {
			List<String> addUsers = request.getAddUsers();

			for(String addUser : addUsers) {
				ClientViewerEntity  entity = new ClientViewerEntity();
				entity.setClientContext(request.getClientContext());
				entity.setClientName(request.getClientName());
				entity.setCreatedBy(userId);
				entity.setCreatedOn(new SimpleDateFormat("dd-MM-yyyy").format(new Date()));
				entity.setRemarks(request.getRemarks());
				entity.setSourceApplication(request.getSourceApplication());
				entity.setTargetApplication(request.getTargetApplication());
				entity.setUserId(addUser);
				clientViewerService.insertClientViewer(entity);
			}

			List<String> removeUsers = request.getRemoveUsers();
			for(String removeUser : removeUsers) {
				ClientViewerEntity  entity = new ClientViewerEntity();
				entity.setUserId(removeUser);
				entity.setClientName(request.getClientName());
				entity.setTargetApplication(request.getTargetApplication());
				clientViewerService.deleteUser(entity);
			}
		}
		return  response;
	}
	
	public GetClientDetailsResponse getClientDetails(GetClientDetailsRequest request) throws ClientViewerException{
		
		GetClientDetailsResponse response = new GetClientDetailsResponse();
		String targetApplication = request.getTargetApplication();
		Boolean matchedTargetApplication = Boolean.FALSE;
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
			matchedTargetApplication  = Boolean.TRUE;
			GetClientHistoryRequest getClientHistoryRequest = new GetClientHistoryRequest();
			GetClientHistoryResponse getClientHistoryResponse = new GetClientHistoryResponse();

			getClientHistoryRequest.setClientName(request.getClientName());

			ClientSummary clientSummary = new ClientSummary();
			
			
			List<String> apis = new ArrayList<String>();
			apis.add("enablePromotionalAccount");
			apis.add("getParkedVoucherBalanceDetails");
			
			clientSummary.setApiList(apis);
			clientSummary.setAuthEnable(AuthEnable.DISABLED);
			clientSummary.setClientContext("SomeReasonWeDon'tKnow ");
			clientSummary.setClientName(request.getClientName());
			clientSummary.setClientStatus(ClientStatus.ACTIVE);
			clientSummary.setCreatedBy("aaquib.javed@snapdeal.com");
			clientSummary.setCreatedTime(new Date());
			clientSummary.setUpdatedBy("aaquib.javed@snapdeal.com");
			clientSummary.setUpdatedTime(new Date());
			clientSummary.setVersionNo(0);
			
			response.setClientSummary(clientSummary);
		}
		
		ClientViewerEntity e = new ClientViewerEntity();
		e.setClientName(request.getClientName());
		e.setTargetApplication(request.getTargetApplication());
		List<String> users = clientViewerService.getUsersForClient(e);
		response.setUsers(users);
		return response;
		
	}
	
	public GenericUpdateClientStatusResponse genericUpdateClientStatus(GenericUpdateClientStatusRequest request) throws ClientViewerException {
		
		String targetApplication = request.getTargetApplication();
		Boolean matchedTargetApplication = Boolean.FALSE;
		
		GenericUpdateClientStatusResponse response = new GenericUpdateClientStatusResponse();
		
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
			matchedTargetApplication  = Boolean.TRUE;
			UpdateClientStatusRequest updateClientStatusRequest = new UpdateClientStatusRequest();
			UpdateClientStatusResponse updateClientStatusResponse = new UpdateClientStatusResponse();

			ClientSummary clientSummary = new ClientSummary();
			
			
			List<String> apis = new ArrayList<String>();
			apis.add("enablePromotionalAccount");
			apis.add("getParkedVoucherBalanceDetails");
			
			clientSummary.setApiList(apis);
			clientSummary.setAuthEnable(AuthEnable.DISABLED);
			clientSummary.setClientContext("SomeReasonWeDon'tKnow ");
			clientSummary.setClientName(request.getUpdateClientStatusRequest().getClientName());
			clientSummary.setClientStatus(ClientStatus.INACTIVE);
			clientSummary.setCreatedBy("aaquib.javed@snapdeal.com");
			clientSummary.setCreatedTime(new Date());
			clientSummary.setUpdatedBy("aaquib.javed@snapdeal.com");
			clientSummary.setUpdatedTime(new Date());
			clientSummary.setVersionNo(request.getUpdateClientStatusRequest().getVersion());
			
			response.setClientDetails(clientSummary);
			response.setMessage("Status of the client " + clientSummary.getClientName() + " successfully updated!");
		}
		return response;
	}

	public GenericUpdateSecretKeyResponse genericUpdateSecretKey(GenericUpdateSecretKeyRequest request) throws ClientViewerException {
		
		String targetApplication = request.getTargetApplication();
		Boolean matchedTargetApplication = Boolean.FALSE;
		
		GenericUpdateSecretKeyResponse response = new GenericUpdateSecretKeyResponse();
		
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
			matchedTargetApplication  = Boolean.TRUE;
			UpdateSecretKeyRequest updateSecretKeyRequest = new UpdateSecretKeyRequest();
			UpdateSecretKeyResponse updateSecretKeyResponse = new UpdateSecretKeyResponse();
			
			ClientSummary clientSummary = new ClientSummary();
			
			
			List<String> apis = new ArrayList<String>();
			apis.add("enablePromotionalAccount");
			apis.add("getParkedVoucherBalanceDetails");
			
			clientSummary.setApiList(apis);
			clientSummary.setAuthEnable(AuthEnable.DISABLED);
			clientSummary.setClientContext("SomeReasonWeDon'tKnow ");
			clientSummary.setClientName(request.getUpdateSecretKeyRequest().getClientName());
			clientSummary.setClientStatus(ClientStatus.ACTIVE);
			clientSummary.setCreatedBy("aaquib.javed@snapdeal.com");
			clientSummary.setCreatedTime(new Date());
			clientSummary.setUpdatedBy("aaquib.javed@snapdeal.com");
			clientSummary.setUpdatedTime(new Date());
			clientSummary.setVersionNo(request.getUpdateSecretKeyRequest().getVersion()+ 1);
			response.setClientDetails(clientSummary);
			response.setMessage("Secret Key of the client " + clientSummary.getClientName() + " successfully updated!");
		}
		return response;
	}
	
	
	public List<String> getSupportedTargetApplications() {
		List<String> supportedApplications = new ArrayList<String>();
		supportedApplications.add(TargetApplications.WALLET.toString());
		return  supportedApplications;
	}

}
*/