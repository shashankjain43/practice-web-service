
package com.snapdeal.ums.admin.ext.newsletter;

import java.util.ArrayList;
import java.util.List;

import com.dyuproject.protostuff.Tag;
import com.snapdeal.base.model.common.ServiceResponse;
import com.snapdeal.ums.core.sro.newsletter.NewsletterEspMappingSRO;

public class GetNewsletterESPMappingResponse
    extends ServiceResponse
{

    /**
	 * 
	 */
	private static final long serialVersionUID = -7908147219259379728L;
	@Tag(5)
    private List<NewsletterEspMappingSRO> getNewsletterESPMapping = new ArrayList<NewsletterEspMappingSRO>();

    public GetNewsletterESPMappingResponse() {
    }

    public GetNewsletterESPMappingResponse(List<NewsletterEspMappingSRO> getNewsletterESPMapping) {
        super();
        this.getNewsletterESPMapping = getNewsletterESPMapping;
    }

    public List<NewsletterEspMappingSRO> getGetNewsletterESPMapping() {
        return getNewsletterESPMapping;
    }

    public void setGetNewsletterESPMapping(List<NewsletterEspMappingSRO> getNewsletterESPMapping) {
        this.getNewsletterESPMapping = getNewsletterESPMapping;
    }

}
