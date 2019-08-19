package com.snapdeal.vanila.bulk.BulkTID;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.dto.TerminalInfoDTO;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.AddTerminalRequest;
import com.snapdeal.mob.request.DeleteTerminalRequest;
import com.snapdeal.mob.request.GetTerminalInfoByMerchantIdRequest;
import com.snapdeal.mob.request.UpdateTerminalInfoRequest;
import com.snapdeal.mob.response.AddTerminalResponse;
import com.snapdeal.mob.response.DeleteTerminalResponse;
import com.snapdeal.mob.response.GetTerminalInfoByMerchantIdResponse;
import com.snapdeal.mob.response.UpdateTerminalInfoResponse;
import com.snapdeal.vanila.bulk.FCPlusOnboard.FCPlusOnboardConstants;


@Component
@Slf4j
public class BulkTIDProcessor implements IRowProcessor{

	@Autowired
	IMerchantServices service;

	@Override
	public Object onStart(Map<String, String> map, Object sharedObject,
			Map<String, String> headerValues) {
		HashSet<CustomTID> hset = new HashSet<CustomTID>();
		CacheWrapper cacheWrapper = (CacheWrapper)sharedObject;
		cacheWrapper.setSuccess(false);
		cacheWrapper.setHset(hset);

		String merchantId = map.get(BulkTIDConstants.MERCHANT_ID);
		if(merchantId == null){
			log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + " MerchantId Not Found or Invalid!");
			cacheWrapper.setHset(hset);
			cacheWrapper.setSuccess(false);
			cacheWrapper.setError(BulkTIDConstants.MERCHANT_ID_INVALID);
			return cacheWrapper;
		}
		
		String platformId = map.get(BulkTIDConstants.PLATFORM_ID);
		if(platformId == null){
			log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + " PlatformId Not Found or Invalid!");
			cacheWrapper.setHset(hset);
			cacheWrapper.setSuccess(false);
			cacheWrapper.setError(BulkTIDConstants.PLATFORM_ID_INVALID);
			return cacheWrapper;
		}

		GetTerminalInfoByMerchantIdRequest request = new GetTerminalInfoByMerchantIdRequest();
		
		request.setAppName(headerValues.get(BulkTIDConstants.APPNAME));
		request.setToken(headerValues.get(BulkTIDConstants.TOKEN));
		request.setMerchantId(merchantId);
		request.setPlatformId(platformId);
		
		GetTerminalInfoByMerchantIdResponse response = new GetTerminalInfoByMerchantIdResponse();
		try {
			log.info("Trying to fill hash set with terminalInfoDTOs ...\n");
			response = service.getTerminalInfoForMerchant(request);
		} catch(HttpTransportException hte){
			log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "\n HttpTransportException while getTerminalInfoForMerchant for merchant id : " + merchantId + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + hte.getErrCode() + "and ErrorMessage : " + hte.getErrMsg() + "\n");
			try {
				log.info("ReTrying to fill hash set with terminalInfoDTOs ...\n");
				response = service.getTerminalInfoForMerchant(request);
			} catch(HttpTransportException hte2){
				log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "\n ON RETRY :  HttpTransportException while getTerminalInfoForMerchant for merchant id : " + merchantId + " ; FAILURE");
				log.info("\n ErrorCode : " + hte2.getErrCode() + "and ErrorMessage : " + hte2.getErrMsg() + "\n");
				cacheWrapper.setError("[" + hte2.getErrCode() + "] " + hte2.getErrMsg());
				cacheWrapper.setSuccess(false);
			} catch (ServiceException e) {
				log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "\n ON RETRY :  ServiceException while getTerminalInfoForMerchant for merchant id : " + merchantId);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				cacheWrapper.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
				cacheWrapper.setSuccess(false);
			}
		} catch (ServiceException e) {
			log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "\n ServiceException while getTerminalInfoForMerchant for merchant id : " + merchantId);
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			cacheWrapper.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
			cacheWrapper.setSuccess(false);
		}

		if(response != null){
			log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "GetTerminalInfoByMerchantIdResponse for merchant id = " + merchantId + " was found and not NULL!");
			List<TerminalInfoDTO> listTerminalInfoDTOs = response.getTerminalInfoList();
			if(listTerminalInfoDTOs != null && listTerminalInfoDTOs.size() != 0){
				log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "listTerminalInfoDTOs for merchant id = " + merchantId + " was found and not NULL or empty!");
				for(TerminalInfoDTO terminalInfoDTO : listTerminalInfoDTOs){
					CustomTID customTID =  new CustomTID();
					customTID.setPlatformId(terminalInfoDTO.getPlatformId());
					customTID.setProviderMerchantId(terminalInfoDTO.getProviderMerchantId());
					customTID.setTerminalId(terminalInfoDTO.getTerminalId());
					hset.add(customTID);
				}
				cacheWrapper.setHset(hset);
				cacheWrapper.setSuccess(true);
				cacheWrapper.setError(null);
				return cacheWrapper;
			} else {
				log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "listTerminalInfoDTOs for merchant id = " + merchantId + " found to be NULL or empty!");
				cacheWrapper.setSuccess(false);
				cacheWrapper.setError("listTerminalInfoDTOs for merchant id = " + merchantId + " found to be NULL or empty!");
			}
		} else{
			log.info(BulkTIDConstants.WHILE_CREATING_TERMINAL_ID_CACHE + "GetTerminalInfoByMerchantIdResponse for merchant id = " + merchantId + " found to be NULL!");
			cacheWrapper.setSuccess(false);
			cacheWrapper.setError("GetTerminalInfoByMerchantIdResponse for merchant id = " + merchantId + " found to be NULL!");
		}


		return cacheWrapper;
	}

	@Override
	public Object execute(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues) {


		BulkTIDResponse response =  new BulkTIDResponse();
		
		String actionType = map.get(BulkTIDConstants.ACTION_TYPE);
		if(actionType == null){
			log.info("ActionType Not Found or Invalid!");
			response.setStatus(BulkTIDConstants.FAILURE);
			response.setError(BulkTIDConstants.ACTION_TYPE_INVALID);
			return response;
		}

		String merchantId = null;
		if (actionType.equalsIgnoreCase(BulkTIDConstants.ADD_UPDATE)) {
			merchantId = map.get(BulkTIDConstants.MERCHANT_ID);
			if (merchantId == null) {
				log.info("MerchantId Not Found or Invalid!");
				response.setStatus(BulkTIDConstants.FAILURE);
				response.setError(BulkTIDConstants.MERCHANT_ID_INVALID);
				return response;
			}
		}
		String providerMerchantId = map.get(BulkTIDConstants.PROVIDER_MERCHANT_ID);
		if(providerMerchantId == null){
			log.info("Provider MerchantId Not Found or Invalid!");
			response.setStatus(BulkTIDConstants.FAILURE);
			response.setError(BulkTIDConstants.PROVIDER_MERCHANT_ID_INVALID);

			return response;
		}

		String platformId = map.get(BulkTIDConstants.PLATFORM_ID);
		if(platformId == null){
			log.info("PlatformId Not Found or Invalid!");
			response.setStatus(BulkTIDConstants.FAILURE);
			response.setError(BulkTIDConstants.PLATFORM_ID_INVALID);

			return response;
		}

		String terminalId = rowValues[0];
		String dealerId = null;
		if (actionType.equalsIgnoreCase(BulkTIDConstants.ADD_UPDATE)) {
			dealerId = rowValues[1];
		}
		
		

		switch (actionType) {
		case BulkTIDConstants.ADD_UPDATE:
			
			CacheWrapper cacheWrapper = (CacheWrapper)sharedObject;
			
			if(!cacheWrapper.isSuccess()){
				log.info("Inside processor.execute : CacheWrapper was unavailable!");
				response.setStatus(BulkTIDConstants.FAILURE);
				response.setError(BulkTIDConstants.TERMINAL_ID_CACHE_UNAVAILABLE);

				return response;
			}

			HashSet<CustomTID> hset = cacheWrapper.getHset();

			CustomTID customTID = new CustomTID();
			customTID.setPlatformId(platformId);
			customTID.setProviderMerchantId(providerMerchantId);
			customTID.setTerminalId(terminalId); 
			String addOrUpdate = null;

			if(hset.contains(customTID)){
				addOrUpdate = BulkTIDConstants.UPDATE;
			} else {
				addOrUpdate = BulkTIDConstants.ADD;
			}

			switch (addOrUpdate) {
			case BulkTIDConstants.ADD:
				AddTerminalRequest addTerminalRequest = new AddTerminalRequest();
				addTerminalRequest.setAppName(headerValues.get(BulkTIDConstants.APPNAME));
				addTerminalRequest.setToken(headerValues.get(BulkTIDConstants.TOKEN));
				addTerminalRequest.setMerchantId(merchantId);
				
				TerminalInfoDTO terminalInfoDTO = new TerminalInfoDTO();
				terminalInfoDTO.setDealerId(dealerId);
				terminalInfoDTO.setMerchantId(merchantId);
				terminalInfoDTO.setPlatformId(platformId);
				terminalInfoDTO.setProviderMerchantId(providerMerchantId);
				terminalInfoDTO.setTerminalId(terminalId);
				
				List<TerminalInfoDTO> listTerminalInfoDTOs = new ArrayList<TerminalInfoDTO>();
				listTerminalInfoDTOs.add(terminalInfoDTO);
				
				addTerminalRequest.setTerminalInfoList(listTerminalInfoDTOs);
				
				AddTerminalResponse addTerminalResponse  = new AddTerminalResponse();
				
				addTerminalResponse =  null;
				
				try {
					log.info("Trying to add terminal id = " + terminalId + " to merchantId = " + merchantId + " ...\n");
					addTerminalResponse = service.addTerminalForMerchant(addTerminalRequest);
				} catch(HttpTransportException hte){
					log.info("\n HttpTransportException while addTerminalForMerchant : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; WILL BE RETRIED...");
					log.info("\n ErrorCode : " + hte.getErrCode() + "and ErrorMessage : " + hte.getErrMsg() + "\n");
					try {
						log.info("ReTrying to add terminal id = " + terminalId + " to merchantId = " + merchantId + " ...\n");
						addTerminalResponse = service.addTerminalForMerchant(addTerminalRequest);
					} catch(HttpTransportException hte2){
						log.info("\n ON RETRY :  HttpTransportException while addTerminalForMerchant : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
						log.info("\n ErrorCode : " + hte2.getErrCode() + "and ErrorMessage : " + hte2.getErrMsg() + "\n");
						response.setStatus(BulkTIDConstants.FAILURE);
						response.setError("[" + hte2.getErrCode() + "] " + hte2.getErrMsg());
					} catch (ServiceException e) {
						log.info("\n ON RETRY :  ServiceException while addTerminalForMerchant : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
						log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
						response.setStatus(BulkTIDConstants.FAILURE);
						response.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
					}
				} catch (ServiceException e) {
					log.info("\n ServiceException while addTerminalForMerchant : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					response.setStatus(BulkTIDConstants.FAILURE);
					response.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
				}
				if(addTerminalResponse != null){
					if(addTerminalResponse.isSuccess()){
						response.setStatus(BulkTIDConstants.SUCCESS);
						response.setError(null);
						cacheWrapper.getHset().add(customTID);
					} else if(!addTerminalResponse.isSuccess()){
						response.setStatus(BulkTIDConstants.FAILURE);
						response.setError(BulkTIDConstants.FAILED_AT_OPS);
					}
				}
				return response;

			case BulkTIDConstants.UPDATE:

				UpdateTerminalInfoRequest _updateTerminalInfoRequest = new UpdateTerminalInfoRequest();
				_updateTerminalInfoRequest.setAppName(headerValues.get(BulkTIDConstants.APPNAME));
				_updateTerminalInfoRequest.setToken(headerValues.get(BulkTIDConstants.TOKEN));
				_updateTerminalInfoRequest.setMerchantId(merchantId);
				
				TerminalInfoDTO _terminalInfoDTO = new TerminalInfoDTO();
				_terminalInfoDTO.setDealerId(dealerId);
				_terminalInfoDTO.setMerchantId(merchantId);
				_terminalInfoDTO.setPlatformId(platformId);
				_terminalInfoDTO.setProviderMerchantId(providerMerchantId);
				_terminalInfoDTO.setTerminalId(terminalId);
				
				_updateTerminalInfoRequest.setTerminalInfo(_terminalInfoDTO);
				
				UpdateTerminalInfoResponse _updateTerminalInfoResponse = new UpdateTerminalInfoResponse();
				
				_updateTerminalInfoResponse = null;
				
				try {
					log.info("Trying to update terminal id = " + terminalId + " to merchantId = " + merchantId + " ...\n");
					_updateTerminalInfoResponse = service.updateTerminalInfo(_updateTerminalInfoRequest);
				} catch(HttpTransportException hte){
					log.info("\n HttpTransportException while updateTerminalInfo : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; WILL BE RETRIED...");
					log.info("\n ErrorCode : " + hte.getErrCode() + "and ErrorMessage : " + hte.getErrMsg() + "\n");
					try {
						log.info("ReTrying to update terminal id = " + terminalId + " to merchantId = " + merchantId + " ...\n");
						_updateTerminalInfoResponse = service.updateTerminalInfo(_updateTerminalInfoRequest);
					} catch(HttpTransportException hte2){
						log.info("\n ON RETRY :  HttpTransportException while updateTerminalInfo : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
						log.info("\n ErrorCode : " + hte2.getErrCode() + "and ErrorMessage : " + hte2.getErrMsg() + "\n");
						response.setStatus(BulkTIDConstants.FAILURE);
						response.setError("[" + hte2.getErrCode() + "] " + hte2.getErrMsg());
					} catch (ServiceException e) {
						log.info("\n ON RETRY :  ServiceException while updateTerminalInfo : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
						log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
						response.setStatus(BulkTIDConstants.FAILURE);
						response.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
					}
				} catch (ServiceException e) {
					log.info("\n ServiceException while updateTerminalInfo : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					response.setStatus(BulkTIDConstants.FAILURE);
					response.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
				}
				if(_updateTerminalInfoResponse != null){
					if(_updateTerminalInfoResponse.isSuccess()){
						response.setStatus(BulkTIDConstants.SUCCESS);
						response.setError(null);
					} else if(!_updateTerminalInfoResponse.isSuccess()){
						response.setStatus(BulkTIDConstants.FAILURE);
						response.setError(BulkTIDConstants.FAILED_AT_OPS);
					}
				}
				return response;
			}

		case BulkTIDConstants.DELETE:
			DeleteTerminalRequest deleteTerminalRequest = new DeleteTerminalRequest();
			deleteTerminalRequest.setAppName(headerValues.get(BulkTIDConstants.APPNAME));
			deleteTerminalRequest.setToken(headerValues.get(BulkTIDConstants.TOKEN));
			deleteTerminalRequest.setPlatformId(platformId);
			deleteTerminalRequest.setProviderMerchantId(providerMerchantId);
			deleteTerminalRequest.setTerminalId(terminalId);
			
			DeleteTerminalResponse deleteTerminalResponse = new DeleteTerminalResponse();
			
			deleteTerminalResponse = null;
			
			try {
				log.info("Trying to delete terminal id = " + terminalId + " of merchantId = " + merchantId + " ...\n");
				deleteTerminalResponse = service.deleteTerminal(deleteTerminalRequest);
			} catch(HttpTransportException hte){
				log.info("\n HttpTransportException while deleteTerminal : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; WILL BE RETRIED...");
				log.info("\n ErrorCode : " + hte.getErrCode() + "and ErrorMessage : " + hte.getErrMsg() + "\n");
				try {
					log.info("Trying to delete terminal id = " + terminalId + " of merchantId = " + merchantId + " ...\n");
					deleteTerminalResponse = service.deleteTerminal(deleteTerminalRequest);
				} catch(HttpTransportException hte2){
					log.info("\n ON RETRY :  HttpTransportException while deleteTerminal : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
					log.info("\n ErrorCode : " + hte2.getErrCode() + "and ErrorMessage : " + hte2.getErrMsg() + "\n");
					response.setStatus(BulkTIDConstants.FAILURE);
					response.setError("[" + hte2.getErrCode() + "] " + hte2.getErrMsg());
				} catch (ServiceException e) {
					log.info("\n ON RETRY :  ServiceException while deleteTerminal : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					response.setStatus(BulkTIDConstants.FAILURE);
					response.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
				}
			} catch (ServiceException e) {
				log.info("\n ServiceException while deleteTerminal : terminal id = " + terminalId + " to merchantId = " + merchantId + " ; FAILURE");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				response.setStatus(BulkTIDConstants.FAILURE);
				response.setError("[" + e.getErrCode() + "] " + e.getErrMsg());
			}
			if(deleteTerminalResponse != null){
				if(deleteTerminalResponse.isSuccess()){
					response.setStatus(BulkTIDConstants.SUCCESS);
					response.setError(null);
				} else if(!deleteTerminalResponse.isSuccess()){
					response.setStatus(BulkTIDConstants.FAILURE);
					response.setError(BulkTIDConstants.FAILED_AT_OPS);
				}
			}
			return response;
		}


		return null;
	}

	@Override
	public Set<String> columnsToIgnore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onFinish(Map<String, String> map, Object sharedObject,
			BulkFileStatus status, String fileName) {
		// TODO Auto-generated method stub

	}

}
