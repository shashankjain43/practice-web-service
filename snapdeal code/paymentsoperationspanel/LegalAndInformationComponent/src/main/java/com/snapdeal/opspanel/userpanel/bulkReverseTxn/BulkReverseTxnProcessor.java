package com.snapdeal.opspanel.userpanel.bulkReverseTxn;

import java.util.List;
import java.util.Map;
import java.util.Set;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.opspanel.userpanel.bulkReverseTxn.response.BulkReverseLoadMoneyResponse;
import com.snapdeal.opspanel.userpanel.bulkReverseTxn.response.BulkReverseRefundTransactionResponse;
import com.snapdeal.opspanel.userpanel.bulkReverseTxn.response.BulkReverseTransactionResponse;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalClientException;
import com.snapdeal.payments.sdmoney.exceptions.InternalServerException;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseLoadMoneyResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseRefundTransactionResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionResponse;
import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;

@Component
@Slf4j
public class BulkReverseTxnProcessor implements IRowProcessor{
	
	@Autowired
	SDMoneyClient sdMoney;

	@Override
	public Object execute(String[] header, String[] rowValues,
			Map<String, String> map, long rowNum, Object sharedObject,
			Map<String, String> headerValues) {
		
		String typeOfReverseTxn = map.get("reverseType");
		
		String transactionReference = rowValues[0];
		
		String sdIdentity = rowValues[1];
		
		String reverseReason = rowValues[2];
		
		boolean successfulTransactionId = true;
		
		GetTransactionsByReferenceRequest getTransactionsByReferenceRequest = new GetTransactionsByReferenceRequest();
		getTransactionsByReferenceRequest.setSdIdentity(sdIdentity);
		getTransactionsByReferenceRequest.setTransactionReference(transactionReference);
		
		GetTransactionsByReferenceResponse getTransactionsByReferenceResponse = new GetTransactionsByReferenceResponse();
		try {
			getTransactionsByReferenceResponse = sdMoney.getTransactionByReference(getTransactionsByReferenceRequest);
			
			
		} catch (InternalClientException | InternalServerException e) {
			log.info("InternalClientException | InternalServerException : while getting transaction for " + transactionReference + " : Will be retried...");
			log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
			
			try {
				getTransactionsByReferenceResponse = sdMoney.getTransactionByReference(getTransactionsByReferenceRequest);
			} catch (InternalClientException | InternalServerException e2) {
				log.info("InternalClientException | InternalServerException on retry : while getting transaction for " + transactionReference + " : FAILURE \n");
				log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
				successfulTransactionId = false;

			} catch (SDMoneyException e2) {
				log.info("SDMoneyException on retry : while getting transaction for " + transactionReference + "\n");
				log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
				successfulTransactionId = false;
			} catch (Exception e2) {
				log.info("Other Exception on retry : while getting transaction for " + transactionReference + "\n");
				log.info(", Error Message : " + e2.getMessage());
				successfulTransactionId = false;
			}			
		} catch (SDMoneyException e) {
			log.info("SDMoneyException : while getting transaction for " + transactionReference + "\n");
			log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
			successfulTransactionId = false;
		} catch (Exception e) {
			log.info("Other Exception : while getting transaction for " + transactionReference + "\n");
			log.info(", Error Message : " + e.getMessage());
			successfulTransactionId = false;
		}
		
		if(successfulTransactionId == false){
			if(typeOfReverseTxn.equals(BulkReverseTxnConstants.REVERSE_LOAD_MONEY)){
				BulkReverseLoadMoneyResponse response = new BulkReverseLoadMoneyResponse();
				ReverseLoadMoneyResponse reverseLoadMoneyResponse = new ReverseLoadMoneyResponse();
				response.setStatus(BulkReverseTxnConstants.FAILURE);
				response.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
				return response;
			} else if(typeOfReverseTxn.equals(BulkReverseTxnConstants.REVERSE_REFUND_TRANSACTION)){
				BulkReverseRefundTransactionResponse response= new BulkReverseRefundTransactionResponse();
				ReverseRefundTransactionResponse reverseRefundTransactionResponse = new ReverseRefundTransactionResponse();
				response.setStatus(BulkReverseTxnConstants.FAILURE);
				response.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
				return response;
			} else if(typeOfReverseTxn.equals(BulkReverseTxnConstants.REVERSE_TRANSACTION)){
				BulkReverseTransactionResponse response = new BulkReverseTransactionResponse();
				ReverseTransactionResponse reverseTransactionResponse = new ReverseTransactionResponse();
				response.setStatus(BulkReverseTxnConstants.FAILURE);
				response.setReverseTransactionResponse(reverseTransactionResponse);
				return response;
			} 
		}
		
		String transactionId = null;
		if(getTransactionsByReferenceResponse != null){
			List<TransactionSummary> transactionSummaries = getTransactionsByReferenceResponse.getListTransaction();
			if(transactionSummaries != null && transactionSummaries.size()>0){
				transactionId = transactionSummaries.get(0).getTransactionId();
			}
		}
		
		
		
		switch (typeOfReverseTxn) {
		case BulkReverseTxnConstants.REVERSE_LOAD_MONEY :
			ReverseLoadMoneyRequest reverseLoadMoneyRequest  = new ReverseLoadMoneyRequest();
			reverseLoadMoneyRequest.setPrevTransactionId(transactionId);
			reverseLoadMoneyRequest.setReverseReason(reverseReason);
			
			ReverseLoadMoneyResponse reverseLoadMoneyResponse = new ReverseLoadMoneyResponse();
			BulkReverseLoadMoneyResponse bulkReverseLoadMoneyResponse = new BulkReverseLoadMoneyResponse();
			
			try {
				reverseLoadMoneyResponse = sdMoney.reverseLoadMoney(reverseLoadMoneyRequest);
				bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.SUCCESS);
				bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
				return bulkReverseLoadMoneyResponse;
			} catch (InternalClientException | InternalServerException e) {
				log.info("InternalClientException | InternalServerException : while doing reverseLoadMoney for transactionId :" + transactionId + " : Will be retried \n");
				log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
				try {
					reverseLoadMoneyResponse = sdMoney.reverseLoadMoney(reverseLoadMoneyRequest);
					bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.SUCCESS);
					bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
					return bulkReverseLoadMoneyResponse;
				} catch (InternalClientException | InternalServerException e2) {
					log.info("InternalClientException | InternalServerException on retry : while doing reverseLoadMoney for transactionId :" + transactionId + " : FAILURE \n");
					log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
					bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
					return bulkReverseLoadMoneyResponse;
				} catch (SDMoneyException e2) {
					log.info("SDMoneyException on retry : while doing reverseLoadMoney for transactionId :" + transactionId + "\n");
					log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
					bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
					return bulkReverseLoadMoneyResponse;
				} catch(Exception e2){
					log.info("Other Exception on retry : while doing reverseLoadMoney for transactionId :" + transactionId + "\n");
					log.info("Error Message : " + e2.getMessage());
					bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
					return bulkReverseLoadMoneyResponse;
				}
			} catch (SDMoneyException e) {
				log.info("SDMoneyException : while doing reverseLoadMoney for transactionId :" + transactionId + "\n");
				log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
				bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.FAILURE);
				bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
				return bulkReverseLoadMoneyResponse;

			} catch (Exception e) {
				log.info("Other Exception : while doing reverseLoadMoney for transactionId :" + transactionId + "\n");
				log.info("Error Message : " + e.getMessage());
				bulkReverseLoadMoneyResponse.setStatus(BulkReverseTxnConstants.FAILURE);
				bulkReverseLoadMoneyResponse.setReverseLoadMoneyResponse(reverseLoadMoneyResponse);
				return bulkReverseLoadMoneyResponse;
			}
			
		case BulkReverseTxnConstants.REVERSE_REFUND_TRANSACTION :
			ReverseRefundTransactionRequest reverseRefundTransactionRequest = new ReverseRefundTransactionRequest();
			reverseRefundTransactionRequest.setPrevTransactionId(transactionId);
			
			ReverseRefundTransactionResponse reverseRefundTransactionResponse = new ReverseRefundTransactionResponse();
			BulkReverseRefundTransactionResponse bulkReverseRefundTransactionResponse = new BulkReverseRefundTransactionResponse();
			try {
				reverseRefundTransactionResponse = sdMoney.reverseRefundTransaction(reverseRefundTransactionRequest);
				bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.SUCCESS);
				bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
				return bulkReverseRefundTransactionResponse;
			} catch (InternalClientException | InternalServerException e) {
				log.info("InternalClientException | InternalServerException : while doing reverseRefundTransaction for transactionId :" + transactionId + " : Will be retried \n");
				log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
				try {
					reverseRefundTransactionResponse = sdMoney.reverseRefundTransaction(reverseRefundTransactionRequest);
					bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.SUCCESS);
					bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
					return bulkReverseRefundTransactionResponse;
				} catch (InternalClientException | InternalServerException e2) {
					log.info("InternalClientException | InternalServerException on retry : while doing reverseRefundTransaction for transactionId :" + transactionId + " : FAILURE \n");
					log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
					bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
					return bulkReverseRefundTransactionResponse;
				} catch (SDMoneyException e2) {
					log.info("SDMoneyException on retry : while doing reverseRefundTransaction for transactionId :" + transactionId + "\n");
					log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
					bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
					return bulkReverseRefundTransactionResponse;
				} catch(Exception e2){
					log.info("Other Exception on retry : while doing reverseRefundTransaction for transactionId :" + transactionId + "\n");
					log.info("Error Message : " + e2.getMessage());
					bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
					return bulkReverseRefundTransactionResponse;
				}
			} catch (SDMoneyException e) {
				log.info("SDMoneyException : while doing reverseRefundTransaction for transactionId :" + transactionId + "\n");
				log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
				bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
				bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
				return bulkReverseRefundTransactionResponse;
			} catch (Exception e) {
				log.info("Other Exception : while doing reverseRefundTransaction for transactionId :" + transactionId + "\n");
				log.info("Error Message : " + e.getMessage());
				bulkReverseRefundTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
				bulkReverseRefundTransactionResponse.setReverseRefundTransactionResponse(reverseRefundTransactionResponse);
				return bulkReverseRefundTransactionResponse;
			}
			
			
		case BulkReverseTxnConstants.REVERSE_TRANSACTION :
			
			ReverseTransactionRequest reverseTransactionRequest = new ReverseTransactionRequest();
			reverseTransactionRequest.setPrevTransactionId(transactionId);
			
			ReverseTransactionResponse reverseTransactionResponse = new ReverseTransactionResponse();
			BulkReverseTransactionResponse bulkReverseTransactionResponse = new BulkReverseTransactionResponse();
			try {
				reverseTransactionResponse = sdMoney.reverseTransaction(reverseTransactionRequest);
				bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.SUCCESS);
				bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
				return bulkReverseTransactionResponse;
			} catch (InternalClientException | InternalServerException e) {
				log.info("InternalClientException | InternalServerException : while doing reverseTransaction for transactionId :" + transactionId + " : Will be retried \n");
				log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
				try {
					reverseTransactionResponse = sdMoney.reverseTransaction(reverseTransactionRequest);
					bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.SUCCESS);
					bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
					return bulkReverseTransactionResponse;
				} catch (InternalClientException | InternalServerException e2) {
					log.info("InternalClientException | InternalServerException on retry : while doing reverseTransaction for transactionId :" + transactionId + " : FAILURE \n");
					log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
					bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
					return bulkReverseTransactionResponse;
				} catch (SDMoneyException e2) {
					log.info("SDMoneyException on retry : while doing reverseTransaction for transactionId :" + transactionId + "\n");
					log.info("Error Code : " + e2.getErrorCode().toString() + ", Error Message : " + e2.getMessage());
					bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
					return bulkReverseTransactionResponse;
				} catch(Exception e2){
					log.info("Other Exception on retry : while doing reverseTransaction for transactionId :" + transactionId + "\n");
					log.info("Error Message : " + e2.getMessage());
					bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
					bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
					return bulkReverseTransactionResponse;
				}
			} catch (SDMoneyException e) {
				log.info("SDMoneyException : while doing reverseTransaction for transactionId :" + transactionId + "\n");
				log.info("Error Code : " + e.getErrorCode().toString() + ", Error Message : " + e.getMessage());
				bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
				bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
				return bulkReverseTransactionResponse;
			} catch (Exception e) {
				log.info("Other Exception : while doing reverseTransaction for transactionId :" + transactionId + "\n");
				log.info("Error Message : " + e.getMessage());
				bulkReverseTransactionResponse.setStatus(BulkReverseTxnConstants.FAILURE);
				bulkReverseTransactionResponse.setReverseTransactionResponse(reverseTransactionResponse);
				return bulkReverseTransactionResponse;
			}
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

	@Override
	public Object onStart(Map<String, String> paramMap1, Object paramObject,
			Map<String, String> paramMap2) {
		// TODO Auto-generated method stub
		return null;
	}

}
