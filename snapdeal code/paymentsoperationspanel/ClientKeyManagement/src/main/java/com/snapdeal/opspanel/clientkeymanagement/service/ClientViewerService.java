package com.snapdeal.opspanel.clientkeymanagement.service;

import java.util.List;

import com.snapdeal.opspanel.clientkeymanagement.entity.ClientViewerEntity;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;

public interface ClientViewerService {

	public void insertClientViewer(ClientViewerEntity entity) throws ClientViewerException;
	
	public void deleteUser(ClientViewerEntity entity) throws ClientViewerException;

	public List<ClientViewerEntity> getAllClientViwer() throws ClientViewerException;

	public List<ClientViewerEntity> getClientsforUser(String userId) throws ClientViewerException;
	
	public List<String> getClientsForTargetApplication(String targetApplication) throws ClientViewerException;

	public List<String> getUsersForClient(ClientViewerEntity entity) throws ClientViewerException;

	public void updateClientForUser(ClientViewerEntity entity) throws ClientViewerException;

	public void updateUserForClient(ClientViewerEntity entity) throws ClientViewerException;

}
