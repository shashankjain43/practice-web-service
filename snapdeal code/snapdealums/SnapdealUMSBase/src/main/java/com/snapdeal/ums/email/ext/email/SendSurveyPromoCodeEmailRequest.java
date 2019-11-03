
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.core.sro.order.PromoCodeSRO;

public class SendSurveyPromoCodeEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5052196238522865083L;
	@Tag(3)
    private String email;
    @Tag(4)
    private PromoCodeSRO prmCode;
    @Tag(5)
    private String contextPath;
    @Tag(6)
    private String contentPath;

    public SendSurveyPromoCodeEmailRequest() {
    }

    public SendSurveyPromoCodeEmailRequest(String email, PromoCodeSRO prmCode, String contextPath, String contentPath) {
        super();
        this.email = email;
        this.prmCode = prmCode;
        this.contextPath = contextPath;
        this.contentPath = contentPath;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public PromoCodeSRO getPrmCode() {
        return prmCode;
    }

    public void setPrmCode(PromoCodeSRO prmCode) {
        this.prmCode = prmCode;
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