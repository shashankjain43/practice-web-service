package com.snapdeal.opspanel.userpanel.service;

import java.net.URL;
import java.util.Date;
import java.util.List;

import com.snapdeal.opspanel.commons.exceptions.OpsPanelException;
import com.snapdeal.opspanel.userpanel.entity.FileActionHistoryRow;
import com.snapdeal.opspanel.userpanel.exception.InfoPanelException;
import com.snapdeal.opspanel.userpanel.request.ActionBulkRequest;
import com.snapdeal.opspanel.userpanel.request.DownloadActionBulkFileRequest;

public interface FileManagementService {

	public void pushActionBulkInputFile( ActionBulkRequest actionBulkRequest, Date timeProcessed, String emailId ) throws InfoPanelException;
	public void pushActionBulkOutputFile( ActionBulkRequest actionBulkRequest, Date timeProcessed, String emailId ) throws InfoPanelException;
	public List<FileActionHistoryRow> getFileHistoryForUser() throws OpsPanelException, InfoPanelException;
	public List<FileActionHistoryRow> getFileHistoryForSuperUser() throws InfoPanelException;
	public URL getDownloadUrl( DownloadActionBulkFileRequest request ) throws InfoPanelException;

}
