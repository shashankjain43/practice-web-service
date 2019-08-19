package com.snapdeal.opspanel.promotion.bulkprocessortester;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;
import com.snapdeal.bulkprocess.registration.IBulkValidator;

public class TFileValidator implements IBulkValidator{

	@Override
	public ValidationResponse validateRow(String[] headers,String[] values, Map<String,String>fileMetaData) {
		int length =  headers.length;
		/*for(int i=0; i<length; i++){
			if(headers[i].equalsIgnoreCase("id") && values[i].equalsIgnoreCase("21")){
				return false;
			}
			if(headers[i].equalsIgnoreCase("comment") && values[i].equalsIgnoreCase("India")){
				return false;
			}
		}*/
		
		return new ValidationResponse(null, null, true);
	}

	@Override
	public ValidationResponse validate(Map<String,String>fileMetaData, File file) {
		// TODO Auto-generated method stub
		return new ValidationResponse(null, null, true);
	}

	@Override
	public boolean hasPermissionForAction(String userId,
			BulkProcessEnum action, Map<String, String> headerValues) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean shouldValidateEachRow() {
		// TODO Auto-generated method stub
		return false;
	}

}
