package com.snapdeal.merchant.test.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantTDRDetailsDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.mob.dto.BusinessInformationDTO;
import com.snapdeal.mob.dto.Permission;
import com.snapdeal.mob.dto.Role;
import com.snapdeal.mob.dto.TDRDetailsDTO;
import com.snapdeal.mob.dto.UserDetailsDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class MOBMapper {

	
	
   @SuppressWarnings("unchecked")
   public static Object merchantAndMobUserMapping(Object object,
            UserMappingDirection userMappingDirection) {
      if (userMappingDirection == UserMappingDirection.MOB_TO_MERCHANT) {
         UserDetailsDTO userDetailDTO = (UserDetailsDTO) object;
         MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
         merchantUserDTO.setEmailId(userDetailDTO.getEmailId());
         merchantUserDTO.setLoginName(userDetailDTO.getLoginName());
         merchantUserDTO.setUserId(userDetailDTO.getUserId());
         merchantUserDTO.setUserName(userDetailDTO.getName());

         // creating role for merchant
         List<MerchantRoleDTO> merchantroleList = null;

         merchantroleList = (List<MerchantRoleDTO>) merchantAndMobRoleMapping(userDetailDTO,
                  userMappingDirection);

         merchantUserDTO.setRoleList(merchantroleList);
         return merchantUserDTO;

      } else if (userMappingDirection == UserMappingDirection.MERCHANT_TO_MOB) {
         MerchantUserDTO merchantUserDTO = (MerchantUserDTO) object;
         UserDetailsDTO userDetailDTO = new UserDetailsDTO();
         userDetailDTO.setEmailId(merchantUserDTO.getEmailId());
         userDetailDTO.setLoginName(merchantUserDTO.getLoginName());
         userDetailDTO.setName(merchantUserDTO.getUserName());
         userDetailDTO.setUserId(merchantUserDTO.getUserId());

         List<Role> roleList = new ArrayList<Role>();

         List<MerchantRoleDTO> merchantroleList = merchantUserDTO.getRoleList();
         for (MerchantRoleDTO merchantRoleDTO : merchantroleList) {
            roleList.add((Role) merchantAndMobRoleMapping(merchantRoleDTO, userMappingDirection));

         }

         userDetailDTO.setRoleList(roleList);
         return userDetailDTO;
      }

      else
         return null;
   }

   public static Object merchantAndMobRoleMapping(Object object,
            UserMappingDirection userMappingDirection) {

      if (userMappingDirection == UserMappingDirection.MOB_TO_MERCHANT) {
         UserDetailsDTO userDetails = (UserDetailsDTO) object;

         List<Role> mobRoleList = userDetails.getRoleList();

         HashMap<Integer, Permission> existingUserPermission = new HashMap<Integer, Permission>();

         // roles dto to be returned
         List<MerchantRoleDTO> merchantRolesList = new ArrayList<MerchantRoleDTO>();
         // get user Permission
         List<Permission> mobPermissionList = userDetails.getPermissionList();

         if (mobPermissionList != null) {
            for (Permission permission : mobPermissionList) {
               existingUserPermission.put(permission.getId(), permission);
            }
         }

         for (Role mobRole : mobRoleList) {

            MerchantRoleDTO merchantRoleDTO = new MerchantRoleDTO();
            merchantRoleDTO.setName(mobRole.getName());
            merchantRoleDTO.setId(mobRole.getId());

            List<Permission> roleMobPermissionList = mobRole.getPermissions();

            List<MerchantPermissionDTO> merchantPermissionDTOList = new ArrayList<MerchantPermissionDTO>();

            for (Permission roleMobPermission : roleMobPermissionList) {

               if (existingUserPermission.containsKey(roleMobPermission.getId())) {

                  MerchantPermissionDTO merchantPermissionDTO = new MerchantPermissionDTO();
                  merchantPermissionDTO.setDisplayName(roleMobPermission.getDisplayName());
                  merchantPermissionDTO.setName(roleMobPermission.getName());
                  merchantPermissionDTO.setId(roleMobPermission.getId());
                  merchantPermissionDTO.setEnabled(true);
                  merchantPermissionDTOList.add(merchantPermissionDTO);
               }

            }
            merchantRoleDTO.setPermissions(merchantPermissionDTOList);
            merchantRolesList.add(merchantRoleDTO);
         }

         return merchantRolesList;

      }

      if (userMappingDirection == UserMappingDirection.MERCHANT_TO_MOB) {
         MerchantRoleDTO merchantRoleDTO = (MerchantRoleDTO) object;
         Role mobRole = new Role();
         mobRole.setName(merchantRoleDTO.getName());
         mobRole.setId(merchantRoleDTO.getId());

         List<Permission> mobPermissionList = new ArrayList<Permission>();
         List<MerchantPermissionDTO> merchantPermissionDTOList = merchantRoleDTO.getPermissions();

         for (MerchantPermissionDTO merchantPermissionDTO : merchantPermissionDTOList) {
            Permission mobPermission = new Permission();
            mobPermission.setDisplayName(merchantPermissionDTO.getDisplayName());
            mobPermission.setId(merchantPermissionDTO.getId());
            mobPermission.setName(merchantPermissionDTO.getName());

            mobPermissionList.add(mobPermission);
         }
         mobRole.setPermissions(mobPermissionList);

         return mobRole;
      }

      else
         return null;

   }
   
	
}
