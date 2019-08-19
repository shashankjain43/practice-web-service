/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 22, 2010
 *  @author singla
 */
package com.snapdeal.admin.services.users;

import java.util.List;

import com.snapdeal.ums.core.sro.user.EmailVerificationCodeSRO;
//import com.snapdeal.ums.core.sro.user.RoleZoneMappingSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO.Role;
import com.snapdeal.ums.core.sro.user.UserSRO;

public interface IUserService {
    public boolean isUserExists(String email);

    public UserSRO addUser(UserSRO user);

    public UserSRO getUserByEmail(String email);

    public UserSRO updateUser(UserSRO user);

    public UserRoleSRO createUserRole(UserRoleSRO userRole);

    public UserSRO getOrCreateVendorUser(String email);

    public UserSRO getUserById(int id);
    
    public EmailVerificationCodeSRO getEmailVerificationCode(String email);
    
    public void clearEmailVerificationCode(String email);
    
    void persistUser(UserSRO user);
    
    public boolean verifyUser(String email, String code);
    
    public EmailVerificationCodeSRO createEmailVerificationCode(String email, String source, String targetUrl);
    
//    public RoleZoneMappingSRO createRoleZoneMap(RoleZoneMappingSRO roleZoneMap);

    List<UserRoleSRO> getUsersByRoleAndZone(String role, List<Integer> zones);

    UserSRO getOpenIdUser(UserSRO rpxUser);
    
    public UserSRO createUser(String email, String password, Role initialRole, String source, String targetUrl, boolean autocreated);
    
}