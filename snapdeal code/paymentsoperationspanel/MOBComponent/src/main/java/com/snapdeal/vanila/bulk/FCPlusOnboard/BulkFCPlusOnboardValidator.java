package com.snapdeal.vanila.bulk.FCPlusOnboard;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

import lombok.extern.slf4j.Slf4j;

import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;

@Component
@Slf4j
public class BulkFCPlusOnboardValidator implements IBulkValidator{

	@Override
	public ValidationResponse validateRow(String[] headers, String[] values,
			Map<String, String> fileMetaData) {
		
		List<String> sampleColumns = new ArrayList<String>();
		sampleColumns = Arrays.asList(FCPlusOnboardConstants.sampleOnboard);
		
		int valueSize = values.length;
		if(valueSize < sampleColumns.size()){
			return new ValidationResponse("BFCP-003", FCPlusOnboardConstants.LESS_ROWS, false);
		}
		if(valueSize > sampleColumns.size()){
			return new ValidationResponse("BFCP-004", FCPlusOnboardConstants.MORE_ROWS, false);
		}
		
		float latitude = Float.NaN;
		try {
			latitude = Float.parseFloat(values[14]);
		} catch (NumberFormatException e) {
			log.info("Inside ValidateRow :  NumberFormatException while fetching value of latitude...\n");
			log.info("Inside ValidateRow :  Please submit a valid integer for latitude  \n");
			return new ValidationResponse("BFCP-005", "Value of latitude is invalid!", false);
		}
		
		float longitude = Float.NaN;
		try {
			longitude = Float.parseFloat(values[15]);
		} catch (NumberFormatException e) {
			log.info("Inside ValidateRow :  NumberFormatException while fetching value of longitude...\n");
			log.info("Inside ValidateRow :  Please submit a valid integer for longitude  \n");
			return new ValidationResponse("BFCP-005", "Value of longitude is invalid!", false);
		}
		
		// TODO Auto-generated method stub
		return new ValidationResponse(null, null, true);
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData, File file) {
		
		FileInputStream inputFileStream =  null;
		try {
			inputFileStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			log.info("FileNotFoundException in validate ...");
			return new ValidationResponse("BFCP-006", FCPlusOnboardConstants.FILE_NOT_FOUND, false);
		}
		BufferedReader buffer = null;
		try {
			buffer = new BufferedReader(new InputStreamReader(inputFileStream, "UTF-8"));
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}

		String firstLine = null;
		try {
			firstLine = buffer.readLine();
		} catch (IOException e) {
			log.info("IOException while buffer.readLine() in validate ...");
			
		}
		String[] headers = firstLine.split(",", -1);

		int headerSize = headers.length;
		for(int i=0; i<headerSize; i++){
			headers[i] = headers[i].trim();
		}

		List<String> columns = Arrays.asList(headers);

		List<String> sampleColumns = new ArrayList<String>();
		sampleColumns = Arrays.asList(FCPlusOnboardConstants.sampleOnboard);
		if(!columns.equals(sampleColumns)){
			return new ValidationResponse("BFCP-002", FCPlusOnboardConstants.COLUMNS_MISMATCH, false);
		}
		return new ValidationResponse(null, null, true);
	}

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return true;
	}

}
