
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.newsletter.NewsletterEspMappingSRO;

public class PersistRequest2
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 8878443313644678448L;
	@Tag(3)
    private NewsletterEspMappingSRO newsletter;

    public PersistRequest2() {
    }

    public NewsletterEspMappingSRO getNewsletterEspMapping() {
        return newsletter;
    }

    public void setNewsletter(NewsletterEspMappingSRO newsletter) {
        this.newsletter = newsletter;
    }

    public PersistRequest2(NewsletterEspMappingSRO newsletter) {
        this.newsletter = newsletter;
    }

}
