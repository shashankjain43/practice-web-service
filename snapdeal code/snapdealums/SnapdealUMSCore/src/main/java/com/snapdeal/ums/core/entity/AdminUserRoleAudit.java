package com.snapdeal.ums.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;

import static javax.persistence.GenerationType.IDENTITY;

@Entity
@Table(name = "admin_user_role_audit", catalog = "ums")
public class AdminUserRoleAudit implements Serializable {

	/**
	 * 
	 */
	private static final long serialVersionUID = 477423487151148288L;
	private Integer id;
	private String userRole;
	private Date updated;
	private String emailRoleGiver;
	private Integer userId;
	private String action;

	public AdminUserRoleAudit() {

	}

	public AdminUserRoleAudit(String emailRoleGiver, Integer userId, String userRole,
			String action) {
		this.emailRoleGiver = emailRoleGiver;
		this.userId = userId;
		this.action = action;
		this.userRole=userRole;
	}

	@Id
	@GeneratedValue(strategy = IDENTITY)
	@Column(name = "id", unique = true, nullable = false)
	public Integer getId() {
		return this.id;
	}

	public void setId(Integer id) {
		this.id = id;
	}

	@Column(name = "role", length = 256)
	public String getUserRole() {
		return userRole;
	}

	public void setUserRole(String userRole) {
		this.userRole = userRole;
	}

	@Column(name = "user_id", nullable = false)
	public Integer getUserId() {
		return this.userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

	@Column(name = "action_taken_by", length = 256)
	public String getEmailRoleGiver() {
		return this.emailRoleGiver;
	}

	public void setEmailRoleGiver(String emailRoleGiver) {
		this.emailRoleGiver = emailRoleGiver;
	}

	@Column(name = "action", length=30)
	public String getAction() {
		return action;
	}

	public void setAction(String action) {
		this.action = action;
	}
	
	@Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

}
