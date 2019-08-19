package com.snapdeal.ums.core.entity;

import java.io.Serializable;
import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.persistence.Version;

import de.ruedigermoeller.serialization.annotations.Transient;


/**
 * @author himanshu
 */
@Entity
@Table(name = "sd_wallet", catalog = "ums")
public class SDWallet implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1650036427863717103L;

    private Integer           id;
    private Integer           userId;
    private Integer           amount;
    private Date              expiry;
    private Integer           activityId;
    private Date              created;
    private Date              updated;
    private Integer           originalAmount;
    private String            referenceId;
    private Integer           version;
    
    public SDWallet() {
    }

    @Column(name = "VERSION")
    @Version
    public Integer getVersion() {
        return version;
    }

    public void setVersion(Integer version) {
        this.version = version;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id", nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    //@ManyToOne(fetch = FetchType.LAZY)
    //@JoinColumn(name = "user_id", nullable = false)
    @Column(name = "user_id", nullable = false)
    public Integer getUserId() {
		return userId;
	}

	public void setUserId(Integer userId) {
		this.userId = userId;
	}

    @Column(name = "amount", nullable = false)
    public Integer getAmount() {
        return amount;
    }

    

	public void setAmount(Integer amount) {
        this.amount = amount;
    }

    @Column(name = "expiry", nullable = false)
    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    @Column(name = "activity_id", nullable = false)
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "created")
    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    @Temporal(TemporalType.TIMESTAMP)
    @Column(name = "updated", updatable = false, insertable = false)
    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    @Column(name = "original_amount", nullable = false)
    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    @Column(name = "reference_id")
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

}
