package com.snapdeal.opspanel.promotion.service;

import java.util.List;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.enums.InstrumentType;
import com.snapdeal.opspanel.promotion.model.ClientRequestModel;
import com.snapdeal.opspanel.promotion.model.ClientResponseModel;
import com.snapdeal.opspanel.promotion.model.DeleteClientRequestModel;
import com.snapdeal.opspanel.promotion.model.PermissionsModel;

public interface BulkPromotionAcccessService {

	public String getUsersRoles(String emailId);

	public void addClient(ClientRequestModel clientRequestModel) throws OpsPanelException;

	public List<ClientResponseModel> getAllClients() throws OpsPanelException;

	public void deleteSpecificPermission(DeleteClientRequestModel deleteClientRequestModel) throws OpsPanelException;

	public void deleteClient(String emailId) throws OpsPanelException;

	public PermissionsModel getAccessPermissionsList(String email) throws OpsPanelException;	

	public boolean checkPermission(String email,String merchant,String corpId, InstrumentType type);

	public boolean mailValidator(String email);
}
