package com.snapdeal.merchant.rest.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantBankInfo;
import com.snapdeal.merchant.dto.MerchantBusinessInfo;
import com.snapdeal.merchant.dto.MerchantContactInfo;
import com.snapdeal.merchant.dto.MerchantDocCategoryAndTypeDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.dto.MerchantUIData;
import com.snapdeal.merchant.dto.MerchantUploadedDocsDTO;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.CreateMPMerchantRequest;
import com.snapdeal.merchant.request.CreateOfflineMerchantRequest;
import com.snapdeal.merchant.request.GetMerchantStateRequest;
import com.snapdeal.merchant.request.GetMerchantUIDataRequest;
import com.snapdeal.merchant.request.MerchantGetKYCDocListRequest;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantRoleRequest;
import com.snapdeal.merchant.request.MerchantUpdateDetailsRequest;
import com.snapdeal.merchant.request.MerchantUploadDocumentRequest;
import com.snapdeal.merchant.response.CreateMPMerchantResponse;
import com.snapdeal.merchant.response.CreateOfflineMerchantResponse;
import com.snapdeal.merchant.response.GetMerchantStateResponse;
import com.snapdeal.merchant.response.GetMerchantUIDataResponse;
import com.snapdeal.merchant.response.MerchantDetailResponse;
import com.snapdeal.merchant.response.MerchantGetKYCDocListResponse;
import com.snapdeal.merchant.response.MerchantRoleResponse;
import com.snapdeal.merchant.response.MerchantUpdateDetailsResponse;
import com.snapdeal.merchant.response.MerchantUploadDocumentResponse;
import com.snapdeal.merchant.rest.http.util.IMSUtil;
import com.snapdeal.merchant.rest.http.util.MOBUtil;
import com.snapdeal.merchant.rest.http.util.RMSUtil;
import com.snapdeal.merchant.rest.service.IMerchantManagementService;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.merchant.util.MOBMapperUtil;
import com.snapdeal.merchant.util.RMSMapperUtil;
import com.snapdeal.mob.dto.DocCategoryAndTypeDTO;
import com.snapdeal.mob.dto.DocumentDTO;
import com.snapdeal.mob.enums.IntegrationMode;
import com.snapdeal.mob.response.GetKYCDocCategoriesByBusinessTypeResponse;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.response.GetUIDataResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.response.UpdateMerchantDetailsResponse;
import com.snapdeal.mob.response.UploadDocumentsResponse;
import com.snapdeal.mob.ui.response.UIData;
import com.snapdeal.payments.aggregator.exception.client.HttpTransportException;
import com.snapdeal.payments.aggregator.exception.client.ServiceException;
import com.snapdeal.payments.metrics.annotations.Logged;
import com.snapdeal.payments.roleManagementModel.response.GetRolesByRoleNamesResponse;

import lombok.extern.slf4j.Slf4j;

@Service
@Slf4j
public class MerchantManagementServiceImpl implements IMerchantManagementService {

	@Autowired
	private MOBUtil mobUtil;

	@Autowired
	private RMSUtil rmsUtil;

	@Autowired
	private IMSUtil imsUtil;

	@Autowired
	private MOBMapperUtil mobMapperUtil;

	@Autowired
	private MpanelConfig config;

	@Logged
	@Override
	public MerchantDetailResponse getMerchantDetails(MerchantProfileRequest request) throws MerchantException {

		GetMerchantDetails mobMerchantDetailsResponse = mobUtil.getMerchantDetails(request);

		MerchantDetailResponse merchantDetailResponse = new MerchantDetailResponse();

		MerchantBusinessInfo merchantBusinessInfo = (MerchantBusinessInfo) mobMapperUtil
				.merchantAndMobBusinessDTOMapping(mobMerchantDetailsResponse.getBusinessInformationDTO(),
						UserMappingDirection.MOB_TO_MERCHANT);
		if (merchantBusinessInfo != null)
			merchantDetailResponse.setBusinessInfo(merchantBusinessInfo);

		MerchantBankInfo merchantBankDetailsDTO = (MerchantBankInfo) mobMapperUtil.merchantAndMobBankDTOMapping(
				mobMerchantDetailsResponse.getBankAccountDetailsDTO(), UserMappingDirection.MOB_TO_MERCHANT);
		if (merchantBankDetailsDTO != null)
			merchantDetailResponse.setBankInfo(merchantBankDetailsDTO);

		List<DocumentDTO> mobDocumentDTOList = mobMerchantDetailsResponse.getDocumentDTO();
		List<MerchantUploadedDocsDTO> merchantUploadedDocDTOList = new ArrayList<MerchantUploadedDocsDTO>();
		for (DocumentDTO mobDocumentDTO : mobDocumentDTOList) {
			MerchantUploadedDocsDTO merchantUploadedDocDTO = mobMapperUtil
					.getMerchantUploadedDocFromMOB(mobDocumentDTO);
			merchantUploadedDocDTOList.add(merchantUploadedDocDTO);
		}
		merchantDetailResponse.setUploadedDocDTO(merchantUploadedDocDTOList);

		return merchantDetailResponse;
	}

	private MerchantDetailResponse getMerchantDetailFromMOB(GetMerchantDetails mobMerchantDetails) {
		MerchantDetailResponse merchantDetailResponse = new MerchantDetailResponse();

		if (mobMerchantDetails.getBusinessInformationDTO() != null) {

			MerchantBusinessInfo MerchantBusinessInfo = (MerchantBusinessInfo) mobMapperUtil
					.merchantAndMobBusinessDTOMapping(mobMerchantDetails.getBusinessInformationDTO(),
							UserMappingDirection.MOB_TO_MERCHANT);
			if (MerchantBusinessInfo != null)
				merchantDetailResponse.setBusinessInfo(MerchantBusinessInfo);
		}

		if (mobMerchantDetails.getBankAccountDetailsDTO() != null) {
			MerchantBankInfo bankInfo = getMerchantBankInfoFromMOB(mobMerchantDetails);
			merchantDetailResponse.setBankInfo(bankInfo);
		}

		MerchantContactInfo contactInfo = getMerchantContactInfoFromMOB(mobMerchantDetails);
		// merchantDetailResponse.setContactInfo(contactInfo);

		return merchantDetailResponse;
	}

	private MerchantContactInfo getMerchantContactInfoFromMOB(GetMerchantDetails mobMerchantDetails) {
		MerchantContactInfo contactInfo = new MerchantContactInfo();

		if (mobMerchantDetails.getBusinessInformationDTO().getAddress1() != null
				|| mobMerchantDetails.getBusinessInformationDTO().getAddress1() != null) {
			String merchantAddress = mobMerchantDetails.getBusinessInformationDTO().getAddress1() + " "
					+ mobMerchantDetails.getBusinessInformationDTO().getAddress2();

			contactInfo.setAddesss(merchantAddress);
		}

		if (mobMerchantDetails.getBusinessInformationDTO().getLandLineNumber() != null)
			contactInfo.setLandLine(mobMerchantDetails.getBusinessInformationDTO().getLandLineNumber());

		if (mobMerchantDetails.getBusinessInformationDTO().getPrimaryMobile() != null)
			contactInfo.setMobile(mobMerchantDetails.getBusinessInformationDTO().getPrimaryMobile());

		if (mobMerchantDetails.getEmail() != null)
			contactInfo.setPrimaryEmail(mobMerchantDetails.getEmail());

		if (mobMerchantDetails.getBusinessInformationDTO().getSecondaryEmail() != null)
			contactInfo.setSecondaryEmail(mobMerchantDetails.getBusinessInformationDTO().getSecondaryEmail());

		return contactInfo;

	}

	private MerchantBankInfo getMerchantBankInfoFromMOB(GetMerchantDetails mobMerchantDetails) {
		MerchantBankInfo bankInfo = new MerchantBankInfo();
		bankInfo.setBankAccount(mobMerchantDetails.getBankAccountDetailsDTO().getAccountNumber());
		bankInfo.setBankName(mobMerchantDetails.getBankAccountDetailsDTO().getBankName());
		bankInfo.setIfscCode(mobMerchantDetails.getBankAccountDetailsDTO().getIfsccode());
		return bankInfo;
	}

	private MerchantBusinessInfo getMerchantBusinessInfoFromMOB(GetMerchantDetails mobMerchantDetails) {
		MerchantBusinessInfo businessInfo = new MerchantBusinessInfo();
		businessInfo.setBusinessCategory(mobMerchantDetails.getBusinessInformationDTO().getBusinessCategory());
		businessInfo.setBusinessType(mobMerchantDetails.getBusinessInformationDTO().getBusinessType());
		businessInfo.setSubCategory(mobMerchantDetails.getBusinessInformationDTO().getSubCategory());
		businessInfo.setTin(mobMerchantDetails.getBusinessInformationDTO().getTIN());
		return businessInfo;
	}

	@Logged
	@Override
	public MerchantRoleResponse getMerchantRoles(MerchantRoleRequest request) throws MerchantException {

		request.setRoles(config.getRoles());

		List<MerchantRoleDTO> roles = new ArrayList<MerchantRoleDTO>();
		GetRolesByRoleNamesResponse rmsResponse = rmsUtil.getRoles(request);

		GetUserMerchantResponse mobResponse = null;

		MerchantGetUserMerchantRequest merchantRequest = new MerchantGetUserMerchantRequest();
		merchantRequest.setToken(request.getToken());
		merchantRequest.setMerchantId(request.getMerchantId());
		merchantRequest.setUserId(request.getLoggedUserId());

		mobResponse = mobUtil.getMerchantForUser(merchantRequest);
		String intMode = mobResponse.getMerchantDetails().getIntegrationMode();

		if (!(IntegrationMode.ONLINE.getIntegrationMode().equalsIgnoreCase(intMode))) {
			for (int i = 0; i < rmsResponse.getRoles().size(); i++) {

				if (AppConstants.payments.equalsIgnoreCase(rmsResponse.getRoles().get(i).getName())) {
					for (i = 0; i < rmsResponse.getRoles().get(i).getPermissions().size(); i++) {
						if (AppConstants.initiateRefundPermission
								.equalsIgnoreCase(rmsResponse.getRoles().get(i).getPermissions().get(i).getName()))
							rmsResponse.getRoles().get(i).getPermissions().remove(i);
					}

				}
				if (AppConstants.integration.equalsIgnoreCase(rmsResponse.getRoles().get(i).getName())) {
					rmsResponse.getRoles().remove(i);
				}
			}
		}

		for (int i = 0; i < rmsResponse.getRoles().size(); i++) {
			MerchantRoleDTO merchantRole = (MerchantRoleDTO) RMSMapperUtil.merchantAndRMSRoleMapping(
					rmsResponse.getRoles().get(i), UserMappingDirection.RMS_TO_MERCHANT, false);
			roles.add(merchantRole);
		}

		MerchantRoleResponse response = new MerchantRoleResponse();
		response.setRoles(roles);

		return response;
	}

	@Logged
	@Override
	public GetMerchantUIDataResponse getUIData(GetMerchantUIDataRequest request) throws MerchantException {

		GetUIDataResponse mobResponse = mobUtil.getUIData(request);
		GetMerchantUIDataResponse response = new GetMerchantUIDataResponse();
		List<UIData> uiDataList = mobResponse.getUiData();
		List<MerchantUIData> merchantUIDataList = new ArrayList<MerchantUIData>();
		if (!uiDataList.isEmpty() || uiDataList != null) {
			for (UIData mobData : uiDataList) {
				MerchantUIData data = new MerchantUIData();
				data.setDisplayValue(mobData.getDisplayValue());
				data.setParent(mobData.getParent());
				data.setType(mobData.getType());
				data.setValue(mobData.getValue());
				merchantUIDataList.add(data);
			}

		}
		response.setMerchantUIData(merchantUIDataList);
		return response;
	}

	@Override
	@Logged
	public MerchantUpdateDetailsResponse updateMerchantDetails(MerchantUpdateDetailsRequest request)
			throws MerchantException {
		MerchantUpdateDetailsResponse response = new MerchantUpdateDetailsResponse();

		
		/*MerchantProfileRequest merchantProfileRequest =  new MerchantProfileRequest();
		merchantProfileRequest.setMerchantId(request.getMerchantId());
		merchantProfileRequest.setToken(request.getToken());
		GetMerchantDetails merchantProfileResponse = null;*/
		
		MerchantGetUserMerchantRequest getMerchantForUserRequest = new MerchantGetUserMerchantRequest();
		getMerchantForUserRequest.setUserId(request.getLoggedUserId());
		getMerchantForUserRequest.setToken(request.getToken());
		GetUserMerchantResponse getUserMerchantResponse = null ;
		
		getUserMerchantResponse = mobUtil.getMerchantForUser(getMerchantForUserRequest);
		
		
		String merchantName =  getUserMerchantResponse.getMerchantDetails().getMerchantName();
				
		UpdateMerchantDetailsResponse mobResponse = mobUtil.updateMerchantDetails(request);
		
		if(merchantName == null)
		{
			imsUtil.sendCreateMerchantEmail(request ,getUserMerchantResponse.getMerchantDetails().getEmail() ,getUserMerchantResponse.getMerchantDetails().getIntegrationMode());
		}
		response.setSuccess(true);
		return response;
	}

	@Override
	@Logged
	public CreateMPMerchantResponse createMerchant(CreateMPMerchantRequest request) throws MerchantException {

		CreateMPMerchantResponse response = new CreateMPMerchantResponse();

		response = mobUtil.createMerchant(request);

		return response;
	}

	@Override
	@Logged
	public GetMerchantStateResponse getMerchantProfileStatus(GetMerchantStateRequest request) throws MerchantException {
		GetMerchantStateResponse response = null;

		response = mobUtil.getMerchantProfileStatus(request);

		return response;
	}

	@Override
	@Logged
	public CreateOfflineMerchantResponse createOfflineMerchant(CreateOfflineMerchantRequest request)
			throws MerchantException {

		CreateOfflineMerchantResponse response = null;

		/*response = imsUtil.sendCreateOfflineMerchantEmail(request);*/

		return response;
	}

	@Override
	@Logged
	public MerchantUploadDocumentResponse uploadDocument(MerchantUploadDocumentRequest request, MultipartFile file)
			throws MerchantException {
		UploadDocumentsResponse mobResponse = null;
		MerchantUploadDocumentResponse response = new MerchantUploadDocumentResponse();

		mobResponse = mobUtil.uploadDocument(request, file);

		response.setSuccess(mobResponse.isSuccess());
		return response;
	}

	@Override
	@Logged
	public MerchantGetKYCDocListResponse GetKYCDocList(MerchantGetKYCDocListRequest request) throws MerchantException {

		MerchantGetKYCDocListResponse response = new MerchantGetKYCDocListResponse();
		GetKYCDocCategoriesByBusinessTypeResponse mobResponse = null;

		mobResponse = mobUtil.GetKYCDocList(request);

		List<MerchantDocCategoryAndTypeDTO> kycDocList = new ArrayList<MerchantDocCategoryAndTypeDTO>();
		if (mobResponse != null) {
			List<DocCategoryAndTypeDTO> docCategoryAndTypeList = mobResponse.getDocCategoryAndTypeList();
			for (DocCategoryAndTypeDTO mobKycDocDto : docCategoryAndTypeList) {
				MerchantDocCategoryAndTypeDTO kycDocDto = new MerchantDocCategoryAndTypeDTO();
				kycDocDto.setDocCategory(mobKycDocDto.getDocCategory());
				kycDocDto.setDocType(mobKycDocDto.getDocType());
				kycDocList.add(kycDocDto);
			}
		}

		response.setMerchantKycDocList(kycDocList);
		return response;
	}

	public void setMobMapperUtil(MOBMapperUtil mobMapperUtil) {
		this.mobMapperUtil = mobMapperUtil;
	}

}
