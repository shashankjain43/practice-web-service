package com.snapdeal.bulkprocess.registration;

import java.io.File;
import java.util.Map;

import org.springframework.web.multipart.MultipartFile;

import com.snapdeal.bulkprocess.enums.BulkProcessEnum;
import com.snapdeal.bulkprocess.model.ValidationResponse;

public interface IBulkValidator {
	
	public boolean shouldValidateEachRow();

	public ValidationResponse validateRow(String[] headers,String[] values, Map<String,String>fileMetaData);

	public ValidationResponse validate(Map<String,String>fileMetaData, File file);

	public boolean hasPermissionForAction(String userId, BulkProcessEnum action, Map<String, String> headerValues);

}
