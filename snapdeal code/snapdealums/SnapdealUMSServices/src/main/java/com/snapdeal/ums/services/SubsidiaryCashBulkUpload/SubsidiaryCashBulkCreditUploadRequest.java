package com.snapdeal.ums.services.SubsidiaryCashBulkUpload;

import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;
import com.snapdeal.base.model.common.ServiceRequest;

@AuditableClass
public class SubsidiaryCashBulkCreditUploadRequest extends ServiceRequest{

	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
		@AuditableField
		private String uploadersEmail;
		@AuditableField
		private String fileName;

		private byte[] fileContent;
		@AuditableField
		private int activityType;
		@AuditableField
		private String creditSDCashEmailTemplateName;
		@AuditableField
		private int defaulAmount;
		@AuditableField
		private String source;
		@AuditableField
		private String accountCreationEmailTemplateName;

		public String getUploadersEmail() {
			return uploadersEmail;
		}

		public void setUploadersEmail(String uploadersEmail) {
			this.uploadersEmail = uploadersEmail;
		}

		public void setCreditSDCashEmailTemplateName(String emailTemplateName) {
			this.creditSDCashEmailTemplateName = emailTemplateName;

		}

		public String getCreditSDCashEmailTemplateName() {
			return creditSDCashEmailTemplateName;
		}

		public int getActivityType() {
			return activityType;
		}

		public void setActivityType(Integer id) {
			this.activityType = id;
		}

		public SubsidiaryCashBulkCreditUploadRequest() {
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
		
		public void setDefaultAmount(int amount){
			this.defaulAmount = amount;
		}
		
		public int getDefaultAmount(){
			return this.defaulAmount;
		}
		
		public void setSource(String src){
			this.source = src;
		}

		public String getSource() {
			return source;
		}
		
		public void setAccountCreationEmailTemplateName(String emailTemplateName) {
			this.accountCreationEmailTemplateName = emailTemplateName;

		}

		public String getAccountCreationEmailTemplateName() {
			return accountCreationEmailTemplateName;
		}
}
