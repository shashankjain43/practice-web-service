package com.snapdeal.opspanel.userpanel.service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.FraudManagementRequest;
import com.snapdeal.opspanel.userpanel.response.FraudManagementResponse;
import com.snapdeal.opspanel.userpanel.response.GenericResponse;

public interface FraudManagementService {

	public GenericResponse performFraudActions(FraudManagementResponse fraudManagementResponse,FraudManagementRequest fraudManagementRequest) throws InfoPanelException, OpsPanelException;
	
	public FraudManagementResponse interceptFraudActions(FraudManagementRequest fraudManagementRequest) throws InfoPanelException;
	
}
