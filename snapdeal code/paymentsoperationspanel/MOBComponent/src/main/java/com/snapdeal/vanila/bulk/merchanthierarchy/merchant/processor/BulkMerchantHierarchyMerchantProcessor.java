package com.snapdeal.vanila.bulk.merchanthierarchy.merchant.processor;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import lombok.extern.slf4j.Slf4j;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.model.BulkFrameworkResponse;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.dto.BankAccountDetailsDTO;
import com.snapdeal.mob.dto.BusinessInformationDTO;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.CreateMerchantRequest;
import com.snapdeal.mob.request.GetMerchantDetailsByEmailIdRequest;
import com.snapdeal.mob.request.UpdateMerchantDetailsRequest;
import com.snapdeal.mob.response.CreateMerchantResponse;
import com.snapdeal.mob.response.GetMerchantDetails;
import com.snapdeal.mob.response.UpdateMerchantDetailsResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.model.BulkCreateMerchantResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.model.BulkOnboardMerchantResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.model.BulkUpdateMerchantDetailsResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;

@Slf4j
@Component
public class BulkMerchantHierarchyMerchantProcessor implements IRowProcessor{

	@Autowired
	IMerchantServices merchantService;

	@Override
	public Object execute(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues) {

		String token = headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN);
		String appName = headerValues.get(BulkMerchantHierarchyMerchantConstants.APP_NAME);

		String actionType = map.get(BulkMerchantHierarchyMerchantConstants.OPERATION);


		boolean onBoard =false;

		BulkOnboardMerchantResponse  bulkOnboardMerchantResponse = new BulkOnboardMerchantResponse();
		BulkCreateMerchantResponse bulkCreateMerchantResponse = new BulkCreateMerchantResponse();
		BulkUpdateMerchantDetailsResponse bulkUpdateMerchantDetailsResponse =  new BulkUpdateMerchantDetailsResponse();

		switch (actionType) {
		case BulkMerchantHierarchyMerchantConstants.ONBOARD :
			onBoard = true;
			log.info("Action = " + actionType);

			bulkCreateMerchantResponse = createMerchant(header, rowValues, map, rowNum, sharedObject, headerValues);
			if(bulkCreateMerchantResponse.getCreateStatus().equals(BulkMerchantHierarchyMerchantConstants.FAILURE)){
				log.info("CreateMerchant FAILURE ... \n \n");
				bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
				bulkUpdateMerchantDetailsResponse.setUpdateError(BulkMerchantHierarchyMerchantConstants.SKIPPED);
			} else {
				log.info("CreateMerchant SUCCESS :  merchantId = " + bulkCreateMerchantResponse.getMerchantId() +" ... \n \n");
				String merchantId = bulkCreateMerchantResponse.getMerchantId();
				bulkUpdateMerchantDetailsResponse = updateMerchantDetailsForOnboard(header, rowValues, map, rowNum, sharedObject, headerValues, onBoard, merchantId);
			}

			bulkOnboardMerchantResponse.setCreateError(bulkCreateMerchantResponse.getCreateError());
			bulkOnboardMerchantResponse.setCreateStatus(bulkCreateMerchantResponse.getCreateStatus());
			bulkOnboardMerchantResponse.setMerchantId(bulkCreateMerchantResponse.getMerchantId());

			bulkOnboardMerchantResponse.setUpdateError(bulkUpdateMerchantDetailsResponse.getUpdateError());
			bulkOnboardMerchantResponse.setUpdateStatus(bulkUpdateMerchantDetailsResponse.getUpdateStatus());
			return bulkOnboardMerchantResponse;

		case BulkMerchantHierarchyMerchantConstants.UPDATE :

			onBoard = false;
			log.info("Action = " + actionType);
			bulkUpdateMerchantDetailsResponse = updateMerchantDetailsForUpdate(header, rowValues, map, rowNum, sharedObject, headerValues, onBoard,  null);
			return bulkUpdateMerchantDetailsResponse;


		}

		return null;
	}

	public BulkCreateMerchantResponse createMerchant(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues){

		CreateMerchantRequest request = new CreateMerchantRequest();
		request.setAppName(headerValues.get(BulkMerchantHierarchyMerchantConstants.APP_NAME));
		request.setToken(headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN));


		String email = getTrueCell(rowValues[0]);
		String integrationMode = getTrueCell(rowValues[19]);
		String integrationModeSubtype = getTrueCell(rowValues[20]);

		request.setEmail(email);
		request.setIntegrationMode(integrationMode);
		request.setIntegrationModeSubtype(integrationModeSubtype);

		BulkCreateMerchantResponse bulkCreateMerchantResponse = new BulkCreateMerchantResponse();
		CreateMerchantResponse response = new CreateMerchantResponse();
		boolean bulkCreateSuccess = false;

		try {
			response = merchantService.createMerchant(request);
			bulkCreateSuccess = true;
		} catch (HttpTransportException e) {
			log.info("\n HttpTransportException while createMerchant for merchant email : " + email + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			try {
				response = merchantService.createMerchant(request);
				bulkCreateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n ON RETRY : ServiceException while createMerchant for merchant email : " + email + " ; FAILURE");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkCreateSuccess = false;
				bulkCreateMerchantResponse.setCreateError(e.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n ON RETRY :  ServiceException while createMerchant for merchant email : " + email);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkCreateSuccess = false;
				bulkCreateMerchantResponse.setCreateError(e.getErrMsg());
			}
		} catch (ServiceException e) {
			log.info("\n ServiceException while createMerchant for merchant email : " + email);
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkCreateSuccess = false;
			bulkCreateMerchantResponse.setCreateError(e.getErrMsg());
		}

		if(bulkCreateSuccess){
			log.info("bulkCreateSuccess = TRUE ...\n  \n");
			bulkCreateMerchantResponse.setCreateError(null);
			bulkCreateMerchantResponse.setCreateStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
			bulkCreateMerchantResponse.setMerchantId(response.getMerchantId());
		} else {
			log.info("bulkCreateSuccess = FALSE ...\n  \n");
			bulkCreateMerchantResponse.setCreateStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
			bulkCreateMerchantResponse.setMerchantId(null);

		}

		return bulkCreateMerchantResponse;
	}

	public BulkUpdateMerchantDetailsResponse updateMerchantDetailsForOnboard(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues, boolean onBoard, String merchantId){

		BulkUpdateMerchantDetailsResponse bulkUpdateMerchantDetailsResponse = new BulkUpdateMerchantDetailsResponse();

		String email = getTrueCell(rowValues[0]);
		GetMerchantDetailsByEmailIdRequest getMerchantDetailsByEmailIdRequest = new GetMerchantDetailsByEmailIdRequest();
		getMerchantDetailsByEmailIdRequest.setAppName(headerValues.get(BulkMerchantHierarchyMerchantConstants.APP_NAME));
		getMerchantDetailsByEmailIdRequest.setToken(headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN));
		getMerchantDetailsByEmailIdRequest.setEmail(getTrueCell(rowValues[0]));

		GetMerchantDetails getMerchantDetails = new GetMerchantDetails();
		boolean fetchMIDSuccess = true;
		if(merchantId == null){
			fetchMIDSuccess = false;
			try {		
				getMerchantDetails = merchantService.getMerchantByEmail(getMerchantDetailsByEmailIdRequest);
				fetchMIDSuccess = true;
				merchantId = getMerchantDetails.getMerchantId();
				log.info("merchantId from MOB SUCCESS , merchantId = " + merchantId + "\n \n");
			} catch (HttpTransportException e) {
				log.info("\n HttpTransportException while getMerchantByEmail for merchant email : " + email + " ; WILL BE RETRIED...");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				try {		
					getMerchantDetails = merchantService.getMerchantByEmail(getMerchantDetailsByEmailIdRequest);
					fetchMIDSuccess = true;
					merchantId = getMerchantDetails.getMerchantId();
					log.info("merchantId from MOB SUCCESS , merchantId = " + merchantId + "\n \n");
				} catch (HttpTransportException e2) {
					log.info("\n ON RETRY :  HttpTransportException while getMerchantByEmail for merchant email : " + email + " ; FAILURE...");
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					fetchMIDSuccess = false;
					log.info("merchantId from MOB FAILURE ... \n \n");
					bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
				} catch (ServiceException e2) {
					log.info("\n ON RETRY :  ServiceException while getMerchantByEmail for merchant email : " + email);
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					fetchMIDSuccess = false;
					log.info("merchantId from MOB FAILURE ... \n \n");
					bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
				}

			} catch (ServiceException e){
				log.info("\n ServiceException while getMerchantByEmail for merchant email : " + email);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				fetchMIDSuccess = false;
				log.info("merchantId from MOB FAILURE ... \n \n");
				bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
			}
		}

		if(!fetchMIDSuccess){
			bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
			return bulkUpdateMerchantDetailsResponse;
		}
		UpdateMerchantDetailsRequest request = new UpdateMerchantDetailsRequest();
		BusinessInformationDTO businessInformationDTO = new BusinessInformationDTO();
		BankAccountDetailsDTO bankAccountDetailsDTO =  new BankAccountDetailsDTO();


		request.setAppName(headerValues.get(BulkMerchantHierarchyMerchantConstants.APP_NAME));
		request.setToken(headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN));

		request.setMerchantId(merchantId);


		businessInformationDTO.setBusinessName(getTrueCell(rowValues[1]));
		businessInformationDTO.setMerchantName(getTrueCell(rowValues[2]));
		businessInformationDTO.setPrimaryMobile(getTrueCell(rowValues[3]));
		businessInformationDTO.setAddress1(getTrueCell(rowValues[4]));
		businessInformationDTO.setCity(getTrueCell(rowValues[5]));
		businessInformationDTO.setState(getTrueCell(rowValues[6]));
		businessInformationDTO.setPincode(getTrueCell(rowValues[7]));
		businessInformationDTO.setStdCode(getTrueCell(rowValues[8]));
		businessInformationDTO.setLandLineNumber(getTrueCell(rowValues[9]));
		businessInformationDTO.setBusinessCategory(getTrueCell(rowValues[10]));
		businessInformationDTO.setSubCategory(getTrueCell(rowValues[11]));
		businessInformationDTO.setBusinessType(getTrueCell(rowValues[12]));
		businessInformationDTO.setDateOfFormation(getTrueCell(rowValues[13]));
		businessInformationDTO.setTIN(getTrueCell(rowValues[14]));
		businessInformationDTO.setVelocityLimits(getTrueCell(rowValues[21]));
		businessInformationDTO.setMerchantReserves(getTrueCell(rowValues[22]));
		businessInformationDTO.setShopName(getTrueCell(rowValues[23]));
		businessInformationDTO.setCampaignText(getTrueCell(rowValues[24]));
		businessInformationDTO.setCampaignLink(getTrueCell(rowValues[25]));
		businessInformationDTO.setNotes(getTrueCell(rowValues[26]));

		List<String> settlementEmailsList = new ArrayList<String>();

		settlementEmailsList.add(getTrueCell(rowValues[27]));
		settlementEmailsList.add(getTrueCell(rowValues[28]));
		settlementEmailsList.add(getTrueCell(rowValues[29]));

		businessInformationDTO.setSettlementEmailsList(settlementEmailsList);

		businessInformationDTO.setSecondaryEmail(getTrueCell(rowValues[31]));
		businessInformationDTO.setSecondaryMobile(getTrueCell(rowValues[32]));
		businessInformationDTO.setWebsite(getTrueCell(rowValues[33]));
		businessInformationDTO.setAppName(getTrueCell(rowValues[34]));
		businessInformationDTO.setLogoUrl(getTrueCell(rowValues[35]));


		/*
		 * Adding Business Description In the Bulk Update Option
		 */

		businessInformationDTO.setBusinessDescription(getTrueCell(rowValues[36]));
		
		request.setBusinessInformationDTO(businessInformationDTO);

		/*BusinessInformationDTO SET*/

		bankAccountDetailsDTO.setAccountHolderName(getTrueCell(rowValues[15]));
		bankAccountDetailsDTO.setAccountNumber(getTrueCell(rowValues[16]));
		bankAccountDetailsDTO.setBankName(getTrueCell(rowValues[17]));
		bankAccountDetailsDTO.setIfsccode(getTrueCell(rowValues[18]));
		
		
		Integer disburseDifferencePeriod;
		if (getTrueCell(rowValues[30]) != null && !getTrueCell(rowValues[30]).isEmpty()) {
			try {
				disburseDifferencePeriod = Integer
						.parseInt(getTrueCell(rowValues[30]));
				bankAccountDetailsDTO
						.setDisburseDifferencePeriod(disburseDifferencePeriod);
			} catch (NumberFormatException e1) {
				log.info("NumberFormatException while fetching value of disburseDifferencePeriod...\n");
				log.info("Please submit a valid integer for disburseDifferencePeriod  \n");
			}
		}
		request.setBankAccountDetailsDTO(bankAccountDetailsDTO);

		/*BankAccountDetailsDTO SET*/


		UpdateMerchantDetailsResponse response = new UpdateMerchantDetailsResponse();
		boolean bulkUpdateSuccess = false;



		try {
			response = merchantService.updateMerchantDetails(request);
			bulkUpdateSuccess = true;
		} catch (HttpTransportException e) {
			log.info("\n HttpTransportException while updateMerchantDetails for merchant email : " + email + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");

			try {
				response = merchantService.updateMerchantDetails(request);
				bulkUpdateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n ON RETRY :  HttpTransportException while updateMerchantDetails for merchant email : " + email + " ; FAILURE");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n ON RETRY :  ServiceException while updateMerchantDetails for merchant email : " + email);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
			}

		} catch (ServiceException e) {
			log.info("\n ServiceException while updateMerchantDetails for merchant email : " + email);
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkUpdateSuccess = false;
			bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
		}




		if(bulkUpdateSuccess){
			log.info("bulkUpdate SUCCESS for merchant email = " + email);
			bulkUpdateMerchantDetailsResponse.setUpdateError(null);
			bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
		} else {
			log.info("bulkUpdate FAILURE for merchant email = " + email);
			bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
		}

		return bulkUpdateMerchantDetailsResponse;

	}

	public BulkUpdateMerchantDetailsResponse updateMerchantDetailsForUpdate(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues, boolean onBoard, String merchantId){

		BulkUpdateMerchantDetailsResponse bulkUpdateMerchantDetailsResponse = new BulkUpdateMerchantDetailsResponse();

		String email = getTrueCell(rowValues[0]);
		GetMerchantDetailsByEmailIdRequest getMerchantDetailsByEmailIdRequest = new GetMerchantDetailsByEmailIdRequest();
		getMerchantDetailsByEmailIdRequest.setAppName(headerValues.get(BulkMerchantHierarchyMerchantConstants.APP_NAME));
		getMerchantDetailsByEmailIdRequest.setToken(headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN));
		getMerchantDetailsByEmailIdRequest.setEmail(getTrueCell(rowValues[0]));

		GetMerchantDetails getMerchantDetails = new GetMerchantDetails();
		boolean fetchMIDSuccess = true;
		if(merchantId == null){
			fetchMIDSuccess = false;
			try {		
				getMerchantDetails = merchantService.getMerchantByEmail(getMerchantDetailsByEmailIdRequest);
				fetchMIDSuccess = true;
				merchantId = getMerchantDetails.getMerchantId();
				log.info("merchantId from MOB SUCCESS , merchantId = " + merchantId + "\n \n");;
			} catch (HttpTransportException e) {
				log.info("\n HttpTransportException while getMerchantByEmail for merchant email : " + email + " ; WILL BE RETRIED...");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				try {		
					getMerchantDetails = merchantService.getMerchantByEmail(getMerchantDetailsByEmailIdRequest);
					fetchMIDSuccess = true;
					merchantId = getMerchantDetails.getMerchantId();
					log.info("merchantId from MOB SUCCESS , merchantId = " + merchantId + "\n \n");
				} catch (HttpTransportException e2) {
					log.info("\n ON RETRY :  HttpTransportException while getMerchantByEmail for merchant email : " + email + " ; FAILURE...");
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					fetchMIDSuccess = false;
					log.info("merchantId from MOB FAILURE ... \n \n");
					bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
				} catch (ServiceException e2) {
					log.info("\n ON RETRY :  ServiceException while getMerchantByEmail for merchant email : " + email);
					log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
					fetchMIDSuccess = false;
					log.info("merchantId from MOB FAILURE ... \n \n");
					bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
				}

			} catch (ServiceException e){
				log.info("\n ServiceException while getMerchantByEmail for merchant email : " + email);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				fetchMIDSuccess = false;
				log.info("merchantId from MOB FAILURE ... \n \n");
				bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
			}
		}

		if(!fetchMIDSuccess){
			bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
			return bulkUpdateMerchantDetailsResponse;
		}
		UpdateMerchantDetailsRequest request = new UpdateMerchantDetailsRequest();
		BusinessInformationDTO businessInformationDTO = new BusinessInformationDTO();
		BankAccountDetailsDTO bankAccountDetailsDTO =  new BankAccountDetailsDTO();


		request.setAppName(headerValues.get(BulkMerchantHierarchyMerchantConstants.APP_NAME));
		request.setToken(headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN));

		request.setMerchantId(merchantId);


		businessInformationDTO.setBusinessName(getTrueCell(rowValues[1]));
		businessInformationDTO.setMerchantName(getTrueCell(rowValues[2]));
		businessInformationDTO.setPrimaryMobile(getTrueCell(rowValues[3]));
		businessInformationDTO.setAddress1(getTrueCell(rowValues[4]));
		businessInformationDTO.setCity(getTrueCell(rowValues[5]));
		businessInformationDTO.setState(getTrueCell(rowValues[6]));
		businessInformationDTO.setPincode(getTrueCell(rowValues[7]));
		businessInformationDTO.setStdCode(getTrueCell(rowValues[8]));
		businessInformationDTO.setLandLineNumber(getTrueCell(rowValues[9]));
		businessInformationDTO.setBusinessCategory(getTrueCell(rowValues[10]));
		businessInformationDTO.setSubCategory(getTrueCell(rowValues[11]));
		businessInformationDTO.setBusinessType(getTrueCell(rowValues[12]));
		businessInformationDTO.setDateOfFormation(getTrueCell(rowValues[13]));
		businessInformationDTO.setTIN(getTrueCell(rowValues[14]));

		/*Velocity Limits,Merchant Reserves & ShopName not present*/

		businessInformationDTO.setCampaignText(getTrueCell(rowValues[19]));
		businessInformationDTO.setCampaignLink(getTrueCell(rowValues[20]));
		businessInformationDTO.setNotes(getTrueCell(rowValues[21]));

		List<String> settlementEmailsList = new ArrayList<String>();

		settlementEmailsList.add(getTrueCell(rowValues[22]));
		settlementEmailsList.add(getTrueCell(rowValues[23]));
		settlementEmailsList.add(getTrueCell(rowValues[24]));

		businessInformationDTO.setSettlementEmailsList(settlementEmailsList);

		businessInformationDTO.setSecondaryEmail(getTrueCell(rowValues[26]));
		businessInformationDTO.setSecondaryMobile(getTrueCell(rowValues[27]));
		businessInformationDTO.setWebsite(getTrueCell(rowValues[28]));
		businessInformationDTO.setAppName(getTrueCell(rowValues[29]));
		businessInformationDTO.setLogoUrl(getTrueCell(rowValues[30]));
		
		/*
		 * Adding Business Description In the Bulk Update Option
		 */

		businessInformationDTO.setBusinessDescription(getTrueCell(rowValues[31]));

		request.setBusinessInformationDTO(businessInformationDTO);

		/*BusinessInformationDTO SET*/

		bankAccountDetailsDTO.setAccountHolderName(getTrueCell(rowValues[17]));
		bankAccountDetailsDTO.setAccountNumber(getTrueCell(rowValues[18]));
		bankAccountDetailsDTO.setBankName(getTrueCell(rowValues[15]));
		bankAccountDetailsDTO.setIfsccode(getTrueCell(rowValues[16]));

		Integer disburseDifferencePeriod;
		if (getTrueCell(rowValues[25]) != null && !getTrueCell(rowValues[25]).isEmpty()) {
			try {
				disburseDifferencePeriod = Integer
						.parseInt(getTrueCell(rowValues[25]));
				bankAccountDetailsDTO
				.setDisburseDifferencePeriod(disburseDifferencePeriod);
			} catch (NumberFormatException e1) {
				log.info("NumberFormatException while fetching value of disburseDifferencePeriod...\n");
				log.info("Please submit a valid integer for disburseDifferencePeriod  \n");
			}
		}
		request.setBankAccountDetailsDTO(bankAccountDetailsDTO);

		/*BankAccountDetailsDTO SET*/


		UpdateMerchantDetailsResponse response = new UpdateMerchantDetailsResponse();
		boolean bulkUpdateSuccess = false;



		try {
			response = merchantService.updateMerchantDetails(request);
			bulkUpdateSuccess = true;
		} catch (HttpTransportException e) {
			log.info("\n HttpTransportException while updateMerchantDetails for merchant email : " + email + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");

			try {
				response = merchantService.updateMerchantDetails(request);
				bulkUpdateSuccess = true;
			} catch (HttpTransportException e2) {
				log.info("\n ON RETRY :  ServiceException while updateMerchantDetails for merchant email : " + email + " ; FAILURE");
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
			} catch (ServiceException e2) {
				log.info("\n ON RETRY :  ServiceException while updateMerchantDetails for merchant email : " + email);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				bulkUpdateSuccess = false;
				bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
			}

		} catch (ServiceException e) {
			log.info("\n ServiceException while updateMerchantDetails for merchant email : " + email);
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			bulkUpdateSuccess = false;
			bulkUpdateMerchantDetailsResponse.setUpdateError(e.getErrMsg());
		}




		if(bulkUpdateSuccess){
			log.info("bulkUpdate SUCCESS for merchant email = " + email);
			bulkUpdateMerchantDetailsResponse.setUpdateError(null);
			bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.SUCCESS);
		} else {
			log.info("bulkUpdate FAILURE for merchant email = " + email);
			bulkUpdateMerchantDetailsResponse.setUpdateStatus(BulkMerchantHierarchyMerchantConstants.FAILURE);
		}

		return bulkUpdateMerchantDetailsResponse;

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

	public String getTrueCell(String cellValue){
		if(cellValue.trim().equals("")){
			return null;
		}
		return cellValue.trim();
	}

	@Override
	public Object onStart(Map<String, String> map, Object sharedObject,
			Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
