
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendSampleVoucherEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7409087639890142336L;
	@Tag(3)
    private Integer suborderId;
    @Tag(4)
    private Integer zoneId;
    @Tag(5)
    private String email;
    @Tag(6)
    private String contentPath;

    
    public SendSampleVoucherEmailRequest(Integer suborderId, Integer zone, String email, String contentPath) {
        super();
        this.suborderId = suborderId;
        this.zoneId = zone;
        this.email = email;
        this.contentPath = contentPath;
    }

    public SendSampleVoucherEmailRequest() {
    }


    public Integer getSuborderId() {
        return suborderId;
    }

    public void setSuborderId(Integer suborderId) {
        this.suborderId = suborderId;
    }

    public Integer getZone() {
        return zoneId;
    }

    public void setZone(Integer zone) {
        this.zoneId = zone;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getContentPath() {
        return contentPath;
    }

    public void setContentPath(String contentPath) {
        this.contentPath = contentPath;
    }

}
