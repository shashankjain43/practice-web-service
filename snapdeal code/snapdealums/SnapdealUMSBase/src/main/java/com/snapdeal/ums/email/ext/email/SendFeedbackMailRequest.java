
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendFeedbackMailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 4956268241259885574L;
	@Tag(3)
    private Integer suborderId;
    @Tag(4)
    private Integer catalogId;
    @Tag(5)
    private String contentPath;
    @Tag(6)
    private String contextPath;
    @Tag(7)
    private boolean redeemed;

    public SendFeedbackMailRequest() {
    }

    public SendFeedbackMailRequest(Integer suborderId, Integer catalog, String contentPath, String contextPath, boolean redeemed) {
        super();
        this.suborderId = suborderId;
        this.catalogId = catalog;
        this.contentPath = contentPath;
        this.contextPath = contextPath;
        this.redeemed = redeemed;
    }

    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

    public Integer getCatalog() {
        return catalogId;
    }

    public void setCatalog(Integer catalog) {
        this.catalogId = catalog;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public boolean getRedeemed() {
        return redeemed;
    }

    public void setRedeemed(boolean redeemed) {
        this.redeemed = redeemed;
    }

}
