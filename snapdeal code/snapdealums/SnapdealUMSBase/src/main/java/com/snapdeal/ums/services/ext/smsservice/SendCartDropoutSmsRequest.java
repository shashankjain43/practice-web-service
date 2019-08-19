
package com.snapdeal.ums.services.ext.smsservice;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCartDropoutSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -8610754165264229740L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private String userName;
    @Tag(5)
    private String catalogText;
    

    public SendCartDropoutSmsRequest(String mobile, String userName, String catalogText) {
        super();
        this.mobile = mobile;
        this.userName = userName;
        this.catalogText = catalogText;
    }

    public SendCartDropoutSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getCatalogText() {
        return catalogText;
    }

    public void setCatalogText(String catalogText) {
        this.catalogText = catalogText;
    }

}
