package com.snapdeal.opspanel.bulk.service;

public interface BulkService {

	public void submitjob(String localFilePath,String outputFilePath,String emailId,String fileName,String refundKey, String merchantId) throws Exception;
}
