package com.snapdeal.opspanel.userpanel.service;

import java.util.Date;

import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;

public interface BulkService {

	public void executeBulkAction( ActionBulkRequest actionBulkRequest, Date currDate, String emailId ) throws InfoPanelException;

}
