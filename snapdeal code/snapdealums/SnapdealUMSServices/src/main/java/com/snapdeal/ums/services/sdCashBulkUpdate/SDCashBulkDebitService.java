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
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import com.snapdeal.base.audit.annotation.AuditableMethod;
import com.snapdeal.base.services.request.context.RequestContextSRO;
import com.snapdeal.ums.admin.sdCashBulkDebit.SDCashBulkDebitUploadRequest;
import com.snapdeal.ums.admin.sdCashBulkDebit.SDCashBulkDebitUploadResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.DebitSDWalletResponse;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdRequest;
import com.snapdeal.ums.admin.sdwallet.ext.sdwallet.GetAvailableBalanceInSDWalletByUserIdResponse;
import com.snapdeal.ums.admin.sdwallet.server.services.ISDWalletService;
import com.snapdeal.ums.constants.ErrorConstants;
import com.snapdeal.ums.core.dto.RawSDCashBulkDebitExcelRow;
import com.snapdeal.ums.core.entity.SDCashFileUploadHistoryDO;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.dao.user.sdwallet.ISDCashFileUploadHistoryDAO;
import com.snapdeal.ums.ext.user.GetUserByEmailRequest;
import com.snapdeal.ums.ext.user.GetUserByEmailResponse;
import com.snapdeal.ums.server.services.IEmailServiceInternal;
import com.snapdeal.ums.server.services.IUserService;
import com.snapdeal.ums.server.services.convertor.IUMSConvertorService;
import com.snapdeal.ums.server.services.impl.IMSService;
import com.snapdeal.ums.services.HashProvider;
import com.snapdeal.ums.services.ValidationService;
import com.snapdeal.ums.utils.UMSStringUtils;

@Service
public class SDCashBulkDebitService {
	
	@Autowired
    private IMSService imsService;

	@Autowired
	private IUMSConvertorService umsConvertorService;

	@Autowired
	private IEmailServiceInternal emailService;

	@Autowired
	private SDCashBulkDebitExcelReader sdCashBulkDebitExcelReader;

	@Autowired
	private ISDCashFileUploadHistoryDAO sdCashFileUploadHistoryDao;

	@Autowired
	private ISDWalletService sdWalletService;

	@Autowired
	private IUserService userService;

	private static final Logger LOG = LoggerFactory
			.getLogger(SDCashBulkDebitService.class);

	private static final String EXCEL_EXTENSION = ".xls";

	private static final int MAX_ROWS_TO_BE_PROCESSED = 10000;

	@Autowired
	private ValidationService validationService;

	@Autowired
	private HashProvider hashProviderService;

	@AuditableMethod
	@Transactional
	public SDCashBulkDebitUploadResponse processSDCashUploadRequest(
			SDCashBulkDebitUploadRequest sdCashUploadRequest, String source) {

		SDCashBulkDebitUploadResponse sdCashBulkDebitUploadResponse = new SDCashBulkDebitUploadResponse();

		byte[] excelFileContent = null;
		try {
			// Applying the first level checks on request parameters
			if (sdCashUploadRequest != null) {

				if (sdCashUploadRequest.getActivityType() > 0) {

					int activityType = sdCashUploadRequest.getActivityType();

					String uploaderEmail = sdCashUploadRequest
							.getUploadersEmail();

					// check if the file was uploaded and set the corresponding
					// error constant.

					if (sdCashUploadRequest.getFileContent() == null
							|| sdCashUploadRequest.getFileContent().length == 0) {

						validationService.addValidationError(
								sdCashBulkDebitUploadResponse,
								ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY);
						LOG.error(ErrorConstants.FILE_NOT_PRESENT_OR_EMPTY
								.getMsg());
						return sdCashBulkDebitUploadResponse;

					}
					// check the file extension is other than xls.

					else if (!(sdCashUploadRequest.getFileName().toLowerCase()
							.endsWith(EXCEL_EXTENSION))) {

						validationService.addValidationError(
								sdCashBulkDebitUploadResponse,
								ErrorConstants.NOT_AN_EXCEL_FILE);
						LOG.error(ErrorConstants.NOT_AN_EXCEL_FILE.getMsg());
						return sdCashBulkDebitUploadResponse;

					}

					else {

						excelFileContent = sdCashUploadRequest.getFileContent();

						/*
						 * To generate hash of the uploaded file and check
						 * whether file is already been processed or not.
						 */
						String uploadedFileHash = getUploadedFileHash(excelFileContent);

						if (isDuplicateFileUploaded(sdCashUploadRequest,
								sdCashBulkDebitUploadResponse, uploadedFileHash)) {
							return sdCashBulkDebitUploadResponse;
						}

						// Now process the file contents in bytes

						List<RawSDCashBulkDebitExcelRow> rawExcelList = sdCashBulkDebitExcelReader
								.extractRawSDCashDebitRow(excelFileContent,
										sdCashBulkDebitUploadResponse);

						int rowCount = 0;

						if (rawExcelList != null && rawExcelList.size() > 0) {
							rowCount = rawExcelList.size();
						}

						LOG.info("Created "
								+ rowCount
								+ " RawSDCashBulkDebitExcelRow objects out of the uploaded excel sheet.");

						// check if the number of entries in excel file are
						// within limit

						if (rowCount == 0) {
							validationService.addValidationError(
									sdCashBulkDebitUploadResponse,
									ErrorConstants.ZERO_ROWS_FOUND);
							LOG.error(ErrorConstants.ZERO_ROWS_FOUND.getMsg());
							return sdCashBulkDebitUploadResponse;
						} else if (rowCount > MAX_ROWS_TO_BE_PROCESSED) {

							validationService.addValidationError(
									sdCashBulkDebitUploadResponse,
									ErrorConstants.MAX_ROW_COUNT_EXCEEDED);
							LOG.error(ErrorConstants.MAX_ROW_COUNT_EXCEEDED
									.getMsg()
									+ " Limit is "
									+ MAX_ROWS_TO_BE_PROCESSED);
							return sdCashBulkDebitUploadResponse;

						}

						// We now have the rows to process

						sdCashBulkDebitUploadResponse = deditSDCash(
								rawExcelList, activityType, uploaderEmail,
								excelFileContent, source,
								sdCashUploadRequest.getFileName(),
								sdCashBulkDebitUploadResponse,
								uploadedFileHash,
								sdCashUploadRequest.getContextSRO());

					}
				}
			} else {
				validationService.addValidationError(
						sdCashBulkDebitUploadResponse,
						ErrorConstants.BAD_REQUEST);
				LOG.error(ErrorConstants.BAD_REQUEST.getMsg());
				return sdCashBulkDebitUploadResponse;
			}
		} catch (Exception e) {
			LOG.error(ErrorConstants.UNEXPECTED_ERROR.getMsg());
			LOG.error("Could not process the request due to:" , e);
			validationService.addValidationError(sdCashBulkDebitUploadResponse,
					ErrorConstants.UNEXPECTED_ERROR);

			return sdCashBulkDebitUploadResponse;
		}
		return sdCashBulkDebitUploadResponse;

	}

	@Transactional
	private SDCashBulkDebitUploadResponse deditSDCash(
			List<RawSDCashBulkDebitExcelRow> rawExcelList, int activityType,
			String uploaderEmail, byte[] excelFileContent, String source,
			String fileName,
			SDCashBulkDebitUploadResponse sdCashBulkDebitUploadResponse,
			String uploadedFileHash, RequestContextSRO contextSRO) {

		// Reading the raw excel file by iterating over it and sends mail to
		// uploader once completed

		if (rawExcelList != null && rawExcelList.size() > 0) {

			int failEmailIdCount = 0;
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap = new HashMap<ErrorConstants, List<String>>();

			ListIterator<RawSDCashBulkDebitExcelRow> rawFileListIterator = rawExcelList
					.listIterator();
			while (rawFileListIterator.hasNext()) {
				RawSDCashBulkDebitExcelRow record = rawFileListIterator.next();
				String uniqueTxnId = uniqueTxnIdGenerator(record, fileName,
						uploadedFileHash);
				try {
					unProcessedSDUserEmailIDMap = processRawRecord(record,
							unProcessedSDUserEmailIDMap, activityType,
							uploaderEmail, source, uniqueTxnId, contextSRO);
				} catch (Exception e) {
					LOG.error(
							"Something unexpected went wrong while trying to debit SD cash to "
									+ record.getEmail(), e);
					unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
							unProcessedSDUserEmailIDMap,
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
			}

			sdCashBulkDebitUploadResponse
					.setUnProcessedSDUserEmailIDMap(unProcessedSDUserEmailIDMap);
			SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO = new SDCashFileUploadHistoryDO(
					uploaderEmail, rawExcelList.size(), failEmailIdCount,
					fileName, excelFileContent, activityType, uploadedFileHash,
					"Debit");
			sdCashFileUploadHistoryDao.save(sdCashFileUploadHistoryDO);
			LOG.info("Finished saving details of the upload file in the db.");

			emailService.sendSDCashBulkDebitResponseEmail(uploaderEmail,
					unProcessedSDUserEmailIDMap);

			return sdCashBulkDebitUploadResponse;
		} else {
			LOG.error("RawExcelFile is empty");
			return sdCashBulkDebitUploadResponse;

		}
	}

	@Transactional(propagation = Propagation.REQUIRES_NEW)
	private Map<ErrorConstants, List<String>> processRawRecord(
			RawSDCashBulkDebitExcelRow record,
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap,
			int activityType, String uploaderEmail, String source,
			String uniqueTxnId, RequestContextSRO contextSRO) {

		if (record != null) {

			DebitSDWalletRequest debitSDWalletRequest = new DebitSDWalletRequest();

			long amount = 0;
			if (UMSStringUtils.isNotNullNotEmpty(record.getEmail())) {

				String email = record.getEmail().trim();

				UserSRO userSRO = fetchUser(email);

				if (userSRO == null) {
					unProcessedSDUserEmailIDMap = addEmailIdsWithError(record,
							unProcessedSDUserEmailIDMap,
							ErrorConstants.USER_DOES_NOT_EXIST);
					LOG.error(ErrorConstants.USER_DOES_NOT_EXIST.getMsg());
				} else {
					// At this point we are certain that the user exists for the
					// given mailID.

					debitSDWalletRequest.setUserId(userSRO.getId());
					try {
						if (UMSStringUtils.isNullOrEmpty(record.getSdCash())) {
							LOG.error("Debit amount for " + userSRO.getEmail()
									+ " is null or empty. ");
							return addEmailIdsWithError(record,
									unProcessedSDUserEmailIDMap,
									ErrorConstants.SD_CASH_INVALID);
						}

						else {
							amount = Long.parseLong(record.getSdCash().trim());
							if (amount <= 0) {
								LOG.error("Debit amount for "
										+ userSRO.getEmail() + " is " + amount
										+ ". It has to be a greater than ZERO.");
								return addEmailIdsWithError(record,
										unProcessedSDUserEmailIDMap,
										ErrorConstants.INVALID_CREDIT_AMOUNT);
							} else {
								// At this point we are certain that this amount
								// to be debited is
								// acceptable
								// first check if user has enough balance
								GetAvailableBalanceInSDWalletByUserIdRequest req = new GetAvailableBalanceInSDWalletByUserIdRequest();
								req.setUserId(userSRO.getId());
								GetAvailableBalanceInSDWalletByUserIdResponse res = sdWalletService
										.getAvailableBalanceInSDWalletByUserId(req);
								if (res != null) {
									if (res.getAvailableAmount() == null
											|| res.getAvailableAmount() <= 0
											|| res.getAvailableAmount() < amount) {
										LOG.error("Insufficient or Zero balance for email: "
												+ userSRO.getEmail());
										return addEmailIdsWithError(record,
												unProcessedSDUserEmailIDMap,
												ErrorConstants.INSUFFICIENT_OR_ZERO_BALANCE);
									}
								}

								// Now debit the cash
								debitSDWalletRequest.setAmount((int) amount);
								debitSDWalletRequest
										.setActivityTypeId(activityType);
								debitSDWalletRequest.setOrderCode(record
										.getOrderId());
								debitSDWalletRequest
										.setRequestedBy(uploaderEmail);
								debitSDWalletRequest.setSource(source);
								debitSDWalletRequest
										.setIdempotentId(uniqueTxnId);
								debitSDWalletRequest.setContextSRO(contextSRO);

								try {
									DebitSDWalletResponse sdResponse = sdWalletService
											.debitSDWallet(debitSDWalletRequest);
									if (sdResponse.isSuccessful()) {
										record.setIsDebited(true);
										LOG.info(userSRO.getEmail()
												+ " has been debited " + amount
												+ " SD cash. TransactionID#"
												+ sdResponse.getTransactionId());
									} else {
										LOG.error(userSRO.getEmail()
												+ " could not be debited "
												+ amount
												+ " SD cash because sdWalletService.debitSDWallet(debitSDWalletRequest) response was un-sucessfull.");

									}
								} catch (Exception e) {
									LOG.error(
											"Something unexpected went wrong while trying to debit SD cash to "
													+ userSRO.getEmail(), e);
									unProcessedSDUserEmailIDMap = addEmailIdsWithError(
											record,
											unProcessedSDUserEmailIDMap,
											ErrorConstants.UNEXPECTED_ERROR);
								}
							}
						}
					} catch (Exception e) {
						unProcessedSDUserEmailIDMap = addEmailIdsWithError(
								record, unProcessedSDUserEmailIDMap,
								ErrorConstants.SD_CASH_INVALID);

						LOG.error(ErrorConstants.SD_CASH_INVALID.getMsg(), e);
					}
				}
			} else {
				LOG.warn("Null email encountered. Skipping this one!");
			}
		} else {
			LOG.error("Record is null");
		}
		return unProcessedSDUserEmailIDMap;
	}

	private Map<ErrorConstants, List<String>> addEmailIdsWithError(
			RawSDCashBulkDebitExcelRow record,
			Map<ErrorConstants, List<String>> unProcessedSDUserEmailIDMap,
			ErrorConstants errorConstants) {
		LOG.warn("Acknowledged " + errorConstants.getMsg() + " for "
				+ record.getEmail());

		List<String> existingEmailsForThisError = unProcessedSDUserEmailIDMap
				.get(errorConstants);

		if (existingEmailsForThisError == null) {
			existingEmailsForThisError = new ArrayList<String>();
			unProcessedSDUserEmailIDMap.put(errorConstants,
					existingEmailsForThisError);
		}
		existingEmailsForThisError.add(record.getEmail());

		return unProcessedSDUserEmailIDMap;

	}

	private String uniqueTxnIdGenerator(RawSDCashBulkDebitExcelRow record,
			String fileName, String uploadedFileHash) {
		String uniqueId = fileName + "_" + uploadedFileHash + "_"+ UUID.randomUUID().toString()
				+ record.getRowId() + "_" + record.getEmail();
		return uniqueId;
	}

	private boolean isDuplicateFileUploaded(
			SDCashBulkDebitUploadRequest sdCashUploadRequest,
			SDCashBulkDebitUploadResponse sdCashBulkDebitUploadResponse,
			String uploadedFileHash) {
		SDCashFileUploadHistoryDO sdCashFileUploadHistoryDO = sdCashFileUploadHistoryDao
				.findByFileNameAndHash(sdCashUploadRequest.getFileName(),
						uploadedFileHash);
		if (sdCashFileUploadHistoryDO != null) {
			validationService.addValidationError(sdCashBulkDebitUploadResponse,
					ErrorConstants.FILE_ALREADY_PRESENT.getCode(),
					"Error: This File is already been processed by: "
							+ sdCashFileUploadHistoryDO.getUploader() + " on: "
							+ sdCashFileUploadHistoryDO.getCreated());
			LOG.error(ErrorConstants.FILE_ALREADY_PRESENT.getMsg());
			return true;
		}
		return false;
	}
	
	private UserSRO fetchUser(String email) {
		return imsService.getUserFromIMSByEmail(email);
	}

	private String getUploadedFileHash(byte[] excelFileContent)
			throws NoSuchAlgorithmException, UnsupportedEncodingException {
		return hashProviderService.generateHash(excelFileContent);
	}

}
