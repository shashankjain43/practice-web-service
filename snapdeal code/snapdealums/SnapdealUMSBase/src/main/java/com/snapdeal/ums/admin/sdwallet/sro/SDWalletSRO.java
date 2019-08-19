package com.snapdeal.ums.admin.sdwallet.sro;

import java.io.Serializable;
import java.util.Date;
import com.dyuproject.protostuff.Tag;

public class SDWalletSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 5922769053110382429L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private Integer           userId;
    @Tag(3)
    private Integer           amount;
    @Tag(4)
    private Date              expiry;
    @Tag(5)
    private Integer           activityId;
    @Tag(6)
    private Date              created;
    @Tag(7)
    private Date              updated;
    @Tag(8)
    private Integer           originalAmount;
    @Tag(9)
    private String            referenceId;

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

    public Integer getActivityId() {
        return activityId;
    }

    public void setActivityId(Integer activityId) {
        this.activityId = activityId;
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

    public Integer getOriginalAmount() {
        return originalAmount;
    }

    public void setOriginalAmount(Integer originalAmount) {
        this.originalAmount = originalAmount;
    }

    public String getReferenceId() {
        return referenceId;
    }

    public void setReferenceId(String referenceId) {
        this.referenceId = referenceId;
    }

}
