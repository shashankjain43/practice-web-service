
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterSRO;

public class GetNewslettersResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = 3616848790174877670L;
	@Tag(5)
    private List<NewsletterSRO> getNewsletters = new ArrayList<NewsletterSRO>();

    public GetNewslettersResponse() {
    }

    public GetNewslettersResponse(List<NewsletterSRO> getNewsletters) {
        super();
        this.getNewsletters = getNewsletters;
    }

    public List<NewsletterSRO> getGetNewsletters() {
        return getNewsletters;
    }

    public void setGetNewsletters(List<NewsletterSRO> getNewsletters) {
        this.getNewsletters = getNewsletters;
    }

}
