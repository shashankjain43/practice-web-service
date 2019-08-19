package com.snapdeal.vanila.bulk.merchanthierarchy.association.processor;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.model.BulkFrameworkResponse;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.mob.client.IDealerServices;
import com.snapdeal.mob.client.IPartnerServices;
import com.snapdeal.mob.client.IPlatformServices;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.dealer.UpdateMerchantDealerAssociationRequest;
import com.snapdeal.mob.request.partner.UpdateMerchantPartnerAssociationRequest;
import com.snapdeal.mob.request.platform.UpdateMerchantPlatformAssociationRequest;
import com.snapdeal.mob.request.platform.UpdatePartnerPlatformAssociationRequest;
import com.snapdeal.mob.response.dealer.UpdateMerchantDealerAssociationResponse;
import com.snapdeal.mob.response.partner.UpdateMerchantPartnerAssociationResponse;
import com.snapdeal.mob.response.platform.UpdateMerchantPlatformAssociationResponse;
import com.snapdeal.mob.response.platform.UpdatePartnerPlatformAssociationResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.enums.AssociationOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.model.BulkMerchantDealerAssociationResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.model.BulkMerchantPartnerAssociationResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.model.BulkMerchantPlatformAssociationResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.association.model.BulkPartnerPlatformAssociationResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BulkMerchantHierarchyAssociationProcessor implements IRowProcessor {
	
	@Autowired
	IDealerServices dealerServices;
	
	@Autowired
	@Qualifier("platformServicesImpl")
	IPlatformServices platformServices;
	
	@Autowired
	@Qualifier("partnerServicesImpl")
	IPartnerServices partnerServices;
	
	
	@Override
	public Object execute(String[] header, String[] rowValues, Map<String, String> map, long rowNum,
			Object sharedObject, Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		String token = headerValues.get("token");
		String appName = headerValues.get("appName");
	
		AssociationOperationType operationType = AssociationOperationType.valueOf(map.get("operation_type"));

		if(token == null || appName == null ){
			return new BulkFrameworkResponse("FAILED", BulkMerchantHierarchyMerchantConstants.TOKEN_OR_APPNAME_NOT_FOUND);
		}
		
		
		switch(operationType){
		case MERCHANT_DEALER:
			BulkMerchantDealerAssociationResponse bulkMerchantDealerAssociationResponse = new BulkMerchantDealerAssociationResponse();
			bulkMerchantDealerAssociationResponse = updateMerchantDealerAssociation(header, rowValues, map, rowNum, sharedObject, headerValues);
			return bulkMerchantDealerAssociationResponse;
			
		case MERCHANT_PARTNER:
			BulkMerchantPartnerAssociationResponse bulkMerchantPartnerAssociationResponse = new BulkMerchantPartnerAssociationResponse();
			bulkMerchantPartnerAssociationResponse = updateMerchantPartnerAssociation(header, rowValues, map, rowNum, sharedObject, headerValues);
			return bulkMerchantPartnerAssociationResponse;
			
		case MERCHANT_PLATFORM:
			BulkMerchantPlatformAssociationResponse bulkMerchantPlatformAssociationResponse = new BulkMerchantPlatformAssociationResponse();
			bulkMerchantPlatformAssociationResponse = updateMerchantPlatformAssociation(header, rowValues, map, rowNum, sharedObject, headerValues);
			return bulkMerchantPlatformAssociationResponse;
		
		case PARTNER_PLATFORM:
			BulkPartnerPlatformAssociationResponse bulkPartnerPlatformAssociationResponse = new BulkPartnerPlatformAssociationResponse();
			bulkPartnerPlatformAssociationResponse = updatePartnerPlatformAssociation(header, rowValues, map, rowNum, sharedObject, headerValues);
			return bulkPartnerPlatformAssociationResponse;
		}
		
		log.info("NO Operation Type Matched for the Association in Association Processor ");
		log.info("Please check whether if the specified operation type is coming from the UI ");
		return null;
		
	}

	@Override
	public Set<String> columnsToIgnore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void onFinish(Map<String, String> map, Object sharedObject, BulkFileStatus status, String fileName) {
		// TODO Auto-generated method stub
		
	}
	
	public BulkMerchantDealerAssociationResponse updateMerchantDealerAssociation(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues){
		
		UpdateMerchantDealerAssociationRequest request = new UpdateMerchantDealerAssociationRequest();
		request.setAppName(headerValues.get("appName"));
		request.setToken(headerValues.get("token"));
		
		String merchantId = rowValues[0];
		String dealerId = rowValues[1];
		
		request.setDealerId(dealerId);
		request.setMerchantId(merchantId);
		
		UpdateMerchantDealerAssociationResponse response = new UpdateMerchantDealerAssociationResponse();
		BulkMerchantDealerAssociationResponse bulkResponse = new BulkMerchantDealerAssociationResponse();
		boolean bulkUpdateSuccess = false;
		try{
			log.info("Going to Hit Merchant Dealer Association with request-- "+request.toString()+"");
			response = dealerServices.updateMerchantDealerAssociation(request);
			bulkUpdateSuccess = response.isSuccess();
			log.info("Success Marked for Merchant Dealer Association "+request.toString()+"");
		}
		catch (HttpTransportException e) {
			log.info("\n HttpTransportException while updateMerchantDealerAssociation for merchantId : " + merchantId + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			try {
				log.info("Going to Hit Merchant Dealer Association with request-- "+request.toString()+"");
				response = dealerServices.updateMerchantDealerAssociation(request);
				bulkUpdateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n HttpTransportException while updateMerchantDealerAssociation for merchantId : " + merchantId + " ;");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n HttpTransportException while updateMerchantDealerAssociation for merchantId : " + merchantId + " ;");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			}
		} catch (ServiceException e) {
			log.info("\n HttpTransportException while updateMerchantDealerAssociation for merchantId : " + merchantId + " ;");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkUpdateSuccess = false;
			bulkResponse.setError(e.getErrMsg());
		}
		
		if(bulkUpdateSuccess){
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
			bulkResponse.setError(null);
		}else{
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
		}
		
		return bulkResponse;
		
	}
	
	
	/*Calling Api for bulk Merchant - Partner Association
	 * and returing response onject to its respective case
	 *  
	 *  */
	public BulkMerchantPartnerAssociationResponse updateMerchantPartnerAssociation(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues){
		
		UpdateMerchantPartnerAssociationRequest request = new UpdateMerchantPartnerAssociationRequest();
		request.setAppName(headerValues.get("appName"));
		request.setToken(headerValues.get("token"));
		
		String merchantId = rowValues[0];
		String partnerId = rowValues[1];
		
		request.setPartnerId(partnerId);
		request.setMerchantId(merchantId);
		
		UpdateMerchantPartnerAssociationResponse response = new UpdateMerchantPartnerAssociationResponse();
		BulkMerchantPartnerAssociationResponse bulkResponse = new BulkMerchantPartnerAssociationResponse();
		boolean bulkUpdateSuccess = false;
		try{
			log.info("Going to Hit Merchant Partner Association with request-- "+request.toString()+"");
			response = partnerServices.updateMerchantPartnerAssociation(request);
			bulkUpdateSuccess = response.isSuccess();
			log.info("Success Marked for Merchant Partner Association "+request.toString()+"");
		}
		catch (HttpTransportException e) {
			log.info("\n HttpTransportException while updateMerchantPartnerAssociation for merchantId : " + merchantId + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			try {
				log.info("Going to Hit Merchant Partner Association with request-- "+request.toString()+"");
				response = partnerServices.updateMerchantPartnerAssociation(request);
				bulkUpdateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n HttpTransportException while updateMerchantDealerAssociation for merchantId : " + merchantId + " ; ");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n HttpTransportException while updateMerchantPartnerAssociation for merchantId : " + merchantId + " ; ");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			}
		} catch (ServiceException e) {
			log.info("\n HttpTransportException while updateMerchantPartnerAssociation for merchantId : " + merchantId + " ; ");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkUpdateSuccess = false;
			bulkResponse.setError(e.getErrMsg());
		}
		
		if(bulkUpdateSuccess){
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
			bulkResponse.setError(null);
		}else{
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
		}
		
		return bulkResponse;
		
	}
	
	/*Calling API for Merchant-Platform Association 
	 * Update request
	 * and returning response object to its respective case*/
	
	public BulkMerchantPlatformAssociationResponse updateMerchantPlatformAssociation(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues){
		
		UpdateMerchantPlatformAssociationRequest request = new UpdateMerchantPlatformAssociationRequest();
		request.setAppName(headerValues.get("appName"));
		request.setToken(headerValues.get("token"));
		
		String merchantId = rowValues[0];
		String platformId = rowValues[1];
		
		request.setPlatformId(platformId);
		request.setMerchantId(merchantId);
		
		UpdateMerchantPlatformAssociationResponse response = new UpdateMerchantPlatformAssociationResponse();
		BulkMerchantPlatformAssociationResponse bulkResponse = new BulkMerchantPlatformAssociationResponse();
		boolean bulkUpdateSuccess = false;
		try{
			log.info("Going to Hit Merchant Platform Association with request-- "+request.toString()+"");
			response = platformServices.updateMerchantPlatformAssociation(request);
			bulkUpdateSuccess = response.isSuccess();
			log.info("Success Marked for Merchant Platform Association "+request.toString()+"");
		}
		catch (HttpTransportException e) {
			log.info("\n HttpTransportException while updateMerchantPlatformAssociation for merchantId : " + merchantId + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			try {
				log.info("Going to Hit Merchant Platform Association with request-- "+request.toString()+"");
				response = platformServices.updateMerchantPlatformAssociation(request);
				bulkUpdateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n HttpTransportException while updateMerchantPlatformAssociation for merchantId : " + merchantId + " ; ");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n HttpTransportException while updateMerchantPlatformAssociation for merchantId : " + merchantId + " ; ");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			}
		} catch (ServiceException e) {
			log.info("\n HttpTransportException while updateMerchantPlatformAssociation for merchantId : " + merchantId + " ; ");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkUpdateSuccess = false;
			bulkResponse.setError(e.getErrMsg());
		}
		
		if(bulkUpdateSuccess){
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
			bulkResponse.setError(null);
		}else{
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
		}
		
		return bulkResponse;
		
	}
	
	
	
	/*Calling API for Partner-Platform Association 
	 * Update request
	 * and returning response object to its respective case*/
	
	public BulkPartnerPlatformAssociationResponse updatePartnerPlatformAssociation(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues){
		
		UpdatePartnerPlatformAssociationRequest request = new UpdatePartnerPlatformAssociationRequest();
		request.setAppName(headerValues.get("appName"));
		request.setToken(headerValues.get("token"));
		
		String partnerId = rowValues[0];
		String platformId = rowValues[1];
		
		request.setPlatformId(platformId);
		request.setPartnerId(partnerId);
		
		UpdatePartnerPlatformAssociationResponse response = new UpdatePartnerPlatformAssociationResponse();
		BulkPartnerPlatformAssociationResponse bulkResponse = new BulkPartnerPlatformAssociationResponse();
		boolean bulkUpdateSuccess = false;
		try{
			log.info("Going to Hit Partner Platform Association with request-- "+request.toString()+"");
			response = platformServices.updatePartnerPlatformAssociation(request);
			bulkUpdateSuccess = response.isSuccess();
			log.info("Success Marked for Partner Platform Association "+request.toString()+"");
		}
		catch (HttpTransportException e) {
			log.info("\n HttpTransportException while updatePartnerPlatformAssociation for platformId : " + platformId + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			try {
				log.info("Going to Hit Merchant Platform Association with request-- "+request.toString()+"");
				response = platformServices.updatePartnerPlatformAssociation(request);
				bulkUpdateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n HttpTransportException while updatePartnerPlatformAssociation for platformId : " + platformId + " ; ");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n HttpTransportException while updatePartnerPlatformAssociation for platformId : " + platformId + " ; ");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkResponse.setError(e2.getErrMsg());
			}
		} catch (ServiceException e) {
			log.info("\n HttpTransportException while updatePartnerPlatformAssociation for platformId : " + platformId + " ; ");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkUpdateSuccess = false;
			bulkResponse.setError(e.getErrMsg());
		}
		
		if(bulkUpdateSuccess){
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
			bulkResponse.setError(null);
		}else{
			bulkResponse.setStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
		}
		
		return bulkResponse;
		
	}

	@Override
	public Object onStart(Map<String, String> map, Object sharedObject,
			Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return null;
	}
	
	
}
