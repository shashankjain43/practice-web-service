
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCustomerFeedbackEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2457771937725966573L;
    @Tag(4)
    private String name;
    @Tag(5)
    private String email;
    @Tag(6)
    private String city;
    @Tag(7)
    private String category;
    @Tag(8)
    private String message;

    public SendCustomerFeedbackEmailRequest() {
    }

    public SendCustomerFeedbackEmailRequest(String name, String email, String city, String category, String message) {
        super();
        this.name = name;
        this.email = email;
        this.city = city;
        this.category = category;
        this.message = message;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

}
