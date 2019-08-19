package com.snapdeal.payments.view.commons.service;


import com.snapdeal.payments.view.commons.exception.service.ValidationException;
import com.snapdeal.payments.view.commons.request.CreateClientRequest;
import com.snapdeal.payments.view.commons.request.GetAllClientsRequest;
import com.snapdeal.payments.view.commons.request.UpdateClientStatusRequest;
import com.snapdeal.payments.view.commons.response.CreateClientResponse;
import com.snapdeal.payments.view.commons.response.GetAllClientsResponse;
import com.snapdeal.payments.view.commons.response.UpdateClientStatusResponse;


public interface IClientService {

   /**
    * This function will create client in database
    * 
    * @param request
    * @return
    * @throws ValidationException
    */
   public CreateClientResponse createClient(CreateClientRequest request) throws ValidationException;



	public UpdateClientStatusResponse updateClientStatus(
			UpdateClientStatusRequest request);



   public GetAllClientsResponse getAllClients(GetAllClientsRequest request);


}
