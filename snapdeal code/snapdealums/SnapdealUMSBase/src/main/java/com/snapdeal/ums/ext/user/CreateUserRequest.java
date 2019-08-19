
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserRoleSRO.Role;

public class CreateUserRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4970785712022802813L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String password;
    @Tag(5)
    private Role initialRole;
    @Tag(6)
    private String source;
    @Tag(7)
    private String targetUrl;
    @Tag(8)
    private boolean autocreated;

    public CreateUserRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getInitialRole() {
        return initialRole;
    }

    public void setInitialRole(Role initialRole) {
        this.initialRole = initialRole;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public boolean getAutocreated() {
        return autocreated;
    }

    public void setAutocreated(boolean autocreated) {
        this.autocreated = autocreated;
    }

    public CreateUserRequest(String email, String password, Role initialRole, String source, String targetUrl, boolean autocreated) {
        this.email = email;
        this.password = password;
        this.initialRole = initialRole;
        this.source = source;
        this.targetUrl = targetUrl;
        this.autocreated = autocreated;
    }

}
