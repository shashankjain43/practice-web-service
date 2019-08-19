package com.snapdeal.merchant.util;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantBankInfo;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.dto.MerchantDocCategoryAndTypeDTO;
import com.snapdeal.merchant.dto.MerchantKey;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantTDRDetailsDTO;
import com.snapdeal.merchant.dto.MerchantUploadedDocsDTO;
import com.snapdeal.merchant.dto.MerchantUserDTO;
import com.snapdeal.merchant.enums.MerchantEnvType;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.response.MerchantGetKYCDocListResponse;
import com.snapdeal.mob.dto.BankAccountDetailsDTO;
import com.snapdeal.mob.dto.BusinessInformationDTO;
import com.snapdeal.mob.dto.DocCategoryAndTypeDTO;
import com.snapdeal.mob.dto.DocumentDTO;
import com.snapdeal.mob.dto.Key;
import com.snapdeal.mob.dto.Permission;
import com.snapdeal.mob.dto.Role;
import com.snapdeal.mob.dto.TDRDetailsDTO;
import com.snapdeal.mob.dto.UserDetailsDTO;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class MOBMapperUtil {
	
	@Autowired
	private MpanelConfig config;

	public  Object merchantAndMobBankDTOMapping(Object object, UserMappingDirection userMappingDirection)
	{
		
		if(userMappingDirection == UserMappingDirection.MOB_TO_MERCHANT)
		{
			MerchantBankInfo merchantBankInfo = null;
			BankAccountDetailsDTO mobBankDto = (BankAccountDetailsDTO) object;
			if(mobBankDto != null)
			{
				merchantBankInfo=  new MerchantBankInfo();
				merchantBankInfo.setAccountHolderName(mobBankDto.getAccountHolderName());
				merchantBankInfo.setBankAccount(mobBankDto.getAccountNumber());
				merchantBankInfo.setBankName(mobBankDto.getBankName());
				merchantBankInfo.setBankStatus(mobBankDto.getBankStatus());
				merchantBankInfo.setIfscCode(mobBankDto.getIfsccode());
				//merchantBankInfo.setDisburseDifferencePeriod(mobBankDto.getDisburseDifferencePeriod());
			}
			return merchantBankInfo;
		}
		else if(userMappingDirection == UserMappingDirection.MERCHANT_TO_MOB)
		{
			BankAccountDetailsDTO mobBankDto = null;
			MerchantBankInfo merchantBankInfo = (MerchantBankInfo) object;
			if(merchantBankInfo != null)
			{
				mobBankDto=  new BankAccountDetailsDTO();
				mobBankDto.setAccountHolderName(merchantBankInfo.getAccountHolderName());
				mobBankDto.setAccountNumber(merchantBankInfo.getBankAccount());
				mobBankDto.setBankName(merchantBankInfo.getBankName());
				mobBankDto.setIfsccode(merchantBankInfo.getIfscCode());
				//mobBankDto.setDisburseDifferencePeriod(config.getMerchantDisbursePeriod());
			}
			return mobBankDto;
			
		}
		else return null;
		
	}
	
	public  Object merchantAndMobBusinessDTOMapping(Object object, UserMappingDirection userMappingDirection) {
		if(userMappingDirection == UserMappingDirection.MOB_TO_MERCHANT) {
			MerchantBusinessInfo merchantbusinessDto = null;
			BusinessInformationDTO mobBusinessDto = (BusinessInformationDTO) object;
			if (mobBusinessDto != null) {
				merchantbusinessDto = new MerchantBusinessInfo();
				merchantbusinessDto.setAddress1(mobBusinessDto.getAddress1());
				merchantbusinessDto.setAddress2(mobBusinessDto.getAddress2());
				merchantbusinessDto.setAppName(mobBusinessDto.getAppName());
				merchantbusinessDto.setBusinessCategory(mobBusinessDto.getBusinessCategory());
				merchantbusinessDto.setBusinessName(mobBusinessDto.getBusinessName());
				merchantbusinessDto.setBusinessType(mobBusinessDto.getBusinessType());
				merchantbusinessDto.setCity(mobBusinessDto.getCity());
				merchantbusinessDto.setDateOfFormation(mobBusinessDto.getDateOfFormation());
				merchantbusinessDto.setLandLineNumber(mobBusinessDto.getLandLineNumber());
				merchantbusinessDto.setLogoUrl(mobBusinessDto.getLogoUrl());
				merchantbusinessDto.setMerchantName(mobBusinessDto.getMerchantName());
				merchantbusinessDto.setMerchantReserves(mobBusinessDto.getMerchantReserves());
				merchantbusinessDto.setPincode(mobBusinessDto.getPincode());
				merchantbusinessDto.setPrimaryMobile(mobBusinessDto.getPrimaryMobile());
				merchantbusinessDto.setSecondaryEmail(mobBusinessDto.getSecondaryEmail());
				merchantbusinessDto.setSecondaryMobile(mobBusinessDto.getSecondaryMobile());
				merchantbusinessDto.setShopName(mobBusinessDto.getShopName());
				merchantbusinessDto.setState(mobBusinessDto.getState());
				merchantbusinessDto.setStdCode(mobBusinessDto.getStdCode());
				merchantbusinessDto.setSubCategory(mobBusinessDto.getSubCategory());
				merchantbusinessDto.setWebsite(mobBusinessDto.getWebsite());
				merchantbusinessDto.setVelocityLimits(mobBusinessDto.getVelocityLimits());
				merchantbusinessDto.setTin(mobBusinessDto.getTIN());

			}
			return merchantbusinessDto;
		} else if (userMappingDirection == UserMappingDirection.MERCHANT_TO_MOB) {

			BusinessInformationDTO mobBusinessDto = null;
			MerchantBusinessInfo merchantbusinessDto = (MerchantBusinessInfo) object;
			if (merchantbusinessDto != null) {
				mobBusinessDto = new BusinessInformationDTO();
				mobBusinessDto.setAddress1(merchantbusinessDto.getAddress1());
				mobBusinessDto.setAddress2(merchantbusinessDto.getAddress2());
				mobBusinessDto.setAppName(merchantbusinessDto.getAppName());
				mobBusinessDto.setBusinessCategory(merchantbusinessDto.getBusinessCategory());
				mobBusinessDto.setBusinessName(merchantbusinessDto.getBusinessName());
				mobBusinessDto.setBusinessType(merchantbusinessDto.getBusinessType());
				mobBusinessDto.setCity(merchantbusinessDto.getCity());
				mobBusinessDto.setDateOfFormation(merchantbusinessDto.getDateOfFormation());
				mobBusinessDto.setLandLineNumber(merchantbusinessDto.getLandLineNumber());
				mobBusinessDto.setLogoUrl(merchantbusinessDto.getLogoUrl());
				mobBusinessDto.setMerchantName(merchantbusinessDto.getMerchantName());
				mobBusinessDto.setMerchantReserves(merchantbusinessDto.getMerchantReserves());
				mobBusinessDto.setPincode(merchantbusinessDto.getPincode());
				mobBusinessDto.setPrimaryMobile(merchantbusinessDto.getPrimaryMobile());
				mobBusinessDto.setSecondaryEmail(merchantbusinessDto.getSecondaryEmail());
				mobBusinessDto.setSecondaryMobile(merchantbusinessDto.getSecondaryMobile());
				mobBusinessDto.setShopName(merchantbusinessDto.getShopName());
				mobBusinessDto.setState(merchantbusinessDto.getState());
				mobBusinessDto.setStdCode(merchantbusinessDto.getStdCode());
				mobBusinessDto.setSubCategory(merchantbusinessDto.getSubCategory());
				mobBusinessDto.setWebsite(merchantbusinessDto.getWebsite());
				mobBusinessDto.setVelocityLimits(merchantbusinessDto.getVelocityLimits());
				mobBusinessDto.setTIN(merchantbusinessDto.getTin());
				TDRDetailsDTO mobTDRDetails =null;
				MerchantTDRDetailsDTO merchantTRDDetails = merchantbusinessDto.getTdrDetailsDTO();
				if(merchantTRDDetails!= null)
				{
					mobTDRDetails = new TDRDetailsDTO();
					mobTDRDetails.setFixedFeeValue(merchantTRDDetails.getFixedFeeValue());
					mobTDRDetails.setMerchantFeeValue(merchantTRDDetails.getMerchantFeeValue());
					mobTDRDetails.setValidFrom(merchantTRDDetails.getValidFrom());
				}
				else
				{
					mobTDRDetails = new TDRDetailsDTO();
					mobTDRDetails.setFixedFeeValue(config.getFixedFee());
					mobTDRDetails.setMerchantFeeValue(config.getMerchantFee());
					Date date = new Date();
				    SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
				    String strDate = sdf.format(date);
					mobTDRDetails.setValidFrom(strDate);
				}
				mobBusinessDto.setTdrDetailsDTO(mobTDRDetails);
			}

			return mobBusinessDto;
		} else
			return null;

	}

	@SuppressWarnings("unchecked")
	public  Object merchantAndMobUserMapping(Object object, UserMappingDirection userMappingDirection) {
		if (userMappingDirection == UserMappingDirection.MOB_TO_MERCHANT) {
			UserDetailsDTO userDetailDTO = (UserDetailsDTO) object;
			MerchantUserDTO merchantUserDTO = new MerchantUserDTO();
			merchantUserDTO.setEmailId(userDetailDTO.getEmailId());
			merchantUserDTO.setLoginName(userDetailDTO.getLoginName());
			merchantUserDTO.setUserId(userDetailDTO.getUserId());
			merchantUserDTO.setUserName(userDetailDTO.getName());

			// creating role for merchant
			List<MerchantRoleDTO> merchantroleList = null;

			merchantroleList = (List<MerchantRoleDTO>) merchantAndMobRoleMapping(userDetailDTO, userMappingDirection);

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

	public  Object merchantAndMobRoleMapping(Object object, UserMappingDirection userMappingDirection) {

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

	public  MerchantKey getMerchantKeyfromMOBKey(Key mobKey) throws MerchantException {

		MerchantKey merchantKey = new MerchantKey();

		merchantKey.setActive(mobKey.isActive());
		merchantKey.setClientId(mobKey.getClientId());
		merchantKey.setDomainId(mobKey.getDomainId());
		merchantKey.setExpirationTime(mobKey.getExpirationTime());
		merchantKey.setId(mobKey.getId());
		merchantKey.setKey(mobKey.getKey());
		MerchantEnvType merchantEnvType = null;
		try {
			merchantEnvType = MerchantEnvType.valueOf(mobKey.getEnvType().toString());

		} catch (Exception e) {
			log.error("Exception when converting MOB EnvType Enum {}  to MPanel Enum : {}",
					mobKey.getEnvType().toString(), e);
			throw e;
		}

		merchantKey.setEnvType(merchantEnvType);
		return merchantKey;

	}
	
	public MerchantUploadedDocsDTO getMerchantUploadedDocFromMOB(DocumentDTO mobDocumentDTO)
	{
		MerchantUploadedDocsDTO merchantUploadedDocDTO = new MerchantUploadedDocsDTO();
		merchantUploadedDocDTO.setApprovalStatus(mobDocumentDTO.isApprovalStatus());
		merchantUploadedDocDTO.setContentType(mobDocumentDTO.getContentType());
		merchantUploadedDocDTO.setDocIdentityValue(mobDocumentDTO.getDocIdentityValue());
		merchantUploadedDocDTO.setDocumentCategory(mobDocumentDTO.getDocumentCategory());
		merchantUploadedDocDTO.setDocumentSize(mobDocumentDTO.getDocumentSize());
		merchantUploadedDocDTO.setDocumentType(mobDocumentDTO.getDocumentType());
		merchantUploadedDocDTO.setId(mobDocumentDTO.getId());
		merchantUploadedDocDTO.setName(mobDocumentDTO.getName());
		merchantUploadedDocDTO.setDocStatus(mobDocumentDTO.getDocStatus());
		return merchantUploadedDocDTO;
		
	}

	public void setConfig(MpanelConfig config) {
		this.config = config;
	}
	
}
