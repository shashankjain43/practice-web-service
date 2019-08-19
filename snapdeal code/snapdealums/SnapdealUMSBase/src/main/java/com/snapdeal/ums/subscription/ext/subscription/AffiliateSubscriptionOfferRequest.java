
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.subscription.AffiliateSubscriptionOfferSRO;

public class AffiliateSubscriptionOfferRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2772378893401721454L;
    @Tag(3)
    private String offerName;
    
    @Tag(4)
    private Integer id;
    
    @Tag(5)
    private AffiliateSubscriptionOfferSRO affiliateSubscriptionOffer;

    public AffiliateSubscriptionOfferRequest() {
    }
    
    public AffiliateSubscriptionOfferRequest(String offerName) {
        super();
        this.offerName = offerName;
    }
    
    

    public AffiliateSubscriptionOfferRequest(AffiliateSubscriptionOfferSRO affiliateSubscriptionOffer) {
        super();
        this.affiliateSubscriptionOffer = affiliateSubscriptionOffer;
    }

    public AffiliateSubscriptionOfferRequest(Integer id) {
        super();
        this.id = id;
    }

    public String getOfferName() {
        return offerName;
    }

    public void setOfferName(String offerName) {
        this.offerName = offerName;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public AffiliateSubscriptionOfferSRO getAffiliateSubscriptionOffer() {
        return affiliateSubscriptionOffer;
    }

    public void setAffiliateSubscriptionOffer(AffiliateSubscriptionOfferSRO affiliateSubscriptionOffer) {
        this.affiliateSubscriptionOffer = affiliateSubscriptionOffer;
    }

}
