package com.snapdeal.vanila.service.impl;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import java.util.List;


import javax.servlet.http.HttpServletRequest;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.csv.request.LIstToCSVRequest;
import com.snapdeal.opspanel.csv.service.CSVServices;
import com.snapdeal.opspanel.rms.service.TokenService;
import com.snapdeal.vanila.dao.MOBOperationsDao;
import com.snapdeal.vanila.dto.MerchantCallHistoryEntity;
import com.snapdeal.vanila.enums.OPSErrorEnum;
import com.snapdeal.vanila.request.GetAttemptsRequest;
import com.snapdeal.vanila.request.GetMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.InsertMerchantCallHistoryRequest;
import com.snapdeal.vanila.request.MerchantPointOfContactRequest;
import com.snapdeal.vanila.response.MerchantPointOfContactResponse;
import com.snapdeal.vanila.service.MerchantOpsService;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Service("MOBOpsService")
public class MerchantOpsServiceImpl implements MerchantOpsService {

	@Autowired
	MOBOperationsDao mobOperationsDao;
	
	@Autowired
	private HttpServletRequest servletRequest;
	
	@Autowired
	private TokenService tokenService;

	@Autowired
	private CSVServices csvServices;

	@Override
	public StringBuffer downloadMerchantContactHistory(
			GetMerchantCallHistoryRequest getMerchantCallHistoryRequest) throws OpsPanelException {
		try {
			
			List<MerchantCallHistoryEntity> merchantCallHistoryEntityList = mobOperationsDao.getMerchantCallHistory(getMerchantCallHistoryRequest);
			
			LIstToCSVRequest request = new LIstToCSVRequest();
			List<Object> list = new ArrayList<Object>();
			
			for(MerchantCallHistoryEntity merchantCallHistoryEntity: merchantCallHistoryEntityList){
				list.add(merchantCallHistoryEntity);
			}
			
			request.setObjects(list);
			request.setClassName(MerchantCallHistoryEntity.class);
						
			return csvServices.getListToCSV(request, ",");

		} catch (Exception e) {

			throw new OpsPanelException(OPSErrorEnum.DB_ERROR_MERCHANT_OPS_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public List<MerchantCallHistoryEntity> getMerchantContactHistory(
			GetMerchantCallHistoryRequest getMerchantCallHistoryRequest) throws OpsPanelException {
		try {

			return mobOperationsDao.getMerchantCallHistory(getMerchantCallHistoryRequest);
		} catch (Exception e) {

			throw new OpsPanelException(OPSErrorEnum.DB_ERROR_MERCHANT_OPS_DATA.getErrorCode(), e.getMessage());
		}
	}

	@Override
	public void insertMerchantContactHistory(InsertMerchantCallHistoryRequest insertMerchantCallHistoryRequest) throws OpsPanelException {
		String callDate = insertMerchantCallHistoryRequest.getCallDate();
		try{
			SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
			formatter.setLenient(false);
			formatter.parse(callDate);
			
		}catch(java.text.ParseException e){
			throw new OpsPanelException("MH-1006", "Date is not in valid format . Please Check");
		}
		boolean countCheck = checkCountBeforeInsertion(insertMerchantCallHistoryRequest);
		if(!countCheck){
			throw new OpsPanelException("MH-1006", "Attempts Count is not correct . Please try Again.");
		}
		try{
			MerchantCallHistoryEntity entity = new MerchantCallHistoryEntity();
			String token = servletRequest.getHeader("token");
			String emailId = tokenService.getEmailFromToken(token);
			entity.setCreatedBy(emailId);
			entity.setAttemptsCount(insertMerchantCallHistoryRequest.getAttemptsCount());
			entity.setMerchantId(insertMerchantCallHistoryRequest.getMerchantId().trim());
			entity.setDescription(insertMerchantCallHistoryRequest.getDescription());
			entity.setCallpath(insertMerchantCallHistoryRequest.getCallpath());
			entity.setCallStatus(insertMerchantCallHistoryRequest.getCallStatus());
			entity.setContactType(insertMerchantCallHistoryRequest.getContactType());
			entity.setCallTime(callDate);
			log.info("Going to insert history in to database with request : "+ entity.toString());
			mobOperationsDao.insertMerchantCallHistory(entity);
		}catch(DuplicateKeyException ex){
			log.info("Integrity Constraint Exception because of the primary key" + ExceptionUtils.getFullStackTrace(ex) );
			throw new OpsPanelException("MCH-1003", "You  can not submit same attempt count again for the same Contact Type" );
		}
		catch(Exception e){
			log.info("Exception Occured while inserting contact history in the database" + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException(OPSErrorEnum.DB_ERROR_MERCHANT_OPS_DATA.getErrorCode(), e.getMessage());
		}
		
		
		
	}

	@Override
	public List<MerchantCallHistoryEntity> getCallsHistoryByMerchantId(String merchantId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	 public int getAttemptsCountForMerchantAndCallType(GetAttemptsRequest getAttemptsRequest) throws OpsPanelException{
		try{
			int count = mobOperationsDao.getAttemptsForMerchantandCallType(getAttemptsRequest);
			count = count +1;
			return count;
		}catch(Exception e){
			log.info("Exception While Getting Attempt count for Merchant and call type" + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("MHC-1003", "Not able to Get Attempts Counts . REASON:"+e.getMessage()+" . Please Try again.");
		}
	}
	
	public boolean checkCountBeforeInsertion(InsertMerchantCallHistoryRequest insertMerchantCallHistoryRequest) throws OpsPanelException{
		try{
			GetAttemptsRequest getAttemptsRequest = new GetAttemptsRequest();
			getAttemptsRequest.setMerchantId(insertMerchantCallHistoryRequest.getMerchantId().trim());
			getAttemptsRequest.setCallType(insertMerchantCallHistoryRequest.getContactType());
			int actualCount = getAttemptsCountForMerchantAndCallType(getAttemptsRequest);
			if(actualCount == insertMerchantCallHistoryRequest.getAttemptsCount()){
				return true;
			}
			return false;
		}catch(Exception e){
			log.info("Exception While checking count before insertion" + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("MCH-1005", "Error in getting Count From database.REASON: "+e.getMessage()+" Please try again after some time.");
		}
	}
	
	@Override
	public void insertMerchantPointOfContact(MerchantPointOfContactRequest merchantPointOfContactRequest) throws OpsPanelException{
		try{
			mobOperationsDao.insertMerchantPointOfContact(merchantPointOfContactRequest);
		}catch(Exception e){
			log.info("Exception While Getting Attempt count for Merchant and call type" + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("MHC-1003", "Not able to Get Attempts Counts . REASON:"+e.getMessage()+" . Please Try again.");
		}
	}
	
	@Override
	public List<MerchantPointOfContactResponse> getMerchantPointOfContact(MerchantPointOfContactRequest merchantPointOfContactRequest) throws OpsPanelException{
		try{
			List<MerchantPointOfContactResponse> merchantPointOfContactList = mobOperationsDao.getMerchantPointOfContact(merchantPointOfContactRequest);
			return merchantPointOfContactList;
		}catch(Exception e){
			log.info("Exception While Getting Attempt count for Merchant and call type" + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("MHC-1003", "Not able to Get Attempts Counts . REASON:"+e.getMessage()+" . Please Try again.");
		}
	}
	
	@Override
	public void updateMerchantPointOfContact(MerchantPointOfContactRequest merchantPointOfContactRequest) throws OpsPanelException{
		try{
			mobOperationsDao.updateMerchantPointOfContact(merchantPointOfContactRequest);
		}catch(Exception e){
			log.info("Exception While Getting Attempt count for Merchant and call type" + ExceptionUtils.getFullStackTrace(e));
			throw new OpsPanelException("MHC-1003", "Not able to Get Attempts Counts . REASON:"+e.getMessage()+" . Please Try again.");
		}
	}
}
