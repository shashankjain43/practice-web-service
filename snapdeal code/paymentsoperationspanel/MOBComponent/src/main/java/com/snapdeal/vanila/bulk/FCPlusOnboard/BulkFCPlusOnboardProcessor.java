package com.snapdeal.vanila.bulk.FCPlusOnboard;

import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.dto.BankAccountDetailsDTO;
import com.snapdeal.mob.dto.BusinessInformationDTO;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.CreateUpdateMicroMerchantRequest;
import com.snapdeal.mob.response.CreateUpdateMicroMerchantResponse;
import com.snapdeal.vanila.bulkFOS.BulkFOSRowProcessor;

@Component
@Slf4j
public class BulkFCPlusOnboardProcessor implements IRowProcessor{
	
	@Autowired
	IMerchantServices service;
	
	@Autowired
	IUserServiceClient iUserService;
	
	@Override
	public Object execute(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues) {
		
		String email = rowValues[0];
		FCPlusOnboardResponse fcPlusOnboardResponse = new FCPlusOnboardResponse();
		String imsId = null;
		
		GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
		getUserByEmailRequest.setEmailId(email);
		GetUserResponse getUserResponseByEmail = new GetUserResponse();
		try {
			getUserResponseByEmail = iUserService.getUserByEmail(getUserByEmailRequest);
		} catch (com.snapdeal.ims.exception.ServiceException e) {
			log.info("IMS Exception : For Email : " + rowValues[0] + " , the exception is : " + e.getErrMsg());
			log.info("Retrying to get merchant ID from IMS ...\n\n");
			try {
				getUserResponseByEmail = iUserService.getUserByEmail(getUserByEmailRequest);
			} catch(com.snapdeal.ims.exception.ServiceException e2) {
				log.info("IMS Exception on retry : For Email : " + rowValues[0] + " , the exception is : " + e.getErrMsg());
				fcPlusOnboardResponse.setOnboardError("[" + e2.getErrCode() + "]  " + e2.getErrMsg());
				fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.FAILURE);
				return fcPlusOnboardResponse;
			} catch (Exception otherEx) {
				log.info("Exception on retry: For Email : " + rowValues[0] + " , the exception is : " + otherEx.getMessage() + otherEx.getStackTrace());
				fcPlusOnboardResponse.setOnboardError(otherEx.getMessage());
				fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.FAILURE);
				return fcPlusOnboardResponse;
			}
		} catch (Exception e) {
			log.info("Exception : For Email : " + rowValues[0] + " , the exception is : " + e.getMessage() + e.getStackTrace());
			fcPlusOnboardResponse.setOnboardError(e.getMessage());
			fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.FAILURE);
			return fcPlusOnboardResponse;
		}
		if(getUserResponseByEmail != null){
			UserDetailsDTO userDetailsDTO =  getUserResponseByEmail.getUserDetails();
			if(userDetailsDTO != null){
				imsId = userDetailsDTO.getUserId();
			}
		}
		
		
		
		CreateUpdateMicroMerchantRequest request = new CreateUpdateMicroMerchantRequest();
		CreateUpdateMicroMerchantResponse response = new CreateUpdateMicroMerchantResponse();
		boolean onboardSuccess = false;
		
		int size = rowValues.length;
		for(int i=0; i<size; i++){
			rowValues[i] = rowValues[i].trim();
			if(rowValues[i].equals("")){
				rowValues[i] = null;
			}
		}
		
		BusinessInformationDTO businessInformationDTO = new BusinessInformationDTO();
		BankAccountDetailsDTO  bankAccountDetailsDTO = new BankAccountDetailsDTO();
		
		request.setAppName(headerValues.get(FCPlusOnboardConstants.APPNAME));
		request.setToken(headerValues.get(FCPlusOnboardConstants.TOKEN));
		request.setImsId(imsId);
		request.setEmailId(rowValues[0]);
		
		request.setIntegrationMode(FCPlusOnboardConstants.FC_PLUS);
		
		
		businessInformationDTO.setShopName(rowValues[1]);
		businessInformationDTO.setMerchantName(rowValues[2]);
		businessInformationDTO.setAddress1(rowValues[3]);
		businessInformationDTO.setCity(rowValues[4]);
		businessInformationDTO.setState(rowValues[5]);
		businessInformationDTO.setPincode(rowValues[6]);
		businessInformationDTO.setPrimaryMobile(rowValues[7]);
		businessInformationDTO.setBusinessCategory(rowValues[8]);
		
		request.setBusinessInformationDTO(businessInformationDTO);
		
									/*businessInformationDTO SET*/
		
		bankAccountDetailsDTO.setAccountHolderName(rowValues[9]);
		bankAccountDetailsDTO.setAccountNumber(rowValues[10]);
		bankAccountDetailsDTO.setBankName(rowValues[11]);
		bankAccountDetailsDTO.setIfsccode(rowValues[12]);
		
		request.setBankDetailsDTO(bankAccountDetailsDTO);
		
									/*bankAccountDetailsDTO SET*/
		
		request.setPaytag(rowValues[13]);
		
		float latitude = Float.NaN;
		try {
			latitude = Float.parseFloat(rowValues[14]);
		} catch (NumberFormatException e) {
			log.info("Latitude value is invalid");
		}
		
		float longitude = Float.NaN;
		try {
			longitude = Float.parseFloat(rowValues[15]);
		} catch (NumberFormatException e) {
			log.info("Longitude value is invalid");
		}
		
		request.setLatitude(latitude);
		request.setLongitude(longitude);
		
								/*CreateUpdateMicroMerchantRequest SET*/
		
		
		
		try {
			response = service.createUpdateMicroMerchant(request);
			onboardSuccess = true;
		} catch(HttpTransportException hte){
			log.info("\n HttpTransportException while createUpdateMicroMerchant for merchant email : " + email + " ; WILL BE RETRIED...");
			log.info("\n ErrorCode : " + hte.getErrCode() + "and ErrorMessage : " + hte.getErrMsg() + "\n");
			try {
				response = service.createUpdateMicroMerchant(request);
				onboardSuccess = true;
			} catch(HttpTransportException hte2){
				log.info("\n ON RETRY :  HttpTransportException while createUpdateMicroMerchant for merchant email : " + email + " ; FAILURE");
				log.info("\n ErrorCode : " + hte2.getErrCode() + "and ErrorMessage : " + hte2.getErrMsg() + "\n");
				fcPlusOnboardResponse.setOnboardError("[" + hte2.getErrCode() + "]  " + hte2.getErrMsg());
				fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.FAILURE);
				onboardSuccess = false;
			} catch (ServiceException e) {
				log.info("\n ON RETRY :  ServiceException while createUpdateMicroMerchant for merchant email : " + email);
				log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
				fcPlusOnboardResponse.setOnboardError("[" + e.getErrCode() + "]  " + e.getErrMsg());
				fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.FAILURE);
				onboardSuccess = false;
			}
		} catch (ServiceException e) {
			log.info("\n ServiceException while createUpdateMicroMerchant for merchant email : " + email);
			log.info("\n ErrorCode : " + e.getErrCode() + "and ErrorMessage : " + e.getErrMsg() + "\n");
			fcPlusOnboardResponse.setOnboardError("[" + e.getErrCode() + "]  " + e.getErrMsg());
			fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.FAILURE);
			onboardSuccess = false;
		}
		
		if(onboardSuccess == true){
			fcPlusOnboardResponse.setOnboardError(null);
			fcPlusOnboardResponse.setOnboardStatus(FCPlusOnboardConstants.SUCCESS);
		}
		
		return fcPlusOnboardResponse;
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

	@Override
	public Object onStart(Map<String, String> map, Object sharedObject,
			Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
