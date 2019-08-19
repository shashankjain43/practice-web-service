package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class EmailSubscriberDetailSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 3652253049347787200L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            email;
    @Tag(3)
    private String            code;
    @Tag(4)
    private boolean           verified;
    @Tag(5)
    private String            uid;
    @Tag(6)
    private SubscriberProfileSRO subscriberProfile;
    @Tag(7)
    private Date              updated;
    @Tag(8)
    private Date              created;
    @Tag(9)
    private boolean           junk;
    @Tag(10)
    private String            gender;

    public EmailSubscriberDetailSRO() {

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

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }

    public SubscriberProfileSRO getSubscriberProfile() {
        return subscriberProfile;
    }

    public void setSubscriberProfile(SubscriberProfileSRO subscriberProfileId) {
        this.subscriberProfile = subscriberProfileId;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public boolean isJunk() {
        return junk;
    }

    public void setJunk(boolean junk) {
        this.junk = junk;
    }

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

}
