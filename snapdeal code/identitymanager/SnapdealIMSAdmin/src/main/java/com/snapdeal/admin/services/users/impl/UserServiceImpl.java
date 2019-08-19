/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 22, 2010
 *  @author singla
 */
package com.snapdeal.admin.services.users.impl;

import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.admin.services.users.IUserService;
import com.snapdeal.ums.client.services.IUserClientService;
import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;
//import com.snapdeal.ums.core.sro.user.RoleZoneMappingSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO.Role;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.ext.user.AddUserRequest;
import com.snapdeal.ums.ext.user.AddUserResponse;
import com.snapdeal.ums.ext.user.ClearEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.CreateEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.CreateEmailVerificationCodeResponse;
//import com.snapdeal.ums.ext.user.CreateRoleZoneMapRequest;
//import com.snapdeal.ums.ext.user.CreateRoleZoneMapResponse;
import com.snapdeal.ums.ext.user.CreateUserRequest;
import com.snapdeal.ums.ext.user.CreateUserResponse;
import com.snapdeal.ums.ext.user.CreateUserRoleRequest;
import com.snapdeal.ums.ext.user.CreateUserRoleResponse;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeRequest;
import com.snapdeal.ums.ext.user.GetEmailVerificationCodeResponse;
import com.snapdeal.ums.ext.user.GetOpenIdUserRequest;
import com.snapdeal.ums.ext.user.GetOpenIdUserResponse;
import com.snapdeal.ums.ext.user.GetOrCreateVendorUserRequest;
import com.snapdeal.ums.ext.user.GetOrCreateVendorUserResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.GetUserByIdRequest;
import com.snapdeal.ums.ext.user.GetUserByIdResponse;
import com.snapdeal.ums.ext.user.GetUsersByRoleAndZoneRequest;
import com.snapdeal.ums.ext.user.GetUsersByRoleAndZoneResponse;
import com.snapdeal.ums.ext.user.IsUserExistsRequest;
import com.snapdeal.ums.ext.user.IsUserExistsResponse;
import com.snapdeal.ums.ext.user.PersistUserRequest;
import com.snapdeal.ums.ext.user.UpdateUserRequest;
import com.snapdeal.ums.ext.user.UpdateUserResponse;
import com.snapdeal.ums.ext.user.VerifyUserRequest;
import com.snapdeal.ums.ext.user.VerifyUserResponse;
import com.snapdeal.ums.subscription.client.services.ISubscriptionsClientService;

@Transactional
@Service("userService")
public class UserServiceImpl implements IUserService {

    private static final Logger      LOG = LoggerFactory.getLogger(UserServiceImpl.class);

    @Autowired
    private IUserClientService       userClientService;


    @Autowired
    private ISubscriptionsClientService  subScriptionService;

    @Override
    public boolean isUserExists(String email) {
        IsUserExistsRequest isUserExistsRequest = new IsUserExistsRequest(email);
        IsUserExistsResponse isUserExistsResponse = userClientService.isUserExists(isUserExistsRequest);
        return isUserExistsResponse.getIsUserExists();
    }

    @Override
    public UserSRO addUser(UserSRO user) {
        AddUserRequest addUserRequest = new AddUserRequest(user);
        AddUserResponse addUserResponse = userClientService.addUser(addUserRequest);
        UserSRO addedUser = addUserResponse.getAddUser();
        return addedUser;
    }

    @Override
    public void persistUser(UserSRO user) {
        PersistUserRequest persistUserRequest = new PersistUserRequest(user);
        userClientService.persistUser(persistUserRequest);
        return;
    }

    @Override
    public UserSRO getUserById(int id) {
        GetUserByIdRequest getUserByIdRequest = new GetUserByIdRequest(id);
        GetUserByIdResponse getUserByIdResponse = userClientService.getUserById(getUserByIdRequest);
        UserSRO user = getUserByIdResponse.getGetUserById();
        return user;
    }

    @Override
    public UserSRO getUserByEmail(String email) {
        GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest(email);
        GetUserByEmailResponse getUserByEmailResponse = userClientService.getUserByEmail(getUserByEmailRequest);
        
        if(!getUserByEmailResponse.isSuccessful() || getUserByEmailResponse.getGetUserByEmail() == null){
            LOG.error("UMS response is false or user is null for email {}", email);
            return null;
        }
        
        UserSRO user = getUserByEmailResponse.getGetUserByEmail();
        
        for(UserRoleSRO role : user.getUserRoles()){
            LOG.info(" -----roles----", role.getRole());
            System.out.println(role.getRole());
        }
        return user;
    }

    @Override
    public UserSRO updateUser(UserSRO user) {
        UpdateUserRequest updateUserRequest = new UpdateUserRequest(user);
        UpdateUserResponse updateUserResponse = userClientService.updateUser(updateUserRequest);
        UserSRO updatedUser = updateUserResponse.getUpdateUser();
        return updatedUser;
    }

    @Override
    public boolean verifyUser(String email, String code) {
        VerifyUserRequest verifyUserRequest = new VerifyUserRequest(email, code);
        VerifyUserResponse verifyUserResponse = userClientService.verifyUser(verifyUserRequest);
        return verifyUserResponse.getVerifyUser();
    }


    @Override
    public UserRoleSRO createUserRole(UserRoleSRO userRole) {
        CreateUserRoleRequest createUserRoleRequest = new CreateUserRoleRequest(userRole);
        CreateUserRoleResponse createUserRoleResponse = userClientService.createUserRole(createUserRoleRequest);
        UserRoleSRO createdUserRole = createUserRoleResponse.getCreateUserRole();
        return createdUserRole;
    }

 /*   @Override
    public RoleZoneMappingSRO createRoleZoneMap(RoleZoneMappingSRO roleZoneMap) {
        CreateRoleZoneMapRequest createRoleZoneMapRequest = new CreateRoleZoneMapRequest(roleZoneMap);
        CreateRoleZoneMapResponse createRoleZoneMapResponse = userClientService.createRoleZoneMap(createRoleZoneMapRequest);
        RoleZoneMappingSRO roleZoneMapping = createRoleZoneMapResponse.getCreateRoleZoneMap();
        return roleZoneMapping;
    }   */

    @Override
    public UserSRO getOrCreateVendorUser(String email) {
        GetOrCreateVendorUserRequest createVendorUserRequest = new GetOrCreateVendorUserRequest(email);
        GetOrCreateVendorUserResponse createVendorUserResponse = userClientService.getOrCreateVendorUser(createVendorUserRequest);
        UserSRO user = createVendorUserResponse.getGetOrCreateVendorUser();
        return user;
    }


    @Override
    public EmailVerificationCodeSRO createEmailVerificationCode(String email, String source, String targetUrl) {
        CreateEmailVerificationCodeRequest createEmailVerificationCodeRequest = new CreateEmailVerificationCodeRequest(email, source, targetUrl);
        CreateEmailVerificationCodeResponse createEmailVerificationCodeResponse = userClientService.createEmailVerificationCode(createEmailVerificationCodeRequest);
        EmailVerificationCodeSRO emailVerificationCode = createEmailVerificationCodeResponse.getCreateEmailVerificationCode();
        return emailVerificationCode;
    }

    @Override
    public EmailVerificationCodeSRO getEmailVerificationCode(String email) {
        GetEmailVerificationCodeRequest getEmailVerificationCodeRequest = new GetEmailVerificationCodeRequest(email);
        GetEmailVerificationCodeResponse getemailVerificationCodeResponse = userClientService.getEmailVerificationCode(getEmailVerificationCodeRequest);
        EmailVerificationCodeSRO emailVerificationCode = getemailVerificationCodeResponse.getGetEmailVerificationCode();
        return emailVerificationCode;
    }

    @Override
    public void clearEmailVerificationCode(String email) {
        ClearEmailVerificationCodeRequest clearEmailVerificationCodeRequest = new ClearEmailVerificationCodeRequest(email);
        userClientService.clearEmailVerificationCode(clearEmailVerificationCodeRequest);
        return;
    }

    @Override
    public List<UserRoleSRO> getUsersByRoleAndZone(String role, List<Integer> zones) {
        GetUsersByRoleAndZoneRequest getUsersByRoleAndZoneRequest = new GetUsersByRoleAndZoneRequest(role, zones);
        GetUsersByRoleAndZoneResponse getUsersByRoleAndZoneResponse = userClientService.getUsersByRoleAndZone(getUsersByRoleAndZoneRequest);
        List<UserRoleSRO> userRoleSROs = getUsersByRoleAndZoneResponse.getUsersByRoleAndZone();
        return userRoleSROs;
    }

    /*@Override
    public List<Integer> getZonesForUserRoles(Set<UserRoleSRO> userRoles) {
        GetZonesForUserRolesRequest getZonesForUserRolesRequest = new GetZonesForUserRolesRequest(userRoles);
        GetZonesForUserRolesResponse getZonesForUserRolesResponse = userClientService.getZonesForUserRoles(getZonesForUserRolesRequest);
        return getZonesForUserRolesResponse.getGetZonesForUserRoles();
    }*/
    
    @Override
    public UserSRO getOpenIdUser(UserSRO rpxUser) {
        GetOpenIdUserRequest getOpenIdUserRequest = new GetOpenIdUserRequest(rpxUser);
        GetOpenIdUserResponse getOpenIdUserResponse = userClientService.getOpenIdUser(getOpenIdUserRequest);
        UserSRO user = getOpenIdUserResponse.getGetOpenIdUser();
        return user;
    }
    
    @Override
    public UserSRO createUser(String email, String password, Role initialRole, String source, String targetUrl, boolean autocreated) {
        CreateUserRequest createUserRequest = new CreateUserRequest(email, password, initialRole, source, targetUrl, autocreated);
        CreateUserResponse createUserResponse = userClientService.createUser(createUserRequest);
        UserSRO user = createUserResponse.getCreateUser();
        return user;
    }

}
