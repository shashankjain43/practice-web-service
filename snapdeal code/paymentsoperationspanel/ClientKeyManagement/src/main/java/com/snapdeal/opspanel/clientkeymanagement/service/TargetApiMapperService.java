package com.snapdeal.opspanel.clientkeymanagement.service;

import java.util.List;

import com.snapdeal.opspanel.clientkeymanagement.entity.TargetApiMapperEntity;
import com.snapdeal.opspanel.clientkeymanagement.exception.ClientViewerException;

public interface TargetApiMapperService {
	
public void insertIntoTargetApiMapper(TargetApiMapperEntity entity) throws ClientViewerException;
	
	public List<TargetApiMapperEntity> getAllTargetApiMapper() throws ClientViewerException;
	
	public List<String> getApisForTargetApplication (String targetApplication) throws ClientViewerException;
	
	public void updateApiName(TargetApiMapperEntity entity) throws ClientViewerException;
	
	public void disableApi(TargetApiMapperEntity entity) throws ClientViewerException;
	
	public void enableApi(TargetApiMapperEntity entity) throws ClientViewerException;

	public void upsertApiForTargetApplication( TargetApiMapperEntity request) throws ClientViewerException;

	public void deleteApiForTargetApplication( TargetApiMapperEntity request) throws ClientViewerException;

}
