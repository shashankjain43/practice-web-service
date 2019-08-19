package com.snapdeal.vanila.service;

import java.util.List;

import com.snapdeal.mob.dto.TerminalInfoDTO;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.CreateMerchantRequest;
import com.snapdeal.mob.request.CreateUpdateMicroMerchantRequest;
import com.snapdeal.mob.request.GetAllMerchantsRequest;
import com.snapdeal.mob.request.GetDocumentsRequest;
import com.snapdeal.mob.request.GetKYCCategoriesByBusinessTypeRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByBusinessCategoryRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByEmailIdRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByMerchantIdRequest;
import com.snapdeal.mob.request.GetMerchantsByCriteriaRequest;
import com.snapdeal.mob.request.GetPGPreferenceByMerchantIdRequest;
import com.snapdeal.mob.request.GetPaytagForMerchantRequest;
import com.snapdeal.mob.request.GetTerminalInfoByMerchantIdRequest;
import com.snapdeal.mob.request.GetUIDataRequest;
import com.snapdeal.mob.request.UpdateBankStatusRequest;
import com.snapdeal.mob.request.UpdateKYCDocsStatusRequest;
import com.snapdeal.mob.request.UpdateKYCStatusRequest;
import com.snapdeal.mob.request.UpdateMerchantCommunicationNumbersRequest;
import com.snapdeal.mob.request.UpdateMerchantDetailsRequest;
import com.snapdeal.mob.request.UpdateMerchantStatusRequest;
import com.snapdeal.mob.request.UpdatePGDetailRequest;
import com.snapdeal.mob.request.UpdatePGPreferenceRequest;
import com.snapdeal.mob.request.UploadDocumentsRequest;
import com.snapdeal.mob.response.CreateMerchantResponse;
import com.snapdeal.mob.response.CreateUpdateMicroMerchantResponse;
import com.snapdeal.mob.response.GetAllMerchantsResponse;
import com.snapdeal.mob.response.GetDocumentsResponse;
import com.snapdeal.mob.response.GetKYCDocCategoriesByBusinessTypeResponse;
import com.snapdeal.mob.response.GetMerchantByCriteriaResponse;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.response.GetMerchantDetailsByBusinessCategoryResponse;
import com.snapdeal.mob.response.GetPGPreferenceByMerchantIdResponse;
import com.snapdeal.mob.response.GetPaytagForMerchantResponse;
import com.snapdeal.mob.response.GetTerminalInfoByMerchantIdResponse;
import com.snapdeal.mob.response.GetUIDataResponse;
import com.snapdeal.mob.response.UpdateBankStatusResponse;
import com.snapdeal.mob.response.UpdateKYCDocsStatusResponse;
import com.snapdeal.mob.response.UpdateKYCStatusResponse;
import com.snapdeal.mob.response.UpdateMerchantCommunicationNumbersResponse;
import com.snapdeal.mob.response.UpdateMerchantDetailsResponse;
import com.snapdeal.mob.response.UpdateMerchantStatusResponse;
import com.snapdeal.mob.response.UpdatePGDetailResponse;
import com.snapdeal.mob.response.UpdatePGPreferenceResponse;
import com.snapdeal.mob.response.UploadDocumentsResponse;
import com.snapdeal.vanila.exception.MerchantException;
import com.snapdeal.vanila.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.vanila.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.vanila.request.MerchantRefundAmountRequest;
import com.snapdeal.vanila.response.MerchantGetTransactionResponse;
import com.snapdeal.vanila.response.MerchantRefundAmountResponse;

/**
 * Base interface for all services.
 * 
 * @author
 *
 */
public interface MerchantOnBoardService {
	public UpdatePGDetailResponse updatePGDetail(UpdatePGDetailRequest request) throws Exception;

	public CreateMerchantResponse createMerchantByEmail(CreateMerchantRequest request) throws Exception;

	GetMerchantDetails getMerchantById(GetMerchantDetailsByMerchantIdRequest request) throws Exception;

	public UpdateMerchantDetailsResponse updateMerchantDetail(UpdateMerchantDetailsRequest request) throws Exception;

	public GetMerchantDetails getMerchantDetailsByEmail(GetMerchantDetailsByEmailIdRequest request) throws Exception;

	public GetMerchantDetailsByBusinessCategoryResponse getMerchantDetailsByBusinessCategory(
			GetMerchantDetailsByBusinessCategoryRequest request) throws Exception;

	public GetAllMerchantsResponse getAllMerchant(GetAllMerchantsRequest request) throws Exception;

	public UploadDocumentsResponse uploadDocument(UploadDocumentsRequest uploadDocumentsRequest) throws Exception;

	public GetUIDataResponse getUIDataByType(GetUIDataRequest request) throws Exception;

	public GetDocumentsResponse getDocumentPresignedUrl(GetDocumentsRequest request) throws Exception;

	public UpdateMerchantStatusResponse updateMerchantStatus(UpdateMerchantStatusRequest request) throws Exception;

	public UpdatePGPreferenceResponse updatePGPrefrence(UpdatePGPreferenceRequest request) throws Exception;

	public GetPGPreferenceByMerchantIdResponse getPGPrefrence(GetPGPreferenceByMerchantIdRequest request)
			throws Exception;

	public UpdateKYCStatusResponse updateKycStatus(UpdateKYCStatusRequest request) throws Exception;

	public UpdateBankStatusResponse updateBankDetails(UpdateBankStatusRequest request) throws Exception;

	public GetMerchantByCriteriaResponse getMerchantsOnCriteria(GetMerchantsByCriteriaRequest request) throws Exception;

	public MerchantGetTransactionResponse getTxnsOfMerchant(MerchantGetFilterTransactionRequest request)
			throws MerchantException;

	public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(MerchantGetSearchTransactionRequest request)
			throws MerchantException;

	public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException;

	public CreateUpdateMicroMerchantResponse updateMicroMerchantDetails(
			CreateUpdateMicroMerchantRequest createUpdateMicroMerchantRequest) throws Exception;

	public GetKYCDocCategoriesByBusinessTypeResponse getKYCDocCategoriesByBusinessType(
			GetKYCCategoriesByBusinessTypeRequest request) throws Exception;

	public UpdateKYCDocsStatusResponse updateKYCDocStatus(UpdateKYCDocsStatusRequest request) throws Exception;

	public UpdateMerchantCommunicationNumbersResponse updateMerchantCommunicationNumbers(
			UpdateMerchantCommunicationNumbersRequest updateMerchantCommunicationNumbersRequest) throws Exception;

	public GetMerchantDetails getmerchantDisplayInfo(
			GetMerchantDetailsByMerchantIdRequest getMerchantDetailsByMerchantIdRequest) throws Exception;
	
	public GetPaytagForMerchantResponse getMerchantPayTag(GetPaytagForMerchantRequest getPaytagForMerchantRequest) throws Exception;
	
	public String getProviderMerchantId(String platformId , String merchantId ,  String appName , String token) throws ServiceException;

	public GetTerminalInfoByMerchantIdResponse getTerminalInfoForMerchant(String merchantId , String platformId , String appName , String token) 
		    throws ServiceException;
}
