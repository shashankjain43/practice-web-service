
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class UpdateRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 2528677236857328388L;
	@Tag(3)
    private NewsletterSRO newsletter;
	public UpdateRequest() {
    }


    public NewsletterSRO getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(NewsletterSRO newsletter) {
        this.newsletter = newsletter;
    }


    public UpdateRequest(NewsletterSRO newsletter) {
        this.newsletter = newsletter;
    }

}
