//package com.snapdeal.opspanel.promotion.rp.controller;
//
//import java.lang.reflect.InvocationTargetException;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Controller;
//import org.springframework.web.bind.annotation.RequestBody;
//import org.springframework.web.bind.annotation.RequestMapping;
//import org.springframework.web.bind.annotation.RequestMethod;
//import org.springframework.web.bind.annotation.ResponseBody;
//
//import com.snapdeal.opspanel.ClientIntegrationsComponent.genericservice.GenericService;
//import com.snapdeal.opspanel.promotion.Response.GenericResponse;
//import com.snapdeal.opspanel.promotion.request.GenericClientRequest;
//import com.snapdeal.opspanel.promotion.utils.OPSUtils;
//import com.snapdeal.payments.disbursement.client.DisbursementClient;
//import com.snapdeal.payments.disbursement.model.DisburseMerchantRequest;
//
//@Controller
//@RequestMapping("generic/")
//// @Slf4j
//public class GenericController {
//
//	@Autowired
//	GenericService genericService;
//
//	@RequestMapping(value = "/execute", method = RequestMethod.POST)
//	public @ResponseBody Object execute(@RequestBody GenericClientRequest request) throws NoSuchMethodException,
//			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Object response;
//
//		response = genericService.execute(request.getMethodName(), request.getRequest(), DisbursementClient.class);
//
//		GenericResponse genericResponse = OPSUtils.getGenericResponse(response);
//		return genericResponse;
//	}
//
//	@RequestMapping(value = "/getAllMethods", method = RequestMethod.POST)
//	public @ResponseBody Object getAllMethods(@RequestBody GenericClientRequest request) throws NoSuchMethodException,
//			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Object response;
//
//		response = genericService.getAllMethods(DisbursementClient.class, request.getOnlyGetRequest());
//
//		GenericResponse genericResponse = OPSUtils.getGenericResponse(response);
//		return genericResponse;
//	}
//	
//	@RequestMapping(value = "/getAllFields", method = RequestMethod.POST)
//	public @ResponseBody Object getAllFields(@RequestBody GenericClientRequest request) throws NoSuchMethodException,
//			SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
//		Object response;
//
//		response = genericService.getFieldsFromRequest(DisburseMerchantRequest.class);
//
//		GenericResponse genericResponse = OPSUtils.getGenericResponse(response);
//		return genericResponse;
//	}
//}
