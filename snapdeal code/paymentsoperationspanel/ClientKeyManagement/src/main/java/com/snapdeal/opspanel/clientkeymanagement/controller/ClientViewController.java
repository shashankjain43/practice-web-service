/*package com.snapdeal.opspanel.clientkeymanagement.controller;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;
import com.snapdeal.opspanel.clientkeymanagement.request.ShowClientsRequest;
import com.snapdeal.opspanel.clientkeymanagement.response.ShowClientsResponse;
import com.snapdeal.opspanel.clientkeymanagement.service.ClientViewTabService;
import com.snapdeal.opspanel.clientkeymanagement.utils.GenericResponse;

@Slf4j
@Controller
@RequestMapping("clientview/")
public class ClientViewController {
	
	@Autowired
	ClientViewTabService clientViewTabService;
	
	@RequestMapping(value = "/getclients", method = RequestMethod.POST)
	public @ResponseBody GenericResponse getClientByName(@RequestBody ShowClientsRequest request)
					throws ClientViewerException {
		String userId = request.getUserId();
		ShowClientsResponse response = new ShowClientsResponse();
		try {
			clientViewTabService.showClients(userId);
		} catch (ClientViewerException e) {
         log.info( "ClientViewerException in getClients " + e );
		}
		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}
	
	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse(walletResponse);
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
	

}
*/