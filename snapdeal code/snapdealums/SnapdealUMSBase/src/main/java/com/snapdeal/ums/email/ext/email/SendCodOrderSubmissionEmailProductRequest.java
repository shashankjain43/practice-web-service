
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCodOrderSubmissionEmailProductRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3212876950072423296L;
	@Tag(3)
    private Integer suborderId;
    @Tag(4)
    private String contextPath;
    @Tag(5)
    private String contentPath;

    public SendCodOrderSubmissionEmailProductRequest() {
    }

    public SendCodOrderSubmissionEmailProductRequest(Integer suborderId, String contextPath, String contentPath) {
        super();
        this.suborderId = suborderId;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

   

    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

    public String getcontextPath() {
        return contextPath;
    }

    public void setcontextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getcontentPath() {
        return contentPath;
    }

    public void setcontentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}
