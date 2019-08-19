package com.snapdeal.opspanel.promotion.rp.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;

import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.mob.client.ICategoryServices;
import com.snapdeal.mob.client.IDealerServices;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.client.IPartnerServices;
import com.snapdeal.mob.client.IPlatformServices;
import com.snapdeal.mob.common.constant.RestURIConstants;
import com.snapdeal.mob.dto.CommunicationMobileDTO;
import com.snapdeal.mob.dto.DocumentDTO;
import com.snapdeal.mob.enums.MOBRequestHeaders;
import com.snapdeal.mob.enums.SourceOfAcquisition;
import com.snapdeal.mob.errorcodes.MOBRequestExceptionCodes;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.AbstractRequest;
import com.snapdeal.mob.request.CreateMerchantRequest;
import com.snapdeal.mob.request.CreateUpdateMicroMerchantRequest;
import com.snapdeal.mob.request.GetAllMerchantsRequest;
import com.snapdeal.mob.request.GetDocumentsRequest;
import com.snapdeal.mob.request.GetKYCCategoriesByBusinessTypeRequest;
import com.snapdeal.mob.request.GetKYCDocsCommentsRequest;
import com.snapdeal.mob.request.GetMerchantConfForMerchantIdResponse;
import com.snapdeal.mob.request.GetMerchantConfigurationsByMerchantIdRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByBusinessCategoryRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByEmailIdRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByMerchantIdRequest;
import com.snapdeal.mob.request.GetMerchantStaticConfigurationsRequest;
import com.snapdeal.mob.request.GetMerchantsByCriteriaRequest;
import com.snapdeal.mob.request.GetPGPreferenceByMerchantIdRequest;
import com.snapdeal.mob.request.GetPaytagForMerchantRequest;
import com.snapdeal.mob.request.GetUIDataRequest;
import com.snapdeal.mob.request.UpdateBankStatusRequest;
import com.snapdeal.mob.request.UpdateDefaultMerchantForPlatformRequest;
import com.snapdeal.mob.request.UpdateKYCDocsStatusRequest;
import com.snapdeal.mob.request.UpdateKYCStatusRequest;
import com.snapdeal.mob.request.UpdateMerchantCommunicationNumbersRequest;
import com.snapdeal.mob.request.UpdateMerchantConfigurationsRequest;
import com.snapdeal.mob.request.UpdateMerchantDetailsRequest;
import com.snapdeal.mob.request.UpdateMerchantStatusRequest;
import com.snapdeal.mob.request.UpdatePGDetailRequest;
import com.snapdeal.mob.request.UpdatePGPreferenceRequest;
import com.snapdeal.mob.request.UpdateProviderMerchantIDRequest;
import com.snapdeal.mob.request.UploadDocumentsRequest;
import com.snapdeal.mob.request.category.GetAllCategoriesRequest;
import com.snapdeal.mob.request.dealer.GetAllDealersRequest;
import com.snapdeal.mob.request.dealer.GetDealerbyIdRequest;
import com.snapdeal.mob.request.dealer.GetDealersByCriteriaRequest;
import com.snapdeal.mob.request.dealer.GetDealersForMerchantRequest;
import com.snapdeal.mob.request.partner.CreatePartnerRequest;
import com.snapdeal.mob.request.partner.GetAllPartnersRequest;
import com.snapdeal.mob.request.partner.GetPartnerBankDetailsRequest;
import com.snapdeal.mob.request.partner.GetPartnerByIdRequest;
import com.snapdeal.mob.request.partner.GetPartnerDetailsByEmailIdRequest;
import com.snapdeal.mob.request.partner.GetPartnerDocumentListRequest;
import com.snapdeal.mob.request.partner.GetPartnerDocumentRequest;
import com.snapdeal.mob.request.partner.GetPartnerForMerchantRequest;
import com.snapdeal.mob.request.partner.GetPartnerMerchantsRequest;
import com.snapdeal.mob.request.partner.GetPartnersByCriteriaRequest;
import com.snapdeal.mob.request.partner.UpdatePartnerBankDetailsRequest;
import com.snapdeal.mob.request.partner.UpdatePartnerBankStatusRequest;
import com.snapdeal.mob.request.partner.UpdatePartnerDetailsRequest;
import com.snapdeal.mob.request.partner.UpdatePartnerKYCStatusRequest;
import com.snapdeal.mob.request.partner.UpdatePartnerStatusRequest;
import com.snapdeal.mob.request.partner.UploadPartnerDocumentRequest;
import com.snapdeal.mob.request.platform.CreatePlatformRequest;
import com.snapdeal.mob.request.platform.GetAllPlatformRequest;
import com.snapdeal.mob.request.platform.GetMerchantsForPlatformRequest;
import com.snapdeal.mob.request.platform.GetPartnersForPlatformRequest;
import com.snapdeal.mob.request.platform.GetPlatformBankDetailsRequest;
import com.snapdeal.mob.request.platform.GetPlatformDetailsByEmailIdRequest;
import com.snapdeal.mob.request.platform.GetPlatformDocumentListRequest;
import com.snapdeal.mob.request.platform.GetPlatformDocumentsRequest;
import com.snapdeal.mob.request.platform.GetPlatformRequest;
import com.snapdeal.mob.request.platform.GetPlatformsByCriteriaRequest;
import com.snapdeal.mob.request.platform.GetPlatformsForMerchantRequest;
import com.snapdeal.mob.request.platform.UpdatePlatformBankDetailsRequest;
import com.snapdeal.mob.request.platform.UpdatePlatformBankStatusRequest;
import com.snapdeal.mob.request.platform.UpdatePlatformDetailsRequest;
import com.snapdeal.mob.request.platform.UpdatePlatformKYCStatusRequest;
import com.snapdeal.mob.request.platform.UpdatePlatformStatusRequest;
import com.snapdeal.mob.request.platform.UploadPlatformDocumentRequest;
import com.snapdeal.mob.response.CreateMerchantResponse;
import com.snapdeal.mob.response.CreateUpdateMicroMerchantResponse;
import com.snapdeal.mob.response.GetAllMerchantsResponse;
import com.snapdeal.mob.response.GetDocumentsResponse;
import com.snapdeal.mob.response.GetKYCDocsCommentsResponse;
import com.snapdeal.mob.response.GetMerchantByCriteriaResponse;
import com.snapdeal.mob.response.GetMerchantConfigurationsResponse;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.response.GetMerchantDetailsByBusinessCategoryResponse;
import com.snapdeal.mob.response.GetPGPreferenceByMerchantIdResponse;
import com.snapdeal.mob.response.GetTerminalInfoByMerchantIdResponse;
import com.snapdeal.mob.response.GetUIDataResponse;
import com.snapdeal.mob.response.UpdateBankStatusResponse;
import com.snapdeal.mob.response.UpdateDefaultMerchantForPlatformResponse;
import com.snapdeal.mob.response.UpdateKYCStatusResponse;
import com.snapdeal.mob.response.UpdateMerchantConfigurationsResponse;
import com.snapdeal.mob.response.UpdateMerchantDetailsResponse;
import com.snapdeal.mob.response.UpdateMerchantStatusResponse;
import com.snapdeal.mob.response.UpdatePGDetailResponse;
import com.snapdeal.mob.response.UpdatePGPreferenceResponse;
import com.snapdeal.mob.response.UpdateProviderMerchantIDResponse;
import com.snapdeal.mob.response.UploadDocumentsResponse;
import com.snapdeal.mob.response.dealer.GetAllDealersResponse;
import com.snapdeal.mob.response.dealer.GetDealersByCriteriaResponse;
import com.snapdeal.mob.response.dealer.GetDealersForMerchantResponse;
import com.snapdeal.mob.response.partner.CreatePartnerResponse;
import com.snapdeal.mob.response.partner.GetAllPartnersResponse;
import com.snapdeal.mob.response.partner.GetPartnerBankDetailsResponse;
import com.snapdeal.mob.response.partner.GetPartnerDocumentListResponse;
import com.snapdeal.mob.response.partner.GetPartnerForMerchantResponse;
import com.snapdeal.mob.response.partner.GetPartnerMerchantsResponse;
import com.snapdeal.mob.response.partner.GetPartnerResponse;
import com.snapdeal.mob.response.partner.GetPartnersByCriteriaResponse;
import com.snapdeal.mob.response.partner.UpdatePartnerBankDetailsResponse;
import com.snapdeal.mob.response.partner.UpdatePartnerBankStatusResponse;
import com.snapdeal.mob.response.partner.UpdatePartnerDetailsResponse;
import com.snapdeal.mob.response.partner.UpdatePartnerKYCStatusResponse;
import com.snapdeal.mob.response.partner.UpdatePartnerStatusResponse;
import com.snapdeal.mob.response.platform.CreatePlatformResponse;
import com.snapdeal.mob.response.platform.GetAllPlatformResponse;
import com.snapdeal.mob.response.platform.GetMerchantsForPlatformResponse;
import com.snapdeal.mob.response.platform.GetPartnersForPlatformResponse;
import com.snapdeal.mob.response.platform.GetPlatformBankDetailsResponse;
import com.snapdeal.mob.response.platform.GetPlatformDocumentListResponse;
import com.snapdeal.mob.response.platform.GetPlatformResponse;
import com.snapdeal.mob.response.platform.GetPlatformsByCriteriaResponse;
import com.snapdeal.mob.response.platform.GetPlatformsForMerchantResponse;
import com.snapdeal.mob.response.platform.UpdatePlatformBankDetailsResponse;
import com.snapdeal.mob.response.platform.UpdatePlatformBankStatusResponse;
import com.snapdeal.mob.response.platform.UpdatePlatformDetailsResponse;
import com.snapdeal.mob.response.platform.UpdatePlatformKYCStatusResponse;
import com.snapdeal.mob.response.platform.UpdatePlatformStatusResponse;
import com.snapdeal.mob.ui.response.UIData;
import com.snapdeal.opspanel.audit.annotations.Audited;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.commons.utils.GenericControllerUtils;
import com.snapdeal.opspanel.promotion.Response.GenericResponse;
import com.snapdeal.opspanel.promotion.enums.FCMerchantsComment;
import com.snapdeal.opspanel.promotion.enums.MerchantOPSCallTypeEnum;
import com.snapdeal.opspanel.promotion.enums.OfflineOnlineMerchantsComment;
import com.snapdeal.opspanel.promotion.enums.VerificationCallStatus;
import com.snapdeal.opspanel.promotion.enums.WelcomeCallStatus;
import com.snapdeal.opspanel.promotion.utils.OPSUtils;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.service.impl.SearchUserServicesImpl;
import com.snapdeal.payments.roleManagementModel.commons.PreAuthorize;
import com.snapdeal.vanila.bulk.BulkTID.BulkTIDConstants;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.enums.AssociationOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.dealer.enums.DealerOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;
import com.snapdeal.vanila.dto.MPSearch;
import com.snapdeal.vanila.dto.MPTransactionDTO;
import com.snapdeal.vanila.dto.MPViewFilters;
import com.snapdeal.vanila.enums.BulkMerchantHeirarchyActivityIds;
import com.snapdeal.vanila.exception.MerchantException;
import com.snapdeal.vanila.request.GetAttemptsRequest;
import com.snapdeal.vanila.request.GetMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.InsertMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.vanila.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.vanila.request.MerchantPointOfContactRequest;
import com.snapdeal.vanila.request.MerchantRefundAmountRequest;
import com.snapdeal.vanila.response.MerchantGetTransactionResponse;
import com.snapdeal.vanila.response.MerchantRefundAmountResponse;
import com.snapdeal.vanila.service.MerchantOnBoardService;
import com.snapdeal.vanila.service.MerchantOpsService;
import com.snapdeal.vanila.utils.MOBUtils;
import static com.snapdeal.vanila.utils.AppConstants.maliciousFileExts;
import static com.snapdeal.vanila.utils.AppConstants.allowedFileExts;


import lombok.extern.slf4j.Slf4j;

@Slf4j
@RestController
@RequestMapping(RestURIConstants.BASE_URI)
public class MOBController {

	@Autowired
	MerchantOnBoardService mobService;

	@Autowired
	MerchantOpsService merchantOpsService;

	@Autowired
	private HttpServletRequest servletRequest;

	@Autowired
	private SearchUserServicesImpl searchUserServices;

	@Autowired
	private TokenService tokenService;

	@Autowired
	@Qualifier("categoryServicesImpl")
	ICategoryServices categoryServices;

	@Autowired
	@Qualifier("platformServicesImpl")
	IPlatformServices platformServices;

	@Autowired
	@Qualifier("partnerServicesImpl")
	IPartnerServices partnerServices;

	@Autowired
	IDealerServices dealerServices;

	@Autowired
	IMerchantServices merchantServices;

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@Audited(context = "MOB", searchId = "request.email", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = RestURIConstants.CREATE_MERCHANT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public CreateMerchantResponse createMerchantByEmail(@RequestBody @Valid CreateMerchantRequest request,
			BindingResult results, HttpServletRequest httpRequest) throws Exception {
		if (results.hasErrors() && null != results.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while creating merchant by email.");
			throw new Exception(code.errMsg());
		}
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		Enumeration<String> sa = httpRequest.getHeaderNames();
		while (sa.hasMoreElements()) {
			System.out.println(sa.nextElement());
		}
		System.out.println("token :" + request.getToken());
		return mobService.createMerchantByEmail(request);
	}

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@Audited(context = "MOB", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = RestURIConstants.UPDATE_MERCHANT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public UpdateMerchantDetailsResponse updateMerchantDetail(@RequestBody UpdateMerchantDetailsRequest request,
			BindingResult results, HttpServletRequest httpRequest) throws Exception {
		if (results.hasErrors() && null != results.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while updating merchant details.");
			throw new Exception(code.errMsg());
		}
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.updateMerchantDetail(request);
	}

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@Audited(context = "MOB", searchId = "request.merchantId", reason = "request.reason", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value = RestURIConstants.UPDATE_MERCHANT_STATUS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public UpdateMerchantStatusResponse updateMerchantStatus(@RequestBody @Valid UpdateMerchantStatusRequest request,
			BindingResult results, HttpServletRequest httpRequest) throws Exception {
		if (results.hasErrors() && null != results.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while updating merchant status.");
			throw new Exception(code.errMsg());
		}
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.updateMerchantStatus(request);

	}

	@Audited(context = "MOB", searchId = "merchantId", reason = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or  hasPermission('OPS_MERCHANT_TERMINAL_MAPPING'))")
	@RequestMapping(value = RestURIConstants.GET_MERCHANT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetMerchantDetails getMerchantDetails(@PathVariable("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws Exception {
		GetMerchantDetailsByMerchantIdRequest request = new GetMerchantDetailsByMerchantIdRequest();
		request.setMerchantId(merchantId);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.getMerchantById(request);
	}

	@Audited(context = "MOB", searchId = "merchantId", reason = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_PAYTAG_MERCHANT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GenericResponse getMerchantPayTag(@PathVariable("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws Exception {
		GetPaytagForMerchantRequest request = new GetPaytagForMerchantRequest();
		request.setMerchantId(merchantId);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return OPSUtils.getGenericResponse(mobService.getMerchantPayTag(request));
	}

	@Audited(context = "MOB", searchId = "encodeEmailId", reason = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY')  or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_MERCHANT_EMAIL, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetMerchantDetails getMerchantDetailsByEmail(@PathVariable("email") String encodeEmailId,
			HttpServletRequest httpRequest) throws Exception {
		// to do -> handle creation of user by mobile or email
		final String email = new String(Base64.decodeBase64(encodeEmailId.getBytes()));
		GetMerchantDetailsByEmailIdRequest request = new GetMerchantDetailsByEmailIdRequest();
		request.setEmail(email);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.getMerchantDetailsByEmail(request);
	}

	@Audited(context = "MOB", searchId = "businessCategory", reason = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY')  or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_MERCHANT_BUSINESS_CATEGORY, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetMerchantDetailsByBusinessCategoryResponse getMerchantDetailsByBusinessCategory(
			@PathVariable("category") String businessCategory, HttpServletRequest httpRequest) throws Exception {
		GetMerchantDetailsByBusinessCategoryRequest request = new GetMerchantDetailsByBusinessCategoryRequest();
		request.setBusinessCategory(businessCategory);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.getMerchantDetailsByBusinessCategory(request);
	}

	@Audited(context = "MOB", searchId = "", reason = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY')  or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_MERCHANT_ALL, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetAllMerchantsResponse getAllMerchant(HttpServletRequest httpRequest) throws Exception {
		GetAllMerchantsRequest request = new GetAllMerchantsRequest();
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.getAllMerchant(request);
	}

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@Audited(context = "MOB", searchId = "merchantId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@RequestMapping(value = RestURIConstants.UPLOAD_DOCUMENT, method = RequestMethod.POST, produces = RestURIConstants.APPLICATION_JSON)
	public UploadDocumentsResponse uploadDocument(@RequestParam("name") String name,
			@RequestParam("merchantId") String merchantId, @RequestParam("docCategory") String docCategory,
			@RequestParam("docType") String docType, @RequestParam("approvalStatus") boolean approvalStatus,
			@RequestParam("file") MultipartFile file, HttpServletRequest httpRequest) throws Exception {

		verifyUploadedFileExtension(file.getOriginalFilename());

		UploadDocumentsRequest uploadDocumentsRequest = new UploadDocumentsRequest();
		uploadDocumentsRequest.setMerchantId(merchantId);
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setApprovalStatus(approvalStatus);
		documentDTO.setDocumentInputStream(file.getInputStream());
		documentDTO.setContentType(file.getContentType())																																																																							;
		documentDTO.setName(name);
		documentDTO.setDocumentSize(file.getSize());
		documentDTO.setDocumentType(docType);
		documentDTO.setDocumentCategory(docCategory);
		documentDTO.setDocumentBytes(file.getBytes());
	
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Disposition", "inline; filename="+file.getOriginalFilename());
	
		documentDTO.setHeader(header);
		
		uploadDocumentsRequest.setDocumentDTO(documentDTO);
		uploadDocumentsRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		uploadDocumentsRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.uploadDocument(uploadDocumentsRequest);
	}

	@Audited(context = "MOB", searchId = "merchantId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY')  or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.DOWNLOAD_DOCUMENT, method = RequestMethod.GET)
	public GetDocumentsResponse downloadDocument(@PathVariable String merchantId, @PathVariable String documentId,
			HttpServletRequest httpRequest) throws Exception {

		GetDocumentsRequest request = new GetDocumentsRequest();
		request.setDocId(documentId);
		request.setMerchantId(merchantId);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		GetDocumentsResponse response = null;
		response = mobService.getDocumentPresignedUrl(request);
		return response;
	}

	@Audited(context = "MOB", searchId = "type", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_UI_DATA, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetUIDataResponse getUIDataByType(@PathVariable("type") String type, @PathVariable("parent") String parent,
			@RequestParam(value = "integrationMode", required = false) String integrationMode,
			HttpServletRequest httpRequest) throws Exception {
		GetUIDataRequest request = new GetUIDataRequest();
		request.setType(type);
		request.setParent(parent);
		request.setIntegrationMode(integrationMode);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.getUIDataByType(request);
	}

	@Audited(context = "MOB", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW')  or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@RequestMapping(value = RestURIConstants.UPDATE_PG_DETAIL, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public UpdatePGDetailResponse updatePGDetail(@RequestBody @Valid UpdatePGDetailRequest request,
			BindingResult results, HttpServletRequest httpRequest) throws Exception {

		if (results.hasErrors() && null != results.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while updating pg details.");
		}
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.updatePGDetail(request);
	}

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@Audited(context = "MOB", searchId = "request.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = RestURIConstants.UPDATE_PG_PREFERENCE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.PUT)
	public UpdatePGPreferenceResponse updatePGPrefs(@RequestBody @Valid UpdatePGPreferenceRequest request,
			BindingResult results, HttpServletRequest httpRequest) throws Exception {

		if (results.hasErrors() && null != results.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(results.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while updating pg details.");
		}
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		return mobService.updatePGPrefrence(request);
	}

	@Audited(context = "MOB", searchId = "merchantId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_MERCHANT_PG_PREFERENCE, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GetPGPreferenceByMerchantIdResponse getPGPrefs(@PathVariable("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws Exception {
		GetPGPreferenceByMerchantIdRequest getPGPreferenceByMerchantIdRequest = new GetPGPreferenceByMerchantIdRequest();
		getPGPreferenceByMerchantIdRequest.setMerchantId(merchantId);
		getPGPreferenceByMerchantIdRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		getPGPreferenceByMerchantIdRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.getPGPrefrence(getPGPreferenceByMerchantIdRequest);
	}

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@Audited(context = "MOB", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = RestURIConstants.UPDATE_KYC_STATUS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public UpdateKYCStatusResponse updateKycStatus(@RequestBody UpdateKYCStatusRequest updateKYCStatusRequest,
			HttpServletRequest httpRequest) throws Exception {
		updateKYCStatusRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		updateKYCStatusRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.updateKycStatus(updateKYCStatusRequest);
	}

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@Audited(context = "MOB", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = RestURIConstants.UPDATE_BANK_STATUS, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public UpdateBankStatusResponse verifyBankDetails(@RequestBody UpdateBankStatusRequest verifyBankDetailsRequest,
			HttpServletRequest httpRequest) throws Exception {
		verifyBankDetailsRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		verifyBankDetailsRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.updateBankDetails(verifyBankDetailsRequest);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') )")
	@Audited(context = "MOB", searchId = "request.searchCriteria.transactionId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "searchfilter", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse getTxnsOfMerchant(
			@RequestParam(value = "fromAmount", required = false) BigDecimal fromAmount,
			@RequestParam(value = "toAmount", required = false) BigDecimal toAmount,
			@RequestBody @Valid MerchantGetFilterTransactionRequest request, HttpServletRequest httpRequest)
					throws MerchantException {
		request.setMerchantId(httpRequest.getHeader("merchantId"));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		MPViewFilters filters = new MPViewFilters();
		MPSearch searchCriteria = new MPSearch();
		if (request.getFilters() == null) {
			request.setFilters(filters);
		}
		if (request.getSearchCriteria() == null) {
			request.setSearchCriteria(searchCriteria);
		}
		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();
		response = mobService.getTxnsOfMerchant(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_VIEW') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@Audited(context = "MOB", searchId = "request.searchCriteria.transactionId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "searchTransactionInBulk", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse searchTransactionInBulk(
			@RequestParam(value = "fromAmount", required = false) BigDecimal fromAmount,
			@RequestParam(value = "toAmount", required = false) BigDecimal toAmount,
			@RequestBody @Valid MerchantGetFilterTransactionRequest request, HttpServletRequest httpRequest)
					throws MerchantException {
		request.setMerchantId(httpRequest.getHeader("merchantId"));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		MPViewFilters filters = new MPViewFilters();
		MPSearch searchCriteria = new MPSearch();
		if (request.getFilters() == null) {
			request.setFilters(filters);
		}
		if (request.getSearchCriteria() == null) {
			request.setSearchCriteria(searchCriteria);
		}

		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();
		response = mobService.getTxnsOfMerchant(request);

		List<MPTransactionDTO> mpTransactions = response.getMpTransactions();

		while (mpTransactions != null && mpTransactions.size() < 1000) {

			request.setPage(request.getPage() + 1);

			MerchantGetTransactionResponse tempResponse = mobService.getTxnsOfMerchant(request);

			if (tempResponse.getMpTransactions() == null || tempResponse.getMpTransactions().isEmpty())
				break;

			mpTransactions.addAll(tempResponse.getMpTransactions());
		}

		response.setMpTransactions(mpTransactions);

		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.searchCriteria.transactionId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getTxnsOfMerchantBySearch", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse getTxnsOfMerchantBySearch(@RequestBody @Valid MerchantGetSearchTransactionRequest request,
			HttpServletRequest httpRequest) throws MerchantException {
		request.setMerchantId(httpRequest.getHeader("merchantId"));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		MPSearch search = new MPSearch();
		if (request.getSearchCriteria() == null) {
			request.setSearchCriteria(search);
		}
		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();
		response = mobService.getTxnsOfMerchantBySearch(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.orderId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MERCHANTOPS_UPDATER') or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = "refundMoney", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse refundMoney(@RequestBody @Valid MerchantRefundAmountRequest request,
			HttpServletRequest httpRequest) throws MerchantException {
		request.setMerchantId(httpRequest.getHeader("merchantId"));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		MerchantRefundAmountResponse response = new MerchantRefundAmountResponse();
		response = mobService.refundMoney(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "getMerchantsByCriteriaRequest.searchString", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW')  or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANTOPS_BULK_UPDATER') or hasPermission('OPS_MERCHANTOPS_VIEW') or hasPermission('OPS_MERCHANTOPS_UPDATER') or hasPermission('OPS_CORPACCOUNT_MERCHANTLOAD_NODAL') or hasPermission('OPS_CORPACCOUNT_CORPTOCORP') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER'))")
	@RequestMapping(value = RestURIConstants.GET_MERCHANTS_BY_CRITERIA, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetMerchantByCriteriaResponse getMerchantOnCriteria(
			@RequestBody GetMerchantsByCriteriaRequest getMerchantsByCriteriaRequest, HttpServletRequest httpRequest)
					throws Exception {
		getMerchantsByCriteriaRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		getMerchantsByCriteriaRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.getMerchantsOnCriteria(getMerchantsByCriteriaRequest);
	}

	@Audited(context = "MOB", searchId = "getMerchantsByCriteriaRequest.searchString", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW')  or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANTOPS_BULK_UPDATER') or hasPermission('OPS_MERCHANTOPS_VIEW') or hasPermission('OPS_MERCHANTOPS_UPDATER') or hasPermission('OPS_CORPACCOUNT_MERCHANTLOAD_NODAL') or hasPermission('OPS_CORPACCOUNT_CORPTOCORP'))")
	@RequestMapping(value = "getMerchantOnCriteriaBulk", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetMerchantByCriteriaResponse getMerchantOnCriteriaBulk(
			@RequestBody GetMerchantsByCriteriaRequest getMerchantsByCriteriaRequest, HttpServletRequest httpRequest)
					throws Exception {
		Long totalfetched = 0l, totalAvailable = Long.MAX_VALUE;
		GetMerchantByCriteriaResponse response = null, tempResponse = null;
		getMerchantsByCriteriaRequest.setRecordsPerPage(1000);
		int pageNumber = 1;
		while (totalfetched <= totalAvailable && pageNumber<=10) {
			getMerchantsByCriteriaRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
			getMerchantsByCriteriaRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
			if (response == null) {
				getMerchantsByCriteriaRequest.setPageNumber(pageNumber);
				response = mobService.getMerchantsOnCriteria(getMerchantsByCriteriaRequest);
				totalAvailable = (long) response.getTotalCount();

			} else {
			
				getMerchantsByCriteriaRequest.setPageNumber(pageNumber);
				tempResponse = mobService.getMerchantsOnCriteria(getMerchantsByCriteriaRequest);
				response.getMerchantDetailsList().addAll(tempResponse.getMerchantDetailsList());
			}
			pageNumber++;
			totalfetched += 1000l;
		}
		return response;
	}
	/*
	@Audited(context = "MOB", searchId = "getMerchantsByCriteriaRequest.searchString", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW')  or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANTOPS_BULK_UPDATER') or hasPermission('OPS_MERCHANTOPS_VIEW') or hasPermission('OPS_MERCHANTOPS_UPDATER') or hasPermission('OPS_CORPACCOUNT_MERCHANTLOAD_NODAL') or hasPermission('OPS_CORPACCOUNT_CORPTOCORP'))")
	@RequestMapping(value = "getMerchantOnCriteriaBulk2", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GetMerchantByCriteriaResponse getMerchantOnCriteriaBulk2(
			@RequestBody GetMerchantsByCriteriaRequest getMerchantsByCriteriaRequest, HttpServletRequest httpRequest) throws Exception {
		Long totalfetched = 0l, totalAvailable = Long.MAX_VALUE;
		GetMerchantByCriteriaResponse response = null, tempResponse = null;
		getMerchantsByCriteriaRequest.setRecordsPerPage(1000);
		int pageNumber = 1;

		getMerchantsByCriteriaRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		getMerchantsByCriteriaRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));

		getMerchantsByCriteriaRequest.setPageNumber(pageNumber);
		response = mobService.getMerchantsOnCriteria(getMerchantsByCriteriaRequest);
		totalAvailable = (long) response.getTotalCount();
		totalfetched = 1000l;
		List<GetMerchantByCriteriaResponse> merchantDetailsList=null;
		while(totalfetched<totalAvailable) {
			getMerchantsByCriteriaRequest.setPageNumber(pageNumber);
			merchantDetailsList	= new ArrayList<GetMerchantByCriteriaResponse>();
			GetMerchantByCriteriaResponse asyncResponse= new GetMerchantByCriteriaResponse();
			merchantDetailsList.add(AsyncReportHelper(getMerchantsByCriteriaRequest, response, pageNumber));
			pageNumber++;
			totalfetched+=1000l;
		}
		for(GetMerchantByCriteriaResponse res:merchantDetailsList) {
			response.getMerchantDetailsList().addAll(res.getMerchantDetailsList());
		}
	return response;
}

@Async("reportHelperExecutor")
private GetMerchantByCriteriaResponse AsyncReportHelper(GetMerchantsByCriteriaRequest getMerchantsByCriteriaRequest,
		GetMerchantByCriteriaResponse response, int pageNumber) throws Exception {
	GetMerchantByCriteriaResponse tempResponse;
	getMerchantsByCriteriaRequest.setPageNumber(pageNumber);
	tempResponse = mobService.getMerchantsOnCriteria(getMerchantsByCriteriaRequest);
	return tempResponse;
}
*/

	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_FC_PLUS_MERCHANT_ON_BOARDING') )")
	@Audited(context = "MOB", searchId = "createUpdateMicroMerchantRequest.imsId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = RestURIConstants.CREATE_MICRO_MERCHANT, produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public CreateUpdateMicroMerchantResponse updateMicroMerchant(
			@RequestBody CreateUpdateMicroMerchantRequest createUpdateMicroMerchantRequest,
			HttpServletRequest httpRequest) throws Exception {
		createUpdateMicroMerchantRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		createUpdateMicroMerchantRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		return mobService.updateMicroMerchantDetails(createUpdateMicroMerchantRequest);
	}

/*	@Audited(context = "MOB", searchId = "merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = "/getContactHistoryById", method = RequestMethod.GET)
	public GenericResponse getContactsHistoryByMerchantId(@RequestParam("merchantId") String merchantId)
			throws Exception {

		return OPSUtils.getGenericResponse(merchantOpsService.getCallsHistoryByMerchantId((merchantId)));
	}*/

	@Audited(context = "MOB", searchId = "getMerchantCallHistoryRequest.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = "/getMerchantContactHistory", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenericResponse getMerchantsContactsHistory(
			@RequestBody @Valid GetMerchantCallHistoryRequest getMerchantCallHistoryRequest, BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "getMerchantsContactsHistory");
		return OPSUtils.getGenericResponse(merchantOpsService.getMerchantContactHistory(getMerchantCallHistoryRequest));
	}
	
	@Audited(context = "MOB", searchId = "", skipRequestKeys = {}, skipResponseKeys = {}, viewable=0)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = "/getAttemptsCountForMerchantAndCallType", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenericResponse getAttemptsCountForMerchantAndCallType(@RequestBody @Valid GetAttemptsRequest getAttemptsRequest) throws Exception {
		
		
		return OPSUtils.getGenericResponse(merchantOpsService.getAttemptsCountForMerchantAndCallType(getAttemptsRequest));
	}
	
	@Audited(context = "MOB", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = "/getCallTypesForContactHistory", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GenericResponse getCallTypesForContactHistory() throws Exception {
		
		List<String> callTypeList = new ArrayList<String>();
		for(MerchantOPSCallTypeEnum callType : MerchantOPSCallTypeEnum.values()){
			callTypeList.add(callType.toString());
		}
		return OPSUtils.getGenericResponse(callTypeList);
	}
	
	@Audited(context = "MOB", searchId = "", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = "/getCallStatusForCallTypes", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.GET)
	public GenericResponse getCallStatusForCallTypes(@RequestParam (value="callType") String callType) throws Exception {
		
		if(callType == null){
			throw new InfoPanelException("MCH-1001", "Please specify Call Type to Get Call Status", "Ops Panel MOB Controller");
		}
		List<String> callStatusList = new ArrayList<String>();
		if(callType.equalsIgnoreCase(MerchantOPSCallTypeEnum.VERIFICATION_CALL.toString())){
						for(VerificationCallStatus callstatus : VerificationCallStatus.values()){
							callStatusList.add(callstatus.getStringValue());
						}
		}
		if(callType.equalsIgnoreCase(MerchantOPSCallTypeEnum.WELCOME_CALL.toString())){
			for(WelcomeCallStatus callstatus : WelcomeCallStatus.values()){
				callStatusList.add(callstatus.getstringValue());
			}
		}
		return OPSUtils.getGenericResponse(callStatusList);
	}
	
	
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@Audited(context = "MOB", searchId = "merchantOpsCallEntity.merchantId", viewable = 0)
	@RequestMapping(value = "/insertMerchantContactHistory", method = RequestMethod.POST)
	public GenericResponse insertMerchantContactHistory(@RequestBody @Valid InsertMerchantCallHistoryRequest insertMerchantCallHistory , BindingResult bindingResult)
			throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "insertMerchantContactHistory");
		merchantOpsService.insertMerchantContactHistory(insertMerchantCallHistory);
		
		return OPSUtils.getGenericResponse(true);
	}

	@RequestMapping(value = "/getacquisitiontypes", method = RequestMethod.GET)
	@Audited(context = "MOB")
	public GenericResponse getAllAcquisitionTypes() {
		List<UIData> uiDatas = SourceOfAcquisition.getAllData();
		List<String> acquisitionTypes = new ArrayList<String>();
		for (SourceOfAcquisition soa : SourceOfAcquisition.values()) {
			acquisitionTypes.add(soa.toString());
		}
		return OPSUtils.getGenericResponse(acquisitionTypes);
	}

	@Audited(context = "MOB", searchId = "bizType")
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') )")
	@RequestMapping(value = RestURIConstants.GET_KYCCATEGORIES_BIZTYPE, method = RequestMethod.GET)
	public GenericResponse getKYCDocCategoriesByBusinessType(@PathVariable String bizType,
			HttpServletRequest httpRequest) throws Exception {

		GetKYCCategoriesByBusinessTypeRequest request = new GetKYCCategoriesByBusinessTypeRequest();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setBizType(bizType);
		return OPSUtils.getGenericResponse(mobService.getKYCDocCategoriesByBusinessType(request));
	}

	@Audited(context = "MOB", viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MOBPANEL_VIEW') 	or hasPermission('OPS_FCPLUS_MERCHANT_VIEW_ONLY') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = RestURIConstants.UPDATE_KYC_DOCS_STATUS, method = RequestMethod.POST)
	public GenericResponse updateKycDocStatus(@RequestBody UpdateKYCDocsStatusRequest request,
			HttpServletRequest httpRequest) throws Exception {

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));

		return OPSUtils.getGenericResponse(mobService.updateKYCDocStatus(request));
	}

	@Audited(context = "MOB", searchId = "emailId")
	@PreAuthorize("hasPermission('OPS_FC_PLUS_MERCHANT_ON_BOARDING')")
	@RequestMapping(value = "/getFCPlusMerchant", method = RequestMethod.GET)
	public GenericResponse getFCPlusMerchant(@RequestParam(value = "merchantEmailId") String emailId) throws Exception {

		GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId(emailId);

		return OPSUtils.getGenericResponse(searchUserServices.searchUserByEmail(request));

	}

	@Audited(context = "MOB", searchId = "merchantId", viewable = 1)
	@PreAuthorize("hasPermission('OPS_MOBPANEL_VIEW')")
	@RequestMapping(value = RestURIConstants.UPDATE_MERCHANT_COMMUNICATION_NUMBERS, method = RequestMethod.POST)
	public GenericResponse updateMerchantCommunicationNumbers(@PathVariable("merchantId") String merchantId,
			@RequestBody CommunicationMobileDTO[] communicationMobile, HttpServletRequest httpRequest)
					throws Exception {

		UpdateMerchantCommunicationNumbersRequest request = new UpdateMerchantCommunicationNumbersRequest();
		request.setMerchantId(merchantId);
		request.setCommunicationNumbers(Arrays.asList(communicationMobile));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));

		return OPSUtils.getGenericResponse(mobService.updateMerchantCommunicationNumbers(request));

	}

	@Audited(context = "MOB", searchId = "merchantId")
	@PreAuthorize("hasPermission('OPS_MOBPANEL_VIEW')")
	@RequestMapping(value = RestURIConstants.GET_MERCHANT_DISPLAY_INFO, method = RequestMethod.GET)
	public GenericResponse getMerchantDisplayNumbers(@PathVariable("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws Exception {

		GetMerchantDetailsByMerchantIdRequest request = new GetMerchantDetailsByMerchantIdRequest();

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setMerchantId(merchantId);

		return OPSUtils.getGenericResponse(mobService.getmerchantDisplayInfo(request));

	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnerForMerchant", method = RequestMethod.POST)
	public GenericResponse getPartnerForMerchant(@RequestBody GetPartnerForMerchantRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		GetPartnerForMerchantResponse response = new GetPartnerForMerchantResponse();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));

		response = partnerServices.getPartnerForMerchant(request);

		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = RestURIConstants.GET_PARTNER_BANK_DETAILS, method = RequestMethod.GET)
	public GenericResponse getPartnerBankDetails(@PathVariable("partnerId") String partnerId,
			HttpServletRequest httpRequest) throws ServiceException {

		GetPartnerBankDetailsRequest request = new GetPartnerBankDetailsRequest();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setPartnerId(partnerId);

		GetPartnerBankDetailsResponse response = partnerServices.getPartnerBankDetails(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = RestURIConstants.GET_PLATFORM_BANK_DETAILS, method = RequestMethod.GET)
	public GenericResponse getPlatformsBankDetails(@PathVariable("platformId") String platformId,
			HttpServletRequest httpRequest) throws ServiceException {

		GetPlatformBankDetailsRequest request = new GetPlatformBankDetailsRequest();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setPlatformId(platformId);

		GetPlatformBankDetailsResponse response = platformServices.getPlatformBankDetails(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getDealerByCriteria", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse getDealerByCriteria(@RequestBody @Valid GetDealersByCriteriaRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetDealersByCriteriaResponse response;
		response = dealerServices.getDealerByCriteria(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnerByCriteria", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse getPartnerByCriteria(@RequestBody @Valid GetPartnersByCriteriaRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetPartnersByCriteriaResponse response;
		response = partnerServices.getPartnerByCriteria(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') or  hasPermission('OPS_MERCHANT_TERMINAL_MAPPING') )")
	@RequestMapping(value = "getPlatformByCriteria", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse getPlatformByCriteria(@RequestBody @Valid GetPlatformsByCriteriaRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetPlatformsByCriteriaResponse response;
		response = platformServices.getPlatformByCriteria(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getDealerById", method = RequestMethod.GET)
	public GenericResponse getDealerById(@RequestParam("id") String dealerId, HttpServletRequest httpRequest)
			throws Exception {

		GetDealerbyIdRequest request = new GetDealerbyIdRequest();

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setDealerId(dealerId);
		return OPSUtils.getGenericResponse(dealerServices.getDealerById(request));

	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPlatformById", method = RequestMethod.GET)
	public GenericResponse getPlatform(@RequestParam("id") String platformId, HttpServletRequest httpRequest)
			throws Exception {

		GetPlatformRequest request = new GetPlatformRequest();

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setPlatformId(platformId);
		return OPSUtils.getGenericResponse(platformServices.getPlatform((request)));

	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getAllCategory", method = RequestMethod.GET)
	public GenericResponse getAllCategory(HttpServletRequest httpRequest) throws Exception {

		GetAllCategoriesRequest request = new GetAllCategoriesRequest();

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));

		return OPSUtils.getGenericResponse(categoryServices.getAllCategory(request));

	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "/getActionsForBulkActivity", method = RequestMethod.GET)
	public GenericResponse getActionsForBulkActivity(@RequestParam(value = "activityid") String activityId)
			throws Exception {
		List<String> actionList = new ArrayList<>();
		if (activityId.equalsIgnoreCase(BulkMerchantHeirarchyActivityIds.MH_DEALER_ACTIVITY.toString())) {
			for (DealerOperationType type : DealerOperationType.values()) {
				actionList.add(type.toString());
			}
		} else if (activityId.equalsIgnoreCase(BulkMerchantHeirarchyActivityIds.MH_MERCHANT_ACTIVITY.toString())) {
			for (String action : BulkMerchantHierarchyMerchantConstants.action) {
				actionList.add(action);
			}
		} else if (activityId.equalsIgnoreCase(BulkMerchantHeirarchyActivityIds.MH_ASSOCIATION_ACTIVITY.toString())) {
			for (AssociationOperationType type : AssociationOperationType.values()) {
				actionList.add(type.toString());
			}
		}

		return OPSUtils.getGenericResponse(actionList);

	}

	@Audited(context = "MOB", searchId = "request.emailId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "createPartner", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse createPartner(@RequestBody @Valid CreatePartnerRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		CreatePartnerResponse response;
		response = partnerServices.createPartner(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.partnerId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updatePartnerKYCStatus", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePartnerKYCStatus(@RequestBody @Valid UpdatePartnerKYCStatusRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePartnerKYCStatusResponse response;
		response = partnerServices.updatePartnerKYCStatus(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.partnerId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updatePartnerBankStatus", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePartnerBankStatus(@RequestBody @Valid UpdatePartnerBankStatusRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePartnerBankStatusResponse response;
		response = partnerServices.updatePartnerBankStatus(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.partnerId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updatePartnerDetails", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePartnerDetails(@RequestBody @Valid UpdatePartnerDetailsRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePartnerDetailsResponse response;
		response = partnerServices.updatePartnerDetails(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.partnerId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updatePartnerBankDetails", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePartnerBankDetails(@RequestBody @Valid UpdatePartnerBankDetailsRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePartnerBankDetailsResponse response;
		response = partnerServices.updatePartnerBankDetails(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.partnerId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updatePartnerStatus", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePartnerStatus(@RequestBody @Valid UpdatePartnerStatusRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePartnerStatusResponse response;
		response = partnerServices.updatePartnerStatus(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getAllPartners", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getAllPartners(HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetAllPartnersRequest request = new GetAllPartnersRequest();
		request.setAppName(MOBRequestHeaders.APP_NAME.toString());
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetAllPartnersResponse response;
		response = partnerServices.getAllPartners(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') or  hasPermission('OPS_MERCHANT_TERMINAL_MAPPING') )")
	@RequestMapping(value = "getPartnerById", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPartnerById(@RequestParam("id") String partnerId, HttpServletRequest httpRequest)
			throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetPartnerByIdRequest request = new GetPartnerByIdRequest();
		request.setPartnerId(partnerId);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetPartnerResponse response;
		response = partnerServices.getPartnerById(request);
		return OPSUtils.getGenericResponse(response);
	}

	/*
	 * Upload Partner Document Api
	 */

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "uploadPartnerDocument", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse uploadPartnerDocument(@RequestParam("partnerId") String partnerId,
			@RequestParam("name") String name, @RequestParam("docCategory") String docCategory,
			@RequestParam("docType") String docType, @RequestParam("approvalStatus") boolean approvalStatus,
			@RequestParam("file") MultipartFile file, HttpServletRequest httpRequest)
					throws ServiceException, IOException, OpsPanelException {

		verifyUploadedFileExtension(file.getOriginalFilename());

		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		UploadPartnerDocumentRequest uploadPartnerDocumentRequest = new UploadPartnerDocumentRequest();
		UploadDocumentsResponse response = new UploadDocumentsResponse();
		uploadPartnerDocumentRequest.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		uploadPartnerDocumentRequest.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		uploadPartnerDocumentRequest.setPartnerId(partnerId);
		
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setApprovalStatus(approvalStatus);
		documentDTO.setDocumentInputStream(file.getInputStream());
		documentDTO.setContentType(file.getContentType());
		documentDTO.setName(name);
		documentDTO.setDocumentSize(file.getSize());
		documentDTO.setDocumentType(docType);
		documentDTO.setDocumentCategory(docCategory);
		documentDTO.setDocumentBytes(file.getBytes());

		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Disposition", "inline; filename="+file.getOriginalFilename());
	
		documentDTO.setHeader(header);
	
		uploadPartnerDocumentRequest.setDocumentDTO(documentDTO);
		response = partnerServices.uploadPartnerDocument(uploadPartnerDocumentRequest);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnerDocument", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPartnerDocumentURL(@RequestParam("partnerId") String partnerId,
			@RequestParam("docId") String docId, HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		GetPartnerDocumentRequest request = new GetPartnerDocumentRequest();
		GetDocumentsResponse response = new GetDocumentsResponse();
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setPartnerId(partnerId);
		request.setDocId(docId);
		response = partnerServices.getPartnerDocumentUrl(request);
		return OPSUtils.getGenericResponse(response);
	}
	/*
	 * Upload Platform Document Api
	 */

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "uploadPlatformDocument", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse uploadPlatformDocument(@RequestParam("platformId") String platformId,
			@RequestParam("name") String name, @RequestParam("docCategory") String docCategory,
			@RequestParam("docType") String docType, @RequestParam("approvalStatus") boolean approvalStatus,
			@RequestParam("file") MultipartFile file, HttpServletRequest httpRequest)
					throws ServiceException, IOException, OpsPanelException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		verifyUploadedFileExtension(file.getOriginalFilename());

		UploadPlatformDocumentRequest request = new UploadPlatformDocumentRequest();

		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setPlatformId(platformId);
		DocumentDTO documentDTO = new DocumentDTO();
		documentDTO.setApprovalStatus(approvalStatus);
		documentDTO.setDocumentInputStream(file.getInputStream());
		documentDTO.setContentType(file.getContentType());
		documentDTO.setName(name);
		documentDTO.setDocumentSize(file.getSize());
		documentDTO.setDocumentType(docType);
		documentDTO.setDocumentCategory(docCategory);
		documentDTO.setDocumentBytes(file.getBytes());
	
		Map<String, String> header = new HashMap<String, String>();
		header.put("Content-Disposition", "inline; filename="+file.getOriginalFilename());
	
		documentDTO.setHeader(header);
			
		request.setDocumentDTO(documentDTO);
		UploadDocumentsResponse response = new UploadDocumentsResponse();
		response = platformServices.uploadPlatformDocument(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPlatformDocumentUrl", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPlatformDocumentUrl(@RequestParam("platformId") String platformId,
			@RequestParam("docId") String docId, HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetPlatformDocumentsRequest request = new GetPlatformDocumentsRequest();

		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setPlatformId(platformId);
		request.setDocId(docId);
		GetDocumentsResponse response = new GetDocumentsResponse();
		response = platformServices.getPlatformDocumentUrl(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnerByEmail", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPartnerByEmail(@RequestParam("email") String email, HttpServletRequest httpRequest)
			throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetPartnerDetailsByEmailIdRequest request = new GetPartnerDetailsByEmailIdRequest();
		request.setEmail(email);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetPartnerResponse response;
		response = partnerServices.getPartnerByEmail(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getAllPlatform", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getAllPlatform(HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetAllPlatformRequest request = new GetAllPlatformRequest();

		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetAllPlatformResponse response;
		response = platformServices.getAllPlatform(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.emailId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "createPlatform", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse createPlatform(@RequestBody @Valid CreatePlatformRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		CreatePlatformResponse response;
		response = platformServices.createPlatform(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.platformId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updatePlatformBankDetails", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePlatformBankDetails(@RequestBody @Valid UpdatePlatformBankDetailsRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePlatformBankDetailsResponse response;
		response = platformServices.updatePlatformBankDetails(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.platformId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@RequestMapping(value = "updatePlatformBankStatus", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePlatformBankStatus(@RequestBody @Valid UpdatePlatformBankStatusRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePlatformBankStatusResponse response;
		response = platformServices.updatePlatformBankStatus(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.platformId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@RequestMapping(value = "updatePlatformDetails", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePlatformDetails(@RequestBody @Valid UpdatePlatformDetailsRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePlatformDetailsResponse response;
		response = platformServices.updatePlatformDetails(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.platformId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@RequestMapping(value = "updatePlatformKYCStatus", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePlatformKYCStatus(@RequestBody @Valid UpdatePlatformKYCStatusRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePlatformKYCStatusResponse response;
		response = platformServices.updatePlatformKYCStatus(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "request.platformId", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 1)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER'))")
	@RequestMapping(value = "updatePlatformStatus", produces = "application/json", method = RequestMethod.POST)
	public GenericResponse updatePlatformStatus(@RequestBody @Valid UpdatePlatformStatusRequest request,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		UpdatePlatformStatusResponse response;
		response = platformServices.updatePlatformStatus(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getAllDealers", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getAllDealers(HttpServletRequest httpRequest) throws ServiceException {
		GetAllDealersRequest request = new GetAllDealersRequest();
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetAllDealersResponse response = dealerServices.getAllDealers(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPlatformByEmail", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPlatformByEmail(@RequestParam("email") String email, HttpServletRequest httpRequest)
			throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetPlatformDetailsByEmailIdRequest request = new GetPlatformDetailsByEmailIdRequest();
		request.setEmail(email);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetPlatformResponse response;
		response = platformServices.getPlatformByEmail(request);
		return OPSUtils.getGenericResponse(response);
	}

	/*
	 * 
	 * Associations
	 * 
	 * 
	 * 
	 */

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnerForMerchant", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getPartnerForMerchant(@RequestParam("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetPartnerForMerchantRequest request = new GetPartnerForMerchantRequest();
		request.setMerchantId(merchantId);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetPartnerForMerchantResponse response;
		response = partnerServices.getPartnerForMerchant(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getDealersForMerchant", produces = "application/json", method = RequestMethod.GET)
	public GenericResponse getDealersForMerchant(@RequestParam("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws ServiceException {
		/* request.setMerchantId(httpRequest.getHeader("merchantId")); */

		GetDealersForMerchantRequest request = new GetDealersForMerchantRequest();
		request.setMerchantId(merchantId);
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		GetDealersForMerchantResponse response;
		response = dealerServices.getDealersForMerchant(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnersForPlatform", method = RequestMethod.GET)
	public GenericResponse getPartnersForPlatform(@RequestParam("platformId") String platformId,
			HttpServletRequest httpRequest) throws ServiceException {
		GetPartnersForPlatformResponse response = new GetPartnersForPlatformResponse();
		GetPartnersForPlatformRequest request = new GetPartnersForPlatformRequest();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setPlatformId(platformId);
		response = platformServices.getPartnersForPlatform(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPartnerMerchants", method = RequestMethod.GET)
	public GenericResponse getPartnerMerchants(@RequestParam("partnerId") String partnerId,
			HttpServletRequest httpRequest) throws ServiceException {
		GetPartnerMerchantsResponse response = new GetPartnerMerchantsResponse();
		GetPartnerMerchantsRequest request = new GetPartnerMerchantsRequest();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setPartnerId(partnerId);
		response = partnerServices.getPartnerMerchants(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getPlatformsForMerchant", method = RequestMethod.GET)
	public GenericResponse getPlatformsForMerchant(@RequestParam("merchantId") String merchantId,
			HttpServletRequest httpRequest) throws ServiceException {
		GetPlatformsForMerchantResponse response = new GetPlatformsForMerchantResponse();
		GetPlatformsForMerchantRequest request = new GetPlatformsForMerchantRequest();
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		request.setMerchantId(merchantId);
		response = platformServices.getPlatformsForMerchant(request);
		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("( hasPermission('OPS_MERCHANT_TERMINAL_MAPPING'))")
	@Audited(context = "MOB", searchId = "platformId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "updateDefaultMerchantForPlatform", method = RequestMethod.GET)
	public GenericResponse updateDefaultMerchantForPlatform(@RequestParam("merchantId") String merchantId,
			@RequestParam("platformId") String platformId, HttpServletRequest httpRequest) throws ServiceException {
		UpdateDefaultMerchantForPlatformResponse response = new UpdateDefaultMerchantForPlatformResponse();
		UpdateDefaultMerchantForPlatformRequest updateDefaultMerchantForPlatformRequest = new UpdateDefaultMerchantForPlatformRequest();
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		String providerMerchantId = mobService.getProviderMerchantId(platformId, merchantId, appName, token);
		if (providerMerchantId == null) {
			throw new ServiceException("No Provider Merchant id found for this Platform and Merchant Id", "MOB-1001");
		}
		updateDefaultMerchantForPlatformRequest.setToken(token);
		updateDefaultMerchantForPlatformRequest.setAppName(appName);
		updateDefaultMerchantForPlatformRequest.setMerchantId(merchantId);
		updateDefaultMerchantForPlatformRequest.setPlatformId(platformId);
		updateDefaultMerchantForPlatformRequest.setProviderMerchantId(providerMerchantId);
		response = platformServices.updateDefaultMerchantForPlatform(updateDefaultMerchantForPlatformRequest);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@PreAuthorize("( hasPermission('OPS_MERCHANT_TERMINAL_MAPPING'))")
	@RequestMapping(value = "getProviderMerchantID", method = RequestMethod.GET)
	public GenericResponse getProviderMerchantID(@RequestParam("merchantId") String merchantId,
			@RequestParam("platformId") String platformId, HttpServletRequest httpRequest) throws ServiceException {
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		String providerMerchantId = mobService.getProviderMerchantId(platformId, merchantId, appName, token);
		if (providerMerchantId == null) {
			throw new ServiceException("No Provider Merchant id found for this Platform and Merchant Id", "MOB-1001");
		}
		return OPSUtils.getGenericResponse(providerMerchantId);
	}

	@PreAuthorize("( hasPermission('OPS_MERCHANT_TERMINAL_MAPPING'))")
	@Audited(context = "MOB", searchId = "providerMerchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@RequestMapping(value = "updateProviderMerchantId", method = RequestMethod.GET)
	public GenericResponse updateProviderMerchantId(@RequestParam("merchantId") String merchantId,
			@RequestParam("platformId") String platformId,
			@RequestParam("providerMerchantId") String providerMerchantId, HttpServletRequest httpRequest)
					throws ServiceException {
		UpdateProviderMerchantIDRequest updateProviderMerchantIDRequest = new UpdateProviderMerchantIDRequest();
		UpdateProviderMerchantIDResponse response = new UpdateProviderMerchantIDResponse();
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		updateProviderMerchantIDRequest.setAppName(appName);
		updateProviderMerchantIDRequest.setToken(token);
		updateProviderMerchantIDRequest.setProviderMerchantId(providerMerchantId);
		updateProviderMerchantIDRequest.setPlatformId(platformId);
		updateProviderMerchantIDRequest.setMerchantId(merchantId);
		response = merchantServices.updateProviderMerchantId(updateProviderMerchantIDRequest);
		return OPSUtils.getGenericResponse(response);
	}

	/*
	 * Getting Terminal Info for Merchant Id and Platform Id Combination (i.e.
	 * Provider merchant Id)
	 */

	@PreAuthorize("( hasPermission('OPS_MERCHANT_TERMINAL_MAPPING'))")
	@Audited(context = "MOB", skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getTerminalInfo", method = RequestMethod.GET)
	public ResponseEntity getTerminalInfo(@RequestParam("merchantId") String merchantId,
			@RequestParam("platformId") String platformId, HttpServletRequest httpRequest)
					throws ServiceException, IOException {
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		GetTerminalInfoByMerchantIdResponse response = mobService.getTerminalInfoForMerchant(merchantId, platformId,
				appName, token);
		HttpHeaders headers = new HttpHeaders();
		headers.add("content-disposition", "attachment; filename=" + merchantId + "_terminal_info.csv");
		headers.add("Content-Type", "text/csv");
		StringBuffer sb = MOBUtils.getBufferForCSV(response);
		return new ResponseEntity(sb.toString().getBytes(), headers, HttpStatus.OK);

	}
	
	@PreAuthorize("( hasPermission('OPS_MERCHANT_TERMINAL_MAPPING'))")
	@Audited(context = "MOB", searchId = "platforrmId" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getMerchantsForPlatform", method = RequestMethod.GET)
	public GenericResponse getMerchantsForPlatform(@RequestParam("platformId") String platformId, 
			HttpServletRequest httpRequest) throws ServiceException{
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		GetMerchantsForPlatformRequest getMerchantsForPlatformRequest = new GetMerchantsForPlatformRequest();
		getMerchantsForPlatformRequest.setAppName(appName);
		getMerchantsForPlatformRequest.setToken(token);
		getMerchantsForPlatformRequest.setPlatformId(platformId);
		GetMerchantsForPlatformResponse response = platformServices.getMerchantsForPlatform(getMerchantsForPlatformRequest);
		return OPSUtils.getGenericResponse(response);
	}
	
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') or hasPermission('OPS_MOBPANEL_VIEW'))")
	@Audited(context = "MOB", searchId = "platforrmId" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getDocumentListForPlatform", method = RequestMethod.GET)
	public GenericResponse getDocumentListForPlatform(@RequestParam("platformId") String platformId, 
			HttpServletRequest httpRequest) throws ServiceException{
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		GetPlatformDocumentListRequest getPlatformDocumentListRequest = new GetPlatformDocumentListRequest();
		getPlatformDocumentListRequest.setAppName(appName);
		getPlatformDocumentListRequest.setToken(token);
		getPlatformDocumentListRequest.setPlatformId(platformId);
		GetPlatformDocumentListResponse response = platformServices.getDocumentListForPlatform(getPlatformDocumentListRequest);
		return OPSUtils.getGenericResponse(response);
	}
	
	
	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') or hasPermission('OPS_MOBPANEL_VIEW'))")
	@Audited(context = "MOB", searchId = "partnerId" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getDocumentListForPartner", method = RequestMethod.GET)
	public GenericResponse getDocumentListForPartner(@RequestParam("partnerId") String partnerId, 
			HttpServletRequest httpRequest) throws ServiceException{
		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		GetPartnerDocumentListRequest getPartnerDocumentListRequest = new GetPartnerDocumentListRequest();
		getPartnerDocumentListRequest.setAppName(appName);
		getPartnerDocumentListRequest.setToken(token);
		getPartnerDocumentListRequest.setPartnerId(partnerId);
		GetPartnerDocumentListResponse response = partnerServices.getDocumentListForPartner(getPartnerDocumentListRequest);
		return OPSUtils.getGenericResponse(response);
	}
	
	@RequestMapping(value = "/getActionTypesforTID", method = RequestMethod.GET)
	@Audited(context = "MOB")
	public GenericResponse getActionTypesforTID() {
		List<String>actionTypes = new ArrayList<String>();
		actionTypes.add(BulkTIDConstants.ADD_UPDATE);
		actionTypes.add(BulkTIDConstants.DELETE);
		return OPSUtils.getGenericResponse(actionTypes);
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') or hasPermission('OPS_MOBPANEL_VIEW'))")
	@Audited(context = "MOB", searchId = "platformId" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getPlatformDocumentsUrl", method = RequestMethod.POST)
	public GenericResponse getPlatformDocumentsUrl( @RequestBody  @Valid GetPlatformDocumentsRequest getPlatformDocumentsRequest, BindingResult bindingResult,
			HttpServletRequest httpRequest ) throws ServiceException{

		if (bindingResult.hasErrors() && null != bindingResult.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(bindingResult.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while getPlatformDocumentsUrl" + code );
		}

		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		getPlatformDocumentsRequest.setAppName(appName);
		getPlatformDocumentsRequest.setToken(token);
		return OPSUtils.getGenericResponse( platformServices.getPlatformDocumentUrl( getPlatformDocumentsRequest ) );
	}

	@PreAuthorize("(hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') or hasPermission('OPS_MOBPANEL_VIEW'))")
	@Audited(context = "MOB", searchId = "partnerId" ,skipRequestKeys = {}, skipResponseKeys = {}, viewable = 0)
	@RequestMapping(value = "getPartnerDocumentUrl", method = RequestMethod.POST)
	public GenericResponse getPartnerDocumentUrl( @RequestBody  @Valid GetPartnerDocumentRequest getPartnerDocumentRequest, BindingResult bindingResult,
			HttpServletRequest httpRequest ) throws ServiceException{

		if (bindingResult.hasErrors() && null != bindingResult.getAllErrors()) {
			MOBRequestExceptionCodes code = MOBRequestExceptionCodes
					.valueOf(bindingResult.getAllErrors().get(0).getDefaultMessage());
			log.error("Invalid Request Error occured while getPartnerDocumentUrl " + code );
		}

		String appName = httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString());
		String token = httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString());
		getPartnerDocumentRequest.setAppName(appName);
		getPartnerDocumentRequest.setToken(token);
		return OPSUtils.getGenericResponse( partnerServices.getPartnerDocumentUrl( getPartnerDocumentRequest ) );
	}

	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getKYCDocsComments", method = RequestMethod.GET)
	public GenericResponse getKYCDocsComments(@RequestParam("docId") String docId,
			HttpServletRequest httpRequest) throws ServiceException {
		
		GetKYCDocsCommentsRequest request = new GetKYCDocsCommentsRequest();
		GetKYCDocsCommentsResponse response = new GetKYCDocsCommentsResponse();
		
		request.setDocId(docId);

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		response = merchantServices.getKYCDocsComments(request);

		return OPSUtils.getGenericResponse(response);
	}

	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getFCMerchantsComments", method = RequestMethod.GET)
	public GenericResponse getFCMerchantsComments() throws ServiceException {
		
		List<String> result = new ArrayList<String>();
		for(FCMerchantsComment fcMerchantsComment: FCMerchantsComment.values()){
			result.add( fcMerchantsComment.getFCMerchantsComment() );
		}
		
		return OPSUtils.getGenericResponse( result );
	}

	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getOfflineOnlineMerchantsComments", method = RequestMethod.GET)
	public GenericResponse getOfflineOnlineMerchantsComments() throws ServiceException {
		
		List<String> result = new ArrayList<String>();
		for(OfflineOnlineMerchantsComment offlineOnlineMerchantsComment: OfflineOnlineMerchantsComment.values()){
			result.add( offlineOnlineMerchantsComment.getOfflineOnlineMerchantsComment() );
		}

		return OPSUtils.getGenericResponse( result );
	}



	public static void verifyUploadedFileExtension(String fileName) throws OpsPanelException{
		boolean isFileExtValid = isUploadedFileExtensionValid(fileName);
		if(!isFileExtValid){
			throw new OpsPanelException("MOB-1109","UPLOADED FILE EXTENSION NOT VALID");
		}
	}

	public static boolean isUploadedFileExtensionValid(String fileName){

		String[] UploadedFileExts = fileName.split("\\.");
		//REMOVE FILE NAME FROM ARRAY
		UploadedFileExts = Arrays.copyOfRange(UploadedFileExts, 1, UploadedFileExts.length);

		//IF FILE CONTAINS ANY MALICIOUS EXTENSION, RETURN FALSE
		for(String UploadedFileExt : UploadedFileExts)
			for(String maliciousExt : maliciousFileExts)
				if(UploadedFileExt.equalsIgnoreCase(maliciousExt))
					return false;

		//IF FILE CONTAINS ANY ONE OF ALLOWED EXTENSIONS, RETURN TRUE
		for(String UploadedFileExt : UploadedFileExts)
			for(String allowedExt : allowedFileExts)
				if(UploadedFileExt.equalsIgnoreCase(allowedExt))
					return true;

		//IF THE EXTENSION NEITHER MATCHED ALLOWED NOR MALICIOUS, RETURN FALSE  
		return false;

	}

	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getMerchantStaticConfigurations", method = RequestMethod.GET)
	public GenericResponse getMerchantStaticConfigurations(GetMerchantStaticConfigurationsRequest request,HttpServletRequest httpRequest) throws ServiceException {
		
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));
		
		GetMerchantConfigurationsResponse merchantConfigurationsResponse = merchantServices.getMerchantStaticConfigurations(request);
		return OPSUtils.getGenericResponse(merchantConfigurationsResponse);
	}

	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "getMerchantConfigurations", method = RequestMethod.GET)
	public GenericResponse getMerchantConfiguration(GetMerchantConfigurationsByMerchantIdRequest request,HttpServletRequest httpRequest) throws ServiceException {
		
		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));

		GetMerchantConfForMerchantIdResponse merchantConfigurationsResponse = merchantServices.getMerchantConfigurations(request);
		return OPSUtils.getGenericResponse(merchantConfigurationsResponse);
	}

	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "updateMerchantConfigurations", method = RequestMethod.POST)
	public GenericResponse updateMerchantConfigurations(@RequestBody UpdateMerchantConfigurationsRequest request,
			HttpServletRequest httpRequest) throws ServiceException {

		request.setToken(httpRequest.getHeader(MOBRequestHeaders.TOKEN.toString()));
		request.setAppName(httpRequest.getHeader(MOBRequestHeaders.APP_NAME.toString()));

		UpdateMerchantConfigurationsResponse response = merchantServices.updateMerchantConfigurations(request);
		return OPSUtils.getGenericResponse(response);
	}

	@Audited(context = "MOB", searchId = "getMerchantCallHistoryRequest.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "/downloadMerchantContactHistory", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public @ResponseBody ResponseEntity downloadMerchantsContactsHistory(
			@RequestBody @Valid GetMerchantCallHistoryRequest getMerchantCallHistoryRequest, BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "getMerchantsContactsHistory");
		
		HttpHeaders httpHeaders = new HttpHeaders();
		httpHeaders.add("content-disposition", "attachment; filename=" + "ListToCSV.csv");
		httpHeaders.add("Content-Type", "text/csv");

		StringBuffer sb = merchantOpsService.downloadMerchantContactHistory(getMerchantCallHistoryRequest);
		return new ResponseEntity(sb.toString().getBytes(), httpHeaders, HttpStatus.OK);
	}

	@Audited(context = "MOB", searchId = "merchantPointOfContactRequest.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "/insertMerchantPointOfContact", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenericResponse insertMerchantPointOfContact(
			@RequestBody @Valid MerchantPointOfContactRequest merchantPointOfContactRequest, BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "insertMerchantPointOfContact");
		merchantOpsService.insertMerchantPointOfContact(merchantPointOfContactRequest);
		
		return OPSUtils.getGenericResponse("SUCCESS");
	}

	@Audited(context = "MOB", searchId = "getMerchantCallHistoryRequest.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') or hasPermission('OPS_MERCHANT_HIERARCHY_VIEWER') or hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "/getMerchantPointOfContact", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenericResponse getMerchantPointOfContact(
			@RequestBody @Valid MerchantPointOfContactRequest merchantPointOfContactRequest, BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "getMerchantPointOfContact");
		return OPSUtils.getGenericResponse(merchantOpsService.getMerchantPointOfContact(merchantPointOfContactRequest));
	}

	@Audited(context = "MOB", searchId = "getMerchantCallHistoryRequest.merchantId", skipRequestKeys = {}, skipResponseKeys = {})
	@PreAuthorize("( hasPermission('OPS_MOBPANEL_VIEW') hasPermission('OPS_MERCHANT_HIERARCHY_UPDATER') )")
	@RequestMapping(value = "/updateMerchantPointOfContact", produces = RestURIConstants.APPLICATION_JSON, method = RequestMethod.POST)
	public GenericResponse updateMerchantPointOfContact(
			@RequestBody @Valid MerchantPointOfContactRequest merchantPointOfContactRequest, BindingResult bindingResult) throws Exception {
		GenericControllerUtils.checkBindingResult(bindingResult, "updateMerchantPointOfContact");
		merchantOpsService.updateMerchantPointOfContact(merchantPointOfContactRequest);
		
		return OPSUtils.getGenericResponse("SUCCESS");
	}

}
