package com.snapdeal.merchant.rest.service;

import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.merchant.exception.MerchantException;
import com.snapdeal.merchant.request.CreateMPMerchantRequest;
import com.snapdeal.merchant.request.CreateOfflineMerchantRequest;
import com.snapdeal.merchant.request.GetMerchantStateRequest;
import com.snapdeal.merchant.request.GetMerchantUIDataRequest;
import com.snapdeal.merchant.request.MerchantGetKYCDocListRequest;
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

public interface IMerchantManagementService {

	public GetMerchantUIDataResponse getUIData(GetMerchantUIDataRequest request) throws MerchantException;

	public MerchantDetailResponse getMerchantDetails(MerchantProfileRequest request) throws MerchantException;

	public MerchantRoleResponse getMerchantRoles(MerchantRoleRequest request) throws MerchantException;

	public MerchantUpdateDetailsResponse updateMerchantDetails(MerchantUpdateDetailsRequest request)
			throws MerchantException;
	
	public CreateMPMerchantResponse createMerchant(CreateMPMerchantRequest request) throws MerchantException ;
	
	public GetMerchantStateResponse getMerchantProfileStatus(GetMerchantStateRequest request) throws MerchantException;
	
	public CreateOfflineMerchantResponse createOfflineMerchant(CreateOfflineMerchantRequest request) throws MerchantException ;

	public MerchantUploadDocumentResponse uploadDocument(MerchantUploadDocumentRequest request ,  MultipartFile file) throws MerchantException;

	public MerchantGetKYCDocListResponse GetKYCDocList(MerchantGetKYCDocListRequest request) throws MerchantException;
}
