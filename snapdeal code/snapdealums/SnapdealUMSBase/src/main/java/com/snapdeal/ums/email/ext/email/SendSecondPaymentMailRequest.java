
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendSecondPaymentMailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 2430734966000845843L;
    @Tag(3)
    private Integer orderId;
    @Tag(4)
    private Integer suborderId;
    @Tag(5)
    private String buyPageUrl;
    @Tag(6)
    private String releaseDate;

    public SendSecondPaymentMailRequest() {
    }
    
    public SendSecondPaymentMailRequest(Integer orderId, Integer suborderId, String buyPageUrl, String releaseDate) {
        super();
        this.orderId = orderId;
        this.suborderId = suborderId;
        this.buyPageUrl = buyPageUrl;
        this.releaseDate = releaseDate;
    }


    public Integer getOrderId() {
        return orderId;
    }

    public void setOrderId(Integer orderId) {
        this.orderId = orderId;
    }

    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

    public String getBuyPageUrl() {
        return buyPageUrl;
    }

    public void setBuyPageUrl(String buyPageUrl) {
        this.buyPageUrl = buyPageUrl;
    }

    public String getReleaseDate() {
        return releaseDate;
    }

    public void setReleaseDate(String releaseDate) {
        this.releaseDate = releaseDate;
    }

}
