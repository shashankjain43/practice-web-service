
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendGroupBuyEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8254372723584676664L;
	@Tag(3)
    private Object groupDeal;
    @Tag(4)
    private String emailTemplate;
    @Tag(5)
    private String to;

    public SendGroupBuyEmailRequest() {
    }

    public SendGroupBuyEmailRequest(Object groupDeal, String emailTemplate, String to) {
        super();
        this.groupDeal = groupDeal;
        this.emailTemplate = emailTemplate;
        this.to = to;
    }

    public Object getGroupDeal() {
        return groupDeal;
    }

    public void setGroupDeal(Object groupDeal) {
        this.groupDeal = groupDeal;
    }

    public String getEmailTemplate() {
        return emailTemplate;
    }

    public void setEmailTemplate(String emailTemplate) {
        this.emailTemplate = emailTemplate;
    }

    public String getTo() {
        return to;
    }

    public void setTo(String to) {
        this.to = to;
    }

}
