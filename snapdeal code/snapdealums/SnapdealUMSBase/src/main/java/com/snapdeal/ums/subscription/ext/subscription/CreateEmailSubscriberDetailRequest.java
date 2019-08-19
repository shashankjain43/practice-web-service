
package com.snapdeal.ums.subscription.ext.subscription;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreateEmailSubscriberDetailRequest
    extends ServiceRequest
{

    /**
     * 
     */
    private static final long serialVersionUID = -2074368570444609412L;
    @Tag(3)
    private String email;
    @Tag(4)
    private String code;

    public CreateEmailSubscriberDetailRequest() {
    }
    
    
    public CreateEmailSubscriberDetailRequest(String email, String code) {
        super();
        this.email = email;
        this.code = code;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

}
