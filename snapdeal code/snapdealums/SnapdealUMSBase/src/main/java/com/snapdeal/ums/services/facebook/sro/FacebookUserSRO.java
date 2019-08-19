package com.snapdeal.ums.services.facebook.sro;

import java.io.Serializable;
import java.util.Date;
import java.util.HashSet;
import java.util.Set;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class FacebookUserSRO implements Serializable {

    /**
     * 
     */
    private static final long    serialVersionUID = -3293069910679534934L;
    @Tag(1)
    private Long                 id;
    @Tag(2)
    private Long                 facebookId;
    @Tag(3)
    private String               emailId;
    @Tag(4)
    private UserSRO              userSRO;
    @Tag(5)
    private FacebookProfileSRO   fbProfile;
    @Tag(6)
    private Set<FacebookLikeSRO> fbLikes          = new HashSet<FacebookLikeSRO>();
    @Tag(7)
    private Date                 created;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getFacebookId() {
        return facebookId;
    }

    public void setFacebookId(Long facebookId) {
        this.facebookId = facebookId;
    }

    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public UserSRO getUserSRO() {
        return userSRO;
    }

    public void setUserSRO(UserSRO userSRO) {
        this.userSRO = userSRO;
    }

    public FacebookProfileSRO getFbProfile() {
        return fbProfile;
    }

    public void setFbProfile(FacebookProfileSRO fbProfile) {
        this.fbProfile = fbProfile;
    }

    public Set<FacebookLikeSRO> getFbLikes() {
        return fbLikes;
    }

    public void setFbLikes(Set<FacebookLikeSRO> fbLikes) {
        this.fbLikes = fbLikes;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }
    
    public void setSnapdealUser(UserSRO user){
        this.userSRO = user;
        for (FacebookLikeSRO fbLike : fbLikes) {
            fbLike.setUserSRO(user);
        }
    }

}
