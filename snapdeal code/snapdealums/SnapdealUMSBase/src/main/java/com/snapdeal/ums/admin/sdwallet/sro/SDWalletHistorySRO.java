package com.snapdeal.ums.admin.sdwallet.sro;

import java.io.Serializable;
import java.util.Date;
import com.dyuproject.protostuff.Tag;

public class SDWalletHistorySRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 8472166843067443255L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Integer           userId;
    @Tag(3)
    private Integer           amount;
    @Tag(4)
    private Date              expiry;
    @Tag(5)
    private Date              created;
    @Tag(6)
    private Date              updated;
    @Tag(7)
    private String            mode;
    @Tag(8)
    private Integer           activityId;
    @Tag(9)
    private Integer           transactionId;
    @Tag(10)
    private String            referenceId;
    @Tag(11)
    private String            source;
    @Tag(12)
    private Integer           sdWalletId;
    @Tag(13)
    private String            requestedBy;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public Integer getAmount() {
        return amount;
    }

    public void setAmount(Integer amount) {
        this.amount = amount;
    }

    public Date getExpiry() {
        return expiry;
    }

    public void setExpiry(Date expiry) {
        this.expiry = expiry;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public Date getUpdated() {
        return updated;
    }

    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    public String getMode() {
        return mode;
    }

    public void setMode(String mode) {
        this.mode = mode;
    }

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
    }

    public Integer getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(Integer transactionId) {
        this.transactionId = transactionId;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public Integer getSdWalletId() {
        return sdWalletId;
    }

    public void setSdWalletId(Integer sdWalletId) {
        this.sdWalletId = sdWalletId;
    }

    public String getRequestedBy() {
        return requestedBy;
    }

    public void setRequestedBy(String requestedBy) {
        this.requestedBy = requestedBy;
    }

}
