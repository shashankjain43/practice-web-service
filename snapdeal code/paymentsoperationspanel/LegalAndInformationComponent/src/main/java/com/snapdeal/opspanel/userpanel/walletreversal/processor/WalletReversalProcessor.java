package com.snapdeal.opspanel.userpanel.walletreversal.processor;

import java.util.Map;
import java.util.Set;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;	
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.opspanel.userpanel.walletreversal.Enum.InstrumentType;
import com.snapdeal.opspanel.userpanel.walletreversal.Enum.TxnIdTypeEnum;
import com.snapdeal.opspanel.userpanel.walletreversal.Model.WalletReversalFileResponse;
import com.snapdeal.opspanel.userpanel.walletreversal.constants.WalletReversalConstants;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalClientException;
import com.snapdeal.payments.sdmoney.exceptions.InternalServerException;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.CancelTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.CancelTransactionResponse;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdResponse;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionRequest;
import com.snapdeal.payments.sdmoney.service.model.ReverseTransactionResponse;

import lombok.extern.slf4j.Slf4j;

@Slf4j
@Component
public class WalletReversalProcessor<E> implements IRowProcessor {

	@Autowired
	IUserServiceClient iUserService;

	@Autowired
	SDMoneyClient SDMoneyClient;

	@Override
	public Object execute(String[] header, String[] rowValues, Map<String, String> map, long rowNum,
			Object sharedObject, Map<String, String> headerValues) {
		WalletReversalFileResponse fileResponse =  new WalletReversalFileResponse();
		initializeRowValues(rowValues);
		String txnType = map.get("txnid_type");
		String instrumentType=map.get("instrumentType");
		txnType = txnType.trim();
		String prevTransactionId=null;
		if (txnType.equals(TxnIdTypeEnum.WALLET_TXN_ID.name())) {
			prevTransactionId=rowValues[0];
			reverseTxnBasedOnTxnId(fileResponse,prevTransactionId,instrumentType,rowValues);

		} else if(txnType.equals(TxnIdTypeEnum.WALLET_IDEMPOTENCYID.name())) {
			GetTransactionByIdempotencyIdRequest request= new GetTransactionByIdempotencyIdRequest();
			request.setIdempotencyKey(rowValues[0]);
			GetTransactionByIdempotencyIdResponse response=null;
			try {
				response = SDMoneyClient.getTransactionByIdempotencyId(request);
			} catch (SDMoneyException e) {
				log.info("SDMoneyException in getTransactionByIdempotencyId "+e.getErrorCode()+" "+e.getMessage());
				fileResponse.setStatus(WalletReversalConstants.FAILURE);
				fileResponse.setError("SDMoneyException in getTransactionByIdempotencyId "+e.getErrorCode()+" "+e.getMessage());
				return fileResponse;
			}  catch(Exception e1) {
				fileResponse.setStatus(WalletReversalConstants.FAILURE);
				fileResponse.setError("SDMoneyException in getTransactionByIdempotencyId "+e1.getMessage());
				log.info("SDMoneyException in getTransactionByIdempotencyId "+e1.getMessage());
				return fileResponse;
			}

			if(response!=null && response.getTransaction()!=null) {
				reverseTxnBasedOnTxnId(fileResponse,response.getTransaction().getTransactionId(),instrumentType,rowValues);
			} 
		}
		return fileResponse;
	}

	private Object reverseTxnBasedOnTxnId(WalletReversalFileResponse fileResponse, String prevTransactionId,
			String instrumentType, Object[] row) {

		int retry = 1;
		while (retry++<=2) {

			try {
				if (instrumentType.equals(InstrumentType.WALLET.name())) {
					ReverseTransactionRequest request = new ReverseTransactionRequest();
					request.setPrevTransactionId(prevTransactionId);
					ReverseTransactionResponse response = SDMoneyClient.reverseTransaction(request);
					fileResponse.setStatus(WalletReversalConstants.SUCCESS);
					fileResponse.setTransactionId(response.getTransactionId());
					fileResponse.setTxnTimeStamp(response.getTransactionTimeStamp().toString());
					return fileResponse;
				} else {
					CancelTransactionRequest request = new CancelTransactionRequest();
					request.setCancelTransactionReason((String) row[1]);
					request.setTransactionId(prevTransactionId);
					CancelTransactionResponse response = SDMoneyClient.cancelTransaction(request);
					fileResponse.setStatus(WalletReversalConstants.SUCCESS);
					fileResponse.setTransactionId(response.getTransactionId());
					fileResponse.setTxnTimeStamp(response.getTransactionTimestamp().toString());
					return fileResponse;
				}

			} catch (InternalClientException | InternalServerException re) {
				fileResponse.setStatus(WalletReversalConstants.RETRY);
				fileResponse.setError("Request timeout");
			} catch (SDMoneyException e) {
				fileResponse.setStatus(WalletReversalConstants.FAILURE);
				fileResponse.setError(e.getErrorCode() + " : " + e.getMessage());

			} catch (Exception ge) {
				fileResponse.setStatus(WalletReversalConstants.FAILURE);
				fileResponse.setError(ge.getMessage());
			}
		}
		return fileResponse;
	}

	// sets blank row values null
	private void initializeRowValues(String[] rowValues) {
		if (rowValues.length >= 1) {
			for (int i = 0; i < rowValues.length; i++) {
				if (rowValues[i].equalsIgnoreCase("")) {
					rowValues[i] = null;
				}
			}
		}
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
	public Object onStart(Map<String, String> paramMap1, Object paramObject,
			Map<String, String> paramMap2) {
		// TODO Auto-generated method stub
		return null;
	}

}
