
package com.snapdeal.ums.email.ext.email;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceRequest;
import com.snapdeal.ums.core.sro.email.FeaturedSRO;

public class SendFeaturedEmailRequest
    extends ServiceRequest
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3559539159611399997L;
	@Tag(3)
    private FeaturedSRO featured;

    public SendFeaturedEmailRequest() {
    }

    public SendFeaturedEmailRequest(FeaturedSRO featured) {
        super();
        this.featured = featured;
    }

    public FeaturedSRO getFeatured() {
        return featured;
    }

    public void setFeatured(FeaturedSRO featured) {
        this.featured = featured;
    }

}
