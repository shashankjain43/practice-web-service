package com.snapdeal.merchant.rest.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GetMerchantStateRequest;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.response.GetMerchantStateResponse;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.rest.service.ISessionService;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.mob.enums.MerchantStatus;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.roleManagementModel.dto.Token;
import com.snapdeal.payments.roleManagementModel.request.Permission;
import com.snapdeal.payments.roleManagementModel.request.Role;
import com.snapdeal.payments.roleManagementModel.request.User;
import com.snapdeal.payments.roleManagementModel.response.LoginUserResponse;
import com.snapdeal.payments.roleManagementModel.services.RoleMgmtService;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class SessionServiceImpl implements ISessionService {

	@Autowired
	private MpanelConfig config;

	@Autowired
	private RMSUtil rmsUtil;

	@Autowired
	private MOBUtil mobUtil;

	@Logged
	@Override
	public MerchantLoginResponse login(MerchantLoginRequest request) throws MerchantException {

		boolean editMerchantPermission =false , viewProfilePermission = false ;
		LoginUserResponse rmsLoginResponse = rmsUtil.login(request);
		
		String merchantStatus  = null;

		List<String> roles = config.getRoles();

		MerchantRoleRequest roleRequest = new MerchantRoleRequest();
		roleRequest.setRoles(roles);
		roleRequest.setToken(rmsLoginResponse.getToken().getToken());

		List<Role> rmsRoles = rmsLoginResponse.getAllRoles();

		MerchantLoginResponse mpResponse = new MerchantLoginResponse();

		Token rmsToken = rmsLoginResponse.getToken();
		mpResponse.setToken(rmsToken.getToken());

		HashMap<Integer, Permission> existingUserPermission = new HashMap<Integer, Permission>();

		// roles dto to be returned
		List<MerchantRoleDTO> merchantRolesList = new ArrayList<MerchantRoleDTO>();
		// get user Permission
		User rmsUser = rmsLoginResponse.getUser();
		List<Permission> permissionList = rmsUser.getPermissions();

		if (permissionList != null) {
			for (Permission permission : permissionList) {
				existingUserPermission.put(permission.getId(), permission);
			}
		}

		MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
		merchantUserDTO.setEmailId(rmsUser.getEmail());
		merchantUserDTO.setLoginName(rmsUser.getUserName());
		merchantUserDTO.setUserId(rmsUser.getId());
		merchantUserDTO.setUserName(rmsUser.getName());

		for (Role rmsRole : rmsRoles) {

			if (roles.contains(rmsRole.getName())) {
				List<MerchantPermissionDTO> merchantPermissionList = new ArrayList<MerchantPermissionDTO>();
				MerchantRoleDTO roleDTO = new MerchantRoleDTO();
				roleDTO.setId(rmsRole.getId());
				roleDTO.setName(rmsRole.getName());
				List<Permission> rmsPermissionForRoleList = rmsRole.getPermissions();
				for (Permission permission : rmsPermissionForRoleList) {
					if (existingUserPermission.containsKey(permission.getId())) {
						MerchantPermissionDTO permissionDTO = new MerchantPermissionDTO();
						permissionDTO.setId(permission.getId());
						permissionDTO.setName(permission.getName());
						permissionDTO.setDisplayName(permission.getDisplayName());
						permissionDTO.setEnabled(true);
						merchantPermissionList.add(permissionDTO);
					}

				}
				roleDTO.setPermissions(merchantPermissionList);
				merchantRolesList.add(roleDTO);

			}
		}

		merchantUserDTO.setRoleList(merchantRolesList);

		GetUserMerchantResponse mobResponse = null;

		MerchantGetUserMerchantRequest merchantRequest = new MerchantGetUserMerchantRequest();
		merchantRequest.setUserId(rmsLoginResponse.getUser().getId());
		merchantRequest.setToken(rmsToken.getToken());

		mobResponse = mobUtil.getMerchantForUser(merchantRequest);
		if(mobResponse.getMerchantDetails().getMerchantId() == null)
		{
			log.info("Merchant Id is NUll in MOB while getting user for merchant: {}",merchantRequest.getUserId());
			throw new MerchantException(ErrorConstants.USER_TO_MERCHANT_MAPPING_ERROR_CODE, ErrorConstants.USER_TO_MERCHANT_MAPPING_ERROR_MSG);
		}
		mpResponse.setMerchantId(mobResponse.getMerchantDetails().getMerchantId());

		merchantUserDTO.setIntegrationMode(mobResponse.getMerchantDetails().getIntegrationMode());

		mpResponse.setUserDTO(merchantUserDTO);
		
		merchantStatus  = mobResponse.getMerchantDetails().getMerchantStatus() ;
		
		
		//Map<Integer, Permission> entrySet = existingUserPermission.entrySet();
		for (Map.Entry<Integer, Permission> entry : existingUserPermission.entrySet())
		{
		    //System.out.println(entry.getKey() + "/" + entry.getValue());
			Permission rmsPermission = entry.getValue();
			String permissionName = rmsPermission.getName();
			if(permissionName.equalsIgnoreCase(AppConstants.Edit_Merchant_Profile_Permission))
			{
				editMerchantPermission =true ;
			}else if(permissionName.equalsIgnoreCase(AppConstants.View_Profile_Permission))
			{
				viewProfilePermission =true ;
			}
			
		}

		if (editMerchantPermission && viewProfilePermission) {
			GetMerchantStateRequest merchantStaterequest = new GetMerchantStateRequest();
			merchantStaterequest.setMerchantId(mobResponse.getMerchantDetails().getMerchantId());
			merchantStaterequest.setToken(rmsLoginResponse.getToken().getToken());
			GetMerchantStateResponse merchantStateresponse = mobUtil.getMerchantProfileStatus(merchantStaterequest);
			mpResponse.setMerchantState(merchantStateresponse.getMerchantFlowDTO());
		} else if (merchantStatus.equalsIgnoreCase(MerchantStatus.BLOCKED.getMerchantStatus()) || merchantStatus.equalsIgnoreCase(MerchantStatus.CLOSED.getMerchantStatus()) ) {
			throw new MerchantException(ErrorConstants.SUB_ORDINATE_LOGIN_ERROR_CODE,ErrorConstants.SUB_ORDINATE_LOGIN_ERROR_MSG);
		}

		return mpResponse;
	}

	@Logged
	@Override
	public MerchantLogoutResponse logout(MerchantLogoutRequest request) throws MerchantException {

		rmsUtil.logout(request);
		MerchantLogoutResponse response = new MerchantLogoutResponse();
		response.setSuccess(true);
		return response;
	}

	@Logged
	@Override
	public MerchantSetPasswordResponse setPassword(MerchantSetPasswordRequest request) throws MerchantException {

		MerchantSetPasswordResponse response = rmsUtil.setPassword(request);

		return response;
	}

	/*
	 * public void setMpanelConfig(MpanelConfig config) { this.config = config;
	 * }
	 */
}