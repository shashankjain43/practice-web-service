package com.snapdeal.ums.admin.sdCashBulkDebit;

import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

public class SDCashBulkDebitUploadRequest extends ServiceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -7571344021945806688L;

	@AuditableField
	private String uploadersEmail;
	@AuditableField
	private String fileName;

	private byte[] fileContent;
	@AuditableField
	private int activityType;

	public SDCashBulkDebitUploadRequest() {
		super();

	}

	public SDCashBulkDebitUploadRequest(String uploadersEmail, String fileName,
			byte[] fileContent, int activityType) {
		super();
		this.uploadersEmail = uploadersEmail;
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.activityType = activityType;
	}

	public String getUploadersEmail() {
		return uploadersEmail;
	}

	public void setUploadersEmail(String uploadersEmail) {
		this.uploadersEmail = uploadersEmail;
	}

	public String getFileName() {
		return fileName;
	}

	public void setFileName(String fileName) {
		this.fileName = fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public void setFileContent(byte[] fileContent) {
		this.fileContent = fileContent;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

}
