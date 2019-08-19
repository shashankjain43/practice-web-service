package com.snapdeal.opspanel.clientkeymanagement.dao;

import java.util.List;

import com.snapdeal.opspanel.clientkeymanagement.entity.TargetApiMapperEntity;

public interface TargetApiMapperDao {
	
	public int insertIntoTargetApiMapper(TargetApiMapperEntity entity);
	
	public List<TargetApiMapperEntity> getAllTargetApiMapper();
	
	public List<String> getApisForTargetApplication (String targetApplication);
	
	public int updateApiName(TargetApiMapperEntity entity);
	
	public void disableApi(TargetApiMapperEntity entity);
	
	public void enableApi(TargetApiMapperEntity entity);

	public void deleteApi( TargetApiMapperEntity entity);
}
