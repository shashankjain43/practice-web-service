package com.snapdeal.vanila.bulk.merchanthierarchy.merchant.validator;

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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.BulkFrameworkResponse;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;
import com.snapdeal.payments.roleManagementClient.client.RoleMgmtClient;
import com.snapdeal.payments.roleManagementModel.request.AuthrizeUserRequest;
import com.snapdeal.vanila.bulk.merchanthierarchy.merchant.utils.BulkMerchantHierarchyMerchantConstants;
import com.snapdeal.vanila.bulkFOS.BulkFOSConstants;

@Component
@Slf4j
public class BulkMerchantHierarchyMerchantValidator implements IBulkValidator{

	@Autowired
	RoleMgmtClient rmsClient;

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return true;
	}

	@Override
	public ValidationResponse validateRow(String[] headers, String[] values,
			Map<String, String> fileMetaData) {
		// TODO Auto-generated method stub
		
		String action = fileMetaData.get(BulkMerchantHierarchyMerchantConstants.OPERATION);

		List<String> sampleColumns = new ArrayList<String>();
		
		if(action.equalsIgnoreCase(BulkMerchantHierarchyMerchantConstants.ONBOARD)){
			sampleColumns = Arrays.asList(BulkMerchantHierarchyMerchantConstants.sampleOnboard);
		} else if(action.equalsIgnoreCase(BulkMerchantHierarchyMerchantConstants.UPDATE)){
			sampleColumns = Arrays.asList(BulkMerchantHierarchyMerchantConstants.sampleUpdate);
		}
		
		int valueSize = values.length;
		if(valueSize < sampleColumns.size()){
			return new ValidationResponse("BM-003", BulkMerchantHierarchyMerchantConstants.LESS_ROWS, false);
		}
		if(valueSize > sampleColumns.size()){
			return new ValidationResponse("BM-004", BulkMerchantHierarchyMerchantConstants.MORE_ROWS, false);
		}
		int disburseDifferencePeriodRow = -1;
		if(action.equalsIgnoreCase(BulkMerchantHierarchyMerchantConstants.ONBOARD)){
			disburseDifferencePeriodRow = 30;
		} else if(action.equalsIgnoreCase(BulkMerchantHierarchyMerchantConstants.UPDATE)){
			disburseDifferencePeriodRow = 25;
		}
		Integer disburseDifferencePeriod;
		if (values[disburseDifferencePeriodRow] != null) {
			values[disburseDifferencePeriodRow] = values[disburseDifferencePeriodRow]
					.trim();
		}
		if (values[disburseDifferencePeriodRow] != null && !values[disburseDifferencePeriodRow].isEmpty()) {
			try {
				disburseDifferencePeriod = Integer
						.parseInt(values[disburseDifferencePeriodRow]);
			} catch (NumberFormatException e1) {
				log.info("Inside ValidateRow :  NumberFormatException while fetching value of disburseDifferencePeriod...\n");
				log.info("Inside ValidateRow :  Please submit a valid integer for disburseDifferencePeriod  \n");
				return new ValidationResponse("BM-005",
						"Value of disburseDifferencePeriod is invalid!", false);
			}
		}
		return new ValidationResponse(null, null, true);
	}

	@Override
	public ValidationResponse validate(Map<String, String> fileMetaData,
			File file) {
		if(!fileMetaData.containsKey(BulkMerchantHierarchyMerchantConstants.OPERATION)){
			return new ValidationResponse("BM-001", BulkMerchantHierarchyMerchantConstants.INVALID_OPERATION_TYPE, false);
		}
		FileInputStream inputFileStream =  null;
		try {
			inputFileStream = new FileInputStream(file);
		} catch (FileNotFoundException e1) {
			log.info("FileNotFoundException in validate ...");
			return new ValidationResponse("BM-006", BulkMerchantHierarchyMerchantConstants.FILE_NOT_FOUND, false);
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
		
		String action = fileMetaData.get(BulkMerchantHierarchyMerchantConstants.OPERATION);

		List<String> columns = Arrays.asList(headers);

		List<String> sampleColumns = new ArrayList<String>();
		
		if(action.equalsIgnoreCase(BulkMerchantHierarchyMerchantConstants.ONBOARD)){
			sampleColumns = Arrays.asList(BulkMerchantHierarchyMerchantConstants.sampleOnboard);
		} else if(action.equalsIgnoreCase(BulkMerchantHierarchyMerchantConstants.UPDATE)){
			sampleColumns = Arrays.asList(BulkMerchantHierarchyMerchantConstants.sampleUpdate);
		}
		if(!columns.equals(sampleColumns)){
			return new ValidationResponse("BM-002", BulkMerchantHierarchyMerchantConstants.COLUMNS_MISMATCH, false);
		}
		return new ValidationResponse(null, null, true);

	}

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		String permission = BulkMerchantHierarchyMerchantConstants.getPermissionForAction(action.toString());
		String token = headerValues.get(BulkMerchantHierarchyMerchantConstants.TOKEN);

		AuthrizeUserRequest request = new AuthrizeUserRequest();
		request.setRequestToken(token);
		request.setPreAuthrizeString(permission);
		try {
			rmsClient.authorizeUser(request);
		} catch (Exception e) {
			return false;
		}

		return true;
	}



}
