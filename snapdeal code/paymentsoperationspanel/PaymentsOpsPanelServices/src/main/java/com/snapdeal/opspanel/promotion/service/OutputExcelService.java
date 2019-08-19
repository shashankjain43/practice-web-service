package com.snapdeal.opspanel.promotion.service;

import com.snapdeal.opspanel.promotion.request.BulkTaskRequest;


public interface OutputExcelService {


	void writeToOutputFile(String fileName, BulkTaskRequest request);

}
