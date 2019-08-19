package com.snapdeal.opspanel.promotion.bulkprocessortester;

import java.util.Map;
import java.util.Set;

import com.snapdeal.bulkprocess.enums.BulkFileStatus;
import com.snapdeal.bulkprocess.registration.IRowProcessor;

public class TRowProcessor implements IRowProcessor{

	@Override
	public Object execute(String[] header, String[] rowValues, Map<String,String> meta,long rowNum,Object sharedObject, Map<String,String> headerValues) {
		/*Thread.sleep(200);*/
		System.out.println(Thread.currentThread().getName());
		return new SampleResponse("india", null, 143, 123);
	}

	@Override
	public void onFinish(Map<String, String> map, Object sharedObject, BulkFileStatus status, String fileName) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Set<String> columnsToIgnore() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
    public Object onStart(Map<String, String> paramMap1, Object paramObject, Map<String, String> paramMap2) {
		// TODO 
		return null;
	}

}
