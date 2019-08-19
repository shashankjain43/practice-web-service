
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendValentineEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 6113319707088098181L;
	@Tag(3)
    private String name;
    @Tag(4)
    private String recipient;
    @Tag(5)
    private String url;

    public SendValentineEmailRequest() {
    }

    public SendValentineEmailRequest(String name, String recipient, String url) {
        super();
        this.name = name;
        this.recipient = recipient;
        this.url = url;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRecipient() {
        return recipient;
    }

    public void setRecipient(String recipient) {
        this.recipient = recipient;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

}
