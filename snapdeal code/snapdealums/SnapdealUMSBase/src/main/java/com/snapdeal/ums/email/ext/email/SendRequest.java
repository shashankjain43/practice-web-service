
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.EmailMessageSRO;

public class SendRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2118253150369294694L;
	@Tag(3)
    private EmailMessageSRO message;

    public SendRequest() {
    }
    
    public SendRequest(EmailMessageSRO message) {
        super();
        this.message = message;
    }

    public EmailMessageSRO getMessage() {
        return message;
    }

    public void setMessage(EmailMessageSRO message) {
        this.message = message;
    }

}
