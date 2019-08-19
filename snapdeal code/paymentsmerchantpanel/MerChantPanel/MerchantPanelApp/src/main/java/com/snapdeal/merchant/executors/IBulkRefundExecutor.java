package com.snapdeal.merchant.executors;

import org.apache.poi.ss.usermodel.Workbook;

public interface IBulkRefundExecutor {

	public void submitjob(String merchantId,String userName,Integer totalNumOfRow,String inputFilePath , String outputFilePath,String fileName);


}
