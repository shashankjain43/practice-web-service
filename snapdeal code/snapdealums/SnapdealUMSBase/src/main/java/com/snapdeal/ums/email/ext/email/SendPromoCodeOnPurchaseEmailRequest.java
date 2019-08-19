
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.core.sro.order.PromoCodeSRO;

public class SendPromoCodeOnPurchaseEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6515680714168999511L;
	@Tag(3)
    private Integer orderId;
    @Tag(4)
    private PromoCodeSRO promoCode;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;

    public SendPromoCodeOnPurchaseEmailRequest() {
    }
    

    public SendPromoCodeOnPurchaseEmailRequest(Integer orderId, PromoCodeSRO promoCode, String contextPath, String contentPath) {
        super();
        this.orderId = orderId;
        this.promoCode = promoCode;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }


  
    public Integer getOrderId() {
        return orderId;
    }


    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }


    public PromoCodeSRO getPromoCode() {
        return promoCode;
    }

    public void setPromoCode(PromoCodeSRO promoCode) {
        this.promoCode = promoCode;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}

