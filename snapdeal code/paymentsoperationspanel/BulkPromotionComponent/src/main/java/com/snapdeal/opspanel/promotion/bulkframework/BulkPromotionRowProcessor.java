package com.snapdeal.opspanel.promotion.bulkframework;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import org.apache.commons.lang.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;
import com.snapdeal.ims.dto.UserDetailsDTO;
import com.snapdeal.ims.exception.HttpTransportException;
import com.snapdeal.ims.exception.ServiceException;
import com.snapdeal.opspanel.promotion.constant.BulkPromotionConstants;
import com.snapdeal.opspanel.promotion.enums.FileState;
import com.snapdeal.opspanel.promotion.enums.InstrumentType;
import com.snapdeal.opspanel.promotion.enums.TaskState;
import com.snapdeal.opspanel.promotion.model.BulkPromotionSharedObject;
import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
import com.snapdeal.opspanel.promotion.model.NotificationRequestModel;
import com.snapdeal.opspanel.promotion.model.OutputResponse;
import com.snapdeal.opspanel.promotion.service.FileMetaService;
import com.snapdeal.opspanel.promotion.service.IMSService;
import com.snapdeal.opspanel.promotion.service.NotificationService;
import com.snapdeal.payments.notification.utility.ChannelType;
import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
import com.snapdeal.payments.sdmoney.exceptions.InternalClientException;
import com.snapdeal.payments.sdmoney.exceptions.InternalServerException;
import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
import com.snapdeal.payments.sdmoney.service.model.CreditGeneralBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.CreditGeneralBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.CreditGeneralToUserMobileRequest;
import com.snapdeal.payments.sdmoney.service.model.CreditGeneralToUserMobileResponse;
import com.snapdeal.payments.sdmoney.service.model.CreditVoucherBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.CreditVoucherBalanceResponse;
import com.snapdeal.payments.sdmoney.service.model.CreditVoucherToUserMobileRequest;
import com.snapdeal.payments.sdmoney.service.model.CreditVoucherToUserMobileResponse;
import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdRequest;
import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdResponse;
import com.snapdeal.payments.sdmoney.service.model.Transaction;

import lombok.extern.slf4j.Slf4j;

//TODO: remove logs which are not needed.

@Component
@Slf4j
public class BulkPromotionRowProcessor implements IRowProcessor {

	@Autowired
	private SDMoneyClient sdMoneyClient;

	@Autowired
	private IMSService imsService;

	@Autowired
	private NotificationService notificationService;

	@Autowired
	private FileMetaService fileMetaService;

	@Override
	public Object execute(String[] header, String[] rowValues, Map<String, String> fileMetaData, long rowNum,
			Object sharedObject, Map<String, String> headerValues) {

		for (int i = 0; i < header.length; i++) {
		    header[ i ] = header[ i ].trim();
		}

		for( int i = 0; i < rowValues.length; i ++ ) {
			rowValues[ i ] = rowValues[ i ].trim();
		}

		try {

			log.info(BulkPromotionConstants.STEP_IDENTIFIER + "Started processing row number :" + rowNum + "   for " + rowValues[BulkPromotionConstants.userIdIndex]
					+ "\n");
	
			OutputResponse outputResponse = createOutputResponse( fileMetaData );
	
			BulkPromotionSharedObject bulkPromotionSharedObject = (BulkPromotionSharedObject) sharedObject;
	
			if( rowNum % BulkPromotionConstants.dbDumpFrequency == 0 ) {
				updatePorcessingRowNum( bulkPromotionSharedObject, fileMetaData );
			}

			if (doesTransactionExists(rowValues, fileMetaData, outputResponse)) {
				bulkPromotionSharedObject.setProcessingRows( ( int ) rowNum );
				return outputResponse;
			}

			/*
			 * user details are needed only when parking is off
			 * or in case of user id both email and sms are suppressed
			 * or in case of mobile number email is suppressed
			 */
			boolean userDetailsDTONeeded = !Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_PARK))
					|| !Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_EMAIL_SUPPRESSED))
					|| ( !Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_SMS_SUPPRESSED))
							&& fileMetaData.get(BulkPromotionConstants.ID_TYPE).equalsIgnoreCase( BulkPromotionConstants.IMS_ID ) );
	
			UserDetailsDTO userDetailsDTO = null;
	
			if (userDetailsDTONeeded) {
				userDetailsDTO = getUserDetailsForUserId(fileMetaData.get(BulkPromotionConstants.ID_TYPE),
						rowValues[BulkPromotionConstants.userIdIndex],
						rowNum );
			}

			boolean userFoundInIMS = userDetailsDTO != null;

			if (Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_PARK)) || userFoundInIMS ) {

				if( ! dispenseMoney( fileMetaData, rowValues, rowNum, outputResponse ) ) {
					bulkPromotionSharedObject.setProcessingRows( ( int ) rowNum );
					return outputResponse;
				}
//				if( !  ) {
//					bulkPromotionSharedObject.setProcessingRows( ( int ) rowNum );
//					return outputResponse;	
//				}
	
			} else {
	
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + "row skipped as parking disabled or could not find mobile number in case");
				outputResponse.setResponseStatus(TaskState.FAILED.name());
				outputResponse.setResponseMessage( BulkPromotionConstants.parkingFailMessage );
				outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
	
			}

			BigDecimal userBalance = null;
	
			if (userFoundInIMS) {
				if (!Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_EMAIL_SUPPRESSED)) && userDetailsDTO.getEmailId() != null ) {
					userBalance = getTotalUserBalance(userDetailsDTO.getUserId(), rowNum);
					sendNotification(ChannelType.EMAIL, fileMetaData.get(BulkPromotionConstants.EMAIL_TEMPLATE_ID), rowValues[BulkPromotionConstants.userIdIndex],
							rowValues[BulkPromotionConstants.amountIndex], userBalance, rowNum);
				}

				if (!Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_SMS_SUPPRESSED)) && userDetailsDTO.getMobileNumber() != null ) {
					sendNotification(ChannelType.SMS, fileMetaData.get(BulkPromotionConstants.SMS_TEMPLATE_ID), rowValues[BulkPromotionConstants.userIdIndex],
							rowValues[BulkPromotionConstants.amountIndex], userBalance, rowNum);
				}

			} else if ( fileMetaData.get(BulkPromotionConstants.ID_TYPE).equalsIgnoreCase( BulkPromotionConstants.MOBILE_ID ) ) {

				if (!Boolean.valueOf(fileMetaData.get(BulkPromotionConstants.IS_SMS_SUPPRESSED))) {
					sendNotification(ChannelType.SMS, fileMetaData.get(BulkPromotionConstants.SMS_TEMPLATE_ID), rowValues[BulkPromotionConstants.userIdIndex],
							rowValues[BulkPromotionConstants.amountIndex], userBalance, rowNum);
				}	
			}

			return outputResponse;
		} catch( Exception e ) {
			log.error( BulkPromotionConstants.STEP_IDENTIFIER + " SEVERE_ERROR " + ExceptionUtils.getFullStackTrace( e ) );
			return new OutputResponse();
		}
	}

	@Override
	public Set<String> columnsToIgnore() {
		return null;
	}

	@Override
	public void onFinish(java.util.Map<String,String> map, Object sharedObject, BulkFileStatus status, String fileName) {

		log.info( BulkPromotionConstants.STEP_IDENTIFIER + "on finish called, status  " + status);
		int successRowsNum = 0;
		int totalRows = 0;
		BigDecimal totalSuccessAmount = new BigDecimal( 0 );
		try {
			File file = new File( fileName );
			if ( file.exists() && file.isFile()) {
				FileInputStream outStream = new FileInputStream( fileName );
	
				BufferedReader readBuf = new BufferedReader(new InputStreamReader(outStream, "UTF-8"));
				String line = readBuf.readLine();
				int outputResponseStatusIndex = 0;
				int outputResponseAmountIndex = 0;
				String[] lineChunks = line.trim().split( "," );
				for( int i = 0; i < lineChunks.length; i ++ ) {
					if( lineChunks[ i ].trim().equalsIgnoreCase( "responseStatus" ) ) {
						outputResponseStatusIndex = i;
					}
					if( lineChunks[ i ].trim().equalsIgnoreCase( "amount" ) ) {
						outputResponseAmountIndex = i;
					}
				}
				while ((line = readBuf.readLine()) != null) {
					totalRows ++;
					lineChunks = line.trim().split( "," );
					if( lineChunks[ outputResponseStatusIndex ].trim().equalsIgnoreCase( "SUCCESS" ) ) {
						successRowsNum ++;
						totalSuccessAmount = totalSuccessAmount.add( new BigDecimal( lineChunks[ outputResponseAmountIndex ] ) );
					}
				}
				readBuf.close();
			}
		} catch( Exception e ) {
			log.info( BulkPromotionConstants.STEP_IDENTIFIER + "Exception while setting totalSuccessAmount and successrownums " + ExceptionUtils.getFullStackTrace( e ) );
		}

		boolean retry = true;
		int retryCount = 0;

		while (retry && retryCount < 2) {

			try {
				if( status == BulkFileStatus.SUCCESS )
					updateFileMetaEntity( map.get( BulkPromotionConstants.FILE_NAME ), FileState.EXECUTION_COMPLETE, successRowsNum, totalSuccessAmount, totalRows );
				else
					updateFileMetaEntity( map.get( BulkPromotionConstants.FILE_NAME ), FileState.EXECUTION_FAILED, successRowsNum, totalSuccessAmount, totalRows );
				retry = false;
			} catch( Exception e ) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + "Exception while db call in onFinish " + ExceptionUtils.getFullStackTrace( e ) );
				retryCount ++;
			}
		}

	}

	private void updatePorcessingRowNum( BulkPromotionSharedObject bulkPromotionSharedObject, Map<String, String> fileMetaData ) {

		log.info( BulkPromotionConstants.STEP_IDENTIFIER + "updating information in db. row number " + ( long ) bulkPromotionSharedObject.getProcessingRows() );
		FileMetaEntity entity = new FileMetaEntity();
		entity.setFileName( fileMetaData.get( BulkPromotionConstants.FILE_NAME ) );
		entity.setProcessingRow( ( long ) bulkPromotionSharedObject.getProcessingRows() );

		fileMetaService.updateFileMetaEntity( entity );
	}

	private void updateFileMetaEntity( String fileName, FileState status, int successRowsNum, BigDecimal totalSuccessAmount, int processingRows ) {
		FileMetaEntity entity = new FileMetaEntity();
		entity.setFileName( fileName );
		entity.setStatus( status.name() );
		entity.setSuccessRowsNum( ( long ) successRowsNum );
		entity.setTotalSuccessMoney( totalSuccessAmount );
		entity.setProcessingRow( ( long ) processingRows );
		entity.setCompletionTime( new Date() );

		fileMetaService.updateFileMetaEntity( entity );
	}

	private OutputResponse createOutputResponse( Map<String, String> fileMetaData ) {
		OutputResponse outputResponse = new OutputResponse();
		outputResponse.setUploadTimeStamp(new Timestamp( Long.valueOf(fileMetaData.get(BulkPromotionConstants.UPLOAD_TIMESTAMP) ) ) );
		return outputResponse;
	}

	private UserDetailsDTO getUserDetailsForUserId(String idType, String userId, long rowNum ) {

		UserDetailsDTO userDetailsDTO = null;

		boolean retry = true;
		int retryCount = 0;

		while (retry && retryCount < 2) {	
			long timeInMilliseconds = System.currentTimeMillis();
			try {
				switch (idType.toUpperCase()) {
				case BulkPromotionConstants.MOBILE_ID:
					userDetailsDTO = imsService.getUserByMobile(userId);
					break;
				case BulkPromotionConstants.IMS_ID:
					userDetailsDTO = imsService.getUserByImsId(userId);
					break;
				}
				retry = false;
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: imsClient, api name:  getUserByImsId, exception: none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
			} catch (HttpTransportException e) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER
						+ " Http Transport Exception while getting imsid and email id from mobile number from IMS retryCount"
						+ retryCount + ExceptionUtils.getFullStackTrace(e));

				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: imsClient, api name:  getUserByImsId, exception: " + e.getMessage() + " time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				retryCount++;
			} catch (ServiceException e) {

				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: imsClient, api name:  getUserByImsId, exception: " + e.getMessage() +", time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				retry = false;
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + "Exception while getting imsid and email id from mobile number from IMS "
						+ ExceptionUtils.getFullStackTrace(e));
			}
		}

		return userDetailsDTO;
	}

	private boolean dispenseMoney( Map<String, String> fileMetaData, String[] rowValues, long rowNum, OutputResponse outputResponse) {

		boolean retry = true;
		int retryCount = 0;
		String responseCode = null;
		String responseMessage = null;
		Timestamp transactionTimestamp = null;
		//Timestamp currentTimestamp = new Timestamp( new Date().getTime() );

		while (retry && retryCount < 2) {

			long timeInMilliseconds = System.currentTimeMillis();
			try {
				switch (InstrumentType.valueOf(fileMetaData.get(BulkPromotionConstants.INSTRUMENT))) {

				case GiftVoucher:
					switch ( fileMetaData.get( BulkPromotionConstants.ID_TYPE ) ) {

					case BulkPromotionConstants.IMS_ID:
						CreditVoucherBalanceResponse creditVoucherBalanceResponse = hitCreditVoucherBalance( fileMetaData,
								rowValues);
						responseCode = creditVoucherBalanceResponse.getTransactionId();
						transactionTimestamp = new Timestamp(
								creditVoucherBalanceResponse.getTransactionTimestamp().getTime());

						if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
							log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  creditVoucherBalance, exception: none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
						log.info(BulkPromotionConstants.STEP_IDENTIFIER + "row successful with transaction id : " + responseCode + "\n");
						break;

					case BulkPromotionConstants.MOBILE_ID:
						CreditVoucherToUserMobileResponse creditVoucherToUserMobileResponse = hitCreditVoucherToUserMobile(
								fileMetaData, rowValues);
						responseCode = creditVoucherToUserMobileResponse.getTransactionId();
						transactionTimestamp = new Timestamp(
								creditVoucherToUserMobileResponse.getTransactionTimestamp().getTime());
						if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
							log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  creditVoucherToUserBalance, exception: none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
						log.info(BulkPromotionConstants.STEP_IDENTIFIER + "row successful with transaction id : " + responseCode + "\n");
						break;

					}
					break;

				case WalletBalance:
					switch ( fileMetaData.get( BulkPromotionConstants.ID_TYPE ) ) {

					case BulkPromotionConstants.IMS_ID:
						CreditGeneralBalanceResponse creditGeneralBalanceResponse = hitCreditGeneralBalance( fileMetaData,
								rowValues);
						responseCode = creditGeneralBalanceResponse.getTransactionId();
						transactionTimestamp = new Timestamp(
								creditGeneralBalanceResponse.getTransactionTimestamp().getTime());
						if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
							log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  creditGeneralBalance, exception: none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
						log.info(BulkPromotionConstants.STEP_IDENTIFIER + "row successful with transaction id : " + responseCode + "\n");
						break;

					case BulkPromotionConstants.MOBILE_ID:
						CreditGeneralToUserMobileResponse creditGeneralToUserMobileResponse = hitCreditGeneralBalanceToUserMobile(
								fileMetaData, rowValues);
						responseCode = creditGeneralToUserMobileResponse.getTransactionId();
						transactionTimestamp = new Timestamp(
								creditGeneralToUserMobileResponse.getTransactionTimestamp().getTime());

						if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
							log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  creditGeneralToUserMobile, exception: none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
						log.info(BulkPromotionConstants.STEP_IDENTIFIER + "row successful with transaction id : " + responseCode + "\n");
						break;
					}
					break;
				}

				retry = false;
//				if( transactionTimestamp.before( currentTimestamp ) ) {
//					outputResponse.setResponseCode( responseCode );
//					outputResponse.setResponseMessage(BulkPromotionConstants.transactionAlreadyExistMessage);
//					outputResponse.setResponseStatus(TaskState.ALREADY_EXISTED.name());
//					outputResponse.setTransactionTimeStamp( transactionTimestamp );
//					return false;
//				}
//				else {
					outputResponse.setResponseStatus(TaskState.SUCCESS.name());
					outputResponse.setResponseCode(responseCode);
					outputResponse.setTransactionTimeStamp(transactionTimestamp);
					outputResponse.setResponseMessage(BulkPromotionConstants.moneySuccessfulMessage);
//					return true;
//				}

				return true;
			} catch (InternalClientException | InternalServerException ie) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER
						+ "catched internalclientexception or internalserverexception while dispensing money "
						+ ExceptionUtils.getFullStackTrace(ie));
				responseCode = ie.getErrorCode().name();
				responseMessage = ie.getMessage();
				transactionTimestamp = new Timestamp( new Date().getTime() ) ;
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  some credit api, exception:" + ie.getMessage() + ", time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				retryCount++;
			} catch (SDMoneyException sdme) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + "catched sd money exception while dispensing money "
						+ ExceptionUtils.getFullStackTrace(sdme));
				transactionTimestamp = new Timestamp( new Date().getTime() ) ;
				responseMessage = sdme.getMessage();
				responseCode = sdme.getErrorCode().name();
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  some credit api, exception:" + sdme.getMessage() + ", time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				retry = false;
			} catch (Exception e) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + "catched exception while dispensing money "
						+ ExceptionUtils.getFullStackTrace(e));
				transactionTimestamp = new Timestamp( new Date().getTime() ) ;
				responseMessage = e.getMessage();
				responseCode = BulkPromotionConstants.OPS_INTERNAL_SERVER_EXCEPTION;
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  some credit api, exception:" + e.getMessage() + ", time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				retry = false;
			}
		}

		outputResponse.setResponseStatus(TaskState.FAILED.name());
		outputResponse.setResponseCode(responseCode);
		outputResponse.setTransactionTimeStamp(transactionTimestamp);
		outputResponse.setResponseMessage(responseMessage);

		return false;
	}

	private String generateIdempotencyId(String formDataIdempotencyId, String userId) {
		return formDataIdempotencyId + userId;
	}

	private CreditVoucherBalanceResponse hitCreditVoucherBalance(Map<String, String> metaData, String[] rowValues)
			throws Exception {

		String idempotencyId = generateIdempotencyId( metaData.get(BulkPromotionConstants.IDEMPOTENCY_ID), rowValues[BulkPromotionConstants.userIdIndex] );
		CreditVoucherBalanceRequest creditRequest = new CreditVoucherBalanceRequest();
		creditRequest.setAmount(new BigDecimal(rowValues[BulkPromotionConstants.amountIndex]));
		creditRequest.setBusinessEntity(metaData.get(BulkPromotionConstants.BUSINESS_ENTITY));
		creditRequest.setEventContext(rowValues[BulkPromotionConstants.eventContextIndex]);
		creditRequest.setSourceCorporateAccountId(metaData.get(BulkPromotionConstants.CORP_ID));
		creditRequest.setSdIdentity(rowValues[BulkPromotionConstants.userIdIndex]);
		creditRequest.setIdempotencyId(idempotencyId);
		creditRequest.setTransactionReference(idempotencyId);
		creditRequest.setNotificationNotDesired(Boolean.valueOf(metaData.get(BulkPromotionConstants.IS_WALLET_NOTIFICATION_SUPPRESSED)));
		return sdMoneyClient.creditVoucherBalance(creditRequest);

	}

	private CreditVoucherToUserMobileResponse hitCreditVoucherToUserMobile(Map<String, String> metaData,
			String[] rowValues) throws Exception {
		String idempotencyId = generateIdempotencyId( metaData.get(BulkPromotionConstants.IDEMPOTENCY_ID), rowValues[BulkPromotionConstants.userIdIndex] );
		CreditVoucherToUserMobileRequest creditRequest = new CreditVoucherToUserMobileRequest();
		creditRequest.setAmount(new BigDecimal(rowValues[BulkPromotionConstants.amountIndex]));
		creditRequest.setBusinessEntity(metaData.get(BulkPromotionConstants.BUSINESS_ENTITY));
		creditRequest.setEventContext(rowValues[BulkPromotionConstants.eventContextIndex]);
		creditRequest.setSourceCorporateAccountId(metaData.get(BulkPromotionConstants.CORP_ID));
		creditRequest.setMobile(rowValues[BulkPromotionConstants.userIdIndex]);
		creditRequest.setIdempotencyId(idempotencyId);
		creditRequest.setTransactionReference(idempotencyId);
		creditRequest.setNotificationNotDesired(Boolean.valueOf(metaData.get(BulkPromotionConstants.IS_WALLET_NOTIFICATION_SUPPRESSED)));
		return sdMoneyClient.creditVoucherToUserMobile(creditRequest);

	}

	private CreditGeneralToUserMobileResponse hitCreditGeneralBalanceToUserMobile(Map<String, String> metaData,
			String[] rowValues) throws Exception {
		String idempotencyId = generateIdempotencyId( metaData.get(BulkPromotionConstants.IDEMPOTENCY_ID), rowValues[BulkPromotionConstants.userIdIndex] );
		CreditGeneralToUserMobileRequest creditRequest = new CreditGeneralToUserMobileRequest();
		creditRequest.setAmount(new BigDecimal(rowValues[BulkPromotionConstants.amountIndex]));
		creditRequest.setBusinessEntity(metaData.get(BulkPromotionConstants.BUSINESS_ENTITY));
		creditRequest.setEventContext(rowValues[BulkPromotionConstants.eventContextIndex]);
		creditRequest.setSourceCorporateAccountId(metaData.get(BulkPromotionConstants.CORP_ID));
		creditRequest.setMobile(rowValues[BulkPromotionConstants.userIdIndex]);
		creditRequest.setIdempotencyId(idempotencyId);
		creditRequest.setTransactionReference(idempotencyId);
		creditRequest.setNotificationNotDesired(Boolean.valueOf(metaData.get(BulkPromotionConstants.IS_WALLET_NOTIFICATION_SUPPRESSED)));
		return sdMoneyClient.creditGeneralToUserMobile(creditRequest);
	}

	private CreditGeneralBalanceResponse hitCreditGeneralBalance(Map<String, String> metaData, String[] rowValues)
			throws SDMoneyException {
		String idempotencyId = generateIdempotencyId( metaData.get(BulkPromotionConstants.IDEMPOTENCY_ID), rowValues[BulkPromotionConstants.userIdIndex] );
		CreditGeneralBalanceRequest creditRequest = new CreditGeneralBalanceRequest();
		creditRequest.setAmount(new BigDecimal(rowValues[BulkPromotionConstants.amountIndex]));
		creditRequest.setBusinessEntity(metaData.get(BulkPromotionConstants.BUSINESS_ENTITY));
		creditRequest.setEventContext(rowValues[BulkPromotionConstants.eventContextIndex]);
		creditRequest.setSourceCorporateAccountId(metaData.get(BulkPromotionConstants.CORP_ID));
		creditRequest.setSdIdentity(rowValues[BulkPromotionConstants.userIdIndex]);
		creditRequest.setIdempotencyId(idempotencyId);
		creditRequest.setTransactionReference(idempotencyId);
		creditRequest.setNotificationNotDesired(Boolean.valueOf(metaData.get(BulkPromotionConstants.IS_WALLET_NOTIFICATION_SUPPRESSED)));
		return sdMoneyClient.creditGeneralBalance(creditRequest);
	}

	private BigDecimal getTotalUserBalance(String userId, long rowNum) {

		boolean retry = true;
		int retryCount = 0;
		while (retry && retryCount < 2) {
			long timeInMilliseconds = System.currentTimeMillis();
			try {
				GetAccountBalanceRequest getBalanceRequest = new GetAccountBalanceRequest();
				getBalanceRequest.setSdIdentity(userId);
				return sdMoneyClient.getAccountBalance(getBalanceRequest).getBalance().getTotalBalance();
			} catch (InternalClientException | InternalServerException e) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER
						+ "Internal client exception or internal server exception occurred during getAccountBalance from sdmoney "
						+ ExceptionUtils.getFullStackTrace(e));
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  getAccountBalance, exception:none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				retryCount++;
			} catch (Exception e) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + "Exception occurred while getAccountBalance from SDMoney "
						+ ExceptionUtils.getFullStackTrace(e));
				retry = false;
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: sdMoneyClient, api name:  getAccountBalance, exception:" + e.getMessage() + ", time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
				
			}
		}
		return null;
	}

	private void sendNotification(ChannelType channelType, String templateId, String userId, String amount,
			BigDecimal userBalance, long rowNum) {
		NotificationRequestModel notificationRequestModel = new NotificationRequestModel();
		notificationRequestModel.setTemplateId(templateId);
		notificationRequestModel.setChannelType(channelType);
		notificationRequestModel.setUserId(userId);
		Map<String, Object> templateParams = new HashMap<String, Object>();
		templateParams.put(BulkPromotionConstants.CREDITED_AMOUNT, amount);
		if( channelType == ChannelType.EMAIL ) {
			templateParams.put(BulkPromotionConstants.TOTAL_AMOUNT, userBalance);
		}
		notificationRequestModel.setTemplateParams(templateParams);

		boolean retry = true;
		int retryCount = 0;
		while (retry && retryCount < 2) {
			long timeInMilliseconds = System.currentTimeMillis();
			try {
				notificationService.sendNotification(notificationRequestModel);
				retry = false;
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: notificationClient, api name:  sendNotification, exception: none, time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );

			} catch (Exception e) {
				log.info(BulkPromotionConstants.STEP_IDENTIFIER + " Exception occurred while sending notification, retry count " + retryCount
						+ 1 + " : " + ExceptionUtils.getFullStackTrace(e));
				retryCount++;
				if( rowNum % BulkPromotionConstants.logDumpFrequency == 0 )
					log.info( BulkPromotionConstants.STEP_IDENTIFIER + BulkPromotionConstants.LATENCY_IDENTIFIER + " retry count: " + retryCount + ", client name: notificationClient, api name:  sendNotification, exception:" + e.getMessage() + ", time taken: " + ( System.currentTimeMillis() - timeInMilliseconds ) );
			}
		}
	}

	private boolean doesTransactionExists(String[] rowValues, Map<String, String> fileMetaData, OutputResponse outputResponse) {
		String idempotencyId = generateIdempotencyId( fileMetaData.get(BulkPromotionConstants.IDEMPOTENCY_ID), rowValues[BulkPromotionConstants.userIdIndex] );
		GetTransactionByIdempotencyIdResponse getTransactionByIdempotencyIdResponse = null;
		GetTransactionByIdempotencyIdRequest getTransactionByIdempotencyIdRequest = new GetTransactionByIdempotencyIdRequest();
		getTransactionByIdempotencyIdRequest.setIdempotencyKey( idempotencyId );
		try {
			log.info(BulkPromotionConstants.STEP_IDENTIFIER + "Now will attempt to know if this transaction have happened" + "\n");
			getTransactionByIdempotencyIdResponse = sdMoneyClient
					.getTransactionByIdempotencyId(getTransactionByIdempotencyIdRequest);
			log.info(BulkPromotionConstants.STEP_IDENTIFIER + " Idempotency check response was : "
					+ getTransactionByIdempotencyIdRequest.toString());

			Transaction transaction = getTransactionByIdempotencyIdResponse.getTransaction();
			if (transaction != null) {
				outputResponse.setResponseCode(transaction.getTransactionId());
				outputResponse.setResponseMessage(BulkPromotionConstants.transactionAlreadyExistMessage);
				outputResponse.setResponseStatus(TaskState.ALREADY_EXISTED.name());
				outputResponse.setTransactionTimeStamp(new Timestamp(transaction.getTimestamp().getTime()));
				return true;
			}
		} catch (Exception sdme) {
			log.info(BulkPromotionConstants.STEP_IDENTIFIER + "failed in getting transaction through idempotancyId , " + "SDMoneyException : "
					+ "Error message :" + ExceptionUtils.getFullStackTrace(sdme) + "\n");
		}
		return false;
	}
	
	@Override
    public Object onStart(Map<String, String> paramMap1, Object paramObject, Map<String, String> paramMap2) {
		// TODO 
		return null;
	}


}
