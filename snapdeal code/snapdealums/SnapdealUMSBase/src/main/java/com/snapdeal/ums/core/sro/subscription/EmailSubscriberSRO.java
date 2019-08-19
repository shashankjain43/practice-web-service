package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.ums.core.sro.bulkemail.EmailServiceProviderSRO;

public class EmailSubscriberSRO implements Serializable {

    /**
     * 
     */
    
    public enum Preference {
        DEAL("deal"),
        PRODUCT("product");

        private String name;
        Preference(String name) {
            this.name = name;
        }
        
        public String getName() {
            return name;
        }
        public void setName(String name) {
            this.name = name;
        }
    }
    
    private static final long       serialVersionUID = -378701449055681458L;
    @Tag(1)
    private Integer                 id;
    @Tag(2)
    private String                  email;
    @Tag(3)
    private String                  normalizedEmail;
    @Tag(4)
    private int                     zoneId;
    @Tag(5)
    private Boolean                 subscribed;
    @Tag(6)
    private Date                    updated;
    @Tag(7)
    private Date                    created;
    @Tag(8)
    private String                  subscriptionPage;
    @Tag(9)
    private Boolean                 customer;
    @Tag(10)
    private String                  reasonUnsubscription;
    @Tag(11)
    private String                  suggestionUnsubscription;
    @Tag(12)
    private String                  channelCode;
    @Tag(13)
    private String                  affiliateTrackingCode;
    @Tag(14)
    private boolean                 active;
    @Tag(15)
    private String                  preference;
    @Tag(16)
    private EmailServiceProviderSRO emailServiceProvider;
    @Tag(17)
    private boolean                 junk;

    public EmailSubscriberSRO() {
       
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

    public String getNormalizedEmail() {
        return normalizedEmail;
    }

    public void setNormalizedEmail(String normalizedEmail) {
        this.normalizedEmail = normalizedEmail;
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

    public String getSubscriptionPage() {
        return subscriptionPage;
    }

    public void setSubscriptionPage(String subscriptionPage) {
        this.subscriptionPage = subscriptionPage;
    }

    public Boolean isCustomer() {
        return customer;
    }

    public void setCustomer(Boolean customer) {
        this.customer = customer;
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

    public boolean isActive() {
        return active;
    }

    public void setActive(boolean active) {
        this.active = active;
    }

    public String getPreference() {
        return preference;
    }

    public void setPreference(String preference) {
        this.preference = preference;
    }

    public EmailServiceProviderSRO getEmailServiceProvider() {
        return emailServiceProvider;
    }

    public void setEmailServiceProvider(EmailServiceProviderSRO emailServiceProvider) {
        this.emailServiceProvider = emailServiceProvider;
    }

    public boolean isJunk() {
        return junk;
    }

    public void setJunk(boolean junk) {
        this.junk = junk;
    }

}
