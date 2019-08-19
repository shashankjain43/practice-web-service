package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class EmailMobileAssociationSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3335830829818934216L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            email;
    @Tag(3)
    private String            mobile;
    @Tag(4)
    private boolean           verified;
    @Tag(5)
    private Date              created;
    
    public EmailMobileAssociationSRO() {
        super();
    }
    
    public EmailMobileAssociationSRO(String email, String mobile, boolean verified, Date created) {
        super();
        this.email = email;
        this.mobile = mobile;
        this.verified = verified;
        this.created = created;
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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
