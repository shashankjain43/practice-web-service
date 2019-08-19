package com.snapdeal.ums.core.entity;

// Generated 16 Aug, 2010 10:08:39 PM by Hibernate Tools 3.2.4.GA

import static javax.persistence.GenerationType.IDENTITY;

import java.util.Date;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.Table;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import javax.xml.bind.annotation.XmlRootElement;

/**
 * Landing Page Content
 */
@Entity
@Table(name = "affiliate_subscription_offer", catalog = "ums")
public class AffiliateSubscriptionOffer implements java.io.Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -3840220147053844686L;
    private Integer           id;
    private String            offerName;
    private String            image;
    private String            promoCode;
    private boolean           enabled;
    private boolean           mobileEnabled;
    private Date              created;
    private Date              updated;
    private String            mobileImage;
    private String            offerText;

    @Id
    @GeneratedValue(strategy = IDENTITY)
    @Column(name = "id", unique = true, nullable = false)
    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    @Column(name = "offer_name", nullable = false, length = 100)
    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    @Column(name = "image", nullable = false, length = 100)
    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    @Column(name = "promo_code", nullable = false, length = 48)
    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    @Column(name = "enabled")
    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
    }
    @Column(name = "mobile_enable")
    public boolean isMobileEnabled() {
        return mobileEnabled;
    }

    public void setMobileEnabled(boolean mobileEnabled) {
        this.mobileEnabled = mobileEnabled;
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

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }
   
    @Column(name = "mobile_image",  length = 100)
    public String getMobileImage() {
        return mobileImage;
    }
    
    @Column(name = "offer_text", length = 100)
    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

}
