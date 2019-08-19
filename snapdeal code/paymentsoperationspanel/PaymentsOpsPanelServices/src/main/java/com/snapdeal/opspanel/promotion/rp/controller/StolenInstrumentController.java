package com.snapdeal.opspanel.promotion.rp.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.ruleDashboard.utils.RuleDashboardConstants;
import com.snapdeal.opspanel.userpanel.stolenInstrument.request.SubmitRequest;
import com.snapdeal.opspanel.userpanel.stolenInstrument.service.StolenInstrumentService;
import com.snapdeal.payments.dashboard.variableManager.request.AddPrimitiveVariableRequest;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.opspanel.userpanel.stolenInstrument.utils.StolenInstrumentsConstants;

@Controller
@RequestMapping(StolenInstrumentsConstants.STOLEN_INSTRUMENT_PREFIX)
@Slf4j
public class StolenInstrumentController {
	
	@Autowired
	StolenInstrumentService service;
	
	@Autowired
	HttpServletRequest httpServletRequest;

	@Autowired
	private TokenService tokenService;
	
	/*@PreAuthorize("( hasPermission('OPS_STOLEN_INSTRUMENT'))")*/
	@Audited(context = "STOLEN_INSTRUMENT" ,skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/getUserAndDeviceDetailsForTransaction",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getUserAndDeviceDetailsForTransaction(@RequestParam String transactionId) throws OpsPanelException, InfoPanelException{
		return getGenericResponse(service.getUserAndDeviceDetailsForTransaction(transactionId));
	}
	
	/*@PreAuthorize("( hasPermission('OPS_STOLEN_INSTRUMENT'))")*/
	@Audited(context = "STOLEN_INSTRUMENT" ,skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/getCategorizedTransactions",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getCategorizedTransactions(@RequestParam String transactionId) throws InfoPanelException{
		return getGenericResponse(service.getCategorizedTransactions(transactionId));
	}
	
	/*@PreAuthorize("( hasPermission('OPS_STOLEN_INSTRUMENT'))")*/
	@Audited(context = "STOLEN_INSTRUMENT" ,skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/submit",method=RequestMethod.POST)
	public @ResponseBody GenericResponse submit(@RequestBody SubmitRequest submitRequest) throws InfoPanelException{
		
		submitRequest.setActionPerformer(getUserId());
		
		return getGenericResponse(service.submit(submitRequest));
	}
	
	
	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
	
	public String getUserId() {
		String token = httpServletRequest.getHeader("token");
		String emailId = null;
		try {
			emailId = tokenService.getEmailFromToken(token);
		} catch (OpsPanelException e) {
			return null;
		}

		return emailId;
	}
}
