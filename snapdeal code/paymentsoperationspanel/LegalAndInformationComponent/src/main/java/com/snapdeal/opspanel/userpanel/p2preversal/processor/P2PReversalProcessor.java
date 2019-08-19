package com.snapdeal.opspanel.userpanel.p2preversal.processor;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.ims.client.IUserServiceClient;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.request.GetUserByEmailRequest;
import com.snapdeal.ims.request.GetUserByMobileRequest;
import com.snapdeal.ims.response.GetUserResponse;
import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.p2preversal.constants.P2PReversalConstants;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.ImsIdTypes;
import com.snapdeal.opspanel.userpanel.p2preversal.enums.P2PReverseTxnTypes;
import com.snapdeal.opspanel.userpanel.p2preversal.model.P2PFullReversalResponse;
import com.snapdeal.opspanel.userpanel.p2preversal.model.P2PReversalResponse;
import com.snapdeal.payments.escrowengine.model.PayMoneyRequest;
import com.snapdeal.payments.escrowengine.model.PayMoneyResponse;
import com.snapdeal.payments.escrowengine.model.ReverseMoneyRequest;
import com.snapdeal.payments.escrowengine.model.ReverseMoneyResponse;
import com.snapdeal.payments.p2pengine.enums.TransactionType;
import com.snapdeal.payments.p2pengine.exceptions.GenericException;
import com.snapdeal.payments.p2pengine.exceptions.service.BusinessException;
import com.snapdeal.payments.p2pengine.exceptions.service.ValidationException;
import com.snapdeal.payments.p2pengine.external.client.impl.EscrowEngineClient;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalClientException;
import com.snapdeal.payments.sdmoney.exceptions.InternalServerException;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.EnableSDMoneyAccountRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdResponse;
import com.snapdeal.payments.sdmoney.service.model.SuspendSDMoneyAccountRequest;

import lombok.extern.slf4j.Slf4j;
import net.logstash.logback.encoder.org.apache.commons.lang.exception.ExceptionUtils;

@Slf4j
@Component
public class P2PReversalProcessor implements IRowProcessor {

	@Autowired
	IUserServiceClient iUserService;

	@Autowired
	SDMoneyClient SDMoneyClient;

	@Autowired
	EscrowEngineClient EscrowEngineClient;

	@Override
	public Object execute(String[] header, String[] rowValues, Map<String, String> map, long rowNum,
			Object sharedObject, Map<String, String> headerValues) {

		/*
		 * 
		 * Checking The transaction Type
		 */
		initializeRowValues(rowValues);
		String txnType = map.get("txn_type");
		//String retrySeq=map.get("retry_seq");
		txnType = txnType.trim();
		if (txnType.equals(P2PReverseTxnTypes.P2P_PARTIAL_REVERSE_TRANSACTION.toString())) {
			try {
				

				P2PReversalResponse fileResponse = new P2PReversalResponse();
				GetTransactionByIdempotencyIdResponse getTransactionByIdempotencyIdResponse=getTransactionByIdempotencyId(
						rowValues[0], rowValues[3], fileResponse);
				/*if(retrySeq!=null && !retrySeq.isEmpty()){
					getTransactionByIdempotencyIdResponse = getTransactionByIdempotencyId(
							rowValues[0]+"_"+retrySeq, rowValues[3], fileResponse);
				}else{
					getTransactionByIdempotencyIdResponse = getTransactionByIdempotencyId(
							rowValues[0], rowValues[3], fileResponse);
				}*/
				
				if (getTransactionByIdempotencyIdResponse == null) {
					return fileResponse;
				}

				/*
				 * Checking ID Type and Then 
				 * Getting IMS ID from
				 * Email and
				 * Mobile
				 */

				String sourceImsId;
				String destinationImsId;
				ImsIdTypes idType = ImsIdTypes.valueOf(map.get("id_type"));
				switch (idType) {
				case EMAIL:
					try {
						sourceImsId = getIdFromEmail(rowValues[1]);
						destinationImsId = getIdFromEmail(rowValues[2]);
					} catch (Exception e) {
						fileResponse.setStatus(P2PReversalConstants.FAILURE);
						fileResponse.setError("Unable to find IMS id for email because " + e.getMessage());
						return fileResponse;
					}
					break;

				case MOBILE:
					try {
						sourceImsId = getIdFromMobile(rowValues[1]);
						destinationImsId = getIdFromMobile(rowValues[2]);
					} catch (Exception e) {
						fileResponse.setStatus(P2PReversalConstants.FAILURE);
						fileResponse.setError("Unable to find IMS id for mobile because " + e.getMessage());
						return fileResponse;
					}
					break;

				case IMS_ID:
					sourceImsId = rowValues[1];
					destinationImsId = rowValues[2];
					break;

				default:
					log.info("IMS ID Type is unknown. PLease Check");
					sourceImsId = rowValues[1];
					destinationImsId = rowValues[2];
					break;
				}

				/*
				 * Enabling source and destination wallet account
				 */
				try {
					enableWalletAccount(sourceImsId);
				} catch (OpsPanelException e) {
					fileResponse.setStatus(P2PReversalConstants.FAILURE);
					fileResponse.setError("Unable to enable source wallet account. REASON: " + e.getErrMessage());
					return fileResponse;
				}
				try {
					enableWalletAccount(destinationImsId);
				} catch (OpsPanelException e) {
					log.info("Disabling source account as destination can not be enabled");
					try {
						disableWalletAccount(sourceImsId);
					} catch (OpsPanelException e1) {
						fileResponse.setStatus(P2PReversalConstants.FAILURE);
						fileResponse.setError(
								"Please disable source Account ASAP. Error Occured while enabling destination account"
										+ e1.getErrMessage());
						return fileResponse;
					}
					fileResponse.setStatus(P2PReversalConstants.FAILURE);
					fileResponse.setError("Unable to enable destination wallet account. REASON: " + e.getErrMessage());
					return fileResponse;
				}

				/*
				 * Generating Request For P2P Reversal and Hitting EscrowClient
				 * For Reversal
				 * 
				 */
				boolean isReversalSuccess = false;
				PayMoneyRequest payMoneyRequest = new PayMoneyRequest();
				payMoneyRequest.setCommunicationNotRequired(true);
				payMoneyRequest.setAmount(new BigDecimal(rowValues[3]));
				payMoneyRequest.setSourceImsId(sourceImsId);
				payMoneyRequest.setDestinationImsId(destinationImsId);
				payMoneyRequest.setTransactionDisplayIdentifier(
						getTransactionByIdempotencyIdResponse.getTransaction().getTransactionReference());
				payMoneyRequest
						.setIdempotencyId(getTransactionByIdempotencyIdResponse.getTransaction().getTransactionId());

				PayMoneyResponse payMoneyResponse;
				try {
					payMoneyResponse = EscrowEngineClient.payMoney(payMoneyRequest);
					fileResponse.setStatus(P2PReversalConstants.SUCCESS);
					fileResponse.setReverseTxnId(payMoneyResponse.getTransactionId());
					fileResponse.setTsmTransactionState(payMoneyResponse.getTransactionStatus().toString());
					fileResponse.setSourceRunningBalance(
							payMoneyResponse.getSourceRunningBalance().getTotalBalance().toString());
					isReversalSuccess = true;
				} catch (BusinessException e) {
					log.info("Error while Reversing Partial P2P transaction in P2P reversal Processor"
							+ ExceptionUtils.getFullStackTrace(e));
					fileResponse.setStatus(P2PReversalConstants.FAILURE);
					fileResponse.setError("Unable to reverse money . REASON :" + e.getError().getMessage());
				} catch (GenericException e) {
					log.info("Error while Reversing Partial P2P transaction in P2P reversal Processor"
							+ ExceptionUtils.getFullStackTrace(e));
					fileResponse.setStatus(P2PReversalConstants.FAILURE);
					fileResponse.setError("Unable to reverse money . REASON :" + e.getError().getMessage());
				} catch (Exception e) {
					log.info("Error while Reversing Partial P2P transaction in P2P reversal Processor"
							+ ExceptionUtils.getFullStackTrace(e));
					fileResponse.setStatus(P2PReversalConstants.FAILURE);
					fileResponse.setError("Unable to reverse money . REASON :" + e.getMessage());
				} finally {
					try {
						log.info("Going to disable source account after Paymoney");
						disableWalletAccount(sourceImsId);
					} catch (OpsPanelException ope) {
						log.info("Unable to disable source account after Reverse Money"
								+ ExceptionUtils.getFullStackTrace(ope));
						if (isReversalSuccess) {
							fileResponse.setStatus(P2PReversalConstants.CAUTION);
							fileResponse.setError(
									"Reverse Money Success but unable to Disable the souce & destination email ID. REASON:"
											+ ope.getErrMessage());
							fileResponse.setRemarks("Please disable the source and destination Account.");
							return fileResponse;
						} else {
							fileResponse.setStatus(P2PReversalConstants.FAILURE);
							fileResponse.setError(
									"Reverse Money Failure  and unable to Disable the source & destination email ID.REASON: "
											+ ope.getErrMessage());
							fileResponse.setRemarks("Please disable the source and destination Account.");
							return fileResponse;
						}

					}
					try {
						log.info("Going to disable destination account after Paymoney");
						disableWalletAccount(destinationImsId);
					} catch (OpsPanelException ope) {
						log.info("Unable to disable destination account after Reverse Money"
								+ ExceptionUtils.getFullStackTrace(ope));
						if (isReversalSuccess) {
							fileResponse.setStatus(P2PReversalConstants.CAUTION);
							fileResponse.setError(
									"Reverse Money Success but unable to Disable the destination email ID.REASON:"
											+ ope.getErrMessage());
							fileResponse.setRemarks("Please disable destination account.");
						} else {
							fileResponse.setStatus(P2PReversalConstants.FAILURE);
							fileResponse.setError(
									"Reverse Money Failure  and unable to Disable the destination email ID.REASON:"
											+ ope.getErrMessage());
							fileResponse.setRemarks("Please disable destination account.");
						}

					}
				}

				return fileResponse;
			} catch (Exception e) {
				log.info("[CRITICAL]:::Exeption Catched in overall try catch block . Exception is "
						+ ExceptionUtils.getFullStackTrace(e));
				P2PReversalResponse response = new P2PReversalResponse();
				response.setStatus(P2PReversalConstants.FAILURE);
				response.setError(e.getMessage());
				return response;
			} finally {

			}
		}

		/*
		 * 
		 * Processor For the Full Reversal Transaction Type
		 * 
		 */

		else if (txnType.equals(P2PReverseTxnTypes.P2P_FULL_REVERSE_TRANSACTION.toString())) {
			String refundKey = map.get("refund_key");
			if(refundKey!= null)
			{
			refundKey = refundKey.trim();
			}
			P2PFullReversalResponse fileResponse = new P2PFullReversalResponse();
			ReverseMoneyResponse reverseMoneyResponse =  new ReverseMoneyResponse();
			
			ReverseMoneyRequest reverseMoneyRequest = new ReverseMoneyRequest();
			reverseMoneyRequest.setParentTransactionId(rowValues[0]);

			/*
			 * Transaction Type is hardcoded as of now as send Money
			 */
			reverseMoneyRequest.setParentTransactionType(TransactionType.SEND_MONEY);
			String idempotencyId = rowValues[0] + "_" + refundKey;
			reverseMoneyRequest.setIdempotencyId(idempotencyId);
			reverseMoneyRequest.setReason(rowValues[1]);
			
			try {
				log.info("Going to Hit Reverse Money With request : " + reverseMoneyRequest.toString());
				reverseMoneyResponse = EscrowEngineClient.reverseMoney(reverseMoneyRequest);
				fileResponse.setStatus(P2PReversalConstants.SUCCESS);
				fileResponse.setTransactionId(reverseMoneyResponse.getTransactionId());
				fileResponse.setTransactionStatus(reverseMoneyResponse.getTransactionStatus());
				fileResponse.setTransactionTimeStamp(reverseMoneyResponse.getTransactionTimeStamp());
			} catch (ValidationException ve) {
					log.info("Validation Exception in FULL REVERSE MONEY " + ve.getErrors());
					log.info(ExceptionUtils.getFullStackTrace(ve));
					fileResponse.setStatus(P2PReversalConstants.FAILURE);
					fileResponse.setError(ve.toString());
			} catch (GenericException ge) {
				log.info("Exception in FULL REVERSE MONEY " + ge.getError().getMessage());
				log.info(ExceptionUtils.getFullStackTrace(ge));
				fileResponse.setStatus(P2PReversalConstants.FAILURE);
				fileResponse.setError(ge.getError().getMessage());
			}
			

			return fileResponse;

		}

		else {

			log.info("Incorrect Transaction Type given . PLease Check. " + txnType);
			return null;
		}

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

	/*
	 * Calling Ims to get IMS ID From email
	 */
	public String getIdFromEmail(String email) throws InfoPanelException, OpsPanelException {
		if (email == null || email.isEmpty()) {
			log.info("Please enter email to get IMS Id");
			throw new OpsPanelException("P2PR-1001", "Please enter email to get IMS Id");
		}
		GetUserResponse response;
		GetUserByEmailRequest request = new GetUserByEmailRequest();
		request.setEmailId(email);
		try {
			response = iUserService.getUserByEmail(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while getUserByEmail : WILL BE RETRIED...");
			try {
				response = iUserService.getUserByEmail(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getUserByEmail : FAILURE ...");
				getErrorMsgFrmException(hte2);
				throw new InfoPanelException(hte2.getErrCode(), hte2.getErrMsg(), "IUserServiceClient");
			} catch (com.snapdeal.ims.exception.ServiceException se) {
				log.info("RETRY : ServiceException while getUserByEmail : FAILURE ...");
				getErrorMsgFrmException(se);
				throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
			}
		} catch (com.snapdeal.ims.exception.ServiceException se) {
			log.info("Exception from IUserServiceClient while searching for user by email "
					+ ExceptionUtils.getFullStackTrace(se));
			getErrorMsgFrmException(se);
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
		}
		return response.getUserDetails().getUserId();
	}

	/*
	 * Calling Ims to get IMS ID From mobile
	 */
	public String getIdFromMobile(String mobile) throws InfoPanelException, OpsPanelException {
		if (mobile == null || mobile.isEmpty()) {
			log.info("Please enter mobile to get IMS Id");
			throw new OpsPanelException("P2PR-1001", "Please enter mobile to get IMS Id");
		}
		GetUserResponse response;
		GetUserByMobileRequest request = new GetUserByMobileRequest();
		request.setMobileNumber(mobile);
		try {
			response = iUserService.getUserByVerifiedMobile(request);
		} catch (HttpTransportException hte) {
			log.info("HttpTransportException while getUserByVerifiedMobile : WILL BE RETRIED...");
			try {
				response = iUserService.getUserByVerifiedMobile(request);
			} catch (HttpTransportException hte2) {
				log.info("HttpTransportException while getUserByVerifiedMobile : FAILURE ...");
				log.info(ExceptionUtils.getFullStackTrace(hte2));
				throw new InfoPanelException(hte2.getErrCode(), hte2.getErrMsg(), "IUserServiceClient");
			} catch (com.snapdeal.ims.exception.ServiceException se) {
				log.info("RETRY : ServiceException while getUserByVerifiedMobile : FAILURE ...");
				log.info(ExceptionUtils.getFullStackTrace(se));
				throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
			}
		} catch (com.snapdeal.ims.exception.ServiceException se) {
			log.info("Exception from IUserServiceClient while searching for user by mobile "
					+ ExceptionUtils.getFullStackTrace(se));
			log.info(getErrorMsgFrmException(se));
			throw new InfoPanelException(se.getErrCode(), se.getErrMsg(), "IUserServiceClient");
		}
		return response.getUserDetails().getUserId();
	}

	/*
	 * Calling Wallet For Enabling WalletAccount.
	 */
	private boolean enableWalletAccount(String imsId) throws OpsPanelException {

		EnableSDMoneyAccountRequest enableSDMoneyAccountRequest = new EnableSDMoneyAccountRequest();
		enableSDMoneyAccountRequest.setSdIdentity(imsId);
		try {
			log.info("Going to enable account with request " + enableSDMoneyAccountRequest.toString());
			SDMoneyClient.enableSDMoneyAccount(enableSDMoneyAccountRequest);
			return true;
		} catch (Exception e) {
			log.info("Exception in SDMONEY while enabling Account because: " + e.getMessage()
					+ ExceptionUtils.getFullStackTrace(e));
			log.info("Retrying to enable Account and going to hit SDMONEY");
			try {
				log.info("Going to enable account with request " + enableSDMoneyAccountRequest.toString());
				SDMoneyClient.enableSDMoneyAccount(enableSDMoneyAccountRequest);
				return true;
			} catch (SDMoneyException sde) {
				log.info("Exception in SDMONEY while enabling Account because: " + sde.getMessage()
						+ ExceptionUtils.getFullStackTrace(sde));
				throw new OpsPanelException("P2PR-1002", sde.getMessage());
			} catch (Exception e1) {
				log.info("Exception in SDMONEY while enabling Account because: " + e1.getMessage()
						+ ExceptionUtils.getFullStackTrace(e1));
				throw new OpsPanelException("P2PR-1002", e1.getMessage());
			}
		}
	}

	/*
	 * Calling Wallet For disabling Account
	 */

	private boolean disableWalletAccount(String imsId) throws OpsPanelException {
		SuspendSDMoneyAccountRequest suspendSDMoneyAccountRequest = new SuspendSDMoneyAccountRequest();
		suspendSDMoneyAccountRequest.setSdIdentity(imsId);
		try {
			log.info("Going to disable account with request " + suspendSDMoneyAccountRequest.toString());
			SDMoneyClient.suspendSDMoneyAccount(suspendSDMoneyAccountRequest);
			return true;
		} catch (Exception e) {
			log.info("Exception in SDMONEY while disabling Account because: " + e.getMessage()
					+ ExceptionUtils.getFullStackTrace(e));
			log.info("Retrying to enable Account and going to hit SDMONEY");
			try {
				log.info("Going to disable account with request " + suspendSDMoneyAccountRequest.toString());
				SDMoneyClient.suspendSDMoneyAccount(suspendSDMoneyAccountRequest);
				return true;
			} catch (SDMoneyException sde) {
				log.info("Exception in SDMONEY while disabling Account because: " + sde.getMessage()
						+ ExceptionUtils.getFullStackTrace(sde));
				throw new OpsPanelException("P2PR-1003", sde.getMessage());
			} catch (Exception e1) {
				log.info("Exception in SDMONEY while disabling Account because: " + e1.getMessage()
						+ ExceptionUtils.getFullStackTrace(e1));
				throw new OpsPanelException("P2PR-1003", e1.getMessage());
			}
		}
	}

	/*
	 * getting error messages from the respective exceptions through a single
	 * function
	 */
	private String getErrorMsgFrmException(Exception e) {
		if (e instanceof SDMoneyException) {
			SDMoneyException se = (SDMoneyException) e;
			return se.getErrorCode() + " : " + se.getMessage();

		}
		if (e instanceof InfoPanelException) {
			InfoPanelException se = (InfoPanelException) e;
			return se.getErrCode() + " : " + se.getMessage();

		}
		if (e instanceof com.snapdeal.ims.exception.ServiceException) {
			SDMoneyException se = (SDMoneyException) e;
			return se.getErrorCode() + " : " + se.getMessage();

		}
		if (e instanceof OpsPanelException) {
			OpsPanelException se = (OpsPanelException) e;
			return se.getErrCode() + " : " + se.getMessage();

		} else {
			return "Some Error occured please retry  Msg:" + e.getMessage();
		}

	}

	// returns transaction by idempotency id, if its valid. Valid check includes
	// valid amount value in row and file amount value should be smaller or
	// equal to transaction value
	private GetTransactionByIdempotencyIdResponse getTransactionByIdempotencyId(String idempotencyId,
			String amountInFile, P2PReversalResponse fileResponse) {

		String error = null;
		boolean retry = true;
		int retryCount = 0;
		boolean validCase = false;

		GetTransactionByIdempotencyIdRequest getTransactionByIdempotencyIdRequest = new GetTransactionByIdempotencyIdRequest();
		GetTransactionByIdempotencyIdResponse geTransactionByIdempotencyIdResponse = null;
		getTransactionByIdempotencyIdRequest.setIdempotencyKey(idempotencyId);

		while (retry && retryCount < 2) {
			try {
				geTransactionByIdempotencyIdResponse = SDMoneyClient
						.getTransactionByIdempotencyId(getTransactionByIdempotencyIdRequest);
				retry = false;
			} catch (InternalClientException | InternalServerException ie) {
				log.info(
						"InternalClientException | InternalServerException in SDMONEY in getTransactionByIdempotencyId for ...."
								+ getTransactionByIdempotencyIdRequest.toString() + " "
								+ ExceptionUtils.getFullStackTrace(ie) + "\n");
				retryCount++;
				error = ie.getMessage();

			} catch (Exception sdme) {
				log.info("Exception in SDMONEY in getTransactionByIdempotencyId for ...."
						+ getTransactionByIdempotencyIdRequest.toString() + " " + ExceptionUtils.getFullStackTrace(sdme)
						+ "\n");
				retry = false;
				error = sdme.getMessage();
			}
		}

		String message = null;
		if (geTransactionByIdempotencyIdResponse == null) {
			message = P2PReversalConstants.transactionNotFoundMessage;
		}

		BigDecimal amount = null;
		try {
			amount = new BigDecimal(amountInFile);
		} catch (Exception e) {
			log.info("Amount given for this reversal is not valid" + ExceptionUtils.getFullStackTrace(e));
			error = e.getMessage();
			message = P2PReversalConstants.amountNotValidMessage;
		}

		if (geTransactionByIdempotencyIdResponse != null) {
			BigDecimal txnAmount = geTransactionByIdempotencyIdResponse.getTransaction().getTransactionAmount();
			if (amount.compareTo(txnAmount) == 1) {
				log.info("Given amount is greater than actual txn amount for this Idempotency ID. i.e. originally "
						+ txnAmount);
				log.info("Hence can not procees further");
				error = P2PReversalConstants.excessAmountMessage;
				message = P2PReversalConstants.excessAmountMessage;
			} else {
				validCase = true;
			}
		}

		if (!validCase) {
			fileResponse.setStatus(P2PReversalConstants.FAILURE);
			fileResponse.setError(error);
			fileResponse.setRemarks(message);
			fileResponse.setReverseTxnId(P2PReversalConstants.NA);
			fileResponse.setSourceRunningBalance(P2PReversalConstants.NA);
			fileResponse.setTsmTransactionState(P2PReversalConstants.NA);
		}

		if (validCase) {
			return geTransactionByIdempotencyIdResponse;
		} else {
			return null;
		}

	}

	@Override
	public Object onStart(Map<String, String> paramMap1, Object paramObject,
			Map<String, String> paramMap2) {
		// TODO Auto-generated method stub
		return null;
	}

}
