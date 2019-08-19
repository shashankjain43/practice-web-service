package com.snapdeal.opspanel.Amazons3.utils;

import org.springframework.beans.factory.annotation.Value;

import com.snapdeal.opspanel.Amazons3.exception.PaymentsCommonException;

import lombok.extern.slf4j.Slf4j;

@Slf4j
public class OpsPanelCommonsUtils {

	public static final String destinationPrefix = "paymentsdashboard/bulk_refund/files";


	public static String getOutputFilePathForCSV(String inputFilePath) throws PaymentsCommonException {
		try{
			if(inputFilePath.indexOf(".csv")!=-1) 
			{
				return inputFilePath.substring(0, inputFilePath.indexOf(".csv")) + "_output.csv";
			} else {
				return  inputFilePath;
			}
		}
		catch(Exception e){
			log.info("Error during getting filename for outputfile." + e);
			/*	 throw new PaymentsCommonException("File Name must end with .csv . Please Check " + e);*/
		}
		return inputFilePath;
	}

}
