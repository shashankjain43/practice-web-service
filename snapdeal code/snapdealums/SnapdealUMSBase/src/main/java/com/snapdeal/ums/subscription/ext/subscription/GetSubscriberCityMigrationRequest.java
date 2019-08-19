
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetSubscriberCityMigrationRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = 4746095655576564259L;
    @Tag(3)
    private String emailId;
    @Tag(4)
    private String city;

    public GetSubscriberCityMigrationRequest() {
    }
    
    
    public GetSubscriberCityMigrationRequest(String emailId, String city) {
        super();
        this.emailId = emailId;
        this.city = city;
    }


    public String getEmailId() {
        return emailId;
    }

    public void setEmailId(String emailId) {
        this.emailId = emailId;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

}
