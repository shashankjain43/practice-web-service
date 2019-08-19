
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class GetNewsletterDetailsResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -8013102793955452209L;
	@Tag(5)
    private NewsletterSRO getNewsletterDetails;

    public GetNewsletterDetailsResponse() {
    }

    public GetNewsletterDetailsResponse(NewsletterSRO getNewsletterDetails) {
        super();
        this.getNewsletterDetails = getNewsletterDetails;
    }

    public NewsletterSRO getGetNewsletterDetails() {
        return getNewsletterDetails;
    }

    public void setGetNewsletterDetails(NewsletterSRO getNewsletterDetails) {
        this.getNewsletterDetails = getNewsletterDetails;
    }

}
