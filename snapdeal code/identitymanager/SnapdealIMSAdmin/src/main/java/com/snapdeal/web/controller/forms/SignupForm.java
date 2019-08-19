/*
 *  Copyright 2010 Jasper Infotech (P) Limited . All Rights Reserved.
 *  JASPER INFOTECH PROPRIETARY/CONFIDENTIAL. Use is subject to license terms.
 *  
 *  @version     1.0, Aug 19, 2010
 *  @author singla
 */
package com.snapdeal.web.controller.forms;

import javax.validation.constraints.AssertTrue;

import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.Length;
import org.hibernate.validator.constraints.NotEmpty;

public class SignupForm {

    @Email(message="Please provide a valid email id")
    @NotEmpty(message="Please provide a valid email id")
    private String  email;

    @Length(min = 6, max = 15, message="Must be between 6 and 15 characters")
    private String  password;

    @Length(min = 6, max = 15, message="Must be between 6 and 15 characters")
    private String  confirmPassword;

    private String  source;

    private String  targetUrl;

    private boolean passwordMatches;

    @AssertTrue(message="Should be same as Password")
    public boolean isPasswordMatches() {
        if (this.password != null) {
            passwordMatches = this.password.equals(this.confirmPassword);
        }
        return passwordMatches;
    }
    
    @AssertTrue(message="Please accept our policy")
    private boolean agree;

    public String getEmail() {
        return email;
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

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }
    
    public boolean isAgree() {
        return agree;
    }
    
    public void setAgree(boolean agree) {
        this.agree = agree;
    }
}
