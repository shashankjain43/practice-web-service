package com.snapdeal.opspanel.promotion.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.UUID;

import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;

import com.snapdeal.opspanel.promotion.Response.GetEligibleAppsResponse;
import com.snapdeal.opspanel.promotion.request.GetAllUsersForAppRequest;
import com.snapdeal.opspanel.promotion.service.RoleMgtService;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementClient.exceptions.ServiceException;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.request.CreateRoleRequest;
import com.snapdeal.payments.roleManagementModel.request.CreateUserRequest;
import com.snapdeal.payments.roleManagementModel.request.DeleteUserRequest;
import com.snapdeal.payments.roleManagementModel.request.EmailTemplate;
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

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Service
@Slf4j
public class RoleMgtServiceImpl implements RoleMgtService {

	@Autowired
	RoleMgmtClient rmsClient;

	@Autowired
	HttpServletRequest servletRequest;

	@Override
	public void createRole(CreateRoleRequest request) throws ServiceException, RoleMgmtException {
		rmsClient.createRole(request);

	}

	@Override
	public void updateRole(UpdateRoleRequest request) throws ServiceException, RoleMgmtException {
		rmsClient.updateRole(request);

	}

	@Override
	public CreateUserResponse createUser(CreateUserRequest request) throws ServiceException, RoleMgmtException {
		request.setPermissionIds(getModifiedPermissions(request.getPermissionIds()));
		CreateUserResponse response = rmsClient.createUser(request);
		return response;
	}

	@Override
	public void updateUser(UpdateUserRequest request) throws ServiceException, RoleMgmtException {
		request.setPermissionIds(getModifiedPermissions(request.getPermissionIds()));
		rmsClient.updateUser(request);

	}

	public List<Integer> getModifiedPermissions(List<Integer> list) {
		GetAllPermissionsResponse response = getAllPermissions(new GetAllPermissionsRequest());
		Permission manageUserPermission = new Permission();
		Permission ops_superuserPermission = new Permission();

		Permission ops_mobViewPermission = new Permission();
		Permission mob_viewMerchantProfilePermission = new Permission();
		Permission mob_editMerchantProfilePermission = new Permission();
		Permission mob_fcPlusMerchanOnlyPermission = new Permission();
		Permission ops_merchantOpsView = null;
		Permission ops_disbursementView = null;
		Permission ops_disbursementUpdater = null;
		Permission ops_merchantOpsBulkUpdater = null;
		Permission ops_merchantOpsSettlementReport = null;
		Permission ops_merchantOpsUpdater = null;
		Permission ops_corpAccountMerchantLoadNodal = null;
		Permission ops_corpAccountCorpToCorp = null;

		Boolean relevant = false;

		for (Permission permission : response.getPermissions()) {
			if (permission.getName().trim().equalsIgnoreCase("MANAGE_USER")) {
				manageUserPermission = permission;
			} else if (permission.getName().equalsIgnoreCase("OPS_INTERNALPANEL_superuser")) {
				ops_superuserPermission = permission;
			} else if (permission.getName().equalsIgnoreCase("VIEW_MERCHANT_PROFILE")
					&& permission.getAppName().equalsIgnoreCase("MOB")) {
				mob_viewMerchantProfilePermission = permission;
			} else if (permission.getName().equalsIgnoreCase("OPS_MOBPANEL_VIEW")
					&& permission.getAppName().equalsIgnoreCase("OPS_MOBPANEL")) {
				ops_mobViewPermission = permission;
			} else if (permission.getName().equalsIgnoreCase("EDIT_MERCHANT_PROFILE")
					&& permission.getAppName().equalsIgnoreCase("MOB")) {
				mob_editMerchantProfilePermission = permission;
			} else if (permission.getName().equalsIgnoreCase("OPS_FCPLUS_MERCHANT_VIEW_ONLY")
					&& permission.getAppName().equalsIgnoreCase("MOB")) {
				mob_fcPlusMerchanOnlyPermission = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_MERCHANTOPS_VIEW" )
					&& permission.getAppName().equalsIgnoreCase( "OPS_MERCHANTOPS" ) ) {
				ops_merchantOpsView = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_DISBURSEMENT_view" ) ) {
				ops_disbursementView = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_DISBURSEMENT_updater" ) ) {
				ops_disbursementUpdater = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_MERCHANTOPS_BULK_UPDATER" ) ) {
				ops_merchantOpsBulkUpdater = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_MERCHANTOPS_SETTLEMENT_REPORT" ) ) {
				ops_merchantOpsSettlementReport = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_MERCHANTOPS_UPDATER" ) ) {
				ops_merchantOpsUpdater = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_CORPACCOUNT_MERCHANTLOAD_NODAL" ) ) {
				ops_corpAccountMerchantLoadNodal = permission;
			} else if( permission.getName().equalsIgnoreCase( "OPS_CORPACCOUNT_CORPTOCORP" ) ) {
				ops_corpAccountCorpToCorp = permission;
			}
		}

		if (list.contains(ops_superuserPermission.getId()) && !list.contains(manageUserPermission.getId())) {
			list.add(manageUserPermission.getId());
		}

		if (list.contains(ops_mobViewPermission.getId())) {
			if (!list.contains(mob_viewMerchantProfilePermission.getId())) {
				list.add(mob_viewMerchantProfilePermission.getId());
			}
			if (!list.contains(manageUserPermission.getId())) {
				list.add(manageUserPermission.getId());
			}
			if (!list.contains(mob_editMerchantProfilePermission.getId())) {
				list.add(mob_editMerchantProfilePermission.getId());
			}
			
		}

		if (list.contains(mob_fcPlusMerchanOnlyPermission.getId())) {
			if (!list.contains(mob_viewMerchantProfilePermission.getId())) {
				list.add(mob_viewMerchantProfilePermission.getId());
			}
			if (!list.contains(manageUserPermission.getId())) {
				list.add(manageUserPermission.getId());
			}
			if (!list.contains(mob_editMerchantProfilePermission.getId())) {
				list.add(mob_editMerchantProfilePermission.getId());
			}
		}

		if( list.contains( ops_merchantOpsView.getId() ) || list.contains( ops_disbursementView.getId() ) ||
				list.contains( ops_disbursementUpdater.getId() ) || list.contains( ops_merchantOpsBulkUpdater.getId() ) ||
				list.contains( ops_merchantOpsSettlementReport.getId() ) || list.contains( ops_merchantOpsView.getId() ) ||
				list.contains( ops_corpAccountCorpToCorp.getId() ) || list.contains( ops_corpAccountMerchantLoadNodal.getId() ) ) {
			if (!list.contains(mob_viewMerchantProfilePermission.getId())) {
				list.add(mob_viewMerchantProfilePermission.getId());
			}
		}
		return list;
	}

	@Override
	public GetUserByUserNameResponse getUserByUserName(GetUserByUserNameRequest request)
			throws ServiceException, RoleMgmtException {
		GetUserByUserNameResponse response = rmsClient.getUserByUserName(request);
		return response;
	}

	@Override
	public LoginUserResponse login(LoginUserRequest request) throws ServiceException, RoleMgmtException {
		LoginUserResponse response = rmsClient.loginUser(request);
		return response;
	}

	@Override
	public void deleteUser(DeleteUserRequest request) throws ServiceException, RoleMgmtException {
		rmsClient.deleteUser(request);

	}

	@Override
	public GetUserByIdResponse getUserById(GetUserByIdRequest request) throws ServiceException, RoleMgmtException {
		GetUserByIdResponse response = rmsClient.getUserById(request);
		return response;
	}

	@Override
	public GetEligibleAppsResponse getAlleligibleApp(String email) throws RoleMgmtException, ServiceException {
		HashMap<String, List<Permission>> map = null;
		GetUserByUserNameRequest request = new GetUserByUserNameRequest();
		request.setUserName(email);
		GetUserByUserNameResponse response = rmsClient.getUserByUserName(request);
		for (Permission permission : response.getUser().getPermissions()) {
			if (map == null) {
				map = new HashMap<String, List<Permission>>();
			}
			if (map.get(permission.getAppName()) == null) {
				List<Permission> permissionList = new ArrayList<Permission>();
				permissionList.add(permission);
				map.put(permission.getAppName(), permissionList);
			} else {
				map.get(permission.getAppName()).add(permission);
			}
		}
		GetEligibleAppsResponse finalResponse = new GetEligibleAppsResponse();
		for (String str : map.keySet()) {
			finalResponse.getApps().add(str);
		}

		return finalResponse;
	}

	@Override
	public HashMap<String, List<Permission>> getAllPermissionsForApp(@RequestBody String email)
			throws RoleMgmtException, ServiceException {
		HashMap<String, List<Permission>> map = new HashMap<String, List<Permission>>();
		GetUserByUserNameRequest superUserRequest = new GetUserByUserNameRequest();
		superUserRequest.setUserName(email);
		GetUserByUserNameResponse superUser = rmsClient.getUserByUserName(superUserRequest);
		for (Permission permission : superUser.getUser().getPermissions()) {
			if ((permission.getAppName() + "_superuser").equalsIgnoreCase(permission.getName())) {
				map.put(permission.getAppName(), new ArrayList<Permission>());
			}
		}
		Boolean isOverAllSuperUser = false;
		GetAllPermissionsResponse response = rmsClient.getAllPermissions(new GetAllPermissionsRequest());
		for (Permission permission : response.getPermissions()) {
			/*
			 * if(permission.getName().equalsIgnoreCase(
			 * "OPS_ACTIONPANEL_superuser")) { isOverAllSuperUser=true; break; }
			 */
			if (map.get(permission.getAppName()) != null) {
				map.get(permission.getAppName()).add(permission);
			}
		}
		/*
		 * if(isOverAllSuperUser) { GetAllPermissionsResponse
		 * allPermissions=getAllPermissions(new GetAllPermissionsRequest());
		 * map.clear(); for(Permission permission
		 * :allPermissions.getPermissions()) {
		 * 
		 * } }
		 */
		// GetAllPermissionForAppResponse finalResponse= new
		// GetAllPermissionForAppResponse();
		// finalResponse.setPermissions((ArrayList<Permission>)
		// map.get(request.getAppName()));
		return map;
	}

	@Override
	public HashMap<String, List<User>> getUsersForApp(@RequestBody GetAllUsersForAppRequest request)
			throws RoleMgmtException, ServiceException {
		HashMap<String, List<User>> userMap = null;
		HashSet appSet = null;
		GetAllUsersRequest req = new GetAllUsersRequest();
		GetAllUsersResponse response = rmsClient.getAllUsers(req);
		if (response.getUsers() != null) {

			for (User user : response.getUsers()) {
				if (user.getPermissions() != null) {
					for (Permission permission : user.getPermissions()) {
						if (appSet == null) {
							appSet = new HashSet();
							appSet.add(permission.getAppName());
						} else {
							if (!appSet.contains(permission.getAppName())) {
								appSet.add(permission.getAppName());
							}
						}
					}
					if (userMap == null) {
						userMap = new HashMap<String, List<User>>();
					}
					for (Object str : appSet.toArray()) {
						if (userMap.get((String) str) == null) {
							userMap.put((String) str, new ArrayList<User>());
						}
						userMap.get((String) str).add(user);
					}
				}
			}
		}

		return userMap;
	}

	@Override
	public GetAllPermissionsResponse getAllPermissions(GetAllPermissionsRequest request)
			throws ServiceException, RoleMgmtException {
		return rmsClient.getAllPermissions(request);

	}

	@Override
	public  GetUsersByCriteriaResponse getUsersBycrieteria(GetUsersByCriteriaRequest getUsersByCriteriaRequest)
		throws ServiceException, RoleMgmtException{
		return rmsClient.getUsersByCriteria(getUsersByCriteriaRequest);
	}
	
	@Override
	public LoginUserResponse socialLoginUser(SocialLoginUserRequest request)
			throws ServiceException, RoleMgmtException {

		return rmsClient.socialLoginUser(request);
	}

	@Override
	public GetAllUsersResponse getAllUsers(GetAllUsersRequest request) throws ServiceException, RoleMgmtException {

		return rmsClient.getAllUsers(request);
	}

	@Override
	public GenerateOTPResponse generateOTP(GenerateOTPRequest generateOtpRequest)
			throws ServiceException, RoleMgmtException {
		return rmsClient.generateOTP(generateOtpRequest);
	}

	
	@Override
	public GenerateOTPResponse resendOTP(ResendOTPRequest resendOTPRequest) throws ServiceException, RoleMgmtException {
		return rmsClient.resendOTP(resendOTPRequest);
	}

	@Override
	public void verifyOTP(VerifyOTPRequest verifyOTPRequest) throws ServiceException, RoleMgmtException {
		rmsClient.verifyOTP(verifyOTPRequest);
	}

	@Override
	public void verifyCodeAndSetPassword(VerifyCodeRequest verifyCodeRequest)
			throws ServiceException, RoleMgmtException {
		rmsClient.verifyCodeAndSetPassword(verifyCodeRequest);
	}

	@Override
	public int getPermissionIdForPermissionName(String name) throws ServiceException, RoleMgmtException {

		GetAllPermissionsResponse response = getAllPermissions(new GetAllPermissionsRequest());
		Permission permissionToFind = new Permission();

		for (Permission permission : response.getPermissions()) {
			if (permission.getName().trim().equalsIgnoreCase(name)) {
				permissionToFind = permission;
				break;
			}
		}

		return permissionToFind.getId();
	}

	@Override
	public void createOrUpdateUserWithPermission(String emailId, String permissionName)
			throws ServiceException, RoleMgmtException {

		List<Integer> permissionIds = new ArrayList<Integer>();
		List<Permission> permissions;
		int permissionId;
		UpdateUserRequest updateUserRequest;

		try {

			// check if user already exist
			GetUserByUserNameRequest getUserByUserNameRequest = new GetUserByUserNameRequest();
			getUserByUserNameRequest.setUserName(emailId);
			GetUserByUserNameResponse getUserByUserNameResponse = getUserByUserName(getUserByUserNameRequest);
			User user = getUserByUserNameResponse.getUser();
			permissions = user.getPermissions();

			// check if permission already assigned
			Permission keyPanelPermission = null;
			for (Permission permission : permissions) {
				permissionIds.add(permission.getId());
				if (permission.getName().trim().equalsIgnoreCase(permissionName)) {
					keyPanelPermission = permission;
				}
			}

			// if permission is not already assigned assign now.
			if (keyPanelPermission == null) {
				updateUserRequest = new UpdateUserRequest();
				permissionId = getPermissionIdForPermissionName(permissionName);
				permissionIds.add(permissionId);
				updateUserRequest.setPermissionIds(permissionIds);
				updateUserRequest.setName(emailId);
				updateUserRequest.setUserId(user.getId());
				updateUser(updateUserRequest);
			}

		} catch (Exception e) {
			log.info("Exception while createOrUpdateUserwithpermission. Email ID: " + emailId + " Exception: "
					+ ExceptionUtils.getFullStackTrace(e));
			permissionId = getPermissionIdForPermissionName(permissionName);

			// user does not exist. create a new user and assign permission.
			CreateUserRequest createUserRequest = new CreateUserRequest();
			createUserRequest.setEmail(emailId);
			createUserRequest.setUserName(emailId);
			createUserRequest.setName(emailId);
			createUserRequest.setPassword(UUID.randomUUID().toString());
			permissionIds.add(permissionId);
			createUserRequest.setPermissionIds(permissionIds);
			createUserRequest.setLinkForSettingPassword(getLinkForSettingPassword());
			createUserRequest.setEmailTemplate(EmailTemplate.OP_PANEL_SETPASSWORD);

			createUser(createUserRequest);
		}
	}

	private String getLinkForSettingPassword() {
		return servletRequest.getScheme() + "://" + servletRequest.getServerName() + ":"
				+ servletRequest.getServerPort() + "/#/setPassword/";
	}
}
