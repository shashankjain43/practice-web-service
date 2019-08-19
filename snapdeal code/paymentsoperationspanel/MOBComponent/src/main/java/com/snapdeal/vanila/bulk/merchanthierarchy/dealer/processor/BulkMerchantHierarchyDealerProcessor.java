package com.snapdeal.vanila.bulk.merchanthierarchy.dealer.processor;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.mob.client.IDealerServices;
import com.snapdeal.mob.dto.DealerBasicInfoDTO;
import com.snapdeal.mob.dto.DealerMetaInfoDTO;
import com.snapdeal.mob.exception.HttpTransportException;
import com.snapdeal.mob.exception.ServiceException;
import com.snapdeal.mob.request.dealer.CreateDealerRequest;
import com.snapdeal.mob.request.dealer.UpdateDealerDetailsRequest;
import com.snapdeal.mob.response.dealer.CreateDealerResponse;
import com.snapdeal.mob.response.dealer.UpdateDealerDetailsResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.dealer.enums.DealerOperationType;
import com.snapdeal.vanila.bulk.merchanthierarchy.dealer.model.OPSCreateUpdateDealerResponse;
import com.snapdeal.vanila.bulk.merchanthierarchy.dealer.model.OPSUpdateDealerResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class BulkMerchantHierarchyDealerProcessor implements IRowProcessor {

	@Autowired
	IDealerServices dealerServices;

	private final String SUCCESS = "SUCCESS";
	private final String FAILURE = "FAILURE";
	private final String SKIPPED = "SKIPPED";

	@Override
	public Object execute(String[] header, String[] rowValues, Map<String, String> map, long rowNum,
			Object sharedObject, Map<String, String> headerValues) {

		if(rowValues.length>=1) {
			for(int i=0; i<rowValues.length;i++) {
				if (rowValues[i].equalsIgnoreCase("")) {
					rowValues[i]= null;
				}
			}
		}

		DealerOperationType opType = DealerOperationType.valueOf(map.get("operation_type"));
		UpdateDealerDetailsRequest updateRequest = new UpdateDealerDetailsRequest();
		UpdateDealerDetailsResponse updateResponse = null;
		switch (opType) {
		case ONBOARD: {
			CreateDealerRequest createRequest = new CreateDealerRequest();

			populateCreateRequest(rowValues, createRequest,headerValues);
			populateUpdateRequest(rowValues, updateRequest,headerValues);
			CreateDealerResponse createResponse = null;

			OPSCreateUpdateDealerResponse fileResponse = new OPSCreateUpdateDealerResponse();
			try {
				createResponse = createDealer(createRequest);
				updateRequest.setDealerId(createResponse.getDealerId());
				updateResponse = updateDealer(updateRequest);
			} catch (Exception se) {
				recordAndLogErrors(fileResponse, createResponse, updateResponse, se);
			} finally {
				populateFileResponse(fileResponse, createResponse, updateResponse);
			}
			return fileResponse;
		}
		case UPDATE: {
			OPSUpdateDealerResponse fileResponse = new OPSUpdateDealerResponse();
			try {

				updateRequest.setDealerId(rowValues[0]);
				populateUpdateRequest(rowValues, updateRequest, headerValues);
				updateResponse = updateDealer(updateRequest);
			} catch (Exception se) {
				recordAndLogErrors(fileResponse, se);
			} finally {
				populateFileResponse(fileResponse, updateResponse);
			}
			return fileResponse;
		}
		}
		return null;
	}

	private void recordAndLogErrors(OPSUpdateDealerResponse fileResponse, Exception se) {
		fileResponse.setUpdateError(getErrorMsgFrmException(se));
		fileResponse.setUpdateStatus(FAILURE);
		log.info("Error while updating dealer :" +getErrorMsgFrmException(se) + "\n");
	}

	private void populateFileResponse(OPSUpdateDealerResponse fileResponse,
			UpdateDealerDetailsResponse updateResponse) {
		if (updateResponse != null) {
			fileResponse.setUpdateStatus(SUCCESS);
		}

	}

	private void recordAndLogErrors(OPSCreateUpdateDealerResponse fileResponse, CreateDealerResponse createResponse,
			UpdateDealerDetailsResponse updateResponse, Exception se) {
		fileResponse.setOverallStatus(FAILURE);

		if (createResponse == null) {
			fileResponse.setCreateError(getErrorMsgFrmException(se));
			fileResponse.setCreateStatus(FAILURE);
			fileResponse.setUpdateStatus(SKIPPED);
			log.info("Error while creating dealer :" + getErrorMsgFrmException(se) + "\n");
		} else if (updateResponse == null) {
			fileResponse.setUpdateError(getErrorMsgFrmException(se));
			fileResponse.setDealerId(createResponse.getDealerId());
			fileResponse.setUpdateStatus(FAILURE);
			fileResponse.setCreateStatus(SUCCESS);
			log.info("Error while updating dealer :" + getErrorMsgFrmException(se) + "\n");
		}

	}

	private String getErrorMsgFrmException(Exception e){
		if(e instanceof ServiceException) {
			ServiceException se=(ServiceException)e;
			return se.getErrCode() + " : " + se.getErrMsg();

		} else {
			return "Some Error occured please retry  Msg:"+e.getMessage();
		}

	}

	private void populateFileResponse(OPSCreateUpdateDealerResponse fileResponse, CreateDealerResponse createResponse,
			UpdateDealerDetailsResponse updateResponse) {
		if (createResponse != null && updateResponse != null) {
			fileResponse.setDealerId(createResponse.getDealerId());
			fileResponse.setCreateStatus(SUCCESS);
			fileResponse.setUpdateStatus(SUCCESS);
			fileResponse.setOverallStatus(SUCCESS);
		}

	}

	private void populateUpdateRequest(String[] rowValues, UpdateDealerDetailsRequest updateRequest, Map<String, String> headerValues) {
		DealerBasicInfoDTO detailsBasicInfoDTO = new DealerBasicInfoDTO();
		DealerMetaInfoDTO dealerMetaInfoDTO = new DealerMetaInfoDTO();

		detailsBasicInfoDTO.setPrimaryMobileNumber(rowValues[1]);
		dealerMetaInfoDTO.setAddress1(rowValues[2]);
		dealerMetaInfoDTO.setAddress2(rowValues[3]);
		dealerMetaInfoDTO.setCity(rowValues[4]);
		dealerMetaInfoDTO.setState(rowValues[5]);
		dealerMetaInfoDTO.setPincode(rowValues[6]);
		dealerMetaInfoDTO.setStdCode(rowValues[7]);
		detailsBasicInfoDTO.setBizName(rowValues[8]);
		detailsBasicInfoDTO.setPrimaryEmail(rowValues[10]);
		dealerMetaInfoDTO.setStoreId(rowValues[11]);
		dealerMetaInfoDTO.setGeoLocation(rowValues[12]);
		dealerMetaInfoDTO.setLogoURL(rowValues[13]);
		dealerMetaInfoDTO.setCampaignLink(rowValues[14]);
		dealerMetaInfoDTO.setCampaignText(rowValues[15]);
		dealerMetaInfoDTO.setAggregatorMId(rowValues[16]);

		updateRequest.setDealerBasicInfoDTO(detailsBasicInfoDTO);
		updateRequest.setDealerMetaInfoDTO(dealerMetaInfoDTO);
		updateRequest.setToken(headerValues.get("token"));
		updateRequest.setAppName(headerValues.get("appName"));
	}

	private void populateCreateRequest(String[] rowValues, CreateDealerRequest createRequest, Map<String, String> headerValues) {
		createRequest.setDealerName(rowValues[0]);
		createRequest.setToken(headerValues.get("token"));
		createRequest.setAppName(headerValues.get("appName"));
	}

	private CreateDealerResponse createDealer(CreateDealerRequest request) throws ServiceException {
		CreateDealerResponse response = null;
		try {
			response = dealerServices.createDealer(request);
		} catch (HttpTransportException transportExc) {
			response = dealerServices.createDealer(request);
		}
		return response;
	}

	private UpdateDealerDetailsResponse updateDealer(UpdateDealerDetailsRequest updateRequest) throws ServiceException {
		UpdateDealerDetailsResponse response = null;
		try {
			response = dealerServices.updateDealerDetails(updateRequest);
		} catch (HttpTransportException transportExc) {
			response = dealerServices.updateDealerDetails(updateRequest);
		}
		return response;
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

	@Override
	public Object onStart(Map<String, String> map, Object sharedObject,
			Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return null;
	}

}
