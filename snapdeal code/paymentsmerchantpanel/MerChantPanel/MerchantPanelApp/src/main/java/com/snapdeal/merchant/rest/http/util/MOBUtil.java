package com.snapdeal.merchant.rest.http.util;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.merchant.config.MpanelConfig;
import com.snapdeal.merchant.dto.MerchantFlowDTO;
import com.snapdeal.merchant.dto.MerchantPermissionDTO;
import com.snapdeal.merchant.dto.MerchantRoleDTO;
import com.snapdeal.merchant.enums.MerchantFlowStateEnum;
import com.snapdeal.merchant.enums.UserMappingDirection;
import com.snapdeal.merchant.errorcodes.ErrorConstants;
import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.CreateMPMerchantRequest;
import com.snapdeal.merchant.request.GetMerchantStateRequest;
import com.snapdeal.merchant.request.GetMerchantUIDataRequest;
import com.snapdeal.merchant.request.MerchantAddUserRequest;
import com.snapdeal.merchant.request.MerchantAllUsersRequest;
import com.snapdeal.merchant.request.MerchantEditUserRequest;
import com.snapdeal.merchant.request.MerchantGetKYCDocListRequest;
import com.snapdeal.merchant.request.MerchantGetUserMerchantRequest;
import com.snapdeal.merchant.request.MerchantKeyRequest;
import com.snapdeal.merchant.request.MerchantProfileRequest;
import com.snapdeal.merchant.request.MerchantUpdateDetailsRequest;
import com.snapdeal.merchant.request.MerchantUploadDocumentRequest;
import com.snapdeal.merchant.request.UpdateSourceOfAcquisitionRequest;
import com.snapdeal.merchant.response.CreateMPMerchantResponse;
import com.snapdeal.merchant.response.GetMerchantStateResponse;
import com.snapdeal.merchant.response.UpdateSourceOfAcquisitionResponse;
import com.snapdeal.merchant.util.AppConstants;
import com.snapdeal.merchant.util.MOBMapperUtil;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.IUserService;
import com.snapdeal.mob.dto.BankAccountDetailsDTO;
import com.snapdeal.mob.dto.BusinessInformationDTO;
import com.snapdeal.mob.dto.DocumentDTO;
import com.snapdeal.mob.dto.OnBoardingStepDTO;
import com.snapdeal.mob.dto.Permission;
import com.snapdeal.mob.dto.SourceOfAcquisitionDTO;
import com.snapdeal.mob.dto.UserDetailsDTO;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.MOBExceptionResponse;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.AddUserRequest;
import com.snapdeal.mob.request.CreateMerchantRequest;
import com.snapdeal.mob.request.GetKYCCategoriesByBusinessTypeRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByMerchantIdRequest;
import com.snapdeal.mob.request.GetMerchantKeysRequest;
import com.snapdeal.mob.request.GetMerchantProfileStatusRequest;
import com.snapdeal.mob.request.GetUIDataRequest;
import com.snapdeal.mob.request.GetUserMerchantRequest;
import com.snapdeal.mob.request.GetUsersForMerchantRequest;
import com.snapdeal.mob.request.UpdateMerchantDetailsRequest;
import com.snapdeal.mob.request.UpdateUserRequest;
import com.snapdeal.mob.request.UpdatesourceOfAcquisitionRequest;
import com.snapdeal.mob.request.UploadDocumentsRequest;
import com.snapdeal.mob.response.AddUserResponse;
import com.snapdeal.mob.response.CreateMerchantResponse;
import com.snapdeal.mob.response.GetKYCDocCategoriesByBusinessTypeResponse;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.response.GetMerchantKeysResponse;
import com.snapdeal.mob.response.GetMerchantProfileStatusResponse;
import com.snapdeal.mob.response.GetUIDataResponse;
import com.snapdeal.mob.response.GetUserMerchantResponse;
import com.snapdeal.mob.response.GetUsersforMerchantResponse;
import com.snapdeal.mob.response.UpdateMerchantDetailsResponse;
import com.snapdeal.mob.response.UpdateUserResponse;
import com.snapdeal.mob.response.UpdatesourceOfAcquisitionResponse;
import com.snapdeal.mob.response.UploadDocumentsResponse;
import com.snapdeal.payments.roleManagementModel.exceptions.RoleMgmtException;
import com.snapdeal.payments.roleManagementModel.request.LoginUserRequest;
import com.snapdeal.payments.roleManagementModel.response.LoginUserResponse;
import com.snapdeal.payments.roleManagementModel.services.RoleMgmtService;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Service
public class MOBUtil {

	@Autowired
	private IUserService userService;

	@Autowired
	private IMerchantServices merchantService;

	@Autowired
	private RoleMgmtService roleMgmtService;

	@Autowired
	private MpanelConfig config;

	@Autowired
	private MOBMapperUtil mobMapperUtil;

	public GetUserMerchantResponse getMerchantForUser(MerchantGetUserMerchantRequest request) throws MerchantException {

		GetUserMerchantRequest getUserMerchantRequest = new GetUserMerchantRequest();
		getUserMerchantRequest.setUserId(request.getUserId());
		getUserMerchantRequest.setToken(request.getToken());

		GetUserMerchantResponse getUserMerchantResponse = null;
		try {
			
			log.info("MOB request For getMerchantForUser: {}",getUserMerchantRequest);
			getUserMerchantResponse = userService.getUserMerchant(getUserMerchantRequest);
			log.info("MOB response For getMerchantForUser: {}",getUserMerchantResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB while Getting Merchant For User: {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		return getUserMerchantResponse;

	}

	public GetMerchantDetails getMerchantDetails(MerchantProfileRequest request) throws MerchantException {

		GetMerchantDetailsByMerchantIdRequest getMerchantDetailsRequest = new GetMerchantDetailsByMerchantIdRequest();
		getMerchantDetailsRequest.setMerchantId(request.getMerchantId());
		getMerchantDetailsRequest.setToken(request.getToken());

		GetMerchantDetails merchantDetails = null;
		try {
			
			log.info("MOB request for getMerchantDetails : {}", getMerchantDetailsRequest);
			merchantDetails = merchantService.getMerchantDetailsByMerchantId(getMerchantDetailsRequest);
			log.info("MOB response for getMerchantDetails : {}", merchantDetails);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB Client while profile view: {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}

		return merchantDetails;

	}

	public GetUsersforMerchantResponse getAllUsersOfMerchant(MerchantAllUsersRequest request) throws MerchantException {

		GetUsersForMerchantRequest mobRequest = new GetUsersForMerchantRequest();
		GetUsersforMerchantResponse mobResponse = null;
		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setToken(request.getToken());
		mobRequest.setAppName(AppConstants.rmsAppName);
		try {
			
			log.info("MOB Request For getAllUsersOfMerchant: {}",mobRequest);
			mobResponse = userService.getUsersForMerchant(mobRequest);
			log.info("MOB Response For getAllUsersOfMerchant: {}",mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB while getting UsersForMerchant : {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}

		return mobResponse;
	}

	public GetUIDataResponse getUIData(GetMerchantUIDataRequest request) throws MerchantException {

		GetUIDataRequest mobRequest = new GetUIDataRequest();
		mobRequest.setIntegrationMode(request.getIntegrationMode());
		mobRequest.setParent(request.getParent());
		mobRequest.setType(request.getType());
		GetUIDataResponse response = null;
		try {
			
			log.info("MOB request For getUIData: {}",mobRequest);
			response = merchantService.getAllUIData(mobRequest);
			log.info("MOB Response For getUIData: {}",response);

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB  while Getting UIData: {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		return response;
	}

	
	public AddUserResponse addUser(MerchantAddUserRequest request) throws MerchantException {
		AddUserResponse mobResponse = null;

		AddUserRequest mobRequest = new AddUserRequest();
		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setPassword(request.getPassword());
		mobRequest.setToken(request.getToken());
		mobRequest.setAppName(AppConstants.rmsAppName);

		UserDetailsDTO mobUser = new UserDetailsDTO();
		mobUser.setEmailId(request.getEmail());
		mobUser.setLoginName(request.getLoginName());
		mobUser.setName(request.getUserName());

		List<MerchantRoleDTO> merchantRoleList = request.getRoleList();

		List<Permission> mobPermissionList = new ArrayList<Permission>();

		if (merchantRoleList != null) {
			for (MerchantRoleDTO merchantRoleDTO : merchantRoleList) {
				if (merchantRoleDTO != null) {
					List<MerchantPermissionDTO> merchantPermissionList = merchantRoleDTO.getPermissions();
					for (MerchantPermissionDTO merchantPermissionDTO : merchantPermissionList) {
						Permission mobPermission = new Permission();
						mobPermission.setDisplayName(merchantPermissionDTO.getDisplayName());
						mobPermission.setId(merchantPermissionDTO.getId());
						mobPermission.setName(merchantPermissionDTO.getName());

						mobPermissionList.add(mobPermission);
					}
				}
			}
		}

		mobUser.setPermissionList(mobPermissionList);

		mobRequest.setUserDetails(mobUser);

		try {
			
			log.info("MOB Request For addUser: {}",mobRequest);
			mobResponse = userService.addUser(mobRequest);
			log.info("MOB Response For addUser: {}",mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB while Adding User : {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		return mobResponse;
	}

	public UpdateUserResponse editUser(MerchantEditUserRequest request) throws MerchantException {

		UpdateUserRequest mobRequest = new UpdateUserRequest();
		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setName(request.getUserName());
		mobRequest.setUserId(request.getUserId());
		mobRequest.setToken(request.getToken());
		mobRequest.setAppName(AppConstants.rmsAppName);

		List<Permission> mobPermissionList = new ArrayList<Permission>();
		List<MerchantRoleDTO> merchantRoleDTOList = request.getRoleList();

		if (merchantRoleDTOList != null) {
			for (MerchantRoleDTO merchantRoleDTO : merchantRoleDTOList) {

				List<MerchantPermissionDTO> merchantPermissionDTOList = merchantRoleDTO.getPermissions();

				for (MerchantPermissionDTO merchantPermissionDTO : merchantPermissionDTOList) {

					Permission mobPermission = new Permission();
					mobPermission.setDisplayName(merchantPermissionDTO.getDisplayName());
					mobPermission.setId(merchantPermissionDTO.getId());
					mobPermission.setName(merchantPermissionDTO.getName());

					mobPermissionList.add(mobPermission);
				}
			}
		}
		mobRequest.setPermissionList(mobPermissionList);

		UpdateUserResponse mobResponse = null;
		try {
			
			log.info("MOB request For editUser: {}",mobRequest);
			mobResponse = userService.updateUser(mobRequest);
			log.info("MOB Response For editUser : {}",mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB while updating user : {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		return mobResponse;

	}

	public GetMerchantKeysResponse getMerchantSandBoxKey(MerchantKeyRequest request) throws MerchantException {

		GetMerchantKeysRequest mobRequest = new GetMerchantKeysRequest();

		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setToken(request.getToken());

		GetMerchantKeysResponse mobResponse;

		try {
			
			log.info("MOB Request for getMerchantSandBoxKey: {}",mobRequest);
			mobResponse = merchantService.getMerchantKeysSandbox(mobRequest);
			log.info("MOB Response for getMerchantSandBoxKey: {}",mobResponse);

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception From MOB While Getting Sandbox  Keys: {} ", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());

		}

		return mobResponse;
	}

	public GetMerchantKeysResponse getMerchantProdKey(MerchantKeyRequest request) throws MerchantException {

		GetMerchantKeysRequest mobRequest = new GetMerchantKeysRequest();

		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setToken(request.getToken());

		GetMerchantKeysResponse mobResponse;

		try {

			log.info("MOB Request For getMerchantProdKey: {}",mobRequest);
			mobResponse = merchantService.getMerchantKeysProduction(mobRequest);
			log.info("MOB Response For getMerchantProdKey: {}",mobResponse);

		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception From MOB While Getting Production  Keys: {} ", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());

		}

		return mobResponse;
	}

	public void setUserService(IUserService userService) {
		this.userService = userService;
	}

	public void setMerchantService(IMerchantServices merchantService) {
		this.merchantService = merchantService;
	}

	public UpdateMerchantDetailsResponse updateMerchantDetails(MerchantUpdateDetailsRequest request)
			throws MerchantException {

		UpdateMerchantDetailsRequest mobRequest = new UpdateMerchantDetailsRequest();
		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setToken(request.getToken());
		BusinessInformationDTO businessInformationDTO = (BusinessInformationDTO) mobMapperUtil
				.merchantAndMobBusinessDTOMapping(request.getBusinessInformationDTO(),
						UserMappingDirection.MERCHANT_TO_MOB);
		mobRequest.setBusinessInformationDTO(businessInformationDTO);
		BankAccountDetailsDTO bankAccountDetailsDTO = (BankAccountDetailsDTO) mobMapperUtil
				.merchantAndMobBankDTOMapping(request.getBankAccountDetailsDTO(), UserMappingDirection.MERCHANT_TO_MOB);
		mobRequest.setBankAccountDetailsDTO(bankAccountDetailsDTO);
		UpdateMerchantDetailsResponse mobResponse = null;
		try {
			
			log.info("MOB Request for updateMerchantDetails: {}",mobRequest);
			mobResponse = merchantService.updateMerchantDetails(mobRequest);
			log.info("MOB response For updateMerchantDetails: {}",mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB while Updating Merchant :{} {} ", request.getMerchantId(), e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		return mobResponse;

	}

	public CreateMPMerchantResponse createMerchant(CreateMPMerchantRequest request)
			throws MerchantException {

		CreateMPMerchantResponse response = new CreateMPMerchantResponse();

		CreateMerchantRequest mobRequest = new CreateMerchantRequest();
		mobRequest.setEmail(request.getEmail());
		mobRequest.setIntegrationMode(request.getIntegrationMode());
		mobRequest.setIntegrationModeSubtype(request.getIntegrationModeSubtype());
		mobRequest.setPassword(request.getPassword());

		LoginUserRequest rmsRequest = new LoginUserRequest();
		rmsRequest.setUserName(config.getAdminLoginName());
		rmsRequest.setPassword(config.getAdminPassword());
		LoginUserResponse rmsResponse = null;
		try {

			log.info("RMS LoginRequest For createMerchant: {}", rmsRequest.getUserName());
			rmsResponse = roleMgmtService.loginUser(rmsRequest);
			log.info("RMS LoginResponse For createMerchant: {}", rmsResponse.getUser());
			
		} catch (com.snapdeal.payments.roleManagementClient.exceptions.HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from RMS: {}", httpException);
			throw new MerchantException(ErrorConstants.SUPER_ADMIN_LOGIN_ERROR_CODE,
					ErrorConstants.SUPER_ADMIN_LOGIN_ERROR_MSG);
		} catch (RoleMgmtException e) {
			log.error("Exception from rms while login using admin user : {}", e.getErrorCode(),e);
			throw new MerchantException(ErrorConstants.SUPER_ADMIN_LOGIN_ERROR_CODE,
					ErrorConstants.SUPER_ADMIN_LOGIN_ERROR_MSG);
		}

		mobRequest.setToken(rmsResponse.getToken().getToken());

		CreateMerchantResponse mobResponse = new CreateMerchantResponse();
		try {
			
			log.info("MOB Request For createMerchant: {}",mobRequest);
			mobResponse = merchantService.createMerchant(mobRequest);
			log.info("MOB Response For createMerchant: {}",mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("HTTPTransport Exception from MOB while Creating Merchant: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exceptioin form MOB while Creating Merchant :{} {}", mobRequest, e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		
		log.info("Merchant Created Successfuly: {}",mobResponse.getMerchantId());
		response.setMerchantId(mobResponse.getMerchantId());
		return response;

	}

	public GetMerchantStateResponse getMerchantProfileStatus(GetMerchantStateRequest request) throws MerchantException {
		GetMerchantProfileStatusRequest mobrequest = new GetMerchantProfileStatusRequest();
		mobrequest.setMerchantId(request.getMerchantId());
		mobrequest.setToken(request.getToken());
		GetMerchantStateResponse response = new GetMerchantStateResponse();
		GetMerchantProfileStatusResponse mobResponse = new GetMerchantProfileStatusResponse();

		try {
			
			log.info("MOB Request For getMerchantProfileStatus: {}", mobrequest);
			mobResponse = merchantService.getMerchantProfileStatus(mobrequest);
			log.info("MOB Response For getMerchantProfileStatus: {}", mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.info("exception from MOB while getMerchantProfileStatus: {} ", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}
		List<MerchantFlowDTO> merchantFlowDtoList = new ArrayList<MerchantFlowDTO>();

		List<OnBoardingStepDTO> steps = mobResponse.getSteps();

		if (steps != null && !steps.isEmpty()) {
			for (OnBoardingStepDTO onBoardingStepDTO : steps) {
				MerchantFlowDTO merchantFlowDTO = new MerchantFlowDTO();
				String state = MerchantFlowStateEnum.enumOf(onBoardingStepDTO.getDescription());
				if (state != null) {
					merchantFlowDTO.setState(state);
					merchantFlowDTO.setCompleted(onBoardingStepDTO.isCompleted());
					merchantFlowDtoList.add(merchantFlowDTO);
				}
			}
		}
		response.setMerchantFlowDTO(merchantFlowDtoList);
		return response;
	}

	public UploadDocumentsResponse uploadDocument(MerchantUploadDocumentRequest request, MultipartFile file)
			throws MerchantException {

		UploadDocumentsRequest mobRequest = new UploadDocumentsRequest();
		UploadDocumentsResponse mobResonse = new UploadDocumentsResponse();
		mobRequest.setMerchantId(request.getMerchantId());
		mobRequest.setToken(request.getToken());
		DocumentDTO documentDTO = new DocumentDTO();
		try {
			documentDTO.setDocumentBytes(file.getBytes());
			documentDTO.setDocumentInputStream(file.getInputStream());
		} catch (IOException e) {
			log.info("Getting IOException Exception :", e);
			throw new MerchantException(ErrorConstants.UNABLE_TO_PROCESS_REQUEST_CODE,
					ErrorConstants.UNABLE_TO_PROCESS_REQUEST_MSG);
		}

		documentDTO.setName(request.getName());
		documentDTO.setContentType(file.getContentType());
		documentDTO.setApprovalStatus(request.isApprovalStatus());
		documentDTO.setDocumentCategory(request.getDocCategory());
		documentDTO.setDocumentType(request.getDocType());
		mobRequest.setDocumentDTO(documentDTO);
		try {
			
			log.info("MOB Request For uploadDocument : {}", mobRequest);
			mobResonse = merchantService.uploadDocuments(mobRequest);
			log.info("MOB Response For uploadDocument : {}", mobResonse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.info("exception from MOB while uploadDocument: {} ", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}

		return mobResonse;
	}

	public GetKYCDocCategoriesByBusinessTypeResponse GetKYCDocList(MerchantGetKYCDocListRequest request)
			throws MerchantException {

		GetKYCDocCategoriesByBusinessTypeResponse mobResponse = new GetKYCDocCategoriesByBusinessTypeResponse();
		GetKYCCategoriesByBusinessTypeRequest mobRequest = new GetKYCCategoriesByBusinessTypeRequest();

		mobRequest.setBizType(request.getBusinessType());
		mobRequest.setToken(request.getToken());
		try {
			
			log.info("MOB Request For GetKYCDocList : {}", mobRequest);
			mobResponse = merchantService.getKycDocCategoriesByBusinessType(mobRequest);
			log.info("MOB Request For GetKYCDocList : {}", mobResponse);
			
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.info("exception from MOB while GetKYCDocList: {} ", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}

		return mobResponse;
	}
	
	
	public UpdateSourceOfAcquisitionResponse updatesourceofacquisition(UpdateSourceOfAcquisitionRequest request) throws MerchantException
	{
		UpdatesourceOfAcquisitionRequest mobRequest=  new UpdatesourceOfAcquisitionRequest();
		
		List<SourceOfAcquisitionDTO> sourceOfAcquisitionList =  new  ArrayList<SourceOfAcquisitionDTO>();
		SourceOfAcquisitionDTO  sourceOfAcquisitionDTO =  new SourceOfAcquisitionDTO() ;
		sourceOfAcquisitionDTO.setMerchantId(request.getMerchantId());
		sourceOfAcquisitionDTO.setSourceOfAcquisition(request.getSourceOfAcquisition());
		sourceOfAcquisitionList.add(sourceOfAcquisitionDTO);
		
		mobRequest.setToken(request.getToken());
		mobRequest.setSourceOfAcquisitionList(sourceOfAcquisitionList);
		UpdatesourceOfAcquisitionResponse mobResponse =null ;
		
		try {
			mobResponse = merchantService.updateSourceOfAcquisition(mobRequest);
		}catch (HttpTransportException httpException) {
			log.info("Source of Acquisition not updated for merchantId : {} {} ",request.getMerchantId());
			log.error("Getting HTTPTransport Exception from MOB while updatesourceofacquisition : {}", httpException);
		} 
		catch (ServiceException e) {
			log.info("Source of Acquisition not updated for merchantId : {} {} ",request.getMerchantId());
			log.error("Exception From MOB while updatesourceofacquisition : {} {} {} ",e,e.getErrCode(),e.getErrMsg());
		}
		
		UpdateSourceOfAcquisitionResponse response = new UpdateSourceOfAcquisitionResponse();
		response.setSuccess(mobResponse.isSuccess());
		return response;
		
	}

	public void setMobMapperUtil(MOBMapperUtil mobMapperUtil) {
		this.mobMapperUtil = mobMapperUtil;
	}
	

}
