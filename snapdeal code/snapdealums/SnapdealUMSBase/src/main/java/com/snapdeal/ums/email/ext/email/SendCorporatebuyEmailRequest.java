
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;

public class SendCorporatebuyEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 1728136986876110807L;
	@Tag(3)
    private Object corporate;

    public SendCorporatebuyEmailRequest() {
    }

    public SendCorporatebuyEmailRequest(Object corporate) {
        super();
        this.corporate = corporate;
    }

    public Object getCorporate() {
        return corporate;
    }

    public void setCorporate(Object corporate) {
        this.corporate = corporate;
    }

}
