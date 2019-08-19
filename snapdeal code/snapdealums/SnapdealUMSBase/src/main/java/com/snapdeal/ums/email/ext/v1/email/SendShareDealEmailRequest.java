
package com.snapdeal.ums.email.ext.v1.email;

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
    private Long dealId;
    
    

    public SendShareDealEmailRequest(String to, String from, String recipientEmail, Long deal) {
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

    /**
     * @return the dealId
     */
    public Long getDealId() {
        return dealId;
    }

    /**
     * @param dealId the dealId to set
     */
    public void setDealId(Long dealId) {
        this.dealId = dealId;
    }

}
