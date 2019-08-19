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
@Table(name = "customer_email_score", catalog = "ums")
public class CustomerEmailScore implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 4081475432532835620L;
    private Integer           id;
    private String            email;
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

    @Column(name = "email", nullable=false)
    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
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
