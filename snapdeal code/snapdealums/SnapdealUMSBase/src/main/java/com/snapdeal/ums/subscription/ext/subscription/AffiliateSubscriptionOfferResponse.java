package com.snapdeal.ums.subscription.ext.subscription;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.subscription.AffiliateSubscriptionOfferSRO;

public class AffiliateSubscriptionOfferResponse extends ServiceResponse {

    /**
     * 
     */
    private static final long                   serialVersionUID = -1193822577664820291L;
    @Tag(5)
    private AffiliateSubscriptionOfferSRO       affiliateSubscriptionOfferSRO;

    @Tag(6)
    private List<AffiliateSubscriptionOfferSRO> affiliateSubscriptionOfferSROs = new ArrayList<AffiliateSubscriptionOfferSRO>();

    public AffiliateSubscriptionOfferResponse() {
        super();
    }

    public AffiliateSubscriptionOfferSRO getAffiliateSubscriptionOfferSRO() {
        return affiliateSubscriptionOfferSRO;
    }


    public void setAffiliateSubscriptionOfferSRO(AffiliateSubscriptionOfferSRO affiliateSubscriptionOfferSRO) {
        this.affiliateSubscriptionOfferSRO = affiliateSubscriptionOfferSRO;
    }


    public List<AffiliateSubscriptionOfferSRO> getAffiliateSubscriptionOfferSROs() {
        return affiliateSubscriptionOfferSROs;
    }

    public void setAffiliateSubscriptionOfferSROs(List<AffiliateSubscriptionOfferSRO> affiliateSubscriptionOfferSROs) {
        this.affiliateSubscriptionOfferSROs = affiliateSubscriptionOfferSROs;
    }

}
