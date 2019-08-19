package com.snapdeal.merchant.rest.stub.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Service;

import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantLoginRequest;
import com.snapdeal.merchant.request.MerchantLogoutRequest;
import com.snapdeal.merchant.request.MerchantSetPasswordRequest;
import com.snapdeal.merchant.response.MerchantLoginResponse;
import com.snapdeal.merchant.response.MerchantLogoutResponse;
import com.snapdeal.merchant.response.MerchantSetPasswordResponse;
import com.snapdeal.merchant.rest.service.ISessionService;

@Service
public class StubSessionServiceImpl implements ISessionService {

	@Override
	public MerchantLoginResponse login(MerchantLoginRequest request) throws MerchantException {
		MerchantLoginResponse response = new MerchantLoginResponse();

		MerchantUserDTO userDetails = new MerchantUserDTO();
		userDetails.setEmailId("emailId@gmail.com");
		userDetails.setUserName("userName1");
		userDetails.setLoginName(request.getLoginName());
		userDetails.setUserId("userId1");

		// Making Permissions for user
		List<MerchantPermissionDTO> permissions = new ArrayList<MerchantPermissionDTO>();
		MerchantPermissionDTO p1 = new MerchantPermissionDTO();
		p1.setId(100);
		p1.setName("name1");
		p1.setDisplayName("displayName1");
		p1.setEnabled(true);
		permissions.add(p1);

		MerchantPermissionDTO p2 = new MerchantPermissionDTO();
		p2.setId(101);
		p2.setName("name2");
		p1.setDisplayName("displayName2");
		p1.setEnabled(true);
		permissions.add(p1);

		List<MerchantRoleDTO> roleList = new ArrayList<MerchantRoleDTO>();
		MerchantRoleDTO role = new MerchantRoleDTO();
		role.setId(101);
		role.setName("displayRoleName");
		role.setPermissions(permissions);

		roleList.add(role);

		userDetails.setRoleList(roleList);

		response.setToken("user_token");
		response.setUserDTO(userDetails);
		response.setMerchantId(UUID.randomUUID().toString());

		return response;
	}

	@Override
	public MerchantLogoutResponse logout(MerchantLogoutRequest request) throws MerchantException {
		MerchantLogoutResponse response = new MerchantLogoutResponse();
		response.setSuccess(true);
		return response;
	}

	@Override
	public MerchantSetPasswordResponse setPassword(MerchantSetPasswordRequest request) throws MerchantException {

		MerchantSetPasswordResponse response = new MerchantSetPasswordResponse();
		response.setSuccess(true);
		return response;
	}

}
