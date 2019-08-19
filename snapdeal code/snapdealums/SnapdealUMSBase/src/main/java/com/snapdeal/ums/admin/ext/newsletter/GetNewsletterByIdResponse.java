
package com.snapdeal.ums.admin.ext.newsletter;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class GetNewsletterByIdResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3050065899801592531L;
	@Tag(5)
    private NewsletterSRO newsletter;

    public GetNewsletterByIdResponse() {
    }

    public GetNewsletterByIdResponse(NewsletterSRO getNewsletterById) {
        super();
        this.newsletter = getNewsletterById;
    }

    public NewsletterSRO getGetNewsletterById() {
        return newsletter;
    }

    public void setGetNewsletterById(NewsletterSRO getNewsletterById) {
        this.newsletter = getNewsletterById;
    }

}
