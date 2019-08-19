package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.UniqueConstraint;

/**
 * The class represents the subsidiary promotion configuration on S3 cloud
 * storage.
 * 
 * @author jain.shashank@snapdeal.com
 */

@Entity
@Table(name = "sdCashBack_program_config", catalog = "ums")
public class SDCashBackProgramConfig implements Serializable {

	private static final long serialVersionUID = 6163120995551261437L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "subsidiary_name", nullable = false)
	private String subsidiaryName;

	@Column(name = "s3_access_id")
	private String S3AccessId;

	@Column(name = "s3_secret_key")
	private String S3SecretKey;
	
	@Column(name = "s3_bucket_name")
	private String S3BucketName;
	
	@Column(name = "s3_source_dir_name")
	private String S3SourceDirName;
	
	@Column(name = "s3_fail_dir_name")
	private String S3FailDirName;
	
	@Column(name = "s3_success_dir_name")
	private String S3SuccessDirName;

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@Column(name = "activity_id")
	private int activityId;
	
	@Column(name = "use_file_amount")
	private boolean useFileAmount;

	@Column(name = "default_sdCash")
	private int defaultSDcash;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_processed")
	private Date lastProcessed;

	@Column(name = "sdcash_email_template")
	private String sdcashEmailTemplate;
	
	@Column(name = "createUser_email_template")
	private String createUserEmailTemplate;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "created")
	private Date created;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "updated")
	private Date updated;

	public SDCashBackProgramConfig() {
		super();
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public String getSubsidiaryName() {
		return subsidiaryName;
	}

	public void setSubsidiaryName(String subsidiaryName) {
		this.subsidiaryName = subsidiaryName;
	}

	public String getS3AccessId() {
		return S3AccessId;
	}

	public void setS3AccessId(String s3AccessId) {
		S3AccessId = s3AccessId;
	}

	public String getS3SecretKey() {
		return S3SecretKey;
	}

	public void setS3SecretKey(String s3SecretKey) {
		S3SecretKey = s3SecretKey;
	}
	
	public String getS3BucketName() {
		return S3BucketName;
	}

	public void setS3BucketName(String s3BucketName) {
		S3BucketName = s3BucketName;
	}

	public String getS3SourceDirName() {
		return S3SourceDirName;
	}

	public void setS3SourceDirName(String s3SourceDirName) {
		S3SourceDirName = s3SourceDirName;
	}

	public String getS3FailDirName() {
		return S3FailDirName;
	}

	public void setS3FailDirName(String s3FailDirName) {
		S3FailDirName = s3FailDirName;
	}

	public String getS3SuccessDirName() {
		return S3SuccessDirName;
	}

	public void setS3SuccessDirName(String s3SuccessDirName) {
		S3SuccessDirName = s3SuccessDirName;
	}

	public boolean isEnabled() {
		return isEnabled;
	}

	public void setEnabled(boolean isEnabled) {
		this.isEnabled = isEnabled;
	}

	public int getActivityId() {
		return activityId;
	}

	public void setActivityId(int activityId) {
		this.activityId = activityId;
	}

	public Date getLastProcessed() {
		return lastProcessed;
	}

	public void setLastProcessed(Date lastProcessed) {
		this.lastProcessed = lastProcessed;
	}

	public String getSdcashEmailTemplate() {
		return sdcashEmailTemplate;
	}

	public void setSdcashEmailTemplate(String sdcashEmailTemplate) {
		this.sdcashEmailTemplate = sdcashEmailTemplate;
	}

	public String getCreateUserEmailTemplate() {
		return createUserEmailTemplate;
	}

	public void setCreateUserEmailTemplate(String createUserEmailTemplate) {
		this.createUserEmailTemplate = createUserEmailTemplate;
	}

	public boolean isUseFileAmount() {
		return useFileAmount;
	}

	public void setUseFileAmount(boolean useFileAmount) {
		this.useFileAmount = useFileAmount;
	}

	public int getDefaultSDcash() {
		return defaultSDcash;
	}

	public void setDefaultSDcash(int defaultSDcash) {
		this.defaultSDcash = defaultSDcash;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getUpdated() {
		return updated;
	}

	public void setUpdated(Date updated) {
		this.updated = updated;
	}

	@Override
	public String toString() {
		return "SDCashBackProgramConfig [id=" + id + ", subsidiaryName="
				+ subsidiaryName + ", S3AccessId=" + S3AccessId
				+ ", S3SecretKey=" + S3SecretKey + ", S3BucketName="
				+ S3BucketName + ", S3SourceDirName=" + S3SourceDirName
				+ ", S3FailDirName=" + S3FailDirName + ", S3SuccessDirName="
				+ S3SuccessDirName + ", isEnabled=" + isEnabled
				+ ", activityId=" + activityId + ", useFileAmount="
				+ useFileAmount + ", defaultSDcash=" + defaultSDcash
				+ ", lastProcessed=" + lastProcessed + ", sdcashEmailTemplate="
				+ sdcashEmailTemplate + ", createUserEmailTemplate="
				+ createUserEmailTemplate + ", created=" + created
				+ ", updated=" + updated + "]";
	}

	
}
