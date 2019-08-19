package com.snapdeal.opspanel.userpanel.dao;

import com.snapdeal.opspanel.userpanel.entity.FraudManagementEntity;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;

public interface FraudAuditDao {

	public void auditFraud(FraudManagementEntity fraudManagementEntity) throws InfoPanelException;
}
