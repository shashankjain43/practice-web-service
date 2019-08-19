
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class GetNewsletterByMsgIdRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6320910974433685144L;
	@Tag(3)
    private String msgId;

    public GetNewsletterByMsgIdRequest() {
    }

    public String getMsgId() {
        return msgId;
    }

    public void setMsgId(String msgId) {
        this.msgId = msgId;
    }

    public GetNewsletterByMsgIdRequest(String msgId) {
        this.msgId = msgId;
    }

}
