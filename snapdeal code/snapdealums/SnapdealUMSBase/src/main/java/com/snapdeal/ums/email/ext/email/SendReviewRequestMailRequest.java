
package com.snapdeal.ums.email.ext.email;

import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendReviewRequestMailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2746067616117390726L;
	@Tag(3)
    private List<Integer> suborderIds;
    @Tag(4)
    private String contentPath;
    @Tag(5)
    private String contextPath;

    public SendReviewRequestMailRequest() {
    }

    public SendReviewRequestMailRequest(List<Integer> suborderIds, String contentPath, String contextPath) {
        super();
        this.suborderIds = suborderIds;
        this.contentPath = contentPath;
        this.contextPath = contextPath;
    }


    public List<Integer> getSuborderIds() {
        return suborderIds;
    }

    public void setSuborderIds(List<Integer> suborderIds) {
        this.suborderIds = suborderIds;
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

}
