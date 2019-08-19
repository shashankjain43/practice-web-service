package com.snapdeal.ums.core.sro.user;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class CustomerScoreSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -6359706334972119682L;
    @Tag(1)
    private Integer           id;

    @Tag(2)
    private String            email;

    @Tag(3)
    private String            mobile;

    @Tag(4)
    private String            riskLevel;

    @Tag(5)
    private Date              created;

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

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    public String getRiskLevel() {
        return riskLevel;
    }

}
