package com.snapdeal.opspanel.promotion.dao;

import java.util.List;

import com.snapdeal.opspanel.promotion.model.ClientEntity;
import com.snapdeal.opspanel.promotion.model.RoleEntity;
import com.snapdeal.opspanel.promotion.model.UsersEntity;



public interface AccessDao {

	
	public String getUserRole(String emailId);
	
	public void addUserRole(RoleEntity roleEntity);
	
	public void updateUserRole(RoleEntity roleEntity);
	
	public void insertClient(ClientEntity clientEntity);
		
	public void deletePermissions(ClientEntity clientEntity);
	
	public List<UsersEntity> getUsers();
	
	public List<ClientEntity> getAllClients();
	
	public void deleteClient(String clientId);
	
	public void deleteClientFromUser(String clientId);
	
	public List<ClientEntity> getAllPermissions(String clientId);

	public List<ClientEntity> checkPermission(ClientEntity entity);
	
	public void updateInstrument(ClientEntity clientEntity);
	
	public ClientEntity checkForExistingPermission(ClientEntity clientEntity);
	
}
