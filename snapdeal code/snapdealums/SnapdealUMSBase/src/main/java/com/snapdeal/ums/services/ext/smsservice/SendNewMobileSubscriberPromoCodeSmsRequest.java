
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.core.sro.order.PromoCodeSRO;

public class SendNewMobileSubscriberPromoCodeSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 629174705027192190L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private PromoCodeSRO promoCode;

    
    public SendNewMobileSubscriberPromoCodeSmsRequest(String mobile, PromoCodeSRO promoCode) {
        super();
        this.mobile = mobile;
        this.promoCode = promoCode;
    }

    public SendNewMobileSubscriberPromoCodeSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public PromoCodeSRO getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromoCodeSRO promoCode) {
        this.promoCode = promoCode;
    }

}
