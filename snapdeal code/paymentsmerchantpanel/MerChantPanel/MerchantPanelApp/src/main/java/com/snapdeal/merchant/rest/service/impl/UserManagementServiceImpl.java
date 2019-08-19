package com.snapdeal.merchant.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.GeneralUserContactUsRequest;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantContactUsRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.GeneralUserContactUsResponse;
import com.snapdeal.merchant.response.MerchantAddUserResponse;
import com.snapdeal.merchant.response.MerchantAllUsersResponse;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantContactUsResponse;
import com.snapdeal.merchant.response.MerchantEditUserResponse;
import com.snapdeal.merchant.response.MerchantForgotPasswordResponse;
import com.snapdeal.merchant.response.MerchantGenerateOTPResponse;
import com.snapdeal.merchant.response.MerchantResendOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;
import com.snapdeal.merchant.rest.http.util.IMSUtil;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.rest.service.IUserManagementService;
import com.snapdeal.merchant.util.MOBMapperUtil;
import com.snapdeal.mob.dto.UserDetailsDTO;
import com.snapdeal.mob.response.AddUserResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.response.GetUsersforMerchantResponse;
import com.snapdeal.mob.response.UpdateUserResponse;
import com.snapdeal.mob.ui.response.MerchantDetails;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.roleManagementModel.request.Permission;
import com.snapdeal.payments.roleManagementModel.request.Role;
import com.snapdeal.payments.roleManagementModel.response.GenerateOTPResponse;
import com.snapdeal.payments.roleManagementModel.response.GetRolesByRoleNamesResponse;
import com.snapdeal.payments.roleManagementModel.response.GetUserByUserNameResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class UserManagementServiceImpl implements IUserManagementService {

	@Value("${mpanel.roles}")
	private List<String> roles;
	
	@Autowired
	private IMSUtil imsUtil;

	@Autowired
	private RMSUtil rmsUtil;

	@Autowired
	private MOBUtil mobUtil;
	
	@Autowired
	private MOBMapperUtil mobMapperUtil;

	@Logged
	@Override
	public MerchantAddUserResponse addUser(MerchantAddUserRequest request) throws MerchantException {

		MerchantAddUserResponse response = new MerchantAddUserResponse();

		AddUserResponse mobResponse = mobUtil.addUser(request);

		if(mobResponse != null)
			response.setSuccess(true);

		return response;

	}

	@Logged
	@Override
	public MerchantEditUserResponse editUser(MerchantEditUserRequest request)
			throws MerchantException {

		UpdateUserResponse mobResponse = mobUtil.editUser(request);
		MerchantEditUserResponse response = new MerchantEditUserResponse();

		if(mobResponse != null)
			response.setSuccess(true);

		return response;
	}

	@Logged
	@Override
	public MerchantAllUsersResponse getAllUsersOfMerchant(MerchantAllUsersRequest request)
			throws MerchantException {

		GetUsersforMerchantResponse mobResponse = mobUtil.getAllUsersOfMerchant(request);

		MerchantRoleRequest roleRequest = new MerchantRoleRequest();
		roleRequest.setRoles(roles);
		roleRequest.setToken(request.getToken());
		GetRolesByRoleNamesResponse rmsResponse = rmsUtil.getRoles(roleRequest);
		List<Role> rmsRoles = rmsResponse.getRoles();


		List<com.snapdeal.mob.dto.Role> mobRoles = new ArrayList<com.snapdeal.mob.dto.Role>();

		for(Role rmsRole : rmsRoles) {

			if(roles.contains(rmsRole.getName())) {
				com.snapdeal.mob.dto.Role mobRole = new com.snapdeal.mob.dto.Role();
				mobRole.setId(rmsRole.getId());
				mobRole.setName(rmsRole.getName());

				List<Permission> rmsPermissionList = rmsRole.getPermissions();
				List<com.snapdeal.mob.dto.Permission> mobPermissionList = new ArrayList<com.snapdeal.mob.dto.Permission>();

				for(Permission rmsPermission : rmsPermissionList){
					com.snapdeal.mob.dto.Permission mobPermission = new com.snapdeal.mob.dto.Permission();
					mobPermission.setId(rmsPermission.getId());
					mobPermission.setName(rmsPermission.getName());
					mobPermission.setDisplayName(rmsPermission.getDisplayName());
					mobPermissionList.add(mobPermission);
				}
				mobRole.setPermissions(mobPermissionList);
				mobRoles.add(mobRole);
			}


		}

		MerchantAllUsersResponse response = new MerchantAllUsersResponse();

		// creating merchantUserDTO list from mobResonse
		List<UserDetailsDTO> mobUserDTOList = mobResponse.getUserDetails();
		List<MerchantUserDTO> merchantUserDTOList = new ArrayList<MerchantUserDTO>();
		for (UserDetailsDTO userDetailsDTO : mobUserDTOList) {
			userDetailsDTO.setRoleList(mobRoles);

			MerchantUserDTO merchantUserDTO = (MerchantUserDTO) mobMapperUtil
					.merchantAndMobUserMapping(userDetailsDTO, UserMappingDirection.MOB_TO_MERCHANT);
			merchantUserDTOList.add(merchantUserDTO);
		}

		response.setUsers(merchantUserDTOList);
		return response;
	}

	@Logged
	@Override
	public MerchantForgotPasswordResponse forgotPassword(MerchantForgotPasswordRequest request)
			throws MerchantException {

		boolean bool = rmsUtil.forgotPassword(request);

		MerchantForgotPasswordResponse response = new MerchantForgotPasswordResponse();
		response.setSuccess(bool);

		return response;

	}

	@Logged
	@Override
	public MerchantChangePasswordResponse changePassword(MerchantChangePasswordRequest request)
			throws MerchantException {

		MerchantChangePasswordResponse response = new MerchantChangePasswordResponse();
		response = rmsUtil.changePassword(request);
		return response;
	}

	@Logged
	@Override
	public MerchantVerifyUserResponse verifyUser(MerchantVerifyUserRequest request) throws MerchantException {

		MerchantVerifyUserResponse response = null;
		response = new MerchantVerifyUserResponse();
		GetUserByUserNameResponse rmsResponse = rmsUtil.getUserDetails(request);
		if(rmsResponse != null){
			response.setUserPresent(true);
		}
		return response;
	}

	@Logged
	@Override
	public MerchantGenerateOTPResponse generateOTP(MerchantGenerateOTPRequest request) throws MerchantException {

		MerchantGenerateOTPResponse response = new MerchantGenerateOTPResponse();
		GenerateOTPResponse rmsResponse = rmsUtil.generateOTP(request);

		response.setOtpId(rmsResponse.getOtpId()); 

		return response;
	}

	@Logged
	@Override
	public MerchantVerifyOTPResponse verifyOTP(MerchantVerifyOTPRequest request) throws MerchantException {

		MerchantVerifyOTPResponse response = new MerchantVerifyOTPResponse();
		response = rmsUtil.verifyOTP(request);
		return response;
	}

	@Logged
	@Override
	public MerchantResendOTPResponse resendOTP(MerchantResendOTPRequest request) throws MerchantException {

		MerchantResendOTPResponse response = new MerchantResendOTPResponse();
		GenerateOTPResponse rmsResponse = rmsUtil.resendOTP(request);

		response.setOtpId(rmsResponse.getOtpId());

		return response;

	}

	@Logged
	@Override
	public MerchantContactUsResponse MerchantUserContactUs(MerchantContactUsRequest request) throws MerchantException {
		
		MerchantContactUsResponse response;
		MerchantGetUserMerchantRequest mobRequest =  new MerchantGetUserMerchantRequest();
		mobRequest.setUserId(request.getLoggedUserId());
		mobRequest.setToken(request.getToken());
		mobRequest.setMerchantId(request.getMerchantId());
		GetUserMerchantResponse mobResponse;
	
		mobResponse = mobUtil.getMerchantForUser(mobRequest);
		
		MerchantDetails merchantDetails = mobResponse.getMerchantDetails();
		
		String merchantName =  merchantDetails.getMerchantName();
		
		request.setMerchantName(merchantName);
		
		response = imsUtil.sendMerchantUserContactUsEmail(request);
		
		return response;
	}
	
	@Logged
	@Override
	public GeneralUserContactUsResponse GeneralUserContactUs(GeneralUserContactUsRequest request)
			throws MerchantException {
		GeneralUserContactUsResponse response = null;
		
		response = imsUtil.sendGeneralUserContactUsEmail(request);
		
		return response;
	}
	
	public void setRmsUtil(RMSUtil rmsUtil) {
		this.rmsUtil = rmsUtil;
	}

	public void setMobUtil(MOBUtil mobUtil) {
		this.mobUtil = mobUtil;
	}
	
	public void setImsUtil(IMSUtil imsUtil) {
		this.imsUtil = imsUtil;
	}
	
	public void setroles(List<String> roles) {
		this.roles = roles;
	}

	
}
