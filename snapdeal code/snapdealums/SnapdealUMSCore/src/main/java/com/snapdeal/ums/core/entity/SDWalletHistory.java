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

import com.snapdeal.ums.core.entity.SDWalletTransaction;

/**
 * @author himanshu
 */
@Entity
@Table(name = "sd_wallet_history", catalog = "ums")
public class SDWalletHistory implements Serializable {

    /**
     * 
     */
    private static final long   serialVersionUID = -6184169053054262651L;

    private Integer             id;
    private Integer             sdWalletId;
    private Integer             userId;
    private Integer             amount;
    private Date                expiry;
    private Date                created;
    private Date                updated;
    private String              mode;
    private Integer             activityId;
    private SDWalletTransaction sdWalletTransaction;
    /**
     * only keeps the id of SDWalletTxnHistory entity as
     * we do not need to show full record details to any interface
     */
    private Integer             sdWalletTxnHistoryId;
    private String              referenceId;
    private String              source;
    private String              requestedBy;

    public SDWalletHistory() {
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

    @Column(name = "mode", nullable = false)
    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    @Column(name = "activity_id", nullable = false)
    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "transaction_id", nullable = false)
    public SDWalletTransaction getSdWalletTransaction() {
        return this.sdWalletTransaction;
    }

    public void setSdWalletTransaction(SDWalletTransaction sdWalletTransaction) {
        this.sdWalletTransaction = sdWalletTransaction;
    }
    
    @Column(name = "sdWallet_txn_history_id", nullable = true)
    public Integer getSdWalletTxnHistoryId() {
    	return sdWalletTxnHistoryId;
    }

	public void setSdWalletTxnHistoryId(Integer sdWalletTxnHistoryId) {
		this.sdWalletTxnHistoryId = sdWalletTxnHistoryId;
	}

	@Column(name = "reference_id")
    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    @Column(name = "source")
    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    @Column(name = "sd_wallet_id", nullable = false)
    public Integer getSdWalletId() {
        return sdWalletId;
    }

    public void setSdWalletId(Integer sdWalletId) {
        this.sdWalletId = sdWalletId;
    }

    @Column(name = "requested_by")
    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }
}
