package com.snapdeal.ums.admin.sdCashBulkCredit;

import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

@AuditableClass
public class SDCashBulkCreditUploadRequest extends ServiceRequest {

	/**
	 * 
	 */
	private static final long serialVersionUID = -1511590016245123290L;

	@AuditableField
	private String uploadersEmail;
	@AuditableField
	private String fileName;

	private byte[] fileContent;
	@AuditableField
	private int activityType;
	@AuditableField
	private String emailTemplateName;

	public String getUploadersEmail() {
		return uploadersEmail;
	}

	public void setUploadersEmail(String uploadersEmail) {
		this.uploadersEmail = uploadersEmail;
	}

	public void setEmailTemplateName(String emailTemplateName) {
		this.emailTemplateName = emailTemplateName;

	}

	public String getEmailTemplateName() {
		return emailTemplateName;
	}

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(Integer id) {
		this.activityType = id;
	}

	public SDCashBulkCreditUploadRequest() {
		super();

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

}
