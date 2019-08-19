package com.snapdeal.ums.core.entity;

import static javax.persistence.GenerationType.IDENTITY;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
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
public class SubsidiaryPromotion implements Serializable {

	private static final long serialVersionUID = 6163120995551261437L;

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	private int id;

	@Column(name = "subsidiary_name", nullable = false)
	private String subsidiaryName;

	@Column(name = "location")
	private String location;

	@Column(name = "user_name")
	private String userName;

	@Column(name = "password")
	private String password;

	@Column(name = "is_enabled")
	private boolean isEnabled;

	@Column(name = "activity_id")
	private int activityId;

	@Column(name = "default_sdCash")
	private int defaultSDcash;

	@Temporal(TemporalType.TIMESTAMP)
	@Column(name = "last_processed")
	private Date lastProcessed;

	@OneToOne
	@JoinColumn(name = "email_template_id")
	private EmailTemplate emailTemplate;

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

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
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

	public EmailTemplate getEmailTemplate() {
		return emailTemplate;
	}

	public void setEmailTemplate(EmailTemplate emailTemplate) {
		this.emailTemplate = emailTemplate;
	}

	@Override
	public String toString() {
		return "SubsidiaryPromotion [id=" + id + ", subsidiaryName="
				+ subsidiaryName + ", location=" + location + ", userName="
				+ userName + ", password=" + password + ", isEnabled="
				+ isEnabled + ", activityId=" + activityId + ", defaultSDcash="
				+ defaultSDcash + ", lastProcessed=" + lastProcessed
				+ ", emailTemplate=" + emailTemplate + "]";
	}

}
