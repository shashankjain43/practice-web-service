package com.snapdeal.admin.controller;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.admin.request.CreateClientBasedConfigurationRequest;
import com.snapdeal.admin.request.CreateConfigurationRequest;
import com.snapdeal.admin.request.DeleteConfigurationRequest;
import com.snapdeal.admin.request.GetConfigurationRequest;
import com.snapdeal.admin.request.UpdateConfigurationRequest;
import com.snapdeal.admin.response.CreateConfigurationResponse;
import com.snapdeal.admin.response.DeleteConfigurationResponse;
import com.snapdeal.admin.response.GetAllConfigurationResponse;
import com.snapdeal.admin.response.GetConfigurationByKeyRequest;
import com.snapdeal.admin.response.GetConfigurationByKeyResponse;
import com.snapdeal.admin.response.GetConfigurationByTypeRequest;
import com.snapdeal.admin.response.GetConfigurationByTypeResponse;
import com.snapdeal.admin.response.GetConfigurationResponse;
import com.snapdeal.admin.response.UpdateConfigurationResponse;
import com.snapdeal.admin.service.IConfigurationService;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.errorcodes.IMSRequestExceptionCodes;
import com.snapdeal.ims.exception.RequestParameterException;
import com.snapdeal.ims.exception.ValidationException;

/**
 * @author praveen
 *
 */
@Controller
@RequestMapping(value = "admin/configuration")
@Slf4j
public class ConfigurationManagementController {
	@Autowired
	private IConfigurationService configurationService;

	@RequestMapping(value = "/clientcreate", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.POST})
	@ResponseBody
	public CreateConfigurationResponse createClientBasedConfiguration(
			@ModelAttribute @Valid CreateClientBasedConfigurationRequest request,
			BindingResult result, ModelMap model) throws ValidationException {
		if (result.hasErrors()) {
			log.error("validation exception is:" + result.getFieldError());
			throw new ValidationException();
		}
		CreateConfigurationResponse createConfigurationResponse = configurationService
				.CreateClientBasedConfigurationRequest(request);
		createConfigurationResponse.setResult("OK");
		return createConfigurationResponse;
	}

	
	@RequestMapping(value = "/create", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.POST})
	@ResponseBody
	public CreateConfigurationResponse createConfiguration(
			@ModelAttribute @Valid CreateConfigurationRequest request,
			BindingResult result, ModelMap model) throws ValidationException {
		if (result.hasErrors()) {
			log.error("validation exception is:" + result.getFieldError());
			throw new ValidationException();
		}
		CreateConfigurationResponse createConfigurationResponse = configurationService
				.createConfiguration(request);
		createConfigurationResponse.setResult("OK");
		return createConfigurationResponse;
	}

	/**
	 * Used for Updating Configuration Data
	 * 
	 * @param request
	 * @param model
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/update", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.POST})
	@ResponseBody
	public UpdateConfigurationResponse updateConfiguration(
			@ModelAttribute UpdateConfigurationRequest request, ModelMap model)
			throws ValidationException {
		UpdateConfigurationResponse response = configurationService
				.updateConfiguration(request);
		return response;

	}

	/**
	 * Used for getting all configuration data with view
	 * 
	 * @param request
	 * @param model
	 * @return
	 * @throws ValidationException
	 */
	/*
	 * @RequestMapping(value = "", produces = RestURIConstants.APPLICATION_JSON,
	 * method = { RequestMethod.GET }) public String
	 * getAllConfiguration(ModelMap model) throws ValidationException {
	 * GetAllConfigurationResponse getAllConfigurationResponse =
	 * configurationService .getAllConfiguration();
	 * model.addAttribute("getAllConfigurationResponse",
	 * getAllConfigurationResponse); return "/admin/manage/configuration"; }
	 */
	@RequestMapping(value = "list", produces = RestURIConstants.APPLICATION_JSON, method = {RequestMethod.GET})
	@ResponseBody
	public GetAllConfigurationResponse listAllConfiguration(
			HttpServletRequest request, ModelMap model)
			throws ValidationException {
		log.debug("Calling list All Configuration Method");
		GetAllConfigurationResponse getAllConfigurationResponse = configurationService
				.getAllConfiguration(request);
		return getAllConfigurationResponse;
	}

	/**
	 * This API will be responsible for Client de-activation
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/delete", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	@ResponseBody
	public DeleteConfigurationResponse deleteConfig(
			@ModelAttribute @Valid DeleteConfigurationRequest request,
			BindingResult results) throws ValidationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while deleting configuration");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return configurationService.deleteConfiguration(request);
	}

	/**
	 * This API will retrieve configuration corresponding to given key and type
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/keyandtype", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetConfigurationResponse getConfig(
			@ModelAttribute @Valid GetConfigurationRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured in get config");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return configurationService.getConfig(request.getConfigKey(),
				request.getConfigType());
	}

	/**
	 * This API will retrieve configurations corresponding to given key
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/key", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetConfigurationByKeyResponse getConfigByKey(
			@ModelAttribute @Valid GetConfigurationByKeyRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured in get configs by key");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return configurationService.getConfigsByKey(request.getConfigKey());
	}

	/**
	 * This API will retrieve configurations corresponding to given type
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	@RequestMapping(value = "/type", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetConfigurationByTypeResponse getConfigByType(
			@ModelAttribute @Valid GetConfigurationByTypeRequest request,
			BindingResult results, HttpServletRequest httpRequest)
			throws ValidationException {

		if (results.hasErrors() && null != results.getAllErrors()) {
			IMSRequestExceptionCodes code = IMSRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured in get configs by type");
			throw new RequestParameterException(code.errCode(), code.errMsg());
		}

		return configurationService.getConfigsByType(request.getConfigType());
	}

}
