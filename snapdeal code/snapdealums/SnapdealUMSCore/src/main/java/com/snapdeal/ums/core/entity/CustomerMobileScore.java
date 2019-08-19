package com.snapdeal.ums.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "customer_mobile_score", catalog = "ums")
public class CustomerMobileScore implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -835358513297985746L;
    private Integer           id;
    private String            mobile;
    private String            riskLevel;
    private Date              created;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "mobile", nullable=false)
    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    @Column(name = "risk_level", nullable=false)
    public String getRiskLevel() {
        return riskLevel;
    }

    public void setRiskLevel(String riskLevel) {
        this.riskLevel = riskLevel;
    }

    @Column(name = "created", nullable=false)
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

}
