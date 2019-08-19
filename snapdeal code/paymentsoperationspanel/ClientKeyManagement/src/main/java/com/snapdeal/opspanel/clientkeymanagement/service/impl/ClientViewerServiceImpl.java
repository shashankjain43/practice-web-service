package com.snapdeal.opspanel.clientkeymanagement.service.impl;

import java.util.ArrayList;
import java.util.List;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.clientkeymanagement.dao.ClientViewerDao;
import com.snapdeal.opspanel.clientkeymanagement.entity.ClientViewerEntity;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewerService;

@Slf4j
@Service("clientViewerService")
public class ClientViewerServiceImpl implements ClientViewerService {

	@Autowired
	ClientViewerDao clientViewerDao;
	
	public void insertClientViewer(ClientViewerEntity entity) throws ClientViewerException{
		try {
			clientViewerDao.insertClientViewer(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3001", "Error while inseriting client in database!");
		}		
	}

	public void deleteUser(ClientViewerEntity entity) throws ClientViewerException{
		try {
			clientViewerDao.deleteUser(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3002", "Error while deleting client in database!");
		}	
		
	}
	
	public List<ClientViewerEntity> getAllClientViwer() throws ClientViewerException{
		List<ClientViewerEntity> allClients = new ArrayList<ClientViewerEntity>();
		try {
			allClients = clientViewerDao.getAllClientViwer();
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3003", "Error while getting all clients in database!");
		}	
		return allClients;
	}

	public List<ClientViewerEntity> getClientsforUser(String userId) throws ClientViewerException {
		List<ClientViewerEntity> clientsForUser = new ArrayList<ClientViewerEntity>();
		try {
			clientsForUser = clientViewerDao.getClientsforUser(userId);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3004", "Error while getting clients for user in database!");
		}	
		return clientsForUser;
	}
	
	public List<String> getClientsForTargetApplication(String targetApplication) throws ClientViewerException {
		List<String> clientsForTargetApplication = new ArrayList<String>();
		try {
			clientsForTargetApplication = clientViewerDao.getClientsForTargetApplication(targetApplication);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3005", "Error while getting clients for targetApplication in database!");
		}	
		return clientsForTargetApplication;
	}

	public List<String> getUsersForClient(ClientViewerEntity entity) throws ClientViewerException{
		
		List<String> usersForClient = new ArrayList<String>();
		try {
			usersForClient = clientViewerDao.getUsersForClient(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3006", "Error while getting users for client in database!");
		}	
		return usersForClient;
	}

	public void updateClientForUser(ClientViewerEntity entity) throws ClientViewerException{
		try {
			clientViewerDao.updateClientForUser(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3007", "Error while updating clients for user in database!");
		}
		
	}

	public void updateUserForClient(ClientViewerEntity entity) throws ClientViewerException{
		
		try {
			clientViewerDao.updateUserForClient(entity);
		} catch (Exception e) {
			log.info("Error from database " + ExceptionUtils.getFullStackTrace(e));
			throw new ClientViewerException("MT-3008", "Error while updating users for client in database!");
		}
	}

}
