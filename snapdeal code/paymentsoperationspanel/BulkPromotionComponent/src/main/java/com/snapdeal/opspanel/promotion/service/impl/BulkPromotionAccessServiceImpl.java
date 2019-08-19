package com.snapdeal.opspanel.promotion.service.impl;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.TimeZone;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.google.gson.Gson;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.dao.AccessDao;
import com.snapdeal.opspanel.promotion.dao.AuditAccessDao;
import com.snapdeal.opspanel.promotion.enums.AuditOperationMode;
import com.snapdeal.opspanel.promotion.enums.InstrumentType;
import com.snapdeal.opspanel.promotion.model.AccessAuditEntity;
import com.snapdeal.opspanel.promotion.model.ClientEntity;
import com.snapdeal.opspanel.promotion.model.ClientRequestModel;
import com.snapdeal.opspanel.promotion.model.ClientResponseModel;
import com.snapdeal.opspanel.promotion.model.DeleteClientRequestModel;
import com.snapdeal.opspanel.promotion.model.MerchantModel;
import com.snapdeal.opspanel.promotion.model.PermissionsModel;
import com.snapdeal.opspanel.promotion.model.RoleEntity;
import com.snapdeal.opspanel.promotion.model.UserAccountModel;
import com.snapdeal.opspanel.promotion.model.UsersEntity;
import com.snapdeal.opspanel.promotion.service.BulkPromotionAcccessService;

import lombok.extern.slf4j.Slf4j;

@Service("BulkPromotionAccessService")
@Slf4j
public class BulkPromotionAccessServiceImpl implements BulkPromotionAcccessService {

	@Autowired
	AccessDao accessDao;

	@Autowired
	AuditAccessDao auditAccessDao;
	
	@Override
	public PermissionsModel getAccessPermissionsList(String clientId) throws OpsPanelException {


		PermissionsModel permissionsModel=new PermissionsModel();

		ArrayList<MerchantModel> merchantsArrayList=new ArrayList<>();


		try {

			List<ClientEntity> clientEntitiesList=accessDao.getAllPermissions(clientId);

			for(ClientEntity clientEntity:clientEntitiesList){

				MerchantModel merchantModel=findAndReturnMerchant(clientEntity.getMerchantName(), merchantsArrayList);


				if(permissionsModel.getInstrumentType()==null)
					permissionsModel.setInstrumentType(clientEntity.getInstrumentType());

				if(merchantModel==null){


					merchantModel=new MerchantModel();
					merchantModel.setMerchantName(clientEntity.getMerchantName());

					List<UserAccountModel> corporateIds=new  ArrayList<>();
					
					corporateIds.add(new Gson().fromJson(clientEntity.getCorpId(),UserAccountModel.class));

					merchantModel.setCorporateIds(corporateIds);

					merchantsArrayList.add(merchantModel);

				} else {

					merchantModel.getCorporateIds().add(new Gson().fromJson(clientEntity.getCorpId(),UserAccountModel.class));

				}

			}

			permissionsModel.setMerchants(merchantsArrayList);


		} catch (Exception e) {
		   log.info( "Exception in getAccessPermissionList " + e );
			throw new OpsPanelException("MT-1102",e.getMessage());

		}

		return permissionsModel;
	}

	@Override
	public void addClient(ClientRequestModel clientRequestModel) throws OpsPanelException {

		try {

			String clientId=clientRequestModel.getClientId();
			String instrumentType=clientRequestModel.getInstrumentType();

			RoleEntity roleEntity=new RoleEntity();
			roleEntity.setClientId(clientId);

		   AccessAuditEntity accessAuditEntity=new 	AccessAuditEntity();
		   accessAuditEntity.setClientEmail(clientId);
		   
			if(accessDao.getUserRole(clientRequestModel.getClientId())==null){

				if(clientRequestModel.getIsSuperUser()){
					roleEntity.setUserRole("Superuser");
					accessAuditEntity.setClientRole("Superuser");
				}

				else {
					roleEntity.setUserRole("Bulktool");
					accessAuditEntity.setClientRole("Bulktool");

				}

				accessDao.addUserRole(roleEntity);
				accessAuditEntity.setOperationMode(AuditOperationMode.ADD_CLIENT);
				//auditing while adding the client
				auditClientDetails(accessAuditEntity);

			} else{

				if(clientRequestModel.getIsSuperUser()){
					roleEntity.setUserRole("Superuser");
					accessAuditEntity.setClientRole("Superuser");

				}

				else {
					roleEntity.setUserRole("Bulktool");
					accessAuditEntity.setClientRole("Bulktool");

				}

				accessDao.updateUserRole(roleEntity);


				ClientEntity clientEntity=new ClientEntity();
				clientEntity.setClientId(clientId);
				clientEntity.setInstrumentType(instrumentType);

				accessAuditEntity.setInstrumentName(instrumentType);				
				accessDao.updateInstrument(clientEntity);

			}

			for(MerchantModel merchantModel:clientRequestModel.getMerchants()){


				for(UserAccountModel corpId:merchantModel.getCorporateIds()){

					ClientEntity clientEntity=new ClientEntity();

					clientEntity.setClientId(clientId);
					
					String corpIdString=new Gson().toJson(corpId);
					
					clientEntity.setCorpId(corpIdString.replace("\\", "\\\\"));
					clientEntity.setMerchantName(merchantModel.getMerchantName());
					accessAuditEntity.setMerchantName(merchantModel.getMerchantName());


					if(accessDao.checkForExistingPermission(clientEntity)==null){
						clientEntity.setInstrumentType(instrumentType);
						accessDao.insertClient(clientEntity);
						
						accessAuditEntity.setOperationMode(AuditOperationMode.ADD_PERMISSION);
						accessAuditEntity.setCorporateData(corpIdString);
						auditClientDetails(accessAuditEntity);
					}

				}

			}


		} catch (Exception e) {
		   log.info( "Exception while inserting permission for clients " + e );
			throw new OpsPanelException("MT-1102","Error in inserting permission for client");
		}


	}

	@Override
	public void deleteSpecificPermission(DeleteClientRequestModel deleteClientRequestModel) throws OpsPanelException {

		try {

			for(MerchantModel merchantModel:deleteClientRequestModel.getMerchants()){

				for(UserAccountModel corpId:merchantModel.getCorporateIds()){

					String corpIdString=new Gson().toJson(corpId).replace("\\", "\\\\");

					
					AccessAuditEntity accessAuditEntity=new AccessAuditEntity();
					accessAuditEntity.setClientEmail(deleteClientRequestModel.getClientId());
					accessAuditEntity.setClientRole(accessDao.getUserRole(deleteClientRequestModel.getClientId()));
					accessAuditEntity.setOperationMode(AuditOperationMode.DELETE_PERMISSION);
					accessAuditEntity.setCorporateData(corpIdString);
					accessAuditEntity.setMerchantName(merchantModel.getMerchantName());
					auditClientDetails(accessAuditEntity);

					
					ClientEntity clientEntity=new ClientEntity();
					clientEntity.setClientId(deleteClientRequestModel.getClientId());
					clientEntity.setCorpId(corpIdString);
					
					clientEntity.setMerchantName(merchantModel.getMerchantName());
					accessDao.deletePermissions(clientEntity);	        		
				}   		
			}

		} catch (Exception e) {
		   log.info( "Exceptioon while deleting specificClientPermission " + e);

			throw new OpsPanelException("MT-1104", e.getMessage());
		}
	}

	@Override
	public void deleteClient(String clientId) throws OpsPanelException {

		try {

			accessDao.deleteClient(clientId);
			accessDao.deleteClientFromUser(clientId);
			
			AccessAuditEntity accessAuditEntity=new AccessAuditEntity();
			accessAuditEntity.setClientEmail(clientId);
			accessAuditEntity.setOperationMode(AuditOperationMode.DELETE_CLIENT);
			
			auditClientDetails(accessAuditEntity);
			
			
		} catch (Exception e) {

		   log.info( "Exception while deleting client " + e );
			throw new OpsPanelException("MT-1105", e.getMessage());
		}

	}

	@Override
	public String getUsersRoles(String emailId) {	

		return accessDao.getUserRole(emailId);
	}

	@Override
	public List<ClientResponseModel> getAllClients() throws OpsPanelException {


		List<ClientResponseModel> clientResponseModels=new ArrayList<>();

		try {

			List<UsersEntity> usersEntityList=accessDao.getUsers();

			for(UsersEntity usersEntity:usersEntityList){

				ClientResponseModel clientResponseModel=new ClientResponseModel();
				clientResponseModel.setEmailId(usersEntity.getEmail_id());

				if(usersEntity.getUser_role().equalsIgnoreCase("Superuser"))
					clientResponseModel.setSuperUser(true);
				
				clientResponseModels.add(clientResponseModel);

			}			

			List<ClientEntity> clients =accessDao.getAllClients();

			for (ClientEntity clientEntity:clients){

				String emailId=clientEntity.getClientId();

				ClientResponseModel clientResponseModel=findAndReturnClient(emailId,clientResponseModels);

				if(clientResponseModel==null){

					clientResponseModels.add(returnNewClient(clientEntity));

				} 
				else{

					updateClientResponse(clientResponseModel,clientEntity);
				}

			}



		} catch (Exception e) {
		   log.info( "Exception while getting all clients " + e );
			throw new OpsPanelException("MT-1103",e.getMessage());
		}

		return clientResponseModels;

	}








	private ClientResponseModel returnNewClient(ClientEntity clientEntity){

		ClientResponseModel clientResponseModel=new ClientResponseModel();

		clientResponseModel.setEmailId(clientEntity.getClientId());

		

		clientResponseModel.setInstruments(clientEntity.getInstrumentType());

		List<MerchantModel> merchantsList=new  ArrayList<>();

		MerchantModel merchantModel=new MerchantModel();
		merchantModel.setMerchantName(clientEntity.getMerchantName());

		List<UserAccountModel> corporateIds=new  ArrayList<>();
		corporateIds.add(new Gson().fromJson(clientEntity.getCorpId(),UserAccountModel.class));

		merchantModel.setCorporateIds(corporateIds);

		merchantsList.add(merchantModel);

		clientResponseModel.setMerchants(merchantsList);

		String clientId=accessDao.getUserRole(clientEntity.getClientId());

		if(clientId!=null && clientId.equalsIgnoreCase("Superuser"))
			clientResponseModel.setSuperUser(true);

		return clientResponseModel;
	}


	private void updateClientResponse(ClientResponseModel clientResponseModel,ClientEntity clientEntity){

		MerchantModel merchantModel= findAndReturnMerchant(clientEntity.getMerchantName(),clientResponseModel.getMerchants());

		if(merchantModel!=null){

			merchantModel.getCorporateIds().add(new Gson().fromJson(clientEntity.getCorpId(),UserAccountModel.class));
		} else{

			merchantModel=new MerchantModel();
			merchantModel.setMerchantName(clientEntity.getMerchantName());
			
			
			List<UserAccountModel> corporateIds=new  ArrayList<>();
			
			Gson gson=new Gson();
			
			String jsonString=clientEntity.getCorpId().replace("\\\\","\\");
			
			corporateIds.add(gson.fromJson(jsonString,UserAccountModel.class));

			merchantModel.setCorporateIds(corporateIds);

			if(clientResponseModel.getMerchants()==null){
				ArrayList<MerchantModel> merchantsList=new  ArrayList<>();
				clientResponseModel.setMerchants(merchantsList);
			}
			clientResponseModel.getMerchants().add(merchantModel);
			clientResponseModel.setInstruments(clientEntity.getInstrumentType());
			
		}

	}

	private MerchantModel findAndReturnMerchant(String key,List<MerchantModel> hay){

		if(hay==null)
			return null;
		
		for(MerchantModel merchantModel:hay){

			if(key.equals(merchantModel.getMerchantName()))
				return merchantModel;

		}

		return null;
	}


	private ClientResponseModel findAndReturnClient(String key,List<ClientResponseModel> hay){
		
		if(hay==null)
			return null;

		for(ClientResponseModel client:hay){

			if(client.getEmailId().equals(key))
				return client;

		}


		return null;
	}

	@Override
	public boolean checkPermission(String email, String merchant,
			String corpId, InstrumentType type) {
		ClientEntity entity= new ClientEntity();
		entity.setClientId(email);
		entity.setCorpId(corpId);
		entity.setMerchantName(merchant);
		List<ClientEntity> list=accessDao.checkPermission(entity);
		if (list.isEmpty()) {
			return true;
		} else {
			if(list.get(0).getInstrumentType().contains(type.name()))
				return false;
		}
		return true;
	}

	@Override
	public boolean mailValidator(String email) {

		Pattern allowedMailFormats = 
				Pattern.compile("^[A-Z0-9._%+-]+@(snapdeal|freecharge|vulcanxpress)+\\.[A-Z]{2,6}$", Pattern.CASE_INSENSITIVE);

		Matcher matcher = allowedMailFormats .matcher(email);

		return matcher.find();
	}


	private void auditClientDetails(AccessAuditEntity accessAuditEntity){
		
		
		Calendar cal  = Calendar.getInstance();
		cal.setTimeZone(TimeZone.getTimeZone("IST"));
		accessAuditEntity.setOperationTime(cal.getTime());
		auditAccessDao.auditClientDetails(accessAuditEntity);
	}
	

}
