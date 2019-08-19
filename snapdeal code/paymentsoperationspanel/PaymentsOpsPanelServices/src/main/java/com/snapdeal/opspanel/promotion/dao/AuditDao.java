package com.snapdeal.opspanel.promotion.dao;

import java.util.List;

import com.snapdeal.opspanel.promotion.entity.AuditEntity;

public interface AuditDao {
	
	public void insert(AuditEntity entity);
	
	public List<AuditEntity> getAllAccess();
	
}
