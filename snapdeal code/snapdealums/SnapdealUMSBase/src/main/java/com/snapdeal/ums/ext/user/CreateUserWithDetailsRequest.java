package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.core.sro.user.UserRoleSRO.Role;

public class CreateUserWithDetailsRequest extends ServiceRequest{

	private static final long serialVersionUID = 2974562081943582687L;
	
	@Tag(3)
	private UserSRO userWithPlainPassword;
	
	@Tag(4)
	private String source;
	
	@Tag(5)
	private Role initialRole;
	
	@Tag(6)
    private String targetUrl;
	
    @Tag(7)
    private boolean autocreated;
	
	public CreateUserWithDetailsRequest() {
    }

	public UserSRO getUserWithPlainPassword() {
		return userWithPlainPassword;
	}

	public void setUserWithPlainPassword(UserSRO userWithPlainPassword) {
		this.userWithPlainPassword = userWithPlainPassword;
	}

	public String getSource() {
		return source;
	}

	public void setSource(String source) {
		this.source = source;
	}

	public Role getInitialRole() {
		return initialRole;
	}

	public void setInitialRole(Role initialRole) {
		this.initialRole = initialRole;
	}

	public String getTargetUrl() {
		return targetUrl;
	}

	public void setTargetUrl(String targetUrl) {
		this.targetUrl = targetUrl;
	}

	public boolean isAutocreated() {
		return autocreated;
	}

	public void setAutocreated(boolean autocreated) {
		this.autocreated = autocreated;
	}
	
}
