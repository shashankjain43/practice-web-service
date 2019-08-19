package com.snapdeal.ums.ext.facebook;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;
import com.snapdeal.ums.services.facebook.sro.FacebookUserSRO;

public class FacebookUserRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = 1793515243942664691L;

    @Tag(3)
    private FacebookUserSRO   fbUser;

    @Tag(4)
    private UserSRO           user;

    @Tag(5)
    private Long              id;

    @Tag(6)
    private String            email;

    public FacebookUserRequest() {
        super();
    }

    public FacebookUserRequest(FacebookUserSRO fbUser) {
        super();
        this.fbUser = fbUser;
    }

    public FacebookUserRequest(UserSRO user) {
        super();
        this.user = user;
    }

    public FacebookUserRequest(Long id) {
        super();
        this.id = id;
    }

    public FacebookUserRequest(String email) {
        super();
        this.email = email;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public FacebookUserSRO getFbUser() {
        return fbUser;
    }

    public void setFbUser(FacebookUserSRO fbUser) {
        this.fbUser = fbUser;
    }
}
