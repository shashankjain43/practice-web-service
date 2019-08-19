package com.snapdeal.ums.services.SubsidiaryCashBulkUpload;

import java.io.UnsupportedEncodingException;
import java.security.NoSuchAlgorithmException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.ListIterator;
import java.util.Map;
import java.util.UUID;
import java.util.Map.Entry;

import org.apache.commons.lang.StringUtils;
import org.hibernate.HibernateException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.cache.CacheManager;
import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.ums.admin.sdCashBulkCredit.SDCashBulkCreditUploadResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.CreditSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.cache.UMSPropertiesCache;
import com.snapdeal.ums.core.dto.RawSDCashBulkCreditExcelRow;
import com.snapdeal.ums.core.entity.SDCashFileUploadHistoryDO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO.Role;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.dao.user.sdwallet.ISDCashFileUploadHistoryDAO;
import com.snapdeal.ums.dao.user.sdwallet.ISDWalletDao;
import com.snapdeal.ums.ext.user.CreateSubsidieryUserRequest;
import com.snapdeal.ums.ext.user.CreateUserRequest;
import com.snapdeal.ums.ext.user.CreateUserResponse;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.ext.user.UpdateUserRequest;
import com.snapdeal.ums.ext.user.UpdateUserResponse;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserService;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.server.subsidiary.services.SubsidiaryEmailService;
import com.snapdeal.ums.services.HashProvider;
import com.snapdeal.ums.services.RandomPasswordGenerator;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.services.sdCashBulkUpdate.SDCashBulkCreditEmailRequest;
import com.snapdeal.ums.utils.UMSStringUtils;

/***
 * Manager Class to handle all the file upload tasks related to Subsidiary
 * SDCash bulk credit.. First layer service.
 * 
 */
@Service
public class SubsidiarySDCashBulkCreditService {
	
	@Autowired
    private IMSService imsService;

	@Autowired
	private IUMSConvertorService umsConvertorService;

	@Autowired
	private IEmailServiceInternal emailService;

	@Autowired
	private SubsidiaryCashBulkCreditExcelReader freechargeCashBulkCreditExcelReader;

	@Autowired
	private ISDCashFileUploadHistoryDAO sdCashFileUploadHistoryDao;

	@Autowired
	private ISDWalletService sdWalletService;

	@Autowired
	private IUserService userService;
	
	@Autowired
    private ISDWalletDao         sdWalletDao;

	private static final Logger LOG = LoggerFactory
			.getLogger(SubsidiarySDCashBulkCreditService.class);

	private static final String EXCEL_EXTENSION = ".xls";

	private static final int MAX_ROWS_TO_BE_PROCESSED = 10000;
	// as upload history table has not null field for uploader, not sending any
	// email to this
	private static final String DUMMY_UPLOADER_EMAIL = "dummy.fc.sd.cashback.uploader@abcxyz.com";
	private static final String SD_FC_DUMMY_VERIFICATION_CODE = "dummy.fc.sd.cashback.verify.code";
	

	@Autowired
	private ValidationService validationService;

	@Autowired
	private HashProvider hashProviderService;

	/**
	 * First level service to process incoming SubsidiarySDCashUpload request
	 * from controller. Returns the final SDCashUpload Response type of object
	 * back to the controller after processing the complete request.
	 * 
	 * @param freechargeCashUploadRequest
	 * @param shouldReadAmountFromFile
	 * @return
	 */
	@AuditableMethod
	@Transactional
	public SDCashBulkCreditUploadResponse processSDCashUploadRequest(
			SubsidiaryCashBulkCreditUploadRequest freechargeCashUploadRequest,
			boolean shouldReadAmountFromFile) {

		SDCashBulkCreditUploadResponse sdCashBulkCreditUploadResponse = new SDCashBulkCreditUploadResponse();

		byte[] excelFileContent = null;
		try {
			// Applying the first level checks on request parameters
			if (freechargeCashUploadRequest != null) {

				int activityType = freechargeCashUploadRequest
						.getActivityType();
				String source = freechargeCashUploadRequest.getSource();
				String creditSDCashTemplateName = freechargeCashUploadRequest
						.getCreditSDCashEmailTemplateName();
				String accountCreationEmailTemplateName = freechargeCashUploadRequest
						.getAccountCreationEmailTemplateName();
				if (creditSDCashTemplateName == null
						|| accountCreationEmailTemplateName == null
						|| accountCreationEmailTemplateName.isEmpty()
						|| creditSDCashTemplateName.isEmpty()) {
					LOG.info("Email TemplateName field is empty.. Could not process the request");
					validationService.addValidationError(
							sdCashBulkCreditUploadResponse,
							ErrorConstants.EMAIL_TEMPLATE_IS_NOT_AVAILABLE);
					LOG.error(ErrorConstants.EMAIL_TEMPLATE_IS_NOT_AVAILABLE
							.getMsg());
					sdCashBulkCreditUploadResponse.setSuccessful(false);
					return sdCashBulkCreditUploadResponse;
				}

				else {

					String uploaderEmail = DUMMY_UPLOADER_EMAIL;
					// check if the file was uploaded and set the corresponding
					// error constant.

					if (freechargeCashUploadRequest.getFileContent() == null
							|| freechargeCashUploadRequest.getFileContent().length == 0) {

						sdCashBulkCreditUploadResponse.setSuccessful(false);
						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY);
						LOG.error(ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY
								.getMsg());
						return sdCashBulkCreditUploadResponse;

					}
					// check the file extension is other than xls.

					else if (!(freechargeCashUploadRequest.getFileName()
							.toLowerCase().endsWith(EXCEL_EXTENSION))) {

						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.NOT_AN_EXCEL_FILE);
						LOG.error(ErrorConstants.NOT_AN_EXCEL_FILE.getMsg());
						return sdCashBulkCreditUploadResponse;

					}

					excelFileContent = freechargeCashUploadRequest
							.getFileContent();

					/*
					 * To generate hash of the uploaded file and check whether
					 * file is already been processed or not.
					 */
					String uploadedFileHash = getUploadedFileHash(excelFileContent);

					if (isDuplicateFileUploaded(freechargeCashUploadRequest,
							sdCashBulkCreditUploadResponse, uploadedFileHash)) {
						return sdCashBulkCreditUploadResponse;
					}

					// Now process the file contents in bytes

					List<RawSDCashBulkCreditExcelRow> rawExcelList = freechargeCashBulkCreditExcelReader
							.extractRawSDCashCreditRow(excelFileContent,
									freechargeCashUploadRequest
											.getDefaultAmount(),
									shouldReadAmountFromFile,
									sdCashBulkCreditUploadResponse);

					int rowCount = 0;

					if (rawExcelList != null && rawExcelList.size() > 0) {
						rowCount = rawExcelList.size();
					}

					LOG.info("Created "
							+ rowCount
							+ " RawSDCashBulkCreditExcelRow objects out of the uploaded excel sheet.");

					// check if the number of entries in excel file are
					// within limit

					if (rowCount == 0) {
						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.ZERO_ROWS_FOUND);
						LOG.error(ErrorConstants.ZERO_ROWS_FOUND.getMsg());
						sdCashBulkCreditUploadResponse.setSuccessful(false);
						return sdCashBulkCreditUploadResponse;
					} else if (rowCount > MAX_ROWS_TO_BE_PROCESSED) {

						validationService.addValidationError(
								sdCashBulkCreditUploadResponse,
								ErrorConstants.MAX_ROW_COUNT_EXCEEDED);
						LOG.error(ErrorConstants.MAX_ROW_COUNT_EXCEEDED
								.getMsg()
								+ " Limit is "
								+ MAX_ROWS_TO_BE_PROCESSED);
						sdCashBulkCreditUploadResponse.setSuccessful(false);
						return sdCashBulkCreditUploadResponse;

					}

					// We now have the rows to process
					sdCashBulkCreditUploadResponse = createUserAndCreditSDCash(
							rawExcelList, activityType,
							creditSDCashTemplateName, uploaderEmail,
							excelFileContent, source,
							freechargeCashUploadRequest.getFileName(),
							sdCashBulkCreditUploadResponse, uploadedFileHash,
							freechargeCashUploadRequest.getContextSRO(),
							accountCreationEmailTemplateName);
				}
			} else {
				validationService.addValidationError(
						sdCashBulkCreditUploadResponse,
						ErrorConstants.BAD_REQUEST);
				LOG.error(ErrorConstants.BAD_REQUEST.getMsg());
			}

		} catch (Exception e) {
			LOG.error(ErrorConstants.UNEXPECTED_ERROR.getMsg());
			LOG.error("Could not process the request due to:", e);
			validationService.addValidationError(
					sdCashBulkCreditUploadResponse,
					ErrorConstants.UNEXPECTED_ERROR);
		}

		return sdCashBulkCreditUploadResponse;

	}

	/**
	 * @author
	 * @category This method check whether duplicate file is uploaded or not.
	 * @param sdCashUploadRequest
	 * @param sdCashBulkCreditUploadResponse
	 * @param uploadedFileHash
	 * @return
	 */

	private boolean isDuplicateFileUploaded(
			SubsidiaryCashBulkCreditUploadRequest sdCashUploadRequest,
			SDCashBulkCreditUploadResponse sdCashBulkCreditUploadResponse,
			String uploadedFileHash) {
		
		List<SDCashFileUploadHistoryDO> histDOs = sdCashFileUploadHistoryDao.
				getFileUploadHistoryByFileName(sdCashUploadRequest.getFileName());
		if(histDOs !=null && !histDOs.isEmpty()){
			validationService.addValidationError(
					sdCashBulkCreditUploadResponse,
					ErrorConstants.FILE_ALREADY_PRESENT.getCode(),
					"Error: This File is already been processed ");
			LOG.error(ErrorConstants.FILE_ALREADY_PRESENT.getMsg()+" , Criteria: only fileName");
			return true;
		}

		SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO = sdCashFileUploadHistoryDao
				.findByFileNameAndHash(sdCashUploadRequest.getFileName(),
						uploadedFileHash);
		if (sdCashFileUploadHistoryDO != null) {
			validationService.addValidationError(
					sdCashBulkCreditUploadResponse,
					ErrorConstants.FILE_ALREADY_PRESENT.getCode(),
					"Error: This File is already been processed by: "
							+ sdCashFileUploadHistoryDO.getUploader() + " on: "
							+ sdCashFileUploadHistoryDO.getCreated());
			LOG.error(ErrorConstants.FILE_ALREADY_PRESENT.getMsg()+" , Criteria: fileName and filehash");
			return true;
		}
		return false;
	}

	private String getUploadedFileHash(byte[] excelFileContent)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return hashProviderService.generateHash(excelFileContent);
	}

	/**
	 * This method iterate over list of raw excel row and sends mail to uploader
	 * processing of whole list is completed
	 * 
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

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private SDCashBulkCreditUploadResponse createUserAndCreditSDCash(
			List<RawSDCashBulkCreditExcelRow> rawExcelRowList,
			int activityType, String creditSDTemplateName,
			String uploaderEmail, byte[] excelFileContent, String source,
			String fileName,
			SDCashBulkCreditUploadResponse sdCashUploadResponse,
			String uploadedFileHash, RequestContextSRO contextSRO,
			String accountCreationTemplateName) {

		// Reading the raw excel file by iterating over it and sends mail to
		// uploader once completed

		if (rawExcelRowList != null && rawExcelRowList.size() > 0) {

			int failEmailIdCount = 0;
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap = new HashMap<ErrorConstants, List<String>>();

			String maxSDCashCreditDBString = (CacheManager.getInstance()
					.getCache(UMSPropertiesCache.class)).getMaxSdcashCredit();

			long maxSdCashCredit = 500;

			try {
				maxSdCashCredit = Long.parseLong(maxSDCashCreditDBString);
			} catch (Exception e) {
				LOG.error("Cached inocrrect value of maxSDCashCredit from database.. Proceeding with default value"
						+ maxSdCashCredit);
			}

			ListIterator<RawSDCashBulkCreditExcelRow> rawFileListIterator = rawExcelRowList
					.listIterator();
			while (rawFileListIterator.hasNext()) {
				RawSDCashBulkCreditExcelRow record = rawFileListIterator.next();
				String uniqueTxnId = uniqueTxnIdGenerator(record, fileName,
						uploadedFileHash);
				try {
					unProcessedSDUserEmailIDMap = processRawRecord(record,
							unProcessedSDUserEmailIDMap, activityType,
							creditSDTemplateName, uploaderEmail, source,
							maxSdCashCredit, uniqueTxnId, contextSRO,
							accountCreationTemplateName);
				} catch (Exception e) {
					LOG.error("Record not processed due to exception: "
							+ record.toString(),e);
					unProcessedSDUserEmailIDMap = addEmailIdsWithError(
							record, unProcessedSDUserEmailIDMap,
							ErrorConstants.UNEXPECTED_ERROR);
				}

			}

			if (unProcessedSDUserEmailIDMap.entrySet() != null) {
				for (Entry<ErrorConstants, List<String>> entry : unProcessedSDUserEmailIDMap
						.entrySet()) {

					failEmailIdCount = failEmailIdCount
							+ entry.getValue().size();
				}
				LOG.info("Failed to process :" + failEmailIdCount
						+ " email Ids...");
				LOG.info(unProcessedSDUserEmailIDMap.toString());
			}

			sdCashUploadResponse
					.setUnProcessedSDUserEmailIDMap(unProcessedSDUserEmailIDMap);
			SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO = new SDCashFileUploadHistoryDO(
					uploaderEmail, rawExcelRowList.size(), failEmailIdCount,
					fileName, excelFileContent, activityType, uploadedFileHash,"Credit");
			sdCashFileUploadHistoryDao.save(sdCashFileUploadHistoryDO);
			LOG.info("Finished saving details of the upload file in the db.");

			//sendEmailToSuccessfulCredits(rawExcelRowList, creditSDTemplateName);

			return sdCashUploadResponse;
		} else {
			LOG.error("RawExcelFile is empty");
			return sdCashUploadResponse;

		}
	}

	/**
	 * Method to send the email for all successful credits only.
	 * 
	 * @author
	 */

	public void sendEmailToSuccessfulCredits(
			List<RawSDCashBulkCreditExcelRow> rawExcelRowList,
			String templateName) {

		ListIterator<RawSDCashBulkCreditExcelRow> rawFileListIterator = rawExcelRowList
				.listIterator();
		while (rawFileListIterator.hasNext()) {
			RawSDCashBulkCreditExcelRow record = rawFileListIterator.next();
			String email = null;
			if (record != null) {
				if (record.getIsCredited()) {
					try {
						if (record.getEmail() != null) {
							email = record.getEmail().trim();
						}

						int expiryDays = Integer.parseInt(record
								.getExpiryDays());
						long amount = Long.parseLong(record.getSdCash().trim());
						UserSRO userSRO = fetchUser(email);
						SDCashBulkCreditEmailRequest sdCashBulkEmailRequest = new SDCashBulkCreditEmailRequest(
								userSRO.getEmail(), (int) amount, expiryDays,
								record.getOrderId(), userSRO.isEmailVerified(),
								userSRO.isEmailVerified());
						emailService.sendUserSDCashCreditEmail(
								sdCashBulkEmailRequest, templateName);
						LOG.info("Email sent for SDCash credit to :"
								+ record.getEmail());
					} catch (Exception e) {
						LOG.error(
								"Unexpected error occured while trying to send mail to : "
										+ record.getEmail(), e);
					}
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

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Map<ErrorConstants, List<String>> processRawRecord(
			RawSDCashBulkCreditExcelRow record,
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap,
			int activityType, String templateName, String uploaderEmail,
			String source, long maxSdCashCredit, String uniqueId,
			RequestContextSRO contextSRO, String accountCreationTemplateName) {

		if (record != null) {

			CreditSDWalletRequest creditSDWalletRequest = new CreditSDWalletRequest();

			long amount = 0;
			if (UMSStringUtils.isNotNullNotEmpty(record.getEmail())) {

				String email = record.getEmail().trim();
				
				UserSRO userSRO = fetchUser(email);

				if (userSRO == null) {
					LOG.info("Email: " + email + ""
							+ ErrorConstants.USER_DOES_NOT_EXIST.getMsg()
							+ ", Creating Account for the same");
					String defaultPwd = createDefaultPassword();
					CreateUserResponse createUserResponse = new CreateUserResponse();
					CreateSubsidieryUserRequest request = new CreateSubsidieryUserRequest();
					request.setEmail(email);
					request.setPassword(defaultPwd);
					request.setSource(source);
					request.setInitialRole(Role.REGISTERED);
					request.setAutocreated(true);
					request.setEmailVarified(true);
					request.setEmailVerificationCode(SD_FC_DUMMY_VERIFICATION_CODE);
					try {
						createUserResponse = userService.createSubsidieryUser(request);
					} catch (Exception e) {
						LOG.error("Exception occured while creating User by Email");
						unProcessedSDUserEmailIDMap = addEmailIdsWithError(
								record, unProcessedSDUserEmailIDMap,
								ErrorConstants.USER_DOES_NOT_GET_CREATED);
						return unProcessedSDUserEmailIDMap;
					}
					if (createUserResponse.isSuccessful()) {
						LOG.info("createUserResponse is successful, now sending email");
						userSRO = createUserResponse.getCreateUser();
						try {
							emailService.sendAccountCreationEmail(email,
									defaultPwd, accountCreationTemplateName);
						} catch (Exception e) {
							LOG.error(
									"Exception while using subsidiaryService for sending mail to:"
											+ email, e);
						}
					} else {
						LOG.info("createUserResponse is not successful");
						unProcessedSDUserEmailIDMap = addEmailIdsWithError(
								record, unProcessedSDUserEmailIDMap,
								ErrorConstants.USER_DOES_NOT_GET_CREATED);
						return unProcessedSDUserEmailIDMap;
					}
				}
				// At this point we are certain that the user exists for the
				// given mailID.

				creditSDWalletRequest.setUserId(userSRO.getId());
				try {
					amount = Long.parseLong(record.getSdCash().trim());
					if (amount > maxSdCashCredit) {
						unProcessedSDUserEmailIDMap = addEmailIdsWithError(
								record, unProcessedSDUserEmailIDMap,
								ErrorConstants.SD_CASH_LIMIT_EXCEED);
					} else if (amount <= 0) {
						LOG.error("Credit amount for " + userSRO.getEmail()
								+ " is " + amount
								+ ". It has to be a greater than ZERO.");
						return addEmailIdsWithError(record,
								unProcessedSDUserEmailIDMap,
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
								expiryDays = Integer.parseInt(record
										.getExpiryDays());
								if (expiryDays < 1) {

									LOG.error("Expiry days is lesser than 1 for "
											+ userSRO.getEmail());
									return addEmailIdsWithError(
											record,
											unProcessedSDUserEmailIDMap,
											ErrorConstants.EXPIRY_DAYS_LESSER_THAN_ONE);

								}
							}

							creditSDWalletRequest.setExpiryDays(expiryDays);
							creditSDWalletRequest
									.setActivityTypeId(activityType);
							creditSDWalletRequest.setOrderCode(record
									.getOrderId());
							creditSDWalletRequest.setRequestedBy(uploaderEmail);
							creditSDWalletRequest.setSource(source);
							creditSDWalletRequest
									.setIdempotentId(uniqueId);
							creditSDWalletRequest.setContextSRO(contextSRO);

							try {
								CreditSDWalletResponse sdResponse = sdWalletService
										.creditSDWallet(creditSDWalletRequest);
								if (sdResponse.isSuccessful()) {
									record.setIsCredited(true);
									LOG.info(userSRO.getEmail()
											+ " has been credited " + amount
											+ " SD cash. TransactionID#"
											+ sdResponse.getTransactionId());
								    sendSdCashCreditEmail(templateName,
												amount, userSRO.getEmail(), expiryDays, activityType);
									
								} else {
									LOG.error(userSRO.getEmail()
											+ " could not be credited "
											+ amount
											+ " as CreditSDWalletResponse was un-sucessfull.");
								}

							} catch (Exception e) {
								LOG.error(
										"Something unexpected went wrong while trying to credit SD cash to "
												+ userSRO.getEmail(), e);
								unProcessedSDUserEmailIDMap = addEmailIdsWithError(
										record, unProcessedSDUserEmailIDMap,
										ErrorConstants.UNEXPECTED_ERROR);
							}
						} catch (Exception e) {
							unProcessedSDUserEmailIDMap = addEmailIdsWithError(
									record, unProcessedSDUserEmailIDMap,
									ErrorConstants.INVALID_EXPIRY_DAYS);
							LOG.error(
									ErrorConstants.INVALID_EXPIRY_DAYS.getMsg(),
									e);
						}
					}
				} catch (Exception e) {

					unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
							unProcessedSDUserEmailIDMap,
							ErrorConstants.SD_CASH_INVALID);

					LOG.error(ErrorConstants.SD_CASH_INVALID.getMsg(), e);
				}
			} else {
				LOG.warn("Null email encountered. Skipping this one!");
			}
		}

		else {
			LOG.error("Record is null");
		}
		return unProcessedSDUserEmailIDMap;
	}

	@Transactional
	private void sendSdCashCreditEmail(String templateName, long amount,
			String email, int expiryDays, int activityType) {
		try{
			SDCashBulkCreditEmailRequest request = new SDCashBulkCreditEmailRequest();
			request.setEmail(email);
			request.setSdCashAmount((int) amount);
			if(expiryDays>0){
				request.setExpiryDays(expiryDays);
			}else{
				request.setExpiryDays(sdWalletDao.getExpiryDaysCorrespondingToActivity(activityType));
			}
			emailService.sendUserSDCashCreditEmail(
					request, templateName);
			LOG.info("Email sent for SDCash credit to :"
					+ email);
		} catch (Exception e) {
			LOG.error("Unexpected error occured while trying to send mail to: "
							+ email, e);
		}
	}

	private String createDefaultPassword() {
		int minLen = 8;
		int maxLen = 8;
		int noOfCAPSAlpha = 1;
		int noOfDigits = 1;
		int noOfSplChars = 0;
		char[] pswd = RandomPasswordGenerator.generatePswd(minLen, maxLen,
				noOfCAPSAlpha, noOfDigits, noOfSplChars);
		return new String(pswd);
	}

	/**
	 * @author
	 * @return String
	 */
	private synchronized String uniqueTxnIdGenerator(
			RawSDCashBulkCreditExcelRow record, String fileName, String hash) {

		String uniqueId = fileName + "_" + hash + "_" + UUID.randomUUID().toString()+ record.getRowId() + "_"
				+ record.getEmail();
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

		LOG.warn("Acknowledged " + errorConstant.getMsg() + " for "
				+ record.getEmail());

		List<String> existingEmailsForThisError = unProcessedSDUserEmailIDMap
				.get(errorConstant);

		if (existingEmailsForThisError == null) {
			existingEmailsForThisError = new ArrayList<String>();
			unProcessedSDUserEmailIDMap.put(errorConstant,
					existingEmailsForThisError);
		}
		existingEmailsForThisError.add(record.getEmail());

		return unProcessedSDUserEmailIDMap;
	}
	
	private UserSRO fetchUser(String email) {
		return imsService.getUserFromIMSByEmail(email);
	}

}
