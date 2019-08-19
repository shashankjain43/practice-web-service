
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.CorporateSRO;

public class SendCorporateEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8447364268777440043L;
	@Tag(3)
    private CorporateSRO corporate;

	
    public SendCorporateEmailRequest(CorporateSRO corporate) {
        super();
        this.corporate = corporate;
    }

    public SendCorporateEmailRequest() {
    }

    public CorporateSRO getCorporate() {
        return corporate;
    }

    public void setCorporate(CorporateSRO corporate) {
        this.corporate = corporate;
    }

}
