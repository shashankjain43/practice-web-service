package com.snapdeal.merchant.rest.stub.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantChangePasswordRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantForgotPasswordRequest;
import com.snapdeal.merchant.request.MerchantGenerateOTPRequest;
import com.snapdeal.merchant.request.MerchantResendOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyOTPRequest;
import com.snapdeal.merchant.request.MerchantVerifyUserRequest;
import com.snapdeal.merchant.response.MerchantAddUserResponse;
import com.snapdeal.merchant.response.MerchantAllUsersResponse;
import com.snapdeal.merchant.response.MerchantChangePasswordResponse;
import com.snapdeal.merchant.response.MerchantEditUserResponse;
import com.snapdeal.merchant.response.MerchantForgotPasswordResponse;
import com.snapdeal.merchant.response.MerchantGenerateOTPResponse;
import com.snapdeal.merchant.response.MerchantResendOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyOTPResponse;
import com.snapdeal.merchant.response.MerchantVerifyUserResponse;
import com.snapdeal.merchant.rest.service.IUserManagementService;

@Service
public class StubUserManagementServiceImpl implements IUserManagementService {

   @Override
   public MerchantAddUserResponse addUser(MerchantAddUserRequest request) throws MerchantException {

      MerchantAddUserResponse response = new MerchantAddUserResponse();
      response.setSuccess(true);
      return response;
   }

   @Override
   public MerchantEditUserResponse editUser(MerchantEditUserRequest request)
            throws MerchantException {
      MerchantEditUserResponse response = new MerchantEditUserResponse();
      response.setSuccess(true);
      return response;
   }

   @Override
   public MerchantAllUsersResponse getAllUsersOfMerchant(MerchantAllUsersRequest request)
            throws MerchantException {
      MerchantAllUsersResponse response = new MerchantAllUsersResponse();
      List<MerchantUserDTO> usersList = createUserList();

      response.setUsers(usersList);

      return response;

   }

   @Override
   public MerchantForgotPasswordResponse forgotPassword(MerchantForgotPasswordRequest request)
            throws MerchantException {
      MerchantForgotPasswordResponse response = new MerchantForgotPasswordResponse();
      response.setSuccess(true);
      return response;
   }

   @Override
   public MerchantChangePasswordResponse changePassword(MerchantChangePasswordRequest request)
            throws MerchantException {
      MerchantChangePasswordResponse response = new MerchantChangePasswordResponse();
      response.setSuccess(true);
      return response;
   }

   // Method to create users for merchant
   public List<MerchantUserDTO> createUserList() {
      List<MerchantUserDTO> usersList = new ArrayList<MerchantUserDTO>();
      // creating 1st user
      MerchantUserDTO user1 = new MerchantUserDTO();
      user1.setEmailId("user1@gamil.com");
      user1.setLoginName("user1");
      user1.setUserName("userName");
      user1.setUserId("102");

      // making permission for users
      List<MerchantPermissionDTO> permissionList = new ArrayList<MerchantPermissionDTO>();
      MerchantPermissionDTO merchantPermission1 = new MerchantPermissionDTO();
      merchantPermission1.setEnabled(true);
      merchantPermission1.setId(101);
      merchantPermission1.setName("perm1");
      permissionList.add(merchantPermission1);

      MerchantPermissionDTO merchantPermission2 = new MerchantPermissionDTO();
      merchantPermission2.setEnabled(true);
      merchantPermission2.setId(102);
      merchantPermission2.setName("perm2");
      permissionList.add(merchantPermission2);

      MerchantPermissionDTO merchantPermission3 = new MerchantPermissionDTO();
      merchantPermission3.setEnabled(false);
      merchantPermission3.setName("perm3");
      merchantPermission3.setId(103);
      permissionList.add(merchantPermission3);

      MerchantRoleDTO role1 = new MerchantRoleDTO();
      List<MerchantRoleDTO> roleList1 = new ArrayList<MerchantRoleDTO>();
      role1.setPermissions(permissionList);
      role1.setName("roleDisplayName1");
      role1.setId(112);

      roleList1.add(role1);

      user1.setRoleList(roleList1);

      usersList.add(user1);

      // creating 2nd user
      MerchantUserDTO user2 = new MerchantUserDTO();
      user2.setEmailId("use21@gamil.com");
      user2.setLoginName("user2");
      user2.setUserName("userName2");
      user2.setUserId("101");
      MerchantRoleDTO role2 = new MerchantRoleDTO();
      List<MerchantRoleDTO> roleList2 = new ArrayList<MerchantRoleDTO>();
      role2.setPermissions(permissionList);
      role2.setName("roleDisplayName");
      role2.setId(123);

      roleList2.add(role2);
      user2.setRoleList(roleList2);
      usersList.add(user2);

      return usersList;

   }

@Override
public MerchantVerifyUserResponse verifyUser(MerchantVerifyUserRequest request) throws MerchantException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public MerchantGenerateOTPResponse generateOTP(MerchantGenerateOTPRequest request) throws MerchantException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public MerchantVerifyOTPResponse verifyOTP(MerchantVerifyOTPRequest request) throws MerchantException {
	// TODO Auto-generated method stub
	return null;
}

@Override
public MerchantResendOTPResponse resendOTP(MerchantResendOTPRequest request) throws MerchantException {
	// TODO Auto-generated method stub
	return null;
}

}
