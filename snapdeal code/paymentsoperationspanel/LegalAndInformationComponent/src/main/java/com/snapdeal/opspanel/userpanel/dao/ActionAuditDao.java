package com.snapdeal.opspanel.userpanel.dao;

import java.util.List;

import com.snapdeal.opspanel.userpanel.entity.ActionAuditEntity;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.response.ViewUserAccountResponse;

public interface ActionAuditDao {

	public void auditAction(ActionAuditEntity actionAuditEntity) throws InfoPanelException;
	
	public List<ViewUserAccountResponse> viewUserHistory(String userId) throws InfoPanelException;
	
}
