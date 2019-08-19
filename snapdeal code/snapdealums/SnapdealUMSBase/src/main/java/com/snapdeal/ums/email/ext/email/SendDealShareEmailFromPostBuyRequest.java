
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendDealShareEmailFromPostBuyRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8167702522076663097L;
	@Tag(3)
    private String refererEmail;
    @Tag(4)
    private String name;
    @Tag(5)
    private String from;
    @Tag(6)
    private String recipientName;
    @Tag(7)
    private String url;
    @Tag(8)
    private String dealDetail;

    public SendDealShareEmailFromPostBuyRequest() {
    }

    public SendDealShareEmailFromPostBuyRequest(String refererEmail, String name, String from, String recipientName, String url, String dealDetail) {
        super();
        this.refererEmail = refererEmail;
        this.name = name;
        this.from = from;
        this.recipientName = recipientName;
        this.url = url;
        this.dealDetail = dealDetail;
    }

    public String getRefererEmail() {
        return refererEmail;
    }

    public void setRefererEmail(String refererEmail) {
        this.refererEmail = refererEmail;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getFrom() {
        return from;
    }

    public void setFrom(String from) {
        this.from = from;
    }

    public String getRecipientName() {
        return recipientName;
    }

    public void setRecipientName(String recipientName) {
        this.recipientName = recipientName;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDealDetail() {
        return dealDetail;
    }

    public void setDealDetail(String dealDetail) {
        this.dealDetail = dealDetail;
    }

}
