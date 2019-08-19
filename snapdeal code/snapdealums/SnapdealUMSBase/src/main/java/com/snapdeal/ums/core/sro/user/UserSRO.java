/*
 *  Copyright 2012 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, 23-Jul-2012
 *  @author Jitesh
 */
package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;
import java.util.Date;
import java.util.Set;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.audit.annotation.AuditableClass;
import com.snapdeal.base.audit.annotation.AuditableField;

@AuditableClass
public class UserSRO implements Serializable {

	/**
     * 
     */
	private static final long serialVersionUID = 1232503366286126021L;

	@AuditableField()
	@Tag(1)
	private Integer id;

	@AuditableField()
	@Tag(2)
	private String email;
	@Tag(3)
	private String password;
	@AuditableField
	@Tag(4)
	private boolean enabled;

	@Tag(5)
	private boolean emailVerified;
	@AuditableField
	@Tag(6)
	private boolean mobileVerified;

	@Tag(7)
	private String displayName;

	@Tag(8)
	private String firstName;

	@Tag(9)
	private String middleName;

	@Tag(10)
	private String lastName;

	@Tag(11)
	private String gender;
	@Tag(12)
	private Date birthday;
	@AuditableField
	@Tag(13)
	private String mobile;

	@Tag(14)
	private String photo;

	@Tag(15)
	private String channelCode;

	@Tag(16)
	private Integer referredBy;

	@Tag(17)
	private int referralCount;

	@Tag(18)
	private String mobileVerificationCode;

	@Tag(19)
	private String emailVerificationCode;
	@Tag(20)
	private Date created;
	@Tag(21)
	private Date modified;

	@Tag(22)
	private int sdCash;

	@Tag(23)
	private int sdCashEarned;

	@Tag(25)
	private String source;

	@Tag(26)
	private boolean autoCreated;

	@Tag(27)
	private String location;

	@Tag(28)
	private String uid;

	@Tag(29)
	private String friendsUids;

	@Tag(30)
	private Integer subscriberProfileId;

	@Tag(31)
	private String accountType;

	@AuditableField
	@Tag(32)
	private Set<UserRoleSRO> userRoles;

	public String getPhoto() {
		return photo;
	}

	public void setPhoto(String photo) {
		this.photo = photo;
	}

	public Integer getReferredBy() {
		return referredBy;
	}

	public void setReferredBy(Integer referredBy) {
		this.referredBy = referredBy;
	}

	public int getReferralCount() {
		return referralCount;
	}

	public void setReferralCount(int referralCount) {
		this.referralCount = referralCount;
	}

	public String getMobileVerificationCode() {
		return mobileVerificationCode;
	}

	public void setMobileVerificationCode(String mobileVerificationCode) {
		this.mobileVerificationCode = mobileVerificationCode;
	}

	public String getEmailVerificationCode() {
		return emailVerificationCode;
	}

	public void setEmailVerificationCode(String emailVerificationCode) {
		this.emailVerificationCode = emailVerificationCode;
	}

	public boolean getAutoCreated() {
		return autoCreated;
	}

	public void setAutoCreated(boolean autoCreated) {
		this.autoCreated = autoCreated;
	}

	public String getFriendsUids() {
		return friendsUids;
	}

	public void setFriendsUids(String friendsUids) {
		this.friendsUids = friendsUids;
	}

	public Integer getSubscriberProfileId() {
		return subscriberProfileId;
	}

	public void setSubscriberProfileId(Integer subscriberProfileId) {
		this.subscriberProfileId = subscriberProfileId;
	}

	public void setBirthday(Date birthday) {
		this.birthday = birthday;
	}

	public UserSRO() {
	}

	public Integer getId() {
		return id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public boolean isEnabled() {
		return enabled;
	}

	public void setEnabled(boolean enabled) {
		this.enabled = enabled;
	}

	public boolean isEmailVerified() {
		return emailVerified;
	}

	public void setEmailVerified(boolean emailVerified) {
		this.emailVerified = emailVerified;
	}

	public boolean isMobileVerified() {
		return mobileVerified;
	}

	public void setMobileVerified(boolean mobileVerified) {
		this.mobileVerified = mobileVerified;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}

	public String getFirstName() {
		return firstName;
	}

	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}

	public String getMiddleName() {
		return middleName;
	}

	public void setMiddleName(String middleName) {
		this.middleName = middleName;
	}

	public String getLastName() {
		return lastName;
	}

	public void setLastName(String lastName) {
		this.lastName = lastName;
	}

	public String getGender() {
		return gender;
	}

	public void setGender(String gender) {
		this.gender = gender;
	}

	public String getMobile() {
		return mobile;
	}

	public void setMobile(String mobile) {
		this.mobile = mobile;
	}

	public String getChannelCode() {
		return channelCode;
	}

	public void setChannelCode(String channelCode) {
		this.channelCode = channelCode;
	}

	public Date getCreated() {
		return created;
	}

	public void setCreated(Date created) {
		this.created = created;
	}

	public Date getModified() {
		return modified;
	}

	public void setModified(Date modified) {
		this.modified = modified;
	}

	public int getSdCash() {
		return sdCash;
	}

	public void setSdCash(int sdCash) {
		this.sdCash = sdCash;
	}

	public int getSdCashEarned() {
		return sdCashEarned;
	}

	public void setSdCashEarned(int sdCashEarned) {
		this.sdCashEarned = sdCashEarned;
	}

	public String getLocation() {
		return location;
	}

	public void setLocation(String location) {
		this.location = location;
	}

	public String getAccountType() {
		return accountType;
	}

	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}

	public Set<UserRoleSRO> getUserRoles() {
		return userRoles;
	}

	public void setUserRoles(Set<UserRoleSRO> userRoleSROs) {
		this.userRoles = userRoleSROs;
	}

	public String getPassword() {
		return password;
	}

	public void setPassword(String password) {
		this.password = password;
	}

	public Date getBirthday() {
		return birthday;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public String getUid() {
		return uid;
	}

	public void setUid(String uid) {
		this.uid = uid;
	}

	public UserRoleSRO getUserRoleSro(String roleName) {
		for (UserRoleSRO role : getUserRoles()) {
			if (role.getRole().equals(roleName)) {
				return role;
			}
		}
		return null;
	}

	
	
	@Override
	public String toString() {
		StringBuilder stringBuilder = new StringBuilder("UserSRO [");
		stringBuilder.append("Email: ").append(email).append(", ID: ")
				.append(id).append(", Enabled: ").append(enabled)
				.append(", EmailVerified: ").append(emailVerified)
				.append(", MobileVerified: ").append(mobileVerified)
				.append(" EmailVerificationCode: ")
				.append(emailVerificationCode).append(", Source: ")
				.append(source).append(", DisplayName: ").append(displayName)
				.append(", FirstName: ").append(firstName)
				.append(", MiddleName: ").append(middleName)
				.append(", LastName: ").append(lastName).append(", Gender: ")
				.append(gender).append(", Birthday: ").append(birthday)
				.append(", Mobile: ").append(mobile).append(", ChannelCode: ")
				.append(channelCode).append(", ReferredBy: ")
				.append(referredBy).append(", ReferralCount: ")
				.append(referralCount).append(", MobileVerificationCode: ")
				.append(mobileVerificationCode)
				.append(", EmailVerificationCode: ")
				.append(emailVerificationCode).append(", Created: ")
				.append(created).append(", Modified: ").append(modified)
				.append(", SdCash: ").append(sdCash).append(", Source: ")
				.append(source).append(", AutoCreated: ").append(autoCreated)
				.append(", Location: ").append(location).append(", Uid: ")
				.append(uid).append(", SubscriberProfileId: ")
				.append(subscriberProfileId).append(", AccountType: ")
				.append(accountType).

				append(", UserRoles: ").append(userRoles).append(" ]");

		return stringBuilder.toString();
	}
}
