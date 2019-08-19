
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.ArrayList;
import java.util.List;
import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class GetNewsletterDetailsResponse3
    extends ServiceResponse
{

    /**
     * 
     */
    private static final long serialVersionUID = -6051158890033150018L;
    @Tag(5)
    private List<NewsletterSRO> getNewsletterDetails = new ArrayList<NewsletterSRO>();

    public GetNewsletterDetailsResponse3() {
    }

    public GetNewsletterDetailsResponse3(List<NewsletterSRO> getNewsletterDetails) {
        super();
        this.getNewsletterDetails = getNewsletterDetails;
    }

    public List<NewsletterSRO> getGetNewsletterDetails() {
        return getNewsletterDetails;
    }

    public void setGetNewsletterDetails(List<NewsletterSRO> getNewsletterDetails) {
        this.getNewsletterDetails = getNewsletterDetails;
    }

}
