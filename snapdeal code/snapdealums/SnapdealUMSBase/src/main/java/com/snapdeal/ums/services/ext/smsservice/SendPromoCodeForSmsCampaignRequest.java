
package com.snapdeal.ums.services.ext.smsservice;

import java.util.Date;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendPromoCodeForSmsCampaignRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -3385076558519032672L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String code;
    @Tag(5)
    private Date expDate;

    
    public SendPromoCodeForSmsCampaignRequest(String mobile, String code, Date expDate) {
        super();
        this.mobile = mobile;
        this.code = code;
        this.expDate = expDate;
    }

    public SendPromoCodeForSmsCampaignRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public Date getExpDate() {
        return expDate;
    }

    public void setExpDate(Date expDate) {
        this.expDate = expDate;
    }

}
