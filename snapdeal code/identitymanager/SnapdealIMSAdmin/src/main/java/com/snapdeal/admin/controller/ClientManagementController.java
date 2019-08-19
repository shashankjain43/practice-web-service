package com.snapdeal.admin.controller;

import javax.servlet.http.HttpServletRequest;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.snapdeal.admin.request.ActivateClientRequest;
import com.snapdeal.admin.request.CreateClientRequest;
import com.snapdeal.admin.request.DeactivateClientRequest;
import com.snapdeal.admin.request.RegenerateClientKeyRequest;
import com.snapdeal.admin.response.ActivateClientResponse;
import com.snapdeal.admin.response.CreateClientResponse;
import com.snapdeal.admin.response.DeactivateClientResponse;
import com.snapdeal.admin.response.GetAllClientResponse;
import com.snapdeal.admin.response.GetClientByMerchantResponse;
import com.snapdeal.admin.response.GetClientByNameResponse;
import com.snapdeal.admin.response.GetClientByStatusResponse;
import com.snapdeal.admin.response.GetClientByTypeResponse;
import com.snapdeal.admin.response.GetClientResponse;
import com.snapdeal.admin.response.RegenerateClientKeyResponse;
import com.snapdeal.admin.service.IClientService;
import com.snapdeal.ims.common.constant.RestURIConstants;
import com.snapdeal.ims.exception.ValidationException;

/**
 * 
 * @author subhash
 */
@Controller
@RequestMapping(value = "/admin/client")
@Slf4j
public class ClientManagementController extends AbstractController {

	@Autowired
	private IClientService clientService;

	/**
	 * This API will be responsible for Client creation
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/create", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	@ResponseBody
	public CreateClientResponse createClient(
			@ModelAttribute CreateClientRequest request,
			HttpServletRequest httpRequest, Model model)
			throws ValidationException {
		CreateClientResponse createClientResponse = clientService
				.createClient(request);
		return createClientResponse;
	}

	/**
	 * This API will be responsible for Client de-activation
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/{clientId}/status/inactive", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@ResponseBody
	public DeactivateClientResponse deactivateClient(
			@ModelAttribute DeactivateClientRequest request,
			HttpServletRequest httpRequest) throws ValidationException {
		return clientService.deactivateClient(request);
	}

	/**
	 * This API will be responsible for Client activation
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/{clientId}/status/active", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@ResponseBody
	public ActivateClientResponse activateClient(
			@ModelAttribute ActivateClientRequest request,
			HttpServletRequest httpRequest) throws ValidationException {
		return clientService.activateClient(request);
	}

	/**
	 * This API will regenerate client key for given clientId and update newKey
	 * in IMS database
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/{clientId}/clientkey", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@ResponseBody
	public RegenerateClientKeyResponse regenerateClientKey(
			@ModelAttribute RegenerateClientKeyRequest request,
			HttpServletRequest httpRequest) throws ValidationException {
		return clientService.regenerateClientKey(request);
	}

	/**
	 * This API will retrieve client corresponding to given client_id
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "list", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	@ResponseBody
	public GetAllClientResponse getAllClient(HttpServletRequest request,
			ModelMap model) {
		log.debug("Calling Manage Client Method");
		GetAllClientResponse getAllClientResponse = clientService
				.getAllClient(request);
		return getAllClientResponse;
	}

	/**
	 * This API will retrieve client corresponding to given client_id
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "{clientId}", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetClientResponse getClientById(
			@PathVariable("clientId") String clientId,
			HttpServletRequest httpRequest) throws ValidationException {

		return clientService.getClientById(clientId);
	}

	/**
	 * This API will retrieve list of clients corresponding to given client name
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/name/{clientName}", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetClientByNameResponse getClientByName(
			@PathVariable("clientName") String clientName,
			HttpServletRequest httpRequest) throws ValidationException {

		return clientService.getClientByName(clientName);
	}

	/**
	 * This API will retrieve list of clients corresponding to given client type
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/type/{clientType}", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetClientByTypeResponse getClientByType(
			@PathVariable("clientType") String clientType,
			HttpServletRequest httpRequest) throws ValidationException {

		return clientService.getClientByType(clientType);
	}

	/**
	 * This API will retrieve list of clients corresponding to given client
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/merchant/{merchant}", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetClientByMerchantResponse getClientByMerchant(
			@PathVariable("merchant") String merchant,
			HttpServletRequest httpRequest) throws ValidationException {

		return clientService.getClientByMerchant(merchant);
	}

	/**
	 * This API will retrieve list of clients corresponding to given client
	 * status
	 * 
	 * @param request
	 * @param results
	 * @return
	 * @throws ValidationException
	 */
	// @Authorize
	@RequestMapping(value = "/status/{clientStatus}", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetClientByStatusResponse getClientByStatus(
			@PathVariable("clientStatus") String clientStatus,
			HttpServletRequest httpRequest) throws ValidationException {

		return clientService.getClientByClientStatus(clientStatus);
	}

}
