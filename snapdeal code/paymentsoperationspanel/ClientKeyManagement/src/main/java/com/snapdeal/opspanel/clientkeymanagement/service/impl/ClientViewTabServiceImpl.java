package com.snapdeal.opspanel.clientkeymanagement.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;




//import com.snapdeal.dashboard.exception.WalletServiceException;
import com.snapdeal.opspanel.clientkeymanagement.entity.ClientViewerEntity;
import com.snapdeal.opspanel.clientkeymanagement.enums.TargetApplications;
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
import com.snapdeal.opspanel.clientkeymanagement.response.WalletClients;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewTabService;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewerService;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.authorize.core.exception.SDMoneyAuthException;
import com.snapdeal.payments.authorize.core.model.ClientDetailsSummary;
import com.snapdeal.payments.authorize.core.model.ClientSummary;
import com.snapdeal.payments.authorize.core.model.GetAllClientRequest;
import com.snapdeal.payments.authorize.core.model.GetAllClientResponse;
/*import com.snapdeal.payments.authorize.core.model.GetClientByNameRequest;
import com.snapdeal.payments.authorize.core.model.GetClientByNameResponse;*/
import com.snapdeal.payments.authorize.core.model.GetClientHistoryRequest;
import com.snapdeal.payments.authorize.core.model.GetClientHistoryResponse;
import com.snapdeal.payments.authorize.core.model.GetLatestClientDetailsRequest;
import com.snapdeal.payments.authorize.core.model.GetLatestClientDetailsResponse;
import com.snapdeal.payments.authorize.core.model.OnBoardClientRequest;
import com.snapdeal.payments.authorize.core.model.UpdateClientStatusRequest;
import com.snapdeal.payments.authorize.core.model.UpdateClientStatusResponse;
import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyRequest;
import com.snapdeal.payments.authorize.core.model.UpdateSecretKeyResponse;
import com.snapdeal.payments.sdmoney.admin.client.SDMoneyAdminClient;

@Slf4j
@Service("clientViewerTabService")
public class ClientViewTabServiceImpl implements ClientViewTabService {

	
	@Autowired
	ClientViewerService clientViewerService;
	
	@Autowired
	SDMoneyAdminClient sdMoneyAdminClient;
	
	@Autowired
	HttpServletRequest servletRequest;

	@Autowired
	TokenService tokenService;
	
	public ShowClientsResponse showClients(ShowClientsRequest showClientsRequest) throws ClientViewerException {
		
		List<ClientViewerEntity> clients = new ArrayList<ClientViewerEntity>();
		clients = clientViewerService.getClientsforUser(showClientsRequest.getUserId());
		
		ShowClientsResponse showClientsResponse = new ShowClientsResponse();
		WalletClients walletClients = new WalletClients();
		List<ClientDetailsSummary> clientSummmary = new ArrayList<ClientDetailsSummary>();
		
		walletClients.setClients(clientSummmary);
		showClientsResponse.setWalletClients(walletClients);
		
		
		if(clients != null && clients.size() != 0) {
			for(ClientViewerEntity clientName : clients) {
				String targetApplication = clientName.getTargetApplication();
				if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
					
					walletClients.setTargetApplication(TargetApplications.WALLET.toString());
					
					GetLatestClientDetailsRequest request = new GetLatestClientDetailsRequest();
					
					request.setClientName(clientName.getClientName());
					
					GetLatestClientDetailsResponse response = new GetLatestClientDetailsResponse();
					
					try {
						/*Key will be shown*/
						response = sdMoneyAdminClient.getLatestClientDetails(request);
					} catch (SDMoneyAuthException sdme) {
						log.info( "SDMoneyAuthException in getClientByName, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
						throw new ClientViewerException(sdme.getErrorCode()
								.getErrorCode().toString(), "Unable to fetch clients! SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");
					}
					ClientDetailsSummary client = new ClientDetailsSummary();
					
					if(response != null){
						client = response.getClientDetails();
						
					}
					if(client != null){
						clientSummmary.add(client);
					}
					
				}
			}
		}
		else {
			throw new ClientViewerException("MT-5151", "You don't have access to view any clients!   ");
		}
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

	
	public GenericOnBoardClientResponse genericOnBoardClient(GenericOnBoardClientRequest request) throws ClientViewerException, OpsPanelException {
		
		String targetApplication = request.getTargetApplication();
		
		Boolean matchedTargetApplication = Boolean.FALSE;
		
		ClientViewerEntity e = new ClientViewerEntity();
		e.setClientName(request.getClientName());
		e.setTargetApplication(request.getTargetApplication());
		List<String> users = clientViewerService.getUsersForClient(e);
		
		int alreadyExisting;
		if(users != null){
			alreadyExisting = users.size();
		} else {
			alreadyExisting =0;
		}
		
		int toBeAdded;
		int toBeRemoved;
		if(request.getAddUsers() != null){
			toBeAdded = request.getAddUsers().size();
		} else {
			toBeAdded = 0;
		}
		
		if(request.getRemoveUsers() != null) {
			toBeRemoved = request.getRemoveUsers().size();
		} else {
			toBeRemoved = 0;
		}
		
		if(alreadyExisting + toBeAdded - toBeRemoved <= 0) {
			throw new ClientViewerException("CAN_NOT_PROCESS", "At least one user must have viewing rights for this client! ");
		}
		
		
		if(!request.isNewClient()) {
			List<String> usersToRemove = new ArrayList<String>();
			usersToRemove = request.getRemoveUsers();
			
			if(usersToRemove != null && usersToRemove.size() !=0){				
				for(String userId : usersToRemove) {
					if(users != null && !users.contains(userId)){
						throw new ClientViewerException("CAN_NOT_REMOVE_USER", "Can not remove a user which doesn't already has viewing access! ");
					}
				}

			}
		}
		
		GenericOnBoardClientResponse response = new GenericOnBoardClientResponse();
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
			matchedTargetApplication  = Boolean.TRUE;
			
			GetClientHistoryRequest getClientHistoryRequest = new GetClientHistoryRequest();
			getClientHistoryRequest.setClientName(request.getClientName());
			GetClientHistoryResponse getClientHistoryResponse = new GetClientHistoryResponse();
			try {
				getClientHistoryResponse = sdMoneyAdminClient.getClientHistory(getClientHistoryRequest);
			} catch (SDMoneyAuthException sdme) {
				log.info( "SDMoneyAuthException in getClientHistory, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
				throw new ClientViewerException(sdme.getErrorCode()
						.getErrorCode().toString(), "SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");
			}
			
			if(request.isNewClient() && getClientHistoryResponse != null && getClientHistoryResponse.getClientList() != null && getClientHistoryResponse.getClientList().size() != 0) {
				throw new ClientViewerException("CLIENT_ALREADY_EXISTS", "Can not onboard as a client with the same name already exists! However you can upadte the existing client! ");
			}
			OnBoardClientRequest onBoardClientRequest =  new OnBoardClientRequest();
			onBoardClientRequest.setClientName(request.getOnBoardClientRequest().getClientName());
			onBoardClientRequest.setAuthEnable(request.getOnBoardClientRequest().getAuthEnable());
			onBoardClientRequest.setClientContext(request.getOnBoardClientRequest().getClientContext());
			onBoardClientRequest.setAuthApis(request.getOnBoardClientRequest().getAuthApis());
			
			try {	
				sdMoneyAdminClient.onBoardClient(onBoardClientRequest);
			} catch (SDMoneyAuthException sdme) {
				log.info( "SDMoneyAuthException in onBoardClient, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
				throw new ClientViewerException(sdme.getErrorCode()
						.getErrorCode().toString(), "Unable to onboard client! : SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");
			}

			if(request.isNewClient()){
				response.setMessage("Wallet Client successfully onBoarded! ");
			} else {
				response.setMessage("Wallet Client successfully updated! ");
			}
			
		}
		
		String token = servletRequest.getHeader("token");
		String userId = tokenService.getEmailFromToken(token);
		
		if(matchedTargetApplication.equals(Boolean.TRUE)) {
			List<String> addUsers = request.getAddUsers();

			if(addUsers != null){
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
			}


			List<String> removeUsers = request.getRemoveUsers();
			if(removeUsers != null){
				for(String removeUser : removeUsers) {
					ClientViewerEntity  entity = new ClientViewerEntity();
					entity.setUserId(removeUser);
					entity.setClientName(request.getClientName());
					entity.setTargetApplication(request.getTargetApplication());
					clientViewerService.deleteUser(entity);
				}
			}
			
		}
		return response;
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

			try {
				//Key will not be shown
				getClientHistoryResponse = sdMoneyAdminClient.getClientHistory(getClientHistoryRequest);
			} catch (SDMoneyAuthException sdme) {
				log.info( "SDMoneyAuthException in getLatestClientDetails, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
				throw new ClientViewerException(String.valueOf(sdme.getErrorCode()
						.getErrorCode()), "SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");
			}
			List<ClientSummary> clients = new ArrayList<ClientSummary>();
			if(getClientHistoryResponse != null) {
				clients = getClientHistoryResponse.getClientList();
			}
			if(clients != null && clients.size() != 0) {
				response.setClientSummary(clients.get(0));
			}
			
		}
		
		ClientViewerEntity e = new ClientViewerEntity();
		e.setClientName(request.getClientName());
		e.setTargetApplication(request.getTargetApplication());
		
		List<String> users = clientViewerService.getUsersForClient(e);
		response.setUsers(users);
		
		return response;
		
	}
	
	public List<String> getClientsForTargetApplication(GetTargetApplicationRequest request) throws ClientViewerException {
		String targetApplication = new String();
		targetApplication= request.getTargetApplication();
		List<String> clientList= new ArrayList<String>();
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())){
			GetAllClientResponse response = new GetAllClientResponse();
			try {
				response= sdMoneyAdminClient.getAllClients(new GetAllClientRequest());
			}  catch (SDMoneyAuthException sdme) {
				log.info( "SDMoneyAuthException in getAllClients, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
				throw new ClientViewerException(String.valueOf(sdme.getErrorCode()
						.getErrorCode()), "SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");
			}
			List<ClientSummary> allClients = new ArrayList<ClientSummary>();
			if(response != null){
				allClients = response.getClients();
			}
			if(allClients != null){
				for(ClientSummary clientSummary: allClients) {
					clientList.add(clientSummary.getClientName());
				}
			}
		}
		return clientList;
		
	}
	
	public GenericUpdateClientStatusResponse genericUpdateClientStatus(GenericUpdateClientStatusRequest request) throws ClientViewerException {
		
		String targetApplication = request.getTargetApplication();
		Boolean matchedTargetApplication = Boolean.FALSE;
		
		GenericUpdateClientStatusResponse response = new GenericUpdateClientStatusResponse();
		
		if(targetApplication.equalsIgnoreCase(TargetApplications.WALLET.toString())) {
			matchedTargetApplication  = Boolean.TRUE;
			UpdateClientStatusRequest updateClientStatusRequest = new UpdateClientStatusRequest();
			UpdateClientStatusResponse updateClientStatusResponse = new UpdateClientStatusResponse();
			updateClientStatusRequest = request.getUpdateClientStatusRequest();
			try {
				updateClientStatusResponse = sdMoneyAdminClient.updateClientStatus(updateClientStatusRequest);

			} catch (SDMoneyAuthException sdme) {
				log.info( "SDMoneyAuthException in updateClientStatus, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
				response.setMessage("Status of the client " + response.getClientDetails().getClientName() + " not updated! ");
				throw new ClientViewerException(String.valueOf(sdme.getErrorCode()
						.getErrorCode()), "Unable to update the client status! SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");

			}
			ClientSummary client = new ClientSummary();
			if(updateClientStatusResponse != null){
				client = updateClientStatusResponse.getClientDetails();
			}
			response.setClientDetails(client);
			response.setMessage("Status of the client '" + response.getClientDetails().getClientName() + "' successfully updated! ");
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
			updateSecretKeyRequest = request.getUpdateSecretKeyRequest();
			try {
				updateSecretKeyResponse = sdMoneyAdminClient.updateSecretKey(updateSecretKeyRequest);
			} catch (SDMoneyAuthException sdme) {
				log.info( "SDMoneyAuthException in updateSecretKey, Error Code : " + sdme.getErrorCode().getErrorCode().toString() + "Error Message : " + sdme.getMessage() );
				response.setMessage("Secret Key of the client " + response.getClientDetails().getClientName() + " not updated! ");
				throw new ClientViewerException(String.valueOf(sdme.getErrorCode()
						.getErrorCode()), "Unable to update the client secret key! SDmmoneyAdminClient Exception: "+sdme.getMessage(), "SDMoneyAdminClient");

			}
			ClientSummary client = new ClientSummary();
			if(updateSecretKeyResponse != null){
				client = updateSecretKeyResponse.getClientDetails();
			}
			response.setClientDetails(client);
			response.setMessage("Secret Key of the client '" + response.getClientDetails().getClientName() + "' successfully updated! ");
		}
		return response;
	}
	
	
	public List<String> getSupportedTargetApplications() {
		List<String> supportedApplications = new ArrayList<String>();
		supportedApplications.add(TargetApplications.WALLET.toString());
		return  supportedApplications;
	}


}


