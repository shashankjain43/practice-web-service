package com.snapdeal.merchant.test.util;

import java.util.ArrayList;
import java.util.List;

import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.payments.roleManagementModel.request.Permission;
import com.snapdeal.payments.roleManagementModel.request.Role;
import com.snapdeal.payments.roleManagementModel.request.User;

public class RMSMapper {

   public static Object merchantAndRMSUserMapping(Object object,
            UserMappingDirection userMappingDirection, boolean assignEnabledFlag) {
      if (userMappingDirection == UserMappingDirection.RMS_TO_MERCHANT) {
         User rmsUser = (User) object;
         MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
         merchantUserDTO.setEmailId(rmsUser.getEmail());
         merchantUserDTO.setLoginName(rmsUser.getUserName());
         merchantUserDTO.setUserId(rmsUser.getId());
         merchantUserDTO.setUserName(rmsUser.getName());

         // creating role for merchant
         List<MerchantRoleDTO> merchantroleList = new ArrayList<MerchantRoleDTO>();
         List<Role> rmsRoleList = rmsUser.getRoles();
         for (Role rmsRole : rmsRoleList) {

            merchantroleList.add((MerchantRoleDTO) merchantAndRMSRoleMapping(rmsRole,
                     userMappingDirection, assignEnabledFlag, merchantUserDTO));
         }
         merchantUserDTO.setRoleList(merchantroleList);
         return merchantUserDTO;
      } else if (userMappingDirection == UserMappingDirection.MERCHANT_TO_RMS) {
         MerchantUserDTO merchantUserDTO = (MerchantUserDTO) object;
         User rmsUser = new User();

         rmsUser.setEmail(merchantUserDTO.getEmailId());
         rmsUser.setId(merchantUserDTO.getUserId());
         rmsUser.setName(merchantUserDTO.getUserName());
         rmsUser.setUserName(merchantUserDTO.getLoginName());

         // creating role for merchant
         List<Role> rmsRoleList = new ArrayList<Role>();
         List<MerchantRoleDTO> merchantroleList = merchantUserDTO.getRoleList();

         for (MerchantRoleDTO merchantRoleDTO : merchantroleList) {

            rmsRoleList.add((Role) merchantAndRMSRoleMapping(merchantRoleDTO, userMappingDirection,
                     assignEnabledFlag, rmsUser));
         }
         rmsUser.setRoles(rmsRoleList);
         return rmsUser;
      } else
         return null;
   }

   public static Object merchantAndRMSRoleMapping(Object object,
            UserMappingDirection userMappingDirection, boolean assignEnabledFlag, Object object1) {

      if (userMappingDirection == UserMappingDirection.RMS_TO_MERCHANT) {
         Role rmsRole = (Role) object;

         MerchantRoleDTO merchantRoleDTO = new MerchantRoleDTO();
         merchantRoleDTO.setName(rmsRole.getName());
         merchantRoleDTO.setId(rmsRole.getId());

         // creating merchant Permission
         List<Permission> rmsPermissionList = rmsRole.getPermissions();
         List<MerchantPermissionDTO> merchantPermissionDTOList = new ArrayList<MerchantPermissionDTO>();

         for (Permission rmsPermission : rmsPermissionList) {
            MerchantPermissionDTO merchantPermissionDTO = new MerchantPermissionDTO();
            merchantPermissionDTO.setDisplayName(rmsPermission.getDisplayName());
            merchantPermissionDTO.setId(rmsPermission.getId());
            merchantPermissionDTO.setName(rmsPermission.getName());
            if (assignEnabledFlag == true)
               merchantPermissionDTO.setEnabled(true);

            merchantPermissionDTOList.add(merchantPermissionDTO);
         }
         merchantRoleDTO.setPermissions(merchantPermissionDTOList);

         return merchantRoleDTO;

      } else if (userMappingDirection == UserMappingDirection.MERCHANT_TO_RMS) {

         Role rmsRole = new Role();

         MerchantRoleDTO merchantRoleDTO = (MerchantRoleDTO) object;
         rmsRole.setId(merchantRoleDTO.getId());
         rmsRole.setName(merchantRoleDTO.getName());

         // creating merchant Permission
         List<Permission> rmsPermissionList = new ArrayList<Permission>();
         List<MerchantPermissionDTO> merchantPermissionDTOList = merchantRoleDTO.getPermissions();

         for (MerchantPermissionDTO merchantPermissionDTO : merchantPermissionDTOList) {

            Permission rmsPermission = new Permission();
            rmsPermission.setDisplayName(merchantPermissionDTO.getDisplayName());
            rmsPermission.setName(merchantPermissionDTO.getName());
            rmsPermission.setId(merchantPermissionDTO.getId());

            rmsPermissionList.add(rmsPermission);
         }
         rmsRole.setPermissions(rmsPermissionList);

         User rmsUser = (User) object1;
         rmsUser.setPermissions(rmsPermissionList);

         return rmsRole;
      } else
         return null;

   }

}
