
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class PersistRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -752889831459176012L;
	@Tag(3)
    private NewsletterSRO newsletter;
    public PersistRequest() {
    }


    public NewsletterSRO getNewsletter() {
        return newsletter;
    }

    public void setNewsletter(NewsletterSRO newsletter) {
        this.newsletter = newsletter;
    }


    public PersistRequest(NewsletterSRO newsletter) {
        this.newsletter = newsletter;
    }

}
