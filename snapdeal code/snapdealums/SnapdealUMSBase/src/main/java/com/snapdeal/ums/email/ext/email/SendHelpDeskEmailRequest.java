
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendHelpDeskEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -1291584832900772008L;
	@Tag(3)
    private String subject;
    @Tag(4)
    private String name;
    @Tag(5)
    private String email;
    @Tag(6)
    private String mobile;
    @Tag(7)
    private String orderId;
    @Tag(8)
    private String itemName;
    @Tag(9)
    private String comments;
    @Tag(10)
    private String ticketId;

    public SendHelpDeskEmailRequest() {
    }

    public SendHelpDeskEmailRequest(String subject, String name, String email, String mobile, String orderId, String itemName, String comments, String ticketId) {
        super();
        this.subject = subject;
        this.name = name;
        this.email = email;
        this.mobile = mobile;
        this.orderId = orderId;
        this.itemName = itemName;
        this.comments = comments;
        this.ticketId = ticketId;
    }

    public String getSubject() {
        return subject;
    }

    public void setSubject(String subject) {
        this.subject = subject;
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

    public String getMobile() {
        return mobile;
    }

    public void setMobile(String mobile) {
        this.mobile = mobile;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public String getComments() {
        return comments;
    }

    public void setComments(String comments) {
        this.comments = comments;
    }

    public String getTicketId() {
        return ticketId;
    }

    public void setTicketId(String ticketId) {
        this.ticketId = ticketId;
    }

}
