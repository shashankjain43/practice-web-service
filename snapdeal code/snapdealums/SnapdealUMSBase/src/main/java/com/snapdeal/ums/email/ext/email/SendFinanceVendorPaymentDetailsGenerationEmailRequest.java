
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendFinanceVendorPaymentDetailsGenerationEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -6632962539632211714L;
	@Tag(3)
    private String email;
    @Tag(4)
    private String path;

    public SendFinanceVendorPaymentDetailsGenerationEmailRequest() {
    }

    public SendFinanceVendorPaymentDetailsGenerationEmailRequest(String email, String path) {
        super();
        this.email = email;
        this.path = path;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

}
