package com.snapdeal.bulkprocess.registration;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;


public interface IRowProcessor {
	
	public Object onStart(Map<String, String> map, Object sharedObject, Map<String, String> headerValues);

	public Object execute(String[] header, String[] rowValues, Map<String, String> map,long rowNum, Object sharedObject, Map<String, String> headerValues);
	
	public Set<String> columnsToIgnore();
	
	public void onFinish(Map<String, String> map, Object sharedObject, BulkFileStatus status,String fileName);

}
