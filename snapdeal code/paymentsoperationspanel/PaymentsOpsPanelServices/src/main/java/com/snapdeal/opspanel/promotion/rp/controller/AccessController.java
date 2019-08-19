package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.annotation.SuperUserOnly;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.model.ClientRequestModel;
import com.snapdeal.opspanel.promotion.model.DeleteClientRequestModel;
import com.snapdeal.opspanel.promotion.request.LoginRequest;
import com.snapdeal.opspanel.promotion.service.BulkPromotionAcccessService;
import com.snapdeal.opspanel.promotion.service.RoleMgtService;
import com.snapdeal.opspanel.promotion.utils.RMSUtils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.LoginUserRequest;
import com.snapdeal.payments.roleManagementModel.request.LogoutUserRequest;
import com.snapdeal.payments.roleManagementModel.response.LoginUserResponse;

import lombok.extern.slf4j.Slf4j;

@Controller
@RequestMapping("access/")
@Slf4j
public class AccessController {

	@Autowired
	private BulkPromotionAcccessService accessService;

	@Autowired
	private HttpServletRequest servletRequest;

	@Autowired
	RoleMgmtClient rmsClient;

	@Autowired
	RoleMgtService rmsService;

	@Autowired
	TokenService tokenService;

	@Audited(context = "Access", searchId = "request.username", skipRequestKeys = {"request.password"}, skipResponseKeys = {"response.data.token"})
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public @ResponseBody GenericResponse login( @Valid @RequestBody LoginRequest request, BindingResult bindingResult )
			throws WalletServiceException, OpsPanelException {

		GenericControllerUtils.checkBindingResult( bindingResult , "login in AccessController" );

		LoginUserResponse socialResponse=null;

		LoginUserRequest loginUserRequest = new LoginUserRequest();
		loginUserRequest.setPassword( request.getPassword() );
		loginUserRequest.setUserName( request.getUsername() );
		try {

			socialResponse=rmsClient.loginUser(loginUserRequest);
			if(socialResponse!=null && socialResponse.getUser()!=null) {
				log.info("User login successful for : "+request.getUsername()+"\n use RMS Permissions :"+ socialResponse.getUser().getPermissions());
			}
		} catch (Exception e) {
			//handle exception
			log.info(e.getStackTrace().toString());
			throw new WalletServiceException("MT-5002",
					"Unable to login: "+e.getMessage(), "RoleMgmtClient");
		}

		String emailId = socialResponse.getUser().getEmail();
		
		String roles=accessService.getUsersRoles( emailId);
		log.info("\n User Bulk roles :" +roles +"\n");
		GenericResponse genericResponse;

		Map<String,Object> responseMap =new HashMap<String, Object>();

		responseMap.put("emailId", emailId);
		responseMap.put("roles", roles);
		responseMap.put("token", socialResponse.getToken());
		responseMap.put("rmsUser", RMSUtils.getPermissionsAsApp(socialResponse.getUser()));
		genericResponse = getGenericResponse(responseMap);

		return genericResponse;
	}


	@Audited(context = "Access", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/logout",method=RequestMethod.GET)
	public @ResponseBody GenericResponse logout() throws WalletServiceException, OpsPanelException{

/*		String token = servletRequest.getHeader("token");
		
		LogoutUserRequest logoutUserRequest = new LogoutUserRequest();
		logoutUserRequest.setRequestToken(token);
		
		rmsClient.logoutUser(logoutUserRequest);
*/
		return getGenericResponse("logged out successfully");
	}


	@Audited(context = "Access", searchId = "clientId", skipRequestKeys = {}, skipResponseKeys = {})
	@SuperUserOnly
	@RequestMapping(value="/deleteClient",method=RequestMethod.GET)
	public @ResponseBody GenericResponse deleteClient(@RequestParam(value="clientId") String clientId) throws WalletServiceException{

		try {	

			if(clientId.equals(""))
				throw new WalletServiceException("MT-1105","client's email id is missing");

			String token = servletRequest.getHeader("token");
			String emailId = tokenService.getEmailFromToken(token);
			
			if(clientId.equals(emailId))
				throw new WalletServiceException("MT-1105","client can'nt delete itself");

			accessService.deleteClient(clientId);

		} catch (Exception e) {
			log.info( "Exception occurred while deleting client " + e);
			throw new WalletServiceException("MT-1105",
					((WalletServiceException)e).getErrMessage());
		}

		return getGenericResponse("client deleted sucessfully");
	}


	@Audited(context = "Access", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value="/getAllPermissions",method=RequestMethod.GET)
	public @ResponseBody GenericResponse getPermissions() throws WalletServiceException{


		Map<String,Object> response=new HashMap<>();

		HttpServletRequest curRequest = ((ServletRequestAttributes) RequestContextHolder.currentRequestAttributes()) .getRequest();

		try {
			String token = servletRequest.getHeader("token");
			String emailId = tokenService.getEmailFromToken(token);
			
			response.put("permissions",accessService.getAccessPermissionsList(emailId));			

		} catch (Exception re) {
			log.info( "Exception while getting all permissions " + re );
			throw new WalletServiceException("MT-1106",
					"failed to get  permissions");
		}

		return getGenericResponse(response);
	}

	@Audited(context = "Access", searchId = "request.clientId", skipRequestKeys = {}, skipResponseKeys = {})
	@SuperUserOnly
	@RequestMapping(value = "/insertPermissionsForClient", method = RequestMethod.POST)
	public @ResponseBody GenericResponse insertPermissionsForClient(
			@Valid @RequestBody ClientRequestModel request, BindingResult bindingResult )
					throws WalletServiceException, OpsPanelException {

		GenericControllerUtils.checkBindingResult( bindingResult , "insertPermissionsForClient in AccessController" );

		try {

			if(accessService.mailValidator(request.getClientId()))
				accessService.addClient(request);
			else
				throw new WalletServiceException("MT-1102","only snapdeal or freecharge employees can be added ");

		} catch (Exception e) {

			log.info( "Exception while inserting permissions for client " + e );
			throw new WalletServiceException("MT-1102",((WalletServiceException)e).getErrMessage());
		}

		return  getGenericResponse("client has been added sucessfully");
	}

	@Audited(context = "Access", searchId = "request.clientId", skipRequestKeys = {}, skipResponseKeys = {})
	@SuperUserOnly
	@RequestMapping(value = "/deletePermission", method = RequestMethod.POST)
	public @ResponseBody GenericResponse deleteClinetPermission(
			@Valid @RequestBody DeleteClientRequestModel request,
			BindingResult bindingResult )
					throws WalletServiceException, OpsPanelException {

		GenericControllerUtils.checkBindingResult( bindingResult , "deletePermission in AccessController" );

		try{

			accessService.deleteSpecificPermission(request);
		} catch (Exception re) {
			log.info( "Exception occurred while deleting specific permission " + re );
			throw new WalletServiceException("MT-1104",
					"failed to delete permission");
		}

		return  getGenericResponse("permissions deleted sucessfully");
	}

	@Audited(context = "Access", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@SuperUserOnly
	@RequestMapping(value = "/getAllClients", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getAllClients()
			throws WalletServiceException {

		Map<String,Object> responseMap =new HashMap<String, Object>();

		try {
			responseMap.put("clients", accessService.getAllClients());

		} catch (Exception e) {
			log.info( "Exception while getting client list " + e );
			throw new WalletServiceException("MT-1103","Error in getting clients List");
		}

		return  getGenericResponse(responseMap);
	}

	@Audited(context = "Access", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "/getSessionValidity", method = RequestMethod.GET)
	public @ResponseBody GenericResponse getSessionValidity() throws Exception {

		Boolean response = null;

		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);

		if (emailId == null) 
			response = false;
		else 
			response = true;

		GenericResponse genericResponse = getGenericResponse(response);
		return genericResponse;
	}

	private GenericResponse getGenericResponse(Object walletResponse) {
		GenericResponse genericResponse = new GenericResponse();
		genericResponse.setError(null);
		genericResponse.setData(walletResponse);
		return genericResponse;
	}
}
