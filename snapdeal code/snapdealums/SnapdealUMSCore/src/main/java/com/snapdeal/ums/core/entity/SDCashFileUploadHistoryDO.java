package com.snapdeal.ums.core.entity;
import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name="sd_cash_file_upload_history",catalog="ums")

public class SDCashFileUploadHistoryDO implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 4927572953001999726L;

	@Id
	@GeneratedValue(strategy= IDENTITY)
	@Column(name="id")
	private int id;

	@Column(name="uploader")
	private String uploader;

	@Column(name= "email_id_count")
	private int emailIDCount;


	@Column(name ="fail_count")
	private int failCount;

	@Column(name="file_name")
	private String fileName;

	@Column(name="file_content")
	private byte[] fileContent;
	
	@Column(name="activity_type")
	private int activityType;

	@Column(name = "file_hash")
	private String fileHash;
	
	@Column(name = "last_updated")
	private Date created;
	
	@Column(name = "mode")
	private String mode;

	public int getActivityType() {
		return activityType;
	}

	public void setActivityType(int activityType) {
		this.activityType = activityType;
	}

	public int getId() {
		return id;
	}

	public String getUploader() {
		return uploader;
	}


	public int getEmailIDCount() {
		return emailIDCount;
	}

	public int getFailCount() {
		return failCount;
	}


	public String getFileName() {
		return fileName;
	}

	public byte[] getFileContent() {
		return fileContent;
	}

	public String getFileHash() {
		return fileHash;
	}

	public SDCashFileUploadHistoryDO(String uploader, int emailIDCount, int failCount,
			String fileName, byte[] fileContent, int activityType, String fileHash, String mode) {
		super();
		this.uploader = uploader;
		this.emailIDCount = emailIDCount;
		this.failCount = failCount;
		this.fileName = fileName;
		this.fileContent = fileContent;
		this.activityType = activityType;
		this.fileHash = fileHash;
		this.mode=mode;
	}

	public SDCashFileUploadHistoryDO() {

	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public String getMode() {
		return mode;
	}

	public void setMode(String mode) {
		this.mode = mode;
	}

}
