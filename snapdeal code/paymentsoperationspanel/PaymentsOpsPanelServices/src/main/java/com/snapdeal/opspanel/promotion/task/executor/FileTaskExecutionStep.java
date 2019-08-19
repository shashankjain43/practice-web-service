//package com.snapdeal.opspanel.promotion.task.executor;
//
//import java.io.BufferedReader;
//import java.io.BufferedWriter;
//import java.io.File;
//import java.io.FileInputStream;
//import java.io.FileNotFoundException;
//import java.io.FileWriter;
//import java.io.IOException;
//import java.io.InputStreamReader;
//import java.math.BigDecimal;
//import java.sql.Timestamp;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.List;
//
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.stereotype.Component;
//
//import com.google.gson.Gson;
//import com.snapdeal.ims.dto.UserDetailsDTO;
//import com.snapdeal.ims.exception.HttpTransportException;
//import com.snapdeal.ims.exception.ServiceException;
//import com.snapdeal.opspanel.promotion.enums.FileState;
//import com.snapdeal.opspanel.promotion.enums.InstrumentType;
//import com.snapdeal.opspanel.promotion.enums.TaskState;
//import com.snapdeal.opspanel.promotion.exception.WalletServiceException;
//import com.snapdeal.opspanel.promotion.model.FileMetaEntity;
//import com.snapdeal.opspanel.promotion.model.NotificationRequestModel;
//import com.snapdeal.opspanel.promotion.model.OutputResponse;
//import com.snapdeal.opspanel.promotion.request.BulkTaskRequest;
//import com.snapdeal.opspanel.promotion.request.FileRow;
//import com.snapdeal.opspanel.promotion.request.FormData;
//import com.snapdeal.opspanel.promotion.service.CSVService;
//import com.snapdeal.opspanel.promotion.service.FileMetaService;
//import com.snapdeal.opspanel.promotion.service.IMSService;
//import com.snapdeal.opspanel.promotion.service.NotificationService;
//import com.snapdeal.opspanel.promotion.service.OutputExcelService;
//import com.snapdeal.opspanel.promotion.utils.AWSConfiguration;
//import com.snapdeal.opspanel.promotion.utils.AmazonS3Utils;
//import com.snapdeal.opspanel.promotion.utils.OPSUtils;
//import com.snapdeal.payments.notification.utility.ChannelType;
//import com.snapdeal.payments.sdmoney.client.SDMoneyClient;
//import com.snapdeal.payments.sdmoney.exceptions.InternalClientException;
//import com.snapdeal.payments.sdmoney.exceptions.InternalServerException;
//import com.snapdeal.payments.sdmoney.exceptions.SDMoneyException;
//import com.snapdeal.payments.sdmoney.service.model.CreditGeneralBalanceRequest;
//import com.snapdeal.payments.sdmoney.service.model.CreditGeneralBalanceResponse;
//import com.snapdeal.payments.sdmoney.service.model.CreditGeneralToUserMobileRequest;
//import com.snapdeal.payments.sdmoney.service.model.CreditGeneralToUserMobileResponse;
//import com.snapdeal.payments.sdmoney.service.model.CreditVoucherBalanceRequest;
//import com.snapdeal.payments.sdmoney.service.model.CreditVoucherBalanceResponse;
//import com.snapdeal.payments.sdmoney.service.model.CreditVoucherToUserMobileRequest;
//import com.snapdeal.payments.sdmoney.service.model.CreditVoucherToUserMobileResponse;
//import com.snapdeal.payments.sdmoney.service.model.GetAccountBalanceRequest;
//import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdRequest;
//import com.snapdeal.payments.sdmoney.service.model.GetTransactionByIdempotencyIdResponse;
//import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceRequest;
//import com.snapdeal.payments.sdmoney.service.model.GetTransactionsByReferenceResponse;
//import com.snapdeal.payments.sdmoney.service.model.TransactionSummary;
//import com.snapdeal.payments.sdmoney.service.model.type.BusinessTransactionType;
//import com.snapdeal.payments.ts.TaskScheduler;
//import com.snapdeal.payments.ts.entity.TaskExecution;
//import com.snapdeal.payments.ts.registration.TaskExecutor;
//import com.snapdeal.payments.ts.response.ExecutorResponse;
//import com.snapdeal.payments.ts.response.ExecutorResponse.Status;
//
//import lombok.extern.slf4j.Slf4j;
//
//@Slf4j
//@Component
//public class FileTaskExecutionStep implements TaskExecutor<BulkTaskRequest> {
//
//	@Autowired
//	SDMoneyClient sdMoneyClient;
//
//	@Autowired
//	IMSService imsService;
//
//	@Autowired
//	CSVService csvService;
//
//	@Autowired
//	FileMetaService fileMetaService;
//
//	@Autowired
//	NotificationService notificationService;
//
//	@Autowired
//	OutputExcelService writeExcelService;
//
//	@Autowired
//	AmazonS3Utils amazonS3;
//
//	@Autowired
//	TaskScheduler taskScheduler;
//
//	// @Value("${TaskScheduler.retry_count}")
//	private long retryCount = 1;
//
//	public final String stepIdentifier = "\n\n\n executor-step:";
//
//	@Override
//	public ExecutorResponse execute(BulkTaskRequest request) {
//		ExecutorResponse executorResponse = new ExecutorResponse();
//		try {
//			log.info(stepIdentifier + "execute starts here\n" + "\n");
//			Gson gson = new Gson();
//			FormData formData = request.getFormData();
//			// get Amazon Configuration
//			AWSConfiguration awsConfig = amazonS3.getConfig();
//
//			try {
//				// download request file from amazon for processing
//				amazonS3.downloadFile(request.getEmailId(), request.getFileName());
//				log.info(stepIdentifier + "file downloaded from amazon\n" + "\n");
//			} catch (Exception e) {
//				// could not download file
//				log.info(stepIdentifier + "Could not downlaod file from amazon s3 server " + e.getStackTrace() + "\n");
//				fileMetaService.updateFileMetaStatus(
//						getFileMetaEntity(request.getFileName(), FileState.FILE_DOWNLOAD__FROM_S3_FAIL));
//				executorResponse.setStatus(Status.SUCCESS);
//			}
//
//			FileInputStream file = null;
//			File downloadedFile = new File(awsConfig.getLocalDownloadPath() + request.getFileName());
//			try {
//				file = new FileInputStream(downloadedFile);
//				log.info(stepIdentifier + " working on file with file name : " + request.getFileName() + "\n");
//			} catch (FileNotFoundException e) {
//				log.info(stepIdentifier + "File not found on local server after downloading from amazon s3 server " + e
//						+ "\n");
//				// could not process download file
//				fileMetaService.updateFileMetaStatus(
//						getFileMetaEntity(request.getFileName(), FileState.FILE_DOWNLOAD__FROM_S3_FAIL));
//				executorResponse.setStatus(Status.SUCCESS);
//				executorResponse.setCompletionLog("Could not read file, download from s3 failed");
//				return executorResponse;
//			}
//
//			BufferedReader br = new BufferedReader(new InputStreamReader(file));
//			try {
//				br.readLine();
//			} catch (IOException e2) {
//				log.info(stepIdentifier + "IOException while reading csv " + e2 + "\n");
//				// executorResponse.setStatus(Status.FAILURE);
//				executorResponse.setStatus(Status.SUCCESS);
//				executorResponse.setCompletionLog("Could not read file");
//				deleteFile(downloadedFile);
//				try {
//					br.close();
//				} catch (IOException ioe) {
//					log.info(stepIdentifier + "IOException while closing buffered reader " + ioe);
//				}
//				fileMetaService
//						.updateFileMetaStatus(getFileMetaEntity(request.getFileName(), FileState.FILE_READ_FAIL));
//				return executorResponse;
//			}
//			Integer rowCount = new Integer(1);
//			FileRow row;
//			List<OutputResponse> outputResponses = new ArrayList<OutputResponse>();
//			TaskState taskState = TaskState.SUCCESS;
//			BigDecimal totalSuccessMoney = new BigDecimal(0);
//			long successRowsNum = 0;
//
//			try {
//
//				// read a row from csv fileoutputResponse
//				while ((row = csvService.readRow(br, rowCount)) != null) {
//					log.info(stepIdentifier + "started processing row number :" + row.getRowNum() + "   for "
//							+ row.getUserId() + "\n");
//					CreditVoucherBalanceResponse creditVoucherBalanceResponse = null;
//					OutputResponse outputResponse = new OutputResponse();
//					outputResponse.setAmount(row.getAmount());
//					outputResponse.setUserId(row.getUserId());
//
//					// notifier model to send email or sms
//					NotificationRequestModel mailNotificationModel = null, smsNotificationModel = null;
//
//					GetTransactionByIdempotencyIdRequest getTransactionByIdempotencyIdRequest = new GetTransactionByIdempotencyIdRequest();
//					getTransactionByIdempotencyIdRequest
//							.setIdempotencyKey(formData.getIdempotencyId() + row.getUserId());
//
//					GetTransactionByIdempotencyIdResponse getTransactionByIdempotencyIdResponse = new GetTransactionByIdempotencyIdResponse();
//
//					try {
//						log.info(stepIdentifier + "Now will attempt to know if this transaction have happened" + "\n");
//						getTransactionByIdempotencyIdResponse = sdMoneyClient
//								.getTransactionByIdempotencyId(getTransactionByIdempotencyIdRequest);
//						log.info(stepIdentifier + " Idempotency check response was : "
//								+ getTransactionByIdempotencyIdRequest.toString());
//					} catch (Exception sdme) {
//
//						log.info(stepIdentifier + "failed in getting transaction through idempotancyId , "
//								+ "SDMoneyException : " + "Error message :" + sdme.getMessage() + "\n");
//					}
//
//					UserDetailsDTO userDetailsDTO = null;
//
//					try {
//						if (formData.getId_type().equalsIgnoreCase("MOBILE_ID")) {
//
//							try {
//								userDetailsDTO = imsService.getUserByMobile(row.getUserId());
//							} catch (ServiceException e) {
//								// user does not exist for mobile number
//
//								log.info(stepIdentifier
//										+ "Exception while getting imsid and email id from mobile number from IMS " + e
//										+ "\n");
//
//							}
//
//						} else {
//							userDetailsDTO = imsService.getUserByImsId(row.getUserId());
//						}
//
//						if (userDetailsDTO != null) {
//							log.info(stepIdentifier + "userDTO recieved " + userDetailsDTO.getUserId() + "\n");
//							if (userDetailsDTO.getMobileNumber() != null) {
//								smsNotificationModel = notiRequestModel(ChannelType.SMS, formData.getSmsTemplateId(),
//										userDetailsDTO.getMobileNumber());
//
//							}
//
//							if (userDetailsDTO.getEmailId() != null) {
//								mailNotificationModel = notiRequestModel(ChannelType.EMAIL,
//										formData.getEmailTemplateId(), userDetailsDTO.getEmailId());
//							}
//						} else {
//							smsNotificationModel = notiRequestModel(ChannelType.SMS, formData.getSmsTemplateId(),
//									row.getUserId());
//							
//						}
//
//						String imsCMobile = null;
//
//						if (userDetailsDTO != null)
//							imsCMobile = userDetailsDTO.getUserId();
//
//						String responseCode = null;
//						Timestamp transactionTimestamp = new Timestamp(new Date().getTime());
//
//						// decision point ,decide which api has to be called
//						// then call specific api handlinggetUserId()
//
//						if (formData.getInstrument() == InstrumentType.GiftVoucher) {
//							if (formData.getId_type().equalsIgnoreCase("IMS_ID")) {
//								creditVoucherBalanceResponse = hitCreditVoucherBalance(formData, row);
//								if (creditVoucherBalanceResponse == null) {
//									responseCode = null;
//								} else {
//									responseCode = creditVoucherBalanceResponse.getTransactionId();
//									transactionTimestamp = new Timestamp(
//											creditVoucherBalanceResponse.getTransactionTimestamp().getTime());
//								}
//							} else if (formData.getId_type().equalsIgnoreCase("MOBILE_ID")) {
//								CreditVoucherToUserMobileResponse response = hitCreditVoucherToUserMobile(formData, row,
//										imsCMobile); // 1st mobile case
//								if (response == null) {
//									responseCode = null;
//								} else {
//									responseCode = response.getTransactionId();
//									transactionTimestamp = new Timestamp(response.getTransactionTimestamp().getTime());
//								}
//							}
//						} else if (formData.getInstrument() == InstrumentType.WalletBalance) {
//							if (formData.getId_type().equalsIgnoreCase("IMS_ID")) {
//								CreditGeneralBalanceResponse generalBalanceResponse = hitCreditGeneralBalance(formData,
//										row);
//								if (generalBalanceResponse == null) {
//									responseCode = null;
//								} else {
//									responseCode = generalBalanceResponse.getTransactionId();
//									transactionTimestamp = new Timestamp(
//											generalBalanceResponse.getTransactionTimestamp().getTime());
//								}
//							} else if (formData.getId_type().equalsIgnoreCase("MOBILE_ID")) {
//								CreditGeneralToUserMobileResponse response = hitCreditGeneralBalanceToUserMobile(
//										formData, row, imsCMobile);// 2nd mobile
//																	// case
//								if (response == null) {
//									responseCode = null;
//								} else {
//									responseCode = response.getTransactionId();
//									transactionTimestamp = new Timestamp(response.getTransactionTimestamp().getTime());
//								}
//							}
//						}
//
//						if (responseCode == null) {
//							log.info(stepIdentifier + "row skipped as parking disabled" + "\n");
//							// null returned from API handlers, mean that they
//							// were skipped for parking
//							smsNotificationModel=null;
//							outputResponse.setResponseStatus(TaskState.FAILED.name());
//							outputResponse.setResponseCode("could not park money as  park money is disabled");
//							outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
//						} else {
//							log.info(stepIdentifier + "row successful with transaction id : " + responseCode + "\n");
//							// api succeeded increase success Row num and Money
//							outputResponse.setResponseStatus(TaskState.SUCCESS.name());
//							outputResponse.setResponseCode(responseCode);
//							outputResponse.setTransactionTimeStamp(transactionTimestamp);
//							totalSuccessMoney = totalSuccessMoney.add(outputResponse.getAmount());
//							successRowsNum++;
//
//						}
//
//						// fill remaining Notification request and send mail
//						log.info(stepIdentifier + " trying to send notification\n" + "\n");
//
//						if (!formData.getIsEmailSuppressed() && getTransactionByIdempotencyIdResponse.getTransaction() == null
//								  && mailNotificationModel != null) {
//
//							HashMap<String, Object> hm = new HashMap<String, Object>();
//							GetAccountBalanceRequest getBalanceRequest = new GetAccountBalanceRequest();
//
//							getBalanceRequest.setSdIdentity(userDetailsDTO.getUserId());
//							try {
//								BigDecimal totalAmount = sdMoneyClient.getAccountBalance(getBalanceRequest).getBalance()
//										.getTotalBalance();
//
//								hm.put("totalAmount", totalAmount);
//								hm.put("creditedAmount", row.getAmount());
//								mailNotificationModel.setTemplateParams(hm);
//
//								try {
//									notificationService.sendNotification(mailNotificationModel);
//									log.info(stepIdentifier + "Mail Notification successful " + "\n");
//								} catch (Exception e) {
//									try {
//										// executor level
//										// retry-------------------------------
//										log.info(stepIdentifier
//												+ "Exception while sending mail, will try one more time " + e + "\n");
//										notificationService.sendNotification(mailNotificationModel);
//										log.info(stepIdentifier + "Notification successful " + "\n");
//
//									} catch (Exception e1) {
//										log.info(stepIdentifier + "This time also got Exception while sending mail " + e
//												+ "\n");
//
//									}
//								}
//
//							} catch (SDMoneyException sdme) {
//								log.info(stepIdentifier
//										+ "Could not get account balance for this account for notification" + "\n");
//							}
//						}
//
//						if (!formData.getIsSMSSuppressed() && getTransactionByIdempotencyIdResponse.getTransaction() == null
//								  && smsNotificationModel != null) {
//
//							HashMap<String, Object> hm = new HashMap<String, Object>();
//
//							hm.put("creditedAmount", row.getAmount());
//							smsNotificationModel.setTemplateParams(hm);
//
//							try {
//								notificationService.sendNotification(smsNotificationModel);
//								log.info(stepIdentifier + "SMS Notification successful " + "\n");
//							} catch (Exception e) {
//								try {
//									// executor level
//									// retry-------------------------------
//									log.info(stepIdentifier + "Exception while sending sms, will try one more time " + e
//											+ "\n");
//
//									notificationService.sendNotification(smsNotificationModel);
//									log.info(stepIdentifier + "Notification successful " + "\n");
//								} catch (Exception e1) {
//									log.info(stepIdentifier + "This time also got Exception while sending sms " + e1
//											+ "\n");
//
//								}
//
//							}
//
//						} // if ended
//
//					} // try ended
//
//					catch (InternalClientException | InternalServerException ie) {
//						// retry needed
//						// -----------------------------------------------
//						log.info(stepIdentifier + "InternalClientException or InternalServerException from SDMoney "
//								+ ie + "\n");
//						taskState = TaskState.RETRY;
//						outputResponse.setResponseStatus(TaskState.RETRY.name());
//						outputResponse.setResponseCode(ie.getErrorCode().toString());
//						outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
//
//					} catch (SDMoneyException sdme) {
//						log.error(stepIdentifier + "SDMoney Exception " + sdme + "\n");
//
//						outputResponse.setResponseStatus(TaskState.FAILED.name());
//						outputResponse.setResponseCode(sdme.getErrorCode().toString());
//						outputResponse.setResponseMessage(sdme.getMessage());
//						outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
//
//					} catch (HttpTransportException e) {
//						// retry-----------------------------------------------------
//						log.info(stepIdentifier + "HttpTransportException from IMS " + e + "\n");
//						taskState = TaskState.RETRY;
//						outputResponse.setResponseStatus(TaskState.RETRY.name());
//						outputResponse.setResponseCode("MT-5021 Timeout");
//						outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
//					} catch (ServiceException e) {
//
//						log.error(stepIdentifier + "Service Exceptioon from IMS " + e.getStackTrace() + "\n");
//						outputResponse.setResponseStatus(TaskState.FAILED.name());
//						outputResponse.setResponseCode(e.getErrCode().toString());
//						outputResponse.setResponseMessage(e.getErrMsg());
//						outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
//
//					} catch (Exception e) {
//
//						log.error(stepIdentifier + "Exception in  executionStep " + e + "\n");
//						outputResponse.setResponseStatus(TaskState.FAILED.name());
//						outputResponse.setResponseMessage(e.getMessage());
//						outputResponse.setTransactionTimeStamp(new Timestamp(new Date().getTime()));
//
//					} finally {
//						outputResponse.setRemarks(row.getEventContex());
//						outputResponse.setUploadTimeStamp(formData.getUploadTimestamp());
//					}
//
//					// outPutCsvMessage(formData.getIdempotencyId()+
//					// row.getUserId(),formData.getBusinessEntity(),
//					// outputResponse);
//
//					outputResponses.add(outputResponse);
//					rowCount++;
//
//				}
//			} catch (WalletServiceException e) { // TODO: refactor rename
//				// TODO Auto-generated catch block
//				log.info(stepIdentifier + "Exception in executionStep " + e.getStackTrace() + "\n");
//				e.printStackTrace();
//			} finally {
//				try {
//					br.close();
//				} catch (IOException ioe) {
//					ioe.printStackTrace();
//					log.info(stepIdentifier + "IOException while closing buffered reader " + ioe + "\n");
//				}
//			}
//
//			String responseFilePath = writeStatusReport(request, executorResponse, outputResponses);
//			if (responseFilePath == null) {
//				deleteFile(downloadedFile);
//				return executorResponse;
//			}
//
//			executorResponse.setStatus(Status.SUCCESS);
//			fileMetaService
//					.updateFileMetaStatus(getFileMetaEntity(request.getFileName(), FileState.EXECUTION_COMPLETE));
//			updateFileStatics(request, totalSuccessMoney, successRowsNum);
//			executorResponse.setCompletionLog("execution step successful");
//			deleteFile(new File(responseFilePath));
//			log.info(stepIdentifier + "EXECUTOR STATUS SUCCESS" + "\n");
//			deleteFile(downloadedFile);
//			return executorResponse;
//		} catch (Exception e) {
//			log.error(stepIdentifier + "FATAL GOING OUT OF EXECUTOR" + e.getStackTrace() + "\n");
//			executorResponse.setStatus(Status.SUCCESS);
//			executorResponse.setCompletionLog(" Fatal uncaught exception");
//			fileMetaService.updateFileMetaStatus(getFileMetaEntity(request.getFileName(), FileState.EXECUTION_FAILED));
//			return executorResponse;
//		}
//	}
//
//	private void updateFileStatics(BulkTaskRequest request, BigDecimal totalSuccessMoney, long successRowsNum) {
//		FileMetaEntity entity = new FileMetaEntity();
//		entity.setFileName(request.getFileName());
//		entity.setTotalSuccessMoney(totalSuccessMoney);
//		entity.setSuccessRowsNum(successRowsNum);
//
//		fileMetaService.updateFileMetaTotalSuccessMoney(entity);
//		fileMetaService.updateFileMetaSuccessRowsNum(entity);
//	}
//
//	private NotificationRequestModel notiRequestModel(ChannelType channelType, String templateId, String userId) {
//
//		NotificationRequestModel notificationRequestModel;
//
//		notificationRequestModel = new NotificationRequestModel();
//		notificationRequestModel.setTemplateId(templateId);
//		notificationRequestModel.setChannelType(channelType);
//		notificationRequestModel.setUserId(userId);
//
//		return notificationRequestModel;
//	}
//
//	private CreditGeneralToUserMobileResponse hitCreditGeneralBalanceToUserMobile(FormData request, FileRow row,
//			String imsCMobile) throws HttpTransportException, ServiceException, SDMoneyException {
//		CreditGeneralToUserMobileRequest creditRequest = new CreditGeneralToUserMobileRequest();
//		creditRequest.setAmount(row.getAmount());
//		creditRequest.setBusinessEntity(request.getBusinessEntity());
//		creditRequest.setEventContext(row.getEventContex());
//		creditRequest.setSourceCorporateAccountId(request.getCorpId());
//		creditRequest.setMobile(row.getUserId());
//		creditRequest.setIdempotencyId(request.getIdempotencyId() + row.getUserId());
//		creditRequest.setTransactionReference(creditRequest.getIdempotencyId());
//		creditRequest.setNotificationNotDesired(false);
//
//		if (request.getIsWalletNotificationSuppressed() && imsCMobile!=null)
//			creditRequest.setNotificationNotDesired(true);
//
//		// String ims_Id = null;
//		// if (request.getIsPark() == Boolean.FALSE) {
//		// ims_Id = imsService.getimsIdByMobileNUmber(row.getUserId());
//		// }
//		if (imsCMobile != null || request.getIsPark() == Boolean.TRUE) {
//			return sdMoneyClient.creditGeneralToUserMobile(creditRequest);
//		}
//		return null;
//	}
//
//	private CreditGeneralBalanceResponse hitCreditGeneralBalance(FormData request, FileRow row)
//			throws SDMoneyException {
//		CreditGeneralBalanceRequest creditRequest = new CreditGeneralBalanceRequest();
//		creditRequest.setAmount(row.getAmount());
//		creditRequest.setBusinessEntity(request.getBusinessEntity());
//		creditRequest.setEventContext(row.getEventContex());
//		creditRequest.setSourceCorporateAccountId(request.getCorpId());
//		creditRequest.setSdIdentity(row.getUserId());
//		creditRequest.setIdempotencyId(request.getIdempotencyId() + row.getUserId());
//		creditRequest.setTransactionReference(creditRequest.getIdempotencyId());
//		creditRequest.setNotificationNotDesired(false);
//
//		if (request.getIsWalletNotificationSuppressed())
//			creditRequest.setNotificationNotDesired(true);
//
//		return sdMoneyClient.creditGeneralBalance(creditRequest);
//	}
//
//	private CreditVoucherToUserMobileResponse hitCreditVoucherToUserMobile(FormData request, FileRow row,
//			String imsCMobile) throws HttpTransportException, ServiceException, SDMoneyException {
//		CreditVoucherToUserMobileRequest creditRequest = new CreditVoucherToUserMobileRequest();
//		creditRequest.setAmount(row.getAmount());
//		creditRequest.setBusinessEntity(request.getBusinessEntity());
//		creditRequest.setEventContext(row.getEventContex());
//		creditRequest.setSourceCorporateAccountId(request.getCorpId());
//		creditRequest.setMobile(row.getUserId());
//		creditRequest.setIdempotencyId(request.getIdempotencyId() + row.getUserId());
//		creditRequest.setTransactionReference(creditRequest.getIdempotencyId());
//		creditRequest.setNotificationNotDesired(false);
//
//		if (request.getIsWalletNotificationSuppressed() && imsCMobile!=null)
//			creditRequest.setNotificationNotDesired(true);
//		// String ims_Id = null;
//		// if (request.getIsPark() == Boolean.FALSE) {
//		// ims_Id = imsService.getimsIdByMobileNUmber(row.getUserId());
//		// }
//		if (imsCMobile != null || request.getIsPark() == Boolean.TRUE) {
//			return sdMoneyClient.creditVoucherToUserMobile(creditRequest);
//		}
//		return null;
//	}
//
//	private CreditVoucherBalanceResponse hitCreditVoucherBalance(FormData request, FileRow row)
//			throws SDMoneyException {
//
//		CreditVoucherBalanceRequest creditRequest = new CreditVoucherBalanceRequest();
//		creditRequest.setAmount(row.getAmount());
//		creditRequest.setBusinessEntity(request.getBusinessEntity());
//		creditRequest.setEventContext(row.getEventContex());
//		creditRequest.setSourceCorporateAccountId(request.getCorpId());
//		creditRequest.setSdIdentity(row.getUserId());
//		creditRequest.setIdempotencyId(request.getIdempotencyId() + row.getUserId());
//		creditRequest.setTransactionReference(creditRequest.getIdempotencyId());
//
//		creditRequest.setNotificationNotDesired(false);
//
//		if (request.getIsWalletNotificationSuppressed())
//			creditRequest.setNotificationNotDesired(true);
//
//		CreditVoucherBalanceResponse response = sdMoneyClient.creditVoucherBalance(creditRequest);
//
//		return response;
//	}
//
//	private FileMetaEntity getFileMetaEntity(String fileName, FileState state) {
//		FileMetaEntity fileEntity = new FileMetaEntity();
//		fileEntity.setFileName(fileName);
//		fileEntity.setStatus(state.name());
//		return fileEntity;
//	}
//
//	private void deleteFile(File deleteFile) {
//
//		if (deleteFile.exists()) {
//			deleteFile.delete();
//		}
//
//	}
//
//	private void cleanUpExecution(BulkTaskRequest request, ExecutorResponse response) {
//		List<TaskExecution> taskExecutionList = taskScheduler.getExecutionInfo("BULK_CALL", request.getTaskId());
//		Long runNumber = taskExecutionList.get(taskExecutionList.size() - 1).getRunNo();
//		log.info(stepIdentifier + "Run number of this execution in scheduler was:" + runNumber);
//		if (runNumber == retryCount + 1) {
//			log.info(stepIdentifier + "Failing execution, retry count exceeded");
//			fileMetaService.updateFileMetaStatus(getFileMetaEntity(request.getFileName(), FileState.EXECUTION_FAILED));
//			response.setCompletionLog("execution failed");
//		}
//	}
//
//	private void outPutCsvMessage(UserDetailsDTO userDetailsDTO, String idempotencyKey, String businessEntity,
//			OutputResponse outputResponse) {
//
//		GetTransactionsByReferenceRequest getTransactionsByReferenceRequest = new GetTransactionsByReferenceRequest();
//		getTransactionsByReferenceRequest.setTransactionReference(idempotencyKey);
//
//		GetTransactionsByReferenceResponse getTransactionsByReferenceResponse = null;
//
//		try {
//			log.info("confirming status of transaction ref " + idempotencyKey);
//			getTransactionsByReferenceResponse = sdMoneyClient
//					.getTransactionByReference(getTransactionsByReferenceRequest);
//
//		} catch (Exception e) {
//
//			log.info("Exception Occured  status of transaction ref " + idempotencyKey);
//			outputResponse.setResponseMessage("UNABLE_TO_VERIFY");
//
//			return;
//
//		}
//
//		// in case of our exception..
//		if (getTransactionsByReferenceResponse == null) {
//
//			if (userDetailsDTO == null) {
//
//				log.info("Transaction ref " + idempotencyKey
//						+ "  sucessfully parked money reported by local check only {unable to verify through ref}");
//				outputResponse.setResponseMessage("AMOUNT_PARKED");
//				return;
//
//			} else {
//
//				log.info("Transaction ref " + idempotencyKey
//						+ "  sucessfully credited money reported by local check only  {unable to verify through ref}");
//				outputResponse.setResponseMessage("AMOUNT_CREDITED");
//				return;
//			}
//
//		}
//
//		if (getTransactionsByReferenceResponse.getListTransaction().size() == 0) {
//
//			log.info("Transaction ref " + idempotencyKey + " failed to credit money");
//			outputResponse.setResponseMessage("Refer Walllet");
//			return;
//
//		}
//
//		TransactionSummary transactionSummary = getTransactionsByReferenceResponse.getListTransaction().get(0);
//
//		if (transactionSummary.getBusinessTransactionType().equals(BusinessTransactionType.WALLET_PARKED_CREDIT)) {
//
//			log.info("Transaction ref " + idempotencyKey + "  sucessfully parked money ");
//			outputResponse.setResponseMessage("AMOUNT_PARKED");
//			return;
//		}
//
//		if (transactionSummary.getBusinessTransactionType().equals(BusinessTransactionType.WALLET_CREDIT)) {
//
//			log.info("Transaction ref " + idempotencyKey + "  sucessfully credited money ");
//			outputResponse.setResponseMessage("AMOUNT_CREDITED");
//			return;
//
//		}
//
//	}
//
//	private String writeStatusReport(BulkTaskRequest request, ExecutorResponse response,
//			List<OutputResponse> outputResponses) {
//
//		AWSConfiguration awsConfig = amazonS3.getConfig();
//
//		String responseFilePath = awsConfig.getLocalDownloadPath()
//				+ request.getFileName().substring(0, request.getFileName().lastIndexOf(".csv")) + "_response.csv";
//		try {
//			File responseFile = new File(responseFilePath);
//			if (!responseFile.exists()) {
//				responseFile.createNewFile();
//			}
//			FileWriter fw = new FileWriter(responseFile.getAbsoluteFile());
//			BufferedWriter bw = new BufferedWriter(fw);
//			bw.write(OPSUtils.responseHeader);
//			for (OutputResponse outputResponse : outputResponses) {
//				csvService.writeRow(bw, outputResponse);
//			}
//			bw.close();
//		} catch (IOException | WalletServiceException e) {
//			log.error(stepIdentifier + "Exception while writing csv response " + e.getStackTrace() + "\n");
//			fileMetaService.updateFileMetaStatus(getFileMetaEntity(request.getFileName(), FileState.EXECUTION_FAILED));
//			response.setStatus(Status.FAILURE);
//			response.setCompletionLog("Could not continue io operation while writing output file");
//			deleteFile(new File(responseFilePath));
//			return null;
//		}
//
//		try {
//			// write success file to amazon
//			amazonS3.pushFile(responseFilePath, request.getEmailId(),
//					request.getFileName().substring(0, request.getFileName().lastIndexOf(".csv")) + "_response.csv");
//		} catch (Exception e) {
//			log.info(stepIdentifier + "Exception while uploading file on amazon s3 " + e.getStackTrace() + "\n");
//			try {
//				// Executor level file upload RETRY
//				// ---------------------------------------------
//				amazonS3.pushFile(responseFilePath, request.getEmailId(),
//						request.getFileName().substring(0, request.getFileName().lastIndexOf(".csv"))
//								+ "_response.csv");
//			} catch (Exception e1) {
//
//				log.info(stepIdentifier + "Exception while uploading file on amazon s3 " + e + "\n");
//				fileMetaService
//						.updateFileMetaStatus(getFileMetaEntity(request.getFileName(), FileState.EXECUTION_FAILED));
//				response.setStatus(Status.FAILURE);
//				response.setCompletionLog("Could not push result file to S3");
//				deleteFile(new File(responseFilePath));
//				return null;
//			}
//
//		}
//
//		return responseFilePath;
//
//	}
//
//}
