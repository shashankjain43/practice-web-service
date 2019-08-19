package com.snapdeal.vanila.bulkFOS;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.mob.client.IMerchantServices;
import com.snapdeal.mob.dto.SourceOfAcquisitionDTO;
import com.snapdeal.mob.enums.SourceOfAcquisition;
import com.snapdeal.mob.request.UpdatesourceOfAcquisitionRequest;
import com.snapdeal.mob.response.UpdatesourceOfAcquisitionResponse;

@Component
@Slf4j
public class BulkFOSRowProcessor implements IRowProcessor{

	@Autowired
	IMerchantServices service;

	@Autowired
	IUserServiceClient iUserService;

	@Override
	public Object execute(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,Map<String, String> headerValues) {

		UpdatesourceOfAcquisitionRequest request = new UpdatesourceOfAcquisitionRequest();

		UpdateFOSResponse fosResponse = new UpdateFOSResponse();
		
		rowValues[0] =   rowValues[0].trim();

		request.setToken(headerValues.get(BulkFOSConstants.TOKEN));
		request.setAppName(headerValues.get(BulkFOSConstants.APPNAME));

		SourceOfAcquisitionDTO soa = new SourceOfAcquisitionDTO();

		List<SourceOfAcquisitionDTO> list = new ArrayList<SourceOfAcquisitionDTO>();
		String merchantId = null;
		String fos = map.get(BulkFOSConstants.ACQUISITION_TYPE);

		String fosValue = null;
		for(SourceOfAcquisition sourceOfAcquisition : SourceOfAcquisition.values()){
			if(sourceOfAcquisition.toString().equals(fos)){
				fosValue = sourceOfAcquisition.getSourceOfAcquisition();
				break;
			}
		}
		
		if(fosValue == null){
			log.info("Unable to get FOS Value for the given Acquisition Type ...\n");
			fosResponse.setError(BulkFOSConstants.INVALID_FOS);
			fosResponse.setStatus(BulkFOSConstants.FAILURE);
			return fosResponse;
		}
		String idType = map.get(BulkFOSConstants.ID_TYPE);

		switch(idType) {
		case BulkFOSConstants.MOBILE :
			log.info("File with mobile numbers ... \n");
			GetUserByMobileRequest getUserByMobileRequest = new GetUserByMobileRequest();
			getUserByMobileRequest.setMobileNumber(rowValues[0]);
			GetUserResponse getUserResponseByMobile = new GetUserResponse();
			try {
				getUserResponseByMobile = iUserService.getUserByVerifiedMobile(getUserByMobileRequest);
				log.info("Response of getUserResponseByMobile : " + getUserResponseByMobile.toString() + "\n");
			} catch (ServiceException e) {
				log.info("IMS Exception : For Mobile : " + rowValues[0] + " , the exception is : " + e.getErrMsg());
				log.info("Retrying to get merchant ID from IMS ...\n\n");
				try {
					getUserResponseByMobile = iUserService.getUserByVerifiedMobile(getUserByMobileRequest);
					log.info("Response of getUserResponseByMobile : " + getUserResponseByMobile.toString() + "\n");
				} catch(ServiceException e2) {
					log.info("IMS Exception on retry : For Mobile : " + rowValues[0] + " , the exception is : " + e.getErrMsg());
					fosResponse.setError("[" + e2.getErrCode() + "]  " + e2.getErrMsg());
					fosResponse.setStatus(BulkFOSConstants.FAILURE);
					return fosResponse;
				} catch (Exception otherEx) {
					log.info("Exception : For Mobile : " + rowValues[0] + " , the exception is : " + otherEx.getMessage() + otherEx.getStackTrace());
					fosResponse.setError(otherEx.getMessage());
					fosResponse.setStatus(BulkFOSConstants.FAILURE);
					return fosResponse;
				}
			} catch (Exception e) {
				log.info("Exception : For Mobile : " + rowValues[0] + " , the exception is : " + e.getMessage() + e.getStackTrace());
				fosResponse.setError(e.getMessage());
				fosResponse.setStatus(BulkFOSConstants.FAILURE);
				return fosResponse;
			}
			if(getUserResponseByMobile != null){
				UserDetailsDTO userDetailsDTO =  getUserResponseByMobile.getUserDetails();
				if(userDetailsDTO != null){
					merchantId = userDetailsDTO.getUserId();
				} else {
					log.info("userDetailsDTO for " + rowValues[0] + " is NULL ... \n");
				}
			} else {
				log.info("getUserResponseByMobile for " + rowValues[0] + "is NULL ... \n");
			}
			break;

		case BulkFOSConstants.EMAIL :
			log.info("File with email IDs ... \n");
			GetUserByEmailRequest getUserByEmailRequest = new GetUserByEmailRequest();
			getUserByEmailRequest.setEmailId(rowValues[0]);
			GetUserResponse getUserResponseByEmail = new GetUserResponse();
			try {
				getUserResponseByEmail = iUserService.getUserByEmail(getUserByEmailRequest);
				log.info("Response of getUserResponseByEmail : " + getUserResponseByEmail.toString() + "\n");
			} catch (ServiceException e) {
				log.info("IMS Exception : For Email : " + rowValues[0] + " , the exception is : " + e.getErrMsg());
				log.info("Retrying to get merchant ID from IMS ...\n\n");
				try {
					getUserResponseByEmail = iUserService.getUserByEmail(getUserByEmailRequest);
					log.info("Response of getUserResponseByEmail : " + getUserResponseByEmail.toString() + "\n");
				} catch(ServiceException e2) {
					log.info("IMS Exception on retry : For Email : " + rowValues[0] + " , the exception is : " + e.getErrMsg());
					fosResponse.setError("[" + e2.getErrCode() + "]  " + e2.getErrMsg());
					fosResponse.setStatus(BulkFOSConstants.FAILURE);
					return fosResponse;
				} catch (Exception otherEx) {
					log.info("Exception : For Email : " + rowValues[0] + " , the exception is : " + otherEx.getMessage() + otherEx.getStackTrace());
					fosResponse.setError(otherEx.getMessage());
					fosResponse.setStatus(BulkFOSConstants.FAILURE);
					return fosResponse;
				}
			} catch (Exception e) {
				log.info("Exception : For Email : " + rowValues[0] + " , the exception is : " + e.getMessage() + e.getStackTrace());
				fosResponse.setError(e.getMessage());
				fosResponse.setStatus(BulkFOSConstants.FAILURE);
				return fosResponse;
			}
			if(getUserResponseByEmail != null){
				UserDetailsDTO userDetailsDTO =  getUserResponseByEmail.getUserDetails();
				if(userDetailsDTO != null){
					merchantId = userDetailsDTO.getUserId();
				} else {
					log.info("userDetailsDTO for " + rowValues[0] + " is NULL ... \n");
				}
			} else {
				log.info("getUserResponseByEmail for " + rowValues[0] + "is NULL ... \n");
			}
		}

		soa.setMerchantId(merchantId);
		soa.setSourceOfAcquisition(fosValue);

		list.add(soa);

		request.setSourceOfAcquisitionList(list);

		UpdatesourceOfAcquisitionResponse response = new UpdatesourceOfAcquisitionResponse();

		try {
			response = service.updateSourceOfAcquisition(request);
			log.info("Response of updateSourceOfAcquisition : " + response.toString());
		} catch (com.snapdeal.mob.exception.HttpTransportException e) {

			log.info("MOB HttpTransportException while updating FOS of merchant : " + rowValues[0] + e.getErrMsg() + "\n\n\n\n\n");
			log.info("Retrying to update FOS ...\n\n");
			try {
				response = service.updateSourceOfAcquisition(request);
				log.info("Response of updateSourceOfAcquisition : " + response.toString());
			} catch (com.snapdeal.mob.exception.HttpTransportException e2) {

				log.info("MOB HttpTransportException while retrying to update FOS of merchant : " + rowValues[0] + e.getErrMsg() + "\n\n\n\n\n");
				fosResponse.setError("[" + e2.getErrCode() + "]  " + e2.getErrMsg());
				fosResponse.setStatus(BulkFOSConstants.FAILURE);
			} catch ( com.snapdeal.mob.exception.ServiceException e2) {
				log.info("ServiceException while updating FOS of merchant : " + rowValues[0] + "\n\n\n\n\n");
				fosResponse.setError("[" + e2.getErrCode() + "]  " + e2.getErrMsg());
				fosResponse.setStatus(BulkFOSConstants.FAILURE);
			}

		} catch (com.snapdeal.mob.exception.ServiceException e) {
			log.info("ServiceException while updating FOS of merchant : " + rowValues[0] + e.getMessage() +  "\n\n\n\n\n");
			fosResponse.setError("[" + e.getErrCode() + "]  " + e.getErrMsg());
			fosResponse.setStatus(BulkFOSConstants.FAILURE);
		}

		if(response.isSuccess()){
			log.info("FOS Update Success for " + rowValues[0]);
			fosResponse.setStatus(BulkFOSConstants.SUCCESS);
			fosResponse.setError(null);
		} else {
			log.info("FOS Update Failure for " + rowValues[0]);
			fosResponse.setStatus(BulkFOSConstants.FAILURE);
		}
		return fosResponse;

	}

	@Override
	public Set<String> columnsToIgnore() {
		return null;
	}

	@Override
	public void onFinish(Map<String, String> map, Object sharedObject,
			BulkFileStatus status, String fileName) {

	}

	@Override
	public Object onStart(Map<String, String> map, Object sharedObject,
			Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
