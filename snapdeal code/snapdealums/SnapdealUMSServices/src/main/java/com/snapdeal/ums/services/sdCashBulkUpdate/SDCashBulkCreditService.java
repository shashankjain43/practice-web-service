package com.snapdeal.ums.services.sdCashBulkUpdate;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadRequest;
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.dto.RawSDCashBulkCreditExcelRow;
import com.snapdeal.ums.core.entity.SDCashFileUploadHistoryDO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.dao.user.sdwallet.ISDCashFileUploadHistoryDAO;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserService;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.server.services.impl.IMSService.UserOwner;
import com.snapdeal.ums.services.HashProvider;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.utils.UMSStringUtils;

/***
 * Manager Class to handle all the file upload tasks related to SDCash bulk
 * credit.. First layer service.
 * 
 * @author lovey
 * 
 * 
 */
@Service
public class SDCashBulkCreditService {
	
	@Autowired
    private IMSService imsService;

	@Autowired
	private IUMSConvertorService umsConvertorService;

	@Autowired
	private IEmailServiceInternal emailService;

	@Autowired
	private SDCashBulkCreditExcelReader sdCashBulkCreditExcelReader;

	@Autowired
	private ISDCashFileUploadHistoryDAO sdCashFileUploadHistoryDao;

	@Autowired
	private ISDWalletService sdWalletService;

	@Autowired
	private IUserService userService;

	private static final Logger log = LoggerFactory.getLogger(SDCashBulkCreditService.class);

	private static final String EXCEL_EXTENSION = ".xls";

	private static final int MAX_ROWS_TO_BE_PROCESSED = 10000;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private HashProvider hashProviderService;

	/**
	 * First level service to process incoming SDCashUpload request from
	 * controller. Returns the final SDCashUpload Response type of object back
	 * to the controller after processing the complete request.
	 * 
	 * @param sdCashUploadRequest
	 * @param source
	 * @return
	 */
	@AuditableMethod
	@Transactional
	public SDCashBulkCreditUploadResponse processSDCashUploadRequest(
			SDCashBulkCreditUploadRequest sdCashUploadRequest, String source) {

		SDCashBulkCreditUploadResponse sdCashBulkCreditUploadResponse = new SDCashBulkCreditUploadResponse();

		byte[] excelFileContent = null;
		try {
			// Applying the first level checks on request parameters
			if (sdCashUploadRequest != null) {

				int activityType = sdCashUploadRequest.getActivityType();

				if (sdCashUploadRequest.getEmailTemplateName() == null
						|| sdCashUploadRequest.getEmailTemplateName().isEmpty()) {
					// sdCashBulkCreditUploadResponse.setReason(false);
					log.info("TemplateName field is empty.. Could not process the request");
					return sdCashBulkCreditUploadResponse;
				}

				else {
					String templateName = sdCashUploadRequest
							.getEmailTemplateName();

					String uploaderEmail = sdCashUploadRequest
							.getUploadersEmail();

					// check if the file was uploaded and set the corresponding
					// error constant.

					if (sdCashUploadRequest.getFileContent() == null
							|| sdCashUploadRequest.getFileContent().length == 0) {

						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY);
						log.error(ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY
								.getMsg());
						return sdCashBulkCreditUploadResponse;

					}
					// check the file extension is other than xls.

					else if (!(sdCashUploadRequest.getFileName().toLowerCase()
							.endsWith(EXCEL_EXTENSION))) {

						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.NOT_AN_EXCEL_FILE);
						log.error(ErrorConstants.NOT_AN_EXCEL_FILE.getMsg());
						return sdCashBulkCreditUploadResponse;

					}

					else {
						excelFileContent = sdCashUploadRequest.getFileContent();
					}

					/*
					 * To generate hash of the uploaded file and check whether
					 * file is already been processed or not.
					 */
					String uploadedFileHash = getUploadedFileHash(excelFileContent);

					if (isDuplicateFileUploaded(sdCashUploadRequest,
							sdCashBulkCreditUploadResponse, uploadedFileHash))
						return sdCashBulkCreditUploadResponse;

					// Now process the file contents in bytes

					List<RawSDCashBulkCreditExcelRow> rawExcelList = sdCashBulkCreditExcelReader
							.extractRawSDCashCreditRow(excelFileContent,
									sdCashBulkCreditUploadResponse);

					int rowCount = 0;

					if (rawExcelList != null && rawExcelList.size() > 0) {
						rowCount = rawExcelList.size();
					}

					log.info("Created "
							+ rowCount
							+ " RawSDCashBulkCreditExcelRow objects out of the uploaded excel sheet.");

					// check if the number of entries in excel file are
					// within limit

					if (rowCount == 0) {
						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.ZERO_ROWS_FOUND);
						log.error(ErrorConstants.ZERO_ROWS_FOUND.getMsg());
						return sdCashBulkCreditUploadResponse;
					} else if (rowCount > MAX_ROWS_TO_BE_PROCESSED) {

						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.MAX_ROW_COUNT_EXCEEDED);
						log.error(ErrorConstants.MAX_ROW_COUNT_EXCEEDED
								.getMsg()
								+ " Limit is "
								+ MAX_ROWS_TO_BE_PROCESSED);
						return sdCashBulkCreditUploadResponse;

					}

					// We now have the rows to process

					sdCashBulkCreditUploadResponse = creditSDCash(rawExcelList,
							activityType, templateName, uploaderEmail,
							excelFileContent, source,
							sdCashUploadRequest.getFileName(),
							sdCashBulkCreditUploadResponse, uploadedFileHash,
							sdCashUploadRequest.getContextSRO());

				}
			}

			else {
				validationService.addValidationError(
						sdCashBulkCreditUploadResponse,
						ErrorConstants.BAD_REQUEST);
				log.error(ErrorConstants.BAD_REQUEST.getMsg());
				return sdCashBulkCreditUploadResponse;
			}

		} catch (Exception e) {
			log.error(ErrorConstants.UNEXPECTED_ERROR.getMsg());
			log.error("Could not process the request due to:" , e);

			validationService.addValidationError(
					sdCashBulkCreditUploadResponse,
					ErrorConstants.UNEXPECTED_ERROR);

			return sdCashBulkCreditUploadResponse;
		}
		return sdCashBulkCreditUploadResponse;

	}
	
	/**
	 * @author shishir.shukla
	 * @category This method check whether duplicate file is uploaded oor not.
	 * @param sdCashUploadRequest
	 * @param sdCashBulkCreditUploadResponse
	 * @param uploadedFileHash
	 * @return
	 */

	private boolean isDuplicateFileUploaded(SDCashBulkCreditUploadRequest sdCashUploadRequest,
			SDCashBulkCreditUploadResponse sdCashBulkCreditUploadResponse, String uploadedFileHash) {
		
		SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO = sdCashFileUploadHistoryDao
				.findByFileNameAndHash(sdCashUploadRequest.getFileName(), uploadedFileHash);
		if (sdCashFileUploadHistoryDO != null) {
			validationService.addValidationError(
					sdCashBulkCreditUploadResponse,
					ErrorConstants.FILE_ALREADY_PRESENT.getCode(),
					"Error: This File is already been processed by: "
							+ sdCashFileUploadHistoryDO.getUploader() + " on: "
							+ sdCashFileUploadHistoryDO.getCreated());
			log.error(ErrorConstants.FILE_ALREADY_PRESENT.getMsg());
			return true;
		}
		return false;
	}

	private String getUploadedFileHash(byte[] excelFileContent) throws NoSuchAlgorithmException,
			UnsupportedEncodingException {
		return hashProviderService.generateHash(excelFileContent);
	}

	/**
	 * This method iterate over list of raw excel row and sends mail to uploader
	 * processing of whole list is completed
	 * 
	 * @param rawExcelList
	 * @param activityType
	 * @param reason
	 * @param uploaderEmail
	 * @param source
	 * @param fileName
	 * @param sdCashUploadResponse
	 * @return
	 */

	@Transactional
	private SDCashBulkCreditUploadResponse creditSDCash(
			List<RawSDCashBulkCreditExcelRow> rawExcelRowList, int activityType,
			String templateName, String uploaderEmail, byte[] excelFileContent, String source,
			String fileName, SDCashBulkCreditUploadResponse sdCashUploadResponse,
			String uploadedFileHash, RequestContextSRO contextSRO) {

		// Reading the raw excel file by iterating over it and sends mail to
		// uploader once completed

		if (rawExcelRowList != null && rawExcelRowList.size() > 0) {

			int failEmailIdCount = 0;
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap = new HashMap<ErrorConstants, List<String>>();

			String maxSDCashCredit = (CacheManager.getInstance()
					.getCache(UMSPropertiesCache.class)).getMaxSdcashCredit();

			long maxSdCashCredit = 500;

			try {
				maxSdCashCredit = Long.parseLong(maxSDCashCredit);
			} catch (Exception e) {
				log.error("Cached inocrrect value of maxSDCashCredit from database.. Proceeding with default value"
						+ maxSdCashCredit);
			}

			ListIterator<RawSDCashBulkCreditExcelRow> rawFileListIterator = rawExcelRowList
					.listIterator();
			while (rawFileListIterator.hasNext()) {
				RawSDCashBulkCreditExcelRow record = rawFileListIterator.next();
				String uniqueTxnId = uniqueTxnIdGenerator(record, fileName, uploadedFileHash);
				unProcessedSDUserEmailIDMap = processRawRecord(record, unProcessedSDUserEmailIDMap,
						activityType, templateName, uploaderEmail, source, maxSdCashCredit, uniqueTxnId, contextSRO);
			}

			if (unProcessedSDUserEmailIDMap.entrySet() != null) {
				for (Entry<ErrorConstants, List<String>> entry : unProcessedSDUserEmailIDMap
						.entrySet()) {

					failEmailIdCount = failEmailIdCount + entry.getValue().size();
				}
				log.info("Failed to process :" + failEmailIdCount + " email Ids...");
			}

			emailService.sendSDCashBulkCreditResponseEmail(uploaderEmail,
					unProcessedSDUserEmailIDMap);

			sdCashUploadResponse.setUnProcessedSDUserEmailIDMap(unProcessedSDUserEmailIDMap);
			SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO = new SDCashFileUploadHistoryDO(
					uploaderEmail, rawExcelRowList.size(), failEmailIdCount, fileName,
					excelFileContent, activityType, uploadedFileHash,"Credit");
			sdCashFileUploadHistoryDao.save(sdCashFileUploadHistoryDO);
			log.info("Finished saving details of the upload file in the db.");
			
			sendEmailToSuccessfulCredits(rawExcelRowList, templateName);

			return sdCashUploadResponse;
		} else {
			log.error("RawExcelFile is empty");
			return sdCashUploadResponse;

		}
	}
	
	/**
	 * Method to send the email for all successful credits only.
	 * @author shishir.shukla
	 */
	
	public void sendEmailToSuccessfulCredits(List<RawSDCashBulkCreditExcelRow> rawExcelRowList, String templateName){
		
		ListIterator<RawSDCashBulkCreditExcelRow> rawFileListIterator = rawExcelRowList
				.listIterator();
		while (rawFileListIterator.hasNext()) {
			RawSDCashBulkCreditExcelRow record = rawFileListIterator.next();
			if(record.getIsCredited()){
				UserSRO userSRO=null;
				try {
					String email = record.getEmail().trim();
					int expiryDays = Integer.parseInt(record.getExpiryDays());
					long amount = Long.parseLong(record.getSdCash().trim());
					userSRO = fetchUser(email);
					SDCashBulkCreditEmailRequest sdCashBulkEmailRequest = new SDCashBulkCreditEmailRequest(
							userSRO.getEmail(), (int) amount,
							expiryDays, record.getOrderId(),
							userSRO.isEmailVerified(), true); // Since user exist, the registered flag is set to true here
					
					emailService.sendUserSDCashCreditEmail(sdCashBulkEmailRequest,
							templateName);
					log.info("Email sent for SDCash credit to :"
							+ userSRO.getEmail());
				} catch (Exception e) {
					log.error("Unexpected error occured while trying to send mail to : "+ record.getEmail());
					e.printStackTrace();
				}
			}
		}
	}

	/**
	 * Method to process each excel raw row.Checks if each field value is
	 * acceptable and process the SDCash credit for acceptable emailId's.
	 * Returns the Map of a set of unprocessed emailID's corresponding to the
	 * error.
	 * 
	 * @param record
	 * @param unProcessedSDUserEmailIDMap
	 * @param activityType
	 * @param reason
	 * @param uploaderEmail
	 * @param source
	 * @param maxSdCashCredit
	 * @return
	 */

	@Transactional
	private Map<ErrorConstants, List<String>> processRawRecord(RawSDCashBulkCreditExcelRow record,
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap, int activityType,
			String templateName, String uploaderEmail, String source, long maxSdCashCredit, String uniqueId, RequestContextSRO contextSRO) {

		if (record != null) {

			CreditSDWalletRequest creditSDWalletRequest = new CreditSDWalletRequest();

			long amount = 0;
			if (UMSStringUtils.isNotNullNotEmpty(record.getEmail())) {

				String email = record.getEmail().trim();
				
				UserSRO userSRO = fetchUser(email);

				if (userSRO == null) {
					unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
							unProcessedSDUserEmailIDMap, ErrorConstants.USER_DOES_NOT_EXIST);
					//log.error(ErrorConstants.USER_DOES_NOT_EXIST.getMsg());
				} else {
					// At this point we are certain that the user exists for the
					// given mailID.

					creditSDWalletRequest.setUserId(userSRO.getId());
					try {
						amount = Long.parseLong(record.getSdCash().trim());
						if (amount > maxSdCashCredit) {
							unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
									unProcessedSDUserEmailIDMap,
									ErrorConstants.SD_CASH_LIMIT_EXCEED);
						} else if (amount <= 0) {
							log.error("Credit amount for " + userSRO.getEmail() + " is " + amount
									+ ". It has to be a greater than ZERO.");
							return addEmailIdsWithError(record, unProcessedSDUserEmailIDMap,
									ErrorConstants.INVALID_CREDIT_AMOUNT);
						} else {
							// At this point we are certain that this amount is
							// acceptable
							creditSDWalletRequest.setAmount((int) amount);

							// Assume expiryDays to be 0 -> If not set later,
							// the main process will rely on activity specific
							// expiry days
							int expiryDays = 0;

							try {
								if (record.getExpiryDays() == null
										|| record.getExpiryDays().isEmpty()) {
									// Everything is fine! Relying on expiryDays
									// = 0;
								} else {
									// Some data exists in the cell
									// corresponding to expiry days - Let us
									// parse it
									expiryDays = Integer.parseInt(record.getExpiryDays());
									if (expiryDays < 1) {

										log.error("Expiry days is lesser than 1 for "
												+ userSRO.getEmail());
										return addEmailIdsWithError(record,
												unProcessedSDUserEmailIDMap,
												ErrorConstants.EXPIRY_DAYS_LESSER_THAN_ONE);

									}
								}

								creditSDWalletRequest.setExpiryDays(expiryDays);
								creditSDWalletRequest.setActivityTypeId(activityType);
								creditSDWalletRequest.setOrderCode(record.getOrderId());
								creditSDWalletRequest.setRequestedBy(uploaderEmail);
								creditSDWalletRequest.setSource(source);
								creditSDWalletRequest.setIdempotentId(uniqueId);
								creditSDWalletRequest.setContextSRO(contextSRO);

								try {
									CreditSDWalletResponse sdResponse = sdWalletService
											.creditSDWallet(creditSDWalletRequest);
									if (sdResponse.isSuccessful()) {
										record.setIsCredited(true);
										log.info(userSRO.getEmail() + " has been credited "
												+ amount + " SD cash. TransactionID#"
												+ sdResponse.getTransactionId());
									} else {
										log.error(userSRO.getEmail()
												+ " could not be credited "
												+ amount
												+ " SD cash because sdWalletService.creditSDWallet(creditSDWalletRequest) response was un-sucessfull.");
										
										unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
												unProcessedSDUserEmailIDMap,
												ErrorConstants.UNEXPECTED_ERROR);
									}

								} catch (Exception e) {
									log.error(
											"Something unexpected went wrong while trying to credit SD cash to "
													+ userSRO.getEmail(), e);
									unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
											unProcessedSDUserEmailIDMap,
											ErrorConstants.UNEXPECTED_ERROR);
								}
							} catch (Exception e) {
								unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
										unProcessedSDUserEmailIDMap,
										ErrorConstants.INVALID_EXPIRY_DAYS);
								log.error(ErrorConstants.INVALID_EXPIRY_DAYS.getMsg(), e);
							}
						}
					} catch (Exception e) {

						unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
								unProcessedSDUserEmailIDMap, ErrorConstants.SD_CASH_INVALID);

						log.error(ErrorConstants.SD_CASH_INVALID.getMsg(), e);
					}
				}
			} else {
				log.warn("Null email encountered. Skipping this one!");
			}
		}

		else {
			log.error("Record is null");
		}
		return unProcessedSDUserEmailIDMap;
	}

	/**
	 * Calls IMS to fetch the user which in turn calls ums
	 * for details
	 * @param email
	 * @return
	 */
	private UserSRO fetchUser(String email) {
		return imsService.getUserFromIMSByEmail(email);
	}

	/**
	 * @author shishir.shukla
	 * @return String
	 */
	private synchronized String uniqueTxnIdGenerator(RawSDCashBulkCreditExcelRow record, String fileName, String hash) {
		
		String uniqueId = fileName + "_" + hash + "_" + UUID.randomUUID().toString()+ record.getRowId()+ "_" + record.getEmail(); 
		return uniqueId;
	}

	/**
	 * Returns the map containing error constants and a set of unprocessed
	 * emailIds corresponding to the error
	 * 
	 * @param record
	 * @param unProcessedSDUserEmailIDMap
	 * @param errorConstant
	 * @param failEmailIdCount
	 * @return
	 */

	private Map<ErrorConstants, List<String>> addEmailIdsWithError(
			RawSDCashBulkCreditExcelRow record,
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap,
			ErrorConstants errorConstant) {

		log.warn("Acknowledged " + errorConstant.getMsg() + " for " + record.getEmail());

		List<String> existingEmailsForThisError = unProcessedSDUserEmailIDMap.get(errorConstant);

		if (existingEmailsForThisError == null) {
			existingEmailsForThisError = new ArrayList<String>();
			unProcessedSDUserEmailIDMap.put(errorConstant, existingEmailsForThisError);
		}
		existingEmailsForThisError.add(record.getEmail());

		return unProcessedSDUserEmailIDMap;
	}

}
