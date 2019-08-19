
package com.snapdeal.ums.email.ext.email;

import java.util.HashMap;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendAffiliateSubscriptionEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2890958403856081283L;
	@Tag(3)
    private String email;
    @Tag(4)
    private List<HashMap<String, String>> listOfMap;

    public SendAffiliateSubscriptionEmailRequest() {
    }
    
    public SendAffiliateSubscriptionEmailRequest(String email, List<HashMap<String, String>> listOfMap) {
        super();
        this.email = email;
        this.listOfMap = listOfMap;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public List<HashMap<String, String>> getListOfMap() {
        return listOfMap;
    }

    public void setListOfMap(List<HashMap<String, String>> listOfMap) {
        this.listOfMap = listOfMap;
    }

}
