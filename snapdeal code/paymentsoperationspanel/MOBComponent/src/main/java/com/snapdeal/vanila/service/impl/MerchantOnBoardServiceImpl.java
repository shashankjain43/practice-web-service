package com.snapdeal.vanila.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.dto.POSMerchantDetailsDTO;
import com.snapdeal.mob.dto.TerminalInfoDTO;
import com.snapdeal.mob.exception.HttpTransportException;
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
import com.snapdeal.mob.request.GetPOSMerchantInfoByMerchantIdRequest;
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
import com.snapdeal.mob.response.GetPOSMerchantInfoByMerchantIdResponse;
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
import com.snapdeal.payments.aggregator.api.IPaymentAggregatorService;
import com.snapdeal.payments.aggregator.request.RefundRequest;
import com.snapdeal.payments.aggregator.response.RefundResponse;
import com.snapdeal.payments.view.commons.exception.PaymentsViewServiceException;
import com.snapdeal.payments.view.merchant.commons.dto.MVTransactionDTO;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchRequest;
import com.snapdeal.payments.view.merchant.commons.request.GetMerchantViewSearchWithFilterRequest;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewFilters;
import com.snapdeal.payments.view.merchant.commons.request.MerchantViewSearch;
import com.snapdeal.payments.view.merchant.commons.response.GetTransactionsResponse;
import com.snapdeal.payments.view.merchant.commons.service.IMerchantViewService;
import com.snapdeal.vanila.dto.MPTransactionDTO;
import com.snapdeal.vanila.error.ErrorConstants;
import com.snapdeal.vanila.exception.MerchantException;
import com.snapdeal.vanila.request.MerchantGetFilterTransactionRequest;
import com.snapdeal.vanila.request.MerchantGetSearchTransactionRequest;
import com.snapdeal.vanila.request.MerchantProfileRequest;
import com.snapdeal.vanila.request.MerchantRefundAmountRequest;
import com.snapdeal.vanila.response.MerchantGetTransactionResponse;
import com.snapdeal.vanila.response.MerchantRefundAmountResponse;
import com.snapdeal.vanila.service.MerchantOnBoardService;
import com.snapdeal.vanila.utils.AppConstants;
import com.snapdeal.vanila.utils.MVMapper;
import com.snapdeal.vanila.utils.SymbolTable;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;
import scala.annotation.meta.getter;

@Slf4j
@Service
public class MerchantOnBoardServiceImpl implements MerchantOnBoardService {

	@Autowired
	IMerchantServices service;

	@Autowired
	IMerchantViewService mvClient;

	@Autowired
	IPaymentAggregatorService aggClient;

	@Override
	public CreateMerchantResponse createMerchantByEmail(CreateMerchantRequest request) throws Exception {

		CreateMerchantResponse response = new CreateMerchantResponse();
		response = service.createMerchant(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public GetMerchantDetails getMerchantById(GetMerchantDetailsByMerchantIdRequest request) throws Exception {
		GetMerchantDetails response = new GetMerchantDetails();
		response = service.getMerchantDetailsByMerchantId(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public UpdateMerchantDetailsResponse updateMerchantDetail(UpdateMerchantDetailsRequest request) throws Exception {

		UpdateMerchantDetailsResponse response = new UpdateMerchantDetailsResponse();
		response = service.updateMerchantDetails(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public GetMerchantDetails getMerchantDetailsByEmail(GetMerchantDetailsByEmailIdRequest request) throws Exception {
		GetMerchantDetails response = new GetMerchantDetails();
		response = service.getMerchantByEmail(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public GetMerchantDetailsByBusinessCategoryResponse getMerchantDetailsByBusinessCategory(
			GetMerchantDetailsByBusinessCategoryRequest request) throws Exception {
		GetMerchantDetailsByBusinessCategoryResponse response = new GetMerchantDetailsByBusinessCategoryResponse();
		response = service.getMerchantByBusinessCategory(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public GetAllMerchantsResponse getAllMerchant(GetAllMerchantsRequest request) throws Exception {
		GetAllMerchantsResponse response = service.getAllMerchants(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public UploadDocumentsResponse uploadDocument(UploadDocumentsRequest request) throws Exception {
		request.getDocumentDTO().setDocIdentityValue(null);
		UploadDocumentsResponse response = service.uploadDocuments(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public GetUIDataResponse getUIDataByType(GetUIDataRequest request) throws Exception {
		GetUIDataResponse response = service.getAllUIData(request);
		System.out.println(request);
		System.out.println(response);
		return response;
	}

	@Override
	public GetDocumentsResponse getDocumentPresignedUrl(GetDocumentsRequest request) throws Exception {
		GetDocumentsResponse response = service.getDocumentUrl(request);
		return response;
	}

	@Override
	public UpdateMerchantStatusResponse updateMerchantStatus(UpdateMerchantStatusRequest request) throws Exception {
		UpdateMerchantStatusResponse response = service.updateMerchantStatus(request);
		return response;
	}

	@Override
	public UpdatePGDetailResponse updatePGDetail(UpdatePGDetailRequest request) throws Exception {
		return service.updatePGDetail(request);
	}

	@Override
	public UpdatePGPreferenceResponse updatePGPrefrence(UpdatePGPreferenceRequest request) throws Exception {

		return service.updatePgPreference(request);
	}

	@Override
	public GetPGPreferenceByMerchantIdResponse getPGPrefrence(GetPGPreferenceByMerchantIdRequest request)
			throws Exception {

		return service.getPgPreference(request);
	}

	@Override
	public UpdateKYCStatusResponse updateKycStatus(UpdateKYCStatusRequest request) throws Exception {

		return service.updateKycStatus(request);
	}

	@Override
	public UpdateBankStatusResponse updateBankDetails(UpdateBankStatusRequest request) throws Exception {

		return service.updateBankStatus(request);
	}

	@Override
	public GetMerchantByCriteriaResponse getMerchantsOnCriteria(GetMerchantsByCriteriaRequest request)
			throws Exception {

		return service.getMerchantsByCriteria(request);
	}

	@Override
	public MerchantGetTransactionResponse getTxnsOfMerchant(MerchantGetFilterTransactionRequest request)
			throws MerchantException {

		MerchantGetTransactionResponse response = new MerchantGetTransactionResponse();

		GetTransactionsResponse mvResponse = getTxnsOfMerchantUtil(request);

		List<MVTransactionDTO> mvTxnList = mvResponse.getMvTransactions();

		List<MPTransactionDTO> mpTxnList = new ArrayList<MPTransactionDTO>();

		if (mvTxnList != null) {
			for (MVTransactionDTO mvTransactionDTO : mvTxnList) {
				MPTransactionDTO mpTxnDTO = (MPTransactionDTO) MVMapper.MVToMPTxnMapping(mvTransactionDTO);
				mpTxnList.add(mpTxnDTO);

			}
		}

		response.setMpTransactions(mpTxnList);

		return response;
	}

	public GetTransactionsResponse getTxnsOfMerchantUtil(MerchantGetFilterTransactionRequest request)
			throws PaymentsViewServiceException, MerchantException {

		GetTransactionsResponse mvResponse;

		GetMerchantViewSearchWithFilterRequest mvRequest = new GetMerchantViewSearchWithFilterRequest();

		mvRequest.setMerchantId(request.getMerchantId());

		mvRequest.setToken(request.getToken());

		MerchantViewFilters mvFilter = new MerchantViewFilters();

		try {
			mvFilter = (MerchantViewFilters) MVMapper.MPToMVFilterMapping(request.getFilters());
		} catch (MerchantException e) {
			log.error("Exception From MPToMVFilterMapping ", e);
			throw e;
		}

		MerchantViewSearch mvSearch = (MerchantViewSearch) MVMapper.MPToMVSearchMapping(request.getSearchCriteria());

		mvRequest.setSearchCriteria(mvSearch);
		mvRequest.setFilters(mvFilter);
		mvRequest.setLimit(request.getLimit());
		mvRequest.setPage(request.getPage());
		mvRequest.setOrderby(request.getOrderby());

		try {

			mvResponse = mvClient.getMerchantViewSearchWithFilter(mvRequest);

		} /*
			 * catch (HttpTransportException httpException) { log.error(
			 * "Getting HTTPTransport Exception from MView: {}", httpException);
			 * throw new
			 * MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
			 * ErrorConstants.GENERIC_INTERNAL_SERVER_MSG); }
			 */ catch (PaymentsViewServiceException e) {
			log.error("Exception From MView  While getting txn : {} ", e);
			throw new MerchantException(e.getMessage());
		}

		return mvResponse;
	}

	@Override
	public MerchantGetTransactionResponse getTxnsOfMerchantBySearch(MerchantGetSearchTransactionRequest request)
			throws MerchantException {

		GetTransactionsResponse mvResponse = getTxnsOfMerchantBySearchUtil(request);

		MerchantGetTransactionResponse mpResponse = new MerchantGetTransactionResponse();

		List<MVTransactionDTO> mvTxnList = mvResponse.getMvTransactions();

		List<MPTransactionDTO> mpTxnList = new ArrayList<MPTransactionDTO>();

		if (mvTxnList != null) {
			for (MVTransactionDTO mvTransactionDTO : mvTxnList) {
				try {
					MPTransactionDTO mpTxnDTO = (MPTransactionDTO) MVMapper.MVToMPTxnMapping(mvTransactionDTO);
					mpTxnList.add(mpTxnDTO);
				} catch (MerchantException e) {
					log.error("Exception From MVToMPTxnMapping ", e);
					throw e;
				}

			}
		}

		mpResponse.setMpTransactions(mpTxnList);

		return mpResponse;
	}

	public GetTransactionsResponse getTxnsOfMerchantBySearchUtil(MerchantGetSearchTransactionRequest request)
			throws MerchantException {

		GetMerchantViewSearchRequest mvRequest = new GetMerchantViewSearchRequest();

		mvRequest.setMerchantId(request.getMerchantId());
		mvRequest.setToken(request.getToken());

		mvRequest.setOrderby(request.getOrderby());

		MerchantViewSearch mvSearch = (MerchantViewSearch) MVMapper.MPToMVSearchMapping(request.getSearchCriteria());

		mvRequest.setSearchCriteria(mvSearch);
		mvRequest.setLimit(request.getLimit());
		mvRequest.setPage(request.getPage());

		GetTransactionsResponse response = null;

		try {
			response = mvClient.getMerchantViewSearch(mvRequest);
		} /*
			 * catch (HttpTransportException httpException) { log.error(
			 * "Getting HTTPTransport Exception from MView: {}", httpException);
			 * throw new
			 * MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
			 * ErrorConstants.GENERIC_INTERNAL_SERVER_MSG); }
			 */ catch (PaymentsViewServiceException e) {
			log.error("Exception from MView  while searching Txn: {}", e);
			throw new MerchantException(e.getErrCode(), e.getMessage());
		}

		return response;

	}

	@Override
	public MerchantRefundAmountResponse refundMoney(MerchantRefundAmountRequest request) throws MerchantException {
		MerchantProfileRequest profileRequest = new MerchantProfileRequest();
		profileRequest.setMerchantId(request.getMerchantId());
		profileRequest.setToken(request.getToken());

		onlineRefundCheck(profileRequest);
		// validate merchant type. if offline then deny such request
		// if(mobMerchantDetails.get)

		refundMoneyUtil(request);
		MerchantRefundAmountResponse response = new MerchantRefundAmountResponse();
		response.setStatus(true);
		return response;
	}

	private void onlineRefundCheck(MerchantProfileRequest profileRequest) throws MerchantException {

		GetMerchantDetailsByMerchantIdRequest getMerchantDetailsRequest = new GetMerchantDetailsByMerchantIdRequest();

		getMerchantDetailsRequest.setMerchantId(profileRequest.getMerchantId());
		getMerchantDetailsRequest.setToken(profileRequest.getToken());

		GetMerchantDetails merchantDetails = new GetMerchantDetails();
		try {

			merchantDetails = service.getMerchantDetailsByMerchantId(getMerchantDetailsRequest);
		} catch (HttpTransportException httpException) {
			log.error("Getting HTTPTransport Exception from MOB: {}", httpException);
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_CODE,
					ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		} catch (ServiceException e) {
			log.error("Exception from MOB Client while profile view: {}", e);
			throw new MerchantException(e.getErrCode(), e.getErrMsg());
		}

		String intMode = merchantDetails.getIntegrationMode();

		if (!AppConstants.onlineIntegration.equalsIgnoreCase(intMode)) {
			throw new MerchantException("", ErrorConstants.ONLY_ONLINE_REFUND_ALLOWED_MSG);
		}
	}

	public RefundResponse refundMoneyUtil(MerchantRefundAmountRequest request) throws MerchantException {
		RefundResponse aggResponse = null;

		SimpleDateFormat formatter = new SimpleDateFormat("yyMMddHHmm");
		String stringDate = formatter.format(new Date());

		log.info("going to generate reference id");
		StringBuilder idemId = new StringBuilder();
		idemId.append(stringDate).append(UUID.randomUUID().toString().substring(0, 3));

		Random random = new Random();

		try {
			Integer firstRandomIndex = random.nextInt(36);
			Integer secondRandomIndex = random.nextInt(36);
			idemId.append(SymbolTable.getSymbol(firstRandomIndex));
			idemId.append(SymbolTable.getSymbol(secondRandomIndex));
		} catch (Exception e) {
			log.error(e.getMessage());
			throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
		}

		log.info("generated reference id {}", idemId.toString());

		RefundRequest aggRequest = new RefundRequest();
		aggRequest.setMerchantId(request.getMerchantId());
		aggRequest.setReferenceId(idemId.toString());
		aggRequest.setSourceSystem(AppConstants.refundSourceSystem);
		aggRequest.setRefundAmount(request.getAmount());
		aggRequest.setMerchantTxnId(request.getOrderId());
		aggRequest.setComments(request.getComments());
		aggRequest.setFeeReversalCode(request.getFeeReversalCode());
		//Setting PLatform Id implemented after Merchant Hierarchy
		aggRequest.setPlatformId(request.getPlatformId());
		int i = -1;
		int maxRetry = 3;
		/* int maxRetry = config.getApiRetryCount(); */
		while (i < maxRetry) {
			try {
				log.info("initiating refund");
				aggResponse = aggClient.refundPayment(aggRequest);
				return aggResponse;
			} catch (com.snapdeal.payments.aggregator.exception.client.HttpTransportException he) {
				log.error("Client exception {} pass {}", he, i + 2);
				if (i + 1 == maxRetry)
					throw new MerchantException(ErrorConstants.GENERIC_INTERNAL_SERVER_MSG);
			} catch (com.snapdeal.payments.aggregator.exception.client.ServiceException se) {
				log.error("Error while refunding transaction {} : {}  error {}", request.getOrderId(),
						se.getErrorMessage(), se);
				throw new MerchantException(se.getErrors().get(0).getMessage());
			}

			i++;
			try {
				Thread.sleep(3000);
			} catch (InterruptedException ie) {
				log.info("suppressed thread interruption. will continue to retry");
			}
		}

		return aggResponse;
	}
	
	/*
	 * Getting Provider merchant Id for corresponding merchant Id and platform Id
	 */
	@Override
	public String getProviderMerchantId(String platformId , String merchantId ,  String appName , String token) throws ServiceException {
		GetPOSMerchantInfoByMerchantIdRequest getPOSMerchantInfoByMerchantIdRequest = new GetPOSMerchantInfoByMerchantIdRequest();

		GetPOSMerchantInfoByMerchantIdResponse response = new GetPOSMerchantInfoByMerchantIdResponse();
		getPOSMerchantInfoByMerchantIdRequest.setMerchantId(merchantId);
		getPOSMerchantInfoByMerchantIdRequest.setAppName(appName);
		getPOSMerchantInfoByMerchantIdRequest.setToken(token);
		try{
		response = service.getPOSMerchantInfoByMerchantId(getPOSMerchantInfoByMerchantIdRequest);
		}catch(ServiceException e){
			log.info("Exception occured while getting POS Merchant Info ::"+ ExceptionUtils.getFullStackTrace(e));
			throw new ServiceException("Not Able to get POS Merchant Info : Reason ::" + e.getErrMsg(), e.getErrCode());
		}
		for(POSMerchantDetailsDTO dto : response.getPosMerchantDetailsList()){
			if(dto.getPlatformId()!=null){
				if(dto.getPlatformId().equalsIgnoreCase(platformId)){
					return dto.getProviderMerchantId();
				}
			}
		}
		return null;
	}

	
	@Override
	public CreateUpdateMicroMerchantResponse updateMicroMerchantDetails(
			CreateUpdateMicroMerchantRequest createUpdateMicroMerchantRequest)
					throws MerchantException, ServiceException {
		return service.createUpdateMicroMerchant(createUpdateMicroMerchantRequest);
	}

	@Override
	public GetKYCDocCategoriesByBusinessTypeResponse getKYCDocCategoriesByBusinessType(
			GetKYCCategoriesByBusinessTypeRequest request) throws Exception {
		return service.getKycDocCategoriesByBusinessType(request);
	}

	@Override
	public UpdateKYCDocsStatusResponse updateKYCDocStatus(UpdateKYCDocsStatusRequest request) throws Exception {
		return service.updateKYCDocsStatus(request);
	}

	@Override
	public UpdateMerchantCommunicationNumbersResponse updateMerchantCommunicationNumbers(
			UpdateMerchantCommunicationNumbersRequest updateMerchantCommunicationNumbersRequest) throws Exception {
		return service.updateMerchantCommunicationInfo(updateMerchantCommunicationNumbersRequest);
	}

	@Override
	public GetMerchantDetails getmerchantDisplayInfo(
			GetMerchantDetailsByMerchantIdRequest getMerchantDetailsByMerchantIdRequest) throws Exception {
		return service.getMerchantDisplayInfo(getMerchantDetailsByMerchantIdRequest);
	}

	@Override
	public GetPaytagForMerchantResponse getMerchantPayTag(GetPaytagForMerchantRequest getPaytagForMerchantRequest)
			throws Exception {
		return service.getPaytagForMerchant(getPaytagForMerchantRequest);
	}

	
	/*
	 * Getting Terminal Id and Dealer Id Corresponding to a Merchant ID and platform Id combination
	 * 	 */
	
	@Override
	public GetTerminalInfoByMerchantIdResponse getTerminalInfoForMerchant(
			String merchantId, String platformId , String appName , String token) throws ServiceException {
		// TODO Auto-generated method stub
		
		GetTerminalInfoByMerchantIdRequest getTerminalInfoByMerchantIdRequest = new GetTerminalInfoByMerchantIdRequest();
		getTerminalInfoByMerchantIdRequest.setAppName(appName);
		getTerminalInfoByMerchantIdRequest.setToken(token);
		getTerminalInfoByMerchantIdRequest.setMerchantId(merchantId);
		getTerminalInfoByMerchantIdRequest.setPlatformId(platformId);
		log.info("Going to get Terminal Info For request :" + getTerminalInfoByMerchantIdRequest.toString());
		try{
		return service.getTerminalInfoForMerchant(getTerminalInfoByMerchantIdRequest);
		} catch(ServiceException e){
			log.info("Exception occured while getting Terminal Info ::"+ ExceptionUtils.getFullStackTrace(e));
			throw new ServiceException("Not Able to get Terminal Info : Reason ::" + e.getErrMsg(), e.getErrCode());
		}
		
	}

}
