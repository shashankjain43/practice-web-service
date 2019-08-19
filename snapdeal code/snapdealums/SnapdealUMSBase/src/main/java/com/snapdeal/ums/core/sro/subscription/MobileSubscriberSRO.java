package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;

public class MobileSubscriberSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = 6582417068365956404L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            mobile;
    @Tag(3)
    private int               zoneId;
    @Tag(4)
    private Boolean           subscribed;
    @Tag(5)
    private Date              created;
    @Tag(6)
    private String            subscriptionPage;
    @Tag(7)
    private String            pin;
    @Tag(8)
    private String            reasonUnsubscription;
    @Tag(9)
    private String            suggestionUnsubscription;
    @Tag(10)
    private String            channelCode;
    @Tag(11)
    private String            affiliateTrackingCode;
    @Tag(12)
    private boolean           dnd;
    @Tag(13)
    private boolean           invalid;
    @Tag(14)
    private boolean           verified;
    @Tag(15)
    private Date              updated;
    @Tag(16)
    private boolean           customer;

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

    public int getZoneId() {
        return zoneId;
    }

    public void setZoneId(int zoneId) {
        this.zoneId = zoneId;
    }

    public Boolean isSubscribed() {
        return subscribed;
    }

    public void setSubscribed(Boolean subscribed) {
        this.subscribed = subscribed;
    }

    public Date getCreated() {
        return created;
    }

    public void setCreated(Date created) {
        this.created = created;
    }

    public String getSubscriptionPage() {
        return subscriptionPage;
    }

    public void setSubscriptionPage(String subscriptionPage) {
        this.subscriptionPage = subscriptionPage;
    }

    public String getPin() {
        return pin;
    }

    public void setPin(String pin) {
        this.pin = pin;
    }

    public String getReasonUnsubscription() {
        return reasonUnsubscription;
    }

    public void setReasonUnsubscription(String reasonUnsubscription) {
        this.reasonUnsubscription = reasonUnsubscription;
    }

    public String getSuggestionUnsubscription() {
        return suggestionUnsubscription;
    }

    public void setSuggestionUnsubscription(String suggestionUnsubscription) {
        this.suggestionUnsubscription = suggestionUnsubscription;
    }

    public String getChannelCode() {
        return channelCode;
    }

    public void setChannelCode(String channelCode) {
        this.channelCode = channelCode;
    }

    public String getAffiliateTrackingCode() {
        return affiliateTrackingCode;
    }

    public void setAffiliateTrackingCode(String affiliateTrackingCode) {
        this.affiliateTrackingCode = affiliateTrackingCode;
    }

    public boolean getDnd() {
        return dnd;
    }

    public void setDnd(boolean dnd) {
        this.dnd = dnd;
    }

    public boolean isInvalid() {
        return invalid;
    }

    public void setInvalid(boolean invalid) {
        this.invalid = invalid;
    }

    public boolean isVerified() {
        return verified;
    }

    public void setVerified(boolean verified) {
        this.verified = verified;
    }

    public Boolean getSubscribed() {
        return subscribed;
    }

    /**
     * @return the updated
     */
    public Date getUpdated() {
        return updated;
    }

    /**
     * @param updated the updated to set
     */
    public void setUpdated(Date updated) {
        this.updated = updated;
    }

    /**
     * @return the customer
     */
    public boolean isCustomer() {
        return customer;
    }

    /**
     * @param customer the customer to set
     */
    public void setCustomer(boolean customer) {
        this.customer = customer;
    }

}
