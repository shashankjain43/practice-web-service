package com.snapdeal.opspanel.promotion.service;

import java.util.HashMap;
import java.util.List;

import com.snapdeal.opspanel.promotion.Response.GetEligibleAppsResponse;
import com.snapdeal.opspanel.promotion.request.GetAllUsersForAppRequest;
import com.snapdeal.payments.roleManagementClient.exceptions.ServiceException;
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

public interface RoleMgtService {

	public void createRole(CreateRoleRequest request) throws ServiceException, RoleMgmtException;

	public void updateRole(UpdateRoleRequest request) throws ServiceException, RoleMgmtException;

	public CreateUserResponse createUser(CreateUserRequest request) throws ServiceException, RoleMgmtException;

	public void updateUser(UpdateUserRequest request) throws ServiceException, RoleMgmtException;


	public GetUserByIdResponse getUserById(GetUserByIdRequest request) throws ServiceException, RoleMgmtException;

	public GetUserByUserNameResponse getUserByUserName(GetUserByUserNameRequest request)
			throws ServiceException, RoleMgmtException;


	public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) throws ServiceException, RoleMgmtException;

	public GetEligibleAppsResponse getAlleligibleApp(String email);

	public HashMap<String, List<User>> getUsersForApp(GetAllUsersForAppRequest request);

	public GetAllPermissionsResponse getAllPermissions(
			GetAllPermissionsRequest request);

	public LoginUserResponse socialLoginUser(SocialLoginUserRequest request);

	public LoginUserResponse login(LoginUserRequest request)
			throws ServiceException, RoleMgmtException;

	public HashMap<String, List<Permission>> getAllPermissionsForApp(String email);

	void deleteUser(DeleteUserRequest request)
			throws ServiceException, RoleMgmtException;

	public GenerateOTPResponse generateOTP(GenerateOTPRequest generateOtpRequest)
		throws ServiceException, RoleMgmtException;

	public GenerateOTPResponse resendOTP(ResendOTPRequest resendOTPRequest)
		throws ServiceException, RoleMgmtException;

	public void verifyOTP(VerifyOTPRequest verifyOTPRequest)
		throws ServiceException, RoleMgmtException;

	public int getPermissionIdForPermissionName( String name )
		throws ServiceException, RoleMgmtException;

	public void createOrUpdateUserWithPermission( String emailId, String permissionName )
		throws ServiceException, RoleMgmtException;

	public void verifyCodeAndSetPassword(VerifyCodeRequest verifyCodeRequest)
		throws ServiceException, RoleMgmtException;


	public GetUsersByCriteriaResponse getUsersBycrieteria(GetUsersByCriteriaRequest getUsersByCriteriaRequest)
		throws ServiceException, RoleMgmtException;

}
