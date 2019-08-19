package com.snapdeal.ums.ext.facebook;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.services.facebook.sro.FacebookLikeSRO;

public class FacebookLikesRequest extends ServiceRequest {

    /**
     * 
     */
    private static final long serialVersionUID = -7431233746589422767L;

    @Tag(3)
    private FacebookLikeSRO   fbLike;

    @Tag(4)
    private String            email;

    @Tag(5)
    private Long              id;

    public FacebookLikesRequest() {
        super();
    }

    public FacebookLikesRequest(FacebookLikeSRO fbLike) {
        super();
        this.fbLike = fbLike;
    }

    public FacebookLikesRequest(String email) {
        super();
        this.email = email;
    }

    public FacebookLikesRequest(Long id) {
        super();
        this.id = id;
    }

    public FacebookLikeSRO getFbLike() {
        return fbLike;
    }

    public void setFbLike(FacebookLikeSRO fbLike) {
        this.fbLike = fbLike;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

}
