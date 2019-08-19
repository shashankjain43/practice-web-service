
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.user.UserSRO;

public class SendCustomerCareEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 673018080455270352L;
	@Tag(3)
    private UserSRO user;
    @Tag(4)
    private Integer dealId;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;

    public SendCustomerCareEmailRequest() {
    }

    public SendCustomerCareEmailRequest(UserSRO user, Integer deal, String contextPath, String contentPath) {
        super();
        this.user = user;
        this.dealId = deal;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public UserSRO getUser() {
        return user;
    }

    public void setUser(UserSRO user) {
        this.user = user;
    }

    public Integer getDeal() {
        return dealId;
    }

    public void setDeal(Integer deal) {
        this.dealId = deal;
    }

    public String getContextPath() {
        return contextPath;
    }

    public void setContextPath(String contextPath) {
        this.contextPath = contextPath;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}
