
package com.snapdeal.ums.services.ext.smsservice;

import java.util.HashMap;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAffiliateSubscriptionSmsRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -6623640200078659732L;
    @Tag(3)
    private String mobile;
    @Tag(4)
    private List<HashMap<String, String>> listOfMap;

    public SendAffiliateSubscriptionSmsRequest() {
    }

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public List<HashMap<String, String>> getListOfMap() {
        return listOfMap;
    }

    public void setListOfMap(List<HashMap<String, String>> listOfMap) {
        this.listOfMap = listOfMap;
    }

}
