package com.snapdeal.opspanel.promotion.rp.controller;

import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.neo4j.cypher.internal.compiler.v2_1.functions.E;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
import com.snapdeal.opspanel.promotion.request.GetAllUsersForAppRequest;
import com.snapdeal.opspanel.promotion.service.RoleMgtService;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.opspanel.promotion.utils.RMSUtils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.payments.roleManagementClient.exceptions.ServiceException;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.request.CreateRoleRequest;
import com.snapdeal.payments.roleManagementModel.request.CreateUserRequest;
import com.snapdeal.payments.roleManagementModel.request.DeleteUserRequest;
import com.snapdeal.payments.roleManagementModel.request.GenerateOTPRequest;
import com.snapdeal.payments.roleManagementModel.request.GetAllPermissionsRequest;
import com.snapdeal.payments.roleManagementModel.request.GetAllUsersRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUserByIdRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUserByUserNameRequest;
import com.snapdeal.payments.roleManagementModel.request.GetUsersByCriteriaRequest;
import com.snapdeal.payments.roleManagementModel.request.LoginUserRequest;
import com.snapdeal.payments.roleManagementModel.request.Permission;
import com.snapdeal.payments.roleManagementModel.request.ResendOTPRequest;
import com.snapdeal.payments.roleManagementModel.request.SocialLoginUserRequest;
import com.snapdeal.payments.roleManagementModel.request.UpdateRoleRequest;
import com.snapdeal.payments.roleManagementModel.request.UpdateUserRequest;
import com.snapdeal.payments.roleManagementModel.request.User;
import com.snapdeal.payments.roleManagementModel.request.VerifyCodeRequest;
import com.snapdeal.payments.roleManagementModel.request.VerifyOTPRequest;
import com.snapdeal.payments.roleManagementModel.response.CreateUserResponse;
import com.snapdeal.payments.roleManagementModel.response.GenerateOTPResponse;
import com.snapdeal.payments.roleManagementModel.response.GetAllPermissionsResponse;
import com.snapdeal.payments.roleManagementModel.response.GetAllUsersResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUserByIdResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUserByUserNameResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUsersByCriteriaResponse;
import com.snapdeal.payments.roleManagementModel.response.LoginUserResponse;

@RestController
@RequestMapping(OPSUtils.RMS_URI)
public class RMSController {

	@Autowired
	RoleMgtService rmsService;
	
	@Autowired
	private HttpServletRequest servletRequest;
	
	@Autowired
	private TokenService tokenService;
	
	@Audited(context = "RMS", searchId = "request.roleName", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.CREATE_ROLE_URI, method = RequestMethod.POST)
	public GenericResponse createRole(@RequestBody CreateRoleRequest request)
					throws ServiceException, RoleMgmtException {

		rmsService.createRole(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse("SUCCESS");
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "request.id", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.UPDATE_ROLE, method = RequestMethod.POST)
	public GenericResponse updateRole(@RequestBody UpdateRoleRequest request)
					throws ServiceException, RoleMgmtException {

		rmsService.updateRole(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse("SUCCESS");
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "request.email", skipRequestKeys = {"request.mobile","request.password","request.linkForSettingPassword"}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.CREATE_USER, method = RequestMethod.POST)
	public GenericResponse createUser(@RequestBody CreateUserRequest request)
					throws ServiceException, RoleMgmtException, WalletServiceException {
		CreateUserResponse response;
		request.setPassword(UUID.randomUUID().toString());

		/*		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);
		if(!ValidateScopeOfSuperUser(request.getPermissionIds(),emailId)){
			throw new WalletServiceException("MT-5096", "can not add permission to other apps");
		}*/
		response=rmsService.createUser(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "request.userId", skipRequestKeys = {"request.mobile"}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.UPDATE_USER, method = RequestMethod.POST)
	public GenericResponse updateUser(@RequestBody UpdateUserRequest request)
					throws ServiceException, RoleMgmtException, WalletServiceException {

		rmsService.updateUser(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse("SUCCESS");
		return genericResponse;	
	}
	
	private boolean ValidateScopeOfSuperUser(List<Integer> permissionIds,
			String emailId) {
		GetAllPermissionsRequest permissionRequest= new GetAllPermissionsRequest();
	
	GetAllPermissionsResponse response=rmsService.getAllPermissions(permissionRequest);
	HashMap<Integer,String> map= new HashMap<Integer,String>();
	for(Permission permission:response.getPermissions()) {
		map.put(permission.getId(),permission.getAppName());
	}	
	GetUserByUserNameRequest request = new GetUserByUserNameRequest();
	request.setUserName(emailId);
	GetUserByUserNameResponse userResponse=rmsService.getUserByUserName(request);
	HashSet set= new HashSet<E>();
	if(userResponse.getUser()!=null) {
	for(Permission permission:userResponse.getUser().getPermissions()){
		set.add(permission.getName());
	}
	}
	for(Integer integer:permissionIds){
			String appName=map.get(integer);
			if(appName==null){
				continue;
			}
			if(!(set.contains(appName+"_superuser"))||set.contains("ops_superuser")) {	

				return false;
			}
		}
	return true;
	}
	
	@Audited(context = "RMS", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_USER_BY_USER_NAME, method = RequestMethod.GET)
	public GenericResponse getUserByUserName()
					throws ServiceException, RoleMgmtException, WalletServiceException, OpsPanelException {
		GetUserByUserNameResponse response;
		
		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);
		
		GetUserByUserNameRequest request= new GetUserByUserNameRequest();
		request.setUserName(emailId);
		response=rmsService.getUserByUserName(request);
		User user =response.getUser();
		GenericResponse genericResponse = RMSUtils.getPermissionsAsApp(user);
		return genericResponse;	
	}


	@Audited(context = "RMS", searchId = "request.userId", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_USER_BY_ID, method = RequestMethod.POST)
	public GenericResponse getUserById(@RequestBody GetUserByIdRequest request)
					throws ServiceException, RoleMgmtException {
		GetUserByIdResponse response;
		response=rmsService.getUserById(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_ALL_USERS, method = RequestMethod.GET)
	public GenericResponse getAllUsers()
					throws ServiceException, RoleMgmtException {
		GetAllUsersResponse response;
		response=rmsService.getAllUsers(new GetAllUsersRequest());
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_ALL_PERMISSIONS, method = RequestMethod.POST)
	public GenericResponse getAllPermissions(@RequestBody GetAllPermissionsRequest request)
					throws ServiceException, RoleMgmtException {
		GetAllPermissionsResponse response;
		response=rmsService.getAllPermissions(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_ALL_PERMISSIONS_AS_APPS, method = RequestMethod.GET)
	public GenericResponse getAllPermissionsForApp()
					throws ServiceException, RoleMgmtException, OpsPanelException {
		HashMap<String, List<Permission>> response;
		
		String token = servletRequest.getHeader("token");
		String emailId = tokenService.getEmailFromToken(token);
		response=rmsService.getAllPermissionsForApp(emailId);
		
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	

	@Audited(context = "RMS", searchId = "request.emailId", skipRequestKeys = {"request.socialToken"}, skipResponseKeys = {"response.data.user.mobile"})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.SOCIAL_LOGIN, method = RequestMethod.POST)
	public GenericResponse sociallogin(@RequestBody SocialLoginUserRequest request)
					throws ServiceException, RoleMgmtException {
		LoginUserResponse response;
		response=rmsService.socialLoginUser(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	

	@Audited(context = "RMS", searchId = "request.userName", skipRequestKeys = {"request.password"}, skipResponseKeys = {"response.data.user.mobile"})
	@RequestMapping(value = OPSUtils.LOGIN, method = RequestMethod.POST)
	public GenericResponse login(@RequestBody LoginUserRequest request)
					throws ServiceException, RoleMgmtException {
		LoginUserResponse response;
		response=rmsService.login(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "request.userId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.DELETE, method = RequestMethod.POST)
	public GenericResponse login(@RequestBody DeleteUserRequest request)
					throws ServiceException, RoleMgmtException {
		rmsService.deleteUser(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse("SUCCESS");
		return genericResponse;	
	}
	
	@Audited(context = "RMS", searchId = "request.appName", skipRequestKeys = {}, skipResponseKeys = {},viewable=0)
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_USERS_FOR_APP, method = RequestMethod.POST)
	public GenericResponse getusersforapp(@RequestBody GetAllUsersForAppRequest request)
					throws ServiceException, RoleMgmtException {
		HashMap<String, List<User>> response;
		response=rmsService.getUsersForApp(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse(response);
		return genericResponse;
	}

	@Audited(context = "RMS", searchId = "request.userName", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = OPSUtils.GENERATE_OTP, method = RequestMethod.POST)
	public GenericResponse generateOTP(@RequestBody GenerateOTPRequest request)
					throws ServiceException, RoleMgmtException {
		GenerateOTPResponse response = rmsService.generateOTP(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse( response );
		return genericResponse;
	}

	@Audited(context = "RMS", searchId = "request.otpId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = OPSUtils.RESEND_OTP, method = RequestMethod.POST)
	public GenericResponse resendOTP(@RequestBody ResendOTPRequest request)
					throws ServiceException, RoleMgmtException {
		GenerateOTPResponse response = rmsService.resendOTP(request);
		GenericResponse genericResponse =OPSUtils.getGenericResponse( response );
		return genericResponse;
	}

	@Audited(context = "RMS", searchId = "request.otpId", skipRequestKeys = {"request.otp","request.newPassword"}, skipResponseKeys = {})
	@RequestMapping(value = OPSUtils.VERIFY_OTP, method = RequestMethod.POST)
	public GenericResponse verifyOTP(@RequestBody VerifyOTPRequest request)
					throws ServiceException, RoleMgmtException {
		rmsService.verifyOTP(request);
		GenericResponse genericResponse = OPSUtils.getGenericResponse( "Success" );
		return genericResponse;
	}

	@Audited(context = "RMS", searchId = "", skipRequestKeys = {"verifyCodeRequest.password","verifyCodeRequest.verificationCode"}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.VERIFY_CODE_AND_SET_PASSWORD, method = RequestMethod.POST)
	public GenericResponse verifyCodeAndSetPassword(@RequestBody VerifyCodeRequest verifyCodeRequest)
					throws ServiceException, RoleMgmtException {
		rmsService.verifyCodeAndSetPassword( verifyCodeRequest );
		GenericResponse genericResponse = OPSUtils.getGenericResponse( "Success" );
		return genericResponse;	
	}

	
	@PreAuthorize("(hasPermission('OPS_INTERNALPANEL_superuser'))")
	@RequestMapping(value = OPSUtils.GET_USERS_BY_CRITERIA, method = RequestMethod.POST)
	public GenericResponse getUsersBycrieteria(@Valid @RequestBody GetUsersByCriteriaRequest getUsersByCriteriaRequest, BindingResult bindingResult)
					throws ServiceException, RoleMgmtException, OpsPanelException {
		GenericControllerUtils.checkBindingResult(bindingResult, "getUsersBycrieteria in RMS Controller");
		GetUsersByCriteriaResponse getUsersBycrieteriaResponse = rmsService.getUsersBycrieteria(getUsersByCriteriaRequest);	
		GenericResponse genericResponse = OPSUtils.getGenericResponse(getUsersBycrieteriaResponse);
		return genericResponse;
	}
	
	
	// Below code is for generating JSON request response for UI , remove before release
	
}
