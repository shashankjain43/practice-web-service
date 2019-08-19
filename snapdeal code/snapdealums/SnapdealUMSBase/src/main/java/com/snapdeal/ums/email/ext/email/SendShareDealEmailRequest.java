
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendShareDealEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -3515903664577924010L;
	@Tag(3)
    private String to;
    @Tag(4)
    private String from;
    @Tag(5)
    private String recipientEmail;
    @Tag(6)
    private Integer dealId;
    
    

    public SendShareDealEmailRequest(String to, String from, String recipientEmail, Integer deal) {
        this.to = to;
        this.from = from;
        this.recipientEmail = recipientEmail;
        this.dealId = deal;
    }

    public SendShareDealEmailRequest() {
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipientEmail() {
        return recipientEmail;
    }

    public void setRecipientEmail(String recipientEmail) {
        this.recipientEmail = recipientEmail;
    }

    public Integer getDeal() {
        return dealId;
    }

    public void setDeal(Integer deal) {
        this.dealId = deal;
    }

}
