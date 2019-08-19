
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.CancelledOrderFeedbackDOSRO;

public class SendCancelledOrderFeedbackRequestEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -2561961465318125000L;
	@Tag(3)
    private CancelledOrderFeedbackDOSRO cancelledOrderFeedbackSRO;
    @Tag(4)
    private String contentPath;
    @Tag(5)
    private String contextPath;

    public SendCancelledOrderFeedbackRequestEmailRequest(CancelledOrderFeedbackDOSRO cancelledOrderFeedbackSRO, String contentPath, String contextPath) {
        super();
        this.cancelledOrderFeedbackSRO = cancelledOrderFeedbackSRO;
        this.contentPath = contentPath;
        this.contextPath = contextPath;
    }


    public SendCancelledOrderFeedbackRequestEmailRequest() {
    }

    
    public CancelledOrderFeedbackDOSRO getCancelledOrderFeedbackSRO() {
        return cancelledOrderFeedbackSRO;
    }


    public void setCancelledOrderFeedbackSRO(CancelledOrderFeedbackDOSRO cancelledOrderFeedbackSRO) {
        this.cancelledOrderFeedbackSRO = cancelledOrderFeedbackSRO;
    }


    public CancelledOrderFeedbackDOSRO getCancelledOrderFeedbackDOSRO() {
        return cancelledOrderFeedbackSRO;
    }

    public void setCancelledOrderFeedbackDTO(CancelledOrderFeedbackDOSRO cancelledOrderFeedbackDTO) {
        this.cancelledOrderFeedbackSRO = cancelledOrderFeedbackDTO;
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
