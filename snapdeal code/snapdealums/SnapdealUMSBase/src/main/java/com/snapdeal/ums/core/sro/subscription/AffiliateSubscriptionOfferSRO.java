package com.snapdeal.ums.core.sro.subscription;

import java.io.Serializable;
import java.util.Date;

import javax.xml.bind.annotation.XmlRootElement;

import com.dyuproject.protostuff.Tag;

@XmlRootElement
public class AffiliateSubscriptionOfferSRO implements Serializable {

    /**
     * 
     */
    private static final long serialVersionUID = -1899212106819856067L;
    @Tag(1)
    private Integer           id;
    @Tag(2)
    private String            offerName;
    @Tag(3)
    private String            image;
    @Tag(4)
    private String            promoCode;
    @Tag(5)
    private boolean           enabled;
    @Tag(6)
    private boolean           mobileEnabled;
    @Tag(7)
    private Date              created;
    @Tag(8)
    private Date              updated;
    @Tag(9)
    private String            mobileImage;
    @Tag(10)
    private String            offerText;

    
    public boolean isMobileEnabled() {
        return mobileEnabled;
    }

    public void setMobileEnabled(boolean mobileEnabled) {
        this.mobileEnabled = mobileEnabled;
    }

    public String getMobileImage() {
        return mobileImage;
    }

    public void setMobileImage(String mobileImage) {
        this.mobileImage = mobileImage;
    }

    public String getOfferText() {
        return offerText;
    }

    public void setOfferText(String offerText) {
        this.offerText = offerText;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(String promoCode) {
        this.promoCode = promoCode;
    }

    public boolean isEnabled() {
        return enabled;
    }

    public void setEnabled(boolean enabled) {
        this.enabled = enabled;
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

	@Override
	public String toString() {
		return "AffiliateSubscriptionOfferSRO [id=" + id + ", offerName="
				+ offerName + ", image=" + image + ", promoCode=" + promoCode
				+ ", enabled=" + enabled + ", mobileEnabled=" + mobileEnabled
				+ ", created=" + created + ", updated=" + updated
				+ ", mobileImage=" + mobileImage + ", offerText=" + offerText
				+ "]";
	}
    
}
