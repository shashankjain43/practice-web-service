
package com.snapdeal.ums.ext.user;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class CreateEmailVerificationCodeRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -5234787528393574289L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String source;
    @Tag(5)
    private String targetUrl;

    public CreateEmailVerificationCodeRequest() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public String getTargetUrl() {
        return targetUrl;
    }

    public void setTargetUrl(String targetUrl) {
        this.targetUrl = targetUrl;
    }

    public CreateEmailVerificationCodeRequest(String email, String source, String targetUrl) {
        this.email = email;
        this.source = source;
        this.targetUrl = targetUrl;
    }

}
