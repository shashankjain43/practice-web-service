package com.snapdeal.opspanel.promotion.dao;

import com.snapdeal.opspanel.promotion.model.AccessAuditEntity;

public interface AuditAccessDao {
		
	public void auditClientDetails(AccessAuditEntity accessAuditEntity);

}
