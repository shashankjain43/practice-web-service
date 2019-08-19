package com.snapdeal.opspanel.clientkeymanagement.dao;
import java.util.List;

import com.snapdeal.opspanel.clientkeymanagement.entity.ClientViewerEntity;

public interface ClientViewerDao {

	public void insertClientViewer(ClientViewerEntity entity);
	
	public void deleteUser(ClientViewerEntity entity);
	
	public List<ClientViewerEntity> getAllClientViwer();
	
	public List<ClientViewerEntity> getClientsforUser(String userId);
	
	public List<String> getClientsForTargetApplication(String targetApplication);
	
	public List<String> getUsersForClient(ClientViewerEntity entity);
	
	public void updateClientForUser(ClientViewerEntity entity);
	
	public void updateUserForClient(ClientViewerEntity entity);
	
	
}
