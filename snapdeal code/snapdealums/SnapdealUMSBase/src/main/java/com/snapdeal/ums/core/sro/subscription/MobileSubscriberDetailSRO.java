package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class MobileSubscriberDetailSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8217942862358145104L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            mobile;
    @Tag(3)
    private boolean           verified;
    @Tag(4)
    private String            uid;
    @Tag(5)
    private Date              updated;
    @Tag(6)
    private Date              created;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
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

}
