package com.snapdeal.ums.services.facebook.sro;

import java.io.Serializable;
import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class FacebookLikeSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6716327807683217L;
    @Tag(1)
    private Long              id;
    @Tag(2)
    private Long              pageId;
    @Tag(3)
    private String            pageName;
    @Tag(4)
    private String            pageCategory;
    @Tag(5)
    private Date              likeTime;
    @Tag(6)
    private Long              facebookUserId;
    @Tag(7)
    private UserSRO           userSRO;
    @Tag(8)
    private String            email;
    @Tag(9)
    private Date              createdAt;
    @Tag(10)
    private Date              updatedAt;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getPageId() {
        return pageId;
    }

    public void setPageId(Long pageId) {
        this.pageId = pageId;
    }

    public String getPageName() {
        return pageName;
    }

    public void setPageName(String pageName) {
        this.pageName = pageName;
    }

    public String getPageCategory() {
        return pageCategory;
    }

    public void setPageCategory(String pageCategory) {
        this.pageCategory = pageCategory;
    }

    public Date getLikeTime() {
        return likeTime;
    }

    public void setLikeTime(Date likeTime) {
        this.likeTime = likeTime;
    }

    public Long getFacebookUserId() {
        return facebookUserId;
    }

    public void setFacebookUserId(Long facebookUserId) {
        this.facebookUserId = facebookUserId;
    }

    public UserSRO getUserSRO() {
        return userSRO;
    }

    public void setUserSRO(UserSRO userSRO) {
        this.userSRO = userSRO;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }

    public Date getUpdatedAt() {
        return updatedAt;
    }

    public void setUpdatedAt(Date updatedAt) {
        this.updatedAt = updatedAt;
    }

}
